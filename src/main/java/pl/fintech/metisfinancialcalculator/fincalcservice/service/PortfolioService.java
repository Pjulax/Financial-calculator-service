package pl.fintech.metisfinancialcalculator.fincalcservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentInPortfolioDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.exception.CustomException;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PortfolioService {
    final InvestmentRepository investmentRepository;
    final PortfolioRepository portfolioRepository;
    final UserRepository userRepository;
    final UserService userService;

    public PortfolioService(PortfolioRepository portfolioRepository, InvestmentRepository investmentRepository, UserRepository userRepository, UserService userService){
        this.portfolioRepository = portfolioRepository;
        this.investmentRepository = investmentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Investment addInvestment(InvestmentDetailsDTO investmentDTO, Long portfolio_id){
        Portfolio portfolio = portfolioRepository.findById(portfolio_id).orElse(null);
        if(null==portfolio)
            throw new CustomException("The portfolio doesn't exist", HttpStatus.NOT_FOUND);
        Investment investment = new Investment();
        //parameters
        investment.setDurationInYears(investmentDTO.getDurationInYears());
        investment.setFrequencyInYears(investmentDTO.getFrequencyInYears());
        investment.setInitialDepositValue(investmentDTO.getInitialDepositValue());
        investment.setReturnOfInvestment(investmentDTO.getReturnOfInvestmentPercentage());
        investment.setSystematicDepositValue(investmentDTO.getSystematicDepositValue());
        //result
        Result result = new Result();

        result.setReturnOfInvestment(investmentDTO.getReturnOfInvestmentPercentage());
        result.setRateOfReturnPercentage(investmentDTO.getRateOfReturnPercentage());

        result.setRateOfReturnValue(investmentDTO.getRateOfReturnValue());
        result.setGraphPointValues(investmentDTO.getGraphPointsValue());

        result.setXAxisDataType(XDateType.YEAR);
        result.setYAxisDataType(YValueType.POUNDS);

        investment.setResult(result);
        investment.setCategory(investmentDTO.getCategory());
        investment.setName(investmentDTO.getName());
        investment.setRisk(investmentDTO.getRisk());
        investment = investmentRepository.save(investment);

        List<Investment> investments = portfolio.getInvestments();
        investments.add(investment);
        portfolio.setInvestments(investments);
        portfolioRepository.save(portfolio);
        return investment;
    }
    public Portfolio createPortfolio(String name){
        User user = userService.whoami();
        if(portfolioRepository.findPortfolioByName(name).orElse(null)!=null)
            throw new CustomException("The portfolio doesn't exist", HttpStatus.NOT_FOUND);
        List<Portfolio> portfolios = user.getPortfolios();
        Portfolio portfolio = portfolioRepository.save(new Portfolio(name));
        portfolios.add(portfolio);
        user.setPortfolios(portfolios);
        userRepository.save(user);
        return portfolio;
    }
    public Portfolio modifyPortfolio(Long portfolio_id, String newName){
        User user = userService.whoami();
        Portfolio portfolio = portfolioRepository.findById(portfolio_id).orElse(null);
        if(null==portfolio)
            throw new CustomException("The portfolio doesn't exist", HttpStatus.NOT_FOUND);
        List<Portfolio> portfolios = user.getPortfolios();
        if (portfolios.contains(portfolio)) {
            portfolio.setName(newName);
            return portfolioRepository.save(portfolio);
        }
        throw new CustomException("The resource can't be found or access is unauthorized", HttpStatus.NOT_FOUND);
    }
    public void removePortfolio(Long portfolio_id){
        User user = userService.whoami();
        Portfolio portfolio = portfolioRepository.findById(portfolio_id).orElse(null);
        if(null==portfolio)
            throw new CustomException("The portfolio doesn't exist", HttpStatus.NOT_FOUND);
        List<Portfolio> portfolios = user.getPortfolios();
        if (portfolios.contains(portfolio)) {
            portfolios.remove(portfolio);
            user.setPortfolios(portfolios);
            userRepository.save(user);
            portfolioRepository.deleteById(portfolio_id);
            return;
        }
        throw new CustomException("The resource can't be found or access is unauthorized", HttpStatus.NOT_FOUND);
    }
    public PortfolioDetailsDTO getPortfolioDetails(Long portfolio_id){
        User user = userService.whoami();
        Portfolio portfolio = portfolioRepository.findById(portfolio_id).orElse(null);
        if(portfolio==null)
            return new PortfolioDetailsDTO(); // throw new CustomException("The portfolio doesn't exist.", HttpStatus.NOT_FOUND);
        List<Portfolio> portfolios = user.getPortfolios();
        if (portfolios.contains(portfolio)) {
            PortfolioDetailsDTO portfolioDetailsDTO = new PortfolioDetailsDTO();
            List<Investment> investments = portfolio.getInvestments();

            portfolioDetailsDTO.setGraphPointsValue(getGraphPointsValuesOFPortfolio(investments));
            portfolioDetailsDTO.setInvestments(investmentInPortfolioDTO(investments));
            portfolioDetailsDTO.setRateOfReturnPercentage(getRateOfReturnOfPortfolio(investments));
            portfolioDetailsDTO.setRateOfReturnValue(getRateOfReturnValueOfPortfolio(investments));
            portfolioDetailsDTO.setTotalInvestedCash(getTotalInvestedCashInPortfolio(investments));
            return portfolioDetailsDTO;
        }
        throw new CustomException("The resource can't be found or access is unauthorized", HttpStatus.NOT_FOUND);
    }
    public PortfolioDetailsDTO getPortfolioAllInvestmentsDetails(){
        User user = userService.whoami();
        List<Portfolio> portfolios = user.getPortfolios();
        List<Investment> investments = new ArrayList<>();
        for (Portfolio p : portfolios) {
            investments.addAll(p.getInvestments());
        }
        PortfolioDetailsDTO portfolioDetailsDTO = new PortfolioDetailsDTO();
        portfolioDetailsDTO.setGraphPointsValue(getGraphPointsValuesOFPortfolio(investments));
        portfolioDetailsDTO.setInvestments(investmentInPortfolioDTO(investments));
        portfolioDetailsDTO.setRateOfReturnPercentage(getRateOfReturnOfPortfolio(investments));
        portfolioDetailsDTO.setRateOfReturnValue(getRateOfReturnValueOfPortfolio(investments));
        portfolioDetailsDTO.setTotalInvestedCash(getTotalInvestedCashInPortfolio(investments));
        return portfolioDetailsDTO;
    }
    private  List<GraphPoint> getGraphPointsValuesOFPortfolio(List<Investment> investments){
        if(investments.size()==0) return List.of();
        if(investments.size()==1) return  investments.get(0).getResult().getGraphPointValues();

        List<GraphPoint> graphPoints = new ArrayList<>();
        investments.sort( (o1, o2) -> o2.getDurationInYears().compareTo(o1.getDurationInYears()));
        double maxDuration = investments.get(0).getDurationInYears();

        investments.sort(Comparator.comparing(Investment::getFrequencyInYears));
        double minInterval = investments.get(0).getFrequencyInYears();

        for(double i = 0.0; i<maxDuration; i+=minInterval){
            GraphPoint gp = new GraphPoint(i, 0d);
            for (Investment inv : investments) {
                for(int j = 0; j<inv.getResult().getGraphPointValues().size();j++){
                    if(Math.abs(inv.getResult().getGraphPointValues().get(j).getX()-i)<0.002){ // smaller than one day
                        gp.setY(gp.getY()+inv.getResult().getGraphPointValues().get(j).getY());
                        break;
                    }
                }
            }
            graphPoints.add(gp);
        }
        return graphPoints;
    }
    private List<InvestmentInPortfolioDTO> investmentInPortfolioDTO(List<Investment> investments){
        List<InvestmentInPortfolioDTO> investmentInPortfolioDTO = new ArrayList<>();

        for (Investment in: investments) {
            InvestmentInPortfolioDTO inPortfolioDTO = new InvestmentInPortfolioDTO();
            inPortfolioDTO.setCategory(in.getName());
            inPortfolioDTO.setGraphPointsValue(in.getResult().getGraphPointValues());
            inPortfolioDTO.setId(in.getId());
            inPortfolioDTO.setName(in.getName());
            inPortfolioDTO.setRateOfReturnPercentage(in.getResult().getRateOfReturnPercentage());
            inPortfolioDTO.setXAxisDataType(XDateType.YEAR);
            inPortfolioDTO.setYAxisDataType(YValueType.POUNDS);
            inPortfolioDTO.setRisk(in.getRisk());
            investmentInPortfolioDTO.add(inPortfolioDTO);
        }
        return investmentInPortfolioDTO;
    }
    private BigDecimal getRateOfReturnOfPortfolio(List<Investment> investments){
        double sumOfInvestedMoney = 0d;
        double sumOfResult = 0d;
        for (Investment in: investments) {
            double investedMoney = in.getSystematicDepositValue()*(in.getDurationInYears()/in.getFrequencyInYears())+in.getInitialDepositValue();
            sumOfInvestedMoney += investedMoney;
            sumOfResult += (investedMoney+in.getResult().getRateOfReturnValue().doubleValue());
        }
        if(sumOfInvestedMoney == 0)
            throw new CustomException("Given data results in dividing by zero",HttpStatus.BAD_REQUEST);
        double result = sumOfResult/sumOfInvestedMoney-1;
        return BigDecimal.valueOf(result);
    }
    private BigDecimal getRateOfReturnValueOfPortfolio(List<Investment> investments){
        double sumOfResult = 0d;
        for (Investment in: investments)
            sumOfResult += (in.getResult().getRateOfReturnValue().doubleValue());
        return BigDecimal.valueOf(sumOfResult);
    }
    private BigDecimal getTotalInvestedCashInPortfolio(List<Investment> investments){
        double sumOfInvestedMoney = 0d;
        for (Investment in: investments) {
            double investedMoney = in.getSystematicDepositValue()*(in.getDurationInYears()/in.getFrequencyInYears())+in.getInitialDepositValue();
            sumOfInvestedMoney += investedMoney;
        }
        return BigDecimal.valueOf(sumOfInvestedMoney);
    }
    public List<PortfolioNameDTO> getAllPortfoliosNames() {
        User user = userService.whoami();
        List<Portfolio> portfolios = user.getPortfolios();
        return portfolios.stream().map(p->new PortfolioNameDTO(p.getId(),p.getName())).collect(Collectors.toList());
    }
}
