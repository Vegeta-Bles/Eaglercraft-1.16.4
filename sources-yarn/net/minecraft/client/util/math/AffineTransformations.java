package net.minecraft.client.util.math;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class AffineTransformations {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final EnumMap<Direction, AffineTransformation> DIRECTION_ROTATIONS = Util.make(Maps.newEnumMap(Direction.class), enumMap -> {
      enumMap.put(Direction.SOUTH, AffineTransformation.identity());
      enumMap.put(Direction.EAST, new AffineTransformation(null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90.0F, true), null, null));
      enumMap.put(Direction.WEST, new AffineTransformation(null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), -90.0F, true), null, null));
      enumMap.put(Direction.NORTH, new AffineTransformation(null, new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180.0F, true), null, null));
      enumMap.put(Direction.UP, new AffineTransformation(null, new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), -90.0F, true), null, null));
      enumMap.put(Direction.DOWN, new AffineTransformation(null, new Quaternion(new Vector3f(1.0F, 0.0F, 0.0F), 90.0F, true), null, null));
   });
   public static final EnumMap<Direction, AffineTransformation> INVERTED_DIRECTION_ROTATIONS = Util.make(Maps.newEnumMap(Direction.class), enumMap -> {
      for (Direction lv : Direction.values()) {
         enumMap.put(lv, DIRECTION_ROTATIONS.get(lv).invert());
      }
   });

   public static AffineTransformation setupUvLock(AffineTransformation arg) {
      Matrix4f lv = Matrix4f.translate(0.5F, 0.5F, 0.5F);
      lv.multiply(arg.getMatrix());
      lv.multiply(Matrix4f.translate(-0.5F, -0.5F, -0.5F));
      return new AffineTransformation(lv);
   }

   public static AffineTransformation uvLock(AffineTransformation arg, Direction arg2, Supplier<String> supplier) {
      Direction lv = Direction.transform(arg.getMatrix(), arg2);
      AffineTransformation lv2 = arg.invert();
      if (lv2 == null) {
         LOGGER.warn(supplier.get());
         return new AffineTransformation(null, null, new Vector3f(0.0F, 0.0F, 0.0F), null);
      } else {
         AffineTransformation lv3 = INVERTED_DIRECTION_ROTATIONS.get(arg2).multiply(lv2).multiply(DIRECTION_ROTATIONS.get(lv));
         return setupUvLock(lv3);
      }
   }
}
