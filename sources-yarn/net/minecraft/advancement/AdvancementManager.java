package net.minecraft.advancement;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<Identifier, Advancement> advancements = Maps.newHashMap();
   private final Set<Advancement> roots = Sets.newLinkedHashSet();
   private final Set<Advancement> dependents = Sets.newLinkedHashSet();
   private AdvancementManager.Listener listener;

   public AdvancementManager() {
   }

   @Environment(EnvType.CLIENT)
   private void remove(Advancement advancement) {
      for (Advancement lv : advancement.getChildren()) {
         this.remove(lv);
      }

      LOGGER.info("Forgot about advancement {}", advancement.getId());
      this.advancements.remove(advancement.getId());
      if (advancement.getParent() == null) {
         this.roots.remove(advancement);
         if (this.listener != null) {
            this.listener.onRootRemoved(advancement);
         }
      } else {
         this.dependents.remove(advancement);
         if (this.listener != null) {
            this.listener.onDependentRemoved(advancement);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   public void removeAll(Set<Identifier> advancements) {
      for (Identifier lv : advancements) {
         Advancement lv2 = this.advancements.get(lv);
         if (lv2 == null) {
            LOGGER.warn("Told to remove advancement {} but I don't know what that is", lv);
         } else {
            this.remove(lv2);
         }
      }
   }

   public void load(Map<Identifier, Advancement.Task> map) {
      Function<Identifier, Advancement> function = Functions.forMap(this.advancements, null);

      while (!map.isEmpty()) {
         boolean bl = false;
         Iterator<Entry<Identifier, Advancement.Task>> iterator = map.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Identifier, Advancement.Task> entry = iterator.next();
            Identifier lv = entry.getKey();
            Advancement.Task lv2 = entry.getValue();
            if (lv2.findParent(function)) {
               Advancement lv3 = lv2.build(lv);
               this.advancements.put(lv, lv3);
               bl = true;
               iterator.remove();
               if (lv3.getParent() == null) {
                  this.roots.add(lv3);
                  if (this.listener != null) {
                     this.listener.onRootAdded(lv3);
                  }
               } else {
                  this.dependents.add(lv3);
                  if (this.listener != null) {
                     this.listener.onDependentAdded(lv3);
                  }
               }
            }
         }

         if (!bl) {
            for (Entry<Identifier, Advancement.Task> entry2 : map.entrySet()) {
               LOGGER.error("Couldn't load advancement {}: {}", entry2.getKey(), entry2.getValue());
            }
            break;
         }
      }

      LOGGER.info("Loaded {} advancements", this.advancements.size());
   }

   @Environment(EnvType.CLIENT)
   public void clear() {
      this.advancements.clear();
      this.roots.clear();
      this.dependents.clear();
      if (this.listener != null) {
         this.listener.onClear();
      }
   }

   public Iterable<Advancement> getRoots() {
      return this.roots;
   }

   public Collection<Advancement> getAdvancements() {
      return this.advancements.values();
   }

   @Nullable
   public Advancement get(Identifier id) {
      return this.advancements.get(id);
   }

   @Environment(EnvType.CLIENT)
   public void setListener(@Nullable AdvancementManager.Listener listener) {
      this.listener = listener;
      if (listener != null) {
         for (Advancement lv : this.roots) {
            listener.onRootAdded(lv);
         }

         for (Advancement lv2 : this.dependents) {
            listener.onDependentAdded(lv2);
         }
      }
   }

   public interface Listener {
      void onRootAdded(Advancement root);

      @Environment(EnvType.CLIENT)
      void onRootRemoved(Advancement root);

      void onDependentAdded(Advancement dependent);

      @Environment(EnvType.CLIENT)
      void onDependentRemoved(Advancement dependent);

      @Environment(EnvType.CLIENT)
      void onClear();
   }
}
