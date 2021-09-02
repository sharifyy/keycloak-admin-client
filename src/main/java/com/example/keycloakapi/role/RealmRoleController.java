package com.example.keycloakapi.role;


import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RealmRoleController {

    private final RoleService roleService;

    public RealmRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{realm}/roles")
    public List<RoleRepresentation> getRealmRoles(@PathVariable String realm) {
        return roleService.getRealmRoles(realm);
    }

    @GetMapping("/{realm}/roles/{name}")
    public RoleRepresentation getRealmRoleByName(@PathVariable String realm, @PathVariable String name) {
        return roleService.getRealmRoleByName(realm, name);
    }

    @PutMapping("/{realm}/roles")
    public void updateRealmRole(@PathVariable String realm, @RequestBody RoleCommand roleCommand) {

        try {
            roleService.updateRealmRole(realm, roleCommand);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }
    }

    @PostMapping("/{realm}/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRealmRole(@PathVariable String realm, @RequestBody RoleCommand roleCommand) {
        try {
            roleService.createRealmRole(realm, roleCommand);
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{realm}/roles/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRealmRole(@PathVariable String realm, @PathVariable String name) {
        roleService.deleteRealmRole(realm, name);
    }

    @ExceptionHandler({NotFoundException.class})
    public void handleNotFoundException(NotFoundException ex) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }

    @ExceptionHandler({ClientErrorException.class})
    public ResponseStatusException handleNotFoundException(ClientErrorException ex) {
        return new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
    }
}
