package com.example.keycloakapi.group;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
public final class GroupClient implements GroupService {

    private final Keycloak keycloak;

    public GroupClient(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    @Override
    public Response createGroup(String realm, GroupCommand command) {
        final GroupRepresentation group = new GroupRepresentation();
        group.setName(command.getName());
        return keycloak
                .realm(realm)
                .groups()
                .add(group);
    }

    @Override
    public List<GroupRepresentation> getGroups(String realm) {
        return keycloak.realm(realm).groups().groups();
    }


    @Override
    public GroupRepresentation getGroupById(String realm, String groupId) {
        return keycloak
                .realm(realm)
                .groups()
                .group(groupId)
                .toRepresentation();
    }

    @Override
    public void updateGroup(String realm, GroupCommand command) {
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setId(command.getId());
        groupRepresentation.setName(command.getName());
        keycloak.realm(realm)
                .groups()
                .group(command.getId())
                .update(groupRepresentation);
    }

    @Override
    public void deleteGroup(String realm, String groupId) {
        keycloak.realm(realm).groups().group(groupId).remove();
    }

    @Override
    public Response addSubgroup(String realm, String groupId, GroupCommand subgroup) {
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(subgroup.getName());
        return keycloak.realm(realm).groups().group(groupId).subGroup(groupRepresentation);
    }

    @Override
    public List<UserRepresentation> getGroupMembers(String realm, String groupId){
        return keycloak.realm(realm).groups().group(groupId).members();
    }

    @Override
    public void addUserToGroup(String realm, String groupId, UserCommand command){
        keycloak.realm(realm).users().get(command.getId()).joinGroup(groupId);
    }

    @Override
    public void removeUserFromGroup(String realm, String groupId, String userId){
        keycloak.realm(realm).users().get(userId).leaveGroup(groupId);
    }
}
