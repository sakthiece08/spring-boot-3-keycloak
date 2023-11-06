# spring-boot-3-keycloak

Keycloak version# 22.0.5

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
#### Keycloak user
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
