package island;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final List<List<Cell>> gameField;
    private static Field field;

    private Field(int length, int width) {
        gameField = new ArrayList<>(length);
        gameField.forEach(element -> new ArrayList<>(width));
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
