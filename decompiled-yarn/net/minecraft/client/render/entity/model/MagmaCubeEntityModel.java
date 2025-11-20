package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.MathHelper;

public class MagmaCubeEntityModel<T extends SlimeEntity> extends CompositeEntityModel<T> {
   private final ModelPart[] field_3427 = new ModelPart[8];
   private final ModelPart innerCube;
   private final ImmutableList<ModelPart> parts;

   public MagmaCubeEntityModel() {
      for (int _snowman = 0; _snowman < this.field_3427.length; _snowman++) {
         int _snowmanx = 0;
         int _snowmanxx = _snowman;
         if (_snowman == 2) {
            _snowmanx = 24;
            _snowmanxx = 10;
         } else if (_snowman == 3) {
            _snowmanx = 24;
            _snowmanxx = 19;
         }

         this.field_3427[_snowman] = new ModelPart(this, _snowmanx, _snowmanxx);
         this.field_3427[_snowman].addCuboid(-4.0F, (float)(16 + _snowman), -4.0F, 8.0F, 1.0F, 8.0F);
      }

      this.innerCube = new ModelPart(this, 0, 16);
      this.innerCube.addCuboid(-2.0F, 18.0F, -2.0F, 4.0F, 4.0F, 4.0F);
      Builder<ModelPart> _snowman = ImmutableList.builder();
      _snowman.add(this.innerCube);
      _snowman.addAll(Arrays.asList(this.field_3427));
      this.parts = _snowman.build();
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
   }

   public void animateModel(T _snowman, float _snowman, float _snowman, float _snowman) {
      float _snowmanxxxx = MathHelper.lerp(_snowman, _snowman.lastStretch, _snowman.stretch);
      if (_snowmanxxxx < 0.0F) {
         _snowmanxxxx = 0.0F;
      }

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < this.field_3427.length; _snowmanxxxxx++) {
         this.field_3427[_snowmanxxxxx].pivotY = (float)(-(4 - _snowmanxxxxx)) * _snowmanxxxx * 1.7F;
      }
   }

   public ImmutableList<ModelPart> getParts() {
      return this.parts;
   }
}
