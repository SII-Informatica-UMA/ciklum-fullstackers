package entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Hueco {
    
    @Id @GeneratedValue
    private Long duracionMinutos;
    private String fechaHoraInicio;
    private String reglaRecurrencia;

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
