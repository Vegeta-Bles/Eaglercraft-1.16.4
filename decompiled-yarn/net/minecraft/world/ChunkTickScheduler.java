package net.minecraft.world;

import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;

public class ChunkTickScheduler<T> implements TickScheduler<T> {
   protected final Predicate<T> shouldExclude;
   private final ChunkPos pos;
   private final ShortList[] scheduledPositions = new ShortList[16];

   public ChunkTickScheduler(Predicate<T> shouldExclude, ChunkPos pos) {
      this(shouldExclude, pos, new ListTag());
   }

   public ChunkTickScheduler(Predicate<T> shouldExclude, ChunkPos pos, ListTag tag) {
      this.shouldExclude = shouldExclude;
      this.pos = pos;

      for (int _snowman = 0; _snowman < tag.size(); _snowman++) {
         ListTag _snowmanx = tag.getList(_snowman);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            Chunk.getList(this.scheduledPositions, _snowman).add(_snowmanx.getShort(_snowmanxx));
         }
      }
   }

   public ListTag toNbt() {
      return ChunkSerializer.toNbt(this.scheduledPositions);
   }

   public void tick(TickScheduler<T> scheduler, Function<BlockPos, T> dataMapper) {
      for (int _snowman = 0; _snowman < this.scheduledPositions.length; _snowman++) {
         if (this.scheduledPositions[_snowman] != null) {
            ShortListIterator var4 = this.scheduledPositions[_snowman].iterator();

            while (var4.hasNext()) {
               Short _snowmanx = (Short)var4.next();
               BlockPos _snowmanxx = ProtoChunk.joinBlockPos(_snowmanx, _snowman, this.pos);
               scheduler.schedule(_snowmanxx, dataMapper.apply(_snowmanxx), 0);
            }

            this.scheduledPositions[_snowman].clear();
         }
      }
   }

   @Override
   public boolean isScheduled(BlockPos pos, T object) {
      return false;
   }

   @Override
   public void schedule(BlockPos pos, T object, int delay, TickPriority priority) {
      Chunk.getList(this.scheduledPositions, pos.getY() >> 4).add(ProtoChunk.getPackedSectionRelative(pos));
   }

   @Override
   public boolean isTicking(BlockPos pos, T object) {
      return false;
   }
}
