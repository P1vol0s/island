package plants;

import island.Field;

import java.util.concurrent.atomic.AtomicInteger;

public class Grass {
    private final AtomicInteger weight;
    private static final int factor = 10;

    public Grass() {
        weight = new AtomicInteger(1_000);
    }

    public int eatGrass(int amount) {
        while (true) {
            int current = weight.get();
            if (current <= 0) return 0;
            int actualEat = Math.min(current, amount);
            int newWeight = current - actualEat;
            if (weight.compareAndSet(current, newWeight)) return actualEat;
        }
    }

    public void grow() {
        if (weight.get() + 10 > 1_000)
            weight.set(1_000);
        else
            weight.addAndGet(10);
    }
}

