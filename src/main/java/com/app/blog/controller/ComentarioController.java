package com.app.blog.controller;

import com.app.blog.dto.ComentarioDTO;
import com.app.blog.service.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioController {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable Long publicacionId){
        return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable Long publicacionId, @PathVariable Long id){
        ComentarioDTO comentarioDTO = comentarioServicio.obtenerPorComentarioId(publicacionId,id);
        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable Long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        return  new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId,comentarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> actualizarComentarios( @PathVariable Long publicacionId, @PathVariable Long id, @Valid @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioServicio.actualizarComentario(publicacionId,id,comentarioDTO), HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> eliminarComentarios(@PathVariable Long publicacionId, @PathVariable Long id){
        comentarioServicio.eliminarComentario(publicacionId, id);
        return new ResponseEntity<>("Eliminación Exotosa", HttpStatus.OK);
    }

}
