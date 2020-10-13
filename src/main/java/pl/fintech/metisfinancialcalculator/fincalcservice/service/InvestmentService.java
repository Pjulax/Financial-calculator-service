package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

@Service
public class InvestmentService {

    InvestmentRepository investmentRepository;

    @Autowired
    Calculator calculator;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository){
        this.investmentRepository = investmentRepository;
    }

    public InvestmentDetailsDTO getInvestment(Long investment_id){
        return new InvestmentDetailsDTO(); //TODO
    }
    public List<Investment> getAllInvestments(){
        return List.of(); //TODO
    }

    public InvestmentDetailsDTO calculateInvestment(InvestmentParametersDTO parameters){
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
        investment.setAnnualRateOfReturnPercentage(BigDecimal.valueOf(result.getRateOfReturnPercentage()).divide(BigDecimal.valueOf(parameters.getDurationInYears()), RoundingMode.FLOOR).doubleValue());
        investment.setRateOfReturnValue(result.getRateOfReturnValue());
        investment.setRateOfReturnPercentage(result.getRateOfReturnPercentage());
        investment.setGraphPointsValue(result.getGraphPointValues());
        investment.setXaxisDataType(result.getXAxisDataType());
        investment.setYaxisDataType(result.getYAxisDataType());
        return investment;
    }
    public void modifyInvestment(Long investment_id){
        //TODO
    }
    public void removeInvestment(Long investment_id){
        //TODO
    }
}
