package net.minecraft.server.dedicated.gui;

import com.google.common.collect.Lists;
import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
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
import javax.swing.JList;
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
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServerGui extends JComponent {
   private static final Font FONT_MONOSPACE = new Font("Monospaced", 0, 12);
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftDedicatedServer server;
   private Thread consoleUpdateThread;
   private final Collection<Runnable> stopTasks = Lists.newArrayList();
   private final AtomicBoolean stopped = new AtomicBoolean();

   public static DedicatedServerGui create(MinecraftDedicatedServer server) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var3) {
      }

      final JFrame _snowman = new JFrame("Minecraft server");
      final DedicatedServerGui _snowmanx = new DedicatedServerGui(server);
      _snowman.setDefaultCloseOperation(2);
      _snowman.add(_snowmanx);
      _snowman.pack();
      _snowman.setLocationRelativeTo(null);
      _snowman.setVisible(true);
      _snowman.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent _snowman) {
            if (!_snowman.stopped.getAndSet(true)) {
               _snowman.setTitle("Minecraft server - shutting down!");
               server.stop(true);
               _snowman.runStopTasks();
            }
         }
      });
      _snowmanx.addStopTask(_snowman::dispose);
      _snowmanx.start();
      return _snowmanx;
   }

   private DedicatedServerGui(MinecraftDedicatedServer server) {
      this.server = server;
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.createLogPanel(), "Center");
         this.add(this.createStatsPanel(), "West");
      } catch (Exception var3) {
         LOGGER.error("Couldn't build server GUI", var3);
      }
   }

   public void addStopTask(Runnable task) {
      this.stopTasks.add(task);
   }

   private JComponent createStatsPanel() {
      JPanel _snowman = new JPanel(new BorderLayout());
      PlayerStatsGui _snowmanx = new PlayerStatsGui(this.server);
      this.stopTasks.add(_snowmanx::stop);
      _snowman.add(_snowmanx, "North");
      _snowman.add(this.createPlaysPanel(), "Center");
      _snowman.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
      return _snowman;
   }

   private JComponent createPlaysPanel() {
      JList<?> _snowman = new PlayerListGui(this.server);
      JScrollPane _snowmanx = new JScrollPane(_snowman, 22, 30);
      _snowmanx.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return _snowmanx;
   }

   private JComponent createLogPanel() {
      JPanel _snowman = new JPanel(new BorderLayout());
      JTextArea _snowmanx = new JTextArea();
      JScrollPane _snowmanxx = new JScrollPane(_snowmanx, 22, 30);
      _snowmanx.setEditable(false);
      _snowmanx.setFont(FONT_MONOSPACE);
      JTextField _snowmanxxx = new JTextField();
      _snowmanxxx.addActionListener(_snowmanxxxx -> {
         String _snowmanxxxxx = _snowman.getText().trim();
         if (!_snowmanxxxxx.isEmpty()) {
            this.server.enqueueCommand(_snowmanxxxxx, this.server.getCommandSource());
         }

         _snowman.setText("");
      });
      _snowmanx.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent _snowman) {
         }
      });
      _snowman.add(_snowmanxx, "Center");
      _snowman.add(_snowmanxxx, "South");
      _snowman.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
      this.consoleUpdateThread = new Thread(() -> {
         String _snowmanxxxx;
         while ((_snowmanxxxx = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
            this.appendToConsole(_snowman, _snowman, _snowmanxxxx);
         }
      });
      this.consoleUpdateThread.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
      this.consoleUpdateThread.setDaemon(true);
      return _snowman;
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

   public void appendToConsole(JTextArea textArea, JScrollPane scrollPane, String _snowman) {
      if (!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(() -> this.appendToConsole(textArea, scrollPane, _snowman));
      } else {
         Document _snowmanx = textArea.getDocument();
         JScrollBar _snowmanxx = scrollPane.getVerticalScrollBar();
         boolean _snowmanxxx = false;
         if (scrollPane.getViewport().getView() == textArea) {
            _snowmanxxx = (double)_snowmanxx.getValue() + _snowmanxx.getSize().getHeight() + (double)(FONT_MONOSPACE.getSize() * 4) > (double)_snowmanxx.getMaximum();
         }

         try {
            _snowmanx.insertString(_snowmanx.getLength(), _snowman, null);
         } catch (BadLocationException var8) {
         }

         if (_snowmanxxx) {
            _snowmanxx.setValue(Integer.MAX_VALUE);
         }
      }
   }
}
