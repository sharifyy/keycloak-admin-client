package com.example.keycloakapi.role;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientRoleController {

    private final RoleService roleService;

    public ClientRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{realm}/client/{id}/roles")
    public List<RoleRepresentation> getClientRoles(@PathVariable String realm,@PathVariable String id){
        return roleService.getClientRoles(realm,id);
    }

    @GetMapping("/{realm}/client/{id}/roles/{name}")
    public RoleRepresentation getClientRoleByName(@PathVariable String realm,@PathVariable String id,@PathVariable String name){
        return roleService.getClientRoleByName(realm,id,name);
    }

    @PutMapping("/{realm}/client/{id}/roles")
    public void updateClientRole(@PathVariable String realm,@PathVariable String id,@RequestBody RoleCommand roleCommand){
        roleService.updateClientRole(realm,id,roleCommand);
    }

    @PostMapping("/{realm}/client/{id}/roles")
    public void createClientRole(@PathVariable String realm,@PathVariable String id,@RequestBody RoleCommand roleCommand){
        roleService.createClientRole(realm,id,roleCommand);
    }

    @DeleteMapping("/{realm}/client/{id}/roles/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientRole(@PathVariable String realm,@PathVariable String id,@PathVariable String name){
        roleService.deleteClientRole(realm,id,name);
    }
}
