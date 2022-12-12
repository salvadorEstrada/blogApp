package com.app.blog.controller;

import com.app.blog.dto.PublicacionDTO;
import com.app.blog.dto.PublicacionRespuesta;
import com.app.blog.service.PublicacionService;
import com.app.blog.util.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public PublicacionRespuesta listPublicaciones(
            @RequestParam(value="pageNo", defaultValue = AppConstantes.NUM_DE_PAGINA_POR_DEFECTO,required = false) int numPagina,
            @RequestParam(value="pageSize",defaultValue = AppConstantes.TAM_DE_PAGINA_POR_DEFECTO, required = false) int tamPagina,
            @RequestParam(value="sortBy", defaultValue = AppConstantes.ORDENAR_ID_POR_DEFECTO,required = false) String ordenarPor,
            @RequestParam(value="sortDir", defaultValue =AppConstantes.ORDENAR_POR_DEFECTO,required = false) String sortDir){
        return publicacionService.obtenerTodalsLasPublicaciones(numPagina,tamPagina, ordenarPor,sortDir);
    }

    @GetMapping("/{publicacionId}")
    public ResponseEntity<PublicacionDTO> getPublicacionPorId(@PathVariable Long publicacionId){
        return new ResponseEntity<>(publicacionService.obtenerPublicacionPorId(publicacionId), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionService.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
   @PutMapping("/{publicacionId}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,@PathVariable Long publicacionId){
    return  new ResponseEntity<>(publicacionService.actualizarPublicacion(publicacionDTO,publicacionId), HttpStatus.OK);
   }

    @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{publicacionId}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable Long publicacionId){
        publicacionService.eliminarPublicacion(publicacionId);
      return new ResponseEntity<>("Eliminacion exitosa",HttpStatus.OK);
   }
}
