/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsServerAddress;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.exception.RetryCallException;
import net.minecraft.client.realms.gui.screen.RealmsBrokenWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsGenericErrorScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongConfirmationScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.realms.gui.screen.RealmsTermsScreen;
import net.minecraft.client.realms.task.LongRunningTask;
import net.minecraft.client.realms.task.RealmsConnectTask;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class RealmsGetServerDetailsTask
extends LongRunningTask {
    private final RealmsServer server;
    private final Screen lastScreen;
    private final RealmsMainScreen mainScreen;
    private final ReentrantLock connectLock;

    public RealmsGetServerDetailsTask(RealmsMainScreen mainScreen, Screen lastScreen, RealmsServer server, ReentrantLock connectLock) {
        this.lastScreen = lastScreen;
        this.mainScreen = mainScreen;
        this.server = server;
        this.connectLock = connectLock;
    }

    @Override
    public void run() {
        this.setTitle(new TranslatableText("mco.connect.connecting"));
        RealmsClient realmsClient = RealmsClient.createRealmsClient();
        boolean _snowman2 = false;
        boolean _snowman3 = false;
        int _snowman4 = 5;
        RealmsServerAddress _snowman5 = null;
        boolean _snowman6 = false;
        boolean _snowman7 = false;
        for (int i = 0; i < 40 && !this.aborted(); ++i) {
            try {
                _snowman5 = realmsClient.join(this.server.id);
                _snowman2 = true;
            }
            catch (RetryCallException retryCallException) {
                _snowman4 = retryCallException.delaySeconds;
            }
            catch (RealmsServiceException realmsServiceException) {
                if (realmsServiceException.errorCode == 6002) {
                    _snowman6 = true;
                    break;
                }
                if (realmsServiceException.errorCode == 6006) {
                    _snowman7 = true;
                    break;
                }
                _snowman3 = true;
                this.error(realmsServiceException.toString());
                LOGGER.error("Couldn't connect to world", (Throwable)realmsServiceException);
                break;
            }
            catch (Exception exception) {
                _snowman3 = true;
                LOGGER.error("Couldn't connect to world", (Throwable)exception);
                this.error(exception.getLocalizedMessage());
                break;
            }
            if (_snowman2) break;
            this.sleep(_snowman4);
        }
        if (_snowman6) {
            RealmsGetServerDetailsTask.setScreen(new RealmsTermsScreen(this.lastScreen, this.mainScreen, this.server));
        } else if (_snowman7) {
            if (this.server.ownerUUID.equals(MinecraftClient.getInstance().getSession().getUuid())) {
                RealmsGetServerDetailsTask.setScreen(new RealmsBrokenWorldScreen(this.lastScreen, this.mainScreen, this.server.id, this.server.worldType == RealmsServer.WorldType.MINIGAME));
            } else {
                RealmsGetServerDetailsTask.setScreen(new RealmsGenericErrorScreen(new TranslatableText("mco.brokenworld.nonowner.title"), new TranslatableText("mco.brokenworld.nonowner.error"), this.lastScreen));
            }
        } else if (!this.aborted() && !_snowman3) {
            if (_snowman2) {
                RealmsServerAddress realmsServerAddress = _snowman5;
                if (realmsServerAddress.resourcePackUrl != null && realmsServerAddress.resourcePackHash != null) {
                    TranslatableText translatableText = new TranslatableText("mco.configure.world.resourcepack.question.line1");
                    _snowman = new TranslatableText("mco.configure.world.resourcepack.question.line2");
                    RealmsGetServerDetailsTask.setScreen(new RealmsLongConfirmationScreen(bl -> {
                        try {
                            if (bl) {
                                Function<Throwable, Void> function = throwable -> {
                                    MinecraftClient.getInstance().getResourcePackDownloader().clear();
                                    LOGGER.error(throwable);
                                    RealmsGetServerDetailsTask.setScreen(new RealmsGenericErrorScreen(new LiteralText("Failed to download resource pack!"), this.lastScreen));
                                    return null;
                                };
                                try {
                                    ((CompletableFuture)MinecraftClient.getInstance().getResourcePackDownloader().download(realmsServerAddress.resourcePackUrl, realmsServerAddress.resourcePackHash).thenRun(() -> this.setScreen(new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsConnectTask(this.lastScreen, this.server, realmsServerAddress))))).exceptionally(function);
                                }
                                catch (Exception _snowman2) {
                                    function.apply(_snowman2);
                                }
                            } else {
                                RealmsGetServerDetailsTask.setScreen(this.lastScreen);
                            }
                        }
                        finally {
                            if (this.connectLock != null && this.connectLock.isHeldByCurrentThread()) {
                                this.connectLock.unlock();
                            }
                        }
                    }, RealmsLongConfirmationScreen.Type.Info, translatableText, _snowman, true));
                } else {
                    this.setScreen(new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsConnectTask(this.lastScreen, this.server, realmsServerAddress)));
                }
            } else {
                this.error(new TranslatableText("mco.errorMessage.connectionFailure"));
            }
        }
    }

    private void sleep(int sleepTimeSeconds) {
        try {
            Thread.sleep(sleepTimeSeconds * 1000);
        }
        catch (InterruptedException interruptedException) {
            LOGGER.warn(interruptedException.getLocalizedMessage());
        }
    }
}

