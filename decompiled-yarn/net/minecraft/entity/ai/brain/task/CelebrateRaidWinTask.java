package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;

public class CelebrateRaidWinTask extends Task<VillagerEntity> {
   @Nullable
   private Raid raid;

   public CelebrateRaidWinTask(int minRunTime, int maxRunTime) {
      super(ImmutableMap.of(), minRunTime, maxRunTime);
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      BlockPos _snowmanxx = _snowman.getBlockPos();
      this.raid = _snowman.getRaidAt(_snowmanxx);
      return this.raid != null && this.raid.hasWon() && SeekSkyTask.isSkyVisible(_snowman, _snowman, _snowmanxx);
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return this.raid != null && !this.raid.hasStopped();
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      this.raid = null;
      _snowman.getBrain().refreshActivities(_snowman.getTimeOfDay(), _snowman.getTime());
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      Random _snowmanxxx = _snowman.getRandom();
      if (_snowmanxxx.nextInt(100) == 0) {
         _snowman.playCelebrateSound();
      }

      if (_snowmanxxx.nextInt(200) == 0 && SeekSkyTask.isSkyVisible(_snowman, _snowman, _snowman.getBlockPos())) {
         DyeColor _snowmanxxxx = Util.getRandom(DyeColor.values(), _snowmanxxx);
         int _snowmanxxxxx = _snowmanxxx.nextInt(3);
         ItemStack _snowmanxxxxxx = this.createFirework(_snowmanxxxx, _snowmanxxxxx);
         FireworkRocketEntity _snowmanxxxxxxx = new FireworkRocketEntity(_snowman.world, _snowman, _snowman.getX(), _snowman.getEyeY(), _snowman.getZ(), _snowmanxxxxxx);
         _snowman.world.spawnEntity(_snowmanxxxxxxx);
      }
   }

   private ItemStack createFirework(DyeColor color, int flight) {
      ItemStack _snowman = new ItemStack(Items.FIREWORK_ROCKET, 1);
      ItemStack _snowmanx = new ItemStack(Items.FIREWORK_STAR);
      CompoundTag _snowmanxx = _snowmanx.getOrCreateSubTag("Explosion");
      List<Integer> _snowmanxxx = Lists.newArrayList();
      _snowmanxxx.add(color.getFireworkColor());
      _snowmanxx.putIntArray("Colors", _snowmanxxx);
      _snowmanxx.putByte("Type", (byte)FireworkItem.Type.BURST.getId());
      CompoundTag _snowmanxxxx = _snowman.getOrCreateSubTag("Fireworks");
      ListTag _snowmanxxxxx = new ListTag();
      CompoundTag _snowmanxxxxxx = _snowmanx.getSubTag("Explosion");
      if (_snowmanxxxxxx != null) {
         _snowmanxxxxx.add(_snowmanxxxxxx);
      }

      _snowmanxxxx.putByte("Flight", (byte)flight);
      if (!_snowmanxxxxx.isEmpty()) {
         _snowmanxxxx.put("Explosions", _snowmanxxxxx);
      }

      return _snowman;
   }
}
