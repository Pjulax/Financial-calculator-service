package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;

import java.util.List;

@RestController
@RequestMapping("/porfolios")
public class PorfolioController {

    @GetMapping("/hello")
    public String hello(){
        return "Welcome in portfolios";
    }

    @GetMapping(path = "/names")
    public List<PortfolioNameDTO> getAllPortfoliosNames(){//TODO
        return List.of();
    }

    @GetMapping(path = "/details")
    public PortfolioDetailsDTO getPorfolioDetails(@RequestParam(value = "id") Long porfolio_id){//TODO
        return new PortfolioDetailsDTO();
    }
    @GetMapping(path = "/all-investments-details")
    public PortfolioDetailsDTO getPortfolioAllInvestmentsDetails(){
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
