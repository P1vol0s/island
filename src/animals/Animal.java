package animals;

public abstract class Animal {
    private int id;
    private final int WEIGHT;
    private final int MAX_ANIMAL_IN_ONE_PLACE;
    private final int MAX_SPEED;
    private final int FULLY_SATISFIED_FOOD;
    private final String GENDER;
    private final Animal PARTNER;


    public Animal(int weight, int maxAnimalInOnePlace, int maxSpeed, int fullySatisfiedFood, String gender, Animal partner) {
        WEIGHT = weight;
        MAX_ANIMAL_IN_ONE_PLACE = maxAnimalInOnePlace;
        MAX_SPEED = maxSpeed;
        FULLY_SATISFIED_FOOD = fullySatisfiedFood;
        GENDER = gender;
        PARTNER = partner;
    }


    public int getId() {
        return id;
    }

    public int getWEIGHT() {
        return WEIGHT;
    }

    public String getGENDER() {
        return GENDER;
    }

    public Animal getPARTNER() {
        return PARTNER;
    }

    public int getMAX_ANIMAL_IN_ONE_PLACE() {
        return MAX_ANIMAL_IN_ONE_PLACE;
    }

    public int getMAX_SPEED() {
        return MAX_SPEED;
    }

    public int getFULLY_SATISFIED_FOOD() {
        return FULLY_SATISFIED_FOOD;
    }

    public abstract void eat();

    public void sex(Animal animal) {

    }
//   TODO: метод sex() это метод размножения животных. Т.к все животные размножаются (не важно травоядные или хищники).
//    Этот метод будет реализован по следующему принципу: при вызове этого метода у наследника класса происходит первая
//    проверка на то, одинакового ли вида животные. Если эта проверка прошла успешна, то идет следующая проверка на
//    пол животных. Если они одного пола, то выбрасывается исключение о однополых контактах. После идет проверка на шка-
//    лу голода. Если она заполнена меньше, чем на 1\3 у одного из животных, то процесс секса будет приостановлен
//    по причине того, что одно из животных слишком голодно и у него не хватит сил на процесс. Если эта проверка пройдена,
//    то процесс можно считать начатым. Во время процесса коэффициент голода у обоих животных увеличивается в 2 раза и
//    эти 2 животных делаются просто неактивными на 3 секунды. После полового акта у самки есть 50% вероятность того, что
//    она забеременеет. Если она забеременеет, то самец следующие 20 сек ходит около самки, и если на эту клетку вдруг по-
//    падает животное, которое может съесть именно эту особь, то съедают самца, а самка перемещается на какое-то большое
//    расстояние, чтобы спастись. Если съедают беременную самку, то ее малыш тоже умирает. Через 20 сек после секса самка
//    с 95% вероятностью рожает, а если она не рожает, то значит у нее выкидыш



    public abstract void choose_a_side();

    public abstract void move();
//    TODO: метод обращается к методы изменения состояния поля и изменяет координаты, учитывая выбранную сторону перемещения и выбранную
//     скорость (скорость выбирается в зависимости от обстоятельств, метод, который будет изменять предпочтительную скорость,
//     будет находиться в абстрактных классах типов животных). Метод движение должен учитывать, есть ли у животного партнер (
//     под формулировкой партнера я имею ввиду животное, которое залетело от тебя)


}
