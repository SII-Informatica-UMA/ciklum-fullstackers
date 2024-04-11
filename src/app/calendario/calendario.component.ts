import { Component,inject, Injectable} from '@angular/core';
import { Evento } from './evento.model';
import { NgFor, NgIf, JsonPipe } from '@angular/common';
import { Usuario, UsuarioImpl } from '../entities/usuario';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormularioEventoComponent } from '../formulario-evento/formulario-evento.component';
import { UsuariosService } from '../services/usuarios.service';
import { Observable, of } from 'rxjs';
import { Rol } from '../entities/login';
import { BackendFakeService } from '../services/backend.fake.service';
import { NgbCalendar, NgbDate, NgbDatepickerModule, NgbDateStruct, NgbDatepickerNavigateEvent } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, NgModel } from '@angular/forms';
import { EventosService } from '../services/eventos.service';
 import { FormularioHuecoComponent } from '../formulario-hueco/formulario-hueco.component';
import { Hueco } from './hueco.model';

@Component({
  selector: 'app-calendario',
  standalone: true,
  imports: [NgFor,NgIf,NgbDatepickerModule, JsonPipe,FormsModule],
  templateUrl: './calendario.component.html',
  styleUrl: './calendario.component.css'
})
export class CalendarioComponent {

  fechaSeleccionada: NgbDate | null = null; // Define fechaSeleccionada
  
  today = inject(NgbCalendar).getToday();

	model: NgbDate;
	date: { year: number; month: number } = { year: 0, month: 0 };;

  onNavigate(event: NgbDatepickerNavigateEvent) {
    this.date = { year: event.next.year, month: event.next.month };
  }


  calendarioEventos : Evento[] = [
    {nombre: 'Evento 1', descripcion: 'Descripción del evento 1', observaciones: 'Observaciones del evento 1', lugar: 'Lugar del evento 1', duracionMinutos: 60, inicio: '00:00', reglaRecurrencia: 'Regla de recurrencia del evento 1', idCliente: 3, id: 1,
    fecha: ''}
  ];
  calendariohuecos : Hueco[] = [
    {id:1,duracionMinutos: 60, inicio: '00:00', reglaRecurrencia: 'Regla de recurrencia del evento 1',fecha: ''}
  ];
  

  usuarios: Usuario [] = [];
  horas: string[] = ["9:00", "10:00", "11:00", "12:00", "13:00", "14:00","15:00", "16:00", "17:00", "18:00", "19:00", "20:00"];
  horasDisponibles: [string, string][] = [];
  horasDelDiaDisponibles: [string, string][] = [];
  constructor(private usuariosService: UsuariosService, private modalService: NgbModal, private backendService: BackendFakeService, private calendar:NgbCalendar) {
    this.actualizarUsuarios(); 
    this.model = this.calendar.getToday()
  }

  obtenerEvento(): void {
    const id = parseInt(prompt('Introduce el ID del evento:') || '');
    const evento = this.calendarioEventos.filter(e => e.id === id);
    if (evento.length > 0) {
      this.calendarioEventos = this.calendarioEventos.filter(e => e.id === id);
    } else {
      alert('No se encontró ningún evento con el ID proporcionado.');
    }
  }
  guardarEvento(): void {
    let ref = this.modalService.open(FormularioEventoComponent);
    ref.componentInstance.accion = "Añadir";
    // Crea un nuevo evento y asigna la fecha
    let nuevoEvento: Evento = {
      nombre: '',
      descripcion: '',
      observaciones: '',
      lugar: '',
      duracionMinutos: 0,
      inicio: '',
      reglaRecurrencia: '',
      idCliente: 0,
      id: 0,
      fecha: `${this.model.year}-${this.model.month}-${this.model.day}` 
    };

    ref.componentInstance.evento = nuevoEvento;
    ref.componentInstance.horasDisponibles = this.horasDisponibles.filter(hora => hora[1] == nuevoEvento.fecha);

    ref.result.then((resultado: { evento: Evento, horaSeleccionada: string }) => {
      this.backendService.agregarEvento(resultado.evento).subscribe(eventoGuardado => {
          this.calendarioEventos.push(eventoGuardado);
          this.calendarioEventos.sort((a, b) => a.inicio.localeCompare(b.inicio));
          this.horasDisponibles = this.horasDisponibles.filter(hora => hora[0] !== resultado.horaSeleccionada);
        });
    });
  }

  guardarHueco(): void {
    let ref = this.modalService.open(FormularioHuecoComponent);
    ref.componentInstance.accion = "Añadir";
        // Crea un nuevo hueco y asigna la fecha
    let nuevoHueco: Hueco = {
      id: 0,
      duracionMinutos: 0,
      inicio: '',
      reglaRecurrencia: '',
      fecha: `${this.model.year}-${this.model.month}-${this.model.day}`
    };

    ref.componentInstance.hueco = nuevoHueco;
    ref.componentInstance.horas = this.horas;

    ref.result.then((resultado: {hueco: Hueco, horaAñadida: string}) => {
      this.backendService.agregarHueco(resultado.hueco).subscribe(huecoGuardado => {
        this.horasDisponibles.push([huecoGuardado.inicio,huecoGuardado.fecha]);
        this.calendariohuecos.push(huecoGuardado);
        this.calendariohuecos.sort((a, b) => a.inicio.localeCompare(b.inicio));
          //this.horas = this.horas.filter(hora => hora !== resultado.horaAñadida);
      });
    });
  }

  onDateSelect(date: NgbDate): void {
    this.fechaSeleccionada = date;
  }

  isEntrenador(): boolean{
    return this.rol?.rol == Rol.ENTRENADOR || this.rol?.rol == Rol.ADMINISTRADOR;
  }

  eventosDelDia(event: Evento):boolean{
    let ano = this.date.year.toString();
    let mon = this.date.month.toString();
    let dai = this.model.day.toString();
    if(mon.length>1 && dai.length>1){
      return ano===event.fecha.substring(0,4) && mon===event.fecha.substring(5,7) && dai===event.fecha.substring(8,10);;
    }else if(mon.length>1){
      return ano===event.fecha.substring(0,4) && mon===event.fecha.substring(5,7) && dai===event.fecha.charAt(8);
    }else if(dai.length>1){
      return ano===event.fecha.substring(0,4) && mon===event.fecha.charAt(5) && dai===event.fecha.substring(7,9);
    }else{
      return ano===event.fecha.substring(0,4) && mon===event.fecha.charAt(5) && dai===event.fecha.charAt(7);
    }
  }

  huecosDelDia(hueco: Hueco):boolean{
    let ano = this.date.year.toString();
    let mon = this.date.month.toString();
    let dai = this.model.day.toString();
    if(mon.length>1 && dai.length>1){
      return ano===hueco.fecha.substring(0,4) && mon===hueco.fecha.substring(5,7) && dai===hueco.fecha.substring(8,10);;
    }else if(mon.length>1){
      return ano===hueco.fecha.substring(0,4) && mon===hueco.fecha.substring(5,7) && dai===hueco.fecha.charAt(8);
    }else if(dai.length>1){
      return ano===hueco.fecha.substring(0,4) && mon===hueco.fecha.charAt(5) && dai===hueco.fecha.substring(7,9);
    }else{
      return ano===hueco.fecha.substring(0,4) && mon===hueco.fecha.charAt(5) && dai===hueco.fecha.charAt(7);
    }
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
    const eventoEditar = this.calendarioEventos.find(evento => evento.id === eventoId);
    if (eventoEditar) {
      let ref = this.modalService.open(FormularioEventoComponent);
      ref.componentInstance.accion = "Editar";
      ref.componentInstance.evento = { ...eventoEditar }; // Pasamos una copia del evento para no modificar el original directamente

      ref.result.then((resultado: { evento: Evento }) => {
        // Actualizamos el evento en la lista
        const index = this.calendarioEventos.findIndex(evento => evento.id === eventoId);
        if (index !== -1) {
          this.calendarioEventos[index] = { ...resultado.evento }; // Actualizamos el evento con los datos del formulario
        }
      });
    }
    }
    
  }
  eliminarEvento(id: number): Observable<void> {
    const index = this.calendarioEventos.findIndex(evento => evento.id === id);
    if (index !== -1) {
      this.calendarioEventos.splice(index, 1);
      localStorage.setItem('eventos', JSON.stringify(this.calendarioEventos));
      return of();
    } else {
      return new Observable<void>(observer => {
        observer.error('Evento no encontrado');
      });
    }
  }
  editarHueco(huecoId: number, clienteId: number): void {
    // Verificar si el usuario es un entrenador o si el usuario en sesión es el cliente relacionado con el hueco
    if (this.isEntrenador() || this.usuarioSesion?.id === clienteId) {
      const huecoEditar = this.calendariohuecos.find(hueco => hueco.id === huecoId);
      if (huecoEditar) {
        let ref = this.modalService.open(FormularioHuecoComponent);
        ref.componentInstance.accion = "Editar";
        ref.componentInstance.hueco = { ...huecoEditar }; // Pasamos una copia del hueco para no modificar el original directamente
  
        ref.result.then((resultado: { hueco: Hueco }) => {
          // Actualizamos el hueco en la lista
          const index = this.calendariohuecos.findIndex(hueco => hueco.id === huecoId);
          if (index !== -1) {
            this.calendariohuecos[index] = { ...resultado.hueco }; // Actualizamos el hueco con los datos del formulario
          }
        });
      }
    }
  }
  
  // En tu componente TypeScript
obtenerIdCliente(hueco: Hueco): number {
  // Encuentra el evento relacionado con este hueco
  const eventoRelacionado = this.calendarioEventos.find(evento => evento.fecha === hueco.fecha && evento.inicio === hueco.inicio);
  // Retorna el idCliente del evento si se encuentra, de lo contrario, retorna 0 o cualquier valor predeterminado que desees
  return eventoRelacionado ? eventoRelacionado.idCliente : 0;
}

  eliminarHueco(huecoId: number): void {
    const index = this.calendariohuecos.findIndex(hueco => hueco.id === huecoId);
    if (index !== -1) {
      this.calendariohuecos.splice(index, 1);
      // Puedes añadir aquí cualquier lógica adicional, como actualizar la base de datos
    }
  }
}
