package com.app.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.blog.model.Rol;
import com.app.blog.model.Usuario;
import com.app.blog.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {  
	
	
	@Autowired 
	public UsuarioRepository usuarioRepository;
    //Cargar los ususarios desde la BD
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		Usuario  usuario = usuarioRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(()-> new UsernameNotFoundException("Usuario o email no encontrado: "+usernameOrEmail));
				
		return new User(usuario.getEmail(), usuario.getPassword(), mapearRoles(usuario.getRoles()));
	} 
	//Roles que son asociados al usuario
	private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
		return roles.stream().map(rol->new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
	}

}
