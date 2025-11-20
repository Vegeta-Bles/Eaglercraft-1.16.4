package net.minecraft.resource;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;

public class ZipResourcePack extends AbstractFileResourcePack {
   public static final Splitter TYPE_NAMESPACE_SPLITTER = Splitter.on('/').omitEmptyStrings().limit(3);
   private ZipFile file;

   public ZipResourcePack(File _snowman) {
      super(_snowman);
   }

   private ZipFile getZipFile() throws IOException {
      if (this.file == null) {
         this.file = new ZipFile(this.base);
      }

      return this.file;
   }

   @Override
   protected InputStream openFile(String name) throws IOException {
      ZipFile _snowman = this.getZipFile();
      ZipEntry _snowmanx = _snowman.getEntry(name);
      if (_snowmanx == null) {
         throw new ResourceNotFoundException(this.base, name);
      } else {
         return _snowman.getInputStream(_snowmanx);
      }
   }

   @Override
   public boolean containsFile(String name) {
      try {
         return this.getZipFile().getEntry(name) != null;
      } catch (IOException var3) {
         return false;
      }
   }

   @Override
   public Set<String> getNamespaces(ResourceType type) {
      ZipFile _snowman;
      try {
         _snowman = this.getZipFile();
      } catch (IOException var9) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> _snowmanx = _snowman.entries();
      Set<String> _snowmanxx = Sets.newHashSet();

      while (_snowmanx.hasMoreElements()) {
         ZipEntry _snowmanxxx = _snowmanx.nextElement();
         String _snowmanxxxx = _snowmanxxx.getName();
         if (_snowmanxxxx.startsWith(type.getDirectory() + "/")) {
            List<String> _snowmanxxxxx = Lists.newArrayList(TYPE_NAMESPACE_SPLITTER.split(_snowmanxxxx));
            if (_snowmanxxxxx.size() > 1) {
               String _snowmanxxxxxx = _snowmanxxxxx.get(1);
               if (_snowmanxxxxxx.equals(_snowmanxxxxxx.toLowerCase(Locale.ROOT))) {
                  _snowmanxx.add(_snowmanxxxxxx);
               } else {
                  this.warnNonLowerCaseNamespace(_snowmanxxxxxx);
               }
            }
         }
      }

      return _snowmanxx;
   }

   @Override
   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   @Override
   public void close() {
      if (this.file != null) {
         IOUtils.closeQuietly(this.file);
         this.file = null;
      }
   }

   @Override
   public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
      ZipFile _snowman;
      try {
         _snowman = this.getZipFile();
      } catch (IOException var15) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> _snowmanx = _snowman.entries();
      List<Identifier> _snowmanxx = Lists.newArrayList();
      String _snowmanxxx = type.getDirectory() + "/" + namespace + "/";
      String _snowmanxxxx = _snowmanxxx + prefix + "/";

      while (_snowmanx.hasMoreElements()) {
         ZipEntry _snowmanxxxxx = _snowmanx.nextElement();
         if (!_snowmanxxxxx.isDirectory()) {
            String _snowmanxxxxxx = _snowmanxxxxx.getName();
            if (!_snowmanxxxxxx.endsWith(".mcmeta") && _snowmanxxxxxx.startsWith(_snowmanxxxx)) {
               String _snowmanxxxxxxx = _snowmanxxxxxx.substring(_snowmanxxx.length());
               String[] _snowmanxxxxxxxx = _snowmanxxxxxxx.split("/");
               if (_snowmanxxxxxxxx.length >= maxDepth + 1 && pathFilter.test(_snowmanxxxxxxxx[_snowmanxxxxxxxx.length - 1])) {
                  _snowmanxx.add(new Identifier(namespace, _snowmanxxxxxxx));
               }
            }
         }
      }

      return _snowmanxx;
   }
}
