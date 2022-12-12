package com.app.blog.dto;

//Despues de hacer las pruebas,  con el passwors creado manualmente, se automatiza creando éste DTO
//Después se crea el AuthControlador

public class LoginDTO { 
	
	private String usernameOrEmail; 
	
	private String password; 
	
	
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
	
	
	

}
