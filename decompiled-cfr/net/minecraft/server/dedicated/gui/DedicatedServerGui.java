/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.util.QueueLogAppender
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.dedicated.gui;

import com.google.common.collect.Lists;
import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.dedicated.gui.PlayerListGui;
import net.minecraft.server.dedicated.gui.PlayerStatsGui;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServerGui
extends JComponent {
    private static final Font FONT_MONOSPACE = new Font("Monospaced", 0, 12);
    private static final Logger LOGGER = LogManager.getLogger();
    private final MinecraftDedicatedServer server;
    private Thread consoleUpdateThread;
    private final Collection<Runnable> stopTasks = Lists.newArrayList();
    private final AtomicBoolean stopped = new AtomicBoolean();

    public static DedicatedServerGui create(MinecraftDedicatedServer server) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception exception) {
            // empty catch block
        }
        JFrame jFrame = new JFrame("Minecraft server");
        DedicatedServerGui _snowman2 = new DedicatedServerGui(server);
        jFrame.setDefaultCloseOperation(2);
        jFrame.add(_snowman2);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowAdapter(_snowman2, jFrame, server){
            final /* synthetic */ DedicatedServerGui field_16857;
            final /* synthetic */ JFrame field_16856;
            final /* synthetic */ MinecraftDedicatedServer field_13841;
            {
                this.field_16857 = dedicatedServerGui;
                this.field_16856 = jFrame;
                this.field_13841 = minecraftDedicatedServer;
            }

            public void windowClosing(WindowEvent windowEvent) {
                if (!DedicatedServerGui.method_16748(this.field_16857).getAndSet(true)) {
                    this.field_16856.setTitle("Minecraft server - shutting down!");
                    this.field_13841.stop(true);
                    DedicatedServerGui.method_16749(this.field_16857);
                }
            }
        });
        _snowman2.addStopTask(jFrame::dispose);
        _snowman2.start();
        return _snowman2;
    }

    private DedicatedServerGui(MinecraftDedicatedServer server) {
        this.server = server;
        this.setPreferredSize(new Dimension(854, 480));
        this.setLayout(new BorderLayout());
        try {
            this.add((Component)this.createLogPanel(), "Center");
            this.add((Component)this.createStatsPanel(), "West");
        }
        catch (Exception exception) {
            LOGGER.error("Couldn't build server GUI", (Throwable)exception);
        }
    }

    public void addStopTask(Runnable task) {
        this.stopTasks.add(task);
    }

    private JComponent createStatsPanel() {
        JPanel jPanel = new JPanel(new BorderLayout());
        PlayerStatsGui _snowman2 = new PlayerStatsGui(this.server);
        this.stopTasks.add(_snowman2::stop);
        jPanel.add((Component)_snowman2, "North");
        jPanel.add((Component)this.createPlaysPanel(), "Center");
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
        return jPanel;
    }

    private JComponent createPlaysPanel() {
        PlayerListGui playerListGui = new PlayerListGui(this.server);
        JScrollPane _snowman2 = new JScrollPane(playerListGui, 22, 30);
        _snowman2.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
        return _snowman2;
    }

    private JComponent createLogPanel() {
        JPanel jPanel = new JPanel(new BorderLayout());
        JTextArea _snowman2 = new JTextArea();
        JScrollPane _snowman3 = new JScrollPane(_snowman2, 22, 30);
        _snowman2.setEditable(false);
        _snowman2.setFont(FONT_MONOSPACE);
        JTextField _snowman4 = new JTextField();
        _snowman4.addActionListener(actionEvent -> {
            String string = _snowman4.getText().trim();
            if (!string.isEmpty()) {
                this.server.enqueueCommand(string, this.server.getCommandSource());
            }
            _snowman4.setText("");
        });
        _snowman2.addFocusListener(new FocusAdapter(this){
            final /* synthetic */ DedicatedServerGui field_13842;
            {
                this.field_13842 = dedicatedServerGui;
            }

            public void focusGained(FocusEvent focusEvent) {
            }
        });
        jPanel.add((Component)_snowman3, "Center");
        jPanel.add((Component)_snowman4, "South");
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
        this.consoleUpdateThread = new Thread(() -> {
            while ((_snowman = QueueLogAppender.getNextLogEvent((String)"ServerGuiConsole")) != null) {
                this.appendToConsole(_snowman2, _snowman3, _snowman);
            }
        });
        this.consoleUpdateThread.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
        this.consoleUpdateThread.setDaemon(true);
        return jPanel;
    }

    public void start() {
        this.consoleUpdateThread.start();
    }

    public void stop() {
        if (!this.stopped.getAndSet(true)) {
            this.runStopTasks();
        }
    }

    private void runStopTasks() {
        this.stopTasks.forEach(Runnable::run);
    }

    public void appendToConsole(JTextArea textArea, JScrollPane scrollPane, String string) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> this.appendToConsole(textArea, scrollPane, string));
            return;
        }
        Document document = textArea.getDocument();
        JScrollBar _snowman2 = scrollPane.getVerticalScrollBar();
        boolean _snowman3 = false;
        if (scrollPane.getViewport().getView() == textArea) {
            _snowman3 = (double)_snowman2.getValue() + _snowman2.getSize().getHeight() + (double)(FONT_MONOSPACE.getSize() * 4) > (double)_snowman2.getMaximum();
        }
        try {
            document.insertString(document.getLength(), string, null);
        }
        catch (BadLocationException badLocationException) {
            // empty catch block
        }
        if (_snowman3) {
            _snowman2.setValue(Integer.MAX_VALUE);
        }
    }

    static /* synthetic */ AtomicBoolean method_16748(DedicatedServerGui dedicatedServerGui) {
        return dedicatedServerGui.stopped;
    }

    static /* synthetic */ void method_16749(DedicatedServerGui dedicatedServerGui) {
        dedicatedServerGui.runStopTasks();
    }
}

