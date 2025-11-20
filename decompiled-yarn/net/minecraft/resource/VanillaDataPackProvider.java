package net.minecraft.resource;

import java.util.function.Consumer;

public class VanillaDataPackProvider implements ResourcePackProvider {
   private final DefaultResourcePack pack = new DefaultResourcePack("minecraft");

   public VanillaDataPackProvider() {
   }

   @Override
   public void register(Consumer<ResourcePackProfile> _snowman, ResourcePackProfile.Factory factory) {
      ResourcePackProfile _snowmanx = ResourcePackProfile.of(
         "vanilla", false, () -> this.pack, factory, ResourcePackProfile.InsertionPosition.BOTTOM, ResourcePackSource.PACK_SOURCE_BUILTIN
      );
      if (_snowmanx != null) {
         _snowman.accept(_snowmanx);
      }
   }
}
