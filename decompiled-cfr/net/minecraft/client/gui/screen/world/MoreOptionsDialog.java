/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonIOException
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DataResult$PartialResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 */
package net.minecraft.client.gui.screen.world;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
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
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
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

public class MoreOptionsDialog
implements TickableElement,
Drawable {
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

    public MoreOptionsDialog(DynamicRegistryManager.Impl impl, GeneratorOptions generatorOptions, Optional<GeneratorType> optional, OptionalLong optionalLong) {
        this.field_25483 = impl;
        this.generatorOptions = generatorOptions;
        this.field_25049 = optional;
        this.seedText = optionalLong;
    }

    public void method_28092(CreateWorldScreen parent, MinecraftClient client, TextRenderer textRenderer) {
        this.textRenderer = textRenderer;
        this.parentWidth = parent.width;
        this.seedTextField = new TextFieldWidget(this.textRenderer, this.parentWidth / 2 - 100, 60, 200, 20, new TranslatableText("selectWorld.enterSeed"));
        this.seedTextField.setText(MoreOptionsDialog.method_30510(this.seedText));
        this.seedTextField.setChangedListener(string -> {
            this.seedText = this.method_30511();
        });
        parent.addChild(this.seedTextField);
        int n = this.parentWidth / 2 - 155;
        _snowman = this.parentWidth / 2 + 5;
        this.mapFeaturesButton = parent.addButton(new ButtonWidget(this, n, 100, 150, 20, new TranslatableText("selectWorld.mapFeatures"), buttonWidget -> {
            this.generatorOptions = this.generatorOptions.toggleGenerateStructures();
            buttonWidget.queueNarration(250);
        }){
            final /* synthetic */ MoreOptionsDialog field_24601;
            {
                this.field_24601 = moreOptionsDialog;
                super(n, n2, n3, n4, text, pressAction);
            }

            public Text getMessage() {
                return ScreenTexts.composeToggleText(super.getMessage(), MoreOptionsDialog.method_28094(this.field_24601).shouldGenerateStructures());
            }

            protected MutableText getNarrationMessage() {
                return super.getNarrationMessage().append(". ").append(new TranslatableText("selectWorld.mapFeatures.info"));
            }
        });
        this.mapFeaturesButton.visible = false;
        this.mapTypeButton = parent.addButton(new ButtonWidget(this, _snowman, 100, 150, 20, new TranslatableText("selectWorld.mapType"), buttonWidget -> {
            while (this.field_25049.isPresent()) {
                int n = GeneratorType.VALUES.indexOf(this.field_25049.get()) + 1;
                if (n >= GeneratorType.VALUES.size()) {
                    n = 0;
                }
                GeneratorType _snowman2 = GeneratorType.VALUES.get(n);
                this.field_25049 = Optional.of(_snowman2);
                this.generatorOptions = _snowman2.createDefaultOptions(this.field_25483, this.generatorOptions.getSeed(), this.generatorOptions.shouldGenerateStructures(), this.generatorOptions.hasBonusChest());
                if (this.generatorOptions.isDebugWorld() && !Screen.hasShiftDown()) continue;
            }
            parent.setMoreOptionsOpen();
            buttonWidget.queueNarration(250);
        }){
            final /* synthetic */ MoreOptionsDialog field_24602;
            {
                this.field_24602 = moreOptionsDialog;
                super(n, n2, n3, n4, text, pressAction);
            }

            public Text getMessage() {
                return super.getMessage().shallowCopy().append(" ").append(MoreOptionsDialog.method_29074(this.field_24602).map(GeneratorType::getTranslationKey).orElse(MoreOptionsDialog.method_29072()));
            }

            protected MutableText getNarrationMessage() {
                if (Objects.equals(MoreOptionsDialog.method_29074(this.field_24602), Optional.of(GeneratorType.AMPLIFIED))) {
                    return super.getNarrationMessage().append(". ").append(MoreOptionsDialog.method_28097());
                }
                return super.getNarrationMessage();
            }
        });
        this.mapTypeButton.visible = false;
        this.mapTypeButton.active = this.field_25049.isPresent();
        this.customizeTypeButton = parent.addButton(new ButtonWidget(_snowman, 120, 150, 20, new TranslatableText("selectWorld.customizeType"), buttonWidget -> {
            GeneratorType.ScreenProvider screenProvider = GeneratorType.SCREEN_PROVIDERS.get(this.field_25049);
            if (screenProvider != null) {
                client.openScreen(screenProvider.createEditScreen(parent, this.generatorOptions));
            }
        }));
        this.customizeTypeButton.visible = false;
        this.bonusItemsButton = parent.addButton(new ButtonWidget(this, n, 151, 150, 20, new TranslatableText("selectWorld.bonusItems"), buttonWidget -> {
            this.generatorOptions = this.generatorOptions.toggleBonusChest();
            buttonWidget.queueNarration(250);
        }, parent){
            final /* synthetic */ CreateWorldScreen field_24603;
            final /* synthetic */ MoreOptionsDialog field_24604;
            {
                this.field_24604 = moreOptionsDialog;
                this.field_24603 = createWorldScreen;
                super(n, n2, n3, n4, text, pressAction);
            }

            public Text getMessage() {
                return ScreenTexts.composeToggleText(super.getMessage(), MoreOptionsDialog.method_28094(this.field_24604).hasBonusChest() && !this.field_24603.hardcore);
            }
        });
        this.bonusItemsButton.visible = false;
        this.field_25048 = parent.addButton(new ButtonWidget(n, 185, 150, 20, new TranslatableText("selectWorld.import_worldgen_settings"), buttonWidget -> {
            DataResult dataResult;
            ServerResourceManager _snowman5;
            Object object;
            TranslatableText translatableText = new TranslatableText("selectWorld.import_worldgen_settings.select_file");
            String _snowman2 = TinyFileDialogs.tinyfd_openFileDialog((CharSequence)translatableText.getString(), null, null, null, (boolean)false);
            if (_snowman2 == null) {
                return;
            }
            DynamicRegistryManager.Impl _snowman3 = DynamicRegistryManager.create();
            ResourcePackManager _snowman4 = new ResourcePackManager(new VanillaDataPackProvider(), new FileResourcePackProvider(parent.method_29693().toFile(), ResourcePackSource.PACK_SOURCE_WORLD));
            try {
                MinecraftServer.loadDataPacks(_snowman4, createWorldScreen.field_25479, false);
                object = ServerResourceManager.reload(_snowman4.createResourcePacks(), CommandManager.RegistrationEnvironment.INTEGRATED, 2, Util.getMainWorkerExecutor(), client);
                client.runTasks(() -> object.isDone());
                _snowman5 = ((CompletableFuture)object).get();
            }
            catch (InterruptedException | ExecutionException exception) {
                field_25046.error("Error loading data packs when importing world settings", (Throwable)exception);
                TranslatableText translatableText2 = new TranslatableText("selectWorld.import_worldgen_settings.failure");
                LiteralText _snowman6 = new LiteralText(exception.getMessage());
                client.getToastManager().add(SystemToast.create(client, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, translatableText2, _snowman6));
                _snowman4.close();
                return;
            }
            object = RegistryOps.of(JsonOps.INSTANCE, _snowman5.getResourceManager(), _snowman3);
            JsonParser _snowman7 = new JsonParser();
            try {
                object2 = Files.newBufferedReader(Paths.get(_snowman2, new String[0]));
                Throwable throwable = null;
                try {
                    throwable2 = _snowman7.parse((Reader)object2);
                    dataResult = GeneratorOptions.CODEC.parse((DynamicOps)object, throwable2);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (object2 != null) {
                        if (throwable != null) {
                            try {
                                ((BufferedReader)object2).close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        } else {
                            ((BufferedReader)object2).close();
                        }
                    }
                }
            }
            catch (JsonIOException | JsonSyntaxException | IOException throwable) {
                dataResult = DataResult.error((String)("Failed to parse file: " + throwable.getMessage()));
            }
            if (dataResult.error().isPresent()) {
                Object object2 = new TranslatableText("selectWorld.import_worldgen_settings.failure");
                String _snowman8 = ((DataResult.PartialResult)dataResult.error().get()).message();
                field_25046.error("Error parsing world settings: {}", (Object)_snowman8);
                throwable2 = new LiteralText(_snowman8);
                client.getToastManager().add(SystemToast.create(client, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, (Text)object2, (Text)throwable2));
            }
            _snowman5.close();
            object2 = dataResult.lifecycle();
            dataResult.resultOrPartial(arg_0 -> ((Logger)field_25046).error(arg_0)).ifPresent(arg_0 -> this.method_29070(client, parent, _snowman3, (Lifecycle)object2, arg_0));
        }));
        this.field_25048.visible = false;
        this.field_26605 = class_5489.method_30890(textRenderer, AMPLIFIED_INFO_TEXT, this.mapTypeButton.getWidth());
    }

    private void method_29073(DynamicRegistryManager.Impl impl, GeneratorOptions generatorOptions) {
        this.field_25483 = impl;
        this.generatorOptions = generatorOptions;
        this.field_25049 = GeneratorType.method_29078(generatorOptions);
        this.seedText = OptionalLong.of(generatorOptions.getSeed());
        this.seedTextField.setText(MoreOptionsDialog.method_30510(this.seedText));
        this.mapTypeButton.active = this.field_25049.isPresent();
    }

    @Override
    public void tick() {
        this.seedTextField.tick();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.mapFeaturesButton.visible) {
            this.textRenderer.drawWithShadow(matrices, field_26604, (float)(this.parentWidth / 2 - 150), 122.0f, -6250336);
        }
        this.seedTextField.render(matrices, mouseX, mouseY, delta);
        if (this.field_25049.equals(Optional.of(GeneratorType.AMPLIFIED))) {
            this.field_26605.method_30893(matrices, this.mapTypeButton.x + 2, this.mapTypeButton.y + 22, this.textRenderer.fontHeight, 0xA0A0A0);
        }
    }

    protected void setGeneratorOptions(GeneratorOptions generatorOptions) {
        this.generatorOptions = generatorOptions;
    }

    private static String method_30510(OptionalLong optionalLong) {
        if (optionalLong.isPresent()) {
            return Long.toString(optionalLong.getAsLong());
        }
        return "";
    }

    private static OptionalLong tryParseLong(String string) {
        try {
            return OptionalLong.of(Long.parseLong(string));
        }
        catch (NumberFormatException numberFormatException) {
            return OptionalLong.empty();
        }
    }

    public GeneratorOptions getGeneratorOptions(boolean hardcore) {
        OptionalLong optionalLong = this.method_30511();
        return this.generatorOptions.withHardcore(hardcore, optionalLong);
    }

    private OptionalLong method_30511() {
        String string = this.seedTextField.getText();
        OptionalLong _snowman2 = StringUtils.isEmpty((CharSequence)string) ? OptionalLong.empty() : ((_snowman = MoreOptionsDialog.tryParseLong(string)).isPresent() && _snowman.getAsLong() != 0L ? _snowman : OptionalLong.of(string.hashCode()));
        return _snowman2;
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

    void method_31132(ServerResourceManager serverResourceManager) {
        DynamicRegistryManager.Impl impl = DynamicRegistryManager.create();
        RegistryReadingOps _snowman2 = RegistryReadingOps.of(JsonOps.INSTANCE, this.field_25483);
        RegistryOps _snowman3 = RegistryOps.of(JsonOps.INSTANCE, serverResourceManager.getResourceManager(), impl);
        DataResult _snowman4 = GeneratorOptions.CODEC.encodeStart(_snowman2, (Object)this.generatorOptions).flatMap(jsonElement -> GeneratorOptions.CODEC.parse((DynamicOps)_snowman3, jsonElement));
        _snowman4.resultOrPartial(Util.method_29188("Error parsing worldgen settings after loading data packs: ", arg_0 -> ((Logger)field_25046).error(arg_0))).ifPresent(generatorOptions -> {
            this.generatorOptions = generatorOptions;
            this.field_25483 = impl;
        });
    }

    private /* synthetic */ void method_29070(MinecraftClient minecraftClient, CreateWorldScreen createWorldScreen, DynamicRegistryManager.Impl impl, Lifecycle lifecycle, GeneratorOptions generatorOptions) {
        BooleanConsumer booleanConsumer = bl -> {
            minecraftClient.openScreen(createWorldScreen);
            if (bl) {
                this.method_29073(impl, generatorOptions);
            }
        };
        if (lifecycle == Lifecycle.stable()) {
            this.method_29073(impl, generatorOptions);
        } else if (lifecycle == Lifecycle.experimental()) {
            minecraftClient.openScreen(new ConfirmScreen(booleanConsumer, new TranslatableText("selectWorld.import_worldgen_settings.experimental.title"), new TranslatableText("selectWorld.import_worldgen_settings.experimental.question")));
        } else {
            minecraftClient.openScreen(new ConfirmScreen(booleanConsumer, new TranslatableText("selectWorld.import_worldgen_settings.deprecated.title"), new TranslatableText("selectWorld.import_worldgen_settings.deprecated.question")));
        }
    }

    static /* synthetic */ GeneratorOptions method_28094(MoreOptionsDialog moreOptionsDialog) {
        return moreOptionsDialog.generatorOptions;
    }

    static /* synthetic */ Text method_29072() {
        return field_25047;
    }

    static /* synthetic */ Optional method_29074(MoreOptionsDialog moreOptionsDialog) {
        return moreOptionsDialog.field_25049;
    }

    static /* synthetic */ Text method_28097() {
        return AMPLIFIED_INFO_TEXT;
    }
}

