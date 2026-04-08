package animals.herbivores;


import animals.Animal;

public abstract class Herbivore extends Animal {
    public Herbivore(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood) {
        super(weight, maxAnimalInOnePlace, maxSpeed, fullySatisfiedFood);
    }

    @Override
    public void eat() {

    }
}
