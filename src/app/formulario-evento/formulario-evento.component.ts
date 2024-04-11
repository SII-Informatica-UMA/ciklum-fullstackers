import { Component, Input } from '@angular/core';
import { NgbActiveModal, NgbDate } from '@ng-bootstrap/ng-bootstrap';
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
  fechaSeleccionada: NgbDate | null = null;

  accion?: "Añadir" | "Editar";
  evento: Evento = {nombre: '', descripcion: '', observaciones: '', lugar: '', duracionMinutos: 0, inicio: '', reglaRecurrencia: '', idCliente: 0, id: 0, fecha: ''};

  constructor(public modal: NgbActiveModal) {   
    this.fechaSeleccionada = null; // Inicialización como null
}

  //horasDisponibles: string[] = this.modal.horasDisponibles;
  onDateSelect(date: NgbDate) {
    console.log(date);
  }

  guardarEvento() {
    if (this.evento.nombre && this.evento.fecha) {
        // Si el nombre del evento y la fecha están presentes, procede a guardar
        console.log("Evento guardado:", this.evento);
        this.modal.close({ evento: this.evento, horaSeleccionada: this.evento.inicio });
    } else {
        // Si falta el nombre del evento o la fecha, muestra un mensaje de error
        console.error("Nombre y Fecha son campos obligatorios");
    }
}



}