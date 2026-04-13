package settings;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameSettings {
    public static final Map<String, Map<String, Integer>> probability;
    public static final String[] GAME_OBJECTS;
    public static final List<Integer> field;
    public static final Map<String, Map<String, Float>> animals;
    private static final Path probabilityPath = Path.of("src\\settings\\csvConfig\\probability.csv");
    private static final Path fieldPath = Path.of("src\\settings\\csvConfig\\field.csv");
    private static final Path animalsPath = Path.of("src\\settings\\csvConfig\\animals.csv");
    private static final Path configDirPath = Path.of("src\\settings\\csvConfig");

    static {
        GAME_OBJECTS = readTitle(probabilityPath);
        probability = readProbabilitySettings();
        field = readFieldSettings();
        animals = readAnimalsSettings();
    }

    private static String[] readTitle(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.filter(line -> line.chars().anyMatch(Character::isLetter))
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .toList().toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Map<String, Integer>> readProbabilitySettings() {
        try (Stream<String> lines = Files.lines(GameSettings.probabilityPath)) {
            List<String[]> rows = lines
                    .skip(1)
                    .map(line -> line.split(","))
                    .toList();
            return IntStream.range(0, rows.size())
                    .boxed()
                    .collect(Collectors.toMap(
                            rowIndex -> GAME_OBJECTS[rowIndex],   // внешний ключ
                            rowIndex -> {
                                String[] parts = rows.get(rowIndex);
                                Map<String, Integer> inner = new HashMap<>();
                                for (int colIndex = 0; colIndex < GAME_OBJECTS.length; colIndex++)
                                    if (rowIndex != colIndex)
                                        inner.put(GAME_OBJECTS[colIndex], Integer.parseInt(parts[colIndex]));
                                return inner;
                            }
                    ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> readFieldSettings() {
        try (Stream<String> lines = Files.lines(fieldPath)) {
            return lines
                    .skip(1)
                    .flatMap(line -> Arrays.stream(line.split(","))
                            .map(Integer::parseInt))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Map<String, Float>> readAnimalsSettings() {
        AtomicInteger rowIndex = new AtomicInteger(0);

        String[] title = readTitle(animalsPath);
        try (Stream<String> lines = Files.lines(animalsPath)) {
            return lines
                    .skip(1)
                    .map(line -> line.split(","))
                    .collect(Collectors.toMap(
                            key -> GAME_OBJECTS[rowIndex.getAndIncrement()],
                            value -> {
                                Map<String, Float> inner = new HashMap<>();
                                for (int colIndex = 0; colIndex < title.length; colIndex++)
                                    inner.put(title[colIndex], Float.parseFloat(value[colIndex].trim().replace("\"", "")));
                                return inner;
                            }

                    ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkSettings() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            configDirPath.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            Map<Path, Long> lastEventTime = new HashMap<>();

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

                    if (Objects.equals(fullPath, animalsPath.toAbsolutePath())) readAnimalsSettings();
                    if (Objects.equals(fullPath, fieldPath.toAbsolutePath())) readFieldSettings();
                    if (Objects.equals(fullPath, probabilityPath.toAbsolutePath())) readProbabilitySettings();
                }
                wk.reset();
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
