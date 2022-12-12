package com.app.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByEmail(String email); 
	
	//Devuelve un true or false
    public Optional<Usuario> findByUsernameOrEmail(String username, String email);
    public Optional<Usuario> findByUsername(String username);
    //Existe o no existe
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);

}
