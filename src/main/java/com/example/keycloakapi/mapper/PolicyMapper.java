package com.example.keycloakapi.mapper;

import com.example.keycloakapi.permission.PermissionDTO;
import com.example.keycloakapi.policy.PolicyGroupDTO;
import com.example.keycloakapi.policy.PolicyRoleDTO;
import org.keycloak.representations.idm.authorization.GroupPolicyRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;
import org.springframework.stereotype.Component;

@Component
public final class PolicyMapper {

    public PolicyRepresentation permissionDTOtoPolicyRepresentation(PermissionDTO permission){
        PolicyRepresentation resourcePermissionRepresentation = new PolicyRepresentation();
        if(permission.getId()!=null)
            resourcePermissionRepresentation.setId(permission.getId());
        resourcePermissionRepresentation.setType(permission.getType());
        resourcePermissionRepresentation.setDecisionStrategy(permission.getDecisionStrategy());
        resourcePermissionRepresentation.setDescription(permission.getDescription());
        resourcePermissionRepresentation.setName(permission.getName());
        resourcePermissionRepresentation.setResources(permission.getResources());
        resourcePermissionRepresentation.setPolicies(permission.getPolicies());
        return resourcePermissionRepresentation;
    }

    public RolePolicyRepresentation policyRoleDTOtoRolePolicyRepresentation(PolicyRoleDTO policyRoleDTO) {
        RolePolicyRepresentation policyRepresentation = new RolePolicyRepresentation();
        if(policyRoleDTO.getId()!=null)
            policyRepresentation.setId(policyRoleDTO.getId());
        policyRepresentation.setName(policyRoleDTO.getName());
        policyRepresentation.setType("role");
        policyRepresentation.setDescription(policyRoleDTO.getDescription());
        policyRepresentation.setLogic(policyRoleDTO.getLogic());
        policyRepresentation.setDecisionStrategy(policyRoleDTO.getDecisionStrategy());
        policyRepresentation.setRoles(policyRoleDTO.getRoles());
        return policyRepresentation;
    }

    public GroupPolicyRepresentation policyGroupDTOtoGroupPolicyRepresentation(PolicyGroupDTO policyGroupDTO){
        GroupPolicyRepresentation groupPolicyRepresentation = new GroupPolicyRepresentation();
        if(policyGroupDTO.getId()!=null)
            groupPolicyRepresentation.setId(policyGroupDTO.getId());
        groupPolicyRepresentation.setName(policyGroupDTO.getName());
        groupPolicyRepresentation.setType("group");
        groupPolicyRepresentation.setDescription(policyGroupDTO.getDescription());
        groupPolicyRepresentation.setLogic(policyGroupDTO.getLogic());
        groupPolicyRepresentation.setGroupsClaim(policyGroupDTO.getGroupsClaim());
        groupPolicyRepresentation.setGroups(policyGroupDTO.getGroups());
        return groupPolicyRepresentation;
    }
}
