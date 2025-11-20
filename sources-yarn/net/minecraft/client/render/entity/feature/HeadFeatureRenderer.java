package net.minecraft.client.render.entity.feature;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class HeadFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
   private final float field_24474;
   private final float field_24475;
   private final float field_24476;

   public HeadFeatureRenderer(FeatureRendererContext<T, M> arg) {
      this(arg, 1.0F, 1.0F, 1.0F);
   }

   public HeadFeatureRenderer(FeatureRendererContext<T, M> arg, float f, float g, float h) {
      super(arg);
      this.field_24474 = f;
      this.field_24475 = g;
      this.field_24476 = h;
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      ItemStack lv = arg3.getEquippedStack(EquipmentSlot.HEAD);
      if (!lv.isEmpty()) {
         Item lv2 = lv.getItem();
         arg.push();
         arg.scale(this.field_24474, this.field_24475, this.field_24476);
         boolean bl = arg3 instanceof VillagerEntity || arg3 instanceof ZombieVillagerEntity;
         if (arg3.isBaby() && !(arg3 instanceof VillagerEntity)) {
            float m = 2.0F;
            float n = 1.4F;
            arg.translate(0.0, 0.03125, 0.0);
            arg.scale(0.7F, 0.7F, 0.7F);
            arg.translate(0.0, 1.0, 0.0);
         }

         this.getContextModel().getHead().rotate(arg);
         if (lv2 instanceof BlockItem && ((BlockItem)lv2).getBlock() instanceof AbstractSkullBlock) {
            float o = 1.1875F;
            arg.scale(1.1875F, -1.1875F, -1.1875F);
            if (bl) {
               arg.translate(0.0, 0.0625, 0.0);
            }

            GameProfile gameProfile = null;
            if (lv.hasTag()) {
               CompoundTag lv3 = lv.getTag();
               if (lv3.contains("SkullOwner", 10)) {
                  gameProfile = NbtHelper.toGameProfile(lv3.getCompound("SkullOwner"));
               } else if (lv3.contains("SkullOwner", 8)) {
                  String string = lv3.getString("SkullOwner");
                  if (!StringUtils.isBlank(string)) {
                     gameProfile = SkullBlockEntity.loadProperties(new GameProfile(null, string));
                     lv3.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), gameProfile));
                  }
               }
            }

            arg.translate(-0.5, 0.0, -0.5);
            SkullBlockEntityRenderer.render(null, 180.0F, ((AbstractSkullBlock)((BlockItem)lv2).getBlock()).getSkullType(), gameProfile, f, arg, arg2, i);
         } else if (!(lv2 instanceof ArmorItem) || ((ArmorItem)lv2).getSlotType() != EquipmentSlot.HEAD) {
            float p = 0.625F;
            arg.translate(0.0, -0.25, 0.0);
            arg.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            arg.scale(0.625F, -0.625F, -0.625F);
            if (bl) {
               arg.translate(0.0, 0.1875, 0.0);
            }

            MinecraftClient.getInstance().getHeldItemRenderer().renderItem(arg3, lv, ModelTransformation.Mode.HEAD, false, arg, arg2, i);
         }

         arg.pop();
      }
   }
}
