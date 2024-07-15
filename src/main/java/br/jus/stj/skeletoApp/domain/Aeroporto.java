package br.jus.stj.skeletoApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Aeroporto {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String sigla;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String aeroporto;

    @ManyToOne
    @JoinColumn(name = "municipio_id")
    @JsonIgnore
    private Municipio municipio;

}
