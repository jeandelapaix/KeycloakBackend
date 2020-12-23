package keyclocktesting.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import keyclocktesting.model.Hero;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    private List<Hero> someHeroes = Arrays.asList(
            new Hero(1, "Ken"),
            new Hero(2, "Yannick"),
            new Hero(3, "Pieter"));

    @GetMapping
    @RolesAllowed("heroes-user")
    public List<Hero> heroes() {
        return someHeroes;
    }

    @GetMapping("/{id}")
    @RolesAllowed("heroes-admin")
    public Hero hero(@PathVariable("id") String id) {
        return someHeroes.stream()
                .filter(h -> Integer.toString(h.getId()).equals(id))
                .findFirst()
                .orElse(null);
    }
}

