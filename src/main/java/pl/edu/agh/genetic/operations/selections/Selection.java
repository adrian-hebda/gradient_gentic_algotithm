package pl.edu.agh.genetic.operations.selections;

import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.operations.Step;

import java.util.List;

public interface Selection {
    List<Chromosome> performSelection(Population population);
}
