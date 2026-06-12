# Instalacion de DBeaver Community

DBeaver es un cliente gratuito de bases de datos. Soporta MySQL, PostgreSQL y muchas mas.

**Nota:** DBeaver Community NO soporta MongoDB. Para MongoDB usa **MongoDB Compass** (ver seccion al final).

---

## Instalacion en Windows

1. Ve a [https://dbeaver.io/download/](https://dbeaver.io/download/)
2. Descarga **Windows (installer)**
3. Ejecuta el `.exe` y sigue el wizard con Next hasta finalizar

---

## Conectar a MySQL (Docker)

Prerequisito: el contenedor `mysql-academia` debe estar corriendo (`docker ps`).

1. Abre DBeaver
2. Clic en **Nueva Conexion** (icono de enchufe con +)
3. Selecciona **MySQL**
4. Llena los datos:

```
Host:     localhost
Puerto:   3306
Usuario:  root
Password: root123
```

5. Clic en **Test Connection** para verificar
6. Si pide descargar el driver, acepta
7. Clic en **Finalizar**

---

## Conectar a MongoDB - usar MongoDB Compass

DBeaver Community no incluye driver de MongoDB (requiere version PRO de pago). Usa **MongoDB Compass** en su lugar.

1. Descarga en [https://www.mongodb.com/products/tools/compass](https://www.mongodb.com/products/tools/compass)
2. Instala el `.exe`
3. En la pantalla de conexion pega el connection string:

```
mongodb://root:root123@localhost:27017
```

4. Clic en **Connect**

---

## Problema comun: "Public Key Retrieval is not allowed"

Este error aparece al conectar a MySQL 8 porque usa un metodo de autenticacion (caching_sha2_password) que necesita un permiso adicional.

**Solucion:**

1. En la pantalla de configuracion de la conexion, ve a la pestaña **Driver properties**
2. Busca la propiedad **allowPublicKeyRetrieval** y cambiala a **TRUE**
3. Clic en **Test Connection** de nuevo

---

## Uso basico

- **Ver tablas/colecciones**: Expande la conexion en el panel izquierdo > esquema/base de datos > tablas
- **Ejecutar queries**: Clic derecho en la conexion > SQL Editor > Open SQL Script
- **Ver datos de una tabla**: Doble clic sobre la tabla en el panel izquierdo
