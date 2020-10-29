package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
@AllArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping(path = "/hello")
    public String hello(){
        return "hello";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping(path = "/hello2")
    public String hello2(){
        return "hello";
    }

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
    public PortfolioDetailsDTO getPortfolio(@RequestParam(value = "id") Long portfolioId){
        return portfolioService.getPortfolioDetails(portfolioId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Portfolio createPortfolio(@RequestParam(value = "name") String name){
        return portfolioService.createPortfolio(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Portfolio modifyPortfolio(@RequestParam(value="new-name") String name, @RequestParam(value = "id") Long portfolioId){
        return portfolioService.modifyPortfolio(portfolioId, name);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public void removePortfolio(@RequestParam(value = "id") Long portfolioId){
        portfolioService.removePortfolio(portfolioId);
    }

}
