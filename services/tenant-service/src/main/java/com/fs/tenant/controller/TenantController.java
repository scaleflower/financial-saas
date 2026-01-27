package com.fs.tenant.controller;

import com.fs.common.result.PageResult;
import com.fs.common.result.Result;
import com.fs.tenant.entity.Tenant;
import com.fs.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public Result<Tenant> create(@RequestBody Tenant tenant) {
        Tenant created = tenantService.createTenant(tenant);
        return Result.success(created);
    }

    @GetMapping("/{tenantId}")
    public Result<Tenant> getById(@PathVariable Long tenantId) {
        Tenant tenant = tenantService.getTenantById(tenantId);
        return Result.success(tenant);
    }

    @GetMapping
    public Result<List<Tenant>> list() {
        List<Tenant> tenants = tenantService.listTenants();
        return Result.success(tenants);
    }

    @PutMapping("/{tenantId}")
    public Result<Void> update(@PathVariable Long tenantId, @RequestBody Tenant tenant) {
        tenant.setTenant_id(tenantId);
        tenantService.updateTenant(tenant);
        return Result.success();
    }

    @DeleteMapping("/{tenantId}")
    public Result<Void> delete(@PathVariable Long tenantId) {
        tenantService.deleteTenant(tenantId);
        return Result.success();
    }
}
