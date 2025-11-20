package net.minecraft.resource;

import java.util.function.Consumer;

public class VanillaDataPackProvider implements ResourcePackProvider {
   private final DefaultResourcePack pack = new DefaultResourcePack("minecraft");

   public VanillaDataPackProvider() {
   }

   @Override
   public void register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory) {
      ResourcePackProfile lv = ResourcePackProfile.of(
         "vanilla", false, () -> this.pack, factory, ResourcePackProfile.InsertionPosition.BOTTOM, ResourcePackSource.PACK_SOURCE_BUILTIN
      );
      if (lv != null) {
         consumer.accept(lv);
      }
   }
}
