package repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import entidades.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    // Consulta personalizada para insertar un evento
    @Query("INSERT INTO Evento(nombre, descripcion, observaciones, lugar, duracionMinutos, fechaHoraInicio) " +
           "VALUES (:nombre, :descripcion, :observaciones, :lugar, :duracionMinutos, :fechaHoraInicio)")
    void insertEvento(@Param("nombre") String nombre, @Param("descripcion") String descripcion, 
                      @Param("observaciones") String observaciones, @Param("lugar") String lugar, 
                      @Param("duracionMinutos") Long duracionMinutos, @Param("fechaHoraInicio") String fechaHoraInicio);
    
    // Consulta personalizada para actualizar un evento
    @Query("UPDATE Evento SET nombre = :nombre, descripcion = :descripcion, observaciones = :observaciones, " +
           "lugar = :lugar, duracionMinutos = :duracionMinutos, fechaHoraInicio = :fechaHoraInicio " +
           "WHERE id = :id")
    void updateEvento(@Param("nombre") String nombre, @Param("descripcion") String descripcion, 
                      @Param("observaciones") String observaciones, @Param("lugar") String lugar, 
                      @Param("duracionMinutos") Long duracionMinutos, @Param("fechaHoraInicio") String fechaHoraInicio,
                      @Param("id") Long id);
    
    List<Evento> findByNombre(String nombre);
    List<Evento> findByLugar(String lugar);
    Optional<Evento> findById(Long id);

}
