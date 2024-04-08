import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Evento } from '../calendario/evento.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formulario-evento',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './formulario-evento.component.html',
  styleUrl: './formulario-evento.component.css'
})
export class FormularioEventoComponent {
  accion?: "Añadir" | "Editar";
  evento: Evento = {nombre: '', descripcion: '', observaciones: '', lugar: '', duracionMinutos: 0, inicio: '', reglaRecurrencia: '', idCliente: 0, id: 0};

  constructor(public modal: NgbActiveModal) { }

  guardarEvento(): void {
    this.modal.close(this.evento);
  }



  cancelar() {
    this.modal.dismiss(this.evento);
  }

  /*
  agregarDisponibilidad() {
    // Implementa la lógica para añadir una nueva franja de disponibilidad
    console.log('Nueva franja de disponibilidad añadida');
  }

  // Método para determinar si el usuario actual es un entrenador
    esEntrenador(): boolean {
    return this.rol === 'entrenador';
  }
  */
}