import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class FourMillions {

    /**
     * Класс-счётчик.
     */
    static class Counter {

        /**
         * Буфер счёта
         * Делаем нашу переменную атомарной, чтобы исправить ошибку.
         */
        private final AtomicLong count = new AtomicLong();

        /**
         * Считаем +1
         * Атомарно инкрементируем переменную count: это позволяет безопасно выполнять вычисление при параллельных потоках без блокировок и синхронизации.
         * Обычная операция инкремента выполняется в несколько шагов, что приводит в ошибкам,
         * а с помощью атомарной функции, поток проверяет было ли изменено значение и производит свое вычисление с уже измененным значением.
         */
        public void increment() {
            count.incrementAndGet();
        }

        /**
         * Получить текущее значение счётчика
         */
        public AtomicLong getCount() {
            return count;
        }
    }

    private final static int N_THREADS = 4;

    /**
     * Точка входа в программу
     *
     * @param args арг-ты командной строки
     */
    public static void main(String[] args) {
        Counter counter = new Counter();

        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

        // создаём java.Util.Stream для интов щт 0 до 4 (искд.)
        // * не путать Stream и Thread
        CompletableFuture<?>[] futures = IntStream.range(0, N_THREADS)
                // вместо каждой цифры запускаем инкременты счётчика
                .mapToObj(ignored -> runCounting(counter, executorService))
                // собираем CompletableFuture'ы в массив
                .toArray(CompletableFuture[]::new);

        // когда все потоки завершат свою работу
        CompletableFuture.allOf(futures).thenRun(() -> {
            // имеем шанс не получить 4 млн
            System.out.println("Total count: " + counter.getCount());
            executorService.shutdown();
        });
    }

    /**
     * Запускает миллион инкрементов счётчика в отдельном потоке
     *
     * @param counter         счётчик для инкрементов
     * @param executorService пул потоков для работы
     * @return CompletableFuture без результата, разрешаемый после завершения инкрементаций
     */
    private static CompletableFuture<?> runCounting(Counter counter, ExecutorService executorService) {
        return CompletableFuture.runAsync(() -> {
            for(int j = 0; j < 1000000; j++) {
                counter.increment();
            }
        }, executorService);
    }
}
