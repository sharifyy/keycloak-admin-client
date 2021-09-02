package com.example.keycloakapi.permission;

import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PermissionService {

    List<PolicyRepresentation> getAllPermissions(String realm,String clientHexId);

    PolicyRepresentation getPermissionById(String realm, String id, String permissionId);

    Response createPermission(String realm, String clientHexId, PermissionDTO permissionDTO);

    void deletePermission(String realm, String id, String permissionId);

    void updatePermission(String realm, String id, PermissionDTO permissionDTO);
}
