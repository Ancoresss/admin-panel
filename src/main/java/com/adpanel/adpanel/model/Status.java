package com.adpanel.adpanel.model;

public enum Status {
    PENDING("В ожидании"),
    ACCEPTED("Принятно"),
    REJECTED("Отказано");

    String translate;

    Status(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }
}
