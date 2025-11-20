/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap
 */
package net.minecraft.entity.ai.brain;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.entity.ai.brain.ScheduleRuleEntry;

public class ScheduleRule {
    private final List<ScheduleRuleEntry> entries = Lists.newArrayList();
    private int prioritizedEntryIndex;

    public ScheduleRule add(int startTime, float priority) {
        this.entries.add(new ScheduleRuleEntry(startTime, priority));
        this.sort();
        return this;
    }

    private void sort() {
        Int2ObjectAVLTreeMap int2ObjectAVLTreeMap = new Int2ObjectAVLTreeMap();
        this.entries.forEach(arg_0 -> ScheduleRule.method_19228((Int2ObjectSortedMap)int2ObjectAVLTreeMap, arg_0));
        this.entries.clear();
        this.entries.addAll((Collection<ScheduleRuleEntry>)int2ObjectAVLTreeMap.values());
        this.prioritizedEntryIndex = 0;
    }

    public float getPriority(int time) {
        if (this.entries.size() <= 0) {
            return 0.0f;
        }
        ScheduleRuleEntry scheduleRuleEntry = this.entries.get(this.prioritizedEntryIndex);
        _snowman = this.entries.get(this.entries.size() - 1);
        boolean _snowman2 = time < scheduleRuleEntry.getStartTime();
        int _snowman3 = _snowman2 ? 0 : this.prioritizedEntryIndex;
        float _snowman4 = _snowman2 ? _snowman.getPriority() : scheduleRuleEntry.getPriority();
        int _snowman5 = _snowman3;
        while (_snowman5 < this.entries.size() && (_snowman = this.entries.get(_snowman5)).getStartTime() <= time) {
            this.prioritizedEntryIndex = _snowman5++;
            _snowman4 = _snowman.getPriority();
        }
        return _snowman4;
    }

    private static /* synthetic */ void method_19228(Int2ObjectSortedMap int2ObjectSortedMap, ScheduleRuleEntry scheduleRuleEntry) {
        ScheduleRuleEntry cfr_ignored_0 = (ScheduleRuleEntry)int2ObjectSortedMap.put(scheduleRuleEntry.getStartTime(), (Object)scheduleRuleEntry);
    }
}

