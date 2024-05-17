package repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import entidades.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
       
       
       Optional<List<Evento>> findByIdEntrenador(Long idEntrenador);
       List<Evento> findByNombre(String nombre);
       List<Evento> findByLugar(String lugar);
       Optional<Evento> findById(Long id);
       Optional<Evento> findByIdEntrenadorIdElemento(Long idEntrenador, Long id);

}
