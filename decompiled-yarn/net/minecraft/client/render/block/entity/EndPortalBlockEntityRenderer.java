package net.minecraft.client.render.block.entity;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;

public class EndPortalBlockEntityRenderer<T extends EndPortalBlockEntity> extends BlockEntityRenderer<T> {
   public static final Identifier SKY_TEXTURE = new Identifier("textures/environment/end_sky.png");
   public static final Identifier PORTAL_TEXTURE = new Identifier("textures/entity/end_portal.png");
   private static final Random RANDOM = new Random(31100L);
   private static final List<RenderLayer> field_21732 = IntStream.range(0, 16)
      .mapToObj(_snowman -> RenderLayer.getEndPortal(_snowman + 1))
      .collect(ImmutableList.toImmutableList());

   public EndPortalBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(T _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      RANDOM.setSeed(31100L);
      double _snowmanxxxxxx = _snowman.getPos().getSquaredDistance(this.dispatcher.camera.getPos(), true);
      int _snowmanxxxxxxx = this.method_3592(_snowmanxxxxxx);
      float _snowmanxxxxxxxx = this.method_3594();
      Matrix4f _snowmanxxxxxxxxx = _snowman.peek().getModel();
      this.method_23084(_snowman, _snowmanxxxxxxxx, 0.15F, _snowmanxxxxxxxxx, _snowman.getBuffer(field_21732.get(0)));

      for (int _snowmanxxxxxxxxxx = 1; _snowmanxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxx++) {
         this.method_23084(_snowman, _snowmanxxxxxxxx, 2.0F / (float)(18 - _snowmanxxxxxxxxxx), _snowmanxxxxxxxxx, _snowman.getBuffer(field_21732.get(_snowmanxxxxxxxxxx)));
      }
   }

   private void method_23084(T _snowman, float _snowman, float _snowman, Matrix4f _snowman, VertexConsumer _snowman) {
      float _snowmanxxxxx = (RANDOM.nextFloat() * 0.5F + 0.1F) * _snowman;
      float _snowmanxxxxxx = (RANDOM.nextFloat() * 0.5F + 0.4F) * _snowman;
      float _snowmanxxxxxxx = (RANDOM.nextFloat() * 0.5F + 0.5F) * _snowman;
      this.method_23085(_snowman, _snowman, _snowman, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, Direction.SOUTH);
      this.method_23085(_snowman, _snowman, _snowman, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, Direction.NORTH);
      this.method_23085(_snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, Direction.EAST);
      this.method_23085(_snowman, _snowman, _snowman, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, Direction.WEST);
      this.method_23085(_snowman, _snowman, _snowman, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, Direction.DOWN);
      this.method_23085(_snowman, _snowman, _snowman, 0.0F, 1.0F, _snowman, _snowman, 1.0F, 1.0F, 0.0F, 0.0F, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, Direction.UP);
   }

   private void method_23085(
      T _snowman, Matrix4f _snowman, VertexConsumer _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, Direction _snowman
   ) {
      if (_snowman.shouldDrawSide(_snowman)) {
         _snowman.vertex(_snowman, _snowman, _snowman, _snowman).color(_snowman, _snowman, _snowman, 1.0F).next();
         _snowman.vertex(_snowman, _snowman, _snowman, _snowman).color(_snowman, _snowman, _snowman, 1.0F).next();
         _snowman.vertex(_snowman, _snowman, _snowman, _snowman).color(_snowman, _snowman, _snowman, 1.0F).next();
         _snowman.vertex(_snowman, _snowman, _snowman, _snowman).color(_snowman, _snowman, _snowman, 1.0F).next();
      }
   }

   protected int method_3592(double _snowman) {
      if (_snowman > 36864.0) {
         return 1;
      } else if (_snowman > 25600.0) {
         return 3;
      } else if (_snowman > 16384.0) {
         return 5;
      } else if (_snowman > 9216.0) {
         return 7;
      } else if (_snowman > 4096.0) {
         return 9;
      } else if (_snowman > 1024.0) {
         return 11;
      } else if (_snowman > 576.0) {
         return 13;
      } else {
         return _snowman > 256.0 ? 14 : 15;
      }
   }

   protected float method_3594() {
      return 0.75F;
   }
}
