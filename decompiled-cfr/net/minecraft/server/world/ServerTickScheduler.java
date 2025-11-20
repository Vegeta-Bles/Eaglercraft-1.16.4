/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
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
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
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

public class ServerTickScheduler<T>
implements TickScheduler<T> {
    protected final Predicate<T> invalidObjPredicate;
    private final Function<T, Identifier> idToName;
    private final Set<ScheduledTick<T>> scheduledTickActions = Sets.newHashSet();
    private final TreeSet<ScheduledTick<T>> scheduledTickActionsInOrder = Sets.newTreeSet(ScheduledTick.getComparator());
    private final ServerWorld world;
    private final Queue<ScheduledTick<T>> currentTickActions = Queues.newArrayDeque();
    private final List<ScheduledTick<T>> consumedTickActions = Lists.newArrayList();
    private final Consumer<ScheduledTick<T>> tickConsumer;

    public ServerTickScheduler(ServerWorld world, Predicate<T> invalidObjPredicate, Function<T, Identifier> idToName, Consumer<ScheduledTick<T>> consumer) {
        this.invalidObjPredicate = invalidObjPredicate;
        this.idToName = idToName;
        this.world = world;
        this.tickConsumer = consumer;
    }

    public void tick() {
        ScheduledTick<T> scheduledTick;
        int n = this.scheduledTickActionsInOrder.size();
        if (n != this.scheduledTickActions.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (n > 65536) {
            n = 65536;
        }
        ServerChunkManager _snowman2 = this.world.getChunkManager();
        Iterator<ScheduledTick<T>> _snowman3 = this.scheduledTickActionsInOrder.iterator();
        this.world.getProfiler().push("cleaning");
        while (n > 0 && _snowman3.hasNext()) {
            scheduledTick = _snowman3.next();
            if (scheduledTick.time > this.world.getTime()) break;
            if (!_snowman2.shouldTickBlock(scheduledTick.pos)) continue;
            _snowman3.remove();
            this.scheduledTickActions.remove(scheduledTick);
            this.currentTickActions.add(scheduledTick);
            --n;
        }
        this.world.getProfiler().swap("ticking");
        while ((scheduledTick = this.currentTickActions.poll()) != null) {
            if (_snowman2.shouldTickBlock(scheduledTick.pos)) {
                try {
                    this.consumedTickActions.add(scheduledTick);
                    this.tickConsumer.accept(scheduledTick);
                    continue;
                }
                catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.create(throwable, "Exception while ticking");
                    CrashReportSection _snowman4 = crashReport.addElement("Block being ticked");
                    CrashReportSection.addBlockInfo(_snowman4, scheduledTick.pos, null);
                    throw new CrashException(crashReport);
                }
            }
            this.schedule(scheduledTick.pos, scheduledTick.getObject(), 0);
        }
        this.world.getProfiler().pop();
        this.consumedTickActions.clear();
        this.currentTickActions.clear();
    }

    @Override
    public boolean isTicking(BlockPos pos, T object) {
        return this.currentTickActions.contains(new ScheduledTick<T>(pos, object));
    }

    public List<ScheduledTick<T>> getScheduledTicksInChunk(ChunkPos chunkPos, boolean updateState, boolean getStaleTicks) {
        int n = (chunkPos.x << 4) - 2;
        _snowman = n + 16 + 2;
        _snowman = (chunkPos.z << 4) - 2;
        _snowman = _snowman + 16 + 2;
        return this.getScheduledTicks(new BlockBox(n, 0, _snowman, _snowman, 256, _snowman), updateState, getStaleTicks);
    }

    public List<ScheduledTick<T>> getScheduledTicks(BlockBox bounds, boolean updateState, boolean getStaleTicks) {
        List<ScheduledTick<T>> list = this.transferTicksInBounds(null, this.scheduledTickActionsInOrder, bounds, updateState);
        if (updateState && list != null) {
            this.scheduledTickActions.removeAll(list);
        }
        list = this.transferTicksInBounds(list, this.currentTickActions, bounds, updateState);
        if (!getStaleTicks) {
            list = this.transferTicksInBounds(list, this.consumedTickActions, bounds, updateState);
        }
        return list == null ? Collections.emptyList() : list;
    }

    @Nullable
    private List<ScheduledTick<T>> transferTicksInBounds(@Nullable List<ScheduledTick<T>> dst, Collection<ScheduledTick<T>> src, BlockBox bounds, boolean move) {
        Iterator<ScheduledTick<T>> iterator = src.iterator();
        while (iterator.hasNext()) {
            ScheduledTick<T> scheduledTick = iterator.next();
            BlockPos _snowman2 = scheduledTick.pos;
            if (_snowman2.getX() < bounds.minX || _snowman2.getX() >= bounds.maxX || _snowman2.getZ() < bounds.minZ || _snowman2.getZ() >= bounds.maxZ) continue;
            if (move) {
                iterator.remove();
            }
            if (dst == null) {
                dst = Lists.newArrayList();
            }
            dst.add(scheduledTick);
        }
        return dst;
    }

    public void copyScheduledTicks(BlockBox box, BlockPos offset) {
        List<ScheduledTick<T>> list = this.getScheduledTicks(box, false, false);
        for (ScheduledTick<T> scheduledTick : list) {
            if (!box.contains(scheduledTick.pos)) continue;
            BlockPos blockPos = scheduledTick.pos.add(offset);
            T _snowman2 = scheduledTick.getObject();
            this.addScheduledTick(new ScheduledTick<T>(blockPos, _snowman2, scheduledTick.time, scheduledTick.priority));
        }
    }

    public ListTag toTag(ChunkPos chunkPos) {
        List<ScheduledTick<T>> list = this.getScheduledTicksInChunk(chunkPos, false, true);
        return ServerTickScheduler.serializeScheduledTicks(this.idToName, list, this.world.getTime());
    }

    private static <T> ListTag serializeScheduledTicks(Function<T, Identifier> identifierProvider, Iterable<ScheduledTick<T>> scheduledTicks, long time) {
        ListTag listTag = new ListTag();
        for (ScheduledTick<T> scheduledTick : scheduledTicks) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("i", identifierProvider.apply(scheduledTick.getObject()).toString());
            compoundTag.putInt("x", scheduledTick.pos.getX());
            compoundTag.putInt("y", scheduledTick.pos.getY());
            compoundTag.putInt("z", scheduledTick.pos.getZ());
            compoundTag.putInt("t", (int)(scheduledTick.time - time));
            compoundTag.putInt("p", scheduledTick.priority.getIndex());
            listTag.add(compoundTag);
        }
        return listTag;
    }

    @Override
    public boolean isScheduled(BlockPos pos, T object) {
        return this.scheduledTickActions.contains(new ScheduledTick<T>(pos, object));
    }

    @Override
    public void schedule(BlockPos pos, T object, int delay, TickPriority priority) {
        if (!this.invalidObjPredicate.test(object)) {
            this.addScheduledTick(new ScheduledTick<T>(pos, object, (long)delay + this.world.getTime(), priority));
        }
    }

    private void addScheduledTick(ScheduledTick<T> scheduledTick) {
        if (!this.scheduledTickActions.contains(scheduledTick)) {
            this.scheduledTickActions.add(scheduledTick);
            this.scheduledTickActionsInOrder.add(scheduledTick);
        }
    }

    public int getTicks() {
        return this.scheduledTickActions.size();
    }
}

