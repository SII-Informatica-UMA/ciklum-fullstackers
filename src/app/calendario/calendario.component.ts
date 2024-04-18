import { Component,inject} from '@angular/core';
import { Evento } from './evento.model';
import { NgFor, NgIf, DatePipe, JsonPipe } from '@angular/common';
import { Usuario } from '../entities/usuario';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormularioEventoComponent } from '../formulario-evento/formulario-evento.component';
import { UsuariosService } from '../services/usuarios.service';
import { Observable, of } from 'rxjs';
import { Rol } from '../entities/login';
import { BackendFakeService } from '../services/backend.fake.service';
import { NgbCalendar, NgbDate, NgbDatepickerModule, NgbDatepickerNavigateEvent } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { EventosService } from '../services/eventos.service';
@Component({
  selector: 'app-calendario',
  standalone: true,
  imports: [NgFor,NgIf,NgbDatepickerModule, JsonPipe,FormsModule, DatePipe],
  templateUrl: './calendario.component.html',
  styleUrl: './calendario.component.css'
})
export class CalendarioComponent {
  today = inject(NgbCalendar).getToday();
	model: NgbDate;
	date: { year: number; month: number } = { year: 0, month: 0 };;

  onNavigate(event: NgbDatepickerNavigateEvent) {
    this.date = { year: event.next.year, month: event.next.month };
  }

  calendario : Evento[];
  usuarios: Usuario [] = [];
  horasDisponibles: string[] = ["9:00", "10:00", "11:00", "12:00", "13:00", "14:00"];
  constructor(
    private usuariosService: UsuariosService,
    private modalService: NgbModal,
    private backendService: BackendFakeService,
    private calendar:NgbCalendar,
    private eventoService: EventosService
  ) {
      this.actualizarUsuarios(); this.model = this.calendar.getToday();
      let _eventos = localStorage.getItem('eventos');
      if (_eventos) {
        const eventos = JSON.parse(_eventos);
        if (!this.isEntrenador()) {
        this.calendario = eventos.filter((evento: Evento) => this.eventoDelCliente(evento));
        } else {
          this.calendario = eventos;
        }
      } else {
        this.calendario = [];
      }
    }

  obtenerEvento(): void {
    const id = parseInt(prompt('Introduce el ID del evento:') || '');
    const evento = this.calendario.filter(e => e.id === id);
    if (evento.length > 0) {
      this.calendario = this.calendario.filter(e => e.id === id);
    } else {
      alert('No se encontró ningún evento con el ID proporcionado.');
    }
  }
  guardarEvento(): void {
    let ref = this.modalService.open(FormularioEventoComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.evento = {nombre: '', descripcion: '', observaciones: '', lugar: '', duracionMinutos: 0, inicio: '', reglaRecurrencia: '', idCliente: 0, id: 0};
    
    ref.componentInstance.horasDisponibles = this.horasDisponibles;
    const fechaHoy = new Date(this.model.year, this.model.month - 1, this.model.day);
    const todayWeekDay = fechaHoy.getDay();
    if ([1, 3].includes(todayWeekDay)) {
      ref.componentInstance.horasDisponibles = this.horasDisponibles.filter(hora => !["11:00", "12:00", "13:00"].includes(hora));
    }

    ref.result.then((resultado: {evento: Evento}) => {
      const horaSeleccionada = Number(resultado.evento.inicio.split(':')[0]);
      const fechaInicio = new Date(this.model.year, this.model.month - 1, this.model.day);
      fechaInicio.setHours(horaSeleccionada);
      resultado.evento.fecha = fechaInicio;
      this.backendService.agregarEvento(resultado.evento)
      .subscribe(eventoGuardado => {
        this.calendario.push(eventoGuardado);
        this.calendario.sort((a, b) => a.inicio.localeCompare(b.inicio));
        this.horasDisponibles = this.horasDisponibles.filter(hora => hora !== resultado.evento.inicio);
      });
    });
  }

  isEntrenador(): boolean{
    return this.rol?.rol == Rol.ENTRENADOR || this.rol?.rol == Rol.ADMINISTRADOR;
  }
  eventosDelDia(event: Evento):boolean{
    let ano = event.inicio.substring(0,4)
    return true;//ano==this.model.year.toString();
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
  eliminarEvento(id: number): Observable<void> {
    const index = this.calendario.findIndex(evento => evento.id === id);
    if (index !== -1) {
      this.calendario.splice(index, 1);
      localStorage.setItem('eventos', JSON.stringify(this.calendario));
      return of();
    } else {
      return new Observable<void>(observer => {
        observer.error('Evento no encontrado');
      });
    }
  }
}