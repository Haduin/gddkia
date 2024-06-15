import Keycloak from 'keycloak-js';

export const keycloak = new Keycloak(
  {
    "realm": "gddkia-realm",
    "auth-server-url": "http://localhost:8081",
    "ssl-required": "external",
    "clientId" : "gddkia-client-id",
    "public-client": true
  }
)
