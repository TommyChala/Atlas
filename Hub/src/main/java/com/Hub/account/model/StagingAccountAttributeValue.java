package com.Hub.account.model;

public class StagingAccountAttributeValue {

    private final String accountRef;

    private final String attributeRef;

    private final String valueRaw;

    public StagingAccountAttributeValue(String accountRef, String attributeRef, String valueRaw) {
        this.accountRef = accountRef;
        this.attributeRef = attributeRef;
        this.valueRaw = valueRaw;
    }

    public Object[] toObjectArray() {
        return new Object[] {
                accountRef,
                attributeRef,
                valueRaw
        };
    }
}
