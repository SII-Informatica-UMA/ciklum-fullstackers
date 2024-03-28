/*export interface evento {
    id: number;
    nombre: string;
    hora: string;
    lugar: string;
  }
  */
export interface evento {
    nombre: string;
    descripcion: string;
    observaciones: string;
    lugar: string;
    duracionMinutos: number;
    inicio: string;
    reglaRecurrencia: string;
    idCliente: number;
    id: number;
}