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

//    Category to String, further I can change it back to Category
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")

    private String category;

    private Double risk;
    private Double initialDepositValue;
    private Double sysematicDepositValue;
    private Double frequneceInYear;
    private Double returnOfInvestment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date duration;

    @OneToOne(cascade = CascadeType.ALL)
    private Result result;
}
