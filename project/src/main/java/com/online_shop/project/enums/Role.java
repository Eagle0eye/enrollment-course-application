package com.online_shop.project.enums;

public enum Role {
    ADMIN,MODERATOR;

    @Override
    public String toString() {
        switch (this){
            case ADMIN -> {
                return "admin";
            }
            case MODERATOR -> {
                return "moderator";
            }
            default -> {
                return "";
            }
        }

    }
}
