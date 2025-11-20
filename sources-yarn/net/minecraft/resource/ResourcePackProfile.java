package net.minecraft.resource;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      ResourcePackSource arg3
   ) {
      try (ResourcePack lv = packFactory.get()) {
         PackResourceMetadata lv2 = lv.parseMetadata(PackResourceMetadata.READER);
         if (alwaysEnabled && lv2 == null) {
            LOGGER.error(
               "Broken/missing pack.mcmeta detected, fudging it into existance. Please check that your launcher has downloaded all assets for the game correctly!"
            );
            lv2 = BROKEN_PACK_META;
         }

         if (lv2 != null) {
            return containerFactory.create(name, alwaysEnabled, packFactory, lv, lv2, insertionPosition, arg3);
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

   @Environment(EnvType.CLIENT)
   public Text getDisplayName() {
      return this.displayName;
   }

   @Environment(EnvType.CLIENT)
   public Text getDescription() {
      return this.description;
   }

   public Text getInformationText(boolean enabled) {
      return Texts.bracketed(this.source.decorate(new LiteralText(this.name)))
         .styled(
            arg -> arg.withColor(enabled ? Formatting.GREEN : Formatting.RED)
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

   @Environment(EnvType.CLIENT)
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
         ResourcePackProfile lv = (ResourcePackProfile)o;
         return this.name.equals(lv.name);
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
         ResourcePackProfile.InsertionPosition lv = listInverted ? this.inverse() : this;
         if (lv == BOTTOM) {
            int i;
            for (i = 0; i < items.size(); i++) {
               ResourcePackProfile lv2 = profileGetter.apply(items.get(i));
               if (!lv2.isPinned() || lv2.getInitialPosition() != this) {
                  break;
               }
            }

            items.add(i, item);
            return i;
         } else {
            int j;
            for (j = items.size() - 1; j >= 0; j--) {
               ResourcePackProfile lv3 = profileGetter.apply(items.get(j));
               if (!lv3.isPinned() || lv3.getInitialPosition() != this) {
                  break;
               }
            }

            items.add(j + 1, item);
            return j + 1;
         }
      }

      public ResourcePackProfile.InsertionPosition inverse() {
         return this == TOP ? BOTTOM : TOP;
      }
   }
}
