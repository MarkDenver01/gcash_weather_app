package com.denver.weather_gcash_app.domain.model.login;

import android.util.Patterns;

public class LoginModel {
    private final String email;
    private final String password;

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        if (email == null) {
            return "";
        }
        return email;
    }

    public String getPassword() {
        if (password == null) {
            return "";
        }
        return password;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }

    public LoginModel clear() {
        return new LoginModel(null, null);
    }
}
