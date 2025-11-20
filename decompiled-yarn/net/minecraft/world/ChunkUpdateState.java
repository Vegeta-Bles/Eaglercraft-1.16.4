package net.minecraft.world;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;

public class ChunkUpdateState extends PersistentState {
   private LongSet all = new LongOpenHashSet();
   private LongSet remaining = new LongOpenHashSet();

   public ChunkUpdateState(String _snowman) {
      super(_snowman);
   }

   @Override
   public void fromTag(CompoundTag tag) {
      this.all = new LongOpenHashSet(tag.getLongArray("All"));
      this.remaining = new LongOpenHashSet(tag.getLongArray("Remaining"));
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      tag.putLongArray("All", this.all.toLongArray());
      tag.putLongArray("Remaining", this.remaining.toLongArray());
      return tag;
   }

   public void add(long _snowman) {
      this.all.add(_snowman);
      this.remaining.add(_snowman);
   }

   public boolean contains(long _snowman) {
      return this.all.contains(_snowman);
   }

   public boolean isRemaining(long _snowman) {
      return this.remaining.contains(_snowman);
   }

   public void markResolved(long _snowman) {
      this.remaining.remove(_snowman);
   }

   public LongSet getAll() {
      return this.all;
   }
}
