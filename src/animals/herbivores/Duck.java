package animals.herbivores;

import animals.Animal;

import java.util.Map;

public class Duck extends Herbivore {
    private static Map<Animal, Integer> probability;

    public Duck(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender) {
        super(weight, maxAnimalInOnePlace, maxSpeed, fullySatisfiedFood, gender);
    }

    @Override
    public void dead() {

    }
}
