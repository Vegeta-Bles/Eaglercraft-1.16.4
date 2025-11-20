package net.minecraft.client.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.util.NetworkUtils;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackCompatibility;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ZipResourcePack;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientBuiltinResourcePackProvider implements ResourcePackProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Pattern ALPHANUMERAL = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final DefaultResourcePack pack;
   private final File serverPacksRoot;
   private final ReentrantLock lock = new ReentrantLock();
   private final ResourceIndex index;
   @Nullable
   private CompletableFuture<?> downloadTask;
   @Nullable
   private ResourcePackProfile serverContainer;

   public ClientBuiltinResourcePackProvider(File serverPacksRoot, ResourceIndex index) {
      this.serverPacksRoot = serverPacksRoot;
      this.index = index;
      this.pack = new DefaultClientResourcePack(index);
   }

   @Override
   public void register(Consumer<ResourcePackProfile> _snowman, ResourcePackProfile.Factory factory) {
      ResourcePackProfile _snowmanx = ResourcePackProfile.of(
         "vanilla", true, () -> this.pack, factory, ResourcePackProfile.InsertionPosition.BOTTOM, ResourcePackSource.PACK_SOURCE_BUILTIN
      );
      if (_snowmanx != null) {
         _snowman.accept(_snowmanx);
      }

      if (this.serverContainer != null) {
         _snowman.accept(this.serverContainer);
      }

      ResourcePackProfile _snowmanxx = this.method_25454(factory);
      if (_snowmanxx != null) {
         _snowman.accept(_snowmanxx);
      }
   }

   public DefaultResourcePack getPack() {
      return this.pack;
   }

   private static Map<String, String> getDownloadHeaders() {
      Map<String, String> _snowman = Maps.newHashMap();
      _snowman.put("X-Minecraft-Username", MinecraftClient.getInstance().getSession().getUsername());
      _snowman.put("X-Minecraft-UUID", MinecraftClient.getInstance().getSession().getUuid());
      _snowman.put("X-Minecraft-Version", SharedConstants.getGameVersion().getName());
      _snowman.put("X-Minecraft-Version-ID", SharedConstants.getGameVersion().getId());
      _snowman.put("X-Minecraft-Pack-Format", String.valueOf(SharedConstants.getGameVersion().getPackVersion()));
      _snowman.put("User-Agent", "Minecraft Java/" + SharedConstants.getGameVersion().getName());
      return _snowman;
   }

   public CompletableFuture<?> download(String _snowman, String _snowman) {
      String _snowmanxx = DigestUtils.sha1Hex(_snowman);
      String _snowmanxxx = ALPHANUMERAL.matcher(_snowman).matches() ? _snowman : "";
      this.lock.lock();

      CompletableFuture var13;
      try {
         this.clear();
         this.deleteOldServerPack();
         File _snowmanxxxx = new File(this.serverPacksRoot, _snowmanxx);
         CompletableFuture<?> _snowmanxxxxx;
         if (_snowmanxxxx.exists()) {
            _snowmanxxxxx = CompletableFuture.completedFuture("");
         } else {
            ProgressScreen _snowmanxxxxxx = new ProgressScreen();
            Map<String, String> _snowmanxxxxxxx = getDownloadHeaders();
            MinecraftClient _snowmanxxxxxxxx = MinecraftClient.getInstance();
            _snowmanxxxxxxxx.submitAndJoin(() -> _snowman.openScreen(_snowman));
            _snowmanxxxxx = NetworkUtils.download(_snowmanxxxx, _snowman, _snowmanxxxxxxx, 104857600, _snowmanxxxxxx, _snowmanxxxxxxxx.getNetworkProxy());
         }

         this.downloadTask = _snowmanxxxxx.<Void>thenCompose(
               _snowmanxxxxxx -> !this.verifyFile(_snowman, _snowman)
                     ? Util.completeExceptionally(new RuntimeException("Hash check failure for file " + _snowman + ", see log"))
                     : this.loadServerPack(_snowman, ResourcePackSource.PACK_SOURCE_SERVER)
            )
            .whenComplete((_snowmanxxxxxx, _snowmanxxxxxxx) -> {
               if (_snowmanxxxxxxx != null) {
                  LOGGER.warn("Pack application failed: {}, deleting file {}", _snowmanxxxxxxx.getMessage(), _snowman);
                  delete(_snowman);
               }
            });
         var13 = this.downloadTask;
      } finally {
         this.lock.unlock();
      }

      return var13;
   }

   private static void delete(File file) {
      try {
         Files.delete(file.toPath());
      } catch (IOException var2) {
         LOGGER.warn("Failed to delete file {}: {}", file, var2.getMessage());
      }
   }

   public void clear() {
      this.lock.lock();

      try {
         if (this.downloadTask != null) {
            this.downloadTask.cancel(true);
         }

         this.downloadTask = null;
         if (this.serverContainer != null) {
            this.serverContainer = null;
            MinecraftClient.getInstance().reloadResourcesConcurrently();
         }
      } finally {
         this.lock.unlock();
      }
   }

   private boolean verifyFile(String expectedSha1, File file) {
      try (FileInputStream _snowman = new FileInputStream(file)) {
         String _snowmanx = DigestUtils.sha1Hex(_snowman);
         if (expectedSha1.isEmpty()) {
            LOGGER.info("Found file {} without verification hash", file);
            return true;
         }

         if (_snowmanx.toLowerCase(Locale.ROOT).equals(expectedSha1.toLowerCase(Locale.ROOT))) {
            LOGGER.info("Found file {} matching requested hash {}", file, expectedSha1);
            return true;
         }

         LOGGER.warn("File {} had wrong hash (expected {}, found {}).", file, expectedSha1, _snowmanx);
      } catch (IOException var17) {
         LOGGER.warn("File {} couldn't be hashed.", file, var17);
      }

      return false;
   }

   private void deleteOldServerPack() {
      try {
         List<File> _snowman = Lists.newArrayList(FileUtils.listFiles(this.serverPacksRoot, TrueFileFilter.TRUE, null));
         _snowman.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         int _snowmanx = 0;

         for (File _snowmanxx : _snowman) {
            if (_snowmanx++ >= 10) {
               LOGGER.info("Deleting old server resource pack {}", _snowmanxx.getName());
               FileUtils.deleteQuietly(_snowmanxx);
            }
         }
      } catch (IllegalArgumentException var5) {
         LOGGER.error("Error while deleting old server resource pack : {}", var5.getMessage());
      }
   }

   public CompletableFuture<Void> loadServerPack(File packZip, ResourcePackSource _snowman) {
      PackResourceMetadata _snowmanx;
      try (ZipResourcePack _snowmanxx = new ZipResourcePack(packZip)) {
         _snowmanx = _snowmanxx.parseMetadata(PackResourceMetadata.READER);
      } catch (IOException var17) {
         return Util.completeExceptionally(new IOException(String.format("Invalid resourcepack at %s", packZip), var17));
      }

      LOGGER.info("Applying server pack {}", packZip);
      this.serverContainer = new ResourcePackProfile(
         "server",
         true,
         () -> new ZipResourcePack(packZip),
         new TranslatableText("resourcePack.server.name"),
         _snowmanx.getDescription(),
         ResourcePackCompatibility.from(_snowmanx.getPackFormat()),
         ResourcePackProfile.InsertionPosition.TOP,
         true,
         _snowman
      );
      return MinecraftClient.getInstance().reloadResourcesConcurrently();
   }

   @Nullable
   private ResourcePackProfile method_25454(ResourcePackProfile.Factory _snowman) {
      ResourcePackProfile _snowmanx = null;
      File _snowmanxx = this.index.getResource(new Identifier("resourcepacks/programmer_art.zip"));
      if (_snowmanxx != null && _snowmanxx.isFile()) {
         _snowmanx = method_25453(_snowman, () -> method_16048(_snowman));
      }

      if (_snowmanx == null && SharedConstants.isDevelopment) {
         File _snowmanxxx = this.index.findFile("../resourcepacks/programmer_art");
         if (_snowmanxxx != null && _snowmanxxx.isDirectory()) {
            _snowmanx = method_25453(_snowman, () -> method_25455(_snowman));
         }
      }

      return _snowmanx;
   }

   @Nullable
   private static ResourcePackProfile method_25453(ResourcePackProfile.Factory _snowman, Supplier<ResourcePack> _snowman) {
      return ResourcePackProfile.of("programer_art", false, _snowman, _snowman, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.PACK_SOURCE_BUILTIN);
   }

   private static DirectoryResourcePack method_25455(File _snowman) {
      return new DirectoryResourcePack(_snowman) {
         @Override
         public String getName() {
            return "Programmer Art";
         }
      };
   }

   private static ResourcePack method_16048(File _snowman) {
      return new ZipResourcePack(_snowman) {
         @Override
         public String getName() {
            return "Programmer Art";
         }
      };
   }
}
