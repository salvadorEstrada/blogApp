package com.app.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ComentarioDTO {
    private Long id;
    @NotEmpty(message = "El nombre no debe de estar vaciío o nulo")
    private String nombre;
    @NotEmpty(message="El email no debe de estar vacío o nulo")
    @Email
    private String email;
    @NotEmpty
    @Size(min=10,message="El cuerpo del comentario debe tener al menos 10 caracteres")
    private String cuerpo;

    public ComentarioDTO(){};

    public ComentarioDTO(Long id, String nombre, String email, String cuerpo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.cuerpo = cuerpo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
