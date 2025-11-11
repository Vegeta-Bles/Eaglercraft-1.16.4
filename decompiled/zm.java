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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zm extends JComponent {
   private static final Font a = new Font("Monospaced", 0, 12);
   private static final Logger b = LogManager.getLogger();
   private final zg c;
   private Thread d;
   private final Collection<Runnable> e = Lists.newArrayList();
   private final AtomicBoolean f = new AtomicBoolean();

   public static zm a(final zg var0) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var3) {
      }

      final JFrame _snowman = new JFrame("Minecraft server");
      final zm _snowmanx = new zm(_snowman);
      _snowman.setDefaultCloseOperation(2);
      _snowman.add(_snowmanx);
      _snowman.pack();
      _snowman.setLocationRelativeTo(null);
      _snowman.setVisible(true);
      _snowman.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent var1x) {
            if (!_snowman.f.getAndSet(true)) {
               _snowman.setTitle("Minecraft server - shutting down!");
               _snowman.a(true);
               _snowman.f();
            }
         }
      });
      _snowmanx.a(_snowman::dispose);
      _snowmanx.a();
      return _snowmanx;
   }

   private zm(zg var1) {
      this.c = _snowman;
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.e(), "Center");
         this.add(this.c(), "West");
      } catch (Exception var3) {
         b.error("Couldn't build server GUI", var3);
      }
   }

   public void a(Runnable var1) {
      this.e.add(_snowman);
   }

   private JComponent c() {
      JPanel _snowman = new JPanel(new BorderLayout());
      zo _snowmanx = new zo(this.c);
      this.e.add(_snowmanx::a);
      _snowman.add(_snowmanx, "North");
      _snowman.add(this.d(), "Center");
      _snowman.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
      return _snowman;
   }

   private JComponent d() {
      JList<?> _snowman = new zn(this.c);
      JScrollPane _snowmanx = new JScrollPane(_snowman, 22, 30);
      _snowmanx.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return _snowmanx;
   }

   private JComponent e() {
      JPanel _snowman = new JPanel(new BorderLayout());
      JTextArea _snowmanx = new JTextArea();
      JScrollPane _snowmanxx = new JScrollPane(_snowmanx, 22, 30);
      _snowmanx.setEditable(false);
      _snowmanx.setFont(a);
      JTextField _snowmanxxx = new JTextField();
      _snowmanxxx.addActionListener(var2x -> {
         String _snowmanxxxx = _snowman.getText().trim();
         if (!_snowmanxxxx.isEmpty()) {
            this.c.a(_snowmanxxxx, this.c.aE());
         }

         _snowman.setText("");
      });
      _snowmanx.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent var1) {
         }
      });
      _snowman.add(_snowmanxx, "Center");
      _snowman.add(_snowmanxxx, "South");
      _snowman.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
      this.d = new Thread(() -> {
         String _snowmanxxxx;
         while ((_snowmanxxxx = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
            this.a(_snowman, _snowman, _snowmanxxxx);
         }
      });
      this.d.setUncaughtExceptionHandler(new o(b));
      this.d.setDaemon(true);
      return _snowman;
   }

   public void a() {
      this.d.start();
   }

   public void b() {
      if (!this.f.getAndSet(true)) {
         this.f();
      }
   }

   private void f() {
      this.e.forEach(Runnable::run);
   }

   public void a(JTextArea var1, JScrollPane var2, String var3) {
      if (!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(() -> this.a(_snowman, _snowman, _snowman));
      } else {
         Document _snowman = _snowman.getDocument();
         JScrollBar _snowmanx = _snowman.getVerticalScrollBar();
         boolean _snowmanxx = false;
         if (_snowman.getViewport().getView() == _snowman) {
            _snowmanxx = (double)_snowmanx.getValue() + _snowmanx.getSize().getHeight() + (double)(a.getSize() * 4) > (double)_snowmanx.getMaximum();
         }

         try {
            _snowman.insertString(_snowman.getLength(), _snowman, null);
         } catch (BadLocationException var8) {
         }

         if (_snowmanxx) {
            _snowmanx.setValue(Integer.MAX_VALUE);
         }
      }
   }
}
