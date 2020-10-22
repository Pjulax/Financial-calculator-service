package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.Calculator;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.InvestmentService;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    @GetMapping("/hello")
    public String hello(){
        return "Welcome in investments";
    }

    @Autowired
    InvestmentService investmentService;

    @Autowired
    PortfolioService portfolioService;

    @GetMapping
    public InvestmentDetailsDTO getInvestmnentDetails(@RequestParam(value = "id") Long investment_id){//TODO
        return new InvestmentDetailsDTO();
    }


    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public InvestmentDetailsDTO calculateInvestment(@RequestBody InvestmentParametersDTO parameters){//TODO
        return investmentService.calculateInvestment(parameters);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Investment addInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @RequestParam(value = "id") Long portfolio_id){//TODO
        return portfolioService.addInvestment(investmentDTO,portfolio_id);
    }

    @PutMapping
    public Investment modifyInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @RequestParam(value = "id") Long investment_id){//TODO
        return investmentService.modifyInvestment(investment_id,investmentDTO);
    }

    @DeleteMapping
    public void removeInvestment(@RequestParam(value = "id") Long investment_id){//TODO
        investmentService.removeInvestment(investment_id);
    }

}
