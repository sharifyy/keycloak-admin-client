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
        try{
            return roleService.getRealmRoleByName(realm, name);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }

    @PutMapping("/{realm}/roles")
    public ResponseEntity<AdminClientResponse> updateRealmRole(@PathVariable String realm, @RequestBody RoleCommand roleCommand, HttpServletRequest request) {
        try {
            roleService.updateRealmRole(realm, roleCommand);
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

    @PostMapping("/{realm}/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdminClientResponse> createRealmRole(@PathVariable String realm, @RequestBody RoleCommand roleCommand, HttpServletRequest request) {
        try {
            roleService.createRealmRole(realm, roleCommand);
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

    @DeleteMapping("/{realm}/roles/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRealmRole(@PathVariable String realm, @PathVariable String name) {
        roleService.deleteRealmRole(realm, name);
    }

//    @ExceptionHandler({NotFoundException.class})
//    public void handleNotFoundException(NotFoundException ex) {
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
//    }
//
//    @ExceptionHandler({ClientErrorException.class})
//    public ResponseStatusException handleNotFoundException(ClientErrorException ex) {
//        return new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
//    }
}
