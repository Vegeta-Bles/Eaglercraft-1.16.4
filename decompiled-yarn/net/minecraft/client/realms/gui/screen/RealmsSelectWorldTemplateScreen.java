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

public class RealmsSelectWorldTemplateScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier LINK_ICONS = new Identifier("realms", "textures/gui/realms/link_icons.png");
   private static final Identifier TRAILER_ICONS = new Identifier("realms", "textures/gui/realms/trailer_icons.png");
   private static final Identifier SLOT_FRAME = new Identifier("realms", "textures/gui/realms/slot_frame.png");
   private static final Text field_26512 = new TranslatableText("mco.template.info.tooltip");
   private static final Text field_26513 = new TranslatableText("mco.template.trailer.tooltip");
   private final RealmsScreenWithCallback parent;
   private RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList templateList;
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
         this.templateList = new RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList();
         this.setPagination(new WorldTemplatePaginatedList(10));
      } else {
         this.templateList = new RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList(Lists.newArrayList(list.templates));
         this.setPagination(list);
      }

      this.title = new TranslatableText("mco.template.title");
   }

   public void setTitle(Text title) {
      this.title = title;
   }

   public void setWarning(Text... _snowman) {
      this.warning = _snowman;
      this.displayWarning = true;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.hoverWarning && this.warningURL != null) {
         Util.getOperatingSystem().open("https://www.minecraft.net/realms/adventure-maps-in-1-9");
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   public void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.templateList = new RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList(this.templateList.getValues());
      this.trailerButton = this.addButton(
         new ButtonWidget(this.width / 2 - 206, this.height - 32, 100, 20, new TranslatableText("mco.template.button.trailer"), _snowman -> this.onTrailer())
      );
      this.selectButton = this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height - 32, 100, 20, new TranslatableText("mco.template.button.select"), _snowman -> this.selectTemplate())
      );
      Text _snowman = this.worldType == RealmsServer.WorldType.MINIGAME ? ScreenTexts.CANCEL : ScreenTexts.BACK;
      ButtonWidget _snowmanx = new ButtonWidget(this.width / 2 + 6, this.height - 32, 100, 20, _snowman, _snowmanxx -> this.backButtonClicked());
      this.addButton(_snowmanx);
      this.publisherButton = this.addButton(
         new ButtonWidget(this.width / 2 + 112, this.height - 32, 100, 20, new TranslatableText("mco.template.button.publisher"), _snowmanxx -> this.onPublish())
      );
      this.selectButton.active = false;
      this.trailerButton.visible = false;
      this.publisherButton.visible = false;
      this.addChild(this.templateList);
      this.focusOn(this.templateList);
      Stream<Text> _snowmanxx = Stream.of(this.title);
      if (this.warning != null) {
         _snowmanxx = Stream.concat(Stream.of(this.warning), _snowmanxx);
      }

      Realms.narrateNow(_snowmanxx.filter(Objects::nonNull).map(Text::getString).collect(Collectors.toList()));
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
      this.clicks--;
      if (this.clicks < 0) {
         this.clicks = 0;
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.backButtonClicked();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
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
         WorldTemplate _snowman = this.method_21434();
         if (!"".equals(_snowman.trailer)) {
            Util.getOperatingSystem().open(_snowman.trailer);
         }
      }
   }

   private void onPublish() {
      if (this.method_25247()) {
         WorldTemplate _snowman = this.method_21434();
         if (!"".equals(_snowman.link)) {
            Util.getOperatingSystem().open(_snowman.link);
         }
      }
   }

   private void setPagination(WorldTemplatePaginatedList _snowman) {
      (new Thread("realms-template-fetcher") {
            @Override
            public void run() {
               WorldTemplatePaginatedList _snowman = _snowman;
               RealmsClient _snowmanx = RealmsClient.createRealmsClient();

               while (_snowman != null) {
                  Either<WorldTemplatePaginatedList, String> _snowmanxx = RealmsSelectWorldTemplateScreen.this.method_21416(_snowman, _snowmanx);
                  _snowman = RealmsSelectWorldTemplateScreen.this.client
                     .submit(
                        () -> {
                           if (_snowman.right().isPresent()) {
                              RealmsSelectWorldTemplateScreen.LOGGER.error("Couldn't fetch templates: {}", _snowman.right().get());
                              if (RealmsSelectWorldTemplateScreen.this.templateList.isEmpty()) {
                                 RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(
                                    I18n.translate("mco.template.select.failure")
                                 );
                              }

                              return null;
                           } else {
                              WorldTemplatePaginatedList _snowmanx = (WorldTemplatePaginatedList)_snowman.left().get();

                              for (WorldTemplate _snowmanxx : _snowmanx.templates) {
                                 RealmsSelectWorldTemplateScreen.this.templateList.addEntry(_snowmanxx);
                              }

                              if (_snowmanx.templates.isEmpty()) {
                                 if (RealmsSelectWorldTemplateScreen.this.templateList.isEmpty()) {
                                    String _snowmanxx = I18n.translate("mco.template.select.none", "%link");
                                    TextRenderingUtils.LineSegment _snowmanxxxx = TextRenderingUtils.LineSegment.link(
                                       I18n.translate("mco.template.select.none.linkTitle"), "https://aka.ms/MinecraftRealmsContentCreator"
                                    );
                                    RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(_snowmanxx, _snowmanxxxx);
                                 }

                                 return null;
                              } else {
                                 return _snowmanx;
                              }
                           }
                        }
                     )
                     .join();
               }
            }
         })
         .start();
   }

   private Either<WorldTemplatePaginatedList, String> method_21416(WorldTemplatePaginatedList _snowman, RealmsClient _snowman) {
      try {
         return Either.left(_snowman.fetchWorldTemplates(_snowman.page + 1, _snowman.size, this.worldType));
      } catch (RealmsServiceException var4) {
         return Either.right(var4.getMessage());
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

      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 13, 16777215);
      if (this.displayWarning) {
         Text[] _snowman = this.warning;

         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            int _snowmanxx = this.textRenderer.getWidth(_snowman[_snowmanx]);
            int _snowmanxxx = this.width / 2 - _snowmanxx / 2;
            int _snowmanxxxx = row(-1 + _snowmanx);
            if (mouseX >= _snowmanxxx && mouseX <= _snowmanxxx + _snowmanxx && mouseY >= _snowmanxxxx && mouseY <= _snowmanxxxx + 9) {
               this.hoverWarning = true;
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < _snowman.length; _snowmanxx++) {
            Text _snowmanxxx = _snowman[_snowmanxx];
            int _snowmanxxxx = 10526880;
            if (this.warningURL != null) {
               if (this.hoverWarning) {
                  _snowmanxxxx = 7107012;
                  _snowmanxxx = _snowmanxxx.shallowCopy().formatted(Formatting.STRIKETHROUGH);
               } else {
                  _snowmanxxxx = 3368635;
               }
            }

            drawCenteredText(matrices, this.textRenderer, _snowmanxxx, this.width / 2, row(-1 + _snowmanxx), _snowmanxxxx);
         }
      }

      super.render(matrices, mouseX, mouseY, delta);
      this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
   }

   private void method_21414(MatrixStack _snowman, int _snowman, int _snowman, List<TextRenderingUtils.Line> _snowman) {
      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         TextRenderingUtils.Line _snowmanxxxxx = _snowman.get(_snowmanxxxx);
         int _snowmanxxxxxx = row(4 + _snowmanxxxx);
         int _snowmanxxxxxxx = _snowmanxxxxx.segments.stream().mapToInt(_snowmanxxxxxxxx -> this.textRenderer.getWidth(_snowmanxxxxxxxx.renderedText())).sum();
         int _snowmanxxxxxxxx = this.width / 2 - _snowmanxxxxxxx / 2;

         for (TextRenderingUtils.LineSegment _snowmanxxxxxxxxx : _snowmanxxxxx.segments) {
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.isLink() ? 3368635 : 16777215;
            int _snowmanxxxxxxxxxxx = this.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxxxxx.renderedText(), (float)_snowmanxxxxxxxx, (float)_snowmanxxxxxx, _snowmanxxxxxxxxxx);
            if (_snowmanxxxxxxxxx.isLink() && _snowman > _snowmanxxxxxxxx && _snowman < _snowmanxxxxxxxxxxx && _snowman > _snowmanxxxxxx - 3 && _snowman < _snowmanxxxxxx + 8) {
               this.toolTip = new LiteralText(_snowmanxxxxxxxxx.getLinkUrl());
               this.currentLink = _snowmanxxxxxxxxx.getLinkUrl();
            }

            _snowmanxxxxxxxx = _snowmanxxxxxxxxxxx;
         }
      }
   }

   protected void renderMousehoverTooltip(MatrixStack _snowman, @Nullable Text _snowman, int _snowman, int _snowman) {
      if (_snowman != null) {
         int _snowmanxxxx = _snowman + 12;
         int _snowmanxxxxx = _snowman - 12;
         int _snowmanxxxxxx = this.textRenderer.getWidth(_snowman);
         this.fillGradient(_snowman, _snowmanxxxx - 3, _snowmanxxxxx - 3, _snowmanxxxx + _snowmanxxxxxx + 3, _snowmanxxxxx + 8 + 3, -1073741824, -1073741824);
         this.textRenderer.drawWithShadow(_snowman, _snowman, (float)_snowmanxxxx, (float)_snowmanxxxxx, 16777215);
      }
   }

   class WorldTemplateObjectSelectionList extends RealmsObjectSelectionList<RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionListEntry> {
      public WorldTemplateObjectSelectionList() {
         this(Collections.emptyList());
      }

      public WorldTemplateObjectSelectionList(Iterable<WorldTemplate> templates) {
         super(
            RealmsSelectWorldTemplateScreen.this.width,
            RealmsSelectWorldTemplateScreen.this.height,
            RealmsSelectWorldTemplateScreen.this.displayWarning ? RealmsSelectWorldTemplateScreen.row(1) : 32,
            RealmsSelectWorldTemplateScreen.this.height - 40,
            46
         );
         templates.forEach(this::addEntry);
      }

      public void addEntry(WorldTemplate template) {
         this.addEntry(RealmsSelectWorldTemplateScreen.this.new WorldTemplateObjectSelectionListEntry(template));
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (button == 0 && mouseY >= (double)this.top && mouseY <= (double)this.bottom) {
            int _snowman = this.width / 2 - 150;
            if (RealmsSelectWorldTemplateScreen.this.currentLink != null) {
               Util.getOperatingSystem().open(RealmsSelectWorldTemplateScreen.this.currentLink);
            }

            int _snowmanx = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int _snowmanxx = _snowmanx / this.itemHeight;
            if (mouseX >= (double)_snowman && mouseX < (double)this.getScrollbarPositionX() && _snowmanxx >= 0 && _snowmanx >= 0 && _snowmanxx < this.getItemCount()) {
               this.setSelected(_snowmanxx);
               this.itemClicked(_snowmanx, _snowmanxx, mouseX, mouseY, this.width);
               if (_snowmanxx >= RealmsSelectWorldTemplateScreen.this.templateList.getItemCount()) {
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
            WorldTemplate _snowman = RealmsSelectWorldTemplateScreen.this.templateList.getItem(index);
            String _snowmanx = I18n.translate("narrator.select.list.position", index + 1, RealmsSelectWorldTemplateScreen.this.templateList.getItemCount());
            String _snowmanxx = I18n.translate("mco.template.select.narrate.version", _snowman.version);
            String _snowmanxxx = I18n.translate("mco.template.select.narrate.authors", _snowman.author);
            String _snowmanxxxx = Realms.joinNarrations(Arrays.asList(_snowman.name, _snowmanxxx, _snowman.recommendedPlayers, _snowmanxx, _snowmanx));
            Realms.narrateNow(I18n.translate("narrator.select", _snowmanxxxx));
         }
      }

      public void setSelected(@Nullable RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionListEntry _snowman) {
         super.setSelected(_snowman);
         RealmsSelectWorldTemplateScreen.this.selectedTemplate = this.children().indexOf(_snowman);
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
         return this.children().get(index).mTemplate;
      }

      public List<WorldTemplate> getValues() {
         return this.children().stream().map(_snowman -> _snowman.mTemplate).collect(Collectors.toList());
      }
   }

   class WorldTemplateObjectSelectionListEntry
      extends AlwaysSelectedEntryListWidget.Entry<RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionListEntry> {
      private final WorldTemplate mTemplate;

      public WorldTemplateObjectSelectionListEntry(WorldTemplate template) {
         this.mTemplate = template;
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.renderWorldTemplateItem(matrices, this.mTemplate, x, y, mouseX, mouseY);
      }

      private void renderWorldTemplateItem(MatrixStack _snowman, WorldTemplate _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         int _snowmanxxxxxx = _snowman + 45 + 20;
         RealmsSelectWorldTemplateScreen.this.textRenderer.draw(_snowman, _snowman.name, (float)_snowmanxxxxxx, (float)(_snowman + 2), 16777215);
         RealmsSelectWorldTemplateScreen.this.textRenderer.draw(_snowman, _snowman.author, (float)_snowmanxxxxxx, (float)(_snowman + 15), 7105644);
         RealmsSelectWorldTemplateScreen.this.textRenderer
            .draw(_snowman, _snowman.version, (float)(_snowmanxxxxxx + 227 - RealmsSelectWorldTemplateScreen.this.textRenderer.getWidth(_snowman.version)), (float)(_snowman + 1), 7105644);
         if (!"".equals(_snowman.link) || !"".equals(_snowman.trailer) || !"".equals(_snowman.recommendedPlayers)) {
            this.drawIcons(_snowman, _snowmanxxxxxx - 1, _snowman + 25, _snowman, _snowman, _snowman.link, _snowman.trailer, _snowman.recommendedPlayers);
         }

         this.drawImage(_snowman, _snowman, _snowman + 1, _snowman, _snowman, _snowman);
      }

      private void drawImage(MatrixStack _snowman, int y, int xm, int ym, int _snowman, WorldTemplate _snowman) {
         RealmsTextureManager.bindWorldTemplate(_snowman.id, _snowman.image);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(_snowman, y + 1, xm + 1, 0.0F, 0.0F, 38, 38, 38, 38);
         RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(RealmsSelectWorldTemplateScreen.SLOT_FRAME);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(_snowman, y, xm, 0.0F, 0.0F, 40, 40, 40, 40);
      }

      private void drawIcons(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, String _snowman, String _snowman, String _snowman) {
         if (!"".equals(_snowman)) {
            RealmsSelectWorldTemplateScreen.this.textRenderer.draw(_snowman, _snowman, (float)_snowman, (float)(_snowman + 4), 5000268);
         }

         int _snowmanxxxxxxxx = "".equals(_snowman) ? 0 : RealmsSelectWorldTemplateScreen.this.textRenderer.getWidth(_snowman) + 2;
         boolean _snowmanxxxxxxxxx = false;
         boolean _snowmanxxxxxxxxxx = false;
         boolean _snowmanxxxxxxxxxxx = "".equals(_snowman);
         if (_snowman >= _snowman + _snowmanxxxxxxxx && _snowman <= _snowman + _snowmanxxxxxxxx + 32 && _snowman >= _snowman && _snowman <= _snowman + 15 && _snowman < RealmsSelectWorldTemplateScreen.this.height - 15 && _snowman > 32) {
            if (_snowman <= _snowman + 15 + _snowmanxxxxxxxx && _snowman > _snowmanxxxxxxxx) {
               if (_snowmanxxxxxxxxxxx) {
                  _snowmanxxxxxxxxxx = true;
               } else {
                  _snowmanxxxxxxxxx = true;
               }
            } else if (!_snowmanxxxxxxxxxxx) {
               _snowmanxxxxxxxxxx = true;
            }
         }

         if (!_snowmanxxxxxxxxxxx) {
            RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(RealmsSelectWorldTemplateScreen.LINK_ICONS);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx ? 15.0F : 0.0F;
            DrawableHelper.drawTexture(_snowman, _snowman + _snowmanxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxx, 0.0F, 15, 15, 30, 15);
            RenderSystem.popMatrix();
         }

         if (!"".equals(_snowman)) {
            RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(RealmsSelectWorldTemplateScreen.TRAILER_ICONS);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            int _snowmanxxxxxxxxxxxx = _snowman + _snowmanxxxxxxxx + (_snowmanxxxxxxxxxxx ? 0 : 17);
            float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx ? 15.0F : 0.0F;
            DrawableHelper.drawTexture(_snowman, _snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxx, 0.0F, 15, 15, 30, 15);
            RenderSystem.popMatrix();
         }

         if (_snowmanxxxxxxxxx) {
            RealmsSelectWorldTemplateScreen.this.toolTip = RealmsSelectWorldTemplateScreen.field_26512;
            RealmsSelectWorldTemplateScreen.this.currentLink = _snowman;
         } else if (_snowmanxxxxxxxxxx && !"".equals(_snowman)) {
            RealmsSelectWorldTemplateScreen.this.toolTip = RealmsSelectWorldTemplateScreen.field_26513;
            RealmsSelectWorldTemplateScreen.this.currentLink = _snowman;
         }
      }
   }
}
