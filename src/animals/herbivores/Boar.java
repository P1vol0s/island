package animals.herbivores;

import animals.Animal;

import java.util.Map;

public class Boar extends Herbivore {
    private static Map<Animal, Integer> probability;

    public Boar(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender, String name) {
        super(gender);
    }

    @Override
    public void dead() {

    }
}
