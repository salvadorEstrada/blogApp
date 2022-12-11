package com.app.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{ 
	public Optional<Rol> findByNombre(String nombre);

}
