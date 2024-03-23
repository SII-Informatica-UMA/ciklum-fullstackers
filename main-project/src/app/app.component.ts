import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormularioEventoComponent } from './formulario-evento/formulario-evento.component'; // Import the missing component
import { evento } from './evento';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'main-project';

  constructor(private modalService: NgbModal) {}

  aniadirEvento(): void {
    let ref = this.modalService.open(FormularioEventoComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.evento = {id: 0, nombre: '', hora: '', lugar: ''};
    ref.result.then((evento: evento) => {

    }, (reason) => {});
  }
}



