import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Evento } from '../calendario/evento.model';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-formulario-evento',
  standalone: true,
  imports: [FormsModule, NgFor],
  templateUrl: './formulario-evento.component.html',
  styleUrl: './formulario-evento.component.css'
})
export class FormularioEventoComponent {
  @Input() horasDisponibles: string[] = [];
  accion?: "Añadir" | "Editar";
  evento: Evento = {nombre: '', descripcion: '', observaciones: '', lugar: '', duracionMinutos: 0, inicio: '', reglaRecurrencia: '', idCliente: 0, id: 0};

  constructor(public modal: NgbActiveModal) { }

  //horasDisponibles: string[] = this.modal.horasDisponibles;

  guardarEvento(): void {
    this.modal.close({evento: this.evento, horaSeleccionada: this.evento.inicio});
  }
}