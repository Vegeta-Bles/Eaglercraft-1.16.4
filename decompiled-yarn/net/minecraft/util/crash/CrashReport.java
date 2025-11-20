package net.minecraft.util.crash;

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
import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String message;
   private final Throwable cause;
   private final CrashReportSection systemDetailsSection = new CrashReportSection(this, "System Details");
   private final List<CrashReportSection> otherSections = Lists.newArrayList();
   private File file;
   private boolean hasStackTrace = true;
   private StackTraceElement[] stackTrace = new StackTraceElement[0];

   public CrashReport(String message, Throwable cause) {
      this.message = message;
      this.cause = cause;
      this.fillSystemDetails();
   }

   private void fillSystemDetails() {
      this.systemDetailsSection.add("Minecraft Version", () -> SharedConstants.getGameVersion().getName());
      this.systemDetailsSection.add("Minecraft Version ID", () -> SharedConstants.getGameVersion().getId());
      this.systemDetailsSection
         .add("Operating System", () -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
      this.systemDetailsSection.add("Java Version", () -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
      this.systemDetailsSection
         .add(
            "Java VM Version",
            () -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor")
         );
      this.systemDetailsSection.add("Memory", () -> {
         Runtime _snowman = Runtime.getRuntime();
         long _snowmanx = _snowman.maxMemory();
         long _snowmanxx = _snowman.totalMemory();
         long _snowmanxxx = _snowman.freeMemory();
         long _snowmanxxxx = _snowmanx / 1024L / 1024L;
         long _snowmanxxxxx = _snowmanxx / 1024L / 1024L;
         long _snowmanxxxxxx = _snowmanxxx / 1024L / 1024L;
         return _snowmanxxx + " bytes (" + _snowmanxxxxxx + " MB) / " + _snowmanxx + " bytes (" + _snowmanxxxxx + " MB) up to " + _snowmanx + " bytes (" + _snowmanxxxx + " MB)";
      });
      this.systemDetailsSection.add("CPUs", Runtime.getRuntime().availableProcessors());
      this.systemDetailsSection.add("JVM Flags", () -> {
         List<String> _snowman = Util.getJVMFlags().collect(Collectors.toList());
         return String.format("%d total; %s", _snowman.size(), _snowman.stream().collect(Collectors.joining(" ")));
      });
   }

   public String getMessage() {
      return this.message;
   }

   public Throwable getCause() {
      return this.cause;
   }

   public void addStackTrace(StringBuilder _snowman) {
      if ((this.stackTrace == null || this.stackTrace.length <= 0) && !this.otherSections.isEmpty()) {
         this.stackTrace = (StackTraceElement[])ArrayUtils.subarray(this.otherSections.get(0).getStackTrace(), 0, 1);
      }

      if (this.stackTrace != null && this.stackTrace.length > 0) {
         _snowman.append("-- Head --\n");
         _snowman.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
         _snowman.append("Stacktrace:\n");

         for (StackTraceElement _snowmanx : this.stackTrace) {
            _snowman.append("\t").append("at ").append(_snowmanx);
            _snowman.append("\n");
         }

         _snowman.append("\n");
      }

      for (CrashReportSection _snowmanx : this.otherSections) {
         _snowmanx.addStackTrace(_snowman);
         _snowman.append("\n\n");
      }

      this.systemDetailsSection.addStackTrace(_snowman);
   }

   public String getCauseAsString() {
      StringWriter _snowman = null;
      PrintWriter _snowmanx = null;
      Throwable _snowmanxx = this.cause;
      if (_snowmanxx.getMessage() == null) {
         if (_snowmanxx instanceof NullPointerException) {
            _snowmanxx = new NullPointerException(this.message);
         } else if (_snowmanxx instanceof StackOverflowError) {
            _snowmanxx = new StackOverflowError(this.message);
         } else if (_snowmanxx instanceof OutOfMemoryError) {
            _snowmanxx = new OutOfMemoryError(this.message);
         }

         _snowmanxx.setStackTrace(this.cause.getStackTrace());
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

   public String asString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("---- Minecraft Crash Report ----\n");
      _snowman.append("// ");
      _snowman.append(generateWittyComment());
      _snowman.append("\n\n");
      _snowman.append("Time: ");
      _snowman.append(new SimpleDateFormat().format(new Date()));
      _snowman.append("\n");
      _snowman.append("Description: ");
      _snowman.append(this.message);
      _snowman.append("\n\n");
      _snowman.append(this.getCauseAsString());
      _snowman.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

      for (int _snowmanx = 0; _snowmanx < 87; _snowmanx++) {
         _snowman.append("-");
      }

      _snowman.append("\n\n");
      this.addStackTrace(_snowman);
      return _snowman.toString();
   }

   public File getFile() {
      return this.file;
   }

   public boolean writeToFile(File _snowman) {
      if (this.file != null) {
         return false;
      } else {
         if (_snowman.getParentFile() != null) {
            _snowman.getParentFile().mkdirs();
         }

         Writer _snowmanx = null;

         boolean var4;
         try {
            _snowmanx = new OutputStreamWriter(new FileOutputStream(_snowman), StandardCharsets.UTF_8);
            _snowmanx.write(this.asString());
            this.file = _snowman;
            return true;
         } catch (Throwable var8) {
            LOGGER.error("Could not save crash report to {}", _snowman, var8);
            var4 = false;
         } finally {
            IOUtils.closeQuietly(_snowmanx);
         }

         return var4;
      }
   }

   public CrashReportSection getSystemDetailsSection() {
      return this.systemDetailsSection;
   }

   public CrashReportSection addElement(String name) {
      return this.addElement(name, 1);
   }

   public CrashReportSection addElement(String name, int ignoredStackTraceCallCount) {
      CrashReportSection _snowman = new CrashReportSection(this, name);
      if (this.hasStackTrace) {
         int _snowmanx = _snowman.initStackTrace(ignoredStackTraceCallCount);
         StackTraceElement[] _snowmanxx = this.cause.getStackTrace();
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

         this.hasStackTrace = _snowman.method_584(_snowmanxxx, _snowmanxxxx);
         if (_snowmanx > 0 && !this.otherSections.isEmpty()) {
            CrashReportSection _snowmanxxxxxx = this.otherSections.get(this.otherSections.size() - 1);
            _snowmanxxxxxx.trimStackTraceEnd(_snowmanx);
         } else if (_snowmanxx != null && _snowmanxx.length >= _snowmanx && 0 <= _snowmanxxxxx && _snowmanxxxxx < _snowmanxx.length) {
            this.stackTrace = new StackTraceElement[_snowmanxxxxx];
            System.arraycopy(_snowmanxx, 0, this.stackTrace, 0, this.stackTrace.length);
         } else {
            this.hasStackTrace = false;
         }
      }

      this.otherSections.add(_snowman);
      return _snowman;
   }

   private static String generateWittyComment() {
      String[] _snowman = new String[]{
         "Who set us up the TNT?",
         "Everything's going to plan. No, really, that was supposed to happen.",
         "Uh... Did I do that?",
         "Oops.",
         "Why did you do that?",
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
         "Don't be sad. I'll do better next time, I promise!",
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
         "Don't do that.",
         "Ouch. That hurt :(",
         "You're mean.",
         "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]",
         "There are four lights!",
         "But it works on my machine."
      };

      try {
         return _snowman[(int)(Util.getMeasuringTimeNano() % (long)_snowman.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport create(Throwable cause, String title) {
      while (cause instanceof CompletionException && cause.getCause() != null) {
         cause = cause.getCause();
      }

      CrashReport _snowman;
      if (cause instanceof CrashException) {
         _snowman = ((CrashException)cause).getReport();
      } else {
         _snowman = new CrashReport(title, cause);
      }

      return _snowman;
   }

   public static void initCrashReport() {
      new CrashReport("Don't panic!", new Throwable()).asString();
   }
}
