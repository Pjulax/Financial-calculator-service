package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PortfolioRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.util.List;

//c@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    @Autowired
    PortfolioRepository portfolioRepository;

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
    public PortfolioDetailsDTO getPortfolioDetails(@RequestParam(value = "id") Long portfolio_id){//TODO
        return portfolioService.getPortfolioDetails(portfolio_id);
    }
    @GetMapping(path = "/all-investments-details")
    public PortfolioDetailsDTO getPortfolioAllInvestmentsDetails(){
        return portfolioService.getPortfolioAllInvestmentsDetails();
    }

    @GetMapping
    public Portfolio getPortfolio(@RequestParam(value = "id") Long portfolio_id){//TODO to remove only to tests
        return portfolioRepository.findById(portfolio_id).orElse(null);
    }

    @PostMapping
    public Portfolio createPortfolio(@RequestParam(value = "name") String name){//TODO
        return portfolioService.createPortfolio(name);
    }

    @PutMapping
    public Portfolio modifyPortfolio(@RequestParam(value="new-name") String name, @RequestParam(value = "id") Long portfolio_id){
        return portfolioService.modifyPortfolio(portfolio_id, name);
    }

    @DeleteMapping
    public void removePortfolio(@RequestParam(value = "id") Long portfolio_id){//TODO
        portfolioService.removePortfolio(portfolio_id);
    }

}
