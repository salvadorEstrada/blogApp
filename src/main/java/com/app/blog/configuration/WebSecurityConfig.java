package com.app.blog.configuration;

import com.app.blog.security.JwtAuthenticationEntryPoint;
import com.app.blog.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.app.blog.security.CustomUserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//PARA AGRGAR SEGURIDAD, SE CREA ESTA CLASE WebSecurityConfig
//PASO2: SE CREAN LAS ENTIDADES ROL Y USUARIO,  y se poblan las tablas con información de Usuario y roles 
//PASO3: SE GNERA UN HASH MANUALMENTE CON PASSWORD ENCODER (clase Util), para llenar la tabla usuario en el campo password
//PARA AUTOMATIZAR EL PROCESO ANTERIOR, SE CREA UN loginDTO Y EL CONTROLADOR AuthControlador

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig { 
	
	@Autowired 
	public CustomUserDetailsService userDetailsService;
    //Una vez que se han progrmado las clases del paquete security
    /*JwtAuthenticationEntryPoint, JwtAuthenticationFilter, JWTAuthResponseDTO, JwtokenProvider*/
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }


	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//errores de autorizacion
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**")
                .permitAll() 
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build(); 
    }
	
	@Bean
    public PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return  http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)//llama al método para que tome efecto el usuario que se ha creado en memoria
                .passwordEncoder(passwordEncoder())//llama al metodo passwordEncoder que esta arriba
                .and()
                .build();

    }
    
    
}
