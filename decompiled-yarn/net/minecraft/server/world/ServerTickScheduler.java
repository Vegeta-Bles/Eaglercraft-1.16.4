package net.minecraft.server.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ScheduledTick;
import net.minecraft.world.TickPriority;
import net.minecraft.world.TickScheduler;

public class ServerTickScheduler<T> implements TickScheduler<T> {
   protected final Predicate<T> invalidObjPredicate;
   private final Function<T, Identifier> idToName;
   private final Set<ScheduledTick<T>> scheduledTickActions = Sets.newHashSet();
   private final TreeSet<ScheduledTick<T>> scheduledTickActionsInOrder = Sets.newTreeSet(ScheduledTick.getComparator());
   private final ServerWorld world;
   private final Queue<ScheduledTick<T>> currentTickActions = Queues.newArrayDeque();
   private final List<ScheduledTick<T>> consumedTickActions = Lists.newArrayList();
   private final Consumer<ScheduledTick<T>> tickConsumer;

   public ServerTickScheduler(ServerWorld world, Predicate<T> invalidObjPredicate, Function<T, Identifier> idToName, Consumer<ScheduledTick<T>> _snowman) {
      this.invalidObjPredicate = invalidObjPredicate;
      this.idToName = idToName;
      this.world = world;
      this.tickConsumer = _snowman;
   }

   public void tick() {
      int _snowman = this.scheduledTickActionsInOrder.size();
      if (_snowman != this.scheduledTickActions.size()) {
         throw new IllegalStateException("TickNextTick list out of synch");
      } else {
         if (_snowman > 65536) {
            _snowman = 65536;
         }

         ServerChunkManager _snowmanx = this.world.getChunkManager();
         Iterator<ScheduledTick<T>> _snowmanxx = this.scheduledTickActionsInOrder.iterator();
         this.world.getProfiler().push("cleaning");

         while (_snowman > 0 && _snowmanxx.hasNext()) {
            ScheduledTick<T> _snowmanxxx = _snowmanxx.next();
            if (_snowmanxxx.time > this.world.getTime()) {
               break;
            }

            if (_snowmanx.shouldTickBlock(_snowmanxxx.pos)) {
               _snowmanxx.remove();
               this.scheduledTickActions.remove(_snowmanxxx);
               this.currentTickActions.add(_snowmanxxx);
               _snowman--;
            }
         }

         this.world.getProfiler().swap("ticking");

         ScheduledTick<T> _snowmanxxxx;
         while ((_snowmanxxxx = this.currentTickActions.poll()) != null) {
            if (_snowmanx.shouldTickBlock(_snowmanxxxx.pos)) {
               try {
                  this.consumedTickActions.add(_snowmanxxxx);
                  this.tickConsumer.accept(_snowmanxxxx);
               } catch (Throwable var8) {
                  CrashReport _snowmanxxxxx = CrashReport.create(var8, "Exception while ticking");
                  CrashReportSection _snowmanxxxxxx = _snowmanxxxxx.addElement("Block being ticked");
                  CrashReportSection.addBlockInfo(_snowmanxxxxxx, _snowmanxxxx.pos, null);
                  throw new CrashException(_snowmanxxxxx);
               }
            } else {
               this.schedule(_snowmanxxxx.pos, _snowmanxxxx.getObject(), 0);
            }
         }

         this.world.getProfiler().pop();
         this.consumedTickActions.clear();
         this.currentTickActions.clear();
      }
   }

   @Override
   public boolean isTicking(BlockPos pos, T object) {
      return this.currentTickActions.contains(new ScheduledTick(pos, object));
   }

   public List<ScheduledTick<T>> getScheduledTicksInChunk(ChunkPos _snowman, boolean updateState, boolean getStaleTicks) {
      int _snowmanx = (_snowman.x << 4) - 2;
      int _snowmanxx = _snowmanx + 16 + 2;
      int _snowmanxxx = (_snowman.z << 4) - 2;
      int _snowmanxxxx = _snowmanxxx + 16 + 2;
      return this.getScheduledTicks(new BlockBox(_snowmanx, 0, _snowmanxxx, _snowmanxx, 256, _snowmanxxxx), updateState, getStaleTicks);
   }

   public List<ScheduledTick<T>> getScheduledTicks(BlockBox bounds, boolean updateState, boolean getStaleTicks) {
      List<ScheduledTick<T>> _snowman = this.transferTicksInBounds(null, this.scheduledTickActionsInOrder, bounds, updateState);
      if (updateState && _snowman != null) {
         this.scheduledTickActions.removeAll(_snowman);
      }

      _snowman = this.transferTicksInBounds(_snowman, this.currentTickActions, bounds, updateState);
      if (!getStaleTicks) {
         _snowman = this.transferTicksInBounds(_snowman, this.consumedTickActions, bounds, updateState);
      }

      return _snowman == null ? Collections.emptyList() : _snowman;
   }

   @Nullable
   private List<ScheduledTick<T>> transferTicksInBounds(@Nullable List<ScheduledTick<T>> dst, Collection<ScheduledTick<T>> src, BlockBox bounds, boolean move) {
      Iterator<ScheduledTick<T>> _snowman = src.iterator();

      while (_snowman.hasNext()) {
         ScheduledTick<T> _snowmanx = _snowman.next();
         BlockPos _snowmanxx = _snowmanx.pos;
         if (_snowmanxx.getX() >= bounds.minX && _snowmanxx.getX() < bounds.maxX && _snowmanxx.getZ() >= bounds.minZ && _snowmanxx.getZ() < bounds.maxZ) {
            if (move) {
               _snowman.remove();
            }

            if (dst == null) {
               dst = Lists.newArrayList();
            }

            dst.add(_snowmanx);
         }
      }

      return dst;
   }

   public void copyScheduledTicks(BlockBox box, BlockPos offset) {
      for (ScheduledTick<T> _snowman : this.getScheduledTicks(box, false, false)) {
         if (box.contains(_snowman.pos)) {
            BlockPos _snowmanx = _snowman.pos.add(offset);
            T _snowmanxx = _snowman.getObject();
            this.addScheduledTick(new ScheduledTick<>(_snowmanx, _snowmanxx, _snowman.time, _snowman.priority));
         }
      }
   }

   public ListTag toTag(ChunkPos _snowman) {
      List<ScheduledTick<T>> _snowmanx = this.getScheduledTicksInChunk(_snowman, false, true);
      return serializeScheduledTicks(this.idToName, _snowmanx, this.world.getTime());
   }

   private static <T> ListTag serializeScheduledTicks(Function<T, Identifier> identifierProvider, Iterable<ScheduledTick<T>> scheduledTicks, long time) {
      ListTag _snowman = new ListTag();

      for (ScheduledTick<T> _snowmanx : scheduledTicks) {
         CompoundTag _snowmanxx = new CompoundTag();
         _snowmanxx.putString("i", identifierProvider.apply(_snowmanx.getObject()).toString());
         _snowmanxx.putInt("x", _snowmanx.pos.getX());
         _snowmanxx.putInt("y", _snowmanx.pos.getY());
         _snowmanxx.putInt("z", _snowmanx.pos.getZ());
         _snowmanxx.putInt("t", (int)(_snowmanx.time - time));
         _snowmanxx.putInt("p", _snowmanx.priority.getIndex());
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   @Override
   public boolean isScheduled(BlockPos pos, T object) {
      return this.scheduledTickActions.contains(new ScheduledTick(pos, object));
   }

   @Override
   public void schedule(BlockPos pos, T object, int delay, TickPriority priority) {
      if (!this.invalidObjPredicate.test(object)) {
         this.addScheduledTick(new ScheduledTick<>(pos, object, (long)delay + this.world.getTime(), priority));
      }
   }

   private void addScheduledTick(ScheduledTick<T> _snowman) {
      if (!this.scheduledTickActions.contains(_snowman)) {
         this.scheduledTickActions.add(_snowman);
         this.scheduledTickActionsInOrder.add(_snowman);
      }
   }

   public int getTicks() {
      return this.scheduledTickActions.size();
   }
}
