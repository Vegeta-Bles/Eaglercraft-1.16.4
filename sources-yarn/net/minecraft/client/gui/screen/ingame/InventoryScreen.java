package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

@Environment(EnvType.CLIENT)
public class InventoryScreen extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
   private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
   private float mouseX;
   private float mouseY;
   private final RecipeBookWidget recipeBook = new RecipeBookWidget();
   private boolean open;
   private boolean narrow;
   private boolean mouseDown;

   public InventoryScreen(PlayerEntity player) {
      super(player.playerScreenHandler, player.inventory, new TranslatableText("container.crafting"));
      this.passEvents = true;
      this.titleX = 97;
   }

   @Override
   public void tick() {
      if (this.client.interactionManager.hasCreativeInventory()) {
         this.client.openScreen(new CreativeInventoryScreen(this.client.player));
      } else {
         this.recipeBook.update();
      }
   }

   @Override
   protected void init() {
      if (this.client.interactionManager.hasCreativeInventory()) {
         this.client.openScreen(new CreativeInventoryScreen(this.client.player));
      } else {
         super.init();
         this.narrow = this.width < 379;
         this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
         this.open = true;
         this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
         this.children.add(this.recipeBook);
         this.setInitialFocus(this.recipeBook);
         this.addButton(new TexturedButtonWidget(this.x + 104, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, arg -> {
            this.recipeBook.reset(this.narrow);
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
            ((TexturedButtonWidget)arg).setPos(this.x + 104, this.height / 2 - 22);
            this.mouseDown = true;
         }));
      }
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      this.drawStatusEffects = !this.recipeBook.isOpen();
      if (this.recipeBook.isOpen() && this.narrow) {
         this.drawBackground(matrices, delta, mouseX, mouseY);
         this.recipeBook.render(matrices, mouseX, mouseY, delta);
      } else {
         this.recipeBook.render(matrices, mouseX, mouseY, delta);
         super.render(matrices, mouseX, mouseY, delta);
         this.recipeBook.drawGhostSlots(matrices, this.x, this.y, false, delta);
      }

      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
      this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);
      this.mouseX = (float)mouseX;
      this.mouseY = (float)mouseY;
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
      int k = this.x;
      int l = this.y;
      this.drawTexture(matrices, k, l, 0, 0, this.backgroundWidth, this.backgroundHeight);
      drawEntity(k + 51, l + 75, 30, (float)(k + 51) - this.mouseX, (float)(l + 75 - 50) - this.mouseY, this.client.player);
   }

   public static void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
      float h = (float)Math.atan((double)(mouseX / 40.0F));
      float l = (float)Math.atan((double)(mouseY / 40.0F));
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)x, (float)y, 1050.0F);
      RenderSystem.scalef(1.0F, 1.0F, -1.0F);
      MatrixStack lv = new MatrixStack();
      lv.translate(0.0, 0.0, 1000.0);
      lv.scale((float)size, (float)size, (float)size);
      Quaternion lv2 = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
      Quaternion lv3 = Vector3f.POSITIVE_X.getDegreesQuaternion(l * 20.0F);
      lv2.hamiltonProduct(lv3);
      lv.multiply(lv2);
      float m = entity.bodyYaw;
      float n = entity.yaw;
      float o = entity.pitch;
      float p = entity.prevHeadYaw;
      float q = entity.headYaw;
      entity.bodyYaw = 180.0F + h * 20.0F;
      entity.yaw = 180.0F + h * 40.0F;
      entity.pitch = -l * 20.0F;
      entity.headYaw = entity.yaw;
      entity.prevHeadYaw = entity.yaw;
      EntityRenderDispatcher lv4 = MinecraftClient.getInstance().getEntityRenderDispatcher();
      lv3.conjugate();
      lv4.setRotation(lv3);
      lv4.setRenderShadows(false);
      VertexConsumerProvider.Immediate lv5 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
      RenderSystem.runAsFancy(() -> lv4.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, lv, lv5, 15728880));
      lv5.draw();
      lv4.setRenderShadows(true);
      entity.bodyYaw = m;
      entity.yaw = n;
      entity.pitch = o;
      entity.prevHeadYaw = p;
      entity.headYaw = q;
      RenderSystem.popMatrix();
   }

   @Override
   protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX, double pointY) {
      return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(xPosition, yPosition, width, height, pointX, pointY);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
         this.setFocused(this.recipeBook);
         return true;
      } else {
         return this.narrow && this.recipeBook.isOpen() ? false : super.mouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.mouseDown) {
         this.mouseDown = false;
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   @Override
   protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
      boolean bl = mouseX < (double)left
         || mouseY < (double)top
         || mouseX >= (double)(left + this.backgroundWidth)
         || mouseY >= (double)(top + this.backgroundHeight);
      return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && bl;
   }

   @Override
   protected void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType) {
      super.onMouseClick(slot, invSlot, clickData, actionType);
      this.recipeBook.slotClicked(slot);
   }

   @Override
   public void refreshRecipeBook() {
      this.recipeBook.refresh();
   }

   @Override
   public void removed() {
      if (this.open) {
         this.recipeBook.close();
      }

      super.removed();
   }

   @Override
   public RecipeBookWidget getRecipeBookWidget() {
      return this.recipeBook;
   }
}
