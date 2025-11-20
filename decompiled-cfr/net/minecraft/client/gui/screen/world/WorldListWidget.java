/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.hash.Hashing
 *  com.mojang.datafixers.util.Function4
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.Validate
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.screen.world;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Function4;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.BackupPromptScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.FatalErrorScreen;
import net.minecraft.client.gui.screen.NoticeScreen;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourceManager;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldListWidget
extends AlwaysSelectedEntryListWidget<Entry> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
    private static final Identifier UNKNOWN_SERVER_LOCATION = new Identifier("textures/misc/unknown_server.png");
    private static final Identifier WORLD_SELECTION_LOCATION = new Identifier("textures/gui/world_selection.png");
    private static final Text field_26606 = new TranslatableText("selectWorld.tooltip.fromNewerVersion1").formatted(Formatting.RED);
    private static final Text field_26607 = new TranslatableText("selectWorld.tooltip.fromNewerVersion2").formatted(Formatting.RED);
    private static final Text field_26608 = new TranslatableText("selectWorld.tooltip.snapshot1").formatted(Formatting.GOLD);
    private static final Text field_26609 = new TranslatableText("selectWorld.tooltip.snapshot2").formatted(Formatting.GOLD);
    private static final Text field_26610 = new TranslatableText("selectWorld.locked").formatted(Formatting.RED);
    private final SelectWorldScreen parent;
    @Nullable
    private List<LevelSummary> levels;

    public WorldListWidget(SelectWorldScreen parent, MinecraftClient client, int width, int height, int top, int bottom, int itemHeight, Supplier<String> searchFilter, @Nullable WorldListWidget list) {
        super(client, width, height, top, bottom, itemHeight);
        this.parent = parent;
        if (list != null) {
            this.levels = list.levels;
        }
        this.filter(searchFilter, false);
    }

    public void filter(Supplier<String> supplier2, boolean load) {
        Supplier<String> supplier2;
        this.clearEntries();
        LevelStorage levelStorage = this.client.getLevelStorage();
        if (this.levels == null || load) {
            try {
                this.levels = levelStorage.getLevelList();
            }
            catch (LevelStorageException levelStorageException) {
                LOGGER.error("Couldn't load level list", (Throwable)levelStorageException);
                this.client.openScreen(new FatalErrorScreen(new TranslatableText("selectWorld.unable_to_load"), new LiteralText(levelStorageException.getMessage())));
                return;
            }
            Collections.sort(this.levels);
        }
        if (this.levels.isEmpty()) {
            this.client.openScreen(CreateWorldScreen.method_31130(null));
            return;
        }
        String _snowman2 = supplier2.get().toLowerCase(Locale.ROOT);
        for (LevelSummary levelSummary : this.levels) {
            if (!levelSummary.getDisplayName().toLowerCase(Locale.ROOT).contains(_snowman2) && !levelSummary.getName().toLowerCase(Locale.ROOT).contains(_snowman2)) continue;
            this.addEntry(new Entry(this, levelSummary));
        }
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 20;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 50;
    }

    @Override
    protected boolean isFocused() {
        return this.parent.getFocused() == this;
    }

    @Override
    public void setSelected(@Nullable Entry entry2) {
        Entry entry2;
        super.setSelected(entry2);
        if (entry2 != null) {
            LevelSummary levelSummary = entry2.level;
            NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.select", new TranslatableText("narrator.select.world", levelSummary.getDisplayName(), new Date(levelSummary.getLastPlayed()), levelSummary.isHardcore() ? new TranslatableText("gameMode.hardcore") : new TranslatableText("gameMode." + levelSummary.getGameMode().getName()), levelSummary.hasCheats() ? new TranslatableText("selectWorld.cheats") : LiteralText.EMPTY, levelSummary.getVersion())).getString());
        }
        this.parent.worldSelected(entry2 != null && !entry2.level.isLocked());
    }

    @Override
    protected void moveSelection(EntryListWidget.MoveDirection direction) {
        this.moveSelectionIf(direction, entry -> !((Entry)entry).level.isLocked());
    }

    public Optional<Entry> method_20159() {
        return Optional.ofNullable(this.getSelected());
    }

    public SelectWorldScreen getParent() {
        return this.parent;
    }

    public final class Entry
    extends AlwaysSelectedEntryListWidget.Entry<Entry>
    implements AutoCloseable {
        private final MinecraftClient client;
        private final SelectWorldScreen screen;
        private final LevelSummary level;
        private final Identifier iconLocation;
        private File iconFile;
        @Nullable
        private final NativeImageBackedTexture icon;
        private long time;

        public Entry(WorldListWidget levelList, LevelSummary level) {
            this.screen = levelList.getParent();
            this.level = level;
            this.client = MinecraftClient.getInstance();
            String string = level.getName();
            this.iconLocation = new Identifier("minecraft", "worlds/" + Util.replaceInvalidChars(string, Identifier::isPathCharacterValid) + "/" + Hashing.sha1().hashUnencodedChars((CharSequence)string) + "/icon");
            this.iconFile = level.getFile();
            if (!this.iconFile.isFile()) {
                this.iconFile = null;
            }
            this.icon = this.getIconTexture();
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            String string = this.level.getDisplayName();
            _snowman = this.level.getName() + " (" + DATE_FORMAT.format(new Date(this.level.getLastPlayed())) + ")";
            if (StringUtils.isEmpty((CharSequence)string)) {
                string = I18n.translate("selectWorld.world", new Object[0]) + " " + (index + 1);
            }
            Text _snowman2 = this.level.method_27429();
            this.client.textRenderer.draw(matrices, string, (float)(x + 32 + 3), (float)(y + 1), 0xFFFFFF);
            this.client.textRenderer.draw(matrices, _snowman, (float)(x + 32 + 3), (float)(y + this.client.textRenderer.fontHeight + 3), 0x808080);
            this.client.textRenderer.draw(matrices, _snowman2, (float)(x + 32 + 3), (float)(y + this.client.textRenderer.fontHeight + this.client.textRenderer.fontHeight + 3), 0x808080);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.client.getTextureManager().bindTexture(this.icon != null ? this.iconLocation : UNKNOWN_SERVER_LOCATION);
            RenderSystem.enableBlend();
            DrawableHelper.drawTexture(matrices, x, y, 0.0f, 0.0f, 32, 32, 32, 32);
            RenderSystem.disableBlend();
            if (this.client.options.touchscreen || hovered) {
                this.client.getTextureManager().bindTexture(WORLD_SELECTION_LOCATION);
                DrawableHelper.fill(matrices, x, y, x + 32, y + 32, -1601138544);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                int n = mouseX - x;
                boolean _snowman3 = n < 32;
                int n2 = _snowman = _snowman3 ? 32 : 0;
                if (this.level.isLocked()) {
                    DrawableHelper.drawTexture(matrices, x, y, 96.0f, _snowman, 32, 32, 256, 256);
                    if (_snowman3) {
                        this.screen.setTooltip(this.client.textRenderer.wrapLines(field_26610, 175));
                    }
                } else if (this.level.isDifferentVersion()) {
                    DrawableHelper.drawTexture(matrices, x, y, 32.0f, _snowman, 32, 32, 256, 256);
                    if (this.level.isFutureLevel()) {
                        DrawableHelper.drawTexture(matrices, x, y, 96.0f, _snowman, 32, 32, 256, 256);
                        if (_snowman3) {
                            this.screen.setTooltip((List<OrderedText>)ImmutableList.of((Object)field_26606.asOrderedText(), (Object)field_26607.asOrderedText()));
                        }
                    } else if (!SharedConstants.getGameVersion().isStable()) {
                        DrawableHelper.drawTexture(matrices, x, y, 64.0f, _snowman, 32, 32, 256, 256);
                        if (_snowman3) {
                            this.screen.setTooltip((List<OrderedText>)ImmutableList.of((Object)field_26608.asOrderedText(), (Object)field_26609.asOrderedText()));
                        }
                    }
                } else {
                    DrawableHelper.drawTexture(matrices, x, y, 0.0f, _snowman, 32, 32, 256, 256);
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.level.isLocked()) {
                return true;
            }
            WorldListWidget.this.setSelected(this);
            this.screen.worldSelected(WorldListWidget.this.method_20159().isPresent());
            if (mouseX - (double)WorldListWidget.this.getRowLeft() <= 32.0) {
                this.play();
                return true;
            }
            if (Util.getMeasuringTimeMs() - this.time < 250L) {
                this.play();
                return true;
            }
            this.time = Util.getMeasuringTimeMs();
            return false;
        }

        public void play() {
            if (this.level.isLocked()) {
                return;
            }
            if (this.level.isOutdatedLevel()) {
                TranslatableText translatableText = new TranslatableText("selectWorld.backupQuestion");
                _snowman = new TranslatableText("selectWorld.backupWarning", this.level.getVersion(), SharedConstants.getGameVersion().getName());
                this.client.openScreen(new BackupPromptScreen(this.screen, (bl, bl2) -> {
                    if (bl) {
                        String string = this.level.getName();
                        try (LevelStorage.Session session = this.client.getLevelStorage().createSession(string);){
                            EditWorldScreen.backupLevel(session);
                        }
                        catch (IOException iOException) {
                            SystemToast.addWorldAccessFailureToast(this.client, string);
                            LOGGER.error("Failed to backup level {}", (Object)string, (Object)iOException);
                        }
                    }
                    this.start();
                }, translatableText, _snowman, false));
            } else if (this.level.isFutureLevel()) {
                this.client.openScreen(new ConfirmScreen(bl -> {
                    if (bl) {
                        try {
                            this.start();
                        }
                        catch (Exception exception) {
                            LOGGER.error("Failure to open 'future world'", (Throwable)exception);
                            this.client.openScreen(new NoticeScreen(() -> this.client.openScreen(this.screen), new TranslatableText("selectWorld.futureworld.error.title"), new TranslatableText("selectWorld.futureworld.error.text")));
                        }
                    } else {
                        this.client.openScreen(this.screen);
                    }
                }, new TranslatableText("selectWorld.versionQuestion"), new TranslatableText("selectWorld.versionWarning", this.level.getVersion(), new TranslatableText("selectWorld.versionJoinButton"), ScreenTexts.CANCEL)));
            } else {
                this.start();
            }
        }

        public void delete() {
            this.client.openScreen(new ConfirmScreen(bl -> {
                if (bl) {
                    this.client.openScreen(new ProgressScreen());
                    LevelStorage levelStorage = this.client.getLevelStorage();
                    String _snowman2 = this.level.getName();
                    try (LevelStorage.Session session = levelStorage.createSession(_snowman2);){
                        session.deleteSessionLock();
                    }
                    catch (IOException iOException) {
                        SystemToast.addWorldDeleteFailureToast(this.client, _snowman2);
                        LOGGER.error("Failed to delete world {}", (Object)_snowman2, (Object)iOException);
                    }
                    WorldListWidget.this.filter(() -> this.screen.searchBox.getText(), true);
                }
                this.client.openScreen(this.screen);
            }, new TranslatableText("selectWorld.deleteQuestion"), new TranslatableText("selectWorld.deleteWarning", this.level.getDisplayName()), new TranslatableText("selectWorld.deleteButton"), ScreenTexts.CANCEL));
        }

        public void edit() {
            String string = this.level.getName();
            try {
                LevelStorage.Session session = this.client.getLevelStorage().createSession(string);
                this.client.openScreen(new EditWorldScreen(bl -> {
                    try {
                        session.close();
                    }
                    catch (IOException iOException) {
                        LOGGER.error("Failed to unlock level {}", (Object)string, (Object)iOException);
                    }
                    if (bl) {
                        WorldListWidget.this.filter(() -> this.screen.searchBox.getText(), true);
                    }
                    this.client.openScreen(this.screen);
                }, session));
            }
            catch (IOException iOException) {
                SystemToast.addWorldAccessFailureToast(this.client, string);
                LOGGER.error("Failed to access level {}", (Object)string, (Object)iOException);
                WorldListWidget.this.filter(() -> this.screen.searchBox.getText(), true);
            }
        }

        public void recreate() {
            this.method_29990();
            DynamicRegistryManager.Impl impl = DynamicRegistryManager.create();
            try (LevelStorage.Session session = this.client.getLevelStorage().createSession(this.level.getName());
                 MinecraftClient.IntegratedResourceManager integratedResourceManager = this.client.method_29604(impl, MinecraftClient::method_29598, (Function4<LevelStorage.Session, DynamicRegistryManager.Impl, ResourceManager, DataPackSettings, SaveProperties>)((Function4)MinecraftClient::createSaveProperties), false, session);){
                LevelInfo levelInfo = integratedResourceManager.getSaveProperties().getLevelInfo();
                DataPackSettings _snowman2 = levelInfo.getDataPackSettings();
                GeneratorOptions _snowman3 = integratedResourceManager.getSaveProperties().getGeneratorOptions();
                Path _snowman4 = CreateWorldScreen.method_29685(session.getDirectory(WorldSavePath.DATAPACKS), this.client);
                if (_snowman3.isLegacyCustomizedType()) {
                    this.client.openScreen(new ConfirmScreen(bl -> this.client.openScreen(bl ? new CreateWorldScreen(this.screen, levelInfo, _snowman3, _snowman4, _snowman2, impl) : this.screen), new TranslatableText("selectWorld.recreate.customized.title"), new TranslatableText("selectWorld.recreate.customized.text"), ScreenTexts.PROCEED, ScreenTexts.CANCEL));
                } else {
                    this.client.openScreen(new CreateWorldScreen(this.screen, levelInfo, _snowman3, _snowman4, _snowman2, impl));
                }
            }
            catch (Exception exception) {
                LOGGER.error("Unable to recreate world", (Throwable)exception);
                this.client.openScreen(new NoticeScreen(() -> this.client.openScreen(this.screen), new TranslatableText("selectWorld.recreate.error.title"), new TranslatableText("selectWorld.recreate.error.text")));
            }
        }

        private void start() {
            this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            if (this.client.getLevelStorage().levelExists(this.level.getName())) {
                this.method_29990();
                this.client.startIntegratedServer(this.level.getName());
            }
        }

        private void method_29990() {
            this.client.method_29970(new SaveLevelScreen(new TranslatableText("selectWorld.data_read")));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Nullable
        private NativeImageBackedTexture getIconTexture() {
            boolean bl;
            boolean bl2 = bl = this.iconFile != null && this.iconFile.isFile();
            if (!bl) {
                this.client.getTextureManager().destroyTexture(this.iconLocation);
                return null;
            }
            try (FileInputStream _snowman2 = new FileInputStream(this.iconFile);){
                NativeImage _snowman3 = NativeImage.read(_snowman2);
                Validate.validState((_snowman3.getWidth() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                Validate.validState((_snowman3.getHeight() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels high", (Object[])new Object[0]);
                NativeImageBackedTexture _snowman4 = new NativeImageBackedTexture(_snowman3);
                this.client.getTextureManager().registerTexture(this.iconLocation, _snowman4);
                NativeImageBackedTexture nativeImageBackedTexture = _snowman4;
                return nativeImageBackedTexture;
            }
            catch (Throwable throwable6) {
                LOGGER.error("Invalid icon for world {}", (Object)this.level.getName(), (Object)throwable6);
                this.iconFile = null;
                return null;
            }
        }

        @Override
        public void close() {
            if (this.icon != null) {
                this.icon.close();
            }
        }
    }
}

