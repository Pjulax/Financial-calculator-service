package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.rules.Timeout;
import org.mockito.Mockito;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CalculatorTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);

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


        final Result[] calculatedResult = new Result[1];
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            calculatedResult[0] = calculator.calculateInvestment(parametersDTO);

        });

        assertEquals(result.getRateOfReturnPercentage(), calculatedResult[0].getRateOfReturnPercentage(), 0.1);
        assertEquals(result.getRateOfReturnValue().doubleValue(), calculatedResult[0].getRateOfReturnValue().doubleValue(), result.getRateOfReturnValue().doubleValue() / 50);
        assertEquals(result.getReturnOfInvestment(), calculatedResult[0].getReturnOfInvestment(), 0.01);
    }
}