package animals.herbivores;

import animals.Animal;

import java.util.Map;

public class Deer extends Herbivore {
    private static Map<Animal, Integer> probability;

    public Deer(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender) {
        super(weight, maxAnimalInOnePlace, maxSpeed, fullySatisfiedFood, gender);
    }


    @Override
    public void dead() {

    }
}
