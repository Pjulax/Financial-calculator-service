package pl.fintech.metisfinancialcalculator.fincalcservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentDetailsDTO {
    private String name;
    private String category;
    private Double risk;
    private Double initialDepositValue;
    private Double systematicDepositValue;
    private Double frequencyInYears;
    private Double durationInYears;
    private Double returnOfInvestmentPercentage;
    private BigDecimal rateOfReturnValue;
    private Double rateOfReturnPercentage;
    private List<GraphPoint> graphPointsValue;
    private XDateType XAxisDataType;
    private YValueType YAxisDataType;
}
