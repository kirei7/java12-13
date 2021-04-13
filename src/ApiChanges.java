import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ApiChanges {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(
//            findSumOfMinAndMax()
//                exceptionally()
//                exceptionallyAsync()
                exceptionallyCompose()
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


    //files mismatch
}
