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

   public PackScreen(Screen parent, ResourcePackManager packManager, Consumer<ResourcePackManager> _snowman, File file, Text title) {
      super(title);
      this.parent = parent;
      this.organizer = new ResourcePackOrganizer(this::updatePackLists, this::method_30287, packManager, _snowman);
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
      this.doneButton = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height - 48, 150, 20, ScreenTexts.DONE, _snowman -> this.onClose()));
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 154,
            this.height - 48,
            150,
            20,
            new TranslatableText("pack.openFolder"),
            _snowman -> Util.getOperatingSystem().open(this.file),
            (_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> this.renderTooltip(_snowmanx, FOLDER_INFO, _snowmanxx, _snowmanxxx)
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
      packs.forEach(_snowmanx -> widget.children().add(new PackListWidget.ResourcePackEntry(this.client, widget, this, _snowmanx)));
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

   protected static void method_29669(MinecraftClient _snowman, List<Path> _snowman, Path _snowman) {
      MutableBoolean _snowmanxxx = new MutableBoolean();
      _snowman.forEach(_snowmanxxxxx -> {
         try (Stream<Path> _snowmanxxx = Files.walk(_snowmanxxxxx)) {
            _snowmanxxx.forEach(_snowmanxxxxxxxx -> {
               try {
                  Util.relativeCopy(_snowmanxxxxx.getParent(), _snowman, _snowmanxxxxxxxx);
               } catch (IOException var5) {
                  LOGGER.warn("Failed to copy datapack file  from {} to {}", _snowmanxxxxxxxx, _snowman, var5);
                  _snowman.setTrue();
               }
            });
         } catch (IOException var16) {
            LOGGER.warn("Failed to copy datapack file from {} to {}", _snowmanxxxxx, _snowman);
            _snowman.setTrue();
         }
      });
      if (_snowmanxxx.isTrue()) {
         SystemToast.addPackCopyFailure(_snowman, _snowman.toString());
      }
   }

   @Override
   public void filesDragged(List<Path> paths) {
      String _snowman = paths.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
      this.client.openScreen(new ConfirmScreen(_snowmanx -> {
         if (_snowmanx) {
            method_29669(this.client, paths, this.file.toPath());
            this.refresh();
         }

         this.client.openScreen(this);
      }, new TranslatableText("pack.dropConfirm"), new LiteralText(_snowman)));
   }

   private Identifier method_30289(TextureManager _snowman, ResourcePackProfile _snowman) {
      try (
         ResourcePack _snowmanxx = _snowman.createResourcePack();
         InputStream _snowmanxxx = _snowmanxx.openRoot("pack.png");
      ) {
         String _snowmanxxxx = _snowman.getName();
         Identifier _snowmanxxxxx = new Identifier(
            "minecraft", "pack/" + Util.replaceInvalidChars(_snowmanxxxx, Identifier::isPathCharacterValid) + "/" + Hashing.sha1().hashUnencodedChars(_snowmanxxxx) + "/icon"
         );
         NativeImage _snowmanxxxxxx = NativeImage.read(_snowmanxxx);
         _snowman.registerTexture(_snowmanxxxxx, new NativeImageBackedTexture(_snowmanxxxxxx));
         return _snowmanxxxxx;
      } catch (FileNotFoundException var41) {
      } catch (Exception var42) {
         LOGGER.warn("Failed to load icon from pack {}", _snowman.getName(), var42);
      }

      return UNKNOWN_PACK;
   }

   private Identifier method_30287(ResourcePackProfile _snowman) {
      return this.field_25789.computeIfAbsent(_snowman.getName(), _snowmanx -> this.method_30289(this.client.getTextureManager(), _snowman));
   }

   static class DirectoryWatcher implements AutoCloseable {
      private final WatchService watchService;
      private final Path path;

      public DirectoryWatcher(File _snowman) throws IOException {
         this.path = _snowman.toPath();
         this.watchService = this.path.getFileSystem().newWatchService();

         try {
            this.watchDirectory(this.path);

            try (DirectoryStream<Path> _snowmanx = Files.newDirectoryStream(this.path)) {
               for (Path _snowmanxx : _snowmanx) {
                  if (Files.isDirectory(_snowmanxx, LinkOption.NOFOLLOW_LINKS)) {
                     this.watchDirectory(_snowmanxx);
                  }
               }
            }
         } catch (Exception var16) {
            this.watchService.close();
            throw var16;
         }
      }

      @Nullable
      public static PackScreen.DirectoryWatcher create(File _snowman) {
         try {
            return new PackScreen.DirectoryWatcher(_snowman);
         } catch (IOException var2) {
            PackScreen.LOGGER.warn("Failed to initialize pack directory {} monitoring", _snowman, var2);
            return null;
         }
      }

      private void watchDirectory(Path _snowman) throws IOException {
         _snowman.register(this.watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
      }

      public boolean pollForChange() throws IOException {
         boolean _snowman = false;

         WatchKey _snowmanx;
         while ((_snowmanx = this.watchService.poll()) != null) {
            for (WatchEvent<?> _snowmanxx : _snowmanx.pollEvents()) {
               _snowman = true;
               if (_snowmanx.watchable() == this.path && _snowmanxx.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  Path _snowmanxxx = this.path.resolve((Path)_snowmanxx.context());
                  if (Files.isDirectory(_snowmanxxx, LinkOption.NOFOLLOW_LINKS)) {
                     this.watchDirectory(_snowmanxxx);
                  }
               }
            }

            _snowmanx.reset();
         }

         return _snowman;
      }

      @Override
      public void close() throws IOException {
         this.watchService.close();
      }
   }
}
