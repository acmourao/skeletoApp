package br.jus.stj.skeletoApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Estado {

    @Id
    @Column(nullable = false, updatable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 127)
    private String name;

    @Column(length = 127)
    private String gentilic;

    @Column(length = 127)
    private String gentilicAlternative;

    @Column(length = 127)
    private String macroregion;

    @Column(length = 127)
    private String website;

    @Column(length = 127)
    private String timezone;

    @Column(length = 127)
    private String flagImage;

    @OneToMany(mappedBy = "municipio")
    //@JsonIgnore
    private Set<Municipio> municipios;

}
