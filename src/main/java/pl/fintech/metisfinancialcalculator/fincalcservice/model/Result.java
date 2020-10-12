package pl.fintech.metisfinancialcalculator.fincalcservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;

import javax.persistence.*;
import java.math.BigDecimal;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Result {
    @JsonIgnore
    @Id
    @GeneratedValue()
    private Long id;

    private BigDecimal rateOfReturnValue;
    private Double rateOfReturnPercentage;

    private Double graphPointsFrequenceInYear;
    private Double returnOfInvestment;

    private BigDecimal annualValueDifference;

    private XDateType xAxisDataType;
    private YValueType yAxisDataType;

    @OneToMany
    private List<GraphPoint> graphPointValues;
}
