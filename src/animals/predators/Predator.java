package animals.predators;

import animals.Animal;

public abstract class Predator extends Animal {
    public Predator(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender) {
        super(weight, maxAnimalInOnePlace, maxSpeed, fullySatisfiedFood, gender);
    }

    @Override
    public void choose_a_side() {

    }

    @Override
    public void eat() {

    }
}
