package entidades;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HUECO") 
public class Hueco {
    
    @Id @GeneratedValue
    @Column(name = "DURACION_MINUTOS" , nullable = false, length = 50)
    private Long duracionMinutos;
    @Column(name = "FECHA_INICIO" , nullable = false, length = 50)
    private String fechaHoraInicio;
    @Column(name = "RECURRENCIA" , nullable = false, length = 50)
    private String reglaRecurrencia;

// Constructor vacío
    public Hueco() {
    }

    // Constructor con todos los atributos
    public Hueco(Long duracionMinutos, String fechaHoraInicio, String reglaRecurrencia) {
        this.duracionMinutos = duracionMinutos;
        this.fechaHoraInicio = fechaHoraInicio;
        this.reglaRecurrencia = reglaRecurrencia;
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

    public String getReglaRecurrencia() {
        return reglaRecurrencia;
    }

    public void setReglaRecurrencia(String reglaRecurrencia) {
        this.reglaRecurrencia = reglaRecurrencia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((duracionMinutos == null) ? 0 : duracionMinutos.hashCode());
        result = prime * result + ((fechaHoraInicio == null) ? 0 : fechaHoraInicio.hashCode());
        result = prime * result + ((reglaRecurrencia == null) ? 0 : reglaRecurrencia.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hueco other = (Hueco) obj;
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
        if (reglaRecurrencia == null) {
            if (other.reglaRecurrencia != null)
                return false;
        } else if (!reglaRecurrencia.equals(other.reglaRecurrencia))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Hueco [duracionMinutos=" + duracionMinutos + ", fechaHoraInicio=" + fechaHoraInicio + ", reglaRecurrencia="
                + reglaRecurrencia + "]";
    }
}
