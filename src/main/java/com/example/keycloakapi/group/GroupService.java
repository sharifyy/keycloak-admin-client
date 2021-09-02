package com.example.keycloakapi.group;

import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.util.List;

public interface GroupService {
    Response createGroup(String realm, GroupCommand command);

    List<GroupRepresentation> getGroups(String realm);

    GroupRepresentation getGroupById(String realm, String groupId);

    void updateGroup(String realm, GroupCommand command);

    void deleteGroup(String realm, String groupId);

    Response addSubgroup(String realm, String groupId, GroupCommand subgroup);

    List<UserRepresentation> getGroupMembers(String realm, String groupId);

    void addUserToGroup(String realm, String groupId, UserCommand command);

    void removeUserFromGroup(String realm, String groupId, String userId);
}
