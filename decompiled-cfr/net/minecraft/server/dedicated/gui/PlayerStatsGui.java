/*
 * Decompiled with CFR 0.152.
 */
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

public class PlayerStatsGui
extends JComponent {
    private static final DecimalFormat AVG_TICK_FORMAT = Util.make(new DecimalFormat("########0.000"), decimalFormat -> decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
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
        this.timer = new Timer(500, actionEvent -> this.update());
        this.timer.start();
        this.setBackground(Color.BLACK);
    }

    private void update() {
        long l = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        this.lines[0] = "Memory use: " + l / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.lines[1] = "Avg tick: " + AVG_TICK_FORMAT.format(this.average(this.server.lastTickLengths) * 1.0E-6) + " ms";
        this.memoryUsePercentage[this.memoryUsePercentagePos++ & 0xFF] = (int)(l * 100L / Runtime.getRuntime().maxMemory());
        this.repaint();
    }

    private double average(long[] lArray) {
        long l = 0L;
        for (long l2 : lArray) {
            l += l2;
        }
        return (double)l / (double)lArray.length;
    }

    @Override
    public void paint(Graphics graphics2) {
        Graphics graphics2;
        int n;
        graphics2.setColor(new Color(0xFFFFFF));
        graphics2.fillRect(0, 0, 456, 246);
        for (n = 0; n < 256; ++n) {
            _snowman = this.memoryUsePercentage[n + this.memoryUsePercentagePos & 0xFF];
            graphics2.setColor(new Color(_snowman + 28 << 16));
            graphics2.fillRect(n, 100 - _snowman, 1, _snowman);
        }
        graphics2.setColor(Color.BLACK);
        for (n = 0; n < this.lines.length; ++n) {
            String string = this.lines[n];
            if (string == null) continue;
            graphics2.drawString(string, 32, 116 + n * 16);
        }
    }

    public void stop() {
        this.timer.stop();
    }
}

