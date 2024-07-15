package br.jus.stj.skeletoApp.controller;

import br.jus.stj.skeletoApp.domain.Municipio;
import br.jus.stj.skeletoApp.model.AeroportoDTO;
import br.jus.stj.skeletoApp.repos.MunicipioRepository;
import br.jus.stj.skeletoApp.service.AeroportoService;
import br.jus.stj.skeletoApp.util.CustomCollectors;
import br.jus.stj.skeletoApp.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/aeroportos")
public class AeroportoController {

    private final AeroportoService aeroportoService;
    private final MunicipioRepository municipioRepository;

    public AeroportoController(final AeroportoService aeroportoService,
                               final MunicipioRepository municipioRepository) {
        this.aeroportoService = aeroportoService;
        this.municipioRepository = municipioRepository;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("aeroportos", aeroportoService.findAll());
        return "aeroporto/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("aeroporto") final AeroportoDTO aeroportoDTO) {
        return "aeroporto/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("aeroporto") @Valid final AeroportoDTO aeroportoDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "aeroporto/add";
        }
        aeroportoService.create(aeroportoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("aeroporto.create.success"));
        return "redirect:/aeroportos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("aeroporto", aeroportoService.get(id));
        model.addAttribute("municipioValues", municipioRepository.findByMunicipioContainingIgnoreCaseOrderByMunicipio(aeroportoService.get(id).getCidade())
                .stream()
                .collect(CustomCollectors.toSortedMap(Municipio::getId, municipio -> municipio.getMunicipio() + " - " + municipio.getUf().getUf())));
        return "aeroporto/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
                       @ModelAttribute("aeroporto") @Valid final AeroportoDTO aeroportoDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "aeroporto/edit";
        }
        aeroportoService.update(id, aeroportoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("aeroporto.update.success"));
        return "redirect:/aeroportos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
                         final RedirectAttributes redirectAttributes) {
        aeroportoService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("aeroporto.delete.success"));
        return "redirect:/aeroportos";
    }

}
