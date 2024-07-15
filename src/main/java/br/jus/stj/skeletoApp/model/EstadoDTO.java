package br.jus.stj.skeletoApp.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EstadoDTO {

    @Size(max = 2)
    @EstadoUfValid
    private String uf;

    @NotNull
    @Size(max = 127)
    private String name;

    @Size(max = 127)
    private String gentilic;

    @Size(max = 127)
    private String gentilicAlternative;

    @Size(max = 127)
    private String macroregion;

    @Size(max = 127)
    private String website;

    @Size(max = 127)
    private String timezone;

    @Size(max = 127)
    private String flagImage;

}
