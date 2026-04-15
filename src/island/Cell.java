package island;

import animals.Animal;
import animals.predators.Predator;
import plants.Grass;
import settings.GameSettings;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cell {


    private List<Animal> animalsInCell;
    private Deque<Grass> grassInCell;
    public int x;
    public int y;
    public Map<String, Integer> GameObjectsInCell;


    public Cell() {
        animalsInCell = new ArrayList<>();
        grassInCell = new ConcurrentLinkedDeque<>();
        GameObjectsInCell = IntStream.range(0, GameSettings.getGameObjects().length)
                .boxed()
                .collect(Collectors.toMap(
                        key ->GameSettings.getGameObjects()[key],
                        value -> 0
                )
        );
    }


    public List<Animal> getAnimalsInCell() {
        return animalsInCell;
    }

    public Deque<Grass> getGrassInCell() {
        return grassInCell;
    }

    public void addAnimalInCell(Animal animal) {
        animalsInCell.add(animal);
    }

    public void addGrassInCell(Grass grass) {
        grassInCell.push(grass);
    }

    public void removeAnimalInCell(Animal animal) {
        animalsInCell.remove(animal);
    }

    public void removeGrassInCell(Grass grass){
        grassInCell.remove(grass);
    }




}
