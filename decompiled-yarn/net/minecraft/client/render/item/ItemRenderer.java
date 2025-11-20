package net.minecraft.client.render.item;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
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

      for (Item _snowman : Registry.ITEM) {
         if (!WITHOUT_MODELS.contains(_snowman)) {
            this.models.putModel(_snowman, new ModelIdentifier(Registry.ITEM.getId(_snowman), "inventory"));
         }
      }

      this.colorMap = colorMap;
   }

   public ItemModels getModels() {
      return this.models;
   }

   private void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {
      Random _snowman = new Random();
      long _snowmanx = 42L;

      for (Direction _snowmanxx : Direction.values()) {
         _snowman.setSeed(42L);
         this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, _snowmanxx, _snowman), stack, light, overlay);
      }

      _snowman.setSeed(42L);
      this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, _snowman), stack, light, overlay);
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
         boolean _snowman = renderMode == ModelTransformation.Mode.GUI
            || renderMode == ModelTransformation.Mode.GROUND
            || renderMode == ModelTransformation.Mode.FIXED;
         if (stack.getItem() == Items.TRIDENT && _snowman) {
            model = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident#inventory"));
         }

         model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
         matrices.translate(-0.5, -0.5, -0.5);
         if (!model.isBuiltin() && (stack.getItem() != Items.TRIDENT || _snowman)) {
            boolean _snowmanx;
            if (renderMode != ModelTransformation.Mode.GUI && !renderMode.isFirstPerson() && stack.getItem() instanceof BlockItem) {
               Block _snowmanxx = ((BlockItem)stack.getItem()).getBlock();
               _snowmanx = !(_snowmanxx instanceof TransparentBlock) && !(_snowmanxx instanceof StainedGlassPaneBlock);
            } else {
               _snowmanx = true;
            }

            RenderLayer _snowmanxx = RenderLayers.getItemLayer(stack, _snowmanx);
            VertexConsumer _snowmanxxx;
            if (stack.getItem() == Items.COMPASS && stack.hasGlint()) {
               matrices.push();
               MatrixStack.Entry _snowmanxxxx = matrices.peek();
               if (renderMode == ModelTransformation.Mode.GUI) {
                  _snowmanxxxx.getModel().multiply(0.5F);
               } else if (renderMode.isFirstPerson()) {
                  _snowmanxxxx.getModel().multiply(0.75F);
               }

               if (_snowmanx) {
                  _snowmanxxx = getDirectCompassGlintConsumer(vertexConsumers, _snowmanxx, _snowmanxxxx);
               } else {
                  _snowmanxxx = getCompassGlintConsumer(vertexConsumers, _snowmanxx, _snowmanxxxx);
               }

               matrices.pop();
            } else if (_snowmanx) {
               _snowmanxxx = getDirectItemGlintConsumer(vertexConsumers, _snowmanxx, true, stack.hasGlint());
            } else {
               _snowmanxxx = getItemGlintConsumer(vertexConsumers, _snowmanxx, true, stack.hasGlint());
            }

            this.renderBakedItemModel(model, stack, light, overlay, matrices, _snowmanxxx);
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
      boolean _snowman = !stack.isEmpty();
      MatrixStack.Entry _snowmanx = matrices.peek();

      for (BakedQuad _snowmanxx : quads) {
         int _snowmanxxx = -1;
         if (_snowman && _snowmanxx.hasColor()) {
            _snowmanxxx = this.colorMap.getColorMultiplier(stack, _snowmanxx.getColorIndex());
         }

         float _snowmanxxxx = (float)(_snowmanxxx >> 16 & 0xFF) / 255.0F;
         float _snowmanxxxxx = (float)(_snowmanxxx >> 8 & 0xFF) / 255.0F;
         float _snowmanxxxxxx = (float)(_snowmanxxx & 0xFF) / 255.0F;
         vertices.quad(_snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, light, overlay);
      }
   }

   public BakedModel getHeldItemModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
      Item _snowman = stack.getItem();
      BakedModel _snowmanx;
      if (_snowman == Items.TRIDENT) {
         _snowmanx = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident_in_hand#inventory"));
      } else {
         _snowmanx = this.models.getModel(stack);
      }

      ClientWorld _snowmanxx = world instanceof ClientWorld ? (ClientWorld)world : null;
      BakedModel _snowmanxxx = _snowmanx.getOverrides().apply(_snowmanx, stack, _snowmanxx, entity);
      return _snowmanxxx == null ? this.models.getModelManager().getMissingModel() : _snowmanxxx;
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
         BakedModel _snowman = this.getHeldItemModel(item, world, entity);
         this.renderItem(item, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, _snowman);
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
      MatrixStack _snowman = new MatrixStack();
      VertexConsumerProvider.Immediate _snowmanx = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
      boolean _snowmanxx = !model.isSideLit();
      if (_snowmanxx) {
         DiffuseLighting.disableGuiDepthLighting();
      }

      this.renderItem(stack, ModelTransformation.Mode.GUI, false, _snowman, _snowmanx, 15728880, OverlayTexture.DEFAULT_UV, model);
      _snowmanx.draw();
      RenderSystem.enableDepthTest();
      if (_snowmanxx) {
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
            CrashReport _snowman = CrashReport.create(var8, "Rendering item");
            CrashReportSection _snowmanx = _snowman.addElement("Item being rendered");
            _snowmanx.add("Item Type", () -> String.valueOf(itemStack.getItem()));
            _snowmanx.add("Item Damage", () -> String.valueOf(itemStack.getDamage()));
            _snowmanx.add("Item NBT", () -> String.valueOf(itemStack.getTag()));
            _snowmanx.add("Item Foil", () -> String.valueOf(itemStack.hasGlint()));
            throw new CrashException(_snowman);
         }

         this.zOffset -= 50.0F;
      }
   }

   public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y) {
      this.renderGuiItemOverlay(renderer, stack, x, y, null);
   }

   public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, @Nullable String countLabel) {
      if (!stack.isEmpty()) {
         MatrixStack _snowman = new MatrixStack();
         if (stack.getCount() != 1 || countLabel != null) {
            String _snowmanx = countLabel == null ? String.valueOf(stack.getCount()) : countLabel;
            _snowman.translate(0.0, 0.0, (double)(this.zOffset + 200.0F));
            VertexConsumerProvider.Immediate _snowmanxx = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
            renderer.draw(_snowmanx, (float)(x + 19 - 2 - renderer.getWidth(_snowmanx)), (float)(y + 6 + 3), 16777215, true, _snowman.peek().getModel(), _snowmanxx, false, 0, 15728880);
            _snowmanxx.draw();
         }

         if (stack.isDamaged()) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
            Tessellator _snowmanx = Tessellator.getInstance();
            BufferBuilder _snowmanxx = _snowmanx.getBuffer();
            float _snowmanxxx = (float)stack.getDamage();
            float _snowmanxxxx = (float)stack.getMaxDamage();
            float _snowmanxxxxx = Math.max(0.0F, (_snowmanxxxx - _snowmanxxx) / _snowmanxxxx);
            int _snowmanxxxxxx = Math.round(13.0F - _snowmanxxx * 13.0F / _snowmanxxxx);
            int _snowmanxxxxxxx = MathHelper.hsvToRgb(_snowmanxxxxx / 3.0F, 1.0F, 1.0F);
            this.renderGuiQuad(_snowmanxx, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
            this.renderGuiQuad(_snowmanxx, x + 2, y + 13, _snowmanxxxxxx, 1, _snowmanxxxxxxx >> 16 & 0xFF, _snowmanxxxxxxx >> 8 & 0xFF, _snowmanxxxxxxx & 0xFF, 255);
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }

         ClientPlayerEntity _snowmanx = MinecraftClient.getInstance().player;
         float _snowmanxx = _snowmanx == null ? 0.0F : _snowmanx.getItemCooldownManager().getCooldownProgress(stack.getItem(), MinecraftClient.getInstance().getTickDelta());
         if (_snowmanxx > 0.0F) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Tessellator _snowmanxxx = Tessellator.getInstance();
            BufferBuilder _snowmanxxxx = _snowmanxxx.getBuffer();
            this.renderGuiQuad(_snowmanxxxx, x, y + MathHelper.floor(16.0F * (1.0F - _snowmanxx)), 16, MathHelper.ceil(16.0F * _snowmanxx), 255, 255, 255, 127);
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
