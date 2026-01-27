package com.fs.orgservice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.orgservice.entity.Org;
import com.fs.orgservice.mapper.OrgMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrgService extends ServiceImpl<OrgMapper, Org> {

    public Org getOrgById(Long orgId) {
        return baseMapper.selectById(orgId);
    }

    public List<Org> listOrgs(Long tenantId) {
        return baseMapper.selectTree(tenantId);
    }

    public List<Org> getChildOrgs(Long parentId, Long tenantId) {
        LambdaQueryWrapper<Org> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Org::getParent_org_id, parentId)
               .eq(Org::getTenant_id, tenantId)
               .orderByAsc(Org::getSort_order);
        return baseMapper.selectList(wrapper);
    }

    public Org createOrg(Org org) {
        if (org.getParent_org_id() != null) {
            Org parent = getOrgById(org.getParent_org_id());
            org.setOrg_level(parent.getOrg_level() + 1);
            org.setParent_path(parent.getParent_path() + "/" + parent.getOrg_id());
        } else {
            org.setOrg_level(1);
            org.setParent_path("/" + org.getOrg_id());
        }
        baseMapper.insert(org);
        return org;
    }

    public boolean updateOrg(Org org) {
        int rows = baseMapper.updateById(org);
        return rows > 0;
    }

    public boolean deleteOrg(Long orgId) {
        LambdaQueryWrapper<Org> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Org::getParent_org_id, orgId);
        Long count = baseMapper.selectCount(wrapper);
        
        if (count > 0) {
            throw new RuntimeException("该组织下有子组织，无法删除");
        }
        
        int rows = baseMapper.deleteById(orgId);
        return rows > 0;
    }
}
