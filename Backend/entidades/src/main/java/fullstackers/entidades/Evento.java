package fullstackers.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.util.Objects;

/*import java.util.Objects;

import org.springframework.http.ProblemDetail;

import jakarta.persistence.Column;
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "EVENTO") 


public class Evento {

    @Id @GeneratedValue
    private Long id;
    private String nombre;
    private String descripcion;
    private String observaciones;
    private String lugar;
    private Long duracionMinutos;
    private String fechaHoraInicio;
    private Long idCliente;
    private Long idEntrenador;


    // Constructor vacío
    public Evento() {
    }

    // Constructor con todos los atributos
    public Evento(Long id, String nombre, String descripcion, String observaciones, String lugar, Long duracionMinutos
    , String fechaHoraInicio, Long idEntrenador, Long idCliente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.lugar = lugar;
        this.duracionMinutos = duracionMinutos;
        this.fechaHoraInicio = fechaHoraInicio;
        this.idEntrenador = idEntrenador;
        this.idCliente = idCliente; 
    }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Evento evento = (Evento) o;
    return Objects.equals(id, evento.id) &&
            Objects.equals(nombre, evento.nombre) &&
            Objects.equals(descripcion, evento.descripcion) &&
            Objects.equals(observaciones, evento.observaciones) &&
            Objects.equals(lugar, evento.lugar) &&
            Objects.equals(duracionMinutos, evento.duracionMinutos) &&
            Objects.equals(fechaHoraInicio, evento.fechaHoraInicio) &&
            Objects.equals(idEntrenador, evento.idEntrenador) &&
            Objects.equals(idCliente, evento.idCliente);
}

@Override
public int hashCode() {
    return Objects.hash(id, nombre, descripcion, observaciones, lugar, duracionMinutos, fechaHoraInicio, idEntrenador, idCliente);
}
}
