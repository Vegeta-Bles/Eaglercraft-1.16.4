package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class OtherClientPlayerEntity extends AbstractClientPlayerEntity {
   public OtherClientPlayerEntity(ClientWorld _snowman, GameProfile _snowman) {
      super(_snowman, _snowman);
      this.stepHeight = 1.0F;
      this.noClip = true;
   }

   @Override
   public boolean shouldRender(double distance) {
      double _snowman = this.getBoundingBox().getAverageSideLength() * 10.0;
      if (Double.isNaN(_snowman)) {
         _snowman = 1.0;
      }

      _snowman *= 64.0 * getRenderDistanceMultiplier();
      return distance < _snowman * _snowman;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      return true;
   }

   @Override
   public void tick() {
      super.tick();
      this.method_29242(this, false);
   }

   @Override
   public void tickMovement() {
      if (this.bodyTrackingIncrements > 0) {
         double _snowman = this.getX() + (this.serverX - this.getX()) / (double)this.bodyTrackingIncrements;
         double _snowmanx = this.getY() + (this.serverY - this.getY()) / (double)this.bodyTrackingIncrements;
         double _snowmanxx = this.getZ() + (this.serverZ - this.getZ()) / (double)this.bodyTrackingIncrements;
         this.yaw = (float)((double)this.yaw + MathHelper.wrapDegrees(this.serverYaw - (double)this.yaw) / (double)this.bodyTrackingIncrements);
         this.pitch = (float)((double)this.pitch + (this.serverPitch - (double)this.pitch) / (double)this.bodyTrackingIncrements);
         this.bodyTrackingIncrements--;
         this.updatePosition(_snowman, _snowmanx, _snowmanxx);
         this.setRotation(this.yaw, this.pitch);
      }

      if (this.headTrackingIncrements > 0) {
         this.headYaw = (float)((double)this.headYaw + MathHelper.wrapDegrees(this.serverHeadYaw - (double)this.headYaw) / (double)this.headTrackingIncrements);
         this.headTrackingIncrements--;
      }

      this.prevStrideDistance = this.strideDistance;
      this.tickHandSwing();
      float _snowman;
      if (this.onGround && !this.isDead()) {
         _snowman = Math.min(0.1F, MathHelper.sqrt(squaredHorizontalLength(this.getVelocity())));
      } else {
         _snowman = 0.0F;
      }

      if (!this.onGround && !this.isDead()) {
         float _snowmanx = (float)Math.atan(-this.getVelocity().y * 0.2F) * 15.0F;
      } else {
         float _snowmanx = 0.0F;
      }

      this.strideDistance = this.strideDistance + (_snowman - this.strideDistance) * 0.4F;
      this.world.getProfiler().push("push");
      this.tickCramming();
      this.world.getProfiler().pop();
   }

   @Override
   protected void updateSize() {
   }

   @Override
   public void sendSystemMessage(Text message, UUID senderUuid) {
      MinecraftClient _snowman = MinecraftClient.getInstance();
      if (!_snowman.shouldBlockMessages(senderUuid)) {
         _snowman.inGameHud.getChatHud().addMessage(message);
      }
   }
}
