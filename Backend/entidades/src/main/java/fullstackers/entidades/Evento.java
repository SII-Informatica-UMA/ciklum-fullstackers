package fullstackers.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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


}