/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.tutorial;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.class_1156;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.client.tutorial.TutorialStepHandler;
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
    private List<class_1156.class_5524> field_26893 = Lists.newArrayList();

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

    public void onBlockAttacked(ClientWorld world, BlockPos pos, BlockState state, float f) {
        if (this.currentHandler != null) {
            this.currentHandler.onBlockAttacked(world, pos, state, f);
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
        if (this.currentHandler == null) {
            return;
        }
        this.currentHandler.destroy();
        this.currentHandler = null;
    }

    public void createHandler() {
        if (this.currentHandler != null) {
            this.destroyHandler();
        }
        this.currentHandler = this.client.options.tutorialStep.createHandler(this);
    }

    public void method_31365(TutorialToast tutorialToast, int n) {
        this.field_26893.add(new class_1156.class_5524(tutorialToast, n, null));
        this.client.getToastManager().add(tutorialToast);
    }

    public void method_31364(TutorialToast tutorialToast) {
        this.field_26893.removeIf(class_55242 -> class_1156.class_5524.method_31370(class_55242) == tutorialToast);
        tutorialToast.hide();
    }

    public void tick() {
        this.field_26893.removeIf(object -> class_1156.class_5524.method_31369((class_1156.class_5524)object));
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
        if (this.client.interactionManager == null) {
            return GameMode.NOT_SET;
        }
        return this.client.interactionManager.getCurrentGameMode();
    }

    public static Text getKeybindName(String string) {
        return new KeybindText("key." + string).formatted(Formatting.BOLD);
    }
}

