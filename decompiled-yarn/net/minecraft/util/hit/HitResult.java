package net.minecraft.util.hit;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public abstract class HitResult {
   protected final Vec3d pos;

   protected HitResult(Vec3d pos) {
      this.pos = pos;
   }

   public double squaredDistanceTo(Entity entity) {
      double _snowman = this.pos.x - entity.getX();
      double _snowmanx = this.pos.y - entity.getY();
      double _snowmanxx = this.pos.z - entity.getZ();
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public abstract HitResult.Type getType();

   public Vec3d getPos() {
      return this.pos;
   }

   public static enum Type {
      MISS,
      BLOCK,
      ENTITY;

      private Type() {
      }
   }
}
