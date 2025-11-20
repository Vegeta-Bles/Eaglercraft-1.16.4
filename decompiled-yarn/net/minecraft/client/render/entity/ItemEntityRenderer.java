package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ItemEntityRenderer extends EntityRenderer<ItemEntity> {
   private final ItemRenderer itemRenderer;
   private final Random random = new Random();

   public ItemEntityRenderer(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
      super(dispatcher);
      this.itemRenderer = itemRenderer;
      this.shadowRadius = 0.15F;
      this.shadowOpacity = 0.75F;
   }

   private int getRenderedAmount(ItemStack stack) {
      int _snowman = 1;
      if (stack.getCount() > 48) {
         _snowman = 5;
      } else if (stack.getCount() > 32) {
         _snowman = 4;
      } else if (stack.getCount() > 16) {
         _snowman = 3;
      } else if (stack.getCount() > 1) {
         _snowman = 2;
      }

      return _snowman;
   }

   public void render(ItemEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      ItemStack _snowmanxxxxxx = _snowman.getStack();
      int _snowmanxxxxxxx = _snowmanxxxxxx.isEmpty() ? 187 : Item.getRawId(_snowmanxxxxxx.getItem()) + _snowmanxxxxxx.getDamage();
      this.random.setSeed((long)_snowmanxxxxxxx);
      BakedModel _snowmanxxxxxxxx = this.itemRenderer.getHeldItemModel(_snowmanxxxxxx, _snowman.world, null);
      boolean _snowmanxxxxxxxxx = _snowmanxxxxxxxx.hasDepth();
      int _snowmanxxxxxxxxxx = this.getRenderedAmount(_snowmanxxxxxx);
      float _snowmanxxxxxxxxxxx = 0.25F;
      float _snowmanxxxxxxxxxxxx = MathHelper.sin(((float)_snowman.getAge() + _snowman) / 10.0F + _snowman.hoverHeight) * 0.1F + 0.1F;
      float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
      _snowman.translate(0.0, (double)(_snowmanxxxxxxxxxxxx + 0.25F * _snowmanxxxxxxxxxxxxx), 0.0);
      float _snowmanxxxxxxxxxxxxxx = _snowman.method_27314(_snowman);
      _snowman.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(_snowmanxxxxxxxxxxxxxx));
      float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.getTransformation().ground.scale.getX();
      float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.getTransformation().ground.scale.getY();
      float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.getTransformation().ground.scale.getZ();
      if (!_snowmanxxxxxxxxx) {
         float _snowmanxxxxxxxxxxxxxxxxxx = -0.0F * (float)(_snowmanxxxxxxxxxx - 1) * 0.5F * _snowmanxxxxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxxxx = -0.0F * (float)(_snowmanxxxxxxxxxx - 1) * 0.5F * _snowmanxxxxxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxxxxx = -0.09375F * (float)(_snowmanxxxxxxxxxx - 1) * 0.5F * _snowmanxxxxxxxxxxxxxxxxx;
         _snowman.translate((double)_snowmanxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxx);
      }

      for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx++) {
         _snowman.push();
         if (_snowmanxxxxxxxxxxxxxxxxxx > 0) {
            if (_snowmanxxxxxxxxx) {
               float _snowmanxxxxxxxxxxxxxxxxxxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               _snowman.translate((double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxxx);
            } else {
               float _snowmanxxxxxxxxxxxxxxxxxxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               _snowman.translate((double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxx, 0.0);
            }
         }

         this.itemRenderer.renderItem(_snowmanxxxxxx, ModelTransformation.Mode.GROUND, false, _snowman, _snowman, _snowman, OverlayTexture.DEFAULT_UV, _snowmanxxxxxxxx);
         _snowman.pop();
         if (!_snowmanxxxxxxxxx) {
            _snowman.translate((double)(0.0F * _snowmanxxxxxxxxxxxxxxx), (double)(0.0F * _snowmanxxxxxxxxxxxxxxxx), (double)(0.09375F * _snowmanxxxxxxxxxxxxxxxxx));
         }
      }

      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(ItemEntity _snowman) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
