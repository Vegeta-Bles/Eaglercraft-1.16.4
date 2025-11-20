package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class DefaultPosArgument implements PosArgument {
   private final CoordinateArgument x;
   private final CoordinateArgument y;
   private final CoordinateArgument z;

   public DefaultPosArgument(CoordinateArgument x, CoordinateArgument y, CoordinateArgument z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   @Override
   public Vec3d toAbsolutePos(ServerCommandSource source) {
      Vec3d _snowman = source.getPosition();
      return new Vec3d(this.x.toAbsoluteCoordinate(_snowman.x), this.y.toAbsoluteCoordinate(_snowman.y), this.z.toAbsoluteCoordinate(_snowman.z));
   }

   @Override
   public Vec2f toAbsoluteRotation(ServerCommandSource source) {
      Vec2f _snowman = source.getRotation();
      return new Vec2f((float)this.x.toAbsoluteCoordinate((double)_snowman.x), (float)this.y.toAbsoluteCoordinate((double)_snowman.y));
   }

   @Override
   public boolean isXRelative() {
      return this.x.isRelative();
   }

   @Override
   public boolean isYRelative() {
      return this.y.isRelative();
   }

   @Override
   public boolean isZRelative() {
      return this.z.isRelative();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DefaultPosArgument)) {
         return false;
      } else {
         DefaultPosArgument _snowman = (DefaultPosArgument)o;
         if (!this.x.equals(_snowman.x)) {
            return false;
         } else {
            return !this.y.equals(_snowman.y) ? false : this.z.equals(_snowman.z);
         }
      }
   }

   public static DefaultPosArgument parse(StringReader reader) throws CommandSyntaxException {
      int _snowman = reader.getCursor();
      CoordinateArgument _snowmanx = CoordinateArgument.parse(reader);
      if (reader.canRead() && reader.peek() == ' ') {
         reader.skip();
         CoordinateArgument _snowmanxx = CoordinateArgument.parse(reader);
         if (reader.canRead() && reader.peek() == ' ') {
            reader.skip();
            CoordinateArgument _snowmanxxx = CoordinateArgument.parse(reader);
            return new DefaultPosArgument(_snowmanx, _snowmanxx, _snowmanxxx);
         } else {
            reader.setCursor(_snowman);
            throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
         }
      } else {
         reader.setCursor(_snowman);
         throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
      }
   }

   public static DefaultPosArgument parse(StringReader reader, boolean centerIntegers) throws CommandSyntaxException {
      int _snowman = reader.getCursor();
      CoordinateArgument _snowmanx = CoordinateArgument.parse(reader, centerIntegers);
      if (reader.canRead() && reader.peek() == ' ') {
         reader.skip();
         CoordinateArgument _snowmanxx = CoordinateArgument.parse(reader, false);
         if (reader.canRead() && reader.peek() == ' ') {
            reader.skip();
            CoordinateArgument _snowmanxxx = CoordinateArgument.parse(reader, centerIntegers);
            return new DefaultPosArgument(_snowmanx, _snowmanxx, _snowmanxxx);
         } else {
            reader.setCursor(_snowman);
            throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
         }
      } else {
         reader.setCursor(_snowman);
         throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
      }
   }

   public static DefaultPosArgument zero() {
      return new DefaultPosArgument(new CoordinateArgument(true, 0.0), new CoordinateArgument(true, 0.0), new CoordinateArgument(true, 0.0));
   }

   @Override
   public int hashCode() {
      int _snowman = this.x.hashCode();
      _snowman = 31 * _snowman + this.y.hashCode();
      return 31 * _snowman + this.z.hashCode();
   }
}
