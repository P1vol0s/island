package animals.predators;

import animals.Animal;

import java.util.Map;

public class Boa extends Predator {
    private static Map<Animal, Integer> probability;

    public Boa(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender) {
        super(weight, maxAnimalInOnePlace, maxSpeed, fullySatisfiedFood, gender);
    }

    @Override
    public void dead() {

    }
}
