package net.minecraft.client.render.entity.feature;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
   private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();
   private final A leggingsModel;
   private final A bodyModel;

   public ArmorFeatureRenderer(FeatureRendererContext<T, M> context, A leggingsModel, A bodyModel) {
      super(context);
      this.leggingsModel = leggingsModel;
      this.bodyModel = bodyModel;
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.renderArmor(_snowman, _snowman, _snowman, EquipmentSlot.CHEST, _snowman, this.getArmor(EquipmentSlot.CHEST));
      this.renderArmor(_snowman, _snowman, _snowman, EquipmentSlot.LEGS, _snowman, this.getArmor(EquipmentSlot.LEGS));
      this.renderArmor(_snowman, _snowman, _snowman, EquipmentSlot.FEET, _snowman, this.getArmor(EquipmentSlot.FEET));
      this.renderArmor(_snowman, _snowman, _snowman, EquipmentSlot.HEAD, _snowman, this.getArmor(EquipmentSlot.HEAD));
   }

   private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T _snowman, EquipmentSlot _snowman, int _snowman, A _snowman) {
      ItemStack _snowmanxxxx = _snowman.getEquippedStack(_snowman);
      if (_snowmanxxxx.getItem() instanceof ArmorItem) {
         ArmorItem _snowmanxxxxx = (ArmorItem)_snowmanxxxx.getItem();
         if (_snowmanxxxxx.getSlotType() == _snowman) {
            this.getContextModel().setAttributes(_snowman);
            this.setVisible(_snowman, _snowman);
            boolean _snowmanxxxxxx = this.usesSecondLayer(_snowman);
            boolean _snowmanxxxxxxx = _snowmanxxxx.hasGlint();
            if (_snowmanxxxxx instanceof DyeableArmorItem) {
               int _snowmanxxxxxxxx = ((DyeableArmorItem)_snowmanxxxxx).getColor(_snowmanxxxx);
               float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxxxx >> 16 & 0xFF) / 255.0F;
               float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxxxx >> 8 & 0xFF) / 255.0F;
               float _snowmanxxxxxxxxxxx = (float)(_snowmanxxxxxxxx & 0xFF) / 255.0F;
               this.renderArmorParts(matrices, vertexConsumers, _snowman, _snowmanxxxxx, _snowmanxxxxxxx, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, null);
               this.renderArmorParts(matrices, vertexConsumers, _snowman, _snowmanxxxxx, _snowmanxxxxxxx, _snowman, _snowmanxxxxxx, 1.0F, 1.0F, 1.0F, "overlay");
            } else {
               this.renderArmorParts(matrices, vertexConsumers, _snowman, _snowmanxxxxx, _snowmanxxxxxxx, _snowman, _snowmanxxxxxx, 1.0F, 1.0F, 1.0F, null);
            }
         }
      }
   }

   protected void setVisible(A bipedModel, EquipmentSlot slot) {
      bipedModel.setVisible(false);
      switch (slot) {
         case HEAD:
            bipedModel.head.visible = true;
            bipedModel.helmet.visible = true;
            break;
         case CHEST:
            bipedModel.torso.visible = true;
            bipedModel.rightArm.visible = true;
            bipedModel.leftArm.visible = true;
            break;
         case LEGS:
            bipedModel.torso.visible = true;
            bipedModel.rightLeg.visible = true;
            bipedModel.leftLeg.visible = true;
            break;
         case FEET:
            bipedModel.rightLeg.visible = true;
            bipedModel.leftLeg.visible = true;
      }
   }

   private void renderArmorParts(
      MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, ArmorItem _snowman, boolean _snowman, A _snowman, boolean _snowman, float _snowman, float _snowman, float _snowman, @Nullable String _snowman
   ) {
      VertexConsumer _snowmanxxxxxxxxxxx = ItemRenderer.getArmorGlintConsumer(_snowman, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(_snowman, _snowman, _snowman)), false, _snowman);
      _snowman.render(_snowman, _snowmanxxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, _snowman, _snowman, _snowman, 1.0F);
   }

   private A getArmor(EquipmentSlot slot) {
      return this.usesSecondLayer(slot) ? this.leggingsModel : this.bodyModel;
   }

   private boolean usesSecondLayer(EquipmentSlot slot) {
      return slot == EquipmentSlot.LEGS;
   }

   private Identifier getArmorTexture(ArmorItem _snowman, boolean _snowman, @Nullable String _snowman) {
      String _snowmanxxx = "textures/models/armor/" + _snowman.getMaterial().getName() + "_layer_" + (_snowman ? 2 : 1) + (_snowman == null ? "" : "_" + _snowman) + ".png";
      return ARMOR_TEXTURE_CACHE.computeIfAbsent(_snowmanxxx, Identifier::new);
   }
}
