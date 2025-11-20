package net.minecraft.client.render.entity;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
   private static final Logger LOGGER = LogManager.getLogger();
   protected M model;
   protected final List<FeatureRenderer<T, M>> features = Lists.newArrayList();

   public LivingEntityRenderer(EntityRenderDispatcher dispatcher, M model, float shadowRadius) {
      super(dispatcher);
      this.model = model;
      this.shadowRadius = shadowRadius;
   }

   protected final boolean addFeature(FeatureRenderer<T, M> feature) {
      return this.features.add(feature);
   }

   @Override
   public M getModel() {
      return this.model;
   }

   public void render(T _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      this.model.handSwingProgress = this.getHandSwingProgress(_snowman, _snowman);
      this.model.riding = _snowman.hasVehicle();
      this.model.child = _snowman.isBaby();
      float _snowmanxxxxxx = MathHelper.lerpAngleDegrees(_snowman, _snowman.prevBodyYaw, _snowman.bodyYaw);
      float _snowmanxxxxxxx = MathHelper.lerpAngleDegrees(_snowman, _snowman.prevHeadYaw, _snowman.headYaw);
      float _snowmanxxxxxxxx = _snowmanxxxxxxx - _snowmanxxxxxx;
      if (_snowman.hasVehicle() && _snowman.getVehicle() instanceof LivingEntity) {
         LivingEntity _snowmanxxxxxxxxx = (LivingEntity)_snowman.getVehicle();
         _snowmanxxxxxx = MathHelper.lerpAngleDegrees(_snowman, _snowmanxxxxxxxxx.prevBodyYaw, _snowmanxxxxxxxxx.bodyYaw);
         _snowmanxxxxxxxx = _snowmanxxxxxxx - _snowmanxxxxxx;
         float _snowmanxxxxxxxxxx = MathHelper.wrapDegrees(_snowmanxxxxxxxx);
         if (_snowmanxxxxxxxxxx < -85.0F) {
            _snowmanxxxxxxxxxx = -85.0F;
         }

         if (_snowmanxxxxxxxxxx >= 85.0F) {
            _snowmanxxxxxxxxxx = 85.0F;
         }

         _snowmanxxxxxx = _snowmanxxxxxxx - _snowmanxxxxxxxxxx;
         if (_snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx > 2500.0F) {
            _snowmanxxxxxx += _snowmanxxxxxxxxxx * 0.2F;
         }

         _snowmanxxxxxxxx = _snowmanxxxxxxx - _snowmanxxxxxx;
      }

      float _snowmanxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
      if (_snowman.getPose() == EntityPose.SLEEPING) {
         Direction _snowmanxxxxxxxxxxxx = _snowman.getSleepingDirection();
         if (_snowmanxxxxxxxxxxxx != null) {
            float _snowmanxxxxxxxxxxxxx = _snowman.getEyeHeight(EntityPose.STANDING) - 0.1F;
            _snowman.translate((double)((float)(-_snowmanxxxxxxxxxxxx.getOffsetX()) * _snowmanxxxxxxxxxxxxx), 0.0, (double)((float)(-_snowmanxxxxxxxxxxxx.getOffsetZ()) * _snowmanxxxxxxxxxxxxx));
         }
      }

      float _snowmanxxxxxxxxxxxx = this.getAnimationProgress(_snowman, _snowman);
      this.setupTransforms(_snowman, _snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxxx, _snowman);
      _snowman.scale(-1.0F, -1.0F, 1.0F);
      this.scale(_snowman, _snowman, _snowman);
      _snowman.translate(0.0, -1.501F, 0.0);
      float _snowmanxxxxxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxxxxxx = 0.0F;
      if (!_snowman.hasVehicle() && _snowman.isAlive()) {
         _snowmanxxxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.lastLimbDistance, _snowman.limbDistance);
         _snowmanxxxxxxxxxxxxxx = _snowman.limbAngle - _snowman.limbDistance * (1.0F - _snowman);
         if (_snowman.isBaby()) {
            _snowmanxxxxxxxxxxxxxx *= 3.0F;
         }

         if (_snowmanxxxxxxxxxxxxx > 1.0F) {
            _snowmanxxxxxxxxxxxxx = 1.0F;
         }
      }

      this.model.animateModel(_snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowman);
      this.model.setAngles(_snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
      MinecraftClient _snowmanxxxxxxxxxxxxxxx = MinecraftClient.getInstance();
      boolean _snowmanxxxxxxxxxxxxxxxx = this.isVisible(_snowman);
      boolean _snowmanxxxxxxxxxxxxxxxxx = !_snowmanxxxxxxxxxxxxxxxx && !_snowman.isInvisibleTo(_snowmanxxxxxxxxxxxxxxx.player);
      boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.hasOutline(_snowman);
      RenderLayer _snowmanxxxxxxxxxxxxxxxxxxx = this.getRenderLayer(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
      if (_snowmanxxxxxxxxxxxxxxxxxxx != null) {
         VertexConsumer _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(_snowmanxxxxxxxxxxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxxxxxxxxx = getOverlay(_snowman, this.getAnimationCounter(_snowman, _snowman));
         this.model.render(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxx, 1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxx ? 0.15F : 1.0F);
      }

      if (!_snowman.isSpectator()) {
         for (FeatureRenderer<T, M> _snowmanxxxxxxxxxxxxxxxxxxxx : this.features) {
            _snowmanxxxxxxxxxxxxxxxxxxxx.render(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
         }
      }

      _snowman.pop();
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   protected RenderLayer getRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline) {
      Identifier _snowman = this.getTexture(entity);
      if (translucent) {
         return RenderLayer.getItemEntityTranslucentCull(_snowman);
      } else if (showBody) {
         return this.model.getLayer(_snowman);
      } else {
         return showOutline ? RenderLayer.getOutline(_snowman) : null;
      }
   }

   public static int getOverlay(LivingEntity entity, float whiteOverlayProgress) {
      return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(entity.hurtTime > 0 || entity.deathTime > 0));
   }

   protected boolean isVisible(T entity) {
      return !entity.isInvisible();
   }

   private static float getYaw(Direction direction) {
      switch (direction) {
         case SOUTH:
            return 90.0F;
         case WEST:
            return 0.0F;
         case NORTH:
            return 270.0F;
         case EAST:
            return 180.0F;
         default:
            return 0.0F;
      }
   }

   protected boolean isShaking(T entity) {
      return false;
   }

   protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
      if (this.isShaking(entity)) {
         bodyYaw += (float)(Math.cos((double)entity.age * 3.25) * Math.PI * 0.4F);
      }

      EntityPose _snowman = entity.getPose();
      if (_snowman != EntityPose.SLEEPING) {
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - bodyYaw));
      }

      if (entity.deathTime > 0) {
         float _snowmanx = ((float)entity.deathTime + tickDelta - 1.0F) / 20.0F * 1.6F;
         _snowmanx = MathHelper.sqrt(_snowmanx);
         if (_snowmanx > 1.0F) {
            _snowmanx = 1.0F;
         }

         matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowmanx * this.getLyingAngle(entity)));
      } else if (entity.isUsingRiptide()) {
         matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F - entity.pitch));
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(((float)entity.age + tickDelta) * -75.0F));
      } else if (_snowman == EntityPose.SLEEPING) {
         Direction _snowmanx = entity.getSleepingDirection();
         float _snowmanxx = _snowmanx != null ? getYaw(_snowmanx) : bodyYaw;
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxx));
         matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(this.getLyingAngle(entity)));
         matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270.0F));
      } else if (entity.hasCustomName() || entity instanceof PlayerEntity) {
         String _snowmanx = Formatting.strip(entity.getName().getString());
         if (("Dinnerbone".equals(_snowmanx) || "Grumm".equals(_snowmanx))
            && (!(entity instanceof PlayerEntity) || ((PlayerEntity)entity).isPartVisible(PlayerModelPart.CAPE))) {
            matrices.translate(0.0, (double)(entity.getHeight() + 0.1F), 0.0);
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
         }
      }
   }

   protected float getHandSwingProgress(T entity, float tickDelta) {
      return entity.getHandSwingProgress(tickDelta);
   }

   protected float getAnimationProgress(T entity, float tickDelta) {
      return (float)entity.age + tickDelta;
   }

   protected float getLyingAngle(T entity) {
      return 90.0F;
   }

   protected float getAnimationCounter(T entity, float tickDelta) {
      return 0.0F;
   }

   protected void scale(T entity, MatrixStack matrices, float amount) {
   }

   protected boolean hasLabel(T _snowman) {
      double _snowmanx = this.dispatcher.getSquaredDistanceToCamera(_snowman);
      float _snowmanxx = _snowman.isSneaky() ? 32.0F : 64.0F;
      if (_snowmanx >= (double)(_snowmanxx * _snowmanxx)) {
         return false;
      } else {
         MinecraftClient _snowmanxxx = MinecraftClient.getInstance();
         ClientPlayerEntity _snowmanxxxx = _snowmanxxx.player;
         boolean _snowmanxxxxx = !_snowman.isInvisibleTo(_snowmanxxxx);
         if (_snowman != _snowmanxxxx) {
            AbstractTeam _snowmanxxxxxx = _snowman.getScoreboardTeam();
            AbstractTeam _snowmanxxxxxxx = _snowmanxxxx.getScoreboardTeam();
            if (_snowmanxxxxxx != null) {
               AbstractTeam.VisibilityRule _snowmanxxxxxxxx = _snowmanxxxxxx.getNameTagVisibilityRule();
               switch (_snowmanxxxxxxxx) {
                  case ALWAYS:
                     return _snowmanxxxxx;
                  case NEVER:
                     return false;
                  case HIDE_FOR_OTHER_TEAMS:
                     return _snowmanxxxxxxx == null ? _snowmanxxxxx : _snowmanxxxxxx.isEqual(_snowmanxxxxxxx) && (_snowmanxxxxxx.shouldShowFriendlyInvisibles() || _snowmanxxxxx);
                  case HIDE_FOR_OWN_TEAM:
                     return _snowmanxxxxxxx == null ? _snowmanxxxxx : !_snowmanxxxxxx.isEqual(_snowmanxxxxxxx) && _snowmanxxxxx;
                  default:
                     return true;
               }
            }
         }

         return MinecraftClient.isHudEnabled() && _snowman != _snowmanxxx.getCameraEntity() && _snowmanxxxxx && !_snowman.hasPassengers();
      }
   }
}
