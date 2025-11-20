/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  com.google.common.base.Functions
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.advancement;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Identifier, Advancement> advancements = Maps.newHashMap();
    private final Set<Advancement> roots = Sets.newLinkedHashSet();
    private final Set<Advancement> dependents = Sets.newLinkedHashSet();
    private Listener listener;

    private void remove(Advancement advancement) {
        for (Advancement advancement2 : advancement.getChildren()) {
            this.remove(advancement2);
        }
        LOGGER.info("Forgot about advancement {}", (Object)advancement.getId());
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
        for (Identifier identifier : advancements) {
            Advancement advancement = this.advancements.get(identifier);
            if (advancement == null) {
                LOGGER.warn("Told to remove advancement {} but I don't know what that is", (Object)identifier);
                continue;
            }
            this.remove(advancement);
        }
    }

    public void load(Map<Identifier, Advancement.Task> map) {
        Function function = Functions.forMap(this.advancements, null);
        while (!map.isEmpty()) {
            boolean _snowman6 = false;
            Iterator<Map.Entry<Identifier, Advancement.Task>> _snowman2 = map.entrySet().iterator();
            while (_snowman2.hasNext()) {
                Map.Entry<Identifier, Advancement.Task> entry = _snowman2.next();
                Identifier _snowman3 = entry.getKey();
                Advancement.Task _snowman4 = entry.getValue();
                if (!_snowman4.findParent((java.util.function.Function<Identifier, Advancement>)function)) continue;
                Advancement _snowman5 = _snowman4.build(_snowman3);
                this.advancements.put(_snowman3, _snowman5);
                _snowman6 = true;
                _snowman2.remove();
                if (_snowman5.getParent() == null) {
                    this.roots.add(_snowman5);
                    if (this.listener == null) continue;
                    this.listener.onRootAdded(_snowman5);
                    continue;
                }
                this.dependents.add(_snowman5);
                if (this.listener == null) continue;
                this.listener.onDependentAdded(_snowman5);
            }
            if (_snowman6) continue;
            for (Map.Entry<Identifier, Advancement.Task> entry : map.entrySet()) {
                LOGGER.error("Couldn't load advancement {}: {}", (Object)entry.getKey(), (Object)entry.getValue());
            }
        }
        LOGGER.info("Loaded {} advancements", (Object)this.advancements.size());
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

    public void setListener(@Nullable Listener listener) {
        this.listener = listener;
        if (listener != null) {
            for (Advancement advancement : this.roots) {
                listener.onRootAdded(advancement);
            }
            for (Advancement advancement : this.dependents) {
                listener.onDependentAdded(advancement);
            }
        }
    }

    public static interface Listener {
        public void onRootAdded(Advancement var1);

        public void onRootRemoved(Advancement var1);

        public void onDependentAdded(Advancement var1);

        public void onDependentRemoved(Advancement var1);

        public void onClear();
    }
}

