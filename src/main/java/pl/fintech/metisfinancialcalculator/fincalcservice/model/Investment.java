package pl.fintech.metisfinancialcalculator.fincalcservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Investment {
    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private Date startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private int risk;
    private BigDecimal initialDepositValue;
    private BigDecimal sysematicDepositValue;
    private Double frequneceInYear;
    private Double returnOfInvestment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date duration;

    @OneToOne
    private Result result;
}
