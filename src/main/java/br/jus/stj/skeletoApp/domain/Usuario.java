package br.jus.stj.skeletoApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 60)
    private String senha;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean active;

    @Column(length = 20)
    private String telefone;

    @Column
    private LocalDate nascimento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banco_id", unique = true)
    private Banco banco;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domicilio_id", unique = true)
    private Municipio domicilio;

}
