package com.example.curso.exemple.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EmailDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    @NotEmpty
    @Email(message = "Email invalido")
    private String email;

    public EmailDTO(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
