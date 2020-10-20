package pl.fintech.metisfinancialcalculator.fincalcservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Category;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvestmentInPorfolioDTO {
    private Long id;
    private String name;
    private Double rateOfReturnPercentage;
    private String category;
    private List<Point.Double> graphPointsValue;
    private Double graphPointsFrequenceInYear;
}
