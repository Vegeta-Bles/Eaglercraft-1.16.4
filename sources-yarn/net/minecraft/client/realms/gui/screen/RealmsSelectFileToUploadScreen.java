package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.Lists;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class RealmsSelectFileToUploadScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Text worldLang = new TranslatableText("selectWorld.world");
   private static final Text conversionLang = new TranslatableText("selectWorld.conversion");
   private static final Text field_26507 = new TranslatableText("mco.upload.hardcore").formatted(Formatting.DARK_RED);
   private static final Text field_26508 = new TranslatableText("selectWorld.cheats");
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
   private final RealmsResetWorldScreen parent;
   private final long worldId;
   private final int slotId;
   private ButtonWidget uploadButton;
   private List<LevelSummary> levelList = Lists.newArrayList();
   private int selectedWorld = -1;
   private RealmsSelectFileToUploadScreen.WorldSelectionList worldSelectionList;
   private RealmsLabel titleLabel;
   private RealmsLabel subtitleLabel;
   private RealmsLabel field_20063;
   private final Runnable field_22717;

   public RealmsSelectFileToUploadScreen(long worldId, int slotId, RealmsResetWorldScreen parent, Runnable runnable) {
      this.parent = parent;
      this.worldId = worldId;
      this.slotId = slotId;
      this.field_22717 = runnable;
   }

   private void loadLevelList() throws Exception {
      this.levelList = this.client.getLevelStorage().getLevelList().stream().sorted((arg, arg2) -> {
         if (arg.getLastPlayed() < arg2.getLastPlayed()) {
            return 1;
         } else {
            return arg.getLastPlayed() > arg2.getLastPlayed() ? -1 : arg.getName().compareTo(arg2.getName());
         }
      }).collect(Collectors.toList());

      for (LevelSummary lv : this.levelList) {
         this.worldSelectionList.addEntry(lv);
      }
   }

   @Override
   public void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.worldSelectionList = new RealmsSelectFileToUploadScreen.WorldSelectionList();

      try {
         this.loadLevelList();
      } catch (Exception var2) {
         LOGGER.error("Couldn't load level list", var2);
         this.client.openScreen(new RealmsGenericErrorScreen(new LiteralText("Unable to load worlds"), Text.of(var2.getMessage()), this.parent));
         return;
      }

      this.addChild(this.worldSelectionList);
      this.uploadButton = this.addButton(
         new ButtonWidget(this.width / 2 - 154, this.height - 32, 153, 20, new TranslatableText("mco.upload.button.name"), arg -> this.upload())
      );
      this.uploadButton.active = this.selectedWorld >= 0 && this.selectedWorld < this.levelList.size();
      this.addButton(new ButtonWidget(this.width / 2 + 6, this.height - 32, 153, 20, ScreenTexts.BACK, arg -> this.client.openScreen(this.parent)));
      this.titleLabel = this.addChild(new RealmsLabel(new TranslatableText("mco.upload.select.world.title"), this.width / 2, 13, 16777215));
      this.subtitleLabel = this.addChild(new RealmsLabel(new TranslatableText("mco.upload.select.world.subtitle"), this.width / 2, row(-1), 10526880));
      if (this.levelList.isEmpty()) {
         this.field_20063 = this.addChild(new RealmsLabel(new TranslatableText("mco.upload.select.world.none"), this.width / 2, this.height / 2 - 20, 16777215));
      } else {
         this.field_20063 = null;
      }

      this.narrateLabels();
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   private void upload() {
      if (this.selectedWorld != -1 && !this.levelList.get(this.selectedWorld).isHardcore()) {
         LevelSummary lv = this.levelList.get(this.selectedWorld);
         this.client.openScreen(new RealmsUploadScreen(this.worldId, this.slotId, this.parent, lv, this.field_22717));
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      this.worldSelectionList.render(matrices, mouseX, mouseY, delta);
      this.titleLabel.render(this, matrices);
      this.subtitleLabel.render(this, matrices);
      if (this.field_20063 != null) {
         this.field_20063.render(this, matrices);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.client.openScreen(this.parent);
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private static Text method_21400(LevelSummary arg) {
      return arg.getGameMode().getTranslatableName();
   }

   private static String method_21404(LevelSummary arg) {
      return DATE_FORMAT.format(new Date(arg.getLastPlayed()));
   }

   @Environment(EnvType.CLIENT)
   class WorldListEntry extends AlwaysSelectedEntryListWidget.Entry<RealmsSelectFileToUploadScreen.WorldListEntry> {
      private final LevelSummary field_22718;
      private final String field_26509;
      private final String field_26510;
      private final Text field_26511;

      public WorldListEntry(LevelSummary arg2) {
         this.field_22718 = arg2;
         this.field_26509 = arg2.getDisplayName();
         this.field_26510 = arg2.getName() + " (" + RealmsSelectFileToUploadScreen.method_21404(arg2) + ")";
         if (arg2.requiresConversion()) {
            this.field_26511 = RealmsSelectFileToUploadScreen.conversionLang;
         } else {
            Text lv;
            if (arg2.isHardcore()) {
               lv = RealmsSelectFileToUploadScreen.field_26507;
            } else {
               lv = RealmsSelectFileToUploadScreen.method_21400(arg2);
            }

            if (arg2.hasCheats()) {
               lv = lv.shallowCopy().append(", ").append(RealmsSelectFileToUploadScreen.field_26508);
            }

            this.field_26511 = lv;
         }
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.renderItem(matrices, this.field_22718, index, x, y);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         RealmsSelectFileToUploadScreen.this.worldSelectionList.setSelected(RealmsSelectFileToUploadScreen.this.levelList.indexOf(this.field_22718));
         return true;
      }

      protected void renderItem(MatrixStack arg, LevelSummary arg2, int i, int j, int k) {
         String string;
         if (this.field_26509.isEmpty()) {
            string = RealmsSelectFileToUploadScreen.worldLang + " " + (i + 1);
         } else {
            string = this.field_26509;
         }

         RealmsSelectFileToUploadScreen.this.textRenderer.draw(arg, string, (float)(j + 2), (float)(k + 1), 16777215);
         RealmsSelectFileToUploadScreen.this.textRenderer.draw(arg, this.field_26510, (float)(j + 2), (float)(k + 12), 8421504);
         RealmsSelectFileToUploadScreen.this.textRenderer.draw(arg, this.field_26511, (float)(j + 2), (float)(k + 12 + 10), 8421504);
      }
   }

   @Environment(EnvType.CLIENT)
   class WorldSelectionList extends RealmsObjectSelectionList<RealmsSelectFileToUploadScreen.WorldListEntry> {
      public WorldSelectionList() {
         super(
            RealmsSelectFileToUploadScreen.this.width,
            RealmsSelectFileToUploadScreen.this.height,
            RealmsSelectFileToUploadScreen.row(0),
            RealmsSelectFileToUploadScreen.this.height - 40,
            36
         );
      }

      public void addEntry(LevelSummary arg) {
         this.addEntry(RealmsSelectFileToUploadScreen.this.new WorldListEntry(arg));
      }

      @Override
      public int getMaxPosition() {
         return RealmsSelectFileToUploadScreen.this.levelList.size() * 36;
      }

      @Override
      public boolean isFocused() {
         return RealmsSelectFileToUploadScreen.this.getFocused() == this;
      }

      @Override
      public void renderBackground(MatrixStack matrices) {
         RealmsSelectFileToUploadScreen.this.renderBackground(matrices);
      }

      @Override
      public void setSelected(int index) {
         this.setSelectedItem(index);
         if (index != -1) {
            LevelSummary lv = RealmsSelectFileToUploadScreen.this.levelList.get(index);
            String string = I18n.translate("narrator.select.list.position", index + 1, RealmsSelectFileToUploadScreen.this.levelList.size());
            String string2 = Realms.joinNarrations(
               Arrays.asList(
                  lv.getDisplayName(), RealmsSelectFileToUploadScreen.method_21404(lv), RealmsSelectFileToUploadScreen.method_21400(lv).getString(), string
               )
            );
            Realms.narrateNow(I18n.translate("narrator.select", string2));
         }
      }

      public void setSelected(@Nullable RealmsSelectFileToUploadScreen.WorldListEntry arg) {
         super.setSelected(arg);
         RealmsSelectFileToUploadScreen.this.selectedWorld = this.children().indexOf(arg);
         RealmsSelectFileToUploadScreen.this.uploadButton.active = RealmsSelectFileToUploadScreen.this.selectedWorld >= 0
            && RealmsSelectFileToUploadScreen.this.selectedWorld < this.getItemCount()
            && !RealmsSelectFileToUploadScreen.this.levelList.get(RealmsSelectFileToUploadScreen.this.selectedWorld).isHardcore();
      }
   }
}
