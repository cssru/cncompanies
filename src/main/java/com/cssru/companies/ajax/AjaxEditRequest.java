package com.cssru.companies.ajax;

public class AjaxEditRequest {
    private long id;
    private String fieldName;
    private String value;

    public long getId() {
        return id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
