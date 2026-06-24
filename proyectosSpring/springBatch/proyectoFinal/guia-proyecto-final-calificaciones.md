# Proyecto Final — Spring Batch: Calificaciones de estudiantes

En este proyecto final vas a construir, **desde cero**, una aplicación de Spring Batch
muy parecida a la que vimos en clase (`spring-batch-v2-mongo`), pero para un dominio
distinto: **calificaciones de estudiantes**.

Es el mismo patrón que ya conoces (un Job con dos Steps, Reader → Processor → Writer,
MySQL y MongoDB), aplicado a datos nuevos. Tienes el proyecto de clase como referencia:
si te atoras, compáralo con el archivo equivalente de aquel proyecto.

## Qué vas a construir

Un Job con **dos Steps**:

```
Step 1:  estudiantes.csv  →  calcular promedio  →  tabla MySQL (estudiantes_procesados)
Step 2:  tabla MySQL       →  determinar estado  →  coleccion MongoDB (reportes_estudiantes)
```

- **Step 1** lee un CSV de estudiantes (con tres notas cada uno), calcula el **promedio**
  y guarda cada estudiante en MySQL.
- **Step 2** lee de MySQL, determina el **estado** (`APROBADO` si el promedio es ≥ 70,
  `REPROBADO` si no) y guarda un reporte en MongoDB.

> **Equivalencia con el proyecto de clase:** donde antes calculábamos el `bono` del
> empleado, ahora calculas el `promedio` del estudiante; donde calculábamos el
> `salarioTotal`, ahora determinas el `estado`. La arquitectura es idéntica.

### El proyecto tiene 3 partes

Para aprovechar el tiempo, este proyecto final cubre varios temas del temario en un solo
desarrollo:

| Parte | Qué construyes | Temas que cubre |
|-------|----------------|-----------------|
| **Parte 1** (Pasos 1–13) | El proceso batch (CSV → MySQL → MongoDB) | Spring Batch, Maven, MySQL, MongoDB, Spring Boot, Spring Core |
| **Parte 2** (Pasos 14–21) | Una **API REST** sobre esos datos | Spring MVC, servicios RESTful, Spring Data JPA, verbos HTTP, URIs, códigos de respuesta, HTTP/HTTPS |
| **Parte 3** (Pasos 22–24) | **Pruebas unitarias** | JUnit, assertions, Mockito |

Haz las partes en orden: la 2 y la 3 se construyen encima de la 1.

---

## Paso 0: Requisitos (reutiliza lo que ya tienes)

**No necesitas crear contenedores nuevos.** Usa los mismos `mysql-academia` y
`mongo-academia` del proyecto de clase. Solo asegúrate de que estén corriendo:

```bash
docker ps
```

Si no aparecen, arráncalos:

```bash
docker start mysql-academia
docker start mongo-academia
```

> El montaje de contenedores está explicado en
> `springBatchV2Mongo/documentos/guia-ejecucion-paso-a-paso.md` (Pasos 1 y 2).

También necesitas **Java 17** y **Maven**.

---

## Paso 1: Crear el proyecto Maven

En Eclipse: **File > New > Maven Project**, marca *Create a simple project (skip
archetype selection)* y llena:

- **Group Id:** `com.academia`
- **Artifact Id:** `spring-batch-final-calificaciones`
- **Version:** `1.0.0`
- **Packaging:** `jar`

Crea estos paquetes (los mismos que en el proyecto de clase):

```
com.academia.batch
com.academia.batch.model
com.academia.batch.processor
com.academia.batch.config
```

---

## Paso 2: pom.xml

Son **las mismas dependencias** del proyecto de clase. Pega esto en tu `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.2</version>
        <relativePath/>
    </parent>

    <groupId>com.academia</groupId>
    <artifactId>spring-batch-final-calificaciones</artifactId>
    <version>1.0.0</version>
    <name>spring-batch-final-calificaciones</name>
    <description>Proyecto final: Spring Batch con calificaciones de estudiantes</description>

    <properties>
        <java.version>17</java.version>
    </properties>

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

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

Después de pegarlo: clic derecho en el proyecto > **Maven > Update Project** (`Alt+F5`).

---

## Paso 3: application.properties

En `src/main/resources/application.properties`. Es **igual** a la del proyecto de clase
(reusa la base `academia`). Lo único distinto en la práctica es que tus datos irán a una
tabla y una colección con nombres nuevos:

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

> **Ojo con el puerto de MongoDB:** es **27018** (no 27017).

---

## Paso 4: Crear la tabla `estudiantes_procesados` en MySQL

Igual que en clase, la tabla de negocio se crea a mano. Conéctate y créala:

```bash
docker exec -it mysql-academia mysql -u alumno -palumno123 academia
```

```sql
CREATE TABLE IF NOT EXISTS estudiantes_procesados (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    grupo VARCHAR(10) NOT NULL,
    nota1 DECIMAL(5,2) NOT NULL,
    nota2 DECIMAL(5,2) NOT NULL,
    nota3 DECIMAL(5,2) NOT NULL,
    promedio DECIMAL(5,2) NOT NULL
);
```

Verifica con `DESCRIBE estudiantes_procesados;` y sal con `exit`.

---

## Paso 5: El archivo de entrada `estudiantes.csv`

Crea `src/main/resources/estudiantes.csv`. La primera línea son los encabezados:

```csv
nombre,grupo,nota1,nota2,nota3
Juan Perez,A,80,75,90
Maria Lopez,A,60,55,40
Carlos Garcia,B,95,88,92
Ana Martinez,B,70,72,68
Pedro Sanchez,A,50,45,60
Laura Diaz,C,85,90,80
Roberto Flores,C,40,55,50
Sofia Ramirez,B,100,95,98
Diego Torres,A,65,70,75
Fernanda Rios,C,72,68,74
```

Son **10 estudiantes**, cada uno con tres notas. El `promedio` no viene en el archivo:
lo calcula el Step 1.

---

## Paso 6: Modelo `Estudiante.java` (Step 1)

En `com.academia.batch.model`. Representa una fila del CSV y de la tabla MySQL.

```java
package com.academia.batch.model;

// Modelo usado en el Step 1: representa un estudiante leido del CSV
public class Estudiante {

    private String nombre;
    private String grupo;
    private double nota1;
    private double nota2;
    private double nota3;
    private double promedio;

    public Estudiante() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public double getNota1() { return nota1; }
    public void setNota1(double nota1) { this.nota1 = nota1; }

    public double getNota2() { return nota2; }
    public void setNota2(double nota2) { this.nota2 = nota2; }

    public double getNota3() { return nota3; }
    public void setNota3(double nota3) { this.nota3 = nota3; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    @Override
    public String toString() {
        return nombre + " | Grupo: " + grupo + " | Promedio: " + promedio;
    }
}
```

---

## Paso 7: `EstudianteProcessor.java` (lógica del Step 1)

En `com.academia.batch.processor`. Calcula el promedio de las tres notas.
Equivale al `EmpleadoProcessor` del proyecto de clase.

```java
package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// Step 1 - Processor: calcula el promedio del estudiante leido del CSV
public class EstudianteProcessor implements ItemProcessor<Estudiante, Estudiante> {

    private static final Logger log = LoggerFactory.getLogger(EstudianteProcessor.class);

    @Override
    public Estudiante process(Estudiante estudiante) {
        double promedio = (estudiante.getNota1() + estudiante.getNota2() + estudiante.getNota3()) / 3;
        estudiante.setPromedio(promedio);

        log.info("Step 1 - Procesando: {}", estudiante);
        return estudiante;
    }
}
```

---

## Paso 8: Modelo `EstudianteReporte.java` (Step 2, documento MongoDB)

En `com.academia.batch.model`. Es la salida del Step 2. Lleva el campo nuevo `estado` y
las anotaciones de MongoDB. Equivale al `EmpleadoReporte` del proyecto de clase.

```java
package com.academia.batch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reportes_estudiantes")
public class EstudianteReporte {

    @Id
    private String id;
    private String nombre;
    private String grupo;
    private double promedio;
    private String estado;

    public EstudianteReporte() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return nombre + " | Grupo: " + grupo + " | Promedio: " + promedio + " | Estado: " + estado;
    }
}
```

---

## Paso 9: `ReporteEstudianteProcessor.java` (lógica del Step 2)

En `com.academia.batch.processor`. Convierte `Estudiante` en `EstudianteReporte` y
determina el estado. Equivale al `ReporteProcessor` del proyecto de clase.

```java
package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// Step 2 - Processor: convierte Estudiante a EstudianteReporte determinando el estado
public class ReporteEstudianteProcessor implements ItemProcessor<Estudiante, EstudianteReporte> {

    private static final Logger log = LoggerFactory.getLogger(ReporteEstudianteProcessor.class);

    @Override
    public EstudianteReporte process(Estudiante estudiante) {
        EstudianteReporte reporte = new EstudianteReporte();
        reporte.setNombre(estudiante.getNombre());
        reporte.setGrupo(estudiante.getGrupo());
        reporte.setPromedio(estudiante.getPromedio());
        reporte.setEstado(estudiante.getPromedio() >= 70 ? "APROBADO" : "REPROBADO");

        log.info("Step 2 - Reporte: {}", reporte);
        return reporte;
    }
}
```

---

## Paso 10: `BatchConfig.java` (Steps y Job)

En `com.academia.batch.config`. Es la misma estructura del proyecto de clase, adaptada a
estudiantes.

```java
package com.academia.batch.config;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.processor.EstudianteProcessor;
import com.academia.batch.processor.ReporteEstudianteProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // =====================================================================
    //  STEP 1: Lee CSV -> calcula promedio -> escribe en MySQL
    // =====================================================================

    @Bean
    public FlatFileItemReader<Estudiante> leerCsv() {
        return new FlatFileItemReaderBuilder<Estudiante>()
                .name("estudianteReader")
                .resource(new ClassPathResource("estudiantes.csv"))
                .delimited()
                .names("nombre", "grupo", "nota1", "nota2", "nota3")
                .targetType(Estudiante.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public EstudianteProcessor procesarEstudiante() {
        return new EstudianteProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Estudiante> escribirEnBd(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Estudiante>()
                .sql("INSERT INTO estudiantes_procesados (nombre, grupo, nota1, nota2, nota3, promedio) "
                   + "VALUES (:nombre, :grupo, :nota1, :nota2, :nota3, :promedio)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Step paso1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Estudiante> leerCsv,
                      EstudianteProcessor procesarEstudiante,
                      JdbcBatchItemWriter<Estudiante> escribirEnBd) {

        return new StepBuilder("paso1", jobRepository)
                .<Estudiante, Estudiante>chunk(3, transactionManager)
                .reader(leerCsv)
                .processor(procesarEstudiante)
                .writer(escribirEnBd)
                .build();
    }

    // =====================================================================
    //  STEP 2: Lee MySQL -> determina estado -> escribe en MongoDB
    // =====================================================================

    @Bean
    public JdbcCursorItemReader<Estudiante> leerDeBd(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Estudiante>()
                .name("estudianteDbReader")
                .dataSource(dataSource)
                .sql("SELECT nombre, grupo, promedio FROM estudiantes_procesados")
                .rowMapper((rs, rowNum) -> {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setGrupo(rs.getString("grupo"));
                    estudiante.setPromedio(rs.getDouble("promedio"));
                    return estudiante;
                })
                .build();
    }

    @Bean
    public ReporteEstudianteProcessor procesarReporte() {
        return new ReporteEstudianteProcessor();
    }

    @Bean
    public MongoItemWriter<EstudianteReporte> escribirEnMongo(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<EstudianteReporte>()
                .template(mongoTemplate)
                .collection("reportes_estudiantes")
                .build();
    }

    @Bean
    public Step paso2(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      JdbcCursorItemReader<Estudiante> leerDeBd,
                      ReporteEstudianteProcessor procesarReporte,
                      MongoItemWriter<EstudianteReporte> escribirEnMongo) {

        return new StepBuilder("paso2", jobRepository)
                .<Estudiante, EstudianteReporte>chunk(3, transactionManager)
                .reader(leerDeBd)
                .processor(procesarReporte)
                .writer(escribirEnMongo)
                .build();
    }

    // =====================================================================
    //  JOB: ejecuta paso1 y luego paso2
    // =====================================================================

    @Bean
    public Job procesarCalificacionesJob(JobRepository jobRepository, Step paso1, Step paso2) {
        return new JobBuilder("procesarCalificacionesJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(paso1)
                .next(paso2)
                .build();
    }
}
```

---

## Paso 11: `SpringBatchApplication.java` (arranque)

En `com.academia.batch`. Igual que en el proyecto de clase.

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

---

## Paso 12: Ejecutar

Asegúrate de que los dos contenedores estén corriendo (Paso 0) y de haber creado la
tabla (Paso 4). Luego, desde la raíz del proyecto:

```bash
mvn spring-boot:run
```

(o en Eclipse: clic derecho en `SpringBatchApplication.java` > **Run As > Java
Application**).

Salida esperada (los dos Steps y el Job en `COMPLETED`):

```
Executing step: [paso1]
Step 1 - Procesando: Juan Perez | Grupo: A | Promedio: 81.66...
...
Executing step: [paso2]
Step 2 - Reporte: Juan Perez | Grupo: A | Promedio: 81.66... | Estado: APROBADO
Step 2 - Reporte: Maria Lopez | Grupo: A | Promedio: 51.66... | Estado: REPROBADO
...
Job: [SimpleJob: [name=procesarCalificacionesJob]] completed ... status: [COMPLETED]
```

---

## Paso 13: Verificar los resultados

**MySQL (Step 1)** — debe haber 10 filas con su promedio calculado:

```bash
docker exec -it mysql-academia mysql -u alumno -palumno123 academia -e "SELECT * FROM estudiantes_procesados;"
```

**MongoDB (Step 2)** — debe haber 10 documentos en `reportes_estudiantes`, cada uno con
su `estado`:

```bash
docker exec -it mongo-academia mongosh -u root -p root123 --quiet --eval "db.getSiblingDB('academia').reportes_estudiantes.find()"
```

Ejemplo de documento:

```javascript
{
  _id: ObjectId('...'),
  nombre: 'Juan Perez',
  grupo: 'A',
  promedio: 81.66666666666667,
  estado: 'APROBADO',
  _class: 'com.academia.batch.model.EstudianteReporte'
}
```

Con los datos del CSV de ejemplo deberías obtener **7 aprobados** y **3 reprobados**
(Maria Lopez, Pedro Sanchez y Roberto Flores quedan reprobados).

Puedes filtrar por estado:

```javascript
db.getSiblingDB('academia').reportes_estudiantes.find({ estado: "REPROBADO" })
```

---

## Para re-ejecutar (limpiar datos)

Antes de volver a correr, limpia para no duplicar:

```bash
# MySQL
docker exec -i mysql-academia mysql -u alumno -palumno123 academia -e "DELETE FROM estudiantes_procesados;"

# MongoDB
docker exec -i mongo-academia mongosh -u root -p root123 --quiet --eval "db.getSiblingDB('academia').reportes_estudiantes.drop()"
```

---
---

# Parte 2: Exponer los datos con una API REST (Spring MVC + Spring Data JPA)

Hasta aquí tu proyecto es un proceso batch que corre y se cierra. Ahora lo convertimos
**también** en un servicio web: una **API REST** para consultar y manipular los
estudiantes y los reportes desde el navegador, `curl` o Postman.

> **Cambio de comportamiento:** al agregar la parte web, la aplicación **ya no se cierra**
> al terminar el batch. Se queda corriendo como servidor en `http://localhost:8080` para
> atender peticiones. El Job de batch sigue ejecutándose al arrancar.

---

## Paso 14: Agregar dependencias (web, JPA, test)

Agrega estas tres dependencias dentro de `<dependencies>` en tu `pom.xml` (además de las
que ya tienes):

```xml
<!-- API REST (Spring MVC + servidor web) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA (para leer/escribir la tabla MySQL como objetos) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Pruebas: JUnit 5 + assertions + Mockito (Parte 3) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

| Dependencia | Qué aporta |
|-------------|-----------|
| `spring-boot-starter-web` | Spring MVC, `@RestController`, y un servidor Tomcat embebido |
| `spring-boot-starter-data-jpa` | Repositorios JPA (consultas automáticas sobre MySQL) |
| `spring-boot-starter-test` | JUnit 5, AssertJ/Assertions y Mockito (ámbito `test`) |

Luego: **Maven > Update Project** (`Alt+F5`).

---

## Paso 15: Ajustar application.properties

Agrega estas líneas al final de tu `application.properties`:

```properties
# JPA: usar la tabla que YA creamos a mano, sin que Hibernate la modifique
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

# Puerto del servidor web (por defecto 8080; descomenta para cambiarlo)
# server.port=8080
```

> **`ddl-auto=none` es importante:** le dice a JPA que **no** cree ni borre tablas. La
> tabla `estudiantes_procesados` ya existe (Paso 4); JPA solo la usará.

> **⚠️ Importante — el batch se re-ejecuta en CADA arranque.** Como
> `spring.batch.job.enabled=true`, cada vez que inicies la app el Job vuelve a correr e
> **inserta otros 10 registros** (la tabla no tiene restricción de unicidad). Si reinicias
> varias veces para probar la API, verás los datos **duplicados** — y en MongoDB incluso
> multiplicados, porque el Step 2 relee toda la tabla ya duplicada (en una prueba real:
> 10 → 20 en MySQL y 10 → 30 en MongoDB tras un solo reinicio). Para trabajar la API:
>
> 1. **Recomendado:** después de la **primera** corrida exitosa (cuando los datos ya estén
>    cargados), pon `spring.batch.job.enabled=false`. Así los reinicios solo levantan la
>    API y sirven los datos existentes, sin re-procesar el CSV.
> 2. Si dejas el batch activo, **limpia las bases antes de cada reinicio** (sección
>    "Para re-ejecutar (limpiar datos)").

---

## Paso 16: Entidad JPA `EstudianteEntity`

En un paquete nuevo `com.academia.batch.repository` (o en `model`, como prefieras). Esta
clase mapea la tabla `estudiantes_procesados` a un objeto Java. En la Parte 1 leíamos esa
tabla con JDBC directo; ahora, para la API, usamos **Spring Data JPA**.

```java
package com.academia.batch.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiantes_procesados")   // mapea a la tabla existente
public class EstudianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // id autoincremental de MySQL
    private Long id;

    private String nombre;
    private String grupo;
    private double nota1;
    private double nota2;
    private double nota3;
    private double promedio;

    public EstudianteEntity() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public double getNota1() { return nota1; }
    public void setNota1(double nota1) { this.nota1 = nota1; }

    public double getNota2() { return nota2; }
    public void setNota2(double nota2) { this.nota2 = nota2; }

    public double getNota3() { return nota3; }
    public void setNota3(double nota3) { this.nota3 = nota3; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }
}
```

| Anotación | Qué hace |
|-----------|----------|
| `@Entity` | Marca la clase como una entidad JPA (una fila de tabla) |
| `@Table(name = "estudiantes_procesados")` | Indica a qué tabla corresponde |
| `@Id` + `@GeneratedValue(IDENTITY)` | El `id` es la clave primaria autoincremental |

---

## Paso 17: Repositorios (JPA y MongoDB)

Un **repositorio** te da operaciones de base de datos **sin escribir SQL**. Crea dos
interfaces en `com.academia.batch.repository`.

**Repositorio JPA (MySQL):**

```java
package com.academia.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {

    // Spring Data crea la consulta automaticamente a partir del nombre del metodo
    List<EstudianteEntity> findByGrupo(String grupo);
}
```

**Repositorio MongoDB (reportes):**

```java
package com.academia.batch.repository;

import com.academia.batch.model.EstudianteReporte;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReporteRepository extends MongoRepository<EstudianteReporte, String> {

    List<EstudianteReporte> findByEstado(String estado);
}
```

Solo por **extender `JpaRepository` / `MongoRepository`**, ya tienes gratis: `findAll()`,
`findById()`, `save()`, `deleteById()`, `count()`, etc. Y los métodos como
`findByGrupo` / `findByEstado` los implementa Spring leyendo su nombre.

> Tienes JPA y MongoDB en el mismo proyecto: Spring sabe cuál es cuál porque
> `EstudianteEntity` está anotada con `@Entity` y `EstudianteReporte` con `@Document`.

---

## Paso 18: Servicio `EstudianteService`

Una clase de **lógica de negocio** entre el controlador y el repositorio. Aquí ponemos un
cálculo de ejemplo (cuántos estudiantes están aprobados). En la Parte 3 probaremos esta
clase con Mockito.

```java
package com.academia.batch.service;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    // Inyeccion por constructor (Spring Core)
    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    // Cuenta cuantos estudiantes tienen promedio aprobatorio (>= 70)
    public long contarAprobados() {
        return repository.findAll().stream()
                .filter(e -> e.getPromedio() >= 70)
                .count();
    }
}
```

> Si pusiste `EstudianteEntity` en el paquete `model`, ajusta el `import`.

---

## Paso 19: Controlador REST de estudiantes (todos los verbos HTTP)

En un paquete nuevo `com.academia.batch.controller`. Un `@RestController` expone los datos
por HTTP. Este cubre **los cinco verbos** y devuelve **códigos de respuesta** correctos.

```java
package com.academia.batch.controller;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import com.academia.batch.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")   // URI base del recurso
public class EstudianteController {

    private final EstudianteRepository repository;
    private final EstudianteService service;

    public EstudianteController(EstudianteRepository repository, EstudianteService service) {
        this.repository = repository;
        this.service = service;
    }

    // GET /api/estudiantes  -> lista todos (200)
    @GetMapping
    public List<EstudianteEntity> listar() {
        return repository.findAll();
    }

    // GET /api/estudiantes/{id} -> uno; 200 o 404
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteEntity> obtener(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)                      // 200 OK
                .orElse(ResponseEntity.notFound().build());   // 404 Not Found
    }

    // GET /api/estudiantes/aprobados/total -> dato calculado por el servicio
    @GetMapping("/aprobados/total")
    public Map<String, Long> totalAprobados() {
        return Map.of("aprobados", service.contarAprobados());
    }

    // POST /api/estudiantes -> crea; 201 Created
    @PostMapping
    public ResponseEntity<EstudianteEntity> crear(@RequestBody EstudianteEntity nuevo) {
        EstudianteEntity guardado = repository.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);   // 201
    }

    // PUT /api/estudiantes/{id} -> reemplaza por completo; 200 o 404
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteEntity> actualizar(@PathVariable Long id,
                                                       @RequestBody EstudianteEntity datos) {
        return repository.findById(id)
                .map(e -> {
                    e.setNombre(datos.getNombre());
                    e.setGrupo(datos.getGrupo());
                    e.setNota1(datos.getNota1());
                    e.setNota2(datos.getNota2());
                    e.setNota3(datos.getNota3());
                    e.setPromedio(datos.getPromedio());
                    return ResponseEntity.ok(repository.save(e));   // 200
                })
                .orElse(ResponseEntity.notFound().build());          // 404
    }

    // PATCH /api/estudiantes/{id} -> cambio parcial (solo el grupo); 200 o 404
    @PatchMapping("/{id}")
    public ResponseEntity<EstudianteEntity> cambiarGrupo(@PathVariable Long id,
                                                         @RequestBody Map<String, String> cambios) {
        return repository.findById(id)
                .map(e -> {
                    if (cambios.containsKey("grupo")) {
                        e.setGrupo(cambios.get("grupo"));
                    }
                    return ResponseEntity.ok(repository.save(e));   // 200
                })
                .orElse(ResponseEntity.notFound().build());          // 404
    }

    // DELETE /api/estudiantes/{id} -> elimina; 204 o 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();                // 404
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();                   // 204 No Content
    }
}
```

> **Nota sobre `promedio` (simplificación a propósito):** el `promedio` lo calcula el batch
> en el Step 1. La API, para mantenerse simple y centrarse en los verbos HTTP, recibe
> `promedio` como un campo más en el POST/PUT y **no lo recalcula**. En un sistema real lo
> normal sería que el servidor lo calcule a partir de las notas. *Mejora opcional para el
> alumno:* recalcular `promedio` en el `EstudianteService` antes de guardar.

---

## Paso 20: Controlador REST de reportes (lado MongoDB)

Para exponer también los reportes guardados en MongoDB:

```java
package com.academia.batch.controller;

import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.repository.ReporteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteRepository repository;

    public ReporteController(ReporteRepository repository) {
        this.repository = repository;
    }

    // GET /api/reportes -> todos los reportes (MongoDB)
    @GetMapping
    public List<EstudianteReporte> listar() {
        return repository.findAll();
    }

    // GET /api/reportes/estado/APROBADO  o  /REPROBADO
    @GetMapping("/estado/{estado}")
    public List<EstudianteReporte> porEstado(@PathVariable String estado) {
        return repository.findByEstado(estado.toUpperCase());
    }
}
```

Fíjate que un mismo proyecto ahora expone datos de **MySQL** (`/api/estudiantes`) y de
**MongoDB** (`/api/reportes`).

---

## Paso 21: Probar la API

Arranca la app (`mvn spring-boot:run`). El batch corre al inicio y luego el servidor
queda escuchando en `http://localhost:8080`.

### Conceptos que estás aplicando

**URIs y recursos:** cada URL identifica un *recurso*. La colección y un elemento:

| URI | Recurso |
|-----|---------|
| `/api/estudiantes` | la colección de estudiantes |
| `/api/estudiantes/1` | el estudiante con id 1 |
| `/api/reportes/estado/REPROBADO` | los reportes reprobados |

**Verbos HTTP:** la *acción* sobre el recurso (mismo URI, distinta operación):

| Verbo | Acción | Código típico de éxito |
|-------|--------|------------------------|
| `GET` | leer | 200 OK |
| `POST` | crear | 201 Created |
| `PUT` | reemplazar | 200 OK |
| `PATCH` | actualizar parcialmente | 200 OK |
| `DELETE` | eliminar | 204 No Content |

**Códigos de respuesta:** indican el resultado. Los que usa esta API:

| Código | Significado |
|--------|-------------|
| `200 OK` | La petición salió bien y hay contenido |
| `201 Created` | Se creó un recurso (POST) |
| `204 No Content` | Salió bien pero no hay cuerpo (DELETE) |
| `404 Not Found` | El recurso (id) no existe |

**HTTP vs HTTPS:** HTTP envía la información en texto plano; HTTPS la **cifra** con TLS.
En local usamos HTTP (`http://localhost:8080`); en producción siempre se usa HTTPS.

### Pruebas con curl

```bash
# GET todos los estudiantes (200)
curl http://localhost:8080/api/estudiantes

# GET uno por id (200) o inexistente (404)
curl -i http://localhost:8080/api/estudiantes/1
curl -i http://localhost:8080/api/estudiantes/9999

# POST crear (201)
curl -i -X POST http://localhost:8080/api/estudiantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Nuevo Alumno","grupo":"A","nota1":80,"nota2":85,"nota3":90,"promedio":85}'

# PUT reemplazar (200)
curl -i -X PUT http://localhost:8080/api/estudiantes/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Perez","grupo":"B","nota1":80,"nota2":75,"nota3":90,"promedio":81.67}'

# PATCH cambio parcial: solo el grupo (200)
curl -i -X PATCH http://localhost:8080/api/estudiantes/1 \
  -H "Content-Type: application/json" \
  -d '{"grupo":"Z"}'

# DELETE eliminar (204)
curl -i -X DELETE http://localhost:8080/api/estudiantes/1

# GET dato calculado por el servicio
curl http://localhost:8080/api/estudiantes/aprobados/total

# GET reportes desde MongoDB
curl http://localhost:8080/api/reportes
curl http://localhost:8080/api/reportes/estado/REPROBADO
```

> `curl -i` muestra la línea de estado (ej. `HTTP/1.1 200 OK`) para que veas el código de
> respuesta. También puedes usar **Postman** si prefieres una herramienta gráfica.

---
---

# Parte 3: Pruebas unitarias (JUnit + Mockito)

Una **prueba unitaria** verifica una pieza de código de forma aislada y automática. Aquí
probamos la lógica del proyecto sin necesidad de las bases de datos.

Los tests van en `src/test/java/...` (mismo paquete que la clase que prueban).

---

## Paso 22: Test del Processor (JUnit + assertions)

Probamos que el cálculo del promedio y la asignación de estado son correctos. No
necesitan Spring ni base de datos: creamos el objeto y verificamos el resultado.

Crea `src/test/java/com/academia/batch/processor/ProcessorTest.java`:

```java
package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessorTest {

    @Test
    void calculaElPromedioCorrectamente() {
        Estudiante e = new Estudiante();
        e.setNota1(80);
        e.setNota2(90);
        e.setNota3(100);

        Estudiante resultado = new EstudianteProcessor().process(e);

        // (80 + 90 + 100) / 3 = 90
        assertEquals(90.0, resultado.getPromedio(), 0.001);
    }

    @Test
    void marcaAprobadoCuandoElPromedioEsAlMenos70() {
        Estudiante e = new Estudiante();
        e.setPromedio(70);

        EstudianteReporte reporte = new ReporteEstudianteProcessor().process(e);

        assertEquals("APROBADO", reporte.getEstado());
    }

    @Test
    void marcaReprobadoCuandoElPromedioEsMenorA70() {
        Estudiante e = new Estudiante();
        e.setPromedio(69.9);

        EstudianteReporte reporte = new ReporteEstudianteProcessor().process(e);

        assertEquals("REPROBADO", reporte.getEstado());
    }
}
```

| Elemento | Qué es |
|----------|--------|
| `@Test` | Marca un método como caso de prueba (JUnit 5) |
| `assertEquals(esperado, real)` | **Assertion**: falla el test si no son iguales |
| El tercer parámetro `0.001` | Tolerancia para comparar `double` |

---

## Paso 23: Test del servicio con Mockito

`EstudianteService.contarAprobados()` depende del repositorio, que normalmente va a la
base de datos. Con **Mockito** creamos un repositorio **falso (mock)** que devuelve datos
de prueba, así probamos *solo* la lógica del servicio, sin MySQL.

Crea `src/test/java/com/academia/batch/service/EstudianteServiceTest.java`:

```java
package com.academia.batch.service;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository repository;     // repositorio falso

    @InjectMocks
    private EstudianteService service;           // recibe el mock por constructor

    @Test
    void cuentaSoloLosAprobados() {
        // Preparamos datos de prueba: 2 aprobados (>=70) y 1 reprobado
        EstudianteEntity aprobado1 = nuevo(85);
        EstudianteEntity aprobado2 = nuevo(70);
        EstudianteEntity reprobado = nuevo(50);

        // Cuando alguien llame repository.findAll(), devuelve esta lista
        when(repository.findAll()).thenReturn(List.of(aprobado1, aprobado2, reprobado));

        long total = service.contarAprobados();

        assertEquals(2, total);   // solo 2 estan aprobados
    }

    private EstudianteEntity nuevo(double promedio) {
        EstudianteEntity e = new EstudianteEntity();
        e.setPromedio(promedio);
        return e;
    }
}
```

| Elemento | Qué hace |
|----------|----------|
| `@ExtendWith(MockitoExtension.class)` | Activa Mockito en JUnit 5 |
| `@Mock` | Crea un objeto falso del repositorio |
| `@InjectMocks` | Crea el servicio e inyecta el mock |
| `when(...).thenReturn(...)` | Define qué responde el mock al ser llamado |

La clave de Mockito: probamos `EstudianteService` **sin** una base de datos real. El mock
simula el repositorio.

---

## Paso 24: Ejecutar las pruebas

```bash
mvn test
```

Estas pruebas son unitarias puras (no levantan Spring ni se conectan a las bases), así que
**no necesitas los contenedores corriendo** para ejecutarlas. Salida esperada:

```
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Cuatro pruebas en verde: tres del Processor y una del servicio.

---

## Problemas comunes (Partes 2 y 3)

| Síntoma | Causa y solución |
|---------|------------------|
| `Web server failed to start. Port 8080 was already in use` | Otra app ocupa el 8080. Cambia el puerto con `server.port=8081` en `application.properties`, o cierra la otra app. |
| Cada reinicio agrega más estudiantes (10, 20, 30…) | El batch se re-ejecuta al arrancar. Pon `spring.batch.job.enabled=false` tras la primera carga, o limpia las bases antes de reiniciar (sección "Para re-ejecutar"). |
| `GET /api/estudiantes/1` devuelve `404` | Ese `id` no existe (lo borraste, o tras limpiar/duplicar los ids cambiaron). Haz `GET /api/estudiantes` para ver los ids reales. |
| `Communications link failure` o `Connection refused` en 27018 | Falta arrancar un contenedor: `docker start mysql-academia mongo-academia`. |
| El POST inserta pero `promedio` queda en `0` | No incluiste `promedio` en el JSON. La API no lo calcula (ver nota del Paso 19); envíalo en el cuerpo. |
| Al compilar: no encuentra `EstudianteEntity` | El `import` no coincide con el paquete donde pusiste la clase (`repository` o `model`). Ajusta el `import`. |

---

> Esta guía se probó de extremo a extremo (compilación, `mvn test`, arranque, batch y los
> endpoints REST) contra los contenedores `mysql-academia` y `mongo-academia`.
