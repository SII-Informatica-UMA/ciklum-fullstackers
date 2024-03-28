import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { evento } from '../evento';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-formulario-evento',
  templateUrl: './formulario-evento.component.html',
  styleUrls: ['./formulario-evento.component.css']
})

export class FormularioEventoComponent {
  accion?: "Añadir" | "Editar";
  contacto: evento = {id: 0, nombre: '', hora: '', lugar: ''};
  modal: NgbActiveModal;
  evento: any;

  constructor(modal: NgbActiveModal) {
    this.modal = modal;
  }

  guardarEvento(): void {
    this.modal.close(this.evento);
  }

}
