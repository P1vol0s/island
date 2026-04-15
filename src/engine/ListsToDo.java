package engine;

import animals.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ListsToDo {
    protected static ConcurrentLinkedQueue<Animal> animalsToSex = new ConcurrentLinkedQueue<>();
    protected static ConcurrentLinkedQueue<Animal> animalsToEat = new ConcurrentLinkedQueue<>();
    protected static ConcurrentLinkedQueue<Animal> animalsToMove = new ConcurrentLinkedQueue<>();
    protected static ConcurrentLinkedQueue<Animal> animalsToBirth = new ConcurrentLinkedQueue<>();
    protected static ConcurrentLinkedQueue<Animal> animalsToDie = new ConcurrentLinkedQueue<>();
}
