package net.minecraft.server.world;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ScheduledTick;
import net.minecraft.world.TickPriority;
import net.minecraft.world.TickScheduler;

public class SimpleTickScheduler<T> implements TickScheduler<T> {
   private final List<SimpleTickScheduler.Tick<T>> scheduledTicks;
   private final Function<T, Identifier> identifierProvider;

   public SimpleTickScheduler(Function<T, Identifier> identifierProvider, List<ScheduledTick<T>> scheduledTicks, long startTime) {
      this(
         identifierProvider,
         scheduledTicks.stream()
            .map(_snowmanx -> new SimpleTickScheduler.Tick(_snowmanx.getObject(), _snowmanx.pos, (int)(_snowmanx.time - startTime), _snowmanx.priority))
            .collect(Collectors.toList())
      );
   }

   private SimpleTickScheduler(Function<T, Identifier> identifierProvider, List<SimpleTickScheduler.Tick<T>> scheduledTicks) {
      this.scheduledTicks = scheduledTicks;
      this.identifierProvider = identifierProvider;
   }

   @Override
   public boolean isScheduled(BlockPos pos, T object) {
      return false;
   }

   @Override
   public void schedule(BlockPos pos, T object, int delay, TickPriority priority) {
      this.scheduledTicks.add(new SimpleTickScheduler.Tick<>(object, pos, delay, priority));
   }

   @Override
   public boolean isTicking(BlockPos pos, T object) {
      return false;
   }

   public ListTag toNbt() {
      ListTag _snowman = new ListTag();

      for (SimpleTickScheduler.Tick<T> _snowmanx : this.scheduledTicks) {
         CompoundTag _snowmanxx = new CompoundTag();
         _snowmanxx.putString("i", this.identifierProvider.apply(_snowmanx.object).toString());
         _snowmanxx.putInt("x", _snowmanx.pos.getX());
         _snowmanxx.putInt("y", _snowmanx.pos.getY());
         _snowmanxx.putInt("z", _snowmanx.pos.getZ());
         _snowmanxx.putInt("t", _snowmanx.delay);
         _snowmanxx.putInt("p", _snowmanx.priority.getIndex());
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   public static <T> SimpleTickScheduler<T> fromNbt(ListTag ticks, Function<T, Identifier> _snowman, Function<Identifier, T> _snowman) {
      List<SimpleTickScheduler.Tick<T>> _snowmanxx = Lists.newArrayList();

      for (int _snowmanxxx = 0; _snowmanxxx < ticks.size(); _snowmanxxx++) {
         CompoundTag _snowmanxxxx = ticks.getCompound(_snowmanxxx);
         T _snowmanxxxxx = _snowman.apply(new Identifier(_snowmanxxxx.getString("i")));
         if (_snowmanxxxxx != null) {
            BlockPos _snowmanxxxxxx = new BlockPos(_snowmanxxxx.getInt("x"), _snowmanxxxx.getInt("y"), _snowmanxxxx.getInt("z"));
            _snowmanxx.add(new SimpleTickScheduler.Tick<>(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxx.getInt("t"), TickPriority.byIndex(_snowmanxxxx.getInt("p"))));
         }
      }

      return new SimpleTickScheduler<>(_snowman, _snowmanxx);
   }

   public void scheduleTo(TickScheduler<T> scheduler) {
      this.scheduledTicks.forEach(_snowmanx -> scheduler.schedule(_snowmanx.pos, _snowmanx.object, _snowmanx.delay, _snowmanx.priority));
   }

   static class Tick<T> {
      private final T object;
      public final BlockPos pos;
      public final int delay;
      public final TickPriority priority;

      private Tick(T object, BlockPos pos, int delay, TickPriority priority) {
         this.object = object;
         this.pos = pos;
         this.delay = delay;
         this.priority = priority;
      }

      @Override
      public String toString() {
         return this.object + ": " + this.pos + ", " + this.delay + ", " + this.priority;
      }
   }
}
