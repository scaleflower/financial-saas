package com.fs.common.plugin;

import com.fs.common.enums.TransType;
import com.fs.common.model.FormSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class TransTypePluginManager {

    private final Map<String, TransTypePlugin> plugins = new ConcurrentHashMap<>();

    public TransTypePluginManager(List<TransTypePlugin> pluginList) {
        for (TransTypePlugin plugin : pluginList) {
            registerPlugin(plugin);
        }
        log.info("Initialized {} trans-type plugins", plugins.size());
    }

    public void registerPlugin(TransTypePlugin plugin) {
        if (plugin.isEnabled()) {
            plugins.put(plugin.getPluginCode(), plugin);
            log.info("Registered trans-type plugin: {}", plugin.getPluginCode());
        }
    }

    public TransTypePlugin getPlugin(String code) {
        return plugins.get(code);
    }

    public FormSchema getFormSchema(String transTypeCode) {
        TransTypePlugin plugin = getPlugin(transTypeCode);
        return plugin != null ? plugin.getFormSchema() : null;
    }

    public Object validateFormData(String transTypeCode, Object formData) {
        TransTypePlugin plugin = getPlugin(transTypeCode);
        return plugin != null ? plugin.validateFormData(formData) : null;
    }

    public Object calculateTotal(String transTypeCode, Object formData) {
        TransTypePlugin plugin = getPlugin(transTypeCode);
        return plugin != null ? plugin.calculateTotal(formData) : null;
    }
}
