package servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import controladores.EventoRest;
import controladores.Mapper;
import dtos.EventoDTO;
import entidades.Evento;
import repositories.EventoRepository;
import servicios.excepciones.EventoNoEncontradoException;

@Service
@Transactional
public class EventoServicio {
	
	private EventoRepository eventoRepo;
	
	public EventoServicio(EventoRepository eventoRepo) {
		this.eventoRepo=eventoRepo;
	}

	public List<Evento> obtenerEventos() {
		return eventoRepo.findAll();
	}

	public Optional<Evento> obtenerEvento(Long id, Long idEntrenador) {
		return this.eventoRepo.findByIdEntrenadorIdElemento(idEntrenador, id);	
	}


	public Evento actualizarEvento(Evento evento) {
		if (eventoRepo.existsById(evento.getId())) {
			return eventoRepo.save(evento);
		} else {
			throw new EventoNoEncontradoException();
		}
	}

	public void eliminarEvento(Long id) {
		var evento = eventoRepo.findById(id);
		if (evento.isPresent()) {
			eventoRepo.deleteById(id);
		} else {
			throw new EventoNoEncontradoException();
		}
	}

	public Long crearEvento(Evento evento) {
		evento.setId(null);
		return eventoRepo.save(evento).getId();
	}

	public Optional<List<Evento>> getDisponibilidad(Long idEntrenador) {
        return eventoRepo.findByIdEntrenador(idEntrenador);
    }

	
}
