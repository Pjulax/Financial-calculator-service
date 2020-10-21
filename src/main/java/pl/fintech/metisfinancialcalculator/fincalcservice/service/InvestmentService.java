package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.ResultRespotiory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class InvestmentService {

    InvestmentRepository investmentRepository;

    ResultRespotiory resultRespotiory;

    @Autowired
    Calculator calculator;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository, ResultRespotiory resultRespotiory){
        this.investmentRepository = investmentRepository;
        this.resultRespotiory = resultRespotiory;
    }

    public InvestmentDetailsDTO getInvestment(Long investment_id){
        return new InvestmentDetailsDTO(); //TODO
    }
    public List<Investment> getAllInvestments(){
        return investmentRepository.findAll();
    }

    public InvestmentDetailsDTO calculateInvestment(InvestmentParametersDTO parameters){
        InvestmentDetailsDTO investment = new InvestmentDetailsDTO();
        // not used, write down just for clarity
        investment.setCategory(null);
        investment.setRisk(null);
        investment.setName(null);
        // values from parameters
        investment.setInitialDepositValue(parameters.getInitialDepositValue());
        investment.setSystematicDepositValue(parameters.getSystematicDepositValue());
        investment.setDurationInYears(parameters.getDurationInYears());
        investment.setFrequenceInYear(parameters.getFrequenceInYear());
        investment.setReturnOfInvestmentPercentage(parameters.getReturnOfInvestment());
        // values from result
        Result result = calculator.calculateInvestment(parameters);
        investment.setRateOfReturnValue(result.getRateOfReturnValue());
        investment.setRateOfReturnPercentage(result.getRateOfReturnPercentage());
        investment.setGraphPointsValue(result.getGraphPointValues());
        investment.setXaxisDataType(result.getXAxisDataType());
        investment.setYaxisDataType(result.getYAxisDataType());
        return investment;
    }
    public Investment modifyInvestment(Long investment_id, InvestmentDetailsDTO investmentDetailsDTO){
        Investment investment = investmentRepository.findById(investment_id).orElse(null);
        if(investment==null)
            return new Investment();
        investment.setCategory(investmentDetailsDTO.getCategory());
        investment.setRisk(investmentDetailsDTO.getRisk());
        investment.setName(investmentDetailsDTO.getName());
        investment.setInitialDepositValue(investmentDetailsDTO.getInitialDepositValue());
        investment.setFrequneceInYear(investmentDetailsDTO.getFrequenceInYear());
        investment.setReturnOfInvestment(investmentDetailsDTO.getReturnOfInvestmentPercentage());
        investment.setSysematicDepositValue(investmentDetailsDTO.getSystematicDepositValue());


        investment.setDurationInYears(investmentDetailsDTO.getDurationInYears());
        //setting parameters for new result
        InvestmentParametersDTO investmentParametersDTO = new InvestmentParametersDTO();
        investmentParametersDTO.setDurationInYears(investmentDetailsDTO.getDurationInYears());
        investmentParametersDTO.setFrequenceInYear(investmentDetailsDTO.getFrequenceInYear());
        investmentParametersDTO.setInitialDepositValue(investmentDetailsDTO.getInitialDepositValue());
        investmentParametersDTO.setReturnOfInvestment(investmentDetailsDTO.getReturnOfInvestmentPercentage());
        investmentParametersDTO.setSystematicDepositValue(investmentDetailsDTO.getSystematicDepositValue());
        investment.setResult(calculator.calculateInvestment(investmentParametersDTO));
        //save modified
        return investmentRepository.save(investment);
    }
    public void removeInvestment(Long investment_id){
        investmentRepository.deleteById(investment_id);
    }
}


