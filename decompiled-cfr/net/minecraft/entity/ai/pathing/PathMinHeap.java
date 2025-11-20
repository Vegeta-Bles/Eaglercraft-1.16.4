/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.pathing;

import net.minecraft.entity.ai.pathing.PathNode;

public class PathMinHeap {
    private PathNode[] pathNodes = new PathNode[128];
    private int count;

    public PathNode push(PathNode node) {
        if (node.heapIndex >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.count == this.pathNodes.length) {
            PathNode[] pathNodeArray = new PathNode[this.count << 1];
            System.arraycopy(this.pathNodes, 0, pathNodeArray, 0, this.count);
            this.pathNodes = pathNodeArray;
        }
        this.pathNodes[this.count] = node;
        node.heapIndex = this.count;
        this.shiftUp(this.count++);
        return node;
    }

    public void clear() {
        this.count = 0;
    }

    public PathNode pop() {
        PathNode pathNode = this.pathNodes[0];
        this.pathNodes[0] = this.pathNodes[--this.count];
        this.pathNodes[this.count] = null;
        if (this.count > 0) {
            this.shiftDown(0);
        }
        pathNode.heapIndex = -1;
        return pathNode;
    }

    public void setNodeWeight(PathNode node, float weight) {
        float f = node.heapWeight;
        node.heapWeight = weight;
        if (weight < f) {
            this.shiftUp(node.heapIndex);
        } else {
            this.shiftDown(node.heapIndex);
        }
    }

    private void shiftUp(int index) {
        PathNode pathNode = this.pathNodes[index];
        float _snowman2 = pathNode.heapWeight;
        while (index > 0) {
            int n = index - 1 >> 1;
            PathNode _snowman3 = this.pathNodes[n];
            if (!(_snowman2 < _snowman3.heapWeight)) break;
            this.pathNodes[index] = _snowman3;
            _snowman3.heapIndex = index;
            index = n;
        }
        this.pathNodes[index] = pathNode;
        pathNode.heapIndex = index;
    }

    private void shiftDown(int index) {
        PathNode pathNode = this.pathNodes[index];
        float _snowman2 = pathNode.heapWeight;
        while (true) {
            float _snowman5;
            int n = 1 + (index << 1);
            _snowman = n + 1;
            if (n >= this.count) break;
            PathNode _snowman3 = this.pathNodes[n];
            float _snowman4 = _snowman3.heapWeight;
            if (_snowman >= this.count) {
                PathNode pathNode2 = null;
                _snowman5 = Float.POSITIVE_INFINITY;
            } else {
                pathNode2 = this.pathNodes[_snowman];
                _snowman5 = pathNode2.heapWeight;
            }
            if (_snowman4 < _snowman5) {
                if (!(_snowman4 < _snowman2)) break;
                this.pathNodes[index] = _snowman3;
                _snowman3.heapIndex = index;
                index = n;
                continue;
            }
            if (!(_snowman5 < _snowman2)) break;
            this.pathNodes[index] = pathNode2;
            pathNode2.heapIndex = index;
            index = _snowman;
        }
        this.pathNodes[index] = pathNode;
        pathNode.heapIndex = index;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }
}

