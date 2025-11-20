package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class HorseEntityModel<T extends HorseBaseEntity> extends AnimalModel<T> {
   protected final ModelPart torso;
   protected final ModelPart head;
   private final ModelPart leftBackLeg;
   private final ModelPart rightBackLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart field_20930;
   private final ModelPart field_20931;
   private final ModelPart field_20932;
   private final ModelPart field_20933;
   private final ModelPart tail;
   private final ModelPart[] field_3304;
   private final ModelPart[] field_3301;

   public HorseEntityModel(float scale) {
      super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.torso = new ModelPart(this, 0, 32);
      this.torso.addCuboid(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, 0.05F);
      this.torso.setPivot(0.0F, 11.0F, 5.0F);
      this.head = new ModelPart(this, 0, 35);
      this.head.addCuboid(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F);
      this.head.pitch = (float) (Math.PI / 6);
      ModelPart lv = new ModelPart(this, 0, 13);
      lv.addCuboid(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, scale);
      ModelPart lv2 = new ModelPart(this, 56, 36);
      lv2.addCuboid(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, scale);
      ModelPart lv3 = new ModelPart(this, 0, 25);
      lv3.addCuboid(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, scale);
      this.head.addChild(lv);
      this.head.addChild(lv2);
      this.head.addChild(lv3);
      this.method_2789(this.head);
      this.leftBackLeg = new ModelPart(this, 48, 21);
      this.leftBackLeg.mirror = true;
      this.leftBackLeg.addCuboid(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale);
      this.leftBackLeg.setPivot(4.0F, 14.0F, 7.0F);
      this.rightBackLeg = new ModelPart(this, 48, 21);
      this.rightBackLeg.addCuboid(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale);
      this.rightBackLeg.setPivot(-4.0F, 14.0F, 7.0F);
      this.leftFrontLeg = new ModelPart(this, 48, 21);
      this.leftFrontLeg.mirror = true;
      this.leftFrontLeg.addCuboid(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale);
      this.leftFrontLeg.setPivot(4.0F, 6.0F, -12.0F);
      this.rightFrontLeg = new ModelPart(this, 48, 21);
      this.rightFrontLeg.addCuboid(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale);
      this.rightFrontLeg.setPivot(-4.0F, 6.0F, -12.0F);
      float g = 5.5F;
      this.field_20930 = new ModelPart(this, 48, 21);
      this.field_20930.mirror = true;
      this.field_20930.addCuboid(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20930.setPivot(4.0F, 14.0F, 7.0F);
      this.field_20931 = new ModelPart(this, 48, 21);
      this.field_20931.addCuboid(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20931.setPivot(-4.0F, 14.0F, 7.0F);
      this.field_20932 = new ModelPart(this, 48, 21);
      this.field_20932.mirror = true;
      this.field_20932.addCuboid(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20932.setPivot(4.0F, 6.0F, -12.0F);
      this.field_20933 = new ModelPart(this, 48, 21);
      this.field_20933.addCuboid(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20933.setPivot(-4.0F, 6.0F, -12.0F);
      this.tail = new ModelPart(this, 42, 36);
      this.tail.addCuboid(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, scale);
      this.tail.setPivot(0.0F, -5.0F, 2.0F);
      this.tail.pitch = (float) (Math.PI / 6);
      this.torso.addChild(this.tail);
      ModelPart lv4 = new ModelPart(this, 26, 0);
      lv4.addCuboid(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, 0.5F);
      this.torso.addChild(lv4);
      ModelPart lv5 = new ModelPart(this, 29, 5);
      lv5.addCuboid(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, scale);
      this.head.addChild(lv5);
      ModelPart lv6 = new ModelPart(this, 29, 5);
      lv6.addCuboid(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, scale);
      this.head.addChild(lv6);
      ModelPart lv7 = new ModelPart(this, 32, 2);
      lv7.addCuboid(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, scale);
      lv7.pitch = (float) (-Math.PI / 6);
      this.head.addChild(lv7);
      ModelPart lv8 = new ModelPart(this, 32, 2);
      lv8.addCuboid(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, scale);
      lv8.pitch = (float) (-Math.PI / 6);
      this.head.addChild(lv8);
      ModelPart lv9 = new ModelPart(this, 1, 1);
      lv9.addCuboid(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, 0.2F);
      this.head.addChild(lv9);
      ModelPart lv10 = new ModelPart(this, 19, 0);
      lv10.addCuboid(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, 0.2F);
      this.head.addChild(lv10);
      this.field_3304 = new ModelPart[]{lv4, lv5, lv6, lv9, lv10};
      this.field_3301 = new ModelPart[]{lv7, lv8};
   }

   protected void method_2789(ModelPart arg) {
      ModelPart lv = new ModelPart(this, 19, 16);
      lv.addCuboid(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
      ModelPart lv2 = new ModelPart(this, 19, 16);
      lv2.addCuboid(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
      arg.addChild(lv);
      arg.addChild(lv2);
   }

   public void setAngles(T arg, float f, float g, float h, float i, float j) {
      boolean bl = arg.isSaddled();
      boolean bl2 = arg.hasPassengers();

      for (ModelPart lv : this.field_3304) {
         lv.visible = bl;
      }

      for (ModelPart lv2 : this.field_3301) {
         lv2.visible = bl2 && bl;
      }

      this.torso.pivotY = 11.0F;
   }

   @Override
   public Iterable<ModelPart> getHeadParts() {
      return ImmutableList.of(this.head);
   }

   @Override
   protected Iterable<ModelPart> getBodyParts() {
      return ImmutableList.of(
         this.torso,
         this.leftBackLeg,
         this.rightBackLeg,
         this.leftFrontLeg,
         this.rightFrontLeg,
         this.field_20930,
         this.field_20931,
         this.field_20932,
         this.field_20933
      );
   }

   public void animateModel(T arg, float f, float g, float h) {
      super.animateModel(arg, f, g, h);
      float i = MathHelper.lerpAngle(arg.prevBodyYaw, arg.bodyYaw, h);
      float j = MathHelper.lerpAngle(arg.prevHeadYaw, arg.headYaw, h);
      float k = MathHelper.lerp(h, arg.prevPitch, arg.pitch);
      float l = j - i;
      float m = k * (float) (Math.PI / 180.0);
      if (l > 20.0F) {
         l = 20.0F;
      }

      if (l < -20.0F) {
         l = -20.0F;
      }

      if (g > 0.2F) {
         m += MathHelper.cos(f * 0.4F) * 0.15F * g;
      }

      float n = arg.getEatingGrassAnimationProgress(h);
      float o = arg.getAngryAnimationProgress(h);
      float p = 1.0F - o;
      float q = arg.getEatingAnimationProgress(h);
      boolean bl = arg.tailWagTicks != 0;
      float r = (float)arg.age + h;
      this.head.pivotY = 4.0F;
      this.head.pivotZ = -12.0F;
      this.torso.pitch = 0.0F;
      this.head.pitch = (float) (Math.PI / 6) + m;
      this.head.yaw = l * (float) (Math.PI / 180.0);
      float s = arg.isTouchingWater() ? 0.2F : 1.0F;
      float t = MathHelper.cos(s * f * 0.6662F + (float) Math.PI);
      float u = t * 0.8F * g;
      float v = (1.0F - Math.max(o, n)) * ((float) (Math.PI / 6) + m + q * MathHelper.sin(r) * 0.05F);
      this.head.pitch = o * ((float) (Math.PI / 12) + m) + n * (2.1816616F + MathHelper.sin(r) * 0.05F) + v;
      this.head.yaw = o * l * (float) (Math.PI / 180.0) + (1.0F - Math.max(o, n)) * this.head.yaw;
      this.head.pivotY = o * -4.0F + n * 11.0F + (1.0F - Math.max(o, n)) * this.head.pivotY;
      this.head.pivotZ = o * -4.0F + n * -12.0F + (1.0F - Math.max(o, n)) * this.head.pivotZ;
      this.torso.pitch = o * (float) (-Math.PI / 4) + p * this.torso.pitch;
      float w = (float) (Math.PI / 12) * o;
      float x = MathHelper.cos(r * 0.6F + (float) Math.PI);
      this.leftFrontLeg.pivotY = 2.0F * o + 14.0F * p;
      this.leftFrontLeg.pivotZ = -6.0F * o - 10.0F * p;
      this.rightFrontLeg.pivotY = this.leftFrontLeg.pivotY;
      this.rightFrontLeg.pivotZ = this.leftFrontLeg.pivotZ;
      float y = ((float) (-Math.PI / 3) + x) * o + u * p;
      float z = ((float) (-Math.PI / 3) - x) * o - u * p;
      this.leftBackLeg.pitch = w - t * 0.5F * g * p;
      this.rightBackLeg.pitch = w + t * 0.5F * g * p;
      this.leftFrontLeg.pitch = y;
      this.rightFrontLeg.pitch = z;
      this.tail.pitch = (float) (Math.PI / 6) + g * 0.75F;
      this.tail.pivotY = -5.0F + g;
      this.tail.pivotZ = 2.0F + g * 2.0F;
      if (bl) {
         this.tail.yaw = MathHelper.cos(r * 0.7F);
      } else {
         this.tail.yaw = 0.0F;
      }

      this.field_20930.pivotY = this.leftBackLeg.pivotY;
      this.field_20930.pivotZ = this.leftBackLeg.pivotZ;
      this.field_20930.pitch = this.leftBackLeg.pitch;
      this.field_20931.pivotY = this.rightBackLeg.pivotY;
      this.field_20931.pivotZ = this.rightBackLeg.pivotZ;
      this.field_20931.pitch = this.rightBackLeg.pitch;
      this.field_20932.pivotY = this.leftFrontLeg.pivotY;
      this.field_20932.pivotZ = this.leftFrontLeg.pivotZ;
      this.field_20932.pitch = this.leftFrontLeg.pitch;
      this.field_20933.pivotY = this.rightFrontLeg.pivotY;
      this.field_20933.pivotZ = this.rightFrontLeg.pivotZ;
      this.field_20933.pitch = this.rightFrontLeg.pitch;
      boolean bl2 = arg.isBaby();
      this.leftBackLeg.visible = !bl2;
      this.rightBackLeg.visible = !bl2;
      this.leftFrontLeg.visible = !bl2;
      this.rightFrontLeg.visible = !bl2;
      this.field_20930.visible = bl2;
      this.field_20931.visible = bl2;
      this.field_20932.visible = bl2;
      this.field_20933.visible = bl2;
      this.torso.pivotY = bl2 ? 10.8F : 0.0F;
   }
}
