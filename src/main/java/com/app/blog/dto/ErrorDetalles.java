package com.app.blog.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ErrorDetalles {
    private LocalDateTime marcaDeTiempo;
    private String mensaje;
    private String detalles;

    public ErrorDetalles( LocalDateTime marcaDeTiempo, String mensaje, String detalles) {
        this.marcaDeTiempo = marcaDeTiempo;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }

    public LocalDateTime getMarcaDeTiempo() {
        return marcaDeTiempo;
    }

    public void setMarcaDeTiempo(LocalDateTime marcaDeTiempo) {
        this.marcaDeTiempo = marcaDeTiempo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
