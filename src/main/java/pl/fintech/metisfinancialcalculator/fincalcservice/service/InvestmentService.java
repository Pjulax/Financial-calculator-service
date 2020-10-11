package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;

import java.util.List;

@Service
public class InvestmentService {

    InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository){
        this.investmentRepository = investmentRepository;
    }

    public Investment getInvestment(Long investment_id){
        return new Investment(); //TODO
    }
    public List<Investment> getAllInvestments(){
        return List.of(); //TODO
    }

    public void modifyInvestment(Long investment_id){
        //TODO
    }
    public void removeInvestment(Long investment_id){
        //TODO
    }
    public InvestmentDetailsDTO getInvestmentResult(Long investment_id){
        return new InvestmentDetailsDTO();//TODO
    }

}
