package com.Hub.account.model;

public class StagingAccountModel {

    private final String accountRef;

    public StagingAccountModel(String accountRef) {
        this.accountRef = accountRef;
    }

    public Object[] toObjectArray() {
        return new Object[]{
                accountRef
        };
    }
}