package pl.edu.agh.genetic.operations;
import pl.edu.agh.genetic.model.Population;

public interface Step {
    void performStep(Population population);
}
