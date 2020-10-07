package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;

import java.util.List;

@Service
public class Calculator {

    InvestmentRepository investmentRepository;

    @Autowired
    public Calculator(InvestmentRepository investmentRepository){
        this.investmentRepository = investmentRepository;
    }

    public Result calculateInvestment(Investment investment){
        return new Result(); //TODO
    }
    public Result calculatePortfolio(List<Investment> portfolioInvestments){
        return new Result(); //TODO
    }
}
