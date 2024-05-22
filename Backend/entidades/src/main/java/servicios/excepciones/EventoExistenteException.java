package servicios.excepciones;

public class EventoExistenteException extends RuntimeException{
    public EventoExistenteException() {
        super("El evento ya existe");
    }
}
