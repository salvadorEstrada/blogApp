package com.app.blog.service;

import com.app.blog.dto.ComentarioDTO;
import com.app.blog.exception.BlogAppException;
import com.app.blog.exception.ResourceNotFoundException;
import com.app.blog.model.Comentario;
import com.app.blog.model.Publicacion;
import com.app.blog.repository.ComentarioRepository;
import com.app.blog.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioImpl implements ComentarioServicio{

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private PublicacionRepository publicacionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        //##########se asignan los comentarios a la publicacion#####################
        Comentario comentario = mapperEntidad(comentarioDTO);
        Publicacion obtenerPublicacionPorId = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        comentario.setPublicacion(obtenerPublicacionPorId);
        //la var nuevoComentario, tiene al nuevo comentario mas la asociación de la publicacion
        Comentario nuevoComentario=comentarioRepository.save(comentario);
        return mapperDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {
        List<Comentario> comentarios =comentarioRepository.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario->mapperDTO(comentario)).collect(Collectors.toList());

    }

    @Override
    public ComentarioDTO obtenerPorComentarioId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion= publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicación", "id", publicacionId));

        Comentario comentario = comentarioRepository.findById(comentarioId)
                //Si el id no existe y tampoco pertenece a otra publicacion
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId));
        //El id existe pero pertenece a otra publicación
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        return mapperDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion= publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicación", "id", publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()->new ResourceNotFoundException("Comentario", "id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());
        Comentario actualizaComentarios = comentarioRepository.save(comentario);
        return mapperDTO(actualizaComentarios);
    }

    @Override
    public void eliminarComentario(Long publicacionId,Long comentarioId) {
        Publicacion publicacion= publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicación", "id", publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(()->new ResourceNotFoundException("Comentario", "id", comentarioId));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        comentarioRepository.delete(comentario);
    }


    //##################################ModelMapper############################################
    private ComentarioDTO mapperDTO(Comentario comentario){
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }

    private Comentario mapperEntidad(ComentarioDTO comentarioDTO){
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        return comentario;
    }
}
