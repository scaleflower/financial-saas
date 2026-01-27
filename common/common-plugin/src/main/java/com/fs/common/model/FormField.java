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
public class FormField {
    private String key;
    private String label;
    private String type;
    private boolean required;
    private Object defaultValue;
    private List<String> options;
    private Map<String, Object> validation;
    private String placeholder;
    private String description;
}
