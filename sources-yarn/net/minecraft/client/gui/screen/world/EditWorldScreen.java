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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class EditWorldScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
   private static final Text ENTER_NAME_TEXT = new TranslatableText("selectWorld.enterName");
   private ButtonWidget saveButton;
   private final BooleanConsumer callback;
   private TextFieldWidget levelNameTextField;
   private final LevelStorage.Session field_23777;

   public EditWorldScreen(BooleanConsumer callback, LevelStorage.Session arg) {
      super(new TranslatableText("selectWorld.edit.title"));
      this.callback = callback;
      this.field_23777 = arg;
   }

   @Override
   public void tick() {
      this.levelNameTextField.tick();
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      ButtonWidget lv = this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height / 4 + 0 + 5, 200, 20, new TranslatableText("selectWorld.edit.resetIcon"), arg -> {
            FileUtils.deleteQuietly(this.field_23777.getIconFile());
            arg.active = false;
         })
      );
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 24 + 5,
            200,
            20,
            new TranslatableText("selectWorld.edit.openFolder"),
            arg -> Util.getOperatingSystem().open(this.field_23777.getDirectory(WorldSavePath.ROOT).toFile())
         )
      );
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 48 + 5, 200, 20, new TranslatableText("selectWorld.edit.backup"), arg -> {
         boolean bl = backupLevel(this.field_23777);
         this.callback.accept(!bl);
      }));
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 72 + 5, 200, 20, new TranslatableText("selectWorld.edit.backupFolder"), arg -> {
         LevelStorage lvx = this.client.getLevelStorage();
         Path path = lvx.getBackupsDirectory();

         try {
            Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }

         Util.getOperatingSystem().open(path.toFile());
      }));
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 100,
            this.height / 4 + 96 + 5,
            200,
            20,
            new TranslatableText("selectWorld.edit.optimize"),
            arg -> this.client.openScreen(new BackupPromptScreen(this, (bl, bl2) -> {
                  if (bl) {
                     backupLevel(this.field_23777);
                  }

                  this.client.openScreen(OptimizeWorldScreen.method_27031(this.client, this.callback, this.client.getDataFixer(), this.field_23777, bl2));
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
            arg -> {
               DynamicRegistryManager.Impl lvx = DynamicRegistryManager.create();

               DataResult<String> dataResult2;
               try (MinecraftClient.IntegratedResourceManager lv2x = this.client
                     .method_29604(lvx, MinecraftClient::method_29598, MinecraftClient::createSaveProperties, false, this.field_23777)) {
                  DynamicOps<JsonElement> dynamicOps = RegistryReadingOps.of(JsonOps.INSTANCE, lvx);
                  DataResult<JsonElement> dataResult = GeneratorOptions.CODEC.encodeStart(dynamicOps, lv2x.getSaveProperties().getGeneratorOptions());
                  dataResult2 = dataResult.flatMap(jsonElement -> {
                     Path path = this.field_23777.getDirectory(WorldSavePath.ROOT).resolve("worldgen_settings_export.json");

                     try {
                        JsonWriter jsonWriter = GSON.newJsonWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8));
                        Throwable var4x = null;

                        try {
                           GSON.toJson(jsonElement, jsonWriter);
                        } catch (Throwable var14) {
                           var4x = var14;
                           throw var14;
                        } finally {
                           if (jsonWriter != null) {
                              if (var4x != null) {
                                 try {
                                    jsonWriter.close();
                                 } catch (Throwable var13) {
                                    var4x.addSuppressed(var13);
                                 }
                              } else {
                                 jsonWriter.close();
                              }
                           }
                        }
                     } catch (JsonIOException | IOException var16) {
                        return DataResult.error("Error writing file: " + var16.getMessage());
                     }

                     return DataResult.success(path.toString());
                  });
               } catch (ExecutionException | InterruptedException var18) {
                  dataResult2 = DataResult.error("Could not parse level data!");
               }

               Text lv3 = new LiteralText((String)dataResult2.get().map(Function.identity(), PartialResult::message));
               Text lv4 = new TranslatableText(
                  dataResult2.result().isPresent() ? "selectWorld.edit.export_worldgen_settings.success" : "selectWorld.edit.export_worldgen_settings.failure"
               );
               dataResult2.error().ifPresent(partialResult -> LOGGER.error("Error exporting world settings: {}", partialResult));
               this.client.getToastManager().add(SystemToast.create(this.client, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, lv4, lv3));
            }
         )
      );
      this.saveButton = this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height / 4 + 144 + 5, 98, 20, new TranslatableText("selectWorld.edit.save"), arg -> this.commit())
      );
      this.addButton(new ButtonWidget(this.width / 2 + 2, this.height / 4 + 144 + 5, 98, 20, ScreenTexts.CANCEL, arg -> this.callback.accept(false)));
      lv.active = this.field_23777.getIconFile().isFile();
      LevelSummary lv2 = this.field_23777.getLevelSummary();
      String string = lv2 == null ? "" : lv2.getDisplayName();
      this.levelNameTextField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 38, 200, 20, new TranslatableText("selectWorld.enterName"));
      this.levelNameTextField.setText(string);
      this.levelNameTextField.setChangedListener(stringx -> this.saveButton.active = !stringx.trim().isEmpty());
      this.children.add(this.levelNameTextField);
      this.setInitialFocus(this.levelNameTextField);
   }

   @Override
   public void resize(MinecraftClient client, int width, int height) {
      String string = this.levelNameTextField.getText();
      this.init(client, width, height);
      this.levelNameTextField.setText(string);
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

   public static void method_29784(LevelStorage arg, String string) {
      boolean bl = false;

      try (LevelStorage.Session lv = arg.createSession(string)) {
         bl = true;
         backupLevel(lv);
      } catch (IOException var16) {
         if (!bl) {
            SystemToast.addWorldAccessFailureToast(MinecraftClient.getInstance(), string);
         }

         LOGGER.warn("Failed to create backup of level {}", string, var16);
      }
   }

   public static boolean backupLevel(LevelStorage.Session arg) {
      long l = 0L;
      IOException iOException = null;

      try {
         l = arg.createBackup();
      } catch (IOException var6) {
         iOException = var6;
      }

      if (iOException != null) {
         Text lv = new TranslatableText("selectWorld.edit.backupFailed");
         Text lv2 = new LiteralText(iOException.getMessage());
         MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, lv, lv2));
         return false;
      } else {
         Text lv3 = new TranslatableText("selectWorld.edit.backupCreated", arg.getDirectoryName());
         Text lv4 = new TranslatableText("selectWorld.edit.backupSize", MathHelper.ceil((double)l / 1048576.0));
         MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, lv3, lv4));
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
