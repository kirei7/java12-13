import java.io.IOException;
import java.math.RoundingMode;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ApiChanges {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        System.out.println(
//            findSumOfMinAndMax()
//                exceptionally()
//                exceptionallyAsync()
//                exceptionallyCompose()
//                numFormat()
        fileMismatch()
        );
        executorService.shutdown();
    }

    //teeing collector
    public static Integer findSumOfMinAndMax(){
        return Stream.of(1,2,3,4,5)
                .collect(Collectors.teeing(
                        Collectors.minBy(Integer::compareTo),
                        Collectors.maxBy(Integer::compareTo),
                        (min, max) -> Integer.sum(min.orElse(0), max.orElse(0))
                ));
    }

    //completable stage old (synchronous) error handling
    public static Integer exceptionally() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Throw exception in: " + Thread.currentThread().getId());
            return Integer.valueOf(null);
        }).exceptionally(e -> {
            System.out.println("Handling exception in: " + Thread.currentThread().getId());
            return -2;
        }).get();
    }

    public static Integer exceptionallyAsync() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Throw exception in: " + Thread.currentThread().getId());
            return Integer.valueOf(null);
        }).exceptionallyAsync(e -> {
            System.out.println("Handling exception in: " + Thread.currentThread().getId());
            return -2;
        }, executorService).get();
    }

    public static Integer exceptionallyCompose() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Throw exception in: " + Thread.currentThread().getId());
            return Integer.valueOf(null);
        }).exceptionallyComposeAsync(e -> {
            System.out.println("Handling exception in: " + Thread.currentThread().getId());
            return CompletableFuture.supplyAsync(() -> -2);
        }, executorService).get();
    }

    //compact number format
    public static String numFormat(){
        final long numberToFormat = 15500;

        return List.of(
                Locale.US,
                Locale.UK,
                Locale.GERMANY,
                Locale.ITALY,
                Locale.FRANCE,
                new Locale("uk", "ua"),
                new Locale("pl", "pl")
        ).stream()
                .flatMap(locale -> Stream.of(
                        String.format("%s short: %s",
                                locale.getCountry(),
                                getShortNumberFormat(locale).format(numberToFormat)),
                        String.format("%s  long: %s",
                                locale.getCountry(),
                                getLongNumberFormat(locale).format(numberToFormat))))
                .collect(Collectors.joining("\n"));
    }

    private static NumberFormat getShortNumberFormat(Locale locale) {
        NumberFormat compactNumberInstance = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT);
        compactNumberInstance.setMaximumFractionDigits(1);
        return compactNumberInstance;
    }
    private static NumberFormat getLongNumberFormat(Locale locale) {
        NumberFormat compactNumberInstance = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.LONG);
        compactNumberInstance.setMaximumFractionDigits(1);
        return compactNumberInstance;
    }

    //file mismatch
    public static String fileMismatch() throws IOException {
        Long pos = Files.mismatch(
                Paths.get("src/file1.txt"),
                Paths.get("src/file2.txt"));
        byte[] bytes = Files.readAllBytes(Paths.get("src/file2.txt"));
        return new String(Arrays.copyOfRange(bytes, pos.intValue() -1, bytes.length));
    }
}
