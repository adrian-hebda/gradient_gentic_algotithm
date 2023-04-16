package pl.edu.agh.genetic.model;

import lombok.Data;
import pl.edu.agh.genetic.model.functions.GradientFunction;

import java.util.stream.Stream;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Data
public class GradientData {
    private Double lenOfGradVector = Double.NaN;
    // Value of gradient coefficients calculated for particular point.
    private Double[] gradient;

    public GradientData(GradientFunction function, Double[] functionArguments){
        if(Stream.of(functionArguments).anyMatch(aDouble -> !Double.isFinite(aDouble))){
            gradient = new Double[functionArguments.length];
            for(int i = 0; i < functionArguments.length; i++){
                gradient[i] = Double.NaN;
            }
            return;
        }
        gradient = function.calculateGradient(functionArguments);
        calculateGradientVectorLength(functionArguments);
    }

    private void calculateGradientVectorLength(Double[] functionArguments) {
        Double sumOfXiSquared = 0.0;
        for (int i = 0; i < functionArguments.length; i++) {
            sumOfXiSquared += pow(gradient[i] - functionArguments[i], 2);
        }
        lenOfGradVector = sqrt(sumOfXiSquared);
    }


}
