package com.example.keycloakapi.role;

import org.keycloak.representations.idm.RoleRepresentation;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleCommand {

    private String id;
    @NotBlank
    private String name;
    private String description;

    /*
    private boolean composite;
    private boolean clientRole;
    private String containerId;
    private String realmName;
    private Map<String, List<String>> attributes = new HashMap<>();
    private RoleRepresentation.Composites composites;
*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/*
    public boolean isComposite() {
        return composite;
    }

    public void setComposite(boolean composite) {
        this.composite = composite;
    }

    public boolean isClientRole() {
        return clientRole;
    }

    public void setClientRole(boolean clientRole) {
        this.clientRole = clientRole;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, List<String>> attributes) {
        this.attributes = attributes;
    }

    public RoleRepresentation.Composites getComposites() {
        return composites;
    }

    public void setComposites(RoleRepresentation.Composites composites) {
        this.composites = composites;
    }

     */
}
