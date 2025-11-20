package net.minecraft.client.particle;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.collection.ReusableStream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class Particle {
   private static final Box EMPTY_BOUNDING_BOX = new Box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   protected final ClientWorld world;
   protected double prevPosX;
   protected double prevPosY;
   protected double prevPosZ;
   protected double x;
   protected double y;
   protected double z;
   protected double velocityX;
   protected double velocityY;
   protected double velocityZ;
   private Box boundingBox = EMPTY_BOUNDING_BOX;
   protected boolean onGround;
   protected boolean collidesWithWorld = true;
   private boolean field_21507;
   protected boolean dead;
   protected float spacingXZ = 0.6F;
   protected float spacingY = 1.8F;
   protected final Random random = new Random();
   protected int age;
   protected int maxAge;
   protected float gravityStrength;
   protected float colorRed = 1.0F;
   protected float colorGreen = 1.0F;
   protected float colorBlue = 1.0F;
   protected float colorAlpha = 1.0F;
   protected float angle;
   protected float prevAngle;

   protected Particle(ClientWorld world, double x, double y, double z) {
      this.world = world;
      this.setBoundingBoxSpacing(0.2F, 0.2F);
      this.setPos(x, y, z);
      this.prevPosX = x;
      this.prevPosY = y;
      this.prevPosZ = z;
      this.maxAge = (int)(4.0F / (this.random.nextFloat() * 0.9F + 0.1F));
   }

   public Particle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      this(world, x, y, z);
      this.velocityX = velocityX + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.velocityY = velocityY + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.velocityZ = velocityZ + (Math.random() * 2.0 - 1.0) * 0.4F;
      float _snowman = (float)(Math.random() + Math.random() + 1.0) * 0.15F;
      float _snowmanx = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
      this.velocityX = this.velocityX / (double)_snowmanx * (double)_snowman * 0.4F;
      this.velocityY = this.velocityY / (double)_snowmanx * (double)_snowman * 0.4F + 0.1F;
      this.velocityZ = this.velocityZ / (double)_snowmanx * (double)_snowman * 0.4F;
   }

   public Particle move(float speed) {
      this.velocityX *= (double)speed;
      this.velocityY = (this.velocityY - 0.1F) * (double)speed + 0.1F;
      this.velocityZ *= (double)speed;
      return this;
   }

   public Particle scale(float scale) {
      this.setBoundingBoxSpacing(0.2F * scale, 0.2F * scale);
      return this;
   }

   public void setColor(float red, float green, float blue) {
      this.colorRed = red;
      this.colorGreen = green;
      this.colorBlue = blue;
   }

   protected void setColorAlpha(float alpha) {
      this.colorAlpha = alpha;
   }

   public void setMaxAge(int maxAge) {
      this.maxAge = maxAge;
   }

   public int getMaxAge() {
      return this.maxAge;
   }

   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         this.velocityY = this.velocityY - 0.04 * (double)this.gravityStrength;
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityX *= 0.98F;
         this.velocityY *= 0.98F;
         this.velocityZ *= 0.98F;
         if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }
      }
   }

   public abstract void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta);

   public abstract ParticleTextureSheet getType();

   @Override
   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + this.x
         + ","
         + this.y
         + ","
         + this.z
         + "), RGBA ("
         + this.colorRed
         + ","
         + this.colorGreen
         + ","
         + this.colorBlue
         + ","
         + this.colorAlpha
         + "), Age "
         + this.age;
   }

   public void markDead() {
      this.dead = true;
   }

   protected void setBoundingBoxSpacing(float spacingXZ, float spacingY) {
      if (spacingXZ != this.spacingXZ || spacingY != this.spacingY) {
         this.spacingXZ = spacingXZ;
         this.spacingY = spacingY;
         Box _snowman = this.getBoundingBox();
         double _snowmanx = (_snowman.minX + _snowman.maxX - (double)spacingXZ) / 2.0;
         double _snowmanxx = (_snowman.minZ + _snowman.maxZ - (double)spacingXZ) / 2.0;
         this.setBoundingBox(new Box(_snowmanx, _snowman.minY, _snowmanxx, _snowmanx + (double)this.spacingXZ, _snowman.minY + (double)this.spacingY, _snowmanxx + (double)this.spacingXZ));
      }
   }

   public void setPos(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
      float _snowman = this.spacingXZ / 2.0F;
      float _snowmanx = this.spacingY;
      this.setBoundingBox(new Box(x - (double)_snowman, y, z - (double)_snowman, x + (double)_snowman, y + (double)_snowmanx, z + (double)_snowman));
   }

   public void move(double dx, double dy, double dz) {
      if (!this.field_21507) {
         double _snowman = dx;
         double _snowmanx = dy;
         double _snowmanxx = dz;
         if (this.collidesWithWorld && (dx != 0.0 || dy != 0.0 || dz != 0.0)) {
            Vec3d _snowmanxxx = Entity.adjustMovementForCollisions(
               null, new Vec3d(dx, dy, dz), this.getBoundingBox(), this.world, ShapeContext.absent(), new ReusableStream<>(Stream.empty())
            );
            dx = _snowmanxxx.x;
            dy = _snowmanxxx.y;
            dz = _snowmanxxx.z;
         }

         if (dx != 0.0 || dy != 0.0 || dz != 0.0) {
            this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
            this.repositionFromBoundingBox();
         }

         if (Math.abs(_snowmanx) >= 1.0E-5F && Math.abs(dy) < 1.0E-5F) {
            this.field_21507 = true;
         }

         this.onGround = _snowmanx != dy && _snowmanx < 0.0;
         if (_snowman != dx) {
            this.velocityX = 0.0;
         }

         if (_snowmanxx != dz) {
            this.velocityZ = 0.0;
         }
      }
   }

   protected void repositionFromBoundingBox() {
      Box _snowman = this.getBoundingBox();
      this.x = (_snowman.minX + _snowman.maxX) / 2.0;
      this.y = _snowman.minY;
      this.z = (_snowman.minZ + _snowman.maxZ) / 2.0;
   }

   protected int getColorMultiplier(float tint) {
      BlockPos _snowman = new BlockPos(this.x, this.y, this.z);
      return this.world.isChunkLoaded(_snowman) ? WorldRenderer.getLightmapCoordinates(this.world, _snowman) : 0;
   }

   public boolean isAlive() {
      return !this.dead;
   }

   public Box getBoundingBox() {
      return this.boundingBox;
   }

   public void setBoundingBox(Box _snowman) {
      this.boundingBox = _snowman;
   }
}
