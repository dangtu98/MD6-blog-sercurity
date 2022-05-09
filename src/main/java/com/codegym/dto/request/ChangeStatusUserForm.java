package com.codegym.dto.request;

public class ChangeStatusUserForm {
    private String statusUser;

    public ChangeStatusUserForm() {
    }

    public ChangeStatusUserForm(String statusUser) {
        this.statusUser = statusUser;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }
}
