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
    private BigDecimal rateOfReturnValue;
    private BigDecimal totalInvestedCash;
    private BigDecimal rateOfReturnPercentage;
    private List<GraphPoint> graphPointsValue;
    private List<InvestmentInPortfolioDTO> investments;
}
