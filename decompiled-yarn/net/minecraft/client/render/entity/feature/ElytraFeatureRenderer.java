package net.minecraft.client.render.entity.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
   private static final Identifier SKIN = new Identifier("textures/entity/elytra.png");
   private final ElytraEntityModel<T> elytra = new ElytraEntityModel<>();

   public ElytraFeatureRenderer(FeatureRendererContext<T, M> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      ItemStack _snowmanxxxxxxxxxx = _snowman.getEquippedStack(EquipmentSlot.CHEST);
      if (_snowmanxxxxxxxxxx.getItem() == Items.ELYTRA) {
         Identifier _snowmanxxxxxxxxxxx;
         if (_snowman instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity _snowmanxxxxxxxxxxxx = (AbstractClientPlayerEntity)_snowman;
            if (_snowmanxxxxxxxxxxxx.canRenderElytraTexture() && _snowmanxxxxxxxxxxxx.getElytraTexture() != null) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getElytraTexture();
            } else if (_snowmanxxxxxxxxxxxx.canRenderCapeTexture() && _snowmanxxxxxxxxxxxx.getCapeTexture() != null && _snowmanxxxxxxxxxxxx.isPartVisible(PlayerModelPart.CAPE)) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getCapeTexture();
            } else {
               _snowmanxxxxxxxxxxx = SKIN;
            }
         } else {
            _snowmanxxxxxxxxxxx = SKIN;
         }

         _snowman.push();
         _snowman.translate(0.0, 0.0, 0.125);
         this.getContextModel().copyStateTo(this.elytra);
         this.elytra.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         VertexConsumer _snowmanxxxxxxxxxxxx = ItemRenderer.getArmorGlintConsumer(_snowman, RenderLayer.getArmorCutoutNoCull(_snowmanxxxxxxxxxxx), false, _snowmanxxxxxxxxxx.hasGlint());
         this.elytra.render(_snowman, _snowmanxxxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
         _snowman.pop();
      }
   }
}
