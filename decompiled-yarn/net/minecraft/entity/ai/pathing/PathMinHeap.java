package net.minecraft.entity.ai.pathing;

public class PathMinHeap {
   private PathNode[] pathNodes = new PathNode[128];
   private int count;

   public PathMinHeap() {
   }

   public PathNode push(PathNode node) {
      if (node.heapIndex >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      } else {
         if (this.count == this.pathNodes.length) {
            PathNode[] _snowman = new PathNode[this.count << 1];
            System.arraycopy(this.pathNodes, 0, _snowman, 0, this.count);
            this.pathNodes = _snowman;
         }

         this.pathNodes[this.count] = node;
         node.heapIndex = this.count;
         this.shiftUp(this.count++);
         return node;
      }
   }

   public void clear() {
      this.count = 0;
   }

   public PathNode pop() {
      PathNode _snowman = this.pathNodes[0];
      this.pathNodes[0] = this.pathNodes[--this.count];
      this.pathNodes[this.count] = null;
      if (this.count > 0) {
         this.shiftDown(0);
      }

      _snowman.heapIndex = -1;
      return _snowman;
   }

   public void setNodeWeight(PathNode node, float weight) {
      float _snowman = node.heapWeight;
      node.heapWeight = weight;
      if (weight < _snowman) {
         this.shiftUp(node.heapIndex);
      } else {
         this.shiftDown(node.heapIndex);
      }
   }

   private void shiftUp(int index) {
      PathNode _snowman = this.pathNodes[index];
      float _snowmanx = _snowman.heapWeight;

      while (index > 0) {
         int _snowmanxx = index - 1 >> 1;
         PathNode _snowmanxxx = this.pathNodes[_snowmanxx];
         if (!(_snowmanx < _snowmanxxx.heapWeight)) {
            break;
         }

         this.pathNodes[index] = _snowmanxxx;
         _snowmanxxx.heapIndex = index;
         index = _snowmanxx;
      }

      this.pathNodes[index] = _snowman;
      _snowman.heapIndex = index;
   }

   private void shiftDown(int index) {
      PathNode _snowman = this.pathNodes[index];
      float _snowmanx = _snowman.heapWeight;

      while (true) {
         int _snowmanxx = 1 + (index << 1);
         int _snowmanxxx = _snowmanxx + 1;
         if (_snowmanxx >= this.count) {
            break;
         }

         PathNode _snowmanxxxx = this.pathNodes[_snowmanxx];
         float _snowmanxxxxx = _snowmanxxxx.heapWeight;
         PathNode _snowmanxxxxxx;
         float _snowmanxxxxxxx;
         if (_snowmanxxx >= this.count) {
            _snowmanxxxxxx = null;
            _snowmanxxxxxxx = Float.POSITIVE_INFINITY;
         } else {
            _snowmanxxxxxx = this.pathNodes[_snowmanxxx];
            _snowmanxxxxxxx = _snowmanxxxxxx.heapWeight;
         }

         if (_snowmanxxxxx < _snowmanxxxxxxx) {
            if (!(_snowmanxxxxx < _snowmanx)) {
               break;
            }

            this.pathNodes[index] = _snowmanxxxx;
            _snowmanxxxx.heapIndex = index;
            index = _snowmanxx;
         } else {
            if (!(_snowmanxxxxxxx < _snowmanx)) {
               break;
            }

            this.pathNodes[index] = _snowmanxxxxxx;
            _snowmanxxxxxx.heapIndex = index;
            index = _snowmanxxx;
         }
      }

      this.pathNodes[index] = _snowman;
      _snowman.heapIndex = index;
   }

   public boolean isEmpty() {
      return this.count == 0;
   }
}
