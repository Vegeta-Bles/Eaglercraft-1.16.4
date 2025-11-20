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

   public Explosion(World world, @Nullable Entity entity, double x, double y, double z, float power, List<BlockPos> affectedBlocks) {
      this(world, entity, x, y, z, power, false, Explosion.DestructionType.DESTROY, affectedBlocks);
   }

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

   public Explosion(World _snowman, @Nullable Entity _snowman, double _snowman, double _snowman, double _snowman, float _snowman, boolean _snowman, Explosion.DestructionType _snowman) {
      this(_snowman, _snowman, null, null, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Explosion(
      World world,
      @Nullable Entity entity,
      @Nullable DamageSource _snowman,
      @Nullable ExplosionBehavior _snowman,
      double _snowman,
      double _snowman,
      double _snowman,
      float _snowman,
      boolean _snowman,
      Explosion.DestructionType _snowman
   ) {
      this.world = world;
      this.entity = entity;
      this.power = _snowman;
      this.x = _snowman;
      this.y = _snowman;
      this.z = _snowman;
      this.createFire = _snowman;
      this.destructionType = _snowman;
      this.damageSource = _snowman == null ? DamageSource.explosion(this) : _snowman;
      this.behavior = _snowman == null ? this.chooseBehavior(entity) : _snowman;
   }

   private ExplosionBehavior chooseBehavior(@Nullable Entity entity) {
      return (ExplosionBehavior)(entity == null ? field_25818 : new EntityExplosionBehavior(entity));
   }

   public static float getExposure(Vec3d source, Entity entity) {
      Box _snowman = entity.getBoundingBox();
      double _snowmanx = 1.0 / ((_snowman.maxX - _snowman.minX) * 2.0 + 1.0);
      double _snowmanxx = 1.0 / ((_snowman.maxY - _snowman.minY) * 2.0 + 1.0);
      double _snowmanxxx = 1.0 / ((_snowman.maxZ - _snowman.minZ) * 2.0 + 1.0);
      double _snowmanxxxx = (1.0 - Math.floor(1.0 / _snowmanx) * _snowmanx) / 2.0;
      double _snowmanxxxxx = (1.0 - Math.floor(1.0 / _snowmanxxx) * _snowmanxxx) / 2.0;
      if (!(_snowmanx < 0.0) && !(_snowmanxx < 0.0) && !(_snowmanxxx < 0.0)) {
         int _snowmanxxxxxx = 0;
         int _snowmanxxxxxxx = 0;

         for (float _snowmanxxxxxxxx = 0.0F; _snowmanxxxxxxxx <= 1.0F; _snowmanxxxxxxxx = (float)((double)_snowmanxxxxxxxx + _snowmanx)) {
            for (float _snowmanxxxxxxxxx = 0.0F; _snowmanxxxxxxxxx <= 1.0F; _snowmanxxxxxxxxx = (float)((double)_snowmanxxxxxxxxx + _snowmanxx)) {
               for (float _snowmanxxxxxxxxxx = 0.0F; _snowmanxxxxxxxxxx <= 1.0F; _snowmanxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxx + _snowmanxxx)) {
                  double _snowmanxxxxxxxxxxx = MathHelper.lerp((double)_snowmanxxxxxxxx, _snowman.minX, _snowman.maxX);
                  double _snowmanxxxxxxxxxxxx = MathHelper.lerp((double)_snowmanxxxxxxxxx, _snowman.minY, _snowman.maxY);
                  double _snowmanxxxxxxxxxxxxx = MathHelper.lerp((double)_snowmanxxxxxxxxxx, _snowman.minZ, _snowman.maxZ);
                  Vec3d _snowmanxxxxxxxxxxxxxx = new Vec3d(_snowmanxxxxxxxxxxx + _snowmanxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx + _snowmanxxxxx);
                  if (entity.world
                        .raycast(new RaycastContext(_snowmanxxxxxxxxxxxxxx, source, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity))
                        .getType()
                     == HitResult.Type.MISS) {
                     _snowmanxxxxxx++;
                  }

                  _snowmanxxxxxxx++;
               }
            }
         }

         return (float)_snowmanxxxxxx / (float)_snowmanxxxxxxx;
      } else {
         return 0.0F;
      }
   }

   public void collectBlocksAndDamageEntities() {
      Set<BlockPos> _snowman = Sets.newHashSet();
      int _snowmanx = 16;

      for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
               if (_snowmanxx == 0 || _snowmanxx == 15 || _snowmanxxx == 0 || _snowmanxxx == 15 || _snowmanxxxx == 0 || _snowmanxxxx == 15) {
                  double _snowmanxxxxx = (double)((float)_snowmanxx / 15.0F * 2.0F - 1.0F);
                  double _snowmanxxxxxx = (double)((float)_snowmanxxx / 15.0F * 2.0F - 1.0F);
                  double _snowmanxxxxxxx = (double)((float)_snowmanxxxx / 15.0F * 2.0F - 1.0F);
                  double _snowmanxxxxxxxx = Math.sqrt(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx);
                  _snowmanxxxxx /= _snowmanxxxxxxxx;
                  _snowmanxxxxxx /= _snowmanxxxxxxxx;
                  _snowmanxxxxxxx /= _snowmanxxxxxxxx;
                  float _snowmanxxxxxxxxx = this.power * (0.7F + this.world.random.nextFloat() * 0.6F);
                  double _snowmanxxxxxxxxxx = this.x;
                  double _snowmanxxxxxxxxxxx = this.y;
                  double _snowmanxxxxxxxxxxxx = this.z;

                  for (float _snowmanxxxxxxxxxxxxx = 0.3F; _snowmanxxxxxxxxx > 0.0F; _snowmanxxxxxxxxx -= 0.22500001F) {
                     BlockPos _snowmanxxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
                     BlockState _snowmanxxxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxxxxxx);
                     FluidState _snowmanxxxxxxxxxxxxxxxx = this.world.getFluidState(_snowmanxxxxxxxxxxxxxx);
                     Optional<Float> _snowmanxxxxxxxxxxxxxxxxx = this.behavior
                        .getBlastResistance(this, this.world, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxx.isPresent()) {
                        _snowmanxxxxxxxxx -= (_snowmanxxxxxxxxxxxxxxxxx.get() + 0.3F) * 0.3F;
                     }

                     if (_snowmanxxxxxxxxx > 0.0F && this.behavior.canDestroyBlock(this, this.world, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx)) {
                        _snowman.add(_snowmanxxxxxxxxxxxxxx);
                     }

                     _snowmanxxxxxxxxxx += _snowmanxxxxx * 0.3F;
                     _snowmanxxxxxxxxxxx += _snowmanxxxxxx * 0.3F;
                     _snowmanxxxxxxxxxxxx += _snowmanxxxxxxx * 0.3F;
                  }
               }
            }
         }
      }

      this.affectedBlocks.addAll(_snowman);
      float _snowmanxx = this.power * 2.0F;
      int _snowmanxxx = MathHelper.floor(this.x - (double)_snowmanxx - 1.0);
      int _snowmanxxxxx = MathHelper.floor(this.x + (double)_snowmanxx + 1.0);
      int _snowmanxxxxxx = MathHelper.floor(this.y - (double)_snowmanxx - 1.0);
      int _snowmanxxxxxxx = MathHelper.floor(this.y + (double)_snowmanxx + 1.0);
      int _snowmanxxxxxxxx = MathHelper.floor(this.z - (double)_snowmanxx - 1.0);
      int _snowmanxxxxxxxxx = MathHelper.floor(this.z + (double)_snowmanxx + 1.0);
      List<Entity> _snowmanxxxxxxxxxx = this.world
         .getOtherEntities(this.entity, new Box((double)_snowmanxxx, (double)_snowmanxxxxxx, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxx, (double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxxx));
      Vec3d _snowmanxxxxxxxxxxx = new Vec3d(this.x, this.y, this.z);

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxx.size(); _snowmanxxxxxxxxxxxx++) {
         Entity _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.get(_snowmanxxxxxxxxxxxx);
         if (!_snowmanxxxxxxxxxxxxx.isImmuneToExplosion()) {
            double _snowmanxxxxxxxxxxxxxxxxxx = (double)(MathHelper.sqrt(_snowmanxxxxxxxxxxxxx.squaredDistanceTo(_snowmanxxxxxxxxxxx)) / _snowmanxx);
            if (_snowmanxxxxxxxxxxxxxxxxxx <= 1.0) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getX() - this.x;
               double _snowmanxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxx instanceof TntEntity ? _snowmanxxxxxxxxxxxxx.getY() : _snowmanxxxxxxxxxxxxx.getEyeY()) - this.y;
               double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getZ() - this.z;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxx = (double)MathHelper.sqrt(
                  _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx
               );
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx != 0.0) {
                  _snowmanxxxxxxxxxxxxxxxxxxx /= _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxx /= _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxxxxxxxx /= _snowmanxxxxxxxxxxxxxxxxxxxxxx;
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (double)getExposure(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = (1.0 - _snowmanxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxx.damage(
                     this.getDamageSource(),
                     (float)((int)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0 * 7.0 * (double)_snowmanxx + 1.0))
                  );
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxx instanceof LivingEntity) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = ProtectionEnchantment.transformExplosionKnockback((LivingEntity)_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                  }

                  _snowmanxxxxxxxxxxxxx.setVelocity(
                     _snowmanxxxxxxxxxxxxx.getVelocity()
                        .add(
                           _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                           _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
                        )
                  );
                  if (_snowmanxxxxxxxxxxxxx instanceof PlayerEntity) {
                     PlayerEntity _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (PlayerEntity)_snowmanxxxxxxxxxxxxx;
                     if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isSpectator()
                        && (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isCreative() || !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.abilities.flying)) {
                        this.affectedPlayers
                           .put(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              new Vec3d(
                                 _snowmanxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx
                              )
                           );
                     }
                  }
               }
            }
         }
      }
   }

   public void affectWorld(boolean _snowman) {
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

      boolean _snowmanx = this.destructionType != Explosion.DestructionType.NONE;
      if (_snowman) {
         if (!(this.power < 2.0F) && _snowmanx) {
            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         } else {
            this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         }
      }

      if (_snowmanx) {
         ObjectArrayList<Pair<ItemStack, BlockPos>> _snowmanxx = new ObjectArrayList();
         Collections.shuffle(this.affectedBlocks, this.world.random);

         for (BlockPos _snowmanxxx : this.affectedBlocks) {
            BlockState _snowmanxxxx = this.world.getBlockState(_snowmanxxx);
            Block _snowmanxxxxx = _snowmanxxxx.getBlock();
            if (!_snowmanxxxx.isAir()) {
               BlockPos _snowmanxxxxxx = _snowmanxxx.toImmutable();
               this.world.getProfiler().push("explosion_blocks");
               if (_snowmanxxxxx.shouldDropItemsOnExplosion(this) && this.world instanceof ServerWorld) {
                  BlockEntity _snowmanxxxxxxx = _snowmanxxxxx.hasBlockEntity() ? this.world.getBlockEntity(_snowmanxxx) : null;
                  LootContext.Builder _snowmanxxxxxxxx = new LootContext.Builder((ServerWorld)this.world)
                     .random(this.world.random)
                     .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(_snowmanxxx))
                     .parameter(LootContextParameters.TOOL, ItemStack.EMPTY)
                     .optionalParameter(LootContextParameters.BLOCK_ENTITY, _snowmanxxxxxxx)
                     .optionalParameter(LootContextParameters.THIS_ENTITY, this.entity);
                  if (this.destructionType == Explosion.DestructionType.DESTROY) {
                     _snowmanxxxxxxxx.parameter(LootContextParameters.EXPLOSION_RADIUS, this.power);
                  }

                  _snowmanxxxx.getDroppedStacks(_snowmanxxxxxxxx).forEach(_snowmanxxxxxxxxx -> method_24023(_snowman, _snowmanxxxxxxxxx, _snowman));
               }

               this.world.setBlockState(_snowmanxxx, Blocks.AIR.getDefaultState(), 3);
               _snowmanxxxxx.onDestroyedByExplosion(this.world, _snowmanxxx, this);
               this.world.getProfiler().pop();
            }
         }

         ObjectListIterator var12 = _snowmanxx.iterator();

         while (var12.hasNext()) {
            Pair<ItemStack, BlockPos> _snowmanxxxx = (Pair<ItemStack, BlockPos>)var12.next();
            Block.dropStack(this.world, (BlockPos)_snowmanxxxx.getSecond(), (ItemStack)_snowmanxxxx.getFirst());
         }
      }

      if (this.createFire) {
         for (BlockPos _snowmanxx : this.affectedBlocks) {
            if (this.random.nextInt(3) == 0
               && this.world.getBlockState(_snowmanxx).isAir()
               && this.world.getBlockState(_snowmanxx.down()).isOpaqueFullCube(this.world, _snowmanxx.down())) {
               this.world.setBlockState(_snowmanxx, AbstractFireBlock.getState(this.world, _snowmanxx));
            }
         }
      }
   }

   private static void method_24023(ObjectArrayList<Pair<ItemStack, BlockPos>> _snowman, ItemStack _snowman, BlockPos _snowman) {
      int _snowmanxxx = _snowman.size();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         Pair<ItemStack, BlockPos> _snowmanxxxxx = (Pair<ItemStack, BlockPos>)_snowman.get(_snowmanxxxx);
         ItemStack _snowmanxxxxxx = (ItemStack)_snowmanxxxxx.getFirst();
         if (ItemEntity.canMerge(_snowmanxxxxxx, _snowman)) {
            ItemStack _snowmanxxxxxxx = ItemEntity.merge(_snowmanxxxxxx, _snowman, 16);
            _snowman.set(_snowmanxxxx, Pair.of(_snowmanxxxxxxx, _snowmanxxxxx.getSecond()));
            if (_snowman.isEmpty()) {
               return;
            }
         }
      }

      _snowman.add(Pair.of(_snowman, _snowman));
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
            Entity _snowman = ((ProjectileEntity)this.entity).getOwner();
            if (_snowman instanceof LivingEntity) {
               return (LivingEntity)_snowman;
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
