package net.minecraft.client.gui.screen.world;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.updater.WorldUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptimizeWorldScreen extends Screen {
   private static final Logger field_25482 = LogManager.getLogger();
   private static final Object2IntMap<RegistryKey<World>> DIMENSION_COLORS = Util.make(new Object2IntOpenCustomHashMap(Util.identityHashStrategy()), _snowman -> {
      _snowman.put(World.OVERWORLD, -13408734);
      _snowman.put(World.NETHER, -10075085);
      _snowman.put(World.END, -8943531);
      _snowman.defaultReturnValue(-2236963);
   });
   private final BooleanConsumer callback;
   private final WorldUpdater updater;

   @Nullable
   public static OptimizeWorldScreen method_27031(MinecraftClient _snowman, BooleanConsumer _snowman, DataFixer _snowman, LevelStorage.Session _snowman, boolean _snowman) {
      DynamicRegistryManager.Impl _snowmanxxxxx = DynamicRegistryManager.create();

      try (MinecraftClient.IntegratedResourceManager _snowmanxxxxxx = _snowman.method_29604(
            _snowmanxxxxx, MinecraftClient::method_29598, MinecraftClient::createSaveProperties, false, _snowman
         )) {
         SaveProperties _snowmanxxxxxxx = _snowmanxxxxxx.getSaveProperties();
         _snowman.backupLevelDataFile(_snowmanxxxxx, _snowmanxxxxxxx);
         ImmutableSet<RegistryKey<World>> _snowmanxxxxxxxx = _snowmanxxxxxxx.getGeneratorOptions().getWorlds();
         return new OptimizeWorldScreen(_snowman, _snowman, _snowman, _snowmanxxxxxxx.getLevelInfo(), _snowman, _snowmanxxxxxxxx);
      } catch (Exception var22) {
         field_25482.warn("Failed to load datapacks, can't optimize world", var22);
         return null;
      }
   }

   private OptimizeWorldScreen(BooleanConsumer callback, DataFixer _snowman, LevelStorage.Session _snowman, LevelInfo _snowman, boolean _snowman, ImmutableSet<RegistryKey<World>> _snowman) {
      super(new TranslatableText("optimizeWorld.title", _snowman.getLevelName()));
      this.callback = callback;
      this.updater = new WorldUpdater(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void init() {
      super.init();
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 150, 200, 20, ScreenTexts.CANCEL, _snowman -> {
         this.updater.cancel();
         this.callback.accept(false);
      }));
   }

   @Override
   public void tick() {
      if (this.updater.isDone()) {
         this.callback.accept(true);
      }
   }

   @Override
   public void onClose() {
      this.callback.accept(false);
   }

   @Override
   public void removed() {
      this.updater.cancel();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
      int _snowman = this.width / 2 - 150;
      int _snowmanx = this.width / 2 + 150;
      int _snowmanxx = this.height / 4 + 100;
      int _snowmanxxx = _snowmanxx + 10;
      drawCenteredText(matrices, this.textRenderer, this.updater.getStatus(), this.width / 2, _snowmanxx - 9 - 2, 10526880);
      if (this.updater.getTotalChunkCount() > 0) {
         fill(matrices, _snowman - 1, _snowmanxx - 1, _snowmanx + 1, _snowmanxxx + 1, -16777216);
         drawTextWithShadow(
            matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.converted", this.updater.getUpgradedChunkCount()), _snowman, 40, 10526880
         );
         drawTextWithShadow(
            matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.skipped", this.updater.getSkippedChunkCount()), _snowman, 40 + 9 + 3, 10526880
         );
         drawTextWithShadow(
            matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.total", this.updater.getTotalChunkCount()), _snowman, 40 + (9 + 3) * 2, 10526880
         );
         int _snowmanxxxx = 0;
         UnmodifiableIterator var10 = this.updater.method_28304().iterator();

         while (var10.hasNext()) {
            RegistryKey<World> _snowmanxxxxx = (RegistryKey<World>)var10.next();
            int _snowmanxxxxxx = MathHelper.floor(this.updater.getProgress(_snowmanxxxxx) * (float)(_snowmanx - _snowman));
            fill(matrices, _snowman + _snowmanxxxx, _snowmanxx, _snowman + _snowmanxxxx + _snowmanxxxxxx, _snowmanxxx, DIMENSION_COLORS.getInt(_snowmanxxxxx));
            _snowmanxxxx += _snowmanxxxxxx;
         }

         int _snowmanxxxxx = this.updater.getUpgradedChunkCount() + this.updater.getSkippedChunkCount();
         drawCenteredString(matrices, this.textRenderer, _snowmanxxxxx + " / " + this.updater.getTotalChunkCount(), this.width / 2, _snowmanxx + 2 * 9 + 2, 10526880);
         drawCenteredString(
            matrices, this.textRenderer, MathHelper.floor(this.updater.getProgress() * 100.0F) + "%", this.width / 2, _snowmanxx + (_snowmanxxx - _snowmanxx) / 2 - 9 / 2, 10526880
         );
      }

      super.render(matrices, mouseX, mouseY, delta);
   }
}
