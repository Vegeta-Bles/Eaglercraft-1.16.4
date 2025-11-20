package net.minecraft.client.util.math;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AffineTransformations {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final EnumMap<Direction, AffineTransformation> DIRECTION_ROTATIONS = Util.make(Maps.newEnumMap(Direction.class), _snowman -> {
      _snowman.put(Direction.SOUTH, AffineTransformation.identity());
      _snowman.put(Direction.EAST, new AffineTransformation(null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90.0F, true), null, null));
      _snowman.put(Direction.WEST, new AffineTransformation(null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), -90.0F, true), null, null));
      _snowman.put(Direction.NORTH, new AffineTransformation(null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180.0F, true), null, null));
      _snowman.put(Direction.UP, new AffineTransformation(null, new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), -90.0F, true), null, null));
      _snowman.put(Direction.DOWN, new AffineTransformation(null, new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), 90.0F, true), null, null));
   });
   public static final EnumMap<Direction, AffineTransformation> INVERTED_DIRECTION_ROTATIONS = Util.make(Maps.newEnumMap(Direction.class), _snowman -> {
      for (Direction _snowmanx : Direction.values()) {
         _snowman.put(_snowmanx, DIRECTION_ROTATIONS.get(_snowmanx).invert());
      }
   });

   public static AffineTransformation setupUvLock(AffineTransformation _snowman) {
      Matrix4f _snowmanx = Matrix4f.translate(0.5F, 0.5F, 0.5F);
      _snowmanx.multiply(_snowman.getMatrix());
      _snowmanx.multiply(Matrix4f.translate(-0.5F, -0.5F, -0.5F));
      return new AffineTransformation(_snowmanx);
   }

   public static AffineTransformation uvLock(AffineTransformation _snowman, Direction _snowman, Supplier<String> _snowman) {
      Direction _snowmanxxx = Direction.transform(_snowman.getMatrix(), _snowman);
      AffineTransformation _snowmanxxxx = _snowman.invert();
      if (_snowmanxxxx == null) {
         LOGGER.warn(_snowman.get());
         return new AffineTransformation(null, null, new Vector3f(0.0F, 0.0F, 0.0F), null);
      } else {
         AffineTransformation _snowmanxxxxx = INVERTED_DIRECTION_ROTATIONS.get(_snowman).multiply(_snowmanxxxx).multiply(DIRECTION_ROTATIONS.get(_snowmanxxx));
         return setupUvLock(_snowmanxxxxx);
      }
   }
}
