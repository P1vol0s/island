
import settings.GameSettings;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        for(String i: GameSettings.getAnimals().keySet()){
            System.out.println(i + ": " + GameSettings.getAnimals().get(i));
        }
    }
}