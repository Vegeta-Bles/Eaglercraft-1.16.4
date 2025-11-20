package net.minecraft.client.gui.screen.pack;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class PackScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Text DROP_INFO = new TranslatableText("pack.dropInfo").formatted(Formatting.GRAY);
   private static final Text FOLDER_INFO = new TranslatableText("pack.folderInfo");
   private static final Identifier UNKNOWN_PACK = new Identifier("textures/misc/unknown_pack.png");
   private final ResourcePackOrganizer organizer;
   private final Screen parent;
   @Nullable
   private PackScreen.DirectoryWatcher directoryWatcher;
   private long field_25788;
   private PackListWidget availablePackList;
   private PackListWidget selectedPackList;
   private final File file;
   private ButtonWidget doneButton;
   private final Map<String, Identifier> field_25789 = Maps.newHashMap();

   public PackScreen(Screen parent, ResourcePackManager packManager, Consumer<ResourcePackManager> consumer, File file, Text title) {
      super(title);
      this.parent = parent;
      this.organizer = new ResourcePackOrganizer(this::updatePackLists, this::method_30287, packManager, consumer);
      this.file = file;
      this.directoryWatcher = PackScreen.DirectoryWatcher.create(file);
   }

   @Override
   public void onClose() {
      this.organizer.apply();
      this.client.openScreen(this.parent);
      this.closeDirectoryWatcher();
   }

   private void closeDirectoryWatcher() {
      if (this.directoryWatcher != null) {
         try {
            this.directoryWatcher.close();
            this.directoryWatcher = null;
         } catch (Exception var2) {
         }
      }
   }

   @Override
   protected void init() {
      this.doneButton = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height - 48, 150, 20, ScreenTexts.DONE, arg -> this.onClose()));
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 154,
            this.height - 48,
            150,
            20,
            new TranslatableText("pack.openFolder"),
            arg -> Util.getOperatingSystem().open(this.file),
            (arg, arg2, i, j) -> this.renderTooltip(arg2, FOLDER_INFO, i, j)
         )
      );
      this.availablePackList = new PackListWidget(this.client, 200, this.height, new TranslatableText("pack.available.title"));
      this.availablePackList.setLeftPos(this.width / 2 - 4 - 200);
      this.children.add(this.availablePackList);
      this.selectedPackList = new PackListWidget(this.client, 200, this.height, new TranslatableText("pack.selected.title"));
      this.selectedPackList.setLeftPos(this.width / 2 + 4);
      this.children.add(this.selectedPackList);
      this.refresh();
   }

   @Override
   public void tick() {
      if (this.directoryWatcher != null) {
         try {
            if (this.directoryWatcher.pollForChange()) {
               this.field_25788 = 20L;
            }
         } catch (IOException var2) {
            LOGGER.warn("Failed to poll for directory {} changes, stopping", this.file);
            this.closeDirectoryWatcher();
         }
      }

      if (this.field_25788 > 0L && --this.field_25788 == 0L) {
         this.refresh();
      }
   }

   private void updatePackLists() {
      this.updatePackList(this.selectedPackList, this.organizer.getEnabledPacks());
      this.updatePackList(this.availablePackList, this.organizer.getDisabledPacks());
      this.doneButton.active = !this.selectedPackList.children().isEmpty();
   }

   private void updatePackList(PackListWidget widget, Stream<ResourcePackOrganizer.Pack> packs) {
      widget.children().clear();
      packs.forEach(arg2 -> widget.children().add(new PackListWidget.ResourcePackEntry(this.client, widget, this, arg2)));
   }

   private void refresh() {
      this.organizer.refresh();
      this.updatePackLists();
      this.field_25788 = 0L;
      this.field_25789.clear();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackgroundTexture(0);
      this.availablePackList.render(matrices, mouseX, mouseY, delta);
      this.selectedPackList.render(matrices, mouseX, mouseY, delta);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
      drawCenteredText(matrices, this.textRenderer, DROP_INFO, this.width / 2, 20, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }

   protected static void method_29669(MinecraftClient arg, List<Path> list, Path path) {
      MutableBoolean mutableBoolean = new MutableBoolean();
      list.forEach(path2 -> {
         try (Stream<Path> stream = Files.walk(path2)) {
            stream.forEach(path3 -> {
               try {
                  Util.relativeCopy(path2.getParent(), path, path3);
               } catch (IOException var5) {
                  LOGGER.warn("Failed to copy datapack file  from {} to {}", path3, path, var5);
                  mutableBoolean.setTrue();
               }
            });
         } catch (IOException var16) {
            LOGGER.warn("Failed to copy datapack file from {} to {}", path2, path);
            mutableBoolean.setTrue();
         }
      });
      if (mutableBoolean.isTrue()) {
         SystemToast.addPackCopyFailure(arg, path.toString());
      }
   }

   @Override
   public void filesDragged(List<Path> paths) {
      String string = paths.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
      this.client.openScreen(new ConfirmScreen(bl -> {
         if (bl) {
            method_29669(this.client, paths, this.file.toPath());
            this.refresh();
         }

         this.client.openScreen(this);
      }, new TranslatableText("pack.dropConfirm"), new LiteralText(string)));
   }

   private Identifier method_30289(TextureManager arg, ResourcePackProfile arg2) {
      try (
         ResourcePack lv = arg2.createResourcePack();
         InputStream inputStream = lv.openRoot("pack.png");
      ) {
         String string = arg2.getName();
         Identifier lv2 = new Identifier(
            "minecraft",
            "pack/" + Util.replaceInvalidChars(string, Identifier::isPathCharacterValid) + "/" + Hashing.sha1().hashUnencodedChars(string) + "/icon"
         );
         NativeImage lv3 = NativeImage.read(inputStream);
         arg.registerTexture(lv2, new NativeImageBackedTexture(lv3));
         return lv2;
      } catch (FileNotFoundException var41) {
      } catch (Exception var42) {
         LOGGER.warn("Failed to load icon from pack {}", arg2.getName(), var42);
      }

      return UNKNOWN_PACK;
   }

   private Identifier method_30287(ResourcePackProfile arg) {
      return this.field_25789.computeIfAbsent(arg.getName(), string -> this.method_30289(this.client.getTextureManager(), arg));
   }

   @Environment(EnvType.CLIENT)
   static class DirectoryWatcher implements AutoCloseable {
      private final WatchService watchService;
      private final Path path;

      public DirectoryWatcher(File file) throws IOException {
         this.path = file.toPath();
         this.watchService = this.path.getFileSystem().newWatchService();

         try {
            this.watchDirectory(this.path);

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(this.path)) {
               for (Path path : directoryStream) {
                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                     this.watchDirectory(path);
                  }
               }
            }
         } catch (Exception var16) {
            this.watchService.close();
            throw var16;
         }
      }

      @Nullable
      public static PackScreen.DirectoryWatcher create(File file) {
         try {
            return new PackScreen.DirectoryWatcher(file);
         } catch (IOException var2) {
            PackScreen.LOGGER.warn("Failed to initialize pack directory {} monitoring", file, var2);
            return null;
         }
      }

      private void watchDirectory(Path path) throws IOException {
         path.register(this.watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
      }

      public boolean pollForChange() throws IOException {
         boolean bl = false;

         WatchKey watchKey;
         while ((watchKey = this.watchService.poll()) != null) {
            for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
               bl = true;
               if (watchKey.watchable() == this.path && watchEvent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  Path path = this.path.resolve((Path)watchEvent.context());
                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                     this.watchDirectory(path);
                  }
               }
            }

            watchKey.reset();
         }

         return bl;
      }

      @Override
      public void close() throws IOException {
         this.watchService.close();
      }
   }
}
