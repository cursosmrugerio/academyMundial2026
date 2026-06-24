# Guia de ejecucion paso a paso - Spring Batch v2 Mongo

Esta guia explica, paso a paso, como dejar corriendo el proyecto **springBatchV2Mongo**
desde cero. Cada paso se ira agregando en orden.

El proyecto necesita dos bases de datos en contenedores Docker:

- **MySQL** (contenedor `mysql-academia`) - origen y destino del Step 1.
- **MongoDB** (contenedor `mongo-academia`) - destino del Step 2.

---

## Paso 1: Descargar la imagen de MySQL y levantar el contenedor

> **Prerequisito:** Tener **Docker Desktop** instalado y corriendo.
> Para comprobarlo, abre una terminal y ejecuta `docker --version`. Si responde con
> un numero de version, Docker esta listo.

El proyecto se conecta a MySQL con estos datos (definidos en `application.properties`):

| Dato | Valor |
|------|-------|
| Host y puerto | `localhost:3306` |
| Base de datos | `academia` |
| Usuario | `alumno` |
| Contraseña | `alumno123` |
| Contraseña root | `root123` |

### 1.1 Descargar la imagen

Descarga la imagen oficial de MySQL version 8 desde Docker Hub:

```bash
docker pull mysql:8
```

La primera vez tarda unos minutos (descarga ~250 MB). Si ya la descargaste antes,
Docker la reutiliza y el comando termina al instante.

### 1.2 Crear y levantar el contenedor

Crea el contenedor `mysql-academia` con un solo comando. Las variables `-e` hacen que
MySQL cree **automaticamente** la base de datos `academia` y el usuario `alumno` al
arrancar por primera vez, asi no tienes que crearlos a mano despues:

```bash
docker run --name mysql-academia \
  -e MYSQL_ROOT_PASSWORD=root123 \
  -e MYSQL_DATABASE=academia \
  -e MYSQL_USER=alumno \
  -e MYSQL_PASSWORD=alumno123 \
  -p 3306:3306 \
  -d mysql:8
```

Que hace cada parte:

| Parametro | Que hace |
|-----------|----------|
| `--name mysql-academia` | Nombre del contenedor (el que usa la app) |
| `-e MYSQL_ROOT_PASSWORD=root123` | Contraseña del usuario `root` |
| `-e MYSQL_DATABASE=academia` | Crea la base de datos `academia` al arrancar |
| `-e MYSQL_USER=alumno` | Crea el usuario `alumno` |
| `-e MYSQL_PASSWORD=alumno123` | Contraseña del usuario `alumno` |
| `-p 3306:3306` | Expone el puerto 3306 de MySQL en tu maquina |
| `-d` | Ejecuta el contenedor en segundo plano (modo *detached*) |
| `mysql:8` | Imagen que se va a usar |

> **Nota:** El usuario `alumno` queda con todos los privilegios sobre la base
> `academia` automaticamente. No necesitas ejecutar `GRANT` manualmente.

### 1.3 Verificar que esta corriendo

```bash
docker ps
```

Debes ver el contenedor `mysql-academia` con la imagen `mysql:8`, estado `Up` y el
puerto `0.0.0.0:3306->3306/tcp`:

```
NAMES            IMAGE     STATUS         PORTS
mysql-academia   mysql:8   Up X minutes   0.0.0.0:3306->3306/tcp
```

Opcionalmente, comprueba que MySQL ya termino de inicializar revisando los logs
(busca la linea `ready for connections`):

```bash
docker logs mysql-academia
```

La primera vez, MySQL tarda entre 10 y 30 segundos en quedar listo para aceptar
conexiones. Si la app falla con "Communications link failure", espera unos segundos
y vuelve a intentar.

### 1.4 Si el contenedor ya existe

Si ya creaste `mysql-academia` antes (con `docker run`), **no lo vuelvas a crear**.
Solo arrancalo:

```bash
docker start mysql-academia
```

Para detenerlo cuando termines:

```bash
docker stop mysql-academia
```

> **Importante:** El comando `docker run` solo se usa **una vez** (la primera).
> A partir de ahi, se usa `docker start` / `docker stop` para encender y apagar el
> contenedor existente. Si intentas hacer `docker run` con un nombre que ya existe,
> Docker dara el error `Conflict. The container name "/mysql-academia" is already in use`.

---

## Paso 2: Descargar la imagen de MongoDB y levantar el contenedor

> **Prerequisito:** El mismo que el Paso 1 (Docker Desktop instalado y corriendo).

El proyecto se conecta a MongoDB con estos datos (definidos en `application.properties`,
en la linea `spring.data.mongodb.uri`):

| Dato | Valor |
|------|-------|
| Host y puerto | `localhost:27018` |
| Base de datos | `academia` |
| Usuario | `root` |
| Contraseña | `root123` |
| Base de autenticacion | `admin` (`authSource=admin`) |

> **¿Por que el puerto 27018 y no el 27017?**
> El 27017 es el puerto por defecto de MongoDB, pero en esta maquina ya lo usa otro
> contenedor (`banca-mongo`). Para que ambos puedan correr a la vez, este contenedor
> publica MongoDB en el puerto **27018** de tu maquina. Por dentro, MongoDB sigue
> escuchando en su puerto normal (27017); solo cambia el puerto "de afuera".

### 2.1 Descargar la imagen

Descarga la imagen oficial de MongoDB version 7 desde Docker Hub:

```bash
docker pull mongo:7
```

La primera vez tarda unos minutos (descarga ~280 MB). Si ya la tienes, Docker la
reutiliza y el comando termina al instante.

### 2.2 Crear y levantar el contenedor

Crea el contenedor `mongo-academia`. Las variables `-e` crean el usuario administrador
`root` la primera vez que arranca:

```bash
docker run --name mongo-academia \
  -e MONGO_INITDB_ROOT_USERNAME=root \
  -e MONGO_INITDB_ROOT_PASSWORD=root123 \
  -p 27018:27017 \
  -d mongo:7
```

Que hace cada parte:

| Parametro | Que hace |
|-----------|----------|
| `--name mongo-academia` | Nombre del contenedor (el que usa la app) |
| `-e MONGO_INITDB_ROOT_USERNAME=root` | Crea el usuario administrador `root` |
| `-e MONGO_INITDB_ROOT_PASSWORD=root123` | Contraseña del usuario `root` |
| `-p 27018:27017` | Publica el MongoDB interno (27017) en el puerto 27018 de tu maquina |
| `-d` | Ejecuta el contenedor en segundo plano (modo *detached*) |
| `mongo:7` | Imagen que se va a usar |

> **Nota:** No necesitas crear la base de datos `academia` ni la coleccion `reportes`.
> MongoDB las crea automaticamente la primera vez que el Job de Spring Batch escribe
> un documento.

### 2.3 Verificar que esta corriendo

```bash
docker ps
```

Debes ver el contenedor `mongo-academia` con la imagen `mongo:7`, estado `Up` y el
puerto `0.0.0.0:27018->27017/tcp`:

```
NAMES            IMAGE     STATUS         PORTS
mongo-academia   mongo:7   Up X minutes   0.0.0.0:27018->27017/tcp
```

Para comprobar que MongoDB ya acepta conexiones, entra a su consola dentro del
contenedor (la conexion interna sigue siendo por el 27017, por eso aqui no se indica puerto):

```bash
docker exec -it mongo-academia mongosh -u root -p root123
```

Si entra a la consola `test>`, MongoDB esta listo. Escribe `exit` para salir.

### 2.4 Si el contenedor ya existe

Si ya creaste `mongo-academia` antes, **no lo vuelvas a crear**. Solo arrancalo:

```bash
docker start mongo-academia
```

Para detenerlo cuando termines:

```bash
docker stop mongo-academia
```

> **Importante:** Igual que con MySQL, `docker run` se usa **una sola vez**.
> Despues, usa `docker start` / `docker stop` sobre el contenedor existente.

### 2.5 Comprobacion final de los dos contenedores

Con los Pasos 1 y 2 completos, al ejecutar `docker ps` debes ver **ambos** corriendo:

```
NAMES            IMAGE     STATUS        PORTS
mysql-academia   mysql:8   Up X minutes  0.0.0.0:3306->3306/tcp
mongo-academia   mongo:7   Up X minutes  0.0.0.0:27018->27017/tcp
```

Si ves los dos en estado `Up`, las dos bases de datos estan listas para que el
proyecto se conecte.

---

## Paso 3: Conectarte a MongoDB con MongoDB Compass

**MongoDB Compass** es la interfaz grafica oficial de MongoDB. Sirve para explorar las
bases de datos, colecciones y documentos sin usar la consola. La usaremos para revisar
los documentos que el Step 2 del Job guarda en la coleccion `reportes`.

> **Prerequisito:** Tener MongoDB Compass instalado y el contenedor `mongo-academia`
> corriendo (Paso 2).

### 3.1 Abrir el dialogo de nueva conexion

Abre MongoDB Compass y haz clic en **New Connection** (o el boton **+** en la lista de
conexiones). Veras un formulario con un campo **URI**.

### 3.2 Escribir la connection string

Activa el interruptor **Edit Connection String** (si no esta activo) y **reemplaza** el
texto que aparece por defecto (`mongodb://localhost:27017/`) por la cadena del proyecto:

```
mongodb://root:root123@localhost:27018/?authSource=admin
```

Que significa cada parte:

| Parte | Significado |
|-------|-------------|
| `root:root123` | Usuario y contraseña del contenedor (Paso 2) |
| `localhost:27018` | Host y **puerto 27018** (el que publica `mongo-academia`, no el 27017) |
| `authSource=admin` | Base de datos donde esta registrado el usuario `root` |

> **Cuidado con el puerto:** Compass propone `27017` por defecto. Hay que cambiarlo a
> **27018**, que es el puerto que expone este proyecto. Si dejas 27017 te conectarias al
> otro contenedor (`banca-mongo`), no al de este proyecto.

### 3.3 (Opcional) Ponerle nombre a la conexion

En el campo **Name** puedes escribir un nombre para identificarla facilmente, por
ejemplo `mongo-academia`. El campo **Color** y **Favorite this connection** son
opcionales. No necesitas tocar **Advanced Connection Options**.

### 3.4 Conectar

Haz clic en **Save & Connect** (guarda la conexion y conecta) o en **Connect** (conecta
sin guardar).

### 3.5 Que veras

Al conectar, Compass muestra las bases de datos del servidor (`admin`, `config`,
`local`).

> **Nota:** La base de datos `academia` y la coleccion `reportes` **todavia no
> apareceran** si aun no has ejecutado el proyecto. MongoDB las crea hasta que el Job
> escribe el primer documento. Despues de ejecutar la aplicacion, usa el boton de
> refrescar de Compass y veras la base `academia` con la coleccion `reportes` dentro.

---

## Paso 4: Crear la tabla `empleados_procesados` en MySQL

El Step 1 del Job inserta los empleados procesados en una tabla llamada
`empleados_procesados`. **Esta tabla hay que crearla a mano antes de ejecutar la app.**

> **¿Por que a mano?** Spring Batch crea automaticamente sus tablas de control (las
> `BATCH_*`), pero **no** crea las tablas de tu negocio. La tabla `empleados_procesados`
> es nuestra, asi que la creamos nosotros. Si no existe, la app falla con el error
> `Table 'academia.empleados_procesados' doesn't exist`.

La tabla debe tener exactamente las columnas que el Writer del Step 1 espera en su
`INSERT` (`nombre`, `departamento`, `salario`, `bono`), mas un `id` autoincremental.

### 4.1 Crear la tabla desde la terminal

Conectate al MySQL del contenedor como el usuario `alumno`, directamente sobre la base
`academia`:

```bash
docker exec -it mysql-academia mysql -u alumno -palumno123 academia
```

> No hay espacio entre `-p` y la contraseña: se escribe `-palumno123`.

Ya dentro del cliente MySQL (veras el prompt `mysql>`), pega el `CREATE TABLE`:

```sql
CREATE TABLE IF NOT EXISTS empleados_procesados (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    departamento VARCHAR(50) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    bono DECIMAL(10,2) NOT NULL
);
```

Que es cada columna:

| Columna | Tipo | Para que sirve |
|---------|------|----------------|
| `id` | `INT PRIMARY KEY AUTO_INCREMENT` | Identificador unico; MySQL lo genera solo |
| `nombre` | `VARCHAR(100)` | Nombre del empleado (ya en mayusculas tras el Step 1) |
| `departamento` | `VARCHAR(50)` | Departamento |
| `salario` | `DECIMAL(10,2)` | Salario base leido del CSV |
| `bono` | `DECIMAL(10,2)` | Bono calculado por el Step 1 (10% del salario) |

> `CREATE TABLE IF NOT EXISTS` hace que el comando no falle si la tabla ya existia.

### 4.2 Verificar que se creo

Aun dentro del cliente MySQL:

```sql
SHOW TABLES;
DESCRIBE empleados_procesados;
```

`SHOW TABLES` debe listar `empleados_procesados`, y `DESCRIBE` debe mostrar las 5
columnas. Para salir del cliente MySQL:

```sql
exit
```

### 4.3 Alternativa: crearla en una sola linea

Si prefieres no entrar al cliente interactivo, puedes crear la tabla con un solo comando
desde tu terminal:

```bash
docker exec -i mysql-academia mysql -u alumno -palumno123 academia -e "
CREATE TABLE IF NOT EXISTS empleados_procesados (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    departamento VARCHAR(50) NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    bono DECIMAL(10,2) NOT NULL
);
"
```

### 4.4 Alternativa: crearla desde DBeaver

Si conectaste DBeaver a `mysql-academia` (usuario `alumno` / `alumno123`, base
`academia`), abre un editor SQL sobre esa base y ejecuta el mismo `CREATE TABLE` del
punto 4.1.

---

## Paso 5: Ejecutar el proyecto

Al arrancar, la aplicacion ejecuta el Job **automaticamente** (gracias a
`spring.batch.job.enabled=true` en `application.properties`). El Job corre sus dos Steps
y, como no es una app web, **termina por si sola** cuando el Job acaba. Que la app se
cierre al final es lo normal en un proyecto batch.

### 5.1 Antes de ejecutar (checklist)

Asegurate de tener todo lo de los pasos anteriores:

- [ ] Contenedor `mysql-academia` corriendo (`docker ps`).
- [ ] Contenedor `mongo-academia` corriendo (`docker ps`).
- [ ] Tabla `empleados_procesados` creada en MySQL (Paso 4).
- [ ] **Java 17** y **Maven** instalados. Verifica con:

```bash
java -version
mvn -version
```

### 5.2 Ejecutar desde la terminal (Maven)

Situate en la **carpeta raiz del proyecto** (la que tiene el `pom.xml`):

```bash
cd /Users/mike/Desarrollo/academyMundial2026/proyectosSpring/springBatch/springBatchV2Mongo
```

Y ejecuta:

```bash
mvn spring-boot:run
```

Maven descarga las dependencias (solo la primera vez), compila el proyecto y arranca la
aplicacion. El Job se ejecuta de inmediato.

### 5.3 Ejecutar desde Eclipse

1. Abre la clase `SpringBatchApplication.java`
   (en `src/main/java/com/academia/batch/`).
2. Clic derecho sobre ella > **Run As** > **Java Application**.

La consola de Eclipse mostrara la misma salida que la terminal.

### 5.4 Alternativa: compilar el JAR y ejecutarlo

Si prefieres generar el ejecutable y correrlo aparte:

```bash
# 1. Compilar y empaquetar (genera el JAR en target/)
mvn clean package -DskipTests

# 2. Ejecutar el JAR generado
java -jar target/spring-batch-v2-mongo-1.0.0.jar
```

### 5.5 Salida esperada en consola

Si todo esta bien, veras los logs de los dos Steps y, al final, el Job en estado
`COMPLETED`:

```
Executing step: [paso1]
Step 1 - Procesando: JUAN PEREZ | Ventas | Salario: 25000.0 | Bono: 2500.0
Step 1 - Procesando: MARIA LOPEZ | TI | Salario: 35000.0 | Bono: 3500.0
...
Executing step: [paso2]
Step 2 - Reporte: JUAN PEREZ | Ventas | Salario: 25000.0 | Bono: 2500.0 | Total: 27500.0
Step 2 - Reporte: MARIA LOPEZ | TI | Salario: 35000.0 | Bono: 3500.0 | Total: 38500.0
...
Job: [SimpleJob: [name=procesarEmpleadosJob]] completed with the following parameters:
[{run.id=1}] and the following status: [COMPLETED]
```

- **Step 1** leyo el `empleados.csv` y escribio 10 filas en la tabla MySQL
  `empleados_procesados`.
- **Step 2** leyo esas filas de MySQL y escribio 10 documentos en la coleccion
  `reportes` de MongoDB.

Cuando el Job termina, la aplicacion se cierra. En el siguiente paso verificaremos que
los datos quedaron guardados en ambas bases.

> **Si algo falla:**
> - `Communications link failure` → el contenedor MySQL no esta corriendo (`docker start mysql-academia`).
> - `Connection refused` en 27018 → el contenedor MongoDB no esta corriendo (`docker start mongo-academia`).
> - `Table 'academia.empleados_procesados' doesn't exist` → falta el Paso 4.
> - `A job instance already exists` → ya ejecutaste el Job antes; reejecutar funciona
>   gracias al `RunIdIncrementer`, pero conviene limpiar los datos (se vera mas adelante).

---

## Paso 6: Verificar los datos en MySQL y MongoDB

Despues de ejecutar el Job, los datos deben estar en las dos bases:

- En **MySQL**, la tabla `empleados_procesados` con 10 filas (resultado del Step 1).
- En **MongoDB**, la coleccion `reportes` con 10 documentos (resultado del Step 2).

### 6.1 Verificar MySQL (resultado del Step 1)

**Opcion A - terminal:**

```bash
docker exec -it mysql-academia mysql -u alumno -palumno123 academia -e "SELECT * FROM empleados_procesados;"
```

**Opcion B - DBeaver:** conectado a `mysql-academia`, abre un editor SQL sobre la base
`academia` y ejecuta:

```sql
SELECT * FROM empleados_procesados;
```

Resultado esperado: **10 filas**, con el nombre en mayusculas y el bono calculado (10%
del salario):

| id | nombre | departamento | salario | bono |
|----|--------|-------------|---------|------|
| 1 | JUAN PEREZ | Ventas | 25000.00 | 2500.00 |
| 2 | MARIA LOPEZ | TI | 35000.00 | 3500.00 |
| ... | ... | ... | ... | ... |
| 10 | FERNANDA RIOS | Ventas | 31000.00 | 3100.00 |

Para contar las filas:

```sql
SELECT COUNT(*) FROM empleados_procesados;
```

Debe devolver `10`.

### 6.2 Verificar MongoDB (resultado del Step 2)

**Opcion A - MongoDB Compass** (la conexion del Paso 3):

1. Conectate con Compass a `mongodb://root:root123@localhost:27018/?authSource=admin`.
2. Si ya estabas conectado, pulsa el boton de **refrescar** para que aparezca la base
   nueva.
3. En el panel izquierdo abre la base **`academia`** y dentro la coleccion
   **`reportes`**.
4. Veras los **10 documentos**, cada uno con `nombre`, `departamento`, `salario`, `bono`
   y `salarioTotal`.

**Opcion B - terminal (mongosh dentro del contenedor):**

```bash
docker exec -it mongo-academia mongosh -u root -p root123
```

> Nota: aqui no se indica puerto porque, **dentro** del contenedor, MongoDB sigue
> escuchando en su 27017 interno. El 27018 solo aplica para conexiones desde tu maquina
> (la app y Compass).

Ya dentro de la consola de MongoDB:

```javascript
use academia
db.reportes.find()
```

Resultado esperado: **10 documentos** como este:

```javascript
{
  _id: ObjectId('...'),
  nombre: 'JUAN PEREZ',
  departamento: 'Ventas',
  salario: 25000,
  bono: 2500,
  salarioTotal: 27500,
  _class: 'com.academia.batch.model.EmpleadoReporte'
}
```

Cada documento tiene:
- `_id` — generado automaticamente por MongoDB.
- `nombre`, `departamento`, `salario`, `bono`, `salarioTotal` — los campos del reporte.
- `_class` — lo agrega Spring Data MongoDB para saber a que clase Java corresponde.

Para contar los documentos:

```javascript
db.reportes.countDocuments()
```

Debe devolver `10`. Para salir de la consola:

```javascript
exit
```

### 6.3 Comprobacion rapida (todo en una linea)

Sin entrar a las consolas interactivas:

```bash
# MySQL: contar filas
docker exec -i mysql-academia mysql -u alumno -palumno123 academia -e "SELECT COUNT(*) AS filas FROM empleados_procesados;"

# MongoDB: contar documentos
docker exec -i mongo-academia mongosh -u root -p root123 --quiet --eval "db.getSiblingDB('academia').reportes.countDocuments()"
```

Si MySQL devuelve `10` filas y MongoDB devuelve `10` documentos, el Job se ejecuto
correctamente de principio a fin.
