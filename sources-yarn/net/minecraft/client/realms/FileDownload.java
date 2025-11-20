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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      CloseableHttpClient closeableHttpClient = null;
      HttpGet httpGet = null;

      long var5;
      try {
         httpGet = new HttpGet(downloadLink);
         closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
         CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
         return Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
      } catch (Throwable var16) {
         LOGGER.error("Unable to get content length for download");
         var5 = 0L;
      } finally {
         if (httpGet != null) {
            httpGet.releaseConnection();
         }

         if (closeableHttpClient != null) {
            try {
               closeableHttpClient.close();
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
            CloseableHttpClient closeableHttpClient = null;

            try {
               this.backupFile = File.createTempFile("backup", ".tar.gz");
               this.httpRequest = new HttpGet(download.downloadLink);
               closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
               HttpResponse httpResponse = closeableHttpClient.execute(this.httpRequest);
               status.totalBytes = Long.parseLong(httpResponse.getFirstHeader("Content-Length").getValue());
               if (httpResponse.getStatusLine().getStatusCode() == 200) {
                  OutputStream outputStream2 = new FileOutputStream(this.backupFile);
                  FileDownload.ProgressListener lv3 = new FileDownload.ProgressListener(message.trim(), this.backupFile, storage, status);
                  FileDownload.DownloadCountingOutputStream lv4 = new FileDownload.DownloadCountingOutputStream(outputStream2);
                  lv4.setListener(lv3);
                  IOUtils.copy(httpResponse.getEntity().getContent(), lv4);
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
                        HttpResponse httpResponse5 = closeableHttpClient.execute(this.httpRequest);
                        status.totalBytes = Long.parseLong(httpResponse5.getFirstHeader("Content-Length").getValue());
                        if (httpResponse5.getStatusLine().getStatusCode() != 200) {
                           this.error = true;
                           this.httpRequest.abort();
                           return;
                        }

                        OutputStream outputStream5 = new FileOutputStream(this.backupFile);
                        FileDownload.ResourcePackProgressListener lv9 = new FileDownload.ResourcePackProgressListener(this.backupFile, status, download);
                        FileDownload.DownloadCountingOutputStream lv10 = new FileDownload.DownloadCountingOutputStream(outputStream5);
                        lv10.setListener(lv9);
                        IOUtils.copy(httpResponse5.getEntity().getContent(), lv10);
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

               if (closeableHttpClient != null) {
                  try {
                     closeableHttpClient.close();
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

      for (String string2 : INVALID_FILE_NAMES) {
         if (folder.equalsIgnoreCase(string2)) {
            folder = "_" + folder + "_";
         }
      }

      return folder;
   }

   private void untarGzipArchive(String name, File archive, LevelStorage storage) throws IOException {
      Pattern pattern = Pattern.compile(".*-([0-9]+)$");
      int i = 1;

      for (char c : SharedConstants.INVALID_CHARS_LEVEL_NAME) {
         name = name.replace(c, '_');
      }

      if (StringUtils.isEmpty(name)) {
         name = "Realm";
      }

      name = findAvailableFolderName(name);

      try {
         for (LevelSummary lv : storage.getLevelList()) {
            if (lv.getName().toLowerCase(Locale.ROOT).startsWith(name.toLowerCase(Locale.ROOT))) {
               Matcher matcher = pattern.matcher(lv.getName());
               if (matcher.matches()) {
                  if (Integer.valueOf(matcher.group(1)) > i) {
                     i = Integer.valueOf(matcher.group(1));
                  }
               } else {
                  i++;
               }
            }
         }
      } catch (Exception var128) {
         LOGGER.error("Error getting level list", var128);
         this.error = true;
         return;
      }

      String string3;
      if (storage.isLevelNameValid(name) && i <= 1) {
         string3 = name;
      } else {
         string3 = name + (i == 1 ? "" : "-" + i);
         if (!storage.isLevelNameValid(string3)) {
            boolean bl = false;

            while (!bl) {
               i++;
               string3 = name + (i == 1 ? "" : "-" + i);
               if (storage.isLevelNameValid(string3)) {
                  bl = true;
               }
            }
         }
      }

      TarArchiveInputStream tarArchiveInputStream = null;
      File file2 = new File(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "saves");

      try {
         file2.mkdir();
         tarArchiveInputStream = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(archive))));

         for (TarArchiveEntry tarArchiveEntry = tarArchiveInputStream.getNextTarEntry();
            tarArchiveEntry != null;
            tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()
         ) {
            File file3 = new File(file2, tarArchiveEntry.getName().replace("world", string3));
            if (tarArchiveEntry.isDirectory()) {
               file3.mkdirs();
            } else {
               file3.createNewFile();

               try (FileOutputStream fileOutputStream = new FileOutputStream(file3)) {
                  IOUtils.copy(tarArchiveInputStream, fileOutputStream);
               }
            }
         }
      } catch (Exception var126) {
         LOGGER.error("Error extracting world", var126);
         this.error = true;
      } finally {
         if (tarArchiveInputStream != null) {
            tarArchiveInputStream.close();
         }

         if (archive != null) {
            archive.delete();
         }

         try (LevelStorage.Session lv4 = storage.createSession(string3)) {
            lv4.save(string3.trim());
            Path path3 = lv4.getDirectory(WorldSavePath.LEVEL_DAT);
            readNbtFile(path3.toFile());
         } catch (IOException var124) {
            LOGGER.error("Failed to rename unpacked realms level {}", string3, var124);
         }

         this.resourcePackPath = new File(file2, string3 + File.separator + "resources.zip");
      }
   }

   private static void readNbtFile(File file) {
      if (file.exists()) {
         try {
            CompoundTag lv = NbtIo.readCompressed(file);
            CompoundTag lv2 = lv.getCompound("Data");
            lv2.remove("Player");
            NbtIo.writeCompressed(lv, file);
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
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
               String string = Hashing.sha1().hashBytes(Files.toByteArray(this.tempFile)).toString();
               if (string.equals(this.worldDownload.resourcePackHash)) {
                  FileUtils.copyFile(this.tempFile, FileDownload.this.resourcePackPath);
                  FileDownload.this.finished = true;
               } else {
                  FileDownload.LOGGER
                     .error("Resourcepack had wrong hash (expected " + this.worldDownload.resourcePackHash + ", found " + string + "). Deleting it.");
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
