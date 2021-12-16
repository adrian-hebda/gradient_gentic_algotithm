package pl.edu.agh.genetic.operations.mutations;

import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.operations.Step;

public interface Mutation{
    public void performMutation(Population population);
}
