/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.item;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.font.TextRenderer;
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
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
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
import net.minecraft.entity.player.PlayerEntity;
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

public class ItemRenderer
implements SynchronousResourceReloadListener {
    public static final Identifier ENCHANTED_ITEM_GLINT = new Identifier("textures/misc/enchanted_item_glint.png");
    private static final Set<Item> WITHOUT_MODELS = Sets.newHashSet((Object[])new Item[]{Items.AIR});
    public float zOffset;
    private final ItemModels models;
    private final TextureManager textureManager;
    private final ItemColors colorMap;

    public ItemRenderer(TextureManager manager, BakedModelManager bakery, ItemColors colorMap) {
        this.textureManager = manager;
        this.models = new ItemModels(bakery);
        for (Item item : Registry.ITEM) {
            if (WITHOUT_MODELS.contains(item)) continue;
            this.models.putModel(item, new ModelIdentifier(Registry.ITEM.getId(item), "inventory"));
        }
        this.colorMap = colorMap;
    }

    public ItemModels getModels() {
        return this.models;
    }

    private void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {
        Random random = new Random();
        long _snowman2 = 42L;
        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, direction, random), stack, light, overlay);
        }
        random.setSeed(42L);
        this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), stack, light, overlay);
    }

    public void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {
        boolean bl;
        if (stack.isEmpty()) {
            return;
        }
        matrices.push();
        boolean bl2 = bl = renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;
        if (stack.getItem() == Items.TRIDENT && bl) {
            model = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident#inventory"));
        }
        model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
        matrices.translate(-0.5, -0.5, -0.5);
        if (model.isBuiltin() || stack.getItem() == Items.TRIDENT && !bl) {
            BuiltinModelItemRenderer.INSTANCE.render(stack, renderMode, matrices, vertexConsumers, light, overlay);
        } else {
            VertexConsumer _snowman2;
            _snowman = renderMode != ModelTransformation.Mode.GUI && !renderMode.isFirstPerson() && stack.getItem() instanceof BlockItem ? !((object = ((BlockItem)stack.getItem()).getBlock()) instanceof TransparentBlock) && !(object instanceof StainedGlassPaneBlock) : true;
            Object object = RenderLayers.getItemLayer(stack, _snowman);
            if (stack.getItem() == Items.COMPASS && stack.hasGlint()) {
                matrices.push();
                MatrixStack.Entry entry = matrices.peek();
                if (renderMode == ModelTransformation.Mode.GUI) {
                    entry.getModel().multiply(0.5f);
                } else if (renderMode.isFirstPerson()) {
                    entry.getModel().multiply(0.75f);
                }
                _snowman2 = _snowman ? ItemRenderer.getDirectCompassGlintConsumer(vertexConsumers, (RenderLayer)object, entry) : ItemRenderer.getCompassGlintConsumer(vertexConsumers, (RenderLayer)object, entry);
                matrices.pop();
            } else {
                _snowman2 = _snowman ? ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, (RenderLayer)object, true, stack.hasGlint()) : ItemRenderer.getItemGlintConsumer(vertexConsumers, (RenderLayer)object, true, stack.hasGlint());
            }
            this.renderBakedItemModel(model, stack, light, overlay, matrices, _snowman2);
        }
        matrices.pop();
    }

    public static VertexConsumer getArmorGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
        if (glint) {
            return VertexConsumers.dual(provider.getBuffer(solid ? RenderLayer.getArmorGlint() : RenderLayer.getArmorEntityGlint()), provider.getBuffer(layer));
        }
        return provider.getBuffer(layer);
    }

    public static VertexConsumer getCompassGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
        return VertexConsumers.dual(new OverlayVertexConsumer(provider.getBuffer(RenderLayer.getGlint()), entry.getModel(), entry.getNormal()), provider.getBuffer(layer));
    }

    public static VertexConsumer getDirectCompassGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry) {
        return VertexConsumers.dual(new OverlayVertexConsumer(provider.getBuffer(RenderLayer.getDirectGlint()), entry.getModel(), entry.getNormal()), provider.getBuffer(layer));
    }

    public static VertexConsumer getItemGlintConsumer(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint) {
        if (glint) {
            if (MinecraftClient.isFabulousGraphicsOrBetter() && layer == TexturedRenderLayers.getItemEntityTranslucentCull()) {
                return VertexConsumers.dual(vertexConsumers.getBuffer(RenderLayer.method_30676()), vertexConsumers.getBuffer(layer));
            }
            return VertexConsumers.dual(vertexConsumers.getBuffer(solid ? RenderLayer.getGlint() : RenderLayer.getEntityGlint()), vertexConsumers.getBuffer(layer));
        }
        return vertexConsumers.getBuffer(layer);
    }

    public static VertexConsumer getDirectItemGlintConsumer(VertexConsumerProvider provider, RenderLayer layer, boolean solid, boolean glint) {
        if (glint) {
            return VertexConsumers.dual(provider.getBuffer(solid ? RenderLayer.getDirectGlint() : RenderLayer.getDirectEntityGlint()), provider.getBuffer(layer));
        }
        return provider.getBuffer(layer);
    }

    private void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
        boolean bl = !stack.isEmpty();
        MatrixStack.Entry _snowman2 = matrices.peek();
        for (BakedQuad bakedQuad : quads) {
            int n = -1;
            if (bl && bakedQuad.hasColor()) {
                n = this.colorMap.getColorMultiplier(stack, bakedQuad.getColorIndex());
            }
            float _snowman3 = (float)(n >> 16 & 0xFF) / 255.0f;
            float _snowman4 = (float)(n >> 8 & 0xFF) / 255.0f;
            float _snowman5 = (float)(n & 0xFF) / 255.0f;
            vertices.quad(_snowman2, bakedQuad, _snowman3, _snowman4, _snowman5, light, overlay);
        }
    }

    public BakedModel getHeldItemModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
        Item item = stack.getItem();
        BakedModel _snowman2 = item == Items.TRIDENT ? this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident_in_hand#inventory")) : this.models.getModel(stack);
        ClientWorld _snowman3 = world instanceof ClientWorld ? (ClientWorld)world : null;
        BakedModel _snowman4 = _snowman2.getOverrides().apply(_snowman2, stack, _snowman3, entity);
        return _snowman4 == null ? this.models.getModelManager().getMissingModel() : _snowman4;
    }

    public void renderItem(ItemStack stack, ModelTransformation.Mode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        this.renderItem(null, stack, transformationType, false, matrices, vertexConsumers, null, light, overlay);
    }

    public void renderItem(@Nullable LivingEntity entity, ItemStack item, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, @Nullable World world, int light, int overlay) {
        if (item.isEmpty()) {
            return;
        }
        BakedModel bakedModel = this.getHeldItemModel(item, world, entity);
        this.renderItem(item, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, bakedModel);
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
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.translatef(x, y, 100.0f + this.zOffset);
        RenderSystem.translatef(8.0f, 8.0f, 0.0f);
        RenderSystem.scalef(1.0f, -1.0f, 1.0f);
        RenderSystem.scalef(16.0f, 16.0f, 16.0f);
        MatrixStack matrixStack = new MatrixStack();
        VertexConsumerProvider.Immediate _snowman2 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        boolean bl = _snowman = !model.isSideLit();
        if (_snowman) {
            DiffuseLighting.disableGuiDepthLighting();
        }
        this.renderItem(stack, ModelTransformation.Mode.GUI, false, matrixStack, _snowman2, 0xF000F0, OverlayTexture.DEFAULT_UV, model);
        _snowman2.draw();
        RenderSystem.enableDepthTest();
        if (_snowman) {
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
        if (itemStack.isEmpty()) {
            return;
        }
        this.zOffset += 50.0f;
        try {
            this.renderGuiItemModel(itemStack, x, y, this.getHeldItemModel(itemStack, null, entity));
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Rendering item");
            CrashReportSection _snowman2 = crashReport.addElement("Item being rendered");
            _snowman2.add("Item Type", () -> String.valueOf(itemStack.getItem()));
            _snowman2.add("Item Damage", () -> String.valueOf(itemStack.getDamage()));
            _snowman2.add("Item NBT", () -> String.valueOf(itemStack.getTag()));
            _snowman2.add("Item Foil", () -> String.valueOf(itemStack.hasGlint()));
            throw new CrashException(crashReport);
        }
        this.zOffset -= 50.0f;
    }

    public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y) {
        this.renderGuiItemOverlay(renderer, stack, x, y, null);
    }

    public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, @Nullable String countLabel) {
        Object object;
        if (stack.isEmpty()) {
            return;
        }
        MatrixStack matrixStack = new MatrixStack();
        if (stack.getCount() != 1 || countLabel != null) {
            object = countLabel == null ? String.valueOf(stack.getCount()) : countLabel;
            matrixStack.translate(0.0, 0.0, this.zOffset + 200.0f);
            _snowman = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
            renderer.draw((String)object, (float)(x + 19 - 2 - renderer.getWidth((String)object)), (float)(y + 6 + 3), 0xFFFFFF, true, matrixStack.peek().getModel(), (VertexConsumerProvider)_snowman, false, 0, 0xF000F0);
            ((VertexConsumerProvider.Immediate)_snowman).draw();
        }
        if (stack.isDamaged()) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
            object = Tessellator.getInstance();
            _snowman = ((Tessellator)object).getBuffer();
            float _snowman2 = stack.getDamage();
            float _snowman3 = stack.getMaxDamage();
            float _snowman4 = Math.max(0.0f, (_snowman3 - _snowman2) / _snowman3);
            int _snowman5 = Math.round(13.0f - _snowman2 * 13.0f / _snowman3);
            int _snowman6 = MathHelper.hsvToRgb(_snowman4 / 3.0f, 1.0f, 1.0f);
            this.renderGuiQuad((BufferBuilder)_snowman, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
            this.renderGuiQuad((BufferBuilder)_snowman, x + 2, y + 13, _snowman5, 1, _snowman6 >> 16 & 0xFF, _snowman6 >> 8 & 0xFF, _snowman6 & 0xFF, 255);
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
        }
        float f = _snowman = (object = MinecraftClient.getInstance().player) == null ? 0.0f : ((PlayerEntity)object).getItemCooldownManager().getCooldownProgress(stack.getItem(), MinecraftClient.getInstance().getTickDelta());
        if (_snowman > 0.0f) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Tessellator _snowman7 = Tessellator.getInstance();
            BufferBuilder _snowman8 = _snowman7.getBuffer();
            this.renderGuiQuad(_snowman8, x, y + MathHelper.floor(16.0f * (1.0f - _snowman)), 16, MathHelper.ceil(16.0f * _snowman), 255, 255, 255, 127);
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
        }
    }

    private void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        buffer.vertex(x + 0, y + 0, 0.0).color(red, green, blue, alpha).next();
        buffer.vertex(x + 0, y + height, 0.0).color(red, green, blue, alpha).next();
        buffer.vertex(x + width, y + height, 0.0).color(red, green, blue, alpha).next();
        buffer.vertex(x + width, y + 0, 0.0).color(red, green, blue, alpha).next();
        Tessellator.getInstance().draw();
    }

    @Override
    public void apply(ResourceManager manager) {
        this.models.reloadModels();
    }
}

