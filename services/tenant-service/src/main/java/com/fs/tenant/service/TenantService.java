package com.fs.tenant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.common.exception.BusinessException;
import com.fs.common.result.ResultCode;
import com.fs.tenant.entity.Tenant;
import com.fs.tenant.mapper.TenantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TenantService extends ServiceImpl<TenantMapper, Tenant> {

    public Tenant createTenant(Tenant tenant) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getTenant_code, tenant.getTenant_code());
        Long count = baseMapper.selectCount(wrapper);

        if (count > 0) {
            throw new BusinessException(ResultCode.TENANT_CODE_EXIST);
        }

        baseMapper.insert(tenant);
        return tenant;
    }

    public Tenant getTenantById(Long tenantId) {
        Tenant tenant = baseMapper.selectById(tenantId);
        if (tenant == null) {
            throw new BusinessException(ResultCode.TENANT_NOT_FOUND);
        }
        return tenant;
    }

    public List<Tenant> listTenants() {
        return baseMapper.selectList(null);
    }

    public boolean updateTenant(Tenant tenant) {
        int rows = baseMapper.updateById(tenant);
        return rows > 0;
    }

    public boolean deleteTenant(Long tenantId) {
        int rows = baseMapper.deleteById(tenantId);
        return rows > 0;
    }
}
