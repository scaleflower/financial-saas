package com.fs.transservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fs.transservice.entity.Trans;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransMapper extends BaseMapper<Trans> {
}
