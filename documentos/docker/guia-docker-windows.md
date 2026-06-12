# Guia de Docker en Windows para Desarrolladores

## ¿Que es Docker?

Docker es una herramienta que permite empaquetar una aplicacion junto con todo lo que necesita para funcionar (Java, base de datos, dependencias, configuracion) dentro de un **contenedor**.

Un contenedor es como una caja portatil: lo que funciona en tu maquina, funciona igual en la maquina de tu compañero, en el servidor de pruebas y en produccion.

## ¿Por que usar Docker?

### El problema clasico: "En mi maquina si funciona"

Sin Docker, cada desarrollador instala versiones diferentes de Java, MySQL, etc. Esto causa errores dificiles de diagnosticar. Docker elimina ese problema.

### Ventajas concretas

- **Entorno reproducible**: Todos trabajan con las mismas versiones exactas.
- **Configuracion rapida**: Un nuevo miembro del equipo ejecuta un comando y tiene todo listo, en lugar de seguir un documento de 20 paginas de instalacion.
- **Aislamiento**: Puedes tener un proyecto con Java 17 y MySQL 8, y otro con Java 21 y PostgreSQL 16, sin conflictos.
- **Similitud con produccion**: Tu entorno local se parece mucho al servidor real donde se despliega la aplicacion.
- **Facil de limpiar**: Si algo sale mal, destruyes el contenedor y creas uno nuevo en segundos.

### Ejemplo practico

Sin Docker:
```
1. Instalar Java 17
2. Instalar MySQL 8
3. Crear base de datos manualmente
4. Configurar variables de entorno
5. Rezar para que todo funcione
```

Con Docker:
```
docker compose up
```

Listo. Todo levantado y configurado.

---

## Instalacion de Docker en Windows

### Requisitos previos

- **Windows 10** (version 2004 o superior) o **Windows 11**
- **Minimo 4 GB de RAM** (recomendado 8 GB)
- Virtualizacion habilitada en BIOS (normalmente ya viene activada)

### Paso 1: Habilitar WSL 2

Docker en Windows funciona sobre WSL 2 (Windows Subsystem for Linux). Abre **PowerShell como Administrador** y ejecuta:

```powershell
wsl --install
```

Esto instala WSL 2 y una distribucion de Ubuntu. **Reinicia tu computadora** cuando termine.

Despues del reinicio, verifica la instalacion:

```powershell
wsl --version
```

### Paso 2: Descargar Docker Desktop

1. Ve a [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)
2. Haz clic en **"Download for Windows"**
3. Ejecuta el instalador `Docker Desktop Installer.exe`

### Paso 3: Instalar Docker Desktop

1. En el instalador, asegurate de que la opcion **"Use WSL 2 instead of Hyper-V"** este marcada.
2. Haz clic en **"Ok"** y espera a que termine la instalacion.
3. **Reinicia tu computadora** cuando lo pida.

### Paso 4: Verificar la instalacion

Abre una terminal (PowerShell o CMD) y ejecuta:

```powershell
docker --version
```

Deberias ver algo como:
```
Docker version 27.x.x, build xxxxxxx
```

Prueba que funciona correctamente:

```powershell
docker run hello-world
```

Si ves el mensaje "Hello from Docker!", todo esta listo.

---

## Comandos esenciales

| Comando | Descripcion |
|---------|-------------|
| `docker run <imagen>` | Crear y ejecutar un contenedor |
| `docker ps` | Ver contenedores en ejecucion |
| `docker ps -a` | Ver todos los contenedores (incluidos los detenidos) |
| `docker stop <id>` | Detener un contenedor |
| `docker rm <id>` | Eliminar un contenedor |
| `docker images` | Ver imagenes descargadas |
| `docker pull <imagen>` | Descargar una imagen |
| `docker compose up` | Levantar servicios definidos en `docker-compose.yml` |
| `docker compose down` | Detener y eliminar los servicios |

## Ejemplo: Levantar una base de datos MySQL

En lugar de instalar MySQL manualmente, ejecuta:

```powershell
docker run --name mi-mysql -e MYSQL_ROOT_PASSWORD=secret -p 3306:3306 -d mysql:8
```

Esto:
- Descarga MySQL 8 si no la tienes
- Crea un contenedor llamado `mi-mysql`
- Configura la contraseña de root como `secret`
- Expone el puerto 3306 para que te conectes normalmente
- Lo ejecuta en segundo plano (`-d`)

Para conectarte, usa cualquier cliente MySQL apuntando a `localhost:3306`.

Para detenerlo y eliminarlo cuando ya no lo necesites:

```powershell
docker stop mi-mysql
docker rm mi-mysql
```

---

## Posibles problemas y soluciones

| Problema | Solucion |
|----------|----------|
| "WSL 2 is not installed" | Ejecuta `wsl --install` en PowerShell como admin y reinicia |
| Docker Desktop no inicia | Verifica que la virtualizacion este habilitada en el BIOS |
| Contenedor no accesible | Revisa que el puerto no este ocupado por otro proceso |
| Rendimiento lento | Asigna mas RAM a Docker en Settings > Resources |

---

## Resumen

Docker no es opcional en el desarrollo moderno. Es una herramienta que todo desarrollador necesita conocer. No tienes que ser experto desde el primer dia, pero si debes entender que:

1. Un **contenedor** empaqueta tu aplicacion y sus dependencias.
2. Una **imagen** es la plantilla para crear contenedores.
3. `docker run` crea contenedores, `docker compose up` levanta multiples servicios.
4. Elimina el problema de "en mi maquina si funciona".
