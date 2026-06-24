# Spring Batch — Tutorial: procesar empleados con MySQL y MongoDB

Este documento es el **tutorial de Spring Batch** de la academia. Primero explica
**qué es** Spring Batch y **por qué** se usa; luego recorre, archivo por archivo, un
proyecto real que aplica esos conceptos.

El proyecto (`spring-batch-v2-mongo`) ejecuta un proceso por lotes de **dos etapas**:

```
Step 1:  empleados.csv  →  procesar (bono + mayusculas)  →  tabla MySQL
Step 2:  tabla MySQL     →  calcular salario total        →  coleccion MongoDB
```

> **Antes de programar:** este documento se centra en **entender el código**. Para
> levantar los contenedores de Docker, crear la tabla y ejecutar la app, sigue la guía
> hermana **`guia-ejecucion-paso-a-paso.md`** (misma carpeta).

---

## Contenido

1. [¿Qué es Spring Batch?](#1-qué-es-spring-batch)
2. [¿Por qué lo usamos?](#2-por-qué-lo-usamos)
3. [Qué construye este proyecto](#3-qué-construye-este-proyecto)
4. [Antes de empezar](#4-antes-de-empezar)
5. [Estructura del proyecto](#5-estructura-del-proyecto)
6. [Configuración del proyecto](#6-configuración-del-proyecto)
7. [Recorrido del código](#7-recorrido-del-código)
8. [Cómo ejecutarlo y verificarlo](#8-cómo-ejecutarlo-y-verificarlo)
9. [Re-ejecutar el Job](#9-re-ejecutar-el-job)
10. [Problemas comunes](#10-problemas-comunes)
11. [Lo que aprendiste](#11-lo-que-aprendiste)

---

## 1. ¿Qué es Spring Batch?

**Spring Batch es un framework para procesamiento por lotes** (*batch processing*):
programas que procesan **grandes volúmenes de datos** de principio a fin, normalmente
**sin intervención de un usuario** (se lanzan solos, de noche o por evento) y que cuando
terminan, **terminan** (no se quedan esperando peticiones como una web).

Ejemplos típicos de procesos batch:

- Importar un archivo de 500 000 registros a una base de datos.
- Migrar datos de un sistema viejo a uno nuevo.
- Generar la nómina o los recibos del mes.
- Calcular reportes nocturnos y dejarlos listos para el día siguiente.

### El problema que resuelve

Procesar millones de registros "a mano" (un `for` gigante con JDBC) es frágil:

- Si el proceso **falla en el registro 400 000**, ¿reinicias desde cero?
- ¿Cómo agrupas los `INSERT` en transacciones para que sea eficiente y seguro?
- ¿Cómo sabes cuántos registros se leyeron, escribieron o fallaron?
- ¿Cómo reintentas un registro problemático sin tumbar todo el proceso?

Spring Batch resuelve todo eso por ti. Tú escribes **qué** hacer con cada dato; el
framework se encarga del **cómo** (transacciones, lotes, reinicio, métricas).

### Vocabulario esencial

| Término | Qué es |
|---------|--------|
| **Job** | El proceso completo. Tiene un nombre y se compone de uno o más Steps. |
| **Step** | Una etapa del Job. Cada Step hace una cosa (leer un archivo, transformar, escribir). |
| **Item** | Un elemento individual del lote (en este proyecto, un empleado). |
| **Chunk** | Un grupo de N items que se procesan y se guardan juntos en una transacción. |
| **JobRepository** | Base de datos donde Spring Batch guarda el estado de cada ejecución (qué corrió, cuántos items, si falló). Son las tablas `BATCH_*`. |

### El patrón Reader → Processor → Writer

El corazón de Spring Batch. **Cada Step** procesa los datos en tres piezas
independientes:

```
   ItemReader              ItemProcessor             ItemWriter
  (lee 1 item)   ───►   (transforma 1 item)   ───►  (escribe en lote)
```

| Pieza | Responsabilidad | De dónde / a dónde |
|-------|-----------------|--------------------|
| **Reader** | Lee los datos de origen, un item a la vez | CSV, base de datos, cola… |
| **Processor** | Transforma o filtra cada item (opcional) | en memoria |
| **Writer** | Escribe los items procesados, **en lotes (chunks)** | base de datos, archivo… |

La gran ventaja: estas tres piezas **no se conocen entre sí**. Puedes cambiar el destino
(de un CSV a MongoDB, por ejemplo) **cambiando solo el Writer**, sin tocar el Reader ni
el Processor. Este proyecto demuestra exactamente eso.

### Cómo funciona el chunk (lo más importante)

El Step no escribe item por item: acumula items en un **chunk** y los escribe juntos en
una sola transacción. Si el `chunk = 3`:

```
lee 3 → procesa 3 → escribe los 3 y hace commit   (transacción 1)
lee 3 → procesa 3 → escribe los 3 y hace commit   (transacción 2)
...
```

Esto es lo que hace eficiente y seguro el procesamiento masivo: menos transacciones, y
si algo falla, solo se pierde el chunk en curso, no todo el trabajo previo.

---

## 2. ¿Por qué lo usamos?

Porque escribir procesamiento masivo robusto **a mano** es mucho trabajo y fácil de
hacer mal. Spring Batch te da, sin que lo programes:

| Necesidad | Sin Spring Batch | Con Spring Batch |
|-----------|------------------|------------------|
| Procesar por lotes y en transacciones | Lo manejas tú con JDBC | `.chunk(n)` y listo |
| Saber qué pasó en cada corrida | Te haces tu propio log | Tablas `BATCH_*` con métricas |
| Reintentar / saltar items con error | Try/catch manual por todos lados | Configuración declarativa |
| Reiniciar un Job que falló | Reinventarlo cada vez | Soportado por el framework |
| Leer CSV / BD / escribir a varios destinos | Código repetitivo | Readers y Writers ya hechos |

En resumen: **te concentras en la lógica de negocio** (qué transformar) y el framework
pone la infraestructura de un proceso batch serio.

---

## 3. Qué construye este proyecto

Un Job llamado `procesarEmpleadosJob` con **dos Steps encadenados**, donde cada Step usa
una **base de datos diferente**. Es un ejemplo compacto que muestra el patrón completo
Reader-Processor-Writer dos veces, y enseña a integrar **dos tecnologías de
almacenamiento** (SQL y NoSQL) en un mismo proceso.

### La analogía de la fábrica

Piensa en una fábrica con dos estaciones de trabajo en línea:

```
Estacion 1 (Step 1)                    Estacion 2 (Step 2)
+----------------------------+         +----------------------------+
| Materia prima: CSV         |         | Materia prima: tabla MySQL |
| Operacion: bono+mayusculas |  --->   | Operacion: salario total   |
| Producto: tabla MySQL      |         | Producto: coleccion Mongo  |
+----------------------------+         +----------------------------+
```

- La **materia prima** que entra a cada estación = el **Reader**.
- La **operación** que transforma la pieza = el **Processor**.
- El **producto terminado** que sale = el **Writer**.

El producto de la estación 1 (la tabla MySQL) es la materia prima de la estación 2.

### Conceptos que verás en el código

| Concepto | Dónde se ve |
|----------|-------------|
| Un Job con varios Steps encadenados | `procesarEmpleadosJob` en `BatchConfig` |
| Reader de archivo plano (`FlatFileItemReader`) | `leerCsv()` — lee el CSV |
| Writer a base SQL (`JdbcBatchItemWriter`) | `escribirEnBd()` — inserta en MySQL |
| Reader desde base SQL (`JdbcCursorItemReader`) | `leerDeBd()` — lee de MySQL |
| Writer a MongoDB (`MongoItemWriter`) | `escribirEnMongo()` — guarda documentos |
| Processor que **cambia el tipo** del item | `ReporteProcessor`: `Empleado → EmpleadoReporte` |
| Una sola app con **dos bases de datos** | MySQL en Step 1, MongoDB en Step 2 |
| `@Document` convierte un POJO en documento Mongo | `EmpleadoReporte` |

---

## 4. Antes de empezar

Necesitas el entorno listo (Docker con MySQL y MongoDB, y la tabla creada). Todo eso
está paso a paso en **`guia-ejecucion-paso-a-paso.md`**. Resumen de lo que requiere el
proyecto:

- **Java 17** y **Maven**.
- Contenedor **`mysql-academia`** corriendo (puerto 3306) con la base `academia` y la
  tabla `empleados_procesados` creada.
- Contenedor **`mongo-academia`** corriendo (puerto **27018**).

---

## 5. Estructura del proyecto

```
springBatchV2Mongo/
├── pom.xml                                      ← dependencias
└── src/main/
    ├── java/com/academia/batch/
    │   ├── SpringBatchApplication.java          ← arranque de la app
    │   ├── config/
    │   │   └── BatchConfig.java                 ← define Steps y Job (el corazón)
    │   ├── model/
    │   │   ├── Empleado.java                    ← modelo del Step 1
    │   │   └── EmpleadoReporte.java             ← modelo del Step 2 (documento Mongo)
    │   └── processor/
    │       ├── EmpleadoProcessor.java           ← lógica del Step 1
    │       └── ReporteProcessor.java            ← lógica del Step 2
    └── resources/
        ├── application.properties               ← conexiones a MySQL y MongoDB
        └── empleados.csv                        ← datos de entrada
```

---

## 6. Configuración del proyecto

### 6.1 pom.xml — las dependencias

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-batch</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
</dependencies>
```

| Dependencia | Qué aporta |
|-------------|-----------|
| `spring-boot-starter-batch` | Spring Batch completo: Jobs, Steps, Readers/Writers, JobRepository |
| `mysql-connector-j` | Driver JDBC para conectar con MySQL (Step 1 y lectura del Step 2) |
| `spring-boot-starter-data-mongodb` | Driver de MongoDB + Spring Data (`MongoTemplate`, `@Document`) para el Writer del Step 2 |

El proyecto usa **Spring Boot 3.2.2** y **Java 17** (definidos en el `pom.xml`).

### 6.2 application.properties — dos conexiones a la vez

```properties
# Conexion a MySQL (contenedor docker mysql-academia)
spring.datasource.url=jdbc:mysql://localhost:3306/academia
spring.datasource.username=alumno
spring.datasource.password=alumno123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Spring Batch crea automaticamente las tablas BATCH_* en MySQL
spring.batch.jdbc.initialize-schema=always

# Ejecutar el Job automaticamente al iniciar la aplicacion
spring.batch.job.enabled=true

# Conexion a MongoDB (contenedor docker mongo-academia, puerto 27018)
spring.data.mongodb.uri=mongodb://root:root123@localhost:27018/academia?authSource=admin
```

Líneas clave:

| Propiedad | Para qué |
|-----------|----------|
| `spring.datasource.*` | Conexión a MySQL (la usa el JobRepository, el Writer del Step 1 y el Reader del Step 2) |
| `spring.batch.jdbc.initialize-schema=always` | Hace que Spring Batch cree solo sus tablas de control `BATCH_*` |
| `spring.batch.job.enabled=true` | Lanza el Job automáticamente al arrancar la app |
| `spring.data.mongodb.uri=...27018...` | Conexión a MongoDB (la usa el Writer del Step 2). **Puerto 27018**, no 27017 |

**Una app, dos bases de datos:** Spring Boot maneja `spring.datasource.*` (MySQL) y
`spring.data.mongodb.*` (MongoDB) de forma independiente, cada una con su propia
conexión. No tienes que crear ningún `@Bean` de conexión: con estas propiedades, Spring
Boot autoconfigura tanto el `DataSource` de MySQL como el `MongoTemplate` de MongoDB.

---

## 7. Recorrido del código

### 7.1 empleados.csv — los datos de entrada

En `src/main/resources/empleados.csv`. La primera línea son los encabezados (se ignora
al leer):

```csv
nombre,departamento,salario
Juan Perez,Ventas,25000
Maria Lopez,TI,35000
Carlos Garcia,RRHH,28000
Ana Martinez,Ventas,27000
Pedro Sanchez,TI,32000
Laura Diaz,RRHH,30000
Roberto Flores,Ventas,26000
Sofia Ramirez,TI,38000
Diego Torres,RRHH,29000
Fernanda Rios,Ventas,31000
Patrobas Filologo,Java Senior,10000
```

Son **11 empleados**. Cada uno tiene `nombre`, `departamento` y `salario` (el `bono` no
viene en el archivo: lo calcula el Step 1).

### 7.2 Empleado.java — el modelo del Step 1

POJO sencillo que representa una fila del CSV (y una fila de la tabla MySQL):

```java
package com.academia.batch.model;

// Modelo usado en el Step 1: representa un empleado leido del CSV
public class Empleado {

    private String nombre;
    private String departamento;
    private double salario;
    private double bono;

    public Empleado() {
    }

    // getters y setters de nombre, departamento, salario, bono ...

    @Override
    public String toString() {
        return nombre + " | " + departamento + " | Salario: " + salario + " | Bono: " + bono;
    }
}
```

Tiene los 3 campos del CSV más `bono`, que se llena durante el procesamiento.

### 7.3 EmpleadoProcessor.java — la lógica del Step 1

Un `ItemProcessor<Empleado, Empleado>`: recibe un `Empleado` y devuelve un `Empleado`
(mismo tipo, transformado).

```java
package com.academia.batch.processor;

import com.academia.batch.model.Empleado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// Step 1 - Processor: transforma el empleado leido del CSV
public class EmpleadoProcessor implements ItemProcessor<Empleado, Empleado> {

    private static final Logger log = LoggerFactory.getLogger(EmpleadoProcessor.class);

    @Override
    public Empleado process(Empleado empleado) {
        empleado.setNombre(empleado.getNombre().toUpperCase());   // nombre en MAYUSCULAS
        empleado.setBono(empleado.getSalario() * 0.10);           // bono = 10% del salario

        log.info("Step 1 - Procesando: {}", empleado);
        return empleado;
    }
}
```

Dos transformaciones: pasa el nombre a mayúsculas y calcula el bono (10% del salario).
El método `process` se llama **una vez por cada item** que entrega el Reader.

### 7.4 EmpleadoReporte.java — el modelo del Step 2 (documento MongoDB)

Es la salida del Step 2. Igual que `Empleado` pero con un campo extra `salarioTotal`, y
anotado para guardarse como **documento de MongoDB**:

```java
package com.academia.batch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reportes")   // esta clase es un documento de la coleccion "reportes"
public class EmpleadoReporte {

    @Id
    private String id;                // MongoDB genera el _id automaticamente
    private String nombre;
    private String departamento;
    private double salario;
    private double bono;
    private double salarioTotal;

    // constructor vacio + getters y setters ...
}
```

| Anotación | Equivalente en MySQL/JPA | Qué hace |
|-----------|--------------------------|----------|
| `@Document(collection = "reportes")` | `@Entity` + `@Table(name="reportes")` | Marca la clase como documento de la colección `reportes` |
| `@Id` sobre `private String id` | `@Id` + `AUTO_INCREMENT` | MongoDB genera un `ObjectId` único al guardar |

A diferencia de MySQL, **no hay que crear la colección ni el id a mano**: MongoDB los
crea solos la primera vez que se guarda un documento.

### 7.5 ReporteProcessor.java — la lógica del Step 2

Un `ItemProcessor<Empleado, EmpleadoReporte>`. Aquí el **tipo de entrada es distinto al
de salida**: entra un `Empleado` (leído de MySQL) y sale un `EmpleadoReporte`.

```java
package com.academia.batch.processor;

import com.academia.batch.model.Empleado;
import com.academia.batch.model.EmpleadoReporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// Step 2 - Processor: convierte Empleado a EmpleadoReporte calculando el salario total
public class ReporteProcessor implements ItemProcessor<Empleado, EmpleadoReporte> {

    private static final Logger log = LoggerFactory.getLogger(ReporteProcessor.class);

    @Override
    public EmpleadoReporte process(Empleado empleado) {
        EmpleadoReporte reporte = new EmpleadoReporte();
        reporte.setNombre(empleado.getNombre());
        reporte.setDepartamento(empleado.getDepartamento());
        reporte.setSalario(empleado.getSalario());
        reporte.setBono(empleado.getBono());
        reporte.setSalarioTotal(empleado.getSalario() + empleado.getBono());  // dato nuevo

        log.info("Step 2 - Reporte: {}", reporte);
        return reporte;
    }
}
```

Copia los datos del empleado y calcula `salarioTotal = salario + bono`. Que entrada y
salida sean de tipos diferentes es totalmente normal en Spring Batch: el Processor es el
lugar donde se transforma la forma del dato.

### 7.6 BatchConfig.java — el corazón (define Steps y Job)

Esta clase ensambla todo. Cada pieza (Reader, Processor, Writer, Step, Job) es un
`@Bean`. La leemos por bloques.

#### Step 1: CSV → MySQL

```java
@Bean
public FlatFileItemReader<Empleado> leerCsv() {
    return new FlatFileItemReaderBuilder<Empleado>()
            .name("empleadoReader")
            .resource(new ClassPathResource("empleados.csv")) // archivo de entrada
            .delimited()                                       // separado por comas
            .names("nombre", "departamento", "salario")        // columnas del CSV
            .targetType(Empleado.class)                        // a que clase mapear cada fila
            .linesToSkip(1)                                    // saltar la fila de encabezados
            .build();
}

@Bean
public EmpleadoProcessor procesarEmpleado() {
    return new EmpleadoProcessor();
}

@Bean
public JdbcBatchItemWriter<Empleado> escribirEnBd(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Empleado>()
            .sql("INSERT INTO empleados_procesados (nombre, departamento, salario, bono) "
               + "VALUES (:nombre, :departamento, :salario, :bono)")
            .dataSource(dataSource)
            .beanMapped()   // toma :nombre, :salario... de los getters del Empleado
            .build();
}

@Bean
public Step paso1(JobRepository jobRepository,
                  PlatformTransactionManager transactionManager,
                  FlatFileItemReader<Empleado> leerCsv,
                  EmpleadoProcessor procesarEmpleado,
                  JdbcBatchItemWriter<Empleado> escribirEnBd) {

    return new StepBuilder("paso1", jobRepository)
            .<Empleado, Empleado>chunk(3, transactionManager)  // procesa de a 3
            .reader(leerCsv)
            .processor(procesarEmpleado)
            .writer(escribirEnBd)
            .build();
}
```

- **`FlatFileItemReader`** lee el CSV fila por fila y la convierte en un `Empleado`.
- **`JdbcBatchItemWriter`** inserta en la tabla `empleados_procesados`. `.beanMapped()`
  hace que `:nombre`, `:salario`, etc. se llenen automáticamente desde los getters del
  objeto.
- **`paso1`** une las tres piezas con `chunk(3)`: lee/procesa/escribe de **3 en 3**.
- Fíjate en `<Empleado, Empleado>`: entra `Empleado`, sale `Empleado` (el Processor del
  Step 1 no cambia el tipo).

#### Step 2: MySQL → MongoDB

```java
@Bean
public JdbcCursorItemReader<Empleado> leerDeBd(DataSource dataSource) {
    return new JdbcCursorItemReaderBuilder<Empleado>()
            .name("empleadoDbReader")
            .dataSource(dataSource)
            .sql("SELECT nombre, departamento, salario, bono FROM empleados_procesados")
            .rowMapper((rs, rowNum) -> {                 // convierte cada fila en un Empleado
                Empleado empleado = new Empleado();
                empleado.setNombre(rs.getString("nombre"));
                empleado.setDepartamento(rs.getString("departamento"));
                empleado.setSalario(rs.getDouble("salario"));
                empleado.setBono(rs.getDouble("bono"));
                return empleado;
            })
            .build();
}

@Bean
public ReporteProcessor procesarReporte() {
    return new ReporteProcessor();
}

@Bean
public MongoItemWriter<EmpleadoReporte> escribirEnMongo(MongoTemplate mongoTemplate) {
    return new MongoItemWriterBuilder<EmpleadoReporte>()
            .template(mongoTemplate)     // inyectado por Spring desde la URI de Mongo
            .collection("reportes")      // coleccion destino
            .build();
}

@Bean
public Step paso2(JobRepository jobRepository,
                  PlatformTransactionManager transactionManager,
                  JdbcCursorItemReader<Empleado> leerDeBd,
                  ReporteProcessor procesarReporte,
                  MongoItemWriter<EmpleadoReporte> escribirEnMongo) {

    return new StepBuilder("paso2", jobRepository)
            .<Empleado, EmpleadoReporte>chunk(3, transactionManager)  // entra Empleado, sale EmpleadoReporte
            .reader(leerDeBd)
            .processor(procesarReporte)
            .writer(escribirEnMongo)
            .build();
}
```

- **`JdbcCursorItemReader`** lee de la tabla MySQL con un `SELECT` y convierte cada fila
  en un `Empleado` mediante el `rowMapper`.
- **`MongoItemWriter`** guarda cada `EmpleadoReporte` como documento en la colección
  `reportes`. El `MongoTemplate` lo inyecta Spring automáticamente (sale de la URI del
  `application.properties`).
- Fíjate en `<Empleado, EmpleadoReporte>`: aquí entrada y salida **son distintas**.

**El detalle que demuestra el proyecto:** comparado con escribir a un CSV
(`FlatFileItemWriter`), pasar a MongoDB solo cambió **el Writer**. El Reader y el
Processor del Step 2 quedarían idénticos. Así de desacoplado es el patrón
Reader-Processor-Writer:

| | Writer a CSV (`FlatFileItemWriter`) | Writer a MongoDB (`MongoItemWriter`) |
|---|---|---|
| Destino | un archivo `.csv` | `.collection("reportes")` |
| Mapeo de campos | hay que listarlos con `.names(...)` | **automático** (todo el objeto a BSON) |
| Conexión | no necesita | `.template(mongoTemplate)` |

#### El Job: encadena los dos Steps

```java
@Bean
public Job procesarEmpleadosJob(JobRepository jobRepository, Step paso1, Step paso2) {
    return new JobBuilder("procesarEmpleadosJob", jobRepository)
            .incrementer(new RunIdIncrementer())  // run.id auto-incremental para re-ejecutar
            .start(paso1)                          // primero paso1
            .next(paso2)                           // luego paso2
            .build();
}
```

- `.start(paso1).next(paso2)` define el orden: **paso1 y, al terminar, paso2**.
- `.incrementer(new RunIdIncrementer())` añade un parámetro `run.id` que crece en cada
  ejecución, lo que **permite re-ejecutar el Job** las veces que quieras (ver sección 9).

### 7.7 SpringBatchApplication.java — el arranque

```java
package com.academia.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }
}
```

El típico arranque de Spring Boot. Como `spring.batch.job.enabled=true`, al iniciar la
app **el Job se ejecuta automáticamente** y, al terminar, la app se cierra sola (es un
proceso batch, no una web).

---

## 8. Cómo ejecutarlo y verificarlo

El paso a paso completo (Docker, crear la tabla, ejecutar y verificar en ambas bases)
está en **`guia-ejecucion-paso-a-paso.md`**. En resumen:

```bash
# Desde la carpeta raiz del proyecto
mvn spring-boot:run
```

Salida esperada en consola (los dos Steps y el Job en `COMPLETED`):

```
Executing step: [paso1]
Step 1 - Procesando: JUAN PEREZ | Ventas | Salario: 25000.0 | Bono: 2500.0
...
Executing step: [paso2]
Step 2 - Reporte: JUAN PEREZ | Ventas | Salario: 25000.0 | Bono: 2500.0 | Total: 27500.0
...
Job: [SimpleJob: [name=procesarEmpleadosJob]] completed ... status: [COMPLETED]
```

Resultado:

- **MySQL** → tabla `empleados_procesados` con **11 filas** (Step 1).
- **MongoDB** → colección `reportes` con **11 documentos** (Step 2).

Las métricas de cada Step quedan en el JobRepository:

```sql
SELECT STEP_NAME, READ_COUNT, WRITE_COUNT, COMMIT_COUNT, STATUS
FROM BATCH_STEP_EXECUTION;
```

| STEP_NAME | READ_COUNT | WRITE_COUNT | COMMIT_COUNT | STATUS |
|-----------|-----------|-------------|-------------|--------|
| paso1 | 11 | 11 | 4 | COMPLETED |
| paso2 | 11 | 11 | 4 | COMPLETED |

> Con `chunk(3)` y 11 items, hay 4 commits: tres chunks de 3 y uno final de 2.

---

## 9. Re-ejecutar el Job

Gracias al `RunIdIncrementer`, puedes ejecutar la app varias veces sin tocar las tablas
`BATCH_*` (cada corrida recibe un `run.id` distinto). Pero para no **duplicar datos**,
conviene limpiar antes de re-ejecutar:

```bash
# MySQL: vaciar la tabla
docker exec -i mysql-academia mysql -u alumno -palumno123 academia -e "DELETE FROM empleados_procesados;"

# MongoDB: borrar la coleccion (se recrea sola en la siguiente corrida)
docker exec -i mongo-academia mongosh -u root -p root123 --quiet --eval "db.getSiblingDB('academia').reportes.drop()"
```

> MongoDB no impone unicidad por defecto: si no limpias, cada corrida **añade** otros 11
> documentos.

---

## 10. Problemas comunes

| Error | Causa y solución |
|-------|------------------|
| `Communications link failure` (MySQL) | El contenedor MySQL no está corriendo → `docker start mysql-academia` |
| `Connection refused` en 27018 | El contenedor MongoDB no está corriendo → `docker start mongo-academia` |
| `Exception authenticating MongoCredential` | La URI no coincide con el usuario/clave del contenedor. Debe ser `mongodb://root:root123@localhost:27018/academia?authSource=admin` |
| `Table 'academia.empleados_procesados' doesn't exist` | Falta crear la tabla (ver `guia-ejecucion-paso-a-paso.md`, Paso 4) |
| `A job instance already exists` | El Job debe tener `.incrementer(new RunIdIncrementer())` para poder re-ejecutarse |
| Los documentos se duplican al re-ejecutar | Limpia las bases antes de correr de nuevo (sección 9) |

---

## 11. Lo que aprendiste

- **Qué es Spring Batch** y cuándo usarlo: procesos por lotes robustos sobre grandes
  volúmenes de datos.
- El patrón **Reader → Processor → Writer** y cómo el **chunk** agrupa el trabajo en
  transacciones.
- Un **Job** se compone de **Steps** encadenados (`paso1 → paso2`).
- Un mismo Job puede tocar **dos bases de datos** distintas (MySQL y MongoDB).
- Cambiar el **destino** de un Step es cambiar **solo el Writer** — el Reader y el
  Processor ni se enteran. Esa es la gran fortaleza del diseño desacoplado de Spring
  Batch.
