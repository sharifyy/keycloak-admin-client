package com.example.keycloakapi.policy;

import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PolicyService {

    List<PolicyRepresentation> getAlPolicies(String realm, String clientHexId);

    PolicyRepresentation getPolicyById(String realm, String id, String policyId);

    Response createRolePolicy(String realm, String clientHexId, PolicyRoleCommand policyRoleCommand);

    void deletePolicy(String realm, String id, String policyId);

    void updateRolePolicy(String realm, String id, PolicyRoleCommand policyRoleCommand);

    Response createGroupPolicy(String realm, String id, PolicyGroupCommand policyGroupCommand);

    void updateGroupPolicy(String realm, String clientHexId, PolicyGroupCommand policyGroupCommand);
}
