package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;

public class PhantomSpawner implements Spawner {
   private int ticksUntilNextSpawn;

   public PhantomSpawner() {
   }

   @Override
   public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
      if (!spawnMonsters) {
         return 0;
      } else if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
         return 0;
      } else {
         Random _snowman = world.random;
         this.ticksUntilNextSpawn--;
         if (this.ticksUntilNextSpawn > 0) {
            return 0;
         } else {
            this.ticksUntilNextSpawn = this.ticksUntilNextSpawn + (60 + _snowman.nextInt(60)) * 20;
            if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
               return 0;
            } else {
               int _snowmanx = 0;

               for (PlayerEntity _snowmanxx : world.getPlayers()) {
                  if (!_snowmanxx.isSpectator()) {
                     BlockPos _snowmanxxx = _snowmanxx.getBlockPos();
                     if (!world.getDimension().hasSkyLight() || _snowmanxxx.getY() >= world.getSeaLevel() && world.isSkyVisible(_snowmanxxx)) {
                        LocalDifficulty _snowmanxxxx = world.getLocalDifficulty(_snowmanxxx);
                        if (_snowmanxxxx.isHarderThan(_snowman.nextFloat() * 3.0F)) {
                           ServerStatHandler _snowmanxxxxx = ((ServerPlayerEntity)_snowmanxx).getStatHandler();
                           int _snowmanxxxxxx = MathHelper.clamp(_snowmanxxxxx.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                           int _snowmanxxxxxxx = 24000;
                           if (_snowman.nextInt(_snowmanxxxxxx) >= 72000) {
                              BlockPos _snowmanxxxxxxxx = _snowmanxxx.up(20 + _snowman.nextInt(15)).east(-10 + _snowman.nextInt(21)).south(-10 + _snowman.nextInt(21));
                              BlockState _snowmanxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxx);
                              FluidState _snowmanxxxxxxxxxx = world.getFluidState(_snowmanxxxxxxxx);
                              if (SpawnHelper.isClearForSpawn(world, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, EntityType.PHANTOM)) {
                                 EntityData _snowmanxxxxxxxxxxx = null;
                                 int _snowmanxxxxxxxxxxxx = 1 + _snowman.nextInt(_snowmanxxxx.getGlobalDifficulty().getId() + 1);

                                 for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
                                    PhantomEntity _snowmanxxxxxxxxxxxxxx = EntityType.PHANTOM.create(world);
                                    _snowmanxxxxxxxxxxxxxx.refreshPositionAndAngles(_snowmanxxxxxxxx, 0.0F, 0.0F);
                                    _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.initialize(world, _snowmanxxxx, SpawnReason.NATURAL, _snowmanxxxxxxxxxxx, null);
                                    world.spawnEntityAndPassengers(_snowmanxxxxxxxxxxxxxx);
                                 }

                                 _snowmanx += _snowmanxxxxxxxxxxxx;
                              }
                           }
                        }
                     }
                  }
               }

               return _snowmanx;
            }
         }
      }
   }
}
