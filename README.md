# 📧 Servicio de Registro de Usuarios con Validación de Email (Spring Boot)

Este proyecto es un servicio backend construido con **Spring Boot** que permite el **registro de usuarios** y el **registro de operaciones de una calculadora con su historial**. Además, valida el correo electrónico utilizando la API de [Apilayer MailboxLayer](https://mailboxlayer.com/).

---

## 📦 Características

- Registro de usuarios con nombre, nombre de usuario, email y contraseña
- Validación de email en tiempo real usando Apilayer (formato + dominio válido)
- Estructura RESTful lista para producción
- Integración con base de datos (ej. H2, MySQL, PostgreSQL)
- Gestión de errores y respuestas claras para el cliente
- Registro de operaciones y validación con token JWT

---

## 🚀 Requisitos

- Java 17 o superior
- Maven 3.6+
- IDE como IntelliJ IDEA o VS Code (opcional)
- Una cuenta en [MailboxLayer](https://mailboxlayer.com/) para obtener la API Key

---

## 🔧 Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/nel-colab/loginJWT.git
cd loginJWT
```

### 2. Configurar el archivo `application.yml`

Asegúrate de tener el archivo `src/main/resources/application.yml` con tu clave de Apilayer:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

mailboxlayer:
  api:
    key: TU_API_KEY_AQUI
```

🔐 **Importante**: Reemplaza `TU_API_KEY_AQUI` por tu clave personal de [Apilayer](https://mailboxlayer.com/product).

---

### 3. Compilar el proyecto

Desde la raíz del proyecto, ejecuta:

```bash
./mvnw clean install
```

> En Windows: `mvnw.cmd clean install`

---

### 4. Ejecutar el servicio

```bash
./mvnw spring-boot:run
```

> O bien, ejecuta directamente la clase `CalculadoraApplication.java` desde tu IDE.

---

## 🧪 Pruebas de la API

### ✅ Registro de usuario

- **URL:** `POST http://localhost:8080/users/register`
- **Content-Type:** `application/json`
- **Body:**

```json
{
  "nombre": "Juan Pérez",
  "user": "juanp",
  "email": "juan@example.com",
  "password": "123456"
}
```

🔄 Si el correo no es válido, se responderá con:

```json
{
  "message": "Correo no válido o no existe"
}
```

---

## 🧠 Lógica de validación

La validación de correo se realiza en el servicio:

```java
public boolean esEmailValido(String email) {
    URI uri = UriComponentsBuilder
        .fromUriString("http://apilayer.net/api/check")
        .queryParam("access_key", apiKey)
        .queryParam("email", email)
        .queryParam("smtp", 1)
        .queryParam("format", 1)
        .build()
        .toUri();

    Map<String, Object> response = restTemplate.getForObject(uri, Map.class);

    Boolean formato = (Boolean) response.get("format_valid");
    Boolean mxFound = (Boolean) response.get("mx_found");

    return Boolean.TRUE.equals(formato) && Boolean.TRUE.equals(mxFound);
}
```

---

## 🧰 Herramientas utilizadas

- Spring Boot 3+
- Maven
- Java 17+
- MailboxLayer API
- H2 Database (en memoria, para desarrollo)

---

## 📂 Estructura del proyecto

```
src/
├── main/
│   ├── java/com/Calculator/calculadora/
│   │   ├── Controller/        → Controladores REST
│   │   ├── Entity/            → Entidades
│   │   ├── Exceptions/        → Excepciones personalizadas
│   │   ├── Repository/        → Repositorios para acceso a la base de datos
│   │   ├── Request/           → Estructuras de solicitud
│   │   ├── Response/          → Estructuras de respuesta al cliente
│   │   ├── Service/           → Lógica de negocio y validaciones
│   │   ├── ServiceImp/        → Implementaciones de servicios
│   │   └── CalculadoraApplication.java
│   └── resources/
│       ├── application.yml
```

---

## 👤 Autor

Desarrollado por **Nelson Castillo**

- GitHub: [@nel-colab](https://github.com/nel-colab)
- Email: nel.castillo95@gmail.com

---