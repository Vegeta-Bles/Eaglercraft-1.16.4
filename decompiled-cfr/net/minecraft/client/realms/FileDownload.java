/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.common.io.Files
 *  org.apache.commons.compress.archivers.tar.TarArchiveInputStream
 *  org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.io.output.CountingOutputStream
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.http.client.config.RequestConfig
 *  org.apache.http.client.methods.CloseableHttpResponse
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.dto.WorldDownload;
import net.minecraft.client.realms.exception.RealmsDefaultUncaughtExceptionHandler;
import net.minecraft.client.realms.gui.screen.RealmsDownloadLatestWorldScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileDownload {
    private static final Logger LOGGER = LogManager.getLogger();
    private volatile boolean cancelled;
    private volatile boolean finished;
    private volatile boolean error;
    private volatile boolean extracting;
    private volatile File backupFile;
    private volatile File resourcePackPath;
    private volatile HttpGet httpRequest;
    private Thread currentThread;
    private final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
    private static final String[] INVALID_FILE_NAMES = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long contentLength(String downloadLink) {
        CloseableHttpClient closeableHttpClient = null;
        HttpGet _snowman2 = null;
        try {
            _snowman2 = new HttpGet(downloadLink);
            closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute((HttpUriRequest)_snowman2);
            long l = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
            return l;
        }
        catch (Throwable throwable) {
            LOGGER.error("Unable to get content length for download");
            long l = 0L;
            return l;
        }
        finally {
            if (_snowman2 != null) {
                _snowman2.releaseConnection();
            }
            if (closeableHttpClient != null) {
                try {
                    closeableHttpClient.close();
                }
                catch (IOException iOException) {
                    LOGGER.error("Could not close http client", (Throwable)iOException);
                }
            }
        }
    }

    public void downloadWorld(WorldDownload download, String message, RealmsDownloadLatestWorldScreen.DownloadStatus status, LevelStorage storage) {
        if (this.currentThread != null) {
            return;
        }
        this.currentThread = new Thread(() -> {
            CloseableHttpClient closeableHttpClient = null;
            try {
                this.backupFile = File.createTempFile("backup", ".tar.gz");
                this.httpRequest = new HttpGet(worldDownload.downloadLink);
                closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
                CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute((HttpUriRequest)this.httpRequest);
                downloadStatus.totalBytes = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
                if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
                    this.error = true;
                    this.httpRequest.abort();
                    return;
                }
                FileOutputStream _snowman2 = new FileOutputStream(this.backupFile);
                ActionListener _snowman3 = new ProgressListener(message.trim(), this.backupFile, storage, status);
                DownloadCountingOutputStream _snowman4 = new DownloadCountingOutputStream(_snowman2);
                _snowman4.setListener(_snowman3);
                IOUtils.copy((InputStream)closeableHttpResponse.getEntity().getContent(), (OutputStream)((Object)_snowman4));
                return;
            }
            catch (Exception exception) {
                LOGGER.error("Caught exception while downloading: " + exception.getMessage());
                this.error = true;
                return;
            }
            finally {
                block40: {
                    block41: {
                        this.httpRequest.releaseConnection();
                        if (this.backupFile != null) {
                            this.backupFile.delete();
                        }
                        if (this.error) break block40;
                        if (worldDownload.resourcePackUrl.isEmpty() || worldDownload.resourcePackHash.isEmpty()) break block41;
                        try {
                            this.backupFile = File.createTempFile("resources", ".tar.gz");
                            this.httpRequest = new HttpGet(worldDownload.resourcePackUrl);
                            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute((HttpUriRequest)this.httpRequest);
                            downloadStatus.totalBytes = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
                            if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
                                this.error = true;
                                this.httpRequest.abort();
                                return;
                            }
                        }
                        catch (Exception exception) {
                            LOGGER.error("Caught exception while downloading: " + exception.getMessage());
                            this.error = true;
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(this.backupFile);
                        ResourcePackProgressListener _snowman5 = new ResourcePackProgressListener(this.backupFile, status, download);
                        DownloadCountingOutputStream _snowman6 = new DownloadCountingOutputStream(fileOutputStream);
                        _snowman6.setListener(_snowman5);
                        IOUtils.copy((InputStream)closeableHttpResponse.getEntity().getContent(), (OutputStream)((Object)_snowman6));
                        break block40;
                        finally {
                            this.httpRequest.releaseConnection();
                            if (this.backupFile != null) {
                                this.backupFile.delete();
                            }
                        }
                    }
                    this.finished = true;
                }
                if (closeableHttpClient != null) {
                    try {
                        closeableHttpClient.close();
                    }
                    catch (IOException iOException) {
                        LOGGER.error("Failed to close Realms download client");
                    }
                }
            }
        });
        this.currentThread.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(LOGGER));
        this.currentThread.start();
    }

    public void cancel() {
        if (this.httpRequest != null) {
            this.httpRequest.abort();
        }
        if (this.backupFile != null) {
            this.backupFile.delete();
        }
        this.cancelled = true;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isError() {
        return this.error;
    }

    public boolean isExtracting() {
        return this.extracting;
    }

    public static String findAvailableFolderName(String folder) {
        folder = folder.replaceAll("[\\./\"]", "_");
        for (String string : INVALID_FILE_NAMES) {
            if (!folder.equalsIgnoreCase(string)) continue;
            folder = "_" + folder + "_";
        }
        return folder;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void untarGzipArchive(String name, File archive, LevelStorage storage) throws IOException {
        block66: {
            boolean _snowman4;
            String string;
            int _snowman2;
            block67: {
                block65: {
                    Pattern pattern = Pattern.compile(".*-([0-9]+)$");
                    _snowman2 = 1;
                    Object object = SharedConstants.INVALID_CHARS_LEVEL_NAME;
                    int n = ((char[])object).length;
                    for (int n2 = 0; n2 < n; name = name.replace(c, '_'), ++n2) {
                        char c = object[n2];
                    }
                    if (StringUtils.isEmpty((CharSequence)name)) {
                        name = "Realm";
                    }
                    name = FileDownload.findAvailableFolderName(name);
                    try {
                        object = storage.getLevelList().iterator();
                        while (object.hasNext()) {
                            LevelSummary levelSummary = (LevelSummary)object.next();
                            if (!levelSummary.getName().toLowerCase(Locale.ROOT).startsWith(name.toLowerCase(Locale.ROOT))) continue;
                            Matcher _snowman3 = pattern.matcher(levelSummary.getName());
                            if (_snowman3.matches()) {
                                if (Integer.valueOf(_snowman3.group(1)) <= _snowman2) continue;
                                _snowman2 = Integer.valueOf(_snowman3.group(1));
                                continue;
                            }
                            ++_snowman2;
                        }
                    }
                    catch (Exception exception) {
                        LOGGER.error("Error getting level list", (Throwable)exception);
                        this.error = true;
                        return;
                    }
                    if (storage.isLevelNameValid(name) && _snowman2 <= true) break block65;
                    string = name + (_snowman2 == 1 ? "" : "-" + _snowman2);
                    if (storage.isLevelNameValid(string)) break block66;
                    _snowman4 = false;
                    break block67;
                }
                string = name;
                break block66;
            }
            while (!_snowman4) {
                string = name + (++_snowman2 == 1 ? "" : "-" + _snowman2);
                if (!storage.isLevelNameValid(string)) continue;
                _snowman4 = true;
            }
        }
        TarArchiveInputStream tarArchiveInputStream = null;
        File _snowman5 = new File(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "saves");
        try {
            _snowman5.mkdir();
            tarArchiveInputStream = new TarArchiveInputStream((InputStream)new GzipCompressorInputStream((InputStream)new BufferedInputStream(new FileInputStream(archive))));
            Object _snowman6 = tarArchiveInputStream.getNextTarEntry();
            while (_snowman6 != null) {
                File file = new File(_snowman5, _snowman6.getName().replace("world", string));
                if (_snowman6.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.createNewFile();
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                        IOUtils.copy((InputStream)tarArchiveInputStream, (OutputStream)fileOutputStream);
                    }
                }
                _snowman6 = tarArchiveInputStream.getNextTarEntry();
            }
            return;
        }
        catch (Exception exception) {
            LOGGER.error("Error extracting world", (Throwable)exception);
            this.error = true;
            return;
        }
        finally {
            if (tarArchiveInputStream != null) {
                tarArchiveInputStream.close();
            }
            if (archive != null) {
                archive.delete();
            }
            try (LevelStorage.Session session = storage.createSession(string);){
                session.save(string.trim());
                Path path = session.getDirectory(WorldSavePath.LEVEL_DAT);
                FileDownload.readNbtFile(path.toFile());
            }
            catch (IOException iOException) {
                LOGGER.error("Failed to rename unpacked realms level {}", (Object)string, (Object)iOException);
            }
            this.resourcePackPath = new File(_snowman5, string + File.separator + "resources.zip");
        }
    }

    private static void readNbtFile(File file) {
        if (file.exists()) {
            try {
                CompoundTag compoundTag = NbtIo.readCompressed(file);
                _snowman = compoundTag.getCompound("Data");
                _snowman.remove("Player");
                NbtIo.writeCompressed(compoundTag, file);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    class DownloadCountingOutputStream
    extends CountingOutputStream {
        private ActionListener listener;

        public DownloadCountingOutputStream(OutputStream out) {
            super(out);
        }

        public void setListener(ActionListener listener) {
            this.listener = listener;
        }

        protected void afterWrite(int n) throws IOException {
            super.afterWrite(n);
            if (this.listener != null) {
                this.listener.actionPerformed(new ActionEvent((Object)this, 0, null));
            }
        }
    }

    class ResourcePackProgressListener
    implements ActionListener {
        private final File tempFile;
        private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;
        private final WorldDownload worldDownload;

        private ResourcePackProgressListener(File tempFile, RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus, WorldDownload worldDownload) {
            this.tempFile = tempFile;
            this.downloadStatus = downloadStatus;
            this.worldDownload = worldDownload;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.downloadStatus.bytesWritten = ((DownloadCountingOutputStream)((Object)e.getSource())).getByteCount();
            if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled) {
                try {
                    String string = Hashing.sha1().hashBytes(Files.toByteArray((File)this.tempFile)).toString();
                    if (string.equals(this.worldDownload.resourcePackHash)) {
                        FileUtils.copyFile((File)this.tempFile, (File)FileDownload.this.resourcePackPath);
                        FileDownload.this.finished = true;
                    } else {
                        LOGGER.error("Resourcepack had wrong hash (expected " + this.worldDownload.resourcePackHash + ", found " + string + "). Deleting it.");
                        FileUtils.deleteQuietly((File)this.tempFile);
                        FileDownload.this.error = true;
                    }
                }
                catch (IOException iOException) {
                    LOGGER.error("Error copying resourcepack file", (Object)iOException.getMessage());
                    FileDownload.this.error = true;
                }
            }
        }
    }

    class ProgressListener
    implements ActionListener {
        private final String worldName;
        private final File tempFile;
        private final LevelStorage levelStorageSource;
        private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;

        private ProgressListener(String worldName, File tempFile, LevelStorage storage, RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus) {
            this.worldName = worldName;
            this.tempFile = tempFile;
            this.levelStorageSource = storage;
            this.downloadStatus = downloadStatus;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.downloadStatus.bytesWritten = ((DownloadCountingOutputStream)((Object)e.getSource())).getByteCount();
            if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled && !FileDownload.this.error) {
                try {
                    FileDownload.this.extracting = true;
                    FileDownload.this.untarGzipArchive(this.worldName, this.tempFile, this.levelStorageSource);
                }
                catch (IOException iOException) {
                    LOGGER.error("Error extracting archive", (Throwable)iOException);
                    FileDownload.this.error = true;
                }
            }
        }
    }
}

