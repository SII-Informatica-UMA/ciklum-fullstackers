package fullstackers.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstackers.entidades.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
       
       
       Optional<List<Evento>> findByIdEntrenador(Long idEntrenador);
       List<Evento> findByNombre(String nombre);
       List<Evento> findByLugar(String lugar);
       Optional<Evento> findById(Long id);
       Optional<Evento> findByIdEntrenadorOrId(Long idEntrenador, Long id);

}
