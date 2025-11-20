package net.minecraft.entity.ai.pathing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
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
      PathNode _snowman = this.pathNodeMaker.getStart();
      Map<TargetPathNode, BlockPos> _snowmanx = positions.stream()
         .collect(Collectors.toMap(_snowmanxx -> this.pathNodeMaker.getNode((double)_snowmanxx.getX(), (double)_snowmanxx.getY(), (double)_snowmanxx.getZ()), Function.identity()));
      Path _snowmanxx = this.findPathToAny(_snowman, _snowmanx, followRange, distance, rangeMultiplier);
      this.pathNodeMaker.clear();
      return _snowmanxx;
   }

   @Nullable
   private Path findPathToAny(PathNode startNode, Map<TargetPathNode, BlockPos> positions, float followRange, int distance, float rangeMultiplier) {
      Set<TargetPathNode> _snowman = positions.keySet();
      startNode.penalizedPathLength = 0.0F;
      startNode.distanceToNearestTarget = this.calculateDistances(startNode, _snowman);
      startNode.heapWeight = startNode.distanceToNearestTarget;
      this.minHeap.clear();
      this.minHeap.push(startNode);
      Set<PathNode> _snowmanx = ImmutableSet.of();
      int _snowmanxx = 0;
      Set<TargetPathNode> _snowmanxxx = Sets.newHashSetWithExpectedSize(_snowman.size());
      int _snowmanxxxx = (int)((float)this.range * rangeMultiplier);

      while (!this.minHeap.isEmpty()) {
         if (++_snowmanxx >= _snowmanxxxx) {
            break;
         }

         PathNode _snowmanxxxxx = this.minHeap.pop();
         _snowmanxxxxx.visited = true;

         for (TargetPathNode _snowmanxxxxxx : _snowman) {
            if (_snowmanxxxxx.getManhattanDistance(_snowmanxxxxxx) <= (float)distance) {
               _snowmanxxxxxx.markReached();
               _snowmanxxx.add(_snowmanxxxxxx);
            }
         }

         if (!_snowmanxxx.isEmpty()) {
            break;
         }

         if (!(_snowmanxxxxx.getDistance(startNode) >= followRange)) {
            int _snowmanxxxxxxx = this.pathNodeMaker.getSuccessors(this.successors, _snowmanxxxxx);

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxx++) {
               PathNode _snowmanxxxxxxxxx = this.successors[_snowmanxxxxxxxx];
               float _snowmanxxxxxxxxxx = _snowmanxxxxx.getDistance(_snowmanxxxxxxxxx);
               _snowmanxxxxxxxxx.pathLength = _snowmanxxxxx.pathLength + _snowmanxxxxxxxxxx;
               float _snowmanxxxxxxxxxxx = _snowmanxxxxx.penalizedPathLength + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx.penalty;
               if (_snowmanxxxxxxxxx.pathLength < followRange && (!_snowmanxxxxxxxxx.isInHeap() || _snowmanxxxxxxxxxxx < _snowmanxxxxxxxxx.penalizedPathLength)) {
                  _snowmanxxxxxxxxx.previous = _snowmanxxxxx;
                  _snowmanxxxxxxxxx.penalizedPathLength = _snowmanxxxxxxxxxxx;
                  _snowmanxxxxxxxxx.distanceToNearestTarget = this.calculateDistances(_snowmanxxxxxxxxx, _snowman) * 1.5F;
                  if (_snowmanxxxxxxxxx.isInHeap()) {
                     this.minHeap.setNodeWeight(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.penalizedPathLength + _snowmanxxxxxxxxx.distanceToNearestTarget);
                  } else {
                     _snowmanxxxxxxxxx.heapWeight = _snowmanxxxxxxxxx.penalizedPathLength + _snowmanxxxxxxxxx.distanceToNearestTarget;
                     this.minHeap.push(_snowmanxxxxxxxxx);
                  }
               }
            }
         }
      }

      Optional<Path> _snowmanxxxxx = !_snowmanxxx.isEmpty()
         ? _snowmanxxx.stream()
            .map(_snowmanxxxxxxx -> this.createPath(_snowmanxxxxxxx.getNearestNode(), positions.get(_snowmanxxxxxxx), true))
            .min(Comparator.comparingInt(Path::getLength))
         : _snowman.stream()
            .map(_snowmanxxxxxxx -> this.createPath(_snowmanxxxxxxx.getNearestNode(), positions.get(_snowmanxxxxxxx), false))
            .min(Comparator.comparingDouble(Path::getManhattanDistanceFromTarget).thenComparingInt(Path::getLength));
      return !_snowmanxxxxx.isPresent() ? null : _snowmanxxxxx.get();
   }

   private float calculateDistances(PathNode node, Set<TargetPathNode> targets) {
      float _snowman = Float.MAX_VALUE;

      for (TargetPathNode _snowmanx : targets) {
         float _snowmanxx = node.getDistance(_snowmanx);
         _snowmanx.updateNearestNode(_snowmanxx, node);
         _snowman = Math.min(_snowmanxx, _snowman);
      }

      return _snowman;
   }

   private Path createPath(PathNode endNode, BlockPos target, boolean reachesTarget) {
      List<PathNode> _snowman = Lists.newArrayList();
      PathNode _snowmanx = endNode;
      _snowman.add(0, endNode);

      while (_snowmanx.previous != null) {
         _snowmanx = _snowmanx.previous;
         _snowman.add(0, _snowmanx);
      }

      return new Path(_snowman, target, reachesTarget);
   }
}
