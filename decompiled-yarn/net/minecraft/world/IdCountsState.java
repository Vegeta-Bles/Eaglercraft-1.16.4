package net.minecraft.world;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.nbt.CompoundTag;

public class IdCountsState extends PersistentState {
   private final Object2IntMap<String> idCounts = new Object2IntOpenHashMap();

   public IdCountsState() {
      super("idcounts");
      this.idCounts.defaultReturnValue(-1);
   }

   @Override
   public void fromTag(CompoundTag tag) {
      this.idCounts.clear();

      for (String _snowman : tag.getKeys()) {
         if (tag.contains(_snowman, 99)) {
            this.idCounts.put(_snowman, tag.getInt(_snowman));
         }
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      ObjectIterator var2 = this.idCounts.object2IntEntrySet().iterator();

      while (var2.hasNext()) {
         Entry<String> _snowman = (Entry<String>)var2.next();
         tag.putInt((String)_snowman.getKey(), _snowman.getIntValue());
      }

      return tag;
   }

   public int getNextMapId() {
      int _snowman = this.idCounts.getInt("map") + 1;
      this.idCounts.put("map", _snowman);
      this.markDirty();
      return _snowman;
   }
}
