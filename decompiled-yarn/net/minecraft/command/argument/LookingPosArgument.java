package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class LookingPosArgument implements PosArgument {
   private final double x;
   private final double y;
   private final double z;

   public LookingPosArgument(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   @Override
   public Vec3d toAbsolutePos(ServerCommandSource source) {
      Vec2f _snowman = source.getRotation();
      Vec3d _snowmanx = source.getEntityAnchor().positionAt(source);
      float _snowmanxx = MathHelper.cos((_snowman.y + 90.0F) * (float) (Math.PI / 180.0));
      float _snowmanxxx = MathHelper.sin((_snowman.y + 90.0F) * (float) (Math.PI / 180.0));
      float _snowmanxxxx = MathHelper.cos(-_snowman.x * (float) (Math.PI / 180.0));
      float _snowmanxxxxx = MathHelper.sin(-_snowman.x * (float) (Math.PI / 180.0));
      float _snowmanxxxxxx = MathHelper.cos((-_snowman.x + 90.0F) * (float) (Math.PI / 180.0));
      float _snowmanxxxxxxx = MathHelper.sin((-_snowman.x + 90.0F) * (float) (Math.PI / 180.0));
      Vec3d _snowmanxxxxxxxx = new Vec3d((double)(_snowmanxx * _snowmanxxxx), (double)_snowmanxxxxx, (double)(_snowmanxxx * _snowmanxxxx));
      Vec3d _snowmanxxxxxxxxx = new Vec3d((double)(_snowmanxx * _snowmanxxxxxx), (double)_snowmanxxxxxxx, (double)(_snowmanxxx * _snowmanxxxxxx));
      Vec3d _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.crossProduct(_snowmanxxxxxxxxx).multiply(-1.0);
      double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.x * this.z + _snowmanxxxxxxxxx.x * this.y + _snowmanxxxxxxxxxx.x * this.x;
      double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx.y * this.z + _snowmanxxxxxxxxx.y * this.y + _snowmanxxxxxxxxxx.y * this.x;
      double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.z * this.z + _snowmanxxxxxxxxx.z * this.y + _snowmanxxxxxxxxxx.z * this.x;
      return new Vec3d(_snowmanx.x + _snowmanxxxxxxxxxxx, _snowmanx.y + _snowmanxxxxxxxxxxxx, _snowmanx.z + _snowmanxxxxxxxxxxxxx);
   }

   @Override
   public Vec2f toAbsoluteRotation(ServerCommandSource source) {
      return Vec2f.ZERO;
   }

   @Override
   public boolean isXRelative() {
      return true;
   }

   @Override
   public boolean isYRelative() {
      return true;
   }

   @Override
   public boolean isZRelative() {
      return true;
   }

   public static LookingPosArgument parse(StringReader reader) throws CommandSyntaxException {
      int _snowman = reader.getCursor();
      double _snowmanx = readCoordinate(reader, _snowman);
      if (reader.canRead() && reader.peek() == ' ') {
         reader.skip();
         double _snowmanxx = readCoordinate(reader, _snowman);
         if (reader.canRead() && reader.peek() == ' ') {
            reader.skip();
            double _snowmanxxx = readCoordinate(reader, _snowman);
            return new LookingPosArgument(_snowmanx, _snowmanxx, _snowmanxxx);
         } else {
            reader.setCursor(_snowman);
            throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
         }
      } else {
         reader.setCursor(_snowman);
         throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
      }
   }

   private static double readCoordinate(StringReader reader, int startingCursorPos) throws CommandSyntaxException {
      if (!reader.canRead()) {
         throw CoordinateArgument.MISSING_COORDINATE.createWithContext(reader);
      } else if (reader.peek() != '^') {
         reader.setCursor(startingCursorPos);
         throw Vec3ArgumentType.MIXED_COORDINATE_EXCEPTION.createWithContext(reader);
      } else {
         reader.skip();
         return reader.canRead() && reader.peek() != ' ' ? reader.readDouble() : 0.0;
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof LookingPosArgument)) {
         return false;
      } else {
         LookingPosArgument _snowman = (LookingPosArgument)o;
         return this.x == _snowman.x && this.y == _snowman.y && this.z == _snowman.z;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.x, this.y, this.z);
   }
}
