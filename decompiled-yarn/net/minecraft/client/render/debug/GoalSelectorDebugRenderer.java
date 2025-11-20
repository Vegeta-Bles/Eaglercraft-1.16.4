package net.minecraft.client.render.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class GoalSelectorDebugRenderer implements DebugRenderer.Renderer {
   private final MinecraftClient client;
   private final Map<Integer, List<GoalSelectorDebugRenderer.GoalSelector>> goalSelectors = Maps.newHashMap();

   @Override
   public void clear() {
      this.goalSelectors.clear();
   }

   public void setGoalSelectorList(int _snowman, List<GoalSelectorDebugRenderer.GoalSelector> _snowman) {
      this.goalSelectors.put(_snowman, _snowman);
   }

   public GoalSelectorDebugRenderer(MinecraftClient _snowman) {
      this.client = _snowman;
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
      Camera _snowman = this.client.gameRenderer.getCamera();
      RenderSystem.pushMatrix();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      BlockPos _snowmanx = new BlockPos(_snowman.getPos().x, 0.0, _snowman.getPos().z);
      this.goalSelectors.forEach((_snowmanxx, _snowmanxxx) -> {
         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
            GoalSelectorDebugRenderer.GoalSelector _snowmanxxx = _snowmanxxx.get(_snowmanxxxx);
            if (_snowman.isWithinDistance(_snowmanxxx.pos, 160.0)) {
               double _snowmanxxxx = (double)_snowmanxxx.pos.getX() + 0.5;
               double _snowmanxxxxx = (double)_snowmanxxx.pos.getY() + 2.0 + (double)_snowmanxxxx * 0.25;
               double _snowmanxxxxxx = (double)_snowmanxxx.pos.getZ() + 0.5;
               int _snowmanxxxxxxx = _snowmanxxx.field_18785 ? -16711936 : -3355444;
               DebugRenderer.drawString(_snowmanxxx.name, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
            }
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

      public GoalSelector(BlockPos _snowman, int _snowman, String _snowman, boolean _snowman) {
         this.pos = _snowman;
         this.field_18783 = _snowman;
         this.name = _snowman;
         this.field_18785 = _snowman;
      }
   }
}
