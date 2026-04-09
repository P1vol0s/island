import animals.predators.Bear;
import island.Field;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Field field = Field.initialization(10, 10);
        System.out.println(field.getGameField());
        List<List<String>> list = new ArrayList<>(5);
        System.out.println(list);

    }
}