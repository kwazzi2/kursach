package by.bsuir.webapp.controller;

import by.bsuir.webapp.dto.PriceListItemDto;
import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.tutor.PriceListItem;
import by.bsuir.webapp.model.tutor.Type;
import by.bsuir.webapp.repository.tutor.PriceListItemRepository;
import by.bsuir.webapp.repository.tutor.SubjectRepository;
import by.bsuir.webapp.service.PriceListItemService;
import by.bsuir.webapp.service.security.PersitedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PriceListItemController {
    @Autowired
    PriceListItemRepository priceListItemRepository;
    @Autowired
    PriceListItemService priceListItemService;
    @Autowired
    SubjectRepository subjectRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/tutor/price-list")
    public String getPriceList(@AuthenticationPrincipal PersitedUser user, Model model,
                            @RequestParam Optional<String> exception) {
        exception.ifPresent(c ->model.addAttribute("exception", c));
        model.addAttribute("priceList", priceListItemRepository.findByTutorId(user.getAccountId()));
        return "/tutor/profile/price-list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/pricelist-item/{itemId}/delete")
    public String deletePriceListItem(@AuthenticationPrincipal PersitedUser user,  @PathVariable Long itemId , Model model) throws NotFoundEntityException {
        PriceListItem item = priceListItemRepository.findByIdAndTutorId(itemId, user.getAccountId())
                .orElseThrow(NotFoundEntityException::new);
        if(priceListItemRepository.countByTutorId(user.getAccountId()) == 1L) {
            model.addAttribute("exception", "Не может быть меньше 1");
            return "redirect:/tutor/price-list";
        }
        priceListItemRepository.delete(item);
        return "redirect:/tutor/price-list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pricelist-item/editor")
    public String getPriceListItemForm(@RequestParam Optional<Long> itemId, Model model) throws NotFoundEntityException {
        if(itemId.isEmpty()) {
            model.addAttribute("form", new PriceListItemDto());
            model.addAttribute("typeList", Type.values());
            model.addAttribute("subjectList", subjectRepository.findAll());
            return "/tutor/profile/pricelist-item-form";
        }
        PriceListItem item = priceListItemRepository.findById(itemId.get())
                .orElseThrow(NotFoundEntityException::new);
        model.addAttribute("item", item);
        model.addAttribute("form", new PriceListItemDto());
        model.addAttribute("typeList", Type.values());
        model.addAttribute("subjectList", subjectRepository.findAll());
        return "/tutor/profile/pricelist-item-form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save-pricelist-item")
    public String savePriceListItem(@ModelAttribute("form") @Valid PriceListItemDto dto,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal PersitedUser user,
                            @RequestParam Optional<Long> itemId, Model model) throws NotFoundEntityException {
        if(bindingResult.hasErrors()) {
            model.addAttribute("typeList", Type.values());
            model.addAttribute("subjectList", subjectRepository.findAll());
            return "/tutor/profile/pricelist-item-form";
        }
        if(itemId.isPresent()) {
            priceListItemService.update(user.getAccountId(), itemId.get(), dto);
        } else {
           priceListItemService.create(user.getAccountId(), dto);
        }
        return "redirect:/tutor/price-list";
    }

}
