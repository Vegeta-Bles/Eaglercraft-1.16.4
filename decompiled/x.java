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
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class x {
   private static final AtomicInteger c = new AtomicInteger(1);
   private static final ExecutorService d = a("Bootstrap");
   private static final ExecutorService e = a("Main");
   private static final ExecutorService f = n();
   public static LongSupplier a = System::nanoTime;
   public static final UUID b = new UUID(0L, 0L);
   private static final Logger g = LogManager.getLogger();

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> a() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T extends Comparable<T>> String a(cfj<T> var0, Object var1) {
      return _snowman.a((T)_snowman);
   }

   public static String a(String var0, @Nullable vk var1) {
      return _snowman == null ? _snowman + ".unregistered_sadface" : _snowman + '.' + _snowman.b() + '.' + _snowman.a().replace('/', '.');
   }

   public static long b() {
      return c() / 1000000L;
   }

   public static long c() {
      return a.getAsLong();
   }

   public static long d() {
      return Instant.now().toEpochMilli();
   }

   private static ExecutorService a(String var0) {
      int _snowman = afm.a(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
      ExecutorService _snowmanx;
      if (_snowman <= 0) {
         _snowmanx = MoreExecutors.newDirectExecutorService();
      } else {
         _snowmanx = new ForkJoinPool(_snowman, var1x -> {
            ForkJoinWorkerThread _snowmanxx = new ForkJoinWorkerThread(var1x) {
               @Override
               protected void onTermination(Throwable var1) {
                  if (_snowman != null) {
                     x.g.warn("{} died", this.getName(), _snowman);
                  } else {
                     x.g.debug("{} shutdown", this.getName());
                  }

                  super.onTermination(_snowman);
               }
            };
            _snowmanxx.setName("Worker-" + _snowman + "-" + c.getAndIncrement());
            return _snowmanxx;
         }, x::a, true);
      }

      return _snowmanx;
   }

   public static Executor e() {
      return d;
   }

   public static Executor f() {
      return e;
   }

   public static Executor g() {
      return f;
   }

   public static void h() {
      a(e);
      a(f);
   }

   private static void a(ExecutorService var0) {
      _snowman.shutdown();

      boolean _snowman;
      try {
         _snowman = _snowman.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException var3) {
         _snowman = false;
      }

      if (!_snowman) {
         _snowman.shutdownNow();
      }
   }

   private static ExecutorService n() {
      return Executors.newCachedThreadPool(var0 -> {
         Thread _snowman = new Thread(var0);
         _snowman.setName("IO-Worker-" + c.getAndIncrement());
         _snowman.setUncaughtExceptionHandler(x::a);
         return _snowman;
      });
   }

   public static <T> CompletableFuture<T> a(Throwable var0) {
      CompletableFuture<T> _snowman = new CompletableFuture<>();
      _snowman.completeExceptionally(_snowman);
      return _snowman;
   }

   public static void b(Throwable var0) {
      throw _snowman instanceof RuntimeException ? (RuntimeException)_snowman : new RuntimeException(_snowman);
   }

   private static void a(Thread var0, Throwable var1) {
      c(_snowman);
      if (_snowman instanceof CompletionException) {
         _snowman = _snowman.getCause();
      }

      if (_snowman instanceof u) {
         vm.a(((u)_snowman).a().e());
         System.exit(-1);
      }

      g.error(String.format("Caught exception in thread %s", _snowman), _snowman);
   }

   @Nullable
   public static Type<?> a(TypeReference var0, String var1) {
      return !w.c ? null : b(_snowman, _snowman);
   }

   @Nullable
   private static Type<?> b(TypeReference var0, String var1) {
      Type<?> _snowman = null;

      try {
         _snowman = agb.a().getSchema(DataFixUtils.makeKey(w.a().getWorldVersion())).getChoiceType(_snowman, _snowman);
      } catch (IllegalArgumentException var4) {
         g.error("No data fixer registered for {}", _snowman);
         if (w.d) {
            throw var4;
         }
      }

      return _snowman;
   }

   public static x.b i() {
      String _snowman = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (_snowman.contains("win")) {
         return x.b.c;
      } else if (_snowman.contains("mac")) {
         return x.b.d;
      } else if (_snowman.contains("solaris")) {
         return x.b.b;
      } else if (_snowman.contains("sunos")) {
         return x.b.b;
      } else if (_snowman.contains("linux")) {
         return x.b.a;
      } else {
         return _snowman.contains("unix") ? x.b.a : x.b.e;
      }
   }

   public static Stream<String> j() {
      RuntimeMXBean _snowman = ManagementFactory.getRuntimeMXBean();
      return _snowman.getInputArguments().stream().filter(var0x -> var0x.startsWith("-X"));
   }

   public static <T> T a(List<T> var0) {
      return _snowman.get(_snowman.size() - 1);
   }

   public static <T> T a(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> _snowman = _snowman.iterator();
      T _snowmanx = _snowman.next();
      if (_snowman != null) {
         T _snowmanxx = _snowmanx;

         while (_snowmanxx != _snowman) {
            if (_snowman.hasNext()) {
               _snowmanxx = _snowman.next();
            }
         }

         if (_snowman.hasNext()) {
            return _snowman.next();
         }
      }

      return _snowmanx;
   }

   public static <T> T b(Iterable<T> var0, @Nullable T var1) {
      Iterator<T> _snowman = _snowman.iterator();
      T _snowmanx = null;

      while (_snowman.hasNext()) {
         T _snowmanxx = _snowman.next();
         if (_snowmanxx == _snowman) {
            if (_snowmanx == null) {
               _snowmanx = (T)(_snowman.hasNext() ? Iterators.getLast(_snowman) : _snowman);
            }
            break;
         }

         _snowmanx = _snowmanxx;
      }

      return _snowmanx;
   }

   public static <T> T a(Supplier<T> var0) {
      return _snowman.get();
   }

   public static <T> T a(T var0, Consumer<T> var1) {
      _snowman.accept(_snowman);
      return _snowman;
   }

   public static <K> Strategy<K> k() {
      return x.a.a;
   }

   public static <V> CompletableFuture<List<V>> b(List<? extends CompletableFuture<? extends V>> var0) {
      List<V> _snowman = Lists.newArrayListWithCapacity(_snowman.size());
      CompletableFuture<?>[] _snowmanx = new CompletableFuture[_snowman.size()];
      CompletableFuture<Void> _snowmanxx = new CompletableFuture<>();
      _snowman.forEach(var3x -> {
         int _snowmanxxx = _snowman.size();
         _snowman.add(null);
         _snowman[_snowmanxxx] = var3x.whenComplete((var3xx, var4x) -> {
            if (var4x != null) {
               _snowman.completeExceptionally(var4x);
            } else {
               _snowman.set(_snowman, (V)var3xx);
            }
         });
      });
      return CompletableFuture.allOf(_snowmanx).applyToEither(_snowmanxx, var1x -> _snowman);
   }

   public static <T> Stream<T> a(Optional<? extends T> var0) {
      return (Stream<T>)DataFixUtils.orElseGet(_snowman.map(Stream::of), Stream::empty);
   }

   public static <T> Optional<T> a(Optional<T> var0, Consumer<T> var1, Runnable var2) {
      if (_snowman.isPresent()) {
         _snowman.accept(_snowman.get());
      } else {
         _snowman.run();
      }

      return _snowman;
   }

   public static Runnable a(Runnable var0, Supplier<String> var1) {
      return _snowman;
   }

   public static <T extends Throwable> T c(T var0) {
      if (w.d) {
         g.error("Trying to throw a fatal exception, pausing in IDE", _snowman);

         while (true) {
            try {
               Thread.sleep(1000L);
               g.error("paused");
            } catch (InterruptedException var2) {
               return _snowman;
            }
         }
      } else {
         return _snowman;
      }
   }

   public static String d(Throwable var0) {
      if (_snowman.getCause() != null) {
         return d(_snowman.getCause());
      } else {
         return _snowman.getMessage() != null ? _snowman.getMessage() : _snowman.toString();
      }
   }

   public static <T> T a(T[] var0, Random var1) {
      return _snowman[_snowman.nextInt(_snowman.length)];
   }

   public static int a(int[] var0, Random var1) {
      return _snowman[_snowman.nextInt(_snowman.length)];
   }

   private static BooleanSupplier a(final Path var0, final Path var1) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            try {
               Files.move(_snowman, _snowman);
               return true;
            } catch (IOException var2) {
               x.g.error("Failed to rename", var2);
               return false;
            }
         }

         @Override
         public String toString() {
            return "rename " + _snowman + " to " + _snowman;
         }
      };
   }

   private static BooleanSupplier a(final Path var0) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            try {
               Files.deleteIfExists(_snowman);
               return true;
            } catch (IOException var2) {
               x.g.warn("Failed to delete", var2);
               return false;
            }
         }

         @Override
         public String toString() {
            return "delete old " + _snowman;
         }
      };
   }

   private static BooleanSupplier b(final Path var0) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return !Files.exists(_snowman);
         }

         @Override
         public String toString() {
            return "verify that " + _snowman + " is deleted";
         }
      };
   }

   private static BooleanSupplier c(final Path var0) {
      return new BooleanSupplier() {
         @Override
         public boolean getAsBoolean() {
            return Files.isRegularFile(_snowman);
         }

         @Override
         public String toString() {
            return "verify that " + _snowman + " is present";
         }
      };
   }

   private static boolean a(BooleanSupplier... var0) {
      for (BooleanSupplier _snowman : _snowman) {
         if (!_snowman.getAsBoolean()) {
            g.warn("Failed to execute {}", _snowman);
            return false;
         }
      }

      return true;
   }

   private static boolean a(int var0, String var1, BooleanSupplier... var2) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         if (a(_snowman)) {
            return true;
         }

         g.error("Failed to {}, retrying {}/{}", _snowman, _snowman, _snowman);
      }

      g.error("Failed to {}, aborting, progress might be lost", _snowman);
      return false;
   }

   public static void a(File var0, File var1, File var2) {
      a(_snowman.toPath(), _snowman.toPath(), _snowman.toPath());
   }

   public static void a(Path var0, Path var1, Path var2) {
      int _snowman = 10;
      if (!Files.exists(_snowman) || a(10, "create backup " + _snowman, a(_snowman), a(_snowman, _snowman), c(_snowman))) {
         if (a(10, "remove old " + _snowman, a(_snowman), b(_snowman))) {
            if (!a(10, "replace " + _snowman + " with " + _snowman, a(_snowman, _snowman), c(_snowman))) {
               a(10, "restore " + _snowman + " from " + _snowman, a(_snowman, _snowman), c(_snowman));
            }
         }
      }
   }

   public static int a(String var0, int var1, int var2) {
      int _snowman = _snowman.length();
      if (_snowman >= 0) {
         for (int _snowmanx = 0; _snowman < _snowman && _snowmanx < _snowman; _snowmanx++) {
            if (Character.isHighSurrogate(_snowman.charAt(_snowman++)) && _snowman < _snowman && Character.isLowSurrogate(_snowman.charAt(_snowman))) {
               _snowman++;
            }
         }
      } else {
         for (int _snowmanxx = _snowman; _snowman > 0 && _snowmanxx < 0; _snowmanxx++) {
            _snowman--;
            if (Character.isLowSurrogate(_snowman.charAt(_snowman)) && _snowman > 0 && Character.isHighSurrogate(_snowman.charAt(_snowman - 1))) {
               _snowman--;
            }
         }
      }

      return _snowman;
   }

   public static Consumer<String> a(String var0, Consumer<String> var1) {
      return var2 -> _snowman.accept(_snowman + var2);
   }

   public static DataResult<int[]> a(IntStream var0, int var1) {
      int[] _snowman = _snowman.limit((long)(_snowman + 1)).toArray();
      if (_snowman.length != _snowman) {
         String _snowmanx = "Input is not a list of " + _snowman + " ints";
         return _snowman.length >= _snowman ? DataResult.error(_snowmanx, Arrays.copyOf(_snowman, _snowman)) : DataResult.error(_snowmanx);
      } else {
         return DataResult.success(_snowman);
      }
   }

   public static void l() {
      Thread _snowman = new Thread("Timer hack thread") {
         @Override
         public void run() {
            while (true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  x.g.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      _snowman.setDaemon(true);
      _snowman.setUncaughtExceptionHandler(new o(g));
      _snowman.start();
   }

   public static void b(Path var0, Path var1, Path var2) throws IOException {
      Path _snowman = _snowman.relativize(_snowman);
      Path _snowmanx = _snowman.resolve(_snowman);
      Files.copy(_snowman, _snowmanx);
   }

   public static String a(String var0, j var1) {
      return _snowman.toLowerCase(Locale.ROOT).chars().mapToObj(var1x -> _snowman.test((char)var1x) ? Character.toString((char)var1x) : "_").collect(Collectors.joining());
   }

   static enum a implements Strategy<Object> {
      a;

      private a() {
      }

      public int hashCode(Object var1) {
         return System.identityHashCode(_snowman);
      }

      public boolean equals(Object var1, Object var2) {
         return _snowman == _snowman;
      }
   }

   public static enum b {
      a,
      b,
      c {
         @Override
         protected String[] b(URL var1) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", _snowman.toString()};
         }
      },
      d {
         @Override
         protected String[] b(URL var1) {
            return new String[]{"open", _snowman.toString()};
         }
      },
      e;

      private b() {
      }

      public void a(URL var1) {
         try {
            Process _snowman = AccessController.doPrivileged((PrivilegedExceptionAction<Process>)(() -> Runtime.getRuntime().exec(this.b(_snowman))));

            for (String _snowmanx : IOUtils.readLines(_snowman.getErrorStream())) {
               x.g.error(_snowmanx);
            }

            _snowman.getInputStream().close();
            _snowman.getErrorStream().close();
            _snowman.getOutputStream().close();
         } catch (IOException | PrivilegedActionException var5) {
            x.g.error("Couldn't open url '{}'", _snowman, var5);
         }
      }

      public void a(URI var1) {
         try {
            this.a(_snowman.toURL());
         } catch (MalformedURLException var3) {
            x.g.error("Couldn't open uri '{}'", _snowman, var3);
         }
      }

      public void a(File var1) {
         try {
            this.a(_snowman.toURI().toURL());
         } catch (MalformedURLException var3) {
            x.g.error("Couldn't open file '{}'", _snowman, var3);
         }
      }

      protected String[] b(URL var1) {
         String _snowman = _snowman.toString();
         if ("file".equals(_snowman.getProtocol())) {
            _snowman = _snowman.replace("file:", "file://");
         }

         return new String[]{"xdg-open", _snowman};
      }

      public void a(String var1) {
         try {
            this.a(new URI(_snowman).toURL());
         } catch (MalformedURLException | IllegalArgumentException | URISyntaxException var3) {
            x.g.error("Couldn't open uri '{}'", _snowman, var3);
         }
      }
   }
}
