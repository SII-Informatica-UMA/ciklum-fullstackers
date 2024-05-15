package dtos;

import lombok.*;
import entidades.Evento;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EventoDTO { 

    private Long id;
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String lugar;
    private Long duracionMinutos;
    private String fechaHoraInicio;
}
