package com.online_shop.project.enums;

public enum RequestStatus {
    PENDING,ACCEPTED, REFUSED;

    @Override
    public String toString() {
        switch (this){
            case PENDING -> {return "pending";}
            case REFUSED -> {return "refused";}
            case ACCEPTED -> {return "accepted";}
            default -> {return "";}
        }

    }
}
