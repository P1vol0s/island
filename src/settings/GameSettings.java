package settings;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameSettings {
    private static ConcurrentHashMap<String, Map<String, Integer>> probability;
    public static final String[] GAME_OBJECTS;
    private static CopyOnWriteArrayList<Integer> field;
    private static ConcurrentHashMap<String, Integer> quantity;
    private static ConcurrentHashMap<String, Map<String, Float>> animals;
    private static final Path probabilityPath = Path.of("src\\settings\\csvConfig\\probability.csv");
    private static final Path fieldPath = Path.of("src\\settings\\csvConfig\\field.csv");
    private static final Path animalsPath = Path.of("src\\settings\\csvConfig\\animals.csv");
    private static final Path quantityPath = Path.of("src\\settings\\csvConfig\\quantity.csv");
    private static final Path configDirPath = Path.of("src\\settings\\csvConfig");
    static {
        GAME_OBJECTS = readTitle(probabilityPath);
        probability = readProbabilitySettings();
        field = readFieldSettings();
        animals = readAnimalsSettings();
        quantity = readQuantity();
    }

    public static ConcurrentHashMap<String, Map<String, Integer>> getProbability() {
        return probability;
    }

    public static String[] getGameObjects() {
        return GAME_OBJECTS;
    }

    public static CopyOnWriteArrayList<Integer> getField() {
        return field;
    }

    public static ConcurrentHashMap<String, Integer> getQuantity() {
        return quantity;
    }

    public static ConcurrentHashMap<String, Map<String, Float>> getAnimals() {
        return animals;
    }




    private static List<String[]> getCSVValue(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            var list = lines
                    .skip(1)
                    .map(line -> line.split(","))
                    .toList();
            Validation.validateValues(list, String.valueOf(path.getFileName()));
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] readTitle(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            var title = lines.filter(line -> line.chars().anyMatch(Character::isLetter))
                    .findFirst()
                    .stream()
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .toList().toArray(new String[0]);
            Validation.validateTitle(Arrays.asList(title), String.valueOf(path.getFileName()));
            return title;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ConcurrentHashMap<String, Map<String, Integer>> readProbabilitySettings() {
        List<String[]> rows = getCSVValue(probabilityPath);
        return IntStream.range(0, rows.size())
                .boxed()
                .collect(Collectors.toMap(
                        rowIndex -> GAME_OBJECTS[rowIndex],
                        rowIndex -> {
                            String[] parts = rows.get(rowIndex);
                            ConcurrentHashMap<String, Integer> inner = new ConcurrentHashMap<>();
                            for (int colIndex = 0; colIndex < GAME_OBJECTS.length; colIndex++)
                                if (rowIndex != colIndex)
                                    inner.put(GAME_OBJECTS[colIndex], Integer.parseInt(parts[colIndex]));
                            return inner;
                        },
                        (a, b) -> a,
                        ConcurrentHashMap::new
                ));

    }

    private static CopyOnWriteArrayList<Integer> readFieldSettings() {
        return Arrays.stream(getCSVValue(fieldPath).getFirst())
                .map(Integer::valueOf)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));


    }

    private static ConcurrentHashMap<String, Map<String, Float>> readAnimalsSettings() {
        AtomicInteger rowIndex = new AtomicInteger(0);

        String[] title = readTitle(animalsPath);
        List<String[]> lines = getCSVValue(animalsPath);
        return lines.stream()
                .collect(Collectors.toMap(
                        key -> GAME_OBJECTS[rowIndex.getAndIncrement()],
                        value -> {
                            ConcurrentHashMap<String, Float> inner = new ConcurrentHashMap<>();
                            for (int colIndex = 0; colIndex < title.length; colIndex++)
                                inner.put(title[colIndex], Float.parseFloat(value[colIndex].trim().replace("\"", "")));

                            float fourthCol = Float.parseFloat(value[3].trim().replace("\"", ""));
                            inner.put("hunger", fourthCol / 12);

                            return inner;
                        },
                        (a, b) -> a,
                        ConcurrentHashMap::new
                ));
    }

    private static ConcurrentHashMap<String, Integer> readQuantity() {
        List<String[]> rows = getCSVValue(quantityPath);
        return IntStream.range(0, rows.getFirst().length)
                .boxed()
                .collect(Collectors.toMap(
                        key -> GAME_OBJECTS[key],
                        value -> Integer.parseInt(rows.getFirst()[value]),
                        (a, b) -> a,
                        ConcurrentHashMap::new
                ));

    }

    public static void checkSettings() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            configDirPath.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            Map<Path, Long> lastEventTime = new ConcurrentHashMap<>();

            while (true) {
                WatchKey wk = watcher.take();

                for (WatchEvent<?> event : wk.pollEvents()) {
                    Path changed = (Path) event.context();

                    if (changed.toString().endsWith("~")) continue;

                    Path fullPath = configDirPath.resolve(changed).toAbsolutePath();

                    long now = System.currentTimeMillis();
                    Long lastTime = lastEventTime.get(changed);

                    if (lastTime != null && (now - lastTime) < 500) continue;

                    lastEventTime.put(changed, now);

                    if (Objects.equals(fullPath, probabilityPath.toAbsolutePath())) probability = readProbabilitySettings();
                    if (Objects.equals(fullPath, fieldPath.toAbsolutePath())) field = readFieldSettings();
                    if (Objects.equals(fullPath, animalsPath.toAbsolutePath())) animals = readAnimalsSettings();
                    if (Objects.equals(fullPath, quantityPath.toAbsolutePath())) quantity = readQuantity();
                }
                wk.reset();
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
