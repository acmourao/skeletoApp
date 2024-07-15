package br.jus.stj.skeletoApp.rest;

import br.jus.stj.skeletoApp.model.BancoDTO;
import br.jus.stj.skeletoApp.service.BancoService;
import br.jus.stj.skeletoApp.util.ReferencedException;
import br.jus.stj.skeletoApp.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/bancos", produces = MediaType.APPLICATION_JSON_VALUE)
public class BancoResource {

    private final BancoService bancoService;

    public BancoResource(final BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @GetMapping
    public ResponseEntity<List<BancoDTO>> getAllBancos() {
        return ResponseEntity.ok(bancoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancoDTO> getBanco(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(bancoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBanco(@RequestBody @Valid final BancoDTO bancoDTO) {
        final Integer createdId = bancoService.create(bancoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateBanco(@PathVariable(name = "id") final Integer id,
                                               @RequestBody @Valid final BancoDTO bancoDTO) {
        bancoService.update(id, bancoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBanco(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = bancoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        bancoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
