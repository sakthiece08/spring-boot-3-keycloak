# spring-boot-3-keycloak

Keycloak version# 23.0.0 https://www.keycloak.org/getting-started/getting-started-zip#_create_an_admin_user

Keycloak dependency:
- Java version: OpenJDK 17
- Database: PostgreSQL

#### Keycloak user details

```
client-id: springboot-be
+-----------------------------------------+
|   ROLE         |  User name  | Password
+-----------------------------------------+
|Authorized User | user        | password
+-----------------------------------------+
|Admin           | admin       | password
+-----------------------------------------+
|Owner           | owner       | password
+-----------------------------------------+
|Manager         | manager     | password
+-----------------------------------------+
```
#### Default role format of Keycloak claim
Custom JwtAuthConverter component is used to parse below default role format in the Keycloack access token.
```
"realm_access": {
    "roles": [
      "owner",
      "offline_access",
      "default-roles-teqmonic",
      "uma_authorization"
    ]
  }
```
#### Keycloak user management
We can leverage Spring boot application to view and create Keycloak users rather than doing it on Keycloak console. Below are the dependencies used for this purpose:

```
<dependency>
  <groupId>org.keycloak</groupId>
  <artifactId>keycloak-servlet-filter-adapter</artifactId>
  <version>22.0.5</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.keycloak</groupId>
  <artifactId>keycloak-spring-boot-starter</artifactId>
  <version>22.0.5</version>
</dependency>
<dependency>
  <groupId>org.keycloak</groupId>
  <artifactId>keycloak-admin-client</artifactId>
  <version>22.0.5</version>
</dependency>
```
Configurations:
```
# Keycloak client API calls
keycloak:
  realm: Teqmonic
  server: http://localhost:8000
  client-id: admin-cli
  grant-type: password
  user-name: clientcli
  password: system
```
### OpenAPI documentation
http://localhost:8080/swagger-ui.html
```
springdoc:
  swagger-ui:
    oauth:
      client-id: springboot-be
      client-secret: TYPasarv7yqXtTn21KyTUZmD5LE1ds46
      # swagger-ui custom path
      path: /swagger-ui.html
```
Security scheme definition to test the API through Swagger ui
```
@SecurityScheme(name = "Keycloak",
                openIdConnectUrl = "http://localhost:8000/realms/Teqmonic/.well-known/openid-configuration",
                scheme = "bearer",
                type = SecuritySchemeType.OPENIDCONNECT,
                in = SecuritySchemeIn.HEADER
		)
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.teqmonic")
public class Application {
```
