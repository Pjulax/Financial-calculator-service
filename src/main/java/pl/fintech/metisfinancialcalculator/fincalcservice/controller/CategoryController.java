package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentInPortfolioDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    PortfolioService portfolioService;

    @Autowired
    CategoryController(PortfolioService portfolioService){
        this.portfolioService = portfolioService;
    }


    @GetMapping
    public String[] getCategories(){
        return portfolioService.getCategories();
    }
}
