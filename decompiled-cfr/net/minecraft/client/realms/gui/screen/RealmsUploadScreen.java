/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.RateLimiter
 *  org.apache.commons.compress.archivers.ArchiveEntry
 *  org.apache.commons.compress.archivers.tar.TarArchiveEntry
 *  org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
 *  org.apache.commons.compress.utils.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.FileUpload;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.SizeUnit;
import net.minecraft.client.realms.UploadStatus;
import net.minecraft.client.realms.dto.UploadInfo;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.exception.RetryCallException;
import net.minecraft.client.realms.gui.screen.RealmsResetWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.realms.util.UploadTokenCache;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsUploadScreen
extends RealmsScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ReentrantLock UPLOAD_LOCK = new ReentrantLock();
    private static final String[] DOTS = new String[]{"", ".", ". .", ". . ."};
    private static final Text field_26526 = new TranslatableText("mco.upload.verifying");
    private final RealmsResetWorldScreen parent;
    private final LevelSummary selectedLevel;
    private final long worldId;
    private final int slotId;
    private final UploadStatus uploadStatus;
    private final RateLimiter narrationRateLimiter;
    private volatile Text[] field_20503;
    private volatile Text status = new TranslatableText("mco.upload.preparing");
    private volatile String progress;
    private volatile boolean cancelled;
    private volatile boolean uploadFinished;
    private volatile boolean showDots = true;
    private volatile boolean uploadStarted;
    private ButtonWidget backButton;
    private ButtonWidget cancelButton;
    private int animTick;
    private Long previousWrittenBytes;
    private Long previousTimeSnapshot;
    private long bytesPersSecond;
    private final Runnable field_22728;

    public RealmsUploadScreen(long worldId, int slotId, RealmsResetWorldScreen parent, LevelSummary levelSummary, Runnable runnable) {
        this.worldId = worldId;
        this.slotId = slotId;
        this.parent = parent;
        this.selectedLevel = levelSummary;
        this.uploadStatus = new UploadStatus();
        this.narrationRateLimiter = RateLimiter.create((double)0.1f);
        this.field_22728 = runnable;
    }

    @Override
    public void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.backButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 42, 200, 20, ScreenTexts.BACK, buttonWidget -> this.onBack()));
        this.backButton.visible = false;
        this.cancelButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 42, 200, 20, ScreenTexts.CANCEL, buttonWidget -> this.onCancel()));
        if (!this.uploadStarted) {
            if (this.parent.slot == -1) {
                this.upload();
            } else {
                this.parent.switchSlot(() -> {
                    if (!this.uploadStarted) {
                        this.uploadStarted = true;
                        this.client.openScreen(this);
                        this.upload();
                    }
                });
            }
        }
    }

    @Override
    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
    }

    private void onBack() {
        this.field_22728.run();
    }

    private void onCancel() {
        this.cancelled = true;
        this.client.openScreen(this.parent);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            if (this.showDots) {
                this.onCancel();
            } else {
                this.onBack();
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        if (!this.uploadFinished && this.uploadStatus.bytesWritten != 0L && this.uploadStatus.bytesWritten == this.uploadStatus.totalBytes) {
            this.status = field_26526;
            this.cancelButton.active = false;
        }
        RealmsUploadScreen.drawCenteredText(matrices, this.textRenderer, this.status, this.width / 2, 50, 0xFFFFFF);
        if (this.showDots) {
            this.drawDots(matrices);
        }
        if (this.uploadStatus.bytesWritten != 0L && !this.cancelled) {
            this.drawProgressBar(matrices);
            this.drawUploadSpeed(matrices);
        }
        if (this.field_20503 != null) {
            for (int i = 0; i < this.field_20503.length; ++i) {
                RealmsUploadScreen.drawCenteredText(matrices, this.textRenderer, this.field_20503[i], this.width / 2, 110 + 12 * i, 0xFF0000);
            }
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void drawDots(MatrixStack matrixStack) {
        int n = this.textRenderer.getWidth(this.status);
        this.textRenderer.draw(matrixStack, DOTS[this.animTick / 10 % DOTS.length], (float)(this.width / 2 + n / 2 + 5), 50.0f, 0xFFFFFF);
    }

    private void drawProgressBar(MatrixStack matrixStack) {
        double d = Math.min((double)this.uploadStatus.bytesWritten / (double)this.uploadStatus.totalBytes, 1.0);
        this.progress = String.format(Locale.ROOT, "%.1f", d * 100.0);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableTexture();
        _snowman = this.width / 2 - 100;
        _snowman = 0.5;
        Tessellator _snowman2 = Tessellator.getInstance();
        BufferBuilder _snowman3 = _snowman2.getBuffer();
        _snowman3.begin(7, VertexFormats.POSITION_COLOR);
        _snowman3.vertex(_snowman - 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
        _snowman3.vertex(_snowman + 200.0 * d + 0.5, 95.5, 0.0).color(217, 210, 210, 255).next();
        _snowman3.vertex(_snowman + 200.0 * d + 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
        _snowman3.vertex(_snowman - 0.5, 79.5, 0.0).color(217, 210, 210, 255).next();
        _snowman3.vertex(_snowman, 95.0, 0.0).color(128, 128, 128, 255).next();
        _snowman3.vertex(_snowman + 200.0 * d, 95.0, 0.0).color(128, 128, 128, 255).next();
        _snowman3.vertex(_snowman + 200.0 * d, 80.0, 0.0).color(128, 128, 128, 255).next();
        _snowman3.vertex(_snowman, 80.0, 0.0).color(128, 128, 128, 255).next();
        _snowman2.draw();
        RenderSystem.enableTexture();
        RealmsUploadScreen.drawCenteredString(matrixStack, this.textRenderer, this.progress + " %", this.width / 2, 84, 0xFFFFFF);
    }

    private void drawUploadSpeed(MatrixStack matrixStack2) {
        if (this.animTick % 20 == 0) {
            if (this.previousWrittenBytes != null) {
                long l = Util.getMeasuringTimeMs() - this.previousTimeSnapshot;
                if (l == 0L) {
                    l = 1L;
                }
                this.bytesPersSecond = 1000L * (this.uploadStatus.bytesWritten - this.previousWrittenBytes) / l;
                this.drawUploadSpeed0(matrixStack2, this.bytesPersSecond);
            }
            this.previousWrittenBytes = this.uploadStatus.bytesWritten;
            this.previousTimeSnapshot = Util.getMeasuringTimeMs();
        } else {
            MatrixStack matrixStack2;
            this.drawUploadSpeed0(matrixStack2, this.bytesPersSecond);
        }
    }

    private void drawUploadSpeed0(MatrixStack matrixStack, long l) {
        if (l > 0L) {
            int n = this.textRenderer.getWidth(this.progress);
            String _snowman2 = "(" + SizeUnit.getUserFriendlyString(l) + "/s)";
            this.textRenderer.draw(matrixStack, _snowman2, (float)(this.width / 2 + n / 2 + 15), 84.0f, 0xFFFFFF);
        }
    }

    @Override
    public void tick() {
        super.tick();
        ++this.animTick;
        if (this.status != null && this.narrationRateLimiter.tryAcquire(1)) {
            ArrayList arrayList = Lists.newArrayList();
            arrayList.add(this.status.getString());
            if (this.progress != null) {
                arrayList.add(this.progress + "%");
            }
            if (this.field_20503 != null) {
                Stream.of(this.field_20503).map(Text::getString).forEach(arrayList::add);
            }
            Realms.narrateNow(String.join((CharSequence)System.lineSeparator(), arrayList));
        }
    }

    private void upload() {
        this.uploadStarted = true;
        new Thread(() -> {
            File _snowman5 = null;
            RealmsClient _snowman2 = RealmsClient.createRealmsClient();
            long _snowman3 = this.worldId;
            try {
                if (!UPLOAD_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
                    this.status = new TranslatableText("mco.upload.close.failure");
                    return;
                }
                UploadInfo uploadInfo = null;
                for (int i = 0; i < 20; ++i) {
                    block35: {
                        if (!this.cancelled) break block35;
                        this.uploadCancelled();
                        return;
                    }
                    try {
                        uploadInfo = _snowman2.upload(_snowman3, UploadTokenCache.get(_snowman3));
                        if (uploadInfo == null) continue;
                        break;
                    }
                    catch (RetryCallException retryCallException) {
                        Thread.sleep(retryCallException.delaySeconds * 1000);
                    }
                }
                if (uploadInfo == null) {
                    this.status = new TranslatableText("mco.upload.close.failure");
                    return;
                }
                UploadTokenCache.put(_snowman3, uploadInfo.getToken());
                if (!uploadInfo.isWorldClosed()) {
                    this.status = new TranslatableText("mco.upload.close.failure");
                    return;
                }
                if (this.cancelled) {
                    this.uploadCancelled();
                    return;
                }
                File _snowman4 = new File(this.client.runDirectory.getAbsolutePath(), "saves");
                _snowman5 = this.tarGzipArchive(new File(_snowman4, this.selectedLevel.getName()));
                if (this.cancelled) {
                    this.uploadCancelled();
                    return;
                }
                if (!this.verify(_snowman5)) {
                    long l = _snowman5.length();
                    SizeUnit _snowman6 = SizeUnit.getLargestUnit(l);
                    SizeUnit _snowman7 = SizeUnit.getLargestUnit(0x140000000L);
                    if (SizeUnit.humanReadableSize(l, _snowman6).equals(SizeUnit.humanReadableSize(0x140000000L, _snowman7)) && _snowman6 != SizeUnit.B) {
                        SizeUnit sizeUnit = SizeUnit.values()[_snowman6.ordinal() - 1];
                        this.method_27460(new TranslatableText("mco.upload.size.failure.line1", this.selectedLevel.getDisplayName()), new TranslatableText("mco.upload.size.failure.line2", SizeUnit.humanReadableSize(l, sizeUnit), SizeUnit.humanReadableSize(0x140000000L, sizeUnit)));
                        return;
                    }
                    this.method_27460(new TranslatableText("mco.upload.size.failure.line1", this.selectedLevel.getDisplayName()), new TranslatableText("mco.upload.size.failure.line2", SizeUnit.humanReadableSize(l, _snowman6), SizeUnit.humanReadableSize(0x140000000L, _snowman7)));
                    return;
                }
                this.status = new TranslatableText("mco.upload.uploading", this.selectedLevel.getDisplayName());
                FileUpload _snowman8 = new FileUpload(_snowman5, this.worldId, this.slotId, uploadInfo, this.client.getSession(), SharedConstants.getGameVersion().getName(), this.uploadStatus);
                _snowman8.upload(uploadResult -> {
                    if (uploadResult.statusCode >= 200 && uploadResult.statusCode < 300) {
                        this.uploadFinished = true;
                        this.status = new TranslatableText("mco.upload.done");
                        this.backButton.setMessage(ScreenTexts.DONE);
                        UploadTokenCache.invalidate(_snowman3);
                    } else if (uploadResult.statusCode == 400 && uploadResult.errorMessage != null) {
                        this.method_27460(new TranslatableText("mco.upload.failed", uploadResult.errorMessage));
                    } else {
                        this.method_27460(new TranslatableText("mco.upload.failed", uploadResult.statusCode));
                    }
                });
                while (!_snowman8.isFinished()) {
                    if (this.cancelled) {
                        _snowman8.cancel();
                        this.uploadCancelled();
                        return;
                    }
                    try {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException interruptedException) {
                        LOGGER.error("Failed to check Realms file upload status");
                    }
                }
            }
            catch (IOException iOException) {
                this.method_27460(new TranslatableText("mco.upload.failed", iOException.getMessage()));
            }
            catch (RealmsServiceException realmsServiceException) {
                this.method_27460(new TranslatableText("mco.upload.failed", realmsServiceException.toString()));
            }
            catch (InterruptedException interruptedException) {
                LOGGER.error("Could not acquire upload lock");
            }
            finally {
                this.uploadFinished = true;
                if (!UPLOAD_LOCK.isHeldByCurrentThread()) {
                    return;
                }
                UPLOAD_LOCK.unlock();
                this.showDots = false;
                this.backButton.visible = true;
                this.cancelButton.visible = false;
                if (_snowman5 != null) {
                    LOGGER.debug("Deleting file " + _snowman5.getAbsolutePath());
                    _snowman5.delete();
                }
            }
        }).start();
    }

    private void method_27460(Text ... textArray) {
        this.field_20503 = textArray;
    }

    private void uploadCancelled() {
        this.status = new TranslatableText("mco.upload.cancelled");
        LOGGER.debug("Upload was cancelled");
    }

    private boolean verify(File archive) {
        return archive.length() < 0x140000000L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private File tarGzipArchive(File pathToDirectoryFile) throws IOException {
        try (TarArchiveOutputStream _snowman2 = null;){
            File file = File.createTempFile("realms-upload-file", ".tar.gz");
            _snowman2 = new TarArchiveOutputStream((OutputStream)new GZIPOutputStream(new FileOutputStream(file)));
            _snowman2.setLongFileMode(3);
            this.addFileToTarGz(_snowman2, pathToDirectoryFile.getAbsolutePath(), "world", true);
            _snowman2.finish();
            File file2 = file;
            return file2;
        }
    }

    private void addFileToTarGz(TarArchiveOutputStream tOut, String path, String base, boolean root) throws IOException {
        if (this.cancelled) {
            return;
        }
        File file = new File(path);
        String _snowman2 = root ? base : base + file.getName();
        TarArchiveEntry _snowman3 = new TarArchiveEntry(file, _snowman2);
        tOut.putArchiveEntry((ArchiveEntry)_snowman3);
        if (file.isFile()) {
            IOUtils.copy((InputStream)new FileInputStream(file), (OutputStream)tOut);
            tOut.closeArchiveEntry();
        } else {
            tOut.closeArchiveEntry();
            File[] fileArray = file.listFiles();
            if (fileArray != null) {
                for (File file2 : fileArray) {
                    this.addFileToTarGz(tOut, file2.getAbsolutePath(), _snowman2 + "/", false);
                }
            }
        }
    }
}

