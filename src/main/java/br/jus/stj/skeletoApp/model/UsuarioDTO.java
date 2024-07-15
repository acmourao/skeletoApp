package br.jus.stj.skeletoApp.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nome;

    @NotNull
    @Size(max = 11)
    private String cpf;

    @NotNull
    @Size(max = 120)
    @UsuarioEmailUnique
    private String email;

    @NotNull
    @Size(max = 60)
    private String senha;

    @NotNull
    private Boolean active;

    @Size(max = 20)
    private String telefone;

    private LocalDate nascimento;

    @UsuarioBancoUnique
    private Integer banco;

    @UsuarioDomicilioUnique
    private Integer domicilio;

}
