package net.minecraft.client.gui.screen;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ChunkStatus;

public class LevelLoadingScreen extends Screen {
   private final WorldGenerationProgressTracker progressProvider;
   private long field_19101 = -1L;
   private static final Object2IntMap<ChunkStatus> STATUS_TO_COLOR = Util.make(new Object2IntOpenHashMap(), map -> {
      map.defaultReturnValue(0);
      map.put(ChunkStatus.EMPTY, 5526612);
      map.put(ChunkStatus.STRUCTURE_STARTS, 10066329);
      map.put(ChunkStatus.STRUCTURE_REFERENCES, 6250897);
      map.put(ChunkStatus.BIOMES, 8434258);
      map.put(ChunkStatus.NOISE, 13750737);
      map.put(ChunkStatus.SURFACE, 7497737);
      map.put(ChunkStatus.CARVERS, 7169628);
      map.put(ChunkStatus.LIQUID_CARVERS, 3159410);
      map.put(ChunkStatus.FEATURES, 2213376);
      map.put(ChunkStatus.LIGHT, 13421772);
      map.put(ChunkStatus.SPAWN, 15884384);
      map.put(ChunkStatus.HEIGHTMAPS, 15658734);
      map.put(ChunkStatus.FULL, 16777215);
   });

   public LevelLoadingScreen(WorldGenerationProgressTracker progressProvider) {
      super(NarratorManager.EMPTY);
      this.progressProvider = progressProvider;
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   public void removed() {
      NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.loading.done").getString());
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      String _snowman = MathHelper.clamp(this.progressProvider.getProgressPercentage(), 0, 100) + "%";
      long _snowmanx = Util.getMeasuringTimeMs();
      if (_snowmanx - this.field_19101 > 2000L) {
         this.field_19101 = _snowmanx;
         NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.loading", _snowman).getString());
      }

      int _snowmanxx = this.width / 2;
      int _snowmanxxx = this.height / 2;
      int _snowmanxxxx = 30;
      drawChunkMap(matrices, this.progressProvider, _snowmanxx, _snowmanxxx + 30, 2, 0);
      drawCenteredString(matrices, this.textRenderer, _snowman, _snowmanxx, _snowmanxxx - 9 / 2 - 30, 16777215);
   }

   public static void drawChunkMap(MatrixStack _snowman, WorldGenerationProgressTracker _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      int _snowmanxxxxxx = _snowman + _snowman;
      int _snowmanxxxxxxx = _snowman.getCenterSize();
      int _snowmanxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxx - _snowman;
      int _snowmanxxxxxxxxx = _snowman.getSize();
      int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxx - _snowman;
      int _snowmanxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxx / 2;
      int _snowmanxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxx / 2;
      int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx / 2 + 1;
      int _snowmanxxxxxxxxxxxxxx = -16772609;
      if (_snowman != 0) {
         fill(_snowman, _snowman - _snowmanxxxxxxxxxxxxx, _snowman - _snowmanxxxxxxxxxxxxx, _snowman - _snowmanxxxxxxxxxxxxx + 1, _snowman + _snowmanxxxxxxxxxxxxx, -16772609);
         fill(_snowman, _snowman + _snowmanxxxxxxxxxxxxx - 1, _snowman - _snowmanxxxxxxxxxxxxx, _snowman + _snowmanxxxxxxxxxxxxx, _snowman + _snowmanxxxxxxxxxxxxx, -16772609);
         fill(_snowman, _snowman - _snowmanxxxxxxxxxxxxx, _snowman - _snowmanxxxxxxxxxxxxx, _snowman + _snowmanxxxxxxxxxxxxx, _snowman - _snowmanxxxxxxxxxxxxx + 1, -16772609);
         fill(_snowman, _snowman - _snowmanxxxxxxxxxxxxx, _snowman + _snowmanxxxxxxxxxxxxx - 1, _snowman + _snowmanxxxxxxxxxxxxx, _snowman + _snowmanxxxxxxxxxxxxx, -16772609);
      }

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
            ChunkStatus _snowmanxxxxxxxxxxxxxxxxx = _snowman.getChunkStatus(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx * _snowmanxxxxxx;
            fill(
               _snowman,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx + _snowman,
               _snowmanxxxxxxxxxxxxxxxxxxx + _snowman,
               STATUS_TO_COLOR.getInt(_snowmanxxxxxxxxxxxxxxxxx) | 0xFF000000
            );
         }
      }
   }
}
