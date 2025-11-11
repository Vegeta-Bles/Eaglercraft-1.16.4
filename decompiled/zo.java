import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.server.MinecraftServer;

public class zo extends JComponent {
   private static final DecimalFormat a = x.a(
      new DecimalFormat("########0.000"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   private final int[] b = new int[256];
   private int c;
   private final String[] d = new String[11];
   private final MinecraftServer e;
   private final Timer f;

   public zo(MinecraftServer var1) {
      this.e = _snowman;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      this.f = new Timer(500, var1x -> this.b());
      this.f.start();
      this.setBackground(Color.BLACK);
   }

   private void b() {
      long _snowman = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      this.d[0] = "Memory use: " + _snowman / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
      this.d[1] = "Avg tick: " + a.format(this.a(this.e.h) * 1.0E-6) + " ms";
      this.b[this.c++ & 0xFF] = (int)(_snowman * 100L / Runtime.getRuntime().maxMemory());
      this.repaint();
   }

   private double a(long[] var1) {
      long _snowman = 0L;

      for (long _snowmanx : _snowman) {
         _snowman += _snowmanx;
      }

      return (double)_snowman / (double)_snowman.length;
   }

   @Override
   public void paint(Graphics var1) {
      _snowman.setColor(new Color(16777215));
      _snowman.fillRect(0, 0, 456, 246);

      for (int _snowman = 0; _snowman < 256; _snowman++) {
         int _snowmanx = this.b[_snowman + this.c & 0xFF];
         _snowman.setColor(new Color(_snowmanx + 28 << 16));
         _snowman.fillRect(_snowman, 100 - _snowmanx, 1, _snowmanx);
      }

      _snowman.setColor(Color.BLACK);

      for (int _snowman = 0; _snowman < this.d.length; _snowman++) {
         String _snowmanx = this.d[_snowman];
         if (_snowmanx != null) {
            _snowman.drawString(_snowmanx, 32, 116 + _snowman * 16);
         }
      }
   }

   public void a() {
      this.f.stop();
   }
}
