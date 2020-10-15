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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    @GetMapping("/hello")
    public String hello(){
        return "Welcome in investments";
    }

    @Autowired
    InvestmentService investmentService;

    @GetMapping
    public InvestmentDetailsDTO getInvestmnentDetails(@RequestParam(value = "id") Long investment_id){//TODO
        return new InvestmentDetailsDTO();
    }


    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public InvestmentDetailsDTO calculateInvestment(@RequestBody InvestmentParametersDTO parameters){//TODO
        return investmentService.calculateInvestment(parameters);
    }


    @PostMapping
    public Investment addInvestment(InvestmentDetailsDTO investmentDTO, Optional<Portfolio> porfolio){//TODO
        return new Investment();
    }

    @PutMapping
    public void modifyInvestment(@RequestParam(value = "id") Long investment_id){//TODO

    }

    @DeleteMapping
    public void removeInvestment(@RequestParam(value = "id") Long investment_id){//TODO

    }

}
