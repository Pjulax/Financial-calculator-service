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
public class User {//TODO
    @Id
    @GeneratedValue()
    private Long id;

    private String username;
    private String password;

    @OneToMany()
    private List<Portfolio> portfolios;
}
