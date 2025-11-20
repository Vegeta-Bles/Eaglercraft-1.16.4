package net.minecraft.client.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class DefaultClientResourcePack extends DefaultResourcePack {
   private final ResourceIndex index;

   public DefaultClientResourcePack(ResourceIndex _snowman) {
      super("minecraft", "realms");
      this.index = _snowman;
   }

   @Nullable
   @Override
   protected InputStream findInputStream(ResourceType type, Identifier id) {
      if (type == ResourceType.CLIENT_RESOURCES) {
         File _snowman = this.index.getResource(id);
         if (_snowman != null && _snowman.exists()) {
            try {
               return new FileInputStream(_snowman);
            } catch (FileNotFoundException var5) {
            }
         }
      }

      return super.findInputStream(type, id);
   }

   @Override
   public boolean contains(ResourceType type, Identifier id) {
      if (type == ResourceType.CLIENT_RESOURCES) {
         File _snowman = this.index.getResource(id);
         if (_snowman != null && _snowman.exists()) {
            return true;
         }
      }

      return super.contains(type, id);
   }

   @Nullable
   @Override
   protected InputStream getInputStream(String path) {
      File _snowman = this.index.findFile(path);
      if (_snowman != null && _snowman.exists()) {
         try {
            return new FileInputStream(_snowman);
         } catch (FileNotFoundException var4) {
         }
      }

      return super.getInputStream(path);
   }

   @Override
   public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
      Collection<Identifier> _snowman = super.findResources(type, namespace, prefix, maxDepth, pathFilter);
      _snowman.addAll(this.index.getFilesRecursively(prefix, namespace, maxDepth, pathFilter));
      return _snowman;
   }
}
