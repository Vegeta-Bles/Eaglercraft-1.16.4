package net.minecraft.resource;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.Util;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryResourcePack extends AbstractFileResourcePack {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final boolean IS_WINDOWS = Util.getOperatingSystem() == Util.OperatingSystem.WINDOWS;
   private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');

   public DirectoryResourcePack(File _snowman) {
      super(_snowman);
   }

   public static boolean isValidPath(File file, String filename) throws IOException {
      String _snowman = file.getCanonicalPath();
      if (IS_WINDOWS) {
         _snowman = BACKSLASH_MATCHER.replaceFrom(_snowman, '/');
      }

      return _snowman.endsWith(filename);
   }

   @Override
   protected InputStream openFile(String name) throws IOException {
      File _snowman = this.getFile(name);
      if (_snowman == null) {
         throw new ResourceNotFoundException(this.base, name);
      } else {
         return new FileInputStream(_snowman);
      }
   }

   @Override
   protected boolean containsFile(String name) {
      return this.getFile(name) != null;
   }

   @Nullable
   private File getFile(String name) {
      try {
         File _snowman = new File(this.base, name);
         if (_snowman.isFile() && isValidPath(_snowman, name)) {
            return _snowman;
         }
      } catch (IOException var3) {
      }

      return null;
   }

   @Override
   public Set<String> getNamespaces(ResourceType type) {
      Set<String> _snowman = Sets.newHashSet();
      File _snowmanx = new File(this.base, type.getDirectory());
      File[] _snowmanxx = _snowmanx.listFiles(DirectoryFileFilter.DIRECTORY);
      if (_snowmanxx != null) {
         for (File _snowmanxxx : _snowmanxx) {
            String _snowmanxxxx = relativize(_snowmanx, _snowmanxxx);
            if (_snowmanxxxx.equals(_snowmanxxxx.toLowerCase(Locale.ROOT))) {
               _snowman.add(_snowmanxxxx.substring(0, _snowmanxxxx.length() - 1));
            } else {
               this.warnNonLowerCaseNamespace(_snowmanxxxx);
            }
         }
      }

      return _snowman;
   }

   @Override
   public void close() {
   }

   @Override
   public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
      File _snowman = new File(this.base, type.getDirectory());
      List<Identifier> _snowmanx = Lists.newArrayList();
      this.findFiles(new File(new File(_snowman, namespace), prefix), maxDepth, namespace, _snowmanx, prefix + "/", pathFilter);
      return _snowmanx;
   }

   private void findFiles(File file, int maxDepth, String namespace, List<Identifier> found, String prefix, Predicate<String> pathFilter) {
      File[] _snowman = file.listFiles();
      if (_snowman != null) {
         for (File _snowmanx : _snowman) {
            if (_snowmanx.isDirectory()) {
               if (maxDepth > 0) {
                  this.findFiles(_snowmanx, maxDepth - 1, namespace, found, prefix + _snowmanx.getName() + "/", pathFilter);
               }
            } else if (!_snowmanx.getName().endsWith(".mcmeta") && pathFilter.test(_snowmanx.getName())) {
               try {
                  found.add(new Identifier(namespace, prefix + _snowmanx.getName()));
               } catch (InvalidIdentifierException var13) {
                  LOGGER.error(var13.getMessage());
               }
            }
         }
      }
   }
}
