/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Function4
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.screen.world;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Function4;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourceManager;
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

public class OptimizeWorldScreen
extends Screen {
    private static final Logger field_25482 = LogManager.getLogger();
    private static final Object2IntMap<RegistryKey<World>> DIMENSION_COLORS = (Object2IntMap)Util.make(new Object2IntOpenCustomHashMap(Util.identityHashStrategy()), object2IntOpenCustomHashMap -> {
        object2IntOpenCustomHashMap.put(World.OVERWORLD, -13408734);
        object2IntOpenCustomHashMap.put(World.NETHER, -10075085);
        object2IntOpenCustomHashMap.put(World.END, -8943531);
        object2IntOpenCustomHashMap.defaultReturnValue(-2236963);
    });
    private final BooleanConsumer callback;
    private final WorldUpdater updater;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public static OptimizeWorldScreen method_27031(MinecraftClient minecraftClient, BooleanConsumer booleanConsumer, DataFixer dataFixer, LevelStorage.Session session, boolean bl) {
        DynamicRegistryManager.Impl impl = DynamicRegistryManager.create();
        try (MinecraftClient.IntegratedResourceManager _snowman2 = minecraftClient.method_29604(impl, MinecraftClient::method_29598, (Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties>)((Function4)MinecraftClient::createSaveProperties), false, session);){
            SaveProperties _snowman3 = _snowman2.getSaveProperties();
            session.backupLevelDataFile(impl, _snowman3);
            ImmutableSet<RegistryKey<World>> _snowman4 = _snowman3.getGeneratorOptions().getWorlds();
            OptimizeWorldScreen optimizeWorldScreen = new OptimizeWorldScreen(booleanConsumer, dataFixer, session, _snowman3.getLevelInfo(), bl, _snowman4);
            return optimizeWorldScreen;
        }
        catch (Exception exception) {
            field_25482.warn("Failed to load datapacks, can't optimize world", (Throwable)exception);
            return null;
        }
    }

    private OptimizeWorldScreen(BooleanConsumer callback, DataFixer dataFixer, LevelStorage.Session session, LevelInfo levelInfo, boolean bl, ImmutableSet<RegistryKey<World>> immutableSet) {
        super(new TranslatableText("optimizeWorld.title", levelInfo.getLevelName()));
        this.callback = callback;
        this.updater = new WorldUpdater(session, dataFixer, immutableSet, bl);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 150, 200, 20, ScreenTexts.CANCEL, buttonWidget -> {
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
        OptimizeWorldScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        int n = this.width / 2 - 150;
        _snowman = this.width / 2 + 150;
        _snowman = this.height / 4 + 100;
        _snowman = _snowman + 10;
        OptimizeWorldScreen.drawCenteredText(matrices, this.textRenderer, this.updater.getStatus(), this.width / 2, _snowman - this.textRenderer.fontHeight - 2, 0xA0A0A0);
        if (this.updater.getTotalChunkCount() > 0) {
            OptimizeWorldScreen.fill(matrices, n - 1, _snowman - 1, _snowman + 1, _snowman + 1, -16777216);
            OptimizeWorldScreen.drawTextWithShadow(matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.converted", this.updater.getUpgradedChunkCount()), n, 40, 0xA0A0A0);
            OptimizeWorldScreen.drawTextWithShadow(matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.skipped", this.updater.getSkippedChunkCount()), n, 40 + this.textRenderer.fontHeight + 3, 0xA0A0A0);
            OptimizeWorldScreen.drawTextWithShadow(matrices, this.textRenderer, new TranslatableText("optimizeWorld.info.total", this.updater.getTotalChunkCount()), n, 40 + (this.textRenderer.fontHeight + 3) * 2, 0xA0A0A0);
            _snowman = 0;
            for (RegistryKey registryKey : this.updater.method_28304()) {
                int n2 = MathHelper.floor(this.updater.getProgress(registryKey) * (float)(_snowman - n));
                OptimizeWorldScreen.fill(matrices, n + _snowman, _snowman, n + _snowman + n2, _snowman, DIMENSION_COLORS.getInt((Object)registryKey));
                _snowman += n2;
            }
            int n3 = this.updater.getUpgradedChunkCount() + this.updater.getSkippedChunkCount();
            OptimizeWorldScreen.drawCenteredString(matrices, this.textRenderer, n3 + " / " + this.updater.getTotalChunkCount(), this.width / 2, _snowman + 2 * this.textRenderer.fontHeight + 2, 0xA0A0A0);
            OptimizeWorldScreen.drawCenteredString(matrices, this.textRenderer, MathHelper.floor(this.updater.getProgress() * 100.0f) + "%", this.width / 2, _snowman + (_snowman - _snowman) / 2 - this.textRenderer.fontHeight / 2, 0xA0A0A0);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }
}

