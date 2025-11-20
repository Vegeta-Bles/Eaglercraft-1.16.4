package net.minecraft.util;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;

public class FileNameUtil {
   private static final Pattern FILE_NAME_WITH_COUNT = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
   private static final Pattern RESERVED_WINDOWS_NAMES = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);

   public static String getNextUniqueName(Path path, String name, String extension) throws IOException {
      for (char _snowman : SharedConstants.INVALID_CHARS_LEVEL_NAME) {
         name = name.replace(_snowman, '_');
      }

      name = name.replaceAll("[./\"]", "_");
      if (RESERVED_WINDOWS_NAMES.matcher(name).matches()) {
         name = "_" + name + "_";
      }

      Matcher _snowman = FILE_NAME_WITH_COUNT.matcher(name);
      int _snowmanx = 0;
      if (_snowman.matches()) {
         name = _snowman.group("name");
         _snowmanx = Integer.parseInt(_snowman.group("count"));
      }

      if (name.length() > 255 - extension.length()) {
         name = name.substring(0, 255 - extension.length());
      }

      while (true) {
         String _snowmanxx = name;
         if (_snowmanx != 0) {
            String _snowmanxxx = " (" + _snowmanx + ")";
            int _snowmanxxxx = 255 - _snowmanxxx.length();
            if (name.length() > _snowmanxxxx) {
               _snowmanxx = name.substring(0, _snowmanxxxx);
            }

            _snowmanxx = _snowmanxx + _snowmanxxx;
         }

         _snowmanxx = _snowmanxx + extension;
         Path _snowmanxxx = path.resolve(_snowmanxx);

         try {
            Path _snowmanxxxx = Files.createDirectory(_snowmanxxx);
            Files.deleteIfExists(_snowmanxxxx);
            return path.relativize(_snowmanxxxx).toString();
         } catch (FileAlreadyExistsException var8) {
            _snowmanx++;
         }
      }
   }

   public static boolean isNormal(Path path) {
      Path _snowman = path.normalize();
      return _snowman.equals(path);
   }

   public static boolean isAllowedName(Path _snowman) {
      for (Path _snowmanx : _snowman) {
         if (RESERVED_WINDOWS_NAMES.matcher(_snowmanx.toString()).matches()) {
            return false;
         }
      }

      return true;
   }

   public static Path getResourcePath(Path path, String resourceName, String extension) {
      String _snowman = resourceName + extension;
      Path _snowmanx = Paths.get(_snowman);
      if (_snowmanx.endsWith(extension)) {
         throw new InvalidPathException(_snowman, "empty resource name");
      } else {
         return path.resolve(_snowmanx);
      }
   }
}
