package pl.edu.agh.genetic.operations.selections;

import lombok.NoArgsConstructor;
import pl.edu.agh.genetic.model.Chromosome;
import pl.edu.agh.genetic.model.Population;
import pl.edu.agh.genetic.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class TournamentSelection implements Selection {

  private Integer tournamentSize;

  @Override
  public List<Chromosome> performSelection(Population population) {
    List<Chromosome> winners = new ArrayList<>(0);
    for (int i = 0; i < population.getChromosomes().size() / 2; i++) {
      winners.add(tournament(population));
    }
    return winners;
  }

  private Chromosome tournament(Population population) {
    List<Chromosome> tournament = new ArrayList<>();

    int size = tournamentSize != null ? tournamentSize : (int) (population.getChromosomes().size() * 0.2);

    for (int i = 0; i < size; i++) {
      Chromosome chromosome =
          population
              .getChromosomes()
              .get(RandomUtils.getRandomIntInRange(0, population.getChromosomes().size()));
      tournament.add(chromosome);
    }

    return tournament.stream().max(Comparator.comparing(Chromosome::getFitness))
        .orElseThrow(() -> new RuntimeException("The best in torunament not present!s"));
  }
}
