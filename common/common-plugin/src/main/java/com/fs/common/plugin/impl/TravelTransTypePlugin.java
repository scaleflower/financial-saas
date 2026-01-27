package com.fs.common.plugin.impl;

import com.fs.common.enums.TransType;
import com.fs.common.model.FormField;
import com.fs.common.model.FormSchema;
import com.fs.common.plugin.TransTypePlugin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TravelTransTypePlugin implements TransTypePlugin {
    @Override
    public String getPluginCode() {
        return TransType.TRAVEL.getCode();
    }
    @Override
    public String getPluginName() {
        return TransType.TRAVEL.getName();
    }
    @Override
    public FormSchema getFormSchema() {
        return FormSchema.builder()
                .formKey("travel-trans")
                .formName("差旅报销表单")
                .fields(List.of(
                        FormField.builder().key("startDate").label("开始日期").type("date").required(true).build(),
                        FormField.builder().key("endDate").label("结束日期").type("date").required(true).build(),
                        FormField.builder().key("destination").label("出差地点").type("text").required(true).build()
                ))
                .build();
    }
    @Override
    public Object validateFormData(Object formData) {
        return formData;
    }
    @Override
    public Object calculateTotal(Object formData) {
        return formData;
    }
}
