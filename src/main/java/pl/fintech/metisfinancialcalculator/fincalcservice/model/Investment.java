package pl.fintech.metisfinancialcalculator.fincalcservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Investment {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private String category;
    private Double risk;
    private Double initialDepositValue;
    private Double systematicDepositValue;
    private Double frequencyInYears;
    private Double returnOfInvestment;
    private Double durationInYears;

    @OneToOne(cascade = CascadeType.ALL)
    private Result result;
}
