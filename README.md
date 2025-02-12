# Proxy API para Mercado Libre

Este proyecto implementa un **proxy API** para consumir la API de Mercado Libre de manera transparente, aplicando autenticación y control de tasa (rate limiting) en las solicitudes.

## Características

- **Autenticación de solicitudes**: Si no se proporciona un token de autenticación en la cabecera `Authorization`, el proxy utiliza un token configurado.
- **Limitación de tasa**: El proxy limita la cantidad de solicitudes que pueden realizarse desde una IP, un path específico o una combinación de ambos.
- **Rutas del proxy**: La aplicación redirige todas las solicitudes a la API de Mercado Libre, excepto las rutas de documentación (Swagger).
  *leer la documentacion de los recursos disponibles de la API de mercado libre en* http://developers.mercadolibre.com).

## Componentes Principales

1. **ProxyController**: Expone un endpoint `/proxy/stats` que devuelve estadísticas sobre la cantidad de solicitudes realizadas por una IP o path.
2. **AuthGatewayFilter**: Filtro que asegura que todas las solicitudes tengan un token de autenticación. Si no lo tienen, lo añade automáticamente.
3. **RateLimitFilter**: Filtro que implementa el control de tasa. Limita las solicitudes a un máximo configurado por IP, path o combinación de ambos.
4. **RateLimiterService**: Servicio encargado de la lógica de limitación de tasa, que usa Redis para mantener el conteo de solicitudes.

## Requisitos

- **Mercado Libre** [API URL](https://api.mercadolibre.com) para redirigir las solicitudes.
- **Java 21** o superior. [Descargar Java 21](https://jdk.java.net/21/)
- **Spring Boot 3.2**. [Descargar Maven](https://maven.apache.org/download.cgi)
- **Docker**: [Descargar Docker](https://www.docker.com/get-started)
- **Redis** para almacenar los contadores de solicitudes y aplicar la limitación de tasa.

```bash
docker run --name redis -p 6379:6379 -d redis:latest
```

## Configuración

### Propiedades de la configuración

- **mercado-libre.api-url**: URL de la API de Mercado Libre.
- **mercado-libre.access-token**: Token de autenticación utilizado por el proxy si el cliente no lo proporciona.
- **mercado-libre.rate-limit.ip-max-requests**: Número máximo de solicitudes permitidas por IP.
- **mercado-libre.rate-limit.path-max-requests**: Número máximo de solicitudes permitidas por path.
- **mercado-libre.rate-limit.comb-max-requests**: Número máximo de solicitudes permitidas por combinación de IP y path.

### 1. Clonar el repositorio

```bash
git clone https://github.com/brendsanchez/proxy-meli
cd proxy-meli
```

### 2. Ejecuta la aplicacion:

```bash
mvn spring-boot:run -Plocal
```

## Documentación de la API con Swagger

Swagger se ha configurado para documentar las estadisticas de la API. Puedes acceder a la interfaz de Swagger en la siguiente URL:

- http://localhost:8080/swagger-ui/index.html

## Postman Collection

Se agregó un archivo `proxy-meli.postman_collection.json` de Postman en el proyecto para facilitar las pruebas de la API. Puedes importarlo en Postman para ejecutar las solicitudes preconfiguradas.
