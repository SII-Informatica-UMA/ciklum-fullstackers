package controladores;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import controladores.Mapper;
import dtos.EventoDTO;
import dtos.EventoNuevoDTO;
import entidades.Evento;
import servicios.EventoServicio;
import servicios.excepciones.EventoExistenteException;
import servicios.excepciones.EventoNoEncontradoException;


@RestController
@CrossOrigin
@RequestMapping("/calendario")
public class EventoRest {

	private EventoServicio servicio;
	
	public EventoRest(EventoServicio servicio) {
		this.servicio = servicio;
	}


	@GetMapping("/{idEntrenador}/{idElemento}")
    public ResponseEntity<EventoDTO> getEvento(@PathVariable Long idEntrenador, @PathVariable Long idElemento) {
        return ResponseEntity.of(this.servicio.obtenerEvento(idElemento, idEntrenador).map(Mapper :: toEventoDTO));
    }
	
	@PutMapping("/{idEntrenador}/{idElemento}")
	public EventoDTO putEvento(@PathVariable Long idEntrenador, @PathVariable Long idElemento, @RequestBody EventoDTO evento) {
		this.servicio.obtenerEvento(idElemento, idEntrenador).orElseThrow(EventoNoEncontradoException::new);
		Evento nuevoEvento = Mapper.EventoID(evento);
		nuevoEvento.setId(idElemento);
		nuevoEvento.setIdEntrenador(idEntrenador);
		return Mapper.toEventoDTO(this.servicio.actualizarEvento(nuevoEvento));
	}

	@DeleteMapping("/{idEntrenador}/{idElemento}")
	public void deleteEvento(@PathVariable Long idEntrenador, @PathVariable Long idElemento) {
		this.servicio.eliminarEvento(idElemento);
	}


	@GetMapping("/{idEntrenador}")
	public List<EventoDTO> getEventos(@PathVariable (required = true) Long idEntrenador) {
		return this.servicio.getDisponibilidad(idEntrenador).get().stream().map(Mapper::toEventoDTO).toList();
	}

	@PostMapping("/{idEntrenador}")
	public ResponseEntity<EventoDTO> postEvento(@PathVariable Long idEntrenador, @RequestBody EventoNuevoDTO evento, UriComponentsBuilder builder) {
		Evento nuevoEvento = Mapper.toEvento(evento);
		nuevoEvento.setIdEntrenador(idEntrenador);
		Long id = this.servicio.crearEvento(nuevoEvento);
		EventoDTO e = Mapper.toEventoDTO(nuevoEvento);
		return ResponseEntity.created(builder.path("/calendario/{idEntrenador}").buildAndExpand(idEntrenador, id).toUri()).body(e);
	}

	@ExceptionHandler(EventoNoEncontradoException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public void noEncontrado() {}
	
	@ExceptionHandler(EventoExistenteException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public void existente() {}
	
}
