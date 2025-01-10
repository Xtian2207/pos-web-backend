package com.tghtechnology.posweb.data.entities;

import java.beans.Transient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "cliente")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank(message = "El nombre del cliente no puede estar vacio")
    @Size(min = 3, max = 50 ,message = "El nombre tiene que tener almenos 3 caracteres y no mayor a 50")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(min = 3, max = 50 ,message = "El apellido tiene que tener almenos 3 caracteres y no mayor a 50")
    @NotBlank(message = "El apellido del cliente no puede estar vacio")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Email(message = "Se debe respetar el formato del correo")
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @NotBlank(message = "La dirección del cliente no puede estar vacía")
    @Column(name = "direccion", nullable = false, length = 100)
    private String direccion;


    @NotBlank(message = "El número del cliente no puede estar vacío")
    @Column(name = "telefono", nullable = false, length = 10)
    private String telefono;

    @NotBlank(message = "El código de país no puede estar vacío")
    @Pattern(regexp = "^\\+\\d{1,3}$", message = "El código del país debe comenzar con '+' seguido de 1 a 3 dígitos")
    @Column(name = "codigo_pais", nullable = false, length = 4)
    private String codigoPais;

    @NotBlank(message = "El documento no puede ser vacio")
    @Column(name = "documento", nullable = false, length = 100, unique = true)
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false, length = 50)
    private TipoDocumento tipoDoc;


    @Transient
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
                return document.matches("[A-Z0-9]{9,12}");

            case PASAPORTE: 
                return document.matches("[A-Z0-9]{6,9}");

            case CEDULA_DIPLOMATICA: 
                return document.matches("[A-Z0-9]{9,12}");            
            default:
                return false;
        }

    }

}
