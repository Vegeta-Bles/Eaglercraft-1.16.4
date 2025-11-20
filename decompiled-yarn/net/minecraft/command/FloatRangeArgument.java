package net.minecraft.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.predicate.NumberRange;
import net.minecraft.text.TranslatableText;

public class FloatRangeArgument {
   public static final FloatRangeArgument ANY = new FloatRangeArgument(null, null);
   public static final SimpleCommandExceptionType ONLY_INTS_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.range.ints"));
   private final Float min;
   private final Float max;

   public FloatRangeArgument(@Nullable Float _snowman, @Nullable Float _snowman) {
      this.min = _snowman;
      this.max = _snowman;
   }

   @Nullable
   public Float getMin() {
      return this.min;
   }

   @Nullable
   public Float getMax() {
      return this.max;
   }

   public static FloatRangeArgument parse(StringReader reader, boolean allowFloats, Function<Float, Float> transform) throws CommandSyntaxException {
      if (!reader.canRead()) {
         throw NumberRange.EXCEPTION_EMPTY.createWithContext(reader);
      } else {
         int _snowman = reader.getCursor();
         Float _snowmanx = mapFloat(parseFloat(reader, allowFloats), transform);
         Float _snowmanxx;
         if (reader.canRead(2) && reader.peek() == '.' && reader.peek(1) == '.') {
            reader.skip();
            reader.skip();
            _snowmanxx = mapFloat(parseFloat(reader, allowFloats), transform);
            if (_snowmanx == null && _snowmanxx == null) {
               reader.setCursor(_snowman);
               throw NumberRange.EXCEPTION_EMPTY.createWithContext(reader);
            }
         } else {
            if (!allowFloats && reader.canRead() && reader.peek() == '.') {
               reader.setCursor(_snowman);
               throw ONLY_INTS_EXCEPTION.createWithContext(reader);
            }

            _snowmanxx = _snowmanx;
         }

         if (_snowmanx == null && _snowmanxx == null) {
            reader.setCursor(_snowman);
            throw NumberRange.EXCEPTION_EMPTY.createWithContext(reader);
         } else {
            return new FloatRangeArgument(_snowmanx, _snowmanxx);
         }
      }
   }

   @Nullable
   private static Float parseFloat(StringReader reader, boolean allowFloats) throws CommandSyntaxException {
      int _snowman = reader.getCursor();

      while (reader.canRead() && peekDigit(reader, allowFloats)) {
         reader.skip();
      }

      String _snowmanx = reader.getString().substring(_snowman, reader.getCursor());
      if (_snowmanx.isEmpty()) {
         return null;
      } else {
         try {
            return Float.parseFloat(_snowmanx);
         } catch (NumberFormatException var5) {
            if (allowFloats) {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(reader, _snowmanx);
            } else {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(reader, _snowmanx);
            }
         }
      }
   }

   private static boolean peekDigit(StringReader reader, boolean allowFloats) {
      char _snowman = reader.peek();
      if ((_snowman < '0' || _snowman > '9') && _snowman != '-') {
         return allowFloats && _snowman == '.' ? !reader.canRead(2) || reader.peek(1) != '.' : false;
      } else {
         return true;
      }
   }

   @Nullable
   private static Float mapFloat(@Nullable Float _snowman, Function<Float, Float> _snowman) {
      return _snowman == null ? null : _snowman.apply(_snowman);
   }
}
