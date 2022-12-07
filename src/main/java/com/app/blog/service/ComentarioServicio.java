package com.app.blog.service;

import com.app.blog.dto.ComentarioDTO;
import com.app.blog.model.Comentario;

import java.util.List;

public interface ComentarioServicio {
    //para poder comentar hay que pasarle el id de publicacion
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);
    //Cuando busque una publicación me trerá el o los comentarios correspondientes
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId);

    public ComentarioDTO obtenerPorComentarioId(Long publicacionId,Long comentarioId);

    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario);

    public void eliminarComentario(Long publicacionId, Long comentarioId);

}
