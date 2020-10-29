package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType.POUNDS;

@Service
@NoArgsConstructor
public class Calculator {
    /*
    * Method is calculating value with "Make deposits at beginning of the period"
    */
    public Result calculateInvestment(InvestmentParametersDTO parameters){
        // resultFV calculation
        BigDecimal resultFVWithCashFlow = new BigDecimal(parameters.getInitialDepositValue()).multiply(
                                BigDecimal.valueOf(
                                    Math.pow((1+parameters.getReturnOfInvestment()),
                                            parameters.getDurationInYears())));

        // resultFV with cash flow calculation and graph points creating
        ArrayList<GraphPoint> graphPoints = new ArrayList<>();
        graphPoints.add(new GraphPoint(0.0, parameters.getInitialDepositValue()));
        BigDecimal systematicDeposit = new BigDecimal(parameters.getSystematicDepositValue().toString());

        if(parameters.getFrequencyInYears() != 0) {
            for (double i = parameters.getFrequencyInYears(); i <= parameters.getDurationInYears(); i += parameters.getFrequencyInYears()) {
                resultFVWithCashFlow = resultFVWithCashFlow.add(
                        systematicDeposit.multiply(
                                BigDecimal.valueOf(
                                        Math.pow((1 + parameters.getReturnOfInvestment()), i))));

                // date in years must be inverted, it is counted from 0 to durationInYears
                graphPoints.add(new GraphPoint(i, resultFVWithCashFlow.doubleValue()));
            }
        }
        return createResult(parameters, resultFVWithCashFlow, graphPoints);
    }
    private Result createResult(InvestmentParametersDTO investment, BigDecimal resultFVWithCashFlow, ArrayList<GraphPoint> graphPoints) {
        Result result = new Result();
        BigDecimal systematicDeposit = new BigDecimal(investment.getSystematicDepositValue().toString());

        // invested money = initial + (systematicDepositValue * (DurationInYears * FrequencyInYear))
        BigDecimal investedMoney = new BigDecimal(investment.getInitialDepositValue().toString());

        if(investment.getFrequencyInYears() != 0) {
            investedMoney = investedMoney.add(
                    systematicDeposit.multiply(
                            BigDecimal.valueOf(investment.getDurationInYears() / investment.getFrequencyInYears())));
        }
        // setting members of result
        result.setReturnOfInvestment(investment.getReturnOfInvestment());
        result.setRateOfReturnValue(resultFVWithCashFlow.subtract(investedMoney));
        if(investedMoney.doubleValue() != 0.0) {
            result.setRateOfReturnPercentage(result.getRateOfReturnValue().divide(investedMoney, RoundingMode.FLOOR).doubleValue());
        }
        else {
            result.setRateOfReturnPercentage(0.0);
        }
        result.setGraphPointValues(graphPoints);
        result.setXAxisDataType(getDateType(investment.getDurationInYears()));
        result.setYAxisDataType(POUNDS);
        return result;
    }
    private XDateType getDateType(Double duration){
        return XDateType.YEAR;
    }
}

