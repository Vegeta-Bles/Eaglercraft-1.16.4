package net.minecraft.entity.player;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class ItemCooldownManager {
   private final Map<Item, ItemCooldownManager.Entry> entries = Maps.newHashMap();
   private int tick;

   public ItemCooldownManager() {
   }

   public boolean isCoolingDown(Item item) {
      return this.getCooldownProgress(item, 0.0F) > 0.0F;
   }

   public float getCooldownProgress(Item item, float partialTicks) {
      ItemCooldownManager.Entry _snowman = this.entries.get(item);
      if (_snowman != null) {
         float _snowmanx = (float)(_snowman.endTick - _snowman.startTick);
         float _snowmanxx = (float)_snowman.endTick - ((float)this.tick + partialTicks);
         return MathHelper.clamp(_snowmanxx / _snowmanx, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   public void update() {
      this.tick++;
      if (!this.entries.isEmpty()) {
         Iterator<Map.Entry<Item, ItemCooldownManager.Entry>> _snowman = this.entries.entrySet().iterator();

         while (_snowman.hasNext()) {
            Map.Entry<Item, ItemCooldownManager.Entry> _snowmanx = _snowman.next();
            if (_snowmanx.getValue().endTick <= this.tick) {
               _snowman.remove();
               this.onCooldownUpdate(_snowmanx.getKey());
            }
         }
      }
   }

   public void set(Item item, int duration) {
      this.entries.put(item, new ItemCooldownManager.Entry(this.tick, this.tick + duration));
      this.onCooldownUpdate(item, duration);
   }

   public void remove(Item item) {
      this.entries.remove(item);
      this.onCooldownUpdate(item);
   }

   protected void onCooldownUpdate(Item item, int duration) {
   }

   protected void onCooldownUpdate(Item item) {
   }

   class Entry {
      private final int startTick;
      private final int endTick;

      private Entry(int startTick, int endTick) {
         this.startTick = startTick;
         this.endTick = endTick;
      }
   }
}
