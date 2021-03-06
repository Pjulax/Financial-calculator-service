package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import lombok.RequiredArgsConstructor;
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
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Calculator calculator;

    public InvestmentDetailsDTO getInvestment(Long investmentId){
        checkIfInvestmentBelongToUser(investmentId);
        Investment investment = investmentRepository.findById(investmentId).orElseThrow();
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
    public InvestmentDetailsDTO calculateInvestment(InvestmentParametersDTO parameters){
        if(parameters.getDurationInYears() <= .0d)
            throw new CustomException("Arguments can't be equal to 0 or below", HttpStatus.NOT_ACCEPTABLE);
        if(parameters.getFrequencyInYears() <= .0d)
            throw new CustomException("Arguments can't be equal to 0 or below", HttpStatus.NOT_ACCEPTABLE);
        if(parameters.getInitialDepositValue() < .0d)
            throw new CustomException("Arguments can't be below 0", HttpStatus.NOT_ACCEPTABLE);
        if(parameters.getSystematicDepositValue() < .0d)
            throw new CustomException("Arguments can't be below 0", HttpStatus.NOT_ACCEPTABLE);
        InvestmentDetailsDTO investment = new InvestmentDetailsDTO();
        // not used, write down just for clarity
        investment.setCategory(null);
        investment.setName(null);
        // values from parameters
        investment.setRisk(parameters.getRisk());
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
    public Investment modifyInvestment(Long investmentId, InvestmentDetailsDTO investmentDetailsDTO){
        if(investmentDetailsDTO.getDurationInYears() <= .0d)
            throw new CustomException("Arguments can't be equal to 0 or below", HttpStatus.NOT_ACCEPTABLE);
        if(investmentDetailsDTO.getFrequencyInYears() <= .0d)
            throw new CustomException("Arguments can't be equal to 0 or below", HttpStatus.NOT_ACCEPTABLE);
        if(investmentDetailsDTO.getInitialDepositValue() < .0d)
            throw new CustomException("Arguments can't be below 0", HttpStatus.NOT_ACCEPTABLE);
        if(investmentDetailsDTO.getSystematicDepositValue() < .0d)
            throw new CustomException("Arguments can't be below 0", HttpStatus.NOT_ACCEPTABLE);
        checkIfInvestmentBelongToUser(investmentId);
        Investment investment = investmentRepository.findById(investmentId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findByInvestmentsContaining(investment).orElseThrow();

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
            if(investments.get(i).getId().equals(investment.getId())){
                investments.set(i, investment);break;
            }
        }
        portfolio.setInvestments(investments);
        portfolioRepository.save(portfolio);
        return investment;
    }
    public void removeInvestment(Long investmentId){
        checkIfInvestmentBelongToUser(investmentId);
        Investment inv = investmentRepository.findById(investmentId).orElseThrow();
        Portfolio portfolio = portfolioRepository.findByInvestmentsContaining(inv).orElseThrow();
        List<Investment> investments = portfolio.getInvestments();
        investments.remove(inv);
        portfolio.setInvestments(investments);
        portfolioRepository.save(portfolio);
        investmentRepository.deleteById(investmentId);
    }
    private void checkIfInvestmentBelongToUser(Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId).orElse(null);
        if (investment == null) throw new CustomException("The resource can't be found or access is unauthorized", HttpStatus.NOT_FOUND);
        Portfolio portfolio = portfolioRepository.findByInvestmentsContaining(investment).orElse(null);
        if (portfolio == null) throw new CustomException("The resource can't be found or access is unauthorized", HttpStatus.NOT_FOUND);
        User user = userRepository.findUserByPortfoliosContaining(portfolio).orElse(null);
        if (user == null) throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        if (!userService.whoami().getUsername().equals(user.getUsername()))
            throw new CustomException("Unauthorized access", HttpStatus.NOT_FOUND);
    }
}