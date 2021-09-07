package com.example.keycloakapi.permission;

import com.example.keycloakapi.AdminClientResponse;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
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
public final class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    @GetMapping("/{realm}/client/{id}/permissions")
    public List<PolicyRepresentation> getPermissions(@PathVariable String realm, @PathVariable String id) {
        try{
            return permissionService.getAllPermissions(realm, id);
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }


    @GetMapping("/{realm}/client/{id}/permissions/{permissionId}")
    public PolicyRepresentation getPermissions(@PathVariable String realm, @PathVariable String id, @PathVariable String permissionId) {
        try{
            return permissionService.getPermissionById(realm, id, permissionId);
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }


    @PostMapping("/{realm}/client/{id}/permissions")
    public ResponseEntity<AdminClientResponse> createPermission(@PathVariable String realm, @PathVariable String id, @RequestBody PermissionCommand permissionCommand, HttpServletRequest request) {
        try(Response response = permissionService.createPermission(realm, id, permissionCommand)){
            URI location = response.getLocation();
            return ResponseEntity.status(response.getStatus())
                    .body(new AdminClientResponse(
                            response.getStatus(),
                            response.getStatusInfo().getReasonPhrase(),
                            location != null ? request.getServletPath() + location.getPath().substring(location.getPath().lastIndexOf('/')) : null

                    ));
        }
    }


    @PutMapping("/{realm}/client/{id}/permissions")
    public ResponseEntity<AdminClientResponse> updatePermission(@PathVariable String realm, @PathVariable String id, @RequestBody PermissionCommand permissionCommand, HttpServletRequest request) {
        try {
            permissionService.updatePermission(realm, id, permissionCommand);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    request.getServletPath() + "/" + permissionCommand.getId()
                            )
                    );
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }
    }

    @PutMapping("/{realm}/client/{id}/permissions/roles")
    public ResponseEntity<AdminClientResponse> addRolesToPermission(@PathVariable String realm, @PathVariable String id, @RequestBody RolePermissionDTO roles){
        try{
            permissionService.addRolesToPermission(realm,id,roles);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    "/api/v1/"+realm+"/client/"+id+"/permissions/"+roles.getPermissionId()
                            )
                    );
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage());
        }
    }


    @DeleteMapping("/{realm}/client/{id}/permissions/roles")
    public ResponseEntity<AdminClientResponse> removeRolesFromPermission(@PathVariable String realm, @PathVariable String id, @RequestBody RolePermissionDTO roles){
        try{
            permissionService.removeRoles(realm,id,roles);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    "/api/v1/"+realm+"/client/"+id+"/permissions/"+roles.getPermissionId()
                            )
                    );
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage());
        }
    }

    @PutMapping("/{realm}/client/{id}/permissions/groups")
    public ResponseEntity<AdminClientResponse> addGroupsToPermission(@PathVariable String realm, @PathVariable String id, @RequestBody GroupPermissionDTO groups){
        try{
            permissionService.addGroupsToPermission(realm,id,groups);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    "/api/v1/"+realm+"/client/"+id+"/permissions/"+groups.getPermissionId()
                            )
                    );
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{realm}/client/{id}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermission(@PathVariable String realm, @PathVariable String id, @PathVariable String permissionId) {
        permissionService.deletePermission(realm, id, permissionId);
    }

}
