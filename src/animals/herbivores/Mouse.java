package animals.herbivores;

import animals.Animal;

import java.util.Map;

public class Mouse extends Herbivore {
    private static Map<Animal, Integer> probability;

    public Mouse(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender) {
        super(gender);
    }

    @Override
    public void dead() {

    }
}
