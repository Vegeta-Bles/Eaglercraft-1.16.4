package net.minecraft.client.gui.screen.world;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.dynamic.RegistryReadingOps;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class MoreOptionsDialog implements TickableElement, Drawable {
   private static final Logger field_25046 = LogManager.getLogger();
   private static final Text field_25047 = new TranslatableText("generator.custom");
   private static final Text AMPLIFIED_INFO_TEXT = new TranslatableText("generator.amplified.info");
   private static final Text field_26604 = new TranslatableText("selectWorld.mapFeatures.info");
   private class_5489 field_26605 = class_5489.field_26528;
   private TextRenderer textRenderer;
   private int parentWidth;
   private TextFieldWidget seedTextField;
   private ButtonWidget mapFeaturesButton;
   public ButtonWidget bonusItemsButton;
   private ButtonWidget mapTypeButton;
   private ButtonWidget customizeTypeButton;
   private ButtonWidget field_25048;
   private DynamicRegistryManager.Impl field_25483;
   private GeneratorOptions generatorOptions;
   private Optional<GeneratorType> field_25049;
   private OptionalLong seedText;

   public MoreOptionsDialog(DynamicRegistryManager.Impl _snowman, GeneratorOptions _snowman, Optional<GeneratorType> _snowman, OptionalLong _snowman) {
      this.field_25483 = _snowman;
      this.generatorOptions = _snowman;
      this.field_25049 = _snowman;
      this.seedText = _snowman;
   }

   public void method_28092(CreateWorldScreen parent, MinecraftClient client, TextRenderer textRenderer) {
      this.textRenderer = textRenderer;
      this.parentWidth = parent.width;
      this.seedTextField = new TextFieldWidget(this.textRenderer, this.parentWidth / 2 - 100, 60, 200, 20, new TranslatableText("selectWorld.enterSeed"));
      this.seedTextField.setText(method_30510(this.seedText));
      this.seedTextField.setChangedListener(_snowman -> this.seedText = this.method_30511());
      parent.addChild(this.seedTextField);
      int _snowman = this.parentWidth / 2 - 155;
      int _snowmanx = this.parentWidth / 2 + 5;
      this.mapFeaturesButton = parent.addButton(new ButtonWidget(_snowman, 100, 150, 20, new TranslatableText("selectWorld.mapFeatures"), _snowmanxx -> {
         this.generatorOptions = this.generatorOptions.toggleGenerateStructures();
         _snowmanxx.queueNarration(250);
      }) {
         @Override
         public Text getMessage() {
            return ScreenTexts.composeToggleText(super.getMessage(), MoreOptionsDialog.this.generatorOptions.shouldGenerateStructures());
         }

         @Override
         protected MutableText getNarrationMessage() {
            return super.getNarrationMessage().append(". ").append(new TranslatableText("selectWorld.mapFeatures.info"));
         }
      });
      this.mapFeaturesButton.visible = false;
      this.mapTypeButton = parent.addButton(
         new ButtonWidget(
            _snowmanx,
            100,
            150,
            20,
            new TranslatableText("selectWorld.mapType"),
            _snowmanxx -> {
               while (this.field_25049.isPresent()) {
                  int _snowmanxxx = GeneratorType.VALUES.indexOf(this.field_25049.get()) + 1;
                  if (_snowmanxxx >= GeneratorType.VALUES.size()) {
                     _snowmanxxx = 0;
                  }

                  GeneratorType _snowmanxx = GeneratorType.VALUES.get(_snowmanxxx);
                  this.field_25049 = Optional.of(_snowmanxx);
                  this.generatorOptions = _snowmanxx.createDefaultOptions(
                     this.field_25483, this.generatorOptions.getSeed(), this.generatorOptions.shouldGenerateStructures(), this.generatorOptions.hasBonusChest()
                  );
                  if (!this.generatorOptions.isDebugWorld() || Screen.hasShiftDown()) {
                     break;
                  }
               }

               parent.setMoreOptionsOpen();
               _snowmanxx.queueNarration(250);
            }
         ) {
            @Override
            public Text getMessage() {
               return super.getMessage()
                  .shallowCopy()
                  .append(" ")
                  .append(MoreOptionsDialog.this.field_25049.map(GeneratorType::getTranslationKey).orElse(MoreOptionsDialog.field_25047));
            }

            @Override
            protected MutableText getNarrationMessage() {
               return Objects.equals(MoreOptionsDialog.this.field_25049, Optional.of(GeneratorType.AMPLIFIED))
                  ? super.getNarrationMessage().append(". ").append(MoreOptionsDialog.AMPLIFIED_INFO_TEXT)
                  : super.getNarrationMessage();
            }
         }
      );
      this.mapTypeButton.visible = false;
      this.mapTypeButton.active = this.field_25049.isPresent();
      this.customizeTypeButton = parent.addButton(new ButtonWidget(_snowmanx, 120, 150, 20, new TranslatableText("selectWorld.customizeType"), _snowmanxx -> {
         GeneratorType.ScreenProvider _snowmanxx = GeneratorType.SCREEN_PROVIDERS.get(this.field_25049);
         if (_snowmanxx != null) {
            client.openScreen(_snowmanxx.createEditScreen(parent, this.generatorOptions));
         }
      }));
      this.customizeTypeButton.visible = false;
      this.bonusItemsButton = parent.addButton(new ButtonWidget(_snowman, 151, 150, 20, new TranslatableText("selectWorld.bonusItems"), _snowmanxx -> {
         this.generatorOptions = this.generatorOptions.toggleBonusChest();
         _snowmanxx.queueNarration(250);
      }) {
         @Override
         public Text getMessage() {
            return ScreenTexts.composeToggleText(super.getMessage(), MoreOptionsDialog.this.generatorOptions.hasBonusChest() && !parent.hardcore);
         }
      });
      this.bonusItemsButton.visible = false;
      this.field_25048 = parent.addButton(
         new ButtonWidget(
            _snowman,
            185,
            150,
            20,
            new TranslatableText("selectWorld.import_worldgen_settings"),
            _snowmanxx -> {
               TranslatableText _snowmanxx = new TranslatableText("selectWorld.import_worldgen_settings.select_file");
               String _snowmanx = TinyFileDialogs.tinyfd_openFileDialog(_snowmanxx.getString(), null, null, null, false);
               if (_snowmanx != null) {
                  DynamicRegistryManager.Impl _snowmanxxx = DynamicRegistryManager.create();
                  ResourcePackManager _snowmanxxxx = new ResourcePackManager(
                     new VanillaDataPackProvider(), new FileResourcePackProvider(parent.method_29693().toFile(), ResourcePackSource.PACK_SOURCE_WORLD)
                  );

                  ServerResourceManager _snowmanxxxxx;
                  try {
                     MinecraftServer.loadDataPacks(_snowmanxxxx, parent.field_25479, false);
                     CompletableFuture<ServerResourceManager> _snowmanxxxxxx = ServerResourceManager.reload(
                        _snowmanxxxx.createResourcePacks(), CommandManager.RegistrationEnvironment.INTEGRATED, 2, Util.getMainWorkerExecutor(), client
                     );
                     client.runTasks(_snowmanxxxxxx::isDone);
                     _snowmanxxxxx = _snowmanxxxxxx.get();
                  } catch (ExecutionException | InterruptedException var25) {
                     field_25046.error("Error loading data packs when importing world settings", var25);
                     Text _snowmanxxxxxxx = new TranslatableText("selectWorld.import_worldgen_settings.failure");
                     Text _snowmanxxxxxxxx = new LiteralText(var25.getMessage());
                     client.getToastManager().add(SystemToast.create(client, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, _snowmanxxxxxxx, _snowmanxxxxxxxx));
                     _snowmanxxxx.close();
                     return;
                  }

                  RegistryOps<JsonElement> _snowmanxxxxxx = RegistryOps.of(JsonOps.INSTANCE, _snowmanxxxxx.getResourceManager(), _snowmanxxx);
                  JsonParser _snowmanxxxxxxx = new JsonParser();

                  DataResult<GeneratorOptions> _snowmanxxxxxxxx;
                  try (BufferedReader _snowmanxxxxxxxxx = Files.newBufferedReader(Paths.get(_snowmanx))) {
                     JsonElement _snowmanxxxxxxxxxx = _snowmanxxxxxxx.parse(_snowmanxxxxxxxxx);
                     _snowmanxxxxxxxx = GeneratorOptions.CODEC.parse(_snowmanxxxxxx, _snowmanxxxxxxxxxx);
                  } catch (JsonIOException | JsonSyntaxException | IOException var27) {
                     _snowmanxxxxxxxx = DataResult.error("Failed to parse file: " + var27.getMessage());
                  }

                  if (_snowmanxxxxxxxx.error().isPresent()) {
                     Text _snowmanxxxxxxxxx = new TranslatableText("selectWorld.import_worldgen_settings.failure");
                     String _snowmanxxxxxxxxxx = ((PartialResult)_snowmanxxxxxxxx.error().get()).message();
                     field_25046.error("Error parsing world settings: {}", _snowmanxxxxxxxxxx);
                     Text _snowmanxxxxxxxxxxx = new LiteralText(_snowmanxxxxxxxxxx);
                     client.getToastManager().add(SystemToast.create(client, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx));
                  }

                  _snowmanxxxxx.close();
                  Lifecycle _snowmanxxxxxxxxx = _snowmanxxxxxxxx.lifecycle();
                  _snowmanxxxxxxxx.resultOrPartial(field_25046::error)
                     .ifPresent(
                        _snowmanxxxxxxxxxx -> {
                           BooleanConsumer _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxx -> {
                              client.openScreen(parent);
                              if (_snowmanxxxxxxxxxxxx) {
                                 this.method_29073(_snowman, _snowmanxxxx);
                              }
                           };
                           if (_snowman == Lifecycle.stable()) {
                              this.method_29073(_snowman, _snowmanxxxxxxxxxx);
                           } else if (_snowman == Lifecycle.experimental()) {
                              client.openScreen(
                                 new ConfirmScreen(
                                    _snowmanxxxxxxxxxxx,
                                    new TranslatableText("selectWorld.import_worldgen_settings.experimental.title"),
                                    new TranslatableText("selectWorld.import_worldgen_settings.experimental.question")
                                 )
                              );
                           } else {
                              client.openScreen(
                                 new ConfirmScreen(
                                    _snowmanxxxxxxxxxxx,
                                    new TranslatableText("selectWorld.import_worldgen_settings.deprecated.title"),
                                    new TranslatableText("selectWorld.import_worldgen_settings.deprecated.question")
                                 )
                              );
                           }
                        }
                     );
               }
            }
         )
      );
      this.field_25048.visible = false;
      this.field_26605 = class_5489.method_30890(textRenderer, AMPLIFIED_INFO_TEXT, this.mapTypeButton.getWidth());
   }

   private void method_29073(DynamicRegistryManager.Impl _snowman, GeneratorOptions _snowman) {
      this.field_25483 = _snowman;
      this.generatorOptions = _snowman;
      this.field_25049 = GeneratorType.method_29078(_snowman);
      this.seedText = OptionalLong.of(_snowman.getSeed());
      this.seedTextField.setText(method_30510(this.seedText));
      this.mapTypeButton.active = this.field_25049.isPresent();
   }

   @Override
   public void tick() {
      this.seedTextField.tick();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.mapFeaturesButton.visible) {
         this.textRenderer.drawWithShadow(matrices, field_26604, (float)(this.parentWidth / 2 - 150), 122.0F, -6250336);
      }

      this.seedTextField.render(matrices, mouseX, mouseY, delta);
      if (this.field_25049.equals(Optional.of(GeneratorType.AMPLIFIED))) {
         this.field_26605.method_30893(matrices, this.mapTypeButton.x + 2, this.mapTypeButton.y + 22, 9, 10526880);
      }
   }

   protected void setGeneratorOptions(GeneratorOptions _snowman) {
      this.generatorOptions = _snowman;
   }

   private static String method_30510(OptionalLong _snowman) {
      return _snowman.isPresent() ? Long.toString(_snowman.getAsLong()) : "";
   }

   private static OptionalLong tryParseLong(String _snowman) {
      try {
         return OptionalLong.of(Long.parseLong(_snowman));
      } catch (NumberFormatException var2) {
         return OptionalLong.empty();
      }
   }

   public GeneratorOptions getGeneratorOptions(boolean hardcore) {
      OptionalLong _snowman = this.method_30511();
      return this.generatorOptions.withHardcore(hardcore, _snowman);
   }

   private OptionalLong method_30511() {
      String _snowman = this.seedTextField.getText();
      OptionalLong _snowmanx;
      if (StringUtils.isEmpty(_snowman)) {
         _snowmanx = OptionalLong.empty();
      } else {
         OptionalLong _snowmanxx = tryParseLong(_snowman);
         if (_snowmanxx.isPresent() && _snowmanxx.getAsLong() != 0L) {
            _snowmanx = _snowmanxx;
         } else {
            _snowmanx = OptionalLong.of((long)_snowman.hashCode());
         }
      }

      return _snowmanx;
   }

   public boolean isDebugWorld() {
      return this.generatorOptions.isDebugWorld();
   }

   public void setVisible(boolean visible) {
      this.mapTypeButton.visible = visible;
      if (this.generatorOptions.isDebugWorld()) {
         this.mapFeaturesButton.visible = false;
         this.bonusItemsButton.visible = false;
         this.customizeTypeButton.visible = false;
         this.field_25048.visible = false;
      } else {
         this.mapFeaturesButton.visible = visible;
         this.bonusItemsButton.visible = visible;
         this.customizeTypeButton.visible = visible && GeneratorType.SCREEN_PROVIDERS.containsKey(this.field_25049);
         this.field_25048.visible = visible;
      }

      this.seedTextField.setVisible(visible);
   }

   public DynamicRegistryManager.Impl method_29700() {
      return this.field_25483;
   }

   void method_31132(ServerResourceManager _snowman) {
      DynamicRegistryManager.Impl _snowmanx = DynamicRegistryManager.create();
      RegistryReadingOps<JsonElement> _snowmanxx = RegistryReadingOps.of(JsonOps.INSTANCE, this.field_25483);
      RegistryOps<JsonElement> _snowmanxxx = RegistryOps.of(JsonOps.INSTANCE, _snowman.getResourceManager(), _snowmanx);
      DataResult<GeneratorOptions> _snowmanxxxx = GeneratorOptions.CODEC
         .encodeStart(_snowmanxx, this.generatorOptions)
         .flatMap(_snowmanxxxxx -> GeneratorOptions.CODEC.parse(_snowman, _snowmanxxxxx));
      _snowmanxxxx.resultOrPartial(Util.method_29188("Error parsing worldgen settings after loading data packs: ", field_25046::error)).ifPresent(_snowmanxxxxx -> {
         this.generatorOptions = _snowmanxxxxx;
         this.field_25483 = _snowman;
      });
   }
}
