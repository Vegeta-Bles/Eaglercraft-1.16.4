package net.minecraft.structure;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import net.minecraft.structure.pool.StructurePool;

public class JigsawJunction {
   private final int sourceX;
   private final int sourceGroundY;
   private final int sourceZ;
   private final int deltaY;
   private final StructurePool.Projection destProjection;

   public JigsawJunction(int sourceX, int sourceGroundY, int sourceZ, int deltaY, StructurePool.Projection destProjection) {
      this.sourceX = sourceX;
      this.sourceGroundY = sourceGroundY;
      this.sourceZ = sourceZ;
      this.deltaY = deltaY;
      this.destProjection = destProjection;
   }

   public int getSourceX() {
      return this.sourceX;
   }

   public int getSourceGroundY() {
      return this.sourceGroundY;
   }

   public int getSourceZ() {
      return this.sourceZ;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> _snowman) {
      Builder<T, T> _snowmanx = ImmutableMap.builder();
      _snowmanx.put(_snowman.createString("source_x"), _snowman.createInt(this.sourceX))
         .put(_snowman.createString("source_ground_y"), _snowman.createInt(this.sourceGroundY))
         .put(_snowman.createString("source_z"), _snowman.createInt(this.sourceZ))
         .put(_snowman.createString("delta_y"), _snowman.createInt(this.deltaY))
         .put(_snowman.createString("dest_proj"), _snowman.createString(this.destProjection.getId()));
      return new Dynamic(_snowman, _snowman.createMap(_snowmanx.build()));
   }

   public static <T> JigsawJunction method_28873(Dynamic<T> _snowman) {
      return new JigsawJunction(
         _snowman.get("source_x").asInt(0),
         _snowman.get("source_ground_y").asInt(0),
         _snowman.get("source_z").asInt(0),
         _snowman.get("delta_y").asInt(0),
         StructurePool.Projection.getById(_snowman.get("dest_proj").asString(""))
      );
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         JigsawJunction _snowman = (JigsawJunction)o;
         if (this.sourceX != _snowman.sourceX) {
            return false;
         } else if (this.sourceZ != _snowman.sourceZ) {
            return false;
         } else {
            return this.deltaY != _snowman.deltaY ? false : this.destProjection == _snowman.destProjection;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.sourceX;
      _snowman = 31 * _snowman + this.sourceGroundY;
      _snowman = 31 * _snowman + this.sourceZ;
      _snowman = 31 * _snowman + this.deltaY;
      return 31 * _snowman + this.destProjection.hashCode();
   }

   @Override
   public String toString() {
      return "JigsawJunction{sourceX="
         + this.sourceX
         + ", sourceGroundY="
         + this.sourceGroundY
         + ", sourceZ="
         + this.sourceZ
         + ", deltaY="
         + this.deltaY
         + ", destProjection="
         + this.destProjection
         + '}';
   }
}
