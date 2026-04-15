package animals.herbivores;

import animals.Animal;

import java.util.Map;

public class Buffalo extends Herbivore {
    private static Map<Animal, Integer> probability;

    public Buffalo(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender, String name) {
        super(gender);
    }


    @Override
    public void dead() {

    }
}
