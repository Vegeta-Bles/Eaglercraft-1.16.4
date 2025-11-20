package net.minecraft.client.render.entity;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int i = 1;
      if (stack.getCount() > 48) {
         i = 5;
      } else if (stack.getCount() > 32) {
         i = 4;
      } else if (stack.getCount() > 16) {
         i = 3;
      } else if (stack.getCount() > 1) {
         i = 2;
      }

      return i;
   }

   public void render(ItemEntity arg, float f, float g, MatrixStack arg2, VertexConsumerProvider arg3, int i) {
      arg2.push();
      ItemStack lv = arg.getStack();
      int j = lv.isEmpty() ? 187 : Item.getRawId(lv.getItem()) + lv.getDamage();
      this.random.setSeed((long)j);
      BakedModel lv2 = this.itemRenderer.getHeldItemModel(lv, arg.world, null);
      boolean bl = lv2.hasDepth();
      int k = this.getRenderedAmount(lv);
      float h = 0.25F;
      float l = MathHelper.sin(((float)arg.getAge() + g) / 10.0F + arg.hoverHeight) * 0.1F + 0.1F;
      float m = lv2.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
      arg2.translate(0.0, (double)(l + 0.25F * m), 0.0);
      float n = arg.method_27314(g);
      arg2.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(n));
      float o = lv2.getTransformation().ground.scale.getX();
      float p = lv2.getTransformation().ground.scale.getY();
      float q = lv2.getTransformation().ground.scale.getZ();
      if (!bl) {
         float r = -0.0F * (float)(k - 1) * 0.5F * o;
         float s = -0.0F * (float)(k - 1) * 0.5F * p;
         float t = -0.09375F * (float)(k - 1) * 0.5F * q;
         arg2.translate((double)r, (double)s, (double)t);
      }

      for (int u = 0; u < k; u++) {
         arg2.push();
         if (u > 0) {
            if (bl) {
               float v = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float w = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               float x = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
               arg2.translate((double)v, (double)w, (double)x);
            } else {
               float y = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               float z = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
               arg2.translate((double)y, (double)z, 0.0);
            }
         }

         this.itemRenderer.renderItem(lv, ModelTransformation.Mode.GROUND, false, arg2, arg3, i, OverlayTexture.DEFAULT_UV, lv2);
         arg2.pop();
         if (!bl) {
            arg2.translate((double)(0.0F * o), (double)(0.0F * p), (double)(0.09375F * q));
         }
      }

      arg2.pop();
      super.render(arg, f, g, arg2, arg3, i);
   }

   public Identifier getTexture(ItemEntity arg) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
