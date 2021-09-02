package com.example.keycloakapi.role;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface RoleService {

    List<RoleRepresentation> getClientRoles(String realm, String clientHexId);

    List<RoleRepresentation> getRealmRoles(String realm);
    RoleRepresentation getRealmRoleByName(String realm, String roleName);

    void deleteRealmRole(String realm, String roleName);

    void deleteClientRole(String realm, String clientHexId, String roleName);

    void createClientRole(String realm, String clientHexId, RoleCommand roleCommand);

    RoleRepresentation getClientRoleByName(String realm, String clientHexId, String name);

    void updateClientRole(String realm, String clientHexId, RoleCommand roleCommand);

    void updateRealmRole(String realm, RoleCommand roleCommand);

    void createRealmRole(String realm, RoleCommand roleCommand);
}
