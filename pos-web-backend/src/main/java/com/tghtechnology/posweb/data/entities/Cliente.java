package com.tghtechnology.posweb.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "cliente")
@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank(message = "El nombre del cliente no puede estar vacio")
    @Min(value = 3, message = "El nombre tiene que tener almenos 3 caracteres")
    @Max(value = 50, message = "El nombre no puede tener mas de 50 caracteres")
    private String nombre;

    @Min(value = 3, message = "El apellido tiene que tener almenos 3 caracteres")
    @Max(value = 50, message = "El apellido no puede tener mas de 50 caracteres")
    @NotBlank(message = "El apellido del cliente no puede estar vacio")
    private String apellido;

    @Email(message = "Se debe respetar el formato del correo")
    private String correo;

    @NotBlank(message = "La direccion del cliente no puede estar vacio")
    private String direccion;

    @NotBlank(message = "El numero del cliente no puede estar vacio")
    @Pattern(regexp = "^\\d{6,10}$", message = "El numero debe tener de 6 a 10 digitos")
    private String telefono;

    @NotBlank(message = "El codigo de pais no puede estar vacio")
    @Pattern(regexp = "^\\+\\d{1,3}$", message = "El codigo del pais debe comenzar con +")
    private String codigoPais;

    @NotBlank(message = "El documento no puede ser vacio")
    private String document;

    @NotBlank(message = "Debe ser uno de los tipos de documentos")
    private TipoDocumento tipoDoc;

    public boolean validarNumeroDocumento(){
        if (tipoDoc == null || document == null) {
            return false;
        }

        switch (tipoDoc) {
            case DNI: 
                return document.matches("\\d{8}");

            case RUC10:
                return document.matches("\\d{11}");
            case RUC20: 
                return document.matches("\\d{11}");

            case CARNET_EXTRANJERIA: 
                return document.matches("[A-Z0-9]d{9,12}$");

            case PASAPORTE: 
                return document.matches("[A-Z0-9]d{6,9}");

            case CEDULA_DIPLOMATICA: 
                return document.matches("[A-Z0-9]d{9,12}$");            
            default:
                return false;
        }

    }

}
