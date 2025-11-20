package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.text.TranslatableText;

public class CoordinateArgument {
   public static final SimpleCommandExceptionType MISSING_COORDINATE = new SimpleCommandExceptionType(new TranslatableText("argument.pos.missing.double"));
   public static final SimpleCommandExceptionType MISSING_BLOCK_POSITION = new SimpleCommandExceptionType(new TranslatableText("argument.pos.missing.int"));
   private final boolean relative;
   private final double value;

   public CoordinateArgument(boolean relative, double value) {
      this.relative = relative;
      this.value = value;
   }

   public double toAbsoluteCoordinate(double offset) {
      return this.relative ? this.value + offset : this.value;
   }

   public static CoordinateArgument parse(StringReader reader, boolean centerIntegers) throws CommandSyntaxException {
      if (reader.canRead() && reader.peek() == '^') {
         throw Vec3ArgumentType.MIXED_COORDINATE_EXCEPTION.createWithContext(reader);
      } else if (!reader.canRead()) {
         throw MISSING_COORDINATE.createWithContext(reader);
      } else {
         boolean _snowman = isRelative(reader);
         int _snowmanx = reader.getCursor();
         double _snowmanxx = reader.canRead() && reader.peek() != ' ' ? reader.readDouble() : 0.0;
         String _snowmanxxx = reader.getString().substring(_snowmanx, reader.getCursor());
         if (_snowman && _snowmanxxx.isEmpty()) {
            return new CoordinateArgument(true, 0.0);
         } else {
            if (!_snowmanxxx.contains(".") && !_snowman && centerIntegers) {
               _snowmanxx += 0.5;
            }

            return new CoordinateArgument(_snowman, _snowmanxx);
         }
      }
   }

   public static CoordinateArgument parse(StringReader reader) throws CommandSyntaxException {
      if (reader.canRead() && reader.peek() == '^') {
         throw Vec3ArgumentType.MIXED_COORDINATE_EXCEPTION.createWithContext(reader);
      } else if (!reader.canRead()) {
         throw MISSING_BLOCK_POSITION.createWithContext(reader);
      } else {
         boolean _snowman = isRelative(reader);
         double _snowmanx;
         if (reader.canRead() && reader.peek() != ' ') {
            _snowmanx = _snowman ? reader.readDouble() : (double)reader.readInt();
         } else {
            _snowmanx = 0.0;
         }

         return new CoordinateArgument(_snowman, _snowmanx);
      }
   }

   public static boolean isRelative(StringReader reader) {
      boolean _snowman;
      if (reader.peek() == '~') {
         _snowman = true;
         reader.skip();
      } else {
         _snowman = false;
      }

      return _snowman;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof CoordinateArgument)) {
         return false;
      } else {
         CoordinateArgument _snowman = (CoordinateArgument)o;
         return this.relative != _snowman.relative ? false : Double.compare(_snowman.value, this.value) == 0;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.relative ? 1 : 0;
      long _snowmanx = Double.doubleToLongBits(this.value);
      return 31 * _snowman + (int)(_snowmanx ^ _snowmanx >>> 32);
   }

   public boolean isRelative() {
      return this.relative;
   }
}
