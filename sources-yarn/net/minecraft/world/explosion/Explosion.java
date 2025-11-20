package net.minecraft.world.explosion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class Explosion {
   private static final ExplosionBehavior field_25818 = new ExplosionBehavior();
   private final boolean createFire;
   private final Explosion.DestructionType destructionType;
   private final Random random = new Random();
   private final World world;
   private final double x;
   private final double y;
   private final double z;
   @Nullable
   private final Entity entity;
   private final float power;
   private final DamageSource damageSource;
   private final ExplosionBehavior behavior;
   private final List<BlockPos> affectedBlocks = Lists.newArrayList();
   private final Map<PlayerEntity, Vec3d> affectedPlayers = Maps.newHashMap();

   @Environment(EnvType.CLIENT)
   public Explosion(World world, @Nullable Entity entity, double x, double y, double z, float power, List<BlockPos> affectedBlocks) {
      this(world, entity, x, y, z, power, false, Explosion.DestructionType.DESTROY, affectedBlocks);
   }

   @Environment(EnvType.CLIENT)
   public Explosion(
      World world,
      @Nullable Entity entity,
      double x,
      double y,
      double z,
      float power,
      boolean createFire,
      Explosion.DestructionType destructionType,
      List<BlockPos> affectedBlocks
   ) {
      this(world, entity, x, y, z, power, createFire, destructionType);
      this.affectedBlocks.addAll(affectedBlocks);
   }

   @Environment(EnvType.CLIENT)
   public Explosion(World arg, @Nullable Entity arg2, double d, double e, double f, float g, boolean bl, Explosion.DestructionType arg3) {
      this(arg, arg2, null, null, d, e, f, g, bl, arg3);
   }

   public Explosion(
      World world,
      @Nullable Entity entity,
      @Nullable DamageSource arg3,
      @Nullable ExplosionBehavior arg4,
      double d,
      double e,
      double f,
      float g,
      boolean bl,
      Explosion.DestructionType arg5
   ) {
      this.world = world;
      this.entity = entity;
      this.power = g;
      this.x = d;
      this.y = e;
      this.z = f;
      this.createFire = bl;
      this.destructionType = arg5;
      this.damageSource = arg3 == null ? DamageSource.explosion(this) : arg3;
      this.behavior = arg4 == null ? this.chooseBehavior(entity) : arg4;
   }

   private ExplosionBehavior chooseBehavior(@Nullable Entity entity) {
      return (ExplosionBehavior)(entity == null ? field_25818 : new EntityExplosionBehavior(entity));
   }

   public static float getExposure(Vec3d source, Entity entity) {
      Box lv = entity.getBoundingBox();
      double d = 1.0 / ((lv.maxX - lv.minX) * 2.0 + 1.0);
      double e = 1.0 / ((lv.maxY - lv.minY) * 2.0 + 1.0);
      double f = 1.0 / ((lv.maxZ - lv.minZ) * 2.0 + 1.0);
      double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
      double h = (1.0 - Math.floor(1.0 / f) * f) / 2.0;
      if (!(d < 0.0) && !(e < 0.0) && !(f < 0.0)) {
         int i = 0;
         int j = 0;

         for (float k = 0.0F; k <= 1.0F; k = (float)((double)k + d)) {
            for (float l = 0.0F; l <= 1.0F; l = (float)((double)l + e)) {
               for (float m = 0.0F; m <= 1.0F; m = (float)((double)m + f)) {
                  double n = MathHelper.lerp((double)k, lv.minX, lv.maxX);
                  double o = MathHelper.lerp((double)l, lv.minY, lv.maxY);
                  double p = MathHelper.lerp((double)m, lv.minZ, lv.maxZ);
                  Vec3d lv2 = new Vec3d(n + g, o, p + h);
                  if (entity.world
                        .raycast(new RaycastContext(lv2, source, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity))
                        .getType()
                     == HitResult.Type.MISS) {
                     i++;
                  }

                  j++;
               }
            }
         }

         return (float)i / (float)j;
      } else {
         return 0.0F;
      }
   }

   public void collectBlocksAndDamageEntities() {
      Set<BlockPos> set = Sets.newHashSet();
      int i = 16;

      for (int j = 0; j < 16; j++) {
         for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
               if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                  double d = (double)((float)j / 15.0F * 2.0F - 1.0F);
                  double e = (double)((float)k / 15.0F * 2.0F - 1.0F);
                  double f = (double)((float)l / 15.0F * 2.0F - 1.0F);
                  double g = Math.sqrt(d * d + e * e + f * f);
                  d /= g;
                  e /= g;
                  f /= g;
                  float h = this.power * (0.7F + this.world.random.nextFloat() * 0.6F);
                  double m = this.x;
                  double n = this.y;
                  double o = this.z;

                  for (float p = 0.3F; h > 0.0F; h -= 0.22500001F) {
                     BlockPos lv = new BlockPos(m, n, o);
                     BlockState lv2 = this.world.getBlockState(lv);
                     FluidState lv3 = this.world.getFluidState(lv);
                     Optional<Float> optional = this.behavior.getBlastResistance(this, this.world, lv, lv2, lv3);
                     if (optional.isPresent()) {
                        h -= (optional.get() + 0.3F) * 0.3F;
                     }

                     if (h > 0.0F && this.behavior.canDestroyBlock(this, this.world, lv, lv2, h)) {
                        set.add(lv);
                     }

                     m += d * 0.3F;
                     n += e * 0.3F;
                     o += f * 0.3F;
                  }
               }
            }
         }
      }

      this.affectedBlocks.addAll(set);
      float q = this.power * 2.0F;
      int r = MathHelper.floor(this.x - (double)q - 1.0);
      int s = MathHelper.floor(this.x + (double)q + 1.0);
      int t = MathHelper.floor(this.y - (double)q - 1.0);
      int u = MathHelper.floor(this.y + (double)q + 1.0);
      int v = MathHelper.floor(this.z - (double)q - 1.0);
      int w = MathHelper.floor(this.z + (double)q + 1.0);
      List<Entity> list = this.world.getOtherEntities(this.entity, new Box((double)r, (double)t, (double)v, (double)s, (double)u, (double)w));
      Vec3d lv4 = new Vec3d(this.x, this.y, this.z);

      for (int x = 0; x < list.size(); x++) {
         Entity lv5 = list.get(x);
         if (!lv5.isImmuneToExplosion()) {
            double y = (double)(MathHelper.sqrt(lv5.squaredDistanceTo(lv4)) / q);
            if (y <= 1.0) {
               double z = lv5.getX() - this.x;
               double aa = (lv5 instanceof TntEntity ? lv5.getY() : lv5.getEyeY()) - this.y;
               double ab = lv5.getZ() - this.z;
               double ac = (double)MathHelper.sqrt(z * z + aa * aa + ab * ab);
               if (ac != 0.0) {
                  z /= ac;
                  aa /= ac;
                  ab /= ac;
                  double ad = (double)getExposure(lv4, lv5);
                  double ae = (1.0 - y) * ad;
                  lv5.damage(this.getDamageSource(), (float)((int)((ae * ae + ae) / 2.0 * 7.0 * (double)q + 1.0)));
                  double af = ae;
                  if (lv5 instanceof LivingEntity) {
                     af = ProtectionEnchantment.transformExplosionKnockback((LivingEntity)lv5, ae);
                  }

                  lv5.setVelocity(lv5.getVelocity().add(z * af, aa * af, ab * af));
                  if (lv5 instanceof PlayerEntity) {
                     PlayerEntity lv6 = (PlayerEntity)lv5;
                     if (!lv6.isSpectator() && (!lv6.isCreative() || !lv6.abilities.flying)) {
                        this.affectedPlayers.put(lv6, new Vec3d(z * ae, aa * ae, ab * ae));
                     }
                  }
               }
            }
         }
      }
   }

   public void affectWorld(boolean bl) {
      if (this.world.isClient) {
         this.world
            .playSound(
               this.x,
               this.y,
               this.z,
               SoundEvents.ENTITY_GENERIC_EXPLODE,
               SoundCategory.BLOCKS,
               4.0F,
               (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F,
               false
            );
      }

      boolean bl2 = this.destructionType != Explosion.DestructionType.NONE;
      if (bl) {
         if (!(this.power < 2.0F) && bl2) {
            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         } else {
            this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         }
      }

      if (bl2) {
         ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList = new ObjectArrayList();
         Collections.shuffle(this.affectedBlocks, this.world.random);

         for (BlockPos lv : this.affectedBlocks) {
            BlockState lv2 = this.world.getBlockState(lv);
            Block lv3 = lv2.getBlock();
            if (!lv2.isAir()) {
               BlockPos lv4 = lv.toImmutable();
               this.world.getProfiler().push("explosion_blocks");
               if (lv3.shouldDropItemsOnExplosion(this) && this.world instanceof ServerWorld) {
                  BlockEntity lv5 = lv3.hasBlockEntity() ? this.world.getBlockEntity(lv) : null;
                  LootContext.Builder lv6 = new LootContext.Builder((ServerWorld)this.world)
                     .random(this.world.random)
                     .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(lv))
                     .parameter(LootContextParameters.TOOL, ItemStack.EMPTY)
                     .optionalParameter(LootContextParameters.BLOCK_ENTITY, lv5)
                     .optionalParameter(LootContextParameters.THIS_ENTITY, this.entity);
                  if (this.destructionType == Explosion.DestructionType.DESTROY) {
                     lv6.parameter(LootContextParameters.EXPLOSION_RADIUS, this.power);
                  }

                  lv2.getDroppedStacks(lv6).forEach(arg2 -> method_24023(objectArrayList, arg2, lv4));
               }

               this.world.setBlockState(lv, Blocks.AIR.getDefaultState(), 3);
               lv3.onDestroyedByExplosion(this.world, lv, this);
               this.world.getProfiler().pop();
            }
         }

         ObjectListIterator var12 = objectArrayList.iterator();

         while (var12.hasNext()) {
            Pair<ItemStack, BlockPos> pair = (Pair<ItemStack, BlockPos>)var12.next();
            Block.dropStack(this.world, (BlockPos)pair.getSecond(), (ItemStack)pair.getFirst());
         }
      }

      if (this.createFire) {
         for (BlockPos lv7 : this.affectedBlocks) {
            if (this.random.nextInt(3) == 0
               && this.world.getBlockState(lv7).isAir()
               && this.world.getBlockState(lv7.down()).isOpaqueFullCube(this.world, lv7.down())) {
               this.world.setBlockState(lv7, AbstractFireBlock.getState(this.world, lv7));
            }
         }
      }
   }

   private static void method_24023(ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList, ItemStack arg, BlockPos arg2) {
      int i = objectArrayList.size();

      for (int j = 0; j < i; j++) {
         Pair<ItemStack, BlockPos> pair = (Pair<ItemStack, BlockPos>)objectArrayList.get(j);
         ItemStack lv = (ItemStack)pair.getFirst();
         if (ItemEntity.canMerge(lv, arg)) {
            ItemStack lv2 = ItemEntity.merge(lv, arg, 16);
            objectArrayList.set(j, Pair.of(lv2, pair.getSecond()));
            if (arg.isEmpty()) {
               return;
            }
         }
      }

      objectArrayList.add(Pair.of(arg, arg2));
   }

   public DamageSource getDamageSource() {
      return this.damageSource;
   }

   public Map<PlayerEntity, Vec3d> getAffectedPlayers() {
      return this.affectedPlayers;
   }

   @Nullable
   public LivingEntity getCausingEntity() {
      if (this.entity == null) {
         return null;
      } else if (this.entity instanceof TntEntity) {
         return ((TntEntity)this.entity).getCausingEntity();
      } else if (this.entity instanceof LivingEntity) {
         return (LivingEntity)this.entity;
      } else {
         if (this.entity instanceof ProjectileEntity) {
            Entity lv = ((ProjectileEntity)this.entity).getOwner();
            if (lv instanceof LivingEntity) {
               return (LivingEntity)lv;
            }
         }

         return null;
      }
   }

   public void clearAffectedBlocks() {
      this.affectedBlocks.clear();
   }

   public List<BlockPos> getAffectedBlocks() {
      return this.affectedBlocks;
   }

   public static enum DestructionType {
      NONE,
      BREAK,
      DESTROY;

      private DestructionType() {
      }
   }
}
