package com.example.keycloakapi.permission;

import com.example.keycloakapi.mapper.PolicyMapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.admin.client.resource.PoliciesResource;
import org.keycloak.admin.client.resource.PolicyResource;
import org.keycloak.representations.idm.authorization.*;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

        PolicyResource policyResource = client(realm, clientHexId)
                .policies()
                .policy(permissionId);

        PolicyRepresentation policy = policyResource.toRepresentation();

        if (!policy.getType().equals("scope") && !policy.getType().equals("resource")) {
            throw new NotFoundException("no permission found");
        }

        Set<String> policies = policyResource.associatedPolicies().stream().map(AbstractPolicyRepresentation::getName).collect(Collectors.toSet());
        policy.setPolicies(policies);

        if (policy.getType().equals("scope")) {
            Set<String> scopes = policyResource.scopes().stream().map(ScopeRepresentation::getName).collect(Collectors.toSet());
            policy.setScopes(scopes);
            return policy;
        }

        Set<String> resources = policyResource.resources().stream().map(ResourceRepresentation::getName).collect(Collectors.toSet());
        policy.setResources(resources);

        return policy;
    }

    @Override
    public Response createPermission(String realm, String clientHexId, PermissionCommand permissionCommand) {
        return client(realm, clientHexId)
                .policies()
                .create(policyMapper.permissionDTOtoPolicyRepresentation(permissionCommand));
    }

    @Override
    public void deletePermission(String realm, String clientHexId, String permissionId) {
        client(realm, clientHexId)
                .policies().policy(permissionId).remove();
    }

    @Override
    public void updatePermission(String realm, String id, PermissionCommand permissionCommand) {
        client(realm, id)
                .policies()
                .policy(permissionCommand.getId())
                .update(policyMapper.permissionDTOtoPolicyRepresentation(permissionCommand));
    }


    @Override
    public void addGroupsToPermission(String realm, String clientHexId, GroupPermissionDTO groupPermissionDTO) {
        final PoliciesResource policiesResource = client(realm, clientHexId).policies();
        final PolicyResource policyResource = policiesResource.policy(groupPermissionDTO.getPermissionId());
        PolicyRepresentation permission = policyResource.toRepresentation();

        if (!permission.getType().equals("scope") && !permission.getType().equals("resource")) {
            throw new NotFoundException("no permission found");
        }

        Set<String> associatedPolicies = policyResource.associatedPolicies().stream()
                .filter(policyRepresentation -> policyRepresentation.getType().equals("group"))
                .map(AbstractPolicyRepresentation::getName)
                .collect(Collectors.toSet());

        for (GroupPermissionDTO.Group group : groupPermissionDTO.getGroups()) {
            if (!associatedPolicies.contains("Policy_" + group.getName())) {
                policyExists(policiesResource, "Policy_" + group.getName())
                        .ifPresentOrElse(existingPolicy -> {
                                    associatedPolicies.add(existingPolicy.getId());
                                    updatePermission(policiesResource, permission, associatedPolicies);
                                },
                                () -> createGroupPolicy(policiesResource, group).ifPresent(
                                        createdPolicy -> {
                                            associatedPolicies.add(createdPolicy.getId());
                                            updatePermission(policiesResource, permission, associatedPolicies);
                                        }
                                )
                        );
            }
        }
    }

    @Override
    public void addRolesToPermission(String realm, String clientHexId, RolePermissionDTO rolePermissionDTO) {

        final PoliciesResource policiesResource = client(realm, clientHexId).policies();
        final PolicyResource policyResource = policiesResource.policy(rolePermissionDTO.getPermissionId());
        PolicyRepresentation permission = policyResource.toRepresentation();

        if (!permission.getType().equals("scope") && !permission.getType().equals("resource")) {
            throw new NotFoundException("no permission found");
        }

        Set<String> associatedPolicies = policyResource.associatedPolicies().stream()
                .filter(policyRepresentation -> policyRepresentation.getType().equals("role"))
                .map(AbstractPolicyRepresentation::getName)
                .collect(Collectors.toSet());

        for (RolePermissionDTO.Role role : rolePermissionDTO.getRoles()) {
            if (!associatedPolicies.contains("Policy_" + role.getName())) {
                policyExists(policiesResource, "Policy_" + role.getName())
                        .ifPresentOrElse(existingPolicy -> {
                                    associatedPolicies.add(existingPolicy.getId());
                                    updatePermission(policiesResource, permission, associatedPolicies);
                                },
                                () -> createRolePolicy(policiesResource, role).ifPresent(
                                        createdPolicy -> {
                                            associatedPolicies.add(createdPolicy.getId());
                                            updatePermission(policiesResource, permission, associatedPolicies);
                                        }
                                )
                        );
            }
        }
    }

    private void updatePermission(final PoliciesResource policiesResource, PolicyRepresentation permission, Set<String> associatedPolicies) {
        permission.setPolicies(associatedPolicies);
        policiesResource.policy(permission.getId()).update(permission);
    }

    private Optional<PolicyRepresentation> createRolePolicy(final PoliciesResource policiesResource, RolePermissionDTO.Role role) {
        RolePolicyRepresentation rolePolicyRepresentation = new RolePolicyRepresentation();
        rolePolicyRepresentation.setName("Policy_" + role.getName());
        rolePolicyRepresentation.setRoles(Set.of(new RolePolicyRepresentation.RoleDefinition(role.getId(), true)));

        try (Response response = policiesResource.role().create(rolePolicyRepresentation)) {
            if (response.getStatus() == 201) {
                return policyExists(policiesResource, rolePolicyRepresentation.getName());
            } else {
                throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
            }
        }
    }

    private Optional<PolicyRepresentation> createGroupPolicy(final PoliciesResource policiesResource, GroupPermissionDTO.Group group) {
        GroupPolicyRepresentation groupPolicyRepresentation = new GroupPolicyRepresentation();
        groupPolicyRepresentation.setName("Policy_" + group.getName());
        groupPolicyRepresentation.setGroupsClaim("groups");
        groupPolicyRepresentation.setGroups(Set.of(new GroupPolicyRepresentation.GroupDefinition(group.getId(), false)));

        try (Response response = policiesResource.group().create(groupPolicyRepresentation)) {
            if (response.getStatus() == 201) {
                return policyExists(policiesResource, groupPolicyRepresentation.getName());
            } else {
                throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
            }
        }
    }

    private Optional<PolicyRepresentation> policyExists(final PoliciesResource policiesResource, String policyName) {
        try {
            return Optional.ofNullable(policiesResource.findByName(policyName));
        } catch (NotFoundException ex) {
            return Optional.empty();
        }
    }


    @Override
    public void removeRoles(String realm, String clientHexId, RolePermissionDTO rolePermissionDTO) {
        PolicyResource policyResource = client(realm, clientHexId)
                .policies()
                .policy(rolePermissionDTO.getPermissionId());

        PolicyRepresentation permission = policyResource.toRepresentation();

        if (!permission.getType().equals("scope") && !permission.getType().equals("resource")) {
            throw new NotFoundException("no permission found");
        }
        Set<String> policyNames = rolePermissionDTO.getRoles()
                .stream()
                .map(RolePermissionDTO.Role::getName)
                .map(name -> "Policy_" + name)
                .collect(Collectors.toSet());

        Set<String> associatedPolicies = policyResource.associatedPolicies()
                .stream()
                .filter(policyRepresentation -> policyRepresentation.getType().equals("role"))
                .filter(policyRepresentation -> !policyNames.contains(policyRepresentation.getName()))
                .map(AbstractPolicyRepresentation::getId)
                .collect(Collectors.toSet());

        permission.setPolicies(associatedPolicies);
        client(realm, clientHexId)
                .policies()
                .policy(permission.getId())
                .update(permission);

    }

    private AuthorizationResource client(String realm, String clientHexId) {
        return keycloak.realm(realm)
                .clients().get(clientHexId)
                .authorization();
    }
}
