package br.jus.stj.skeleto_app.controller;

import br.jus.stj.skeleto_app.model.BancoDTO;
import br.jus.stj.skeleto_app.service.BancoService;
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
@RequestMapping("/bancos")
public class BancoController {

    private final BancoService bancoService;

    public BancoController(final BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bancoes", bancoService.findAll());
        return "banco/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("banco") final BancoDTO bancoDTO) {
        return "banco/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("banco") @Valid final BancoDTO bancoDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "banco/add";
        }
        bancoService.create(bancoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("banco.create.success"));
        return "redirect:/bancos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("banco", bancoService.get(id));
        return "banco/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
                       @ModelAttribute("banco") @Valid final BancoDTO bancoDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "banco/edit";
        }
        bancoService.update(id, bancoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("banco.update.success"));
        return "redirect:/bancos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
                         final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = bancoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            bancoService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("banco.delete.success"));
        }
        return "redirect:/bancos";
    }

}
