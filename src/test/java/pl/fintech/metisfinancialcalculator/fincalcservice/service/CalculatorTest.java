package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CalculatorTest {

    private static Calculator calculator;

    @BeforeAll
    static void setup() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/calculator-data.csv")
    void calculateInvestment(
            double durationInYears,
            double frequencyInYears,
            double initialDepositValue,
            double returnOfInvestment,
            double systematicDepositValue,
            double rateOfReturnPercentage,
            BigDecimal rateOfReturnValue
    ) {
        InvestmentParametersDTO parametersDTO = Mockito.mock(InvestmentParametersDTO.class);
        Result result = new Result();

        result.setRateOfReturnPercentage(rateOfReturnPercentage);
        result.setRateOfReturnValue(rateOfReturnValue);
        result.setXAxisDataType(XDateType.YEAR);
        result.setYAxisDataType(YValueType.POUNDS);
        result.setReturnOfInvestment(returnOfInvestment);

        when(parametersDTO.getDurationInYears()).thenReturn(durationInYears);
        when(parametersDTO.getFrequencyInYears()).thenReturn(frequencyInYears);
        when(parametersDTO.getInitialDepositValue()).thenReturn(initialDepositValue);
        when(parametersDTO.getReturnOfInvestment()).thenReturn(returnOfInvestment);
        when(parametersDTO.getStartDate()).thenReturn("");
        when(parametersDTO.getSystematicDepositValue()).thenReturn(systematicDepositValue);

        Result calculatedResult = calculator.calculateInvestment(parametersDTO);

        assertEquals(result.getRateOfReturnPercentage(), calculatedResult.getRateOfReturnPercentage());
        assertEquals(result.getRateOfReturnValue(), calculatedResult.getRateOfReturnValue());
        assertEquals(result.getReturnOfInvestment(), calculatedResult.getReturnOfInvestment());
    }
}