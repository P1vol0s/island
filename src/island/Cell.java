package island;

import animals.Animal;
import plants.Grass;

import java.util.ArrayList;
import java.util.List;

public class Cell {


    private List<Animal> animalsInCell;
    private List <Grass> grassInCell;

    public Cell() {
        animalsInCell = new ArrayList<>();
        grassInCell = new ArrayList<>();
    }

    public List<Animal> getAnimalsInCell() {
        return animalsInCell;
    }

    public List<Grass> getGrassInCell() {
        return grassInCell;
    }

    public void addAnimalInCell(Animal animal){
        animalsInCell.add(animal);
    }

    public void addGrassInCell(Grass grass){
        grassInCell.add(grass);
    }

}
