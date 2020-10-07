package pl.fintech.metisfinancialcalculator.fincalcservice.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GraphPoint {
    @Id
    @GeneratedValue()
    private Long id;

    private Double x;
    private Double y;
}
