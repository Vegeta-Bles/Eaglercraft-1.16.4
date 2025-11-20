package net.minecraft.client.gui.screen.world;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldListWidget extends AlwaysSelectedEntryListWidget<WorldListWidget.Entry> {
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

   public WorldListWidget(
      SelectWorldScreen parent,
      MinecraftClient client,
      int width,
      int height,
      int top,
      int bottom,
      int itemHeight,
      Supplier<String> searchFilter,
      @Nullable WorldListWidget list
   ) {
      super(client, width, height, top, bottom, itemHeight);
      this.parent = parent;
      if (list != null) {
         this.levels = list.levels;
      }

      this.filter(searchFilter, false);
   }

   public void filter(Supplier<String> _snowman, boolean load) {
      this.clearEntries();
      LevelStorage _snowmanx = this.client.getLevelStorage();
      if (this.levels == null || load) {
         try {
            this.levels = _snowmanx.getLevelList();
         } catch (LevelStorageException var7) {
            LOGGER.error("Couldn't load level list", var7);
            this.client.openScreen(new FatalErrorScreen(new TranslatableText("selectWorld.unable_to_load"), new LiteralText(var7.getMessage())));
            return;
         }

         Collections.sort(this.levels);
      }

      if (this.levels.isEmpty()) {
         this.client.openScreen(CreateWorldScreen.method_31130(null));
      } else {
         String _snowmanxx = _snowman.get().toLowerCase(Locale.ROOT);

         for (LevelSummary _snowmanxxx : this.levels) {
            if (_snowmanxxx.getDisplayName().toLowerCase(Locale.ROOT).contains(_snowmanxx) || _snowmanxxx.getName().toLowerCase(Locale.ROOT).contains(_snowmanxx)) {
               this.addEntry(new WorldListWidget.Entry(this, _snowmanxxx));
            }
         }
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

   public void setSelected(@Nullable WorldListWidget.Entry _snowman) {
      super.setSelected(_snowman);
      if (_snowman != null) {
         LevelSummary _snowmanx = _snowman.level;
         NarratorManager.INSTANCE
            .narrate(
               new TranslatableText(
                     "narrator.select",
                     new TranslatableText(
                        "narrator.select.world",
                        _snowmanx.getDisplayName(),
                        new Date(_snowmanx.getLastPlayed()),
                        _snowmanx.isHardcore() ? new TranslatableText("gameMode.hardcore") : new TranslatableText("gameMode." + _snowmanx.getGameMode().getName()),
                        _snowmanx.hasCheats() ? new TranslatableText("selectWorld.cheats") : LiteralText.EMPTY,
                        _snowmanx.getVersion()
                     )
                  )
                  .getString()
            );
      }

      this.parent.worldSelected(_snowman != null && !_snowman.level.isLocked());
   }

   @Override
   protected void moveSelection(EntryListWidget.MoveDirection direction) {
      this.moveSelectionIf(direction, _snowman -> !_snowman.level.isLocked());
   }

   public Optional<WorldListWidget.Entry> method_20159() {
      return Optional.ofNullable(this.getSelected());
   }

   public SelectWorldScreen getParent() {
      return this.parent;
   }

   public final class Entry extends AlwaysSelectedEntryListWidget.Entry<WorldListWidget.Entry> implements AutoCloseable {
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
         String _snowmanx = level.getName();
         this.iconLocation = new Identifier(
            "minecraft", "worlds/" + Util.replaceInvalidChars(_snowmanx, Identifier::isPathCharacterValid) + "/" + Hashing.sha1().hashUnencodedChars(_snowmanx) + "/icon"
         );
         this.iconFile = level.getFile();
         if (!this.iconFile.isFile()) {
            this.iconFile = null;
         }

         this.icon = this.getIconTexture();
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         String _snowman = this.level.getDisplayName();
         String _snowmanx = this.level.getName() + " (" + WorldListWidget.DATE_FORMAT.format(new Date(this.level.getLastPlayed())) + ")";
         if (StringUtils.isEmpty(_snowman)) {
            _snowman = I18n.translate("selectWorld.world") + " " + (index + 1);
         }

         Text _snowmanxx = this.level.method_27429();
         this.client.textRenderer.draw(matrices, _snowman, (float)(x + 32 + 3), (float)(y + 1), 16777215);
         this.client.textRenderer.draw(matrices, _snowmanx, (float)(x + 32 + 3), (float)(y + 9 + 3), 8421504);
         this.client.textRenderer.draw(matrices, _snowmanxx, (float)(x + 32 + 3), (float)(y + 9 + 9 + 3), 8421504);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(this.icon != null ? this.iconLocation : WorldListWidget.UNKNOWN_SERVER_LOCATION);
         RenderSystem.enableBlend();
         DrawableHelper.drawTexture(matrices, x, y, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.disableBlend();
         if (this.client.options.touchscreen || hovered) {
            this.client.getTextureManager().bindTexture(WorldListWidget.WORLD_SELECTION_LOCATION);
            DrawableHelper.fill(matrices, x, y, x + 32, y + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int _snowmanxxx = mouseX - x;
            boolean _snowmanxxxx = _snowmanxxx < 32;
            int _snowmanxxxxx = _snowmanxxxx ? 32 : 0;
            if (this.level.isLocked()) {
               DrawableHelper.drawTexture(matrices, x, y, 96.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
               if (_snowmanxxxx) {
                  this.screen.setTooltip(this.client.textRenderer.wrapLines(WorldListWidget.field_26610, 175));
               }
            } else if (this.level.isDifferentVersion()) {
               DrawableHelper.drawTexture(matrices, x, y, 32.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
               if (this.level.isFutureLevel()) {
                  DrawableHelper.drawTexture(matrices, x, y, 96.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
                  if (_snowmanxxxx) {
                     this.screen.setTooltip(ImmutableList.of(WorldListWidget.field_26606.asOrderedText(), WorldListWidget.field_26607.asOrderedText()));
                  }
               } else if (!SharedConstants.getGameVersion().isStable()) {
                  DrawableHelper.drawTexture(matrices, x, y, 64.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
                  if (_snowmanxxxx) {
                     this.screen.setTooltip(ImmutableList.of(WorldListWidget.field_26608.asOrderedText(), WorldListWidget.field_26609.asOrderedText()));
                  }
               }
            } else {
               DrawableHelper.drawTexture(matrices, x, y, 0.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
            }
         }
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (this.level.isLocked()) {
            return true;
         } else {
            WorldListWidget.this.setSelected(this);
            this.screen.worldSelected(WorldListWidget.this.method_20159().isPresent());
            if (mouseX - (double)WorldListWidget.this.getRowLeft() <= 32.0) {
               this.play();
               return true;
            } else if (Util.getMeasuringTimeMs() - this.time < 250L) {
               this.play();
               return true;
            } else {
               this.time = Util.getMeasuringTimeMs();
               return false;
            }
         }
      }

      public void play() {
         if (!this.level.isLocked()) {
            if (this.level.isOutdatedLevel()) {
               Text _snowman = new TranslatableText("selectWorld.backupQuestion");
               Text _snowmanx = new TranslatableText("selectWorld.backupWarning", this.level.getVersion(), SharedConstants.getGameVersion().getName());
               this.client.openScreen(new BackupPromptScreen(this.screen, (_snowmanxx, _snowmanxxx) -> {
                  if (_snowmanxx) {
                     String _snowmanxx = this.level.getName();

                     try (LevelStorage.Session _snowmanxxx = this.client.getLevelStorage().createSession(_snowmanxx)) {
                        EditWorldScreen.backupLevel(_snowmanxxx);
                     } catch (IOException var17) {
                        SystemToast.addWorldAccessFailureToast(this.client, _snowmanxx);
                        WorldListWidget.LOGGER.error("Failed to backup level {}", _snowmanxx, var17);
                     }
                  }

                  this.start();
               }, _snowman, _snowmanx, false));
            } else if (this.level.isFutureLevel()) {
               this.client
                  .openScreen(
                     new ConfirmScreen(
                        _snowman -> {
                           if (_snowman) {
                              try {
                                 this.start();
                              } catch (Exception var3) {
                                 WorldListWidget.LOGGER.error("Failure to open 'future world'", var3);
                                 this.client
                                    .openScreen(
                                       new NoticeScreen(
                                          () -> this.client.openScreen(this.screen),
                                          new TranslatableText("selectWorld.futureworld.error.title"),
                                          new TranslatableText("selectWorld.futureworld.error.text")
                                       )
                                    );
                              }
                           } else {
                              this.client.openScreen(this.screen);
                           }
                        },
                        new TranslatableText("selectWorld.versionQuestion"),
                        new TranslatableText(
                           "selectWorld.versionWarning", this.level.getVersion(), new TranslatableText("selectWorld.versionJoinButton"), ScreenTexts.CANCEL
                        )
                     )
                  );
            } else {
               this.start();
            }
         }
      }

      public void delete() {
         this.client
            .openScreen(
               new ConfirmScreen(
                  _snowman -> {
                     if (_snowman) {
                        this.client.openScreen(new ProgressScreen());
                        LevelStorage _snowmanx = this.client.getLevelStorage();
                        String _snowmanxx = this.level.getName();

                        try (LevelStorage.Session _snowmanxxx = _snowmanx.createSession(_snowmanxx)) {
                           _snowmanxxx.deleteSessionLock();
                        } catch (IOException var17) {
                           SystemToast.addWorldDeleteFailureToast(this.client, _snowmanxx);
                           WorldListWidget.LOGGER.error("Failed to delete world {}", _snowmanxx, var17);
                        }

                        WorldListWidget.this.filter(() -> this.screen.searchBox.getText(), true);
                     }

                     this.client.openScreen(this.screen);
                  },
                  new TranslatableText("selectWorld.deleteQuestion"),
                  new TranslatableText("selectWorld.deleteWarning", this.level.getDisplayName()),
                  new TranslatableText("selectWorld.deleteButton"),
                  ScreenTexts.CANCEL
               )
            );
      }

      public void edit() {
         String _snowman = this.level.getName();

         try {
            LevelStorage.Session _snowmanx = this.client.getLevelStorage().createSession(_snowman);
            this.client.openScreen(new EditWorldScreen(_snowmanxx -> {
               try {
                  _snowman.close();
               } catch (IOException var5) {
                  WorldListWidget.LOGGER.error("Failed to unlock level {}", _snowman, var5);
               }

               if (_snowmanxx) {
                  WorldListWidget.this.filter(() -> this.screen.searchBox.getText(), true);
               }

               this.client.openScreen(this.screen);
            }, _snowmanx));
         } catch (IOException var3) {
            SystemToast.addWorldAccessFailureToast(this.client, _snowman);
            WorldListWidget.LOGGER.error("Failed to access level {}", _snowman, var3);
            WorldListWidget.this.filter(() -> this.screen.searchBox.getText(), true);
         }
      }

      public void recreate() {
         this.method_29990();
         DynamicRegistryManager.Impl _snowman = DynamicRegistryManager.create();

         try (
            LevelStorage.Session _snowmanx = this.client.getLevelStorage().createSession(this.level.getName());
            MinecraftClient.IntegratedResourceManager _snowmanxx = this.client
               .method_29604(_snowman, MinecraftClient::method_29598, MinecraftClient::createSaveProperties, false, _snowmanx);
         ) {
            LevelInfo _snowmanxxx = _snowmanxx.getSaveProperties().getLevelInfo();
            DataPackSettings _snowmanxxxx = _snowmanxxx.getDataPackSettings();
            GeneratorOptions _snowmanxxxxx = _snowmanxx.getSaveProperties().getGeneratorOptions();
            Path _snowmanxxxxxx = CreateWorldScreen.method_29685(_snowmanx.getDirectory(WorldSavePath.DATAPACKS), this.client);
            if (_snowmanxxxxx.isLegacyCustomizedType()) {
               this.client
                  .openScreen(
                     new ConfirmScreen(
                        _snowmanxxxxxxx -> this.client.openScreen((Screen)(_snowmanxxxxxxx ? new CreateWorldScreen(this.screen, _snowman, _snowman, _snowman, _snowman, _snowman) : this.screen)),
                        new TranslatableText("selectWorld.recreate.customized.title"),
                        new TranslatableText("selectWorld.recreate.customized.text"),
                        ScreenTexts.PROCEED,
                        ScreenTexts.CANCEL
                     )
                  );
            } else {
               this.client.openScreen(new CreateWorldScreen(this.screen, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxx, _snowman));
            }
         } catch (Exception var37) {
            WorldListWidget.LOGGER.error("Unable to recreate world", var37);
            this.client
               .openScreen(
                  new NoticeScreen(
                     () -> this.client.openScreen(this.screen),
                     new TranslatableText("selectWorld.recreate.error.title"),
                     new TranslatableText("selectWorld.recreate.error.text")
                  )
               );
         }
      }

      private void start() {
         this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         if (this.client.getLevelStorage().levelExists(this.level.getName())) {
            this.method_29990();
            this.client.startIntegratedServer(this.level.getName());
         }
      }

      private void method_29990() {
         this.client.method_29970(new SaveLevelScreen(new TranslatableText("selectWorld.data_read")));
      }

      @Nullable
      private NativeImageBackedTexture getIconTexture() {
         boolean _snowman = this.iconFile != null && this.iconFile.isFile();
         if (_snowman) {
            try (InputStream _snowmanx = new FileInputStream(this.iconFile)) {
               NativeImage _snowmanxx = NativeImage.read(_snowmanx);
               Validate.validState(_snowmanxx.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(_snowmanxx.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               NativeImageBackedTexture _snowmanxxx = new NativeImageBackedTexture(_snowmanxx);
               this.client.getTextureManager().registerTexture(this.iconLocation, _snowmanxxx);
               return _snowmanxxx;
            } catch (Throwable var18) {
               WorldListWidget.LOGGER.error("Invalid icon for world {}", this.level.getName(), var18);
               this.iconFile = null;
               return null;
            }
         } else {
            this.client.getTextureManager().destroyTexture(this.iconLocation);
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
