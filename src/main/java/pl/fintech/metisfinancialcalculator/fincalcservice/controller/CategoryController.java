package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final PortfolioService portfolioService;

    @GetMapping
    public String[] getCategories(){
        return portfolioService.getCategories();
    }
}
