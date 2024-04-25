package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

import jakarta.persistence.Column;

@Entity
@Table(name = "EVENTO") 

public class Evento {

    @Id @GeneratedValue
private Long id;

@Column(name="NOMBRE", nullable=false, length=50)
private String nombre;

@Column(name="DESCRIPCION", nullable=false, length=50)
private String descripcion;

@Column(name="OBSERVACIONES", nullable=false, length=50)
private String observaciones;

@Column(name="LUGAR", nullable=false, length=50)
private String lugar;

@Column(name="DURACION_MINUTOS", nullable=false)
private Long duracionMinutos;

@Column(name="FECHA_HORA_INICIO", nullable=false, length=50)
private String fechaHoraInicio;


    // Constructor vacío
    public Evento() {
    }

    // Constructor con todos los atributos
    public Evento(Long id, String nombre, String descripcion, String observaciones, String lugar, Long duracionMinutos, String fechaHoraInicio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.lugar = lugar;
        this.duracionMinutos = duracionMinutos;
        this.fechaHoraInicio = fechaHoraInicio;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Long getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Long duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, duracionMinutos, fechaHoraInicio, lugar, nombre, observaciones);
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
