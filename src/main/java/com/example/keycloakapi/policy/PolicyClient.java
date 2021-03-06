package com.example.keycloakapi.policy;

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
public final class PolicyClient implements PolicyService {

    private final Keycloak keycloak;
    private final PolicyMapper policyMapper;

    public PolicyClient(Keycloak keycloak, PolicyMapper policyMapper) {
        this.keycloak = keycloak;
        this.policyMapper = policyMapper;
    }

    @Override
    public List<PolicyRepresentation> getAlPolicies(String realm, String clientHexId) {
        return client(realm, clientHexId)
                .policies().policies()
                .stream()
                .filter(policy -> !policy.getType().equals("scope") && !policy.getType().equals("resource"))
                .collect(Collectors.toList());
    }

    @Override
    public PolicyRepresentation getPolicyById(String realm, String clientHexId, String policyId) {
        try {
            PolicyRepresentation policy = client(realm, clientHexId)
                    .policies()
                    .policy(policyId)
                    .toRepresentation();
            if (!policy.getType().equals("scope") && !policy.getType().equals("resource")) {
                return policy;
            }
            throw new NotFoundException("no policy found");
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }


    @Override
    public Response createRolePolicy(String realm, String clientHexId, PolicyRoleCommand policyRoleCommand) {
        return client(realm, clientHexId)
                .policies()
                .role()
                .create(policyMapper.policyRoleDTOtoRolePolicyRepresentation(policyRoleCommand));
    }

    @Override
    public Response createGroupPolicy(String realm, String clientHexId, PolicyGroupCommand policyGroupCommand) {
        return client(realm, clientHexId)
                .policies()
                .group()
                .create(policyMapper.policyGroupDTOtoGroupPolicyRepresentation(policyGroupCommand));
    }

    @Override
    public void deletePolicy(String realm, String id, String policyId) {
        client(realm, id)
                .policies()
                .policy(policyId)
                .remove();
    }

    @Override
    public void updateRolePolicy(String realm, String clientHexId, PolicyRoleCommand policyRoleCommand) {
        client(realm, clientHexId)
                .policies()
                .role().findById(policyRoleCommand.getId())
                .update(policyMapper.policyRoleDTOtoRolePolicyRepresentation(policyRoleCommand));
    }

    @Override
    public void updateGroupPolicy(String realm, String clientHexId, PolicyGroupCommand policyGroupCommand) {
        client(realm, clientHexId)
                .policies()
                .group().findById(policyGroupCommand.getId())
                .update(policyMapper.policyGroupDTOtoGroupPolicyRepresentation(policyGroupCommand));
    }

    private AuthorizationResource client(String realm, String clientHexId) {
        return keycloak.realm(realm)
                .clients().get(clientHexId)
                .authorization();
    }
}
