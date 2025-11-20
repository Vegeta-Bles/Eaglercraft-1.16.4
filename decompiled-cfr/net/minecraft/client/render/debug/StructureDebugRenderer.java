/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class StructureDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient field_4624;
    private final Map<DimensionType, Map<String, BlockBox>> field_4626 = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, BlockBox>> field_4627 = Maps.newIdentityHashMap();
    private final Map<DimensionType, Map<String, Boolean>> field_4625 = Maps.newIdentityHashMap();

    public StructureDebugRenderer(MinecraftClient minecraftClient) {
        this.field_4624 = minecraftClient;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        Camera camera = this.field_4624.gameRenderer.getCamera();
        ClientWorld _snowman2 = this.field_4624.world;
        DimensionType _snowman3 = _snowman2.getDimension();
        BlockPos _snowman4 = new BlockPos(camera.getPos().x, 0.0, camera.getPos().z);
        VertexConsumer _snowman5 = vertexConsumers.getBuffer(RenderLayer.getLines());
        if (this.field_4626.containsKey(_snowman3)) {
            for (BlockBox blockBox : this.field_4626.get(_snowman3).values()) {
                if (!_snowman4.isWithinDistance(blockBox.getCenter(), 500.0)) continue;
                WorldRenderer.drawBox(matrices, _snowman5, (double)blockBox.minX - cameraX, (double)blockBox.minY - cameraY, (double)blockBox.minZ - cameraZ, (double)(blockBox.maxX + 1) - cameraX, (double)(blockBox.maxY + 1) - cameraY, (double)(blockBox.maxZ + 1) - cameraZ, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        if (this.field_4627.containsKey(_snowman3)) {
            for (Map.Entry entry : this.field_4627.get(_snowman3).entrySet()) {
                String string = (String)entry.getKey();
                BlockBox _snowman6 = (BlockBox)entry.getValue();
                Boolean _snowman7 = this.field_4625.get(_snowman3).get(string);
                if (!_snowman4.isWithinDistance(_snowman6.getCenter(), 500.0)) continue;
                if (_snowman7.booleanValue()) {
                    WorldRenderer.drawBox(matrices, _snowman5, (double)_snowman6.minX - cameraX, (double)_snowman6.minY - cameraY, (double)_snowman6.minZ - cameraZ, (double)(_snowman6.maxX + 1) - cameraX, (double)(_snowman6.maxY + 1) - cameraY, (double)(_snowman6.maxZ + 1) - cameraZ, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f);
                    continue;
                }
                WorldRenderer.drawBox(matrices, _snowman5, (double)_snowman6.minX - cameraX, (double)_snowman6.minY - cameraY, (double)_snowman6.minZ - cameraZ, (double)(_snowman6.maxX + 1) - cameraX, (double)(_snowman6.maxY + 1) - cameraY, (double)(_snowman6.maxZ + 1) - cameraZ, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }

    public void method_3871(BlockBox blockBox, List<BlockBox> list, List<Boolean> list2, DimensionType dimensionType) {
        if (!this.field_4626.containsKey(dimensionType)) {
            this.field_4626.put(dimensionType, Maps.newHashMap());
        }
        if (!this.field_4627.containsKey(dimensionType)) {
            this.field_4627.put(dimensionType, Maps.newHashMap());
            this.field_4625.put(dimensionType, Maps.newHashMap());
        }
        this.field_4626.get(dimensionType).put(blockBox.toString(), blockBox);
        for (int i = 0; i < list.size(); ++i) {
            BlockBox blockBox2 = list.get(i);
            Boolean _snowman2 = list2.get(i);
            this.field_4627.get(dimensionType).put(blockBox2.toString(), blockBox2);
            this.field_4625.get(dimensionType).put(blockBox2.toString(), _snowman2);
        }
    }

    @Override
    public void clear() {
        this.field_4626.clear();
        this.field_4627.clear();
        this.field_4625.clear();
    }
}

