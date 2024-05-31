package fullstackers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EventoDTO { 
    @JsonProperty("id")
    private Long id;
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String lugar;
    private Long duracionMinutos;
    private String fechaHoraInicio;
    private Long idCliente;
    private String tipo;

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventoDTO eventoDTO = (EventoDTO) o;
    return Objects.equals(id, eventoDTO.id) &&
            Objects.equals(nombre, eventoDTO.nombre) &&
            Objects.equals(descripcion, eventoDTO.descripcion) &&
            Objects.equals(observaciones, eventoDTO.observaciones) &&
            Objects.equals(lugar, eventoDTO.lugar) &&
            Objects.equals(duracionMinutos, eventoDTO.duracionMinutos) &&
            Objects.equals(fechaHoraInicio, eventoDTO.fechaHoraInicio) &&
            Objects.equals(idCliente, eventoDTO.idCliente) &&
            Objects.equals(tipo, eventoDTO.tipo);
}

@Override
public int hashCode() {
    return Objects.hash(id, nombre, descripcion, observaciones, lugar, duracionMinutos, fechaHoraInicio, idCliente, tipo);
}}
