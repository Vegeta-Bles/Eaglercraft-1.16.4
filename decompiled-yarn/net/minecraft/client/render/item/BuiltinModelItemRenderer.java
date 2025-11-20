package net.minecraft.client.render.item;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.DyeColor;
import org.apache.commons.lang3.StringUtils;

public class BuiltinModelItemRenderer {
   private static final ShulkerBoxBlockEntity[] RENDER_SHULKER_BOX_DYED = Arrays.stream(DyeColor.values())
      .sorted(Comparator.comparingInt(DyeColor::getId))
      .map(ShulkerBoxBlockEntity::new)
      .toArray(ShulkerBoxBlockEntity[]::new);
   private static final ShulkerBoxBlockEntity RENDER_SHULKER_BOX = new ShulkerBoxBlockEntity(null);
   public static final BuiltinModelItemRenderer INSTANCE = new BuiltinModelItemRenderer();
   private final ChestBlockEntity renderChestNormal = new ChestBlockEntity();
   private final ChestBlockEntity renderChestTrapped = new TrappedChestBlockEntity();
   private final EnderChestBlockEntity renderChestEnder = new EnderChestBlockEntity();
   private final BannerBlockEntity renderBanner = new BannerBlockEntity();
   private final BedBlockEntity renderBed = new BedBlockEntity();
   private final ConduitBlockEntity renderConduit = new ConduitBlockEntity();
   private final ShieldEntityModel modelShield = new ShieldEntityModel();
   private final TridentEntityModel modelTrident = new TridentEntityModel();

   public BuiltinModelItemRenderer() {
   }

   public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
      Item _snowman = stack.getItem();
      if (_snowman instanceof BlockItem) {
         Block _snowmanx = ((BlockItem)_snowman).getBlock();
         if (_snowmanx instanceof AbstractSkullBlock) {
            GameProfile _snowmanxx = null;
            if (stack.hasTag()) {
               CompoundTag _snowmanxxx = stack.getTag();
               if (_snowmanxxx.contains("SkullOwner", 10)) {
                  _snowmanxx = NbtHelper.toGameProfile(_snowmanxxx.getCompound("SkullOwner"));
               } else if (_snowmanxxx.contains("SkullOwner", 8) && !StringUtils.isBlank(_snowmanxxx.getString("SkullOwner"))) {
                  GameProfile var16 = new GameProfile(null, _snowmanxxx.getString("SkullOwner"));
                  _snowmanxx = SkullBlockEntity.loadProperties(var16);
                  _snowmanxxx.remove("SkullOwner");
                  _snowmanxxx.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), _snowmanxx));
               }
            }

            SkullBlockEntityRenderer.render(null, 180.0F, ((AbstractSkullBlock)_snowmanx).getSkullType(), _snowmanxx, 0.0F, matrices, vertexConsumers, light);
         } else {
            BlockEntity _snowmanxx;
            if (_snowmanx instanceof AbstractBannerBlock) {
               this.renderBanner.readFrom(stack, ((AbstractBannerBlock)_snowmanx).getColor());
               _snowmanxx = this.renderBanner;
            } else if (_snowmanx instanceof BedBlock) {
               this.renderBed.setColor(((BedBlock)_snowmanx).getColor());
               _snowmanxx = this.renderBed;
            } else if (_snowmanx == Blocks.CONDUIT) {
               _snowmanxx = this.renderConduit;
            } else if (_snowmanx == Blocks.CHEST) {
               _snowmanxx = this.renderChestNormal;
            } else if (_snowmanx == Blocks.ENDER_CHEST) {
               _snowmanxx = this.renderChestEnder;
            } else if (_snowmanx == Blocks.TRAPPED_CHEST) {
               _snowmanxx = this.renderChestTrapped;
            } else {
               if (!(_snowmanx instanceof ShulkerBoxBlock)) {
                  return;
               }

               DyeColor _snowmanxxx = ShulkerBoxBlock.getColor(_snowman);
               if (_snowmanxxx == null) {
                  _snowmanxx = RENDER_SHULKER_BOX;
               } else {
                  _snowmanxx = RENDER_SHULKER_BOX_DYED[_snowmanxxx.getId()];
               }
            }

            BlockEntityRenderDispatcher.INSTANCE.renderEntity(_snowmanxx, matrices, vertexConsumers, light, overlay);
         }
      } else {
         if (_snowman == Items.SHIELD) {
            boolean _snowmanx = stack.getSubTag("BlockEntityTag") != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            SpriteIdentifier _snowmanxx = _snowmanx ? ModelLoader.SHIELD_BASE : ModelLoader.SHIELD_BASE_NO_PATTERN;
            VertexConsumer _snowmanxxx = _snowmanxx.getSprite()
               .getTextureSpecificVertexConsumer(
                  ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.modelShield.getLayer(_snowmanxx.getAtlasId()), true, stack.hasGlint())
               );
            this.modelShield.getHandle().render(matrices, _snowmanxxx, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            if (_snowmanx) {
               List<Pair<BannerPattern, DyeColor>> _snowmanxxxx = BannerBlockEntity.method_24280(
                  ShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack)
               );
               BannerBlockEntityRenderer.renderCanvas(
                  matrices, vertexConsumers, light, overlay, this.modelShield.getPlate(), _snowmanxx, false, _snowmanxxxx, stack.hasGlint()
               );
            } else {
               this.modelShield.getPlate().render(matrices, _snowmanxxx, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            matrices.pop();
         } else if (_snowman == Items.TRIDENT) {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer _snowmanx = ItemRenderer.getDirectItemGlintConsumer(
               vertexConsumers, this.modelTrident.getLayer(TridentEntityModel.TEXTURE), false, stack.hasGlint()
            );
            this.modelTrident.render(matrices, _snowmanx, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
         }
      }
   }
}
