/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.task;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.task.LongRunningTask;
import net.minecraft.text.TranslatableText;

public class WorldCreationTask
extends LongRunningTask {
    private final String name;
    private final String motd;
    private final long worldId;
    private final Screen lastScreen;

    public WorldCreationTask(long worldId, String name, String motd, Screen lastScreen) {
        this.worldId = worldId;
        this.name = name;
        this.motd = motd;
        this.lastScreen = lastScreen;
    }

    @Override
    public void run() {
        this.setTitle(new TranslatableText("mco.create.world.wait"));
        RealmsClient realmsClient = RealmsClient.createRealmsClient();
        try {
            realmsClient.initializeWorld(this.worldId, this.name, this.motd);
            WorldCreationTask.setScreen(this.lastScreen);
        }
        catch (RealmsServiceException _snowman2) {
            LOGGER.error("Couldn't create world");
            this.error(_snowman2.toString());
        }
        catch (Exception _snowman3) {
            LOGGER.error("Could not create world");
            this.error(_snowman3.getLocalizedMessage());
        }
    }
}

