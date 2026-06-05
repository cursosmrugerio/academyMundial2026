# Aprobación de Crédito (Kogito + Spring Boot)

Servicio de automatización que evalúa solicitudes de crédito combinando un
**proceso BPMN** (orquestación) con una **decisión DMN** (scoring). Está construido
sobre **Apache KIE / Kogito 10.2.0** con runtime **Spring Boot** y persistencia
**en memoria**, de modo que arranca sin necesidad de base de datos ni infraestructura
externa.

## ¿Qué hace?

1. Recibe una `SolicitudCredito` por REST.
2. La tarea **Evaluar score** calcula un `score` y una `decision`
   (`APROBADO`, `RECHAZADO` o `REVISION`) con la misma lógica de negocio modelada
   en el DMN `scoringCredito`.
3. Un *gateway exclusivo* enruta según la decisión:
   - **APROBADO** → fin.
   - **RECHAZADO** → fin.
   - **REVISION** → tarea humana **"Revisión del analista"** (grupo `analistas`); al
     completarla, el proceso termina.

> **Sobre el cálculo del scoring.** En este proyecto la tarea *Evaluar score* es una
> **Script Task** que calcula el resultado en Java, para que el ejemplo corra sin
> configuración adicional y de forma determinista. La MISMA lógica está modelada como
> decisión **DMN** (`scoringCredito.dmn`), desplegada como servicio independiente
> (`POST /scoringCredito`) y probada de forma aislada en `ScoringDmnTest`. En un sistema
> productivo, *Evaluar score* sería una **Business Rule Task** que invoca ese DMN.

### Artefactos principales

| Artefacto | Archivo | Endpoint REST autogenerado |
|-----------|---------|----------------------------|
| Proceso BPMN `aprobacionCredito` | `src/main/resources/aprobacionCredito.bpmn2` | `POST /aprobacionCredito` |
| Decisión DMN `scoringCredito` | `src/main/resources/scoringCredito.dmn` | `POST /scoringCredito` |

### Modelos de datos

`SolicitudCredito` (entrada):

```json
{
  "nombre": "Ana López",
  "edad": 34,
  "ingresosMensuales": 25000.0,
  "montoSolicitado": 120000.0,
  "plazoMeses": 24,
  "tieneMora": false
}
```

`Evaluacion` (salida): `score` (int), `decision` (`APROBADO`/`RECHAZADO`/`REVISION`),
`motivo` (texto).

## Requisitos

- **Java 21** (`java -version` debe reportar 21).
- **Maven 3.9.6+**.

No se requiere base de datos: el estado del proceso se mantiene en memoria.

## Cómo ejecutar

Desde la carpeta del proyecto:

```bash
mvn spring-boot:run
```

La aplicación queda escuchando en `http://localhost:8080`.

## Probar el servicio

### 1. Iniciar una solicitud de crédito

```bash
curl -s -X POST http://localhost:8080/aprobacionCredito \
  -H 'Content-Type: application/json' \
  -d '{
        "solicitud": {
          "nombre": "Ana López",
          "edad": 34,
          "ingresosMensuales": 25000.0,
          "montoSolicitado": 120000.0,
          "plazoMeses": 24,
          "tieneMora": false
        }
      }'
```

La respuesta incluye el `id` de la instancia y la `evaluacion` calculada (`score`,
`decision` y `motivo`).
Si la solicitud queda **APROBADA** o **RECHAZADA**, el proceso termina de inmediato y
no quedará instancia activa.

Si la decisión es **REVISION**, el proceso se detiene en la tarea humana y la respuesta
trae el `id` de la instancia (necesario para los pasos siguientes). Guárdalo:

```bash
PID=<id-de-la-instancia>
```

### 2. Ver la tarea de revisión pendiente

Listar las tareas de la instancia:

```bash
curl -s "http://localhost:8080/aprobacionCredito/$PID/tasks"
```

Esto devuelve la tarea **"Revisión del analista"** con su `taskId`. Para ver el detalle:

```bash
TASK_ID=<taskId-devuelto>
curl -s "http://localhost:8080/aprobacionCredito/$PID/Revisión%20del%20analista/$TASK_ID"
```

> El nombre de la tarea aparece codificado en la URL (los espacios y acentos se
> codifican). El nombre exacto que se debe usar es el que devuelve el listado de tareas.

### 3. Completar la tarea de revisión

Al completar la tarea, el proceso continúa hasta el fin:

```bash
curl -s -X POST "http://localhost:8080/aprobacionCredito/$PID/Revisión%20del%20analista/$TASK_ID" \
  -H 'Content-Type: application/json' \
  -d '{}'
```

### 4. (Opcional) Evaluar solo la decisión DMN

El scoring también se expone de forma independiente:

```bash
curl -s -X POST http://localhost:8080/scoringCredito \
  -H 'Content-Type: application/json' \
  -d '{
        "solicitud": {
          "edad": 34,
          "ingresosMensuales": 25000.0,
          "montoSolicitado": 120000.0,
          "plazoMeses": 24,
          "tieneMora": false
        }
      }'
```

## Ejecutar las pruebas

```bash
mvn test
```

## Notas

- **Persistencia en memoria**: al reiniciar la aplicación se pierden las instancias en
  curso. Para entornos productivos se añadiría un add-on de persistencia (p. ej. JDBC).
- Los endpoints de gestión de procesos (listado/aborto de instancias) están disponibles
  gracias al add-on de *process management*.
