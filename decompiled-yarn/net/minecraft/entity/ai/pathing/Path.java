package net.minecraft.entity.ai.pathing;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Path {
   private final List<PathNode> nodes;
   private PathNode[] field_57 = new PathNode[0];
   private PathNode[] field_55 = new PathNode[0];
   private Set<TargetPathNode> field_20300;
   private int currentNodeIndex;
   private final BlockPos target;
   private final float manhattanDistanceFromTarget;
   private final boolean reachesTarget;

   public Path(List<PathNode> nodes, BlockPos target, boolean reachesTarget) {
      this.nodes = nodes;
      this.target = target;
      this.manhattanDistanceFromTarget = nodes.isEmpty() ? Float.MAX_VALUE : this.nodes.get(this.nodes.size() - 1).getManhattanDistance(this.target);
      this.reachesTarget = reachesTarget;
   }

   public void next() {
      this.currentNodeIndex++;
   }

   public boolean method_30849() {
      return this.currentNodeIndex <= 0;
   }

   public boolean isFinished() {
      return this.currentNodeIndex >= this.nodes.size();
   }

   @Nullable
   public PathNode getEnd() {
      return !this.nodes.isEmpty() ? this.nodes.get(this.nodes.size() - 1) : null;
   }

   public PathNode getNode(int index) {
      return this.nodes.get(index);
   }

   public void setLength(int length) {
      if (this.nodes.size() > length) {
         this.nodes.subList(length, this.nodes.size()).clear();
      }
   }

   public void setNode(int index, PathNode node) {
      this.nodes.set(index, node);
   }

   public int getLength() {
      return this.nodes.size();
   }

   public int getCurrentNodeIndex() {
      return this.currentNodeIndex;
   }

   public void setCurrentNodeIndex(int index) {
      this.currentNodeIndex = index;
   }

   public Vec3d getNodePosition(Entity entity, int index) {
      PathNode _snowman = this.nodes.get(index);
      double _snowmanx = (double)_snowman.x + (double)((int)(entity.getWidth() + 1.0F)) * 0.5;
      double _snowmanxx = (double)_snowman.y;
      double _snowmanxxx = (double)_snowman.z + (double)((int)(entity.getWidth() + 1.0F)) * 0.5;
      return new Vec3d(_snowmanx, _snowmanxx, _snowmanxxx);
   }

   public BlockPos method_31031(int _snowman) {
      return this.nodes.get(_snowman).getPos();
   }

   public Vec3d getNodePosition(Entity _snowman) {
      return this.getNodePosition(_snowman, this.currentNodeIndex);
   }

   public BlockPos method_31032() {
      return this.nodes.get(this.currentNodeIndex).getPos();
   }

   public PathNode method_29301() {
      return this.nodes.get(this.currentNodeIndex);
   }

   @Nullable
   public PathNode method_30850() {
      return this.currentNodeIndex > 0 ? this.nodes.get(this.currentNodeIndex - 1) : null;
   }

   public boolean equalsPath(@Nullable Path _snowman) {
      if (_snowman == null) {
         return false;
      } else if (_snowman.nodes.size() != this.nodes.size()) {
         return false;
      } else {
         for (int _snowmanx = 0; _snowmanx < this.nodes.size(); _snowmanx++) {
            PathNode _snowmanxx = this.nodes.get(_snowmanx);
            PathNode _snowmanxxx = _snowman.nodes.get(_snowmanx);
            if (_snowmanxx.x != _snowmanxxx.x || _snowmanxx.y != _snowmanxxx.y || _snowmanxx.z != _snowmanxxx.z) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean reachesTarget() {
      return this.reachesTarget;
   }

   public PathNode[] method_22880() {
      return this.field_57;
   }

   public PathNode[] method_22881() {
      return this.field_55;
   }

   public static Path fromBuffer(PacketByteBuf buffer) {
      boolean _snowman = buffer.readBoolean();
      int _snowmanx = buffer.readInt();
      int _snowmanxx = buffer.readInt();
      Set<TargetPathNode> _snowmanxxx = Sets.newHashSet();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx; _snowmanxxxx++) {
         _snowmanxxx.add(TargetPathNode.fromBuffer(buffer));
      }

      BlockPos _snowmanxxxx = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
      List<PathNode> _snowmanxxxxx = Lists.newArrayList();
      int _snowmanxxxxxx = buffer.readInt();

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
         _snowmanxxxxx.add(PathNode.fromBuffer(buffer));
      }

      PathNode[] _snowmanxxxxxxx = new PathNode[buffer.readInt()];

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx.length; _snowmanxxxxxxxx++) {
         _snowmanxxxxxxx[_snowmanxxxxxxxx] = PathNode.fromBuffer(buffer);
      }

      PathNode[] _snowmanxxxxxxxx = new PathNode[buffer.readInt()];

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx.length; _snowmanxxxxxxxxx++) {
         _snowmanxxxxxxxx[_snowmanxxxxxxxxx] = PathNode.fromBuffer(buffer);
      }

      Path _snowmanxxxxxxxxx = new Path(_snowmanxxxxx, _snowmanxxxx, _snowman);
      _snowmanxxxxxxxxx.field_57 = _snowmanxxxxxxx;
      _snowmanxxxxxxxxx.field_55 = _snowmanxxxxxxxx;
      _snowmanxxxxxxxxx.field_20300 = _snowmanxxx;
      _snowmanxxxxxxxxx.currentNodeIndex = _snowmanx;
      return _snowmanxxxxxxxxx;
   }

   @Override
   public String toString() {
      return "Path(length=" + this.nodes.size() + ")";
   }

   public BlockPos getTarget() {
      return this.target;
   }

   public float getManhattanDistanceFromTarget() {
      return this.manhattanDistanceFromTarget;
   }
}
