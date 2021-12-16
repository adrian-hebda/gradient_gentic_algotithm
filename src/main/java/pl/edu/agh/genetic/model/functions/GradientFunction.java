package pl.edu.agh.genetic.model.functions;

import pl.edu.agh.genetic.model.Gradient;

public interface GradientFunction extends Gradient {
    Double[] calculateGradient(Double... parameters);
}
