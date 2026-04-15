package engine;

import animals.Animal;
import island.Cell;
import island.Field;
import settings.GameSettings;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EngineOutTick {

    private final Field field = Field.initialization(GameSettings.getField().getFirst(), GameSettings.getField().getLast());

    public enum toDo {toSex, toEat, toBirth, toDie, toMove}

    private void setHunger(Cell cell) {
        for (Animal animal : cell.getAnimalsInCell()) {
            if (!animal.isBlocked) animal.hungerScale -= 8.3F;
        }
    }


    private void choseToDO(Cell cell) {
        for (Animal animal: cell.getAnimalsInCell()){
            if(isDying(animal)){
                addToDoList(animal, ListsToDo.animalsToDie, toDo.toDie);
                continue;
            }
            if(isReadyToBrith(animal)){
                addToDoList(animal, ListsToDo.animalsToBirth, toDo.toBirth);
                continue;
            }
            if(shouldEat(animal)){
                addToDoList(animal, ListsToDo.animalsToEat, toDo.toEat);
                continue;
            }
            if(shouldMove(animal)){
                addToDoList(animal, ListsToDo.animalsToMove, toDo.toMove);
                continue;
            }
            if(shouldSex(animal)){
                addToDoList(animal, ListsToDo.animalsToSex, toDo.toSex);
                continue;
            }
            addToDoList(animal, ListsToDo.animalsToMove, toDo.toMove);
        }



    }

    private boolean isDying(Animal animal) {
        return animal.hungerScale <= 0 || animal.isDied;


    }

    private boolean isReadyToBrith(Animal animal) {


    }

    private boolean shouldEat(Animal animal) {


    }

    private boolean shouldMove(Animal animal) {


    }

    private boolean shouldSex(Animal animal) {
    }



    private void addToDoList(Animal animal, ConcurrentLinkedQueue<Animal> toDoList, toDo toDo) {
        animal.toDo = toDo;
        toDoList.add(animal);
    }


}
