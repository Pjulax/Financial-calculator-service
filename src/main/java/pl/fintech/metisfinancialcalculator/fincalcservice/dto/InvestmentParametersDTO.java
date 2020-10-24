package pl.fintech.metisfinancialcalculator.fincalcservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentParametersDTO {
    private String startDate;
    private Double initialDepositValue;
    private Double systematicDepositValue;
    private Double frequencyInYears;
    private Double durationInYears;
    private Double returnOfInvestment;
}
