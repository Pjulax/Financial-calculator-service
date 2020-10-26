package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(path = "/names")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<PortfolioNameDTO> getAllPortfoliosNames(){
        return portfolioService.getAllPortfoliosNames();
    }

    @GetMapping(path = "/all-investments-details")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public PortfolioDetailsDTO getPortfolioAllInvestmentsDetails(){
        return portfolioService.getPortfolioAllInvestmentsDetails();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public PortfolioDetailsDTO getPortfolio(@RequestParam(value = "id") Long portfolio_id){
        return portfolioService.getPortfolioDetails(portfolio_id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Portfolio createPortfolio(@RequestParam(value = "name") String name){
        return portfolioService.createPortfolio(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Portfolio modifyPortfolio(@RequestParam(value="new-name") String name, @RequestParam(value = "id") Long portfolio_id){
        return portfolioService.modifyPortfolio(portfolio_id, name);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public void removePortfolio(@RequestParam(value = "id") Long portfolio_id){
        portfolioService.removePortfolio(portfolio_id);
    }

}
