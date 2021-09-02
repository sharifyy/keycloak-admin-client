package com.example.keycloakapi.role;

import com.example.keycloakapi.mapper.RoleMapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class RoleClient implements RoleService{

    private final Keycloak keycloak;
    private final RoleMapper roleMapper;

    public RoleClient(Keycloak keycloak, RoleMapper roleMapper) {
        this.keycloak = keycloak;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleRepresentation> getClientRoles(String realm, String clientHexId){
        return keycloak
                .realm(realm)
                .clients()
                .get(clientHexId)
                .roles()
                .list();
    }

    @Override
    public List<RoleRepresentation> getRealmRoles(String realm) {
        return keycloak
                .realm(realm)
                .roles()
                .list();
    }


    @Override
    public RoleRepresentation getClientRoleByName(String realm, String clientHexId, String roleName){
        return keycloak
                .realm(realm)
                .clients()
                .get(clientHexId)
                .roles()
                .get(roleName)
                .toRepresentation();
    }

    @Override
    public RoleRepresentation getRealmRoleByName(String realm, String roleName){
        return keycloak
                .realm(realm)
                .roles()
                .get(roleName)
                .toRepresentation();
    }


    @Override
    public void deleteRealmRole(String realm, String roleName){
        keycloak.realm(realm)
                .roles()
                .deleteRole(roleName);
    }

    @Override
    public void deleteClientRole(String realm, String clientHexId, String roleName){
        keycloak
                .realm(realm)
                .clients()
                .get(clientHexId)
                .roles()
                .deleteRole(roleName);
    }

    @Override
    public void createClientRole(String realm, String clientHexId, RoleCommand roleCommand){
        keycloak
                .realm(realm)
                .clients()
                .get(clientHexId)
                .roles()
                .create(roleMapper.roleCommandToRoleRepresentation(roleCommand));
    }

    @Override
    public void createRealmRole(String realm, RoleCommand roleCommand) {
        keycloak
                .realm(realm)
                .roles()
                .create(roleMapper.roleCommandToRoleRepresentation(roleCommand));
    }

    @Override
    public void updateClientRole(String realm, String clientHexId, RoleCommand roleCommand){
        keycloak
                .realm(realm)
                .clients()
                .get(clientHexId)
                .roles()
                .get(roleCommand.getName())
                .update(roleMapper.roleCommandToRoleRepresentation(roleCommand));
    }

    @Override
    public void updateRealmRole(String realm, RoleCommand roleCommand) {
        keycloak
                .realm(realm)
                .roles()
                .get(roleCommand.getName())
                .update(roleMapper.roleCommandToRoleRepresentation(roleCommand));
    }
}
