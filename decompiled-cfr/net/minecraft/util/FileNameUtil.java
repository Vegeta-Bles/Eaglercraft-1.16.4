/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;

public class FileNameUtil {
    private static final Pattern FILE_NAME_WITH_COUNT = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
    private static final Pattern RESERVED_WINDOWS_NAMES = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);

    public static String getNextUniqueName(Path path, String name, String extension) throws IOException {
        for (char c : SharedConstants.INVALID_CHARS_LEVEL_NAME) {
            name = name.replace(c, '_');
        }
        if (RESERVED_WINDOWS_NAMES.matcher(name = name.replaceAll("[./\"]", "_")).matches()) {
            name = "_" + name + "_";
        }
        Matcher matcher = FILE_NAME_WITH_COUNT.matcher(name);
        int _snowman2 = 0;
        if (matcher.matches()) {
            name = matcher.group("name");
            _snowman2 = Integer.parseInt(matcher.group("count"));
        }
        if (name.length() > 255 - extension.length()) {
            name = name.substring(0, 255 - extension.length());
        }
        while (true) {
            Object _snowman4;
            String string = name;
            if (_snowman2 != 0) {
                _snowman4 = " (" + _snowman2 + ")";
                int _snowman3 = 255 - ((String)_snowman4).length();
                if (string.length() > _snowman3) {
                    string = string.substring(0, _snowman3);
                }
                string = string + (String)_snowman4;
            }
            string = string + extension;
            _snowman4 = path.resolve(string);
            try {
                Path path2 = Files.createDirectory((Path)_snowman4, new FileAttribute[0]);
                Files.deleteIfExists(path2);
                return path.relativize(path2).toString();
            }
            catch (FileAlreadyExistsException fileAlreadyExistsException) {
                ++_snowman2;
                continue;
            }
            break;
        }
    }

    public static boolean isNormal(Path path) {
        Path path2 = path.normalize();
        return path2.equals(path);
    }

    public static boolean isAllowedName(Path path) {
        for (Path path2 : path) {
            if (!RESERVED_WINDOWS_NAMES.matcher(path2.toString()).matches()) continue;
            return false;
        }
        return true;
    }

    public static Path getResourcePath(Path path, String resourceName, String extension) {
        String string = resourceName + extension;
        Path _snowman2 = Paths.get(string, new String[0]);
        if (_snowman2.endsWith(extension)) {
            throw new InvalidPathException(string, "empty resource name");
        }
        return path.resolve(_snowman2);
    }
}

