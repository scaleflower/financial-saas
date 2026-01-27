package com.fs.common.plugin.impl;

import com.fs.common.enums.TransType;
import com.fs.common.model.FormField;
import com.fs.common.model.FormSchema;
import com.fs.common.plugin.TransTypePlugin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneralTransTypePlugin implements TransTypePlugin {
    @Override
    public String getPluginCode() {
        return TransType.GENERAL.getCode();
    }
    @Override
    public String getPluginName() {
        return TransType.GENERAL.getName();
    }
    @Override
    public FormSchema getFormSchema() {
        return FormSchema.builder()
                .formKey("general-trans")
                .formName("普通报销表单")
                .fields(List.of(
                        FormField.builder().key("transDate").label("报销日期").type("date").required(true).build(),
                        FormField.builder().key("amount").label("报销金额").type("number").required(true).build(),
                        FormField.builder().key("description").label("报销说明").type("textarea").required(true).build()
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
