package net.minecraft.client.render.item;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      Item lv = stack.getItem();
      if (lv instanceof BlockItem) {
         Block lv2 = ((BlockItem)lv).getBlock();
         if (lv2 instanceof AbstractSkullBlock) {
            GameProfile gameProfile = null;
            if (stack.hasTag()) {
               CompoundTag lv3 = stack.getTag();
               if (lv3.contains("SkullOwner", 10)) {
                  gameProfile = NbtHelper.toGameProfile(lv3.getCompound("SkullOwner"));
               } else if (lv3.contains("SkullOwner", 8) && !StringUtils.isBlank(lv3.getString("SkullOwner"))) {
                  GameProfile var16 = new GameProfile(null, lv3.getString("SkullOwner"));
                  gameProfile = SkullBlockEntity.loadProperties(var16);
                  lv3.remove("SkullOwner");
                  lv3.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), gameProfile));
               }
            }

            SkullBlockEntityRenderer.render(null, 180.0F, ((AbstractSkullBlock)lv2).getSkullType(), gameProfile, 0.0F, matrices, vertexConsumers, light);
         } else {
            BlockEntity lv4;
            if (lv2 instanceof AbstractBannerBlock) {
               this.renderBanner.readFrom(stack, ((AbstractBannerBlock)lv2).getColor());
               lv4 = this.renderBanner;
            } else if (lv2 instanceof BedBlock) {
               this.renderBed.setColor(((BedBlock)lv2).getColor());
               lv4 = this.renderBed;
            } else if (lv2 == Blocks.CONDUIT) {
               lv4 = this.renderConduit;
            } else if (lv2 == Blocks.CHEST) {
               lv4 = this.renderChestNormal;
            } else if (lv2 == Blocks.ENDER_CHEST) {
               lv4 = this.renderChestEnder;
            } else if (lv2 == Blocks.TRAPPED_CHEST) {
               lv4 = this.renderChestTrapped;
            } else {
               if (!(lv2 instanceof ShulkerBoxBlock)) {
                  return;
               }

               DyeColor lv10 = ShulkerBoxBlock.getColor(lv);
               if (lv10 == null) {
                  lv4 = RENDER_SHULKER_BOX;
               } else {
                  lv4 = RENDER_SHULKER_BOX_DYED[lv10.getId()];
               }
            }

            BlockEntityRenderDispatcher.INSTANCE.renderEntity(lv4, matrices, vertexConsumers, light, overlay);
         }
      } else {
         if (lv == Items.SHIELD) {
            boolean bl = stack.getSubTag("BlockEntityTag") != null;
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            SpriteIdentifier lv14 = bl ? ModelLoader.SHIELD_BASE : ModelLoader.SHIELD_BASE_NO_PATTERN;
            VertexConsumer lv15 = lv14.getSprite()
               .getTextureSpecificVertexConsumer(
                  ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.modelShield.getLayer(lv14.getAtlasId()), true, stack.hasGlint())
               );
            this.modelShield.getHandle().render(matrices, lv15, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            if (bl) {
               List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.method_24280(ShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack));
               BannerBlockEntityRenderer.renderCanvas(
                  matrices, vertexConsumers, light, overlay, this.modelShield.getPlate(), lv14, false, list, stack.hasGlint()
               );
            } else {
               this.modelShield.getPlate().render(matrices, lv15, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            matrices.pop();
         } else if (lv == Items.TRIDENT) {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer lv16 = ItemRenderer.getDirectItemGlintConsumer(
               vertexConsumers, this.modelTrident.getLayer(TridentEntityModel.TEXTURE), false, stack.hasGlint()
            );
            this.modelTrident.render(matrices, lv16, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
         }
      }
   }
}
