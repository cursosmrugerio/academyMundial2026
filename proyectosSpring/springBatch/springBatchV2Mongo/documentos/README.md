# Documentación — Spring Batch (academia)

Esta carpeta contiene el material del proyecto **`spring-batch-v2-mongo`**, el proyecto
de Spring Batch de la academia. Procesa empleados en dos etapas y toca **dos bases de
datos**: MySQL y MongoDB.

## Documentos

| Documento | Para qué sirve |
|-----------|----------------|
| [`guia-spring-batch-v2-mongo.md`](guia-spring-batch-v2-mongo.md) | **Tutorial.** Explica qué es Spring Batch, por qué se usa, y recorre el código del proyecto archivo por archivo. |
| [`guia-ejecucion-paso-a-paso.md`](guia-ejecucion-paso-a-paso.md) | **Manual de ejecución.** Cómo levantar los contenedores (MySQL y MongoDB), crear la tabla, ejecutar la app y verificar los resultados. |

## Orden de lectura sugerido

1. **Entiende el concepto y el código** → lee el [tutorial](guia-spring-batch-v2-mongo.md)
   (secciones 1 a 7). Aquí ves *qué* hace el proyecto y *cómo* está escrito.
2. **Pon a correr el proyecto** → sigue la [guía de ejecución](guia-ejecucion-paso-a-paso.md)
   paso a paso (Docker → tabla → ejecutar → verificar).
3. **Cierra el círculo** → vuelve al tutorial (secciones 8 a 11) para relacionar la
   salida en consola y los datos en las bases con lo que leíste del código.

## Qué necesitas

- **Java 17** y **Maven**.
- **Docker Desktop** (para los contenedores MySQL y MongoDB).
- Opcional: **DBeaver** (cliente MySQL) y **MongoDB Compass** (cliente MongoDB) para
  inspeccionar los datos con interfaz gráfica.
