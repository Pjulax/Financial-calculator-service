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
    String name;
    Date startDate;
    Category category;
    Integer risk;
    BigDecimal initialDepositValue;
    BigDecimal systematicDepositValue;
    Double frequenceInYear;
    Integer durationInDays;
    Double ReturnOfInvestment;
    BigDecimal RateOfReturnValue;
    BigDecimal RateOfReturnPercentage;
    List<Point2D.Double> graphPointsValue;
    double graphPointsFrequenceInYear;
}
