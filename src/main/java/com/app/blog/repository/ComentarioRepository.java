package com.app.blog.repository;

import com.app.blog.dto.ComentarioDTO;
import com.app.blog.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    public List<Comentario> findByPublicacionId(Long publicacionId);
}
