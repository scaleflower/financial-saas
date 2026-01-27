package com.fs.orgservice.controller;

import com.fs.common.result.Result;
import com.fs.orgservice.entity.Org;
import com.fs.orgservice.service.OrgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgs")
@RequiredArgsConstructor
public class OrgController {

    private final OrgService orgService;

    @GetMapping("/{orgId}")
    public Result<Org> getById(@PathVariable Long orgId) {
        Org org = orgService.getOrgById(orgId);
        return Result.success(org);
    }

    @GetMapping("/tree")
    public Result<List<Org>> getTree(@RequestParam Long tenantId) {
        List<Org> tree = orgService.listOrgs(tenantId);
        return Result.success(tree);
    }

    @GetMapping("/children")
    public Result<List<Org>> getChildren(@RequestParam Long parentId, @RequestParam Long tenantId) {
        List<Org> children = orgService.getChildOrgs(parentId, tenantId);
        return Result.success(children);
    }

    @PostMapping
    public Result<Org> create(@RequestBody Org org) {
        Org created = orgService.createOrg(org);
        return Result.success(created);
    }

    @PutMapping("/{orgId}")
    public Result<Void> update(@PathVariable Long orgId, @RequestBody Org org) {
        org.setOrg_id(orgId);
        orgService.updateOrg(org);
        return Result.success();
    }

    @DeleteMapping("/{orgId}")
    public Result<Void> delete(@PathVariable Long orgId) {
        orgService.deleteOrg(orgId);
        return Result.success();
    }
}
