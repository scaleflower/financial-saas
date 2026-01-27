package com.fs.transservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fs.common.enums.TransType;
import com.fs.common.plugin.TransTypePluginManager;
import com.fs.transservice.entity.Trans;
import com.fs.transservice.entity.TransItem;
import com.fs.transservice.mapper.TransItemMapper;
import com.fs.transservice.mapper.TransMapper;
import com.fs.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransService extends ServiceImpl<TransMapper, Trans> {

    private final TransMapper transMapper;
    private final TransItemMapper transItemMapper;
    private final TransTypePluginManager pluginManager;

    public Trans createTrans(Trans trans, List<TransItem> items) {
        String transCode = StringUtils.generateCode("TR");
        trans.setTrans_code(transCode);
        trans.setState("DRAFT");
        trans.setCreated_at(LocalDateTime.now());
        transMapper.insert(trans);

        for (TransItem item : items) {
            item.setTrans_id(trans.getTrans_id());
            item.setTenant_id(trans.getTenant_id());
            item.setCreated_at(LocalDateTime.now());
            transItemMapper.insert(item);
        }

        log.info("Created trans: {} with {} items", transCode, items.size());
        return trans;
    }

    public Trans submitTrans(Long transId) {
        Trans trans = transMapper.selectById(transId);
        trans.setState("SUBMITTED");
        trans.setSubmit_time(LocalDateTime.now());
        transMapper.updateById(trans);
        return trans;
    }

    public Trans getTransById(Long transId) {
        return transMapper.selectById(transId);
    }

    public List<Trans> listTrans(Long tenantId) {
        return transMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Trans>()
                        .eq(Trans::getTenant_id, tenantId)
                        .orderByDesc(Trans::getCreated_at)
        );
    }

    public Object getFormSchema(String transTypeCode) {
        return pluginManager.getFormSchema(transTypeCode);
    }
}
