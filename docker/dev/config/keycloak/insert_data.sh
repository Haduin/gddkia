#!/bin/bash

CONTAINER_NAME="keycloak"
REALM_NAME="gddkia-realm"
CLIENT_ID="gddkia-client-id"
ROLE_NAME="admin"
USERNAME="example-admin"
PASSWORD="example-password"

COMMAND=$(cat << EOF

cd /opt/keycloak/bin
./kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin
./kcadm.sh create realms -s realm=$REALM_NAME -s enable=true
./kcadm.sh create clients -r $REALM_NAME -s clientId=$CLIENT_ID -s 'redirectUris=["http://localhost:8080/*","http://localhost:3000/*"]'
./kcadm.sh create roles -r $REALM_NAME -s name=$ROLE_NAME
./kcadm.sh create users -r $REALM_NAME -s username=$USERNAME -s enabled=true
./kcadm.sh set-password -r $REALM_NAME --username $USERNAME --new-password $PASSWORD -t
./kcadm.sh add-roles -r $REALM_NAME --uusername $USERNAME --rolename $ROLE_NAME
EOF
)

docker exec -it $CONTAINER_NAME bash -c "$COMMAND"

exit
