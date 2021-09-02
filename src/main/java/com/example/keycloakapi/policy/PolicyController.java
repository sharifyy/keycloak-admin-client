package com.example.keycloakapi.policy;

import com.example.keycloakapi.permission.AdminClientResponse;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
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
        return policyService.getAlPolicies(realm, id);
    }

    @GetMapping("/{realm}/client/{id}/policies/{policyId}")
    public PolicyRepresentation getPermissions(@PathVariable String realm, @PathVariable String id, @PathVariable String policyId) {
        return policyService.getPolicyById(realm, id, policyId);
    }


    @PostMapping("/{realm}/client/{id}/policies/role-base")
    public ResponseEntity<AdminClientResponse> createRolePolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyRoleDTO policyRoleDTO) {
        Response response = policyService.createRolePolicy(realm, id, policyRoleDTO);
        return ResponseEntity.status(response.getStatus()).body(new AdminClientResponse(response.getStatus(), response.getStatusInfo().getReasonPhrase()));
    }

    @PostMapping("/{realm}/client/{id}/policies/group-base")
    public ResponseEntity<AdminClientResponse> createGroupPolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyGroupDTO policyGroupDTO) {
        Response response = policyService.createGroupPolicy(realm, id, policyGroupDTO);
        return ResponseEntity.status(response.getStatus()).body(new AdminClientResponse(response.getStatus(), response.getStatusInfo().getReasonPhrase()));
    }

    @PutMapping("/{realm}/client/{id}/policies/role-base")
    public void updateRolePolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyRoleDTO policyRoleDTO) {
        policyService.updateRolePolicy(realm, id, policyRoleDTO);
    }

    @PutMapping("/{realm}/client/{id}/policies/group-base")
    public void updateGroupPolicy(@PathVariable String realm, @PathVariable String id, @RequestBody PolicyGroupDTO policyGroupDTO) {
        policyService.updateGroupPolicy(realm, id, policyGroupDTO);
    }

    @DeleteMapping("/{realm}/client/{id}/policies/{policyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePolicy(@PathVariable String realm, @PathVariable String id, @PathVariable String policyId) {
        policyService.deletePolicy(realm, id, policyId);
    }
}
