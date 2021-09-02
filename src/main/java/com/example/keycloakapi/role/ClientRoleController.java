package com.example.keycloakapi.role;

import com.example.keycloakapi.permission.AdminClientResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientRoleController {

    private final RoleService roleService;

    public ClientRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{realm}/client/{id}/roles")
    public List<RoleRepresentation> getClientRoles(@PathVariable String realm, @PathVariable String id) {
        return roleService.getClientRoles(realm, id);
    }

    @GetMapping("/{realm}/client/{id}/roles/{name}")
    public RoleRepresentation getClientRoleByName(@PathVariable String realm, @PathVariable String id, @PathVariable String name) {
        try {
            return roleService.getClientRoleByName(realm, id, name);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }

    @PutMapping("/{realm}/client/{id}/roles")
    public ResponseEntity<AdminClientResponse> updateClientRole(@PathVariable String realm, @PathVariable String id, @RequestBody RoleCommand roleCommand, HttpServletRequest request) {
        try {
            roleService.updateClientRole(realm, id, roleCommand);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    request.getServletPath() + "/" + roleCommand.getName()
                            )
                    );
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }
    }

    @PostMapping("/{realm}/client/{id}/roles")
    public ResponseEntity<AdminClientResponse> createClientRole(@PathVariable String realm, @PathVariable String id, @RequestBody RoleCommand roleCommand, HttpServletRequest request) {
        try {
            roleService.createClientRole(realm, id, roleCommand);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new AdminClientResponse(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase(),
                            request.getServletPath() + "/" + roleCommand.getName()
                    )
            );
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{realm}/client/{id}/roles/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientRole(@PathVariable String realm, @PathVariable String id, @PathVariable String name) {
        roleService.deleteClientRole(realm, id, name);
    }
}
