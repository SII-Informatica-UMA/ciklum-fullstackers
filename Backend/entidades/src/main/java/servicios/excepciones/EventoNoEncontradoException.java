
//no esta terminado, PROVISIONAL


package servicios.excepciones;

public class EventoNoEncontradoException extends RuntimeException {
    public EventoNoEncontradoException() {
        super("El evento no se ha encontrado");
    }
}
