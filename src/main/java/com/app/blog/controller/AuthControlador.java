package com.app.blog.controller;

import java.security.Principal;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.app.blog.dto.LoginDTO;
import com.app.blog.dto.RegistroUsuariosDTO;
import com.app.blog.model.Rol;
import com.app.blog.model.Usuario;
import com.app.blog.repository.RolRepository;
import com.app.blog.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador { 
	

	@Autowired 
	private AuthenticationManager authenticationManager;//interface de spring   
	
	@Autowired 
	private UsuarioRepository usuarioRepository; 
	
	@Autowired 
	private RolRepository rolRepository; 
	
	@Autowired 
	private PasswordEncoder passwordEncoder;

	@GetMapping("/obtenrUsuarioLogeado")
	public String currentUserName(Authentication authentication){
		return authentication.getName();
	}
	
	 
	@PostMapping("/inicioSesion")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO ){
		Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);//Establecer la authenticacion
		return new ResponseEntity<>("Ha iniciado sessión con exito",HttpStatus.OK);
		} 
	
	@PostMapping("/registrar") 
	public ResponseEntity<?> registrarUsuario(@RequestBody  RegistroUsuariosDTO resgistroUsuariosDTO){ 
		if(usuarioRepository.existsByUsername(resgistroUsuariosDTO.getUsername())){
			return new ResponseEntity<>("Ese uauario ya existe ", HttpStatus.BAD_REQUEST);
		} 
		
		if(usuarioRepository.existsByEmail(resgistroUsuariosDTO.getEmail())){ 
			
			return new ResponseEntity<>("Ese email de usuario ya existe ", HttpStatus.BAD_REQUEST);
		}
		//Si no exite el usuario entonces crearlo
		Usuario usuario = new Usuario(); 
		usuario.setNombre(resgistroUsuariosDTO.getNombre());
		usuario.setUsername(resgistroUsuariosDTO.getUsername()); 
		usuario.setEmail(resgistroUsuariosDTO.getEmail());
		usuario.setPassword(passwordEncoder.encode(resgistroUsuariosDTO.getPassword()));  
		
		Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
		usuario.setRoles(Collections.singleton(roles));
		usuarioRepository.save(usuario); 
		
		return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
		
		
	}
	
		
}
	


