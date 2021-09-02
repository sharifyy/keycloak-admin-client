package com.example.keycloakapi.mapper;

import com.example.keycloakapi.role.RoleCommand;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleRepresentation roleCommandToRoleRepresentation(RoleCommand roleCommand) {
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleCommand.getName());
        roleRepresentation.setDescription(roleCommand.getDescription());
//        roleRepresentation.setComposite(roleCommand.isComposite());
//        roleRepresentation.setClientRole(roleCommand.isClientRole());
//        roleRepresentation.setComposites(roleCommand.getComposites());
//        roleRepresentation.setContainerId(roleCommand.getContainerId());
        return roleRepresentation;
    }
}
