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
    double ReturnOfInvestment;
    BigDecimal RateOfReturnValue;
    BigDecimal RateOfReturnPercentage;
    List<GraphPoint> graphPointsValue;
    double graphPointsFrequenceInYear;
    List<InvestmentDTO> investmentDTOS;
}
