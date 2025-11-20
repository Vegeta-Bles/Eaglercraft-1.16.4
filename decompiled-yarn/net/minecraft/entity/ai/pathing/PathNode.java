package net.minecraft.entity.ai.pathing;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PathNode {
   public final int x;
   public final int y;
   public final int z;
   private final int hashCode;
   public int heapIndex = -1;
   public float penalizedPathLength;
   public float distanceToNearestTarget;
   public float heapWeight;
   public PathNode previous;
   public boolean visited;
   public float pathLength;
   public float penalty;
   public PathNodeType type = PathNodeType.BLOCKED;

   public PathNode(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.hashCode = hash(x, y, z);
   }

   public PathNode copyWithNewPosition(int x, int y, int z) {
      PathNode _snowman = new PathNode(x, y, z);
      _snowman.heapIndex = this.heapIndex;
      _snowman.penalizedPathLength = this.penalizedPathLength;
      _snowman.distanceToNearestTarget = this.distanceToNearestTarget;
      _snowman.heapWeight = this.heapWeight;
      _snowman.previous = this.previous;
      _snowman.visited = this.visited;
      _snowman.pathLength = this.pathLength;
      _snowman.penalty = this.penalty;
      _snowman.type = this.type;
      return _snowman;
   }

   public static int hash(int x, int y, int z) {
      return y & 0xFF | (x & 32767) << 8 | (z & 32767) << 24 | (x < 0 ? Integer.MIN_VALUE : 0) | (z < 0 ? 32768 : 0);
   }

   public float getDistance(PathNode node) {
      float _snowman = (float)(node.x - this.x);
      float _snowmanx = (float)(node.y - this.y);
      float _snowmanxx = (float)(node.z - this.z);
      return MathHelper.sqrt(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx);
   }

   public float getSquaredDistance(PathNode node) {
      float _snowman = (float)(node.x - this.x);
      float _snowmanx = (float)(node.y - this.y);
      float _snowmanxx = (float)(node.z - this.z);
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public float getManhattanDistance(PathNode node) {
      float _snowman = (float)Math.abs(node.x - this.x);
      float _snowmanx = (float)Math.abs(node.y - this.y);
      float _snowmanxx = (float)Math.abs(node.z - this.z);
      return _snowman + _snowmanx + _snowmanxx;
   }

   public float getManhattanDistance(BlockPos pos) {
      float _snowman = (float)Math.abs(pos.getX() - this.x);
      float _snowmanx = (float)Math.abs(pos.getY() - this.y);
      float _snowmanxx = (float)Math.abs(pos.getZ() - this.z);
      return _snowman + _snowmanx + _snowmanxx;
   }

   public BlockPos getPos() {
      return new BlockPos(this.x, this.y, this.z);
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof PathNode)) {
         return false;
      } else {
         PathNode _snowman = (PathNode)o;
         return this.hashCode == _snowman.hashCode && this.x == _snowman.x && this.y == _snowman.y && this.z == _snowman.z;
      }
   }

   @Override
   public int hashCode() {
      return this.hashCode;
   }

   public boolean isInHeap() {
      return this.heapIndex >= 0;
   }

   @Override
   public String toString() {
      return "Node{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
   }

   public static PathNode fromBuffer(PacketByteBuf buffer) {
      PathNode _snowman = new PathNode(buffer.readInt(), buffer.readInt(), buffer.readInt());
      _snowman.pathLength = buffer.readFloat();
      _snowman.penalty = buffer.readFloat();
      _snowman.visited = buffer.readBoolean();
      _snowman.type = PathNodeType.values()[buffer.readInt()];
      _snowman.heapWeight = buffer.readFloat();
      return _snowman;
   }
}
