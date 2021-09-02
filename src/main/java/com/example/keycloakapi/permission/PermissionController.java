package com.example.keycloakapi.permission;

import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
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
        return permissionService.getAllPermissions(realm, id);
    }


    @GetMapping("/{realm}/client/{id}/permissions/{permissionId}")
    public PolicyRepresentation getPermissions(@PathVariable String realm, @PathVariable String id, @PathVariable String permissionId) {
        return permissionService.getPermissionById(realm, id, permissionId);
    }


    @PostMapping("/{realm}/client/{id}/permissions")
    public ResponseEntity<AdminClientResponse> createPermission(@PathVariable String realm, @PathVariable String id, @RequestBody PermissionDTO permissionDTO) {
        Response response = permissionService.createPermission(realm, id, permissionDTO);
        return ResponseEntity.status(response.getStatus()).body(new AdminClientResponse(response.getStatus(), response.getStatusInfo().getReasonPhrase()));
    }


    @PutMapping("/{realm}/client/{id}/permissions")
    public void updatePermission(@PathVariable String realm, @PathVariable String id, @RequestBody PermissionDTO permissionDTO) {
        permissionService.updatePermission(realm, id, permissionDTO);
    }


    @DeleteMapping("/{realm}/client/{id}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermission(@PathVariable String realm, @PathVariable String id, @PathVariable String permissionId) {
        permissionService.deletePermission(realm, id, permissionId);
    }

}
