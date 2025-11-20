/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.pathing;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.TargetPathNode;
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
        ++this.currentNodeIndex;
    }

    public boolean method_30849() {
        return this.currentNodeIndex <= 0;
    }

    public boolean isFinished() {
        return this.currentNodeIndex >= this.nodes.size();
    }

    @Nullable
    public PathNode getEnd() {
        if (!this.nodes.isEmpty()) {
            return this.nodes.get(this.nodes.size() - 1);
        }
        return null;
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
        PathNode pathNode = this.nodes.get(index);
        double _snowman2 = (double)pathNode.x + (double)((int)(entity.getWidth() + 1.0f)) * 0.5;
        double _snowman3 = pathNode.y;
        double _snowman4 = (double)pathNode.z + (double)((int)(entity.getWidth() + 1.0f)) * 0.5;
        return new Vec3d(_snowman2, _snowman3, _snowman4);
    }

    public BlockPos method_31031(int n) {
        return this.nodes.get(n).getPos();
    }

    public Vec3d getNodePosition(Entity entity) {
        return this.getNodePosition(entity, this.currentNodeIndex);
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

    public boolean equalsPath(@Nullable Path path) {
        if (path == null) {
            return false;
        }
        if (path.nodes.size() != this.nodes.size()) {
            return false;
        }
        for (int i = 0; i < this.nodes.size(); ++i) {
            PathNode pathNode = this.nodes.get(i);
            _snowman = path.nodes.get(i);
            if (pathNode.x == _snowman.x && pathNode.y == _snowman.y && pathNode.z == _snowman.z) continue;
            return false;
        }
        return true;
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
        boolean bl = buffer.readBoolean();
        int _snowman2 = buffer.readInt();
        int _snowman3 = buffer.readInt();
        HashSet _snowman4 = Sets.newHashSet();
        for (int i = 0; i < _snowman3; ++i) {
            _snowman4.add(TargetPathNode.fromBuffer(buffer));
        }
        BlockPos blockPos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
        ArrayList _snowman5 = Lists.newArrayList();
        int _snowman6 = buffer.readInt();
        for (int i = 0; i < _snowman6; ++i) {
            _snowman5.add(PathNode.fromBuffer(buffer));
        }
        PathNode[] pathNodeArray = new PathNode[buffer.readInt()];
        for (int i = 0; i < pathNodeArray.length; ++i) {
            pathNodeArray[i] = PathNode.fromBuffer(buffer);
        }
        PathNode[] pathNodeArray2 = new PathNode[buffer.readInt()];
        for (int i = 0; i < pathNodeArray2.length; ++i) {
            pathNodeArray2[i] = PathNode.fromBuffer(buffer);
        }
        Path _snowman7 = new Path(_snowman5, blockPos, bl);
        _snowman7.field_57 = pathNodeArray;
        _snowman7.field_55 = pathNodeArray2;
        _snowman7.field_20300 = _snowman4;
        _snowman7.currentNodeIndex = _snowman2;
        return _snowman7;
    }

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

