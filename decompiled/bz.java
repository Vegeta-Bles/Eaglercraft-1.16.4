import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public abstract class bz<T extends Number> {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.range.empty"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.range.swapped"));
   protected final T c;
   protected final T d;

   protected bz(@Nullable T var1, @Nullable T var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   @Nullable
   public T a() {
      return this.c;
   }

   @Nullable
   public T b() {
      return this.d;
   }

   public boolean c() {
      return this.c == null && this.d == null;
   }

   public JsonElement d() {
      if (this.c()) {
         return JsonNull.INSTANCE;
      } else if (this.c != null && this.c.equals(this.d)) {
         return new JsonPrimitive(this.c);
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.c != null) {
            _snowman.addProperty("min", this.c);
         }

         if (this.d != null) {
            _snowman.addProperty("max", this.d);
         }

         return _snowman;
      }
   }

   protected static <T extends Number, R extends bz<T>> R a(@Nullable JsonElement var0, R var1, BiFunction<JsonElement, String, T> var2, bz.a<T, R> var3) {
      if (_snowman == null || _snowman.isJsonNull()) {
         return _snowman;
      } else if (afd.b(_snowman)) {
         T _snowman = (T)_snowman.apply(_snowman, "value");
         return _snowman.create(_snowman, _snowman);
      } else {
         JsonObject _snowman = afd.m(_snowman, "value");
         T _snowmanx = _snowman.has("min") ? _snowman.apply(_snowman.get("min"), "min") : null;
         T _snowmanxx = _snowman.has("max") ? _snowman.apply(_snowman.get("max"), "max") : null;
         return _snowman.create(_snowmanx, _snowmanxx);
      }
   }

   protected static <T extends Number, R extends bz<T>> R a(
      StringReader var0, bz.b<T, R> var1, Function<String, T> var2, Supplier<DynamicCommandExceptionType> var3, Function<T, T> var4
   ) throws CommandSyntaxException {
      if (!_snowman.canRead()) {
         throw a.createWithContext(_snowman);
      } else {
         int _snowman = _snowman.getCursor();

         try {
            T _snowmanx = (T)a(a(_snowman, _snowman, _snowman), _snowman);
            T _snowmanxx;
            if (_snowman.canRead(2) && _snowman.peek() == '.' && _snowman.peek(1) == '.') {
               _snowman.skip();
               _snowman.skip();
               _snowmanxx = (T)a(a(_snowman, _snowman, _snowman), _snowman);
               if (_snowmanx == null && _snowmanxx == null) {
                  throw a.createWithContext(_snowman);
               }
            } else {
               _snowmanxx = _snowmanx;
            }

            if (_snowmanx == null && _snowmanxx == null) {
               throw a.createWithContext(_snowman);
            } else {
               return _snowman.create(_snowman, _snowmanx, _snowmanxx);
            }
         } catch (CommandSyntaxException var8) {
            _snowman.setCursor(_snowman);
            throw new CommandSyntaxException(var8.getType(), var8.getRawMessage(), var8.getInput(), _snowman);
         }
      }
   }

   @Nullable
   private static <T extends Number> T a(StringReader var0, Function<String, T> var1, Supplier<DynamicCommandExceptionType> var2) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();

      while (_snowman.canRead() && a(_snowman)) {
         _snowman.skip();
      }

      String _snowmanx = _snowman.getString().substring(_snowman, _snowman.getCursor());
      if (_snowmanx.isEmpty()) {
         return null;
      } else {
         try {
            return _snowman.apply(_snowmanx);
         } catch (NumberFormatException var6) {
            throw _snowman.get().createWithContext(_snowman, _snowmanx);
         }
      }
   }

   private static boolean a(StringReader var0) {
      char _snowman = _snowman.peek();
      if ((_snowman < '0' || _snowman > '9') && _snowman != '-') {
         return _snowman != '.' ? false : !_snowman.canRead(2) || _snowman.peek(1) != '.';
      } else {
         return true;
      }
   }

   @Nullable
   private static <T> T a(@Nullable T var0, Function<T, T> var1) {
      return _snowman == null ? null : _snowman.apply(_snowman);
   }

   @FunctionalInterface
   public interface a<T extends Number, R extends bz<T>> {
      R create(@Nullable T var1, @Nullable T var2);
   }

   @FunctionalInterface
   public interface b<T extends Number, R extends bz<T>> {
      R create(StringReader var1, @Nullable T var2, @Nullable T var3) throws CommandSyntaxException;
   }

   public static class c extends bz<Float> {
      public static final bz.c e = new bz.c(null, null);
      private final Double f;
      private final Double g;

      private static bz.c a(StringReader var0, @Nullable Float var1, @Nullable Float var2) throws CommandSyntaxException {
         if (_snowman != null && _snowman != null && _snowman > _snowman) {
            throw b.createWithContext(_snowman);
         } else {
            return new bz.c(_snowman, _snowman);
         }
      }

      @Nullable
      private static Double a(@Nullable Float var0) {
         return _snowman == null ? null : _snowman.doubleValue() * _snowman.doubleValue();
      }

      private c(@Nullable Float var1, @Nullable Float var2) {
         super(_snowman, _snowman);
         this.f = a(_snowman);
         this.g = a(_snowman);
      }

      public static bz.c b(float var0) {
         return new bz.c(_snowman, null);
      }

      public boolean d(float var1) {
         return this.c != null && this.c > _snowman ? false : this.d == null || !(this.d < _snowman);
      }

      public boolean a(double var1) {
         return this.f != null && this.f > _snowman ? false : this.g == null || !(this.g < _snowman);
      }

      public static bz.c a(@Nullable JsonElement var0) {
         return a(_snowman, e, afd::e, bz.c::new);
      }

      public static bz.c a(StringReader var0) throws CommandSyntaxException {
         return a(_snowman, var0x -> var0x);
      }

      public static bz.c a(StringReader var0, Function<Float, Float> var1) throws CommandSyntaxException {
         return a(_snowman, bz.c::a, Float::parseFloat, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidFloat, _snowman);
      }
   }

   public static class d extends bz<Integer> {
      public static final bz.d e = new bz.d(null, null);
      private final Long f;
      private final Long g;

      private static bz.d a(StringReader var0, @Nullable Integer var1, @Nullable Integer var2) throws CommandSyntaxException {
         if (_snowman != null && _snowman != null && _snowman > _snowman) {
            throw b.createWithContext(_snowman);
         } else {
            return new bz.d(_snowman, _snowman);
         }
      }

      @Nullable
      private static Long a(@Nullable Integer var0) {
         return _snowman == null ? null : _snowman.longValue() * _snowman.longValue();
      }

      private d(@Nullable Integer var1, @Nullable Integer var2) {
         super(_snowman, _snowman);
         this.f = a(_snowman);
         this.g = a(_snowman);
      }

      public static bz.d a(int var0) {
         return new bz.d(_snowman, _snowman);
      }

      public static bz.d b(int var0) {
         return new bz.d(_snowman, null);
      }

      public boolean d(int var1) {
         return this.c != null && this.c > _snowman ? false : this.d == null || this.d >= _snowman;
      }

      public static bz.d a(@Nullable JsonElement var0) {
         return a(_snowman, e, afd::g, bz.d::new);
      }

      public static bz.d a(StringReader var0) throws CommandSyntaxException {
         return a(_snowman, var0x -> var0x);
      }

      public static bz.d a(StringReader var0, Function<Integer, Integer> var1) throws CommandSyntaxException {
         return a(_snowman, bz.d::a, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, _snowman);
      }
   }
}
