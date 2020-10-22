package pl.fintech.metisfinancialcalculator.fincalcservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PortfolioDetailsDTO {
    private BigDecimal RateOfReturnValue;
    private BigDecimal totalInvestedCash;
    private BigDecimal RateOfReturnPercentage;
    private List<GraphPoint> graphPointsValue;
    private List<InvestmentInPortfolioDTO> investmentDTOS;
}
