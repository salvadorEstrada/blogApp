package com.app.blog.service;

import com.app.blog.dto.PublicacionDTO;
import com.app.blog.dto.PublicacionRespuesta;
import com.app.blog.exception.ResourceNotFoundException;
import com.app.blog.model.Publicacion;
import com.app.blog.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class PublicacionServiceImpl implements PublicacionService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;
    @Override
    //#######################GUARDAR PUBLICAION############################
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = mapperEntidad(publicacionDTO);
        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);
        //Mostrar la respuesta en Json
        PublicacionDTO publicacionRespuesta = mapperDTO(nuevaPublicacion);
        return publicacionRespuesta;
    }
    //#######################LISTAR PUBLICAIONES############################
    @Override
    public PublicacionRespuesta obtenerTodalsLasPublicaciones(int numPagina, int tamPagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numPagina,tamPagina, sort);
        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);
        List<Publicacion> listaPublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido=listaPublicaciones.stream().map(publicacion->mapperDTO(publicacion)).collect(Collectors.toList());
        //Caracteristicas de la página
        PublicacionRespuesta publicacionRespuesta= new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setTamPagina(publicaciones.getSize());
        publicacionRespuesta.setTotElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long publicacionId) {
        Publicacion obtenerPorId = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        return mapperDTO(obtenerPorId);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long publicacionId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("Publicaion", "id", publicacionId));
        //Si se encontró el id buscado entonces, proceder a actualizar
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());
        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);

        return mapperDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(Long publicacionId) {
        Publicacion publicacion= publicacionRepository.findById(publicacionId)
                .orElseThrow(()-> new ResourceNotFoundException("Publicación", "id", publicacionId));
        publicacionRepository.delete(publicacion);

    }


    //##################################ModelMapper############################################
    private PublicacionDTO mapperDTO(Publicacion publicacion){
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
        return publicacionDTO;
    }

    private Publicacion mapperEntidad(PublicacionDTO publicacionDTO){
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
        return publicacion;
    }



}
