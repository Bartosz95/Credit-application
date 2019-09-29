package bolo.spring.creditapplication.controllers;

import bolo.spring.creditapplication.domain.Credit;
import bolo.spring.creditapplication.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credit")
public class CreditController {

    @Autowired
    private CreditRepository repository;

    @PostMapping
    public @ResponseBody
    long createCredit(@RequestBody String creditName){
        Credit credit = Credit.builder()
                .creditName(creditName)
                .build();
        return repository.save(credit).getId();
    }

    @GetMapping
    public @ResponseBody
    Iterable<Credit> getCredits(){
      return  repository.findAll();
    }
}
