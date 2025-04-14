package com.iphone_store.iphone_store.dto;

public class OptionItem {
    private Integer id;
    private String name;

    public OptionItem(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

