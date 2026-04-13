package island;

import animals.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Field {
    private final List<List<Cell>> gameField;
    private static Field field;

    private Field(int length, int width) {
        gameField = IntStream.range(0, length)
                .mapToObj(i -> IntStream.range(0, width)
                        .mapToObj(j-> new Cell())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public static Field initialization(int length, int width){
        if (field == null)
            field = new Field(length, width);

        return field;
    }

    public List<List<Cell>> getGameField() {
        return gameField;
    }
}
