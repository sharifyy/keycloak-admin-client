package com.example.keycloakapi.mapper;

import com.example.keycloakapi.role.RoleCommand;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleRepresentation roleCommandToRoleRepresentation(RoleCommand roleCommand) {
        RoleRepresentation roleRepresentation = new RoleRepresentation();
//        if(roleCommand.getId()!=null)
//            roleRepresentation.setId(roleCommand.getId());
        roleRepresentation.setName(roleCommand.getName());
        roleRepresentation.setDescription(roleCommand.getDescription());
        return roleRepresentation;
    }
}
