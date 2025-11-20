package net.minecraft.client.gui.screen.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.BackupPromptScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.dynamic.RegistryReadingOps;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditWorldScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
   private static final Text ENTER_NAME_TEXT = new TranslatableText("selectWorld.enterName");
   private ButtonWidget saveButton;
   private final BooleanConsumer callback;
   private TextFieldWidget levelNameTextField;
   private final LevelStorage.Session field_23777;

   public EditWorldScreen(BooleanConsumer callback, LevelStorage.Session _snowman) {
      super(new TranslatableText("selectWorld.edit.title"));
      this.callback = callback;
      this.field_23777 = _snowman;
   }

   @Override
   public void tick() {
      this.levelNameTextField.tick();
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      ButtonWidget _snowman = this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height / 4 + 0 + 5, 200, 20, new TranslatableText("selectWorld.edit.resetIcon"), _snowmanx -> {
            FileUtils.deleteQuietly(this.field_23777.getIconFile());
            _snowmanx.active = false;
         })
      );
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 24 + 5,
            200,
            20,
            new TranslatableText("selectWorld.edit.openFolder"),
            _snowmanx -> Util.getOperatingSystem().open(this.field_23777.getDirectory(WorldSavePath.ROOT).toFile())
         )
      );
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 48 + 5, 200, 20, new TranslatableText("selectWorld.edit.backup"), _snowmanx -> {
         boolean _snowmanx = backupLevel(this.field_23777);
         this.callback.accept(!_snowmanx);
      }));
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 72 + 5, 200, 20, new TranslatableText("selectWorld.edit.backupFolder"), _snowmanx -> {
         LevelStorage _snowmanx = this.client.getLevelStorage();
         Path _snowmanxx = _snowmanx.getBackupsDirectory();

         try {
            Files.createDirectories(Files.exists(_snowmanxx) ? _snowmanxx.toRealPath() : _snowmanxx);
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }

         Util.getOperatingSystem().open(_snowmanxx.toFile());
      }));
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 96 + 5,
            200,
            20,
            new TranslatableText("selectWorld.edit.optimize"),
            _snowmanx -> this.client.openScreen(new BackupPromptScreen(this, (_snowmanxx, _snowmanxx) -> {
                  if (_snowmanxx) {
                     backupLevel(this.field_23777);
                  }

                  this.client.openScreen(OptimizeWorldScreen.method_27031(this.client, this.callback, this.client.getDataFixer(), this.field_23777, _snowmanxx));
               }, new TranslatableText("optimizeWorld.confirm.title"), new TranslatableText("optimizeWorld.confirm.description"), true))
         )
      );
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 120 + 5,
            200,
            20,
            new TranslatableText("selectWorld.edit.export_worldgen_settings"),
            _snowmanx -> {
               DynamicRegistryManager.Impl _snowmanx = DynamicRegistryManager.create();

               DataResult<String> _snowmanxx;
               try (MinecraftClient.IntegratedResourceManager _snowmanxxx = this.client
                     .method_29604(_snowmanx, MinecraftClient::method_29598, MinecraftClient::createSaveProperties, false, this.field_23777)) {
                  DynamicOps<JsonElement> _snowmanxxxx = RegistryReadingOps.of(JsonOps.INSTANCE, _snowmanx);
                  DataResult<JsonElement> _snowmanxxxxx = GeneratorOptions.CODEC.encodeStart(_snowmanxxxx, _snowmanxxx.getSaveProperties().getGeneratorOptions());
                  _snowmanxx = _snowmanxxxxx.flatMap(_snowmanxxxxxx -> {
                     Path _snowmanxxxxxxx = this.field_23777.getDirectory(WorldSavePath.ROOT).resolve("worldgen_settings_export.json");

                     try {
                        JsonWriter _snowmanxxxxxxxx = GSON.newJsonWriter(Files.newBufferedWriter(_snowmanxxxxxxx, StandardCharsets.UTF_8));
                        Throwable var4x = null;

                        try {
                           GSON.toJson(_snowmanxxxxxx, _snowmanxxxxxxxx);
                        } catch (Throwable var14) {
                           var4x = var14;
                           throw var14;
                        } finally {
                           if (_snowmanxxxxxxxx != null) {
                              if (var4x != null) {
                                 try {
                                    _snowmanxxxxxxxx.close();
                                 } catch (Throwable var13) {
                                    var4x.addSuppressed(var13);
                                 }
                              } else {
                                 _snowmanxxxxxxxx.close();
                              }
                           }
                        }
                     } catch (JsonIOException | IOException var16) {
                        return DataResult.error("Error writing file: " + var16.getMessage());
                     }

                     return DataResult.success(_snowmanxxxxxxx.toString());
                  });
               } catch (ExecutionException | InterruptedException var18) {
                  _snowmanxx = DataResult.error("Could not parse level data!");
               }

               Text _snowmanxxx = new LiteralText((String)_snowmanxx.get().map(Function.identity(), PartialResult::message));
               Text _snowmanxxxx = new TranslatableText(
                  _snowmanxx.result().isPresent() ? "selectWorld.edit.export_worldgen_settings.success" : "selectWorld.edit.export_worldgen_settings.failure"
               );
               _snowmanxx.error().ifPresent(_snowmanxxxxx -> LOGGER.error("Error exporting world settings: {}", _snowmanxxxxx));
               this.client.getToastManager().add(SystemToast.create(this.client, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, _snowmanxxxx, _snowmanxxx));
            }
         )
      );
      this.saveButton = this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height / 4 + 144 + 5, 98, 20, new TranslatableText("selectWorld.edit.save"), _snowmanx -> this.commit())
      );
      this.addButton(new ButtonWidget(this.width / 2 + 2, this.height / 4 + 144 + 5, 98, 20, ScreenTexts.CANCEL, _snowmanx -> this.callback.accept(false)));
      _snowman.active = this.field_23777.getIconFile().isFile();
      LevelSummary _snowmanx = this.field_23777.getLevelSummary();
      String _snowmanxx = _snowmanx == null ? "" : _snowmanx.getDisplayName();
      this.levelNameTextField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 38, 200, 20, new TranslatableText("selectWorld.enterName"));
      this.levelNameTextField.setText(_snowmanxx);
      this.levelNameTextField.setChangedListener(_snowmanxxx -> this.saveButton.active = !_snowmanxxx.trim().isEmpty());
      this.children.add(this.levelNameTextField);
      this.setInitialFocus(this.levelNameTextField);
   }

   @Override
   public void resize(MinecraftClient client, int width, int height) {
      String _snowman = this.levelNameTextField.getText();
      this.init(client, width, height);
      this.levelNameTextField.setText(_snowman);
   }

   @Override
   public void onClose() {
      this.callback.accept(false);
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   private void commit() {
      try {
         this.field_23777.save(this.levelNameTextField.getText().trim());
         this.callback.accept(true);
      } catch (IOException var2) {
         LOGGER.error("Failed to access world '{}'", this.field_23777.getDirectoryName(), var2);
         SystemToast.addWorldAccessFailureToast(this.client, this.field_23777.getDirectoryName());
         this.callback.accept(true);
      }
   }

   public static void method_29784(LevelStorage _snowman, String _snowman) {
      boolean _snowmanxx = false;

      try (LevelStorage.Session _snowmanxxx = _snowman.createSession(_snowman)) {
         _snowmanxx = true;
         backupLevel(_snowmanxxx);
      } catch (IOException var16) {
         if (!_snowmanxx) {
            SystemToast.addWorldAccessFailureToast(MinecraftClient.getInstance(), _snowman);
         }

         LOGGER.warn("Failed to create backup of level {}", _snowman, var16);
      }
   }

   public static boolean backupLevel(LevelStorage.Session _snowman) {
      long _snowmanx = 0L;
      IOException _snowmanxx = null;

      try {
         _snowmanx = _snowman.createBackup();
      } catch (IOException var6) {
         _snowmanxx = var6;
      }

      if (_snowmanxx != null) {
         Text _snowmanxxx = new TranslatableText("selectWorld.edit.backupFailed");
         Text _snowmanxxxx = new LiteralText(_snowmanxx.getMessage());
         MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, _snowmanxxx, _snowmanxxxx));
         return false;
      } else {
         Text _snowmanxxx = new TranslatableText("selectWorld.edit.backupCreated", _snowman.getDirectoryName());
         Text _snowmanxxxx = new TranslatableText("selectWorld.edit.backupSize", MathHelper.ceil((double)_snowmanx / 1048576.0));
         MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, _snowmanxxx, _snowmanxxxx));
         return true;
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
      drawTextWithShadow(matrices, this.textRenderer, ENTER_NAME_TEXT, this.width / 2 - 100, 24, 10526880);
      this.levelNameTextField.render(matrices, mouseX, mouseY, delta);
      super.render(matrices, mouseX, mouseY, delta);
   }
}
