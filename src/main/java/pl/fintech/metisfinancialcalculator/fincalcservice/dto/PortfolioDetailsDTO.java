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
    private Double ReturnOfInvestment;
    private BigDecimal RateOfReturnValue;
    private BigDecimal RateOfReturnPercentage;
    private List<GraphPoint> graphPointsValue;
    private Double graphPointsFrequenceInYear;
    private List<InvestmentInPorfolioDTO> investmentDTOS;
}
