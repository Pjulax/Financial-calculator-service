package pl.fintech.metisfinancialcalculator.fincalcservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentParametersDTO {
    private String startDate;
    private Double initialDepositValue;
    private Double systematicDepositValue;
    private Double frequenceInYear;
    private Double durationInYears;
    private Double returnOfInvestment;
}
