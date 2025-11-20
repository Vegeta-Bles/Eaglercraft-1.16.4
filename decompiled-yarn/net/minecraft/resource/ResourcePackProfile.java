package net.minecraft.resource;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackProfile implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final PackResourceMetadata BROKEN_PACK_META = new PackResourceMetadata(
      new TranslatableText("resourcePack.broken_assets").formatted(new Formatting[]{Formatting.RED, Formatting.ITALIC}),
      SharedConstants.getGameVersion().getPackVersion()
   );
   private final String name;
   private final Supplier<ResourcePack> packGetter;
   private final Text displayName;
   private final Text description;
   private final ResourcePackCompatibility compatibility;
   private final ResourcePackProfile.InsertionPosition position;
   private final boolean alwaysEnabled;
   private final boolean pinned;
   private final ResourcePackSource source;

   @Nullable
   public static ResourcePackProfile of(
      String name,
      boolean alwaysEnabled,
      Supplier<ResourcePack> packFactory,
      ResourcePackProfile.Factory containerFactory,
      ResourcePackProfile.InsertionPosition insertionPosition,
      ResourcePackSource _snowman
   ) {
      try (ResourcePack _snowmanx = packFactory.get()) {
         PackResourceMetadata _snowmanxx = _snowmanx.parseMetadata(PackResourceMetadata.READER);
         if (alwaysEnabled && _snowmanxx == null) {
            LOGGER.error(
               "Broken/missing pack.mcmeta detected, fudging it into existance. Please check that your launcher has downloaded all assets for the game correctly!"
            );
            _snowmanxx = BROKEN_PACK_META;
         }

         if (_snowmanxx != null) {
            return containerFactory.create(name, alwaysEnabled, packFactory, _snowmanx, _snowmanxx, insertionPosition, _snowman);
         }

         LOGGER.warn("Couldn't find pack meta for pack {}", name);
      } catch (IOException var22) {
         LOGGER.warn("Couldn't get pack info for: {}", var22.toString());
      }

      return null;
   }

   public ResourcePackProfile(
      String name,
      boolean alwaysEnabled,
      Supplier<ResourcePack> packFactory,
      Text displayName,
      Text description,
      ResourcePackCompatibility compatibility,
      ResourcePackProfile.InsertionPosition direction,
      boolean pinned,
      ResourcePackSource source
   ) {
      this.name = name;
      this.packGetter = packFactory;
      this.displayName = displayName;
      this.description = description;
      this.compatibility = compatibility;
      this.alwaysEnabled = alwaysEnabled;
      this.position = direction;
      this.pinned = pinned;
      this.source = source;
   }

   public ResourcePackProfile(
      String name,
      boolean alwaysEnabled,
      Supplier<ResourcePack> packFactory,
      ResourcePack pack,
      PackResourceMetadata metadata,
      ResourcePackProfile.InsertionPosition direction,
      ResourcePackSource source
   ) {
      this(
         name,
         alwaysEnabled,
         packFactory,
         new LiteralText(pack.getName()),
         metadata.getDescription(),
         ResourcePackCompatibility.from(metadata.getPackFormat()),
         direction,
         false,
         source
      );
   }

   public Text getDisplayName() {
      return this.displayName;
   }

   public Text getDescription() {
      return this.description;
   }

   public Text getInformationText(boolean enabled) {
      return Texts.bracketed(this.source.decorate(new LiteralText(this.name)))
         .styled(
            _snowmanx -> _snowmanx.withColor(enabled ? Formatting.GREEN : Formatting.RED)
                  .withInsertion(StringArgumentType.escapeIfRequired(this.name))
                  .withHoverEvent(
                     new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("").append(this.displayName).append("\n").append(this.description))
                  )
         );
   }

   public ResourcePackCompatibility getCompatibility() {
      return this.compatibility;
   }

   public ResourcePack createResourcePack() {
      return this.packGetter.get();
   }

   public String getName() {
      return this.name;
   }

   public boolean isAlwaysEnabled() {
      return this.alwaysEnabled;
   }

   public boolean isPinned() {
      return this.pinned;
   }

   public ResourcePackProfile.InsertionPosition getInitialPosition() {
      return this.position;
   }

   public ResourcePackSource getSource() {
      return this.source;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ResourcePackProfile)) {
         return false;
      } else {
         ResourcePackProfile _snowman = (ResourcePackProfile)o;
         return this.name.equals(_snowman.name);
      }
   }

   @Override
   public int hashCode() {
      return this.name.hashCode();
   }

   @Override
   public void close() {
   }

   @FunctionalInterface
   public interface Factory {
      @Nullable
      ResourcePackProfile create(
         String name,
         boolean alwaysEnabled,
         Supplier<ResourcePack> packFactory,
         ResourcePack pack,
         PackResourceMetadata metadata,
         ResourcePackProfile.InsertionPosition initialPosition,
         ResourcePackSource source
      );
   }

   public static enum InsertionPosition {
      TOP,
      BOTTOM;

      private InsertionPosition() {
      }

      public <T> int insert(List<T> items, T item, Function<T, ResourcePackProfile> profileGetter, boolean listInverted) {
         ResourcePackProfile.InsertionPosition _snowman = listInverted ? this.inverse() : this;
         if (_snowman == BOTTOM) {
            int _snowmanx;
            for (_snowmanx = 0; _snowmanx < items.size(); _snowmanx++) {
               ResourcePackProfile _snowmanxx = profileGetter.apply(items.get(_snowmanx));
               if (!_snowmanxx.isPinned() || _snowmanxx.getInitialPosition() != this) {
                  break;
               }
            }

            items.add(_snowmanx, item);
            return _snowmanx;
         } else {
            int _snowmanx;
            for (_snowmanx = items.size() - 1; _snowmanx >= 0; _snowmanx--) {
               ResourcePackProfile _snowmanxx = profileGetter.apply(items.get(_snowmanx));
               if (!_snowmanxx.isPinned() || _snowmanxx.getInitialPosition() != this) {
                  break;
               }
            }

            items.add(_snowmanx + 1, item);
            return _snowmanx + 1;
         }
      }

      public ResourcePackProfile.InsertionPosition inverse() {
         return this == TOP ? BOTTOM : TOP;
      }
   }
}
