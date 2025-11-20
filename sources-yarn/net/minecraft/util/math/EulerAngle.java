package net.minecraft.util.math;

import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;

public class EulerAngle {
   protected final float pitch;
   protected final float yaw;
   protected final float roll;

   public EulerAngle(float pitch, float yaw, float roll) {
      this.pitch = !Float.isInfinite(pitch) && !Float.isNaN(pitch) ? pitch % 360.0F : 0.0F;
      this.yaw = !Float.isInfinite(yaw) && !Float.isNaN(yaw) ? yaw % 360.0F : 0.0F;
      this.roll = !Float.isInfinite(roll) && !Float.isNaN(roll) ? roll % 360.0F : 0.0F;
   }

   public EulerAngle(ListTag serialized) {
      this(serialized.getFloat(0), serialized.getFloat(1), serialized.getFloat(2));
   }

   public ListTag serialize() {
      ListTag lv = new ListTag();
      lv.add(FloatTag.of(this.pitch));
      lv.add(FloatTag.of(this.yaw));
      lv.add(FloatTag.of(this.roll));
      return lv;
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof EulerAngle)) {
         return false;
      } else {
         EulerAngle lv = (EulerAngle)o;
         return this.pitch == lv.pitch && this.yaw == lv.yaw && this.roll == lv.roll;
      }
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getRoll() {
      return this.roll;
   }
}
