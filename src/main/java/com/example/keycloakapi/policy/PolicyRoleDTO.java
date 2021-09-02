package com.example.keycloakapi.policy;

import org.keycloak.representations.idm.authorization.DecisionStrategy;
import org.keycloak.representations.idm.authorization.Logic;
import org.keycloak.representations.idm.authorization.RolePolicyRepresentation;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class PolicyRoleDTO {

    private String id;
    @NotNull
    private String name;
    private String description;
//    private String type;
    private Logic logic = Logic.POSITIVE;
    @NotNull
    private Set<RolePolicyRepresentation.RoleDefinition> roles;

    private DecisionStrategy decisionStrategy = DecisionStrategy.UNANIMOUS;

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

//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getType() {
//        return "role";
//    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setRoles(Set<RolePolicyRepresentation.RoleDefinition> roles) {
        this.roles = roles;
    }

    public Set<RolePolicyRepresentation.RoleDefinition> getRoles() {
        return roles;
    }

    public DecisionStrategy getDecisionStrategy() {
        return decisionStrategy;
    }

    public void setDecisionStrategy(DecisionStrategy decisionStrategy) {
        this.decisionStrategy = decisionStrategy;
    }
}