package dtos;

import lombok.*;
import entidades.Evento;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoNuevoDTO {
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String lugar;
    private Long duracionMinutos;
    private String fechaHoraInicio;
    private String tipo;
}
