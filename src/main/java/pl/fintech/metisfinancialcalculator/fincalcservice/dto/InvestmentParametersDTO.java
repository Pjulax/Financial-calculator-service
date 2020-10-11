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
    private Date startDate;
    private BigDecimal initialDepositValue;
    private BigDecimal systematicDepositValue;
    private Double frequenceInYear;
    private Double durationInYears;
    private Double ReturnOfInvestment;
}