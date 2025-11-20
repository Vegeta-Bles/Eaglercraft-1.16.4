/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.pathing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathMinHeap;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkCache;

public class PathNodeNavigator {
    private final PathNode[] successors = new PathNode[32];
    private final int range;
    private final PathNodeMaker pathNodeMaker;
    private final PathMinHeap minHeap = new PathMinHeap();

    public PathNodeNavigator(PathNodeMaker pathNodeMaker, int range) {
        this.pathNodeMaker = pathNodeMaker;
        this.range = range;
    }

    @Nullable
    public Path findPathToAny(ChunkCache world, MobEntity mob, Set<BlockPos> positions, float followRange, int distance, float rangeMultiplier) {
        this.minHeap.clear();
        this.pathNodeMaker.init(world, mob);
        PathNode pathNode = this.pathNodeMaker.getStart();
        Map<TargetPathNode, BlockPos> _snowman2 = positions.stream().collect(Collectors.toMap(blockPos -> this.pathNodeMaker.getNode((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()), Function.identity()));
        Path _snowman3 = this.findPathToAny(pathNode, _snowman2, followRange, distance, rangeMultiplier);
        this.pathNodeMaker.clear();
        return _snowman3;
    }

    @Nullable
    private Path findPathToAny(PathNode startNode, Map<TargetPathNode, BlockPos> positions, float followRange, int distance, float rangeMultiplier) {
        Set<TargetPathNode> set = positions.keySet();
        startNode.penalizedPathLength = 0.0f;
        startNode.heapWeight = startNode.distanceToNearestTarget = this.calculateDistances(startNode, set);
        this.minHeap.clear();
        this.minHeap.push(startNode);
        ImmutableSet _snowman2 = ImmutableSet.of();
        int _snowman3 = 0;
        HashSet _snowman4 = Sets.newHashSetWithExpectedSize((int)set.size());
        int _snowman5 = (int)((float)this.range * rangeMultiplier);
        while (!this.minHeap.isEmpty() && ++_snowman3 < _snowman5) {
            Object object = this.minHeap.pop();
            ((PathNode)object).visited = true;
            for (TargetPathNode targetPathNode2 : set) {
                if (!(((PathNode)object).getManhattanDistance(targetPathNode2) <= (float)distance)) continue;
                targetPathNode2.markReached();
                _snowman4.add(targetPathNode2);
            }
            if (!_snowman4.isEmpty()) break;
            if (((PathNode)object).getDistance(startNode) >= followRange) continue;
            int _snowman6 = this.pathNodeMaker.getSuccessors(this.successors, (PathNode)object);
            for (int i = 0; i < _snowman6; ++i) {
                PathNode pathNode = this.successors[i];
                float _snowman7 = ((PathNode)object).getDistance(pathNode);
                pathNode.pathLength = ((PathNode)object).pathLength + _snowman7;
                float _snowman8 = ((PathNode)object).penalizedPathLength + _snowman7 + pathNode.penalty;
                if (!(pathNode.pathLength < followRange) || pathNode.isInHeap() && !(_snowman8 < pathNode.penalizedPathLength)) continue;
                pathNode.previous = object;
                pathNode.penalizedPathLength = _snowman8;
                pathNode.distanceToNearestTarget = this.calculateDistances(pathNode, set) * 1.5f;
                if (pathNode.isInHeap()) {
                    this.minHeap.setNodeWeight(pathNode, pathNode.penalizedPathLength + pathNode.distanceToNearestTarget);
                    continue;
                }
                pathNode.heapWeight = pathNode.penalizedPathLength + pathNode.distanceToNearestTarget;
                this.minHeap.push(pathNode);
            }
        }
        Object object = object = !_snowman4.isEmpty() ? _snowman4.stream().map(targetPathNode -> this.createPath(targetPathNode.getNearestNode(), (BlockPos)positions.get(targetPathNode), true)).min(Comparator.comparingInt(Path::getLength)) : set.stream().map(targetPathNode -> this.createPath(targetPathNode.getNearestNode(), (BlockPos)positions.get(targetPathNode), false)).min(Comparator.comparingDouble(Path::getManhattanDistanceFromTarget).thenComparingInt(Path::getLength));
        if (!((Optional)object).isPresent()) {
            return null;
        }
        Path path = (Path)((Optional)object).get();
        return path;
    }

    private float calculateDistances(PathNode node, Set<TargetPathNode> targets) {
        float f = Float.MAX_VALUE;
        for (TargetPathNode targetPathNode : targets) {
            float f2 = node.getDistance(targetPathNode);
            targetPathNode.updateNearestNode(f2, node);
            f = Math.min(f2, f);
        }
        return f;
    }

    private Path createPath(PathNode endNode, BlockPos target, boolean reachesTarget) {
        ArrayList arrayList = Lists.newArrayList();
        PathNode _snowman2 = endNode;
        arrayList.add(0, _snowman2);
        while (_snowman2.previous != null) {
            _snowman2 = _snowman2.previous;
            arrayList.add(0, _snowman2);
        }
        return new Path(arrayList, target, reachesTarget);
    }
}

