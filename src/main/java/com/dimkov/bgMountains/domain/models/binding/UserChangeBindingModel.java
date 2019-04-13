package com.dimkov.bgMountains.domain.models.binding;

import javax.validation.constraints.NotEmpty;

public class UserChangeBindingModel {
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    @NotEmpty
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotEmpty
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotEmpty
    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
