package com.example.keycloakapi.policy;

import com.example.keycloakapi.permission.PermissionDTO;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PolicyService {

    List<PolicyRepresentation> getAlPolicies(String realm, String clientHexId);

    PolicyRepresentation getPolicyById(String realm, String id, String policyId);

    Response createRolePolicy(String realm, String clientHexId, PolicyRoleDTO policyRoleDTO);

    void deletePolicy(String realm, String id, String policyId);

    void updateRolePolicy(String realm, String id, PolicyRoleDTO policyRoleDTO);

    Response createGroupPolicy(String realm, String id, PolicyGroupDTO policyGroupDTO);

    void updateGroupPolicy(String realm, String clientHexId, PolicyGroupDTO policyGroupDTO);
}
