import com.google.common.base.MoreObjects;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class zk<T extends zk<T>> {
   private static final Logger a = LogManager.getLogger();
   private final Properties b;

   public zk(Properties var1) {
      this.b = _snowman;
   }

   public static Properties a(Path var0) {
      Properties _snowman = new Properties();

      try (InputStream _snowmanx = Files.newInputStream(_snowman)) {
         _snowman.load(_snowmanx);
      } catch (IOException var15) {
         a.error("Failed to load properties from file: " + _snowman);
      }

      return _snowman;
   }

   public void b(Path var1) {
      try (OutputStream _snowman = Files.newOutputStream(_snowman)) {
         this.b.store(_snowman, "Minecraft server properties");
      } catch (IOException var15) {
         a.error("Failed to store properties to file: " + _snowman);
      }
   }

   private static <V extends Number> Function<String, V> a(Function<String, V> var0) {
      return var1 -> {
         try {
            return _snowman.apply(var1);
         } catch (NumberFormatException var3) {
            return null;
         }
      };
   }

   protected static <V> Function<String, V> a(IntFunction<V> var0, Function<String, V> var1) {
      return var2 -> {
         try {
            return _snowman.apply(Integer.parseInt(var2));
         } catch (NumberFormatException var4) {
            return _snowman.apply(var2);
         }
      };
   }

   @Nullable
   private String c(String var1) {
      return (String)this.b.get(_snowman);
   }

   @Nullable
   protected <V> V a(String var1, Function<String, V> var2) {
      String _snowman = this.c(_snowman);
      if (_snowman == null) {
         return null;
      } else {
         this.b.remove(_snowman);
         return _snowman.apply(_snowman);
      }
   }

   protected <V> V a(String var1, Function<String, V> var2, Function<V, String> var3, V var4) {
      String _snowman = this.c(_snowman);
      V _snowmanx = (V)MoreObjects.firstNonNull(_snowman != null ? _snowman.apply(_snowman) : null, _snowman);
      this.b.put(_snowman, _snowman.apply(_snowmanx));
      return _snowmanx;
   }

   protected <V> zk<T>.a<V> b(String var1, Function<String, V> var2, Function<V, String> var3, V var4) {
      String _snowman = this.c(_snowman);
      V _snowmanx = (V)MoreObjects.firstNonNull(_snowman != null ? _snowman.apply(_snowman) : null, _snowman);
      this.b.put(_snowman, _snowman.apply(_snowmanx));
      return new zk.a<>(_snowman, _snowmanx, _snowman);
   }

   protected <V> V a(String var1, Function<String, V> var2, UnaryOperator<V> var3, Function<V, String> var4, V var5) {
      return this.a(_snowman, var2x -> {
         V _snowman = _snowman.apply(var2x);
         return _snowman != null ? _snowman.apply(_snowman) : null;
      }, _snowman, _snowman);
   }

   protected <V> V a(String var1, Function<String, V> var2, V var3) {
      return this.a(_snowman, _snowman, Objects::toString, _snowman);
   }

   protected <V> zk<T>.a<V> b(String var1, Function<String, V> var2, V var3) {
      return this.b(_snowman, _snowman, Objects::toString, _snowman);
   }

   protected String a(String var1, String var2) {
      return this.a(_snowman, Function.identity(), Function.identity(), _snowman);
   }

   @Nullable
   protected String a(String var1) {
      return this.a(_snowman, Function.identity());
   }

   protected int a(String var1, int var2) {
      return this.a(_snowman, a(Integer::parseInt), Integer.valueOf(_snowman));
   }

   protected zk<T>.a<Integer> b(String var1, int var2) {
      return this.b(_snowman, a(Integer::parseInt), _snowman);
   }

   protected int a(String var1, UnaryOperator<Integer> var2, int var3) {
      return this.a(_snowman, a(Integer::parseInt), _snowman, Objects::toString, _snowman);
   }

   protected long a(String var1, long var2) {
      return this.a(_snowman, a(Long::parseLong), _snowman);
   }

   protected boolean a(String var1, boolean var2) {
      return this.a(_snowman, Boolean::valueOf, _snowman);
   }

   protected zk<T>.a<Boolean> b(String var1, boolean var2) {
      return this.b(_snowman, Boolean::valueOf, _snowman);
   }

   @Nullable
   protected Boolean b(String var1) {
      return this.a(_snowman, Boolean::valueOf);
   }

   protected Properties a() {
      Properties _snowman = new Properties();
      _snowman.putAll(this.b);
      return _snowman;
   }

   protected abstract T b(gn var1, Properties var2);

   public class a<V> implements Supplier<V> {
      private final String b;
      private final V c;
      private final Function<V, String> d;

      private a(String var2, V var3, Function<V, String> var4) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      @Override
      public V get() {
         return this.c;
      }

      public T a(gn var1, V var2) {
         Properties _snowman = zk.this.a();
         _snowman.put(this.b, this.d.apply(_snowman));
         return zk.this.b(_snowman, _snowman);
      }
   }
}
