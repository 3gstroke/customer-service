# 🧑‍💼 customer-service

Microservicio encargado de la **gestión y validación de clientes** dentro del Sistema de Transferencias Bancarias. Este servicio es la **fuente de verdad** para la existencia y el estado de un cliente y es consultado de forma sincrónica por otros microservicios (por ejemplo, `transaction-service`).

---

## 🛠️ Stack Tecnológico

* **Java:** 21
* **Framework:** Quarkus 3.27.1
* **Persistencia:** Hibernate ORM + Panache
* **Migraciones:** Flyway
* **Base de datos:** PostgreSQL
* **API REST:** RESTEasy Reactive + Jackson
* **Observabilidad:** SmallRye Health, SmallRye Metrics
* **Build:** Maven
* **Docker Compose:** PostgreSQL en Raspberrypi 5

---

## ⚙️ Configuración Principal

## Docker Compose 
```
* docker-compose.yaml *
services:
    postgres_db:
      image: postgres:16
      container_name: postgres_db
      restart: always
      environment:
        POSTGRES_USER: gstroke
        POSTGRES_PASSWORD: gstroke24
        POSTGRES_DB: dev_gstroke_db
      volumes:
        - postgres_data:/var/lib/postgresql/data
      ports:
        - "5432:5432"
      healthcheck:
        test: ["CMD-SHELL", "pg_isready -U gstroke"]
        interval: 10s
        timeout: 5s
        retries: 5
```
* Ejecutar sudo nano docker compose up -d
* Verificar con docker ps
### Servidor

* **Puerto:** `8081`

```properties
quarkus.http.port=8081
```

### Base de Datos

* **Motor:** PostgreSQL
* **Host:** `192.168.18.31`
* **Base de datos:** `dev_gstroke_db`
* **Schema:** `customer_db`

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=gstroke
quarkus.datasource.password=gstroke24
quarkus.datasource.jdbc.url=jdbc:postgresql://192.168.18.31:5432/dev_gstroke_db
```

> ⚠️ Nota: las credenciales están definidas para entorno de desarrollo. En entornos productivos se recomienda usar variables de entorno o un vault.

---

## 🗄️ Hibernate ORM

* **Estrategia de schema:** `validate`
* **Schema por defecto:** `customer_db`
* **Log SQL:** habilitado

```properties
quarkus.hibernate-orm.database.default-schema=customer_db
quarkus.hibernate-orm.schema-management.strategy=validate
quarkus.hibernate-orm.log.sql=true
```

Esto implica que:

* Hibernate **no crea ni modifica** tablas
* El esquema debe existir previamente
* La estructura se valida al iniciar la aplicación

---

## 🛫 Flyway (Migraciones)

Las migraciones se ejecutan automáticamente al iniciar la aplicación.

```properties
quarkus.flyway.migrate-at-start=true
quarkus.flyway.schemas=customer_db
quarkus.flyway.default-schema=customer_db
quarkus.flyway.baseline-on-migrate=true
```

### Convención de scripts

Los scripts deben ubicarse en:

```
src/main/resources/db/migration
```

Ejemplo:

```
V1__init.sql
```

---

## 📌 Responsabilidades del Servicio

* Gestionar clientes del banco
* Consultar información de clientes
* Validar si un cliente existe y se encuentra activo
* Exponer endpoints REST para uso interno por otros microservicios

---

## 🔗 Endpoints REST

### Obtener cliente por ID

```
GET /customers/{id}
```

**Respuesta:**

* `200 OK` → cliente encontrado
* `404 Not Found` → cliente no existe

---

### Validar cliente (uso interno)

```
GET /customers/{id}/validate
```

**Respuesta:**

* `200 OK` → cliente válido y activo
* `404 Not Found` → cliente no existe
* `409 Conflict` → cliente inactivo

Este endpoint es consumido principalmente por `transaction-service` antes de iniciar una transferencia.

---

## 🩺 Health Check

El servicio expone endpoints de salud estándar de Quarkus:

```
GET /q/health
```

---

## 📊 Métricas

Las métricas están disponibles en formato Prometheus:

```
GET /q/metrics
```

---

## ▶️ Ejecución en Desarrollo

### Requisitos

* Java 21
* Maven 3.9+
* PostgreSQL accesible desde la red

### Comando

```bash
./mvnw quarkus:dev
```

---

## 🧪 Testing

El proyecto incluye soporte para:

* JUnit 5
* RestAssured

Ejecución de tests:

```bash
./mvnw test
```

---

## 🧠 Decisiones de Diseño

* **Schema dedicado (`customer_db`)** para aislar datos por microservicio
* **Flyway + validate** para control estricto del modelo
* **REST sincrónico** para validaciones rápidas (fail-fast)
* **Sin acceso directo desde otros servicios a la BD**

---

## 📎 Notas Importantes

* Este servicio **no maneja transferencias ni saldos**
* Es un servicio **core** dentro del flujo de transferencias
* Diseñado para ser consumido por otros microservicios de forma segura y desacoplada
