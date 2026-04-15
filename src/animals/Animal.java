package animals;

import engine.EngineOutTick;
import island.Cell;
import settings.GameSettings;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Animal {
    public final String GENDER;
    public float hungerScale = 100;
    public EngineOutTick.toDo toDo;
    public String name;
    protected AtomicBoolean isDead;
    public int countTimeInBlock;
    public int countTimeInPregnant;
    public int x;
    public int y;
    public boolean isPregnant = false;
    public boolean isDangerous = false;
    public boolean isHunger = false;
    public boolean isBlocked = false;
    public boolean isDied = false;


    public Animal() {
        GENDER = Arrays.asList("M", "F").get(new Random().nextInt(2));
        isDead = new AtomicBoolean(false);
    }

    public Animal giveBrith() {
        isPregnant = false;
        isBlocked = true;
        countTimeInBlock = 2;
        hungerScale -= 8.3F;
        Animal newAnimal = createNewAnimal();
        newAnimal.x = this.x;
        newAnimal.y = this.y;
        return newAnimal;

    }

    public boolean eat(Animal bestToEatAnimal){
        var probabilityToEat = GameSettings.getProbability().get(name).get(bestToEatAnimal.name);
        if(new Random().nextInt(101) < probabilityToEat){
            if(bestToEatAnimal.getWeight() > getEat() / 100 * hungerScale){
                hungerScale = 100;
            }else{
                hungerScale += bestToEatAnimal.getWeight();
            }
            return true;
        }
        return false;
    }

    public void setDangerous() {

    }


    public void sex() {
        if (GENDER.equals("F") && new Random().nextInt(100) >= 50) {
            isPregnant = true;
            countTimeInPregnant = 16;
        }
        isBlocked = true;
        hungerScale -= 33.2F;
        countTimeInBlock = 2;
    }


    public void dead(){
        isDied = true;
    }


    protected abstract Animal createNewAnimal();

    public Float getWeight() {
        return GameSettings.getAnimals().get(name).get("weight");
    }

    public Float getOnScale() {
        return GameSettings.getAnimals().get(name).get("onCell");
    }

    public Float getSpeed() {
        return GameSettings.getAnimals().get(name).get("speed");
    }

    public Float getEat() {
        return GameSettings.getAnimals().get(name).get("eat");
    }



    public void move(Cell bestCell) {
        x = bestCell.x;
        y = bestCell.y;
        bestCell.addAnimalInCell(this);

    }
}

