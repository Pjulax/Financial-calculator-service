package pl.fintech.metisfinancialcalculator.fincalcservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentInPorfolioDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PorfolioRepository;

import java.util.List;
import java.util.Optional;


@Service
public class PortfolioService {
    InvestmentRepository investmentRepository;
    PorfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PorfolioRepository portfolioRepository, InvestmentRepository investmentRepository){
        this.portfolioRepository = portfolioRepository;
        this.investmentRepository = investmentRepository;
    }

    public List<Investment> getPortfiolioInvestments(){//TODO
        return List.of();
    }
    public List<Portfolio> getAllPortfiolios(){//TODO
        return List.of();
    }

    public Investment addInvestment(InvestmentDetailsDTO investmentDTO, Optional<Portfolio> portfolio){//TODO
        return new Investment();
    }

    public Portfolio createPortfolio(String name, List<Investment> investments){//TODO
        return new Portfolio();
    }
    public void modifyPortfolio(Long portfolio_id, String newName){//TODO

    }
    public void removePortfolio(Long portfolio_id){//TODO

    }
    public Result getPortfioloResult(Long portfolio_id){//TODO
        return new Result();
    }
}
