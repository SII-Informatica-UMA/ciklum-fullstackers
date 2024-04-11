import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
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
  accion?: "Añadir" | "Editar";
  hueco: Hueco = {duracionMinutos: 0, inicio: '', reglaRecurrencia: ''};

  constructor(public modal: NgbActiveModal) { }

  guardarHueco(): void {
    this.modal.close({hueco: this.hueco, horaSeleccionada: this.hueco.inicio});
  }
}