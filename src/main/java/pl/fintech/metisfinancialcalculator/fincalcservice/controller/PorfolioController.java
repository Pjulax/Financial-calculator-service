package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNamesDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;

@RestController
@RequestMapping("/porfolios")
public class PorfolioController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping(path = "/names")
    public PortfolioNamesDTO getAllPorfoliosNames(){//TODO
        return new PortfolioNamesDTO();
    }

    @GetMapping(path = "/details")
    public PortfolioDetailsDTO getPorfolioDetails(@RequestParam(value = "id") Long porfolio_id){//TODO
        return new PortfolioDetailsDTO();
    }

    @GetMapping
    public Portfolio getPorfolio(@RequestParam(value = "id") Long porfolio_id){//TODO
        return new Portfolio();
    }

    @PostMapping
    public Portfolio createPorfolio(@RequestParam(value = "name") String name){//TODO
        return new Portfolio();
    }

    @PutMapping
    public void modifyPorfolio(@RequestParam(value = "id") Long porfolio_id){//TODO

    }

    @DeleteMapping
    public void removePorfolio(@RequestParam(value = "id") Long porfolio_id){//TODO

    }

}
