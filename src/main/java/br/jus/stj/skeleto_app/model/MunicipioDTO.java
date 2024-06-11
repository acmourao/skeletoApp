package br.jus.stj.skeleto_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MunicipioDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    @MunicipioMunicipioUnique
    private String municipio;

    @Size(max = 2)
    private String uf;

}
