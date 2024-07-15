package br.jus.stj.skeletoApp.controller;

import br.jus.stj.skeletoApp.domain.Estado;
import br.jus.stj.skeletoApp.model.MunicipioDTO;
import br.jus.stj.skeletoApp.repos.EstadoRepository;
import br.jus.stj.skeletoApp.service.MunicipioService;
import br.jus.stj.skeletoApp.util.CustomCollectors;
import br.jus.stj.skeletoApp.util.ReferencedWarning;
import br.jus.stj.skeletoApp.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/municipios")
public class MunicipioController {

    private final MunicipioService municipioService;
    private final EstadoRepository estadoRepository;

    public MunicipioController(final MunicipioService municipioService,
                               final EstadoRepository estadoRepository) {
        this.municipioService = municipioService;
        this.estadoRepository = estadoRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("ufValues", estadoRepository.findAll(Sort.by("uf"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Estado::getUf, Estado::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("municipios", municipioService.findAll());
        return "municipio/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("municipio") final MunicipioDTO municipioDTO) {
        return "municipio/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("municipio") @Valid final MunicipioDTO municipioDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "municipio/add";
        }
        municipioService.create(municipioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("municipio.create.success"));
        return "redirect:/municipios";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("municipio", municipioService.get(id));
        return "municipio/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
                       @ModelAttribute("municipio") @Valid final MunicipioDTO municipioDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "municipio/edit";
        }
        municipioService.update(id, municipioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("municipio.update.success"));
        return "redirect:/municipios";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
                         final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = municipioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            municipioService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("municipio.delete.success"));
        }
        return "redirect:/municipios";
    }

}
