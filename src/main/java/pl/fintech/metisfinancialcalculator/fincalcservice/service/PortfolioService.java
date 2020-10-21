package pl.fintech.metisfinancialcalculator.fincalcservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentInPorfolioDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.GraphPointRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PorfolioRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.ResultRespotiory;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PortfolioService {
    InvestmentRepository investmentRepository;
    PorfolioRepository portfolioRepository;
    ResultRespotiory resultRespotiory;
    GraphPointRepository graphPointRepository;

    @Autowired
    public PortfolioService(PorfolioRepository portfolioRepository, InvestmentRepository investmentRepository, ResultRespotiory resultRespotiory){
        this.portfolioRepository = portfolioRepository;
        this.investmentRepository = investmentRepository;
        this.resultRespotiory = resultRespotiory;
    }

    public List<Investment> getPortfiolioInvestments(){//TODO
        return List.of();
    }
    public List<Portfolio> getAllPortfiolios(){//TODO
        return List.of();
    }

    public Investment addInvestment(InvestmentDetailsDTO investmentDTO, Long portfolio_id){//TODO

        Portfolio portfolio = portfolioRepository.findById(portfolio_id).orElse(null);
        if(portfolio==null) {
            return null;
        }
        Investment investment = new Investment();
        //parameters
        investment.setDurationInYears(investmentDTO.getDurationInYears());
        investment.setFrequneceInYear(investmentDTO.getFrequenceInYear());
        investment.setInitialDepositValue(investmentDTO.getInitialDepositValue());
        investment.setReturnOfInvestment(investmentDTO.getReturnOfInvestmentPercentage());
        investment.setSysematicDepositValue(investmentDTO.getSystematicDepositValue());
        //result
        Result result = new Result();

        result.setReturnOfInvestment(investmentDTO.getReturnOfInvestmentPercentage());
        result.setRateOfReturnPercentage(investmentDTO.getRateOfReturnPercentage());

        result.setRateOfReturnValue(investmentDTO.getRateOfReturnValue());
        result.setGraphPointValues(investmentDTO.getGraphPointsValue());

        result.setXAxisDataType(XDateType.YEAR);
        result.setYAxisDataType(YValueType.POUNDS);


        investment.setResult(result);

        //
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
        return portfolioRepository.save(new Portfolio(name));
    }
    public Portfolio modifyPortfolio(Long portfolio_id, String newName){
        Portfolio portfolio = portfolioRepository.findById(portfolio_id).orElse(null);
        if(portfolio==null)
            return new Portfolio();
        portfolio.setName(newName);
        return portfolioRepository.save(portfolio);
    }
    public void removePortfolio(Long portfolio_id){
        portfolioRepository.deleteById(portfolio_id);
    }

    public PortfolioDetailsDTO getPorfolioDetails(Long portfolio_id){
        Portfolio portfolio = portfolioRepository.findById(portfolio_id).orElse(null);
        if(portfolio==null)
            return new PortfolioDetailsDTO();
        PortfolioDetailsDTO portfolioDetailsDTO = new PortfolioDetailsDTO();
        List<Investment> investments = portfolio.getInvestments();

        portfolioDetailsDTO.setGraphPointsValue(getGraphPointsValuesOFPorfolio(investments));
        portfolioDetailsDTO.setInvestmentDTOS(investmentInPorfolioDTOS(investments));
        portfolioDetailsDTO.setRateOfReturnPercentage(getRateOfReturnOfPortfolio(investments));
        portfolioDetailsDTO.setRateOfReturnValue(getRateOfReturnValueOfPortfolio(investments));
        portfolioDetailsDTO.setTotalInvestedCash(getTotalInvesteCashInPortfolio(investments));
        return portfolioDetailsDTO;
    }

    public PortfolioDetailsDTO getPortfolioAllInvestmentsDetails(){
        List<Portfolio> portfolios = portfolioRepository.findAll();
        List<Investment> investments = new ArrayList<>();
        for (Portfolio p : portfolios) {
            investments.addAll(p.getInvestments());
        }
        PortfolioDetailsDTO portfolioDetailsDTO = new PortfolioDetailsDTO();
        portfolioDetailsDTO.setGraphPointsValue(getGraphPointsValuesOFPorfolio(investments));
        portfolioDetailsDTO.setInvestmentDTOS(investmentInPorfolioDTOS(investments));
        portfolioDetailsDTO.setRateOfReturnPercentage(getRateOfReturnOfPortfolio(investments));
        portfolioDetailsDTO.setRateOfReturnValue(getRateOfReturnValueOfPortfolio(investments));
        portfolioDetailsDTO.setTotalInvestedCash(getTotalInvesteCashInPortfolio(investments));
        return portfolioDetailsDTO;
    }


    private  List<GraphPoint> getGraphPointsValuesOFPorfolio(List<Investment> investments){
        if(investments.size()==0) return List.of();
        if(investments.size()==1) return  investments.get(0).getResult().getGraphPointValues();

        List<GraphPoint> graphPoints = new ArrayList<>();
        investments.sort( (o1, o2) -> o2.getDurationInYears().compareTo(o1.getDurationInYears()));
        double maxDuration = investments.get(0).getDurationInYears();

        investments.sort(Comparator.comparing(Investment::getFrequneceInYear));
        double minInterval = investments.get(0).getFrequneceInYear();

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
    private List<InvestmentInPorfolioDTO> investmentInPorfolioDTOS(List<Investment> investments){
        List<InvestmentInPorfolioDTO> investmentInPorfolioDTOS = new ArrayList<>();

        for (Investment in: investments
             ) {
            InvestmentInPorfolioDTO inPorfolioDTO = new InvestmentInPorfolioDTO();
            inPorfolioDTO.setCategory(in.getName());
            inPorfolioDTO.setGraphPointsValue(in.getResult().getGraphPointValues());
            inPorfolioDTO.setId(in.getId());
            inPorfolioDTO.setName(in.getName());
            inPorfolioDTO.setRateOfReturnPercentage(in.getResult().getRateOfReturnPercentage());
            inPorfolioDTO.setXaxisDataType(XDateType.YEAR);
            inPorfolioDTO.setYaxisDataType(YValueType.POUNDS);
            inPorfolioDTO.setRisk(in.getRisk());
            investmentInPorfolioDTOS.add(inPorfolioDTO);
        }
        return investmentInPorfolioDTOS;
    }
    private BigDecimal getRateOfReturnOfPortfolio(List<Investment> investments){
        //(1200÷1000−1)÷(3÷12) = (k-p-1)/(frequence)
        double sumOfInvestedMoney = 0d;
        double sumOfResult = 0d;
        for (Investment in: investments) {
            double investedMoney = in.getSysematicDepositValue()*(in.getDurationInYears()/in.getFrequneceInYear())+in.getInitialDepositValue();
            sumOfInvestedMoney += investedMoney;
            sumOfResult += (investedMoney+in.getResult().getRateOfReturnValue().doubleValue());
        }
        double result = sumOfResult/sumOfInvestedMoney-1;
        return BigDecimal.valueOf(result);
    }
    private BigDecimal getRateOfReturnValueOfPortfolio(List<Investment> investments){
        double sumOfResult = 0d;
        for (Investment in: investments)
            sumOfResult += (in.getResult().getRateOfReturnValue().doubleValue());
        return BigDecimal.valueOf(sumOfResult);
    }
    private BigDecimal getTotalInvesteCashInPortfolio(List<Investment> investments){
        double sumOfInvestedMoney = 0d;
        for (Investment in: investments) {
            double investedMoney = in.getSysematicDepositValue()*(in.getDurationInYears()/in.getFrequneceInYear())+in.getInitialDepositValue();
            sumOfInvestedMoney += investedMoney;
        }
        return BigDecimal.valueOf(sumOfInvestedMoney);
    }


    public List<PortfolioNameDTO> getAllPortfioliosNames() {
        return portfolioRepository.findAll().stream().map(p->new PortfolioNameDTO(p.getId(),p.getName())).collect(Collectors.toList());
    }
}
