package net.minecraft.client.render.item;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.OverlayVertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ItemRenderer implements SynchronousResourceReloadListener {
   public static final Identifier ENCHANTED_ITEM_GLINT = new Identifier("textures/misc/enchanted_item_glint.png");
   private static final Set<Item> WITHOUT_MODELS = Sets.newHashSet(new Item[]{Items.AIR});
   public float zOffset;
   private final ItemModels models;
   private final TextureManager textureManager;
   private final ItemColors colorMap;

   public ItemRenderer(TextureManager manager, BakedModelManager bakery, ItemColors colorMap) {
      this.textureManager = manager;
      this.models = new ItemModels(bakery);

      for (Item lv : Registry.ITEM) {
         if (!WITHOUT_MODELS.contains(lv)) {
            this.models.putModel(lv, new ModelIdentifier(Registry.ITEM.getId(lv), "inventory"));
         }
      }

      this.colorMap = colorMap;
   }

   public ItemModels getModels() {
      return this.models;
   }

   private void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {
      Random random = new Random();
      long l = 42L;

      for (Direction lv : Direction.values()) {
         random.setSeed(42L);
         this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, lv, random), stack, light, overlay);
      }

      random.setSeed(42L);
      this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), stack, light, overlay);
   }

   public void renderItem(
      ItemStack stack,
      ModelTransformation.Mode renderMode,
      boolean leftHanded,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      int overlay,
      BakedModel model
   ) {
      if (!stack.isEmpty()) {
         matrices.push();
         boolean bl2 = renderMode == ModelTransformation.Mode.GUI
            || renderMode == ModelTransformation.Mode.GROUND
            || renderMode == ModelTransformation.Mode.FIXED;
         if (stack.getItem() == Items.TRIDENT && bl2) {
            model = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident#inventory"));
         }

         model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
         matrices.translate(-0.5, -0.5, -0.5);
         if (!model.isBuiltin() && (stack.getItem() != Items.TRIDENT || bl2)) {
            boolean bl3;
            if (renderMode != ModelTransformation.Mode.GUI && !renderMode.isFirstPerson() && stack.getItem() instanceof BlockItem) {
               Block lv = ((BlockItem)stack.getItem()).getBlock();
               bl3 = !(lv instanceof TransparentBlock) && !(lv instanceof StainedGlassPaneBlock);
            } else {
               bl3 = true;
            }

            RenderLayer lv2 = RenderLayers.getItemLayer(stack, bl3);
            VertexConsumer lv4;
            if (stack.getItem() == Items.COMPASS && stack.hasGlint()) {
               matrices.push();
               MatrixStack.Entry lv3 = matrices.peek();
               if (renderMode == ModelTransformation.Mode.GUI) {
                  lv3.getModel().multiply(0.5F);
               } else if (renderMode.isFirstPerson()) {
                  lv3.getModel().multiply(0.75F);
               }

               if (bl3) {
                  lv4 = getDirectCompassGlintConsumer(vertexConsumers, lv2, lv3);
               } else {
                  lv4 = getCompassGlintConsumer(vertexConsumers, lv2, lv3);
               }

               matrices.pop();
            } else if (bl3) {
               lv4 = getDirectItemGlintConsumer(vertexConsumers, lv2, true, stack.hasGlint());
            } else {
               lv4 = getItemGlintConsumer(vertexConsumers, lv2, true, stack.hasGlint());
            }

            this.renderBakedItemModel(model, stack, light, overlay, matrices, lv4);
         } else {
            BuiltinModelItemRenderer.INSTANCE.render(stack, renderMode, matrices, vertexConsumers, light, overlay);
         }

         matrices.pop();
      }
   }

   public static VertexConsumer getArmorGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
      return glint
         ? VertexConsumers.dual(provider.getBuffer(solid ? RenderLayer.getArmorGlint() : RenderLayer.getArmorEntityGlint()), provider.getBuffer(layer))
         : provider.getBuffer(layer);
   }

   public static VertexConsumer getCompassGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
      return VertexConsumers.dual(
         new OverlayVertexConsumer(provider.getBuffer(RenderLayer.getGlint()), entry.getModel(), entry.getNormal()), provider.getBuffer(layer)
      );
   }

   public static VertexConsumer getDirectCompassGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
      return VertexConsumers.dual(
         new OverlayVertexConsumer(provider.getBuffer(RenderLayer.getDirectGlint()), entry.getModel(), entry.getNormal()), provider.getBuffer(layer)
      );
   }

   public static VertexConsumer getItemGlintConsumer(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint) {
      if (glint) {
         return MinecraftClient.isFabulousGraphicsOrBetter() && layer == TexturedRenderLayers.getItemEntityTranslucentCull()
            ? VertexConsumers.dual(vertexConsumers.getBuffer(RenderLayer.method_30676()), vertexConsumers.getBuffer(layer))
            : VertexConsumers.dual(vertexConsumers.getBuffer(solid ? RenderLayer.getGlint() : RenderLayer.getEntityGlint()), vertexConsumers.getBuffer(layer));
      } else {
         return vertexConsumers.getBuffer(layer);
      }
   }

   public static VertexConsumer getDirectItemGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
      return glint
         ? VertexConsumers.dual(provider.getBuffer(solid ? RenderLayer.getDirectGlint() : RenderLayer.getDirectEntityGlint()), provider.getBuffer(layer))
         : provider.getBuffer(layer);
   }

   private void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
      boolean bl = !stack.isEmpty();
      MatrixStack.Entry lv = matrices.peek();

      for (BakedQuad lv2 : quads) {
         int k = -1;
         if (bl && lv2.hasColor()) {
            k = this.colorMap.getColorMultiplier(stack, lv2.getColorIndex());
         }

         float f = (float)(k >> 16 & 0xFF) / 255.0F;
         float g = (float)(k >> 8 & 0xFF) / 255.0F;
         float h = (float)(k & 0xFF) / 255.0F;
         vertices.quad(lv, lv2, f, g, h, light, overlay);
      }
   }

   public BakedModel getHeldItemModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
      Item lv = stack.getItem();
      BakedModel lv2;
      if (lv == Items.TRIDENT) {
         lv2 = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident_in_hand#inventory"));
      } else {
         lv2 = this.models.getModel(stack);
      }

      ClientWorld lv4 = world instanceof ClientWorld ? (ClientWorld)world : null;
      BakedModel lv5 = lv2.getOverrides().apply(lv2, stack, lv4, entity);
      return lv5 == null ? this.models.getModelManager().getMissingModel() : lv5;
   }

   public void renderItem(
      ItemStack stack, ModelTransformation.Mode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers
   ) {
      this.renderItem(null, stack, transformationType, false, matrices, vertexConsumers, null, light, overlay);
   }

   public void renderItem(
      @Nullable LivingEntity entity,
      ItemStack item,
      ModelTransformation.Mode renderMode,
      boolean leftHanded,
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      @Nullable World world,
      int light,
      int overlay
   ) {
      if (!item.isEmpty()) {
         BakedModel lv = this.getHeldItemModel(item, world, entity);
         this.renderItem(item, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, lv);
      }
   }

   public void renderGuiItemIcon(ItemStack stack, int x, int y) {
      this.renderGuiItemModel(stack, x, y, this.getHeldItemModel(stack, null, null));
   }

   protected void renderGuiItemModel(ItemStack stack, int x, int y, BakedModel model) {
      RenderSystem.pushMatrix();
      this.textureManager.bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
      this.textureManager.getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false);
      RenderSystem.enableRescaleNormal();
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.translatef((float)x, (float)y, 100.0F + this.zOffset);
      RenderSystem.translatef(8.0F, 8.0F, 0.0F);
      RenderSystem.scalef(1.0F, -1.0F, 1.0F);
      RenderSystem.scalef(16.0F, 16.0F, 16.0F);
      MatrixStack lv = new MatrixStack();
      VertexConsumerProvider.Immediate lv2 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
      boolean bl = !model.isSideLit();
      if (bl) {
         DiffuseLighting.disableGuiDepthLighting();
      }

      this.renderItem(stack, ModelTransformation.Mode.GUI, false, lv, lv2, 15728880, OverlayTexture.DEFAULT_UV, model);
      lv2.draw();
      RenderSystem.enableDepthTest();
      if (bl) {
         DiffuseLighting.enableGuiDepthLighting();
      }

      RenderSystem.disableAlphaTest();
      RenderSystem.disableRescaleNormal();
      RenderSystem.popMatrix();
   }

   public void renderInGuiWithOverrides(ItemStack stack, int x, int y) {
      this.innerRenderInGui(MinecraftClient.getInstance().player, stack, x, y);
   }

   public void renderInGui(ItemStack stack, int x, int y) {
      this.innerRenderInGui(null, stack, x, y);
   }

   public void renderInGuiWithOverrides(LivingEntity entity, ItemStack stack, int x, int y) {
      this.innerRenderInGui(entity, stack, x, y);
   }

   private void innerRenderInGui(@Nullable LivingEntity entity, ItemStack itemStack, int x, int y) {
      if (!itemStack.isEmpty()) {
         this.zOffset += 50.0F;

         try {
            this.renderGuiItemModel(itemStack, x, y, this.getHeldItemModel(itemStack, null, entity));
         } catch (Throwable var8) {
            CrashReport lv = CrashReport.create(var8, "Rendering item");
            CrashReportSection lv2 = lv.addElement("Item being rendered");
            lv2.add("Item Type", () -> String.valueOf(itemStack.getItem()));
            lv2.add("Item Damage", () -> String.valueOf(itemStack.getDamage()));
            lv2.add("Item NBT", () -> String.valueOf(itemStack.getTag()));
            lv2.add("Item Foil", () -> String.valueOf(itemStack.hasGlint()));
            throw new CrashException(lv);
         }

         this.zOffset -= 50.0F;
      }
   }

   public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y) {
      this.renderGuiItemOverlay(renderer, stack, x, y, null);
   }

   public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, @Nullable String countLabel) {
      if (!stack.isEmpty()) {
         MatrixStack lv = new MatrixStack();
         if (stack.getCount() != 1 || countLabel != null) {
            String string2 = countLabel == null ? String.valueOf(stack.getCount()) : countLabel;
            lv.translate(0.0, 0.0, (double)(this.zOffset + 200.0F));
            VertexConsumerProvider.Immediate lv2 = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
            renderer.draw(
               string2, (float)(x + 19 - 2 - renderer.getWidth(string2)), (float)(y + 6 + 3), 16777215, true, lv.peek().getModel(), lv2, false, 0, 15728880
            );
            lv2.draw();
         }

         if (stack.isDamaged()) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
            Tessellator lv3 = Tessellator.getInstance();
            BufferBuilder lv4 = lv3.getBuffer();
            float f = (float)stack.getDamage();
            float g = (float)stack.getMaxDamage();
            float h = Math.max(0.0F, (g - f) / g);
            int k = Math.round(13.0F - f * 13.0F / g);
            int l = MathHelper.hsvToRgb(h / 3.0F, 1.0F, 1.0F);
            this.renderGuiQuad(lv4, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
            this.renderGuiQuad(lv4, x + 2, y + 13, k, 1, l >> 16 & 0xFF, l >> 8 & 0xFF, l & 0xFF, 255);
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }

         ClientPlayerEntity lv5 = MinecraftClient.getInstance().player;
         float m = lv5 == null ? 0.0F : lv5.getItemCooldownManager().getCooldownProgress(stack.getItem(), MinecraftClient.getInstance().getTickDelta());
         if (m > 0.0F) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Tessellator lv6 = Tessellator.getInstance();
            BufferBuilder lv7 = lv6.getBuffer();
            this.renderGuiQuad(lv7, x, y + MathHelper.floor(16.0F * (1.0F - m)), 16, MathHelper.ceil(16.0F * m), 255, 255, 255, 127);
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }
      }
   }

   private void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
      buffer.begin(7, VertexFormats.POSITION_COLOR);
      buffer.vertex((double)(x + 0), (double)(y + 0), 0.0).color(red, green, blue, alpha).next();
      buffer.vertex((double)(x + 0), (double)(y + height), 0.0).color(red, green, blue, alpha).next();
      buffer.vertex((double)(x + width), (double)(y + height), 0.0).color(red, green, blue, alpha).next();
      buffer.vertex((double)(x + width), (double)(y + 0), 0.0).color(red, green, blue, alpha).next();
      Tessellator.getInstance().draw();
   }

   @Override
   public void apply(ResourceManager manager) {
      this.models.reloadModels();
   }
}
