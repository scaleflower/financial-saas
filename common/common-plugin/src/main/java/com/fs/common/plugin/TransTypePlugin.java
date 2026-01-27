package com.fs.common.plugin;

import com.fs.common.enums.TransType;
import com.fs.common.model.FormSchema;

public interface TransTypePlugin {
    String getPluginCode();
    String getPluginName();
    FormSchema getFormSchema();
    Object validateFormData(Object formData);
    Object calculateTotal(Object formData);
    default boolean isEnabled() {
        return true;
    }
}
