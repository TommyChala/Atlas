package com.Hub.system.enums;

public enum ImportStatus {
    COMPLETE(1),
    FAILED(2),
    RUNNING(3),
    WARNING(4);

    private int numVal;

    ImportStatus (int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
