package fullstackers.dtos;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntrenadorDTO {
    private Long idUsuario;
    private String telefono;
    private String direccion;
    private String dni;
    private String fechaNacimiento;
    private String fechaAlta;
    private String fechaBaja;
    private String especialidad;
    private String titulacion;
    private String experiencia;
    private String observaciones;
    private Long id; 
}