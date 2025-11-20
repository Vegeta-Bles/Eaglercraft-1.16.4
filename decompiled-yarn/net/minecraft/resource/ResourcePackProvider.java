package net.minecraft.resource;

import java.util.function.Consumer;

public interface ResourcePackProvider {
   void register(Consumer<ResourcePackProfile> var1, ResourcePackProfile.Factory factory);
}
