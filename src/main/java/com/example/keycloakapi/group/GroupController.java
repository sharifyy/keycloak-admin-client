package com.example.keycloakapi.group;

import com.example.keycloakapi.permission.AdminClientResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{realm}/groups")
    public List<GroupRepresentation> getGroups(@PathVariable String realm) {
        return groupService.getGroups(realm);
    }

    @GetMapping("/{realm}/groups/{groupId}")
    public GroupRepresentation getGroupById(@PathVariable String realm, @PathVariable String groupId) {
        try {
            return groupService.getGroupById(realm, groupId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }

    @PostMapping("/{realm}/groups")
    public ResponseEntity<AdminClientResponse> createGroup(@PathVariable String realm, @RequestBody GroupCommand group, HttpServletRequest request) {
        try (Response response = groupService.createGroup(realm, group)) {
            URI location = response.getLocation();
            return ResponseEntity.status(response.getStatus())
                    .body(new AdminClientResponse(response.getStatus(),
                            response.getStatusInfo().getReasonPhrase(),
                            location != null ? request.getServletPath() + location.getPath().substring(location.getPath().lastIndexOf('/')) : null)
                    );
        }
    }

    @PutMapping("/{realm}/groups")
    public ResponseEntity<AdminClientResponse> updateGroup(@PathVariable String realm, @RequestBody GroupCommand group, HttpServletRequest request) {
        try {
            groupService.updateGroup(realm, group);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    request.getServletPath() + "/" + group.getId()
                            )
                    );
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{realm}/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroupById(@PathVariable String realm, @PathVariable String groupId) {
        groupService.deleteGroup(realm, groupId);
    }

    @GetMapping("/{realm}/groups/{groupId}/users")
    public List<UserRepresentation> getGroupMembers(@PathVariable String realm, @PathVariable String groupId){
        return groupService.getGroupMembers(realm,groupId);
    }

    @PostMapping("/{realm}/groups/{groupId}/users")
    public ResponseEntity<AdminClientResponse> addUserToGroup(@PathVariable String realm, @PathVariable String groupId, @RequestBody UserCommand userCommand, HttpServletRequest request) {
        try {
            groupService.addUserToGroup(realm, groupId, userCommand);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AdminClientResponse(
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.getReasonPhrase(),
                            request.getServletPath())
                    );
        } catch (NotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{realm}/groups/{groupId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUserFromGroup(@PathVariable String realm, @PathVariable String groupId, @PathVariable String userId) {
        groupService.removeUserFromGroup(realm, groupId, userId);
    }

}
