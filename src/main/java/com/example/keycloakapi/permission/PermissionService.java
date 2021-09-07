package com.example.keycloakapi.permission;

import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PermissionService {

    List<PolicyRepresentation> getAllPermissions(String realm,String clientHexId);

    PolicyRepresentation getPermissionById(String realm, String id, String permissionId);

    Response createPermission(String realm, String clientHexId, PermissionCommand permissionCommand);

    void deletePermission(String realm, String id, String permissionId);

    void updatePermission(String realm, String id, PermissionCommand permissionCommand);

    void addRolesToPermission(String realm, String id, RolePermissionDTO roles);

    void removeRoles(String realm, String id, RolePermissionDTO roles);

    void addGroupsToPermission(String realm, String id, GroupPermissionDTO roles);
}
