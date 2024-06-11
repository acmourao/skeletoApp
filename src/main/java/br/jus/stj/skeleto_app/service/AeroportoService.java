package br.jus.stj.skeleto_app.service;

import br.jus.stj.skeleto_app.domain.Aeroporto;
import br.jus.stj.skeleto_app.domain.Municipio;
import br.jus.stj.skeleto_app.model.AeroportoDTO;
import br.jus.stj.skeleto_app.repos.AeroportoRepository;
import br.jus.stj.skeleto_app.repos.MunicipioRepository;
import br.jus.stj.skeleto_app.util.NotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AeroportoService {


    @Autowired
    private Environment env;

    private final AeroportoRepository aeroportoRepository;
    private final MunicipioRepository municipioRepository;

    public AeroportoService(final AeroportoRepository aeroportoRepository,
                            final MunicipioRepository municipioRepository) {
        this.aeroportoRepository = aeroportoRepository;
        this.municipioRepository = municipioRepository;
    }

    public List<AeroportoDTO> findAll() {
        return aeroportoRepository
                .findAll(Sort.by("sigla"))
                .stream()
                .limit(20)
                .map(aeroporto -> mapToDTO(aeroporto, new AeroportoDTO()))
                .toList();
    }

    public AeroportoDTO get(final Integer id) {
        return aeroportoRepository.findById(id)
                .map(aeroporto -> mapToDTO(aeroporto, new AeroportoDTO()))
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

    private AeroportoDTO mapToDTO(final Aeroporto aeroporto, final AeroportoDTO aeroportoDTO) {
        aeroportoDTO.setId(aeroporto.getId());
        aeroportoDTO.setSigla(aeroporto.getSigla());
        aeroportoDTO.setUf(aeroporto.getUf());
        aeroportoDTO.setCidade(aeroporto.getCidade());
        aeroportoDTO.setAeroporto(aeroporto.getAeroporto());
        aeroportoDTO.setLocalidade(aeroporto.getLocalidade() == null ? null : aeroporto.getLocalidade());
        return aeroportoDTO;
    }

    private Aeroporto mapToEntity(final AeroportoDTO aeroportoDTO, final Aeroporto aeroporto) {
        aeroporto.setSigla(aeroportoDTO.getSigla());
        aeroporto.setUf(aeroportoDTO.getUf());
        aeroporto.setCidade(aeroportoDTO.getCidade());
        aeroporto.setAeroporto(aeroportoDTO.getAeroporto());
        final Municipio localidade = aeroportoDTO.getLocalidade() == null ? null : municipioRepository.findById(aeroportoDTO.getLocalidade().getId())
                .orElseThrow(() -> new NotFoundException("localidade not found"));
        aeroporto.setLocalidade(localidade);
        return aeroporto;
    }

    public boolean siglaExists(final String sigla) {
        return aeroportoRepository.existsBySiglaIgnoreCase(sigla);
    }

}
