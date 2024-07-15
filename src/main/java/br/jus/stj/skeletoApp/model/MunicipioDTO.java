package br.jus.stj.skeletoApp.model;

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
    private String municipio;

    @Size(max = 2)
    private String uf;

}
