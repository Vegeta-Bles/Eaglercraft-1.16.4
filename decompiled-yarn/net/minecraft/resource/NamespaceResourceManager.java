package net.minecraft.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NamespaceResourceManager implements ResourceManager {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final List<ResourcePack> packList = Lists.newArrayList();
   private final ResourceType type;
   private final String namespace;

   public NamespaceResourceManager(ResourceType type, String namespace) {
      this.type = type;
      this.namespace = namespace;
   }

   public void addPack(ResourcePack pack) {
      this.packList.add(pack);
   }

   @Override
   public Set<String> getAllNamespaces() {
      return ImmutableSet.of(this.namespace);
   }

   @Override
   public Resource getResource(Identifier id) throws IOException {
      this.validate(id);
      ResourcePack _snowman = null;
      Identifier _snowmanx = getMetadataPath(id);

      for (int _snowmanxx = this.packList.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
         ResourcePack _snowmanxxx = this.packList.get(_snowmanxx);
         if (_snowman == null && _snowmanxxx.contains(this.type, _snowmanx)) {
            _snowman = _snowmanxxx;
         }

         if (_snowmanxxx.contains(this.type, id)) {
            InputStream _snowmanxxxx = null;
            if (_snowman != null) {
               _snowmanxxxx = this.open(_snowmanx, _snowman);
            }

            return new ResourceImpl(_snowmanxxx.getName(), id, this.open(id, _snowmanxxx), _snowmanxxxx);
         }
      }

      throw new FileNotFoundException(id.toString());
   }

   @Override
   public boolean containsResource(Identifier id) {
      if (!this.isPathAbsolute(id)) {
         return false;
      } else {
         for (int _snowman = this.packList.size() - 1; _snowman >= 0; _snowman--) {
            ResourcePack _snowmanx = this.packList.get(_snowman);
            if (_snowmanx.contains(this.type, id)) {
               return true;
            }
         }

         return false;
      }
   }

   protected InputStream open(Identifier id, ResourcePack pack) throws IOException {
      InputStream _snowman = pack.open(this.type, id);
      return (InputStream)(LOGGER.isDebugEnabled() ? new NamespaceResourceManager.DebugInputStream(_snowman, id, pack.getName()) : _snowman);
   }

   private void validate(Identifier id) throws IOException {
      if (!this.isPathAbsolute(id)) {
         throw new IOException("Invalid relative path to resource: " + id);
      }
   }

   private boolean isPathAbsolute(Identifier id) {
      return !id.getPath().contains("..");
   }

   @Override
   public List<Resource> getAllResources(Identifier id) throws IOException {
      this.validate(id);
      List<Resource> _snowman = Lists.newArrayList();
      Identifier _snowmanx = getMetadataPath(id);

      for (ResourcePack _snowmanxx : this.packList) {
         if (_snowmanxx.contains(this.type, id)) {
            InputStream _snowmanxxx = _snowmanxx.contains(this.type, _snowmanx) ? this.open(_snowmanx, _snowmanxx) : null;
            _snowman.add(new ResourceImpl(_snowmanxx.getName(), id, this.open(id, _snowmanxx), _snowmanxxx));
         }
      }

      if (_snowman.isEmpty()) {
         throw new FileNotFoundException(id.toString());
      } else {
         return _snowman;
      }
   }

   @Override
   public Collection<Identifier> findResources(String resourceType, Predicate<String> pathPredicate) {
      List<Identifier> _snowman = Lists.newArrayList();

      for (ResourcePack _snowmanx : this.packList) {
         _snowman.addAll(_snowmanx.findResources(this.type, this.namespace, resourceType, Integer.MAX_VALUE, pathPredicate));
      }

      Collections.sort(_snowman);
      return _snowman;
   }

   @Override
   public Stream<ResourcePack> streamResourcePacks() {
      return this.packList.stream();
   }

   static Identifier getMetadataPath(Identifier id) {
      return new Identifier(id.getNamespace(), id.getPath() + ".mcmeta");
   }

   static class DebugInputStream extends FilterInputStream {
      private final String leakMessage;
      private boolean closed;

      public DebugInputStream(InputStream parent, Identifier id, String packName) {
         super(parent);
         ByteArrayOutputStream _snowman = new ByteArrayOutputStream();
         new Exception().printStackTrace(new PrintStream(_snowman));
         this.leakMessage = "Leaked resource: '" + id + "' loaded from pack: '" + packName + "'\n" + _snowman;
      }

      @Override
      public void close() throws IOException {
         super.close();
         this.closed = true;
      }

      @Override
      protected void finalize() throws Throwable {
         if (!this.closed) {
            NamespaceResourceManager.LOGGER.warn(this.leakMessage);
         }

         super.finalize();
      }
   }
}
