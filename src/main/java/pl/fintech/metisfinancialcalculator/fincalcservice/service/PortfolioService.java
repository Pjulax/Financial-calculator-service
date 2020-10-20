package pl.fintech.metisfinancialcalculator.fincalcservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentInPorfolioDTO;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        long duration =  investmentDTO.getDurationInYears().longValue();
        duration = duration*((long)364.25)*24*60*1000;
        investment.setDuration(new Date(duration));
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
        investment.setStartDate(investmentDTO.getStartDate());
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

    public Portfolio createPortfolio(String name){//TODO
        return portfolioRepository.save(new Portfolio(name));
    }
    public void modifyPortfolio(Long portfolio_id, String newName){//TODO

    }
    public void removePortfolio(Long portfolio_id){//TODO

    }
    public Result getPortfioloResult(Long portfolio_id){//TODO
        return new Result();
    }

    public List<PortfolioNameDTO> getAllPortfioliosNames() {
        return portfolioRepository.findAll().stream().map(p->new PortfolioNameDTO(p.getId(),p.getName())).collect(Collectors.toList());
    }
}
