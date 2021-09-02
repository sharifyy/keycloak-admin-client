package com.example.keycloakapi.permission;

import com.example.keycloakapi.mapper.PolicyMapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class PermissionsClient implements PermissionService {

    private final Keycloak keycloak;
    private final PolicyMapper policyMapper;

    public PermissionsClient(Keycloak keycloak, PolicyMapper policyMapper) {
        this.keycloak = keycloak;
        this.policyMapper = policyMapper;
    }

    @Override
    public List<PolicyRepresentation> getAllPermissions(String realm, String clientHexId) {
        return client(realm, clientHexId)
                .policies()
                .policies()
                .stream()
                .filter(policy -> policy.getType().equals("scope") || policy.getType().equals("resource"))
                .collect(Collectors.toList());
    }


    @Override
    public PolicyRepresentation getPermissionById(String realm, String clientHexId, String permissionId) {
        try {
            PolicyRepresentation policy = client(realm, clientHexId)
                    .policies()
                    .policy(permissionId)
                    .toRepresentation();
            if (policy.getType().equals("scope") || policy.getType().equals("resource")) {
                return policy;
            }
            throw new NotFoundException("no permission found");
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }

    @Override
    public Response createPermission(String realm, String clientHexId, PermissionDTO permissionDTO) {
        return client(realm, clientHexId)
                .policies()
                .create(policyMapper.permissionDTOtoPolicyRepresentation(permissionDTO));
    }

    @Override
    public void deletePermission(String realm, String clientHexId, String permissionId) {
        client(realm, clientHexId)
                .policies().policy(permissionId).remove();
    }

    @Override
    public void updatePermission(String realm, String id, PermissionDTO permissionDTO) {
        client(realm, id)
                .policies()
                .policy(permissionDTO.getId())
                .update(policyMapper.permissionDTOtoPolicyRepresentation(permissionDTO));
    }

    private AuthorizationResource client(String realm, String clientHexId) {
        return keycloak.realm(realm)
                .clients().get(clientHexId)
                .authorization();
    }
}
