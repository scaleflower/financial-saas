package com.fs.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormSchema {
    private String formKey;
    private String formName;
    private List<FormField> fields;
    private Map<String, Object> layout;
    private Map<String, Object> rules;
}
