package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Random;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class GhastEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart[] tentacles = new ModelPart[9];
   private final ImmutableList<ModelPart> parts;

   public GhastEntityModel() {
      Builder<ModelPart> _snowman = ImmutableList.builder();
      ModelPart _snowmanx = new ModelPart(this, 0, 0);
      _snowmanx.addCuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
      _snowmanx.pivotY = 17.6F;
      _snowman.add(_snowmanx);
      Random _snowmanxx = new Random(1660L);

      for (int _snowmanxxx = 0; _snowmanxxx < this.tentacles.length; _snowmanxxx++) {
         this.tentacles[_snowmanxxx] = new ModelPart(this, 0, 0);
         float _snowmanxxxx = (((float)(_snowmanxxx % 3) - (float)(_snowmanxxx / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float _snowmanxxxxx = ((float)(_snowmanxxx / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
         int _snowmanxxxxxx = _snowmanxx.nextInt(7) + 8;
         this.tentacles[_snowmanxxx].addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, (float)_snowmanxxxxxx, 2.0F);
         this.tentacles[_snowmanxxx].pivotX = _snowmanxxxx;
         this.tentacles[_snowmanxxx].pivotZ = _snowmanxxxxx;
         this.tentacles[_snowmanxxx].pivotY = 24.6F;
         _snowman.add(this.tentacles[_snowmanxxx]);
      }

      this.parts = _snowman.build();
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      for (int _snowman = 0; _snowman < this.tentacles.length; _snowman++) {
         this.tentacles[_snowman].pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)_snowman) + 0.4F;
      }
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return this.parts;
   }
}
