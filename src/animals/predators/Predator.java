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

/*Прописать атомарно логику поедания животным животного (только в абстрактном классе травоядные)
 * делаем AtomicBool на параметр живо ли животное или нет
 * в методе dead сделать в начале атомарную проверку на то, не убил ли кто-то это животное до нас
 * после реализовать метод смерти животного
 * и уже в методе eat класса predator реализовать cas проверку после съедения животного на то, не влез ли кто-то раньше нас.
 * Если кто-то влез, то просто безопасно прервать метод съедения  */
