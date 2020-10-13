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
    Calculator calculator;

    @GetMapping
    public InvestmentDetailsDTO getInvestmnentDetails(@RequestParam(value = "id") Long investment_id){//TODO
        return new InvestmentDetailsDTO();
    }


    @GetMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public InvestmentDetailsDTO calculateInvestment(@RequestBody InvestmentParametersDTO parameters){//TODO
        InvestmentDetailsDTO investment = new InvestmentDetailsDTO();
        // not used, write down just for clarity
        investment.setCategory(null);
        investment.setRisk(null);
        investment.setName(null);
        // values from parameters
        investment.setStartDate(Calendar.getInstance().getTime());
        investment.setInitialDepositValue(parameters.getInitialDepositValue());
        investment.setSystematicDepositValue(parameters.getSystematicDepositValue());
        investment.setDurationInYears(parameters.getDurationInYears());
        investment.setFrequenceInYear(parameters.getFrequenceInYear());
        investment.setReturnOfInvestmentPercentage(parameters.getReturnOfInvestment());
        // values from result
        Result result = calculator.calculateInvestment(parameters);
        investment.setAnnualRateOfReturnValue(result.getAnnualRateOfReturnValue());
        investment.setAnnualRateOfReturnPercentage(BigDecimal.valueOf(result.getRateOfReturnPercentage()).divide(BigDecimal.valueOf(parameters.getDurationInYears()), RoundingMode.FLOOR).doubleValue());
        investment.setRateOfReturnValue(result.getRateOfReturnValue());
        investment.setRateOfReturnPercentage(result.getRateOfReturnPercentage());
        investment.setGraphPointsValue(result.getGraphPointValues());
        investment.setXaxisDataType(result.getXAxisDataType());
        investment.setYaxisDataType(result.getYAxisDataType());
        return investment;
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
