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
  hueco: Hueco = {id:1,duracionMinutos: 60, inicio: '', reglaRecurrencia: '', fecha: ''};

  constructor(public modal: NgbActiveModal) { 
    this.fechaSeleccionada = null;
  }

  onDateSelect(date: NgbDate) {
    console.log(date);
  }

  guardarHueco(): void {
    if(this.hueco.fecha && this.hueco.reglaRecurrencia === 'Diaria') {
      // Si la fecha y la regla de recurrencia están presentes, procede a guardar
      
      console.log("Hueco guardado:", this.hueco);
      this.modal.close({hueco: this.hueco,fechaSeleccionada: this.fechaSeleccionada, horaSeleccionada: this.hueco.inicio});
    
    }else if (this.hueco.fecha) {
      // Si la fecha está presentes, procede a guardar
      console.log("Hueco guardado:", this.hueco);
      this.modal.close({hueco: this.hueco, horaSeleccionada: this.hueco.inicio});
    } else {
              // Si falta fecha, muestra un mensaje de error
      console.error("Fecha es un campo obligatorio");
    }
  }
}