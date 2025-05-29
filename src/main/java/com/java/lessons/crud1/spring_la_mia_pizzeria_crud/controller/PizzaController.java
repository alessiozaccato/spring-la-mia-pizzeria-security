package com.java.lessons.crud1.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.model.Pizza;
import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.model.SpecialOffer;
import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.repositories.IngredientRepository;
import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.repositories.SpecialOfferRepository;
import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.service.PizzaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Authentication authentication, Model model) {
        List<Pizza> pizzas = pizzaService.findAll();
        model.addAttribute("pizzas", pizzas);
        model.addAttribute("username", authentication.getName());
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = pizzaService.getById(id);
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name = "name") String name, Model model) {
        List<Pizza> pizzas = pizzaService.findByName(name);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindimgResult, Model model) {
        if (bindimgResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());

            return "pizzas/create";
        }

        pizzaService.create(formPizza);

        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("pizza", pizzaService.getById(id));
        model.addAttribute("edit", true);
        model.addAttribute("ingredients", ingredientRepository.findAll());

        return "pizzas/create";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindimgResult, Model model) {
        if (bindimgResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());

            return "pizzas/create";
        }

        pizzaService.update(formPizza);

        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        Pizza pizza = pizzaService.getById(id);

        for (SpecialOffer specialOfferToDelete : pizza.getSpecialOffers()) {
            specialOfferRepository.delete(specialOfferToDelete);
        }
        pizzaService.delete(pizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/specialOffer")
    public String offer(@PathVariable Integer id, Model model) {
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setPizza(pizzaService.getById(id));
        model.addAttribute("specialOffer", specialOffer);
        return "specialOffers/create";
    }
}
