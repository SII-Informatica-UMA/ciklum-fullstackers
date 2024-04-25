import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import entidades.Evento;
import jakarta.transaction.Transactional;
import repositories.EventoRepository;

@Component
public class LineaComandos implements CommandLineRunner{
    private EventoRepository repository;
    public LineaComandos(EventoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (String s: args) {
            System.out.println(s);
        }
        if(args.length > 0){
            for(Evento e : repository.findByNombre(args[0])){
                System.out.println(e);
            }
        }
    }
    
}
