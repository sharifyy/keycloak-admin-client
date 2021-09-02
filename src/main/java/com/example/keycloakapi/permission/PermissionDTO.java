package com.example.keycloakapi.permission;

import org.keycloak.representations.idm.authorization.DecisionStrategy;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class PermissionDTO {

    private String id;
    @NotNull
    private String name;
    private String description;
    private String type;
    private Set<String> policies;
    private Set<String> resources;
    private Set<String> scopes;
    private DecisionStrategy decisionStrategy = DecisionStrategy.UNANIMOUS;
    private String resourceType;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getPolicies() {
        return policies;
    }

    public void setPolicies(Set<String> policies) {
        this.policies = policies;
    }

    public Set<String> getResources() {
        return resources;
    }

    public void setResources(Set<String> resources) {
        this.resources = resources;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public DecisionStrategy getDecisionStrategy() {
        return decisionStrategy;
    }

    public void setDecisionStrategy(DecisionStrategy decisionStrategy) {
        this.decisionStrategy = decisionStrategy;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}