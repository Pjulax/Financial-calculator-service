package pl.fintech.metisfinancialcalculator.fincalcservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentInPortfolioDTO {
    private Long id;
    private String name;
    private Double risk;
    private Double rateOfReturnPercentage;
    private String category;
    private List<GraphPoint> graphPointsValue;
    private XDateType XAxisDataType;
    private YValueType YAxisDataType;
}
