package net.minecraft.client.tutorial;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.class_1156$class_5524;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.text.KeybindText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class TutorialManager {
   private final MinecraftClient client;
   @Nullable
   private TutorialStepHandler currentHandler;
   private List<class_1156$class_5524> field_26893 = Lists.newArrayList();

   public TutorialManager(MinecraftClient client) {
      this.client = client;
   }

   public void onMovement(Input input) {
      if (this.currentHandler != null) {
         this.currentHandler.onMovement(input);
      }
   }

   public void onUpdateMouse(double deltaX, double deltaY) {
      if (this.currentHandler != null) {
         this.currentHandler.onMouseUpdate(deltaX, deltaY);
      }
   }

   public void tick(@Nullable ClientWorld world, @Nullable HitResult hitResult) {
      if (this.currentHandler != null && hitResult != null && world != null) {
         this.currentHandler.onTarget(world, hitResult);
      }
   }

   public void onBlockAttacked(ClientWorld world, BlockPos pos, BlockState state, float _snowman) {
      if (this.currentHandler != null) {
         this.currentHandler.onBlockAttacked(world, pos, state, _snowman);
      }
   }

   public void onInventoryOpened() {
      if (this.currentHandler != null) {
         this.currentHandler.onInventoryOpened();
      }
   }

   public void onSlotUpdate(ItemStack stack) {
      if (this.currentHandler != null) {
         this.currentHandler.onSlotUpdate(stack);
      }
   }

   public void destroyHandler() {
      if (this.currentHandler != null) {
         this.currentHandler.destroy();
         this.currentHandler = null;
      }
   }

   public void createHandler() {
      if (this.currentHandler != null) {
         this.destroyHandler();
      }

      this.currentHandler = this.client.options.tutorialStep.createHandler(this);
   }

   public void method_31365(TutorialToast _snowman, int _snowman) {
      this.field_26893.add(new class_1156$class_5524(_snowman, _snowman));
      this.client.getToastManager().add(_snowman);
   }

   public void method_31364(TutorialToast _snowman) {
      this.field_26893.removeIf(_snowmanxx -> class_1156$class_5524.method_31370(_snowmanxx) == _snowman);
      _snowman.hide();
   }

   public void tick() {
      this.field_26893.removeIf(_snowman -> class_1156$class_5524.method_31369(_snowman));
      if (this.currentHandler != null) {
         if (this.client.world != null) {
            this.currentHandler.tick();
         } else {
            this.destroyHandler();
         }
      } else if (this.client.world != null) {
         this.createHandler();
      }
   }

   public void setStep(TutorialStep step) {
      this.client.options.tutorialStep = step;
      this.client.options.write();
      if (this.currentHandler != null) {
         this.currentHandler.destroy();
         this.currentHandler = step.createHandler(this);
      }
   }

   public MinecraftClient getClient() {
      return this.client;
   }

   public GameMode getGameMode() {
      return this.client.interactionManager == null ? GameMode.NOT_SET : this.client.interactionManager.getCurrentGameMode();
   }

   public static Text getKeybindName(String _snowman) {
      return new KeybindText("key." + _snowman).formatted(Formatting.BOLD);
   }
}
