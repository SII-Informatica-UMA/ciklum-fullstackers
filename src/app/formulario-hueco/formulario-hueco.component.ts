import { Component, Input } from '@angular/core';
import { NgbActiveModal, NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { Hueco } from '../calendario/hueco.model';

@Component({
  selector: 'app-formulario-hueco',
  standalone: true,
  imports: [FormsModule, NgFor],
  templateUrl: './formulario-hueco.component.html',
  styleUrl: './formulario-hueco.component.css'
})

export class FormularioHuecoComponent {
  @Input() horas: string[] = [];
  fechaSeleccionada: NgbDate | null = null
  
  accion?: "Añadir" | "Editar";
  hueco: Hueco = {duracionMinutos: 0, inicio: '', reglaRecurrencia: '', fecha: ''};

  constructor(public modal: NgbActiveModal) { 
    this.fechaSeleccionada = null;
  }

  onDateSelect(date: NgbDate) {
    console.log(date);
  }

  guardarHueco(): void {
    if (this.hueco.fecha) {
      // Si la fecha está presentes, procede a guardar
      console.log("Hueco guardado:", this.hueco);
      this.modal.close({hueco: this.hueco, horaSeleccionada: this.hueco.inicio});
    } else {
              // Si falta fecha, muestra un mensaje de error
      console.error("Fecha es un campo obligatorio");
    }
  }
}