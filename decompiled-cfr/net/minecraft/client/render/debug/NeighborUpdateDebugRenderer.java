/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Ordering
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class NeighborUpdateDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient client;
    private final Map<Long, Map<BlockPos, Integer>> neighborUpdates = Maps.newTreeMap((Comparator)Ordering.natural().reverse());

    NeighborUpdateDebugRenderer(MinecraftClient client) {
        this.client = client;
    }

    public void addNeighborUpdate(long time, BlockPos pos) {
        Map map = this.neighborUpdates.computeIfAbsent(time, l -> Maps.newHashMap());
        int _snowman2 = map.getOrDefault(pos, 0);
        map.put(pos, _snowman2 + 1);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        long l = this.client.world.getTime();
        int _snowman2 = 200;
        double _snowman3 = 0.0025;
        HashSet _snowman4 = Sets.newHashSet();
        HashMap _snowman5 = Maps.newHashMap();
        VertexConsumer _snowman6 = vertexConsumers.getBuffer(RenderLayer.getLines());
        Iterator<Map.Entry<Long, Map<BlockPos, Integer>>> _snowman7 = this.neighborUpdates.entrySet().iterator();
        while (_snowman7.hasNext()) {
            Map.Entry<Long, Map<BlockPos, Integer>> entry = _snowman7.next();
            Long _snowman8 = entry.getKey();
            Map<BlockPos, Integer> _snowman9 = entry.getValue();
            long _snowman10 = l - _snowman8;
            if (_snowman10 > 200L) {
                _snowman7.remove();
                continue;
            }
            for (Map.Entry<BlockPos, Integer> entry2 : _snowman9.entrySet()) {
                BlockPos blockPos = entry2.getKey();
                Integer _snowman11 = entry2.getValue();
                if (!_snowman4.add(blockPos)) continue;
                Box _snowman12 = new Box(BlockPos.ORIGIN).expand(0.002).contract(0.0025 * (double)_snowman10).offset(blockPos.getX(), blockPos.getY(), blockPos.getZ()).offset(-cameraX, -cameraY, -cameraZ);
                WorldRenderer.drawBox(matrices, _snowman6, _snowman12.minX, _snowman12.minY, _snowman12.minZ, _snowman12.maxX, _snowman12.maxY, _snowman12.maxZ, 1.0f, 1.0f, 1.0f, 1.0f);
                _snowman5.put(blockPos, _snowman11);
            }
        }
        for (Map.Entry entry : _snowman5.entrySet()) {
            BlockPos blockPos = (BlockPos)entry.getKey();
            Integer _snowman13 = (Integer)entry.getValue();
            DebugRenderer.drawString(String.valueOf(_snowman13), blockPos.getX(), blockPos.getY(), blockPos.getZ(), -1);
        }
    }
}

