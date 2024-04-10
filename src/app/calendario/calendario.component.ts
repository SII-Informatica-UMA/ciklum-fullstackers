import { Component } from '@angular/core';
import { Evento } from './evento.model';
import { NgFor, NgIf } from '@angular/common';
import { Usuario, UsuarioImpl } from '../entities/usuario';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormularioEventoComponent } from '../formulario-evento/formulario-evento.component';
import { UsuariosService } from '../services/usuarios.service';
import { FormularioUsuarioComponent } from '../formulario-usuario/formulario-usuario.component';
import { Rol } from '../entities/login';

@Component({
  selector: 'app-calendario',
  standalone: true,
  imports: [NgFor,NgIf],
  templateUrl: './calendario.component.html',
  styleUrl: './calendario.component.css'
})
export class CalendarioComponent {
  calendario : Evento[] = [
    {nombre: 'Evento 1', descripcion: 'Descripción del evento 1', observaciones: 'Observaciones del evento 1', lugar: 'Lugar del evento 1', duracionMinutos: 60, inicio: '2021-01-01T00:00:00', reglaRecurrencia: 'Regla de recurrencia del evento 1', idCliente: 3, id: 1},
  ]
  usuarios: Usuario [] = [];
  horasDisponibles: string[] = ["9:00", "10:00", "11:00", "12:00", "13:00", "14:00"];
  constructor(private usuariosService: UsuariosService, private modalService: NgbModal) {this.actualizarUsuarios();}

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

  isEntrenador(): boolean{
    return this.rol?.rol == Rol.ENTRENADOR || this.rol?.rol == Rol.ADMINISTRADOR;
  }
  eventoDelCliente(event: Evento): boolean{
    return event.idCliente==this.usuarioSesion?.id;
  }
  actualizarUsuarios() {
    this.usuariosService.getUsuarios().subscribe(usuarios => {
      this.usuarios = usuarios;
    });
  }
  private get rol() {
    return this.usuariosService.rolCentro;
  }
  get usuarioSesion() {
    return this.usuariosService.getUsuarioSesion();
  }
  editarEvento(eventoId: number, clienteId: number): void {
    if(this.isEntrenador() || this.usuarioSesion?.id==clienteId){
    const eventoEditar = this.calendario.find(evento => evento.id === eventoId);
    if (eventoEditar) {
      let ref = this.modalService.open(FormularioEventoComponent);
      ref.componentInstance.accion = "Editar";
      ref.componentInstance.evento = { ...eventoEditar }; // Pasamos una copia del evento para no modificar el original directamente

      ref.result.then((resultado: { evento: Evento }) => {
        // Actualizamos el evento en la lista
        const index = this.calendario.findIndex(evento => evento.id === eventoId);
        if (index !== -1) {
          this.calendario[index] = { ...resultado.evento }; // Actualizamos el evento con los datos del formulario
        }
      });
    }
  }
}
}