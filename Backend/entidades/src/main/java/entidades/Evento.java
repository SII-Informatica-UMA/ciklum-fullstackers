package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

import org.springframework.http.ProblemDetail;

import jakarta.persistence.Column;

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
    private Long idEntrenador;


    // Constructor vacío
    public Evento() {
    }

    // Constructor con todos los atributos
    public Evento(Long id, String nombre, String descripcion, String observaciones, String lugar, Long duracionMinutos, String fechaHoraInicio, Long idEntrenador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.lugar = lugar;
        this.duracionMinutos = duracionMinutos;
        this.fechaHoraInicio = fechaHoraInicio;
        this.idEntrenador = idEntrenador;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Evento other = (Evento) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (observaciones == null) {
            if (other.observaciones != null)
                return false;
        } else if (!observaciones.equals(other.observaciones))
            return false;
        if (lugar == null) {
            if (other.lugar != null)
                return false;
        } else if (!lugar.equals(other.lugar))
            return false;
        if (duracionMinutos == null) {
            if (other.duracionMinutos != null)
                return false;
        } else if (!duracionMinutos.equals(other.duracionMinutos))
            return false;
        if (fechaHoraInicio == null) {
            if (other.fechaHoraInicio != null)
                return false;
        } else if (!fechaHoraInicio.equals(other.fechaHoraInicio))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Evento [nombre=" + nombre + ", descripcion=" + descripcion + ", observaciones=" + observaciones
                + ", lugar=" + lugar + ", duracionMinutos=" + duracionMinutos + ", fechaHoraInicio=" + fechaHoraInicio
                + "]";
    }


}
