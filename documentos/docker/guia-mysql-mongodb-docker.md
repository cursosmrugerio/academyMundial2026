# MySQL y MongoDB en Docker - Guia paso a paso

Prerequisito: tener Docker Desktop instalado y corriendo.

---

## MySQL 8

### 1. Descargar la imagen

```powershell
docker pull mysql:8
```

### 2. Crear y ejecutar el contenedor

```powershell
docker run --name mysql-academia -e MYSQL_ROOT_PASSWORD=root123 -p 3306:3306 -d mysql:8
```

| Parametro | Que hace |
|-----------|----------|
| `--name mysql-academia` | Nombre del contenedor |
| `-e MYSQL_ROOT_PASSWORD=root123` | Contraseña del usuario root |
| `-p 3306:3306` | Expone el puerto 3306 en tu maquina |
| `-d` | Ejecuta en segundo plano |

### 3. Verificar que esta corriendo

```powershell
docker ps
```

Debes ver `mysql-academia` con estado `Up`.

### 4. Conectarte desde terminal

```powershell
docker exec -it mysql-academia mysql -u root -p
```

Ingresa la contraseña `root123` y estaras dentro del cliente MySQL.

### 5. Crear una base de datos de prueba

```sql
CREATE DATABASE academia;
USE academia;
CREATE TABLE alumnos (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(100));
INSERT INTO alumnos (nombre) VALUES ('Juan'), ('Maria');
SELECT * FROM alumnos;
```

### 6. Conectarte desde tu IDE o cliente externo

Usa estos datos de conexion:

```
Host:     localhost
Puerto:   3306
Usuario:  root
Password: root123
```

Funciona con MySQL Workbench, DBeaver, IntelliJ, etc.

---

## MongoDB 7

### 1. Descargar la imagen

```powershell
docker pull mongo:7
```

### 2. Crear y ejecutar el contenedor

```powershell
docker run --name mongo-academia -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root123 -p 27017:27017 -d mongo:7
```

| Parametro | Que hace |
|-----------|----------|
| `--name mongo-academia` | Nombre del contenedor |
| `-e MONGO_INITDB_ROOT_USERNAME=root` | Usuario administrador |
| `-e MONGO_INITDB_ROOT_PASSWORD=root123` | Contraseña del administrador |
| `-p 27017:27017` | Expone el puerto 27017 en tu maquina |
| `-d` | Ejecuta en segundo plano |

### 3. Verificar que esta corriendo

```powershell
docker ps
```

Debes ver `mongo-academia` con estado `Up`.

### 4. Conectarte desde terminal

```powershell
docker exec -it mongo-academia mongosh -u root -p root123
```

### 5. Crear una base de datos y coleccion de prueba

```javascript
use academia
db.alumnos.insertMany([
  { nombre: "Juan", edad: 25 },
  { nombre: "Maria", edad: 28 }
])
db.alumnos.find()
```

### 6. Conectarte desde tu IDE o cliente externo

Usa este connection string:

```
mongodb://root:root123@localhost:27017
```

Funciona con MongoDB Compass, Studio 3T, IntelliJ, etc.

---

## Comandos del dia a dia

### Detener los contenedores

```powershell
docker stop mysql-academia mongo-academia
```

### Iniciarlos de nuevo (sin perder datos)

```powershell
docker start mysql-academia mongo-academia
```

### Ver logs si algo falla

```powershell
docker logs mysql-academia
docker logs mongo-academia
```

### Eliminar los contenedores (se pierden los datos)

```powershell
docker stop mysql-academia mongo-academia
docker rm mysql-academia mongo-academia
```

---

## Nota sobre persistencia de datos

Con los comandos anteriores, los datos se mantienen mientras el contenedor exista. Si eliminas el contenedor con `docker rm`, los datos se pierden.

Para que los datos sobrevivan incluso si eliminas el contenedor, agrega un volumen al crear el contenedor:

```powershell
docker run --name mysql-academia -e MYSQL_ROOT_PASSWORD=root123 -p 3306:3306 -v mysql-data:/var/lib/mysql -d mysql:8
```

```powershell
docker run --name mongo-academia -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root123 -p 27017:27017 -v mongo-data:/data/db -d mongo:7
```

La diferencia es el flag `-v nombre:/ruta` que crea un volumen persistente en Docker.
