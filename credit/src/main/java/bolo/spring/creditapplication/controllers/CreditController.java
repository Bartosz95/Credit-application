package bolo.spring.creditapplication.controllers;

import bolo.spring.creditapplication.domain.Credit;
import bolo.spring.creditapplication.repository.CreditRepository;
import bolo.spring.creditapplication.responses.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${server.path}")
public class CreditController {

    @Value("${products.url}")
    String productUrl;
    @Value("${customers.url}")
    String customersUrl;

    @Autowired
    private CreditRepository repository;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public @ResponseBody
    long createCredit(@RequestBody String name){

        // todo repair this function
        ProductResponse products = restTemplate.getForObject(productUrl, ProductResponse.class);
        System.out.println(productUrl);
        System.out.println(productUrl.toString());


        Credit credit = Credit.builder()
                .name(name)
                .build();

        return repository.save(credit).getId();
    }

    @GetMapping
    public @ResponseBody
    Iterable<Credit> getCredits(){
      return repository.findAll();
    }
}
