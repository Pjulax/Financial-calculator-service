package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    @GetMapping
    public Investment getInvestmnent(@RequestParam(value = "id") Long investment_id){//TODO
        return new Investment();
    }

    @GetMapping("/calculate")
    public InvestmentDetailsDTO calculateInvestment(InvestmentDTO investmentDTO){//TODO
        return new InvestmentDetailsDTO();
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
