package br.jus.stj.skeletoApp.service;

import br.jus.stj.skeletoApp.domain.Aeroporto;
import br.jus.stj.skeletoApp.domain.Municipio;
import br.jus.stj.skeletoApp.model.AeroportoDTO;
import br.jus.stj.skeletoApp.repos.AeroportoRepository;
import br.jus.stj.skeletoApp.repos.MunicipioRepository;
import br.jus.stj.skeletoApp.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AeroportoService {

    private final AeroportoRepository aeroportoRepository;
    private final MunicipioRepository municipioRepository;

    public AeroportoService(final AeroportoRepository aeroportoRepository,
                            final MunicipioRepository municipioRepository) {
        this.aeroportoRepository = aeroportoRepository;
        this.municipioRepository = municipioRepository;
    }

    public List<Aeroporto> findAll() {
        return aeroportoRepository.findAll(Sort.by("sigla"))
                .stream()
                .limit(20).toList();
    }

    public Aeroporto get(final Integer id) {
        return aeroportoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AeroportoDTO aeroportoDTO) {
        final Aeroporto aeroporto = new Aeroporto();
        mapToEntity(aeroportoDTO, aeroporto);
        return aeroportoRepository.save(aeroporto).getId();
    }

    public void update(final Integer id, final AeroportoDTO aeroportoDTO) {
        final Aeroporto aeroporto = aeroportoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(aeroportoDTO, aeroporto);
        aeroportoRepository.save(aeroporto);
    }

    public void delete(final Integer id) {
        aeroportoRepository.deleteById(id);
    }

    private Aeroporto mapToEntity(final AeroportoDTO aeroportoDTO, final Aeroporto aeroporto) {
        aeroporto.setSigla(aeroportoDTO.getSigla());
        aeroporto.setUf(aeroportoDTO.getUf());
        aeroporto.setCidade(aeroportoDTO.getCidade());
        aeroporto.setAeroporto(aeroportoDTO.getAeroporto());
        final Municipio municipio = aeroportoDTO.getMunicipio() == null ? null : municipioRepository.findById(aeroportoDTO.getMunicipio().getId())
                .orElseThrow(NotFoundException::new);
        aeroporto.setMunicipio(municipio);
        return aeroporto;
    }

    public boolean siglaExists(final String sigla) {
        return aeroportoRepository.existsBySiglaIgnoreCase(sigla);
    }

}
