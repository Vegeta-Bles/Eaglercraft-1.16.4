package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
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
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
   private static final AtomicInteger NEXT_WORKER_ID = new AtomicInteger(1);
   private static final ExecutorService BOOTSTRAP_EXECUTOR = createWorker("Bootstrap");
   private static final ExecutorService MAIN_WORKER_EXECUTOR = createWorker("Main");
   private static final ExecutorService IO_WORKER_EXECUTOR = createIoWorker();
   public static LongSupplier nanoTimeSupplier = System::nanoTime;
   public static final UUID NIL_UUID = new UUID(0L, 0L);
   private static final Logger LOGGER = LogManager.getLogger();

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> toMap() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T extends Comparable<T>> String getValueAsString(Property<T> _snowman, Object _snowman) {
      return _snowman.name((T)_snowman);
   }

   public static String createTranslationKey(String type, @Nullable Identifier id) {
      return id == null ? type + ".unregistered_sadface" : type + '.' + id.getNamespace() + '.' + id.getPath().replace('/', '.');
   }

   public static long getMeasuringTimeMs() {
      return getMeasuringTimeNano() / 1000000L;
   }

   public static long getMeasuringTimeNano() {
      return nanoTimeSupplier.getAsLong();
   }

   public static long getEpochTimeMs() {
      return Instant.now().toEpochMilli();
   }

   private static ExecutorService createWorker(String name) {
      int _snowman = MathHelper.clamp(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
      ExecutorService _snowmanx;
      if (_snowman <= 0) {
         _snowmanx = MoreExecutors.newDirectExecutorService();
      } else {
         _snowmanx = new ForkJoinPool(_snowman, _snowmanxx -> {
            ForkJoinWorkerThread _snowmanxxx = new ForkJoinWorkerThread(_snowmanxx) {
               @Override
               protected void onTermination(Throwable _snowman) {
                  if (_snowman != null) {
                     Util.LOGGER.warn("{} died", this.getName(), _snowman);
                  } else {
                     Util.LOGGER.debug("{} shutdown", this.getName());
                  }

                  super.onTermination(_snowman);
               }
            };
            _snowmanxxx.setName("Worker-" + name + "-" + NEXT_WORKER_ID.getAndIncrement());
            return _snowmanxxx;
         }, Util::method_18347, true);
      }

      return _snowmanx;
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
      attemptShutdown(MAIN_WORKER_EXECUTOR);
      attemptShutdown(IO_WORKER_EXECUTOR);
   }

   private static void attemptShutdown(ExecutorService service) {
      service.shutdown();

      boolean _snowman;
      try {
         _snowman = service.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException var3) {
         _snowman = false;
      }

      if (!_snowman) {
         service.shutdownNow();
      }
   }

   private static ExecutorService createIoWorker() {
      return Executors.newCachedThreadPool(_snowman -> {
         Thread _snowmanx = new Thread(_snowman);
         _snowmanx.setName("IO-Worker-" + NEXT_WORKER_ID.getAndIncrement());
         _snowmanx.setUncaughtExceptionHandler(Util::method_18347);
         return _snowmanx;
      });
   }

   public static <T> CompletableFuture<T> completeExceptionally(Throwable _snowman) {
      CompletableFuture<T> _snowmanx = new CompletableFuture<>();
      _snowmanx.completeExceptionally(_snowman);
      return _snowmanx;
   }

   public static void throwUnchecked(Throwable _snowman) {
      throw _snowman instanceof RuntimeException ? (RuntimeException)_snowman : new RuntimeException(_snowman);
   }

   private static void method_18347(Thread _snowman, Throwable _snowman) {
      throwOrPause(_snowman);
      if (_snowman instanceof CompletionException) {
         _snowman = _snowman.getCause();
      }

      if (_snowman instanceof CrashException) {
         Bootstrap.println(((CrashException)_snowman).getReport().asString());
         System.exit(-1);
      }

      LOGGER.error(String.format("Caught exception in thread %s", _snowman), _snowman);
   }

   @Nullable
   public static Type<?> getChoiceType(TypeReference typeReference, String id) {
      return !SharedConstants.useChoiceTypeRegistrations ? null : getChoiceTypeInternal(typeReference, id);
   }

   @Nullable
   private static Type<?> getChoiceTypeInternal(TypeReference typeReference, String id) {
      Type<?> _snowman = null;

      try {
         _snowman = Schemas.getFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion())).getChoiceType(typeReference, id);
      } catch (IllegalArgumentException var4) {
         LOGGER.error("No data fixer registered for {}", id);
         if (SharedConstants.isDevelopment) {
            throw var4;
         }
      }

      return _snowman;
   }

   public static Util.OperatingSystem getOperatingSystem() {
      String _snowman = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (_snowman.contains("win")) {
         return Util.OperatingSystem.WINDOWS;
      } else if (_snowman.contains("mac")) {
         return Util.OperatingSystem.OSX;
      } else if (_snowman.contains("solaris")) {
         return Util.OperatingSystem.SOLARIS;
      } else if (_snowman.contains("sunos")) {
         return Util.OperatingSystem.SOLARIS;
      } else if (_snowman.contains("linux")) {
         return Util.OperatingSystem.LINUX;
      } else {
         return _snowman.contains("unix") ? Util.OperatingSystem.LINUX : Util.OperatingSystem.UNKNOWN;
      }
   }

   public static Stream<String> getJVMFlags() {
      RuntimeMXBean _snowman = ManagementFactory.getRuntimeMXBean();
      return _snowman.getInputArguments().stream().filter(_snowmanx -> _snowmanx.startsWith("-X"));
   }

   public static <T> T getLast(List<T> list) {
      return list.get(list.size() - 1);
   }

   public static <T> T next(Iterable<T> _snowman, @Nullable T _snowman) {
      Iterator<T> _snowmanxx = _snowman.iterator();
      T _snowmanxxx = _snowmanxx.next();
      if (_snowman != null) {
         T _snowmanxxxx = _snowmanxxx;

         while (_snowmanxxxx != _snowman) {
            if (_snowmanxx.hasNext()) {
               _snowmanxxxx = _snowmanxx.next();
            }
         }

         if (_snowmanxx.hasNext()) {
            return _snowmanxx.next();
         }
      }

      return _snowmanxxx;
   }

   public static <T> T previous(Iterable<T> _snowman, @Nullable T _snowman) {
      Iterator<T> _snowmanxx = _snowman.iterator();
      T _snowmanxxx = null;

      while (_snowmanxx.hasNext()) {
         T _snowmanxxxx = _snowmanxx.next();
         if (_snowmanxxxx == _snowman) {
            if (_snowmanxxx == null) {
               _snowmanxxx = (T)(_snowmanxx.hasNext() ? Iterators.getLast(_snowmanxx) : _snowman);
            }
            break;
         }

         _snowmanxxx = _snowmanxxxx;
      }

      return _snowmanxxx;
   }

   public static <T> T make(Supplier<T> factory) {
      return factory.get();
   }

   public static <T> T make(T object, Consumer<T> initializer) {
      initializer.accept(object);
      return object;
   }

   public static <K> Strategy<K> identityHashStrategy() {
      return Util.IdentityHashStrategy.INSTANCE;
   }

   public static <V> CompletableFuture<List<V>> combine(List<? extends CompletableFuture<? extends V>> futures) {
      List<V> _snowman = Lists.newArrayListWithCapacity(futures.size());
      CompletableFuture<?>[] _snowmanx = new CompletableFuture[futures.size()];
      CompletableFuture<Void> _snowmanxx = new CompletableFuture<>();
      futures.forEach(_snowmanxxx -> {
         int _snowmanxxx = _snowman.size();
         _snowman.add(null);
         _snowman[_snowmanxxx] = _snowmanxxx.whenComplete((_snowmanxxxxxx, _snowmanxxxxx) -> {
            if (_snowmanxxxxx != null) {
               _snowman.completeExceptionally(_snowmanxxxxx);
            } else {
               _snowman.set(_snowman, (V)_snowmanxxxxxx);
            }
         });
      });
      return CompletableFuture.allOf(_snowmanx).applyToEither(_snowmanxx, _snowmanxxx -> _snowman);
   }

   public static <T> Stream<T> stream(Optional<? extends T> _snowman) {
      return (Stream<T>)DataFixUtils.orElseGet(_snowman.map(Stream::of), Stream::empty);
   }

   public static <T> Optional<T> ifPresentOrElse(Optional<T> _snowman, Consumer<T> _snowman, Runnable _snowman) {
      if (_snowman.isPresent()) {
         _snowman.accept(_snowman.get());
      } else {
         _snowman.run();
      }

      return _snowman;
   }

   public static Runnable debugRunnable(Runnable runnable, Supplier<String> messageSupplier) {
      return runnable;
   }

   public static <T extends Throwable> T throwOrPause(T t) {
      if (SharedConstants.isDevelopment) {
         LOGGER.error("Trying to throw a fatal exception, pausing in IDE", t);

         while (true) {
            try {
               Thread.sleep(1000L);
               LOGGER.error("paused");
            } catch (InterruptedException var2) {
               return t;
            }
         }
      } else {
         return t;
      }
   }

   public static String getInnermostMessage(Throwable t) {
      if (t.getCause() != null) {
         return getInnermostMessage(t.getCause());
      } else {
         return t.getMessage() != null ? t.getMessage() : t.toString();
      }
   }

   public static <T> T getRandom(T[] array, Random random) {
      return array[random.nextInt(array.length)];
   }

   public static int getRandom(int[] array, Random random) {
      return array[random.nextInt(array.length)];
   }

   private static BooleanSupplier renameTask(Path src, Path dest) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            try {
               Files.move(src, dest);
               return true;
            } catch (IOException var2) {
               Util.LOGGER.error("Failed to rename", var2);
               return false;
            }
         }

         @Override
         public String toString() {
            return "rename " + src + " to " + dest;
         }
      };
   }

   private static BooleanSupplier deleteTask(Path _snowman) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            try {
               Files.deleteIfExists(_snowman);
               return true;
            } catch (IOException var2) {
               Util.LOGGER.warn("Failed to delete", var2);
               return false;
            }
         }

         @Override
         public String toString() {
            return "delete old " + _snowman;
         }
      };
   }

   private static BooleanSupplier deletionVerifyTask(Path path) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return !Files.exists(path);
         }

         @Override
         public String toString() {
            return "verify that " + path + " is deleted";
         }
      };
   }

   private static BooleanSupplier existenceCheckTask(Path path) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return Files.isRegularFile(path);
         }

         @Override
         public String toString() {
            return "verify that " + path + " is present";
         }
      };
   }

   private static boolean attemptTasks(BooleanSupplier... _snowman) {
      for (BooleanSupplier _snowmanx : _snowman) {
         if (!_snowmanx.getAsBoolean()) {
            LOGGER.warn("Failed to execute {}", _snowmanx);
            return false;
         }
      }

      return true;
   }

   private static boolean attemptTasks(int retries, String taskName, BooleanSupplier... tasks) {
      for (int _snowman = 0; _snowman < retries; _snowman++) {
         if (attemptTasks(tasks)) {
            return true;
         }

         LOGGER.error("Failed to {}, retrying {}/{}", taskName, _snowman, retries);
      }

      LOGGER.error("Failed to {}, aborting, progress might be lost", taskName);
      return false;
   }

   public static void backupAndReplace(File current, File newFile, File backup) {
      backupAndReplace(current.toPath(), newFile.toPath(), backup.toPath());
   }

   public static void backupAndReplace(Path current, Path newPath, Path backup) {
      int _snowman = 10;
      if (!Files.exists(current) || attemptTasks(10, "create backup " + backup, deleteTask(backup), renameTask(current, backup), existenceCheckTask(backup))) {
         if (attemptTasks(10, "remove old " + current, deleteTask(current), deletionVerifyTask(current))) {
            if (!attemptTasks(10, "replace " + current + " with " + newPath, renameTask(newPath, current), existenceCheckTask(current))) {
               attemptTasks(10, "restore " + current + " from " + backup, renameTask(backup, current), existenceCheckTask(current));
            }
         }
      }
   }

   public static int moveCursor(String string, int cursor, int delta) {
      int _snowman = string.length();
      if (delta >= 0) {
         for (int _snowmanx = 0; cursor < _snowman && _snowmanx < delta; _snowmanx++) {
            if (Character.isHighSurrogate(string.charAt(cursor++)) && cursor < _snowman && Character.isLowSurrogate(string.charAt(cursor))) {
               cursor++;
            }
         }
      } else {
         for (int _snowmanxx = delta; cursor > 0 && _snowmanxx < 0; _snowmanxx++) {
            cursor--;
            if (Character.isLowSurrogate(string.charAt(cursor)) && cursor > 0 && Character.isHighSurrogate(string.charAt(cursor - 1))) {
               cursor--;
            }
         }
      }

      return cursor;
   }

   public static Consumer<String> method_29188(String _snowman, Consumer<String> _snowman) {
      return _snowmanxxx -> _snowman.accept(_snowman + _snowmanxxx);
   }

   public static DataResult<int[]> toIntArray(IntStream intStream, int length) {
      int[] _snowman = intStream.limit((long)(length + 1)).toArray();
      if (_snowman.length != length) {
         String _snowmanx = "Input is not a list of " + length + " ints";
         return _snowman.length >= length ? DataResult.error(_snowmanx, Arrays.copyOf(_snowman, length)) : DataResult.error(_snowmanx);
      } else {
         return DataResult.success(_snowman);
      }
   }

   public static void startTimerHack() {
      Thread _snowman = new Thread("Timer hack thread") {
         @Override
         public void run() {
            while (true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  Util.LOGGER.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      _snowman.setDaemon(true);
      _snowman.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
      _snowman.start();
   }

   public static void relativeCopy(Path src, Path dest, Path toCopy) throws IOException {
      Path _snowman = src.relativize(toCopy);
      Path _snowmanx = dest.resolve(_snowman);
      Files.copy(toCopy, _snowmanx);
   }

   public static String replaceInvalidChars(String _snowman, CharPredicate predicate) {
      return _snowman.toLowerCase(Locale.ROOT).chars().mapToObj(_snowmanxx -> predicate.test((char)_snowmanxx) ? Character.toString((char)_snowmanxx) : "_").collect(Collectors.joining());
   }

   static enum IdentityHashStrategy implements Strategy<Object> {
      INSTANCE;

      private IdentityHashStrategy() {
      }

      public int hashCode(Object _snowman) {
         return System.identityHashCode(_snowman);
      }

      public boolean equals(Object _snowman, Object _snowman) {
         return _snowman == _snowman;
      }
   }

   public static enum OperatingSystem {
      LINUX,
      SOLARIS,
      WINDOWS {
         @Override
         protected String[] getURLOpenCommand(URL url) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", url.toString()};
         }
      },
      OSX {
         @Override
         protected String[] getURLOpenCommand(URL url) {
            return new String[]{"open", url.toString()};
         }
      },
      UNKNOWN;

      private OperatingSystem() {
      }

      public void open(URL url) {
         try {
            Process _snowman = AccessController.doPrivileged((PrivilegedExceptionAction<Process>)(() -> Runtime.getRuntime().exec(this.getURLOpenCommand(url))));

            for (String _snowmanx : IOUtils.readLines(_snowman.getErrorStream())) {
               Util.LOGGER.error(_snowmanx);
            }

            _snowman.getInputStream().close();
            _snowman.getErrorStream().close();
            _snowman.getOutputStream().close();
         } catch (IOException | PrivilegedActionException var5) {
            Util.LOGGER.error("Couldn't open url '{}'", url, var5);
         }
      }

      public void open(URI _snowman) {
         try {
            this.open(_snowman.toURL());
         } catch (MalformedURLException var3) {
            Util.LOGGER.error("Couldn't open uri '{}'", _snowman, var3);
         }
      }

      public void open(File _snowman) {
         try {
            this.open(_snowman.toURI().toURL());
         } catch (MalformedURLException var3) {
            Util.LOGGER.error("Couldn't open file '{}'", _snowman, var3);
         }
      }

      protected String[] getURLOpenCommand(URL url) {
         String _snowman = url.toString();
         if ("file".equals(url.getProtocol())) {
            _snowman = _snowman.replace("file:", "file://");
         }

         return new String[]{"xdg-open", _snowman};
      }

      public void open(String _snowman) {
         try {
            this.open(new URI(_snowman).toURL());
         } catch (MalformedURLException | IllegalArgumentException | URISyntaxException var3) {
            Util.LOGGER.error("Couldn't open uri '{}'", _snowman, var3);
         }
      }
   }
}
