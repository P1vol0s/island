package engine;

import animals.Animal;
import island.Cell;
import island.Field;
import settings.GameSettings;

import java.util.*;
import java.util.stream.Collectors;

public class EngineInTick {

    Field field = Field.initialization(GameSettings.getField().getFirst(), GameSettings.getField().getLast());

    private void removeAnimal(Animal animal) {
        animal.dead();
        field.getCellByCoords(animal.x, animal.y).removeAnimalInCell(animal);
    }

    private void doBrith() {
        for (Animal animal : ListsToDo.animalsToBirth) {
            Animal newAnimal = animal.giveBrith();
            field.getCellByCoords(animal.x, animal.y).addAnimalInCell(newAnimal);
        }
        ListsToDo.animalsToBirth.clear();
    }

    private void doDie() {
        for (Animal animal : ListsToDo.animalsToDie) {
            removeAnimal(animal);
        }
        ListsToDo.animalsToDie.clear();
    }

    private void doEat() {
        for (Animal animal : ListsToDo.animalsToEat) {
            var tempAnimalList = field.getCellByCoords(animal.x, animal.y).getAnimalsInCell();
            Animal bestToEatAnimal = findBestPrey(tempAnimalList, animal);
            if (bestToEatAnimal != null && animal.eat(bestToEatAnimal)) removeAnimal(bestToEatAnimal);
        }
        ListsToDo.animalsToEat.clear();
    }

    private void doMove() {
        for (Animal animal : ListsToDo.animalsToMove) {
            Cell bestCell = findBestCell(animal);
            field.getCellByCoords(animal.x, animal.y).removeAnimalInCell(animal);
            animal.move(bestCell);
        }
        ListsToDo.animalsToMove.clear();
    }

    private void doSex() {
        for (Animal animal : validateSexList().stream().
                flatMap(Collection::stream).
                toList()) {
            animal.sex();
        }
        ListsToDo.animalsToSex.clear();
    }

    private Animal findBestPrey(List<Animal> animals, Animal predator) {
        return animals.stream()
                .filter(animal -> !animal.isBlocked)
                .filter(animal -> !animal.isDied)
                .filter(animal -> !animal.equals(predator))
                .filter(animal -> GameSettings.getProbability()
                        .get(predator.name)
                        .get(animal.name) > 0)
                .max(Comparator.comparingDouble(animal -> score(animal, predator)))
                .orElse(null);
    }

    private double score(Animal prey, Animal predator) {
        double weightScore = 1.0 / (1.0 + Math.abs(prey.getWeight() - (predator.getEat() / 100 * predator.hungerScale)));
        double satietyScore = 1.0 / (1.0 + prey.hungerScale);
        double probabilityScore = GameSettings.getProbability()
                .get(predator.name)
                .get(prey.name) / 100.0;
        return weightScore + satietyScore + probabilityScore;
    }


    private double cellScore(Cell cell, Animal animal) {
        long foodCount = cell.getAnimalsInCell().stream()
                .filter(a -> GameSettings.getProbability().get(animal.name).get(a.name) > 0)
                .filter(a -> !a.equals(animal))
                .count();

        long partnerCount = cell.getAnimalsInCell().stream()
                .filter(a -> a.name.equals(animal.name))
                .filter(a -> !a.GENDER.equals(animal.GENDER))
                .filter(a -> !a.isBlocked && !a.isPregnant)
                .count();

        long enemyCount = cell.getAnimalsInCell().stream()
                .filter(a -> GameSettings.getProbability().get(a.name).get(animal.name) > 0)
                .filter(a -> !a.equals(animal))
                .count();

        return (foodCount * 1.0) + (partnerCount * 0.5) - (enemyCount * 1.5);
    }


    private Cell findBestCell(Animal animal) {
        float speed = animal.getSpeed();
        List<Cell> reachableCells = new ArrayList<>();
        for (int steps = 1; steps <= (int) speed; steps++) {
            Cell up = field.getCellByCoords(animal.x, animal.y - steps);
            Cell down = field.getCellByCoords(animal.x, animal.y + steps);
            Cell left = field.getCellByCoords(animal.x - steps, animal.y);
            Cell right = field.getCellByCoords(animal.x + steps, animal.y);

            for (Cell cell : List.of(up, down, left, right)) {
                if (cell == null) continue;
                if (cell.getAnimalsInCell().size() >= animal.getOnScale()) continue;
                reachableCells.add(cell);
            }
        }
        return reachableCells.stream()
                .max(Comparator.comparingDouble(cell -> cellScore(cell, animal)))
                .orElse(field.getCellByCoords(animal.x, animal.y));
    }


    private List<List<Animal>> validateSexList() {
        var maleList = ListsToDo.animalsToSex.stream()
                .filter(i -> i.GENDER.equals("M"))
                .collect(Collectors.toCollection(ArrayList::new));
        var feMaleList = ListsToDo.animalsToSex.stream()
                .filter(i -> i.GENDER.equals("F"))
                .collect(Collectors.toCollection(ArrayList::new));
        var minSize = Math.min(maleList.size(), feMaleList.size());
        maleList.subList(minSize, maleList.size()).clear();
        feMaleList.subList(minSize, feMaleList.size()).clear();
        return Arrays.asList(maleList, feMaleList);
    }
}
