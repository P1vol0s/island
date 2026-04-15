package settings;

import exceptions.NegaiveValue;
import exceptions.StringInIntegerValue;
import exceptions.TitleOfCSVIsNotString;

import java.util.Arrays;
import java.util.List;

public class Validation {
    public static void validateTitle(List<?> title, String fileName){
        if (!title.stream().allMatch(i -> i.toString().chars().allMatch(Character::isLetter))) {
            try {
                throw new TitleOfCSVIsNotString();
            } catch (TitleOfCSVIsNotString e) {
                System.err.printf("В файле %s титульные заголовки имеют в себе буквы. Измените файл и запустите игру заново\n", fileName);
            }
        }
    }

    public static void validateValues(List<String[]> rows, String fileName){
         if (rows.stream().anyMatch(i -> Arrays.stream(i).anyMatch(j -> j.matches("(?!-1$)(?!\\d+([.,]\\d+)?$).+")))){
             try{
                 throw new StringInIntegerValue();
             } catch (StringInIntegerValue e) {
                 System.err.printf("В файле %s замечены невалидные данные. Измените файл и запустите игру заново\n", fileName);
             }
         }

    }


}
