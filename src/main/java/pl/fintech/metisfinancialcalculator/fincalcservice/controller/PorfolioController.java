package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PorfolioRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.util.List;

//c@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/porfolios")
public class PorfolioController {

    @Autowired
    PorfolioRepository porfolioRepository;

    @Autowired
    PortfolioService portfolioService;

    @GetMapping("/hello")
    public String hello(){
        return "Welcome in portfolios";

    }

    @GetMapping(path = "/names")
    public List<PortfolioNameDTO> getAllPortfoliosNames(){//TODO
        return portfolioService.getAllPortfioliosNames();
    }

    @GetMapping(path = "/details")
    public PortfolioDetailsDTO getPorfolioDetails(@RequestParam(value = "id") Long porfolio_id){//TODO
        return portfolioService.getPorfolioDetails(porfolio_id);
    }
    @GetMapping(path = "/all-investments-details")
    public PortfolioDetailsDTO getPortfolioAllInvestmentsDetails(){
        return portfolioService.getPortfolioAllInvestmentsDetails();
    }

    @GetMapping
    public Portfolio getPortfolio(@RequestParam(value = "id") Long portfolio_id){//TODO to remove only to tests
        return porfolioRepository.findById(portfolio_id).orElse(null);
    }

    @PostMapping
    public Portfolio createPorfolio(@RequestParam(value = "name") String name){//TODO
        return portfolioService.createPortfolio(name);
    }

    @PutMapping
    public Portfolio modifyPorfolio(@RequestParam(value="new-name") String name, @RequestParam(value = "id") Long porfolio_id){
        return portfolioService.modifyPortfolio(porfolio_id, name);
    }

    @DeleteMapping
    public void removePorfolio(@RequestParam(value = "id") Long porfolio_id){//TODO
        portfolioService.removePortfolio(porfolio_id);
    }

}
