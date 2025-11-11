import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.annotation.Nullable;

public class dit {
   private int[] a;
   private int b;
   private int c;

   public dit() {
   }

   @Nullable
   public BufferedImage a(BufferedImage var1) {
      if (_snowman == null) {
         return null;
      } else {
         this.b = 64;
         this.c = 64;
         BufferedImage _snowman = new BufferedImage(this.b, this.c, 2);
         Graphics _snowmanx = _snowman.getGraphics();
         _snowmanx.drawImage(_snowman, 0, 0, null);
         boolean _snowmanxx = _snowman.getHeight() == 32;
         if (_snowmanxx) {
            _snowmanx.setColor(new Color(0, 0, 0, 0));
            _snowmanx.fillRect(0, 32, 64, 32);
            _snowmanx.drawImage(_snowman, 24, 48, 20, 52, 4, 16, 8, 20, null);
            _snowmanx.drawImage(_snowman, 28, 48, 24, 52, 8, 16, 12, 20, null);
            _snowmanx.drawImage(_snowman, 20, 52, 16, 64, 8, 20, 12, 32, null);
            _snowmanx.drawImage(_snowman, 24, 52, 20, 64, 4, 20, 8, 32, null);
            _snowmanx.drawImage(_snowman, 28, 52, 24, 64, 0, 20, 4, 32, null);
            _snowmanx.drawImage(_snowman, 32, 52, 28, 64, 12, 20, 16, 32, null);
            _snowmanx.drawImage(_snowman, 40, 48, 36, 52, 44, 16, 48, 20, null);
            _snowmanx.drawImage(_snowman, 44, 48, 40, 52, 48, 16, 52, 20, null);
            _snowmanx.drawImage(_snowman, 36, 52, 32, 64, 48, 20, 52, 32, null);
            _snowmanx.drawImage(_snowman, 40, 52, 36, 64, 44, 20, 48, 32, null);
            _snowmanx.drawImage(_snowman, 44, 52, 40, 64, 40, 20, 44, 32, null);
            _snowmanx.drawImage(_snowman, 48, 52, 44, 64, 52, 20, 56, 32, null);
         }

         _snowmanx.dispose();
         this.a = ((DataBufferInt)_snowman.getRaster().getDataBuffer()).getData();
         this.b(0, 0, 32, 16);
         if (_snowmanxx) {
            this.a(32, 0, 64, 32);
         }

         this.b(0, 16, 64, 32);
         this.b(16, 48, 48, 64);
         return _snowman;
      }
   }

   private void a(int var1, int var2, int var3, int var4) {
      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx < _snowman; _snowmanx++) {
            int _snowmanxx = this.a[_snowman + _snowmanx * this.b];
            if ((_snowmanxx >> 24 & 0xFF) < 128) {
               return;
            }
         }
      }

      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         for (int _snowmanxx = _snowman; _snowmanxx < _snowman; _snowmanxx++) {
            this.a[_snowman + _snowmanxx * this.b] = this.a[_snowman + _snowmanxx * this.b] & 16777215;
         }
      }
   }

   private void b(int var1, int var2, int var3, int var4) {
      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx < _snowman; _snowmanx++) {
            this.a[_snowman + _snowmanx * this.b] = this.a[_snowman + _snowmanx * this.b] | 0xFF000000;
         }
      }
   }
}
