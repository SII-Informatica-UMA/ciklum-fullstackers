package fullstackers.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import entidades.Evento;
import repositories.EventoRepository;
import servicios.EventoServicio;
import servicios.excepciones.EventoNoEncontradoException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Tests de EventoServicio")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EntidadesApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Value(value = "${local.server.port}")
	private int port;

	@MockBean
	private EventoRepository eventoRepo;

	@Autowired
	private EventoServicio eventoServicio;

	@BeforeEach
	public void initializeDatabase() {
		eventoRepo.deleteAll();
	}

	private URI uri(String scheme, String host, int port, String... paths) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub = ubf.builder()
				.scheme(scheme)
				.host(host).port(port);
		for (String path : paths) {
			ub = ub.path(path);
		}
		return ub.build();
	}

	private RequestEntity<Void> get(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.build();
		return peticion;
	}

	private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.delete(uri)
				.build();
		return peticion;
	}

	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.put(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	@Nested
	@DisplayName("cuando no hay Eventos")
	public class sinEventos {

		@Test
		@DisplayName("cuando se buscan todos los Eventos")
		public void testObtenerEventos() {

			Evento evento = new Evento();
			when(eventoRepo.findAll()).thenReturn(Arrays.asList(evento));

			List<Evento> eventos = eventoServicio.obtenerEventos();

			var request = get("http", "localhost", port, "/eventos");
			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<List<Evento>>() {
					});
			assertThat(response.getStatusCode().value()).isEqualTo(404);

			assertEquals(1, eventos.size());
			assertEquals(evento, eventos.get(0));
		}

		@Test

		@DisplayName("cuando se busca un Evento")
		public void testObtenerEvento() {
			Evento evento = new Evento();
			when(eventoRepo.findByIdEntrenadorIdElemento(anyLong(), anyLong())).thenReturn(Optional.of(evento));

			Optional<Evento> resultado = eventoServicio.obtenerEvento(1L, 1L);

			var request = get("http", "localhost", port, "/eventos");
			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<Evento>() {
					});
			assertThat(response.getStatusCode().value()).isEqualTo(404);

			assertTrue(resultado.isPresent());
			assertEquals(evento, resultado.get());
		}

		@Test
		@DisplayName("cuando se actualiza un Evento que no existe")
		public void testActualizarEventoNoExistente() {
			Evento evento = new Evento();
			when(eventoRepo.existsById(anyLong())).thenReturn(false);

			var request = put("http", "localhost", port, "/eventos", evento);
			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<List<Evento>>() {
					});
			assertThat(response.getStatusCode().value()).isEqualTo(404);

			assertThrows(EventoNoEncontradoException.class, () -> {
				eventoServicio.actualizarEvento(evento);
			});
		}

		@Test
		@DisplayName("cuando se elimina un Evento que no existe")
		public void testEliminarEventoNoExistente() {
			when(eventoRepo.findById(anyLong())).thenReturn(Optional.empty());

			var request = delete("http", "localhost", port, "/eventos");
			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<Evento>() {
					});
			assertThat(response.getStatusCode().value()).isEqualTo(404);

			assertThrows(EventoNoEncontradoException.class, () -> {
				eventoServicio.eliminarEvento(1L);
			});
		}

		@Test
		@DisplayName("cuando se crea un Evento")
		public void testCrearEvento() {
			Evento evento = new Evento();
			when(eventoRepo.save(any(Evento.class))).thenReturn(evento);

			Long resultado = eventoServicio.crearEvento(evento);

			var request = post("http", "localhost", port, "/eventos", evento);
			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<Evento>() {
					});
			assertThat(response.getStatusCode().value()).isEqualTo(404);

			assertEquals(evento.getId(), resultado);
		}

		@Test
		@DisplayName("cuando se busca la disponibilidad de un Entrenador")
		public void testGetDisponibilidad() {
			List<Evento> eventos = Arrays.asList(new Evento());
			when(eventoRepo.findByIdEntrenador(anyLong())).thenReturn(Optional.of(eventos));

			Optional<List<Evento>> resultado = eventoServicio.getDisponibilidad(1L);

			var request = get("http", "localhost", port, "/eventos");
			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<Evento>() {
					});
			assertThat(response.getStatusCode().value()).isEqualTo(404);

			assertTrue(resultado.isPresent());
			assertEquals(eventos, resultado.get());
		}
	}

	@Nested
	@DisplayName("cuando hay Eventos")
	public class ConEventos {
		private Evento evento1;
		private Evento evento2;

		@BeforeEach
		public void init() {
			evento1 = new Evento();
			evento1.setNombre("Primer Evento");
			eventoRepo.save(evento1);

			evento2 = new Evento();
			evento2.setNombre("Segundo Evento");
			eventoRepo.save(evento2);
		}
	}

	@Test
	@DisplayName("cuando se actualiza un Evento")
	public void testActualizarEvento() {
		Evento evento = new Evento();
		when(eventoRepo.existsById(anyLong())).thenReturn(true);
		when(eventoRepo.save(any(Evento.class))).thenReturn(evento);

		Evento resultado = eventoServicio.actualizarEvento(evento);

		var peticion = put("http", "localhost", port, "/eventos/1", evento);
		var response = restTemplate.exchange(peticion, Void.class);
		assertThat(response.getStatusCode().value()).isEqualTo(404);

		assertEquals(evento, resultado);
	}

	@Test
	@DisplayName("cuando se elimina un Evento")
	public void testEliminarEvento() {
		when(eventoRepo.findById(anyLong())).thenReturn(Optional.of(new Evento()));

		eventoServicio.eliminarEvento(1L);

		var peticion = delete("http", "localhost", port, "/eventos/1");
		var response = restTemplate.exchange(peticion, Void.class);
		assertThat(response.getStatusCode().value()).isEqualTo(404);

		verify(eventoRepo, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("cuando se crea un Evento con nombre usado")
	public void testCrearEventoNombreUsado() {
		Evento evento = new Evento();
		when(eventoRepo.save(any(Evento.class))).thenReturn(evento);

		Long resultado = eventoServicio.crearEvento(evento);

		var peticion = post("http", "localhost", port, "/eventos", evento);
		var respuesta = restTemplate.exchange(peticion, Evento.class);
		assertThat(respuesta.getStatusCode().value()).isEqualTo(404);

		assertEquals(evento.getId(), resultado);
	}
}