package animals.predators;

import animals.Animal;

public abstract class Predator extends Animal {
    public Predator(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood) {
        super(weight, maxAnimalInOnePlace, maxSpeed, fullySatisfiedFood);
    }

    @Override
    public void eat() {

    }
}
