package com.fs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransType {

    GENERAL("GENERAL", "普通报销"),
    TRAVEL("TRAVEL", "差旅报销"),
    TEAM_BUILDING("TEAM_BUILDING", "团建报销"),
    DOMESTIC_TRANSPORT("DOMESTIC_TRANSPORT", "国内交通费"),
    PERSONAL_COMM("PERSONAL_COMM", "个人通讯费"),
    BUSINESS_ACTIVITY("BUSINESS_ACTIVITY", "业务活动费");

    private final String code;
    private final String name;

    public static TransType fromCode(String code) {
        for (TransType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return GENERAL;
    }
}
