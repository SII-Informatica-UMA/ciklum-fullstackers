package repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entidades.Hueco;

public interface HuecoRepository extends JpaRepository<Hueco, Long>{
        
        // Consulta personalizada para insertar un hueco
        @Query("INSERT INTO Hueco(duracionMinutos, fechaHoraInicio, reglaRecurrencia) " +
            "VALUES (:duracionMinutos, :fechaHoraInicio, :reglaRecurrencia)")
        void insertHueco(@Param("duracionMinutos") Long duracionMinutos, @Param("fechaHoraInicio") String fechaHoraInicio, 
                        @Param("reglaRecurrencia") String reglaRecurrencia);
        
        // Consulta personalizada para actualizar un hueco
        @Query("UPDATE Hueco SET duracionMinutos = :duracionMinutos, fechaHoraInicio = :fechaHoraInicio, " +
            "reglaRecurrencia = :reglaRecurrencia WHERE id = :id")
        void updateHueco(@Param("duracionMinutos") Long duracionMinutos, @Param("fechaHoraInicio") String fechaHoraInicio, 
                        @Param("reglaRecurrencia") String reglaRecurrencia, @Param("id") Long id);
        
        List<Hueco> findByDuracionMinutos(Long duracionMinutos);
        List<Hueco> findByFechaHoraInicio(String fechaHoraInicio);
}
