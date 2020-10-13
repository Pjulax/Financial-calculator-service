package pl.fintech.metisfinancialcalculator.fincalcservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Category;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentDetailsDTO {
    private String name;
    private Date startDate;
    private Category category;
    private Integer risk;
    private Double initialDepositValue;
    private Double systematicDepositValue;
    private Double frequenceInYear;
    private Double durationInYears;
    private Double returnOfInvestmentPercentage;
    private BigDecimal annualRateOfReturnValue;
    private Double annualRateOfReturnPercentage;
    private BigDecimal rateOfReturnValue;
    private Double rateOfReturnPercentage;
    private List<GraphPoint> graphPointsValue;
    private XDateType xaxisDataType;
    private YValueType yaxisDataType;
}
