import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import repositories.EventoRepository;

@Component
public class LineaComandos implements CommandLineRunner{
    private EventoRepository repository;
    public LineaComandos(EventoRepository repository) {
        this.repository = repository;
    }

    private static Logger LOG = LoggerFactory.getLogger(LineaComandos.class);

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
