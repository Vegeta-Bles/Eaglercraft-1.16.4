package net.minecraft.client.util;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class VideoMode {
   private final int width;
   private final int height;
   private final int redBits;
   private final int greenBits;
   private final int blueBits;
   private final int refreshRate;
   private static final Pattern PATTERN = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

   public VideoMode(int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.width = _snowman;
      this.height = _snowman;
      this.redBits = _snowman;
      this.greenBits = _snowman;
      this.blueBits = _snowman;
      this.refreshRate = _snowman;
   }

   public VideoMode(Buffer _snowman) {
      this.width = _snowman.width();
      this.height = _snowman.height();
      this.redBits = _snowman.redBits();
      this.greenBits = _snowman.greenBits();
      this.blueBits = _snowman.blueBits();
      this.refreshRate = _snowman.refreshRate();
   }

   public VideoMode(GLFWVidMode _snowman) {
      this.width = _snowman.width();
      this.height = _snowman.height();
      this.redBits = _snowman.redBits();
      this.greenBits = _snowman.greenBits();
      this.blueBits = _snowman.blueBits();
      this.refreshRate = _snowman.refreshRate();
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getRedBits() {
      return this.redBits;
   }

   public int getGreenBits() {
      return this.greenBits;
   }

   public int getBlueBits() {
      return this.blueBits;
   }

   public int getRefreshRate() {
      return this.refreshRate;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         VideoMode _snowman = (VideoMode)o;
         return this.width == _snowman.width
            && this.height == _snowman.height
            && this.redBits == _snowman.redBits
            && this.greenBits == _snowman.greenBits
            && this.blueBits == _snowman.blueBits
            && this.refreshRate == _snowman.refreshRate;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.width, this.height, this.redBits, this.greenBits, this.blueBits, this.refreshRate);
   }

   @Override
   public String toString() {
      return String.format("%sx%s@%s (%sbit)", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
   }

   public static Optional<VideoMode> fromString(@Nullable String _snowman) {
      if (_snowman == null) {
         return Optional.empty();
      } else {
         try {
            Matcher _snowmanx = PATTERN.matcher(_snowman);
            if (_snowmanx.matches()) {
               int _snowmanxx = Integer.parseInt(_snowmanx.group(1));
               int _snowmanxxx = Integer.parseInt(_snowmanx.group(2));
               String _snowmanxxxx = _snowmanx.group(3);
               int _snowmanxxxxx;
               if (_snowmanxxxx == null) {
                  _snowmanxxxxx = 60;
               } else {
                  _snowmanxxxxx = Integer.parseInt(_snowmanxxxx);
               }

               String _snowmanxxxxxx = _snowmanx.group(4);
               int _snowmanxxxxxxx;
               if (_snowmanxxxxxx == null) {
                  _snowmanxxxxxxx = 24;
               } else {
                  _snowmanxxxxxxx = Integer.parseInt(_snowmanxxxxxx);
               }

               int _snowmanxxxxxxxx = _snowmanxxxxxxx / 3;
               return Optional.of(new VideoMode(_snowmanxx, _snowmanxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx));
            }
         } catch (Exception var9) {
         }

         return Optional.empty();
      }
   }

   public String asString() {
      return String.format("%sx%s@%s:%s", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
   }
}
