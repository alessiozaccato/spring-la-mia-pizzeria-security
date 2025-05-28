package com.java.lessons.crud1.spring_la_mia_pizzeria_crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.model.Pizza;
import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.repositories.PizzaRepository;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> findAll(){
        return pizzaRepository.findAll();
    }

    public List<Pizza> findAllByName(){
        return pizzaRepository.findAll(Sort.by("name"));
    }

    public List<Pizza> findByName(String name){
        return pizzaRepository.findByNameContaining(name);
    }

    public Optional<Pizza> findById(Integer id){
        return pizzaRepository.findById(id);
    }

    public Pizza getById(Integer id){
        Optional<Pizza> pizzaAttempt = pizzaRepository.findById(id);

        if (pizzaAttempt.isEmpty()) {
            //do something with response entity
        }

        return pizzaAttempt.get();
        // return pizzaRepository.findById(id).get();
    }

    public Pizza create(Pizza pizza){
        return pizzaRepository.save(pizza);
    }

    public Pizza update(Pizza pizza){
        return pizzaRepository.save(pizza);
    }

    public void delete(Pizza pizza){
        pizzaRepository.delete(pizza);
    }

    public void deleteById(Integer id){
        Pizza pizza = pizzaRepository.findById(id).get();

        pizzaRepository.delete(pizza);
    }

    public Boolean existsById(Integer id){
        return pizzaRepository.existsById(id);
    }

    public Boolean exists(Pizza pizza){
        return existsById(pizza.getId());
    }

}
