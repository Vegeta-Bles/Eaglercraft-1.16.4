package net.minecraft.entity.ai.pathing;

import net.minecraft.network.PacketByteBuf;

public class TargetPathNode extends PathNode {
   private float nearestNodeDistance = Float.MAX_VALUE;
   private PathNode nearestNode;
   private boolean reached;

   public TargetPathNode(PathNode node) {
      super(node.x, node.y, node.z);
   }

   public TargetPathNode(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   public void updateNearestNode(float distance, PathNode node) {
      if (distance < this.nearestNodeDistance) {
         this.nearestNodeDistance = distance;
         this.nearestNode = node;
      }
   }

   public PathNode getNearestNode() {
      return this.nearestNode;
   }

   public void markReached() {
      this.reached = true;
   }

   public static TargetPathNode fromBuffer(PacketByteBuf buffer) {
      TargetPathNode _snowman = new TargetPathNode(buffer.readInt(), buffer.readInt(), buffer.readInt());
      _snowman.pathLength = buffer.readFloat();
      _snowman.penalty = buffer.readFloat();
      _snowman.visited = buffer.readBoolean();
      _snowman.type = PathNodeType.values()[buffer.readInt()];
      _snowman.heapWeight = buffer.readFloat();
      return _snowman;
   }
}
