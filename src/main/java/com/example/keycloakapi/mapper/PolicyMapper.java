package com.example.keycloakapi.mapper;

import com.example.keycloakapi.permission.PermissionCommand;
import com.example.keycloakapi.policy.PolicyGroupCommand;
import com.example.keycloakapi.policy.PolicyRoleCommand;
import org.keycloak.representations.idm.authorization.GroupPolicyRepresentation;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;
import org.springframework.stereotype.Component;

@Component
public final class PolicyMapper {

    public PolicyRepresentation permissionDTOtoPolicyRepresentation(PermissionCommand permission){
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

    public RolePolicyRepresentation policyRoleDTOtoRolePolicyRepresentation(PolicyRoleCommand policyRoleCommand) {
        RolePolicyRepresentation policyRepresentation = new RolePolicyRepresentation();
        if(policyRoleCommand.getId()!=null)
            policyRepresentation.setId(policyRoleCommand.getId());
        policyRepresentation.setName(policyRoleCommand.getName());
        policyRepresentation.setType("role");
        policyRepresentation.setDescription(policyRoleCommand.getDescription());
        policyRepresentation.setLogic(policyRoleCommand.getLogic());
        policyRepresentation.setDecisionStrategy(policyRoleCommand.getDecisionStrategy());
        policyRepresentation.setRoles(policyRoleCommand.getRoles());
        return policyRepresentation;
    }

    public GroupPolicyRepresentation policyGroupDTOtoGroupPolicyRepresentation(PolicyGroupCommand policyGroupCommand){
        GroupPolicyRepresentation groupPolicyRepresentation = new GroupPolicyRepresentation();
        if(policyGroupCommand.getId()!=null)
            groupPolicyRepresentation.setId(policyGroupCommand.getId());
        groupPolicyRepresentation.setName(policyGroupCommand.getName());
        groupPolicyRepresentation.setType("group");
        groupPolicyRepresentation.setDescription(policyGroupCommand.getDescription());
        groupPolicyRepresentation.setLogic(policyGroupCommand.getLogic());
        groupPolicyRepresentation.setGroupsClaim(policyGroupCommand.getGroupsClaim());
        groupPolicyRepresentation.setGroups(policyGroupCommand.getGroups());
        return groupPolicyRepresentation;
    }
}
