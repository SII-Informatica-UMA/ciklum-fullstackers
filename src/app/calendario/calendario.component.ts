import { Component } from '@angular/core';
import { Evento } from './evento.model';
import { NgFor } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormularioEventoComponent } from '../formulario-evento/formulario-evento.component';

@Component({
  selector: 'app-calendario',
  standalone: true,
  imports: [NgFor],
  templateUrl: './calendario.component.html',
  styleUrl: './calendario.component.css'
})
export class CalendarioComponent {

  calendario : Evento[] = [
    {nombre: 'Evento 1', descripcion: 'Descripción del evento 1', observaciones: 'Observaciones del evento 1', lugar: 'Lugar del evento 1', duracionMinutos: 60, inicio: '2021-01-01T00:00:00', reglaRecurrencia: 'Regla de recurrencia del evento 1', idCliente: 1, id: 1},
  ]
  horasDisponibles: string[] = ["9:00", "10:00", "11:00", "12:00", "13:00", "14:00"];

  constructor(private modalService: NgbModal) { }

  guardarEvento(): void {
    let ref = this.modalService.open(FormularioEventoComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.evento = {nombre: '', descripcion: '', observaciones: '', lugar: '', duracionMinutos: 0, inicio: '', reglaRecurrencia: '', idCliente: 0, id: 0};
    ref.componentInstance.horasDisponibles = this.horasDisponibles;

    ref.result.then((resultado: {evento: Evento, horaSeleccionada: string}) => {
      this.calendario.push(resultado.evento);
      this.calendario.sort((a, b) => a.inicio.localeCompare(b.inicio));
      this.horasDisponibles = this.horasDisponibles.filter(hora => hora !== resultado.horaSeleccionada);
    });
  }
}