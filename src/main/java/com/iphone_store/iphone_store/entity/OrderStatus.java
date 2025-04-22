package com.iphone_store.iphone_store.entity;

public enum OrderStatus {
    PENDING_CONFIRM("Pending confirm"),
    CONFIRMED("Confirmed"),
    REJECTED("Rejected"),
    CANCELLED("Cancelled");

    private final String label;
    OrderStatus(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

}


