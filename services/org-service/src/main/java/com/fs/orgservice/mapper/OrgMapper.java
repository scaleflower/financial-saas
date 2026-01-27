package com.fs.orgservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fs.orgservice.entity.Org;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrgMapper extends BaseMapper<Org> {
    List<Org> selectTree(Long tenantId);
}
