import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class l {
   private static final Logger a = LogManager.getLogger();
   private final String b;
   private final Throwable c;
   private final m d = new m(this, "System Details");
   private final List<m> e = Lists.newArrayList();
   private File f;
   private boolean g = true;
   private StackTraceElement[] h = new StackTraceElement[0];

   public l(String var1, Throwable var2) {
      this.b = _snowman;
      this.c = _snowman;
      this.i();
   }

   private void i() {
      this.d.a("Minecraft Version", () -> w.a().getName());
      this.d.a("Minecraft Version ID", () -> w.a().getId());
      this.d
         .a("Operating System", () -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
      this.d.a("Java Version", () -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
      this.d
         .a(
            "Java VM Version",
            () -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor")
         );
      this.d.a("Memory", () -> {
         Runtime _snowman = Runtime.getRuntime();
         long _snowmanx = _snowman.maxMemory();
         long _snowmanxx = _snowman.totalMemory();
         long _snowmanxxx = _snowman.freeMemory();
         long _snowmanxxxx = _snowmanx / 1024L / 1024L;
         long _snowmanxxxxx = _snowmanxx / 1024L / 1024L;
         long _snowmanxxxxxx = _snowmanxxx / 1024L / 1024L;
         return _snowmanxxx + " bytes (" + _snowmanxxxxxx + " MB) / " + _snowmanxx + " bytes (" + _snowmanxxxxx + " MB) up to " + _snowmanx + " bytes (" + _snowmanxxxx + " MB)";
      });
      this.d.a("CPUs", Runtime.getRuntime().availableProcessors());
      this.d.a("JVM Flags", () -> {
         List<String> _snowman = x.j().collect(Collectors.toList());
         return String.format("%d total; %s", _snowman.size(), _snowman.stream().collect(Collectors.joining(" ")));
      });
   }

   public String a() {
      return this.b;
   }

   public Throwable b() {
      return this.c;
   }

   public void a(StringBuilder var1) {
      if ((this.h == null || this.h.length <= 0) && !this.e.isEmpty()) {
         this.h = (StackTraceElement[])ArrayUtils.subarray(this.e.get(0).a(), 0, 1);
      }

      if (this.h != null && this.h.length > 0) {
         _snowman.append("-- Head --\n");
         _snowman.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         _snowman.append("Stacktrace:\n");

         for (StackTraceElement _snowman : this.h) {
            _snowman.append("\t").append("at ").append(_snowman);
            _snowman.append("\n");
         }

         _snowman.append("\n");
      }

      for (m _snowman : this.e) {
         _snowman.a(_snowman);
         _snowman.append("\n\n");
      }

      this.d.a(_snowman);
   }

   public String d() {
      StringWriter _snowman = null;
      PrintWriter _snowmanx = null;
      Throwable _snowmanxx = this.c;
      if (_snowmanxx.getMessage() == null) {
         if (_snowmanxx instanceof NullPointerException) {
            _snowmanxx = new NullPointerException(this.b);
         } else if (_snowmanxx instanceof StackOverflowError) {
            _snowmanxx = new StackOverflowError(this.b);
         } else if (_snowmanxx instanceof OutOfMemoryError) {
            _snowmanxx = new OutOfMemoryError(this.b);
         }

         _snowmanxx.setStackTrace(this.c.getStackTrace());
      }

      String var4;
      try {
         _snowman = new StringWriter();
         _snowmanx = new PrintWriter(_snowman);
         _snowmanxx.printStackTrace(_snowmanx);
         var4 = _snowman.toString();
      } finally {
         IOUtils.closeQuietly(_snowman);
         IOUtils.closeQuietly(_snowmanx);
      }

      return var4;
   }

   public String e() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("---- Minecraft Crash Report ----\n");
      _snowman.append("// ");
      _snowman.append(j());
      _snowman.append("\n\n");
      _snowman.append("Time: ");
      _snowman.append(new SimpleDateFormat().format(new Date()));
      _snowman.append("\n");
      _snowman.append("Description: ");
      _snowman.append(this.b);
      _snowman.append("\n\n");
      _snowman.append(this.d());
      _snowman.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for (int _snowmanx = 0; _snowmanx < 87; _snowmanx++) {
         _snowman.append("-");
      }

      _snowman.append("\n\n");
      this.a(_snowman);
      return _snowman.toString();
   }

   public File f() {
      return this.f;
   }

   public boolean a(File var1) {
      if (this.f != null) {
         return false;
      } else {
         if (_snowman.getParentFile() != null) {
            _snowman.getParentFile().mkdirs();
         }

         Writer _snowman = null;

         boolean var4;
         try {
            _snowman = new OutputStreamWriter(new FileOutputStream(_snowman), StandardCharsets.UTF_8);
            _snowman.write(this.e());
            this.f = _snowman;
            return true;
         } catch (Throwable var8) {
            a.error("Could not save crash report to {}", _snowman, var8);
            var4 = false;
         } finally {
            IOUtils.closeQuietly(_snowman);
         }

         return var4;
      }
   }

   public m g() {
      return this.d;
   }

   public m a(String var1) {
      return this.a(_snowman, 1);
   }

   public m a(String var1, int var2) {
      m _snowman = new m(this, _snowman);
      if (this.g) {
         int _snowmanx = _snowman.a(_snowman);
         StackTraceElement[] _snowmanxx = this.c.getStackTrace();
         StackTraceElement _snowmanxxx = null;
         StackTraceElement _snowmanxxxx = null;
         int _snowmanxxxxx = _snowmanxx.length - _snowmanx;
         if (_snowmanxxxxx < 0) {
            System.out.println("Negative index in crash report handler (" + _snowmanxx.length + "/" + _snowmanx + ")");
         }

         if (_snowmanxx != null && 0 <= _snowmanxxxxx && _snowmanxxxxx < _snowmanxx.length) {
            _snowmanxxx = _snowmanxx[_snowmanxxxxx];
            if (_snowmanxx.length + 1 - _snowmanx < _snowmanxx.length) {
               _snowmanxxxx = _snowmanxx[_snowmanxx.length + 1 - _snowmanx];
            }
         }

         this.g = _snowman.a(_snowmanxxx, _snowmanxxxx);
         if (_snowmanx > 0 && !this.e.isEmpty()) {
            m _snowmanxxxxxx = this.e.get(this.e.size() - 1);
            _snowmanxxxxxx.b(_snowmanx);
         } else if (_snowmanxx != null && _snowmanxx.length >= _snowmanx && 0 <= _snowmanxxxxx && _snowmanxxxxx < _snowmanxx.length) {
            this.h = new StackTraceElement[_snowmanxxxxx];
            System.arraycopy(_snowmanxx, 0, this.h, 0, this.h.length);
         } else {
            this.g = false;
         }
      }

      this.e.add(_snowman);
      return _snowman;
   }

   private static String j() {
      String[] _snowman = new String[]{
         "Who set us up the TNT?",
         "Everything's going to plan. No, really, that was supposed to happen.",
         "Uh... Did I do_ that?",
         "Oops.",
         "Why did you do_ that?",
         "I feel sad now :(",
         "My bad.",
         "I'm sorry, Dave.",
         "I let you down. Sorry :(",
         "On the bright side, I bought you a teddy bear!",
         "Daisy, daisy...",
         "Oh - I know what I did wrong!",
         "Hey, that tickles! Hehehe!",
         "I blame Dinnerbone.",
         "You should try our sister game, Minceraft!",
         "Don't be sad. I'll do_ better next time, I promise!",
         "Don't be sad, have a hug! <3",
         "I just don't know what went wrong :(",
         "Shall we play a game?",
         "Quite honestly, I wouldn't worry myself about that.",
         "I bet Cylons wouldn't have this problem.",
         "Sorry :(",
         "Surprise! Haha. Well, this is awkward.",
         "Would you like a cupcake?",
         "Hi. I'm Minecraft, and I'm a crashaholic.",
         "Ooh. Shiny.",
         "This doesn't make any sense!",
         "Why is it breaking :(",
         "Don't do_ that.",
         "Ouch. That hurt :(",
         "You're mean.",
         "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]",
         "There are four lights!",
         "But it works on my machine."
      };

      try {
         return _snowman[(int)(x.c() % (long)_snowman.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public static l a(Throwable var0, String var1) {
      while (_snowman instanceof CompletionException && _snowman.getCause() != null) {
         _snowman = _snowman.getCause();
      }

      l _snowman;
      if (_snowman instanceof u) {
         _snowman = ((u)_snowman).a();
      } else {
         _snowman = new l(_snowman, _snowman);
      }

      return _snowman;
   }

   public static void h() {
      new l("Don't panic!", new Throwable()).e();
   }
}
