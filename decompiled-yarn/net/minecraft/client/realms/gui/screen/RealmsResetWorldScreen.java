package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.WorldTemplate;
import net.minecraft.client.realms.dto.WorldTemplatePaginatedList;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.task.ResettingWorldTask;
import net.minecraft.client.realms.task.SwitchSlotTask;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsResetWorldScreen extends RealmsScreenWithCallback {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Screen parent;
   private final RealmsServer serverData;
   private RealmsLabel titleLabel;
   private RealmsLabel subtitleLabel;
   private Text title = new TranslatableText("mco.reset.world.title");
   private Text subtitle = new TranslatableText("mco.reset.world.warning");
   private Text buttonTitle = ScreenTexts.CANCEL;
   private int subtitleColor = 16711680;
   private static final Identifier SLOT_FRAME_TEXTURE = new Identifier("realms", "textures/gui/realms/slot_frame.png");
   private static final Identifier UPLOAD_TEXTURE = new Identifier("realms", "textures/gui/realms/upload.png");
   private static final Identifier ADVENTURE_TEXTURE = new Identifier("realms", "textures/gui/realms/adventure.png");
   private static final Identifier SURVIVAL_SPAWN_TEXTURE = new Identifier("realms", "textures/gui/realms/survival_spawn.png");
   private static final Identifier NEW_WORLD_TEXTURE = new Identifier("realms", "textures/gui/realms/new_world.png");
   private static final Identifier EXPERIENCE_TEXTURE = new Identifier("realms", "textures/gui/realms/experience.png");
   private static final Identifier INSPIRATION_TEXTURE = new Identifier("realms", "textures/gui/realms/inspiration.png");
   private WorldTemplatePaginatedList field_20495;
   private WorldTemplatePaginatedList field_20496;
   private WorldTemplatePaginatedList field_20497;
   private WorldTemplatePaginatedList field_20498;
   public int slot = -1;
   private RealmsResetWorldScreen.ResetType typeToReset = RealmsResetWorldScreen.ResetType.NONE;
   private RealmsResetWorldScreen.ResetWorldInfo field_20499;
   private WorldTemplate field_20500;
   @Nullable
   private Text field_20501;
   private final Runnable field_22711;
   private final Runnable field_22712;

   public RealmsResetWorldScreen(Screen parent, RealmsServer _snowman, Runnable _snowman, Runnable _snowman) {
      this.parent = parent;
      this.serverData = _snowman;
      this.field_22711 = _snowman;
      this.field_22712 = _snowman;
   }

   public RealmsResetWorldScreen(Screen parent, RealmsServer _snowman, Text _snowman, Text _snowman, int _snowman, Text _snowman, Runnable _snowman, Runnable _snowman) {
      this(parent, _snowman, _snowman, _snowman);
      this.title = _snowman;
      this.subtitle = _snowman;
      this.subtitleColor = _snowman;
      this.buttonTitle = _snowman;
   }

   public void setSlot(int slot) {
      this.slot = slot;
   }

   public void setResetTitle(Text _snowman) {
      this.field_20501 = _snowman;
   }

   @Override
   public void init() {
      this.addButton(new ButtonWidget(this.width / 2 - 40, row(14) - 10, 80, 20, this.buttonTitle, _snowman -> this.client.openScreen(this.parent)));
      (new Thread("Realms-reset-world-fetcher") {
         @Override
         public void run() {
            RealmsClient _snowman = RealmsClient.createRealmsClient();

            try {
               WorldTemplatePaginatedList _snowmanx = _snowman.fetchWorldTemplates(1, 10, RealmsServer.WorldType.NORMAL);
               WorldTemplatePaginatedList _snowmanxx = _snowman.fetchWorldTemplates(1, 10, RealmsServer.WorldType.ADVENTUREMAP);
               WorldTemplatePaginatedList _snowmanxxx = _snowman.fetchWorldTemplates(1, 10, RealmsServer.WorldType.EXPERIENCE);
               WorldTemplatePaginatedList _snowmanxxxx = _snowman.fetchWorldTemplates(1, 10, RealmsServer.WorldType.INSPIRATION);
               RealmsResetWorldScreen.this.client.execute(() -> {
                  RealmsResetWorldScreen.this.field_20495 = _snowman;
                  RealmsResetWorldScreen.this.field_20496 = _snowman;
                  RealmsResetWorldScreen.this.field_20497 = _snowman;
                  RealmsResetWorldScreen.this.field_20498 = _snowman;
               });
            } catch (RealmsServiceException var6) {
               RealmsResetWorldScreen.LOGGER.error("Couldn't fetch templates in reset world", var6);
            }
         }
      }).start();
      this.titleLabel = this.addChild(new RealmsLabel(this.title, this.width / 2, 7, 16777215));
      this.subtitleLabel = this.addChild(new RealmsLabel(this.subtitle, this.width / 2, 22, this.subtitleColor));
      this.addButton(
         new RealmsResetWorldScreen.FrameButton(
            this.frame(1),
            row(0) + 10,
            new TranslatableText("mco.reset.world.generate"),
            NEW_WORLD_TEXTURE,
            _snowman -> this.client.openScreen(new RealmsResetNormalWorldScreen(this, this.title))
         )
      );
      this.addButton(new RealmsResetWorldScreen.FrameButton(this.frame(2), row(0) + 10, new TranslatableText("mco.reset.world.upload"), UPLOAD_TEXTURE, _snowman -> {
         Screen _snowmanx = new RealmsSelectFileToUploadScreen(this.serverData.id, this.slot != -1 ? this.slot : this.serverData.activeSlot, this, this.field_22712);
         this.client.openScreen(_snowmanx);
      }));
      this.addButton(
         new RealmsResetWorldScreen.FrameButton(this.frame(3), row(0) + 10, new TranslatableText("mco.reset.world.template"), SURVIVAL_SPAWN_TEXTURE, _snowman -> {
            RealmsSelectWorldTemplateScreen _snowmanx = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.NORMAL, this.field_20495);
            _snowmanx.setTitle(new TranslatableText("mco.reset.world.template"));
            this.client.openScreen(_snowmanx);
         })
      );
      this.addButton(
         new RealmsResetWorldScreen.FrameButton(this.frame(1), row(6) + 20, new TranslatableText("mco.reset.world.adventure"), ADVENTURE_TEXTURE, _snowman -> {
            RealmsSelectWorldTemplateScreen _snowmanx = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.ADVENTUREMAP, this.field_20496);
            _snowmanx.setTitle(new TranslatableText("mco.reset.world.adventure"));
            this.client.openScreen(_snowmanx);
         })
      );
      this.addButton(
         new RealmsResetWorldScreen.FrameButton(this.frame(2), row(6) + 20, new TranslatableText("mco.reset.world.experience"), EXPERIENCE_TEXTURE, _snowman -> {
            RealmsSelectWorldTemplateScreen _snowmanx = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.EXPERIENCE, this.field_20497);
            _snowmanx.setTitle(new TranslatableText("mco.reset.world.experience"));
            this.client.openScreen(_snowmanx);
         })
      );
      this.addButton(
         new RealmsResetWorldScreen.FrameButton(this.frame(3), row(6) + 20, new TranslatableText("mco.reset.world.inspiration"), INSPIRATION_TEXTURE, _snowman -> {
            RealmsSelectWorldTemplateScreen _snowmanx = new RealmsSelectWorldTemplateScreen(this, RealmsServer.WorldType.INSPIRATION, this.field_20498);
            _snowmanx.setTitle(new TranslatableText("mco.reset.world.inspiration"));
            this.client.openScreen(_snowmanx);
         })
      );
      this.narrateLabels();
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
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

   private int frame(int i) {
      return this.width / 2 - 130 + (i - 1) * 100;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      this.titleLabel.render(this, matrices);
      this.subtitleLabel.render(this, matrices);
      super.render(matrices, mouseX, mouseY, delta);
   }

   private void drawFrame(MatrixStack _snowman, int x, int y, Text text, Identifier _snowman, boolean _snowman, boolean _snowman) {
      this.client.getTextureManager().bindTexture(_snowman);
      if (_snowman) {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      } else {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      DrawableHelper.drawTexture(_snowman, x + 2, y + 14, 0.0F, 0.0F, 56, 56, 56, 56);
      this.client.getTextureManager().bindTexture(SLOT_FRAME_TEXTURE);
      if (_snowman) {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      } else {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      DrawableHelper.drawTexture(_snowman, x, y + 12, 0.0F, 0.0F, 60, 60, 60, 60);
      int _snowmanxxxx = _snowman ? 10526880 : 16777215;
      drawCenteredText(_snowman, this.textRenderer, text, x + 30, y, _snowmanxxxx);
   }

   @Override
   protected void callback(@Nullable WorldTemplate template) {
      if (template != null) {
         if (this.slot == -1) {
            this.resetWorldWithTemplate(template);
         } else {
            switch (template.type) {
               case WORLD_TEMPLATE:
                  this.typeToReset = RealmsResetWorldScreen.ResetType.SURVIVAL_SPAWN;
                  break;
               case ADVENTUREMAP:
                  this.typeToReset = RealmsResetWorldScreen.ResetType.ADVENTURE;
                  break;
               case EXPERIENCE:
                  this.typeToReset = RealmsResetWorldScreen.ResetType.EXPERIENCE;
                  break;
               case INSPIRATION:
                  this.typeToReset = RealmsResetWorldScreen.ResetType.INSPIRATION;
            }

            this.field_20500 = template;
            this.switchSlot();
         }
      }
   }

   private void switchSlot() {
      this.switchSlot(() -> {
         switch (this.typeToReset) {
            case ADVENTURE:
            case SURVIVAL_SPAWN:
            case EXPERIENCE:
            case INSPIRATION:
               if (this.field_20500 != null) {
                  this.resetWorldWithTemplate(this.field_20500);
               }
               break;
            case GENERATE:
               if (this.field_20499 != null) {
                  this.triggerResetWorld(this.field_20499);
               }
         }
      });
   }

   public void switchSlot(Runnable callback) {
      this.client.openScreen(new RealmsLongRunningMcoTaskScreen(this.parent, new SwitchSlotTask(this.serverData.id, this.slot, callback)));
   }

   public void resetWorldWithTemplate(WorldTemplate template) {
      this.method_25207(null, template, -1, true);
   }

   private void triggerResetWorld(RealmsResetWorldScreen.ResetWorldInfo resetWorldInfo) {
      this.method_25207(resetWorldInfo.seed, null, resetWorldInfo.levelType, resetWorldInfo.generateStructures);
   }

   private void method_25207(@Nullable String _snowman, @Nullable WorldTemplate _snowman, int _snowman, boolean _snowman) {
      this.client
         .openScreen(
            new RealmsLongRunningMcoTaskScreen(this.parent, new ResettingWorldTask(_snowman, _snowman, _snowman, _snowman, this.serverData.id, this.field_20501, this.field_22711))
         );
   }

   public void resetWorld(RealmsResetWorldScreen.ResetWorldInfo resetWorldInfo) {
      if (this.slot == -1) {
         this.triggerResetWorld(resetWorldInfo);
      } else {
         this.typeToReset = RealmsResetWorldScreen.ResetType.GENERATE;
         this.field_20499 = resetWorldInfo;
         this.switchSlot();
      }
   }

   class FrameButton extends ButtonWidget {
      private final Identifier image;

      public FrameButton(int x, int y, Text var4, Identifier var5, ButtonWidget.PressAction var6) {
         super(x, y, 60, 72, _snowman, _snowman);
         this.image = _snowman;
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RealmsResetWorldScreen.this.drawFrame(
            matrices, this.x, this.y, this.getMessage(), this.image, this.isHovered(), this.isMouseOver((double)mouseX, (double)mouseY)
         );
      }
   }

   static enum ResetType {
      NONE,
      GENERATE,
      UPLOAD,
      ADVENTURE,
      SURVIVAL_SPAWN,
      EXPERIENCE,
      INSPIRATION;

      private ResetType() {
      }
   }

   public static class ResetWorldInfo {
      private final String seed;
      private final int levelType;
      private final boolean generateStructures;

      public ResetWorldInfo(String seed, int levelType, boolean generateStructures) {
         this.seed = seed;
         this.levelType = levelType;
         this.generateStructures = generateStructures;
      }
   }
}
