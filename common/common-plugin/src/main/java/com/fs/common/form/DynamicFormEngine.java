package com.fs.common.form;

import com.alibaba.fastjson2.JSON;
import com.fs.common.model.FormSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DynamicFormEngine {

    private final Map<String, FormSchema> schemaCache = new HashMap<>();

    public void registerSchema(String formKey, FormSchema schema) {
        schemaCache.put(formKey, schema);
        log.info("Registered form schema: {}", formKey);
    }

    public FormSchema getSchema(String formKey) {
        return schemaCache.get(formKey);
    }

    public Map<String, Object> renderForm(String formKey) {
        FormSchema schema = getSchema(formKey);
        if (schema == null) {
            return null;
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("schema", schema);
        result.put("formData", buildFormDataTemplate(schema));
        return result;
    }

    private Map<String, Object> buildFormDataTemplate(FormSchema schema) {
        Map<String, Object> template = new HashMap<>();
        schema.getFields().forEach(field -> {
            if (field.getDefaultValue() != null) {
                template.put(field.getKey(), field.getDefaultValue());
            }
        });
        return template;
    }

    public boolean validate(String formKey, Map<String, Object> formData) {
        FormSchema schema = getSchema(formKey);
        if (schema == null) {
            return false;
        }
        
        for (var field : schema.getFields()) {
            if (field.isRequired() && formData.get(field.getKey()) == null) {
                log.warn("Required field missing: {}", field.getKey());
                return false;
            }
        }
        
        return true;
    }
}
