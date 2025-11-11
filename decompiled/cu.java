import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;

public class cu {
   public static final cu a = new cu(null, null);
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.range.ints"));
   private final Float c;
   private final Float d;

   public cu(@Nullable Float var1, @Nullable Float var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   @Nullable
   public Float a() {
      return this.c;
   }

   @Nullable
   public Float b() {
      return this.d;
   }

   public static cu a(StringReader var0, boolean var1, Function<Float, Float> var2) throws CommandSyntaxException {
      if (!_snowman.canRead()) {
         throw bz.a.createWithContext(_snowman);
      } else {
         int _snowman = _snowman.getCursor();
         Float _snowmanx = a(b(_snowman, _snowman), _snowman);
         Float _snowmanxx;
         if (_snowman.canRead(2) && _snowman.peek() == '.' && _snowman.peek(1) == '.') {
            _snowman.skip();
            _snowman.skip();
            _snowmanxx = a(b(_snowman, _snowman), _snowman);
            if (_snowmanx == null && _snowmanxx == null) {
               _snowman.setCursor(_snowman);
               throw bz.a.createWithContext(_snowman);
            }
         } else {
            if (!_snowman && _snowman.canRead() && _snowman.peek() == '.') {
               _snowman.setCursor(_snowman);
               throw b.createWithContext(_snowman);
            }

            _snowmanxx = _snowmanx;
         }

         if (_snowmanx == null && _snowmanxx == null) {
            _snowman.setCursor(_snowman);
            throw bz.a.createWithContext(_snowman);
         } else {
            return new cu(_snowmanx, _snowmanxx);
         }
      }
   }

   @Nullable
   private static Float b(StringReader var0, boolean var1) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();

      while (_snowman.canRead() && c(_snowman, _snowman)) {
         _snowman.skip();
      }

      String _snowmanx = _snowman.getString().substring(_snowman, _snowman.getCursor());
      if (_snowmanx.isEmpty()) {
         return null;
      } else {
         try {
            return Float.parseFloat(_snowmanx);
         } catch (NumberFormatException var5) {
            if (_snowman) {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(_snowman, _snowmanx);
            } else {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(_snowman, _snowmanx);
            }
         }
      }
   }

   private static boolean c(StringReader var0, boolean var1) {
      char _snowman = _snowman.peek();
      if ((_snowman < '0' || _snowman > '9') && _snowman != '-') {
         return _snowman && _snowman == '.' ? !_snowman.canRead(2) || _snowman.peek(1) != '.' : false;
      } else {
         return true;
      }
   }

   @Nullable
   private static Float a(@Nullable Float var0, Function<Float, Float> var1) {
      return _snowman == null ? null : _snowman.apply(_snowman);
   }
}
