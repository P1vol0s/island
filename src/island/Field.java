package island;


import animals.Animal;
import plants.Grass;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Field {
    private final List<List<Cell>> gameField;
    private static Field field;
    public int wight;
    public int height;



    private Field(int height, int width) {
        gameField = IntStream.range(0, height)
                .mapToObj(i -> IntStream.range(0, width)
                        .mapToObj(j-> new Cell())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        this.height = height;
        this.wight = width;
    }

    public static Field initialization(int length, int width){
        if (field == null)
            field = new Field(length, width);

        return field;
    }

    public List<List<Cell>> getGameField() {
        return gameField;
    }

    public void moveAnimal(Animal animal, int newX, int newY){
        gameField.get(newY).get(newX).addAnimalInCell(animal);
        gameField.get(newY).get(newX).removeAnimalInCell(animal);
    }

    public void moveGrass(Grass grass, int newX, int newY){
        gameField.get(newY).get(newX).addGrassInCell(grass);
        gameField.get(newY).get(newX).removeGrassInCell(grass);
    }

    public Cell getCellByCoords(int x, int y){
        if(x > wight || x < 0 || y > height || y < 0) return null;
        return gameField.get(y).get(x);
    }
}
