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

   private void remove(Advancement advancement) {
      for (Advancement _snowman : advancement.getChildren()) {
         this.remove(_snowman);
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

   public void removeAll(Set<Identifier> advancements) {
      for (Identifier _snowman : advancements) {
         Advancement _snowmanx = this.advancements.get(_snowman);
         if (_snowmanx == null) {
            LOGGER.warn("Told to remove advancement {} but I don't know what that is", _snowman);
         } else {
            this.remove(_snowmanx);
         }
      }
   }

   public void load(Map<Identifier, Advancement.Task> _snowman) {
      Function<Identifier, Advancement> _snowmanx = Functions.forMap(this.advancements, null);

      while (!_snowman.isEmpty()) {
         boolean _snowmanxx = false;
         Iterator<Entry<Identifier, Advancement.Task>> _snowmanxxx = _snowman.entrySet().iterator();

         while (_snowmanxxx.hasNext()) {
            Entry<Identifier, Advancement.Task> _snowmanxxxx = _snowmanxxx.next();
            Identifier _snowmanxxxxx = _snowmanxxxx.getKey();
            Advancement.Task _snowmanxxxxxx = _snowmanxxxx.getValue();
            if (_snowmanxxxxxx.findParent(_snowmanx)) {
               Advancement _snowmanxxxxxxx = _snowmanxxxxxx.build(_snowmanxxxxx);
               this.advancements.put(_snowmanxxxxx, _snowmanxxxxxxx);
               _snowmanxx = true;
               _snowmanxxx.remove();
               if (_snowmanxxxxxxx.getParent() == null) {
                  this.roots.add(_snowmanxxxxxxx);
                  if (this.listener != null) {
                     this.listener.onRootAdded(_snowmanxxxxxxx);
                  }
               } else {
                  this.dependents.add(_snowmanxxxxxxx);
                  if (this.listener != null) {
                     this.listener.onDependentAdded(_snowmanxxxxxxx);
                  }
               }
            }
         }

         if (!_snowmanxx) {
            for (Entry<Identifier, Advancement.Task> _snowmanxxxx : _snowman.entrySet()) {
               LOGGER.error("Couldn't load advancement {}: {}", _snowmanxxxx.getKey(), _snowmanxxxx.getValue());
            }
            break;
         }
      }

      LOGGER.info("Loaded {} advancements", this.advancements.size());
   }

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

   public void setListener(@Nullable AdvancementManager.Listener listener) {
      this.listener = listener;
      if (listener != null) {
         for (Advancement _snowman : this.roots) {
            listener.onRootAdded(_snowman);
         }

         for (Advancement _snowman : this.dependents) {
            listener.onDependentAdded(_snowman);
         }
      }
   }

   public interface Listener {
      void onRootAdded(Advancement root);

      void onRootRemoved(Advancement root);

      void onDependentAdded(Advancement dependent);

      void onDependentRemoved(Advancement dependent);

      void onClear();
   }
}
