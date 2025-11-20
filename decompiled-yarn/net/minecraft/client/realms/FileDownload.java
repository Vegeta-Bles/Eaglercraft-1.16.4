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
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
   private static final String[] INVALID_FILE_NAMES = new String[]{
      "CON",
      "COM",
      "PRN",
      "AUX",
      "CLOCK$",
      "NUL",
      "COM1",
      "COM2",
      "COM3",
      "COM4",
      "COM5",
      "COM6",
      "COM7",
      "COM8",
      "COM9",
      "LPT1",
      "LPT2",
      "LPT3",
      "LPT4",
      "LPT5",
      "LPT6",
      "LPT7",
      "LPT8",
      "LPT9"
   };

   public FileDownload() {
   }

   public long contentLength(String downloadLink) {
      CloseableHttpClient _snowman = null;
      HttpGet _snowmanx = null;

      long var5;
      try {
         _snowmanx = new HttpGet(downloadLink);
         _snowman = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
         CloseableHttpResponse _snowmanxx = _snowman.execute(_snowmanx);
         return Long.parseLong(_snowmanxx.getFirstHeader("Content-Length").getValue());
      } catch (Throwable var16) {
         LOGGER.error("Unable to get content length for download");
         var5 = 0L;
      } finally {
         if (_snowmanx != null) {
            _snowmanx.releaseConnection();
         }

         if (_snowman != null) {
            try {
               _snowman.close();
            } catch (IOException var15) {
               LOGGER.error("Could not close http client", var15);
            }
         }
      }

      return var5;
   }

   public void downloadWorld(WorldDownload download, String message, RealmsDownloadLatestWorldScreen.DownloadStatus status, LevelStorage storage) {
      if (this.currentThread == null) {
         this.currentThread = new Thread(() -> {
            CloseableHttpClient _snowmanxxxx = null;

            try {
               this.backupFile = File.createTempFile("backup", ".tar.gz");
               this.httpRequest = new HttpGet(download.downloadLink);
               _snowmanxxxx = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
               HttpResponse _snowmanx = _snowmanxxxx.execute(this.httpRequest);
               status.totalBytes = Long.parseLong(_snowmanx.getFirstHeader("Content-Length").getValue());
               if (_snowmanx.getStatusLine().getStatusCode() == 200) {
                  OutputStream _snowmanxx = new FileOutputStream(this.backupFile);
                  FileDownload.ProgressListener _snowmanxxx = new FileDownload.ProgressListener(message.trim(), this.backupFile, storage, status);
                  FileDownload.DownloadCountingOutputStream _snowmanxxxx = new FileDownload.DownloadCountingOutputStream(_snowmanxx);
                  _snowmanxxxx.setListener(_snowmanxxx);
                  IOUtils.copy(_snowmanx.getEntity().getContent(), _snowmanxxxx);
                  return;
               }

               this.error = true;
               this.httpRequest.abort();
            } catch (Exception var93) {
               LOGGER.error("Caught exception while downloading: " + var93.getMessage());
               this.error = true;
               return;
            } finally {
               this.httpRequest.releaseConnection();
               if (this.backupFile != null) {
                  this.backupFile.delete();
               }

               if (!this.error) {
                  if (!download.resourcePackUrl.isEmpty() && !download.resourcePackHash.isEmpty()) {
                     try {
                        this.backupFile = File.createTempFile("resources", ".tar.gz");
                        this.httpRequest = new HttpGet(download.resourcePackUrl);
                        HttpResponse _snowmanx = _snowmanxxxx.execute(this.httpRequest);
                        status.totalBytes = Long.parseLong(_snowmanx.getFirstHeader("Content-Length").getValue());
                        if (_snowmanx.getStatusLine().getStatusCode() != 200) {
                           this.error = true;
                           this.httpRequest.abort();
                           return;
                        }

                        OutputStream _snowmanxx = new FileOutputStream(this.backupFile);
                        FileDownload.ResourcePackProgressListener _snowmanxxx = new FileDownload.ResourcePackProgressListener(this.backupFile, status, download);
                        FileDownload.DownloadCountingOutputStream _snowmanxxxx = new FileDownload.DownloadCountingOutputStream(_snowmanxx);
                        _snowmanxxxx.setListener(_snowmanxxx);
                        IOUtils.copy(_snowmanx.getEntity().getContent(), _snowmanxxxx);
                     } catch (Exception var91) {
                        LOGGER.error("Caught exception while downloading: " + var91.getMessage());
                        this.error = true;
                     } finally {
                        this.httpRequest.releaseConnection();
                        if (this.backupFile != null) {
                           this.backupFile.delete();
                        }
                     }
                  } else {
                     this.finished = true;
                  }
               }

               if (_snowmanxxxx != null) {
                  try {
                     _snowmanxxxx.close();
                  } catch (IOException var90) {
                     LOGGER.error("Failed to close Realms download client");
                  }
               }
            }
         });
         this.currentThread.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(LOGGER));
         this.currentThread.start();
      }
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

      for (String _snowman : INVALID_FILE_NAMES) {
         if (folder.equalsIgnoreCase(_snowman)) {
            folder = "_" + folder + "_";
         }
      }

      return folder;
   }

   private void untarGzipArchive(String name, File archive, LevelStorage storage) throws IOException {
      Pattern _snowman = Pattern.compile(".*-([0-9]+)$");
      int _snowmanx = 1;

      for (char _snowmanxx : SharedConstants.INVALID_CHARS_LEVEL_NAME) {
         name = name.replace(_snowmanxx, '_');
      }

      if (StringUtils.isEmpty(name)) {
         name = "Realm";
      }

      name = findAvailableFolderName(name);

      try {
         for (LevelSummary _snowmanxx : storage.getLevelList()) {
            if (_snowmanxx.getName().toLowerCase(Locale.ROOT).startsWith(name.toLowerCase(Locale.ROOT))) {
               Matcher _snowmanxxx = _snowman.matcher(_snowmanxx.getName());
               if (_snowmanxxx.matches()) {
                  if (Integer.valueOf(_snowmanxxx.group(1)) > _snowmanx) {
                     _snowmanx = Integer.valueOf(_snowmanxxx.group(1));
                  }
               } else {
                  _snowmanx++;
               }
            }
         }
      } catch (Exception var128) {
         LOGGER.error("Error getting level list", var128);
         this.error = true;
         return;
      }

      String _snowmanxxx;
      if (storage.isLevelNameValid(name) && _snowmanx <= 1) {
         _snowmanxxx = name;
      } else {
         _snowmanxxx = name + (_snowmanx == 1 ? "" : "-" + _snowmanx);
         if (!storage.isLevelNameValid(_snowmanxxx)) {
            boolean _snowmanxxxx = false;

            while (!_snowmanxxxx) {
               _snowmanx++;
               _snowmanxxx = name + (_snowmanx == 1 ? "" : "-" + _snowmanx);
               if (storage.isLevelNameValid(_snowmanxxx)) {
                  _snowmanxxxx = true;
               }
            }
         }
      }

      TarArchiveInputStream _snowmanxxxx = null;
      File _snowmanxxxxx = new File(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "saves");

      try {
         _snowmanxxxxx.mkdir();
         _snowmanxxxx = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(archive))));

         for (TarArchiveEntry _snowmanxxxxxx = _snowmanxxxx.getNextTarEntry(); _snowmanxxxxxx != null; _snowmanxxxxxx = _snowmanxxxx.getNextTarEntry()) {
            File _snowmanxxxxxxx = new File(_snowmanxxxxx, _snowmanxxxxxx.getName().replace("world", _snowmanxxx));
            if (_snowmanxxxxxx.isDirectory()) {
               _snowmanxxxxxxx.mkdirs();
            } else {
               _snowmanxxxxxxx.createNewFile();

               try (FileOutputStream _snowmanxxxxxxxx = new FileOutputStream(_snowmanxxxxxxx)) {
                  IOUtils.copy(_snowmanxxxx, _snowmanxxxxxxxx);
               }
            }
         }
      } catch (Exception var126) {
         LOGGER.error("Error extracting world", var126);
         this.error = true;
      } finally {
         if (_snowmanxxxx != null) {
            _snowmanxxxx.close();
         }

         if (archive != null) {
            archive.delete();
         }

         try (LevelStorage.Session _snowmanxxxxxxx = storage.createSession(_snowmanxxx)) {
            _snowmanxxxxxxx.save(_snowmanxxx.trim());
            Path _snowmanxxxxxxxx = _snowmanxxxxxxx.getDirectory(WorldSavePath.LEVEL_DAT);
            readNbtFile(_snowmanxxxxxxxx.toFile());
         } catch (IOException var124) {
            LOGGER.error("Failed to rename unpacked realms level {}", _snowmanxxx, var124);
         }

         this.resourcePackPath = new File(_snowmanxxxxx, _snowmanxxx + File.separator + "resources.zip");
      }
   }

   private static void readNbtFile(File file) {
      if (file.exists()) {
         try {
            CompoundTag _snowman = NbtIo.readCompressed(file);
            CompoundTag _snowmanx = _snowman.getCompound("Data");
            _snowmanx.remove("Player");
            NbtIo.writeCompressed(_snowman, file);
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   class DownloadCountingOutputStream extends CountingOutputStream {
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
            this.listener.actionPerformed(new ActionEvent(this, 0, null));
         }
      }
   }

   class ProgressListener implements ActionListener {
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
         this.downloadStatus.bytesWritten = ((FileDownload.DownloadCountingOutputStream)e.getSource()).getByteCount();
         if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled && !FileDownload.this.error) {
            try {
               FileDownload.this.extracting = true;
               FileDownload.this.untarGzipArchive(this.worldName, this.tempFile, this.levelStorageSource);
            } catch (IOException var3) {
               FileDownload.LOGGER.error("Error extracting archive", var3);
               FileDownload.this.error = true;
            }
         }
      }
   }

   class ResourcePackProgressListener implements ActionListener {
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
         this.downloadStatus.bytesWritten = ((FileDownload.DownloadCountingOutputStream)e.getSource()).getByteCount();
         if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled) {
            try {
               String _snowman = Hashing.sha1().hashBytes(Files.toByteArray(this.tempFile)).toString();
               if (_snowman.equals(this.worldDownload.resourcePackHash)) {
                  FileUtils.copyFile(this.tempFile, FileDownload.this.resourcePackPath);
                  FileDownload.this.finished = true;
               } else {
                  FileDownload.LOGGER
                     .error("Resourcepack had wrong hash (expected " + this.worldDownload.resourcePackHash + ", found " + _snowman + "). Deleting it.");
                  FileUtils.deleteQuietly(this.tempFile);
                  FileDownload.this.error = true;
               }
            } catch (IOException var3) {
               FileDownload.LOGGER.error("Error copying resourcepack file", var3.getMessage());
               FileDownload.this.error = true;
            }
         }
      }
   }
}
