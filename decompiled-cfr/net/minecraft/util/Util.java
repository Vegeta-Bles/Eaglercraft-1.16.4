/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.MoreExecutors
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.DataResult
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.client.util.CharPredicate;
import net.minecraft.datafixer.Schemas;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
    private static final AtomicInteger NEXT_WORKER_ID = new AtomicInteger(1);
    private static final ExecutorService BOOTSTRAP_EXECUTOR = Util.createWorker("Bootstrap");
    private static final ExecutorService MAIN_WORKER_EXECUTOR = Util.createWorker("Main");
    private static final ExecutorService IO_WORKER_EXECUTOR = Util.createIoWorker();
    public static LongSupplier nanoTimeSupplier = System::nanoTime;
    public static final UUID NIL_UUID = new UUID(0L, 0L);
    private static final Logger LOGGER = LogManager.getLogger();

    public static <K, V> Collector<Map.Entry<? extends K, ? extends V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <T extends Comparable<T>> String getValueAsString(Property<T> property, Object object) {
        return property.name((Comparable)object);
    }

    public static String createTranslationKey(String type, @Nullable Identifier id) {
        if (id == null) {
            return type + ".unregistered_sadface";
        }
        return type + '.' + id.getNamespace() + '.' + id.getPath().replace('/', '.');
    }

    public static long getMeasuringTimeMs() {
        return Util.getMeasuringTimeNano() / 1000000L;
    }

    public static long getMeasuringTimeNano() {
        return nanoTimeSupplier.getAsLong();
    }

    public static long getEpochTimeMs() {
        return Instant.now().toEpochMilli();
    }

    private static ExecutorService createWorker(String name) {
        int n = MathHelper.clamp(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
        Object _snowman2 = n <= 0 ? MoreExecutors.newDirectExecutorService() : new ForkJoinPool(n, forkJoinPool -> {
            ForkJoinWorkerThread forkJoinWorkerThread = new ForkJoinWorkerThread(forkJoinPool){

                protected void onTermination(Throwable throwable) {
                    if (throwable != null) {
                        Util.getLogger().warn("{} died", (Object)this.getName(), (Object)throwable);
                    } else {
                        Util.getLogger().debug("{} shutdown", (Object)this.getName());
                    }
                    super.onTermination(throwable);
                }
            };
            forkJoinWorkerThread.setName("Worker-" + name + "-" + NEXT_WORKER_ID.getAndIncrement());
            return forkJoinWorkerThread;
        }, Util::method_18347, true);
        return _snowman2;
    }

    public static Executor getBootstrapExecutor() {
        return BOOTSTRAP_EXECUTOR;
    }

    public static Executor getMainWorkerExecutor() {
        return MAIN_WORKER_EXECUTOR;
    }

    public static Executor getIoWorkerExecutor() {
        return IO_WORKER_EXECUTOR;
    }

    public static void shutdownExecutors() {
        Util.attemptShutdown(MAIN_WORKER_EXECUTOR);
        Util.attemptShutdown(IO_WORKER_EXECUTOR);
    }

    private static void attemptShutdown(ExecutorService service) {
        boolean bl;
        service.shutdown();
        try {
            bl = service.awaitTermination(3L, TimeUnit.SECONDS);
        }
        catch (InterruptedException interruptedException) {
            bl = false;
        }
        if (!bl) {
            service.shutdownNow();
        }
    }

    private static ExecutorService createIoWorker() {
        return Executors.newCachedThreadPool(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("IO-Worker-" + NEXT_WORKER_ID.getAndIncrement());
            thread.setUncaughtExceptionHandler(Util::method_18347);
            return thread;
        });
    }

    public static <T> CompletableFuture<T> completeExceptionally(Throwable throwable) {
        CompletableFuture completableFuture = new CompletableFuture();
        completableFuture.completeExceptionally(throwable);
        return completableFuture;
    }

    public static void throwUnchecked(Throwable throwable) {
        throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }

    private static void method_18347(Thread thread, Throwable throwable) {
        Util.throwOrPause(throwable);
        if (throwable instanceof CompletionException) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof CrashException) {
            Bootstrap.println(((CrashException)throwable).getReport().asString());
            System.exit(-1);
        }
        LOGGER.error(String.format("Caught exception in thread %s", thread), throwable);
    }

    @Nullable
    public static Type<?> getChoiceType(DSL.TypeReference typeReference, String id) {
        if (!SharedConstants.useChoiceTypeRegistrations) {
            return null;
        }
        return Util.getChoiceTypeInternal(typeReference, id);
    }

    @Nullable
    private static Type<?> getChoiceTypeInternal(DSL.TypeReference typeReference, String id) {
        block2: {
            Type type = null;
            try {
                type = Schemas.getFixer().getSchema(DataFixUtils.makeKey((int)SharedConstants.getGameVersion().getWorldVersion())).getChoiceType(typeReference, id);
            }
            catch (IllegalArgumentException _snowman2) {
                LOGGER.error("No data fixer registered for {}", (Object)id);
                if (!SharedConstants.isDevelopment) break block2;
                throw _snowman2;
            }
        }
        return type;
    }

    public static OperatingSystem getOperatingSystem() {
        String string = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (string.contains("win")) {
            return OperatingSystem.WINDOWS;
        }
        if (string.contains("mac")) {
            return OperatingSystem.OSX;
        }
        if (string.contains("solaris")) {
            return OperatingSystem.SOLARIS;
        }
        if (string.contains("sunos")) {
            return OperatingSystem.SOLARIS;
        }
        if (string.contains("linux")) {
            return OperatingSystem.LINUX;
        }
        if (string.contains("unix")) {
            return OperatingSystem.LINUX;
        }
        return OperatingSystem.UNKNOWN;
    }

    public static Stream<String> getJVMFlags() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getInputArguments().stream().filter(string -> string.startsWith("-X"));
    }

    public static <T> T getLast(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T next(Iterable<T> iterable, @Nullable T t) {
        Iterator<T> iterator = iterable.iterator();
        T _snowman2 = iterator.next();
        if (t != null) {
            T t2 = _snowman2;
            while (true) {
                if (t2 == t) {
                    if (!iterator.hasNext()) break;
                    return iterator.next();
                }
                if (!iterator.hasNext()) continue;
                t2 = iterator.next();
            }
        }
        return _snowman2;
    }

    public static <T> T previous(Iterable<T> iterable, @Nullable T t) {
        Iterator<T> iterator = iterable.iterator();
        T _snowman2 = null;
        while (iterator.hasNext()) {
            T t2 = iterator.next();
            if (t2 == t) {
                if (_snowman2 != null) break;
                _snowman2 = (T)(iterator.hasNext() ? Iterators.getLast(iterator) : t);
                break;
            }
            _snowman2 = t2;
        }
        return _snowman2;
    }

    public static <T> T make(Supplier<T> factory) {
        return factory.get();
    }

    public static <T> T make(T object, Consumer<T> initializer) {
        initializer.accept(object);
        return object;
    }

    public static <K> Hash.Strategy<K> identityHashStrategy() {
        return IdentityHashStrategy.INSTANCE;
    }

    public static <V> CompletableFuture<List<V>> combine(List<? extends CompletableFuture<? extends V>> futures) {
        ArrayList arrayList = Lists.newArrayListWithCapacity((int)futures.size());
        CompletableFuture[] _snowman2 = new CompletableFuture[futures.size()];
        CompletableFuture _snowman3 = new CompletableFuture();
        futures.forEach(completableFuture2 -> {
            int n = arrayList.size();
            arrayList.add(null);
            completableFutureArray[n] = completableFuture2.whenComplete((object, throwable) -> {
                if (throwable != null) {
                    _snowman3.completeExceptionally((Throwable)throwable);
                } else {
                    arrayList.set(n, object);
                }
            });
        });
        return CompletableFuture.allOf(_snowman2).applyToEither((CompletionStage)_snowman3, void_ -> arrayList);
    }

    public static <T> Stream<T> stream(Optional<? extends T> optional) {
        return (Stream)DataFixUtils.orElseGet(optional.map(Stream::of), Stream::empty);
    }

    public static <T> Optional<T> ifPresentOrElse(Optional<T> optional, Consumer<T> consumer, Runnable runnable) {
        if (optional.isPresent()) {
            consumer.accept(optional.get());
        } else {
            runnable.run();
        }
        return optional;
    }

    public static Runnable debugRunnable(Runnable runnable, Supplier<String> messageSupplier) {
        return runnable;
    }

    public static <T extends Throwable> T throwOrPause(T t) {
        if (SharedConstants.isDevelopment) {
            LOGGER.error("Trying to throw a fatal exception, pausing in IDE", t);
            try {
                while (true) {
                    Thread.sleep(1000L);
                    LOGGER.error("paused");
                }
            }
            catch (InterruptedException interruptedException) {
                return t;
            }
        }
        return t;
    }

    public static String getInnermostMessage(Throwable t) {
        if (t.getCause() != null) {
            return Util.getInnermostMessage(t.getCause());
        }
        if (t.getMessage() != null) {
            return t.getMessage();
        }
        return t.toString();
    }

    public static <T> T getRandom(T[] array, Random random) {
        return array[random.nextInt(array.length)];
    }

    public static int getRandom(int[] array, Random random) {
        return array[random.nextInt(array.length)];
    }

    private static BooleanSupplier renameTask(Path src, Path dest) {
        return new BooleanSupplier(src, dest){
            final /* synthetic */ Path field_26348;
            final /* synthetic */ Path field_26349;
            {
                this.field_26348 = path;
                this.field_26349 = path2;
            }

            public boolean getAsBoolean() {
                try {
                    Files.move(this.field_26348, this.field_26349, new CopyOption[0]);
                    return true;
                }
                catch (IOException iOException) {
                    Util.getLogger().error("Failed to rename", (Throwable)iOException);
                    return false;
                }
            }

            public String toString() {
                return "rename " + this.field_26348 + " to " + this.field_26349;
            }
        };
    }

    private static BooleanSupplier deleteTask(Path path) {
        return new BooleanSupplier(path){
            final /* synthetic */ Path field_26350;
            {
                this.field_26350 = path;
            }

            public boolean getAsBoolean() {
                try {
                    Files.deleteIfExists(this.field_26350);
                    return true;
                }
                catch (IOException iOException) {
                    Util.getLogger().warn("Failed to delete", (Throwable)iOException);
                    return false;
                }
            }

            public String toString() {
                return "delete old " + this.field_26350;
            }
        };
    }

    private static BooleanSupplier deletionVerifyTask(Path path) {
        return new BooleanSupplier(path){
            final /* synthetic */ Path field_26351;
            {
                this.field_26351 = path;
            }

            public boolean getAsBoolean() {
                return !Files.exists(this.field_26351, new LinkOption[0]);
            }

            public String toString() {
                return "verify that " + this.field_26351 + " is deleted";
            }
        };
    }

    private static BooleanSupplier existenceCheckTask(Path path) {
        return new BooleanSupplier(path){
            final /* synthetic */ Path field_26352;
            {
                this.field_26352 = path;
            }

            public boolean getAsBoolean() {
                return Files.isRegularFile(this.field_26352, new LinkOption[0]);
            }

            public String toString() {
                return "verify that " + this.field_26352 + " is present";
            }
        };
    }

    private static boolean attemptTasks(BooleanSupplier ... booleanSupplierArray) {
        for (BooleanSupplier booleanSupplier : booleanSupplierArray) {
            if (booleanSupplier.getAsBoolean()) continue;
            LOGGER.warn("Failed to execute {}", (Object)booleanSupplier);
            return false;
        }
        return true;
    }

    private static boolean attemptTasks(int retries, String taskName, BooleanSupplier ... tasks) {
        for (int i = 0; i < retries; ++i) {
            if (Util.attemptTasks(tasks)) {
                return true;
            }
            LOGGER.error("Failed to {}, retrying {}/{}", (Object)taskName, (Object)i, (Object)retries);
        }
        LOGGER.error("Failed to {}, aborting, progress might be lost", (Object)taskName);
        return false;
    }

    public static void backupAndReplace(File current, File newFile, File backup) {
        Util.backupAndReplace(current.toPath(), newFile.toPath(), backup.toPath());
    }

    public static void backupAndReplace(Path current, Path newPath, Path backup) {
        int n = 10;
        if (Files.exists(current, new LinkOption[0]) && !Util.attemptTasks(10, "create backup " + backup, Util.deleteTask(backup), Util.renameTask(current, backup), Util.existenceCheckTask(backup))) {
            return;
        }
        if (!Util.attemptTasks(10, "remove old " + current, Util.deleteTask(current), Util.deletionVerifyTask(current))) {
            return;
        }
        if (!Util.attemptTasks(10, "replace " + current + " with " + newPath, Util.renameTask(newPath, current), Util.existenceCheckTask(current))) {
            Util.attemptTasks(10, "restore " + current + " from " + backup, Util.renameTask(backup, current), Util.existenceCheckTask(current));
        }
    }

    public static int moveCursor(String string, int cursor, int delta) {
        int n = string.length();
        if (delta >= 0) {
            for (_snowman = 0; cursor < n && _snowman < delta; ++_snowman) {
                if (!Character.isHighSurrogate(string.charAt(cursor++)) || cursor >= n || !Character.isLowSurrogate(string.charAt(cursor))) continue;
                ++cursor;
            }
        } else {
            for (_snowman = delta; cursor > 0 && _snowman < 0; ++_snowman) {
                if (!Character.isLowSurrogate(string.charAt(--cursor)) || cursor <= 0 || !Character.isHighSurrogate(string.charAt(cursor - 1))) continue;
                --cursor;
            }
        }
        return cursor;
    }

    public static Consumer<String> method_29188(String string, Consumer<String> consumer) {
        return string2 -> consumer.accept(string + string2);
    }

    public static DataResult<int[]> toIntArray(IntStream intStream, int length) {
        int[] nArray = intStream.limit(length + 1).toArray();
        if (nArray.length != length) {
            String string = "Input is not a list of " + length + " ints";
            if (nArray.length >= length) {
                return DataResult.error((String)string, (Object)Arrays.copyOf(nArray, length));
            }
            return DataResult.error((String)string);
        }
        return DataResult.success((Object)nArray);
    }

    public static void startTimerHack() {
        Thread thread = new Thread("Timer hack thread"){

            public void run() {
                try {
                    while (true) {
                        Thread.sleep(Integer.MAX_VALUE);
                    }
                }
                catch (InterruptedException interruptedException) {
                    Util.getLogger().warn("Timer hack thread interrupted, that really should not happen");
                    return;
                }
            }
        };
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
        thread.start();
    }

    public static void relativeCopy(Path src, Path dest, Path toCopy) throws IOException {
        Path path = src.relativize(toCopy);
        _snowman = dest.resolve(path);
        Files.copy(toCopy, _snowman, new CopyOption[0]);
    }

    public static String replaceInvalidChars(String string, CharPredicate predicate) {
        return string.toLowerCase(Locale.ROOT).chars().mapToObj(n -> predicate.test((char)n) ? Character.toString((char)n) : "_").collect(Collectors.joining());
    }

    static enum IdentityHashStrategy implements Hash.Strategy<Object>
    {
        INSTANCE;


        public int hashCode(Object object) {
            return System.identityHashCode(object);
        }

        public boolean equals(Object object, Object object2) {
            return object == object2;
        }
    }

    public static enum OperatingSystem {
        LINUX,
        SOLARIS,
        WINDOWS{

            protected String[] getURLOpenCommand(URL url) {
                return new String[]{"rundll32", "url.dll,FileProtocolHandler", url.toString()};
            }
        }
        ,
        OSX{

            protected String[] getURLOpenCommand(URL url) {
                return new String[]{"open", url.toString()};
            }
        }
        ,
        UNKNOWN;


        public void open(URL url) {
            try {
                Process process = AccessController.doPrivileged(() -> Runtime.getRuntime().exec(this.getURLOpenCommand(url)));
                for (String string : IOUtils.readLines((InputStream)process.getErrorStream())) {
                    LOGGER.error(string);
                }
                process.getInputStream().close();
                process.getErrorStream().close();
                process.getOutputStream().close();
            }
            catch (IOException | PrivilegedActionException exception) {
                LOGGER.error("Couldn't open url '{}'", (Object)url, (Object)exception);
            }
        }

        public void open(URI uRI) {
            try {
                this.open(uRI.toURL());
            }
            catch (MalformedURLException malformedURLException) {
                LOGGER.error("Couldn't open uri '{}'", (Object)uRI, (Object)malformedURLException);
            }
        }

        public void open(File file) {
            try {
                this.open(file.toURI().toURL());
            }
            catch (MalformedURLException malformedURLException) {
                LOGGER.error("Couldn't open file '{}'", (Object)file, (Object)malformedURLException);
            }
        }

        protected String[] getURLOpenCommand(URL url) {
            String string = url.toString();
            if ("file".equals(url.getProtocol())) {
                string = string.replace("file:", "file://");
            }
            return new String[]{"xdg-open", string};
        }

        public void open(String string) {
            try {
                this.open(new URI(string).toURL());
            }
            catch (IllegalArgumentException | MalformedURLException | URISyntaxException exception) {
                LOGGER.error("Couldn't open uri '{}'", (Object)string, (Object)exception);
            }
        }
    }
}

