package com.app.blog.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity

@Table(name="usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})
public class Usuario { 
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    @NotEmpty
    @Size(min=2, message="El nombre del username debe tener al menos 2 caracteres")
    private String username;

    private String email;

    private String password;
    //Con CascadeType.ALL,  don't working out, instead of set MERGE
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name="usuarios_roles", joinColumns = @JoinColumn(name="usuario_id", referencedColumnName="id"),inverseJoinColumns = @JoinColumn(name="rol_id", referencedColumnName = "id"))
    private Set<Rol> roles= new HashSet<>(); 
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
		

}
