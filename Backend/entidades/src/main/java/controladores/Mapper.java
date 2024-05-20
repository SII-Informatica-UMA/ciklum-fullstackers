package controladores;

import dtos.EventoDTO;
import dtos.EventoNuevoDTO;
import entidades.Evento;

public class Mapper {

    public static EventoDTO toEventoDTO(Evento evento) {
        return EventoDTO.builder()
                .id(evento.getId())
                .nombre(evento.getNombre())
                .descripcion(evento.getDescripcion())
                .observaciones(evento.getObservaciones())
                .lugar(evento.getLugar())
                .duracionMinutos(evento.getDuracionMinutos())
                .fechaHoraInicio(evento.getFechaHoraInicio())
                .idCliente(evento.getIdCliente())
                .build();
    }

    public static Evento toEvento(EventoNuevoDTO eventoNuevo) {
        Evento evento = new Evento();
        evento.setNombre(eventoNuevo.getNombre());
        evento.setDescripcion(eventoNuevo.getDescripcion());
        evento.setObservaciones(eventoNuevo.getObservaciones());
        evento.setLugar(eventoNuevo.getLugar());
        evento.setDuracionMinutos(eventoNuevo.getDuracionMinutos());
        evento.setFechaHoraInicio(eventoNuevo.getFechaHoraInicio());
        return evento;
    }

    public static Evento EventoID (EventoDTO evento) {
        Evento nuevoEvento = new Evento();
        nuevoEvento.setId(evento.getId());
        nuevoEvento.setNombre(evento.getNombre());
        nuevoEvento.setDescripcion(evento.getDescripcion());
        nuevoEvento.setObservaciones(evento.getObservaciones());
        nuevoEvento.setLugar(evento.getLugar());
        nuevoEvento.setDuracionMinutos(evento.getDuracionMinutos());
        nuevoEvento.setFechaHoraInicio(evento.getFechaHoraInicio());

        return nuevoEvento;
    }
    
}
