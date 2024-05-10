import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import entidades.Evento;
import jakarta.transaction.Transactional;
import repositories.EventoRepository;
@Component
public class LineaComandos implements CommandLineRunner {
	
	
	private EventoRepository repoEvento;
    


	public LineaComandos(EventoRepository repoEvento) {
		this.repoEvento = repoEvento;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		
		//Ejercicio 1
		Evento ejercicio1 = new Evento();
        ejercicio1.setNombre("Paco");
        ejercicio1.setDescripcion("Dieta");
        ejercicio1.setObservaciones("Mejoras");
        ejercicio1.setLugar("Cartama");
        ejercicio1.setDuracionMinutos(1L);
        ejercicio1.setFechaHoraInicio("2023/07/22");
		repoEvento.save(ejercicio1);
        
        // Ejercicio 2
        Evento ejercicio2 = new Evento();
        ejercicio2.setNombre("Maria");
        ejercicio2.setDescripcion("Ejercicio");
        ejercicio2.setObservaciones("Sin observaciones");
        ejercicio2.setDuracionMinutos(60L);
        ejercicio2.setFechaHoraInicio("2023/07/23");
        repoEvento.save(ejercicio2);

        // Ejercicio 3
        Evento ejercicio3 = new Evento();
        ejercicio3.setNombre("Juan");
        ejercicio3.setDescripcion("Reunion");
        ejercicio3.setObservaciones("Importante");
        ejercicio3.setLugar("Sevilla");
        ejercicio3.setDuracionMinutos(120L);
        ejercicio3.setFechaHoraInicio("2023/07/24");
        repoEvento.save(ejercicio3);

		//Ejercicio 4
        /*
        Hueco ejercicio4 = new  Hueco();
        ejercicio4.setDuracionMinutos(60L);
        ejercicio4.setFechaHoraInicio("2024/06/05");
        ejercicio4.setReglaRecurrencia("semanal");
        repoHueco.save(ejercicio4);
        
        // Ejercicio 5
        Hueco ejercicio5 = new Hueco();
        ejercicio5.setDuracionMinutos(30L);
        ejercicio5.setFechaHoraInicio("2024/06/10");
        ejercicio5.setReglaRecurrencia("diaria");
        repoHueco.save(ejercicio5);

        // Ejercicio 6
        Hueco ejercicio6 = new Hueco();
        ejercicio6.setDuracionMinutos(90L);
        ejercicio6.setFechaHoraInicio("2024/06/15");
        ejercicio6.setReglaRecurrencia("mensual");
        repoHueco.save(ejercicio6);
		*/

		



		


	}

}
