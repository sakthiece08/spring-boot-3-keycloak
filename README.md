# spring-boot-3-keycloak

#### Keycloak user details

```
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
