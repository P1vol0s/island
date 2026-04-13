package island;

import animals.Animal;
import plants.Grass;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Cell {


    private List<Animal> animalsInCell;
    private Deque<Grass> grassInCell;

    public Cell() {
        animalsInCell = new ArrayList<>();
        grassInCell = new ConcurrentLinkedDeque<>();
    }

    public List<Animal> getAnimalsInCell() {
        return animalsInCell;
    }

    public Deque<Grass> getGrassInCell() {
        return grassInCell;
    }

    public void addAnimalInCell(Animal animal){
        animalsInCell.add(animal);
    }

    public void addGrassInCell(Grass grass){
        grassInCell.push(grass);
    }

}
