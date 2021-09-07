package com.example.keycloakapi.policy;

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
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/{realm}/client/{id}/policies")
    public List<PolicyRepresentation> getPolicies(@PathVariable String realm, @PathVariable String id) {
        try {
            return policyService.getAlPolicies(realm, id);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }

    @GetMapping("/{realm}/client/{id}/policies/{policyId}")
    public PolicyRepresentation getPermissions(@PathVariable String realm, @PathVariable String id, @PathVariable String policyId) {
        try {
            return policyService.getPolicyById(realm, id, policyId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        }
    }


    @PostMapping("/{realm}/client/{id}/policies/role-base")
    public ResponseEntity<AdminClientResponse> createRolePolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyRoleCommand policyRoleCommand, HttpServletRequest request) {
        try(Response response = policyService.createRolePolicy(realm, id, policyRoleCommand)){
            URI location = response.getLocation();
            return ResponseEntity.status(response.getStatus())
                    .body(new AdminClientResponse(
                            response.getStatus(),
                            response.getStatusInfo().getReasonPhrase(),
                            location != null ? request.getServletPath() + location.getPath().substring(location.getPath().lastIndexOf('/')) : null
                    ));
        }
    }

    @PostMapping("/{realm}/client/{id}/policies/group-base")
    public ResponseEntity<AdminClientResponse> createGroupPolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyGroupCommand policyGroupCommand, HttpServletRequest request) {
        try(Response response = policyService.createGroupPolicy(realm, id, policyGroupCommand)){
            URI location = response.getLocation();
            return ResponseEntity.status(response.getStatus())
                    .body(new AdminClientResponse(
                            response.getStatus(),
                            response.getStatusInfo().getReasonPhrase(),
                            location != null ? request.getServletPath() + location.getPath().substring(location.getPath().lastIndexOf('/')) : null
                    ));
        }
    }

    @PutMapping("/{realm}/client/{id}/policies/role-base")
    public ResponseEntity<AdminClientResponse> updateRolePolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyRoleCommand policyRoleCommand, HttpServletRequest request) {
        try{
            policyService.updateRolePolicy(realm, id, policyRoleCommand);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    request.getServletPath() + "/" + policyRoleCommand.getId()
                            )
                    );
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }
    }

    @PutMapping("/{realm}/client/{id}/policies/group-base")
    public ResponseEntity<AdminClientResponse> updateGroupPolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyGroupCommand policyGroupCommand, HttpServletRequest request) {
        try{
            policyService.updateGroupPolicy(realm, id, policyGroupCommand);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AdminClientResponse(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase(),
                                    request.getServletPath() + "/" + policyGroupCommand.getId()
                            )
                    );
        }catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        } catch (ClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        }

    }

    @DeleteMapping("/{realm}/client/{id}/policies/{policyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePolicy(@PathVariable String realm, @PathVariable String id, @PathVariable String policyId) {
        policyService.deletePolicy(realm, id, policyId);
    }
}
