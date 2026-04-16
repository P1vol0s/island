package engine;

import animals.Animal;
import island.Cell;
import island.Field;
import settings.GameSettings;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EngineOutTick {

    private final Field field = Field.initialization(GameSettings.getField().getFirst(), GameSettings.getField().getLast());

    public enum toDo {toSex, toEat, toBirth, toDie, toMove}

    private void setHunger(Cell cell) {
        for (Animal animal : cell.getAnimalsInCell()) {
            if (!animal.isBlocked) animal.hungerScale -= 8.3F;
            if(animal.hungerScale < 60) animal.isHunger = true;
        }
    }


    private void choseToDO(Cell cell) {
        for (Animal animal : cell.getAnimalsInCell()) {
            if (isDying(animal)) {
                addToDoList(animal, ListsToDo.animalsToDie, toDo.toDie);
                continue;
            }
            if (isReadyToBrith(animal)) {
                addToDoList(animal, ListsToDo.animalsToBirth, toDo.toBirth);
                continue;
            }
            if (shouldEat(animal)) {
                addToDoList(animal, ListsToDo.animalsToEat, toDo.toEat);
                continue;
            }
            if (shouldMove(animal)) {
                addToDoList(animal, ListsToDo.animalsToMove, toDo.toMove);
                continue;
            }
            if (shouldSex(animal)) {
                addToDoList(animal, ListsToDo.animalsToSex, toDo.toSex);
                continue;
            }
            addToDoList(animal, ListsToDo.animalsToMove, toDo.toMove);
        }


    }

    private boolean isDying(Animal animal) {
        return animal.isHunger || animal.isDied;


    }

    private boolean isReadyToBrith(Animal animal) {
        return animal.isPregnant && animal.countTimeInPregnant == 0 && !animal.isDied && new Random().nextInt(100) < 95 && !animal.isBlocked;


    }

    private boolean shouldEat(Animal animal) {
        Cell cell = field.getCellByCoords(animal.x, animal.y);
        boolean isDangerInCell = cell.getAnimalsInCell().stream()
                .filter(a -> GameSettings.getProbability().get(a.name).get(animal.name) > 0)
                .anyMatch(a -> !a.equals(animal));
        boolean isEatInYourCell = cell.getAnimalsInCell().stream()
                .filter(a -> GameSettings.getProbability().get(animal.name).get(a.name) > 0)
                .anyMatch(a ->!a.equals(animal));
        return !isDangerInCell && isEatInYourCell && animal.isHunger && !animal.isBlocked;

    }

    private boolean shouldMove(Animal animal) {
        Cell cell = field.getCellByCoords(animal.x, animal.y);
        boolean isDangerInCell = cell.getAnimalsInCell().stream()
                .filter(a -> GameSettings.getProbability().get(a.name).get(animal.name) > 0)
                .anyMatch(a -> !a.equals(animal));
        boolean isEatInYourCell = cell.getAnimalsInCell().stream()
                .filter(a -> GameSettings.getProbability().get(animal.name).get(a.name) > 0)
                .anyMatch(a ->!a.equals(animal));
        return isDangerInCell && !isEatInYourCell && !animal.isBlocked;

    }

    private boolean shouldSex(Animal animal) {
        Cell cell = field.getCellByCoords(animal.x, animal.y);
        boolean isPartner = cell.getAnimalsInCell().stream()
                .filter(a -> a.name.equals(animal.name))
                .filter(a -> !a.GENDER.equals(animal.GENDER))
                .anyMatch(a -> !a.equals(animal));
        return !animal.isHunger && !animal.isPregnant && !animal.isBlocked && isPartner;

    }


    private void addToDoList(Animal animal, ConcurrentLinkedQueue<Animal> toDoList, toDo toDo) {
        animal.toDo = toDo;
        toDoList.add(animal);
    }


}
