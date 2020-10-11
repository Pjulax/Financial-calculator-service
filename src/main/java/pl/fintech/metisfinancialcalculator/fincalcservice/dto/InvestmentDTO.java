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
public class InvestmentDTO {
    Long id;
    String name;
    double rateOfReturnPercentage;
    Category category;
    List<Point.Double> graphPointsValue;
    double graphPointsFrequenceInYear;
}
