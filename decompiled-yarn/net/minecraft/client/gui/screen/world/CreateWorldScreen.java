package net.minecraft.client.gui.screen.world;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.resource.VanillaDataPackProvider;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.FileNameUtil;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWorldScreen extends Screen {
   private static final Logger field_25480 = LogManager.getLogger();
   private static final Text field_25898 = new TranslatableText("selectWorld.gameMode");
   private static final Text field_26598 = new TranslatableText("selectWorld.enterSeed");
   private static final Text field_26599 = new TranslatableText("selectWorld.seedInfo");
   private static final Text field_26600 = new TranslatableText("selectWorld.enterName");
   private static final Text field_26601 = new TranslatableText("selectWorld.resultFolder");
   private static final Text field_26602 = new TranslatableText("selectWorld.allowCommands.info");
   private final Screen parent;
   private TextFieldWidget levelNameField;
   private String saveDirectoryName;
   private CreateWorldScreen.Mode currentMode = CreateWorldScreen.Mode.SURVIVAL;
   @Nullable
   private CreateWorldScreen.Mode lastMode;
   private Difficulty field_24289 = Difficulty.NORMAL;
   private Difficulty field_24290 = Difficulty.NORMAL;
   private boolean cheatsEnabled;
   private boolean tweakedCheats;
   public boolean hardcore;
   protected DataPackSettings field_25479;
   @Nullable
   private Path field_25477;
   @Nullable
   private ResourcePackManager field_25792;
   private boolean moreOptionsOpen;
   private ButtonWidget createLevelButton;
   private ButtonWidget gameModeSwitchButton;
   private ButtonWidget difficultyButton;
   private ButtonWidget moreOptionsButton;
   private ButtonWidget gameRulesButton;
   private ButtonWidget dataPacksButton;
   private ButtonWidget enableCheatsButton;
   private Text firstGameModeDescriptionLine;
   private Text secondGameModeDescriptionLine;
   private String levelName;
   private GameRules gameRules = new GameRules();
   public final MoreOptionsDialog moreOptionsDialog;

   public CreateWorldScreen(@Nullable Screen _snowman, LevelInfo _snowman, GeneratorOptions _snowman, @Nullable Path _snowman, DataPackSettings _snowman, DynamicRegistryManager.Impl _snowman) {
      this(_snowman, _snowman, new MoreOptionsDialog(_snowman, _snowman, GeneratorType.method_29078(_snowman), OptionalLong.of(_snowman.getSeed())));
      this.levelName = _snowman.getLevelName();
      this.cheatsEnabled = _snowman.areCommandsAllowed();
      this.tweakedCheats = true;
      this.field_24289 = _snowman.getDifficulty();
      this.field_24290 = this.field_24289;
      this.gameRules.setAllValues(_snowman.getGameRules(), null);
      if (_snowman.isHardcore()) {
         this.currentMode = CreateWorldScreen.Mode.HARDCORE;
      } else if (_snowman.getGameMode().isSurvivalLike()) {
         this.currentMode = CreateWorldScreen.Mode.SURVIVAL;
      } else if (_snowman.getGameMode().isCreative()) {
         this.currentMode = CreateWorldScreen.Mode.CREATIVE;
      }

      this.field_25477 = _snowman;
   }

   public static CreateWorldScreen method_31130(@Nullable Screen _snowman) {
      DynamicRegistryManager.Impl _snowmanx = DynamicRegistryManager.create();
      return new CreateWorldScreen(
         _snowman,
         DataPackSettings.SAFE_MODE,
         new MoreOptionsDialog(
            _snowmanx,
            GeneratorOptions.getDefaultOptions(_snowmanx.get(Registry.DIMENSION_TYPE_KEY), _snowmanx.get(Registry.BIOME_KEY), _snowmanx.get(Registry.NOISE_SETTINGS_WORLDGEN)),
            Optional.of(GeneratorType.DEFAULT),
            OptionalLong.empty()
         )
      );
   }

   private CreateWorldScreen(@Nullable Screen _snowman, DataPackSettings _snowman, MoreOptionsDialog _snowman) {
      super(new TranslatableText("selectWorld.create"));
      this.parent = _snowman;
      this.levelName = I18n.translate("selectWorld.newWorld");
      this.field_25479 = _snowman;
      this.moreOptionsDialog = _snowman;
   }

   @Override
   public void tick() {
      this.levelNameField.tick();
      this.moreOptionsDialog.tick();
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.levelNameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 60, 200, 20, new TranslatableText("selectWorld.enterName")) {
         @Override
         protected MutableText getNarrationMessage() {
            return super.getNarrationMessage()
               .append(". ")
               .append(new TranslatableText("selectWorld.resultFolder"))
               .append(" ")
               .append(CreateWorldScreen.this.saveDirectoryName);
         }
      };
      this.levelNameField.setText(this.levelName);
      this.levelNameField.setChangedListener(_snowman -> {
         this.levelName = _snowman;
         this.createLevelButton.active = !this.levelNameField.getText().isEmpty();
         this.updateSaveFolderName();
      });
      this.children.add(this.levelNameField);
      int _snowman = this.width / 2 - 155;
      int _snowmanx = this.width / 2 + 5;
      this.gameModeSwitchButton = this.addButton(
         new ButtonWidget(_snowman, 100, 150, 20, LiteralText.EMPTY, _snowmanxx -> {
            switch (this.currentMode) {
               case SURVIVAL:
                  this.tweakDefaultsTo(CreateWorldScreen.Mode.HARDCORE);
                  break;
               case HARDCORE:
                  this.tweakDefaultsTo(CreateWorldScreen.Mode.CREATIVE);
                  break;
               case CREATIVE:
                  this.tweakDefaultsTo(CreateWorldScreen.Mode.SURVIVAL);
            }

            _snowmanxx.queueNarration(250);
         }) {
            @Override
            public Text getMessage() {
               return new TranslatableText(
                  "options.generic_value",
                  CreateWorldScreen.field_25898,
                  new TranslatableText("selectWorld.gameMode." + CreateWorldScreen.this.currentMode.translationSuffix)
               );
            }

            @Override
            protected MutableText getNarrationMessage() {
               return super.getNarrationMessage()
                  .append(". ")
                  .append(CreateWorldScreen.this.firstGameModeDescriptionLine)
                  .append(" ")
                  .append(CreateWorldScreen.this.secondGameModeDescriptionLine);
            }
         }
      );
      this.difficultyButton = this.addButton(new ButtonWidget(_snowmanx, 100, 150, 20, new TranslatableText("options.difficulty"), button -> {
         this.field_24289 = this.field_24289.cycle();
         this.field_24290 = this.field_24289;
         button.queueNarration(250);
      }) {
         @Override
         public Text getMessage() {
            return new TranslatableText("options.difficulty").append(": ").append(CreateWorldScreen.this.field_24290.getTranslatableName());
         }
      });
      this.enableCheatsButton = this.addButton(new ButtonWidget(_snowman, 151, 150, 20, new TranslatableText("selectWorld.allowCommands"), button -> {
         this.tweakedCheats = true;
         this.cheatsEnabled = !this.cheatsEnabled;
         button.queueNarration(250);
      }) {
         @Override
         public Text getMessage() {
            return ScreenTexts.composeToggleText(super.getMessage(), CreateWorldScreen.this.cheatsEnabled && !CreateWorldScreen.this.hardcore);
         }

         @Override
         protected MutableText getNarrationMessage() {
            return super.getNarrationMessage().append(". ").append(new TranslatableText("selectWorld.allowCommands.info"));
         }
      });
      this.dataPacksButton = this.addButton(new ButtonWidget(_snowmanx, 151, 150, 20, new TranslatableText("selectWorld.dataPacks"), button -> this.method_29694()));
      this.gameRulesButton = this.addButton(
         new ButtonWidget(
            _snowman,
            185,
            150,
            20,
            new TranslatableText("selectWorld.gameRules"),
            button -> this.client.openScreen(new EditGameRulesScreen(this.gameRules.copy(), _snowmanxx -> {
                  this.client.openScreen(this);
                  _snowmanxx.ifPresent(_snowmanxxx -> this.gameRules = _snowmanxxx);
               }))
         )
      );
      this.moreOptionsDialog.method_28092(this, this.client, this.textRenderer);
      this.moreOptionsButton = this.addButton(
         new ButtonWidget(_snowmanx, 185, 150, 20, new TranslatableText("selectWorld.moreWorldOptions"), _snowmanxx -> this.toggleMoreOptions())
      );
      this.createLevelButton = this.addButton(
         new ButtonWidget(_snowman, this.height - 28, 150, 20, new TranslatableText("selectWorld.create"), _snowmanxx -> this.createLevel())
      );
      this.createLevelButton.active = !this.levelName.isEmpty();
      this.addButton(new ButtonWidget(_snowmanx, this.height - 28, 150, 20, ScreenTexts.CANCEL, _snowmanxx -> this.method_30297()));
      this.setMoreOptionsOpen();
      this.setInitialFocus(this.levelNameField);
      this.tweakDefaultsTo(this.currentMode);
      this.updateSaveFolderName();
   }

   private void updateSettingsLabels() {
      this.firstGameModeDescriptionLine = new TranslatableText("selectWorld.gameMode." + this.currentMode.translationSuffix + ".line1");
      this.secondGameModeDescriptionLine = new TranslatableText("selectWorld.gameMode." + this.currentMode.translationSuffix + ".line2");
   }

   private void updateSaveFolderName() {
      this.saveDirectoryName = this.levelNameField.getText().trim();
      if (this.saveDirectoryName.isEmpty()) {
         this.saveDirectoryName = "World";
      }

      try {
         this.saveDirectoryName = FileNameUtil.getNextUniqueName(this.client.getLevelStorage().getSavesDirectory(), this.saveDirectoryName, "");
      } catch (Exception var4) {
         this.saveDirectoryName = "World";

         try {
            this.saveDirectoryName = FileNameUtil.getNextUniqueName(this.client.getLevelStorage().getSavesDirectory(), this.saveDirectoryName, "");
         } catch (Exception var3) {
            throw new RuntimeException("Could not create save folder", var3);
         }
      }
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   private void createLevel() {
      this.client.method_29970(new SaveLevelScreen(new TranslatableText("createWorld.preparing")));
      if (this.method_29696()) {
         this.method_30298();
         GeneratorOptions _snowman = this.moreOptionsDialog.getGeneratorOptions(this.hardcore);
         LevelInfo _snowmanx;
         if (_snowman.isDebugWorld()) {
            GameRules _snowmanxx = new GameRules();
            _snowmanxx.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, null);
            _snowmanx = new LevelInfo(this.levelNameField.getText().trim(), GameMode.SPECTATOR, false, Difficulty.PEACEFUL, true, _snowmanxx, DataPackSettings.SAFE_MODE);
         } else {
            _snowmanx = new LevelInfo(
               this.levelNameField.getText().trim(),
               this.currentMode.defaultGameMode,
               this.hardcore,
               this.field_24290,
               this.cheatsEnabled && !this.hardcore,
               this.gameRules,
               this.field_25479
            );
         }

         this.client.method_29607(this.saveDirectoryName, _snowmanx, this.moreOptionsDialog.method_29700(), _snowman);
      }
   }

   private void toggleMoreOptions() {
      this.setMoreOptionsOpen(!this.moreOptionsOpen);
   }

   private void tweakDefaultsTo(CreateWorldScreen.Mode _snowman) {
      if (!this.tweakedCheats) {
         this.cheatsEnabled = _snowman == CreateWorldScreen.Mode.CREATIVE;
      }

      if (_snowman == CreateWorldScreen.Mode.HARDCORE) {
         this.hardcore = true;
         this.enableCheatsButton.active = false;
         this.moreOptionsDialog.bonusItemsButton.active = false;
         this.field_24290 = Difficulty.HARD;
         this.difficultyButton.active = false;
      } else {
         this.hardcore = false;
         this.enableCheatsButton.active = true;
         this.moreOptionsDialog.bonusItemsButton.active = true;
         this.field_24290 = this.field_24289;
         this.difficultyButton.active = true;
      }

      this.currentMode = _snowman;
      this.updateSettingsLabels();
   }

   public void setMoreOptionsOpen() {
      this.setMoreOptionsOpen(this.moreOptionsOpen);
   }

   private void setMoreOptionsOpen(boolean moreOptionsOpen) {
      this.moreOptionsOpen = moreOptionsOpen;
      this.gameModeSwitchButton.visible = !this.moreOptionsOpen;
      this.difficultyButton.visible = !this.moreOptionsOpen;
      if (this.moreOptionsDialog.isDebugWorld()) {
         this.dataPacksButton.visible = false;
         this.gameModeSwitchButton.active = false;
         if (this.lastMode == null) {
            this.lastMode = this.currentMode;
         }

         this.tweakDefaultsTo(CreateWorldScreen.Mode.DEBUG);
         this.enableCheatsButton.visible = false;
      } else {
         this.gameModeSwitchButton.active = true;
         if (this.lastMode != null) {
            this.tweakDefaultsTo(this.lastMode);
         }

         this.enableCheatsButton.visible = !this.moreOptionsOpen;
         this.dataPacksButton.visible = !this.moreOptionsOpen;
      }

      this.moreOptionsDialog.setVisible(this.moreOptionsOpen);
      this.levelNameField.setVisible(!this.moreOptionsOpen);
      if (this.moreOptionsOpen) {
         this.moreOptionsButton.setMessage(ScreenTexts.DONE);
      } else {
         this.moreOptionsButton.setMessage(new TranslatableText("selectWorld.moreWorldOptions"));
      }

      this.gameRulesButton.visible = !this.moreOptionsOpen;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (keyCode != 257 && keyCode != 335) {
         return false;
      } else {
         this.createLevel();
         return true;
      }
   }

   @Override
   public void onClose() {
      if (this.moreOptionsOpen) {
         this.setMoreOptionsOpen(false);
      } else {
         this.method_30297();
      }
   }

   public void method_30297() {
      this.client.openScreen(this.parent);
      this.method_30298();
   }

   private void method_30298() {
      if (this.field_25792 != null) {
         this.field_25792.close();
      }

      this.method_29695();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, -1);
      if (this.moreOptionsOpen) {
         drawTextWithShadow(matrices, this.textRenderer, field_26598, this.width / 2 - 100, 47, -6250336);
         drawTextWithShadow(matrices, this.textRenderer, field_26599, this.width / 2 - 100, 85, -6250336);
         this.moreOptionsDialog.render(matrices, mouseX, mouseY, delta);
      } else {
         drawTextWithShadow(matrices, this.textRenderer, field_26600, this.width / 2 - 100, 47, -6250336);
         drawTextWithShadow(
            matrices, this.textRenderer, new LiteralText("").append(field_26601).append(" ").append(this.saveDirectoryName), this.width / 2 - 100, 85, -6250336
         );
         this.levelNameField.render(matrices, mouseX, mouseY, delta);
         drawTextWithShadow(matrices, this.textRenderer, this.firstGameModeDescriptionLine, this.width / 2 - 150, 122, -6250336);
         drawTextWithShadow(matrices, this.textRenderer, this.secondGameModeDescriptionLine, this.width / 2 - 150, 134, -6250336);
         if (this.enableCheatsButton.visible) {
            drawTextWithShadow(matrices, this.textRenderer, field_26602, this.width / 2 - 150, 172, -6250336);
         }
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   @Override
   protected <T extends Element> T addChild(T child) {
      return super.addChild(child);
   }

   @Override
   protected <T extends AbstractButtonWidget> T addButton(T button) {
      return super.addButton(button);
   }

   @Nullable
   protected Path method_29693() {
      if (this.field_25477 == null) {
         try {
            this.field_25477 = Files.createTempDirectory("mcworld-");
         } catch (IOException var2) {
            field_25480.warn("Failed to create temporary dir", var2);
            SystemToast.addPackCopyFailure(this.client, this.saveDirectoryName);
            this.method_30297();
         }
      }

      return this.field_25477;
   }

   private void method_29694() {
      Pair<File, ResourcePackManager> _snowman = this.method_30296();
      if (_snowman != null) {
         this.client
            .openScreen(
               new PackScreen(this, (ResourcePackManager)_snowman.getSecond(), this::method_29682, (File)_snowman.getFirst(), new TranslatableText("dataPack.title"))
            );
      }
   }

   private void method_29682(ResourcePackManager _snowman) {
      List<String> _snowmanx = ImmutableList.copyOf(_snowman.getEnabledNames());
      List<String> _snowmanxx = _snowman.getNames().stream().filter(_snowmanxxx -> !_snowman.contains(_snowmanxxx)).collect(ImmutableList.toImmutableList());
      DataPackSettings _snowmanxxx = new DataPackSettings(_snowmanx, _snowmanxx);
      if (_snowmanx.equals(this.field_25479.getEnabled())) {
         this.field_25479 = _snowmanxxx;
      } else {
         this.client.send(() -> this.client.openScreen(new SaveLevelScreen(new TranslatableText("dataPack.validation.working"))));
         ServerResourceManager.reload(_snowman.createResourcePacks(), CommandManager.RegistrationEnvironment.INTEGRATED, 2, Util.getMainWorkerExecutor(), this.client)
            .handle(
               (_snowmanxxxx, _snowmanxxxxx) -> {
                  if (_snowmanxxxxx != null) {
                     field_25480.warn("Failed to validate datapack", _snowmanxxxxx);
                     this.client
                        .send(
                           () -> this.client
                                 .openScreen(
                                    new ConfirmScreen(
                                       _snowmanxxxxxx -> {
                                          if (_snowmanxxxxxx) {
                                             this.method_29694();
                                          } else {
                                             this.field_25479 = DataPackSettings.SAFE_MODE;
                                             this.client.openScreen(this);
                                          }
                                       },
                                       new TranslatableText("dataPack.validation.failed"),
                                       LiteralText.EMPTY,
                                       new TranslatableText("dataPack.validation.back"),
                                       new TranslatableText("dataPack.validation.reset")
                                    )
                                 )
                        );
                  } else {
                     this.client.send(() -> {
                        this.field_25479 = _snowman;
                        this.moreOptionsDialog.method_31132(_snowmanxx);
                        _snowmanxx.close();
                        this.client.openScreen(this);
                     });
                  }

                  return null;
               }
            );
      }
   }

   private void method_29695() {
      if (this.field_25477 != null) {
         try (Stream<Path> _snowman = Files.walk(this.field_25477)) {
            _snowman.sorted(Comparator.reverseOrder()).forEach(_snowmanx -> {
               try {
                  Files.delete(_snowmanx);
               } catch (IOException var2) {
                  field_25480.warn("Failed to remove temporary file {}", _snowmanx, var2);
               }
            });
         } catch (IOException var14) {
            field_25480.warn("Failed to list temporary dir {}", this.field_25477);
         }

         this.field_25477 = null;
      }
   }

   private static void method_29687(Path _snowman, Path _snowman, Path _snowman) {
      try {
         Util.relativeCopy(_snowman, _snowman, _snowman);
      } catch (IOException var4) {
         field_25480.warn("Failed to copy datapack file from {} to {}", _snowman, _snowman);
         throw new CreateWorldScreen.WorldCreationException(var4);
      }
   }

   private boolean method_29696() {
      if (this.field_25477 != null) {
         try (
            LevelStorage.Session _snowman = this.client.getLevelStorage().createSession(this.saveDirectoryName);
            Stream<Path> _snowmanx = Files.walk(this.field_25477);
         ) {
            Path _snowmanxx = _snowman.getDirectory(WorldSavePath.DATAPACKS);
            Files.createDirectories(_snowmanxx);
            _snowmanx.filter(_snowmanxxx -> !_snowmanxxx.equals(this.field_25477)).forEach(_snowmanxxx -> method_29687(this.field_25477, _snowman, _snowmanxxx));
         } catch (CreateWorldScreen.WorldCreationException | IOException var33) {
            field_25480.warn("Failed to copy datapacks to world {}", this.saveDirectoryName, var33);
            SystemToast.addPackCopyFailure(this.client, this.saveDirectoryName);
            this.method_30297();
            return false;
         }
      }

      return true;
   }

   @Nullable
   public static Path method_29685(Path _snowman, MinecraftClient _snowman) {
      MutableObject<Path> _snowmanxx = new MutableObject();

      try (Stream<Path> _snowmanxxx = Files.walk(_snowman)) {
         _snowmanxxx.filter(_snowmanxxxx -> !_snowmanxxxx.equals(_snowman)).forEach(_snowmanxxxx -> {
            Path _snowmanxxxx = (Path)_snowman.getValue();
            if (_snowmanxxxx == null) {
               try {
                  _snowmanxxxx = Files.createTempDirectory("mcworld-");
               } catch (IOException var5) {
                  field_25480.warn("Failed to create temporary dir");
                  throw new CreateWorldScreen.WorldCreationException(var5);
               }

               _snowman.setValue(_snowmanxxxx);
            }

            method_29687(_snowman, _snowmanxxxx, _snowmanxxxx);
         });
      } catch (CreateWorldScreen.WorldCreationException | IOException var16) {
         field_25480.warn("Failed to copy datapacks from world {}", _snowman, var16);
         SystemToast.addPackCopyFailure(_snowman, _snowman.toString());
         return null;
      }

      return (Path)_snowmanxx.getValue();
   }

   @Nullable
   private Pair<File, ResourcePackManager> method_30296() {
      Path _snowman = this.method_29693();
      if (_snowman != null) {
         File _snowmanx = _snowman.toFile();
         if (this.field_25792 == null) {
            this.field_25792 = new ResourcePackManager(new VanillaDataPackProvider(), new FileResourcePackProvider(_snowmanx, ResourcePackSource.field_25347));
            this.field_25792.scanPacks();
         }

         this.field_25792.setEnabledProfiles(this.field_25479.getEnabled());
         return Pair.of(_snowmanx, this.field_25792);
      } else {
         return null;
      }
   }

   static enum Mode {
      SURVIVAL("survival", GameMode.SURVIVAL),
      HARDCORE("hardcore", GameMode.SURVIVAL),
      CREATIVE("creative", GameMode.CREATIVE),
      DEBUG("spectator", GameMode.SPECTATOR);

      private final String translationSuffix;
      private final GameMode defaultGameMode;

      private Mode(String translationSuffix, GameMode defaultGameMode) {
         this.translationSuffix = translationSuffix;
         this.defaultGameMode = defaultGameMode;
      }
   }

   static class WorldCreationException extends RuntimeException {
      public WorldCreationException(Throwable _snowman) {
         super(_snowman);
      }
   }
}
