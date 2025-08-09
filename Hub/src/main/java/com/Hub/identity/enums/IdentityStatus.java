package com.Hub.identity.enums;

public enum IdentityStatus {
    ACTIVE(1),
    INACTIVE(2),
    TERMINATED(3),
    LEAVE(4);

    private final int id;

    IdentityStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static IdentityStatus fromId(int id) {
        for (IdentityStatus status : values()) {
            if (status.id == id) return status;
        }
        throw new IllegalArgumentException("Invalid IdentityStatus id: " + id);
    }
}
