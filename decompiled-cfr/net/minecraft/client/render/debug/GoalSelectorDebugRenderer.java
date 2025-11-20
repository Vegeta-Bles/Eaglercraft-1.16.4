/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class GoalSelectorDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient client;
    private final Map<Integer, List<GoalSelector>> goalSelectors = Maps.newHashMap();

    @Override
    public void clear() {
        this.goalSelectors.clear();
    }

    public void setGoalSelectorList(int n, List<GoalSelector> list) {
        this.goalSelectors.put(n, list);
    }

    public GoalSelectorDebugRenderer(MinecraftClient minecraftClient) {
        this.client = minecraftClient;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        Camera camera = this.client.gameRenderer.getCamera();
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        BlockPos _snowman2 = new BlockPos(camera.getPos().x, 0.0, camera.getPos().z);
        this.goalSelectors.forEach((n, list) -> {
            for (int i = 0; i < list.size(); ++i) {
                GoalSelector goalSelector = (GoalSelector)list.get(i);
                if (!_snowman2.isWithinDistance(goalSelector.pos, 160.0)) continue;
                double _snowman2 = (double)goalSelector.pos.getX() + 0.5;
                double _snowman3 = (double)goalSelector.pos.getY() + 2.0 + (double)i * 0.25;
                double _snowman4 = (double)goalSelector.pos.getZ() + 0.5;
                int _snowman5 = goalSelector.field_18785 ? -16711936 : -3355444;
                DebugRenderer.drawString(goalSelector.name, _snowman2, _snowman3, _snowman4, _snowman5);
            }
        });
        RenderSystem.enableDepthTest();
        RenderSystem.enableTexture();
        RenderSystem.popMatrix();
    }

    public static class GoalSelector {
        public final BlockPos pos;
        public final int field_18783;
        public final String name;
        public final boolean field_18785;

        public GoalSelector(BlockPos blockPos, int n, String string, boolean bl) {
            this.pos = blockPos;
            this.field_18783 = n;
            this.name = string;
            this.field_18785 = bl;
        }
    }
}

