package com.java.lessons.crud1.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.model.Pizza;
import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.service.PizzaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/pizzas")
@CrossOrigin
public class PizzaRestController {

    @Autowired
    private PizzaService service;

    @GetMapping
    public List<Pizza> index() {
        List<Pizza> pizzas = service.findAll();
        return pizzas;
    }

    @GetMapping("/sortByName")
    public List<Pizza> indexByName() {
        List<Pizza> pizzas = service.findAllByName();
        return pizzas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@PathVariable Integer id) {
        Optional<Pizza> pizzaAttempt = service.findById(id);
        if (pizzaAttempt.isEmpty()) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pizza>(pizzaAttempt.get(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizza) {
        return new ResponseEntity<Pizza>(service.create(pizza), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Pizza> patch(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        if (!service.existsById(id)) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        pizza.setId(id);
        return new ResponseEntity<Pizza>(service.update(pizza), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Pizza> delete(@PathVariable Integer id) {
        if (!service.existsById(id)) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);

        }
        service.deleteById(id);
        return new ResponseEntity<Pizza>(HttpStatus.OK);

    }

}
