package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentInPorfolioDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;

import java.util.Optional;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    @GetMapping
    public InvestmentDetailsDTO getInvestmnentDetails(@RequestParam(value = "id") Long investment_id){//TODO
        return new InvestmentDetailsDTO();
    }

    @GetMapping("/calculate")
    public InvestmentDetailsDTO calculateInvestment(InvestmentParametersDTO parameters){//TODO
        return new InvestmentDetailsDTO();
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
