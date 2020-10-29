package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.InvestmentService;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

@RestController
@RequestMapping("/investments")
@AllArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;
    private final PortfolioService portfolioService;

    @GetMapping
    public InvestmentDetailsDTO getInvestmentDetails(@RequestParam(value = "id") Long investmentId){
        return investmentService.getInvestment(investmentId);
    }

    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public InvestmentDetailsDTO calculateInvestment(@RequestBody InvestmentParametersDTO parameters){
        return investmentService.calculateInvestment(parameters);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Investment addInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @RequestParam(value = "id") Long portfolioId){
        return portfolioService.addInvestment(investmentDTO,portfolioId);
    }

    @PutMapping
    public Investment modifyInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @RequestParam(value = "id") Long investmentId){
        return investmentService.modifyInvestment(investmentId,investmentDTO);
    }

    @DeleteMapping
    public void removeInvestment(@RequestParam(value = "id") Long investmentId){
        investmentService.removeInvestment(investmentId);
    }

}
