package com.fs.transservice.controller;

import com.fs.common.result.Result;
import com.fs.transservice.entity.Trans;
import com.fs.transservice.entity.TransItem;
import com.fs.transservice.service.TransService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trans")
@RequiredArgsConstructor
public class TransController {

    private final TransService transService;

    @PostMapping
    public Result<Trans> create(@RequestBody Map<String, Object> request) {
        Trans trans = new Trans();
        List<TransItem> items = List.of();
        Trans created = transService.createTrans(trans, items);
        return Result.success(created);
    }

    @PostMapping("/{transId}/submit")
    public Result<Trans> submit(@PathVariable Long transId) {
        Trans trans = transService.submitTrans(transId);
        return Result.success(trans);
    }

    @GetMapping("/{transId}")
    public Result<Trans> getById(@PathVariable Long transId) {
        Trans trans = transService.getTransById(transId);
        return Result.success(trans);
    }

    @GetMapping
    public Result<List<Trans>> list(@RequestParam Long tenantId) {
        List<Trans> transList = transService.listTrans(tenantId);
        return Result.success(transList);
    }

    @GetMapping("/schema/{transTypeCode}")
    public Result<Object> getSchema(@PathVariable String transTypeCode) {
        Object schema = transService.getFormSchema(transTypeCode);
        return Result.success(schema);
    }
}
