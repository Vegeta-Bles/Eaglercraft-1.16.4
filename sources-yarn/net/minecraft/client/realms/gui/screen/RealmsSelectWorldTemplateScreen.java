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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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

   public void setWarning(Text... args) {
      this.warning = args;
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
         new ButtonWidget(this.width / 2 - 206, this.height - 32, 100, 20, new TranslatableText("mco.template.button.trailer"), arg -> this.onTrailer())
      );
      this.selectButton = this.addButton(
         new ButtonWidget(this.width / 2 - 100, this.height - 32, 100, 20, new TranslatableText("mco.template.button.select"), arg -> this.selectTemplate())
      );
      Text lv = this.worldType == RealmsServer.WorldType.MINIGAME ? ScreenTexts.CANCEL : ScreenTexts.BACK;
      ButtonWidget lv2 = new ButtonWidget(this.width / 2 + 6, this.height - 32, 100, 20, lv, arg -> this.backButtonClicked());
      this.addButton(lv2);
      this.publisherButton = this.addButton(
         new ButtonWidget(this.width / 2 + 112, this.height - 32, 100, 20, new TranslatableText("mco.template.button.publisher"), arg -> this.onPublish())
      );
      this.selectButton.active = false;
      this.trailerButton.visible = false;
      this.publisherButton.visible = false;
      this.addChild(this.templateList);
      this.focusOn(this.templateList);
      Stream<Text> stream = Stream.of(this.title);
      if (this.warning != null) {
         stream = Stream.concat(Stream.of(this.warning), stream);
      }

      Realms.narrateNow(stream.filter(Objects::nonNull).map(Text::getString).collect(Collectors.toList()));
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
         WorldTemplate lv = this.method_21434();
         if (!"".equals(lv.trailer)) {
            Util.getOperatingSystem().open(lv.trailer);
         }
      }
   }

   private void onPublish() {
      if (this.method_25247()) {
         WorldTemplate lv = this.method_21434();
         if (!"".equals(lv.link)) {
            Util.getOperatingSystem().open(lv.link);
         }
      }
   }

   private void setPagination(final WorldTemplatePaginatedList arg) {
      (new Thread("realms-template-fetcher") {
            @Override
            public void run() {
               WorldTemplatePaginatedList lv = arg;
               RealmsClient lv2 = RealmsClient.createRealmsClient();

               while (lv != null) {
                  Either<WorldTemplatePaginatedList, String> either = RealmsSelectWorldTemplateScreen.this.method_21416(lv, lv2);
                  lv = RealmsSelectWorldTemplateScreen.this.client
                     .submit(
                        () -> {
                           if (either.right().isPresent()) {
                              RealmsSelectWorldTemplateScreen.LOGGER.error("Couldn't fetch templates: {}", either.right().get());
                              if (RealmsSelectWorldTemplateScreen.this.templateList.isEmpty()) {
                                 RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(
                                    I18n.translate("mco.template.select.failure")
                                 );
                              }

                              return null;
                           } else {
                              WorldTemplatePaginatedList lvx = (WorldTemplatePaginatedList)either.left().get();

                              for (WorldTemplate lv2x : lvx.templates) {
                                 RealmsSelectWorldTemplateScreen.this.templateList.addEntry(lv2x);
                              }

                              if (lvx.templates.isEmpty()) {
                                 if (RealmsSelectWorldTemplateScreen.this.templateList.isEmpty()) {
                                    String string = I18n.translate("mco.template.select.none", "%link");
                                    TextRenderingUtils.LineSegment lv3 = TextRenderingUtils.LineSegment.link(
                                       I18n.translate("mco.template.select.none.linkTitle"), "https://aka.ms/MinecraftRealmsContentCreator"
                                    );
                                    RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(string, lv3);
                                 }

                                 return null;
                              } else {
                                 return lvx;
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

   private Either<WorldTemplatePaginatedList, String> method_21416(WorldTemplatePaginatedList arg, RealmsClient arg2) {
      try {
         return Either.left(arg2.fetchWorldTemplates(arg.page + 1, arg.size, this.worldType));
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
         Text[] lvs = this.warning;

         for (int k = 0; k < lvs.length; k++) {
            int l = this.textRenderer.getWidth(lvs[k]);
            int m = this.width / 2 - l / 2;
            int n = row(-1 + k);
            if (mouseX >= m && mouseX <= m + l && mouseY >= n && mouseY <= n + 9) {
               this.hoverWarning = true;
            }
         }

         for (int o = 0; o < lvs.length; o++) {
            Text lv = lvs[o];
            int p = 10526880;
            if (this.warningURL != null) {
               if (this.hoverWarning) {
                  p = 7107012;
                  lv = lv.shallowCopy().formatted(Formatting.STRIKETHROUGH);
               } else {
                  p = 3368635;
               }
            }

            drawCenteredText(matrices, this.textRenderer, lv, this.width / 2, row(-1 + o), p);
         }
      }

      super.render(matrices, mouseX, mouseY, delta);
      this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
   }

   private void method_21414(MatrixStack arg, int i, int j, List<TextRenderingUtils.Line> list) {
      for (int k = 0; k < list.size(); k++) {
         TextRenderingUtils.Line lv = list.get(k);
         int l = row(4 + k);
         int m = lv.segments.stream().mapToInt(argx -> this.textRenderer.getWidth(argx.renderedText())).sum();
         int n = this.width / 2 - m / 2;

         for (TextRenderingUtils.LineSegment lv2 : lv.segments) {
            int o = lv2.isLink() ? 3368635 : 16777215;
            int p = this.textRenderer.drawWithShadow(arg, lv2.renderedText(), (float)n, (float)l, o);
            if (lv2.isLink() && i > n && i < p && j > l - 3 && j < l + 8) {
               this.toolTip = new LiteralText(lv2.getLinkUrl());
               this.currentLink = lv2.getLinkUrl();
            }

            n = p;
         }
      }
   }

   protected void renderMousehoverTooltip(MatrixStack arg, @Nullable Text arg2, int i, int j) {
      if (arg2 != null) {
         int k = i + 12;
         int l = j - 12;
         int m = this.textRenderer.getWidth(arg2);
         this.fillGradient(arg, k - 3, l - 3, k + m + 3, l + 8 + 3, -1073741824, -1073741824);
         this.textRenderer.drawWithShadow(arg, arg2, (float)k, (float)l, 16777215);
      }
   }

   @Environment(EnvType.CLIENT)
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
            int j = this.width / 2 - 150;
            if (RealmsSelectWorldTemplateScreen.this.currentLink != null) {
               Util.getOperatingSystem().open(RealmsSelectWorldTemplateScreen.this.currentLink);
            }

            int k = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int l = k / this.itemHeight;
            if (mouseX >= (double)j && mouseX < (double)this.getScrollbarPositionX() && l >= 0 && k >= 0 && l < this.getItemCount()) {
               this.setSelected(l);
               this.itemClicked(k, l, mouseX, mouseY, this.width);
               if (l >= RealmsSelectWorldTemplateScreen.this.templateList.getItemCount()) {
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
            WorldTemplate lv = RealmsSelectWorldTemplateScreen.this.templateList.getItem(index);
            String string = I18n.translate("narrator.select.list.position", index + 1, RealmsSelectWorldTemplateScreen.this.templateList.getItemCount());
            String string2 = I18n.translate("mco.template.select.narrate.version", lv.version);
            String string3 = I18n.translate("mco.template.select.narrate.authors", lv.author);
            String string4 = Realms.joinNarrations(Arrays.asList(lv.name, string3, lv.recommendedPlayers, string2, string));
            Realms.narrateNow(I18n.translate("narrator.select", string4));
         }
      }

      public void setSelected(@Nullable RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionListEntry arg) {
         super.setSelected(arg);
         RealmsSelectWorldTemplateScreen.this.selectedTemplate = this.children().indexOf(arg);
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
         return this.children().stream().map(arg -> arg.mTemplate).collect(Collectors.toList());
      }
   }

   @Environment(EnvType.CLIENT)
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

      private void renderWorldTemplateItem(MatrixStack arg, WorldTemplate arg2, int i, int j, int k, int l) {
         int m = i + 45 + 20;
         RealmsSelectWorldTemplateScreen.this.textRenderer.draw(arg, arg2.name, (float)m, (float)(j + 2), 16777215);
         RealmsSelectWorldTemplateScreen.this.textRenderer.draw(arg, arg2.author, (float)m, (float)(j + 15), 7105644);
         RealmsSelectWorldTemplateScreen.this.textRenderer
            .draw(arg, arg2.version, (float)(m + 227 - RealmsSelectWorldTemplateScreen.this.textRenderer.getWidth(arg2.version)), (float)(j + 1), 7105644);
         if (!"".equals(arg2.link) || !"".equals(arg2.trailer) || !"".equals(arg2.recommendedPlayers)) {
            this.drawIcons(arg, m - 1, j + 25, k, l, arg2.link, arg2.trailer, arg2.recommendedPlayers);
         }

         this.drawImage(arg, i, j + 1, k, l, arg2);
      }

      private void drawImage(MatrixStack arg, int y, int xm, int ym, int l, WorldTemplate arg2) {
         RealmsTextureManager.bindWorldTemplate(arg2.id, arg2.image);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(arg, y + 1, xm + 1, 0.0F, 0.0F, 38, 38, 38, 38);
         RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(RealmsSelectWorldTemplateScreen.SLOT_FRAME);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(arg, y, xm, 0.0F, 0.0F, 40, 40, 40, 40);
      }

      private void drawIcons(MatrixStack arg, int i, int j, int k, int l, String string, String string2, String string3) {
         if (!"".equals(string3)) {
            RealmsSelectWorldTemplateScreen.this.textRenderer.draw(arg, string3, (float)i, (float)(j + 4), 5000268);
         }

         int m = "".equals(string3) ? 0 : RealmsSelectWorldTemplateScreen.this.textRenderer.getWidth(string3) + 2;
         boolean bl = false;
         boolean bl2 = false;
         boolean bl3 = "".equals(string);
         if (k >= i + m && k <= i + m + 32 && l >= j && l <= j + 15 && l < RealmsSelectWorldTemplateScreen.this.height - 15 && l > 32) {
            if (k <= i + 15 + m && k > m) {
               if (bl3) {
                  bl2 = true;
               } else {
                  bl = true;
               }
            } else if (!bl3) {
               bl2 = true;
            }
         }

         if (!bl3) {
            RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(RealmsSelectWorldTemplateScreen.LINK_ICONS);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            float f = bl ? 15.0F : 0.0F;
            DrawableHelper.drawTexture(arg, i + m, j, f, 0.0F, 15, 15, 30, 15);
            RenderSystem.popMatrix();
         }

         if (!"".equals(string2)) {
            RealmsSelectWorldTemplateScreen.this.client.getTextureManager().bindTexture(RealmsSelectWorldTemplateScreen.TRAILER_ICONS);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            int n = i + m + (bl3 ? 0 : 17);
            float g = bl2 ? 15.0F : 0.0F;
            DrawableHelper.drawTexture(arg, n, j, g, 0.0F, 15, 15, 30, 15);
            RenderSystem.popMatrix();
         }

         if (bl) {
            RealmsSelectWorldTemplateScreen.this.toolTip = RealmsSelectWorldTemplateScreen.field_26512;
            RealmsSelectWorldTemplateScreen.this.currentLink = string;
         } else if (bl2 && !"".equals(string2)) {
            RealmsSelectWorldTemplateScreen.this.toolTip = RealmsSelectWorldTemplateScreen.field_26513;
            RealmsSelectWorldTemplateScreen.this.currentLink = string2;
         }
      }
   }
}
