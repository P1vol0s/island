package animals.herbivores;

import animals.Animal;

import java.util.Map;

public class Caterpillar extends Herbivore {
    private static Map<Animal, Integer> probability;

    public Caterpillar(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender, String name) {
        super(gender);
    }

    @Override
    public void dead() {

    }
}
