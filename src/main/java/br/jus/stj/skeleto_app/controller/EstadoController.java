package br.jus.stj.skeleto_app.controller;

import br.jus.stj.skeleto_app.model.EstadoDTO;
import br.jus.stj.skeleto_app.service.EstadoService;
import br.jus.stj.skeleto_app.util.ReferencedWarning;
import br.jus.stj.skeleto_app.util.WebUtils;
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
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(final EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("estados", estadoService.findAll());
        return "estado/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("estado") final EstadoDTO estadoDTO) {
        return "estado/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("estado") @Valid final EstadoDTO estadoDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "estado/add";
        }
        estadoService.create(estadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("estado.create.success"));
        return "redirect:/estados";
    }

    @GetMapping("/edit/{uf}")
    public String edit(@PathVariable(name = "uf") final String uf, final Model model) {
        model.addAttribute("estado", estadoService.get(uf));
        return "estado/edit";
    }

    @PostMapping("/edit/{uf}")
    public String edit(@PathVariable(name = "uf") final String uf,
                       @ModelAttribute("estado") @Valid final EstadoDTO estadoDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "estado/edit";
        }
        estadoService.update(uf, estadoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("estado.update.success"));
        return "redirect:/estados";
    }

    @PostMapping("/delete/{uf}")
    public String delete(@PathVariable(name = "uf") final String uf,
                         final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = estadoService.getReferencedWarning(uf);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            estadoService.delete(uf);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("estado.delete.success"));
        }
        return "redirect:/estados";
    }

}
