/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Either;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.WorldTemplate;
import net.minecraft.client.realms.dto.WorldTemplatePaginatedList;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreenWithCallback;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.realms.util.TextRenderingUtils;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSelectWorldTemplateScreen
extends RealmsScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Identifier LINK_ICONS = new Identifier("realms", "textures/gui/realms/link_icons.png");
    private static final Identifier TRAILER_ICONS = new Identifier("realms", "textures/gui/realms/trailer_icons.png");
    private static final Identifier SLOT_FRAME = new Identifier("realms", "textures/gui/realms/slot_frame.png");
    private static final Text field_26512 = new TranslatableText("mco.template.info.tooltip");
    private static final Text field_26513 = new TranslatableText("mco.template.trailer.tooltip");
    private final RealmsScreenWithCallback parent;
    private WorldTemplateObjectSelectionList templateList;
    private int selectedTemplate = -1;
    private Text title;
    private ButtonWidget selectButton;
    private ButtonWidget trailerButton;
    private ButtonWidget publisherButton;
    @Nullable
    private Text toolTip;
    private String currentLink;
    private final RealmsServer.WorldType worldType;
    private int clicks;
    @Nullable
    private Text[] warning;
    private String warningURL;
    private boolean displayWarning;
    private boolean hoverWarning;
    @Nullable
    private List<TextRenderingUtils.Line> noTemplatesMessage;

    public RealmsSelectWorldTemplateScreen(RealmsScreenWithCallback parent, RealmsServer.WorldType worldType) {
        this(parent, worldType, null);
    }

    public RealmsSelectWorldTemplateScreen(RealmsScreenWithCallback parent, RealmsServer.WorldType worldType, @Nullable WorldTemplatePaginatedList list) {
        this.parent = parent;
        this.worldType = worldType;
        if (list == null) {
            this.templateList = new WorldTemplateObjectSelectionList();
            this.setPagination(new WorldTemplatePaginatedList(10));
        } else {
            this.templateList = new WorldTemplateObjectSelectionList(Lists.newArrayList(list.templates));
            this.setPagination(list);
        }
        this.title = new TranslatableText("mco.template.title");
    }

    public void setTitle(Text title) {
        this.title = title;
    }

    public void setWarning(Text ... textArray) {
        this.warning = textArray;
        this.displayWarning = true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.hoverWarning && this.warningURL != null) {
            Util.getOperatingSystem().open("https://www.minecraft.net/realms/adventure-maps-in-1-9");
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.templateList = new WorldTemplateObjectSelectionList(this.templateList.getValues());
        this.trailerButton = this.addButton(new ButtonWidget(this.width / 2 - 206, this.height - 32, 100, 20, new TranslatableText("mco.template.button.trailer"), buttonWidget -> this.onTrailer()));
        this.selectButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 32, 100, 20, new TranslatableText("mco.template.button.select"), buttonWidget -> this.selectTemplate()));
        Text text = this.worldType == RealmsServer.WorldType.MINIGAME ? ScreenTexts.CANCEL : ScreenTexts.BACK;
        ButtonWidget _snowman2 = new ButtonWidget(this.width / 2 + 6, this.height - 32, 100, 20, text, buttonWidget -> this.backButtonClicked());
        this.addButton(_snowman2);
        this.publisherButton = this.addButton(new ButtonWidget(this.width / 2 + 112, this.height - 32, 100, 20, new TranslatableText("mco.template.button.publisher"), buttonWidget -> this.onPublish()));
        this.selectButton.active = false;
        this.trailerButton.visible = false;
        this.publisherButton.visible = false;
        this.addChild(this.templateList);
        this.focusOn(this.templateList);
        Stream<Text> _snowman3 = Stream.of(this.title);
        if (this.warning != null) {
            _snowman3 = Stream.concat(Stream.of(this.warning), _snowman3);
        }
        Realms.narrateNow(_snowman3.filter(Objects::nonNull).map(Text::getString).collect(Collectors.toList()));
    }

    private void updateButtonStates() {
        this.publisherButton.visible = this.shouldPublisherBeVisible();
        this.trailerButton.visible = this.shouldTrailerBeVisible();
        this.selectButton.active = this.shouldSelectButtonBeActive();
    }

    private boolean shouldSelectButtonBeActive() {
        return this.selectedTemplate != -1;
    }

    private boolean shouldPublisherBeVisible() {
        return this.selectedTemplate != -1 && !this.method_21434().link.isEmpty();
    }

    private WorldTemplate method_21434() {
        return this.templateList.getItem(this.selectedTemplate);
    }

    private boolean shouldTrailerBeVisible() {
        return this.selectedTemplate != -1 && !this.method_21434().trailer.isEmpty();
    }

    @Override
    public void tick() {
        super.tick();
        --this.clicks;
        if (this.clicks < 0) {
            this.clicks = 0;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.backButtonClicked();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void backButtonClicked() {
        this.parent.callback(null);
        this.client.openScreen(this.parent);
    }

    private void selectTemplate() {
        if (this.method_25247()) {
            this.parent.callback(this.method_21434());
        }
    }

    private boolean method_25247() {
        return this.selectedTemplate >= 0 && this.selectedTemplate < this.templateList.getItemCount();
    }

    private void onTrailer() {
        if (this.method_25247()) {
            WorldTemplate worldTemplate = this.method_21434();
            if (!"".equals(worldTemplate.trailer)) {
                Util.getOperatingSystem().open(worldTemplate.trailer);
            }
        }
    }

    private void onPublish() {
        if (this.method_25247()) {
            WorldTemplate worldTemplate = this.method_21434();
            if (!"".equals(worldTemplate.link)) {
                Util.getOperatingSystem().open(worldTemplate.link);
            }
        }
    }

    private void setPagination(WorldTemplatePaginatedList worldTemplatePaginatedList) {
        new Thread(this, "realms-template-fetcher", worldTemplatePaginatedList){
            final /* synthetic */ WorldTemplatePaginatedList field_20091;
            final /* synthetic */ RealmsSelectWorldTemplateScreen field_20092;
            {
                this.field_20092 = realmsSelectWorldTemplateScreen;
                this.field_20091 = worldTemplatePaginatedList;
                super(string);
            }

            public void run() {
                WorldTemplatePaginatedList _snowman3 = this.field_20091;
                RealmsClient _snowman2 = RealmsClient.createRealmsClient();
                while (_snowman3 != null) {
                    Either either = RealmsSelectWorldTemplateScreen.method_21419(this.field_20092, _snowman3, _snowman2);
                    _snowman3 = RealmsSelectWorldTemplateScreen.method_25229(this.field_20092).submit(() -> {
                        if (either.right().isPresent()) {
                            RealmsSelectWorldTemplateScreen.method_21413().error("Couldn't fetch templates: {}", either.right().get());
                            if (RealmsSelectWorldTemplateScreen.method_21435(this.field_20092).isEmpty()) {
                                RealmsSelectWorldTemplateScreen.method_21421(this.field_20092, TextRenderingUtils.decompose(I18n.translate("mco.template.select.failure", new Object[0]), new TextRenderingUtils.LineSegment[0]));
                            }
                            return null;
                        }
                        WorldTemplatePaginatedList worldTemplatePaginatedList = (WorldTemplatePaginatedList)either.left().get();
                        for (WorldTemplate worldTemplate : worldTemplatePaginatedList.templates) {
                            RealmsSelectWorldTemplateScreen.method_21435(this.field_20092).addEntry(worldTemplate);
                        }
                        if (worldTemplatePaginatedList.templates.isEmpty()) {
                            if (RealmsSelectWorldTemplateScreen.method_21435(this.field_20092).isEmpty()) {
                                String string = I18n.translate("mco.template.select.none", "%link");
                                TextRenderingUtils.LineSegment lineSegment = TextRenderingUtils.LineSegment.link(I18n.translate("mco.template.select.none.linkTitle", new Object[0]), "https://aka.ms/MinecraftRealmsContentCreator");
                                RealmsSelectWorldTemplateScreen.method_21421(this.field_20092, TextRenderingUtils.decompose(string, lineSegment));
                            }
                            return null;
                        }
                        return worldTemplatePaginatedList;
                    }).join();
                }
            }
        }.start();
    }

    private Either<WorldTemplatePaginatedList, String> method_21416(WorldTemplatePaginatedList worldTemplatePaginatedList, RealmsClient realmsClient) {
        try {
            return Either.left((Object)realmsClient.fetchWorldTemplates(worldTemplatePaginatedList.page + 1, worldTemplatePaginatedList.size, this.worldType));
        }
        catch (RealmsServiceException realmsServiceException) {
            return Either.right((Object)realmsServiceException.getMessage());
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.toolTip = null;
        this.currentLink = null;
        this.hoverWarning = false;
        this.renderBackground(matrices);
        this.templateList.render(matrices, mouseX, mouseY, delta);
        if (this.noTemplatesMessage != null) {
            this.method_21414(matrices, mouseX, mouseY, this.noTemplatesMessage);
        }
        RealmsSelectWorldTemplateScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 13, 0xFFFFFF);
        if (this.displayWarning) {
            int n;
            Text[] textArray = this.warning;
            for (n = 0; n < textArray.length; ++n) {
                _snowman = this.textRenderer.getWidth(textArray[n]);
                _snowman2 = this.width / 2 - _snowman / 2;
                _snowman = RealmsSelectWorldTemplateScreen.row(-1 + n);
                if (mouseX < _snowman2 || mouseX > _snowman2 + _snowman || mouseY < _snowman || mouseY > _snowman + this.textRenderer.fontHeight) continue;
                this.hoverWarning = true;
            }
            for (n = 0; n < textArray.length; ++n) {
                Text text = textArray[n];
                int _snowman2 = 0xA0A0A0;
                if (this.warningURL != null) {
                    if (this.hoverWarning) {
                        _snowman2 = 7107012;
                        text = text.shallowCopy().formatted(Formatting.STRIKETHROUGH);
                    } else {
                        _snowman2 = 0x3366BB;
                    }
                }
                RealmsSelectWorldTemplateScreen.drawCenteredText(matrices, this.textRenderer, text, this.width / 2, RealmsSelectWorldTemplateScreen.row(-1 + n), _snowman2);
            }
        }
        super.render(matrices, mouseX, mouseY, delta);
        this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
    }

    private void method_21414(MatrixStack matrixStack, int n, int n2, List<TextRenderingUtils.Line> list) {
        for (int i = 0; i < list.size(); ++i) {
            TextRenderingUtils.Line line = list.get(i);
            int _snowman2 = RealmsSelectWorldTemplateScreen.row(4 + i);
            int _snowman3 = line.segments.stream().mapToInt(lineSegment -> this.textRenderer.getWidth(lineSegment.renderedText())).sum();
            int _snowman4 = this.width / 2 - _snowman3 / 2;
            for (TextRenderingUtils.LineSegment lineSegment2 : line.segments) {
                int n3 = lineSegment2.isLink() ? 0x3366BB : 0xFFFFFF;
                _snowman = this.textRenderer.drawWithShadow(matrixStack, lineSegment2.renderedText(), (float)_snowman4, (float)_snowman2, n3);
                if (lineSegment2.isLink() && n > _snowman4 && n < _snowman && n2 > _snowman2 - 3 && n2 < _snowman2 + 8) {
                    this.toolTip = new LiteralText(lineSegment2.getLinkUrl());
                    this.currentLink = lineSegment2.getLinkUrl();
                }
                _snowman4 = _snowman;
            }
        }
    }

    protected void renderMousehoverTooltip(MatrixStack matrixStack, @Nullable Text text, int n, int n2) {
        if (text == null) {
            return;
        }
        _snowman = n + 12;
        _snowman = n2 - 12;
        _snowman = this.textRenderer.getWidth(text);
        this.fillGradient(matrixStack, _snowman - 3, _snowman - 3, _snowman + _snowman + 3, _snowman + 8 + 3, -1073741824, -1073741824);
        this.textRenderer.drawWithShadow(matrixStack, text, (float)_snowman, (float)_snowman, 0xFFFFFF);
    }

    static /* synthetic */ Either method_21419(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, WorldTemplatePaginatedList worldTemplatePaginatedList, RealmsClient realmsClient) {
        return realmsSelectWorldTemplateScreen.method_21416(worldTemplatePaginatedList, realmsClient);
    }

    static /* synthetic */ MinecraftClient method_25229(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen) {
        return realmsSelectWorldTemplateScreen.client;
    }

    static /* synthetic */ Logger method_21413() {
        return LOGGER;
    }

    static /* synthetic */ List method_21421(RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen, List list) {
        realmsSelectWorldTemplateScreen.noTemplatesMessage = list;
        return realmsSelectWorldTemplateScreen.noTemplatesMessage;
    }

    class WorldTemplateObjectSelectionListEntry
    extends AlwaysSelectedEntryListWidget.Entry<WorldTemplateObjectSelectionListEntry> {
        private final WorldTemplate mTemplate;

        public WorldTemplateObjectSelectionListEntry(WorldTemplate template) {
            this.mTemplate = template;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.renderWorldTemplateItem(matrices, this.mTemplate, x, y, mouseX, mouseY);
        }

        private void renderWorldTemplateItem(MatrixStack matrixStack, WorldTemplate worldTemplate, int n, int n2, int n3, int n4) {
            _snowman = n + 45 + 20;
            RealmsSelectWorldTemplateScreen.this.textRenderer.draw(matrixStack, worldTemplate.name, (float)_snowman, (float)(n2 + 2), 0xFFFFFF);
            RealmsSelectWorldTemplateScreen.this.textRenderer.draw(matrixStack, worldTemplate.author, (float)_snowman, (float)(n2 + 15), 0x6C6C6C);
            RealmsSelectWorldTemplateScreen.this.textRenderer.draw(matrixStack, worldTemplate.version, (float)(_snowman + 227 - RealmsSelectWorldTemplateScreen.this.textRenderer.getWidth(worldTemplate.version)), (float)(n2 + 1), 0x6C6C6C);
            if (!("".equals(worldTemplate.link) && "".equals(worldTemplate.trailer) && "".equals(worldTemplate.recommendedPlayers))) {
                this.drawIcons(matrixStack, _snowman - 1, n2 + 25, n3, n4, worldTemplate.link, worldTemplate.trailer, worldTemplate.recommendedPlayers);
            }
            this.drawImage(matrixStack, n, n2 + 1, n3, n4, worldTemplate);
        }

        private void drawImage(MatrixStack matrixStack, int y, int xm, int ym, int n, WorldTemplate worldTemplate) {
            RealmsTextureManager.bindWorldTemplate(worldTemplate.id, worldTemplate.image);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            DrawableHelper.drawTexture(matrixStack, y + 1, xm + 1, 0.0f, 0.0f, 38, 38, 38, 38);
            RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(SLOT_FRAME);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            DrawableHelper.drawTexture(matrixStack, y, xm, 0.0f, 0.0f, 40, 40, 40, 40);
        }

        private void drawIcons(MatrixStack matrixStack, int n, int n2, int n3, int n4, String string, String string22, String string3) {
            String string22;
            if (!"".equals(string3)) {
                RealmsSelectWorldTemplateScreen.this.textRenderer.draw(matrixStack, string3, (float)n, (float)(n2 + 4), 0x4C4C4C);
            }
            int n5 = "".equals(string3) ? 0 : RealmsSelectWorldTemplateScreen.this.textRenderer.getWidth(string3) + 2;
            boolean _snowman2 = false;
            boolean _snowman3 = false;
            boolean _snowman4 = "".equals(string);
            if (n3 >= n + n5 && n3 <= n + n5 + 32 && n4 >= n2 && n4 <= n2 + 15 && n4 < RealmsSelectWorldTemplateScreen.this.height - 15 && n4 > 32) {
                if (n3 <= n + 15 + n5 && n3 > n5) {
                    if (_snowman4) {
                        _snowman3 = true;
                    } else {
                        _snowman2 = true;
                    }
                } else if (!_snowman4) {
                    _snowman3 = true;
                }
            }
            if (!_snowman4) {
                RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(LINK_ICONS);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.pushMatrix();
                RenderSystem.scalef(1.0f, 1.0f, 1.0f);
                float f = _snowman2 ? 15.0f : 0.0f;
                DrawableHelper.drawTexture(matrixStack, n + n5, n2, f, 0.0f, 15, 15, 30, 15);
                RenderSystem.popMatrix();
            }
            if (!"".equals(string22)) {
                RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(TRAILER_ICONS);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.pushMatrix();
                RenderSystem.scalef(1.0f, 1.0f, 1.0f);
                int n6 = n + n5 + (_snowman4 ? 0 : 17);
                float _snowman5 = _snowman3 ? 15.0f : 0.0f;
                DrawableHelper.drawTexture(matrixStack, n6, n2, _snowman5, 0.0f, 15, 15, 30, 15);
                RenderSystem.popMatrix();
            }
            if (_snowman2) {
                RealmsSelectWorldTemplateScreen.this.toolTip = field_26512;
                RealmsSelectWorldTemplateScreen.this.currentLink = string;
            } else if (_snowman3 && !"".equals(string22)) {
                RealmsSelectWorldTemplateScreen.this.toolTip = field_26513;
                RealmsSelectWorldTemplateScreen.this.currentLink = string22;
            }
        }
    }

    class WorldTemplateObjectSelectionList
    extends RealmsObjectSelectionList<WorldTemplateObjectSelectionListEntry> {
        public WorldTemplateObjectSelectionList() {
            this(Collections.emptyList());
        }

        public WorldTemplateObjectSelectionList(Iterable<WorldTemplate> templates) {
            super(RealmsSelectWorldTemplateScreen.this.width, RealmsSelectWorldTemplateScreen.this.height, RealmsSelectWorldTemplateScreen.this.displayWarning ? RealmsSelectWorldTemplateScreen.row(1) : 32, RealmsSelectWorldTemplateScreen.this.height - 40, 46);
            templates.forEach(this::addEntry);
        }

        public void addEntry(WorldTemplate template) {
            this.addEntry(new WorldTemplateObjectSelectionListEntry(template));
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0 && mouseY >= (double)this.top && mouseY <= (double)this.bottom) {
                int n = this.width / 2 - 150;
                if (RealmsSelectWorldTemplateScreen.this.currentLink != null) {
                    Util.getOperatingSystem().open(RealmsSelectWorldTemplateScreen.this.currentLink);
                }
                _snowman = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
                _snowman = _snowman / this.itemHeight;
                if (mouseX >= (double)n && mouseX < (double)this.getScrollbarPositionX() && _snowman >= 0 && _snowman >= 0 && _snowman < this.getItemCount()) {
                    this.setSelected(_snowman);
                    this.itemClicked(_snowman, _snowman, mouseX, mouseY, this.width);
                    if (_snowman >= RealmsSelectWorldTemplateScreen.this.templateList.getItemCount()) {
                        return super.mouseClicked(mouseX, mouseY, button);
                    }
                    RealmsSelectWorldTemplateScreen.this.clicks = RealmsSelectWorldTemplateScreen.this.clicks + 7;
                    if (RealmsSelectWorldTemplateScreen.this.clicks >= 10) {
                        RealmsSelectWorldTemplateScreen.this.selectTemplate();
                    }
                    return true;
                }
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void setSelected(int index) {
            this.setSelectedItem(index);
            if (index != -1) {
                WorldTemplate worldTemplate = RealmsSelectWorldTemplateScreen.this.templateList.getItem(index);
                String _snowman2 = I18n.translate("narrator.select.list.position", index + 1, RealmsSelectWorldTemplateScreen.this.templateList.getItemCount());
                String _snowman3 = I18n.translate("mco.template.select.narrate.version", worldTemplate.version);
                String _snowman4 = I18n.translate("mco.template.select.narrate.authors", worldTemplate.author);
                String _snowman5 = Realms.joinNarrations(Arrays.asList(worldTemplate.name, _snowman4, worldTemplate.recommendedPlayers, _snowman3, _snowman2));
                Realms.narrateNow(I18n.translate("narrator.select", _snowman5));
            }
        }

        @Override
        public void setSelected(@Nullable WorldTemplateObjectSelectionListEntry worldTemplateObjectSelectionListEntry) {
            super.setSelected(worldTemplateObjectSelectionListEntry);
            RealmsSelectWorldTemplateScreen.this.selectedTemplate = this.children().indexOf(worldTemplateObjectSelectionListEntry);
            RealmsSelectWorldTemplateScreen.this.updateButtonStates();
        }

        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 46;
        }

        @Override
        public int getRowWidth() {
            return 300;
        }

        @Override
        public void renderBackground(MatrixStack matrices) {
            RealmsSelectWorldTemplateScreen.this.renderBackground(matrices);
        }

        @Override
        public boolean isFocused() {
            return RealmsSelectWorldTemplateScreen.this.getFocused() == this;
        }

        public boolean isEmpty() {
            return this.getItemCount() == 0;
        }

        public WorldTemplate getItem(int index) {
            return ((WorldTemplateObjectSelectionListEntry)this.children().get(index)).mTemplate;
        }

        public List<WorldTemplate> getValues() {
            return this.children().stream().map(worldTemplateObjectSelectionListEntry -> ((WorldTemplateObjectSelectionListEntry)worldTemplateObjectSelectionListEntry).mTemplate).collect(Collectors.toList());
        }
    }
}

