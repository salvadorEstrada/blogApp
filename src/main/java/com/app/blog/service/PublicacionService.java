package com.app.blog.service;

import com.app.blog.dto.PublicacionDTO;
import com.app.blog.dto.PublicacionRespuesta;
import com.app.blog.model.Publicacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublicacionService {
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerTodalsLasPublicaciones(int numPagina, int tamPagina,String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorId(Long publicacionId);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long publicacionId);

    public void eliminarPublicacion(Long publicacionId);

}
