package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;

public class SquidEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart head;
   private final ModelPart[] tentacles = new ModelPart[8];
   private final ImmutableList<ModelPart> parts;

   public SquidEntityModel() {
      int _snowman = -16;
      this.head = new ModelPart(this, 0, 0);
      this.head.addCuboid(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F);
      this.head.pivotY += 8.0F;

      for (int _snowmanx = 0; _snowmanx < this.tentacles.length; _snowmanx++) {
         this.tentacles[_snowmanx] = new ModelPart(this, 48, 0);
         double _snowmanxx = (double)_snowmanx * Math.PI * 2.0 / (double)this.tentacles.length;
         float _snowmanxxx = (float)Math.cos(_snowmanxx) * 5.0F;
         float _snowmanxxxx = (float)Math.sin(_snowmanxx) * 5.0F;
         this.tentacles[_snowmanx].addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);
         this.tentacles[_snowmanx].pivotX = _snowmanxxx;
         this.tentacles[_snowmanx].pivotZ = _snowmanxxxx;
         this.tentacles[_snowmanx].pivotY = 15.0F;
         _snowmanxx = (double)_snowmanx * Math.PI * -2.0 / (double)this.tentacles.length + (Math.PI / 2);
         this.tentacles[_snowmanx].yaw = (float)_snowmanxx;
      }

      Builder<ModelPart> _snowmanx = ImmutableList.builder();
      _snowmanx.add(this.head);
      _snowmanx.addAll(Arrays.asList(this.tentacles));
      this.parts = _snowmanx.build();
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      for (ModelPart _snowman : this.tentacles) {
         _snowman.pitch = animationProgress;
      }
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return this.parts;
   }
}
