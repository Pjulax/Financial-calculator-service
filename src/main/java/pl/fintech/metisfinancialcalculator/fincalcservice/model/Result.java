package pl.fintech.metisfinancialcalculator.fincalcservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue()
    private Long id;

    private double ReturnOfInvestment;

    @Column(precision = 14, scale = 2)
    private BigDecimal annualValueDifference;

    private XDateType xAxisDataType;
    private YValueType yAxisDataType;

    @OneToMany()
    private List<GraphPoint> graphPointValues;
}
