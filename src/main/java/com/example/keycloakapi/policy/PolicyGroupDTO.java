package com.example.keycloakapi.policy;

import org.keycloak.representations.idm.authorization.DecisionStrategy;
import org.keycloak.representations.idm.authorization.GroupPolicyRepresentation;
import org.keycloak.representations.idm.authorization.Logic;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class PolicyGroupDTO {

    private String id;
    @NotNull
    private String name;
    private String description;
//    private String type;
    private Logic logic = Logic.POSITIVE;
    private String groupsClaim;
    private Set<GroupPolicyRepresentation.GroupDefinition> groups;
//    private DecisionStrategy decisionStrategy = DecisionStrategy.UNANIMOUS;

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

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public String getGroupsClaim() {
        return groupsClaim;
    }

    public void setGroupsClaim(String groupsClaim) {
        this.groupsClaim = groupsClaim;
    }

    public Set<GroupPolicyRepresentation.GroupDefinition> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupPolicyRepresentation.GroupDefinition> groups) {
        this.groups = groups;
    }

//    public DecisionStrategy getDecisionStrategy() {
//        return decisionStrategy;
//    }
//
//    public void setDecisionStrategy(DecisionStrategy decisionStrategy) {
//        this.decisionStrategy = decisionStrategy;
//    }
}
