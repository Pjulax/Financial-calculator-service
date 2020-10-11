package pl.fintech.metisfinancialcalculator.fincalcservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Category;

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
    private BigDecimal initialDepositValue;
    private BigDecimal systematicDepositValue;
    private Double frequenceInYear;
    private Integer durationInDays;
    private Double ReturnOfInvestment;
    private BigDecimal RateOfReturnValue;
    private BigDecimal RateOfReturnPercentage;
    private List<Point2D.Double> graphPointsValue;
    double graphPointsFrequenceInYear;
}
