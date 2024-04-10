import { Component } from '@angular/core';
import { Usuario, UsuarioImpl } from '../entities/usuario';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-formulario-usuario',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-usuario.component.html',
  styleUrl: './formulario-usuario.component.css'
})
export class FormularioUsuarioComponent {
  accion?: "Añadir" | "Editar";
  _usuario: Usuario = new UsuarioImpl();
  rpassword: string = '';
  error: string = '';

  constructor(public modal: NgbActiveModal) { }

  get usuario () {
    return this._usuario;
  }

  set usuario(u: Usuario) {
    this._usuario = u;
    this._usuario.password='';
  }
  getRandomInt(max: any) {
    return Math.floor(Math.random() * max);
  }
  guardarUsuario(): void {
    if(this._usuario.entrenador!=true)
      this._usuario.id=12;
    if (this._usuario.password != this.rpassword) {
      this.error="Las contraseñas no coinciden";
      return;
    }

    this.modal.close(this.usuario);
  }
}