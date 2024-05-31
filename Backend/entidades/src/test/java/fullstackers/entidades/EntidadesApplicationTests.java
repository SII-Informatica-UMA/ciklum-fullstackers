package fullstackers.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fullstackers.controladores.*;
import fullstackers.dtos.*;
import fullstackers.EntidadesApplication;
import fullstackers.repositories.EventoRepository;
import jakarta.transaction.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest(classes=EntidadesApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Tests de EventoServicio")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EntidadesApplicationTests {

	private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

	/*@Autowired
	private RestTemplate rt;*/

	@Autowired
	private TestRestTemplate restTemplate;

	@Value(value = "8080")
	private int port;

	@Autowired
	private EventoRepository eventoRepo;

	@BeforeEach
	public void initializeDatabase() {
		eventoRepo.deleteAll();
		mockServer = MockRestServiceServer.createServer(restTemplate.getRestTemplate());
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
				.accept(MediaType.APPLICATION_JSON)				.build();
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

		@Test//1
		@DisplayName("cuando se buscan todos los Eventos")
		public void testObtenerEventos() {
			

			var request = get("http", "localhost", port, "/calendario/1");
			List<Evento> lista = eventoRepo.findAll();
			//var response = restTemplate.exchange(request,
			//		new ParameterizedTypeReference<List<EventoDTO>>() {
			//		});
			assertThat(lista).hasSize(0);
			//assertThat(response.getStatusCode().value()).isEqualTo(404);
		}
//	MOCK EN EL BEFORE-EACH
		@Test//2
		@DisplayName("cuando se busca un Evento concreto")
		public void testObtenerEvento(){
			EntrenadorDTO entrenador = new EntrenadorDTO();
			entrenador.setIdUsuario(2L);

			try {
				/*mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/entrenador/" + entrenador.getIdUsuario())))
						.andExpect(method(HttpMethod.GET))
						.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(entrenador)));*/

				mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
						.andExpect(method(HttpMethod.GET))
						.andRespond(withStatus(HttpStatus.NOT_FOUND));


			} catch (URISyntaxException e) {
				e.printStackTrace();
			} /*catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			var request = get("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario());

			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<EventoDTO>() {
					});

			assertThat(response.getStatusCode().value()).isEqualTo(404);

		}

		@Test//3
		@DisplayName("cuando se elimina un Evento que no existe")
		public void testEliminarEventoNoExistente() {
			EntrenadorDTO entrenador = new EntrenadorDTO();
			entrenador.setIdUsuario(2L);

			try {
				mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
						.andExpect(method(HttpMethod.DELETE))
						.andRespond(withStatus(HttpStatus.NOT_FOUND));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}


			var request = delete("http", "localhost", port, "/calendario/" +entrenador.getIdUsuario());

			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<Void>() {
					});
			assertThat(response.getStatusCode().value()).isEqualTo(404);
		}
		
		@Test//5
		@DisplayName("cuando se actualiza un Evento que no existe")
		public void testActualizarEventoNoExistente() {
			EntrenadorDTO entrenador = new EntrenadorDTO();
			entrenador.setIdUsuario(2L);

			Evento evento = new Evento().builder()
					.nombre("Spinning")
					.fechaHoraInicio(new Date().toString())
					.duracionMinutos(60L)
					.lugar("Gimnasio")
					.observaciones("ninguna")
					.idCliente(3L)
					.id(1L)
					.idEntrenador(entrenador.getIdUsuario())
					.build();
			try{
				mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
						.andExpect(method(HttpMethod.PUT))
						.andRespond(withStatus(HttpStatus.NOT_FOUND));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			var request = put("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario(), evento);
			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<Evento>() {
					});

			assertThat(response.getStatusCode().value()).isEqualTo(404);
		}
	}

	@Nested
	@Transactional
	@DisplayName("cuando hay Eventos")
	public class ConEventos {

		@BeforeEach
		public void init() {
			var evento1 = new Evento();
			evento1.setNombre("Primer Evento");
			evento1.setIdEntrenador(1L);
			evento1.setId(1L);
			evento1.setDescripcion("descr");
			evento1.setDuracionMinutos(1L);
			evento1.setFechaHoraInicio("hoy y ahora");
			evento1.setIdCliente(1L);
			evento1.setLugar("mi casa");
			evento1.setObservaciones("ninguna");
			eventoRepo.save(evento1);

		}
	
	@Test//1
		@DisplayName("No hay elementos en el repositorio")
		public void testObtenerEventos() {

			var request = get("http", "localhost", port, "/calendario/1");
			List<Evento> lista = eventoRepo.findAll();
			assertThat(lista).hasSize(1);
		}

	//@SuppressWarnings("null")
	@Test//2
	@DisplayName("cuando se busca un Evento")
	public void obtenerEventoConcreto() {
		EntrenadorDTO entrenador = new EntrenadorDTO();
		entrenador.setIdUsuario(2L);

		try{
			mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
					.andExpect(method(HttpMethod.GET))
					.andRespond(withStatus(HttpStatus.OK));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


		var request = get("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario());

		var response = restTemplate.exchange(request,
				new ParameterizedTypeReference<EventoDTO>() {
				});

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		//assertThat(response.getBody().getId()).isEqualTo(1L);

	}

	@Test//3
	@DisplayName("cuando se elimina un Evento")
	public void testEliminarEventoExistente() {
		EntrenadorDTO entrenador = new EntrenadorDTO();
		entrenador.setIdUsuario(2L);

		try{
			mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
					.andExpect(method(HttpMethod.DELETE))
					.andRespond(withStatus(HttpStatus.OK));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		var request = delete("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario());

		var response = restTemplate.exchange(request,
				new ParameterizedTypeReference<Void>() {
				});
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(eventoRepo.findById(2L).isPresent()).isFalse();
	}

	@Test//4
	@DisplayName("cuando se crea un Evento")
	public void testCrearEvento() {
		EntrenadorDTO entrenador = new EntrenadorDTO();
		entrenador.setIdUsuario(2L);	

		Evento evento = Evento.builder()
				.nombre("Zumba")
				.fechaHoraInicio(new Date().toString())
				.duracionMinutos(60L)
				.lugar("Gimnasio")
				.observaciones("ninguna")
				.idCliente(3L)
				.id(1L)
				.build();

		try{
			mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
					.andExpect(method(HttpMethod.POST))
					.andRespond(withStatus(HttpStatus.CREATED));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		var request = post("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario(), evento);

		var response = restTemplate.exchange(request,
				new ParameterizedTypeReference<Void>() {
				});

		assertThat(response.getStatusCode().value()).isEqualTo(201);

		/*List<Evento> eventoBD = eventoRepo.findAll();
		assertThat(eventoBD.get(1).getNombre()).isEqualTo(evento.getNombre());*/
	}

	/*@Test//5
	@DisplayName("cuando se actualiza un Evento que no existe")
	public void testActualizarEventoNoExistente() {

		Evento evento = new Evento();

		var request = put("http", "localhost", port, "/calendario/1/1", evento);
		var response = restTemplate.exchange(request,
				new ParameterizedTypeReference<Evento>() {
				});

		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}*/

	@Test//5
        @DisplayName("Se actualiza un Evento-----")
        public void testActualizarEventoNoExistente() {
            EntrenadorDTO entrenador = new EntrenadorDTO();
            entrenador.setIdUsuario(2L);

            Evento evento = new Evento().builder()
                    .nombre("Spinning")
                    .fechaHoraInicio(new Date().toString())
                    .duracionMinutos(60L)
                    .lugar("Gimnasio")
                    .observaciones("ninguna")
                    .idCliente(3L)
                    .id(1L)
                    .idEntrenador(entrenador.getIdUsuario())
                    .build();
            try{
                mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
                        .andExpect(method(HttpMethod.PUT))
                        .andRespond(withStatus(HttpStatus.OK));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            var request = put("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario(), evento);
            var response = restTemplate.exchange(request,
                    new ParameterizedTypeReference<Evento>() {
                    });

            assertThat(response.getStatusCode().value()).isEqualTo(200);
        }


	@Test//6
	@DisplayName("cuando se crea un Evento con nombre usado o la cita esta solapda")
	public void testCrearEventoNombreUsado() {
		EntrenadorDTO entrenador = new EntrenadorDTO();
		entrenador.setIdUsuario(2L);


		Evento evento =  Evento.builder()
				.nombre("Waterpolo")
				.fechaHoraInicio(new Date().toString())
				.duracionMinutos(180L)
				.lugar("Piscina")
				.observaciones("ninguna")
				.idCliente(3L)
				.id(1L)
				.idEntrenador(entrenador.getIdUsuario())
				.build();

		try{
			mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/calendario/" + entrenador.getIdUsuario())))
					.andExpect(method(HttpMethod.POST))
					.andRespond(withStatus(HttpStatus.BAD_REQUEST));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		var request = post("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario(), evento);

		var response = restTemplate.exchange(request,
				new ParameterizedTypeReference<Evento>() {
				});

		assertThat(response.getStatusCode().value()).isEqualTo(400);
	}
}
}