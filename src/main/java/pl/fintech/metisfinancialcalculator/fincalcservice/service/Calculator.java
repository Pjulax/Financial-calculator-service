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


import java.util.List;

import static pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType.POUNDS;

@Service
@NoArgsConstructor
public class Calculator {

    public Result calculateInvestment(InvestmentParametersDTO parameters){
        // resultFV calculation
        BigDecimal resultFVWithCashFlow = new BigDecimal(parameters.getInitialDepositValue()).multiply(
                                BigDecimal.valueOf(
                                    Math.pow((1+parameters.getReturnOfInvestment()),
                                            parameters.getDurationInYears())));

        // resultFV with cash flow calculation and graph points creating
        ArrayList<GraphPoint> graphPoints = new ArrayList<GraphPoint>();
        BigDecimal systematicDeposit = new BigDecimal(parameters.getSystematicDepositValue());
        for(double i = parameters.getDurationInYears(); i >= 0; i -= parameters.getFrequenceInYear()){
            resultFVWithCashFlow = resultFVWithCashFlow.add(systematicDeposit)
                                                        .multiply(BigDecimal.valueOf(Math.pow((1+parameters.getReturnOfInvestment()), i)));

            // date in years must be inverted, it is counted from 0 to durationInYears
            graphPoints.add(new GraphPoint(resultFVWithCashFlow.doubleValue(),(parameters.getDurationInYears().doubleValue() - i)));
        }
        return createResult(parameters, resultFVWithCashFlow, graphPoints);
    }
    private Result createResult(InvestmentParametersDTO investment, BigDecimal resultFVWithCashFlow, ArrayList<GraphPoint> graphPoints) {
        Result result = new Result();
        BigDecimal systematicDeposit = new BigDecimal(investment.getSystematicDepositValue());

        // invested money = initial + (systematicDepositValue * (DurationInYears * FrequenceInYear))
        BigDecimal investedMoney = cloneBigDecimal(systematicDeposit);
        investedMoney = investedMoney.add(
                systematicDeposit.multiply(
                BigDecimal.valueOf(investment.getDurationInYears()* investment.getFrequenceInYear())));

        // setting members of result
        result.setRateOfReturnValue(resultFVWithCashFlow.subtract(investedMoney));
        result.setRateOfReturnPercentage(result.getRateOfReturnValue().divide(investedMoney,RoundingMode.FLOOR).doubleValue());

        // possibly this member could be better to be BigDecimal, not double
        result.setAnnualValueDifference(result.getRateOfReturnValue().divide(BigDecimal.valueOf(investment.getDurationInYears()), RoundingMode.FLOOR));
        result.setGraphPointsFrequenceInYear(investment.getFrequenceInYear());
        result.setGraphPointValues(graphPoints);
        result.setXAxisDataType(getDateType(investment.getDurationInYears()));
        result.setYAxisDataType(POUNDS);
        return result;
    }
    private BigDecimal cloneBigDecimal(BigDecimal bigDecimal){
        return new BigDecimal(bigDecimal.toString());
    }
    private XDateType getDateType(Double duration){
        if(duration>=3)
            return XDateType.YEAR;
        if(duration>=0.25)
            return XDateType.MONTH;
        else
            return XDateType.DAY;
    }
}

