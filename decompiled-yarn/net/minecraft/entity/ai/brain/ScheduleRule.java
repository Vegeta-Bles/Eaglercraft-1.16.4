package net.minecraft.entity.ai.brain;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.List;

public class ScheduleRule {
   private final List<ScheduleRuleEntry> entries = Lists.newArrayList();
   private int prioritizedEntryIndex;

   public ScheduleRule() {
   }

   public ScheduleRule add(int startTime, float priority) {
      this.entries.add(new ScheduleRuleEntry(startTime, priority));
      this.sort();
      return this;
   }

   private void sort() {
      Int2ObjectSortedMap<ScheduleRuleEntry> _snowman = new Int2ObjectAVLTreeMap();
      this.entries.forEach(_snowmanx -> {
         ScheduleRuleEntry var10000 = (ScheduleRuleEntry)_snowman.put(_snowmanx.getStartTime(), _snowmanx);
      });
      this.entries.clear();
      this.entries.addAll(_snowman.values());
      this.prioritizedEntryIndex = 0;
   }

   public float getPriority(int time) {
      if (this.entries.size() <= 0) {
         return 0.0F;
      } else {
         ScheduleRuleEntry _snowman = this.entries.get(this.prioritizedEntryIndex);
         ScheduleRuleEntry _snowmanx = this.entries.get(this.entries.size() - 1);
         boolean _snowmanxx = time < _snowman.getStartTime();
         int _snowmanxxx = _snowmanxx ? 0 : this.prioritizedEntryIndex;
         float _snowmanxxxx = _snowmanxx ? _snowmanx.getPriority() : _snowman.getPriority();

         for (int _snowmanxxxxx = _snowmanxxx; _snowmanxxxxx < this.entries.size(); _snowmanxxxxx++) {
            ScheduleRuleEntry _snowmanxxxxxx = this.entries.get(_snowmanxxxxx);
            if (_snowmanxxxxxx.getStartTime() > time) {
               break;
            }

            this.prioritizedEntryIndex = _snowmanxxxxx;
            _snowmanxxxx = _snowmanxxxxxx.getPriority();
         }

         return _snowmanxxxx;
      }
   }
}
