package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.exception.CustomException;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.User;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PortfolioRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.ResultRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.UserRepository;

import java.util.List;

@Service
public class InvestmentService {

    InvestmentRepository investmentRepository;

    PortfolioRepository portfolioRepository;

    ResultRepository resultRepository;

    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    Calculator calculator;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository, ResultRepository resultRepository, PortfolioRepository portfolioRepository, UserRepository userRepository){
        this.investmentRepository = investmentRepository;
        this.resultRepository = resultRepository;
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    public InvestmentDetailsDTO getInvestment(Long investment_id){
        if(doesInvestmentBelongToUser(investment_id)) return new InvestmentDetailsDTO();
        Investment investment = investmentRepository.findById(investment_id).orElse(null);
        if(investment==null){
            return new InvestmentDetailsDTO();
        }
        InvestmentDetailsDTO investmentDetailsDTO = new InvestmentDetailsDTO();
        Result result = investment.getResult();
        investmentDetailsDTO.setName(investment.getName());
        investmentDetailsDTO.setReturnOfInvestmentPercentage(investment.getReturnOfInvestment());
        investmentDetailsDTO.setRateOfReturnPercentage(result.getRateOfReturnPercentage());
        investmentDetailsDTO.setRateOfReturnValue(result.getRateOfReturnValue());
        investmentDetailsDTO.setDurationInYears(investment.getDurationInYears());
        investmentDetailsDTO.setFrequencyInYears(investment.getFrequencyInYears());
        investmentDetailsDTO.setInitialDepositValue(investment.getInitialDepositValue());
        investmentDetailsDTO.setRisk(investment.getRisk());
        investmentDetailsDTO.setCategory(investment.getCategory());
        investmentDetailsDTO.setSystematicDepositValue(investment.getSystematicDepositValue());
        investmentDetailsDTO.setXAxisDataType(result.getXAxisDataType());
        investmentDetailsDTO.setYAxisDataType(result.getYAxisDataType());
        investmentDetailsDTO.setGraphPointsValue(result.getGraphPointValues());
        return investmentDetailsDTO;
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
        investment.setFrequencyInYears(parameters.getFrequencyInYears());
        investment.setReturnOfInvestmentPercentage(parameters.getReturnOfInvestment());
        // values from result
        Result result = calculator.calculateInvestment(parameters);
        investment.setRateOfReturnValue(result.getRateOfReturnValue());
        investment.setRateOfReturnPercentage(result.getRateOfReturnPercentage());
        investment.setGraphPointsValue(result.getGraphPointValues());
        investment.setXAxisDataType(result.getXAxisDataType());
        investment.setYAxisDataType(result.getYAxisDataType());
        return investment;
    }
    public Investment modifyInvestment(Long investment_id, InvestmentDetailsDTO investmentDetailsDTO){
        if(doesInvestmentBelongToUser(investment_id)) return new Investment();
        Investment investment = investmentRepository.findById(investment_id).orElse(null);
        if(investment==null) return new Investment();
        Portfolio portfolio = portfolioRepository.findByInvestmentsContaining(investment).orElse(null);
        if(portfolio == null) return new Investment();
        List<Investment> investments = portfolio.getInvestments();

        investment.setCategory(investmentDetailsDTO.getCategory());
        investment.setRisk(investmentDetailsDTO.getRisk());
        investment.setName(investmentDetailsDTO.getName());
        investment.setInitialDepositValue(investmentDetailsDTO.getInitialDepositValue());
        investment.setFrequencyInYears(investmentDetailsDTO.getFrequencyInYears());
        investment.setReturnOfInvestment(investmentDetailsDTO.getReturnOfInvestmentPercentage());
        investment.setSystematicDepositValue(investmentDetailsDTO.getSystematicDepositValue());


        investment.setDurationInYears(investmentDetailsDTO.getDurationInYears());
        //setting parameters for new result
        InvestmentParametersDTO investmentParametersDTO = new InvestmentParametersDTO();
        investmentParametersDTO.setDurationInYears(investmentDetailsDTO.getDurationInYears());
        investmentParametersDTO.setFrequencyInYears(investmentDetailsDTO.getFrequencyInYears());
        investmentParametersDTO.setInitialDepositValue(investmentDetailsDTO.getInitialDepositValue());
        investmentParametersDTO.setReturnOfInvestment(investmentDetailsDTO.getReturnOfInvestmentPercentage());
        investmentParametersDTO.setSystematicDepositValue(investmentDetailsDTO.getSystematicDepositValue());
        investment.setResult(calculator.calculateInvestment(investmentParametersDTO));
        for(int i = 0; i<investments.size();i++){
            if(investments.get(i).getId()== investment.getId()){
                investments.set(i, investment);break;
            }
        }
        portfolio.setInvestments(investments);
        portfolioRepository.save(portfolio);
        return investment;
    }
    public void removeInvestment(Long investment_id){
        if(doesInvestmentBelongToUser(investment_id)) return;
        Investment inv = investmentRepository.findById(investment_id).orElse(null);
        if(inv==null) return;
        Portfolio portfolio = portfolioRepository.findByInvestmentsContaining(inv).orElse(null);
        if(portfolio == null) return;
        List<Investment> investments = portfolio.getInvestments();
        investments.remove(inv);
        portfolio.setInvestments(investments);
        portfolioRepository.save(portfolio);
        investmentRepository.deleteById(investment_id);
    }
    private void checkIfInvestmentBelongToUser(Long investment_id) {
        Investment investment = investmentRepository.findById(investment_id).orElse(null);
        if (investment == null) throw new CustomException("Unauthorized access.", HttpStatus.NOT_FOUND);
        Portfolio portfolio = portfolioRepository.findByInvestmentsContaining(investment).orElse(null);
        if (portfolio == null) throw new CustomException("Unauthorized access.", HttpStatus.NOT_FOUND);
        User user = userRepository.findUserByPortfoliosContaining(portfolio).orElse(null);
        if (user == null) throw new CustomException("Unauthorized access.", HttpStatus.NOT_FOUND);
        boolean userMatch = !userService.whoami().getUsername().equals(user.getUsername());
        if (userMatch)
            throw new CustomException("Unauthorized access.", HttpStatus.NOT_FOUND);
    }
}

