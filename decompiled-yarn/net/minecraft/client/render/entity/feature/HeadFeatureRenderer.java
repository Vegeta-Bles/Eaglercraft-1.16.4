package net.minecraft.client.render.entity.feature;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import org.apache.commons.lang3.StringUtils;

public class HeadFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
   private final float field_24474;
   private final float field_24475;
   private final float field_24476;

   public HeadFeatureRenderer(FeatureRendererContext<T, M> _snowman) {
      this(_snowman, 1.0F, 1.0F, 1.0F);
   }

   public HeadFeatureRenderer(FeatureRendererContext<T, M> _snowman, float _snowman, float _snowman, float _snowman) {
      super(_snowman);
      this.field_24474 = _snowman;
      this.field_24475 = _snowman;
      this.field_24476 = _snowman;
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      ItemStack _snowmanxxxxxxxxxx = _snowman.getEquippedStack(EquipmentSlot.HEAD);
      if (!_snowmanxxxxxxxxxx.isEmpty()) {
         Item _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getItem();
         _snowman.push();
         _snowman.scale(this.field_24474, this.field_24475, this.field_24476);
         boolean _snowmanxxxxxxxxxxxx = _snowman instanceof VillagerEntity || _snowman instanceof ZombieVillagerEntity;
         if (_snowman.isBaby() && !(_snowman instanceof VillagerEntity)) {
            float _snowmanxxxxxxxxxxxxx = 2.0F;
            float _snowmanxxxxxxxxxxxxxx = 1.4F;
            _snowman.translate(0.0, 0.03125, 0.0);
            _snowman.scale(0.7F, 0.7F, 0.7F);
            _snowman.translate(0.0, 1.0, 0.0);
         }

         this.getContextModel().getHead().rotate(_snowman);
         if (_snowmanxxxxxxxxxxx instanceof BlockItem && ((BlockItem)_snowmanxxxxxxxxxxx).getBlock() instanceof AbstractSkullBlock) {
            float _snowmanxxxxxxxxxxxxx = 1.1875F;
            _snowman.scale(1.1875F, -1.1875F, -1.1875F);
            if (_snowmanxxxxxxxxxxxx) {
               _snowman.translate(0.0, 0.0625, 0.0);
            }

            GameProfile _snowmanxxxxxxxxxxxxxx = null;
            if (_snowmanxxxxxxxxxx.hasTag()) {
               CompoundTag _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.getTag();
               if (_snowmanxxxxxxxxxxxxxxx.contains("SkullOwner", 10)) {
                  _snowmanxxxxxxxxxxxxxx = NbtHelper.toGameProfile(_snowmanxxxxxxxxxxxxxxx.getCompound("SkullOwner"));
               } else if (_snowmanxxxxxxxxxxxxxxx.contains("SkullOwner", 8)) {
                  String _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getString("SkullOwner");
                  if (!StringUtils.isBlank(_snowmanxxxxxxxxxxxxxxxx)) {
                     _snowmanxxxxxxxxxxxxxx = SkullBlockEntity.loadProperties(new GameProfile(null, _snowmanxxxxxxxxxxxxxxxx));
                     _snowmanxxxxxxxxxxxxxxx.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), _snowmanxxxxxxxxxxxxxx));
                  }
               }
            }

            _snowman.translate(-0.5, 0.0, -0.5);
            SkullBlockEntityRenderer.render(
               null, 180.0F, ((AbstractSkullBlock)((BlockItem)_snowmanxxxxxxxxxxx).getBlock()).getSkullType(), _snowmanxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman
            );
         } else if (!(_snowmanxxxxxxxxxxx instanceof ArmorItem) || ((ArmorItem)_snowmanxxxxxxxxxxx).getSlotType() != EquipmentSlot.HEAD) {
            float _snowmanxxxxxxxxxxxxxx = 0.625F;
            _snowman.translate(0.0, -0.25, 0.0);
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            _snowman.scale(0.625F, -0.625F, -0.625F);
            if (_snowmanxxxxxxxxxxxx) {
               _snowman.translate(0.0, 0.1875, 0.0);
            }

            MinecraftClient.getInstance().getHeldItemRenderer().renderItem(_snowman, _snowmanxxxxxxxxxx, ModelTransformation.Mode.HEAD, false, _snowman, _snowman, _snowman);
         }

         _snowman.pop();
      }
   }
}
