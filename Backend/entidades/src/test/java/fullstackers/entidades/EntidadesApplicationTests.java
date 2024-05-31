package fullstackers.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; //añadir esto a
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import fullstackers.dtos.EntrenadorDTO;
import java.net.URISyntaxException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import fullstackers.controladores.*;
import fullstackers.dtos.*;
import fullstackers.EntidadesApplication;
import fullstackers.repositories.EventoRepository;
import fullstackers.security.JwtUtil;
import jakarta.transaction.Transactional;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(classes=EntidadesApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Tests de EventoServicio")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EntidadesApplicationTests {

   private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "8080")
	private int port;

    @Autowired
    private EventoRepository eventoRepo;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtToken;

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

    private RequestEntity<Void> get(String scheme, String host, int port, String path, String token) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path, String token) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object, String token) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object, String token) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(object);
        return peticion;
    }

    @BeforeEach
    public void init() {
        UserDetails userDetails = new User("testuser", "", Collections.emptyList());
        jwtToken = jwtUtil.generateToken(userDetails);
    }

    @Nested
    @DisplayName("cuando no hay Eventos")
    public class sinEventos {

        @Test
        @DisplayName("cuando se buscan todos los Eventos")
        public void testObtenerEventos() {
            var request = get("http", "localhost", port, "/calendario/1", jwtToken);
            List<Evento> lista = eventoRepo.findAll();
            assertThat(lista).hasSize(0);
        }

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

			var request = get("http", "localhost", port, "/calendario/" + entrenador.getIdUsuario(),jwtToken);

			var response = restTemplate.exchange(request,
					new ParameterizedTypeReference<EventoDTO>() {
					});

			assertThat(response.getStatusCode().value()).isEqualTo(404);

		}

        @Test
        @DisplayName("cuando se elimina un Evento que no existe")
        public void testEliminarEventoNoExistente() {
            var request = delete("http", "localhost", port, "/calendario/1/1", jwtToken);
            var response = restTemplate.exchange(request, new ParameterizedTypeReference<Void>() {});
            assertThat(response.getStatusCode().value()).isEqualTo(404);
        }

        @Test
        @DisplayName("cuando se actualiza un Evento que no existe")
        public void testActualizarEventoNoExistente() {
            Evento evento = new Evento();
            var request = put("http", "localhost", port, "/calendario/1/1", evento, jwtToken);
            var response = restTemplate.exchange(request, new ParameterizedTypeReference<Evento>() {});
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

        @Test
        @DisplayName("No hay elementos en el repositorio")
        public void testObtenerEventos() {
            var request = get("http", "localhost", port, "/calendario/1", jwtToken);
            List<Evento> lista = eventoRepo.findAll();
            assertThat(lista).hasSize(1); 
        }

	
        @Test
        @DisplayName("cuando se elimina un Evento que existe")
        public void testEliminarEventoExistente() {
            var request = delete("http", "localhost", port, "/calendario/1/1", jwtToken);
            var response = restTemplate.exchange(request, new ParameterizedTypeReference<Void>() {});
            assertThat(response.getStatusCode().value()).isEqualTo(200);
        }

        @Test
        @DisplayName("cuando se crea un Evento")
        public void testCrearEvento() {
            Evento evento = new Evento();
            var request = post("http", "localhost", port, "/calendario/1", evento, jwtToken);
            var response = restTemplate.exchange(request, new ParameterizedTypeReference<Evento>() {});
            assertThat(response.getStatusCode().value()).isEqualTo(201);
        }
        @Test
        @DisplayName("cuando se crea un Evento con nombre usado")
        public void testCrearEventoNombreUsado() {
            Evento evento = new Evento();
            evento.setNombre("Primer Evento");
            var request = post("http", "localhost", port, "/calendario/1", evento, jwtToken);
            var response = restTemplate.exchange(request, new ParameterizedTypeReference<Evento>() {});
            assertThat(response.getStatusCode().value()).isEqualTo(201);
        }
		@Test
		@DisplayName("cuando se busca un Evento")
		public void testObtenerEvento() {
    		var request = get("http", "localhost", port, "/calendario/1/1", jwtToken);
    		var response = restTemplate.exchange(request, new ParameterizedTypeReference<Evento>() {});
    		assertThat(response.getStatusCode().value()).isEqualTo(200);
}
        //a
        @Test
        @DisplayName("Constructor con todos los argumentos en EventoNuevoDTO")
        void constructorConTodosLosArgumentosEnEventoNuevoDTO() {
        String nombre = "Nuevo Evento";
        String descripcion = "Descripción del nuevo evento";
        String observaciones = "Observaciones del nuevo evento";
        String lugar = "Ubicación del nuevo evento";
        Long duracionMinutos = 60L;
        String fechaHoraInicio = "2024-05-31T12:00:00";
        String tipo = "Tipo de evento";
        Long idCliente = 123L;

        EventoNuevoDTO dto = new EventoNuevoDTO(nombre, descripcion, observaciones, lugar,
                duracionMinutos, fechaHoraInicio, tipo, idCliente);

        assertNotNull(dto);
        assertEquals(nombre, dto.getNombre());
        assertEquals(descripcion, dto.getDescripcion());
        assertEquals(observaciones, dto.getObservaciones());
        assertEquals(lugar, dto.getLugar());
        assertEquals(duracionMinutos, dto.getDuracionMinutos());
        assertEquals(fechaHoraInicio, dto.getFechaHoraInicio());
        assertEquals(tipo, dto.getTipo());
        assertEquals(idCliente, dto.getIdCliente());
    }
    //a
	@Test
    @DisplayName("Constructor con todos los argumentos en EventoDTO")
    void constructorConTodosLosArgumentosEnEventoDTO() {
        Long id = 1L;
        String nombre = "Evento de prueba";
        String descripcion = "Descripción del evento";
        String observaciones = "Observaciones del evento";
        String lugar = "Lugar del evento";
        Long duracionMinutos = 60L;
        String fechaHoraInicio = "2024-05-31T12:00:00";
        Long idCliente = 123L;
        String tipo = "Tipo de evento";

        EventoDTO dto = new EventoDTO(id, nombre, descripcion, observaciones, lugar,
                duracionMinutos, fechaHoraInicio, idCliente, tipo);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(descripcion, dto.getDescripcion());
        assertEquals(observaciones, dto.getObservaciones());
        assertEquals(lugar, dto.getLugar());
        assertEquals(duracionMinutos, dto.getDuracionMinutos());
        assertEquals(fechaHoraInicio, dto.getFechaHoraInicio());
        assertEquals(idCliente, dto.getIdCliente());
        assertEquals(tipo, dto.getTipo());
    }
    //a
    @Test
    @DisplayName("Constructor vacío")
    public void testConstructorVacio() {
        // Arrange
        Evento evento = new Evento();

        // Assert
        assertNotNull(evento);
        assertNull(evento.getId());
        assertNull(evento.getNombre());
        assertNull(evento.getDescripcion());
        assertNull(evento.getObservaciones());
        assertNull(evento.getLugar());
        assertNull(evento.getDuracionMinutos());
        assertNull(evento.getFechaHoraInicio());
        assertNull(evento.getIdEntrenador());
        assertNull(evento.getIdCliente());
    }
    @Test
@DisplayName("Prueba de igualdad entre dos eventos")
public void testEquals() {
    Evento evento1 = new Evento();
    evento1.setNombre("Evento de prueba");
    evento1.setDescripcion("Descripción del evento");

    Evento evento2 = new Evento();
    evento2.setNombre("Evento de prueba");
    evento2.setDescripcion("Descripción del evento");

    assertTrue(evento1.equals(evento2));
    assertEquals(evento1.hashCode(), evento2.hashCode());
}



@Test
@DisplayName("Prueba solicitud incorrecta")
public void testSolicitudIncorrecta() {
    // Arrange
    // Modificar la URL para que apunte a un recurso inexistente
    var request = get("http", "localhost", port, "/calendario/999/999", jwtToken);

    // Act
    var response = restTemplate.exchange(request, new ParameterizedTypeReference<Evento>() {});

    // Assert
    assertThat(response.getStatusCode().value()).isEqualTo(404); // Esperar un código de estado 404 (Not Found)
}

    }
}
