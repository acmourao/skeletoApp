package br.jus.stj.skeletoApp.service;

import br.jus.stj.skeletoApp.domain.Banco;
import br.jus.stj.skeletoApp.domain.Usuario;
import br.jus.stj.skeletoApp.model.BancoDTO;
import br.jus.stj.skeletoApp.repos.BancoRepository;
import br.jus.stj.skeletoApp.repos.UsuarioRepository;
import br.jus.stj.skeletoApp.util.NotFoundException;
import br.jus.stj.skeletoApp.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BancoService {

    private final BancoRepository bancoRepository;
    private final UsuarioRepository usuarioRepository;

    public BancoService(final BancoRepository bancoRepository,
                        final UsuarioRepository usuarioRepository) {
        this.bancoRepository = bancoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<BancoDTO> findAll() {
        return bancoRepository.findAll(Sort.by("compe")).stream().limit(20)
                .map(banco -> mapToDTO(banco, new BancoDTO()))
                .toList();
    }

    public BancoDTO get(final Integer id) {
        return bancoRepository.findById(id)
                .map(banco -> mapToDTO(banco, new BancoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BancoDTO bancoDTO) {
        final Banco banco = new Banco();
        mapToEntity(bancoDTO, banco);
        return bancoRepository.save(banco).getId();
    }

    public void update(final Integer id, final BancoDTO bancoDTO) {
        final Banco banco = bancoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bancoDTO, banco);
        bancoRepository.save(banco);
    }

    public void delete(final Integer id) {
        bancoRepository.deleteById(id);
    }

    private BancoDTO mapToDTO(final Banco banco, final BancoDTO bancoDTO) {
        bancoDTO.setId(banco.getId());
        bancoDTO.setCompe(banco.getCompe());
        bancoDTO.setCnpj(banco.getCnpj());
        bancoDTO.setRazaoSocial(banco.getRazaoSocial());
        bancoDTO.setNome(banco.getNome());
        bancoDTO.setProdutos(banco.getProdutos());
        bancoDTO.setUrl(banco.getUrl());
        return bancoDTO;
    }

    private Banco mapToEntity(final BancoDTO bancoDTO, final Banco banco) {
        banco.setCompe(bancoDTO.getCompe());
        banco.setCnpj(bancoDTO.getCnpj());
        banco.setRazaoSocial(bancoDTO.getRazaoSocial());
        banco.setNome(bancoDTO.getNome());
        banco.setProdutos(bancoDTO.getProdutos());
        banco.setUrl(bancoDTO.getUrl());
        return banco;
    }

    public boolean compeExists(final String compe) {
        return bancoRepository.existsByCompeIgnoreCase(compe);
    }

    public boolean cnpjExists(final String cnpj) {
        return bancoRepository.existsByCnpjIgnoreCase(cnpj);
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Banco banco = bancoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Usuario bancoUsuario = usuarioRepository.findFirstByBanco(banco);
        if (bancoUsuario != null) {
            referencedWarning.setKey("banco.usuario.banco.referenced");
            referencedWarning.addParam(bancoUsuario.getId());
            return referencedWarning;
        }
        return null;
    }

}
