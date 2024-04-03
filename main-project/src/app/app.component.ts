import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormularioEventoComponent } from './formulario-evento/formulario-evento.component';
import { FormsModule } from '@angular/forms';
import {evento} from './evento';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule],
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
    
  }
}
export function changeMonth() {
  const year = new Date().getFullYear(); // Año actual
  var m;
  /*if(document.getElementById("monthInput")!=null)
    m = document.getElementById("monthInput");
  const month = m; // Mes seleccionado (-1 porque en JavaScript los meses van de 0 a 11)
  
  */
  const month = 3;
  generateCalendar(year, month);
}
export function generateCalendar(year: number,month:number) {
  const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
      "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
  const daysInMonth = new Date(year, month + 1, 0).getDate(); // Número de días en el mes
  const firstDayOfWeek = new Date(year, month, 0).getDay(); // Día de la semana del primer día del mes
  const calendarBody = document.getElementById("calendarBody");
  const monthYear = document.getElementById("monthYear");
  const monthInput = document.getElementById("monthInput");

  // Limpiar el cuerpo del calendario
  if(calendarBody!=null && monthYear!=null){
    calendarBody.innerHTML = "";
  monthYear.textContent = monthNames[month] + " " + year;

  let date = 1;
  // Generar filas para el calendario
  for (let i = 0; date <= daysInMonth; i++) {
      const row = document.createElement("tr");

      // Generar celdas para cada día de la semana
     // Dentro del bucle for que genera las celdas para cada día de la semana
  for (let j = 0; j < 8; j++) {
    const cell = document.createElement("td");
    cell.textContent="llllllllllll";
    if (j === 0) {
        // Celda de horario
        if (date <= daysInMonth) {
            cell.textContent = date + "h";
        } else {
            cell.textContent = "";
        }
    } else if (i === 0 && j < firstDayOfWeek) {
        // Espacios en blanco para los días anteriores al primer día del mes
        cell.textContent = "";
    } else if (date > daysInMonth) {
        // No mostrar días que superan el número de días en el mes
        cell.textContent = "";
    } else {
        // Celda para mostrar el número del día debajo del nombre del día
        const dayNumberCell = document.createElement("div");
        dayNumberCell.textContent = date.toString();
        cell.appendChild(dayNumberCell);
        cell.appendChild(document.createElement("br")); // Salto de línea para separar el nombre del día del número del día
        cell.textContent += "Editar"; // Agregar texto "Editar" al final de la celda
    }
    row.appendChild(cell);
    if (j === 0) date++;
  }

      calendarBody.appendChild(row);
  }
}
const currentDate = new Date();
generateCalendar(currentDate.getFullYear(), currentDate.getMonth());
}
