package pl.fintech.metisfinancialcalculator.fincalcservice.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Portfolio {
    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    @OneToMany
    private List<Investment> investments;

    @OneToOne
    Result result;
}
