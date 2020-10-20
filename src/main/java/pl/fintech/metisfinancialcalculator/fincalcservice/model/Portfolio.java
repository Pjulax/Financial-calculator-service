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

    public Portfolio(String name){
        this.name = name;
    }

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Investment> investments;

    @OneToOne
    Result result;
}
