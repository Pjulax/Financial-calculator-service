package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.Calculator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investments")
public class InvestmentController {


    @Autowired
    Calculator calculator;

    @GetMapping
    public Investment getInvestmnent(@RequestParam(value = "id") Long investment_id){//TODO
        return new Investment();
    }


    @GetMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result calculateInvestment(@RequestBody InvestmentParametersDTO parameters){//TODO
        return calculator.calculateInvestment(parameters);
    }

    @GetMapping("/all")
    public List<Investment> getAllInvestment(){//TODO
        return List.of();
    }

    @PostMapping
    public Investment addInvestment(InvestmentDTO investmentDTO, Optional<Portfolio> porfolio){//TODO
        return new Investment();
    }

    @PutMapping
    public void modifyInvestment(@RequestParam(value = "id") Long investment_id){//TODO

    }

    @DeleteMapping
    public void removeInvestment(@RequestParam(value = "id") Long investment_id){//TODO

    }

}
