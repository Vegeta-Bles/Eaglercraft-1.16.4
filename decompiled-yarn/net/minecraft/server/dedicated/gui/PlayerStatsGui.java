package net.minecraft.server.dedicated.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;

public class PlayerStatsGui extends JComponent {
   private static final DecimalFormat AVG_TICK_FORMAT = Util.make(
      new DecimalFormat("########0.000"), _snowman -> _snowman.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   private final int[] memoryUsePercentage = new int[256];
   private int memoryUsePercentagePos;
   private final String[] lines = new String[11];
   private final MinecraftServer server;
   private final Timer timer;

   public PlayerStatsGui(MinecraftServer server) {
      this.server = server;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      this.timer = new Timer(500, _snowman -> this.update());
      this.timer.start();
      this.setBackground(Color.BLACK);
   }

   private void update() {
      long _snowman = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      this.lines[0] = "Memory use: " + _snowman / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
      this.lines[1] = "Avg tick: " + AVG_TICK_FORMAT.format(this.average(this.server.lastTickLengths) * 1.0E-6) + " ms";
      this.memoryUsePercentage[this.memoryUsePercentagePos++ & 0xFF] = (int)(_snowman * 100L / Runtime.getRuntime().maxMemory());
      this.repaint();
   }

   private double average(long[] _snowman) {
      long _snowmanx = 0L;

      for (long _snowmanxx : _snowman) {
         _snowmanx += _snowmanxx;
      }

      return (double)_snowmanx / (double)_snowman.length;
   }

   @Override
   public void paint(Graphics _snowman) {
      _snowman.setColor(new Color(16777215));
      _snowman.fillRect(0, 0, 456, 246);

      for (int _snowmanx = 0; _snowmanx < 256; _snowmanx++) {
         int _snowmanxx = this.memoryUsePercentage[_snowmanx + this.memoryUsePercentagePos & 0xFF];
         _snowman.setColor(new Color(_snowmanxx + 28 << 16));
         _snowman.fillRect(_snowmanx, 100 - _snowmanxx, 1, _snowmanxx);
      }

      _snowman.setColor(Color.BLACK);

      for (int _snowmanx = 0; _snowmanx < this.lines.length; _snowmanx++) {
         String _snowmanxx = this.lines[_snowmanx];
         if (_snowmanxx != null) {
            _snowman.drawString(_snowmanxx, 32, 116 + _snowmanx * 16);
         }
      }
   }

   public void stop() {
      this.timer.stop();
   }
}
