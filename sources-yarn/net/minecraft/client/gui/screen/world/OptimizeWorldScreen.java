package net.minecraft.client.gui.screen.world;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class OptimizeWorldScreen extends Screen {
   private static final Logger field_25482 = LogManager.getLogger();
   private static final Object2IntMap<RegistryKey<World>> DIMENSION_COLORS = Util.make(
      new Object2IntOpenCustomHashMap(Util.identityHashStrategy()), object2IntOpenCustomHashMap -> {
         object2IntOpenCustomHashMap.put(World.OVERWORLD, -13408734);
         object2IntOpenCustomHashMap.put(World.NETHER, -10075085);
         object2IntOpenCustomHashMap.put(World.END, -8943531);
         object2IntOpenCustomHashMap.defaultReturnValue(-2236963);
      }
   );
   private final BooleanConsumer callback;
   private final WorldUpdater updater;

   @Nullable
   public static OptimizeWorldScreen method_27031(
      MinecraftClient arg, BooleanConsumer booleanConsumer, DataFixer dataFixer, LevelStorage.Session arg2, boolean bl
   ) {
      DynamicRegistryManager.Impl lv = DynamicRegistryManager.create();

      try (MinecraftClient.IntegratedResourceManager lv2 = arg.method_29604(
            lv, MinecraftClient::method_29598, MinecraftClient::createSaveProperties, false, arg2
         )) {
         SaveProperties lv3 = lv2.getSaveProperties();
         arg2.backupLevelDataFile(lv, lv3);
         ImmutableSet<RegistryKey<World>> immutableSet = lv3.getGeneratorOptions().getWorlds();
         return new OptimizeWorldScreen(booleanConsumer, dataFixer, arg2, lv3.getLevelInfo(), bl, immutableSet);
      } catch (Exception var22) {
         field_25482.warn("Failed to load datapacks, can't optimize world", var22);
         return null;
      }
   }

   private OptimizeWorldScreen(
      BooleanConsumer callback, DataFixer dataFixer, LevelStorage.Session arg, LevelInfo arg2, boolean bl, ImmutableSet<RegistryKey<World>> immutableSet
   ) {
      super(new TranslatableText("optimizeWorld.title", arg2.getLevelName()));
      this.callback = callback;
      this.updater = new WorldUpdater(arg, dataFixer, immutableSet, bl);
   }

   @Override
   protected void init() {
      super.init();
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 150, 200, 20, ScreenTexts.CANCEL, arg -> {
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
      int k = this.width / 2 - 150;
      int l = this.width / 2 + 150;
      int m = this.height / 4 + 100;
      int n = m + 10;
      drawCenteredText(matrices, this.textRenderer, this.updater.getStatus(), this.width / 2, m - 9 - 2, 10526880);
      if (this.updater.getTotalChunkCount() > 0) {
         fill(matrices, k - 1, m - 1, l + 1, n + 1, -16777216);
         drawTextWithShadow(
            matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.converted", this.updater.getUpgradedChunkCount()), k, 40, 10526880
         );
         drawTextWithShadow(
            matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.skipped", this.updater.getSkippedChunkCount()), k, 40 + 9 + 3, 10526880
         );
         drawTextWithShadow(
            matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.total", this.updater.getTotalChunkCount()), k, 40 + (9 + 3) * 2, 10526880
         );
         int o = 0;
         UnmodifiableIterator q = this.updater.method_28304().iterator();

         while (q.hasNext()) {
            RegistryKey<World> lv = (RegistryKey<World>)q.next();
            int p = MathHelper.floor(this.updater.getProgress(lv) * (float)(l - k));
            fill(matrices, k + o, m, k + o + p, n, DIMENSION_COLORS.getInt(lv));
            o += p;
         }

         int qx = this.updater.getUpgradedChunkCount() + this.updater.getSkippedChunkCount();
         drawCenteredString(matrices, this.textRenderer, qx + " / " + this.updater.getTotalChunkCount(), this.width / 2, m + 2 * 9 + 2, 10526880);
         drawCenteredString(
            matrices, this.textRenderer, MathHelper.floor(this.updater.getProgress() * 100.0F) + "%", this.width / 2, m + (n - m) / 2 - 9 / 2, 10526880
         );
      }

      super.render(matrices, mouseX, mouseY, delta);
   }
}
