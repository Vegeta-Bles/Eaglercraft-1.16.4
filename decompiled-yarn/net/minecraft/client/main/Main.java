package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import com.mojang.blaze3d.systems.RenderCallStorage;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.Bootstrap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.GlException;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
   private static final Logger LOGGER = LogManager.getLogger();

   public Main() {
   }

   public static void main(String[] args) {
      OptionParser _snowman = new OptionParser();
      _snowman.allowsUnrecognizedOptions();
      _snowman.accepts("demo");
      _snowman.accepts("disableMultiplayer");
      _snowman.accepts("disableChat");
      _snowman.accepts("fullscreen");
      _snowman.accepts("checkGlErrors");
      OptionSpec<String> _snowmanx = _snowman.accepts("server").withRequiredArg();
      OptionSpec<Integer> _snowmanxx = _snowman.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565, new Integer[0]);
      OptionSpec<File> _snowmanxxx = _snowman.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
      OptionSpec<File> _snowmanxxxx = _snowman.accepts("assetsDir").withRequiredArg().ofType(File.class);
      OptionSpec<File> _snowmanxxxxx = _snowman.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
      OptionSpec<File> _snowmanxxxxxx = _snowman.accepts("dataPackDir").withRequiredArg().ofType(File.class);
      OptionSpec<String> _snowmanxxxxxxx = _snowman.accepts("proxyHost").withRequiredArg();
      OptionSpec<Integer> _snowmanxxxxxxxx = _snowman.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
      OptionSpec<String> _snowmanxxxxxxxxx = _snowman.accepts("proxyUser").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxx = _snowman.accepts("proxyPass").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxxx = _snowman.accepts("username").withRequiredArg().defaultsTo("Player" + Util.getMeasuringTimeMs() % 1000L, new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxxx = _snowman.accepts("uuid").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxxxxx = _snowman.accepts("accessToken").withRequiredArg().required();
      OptionSpec<String> _snowmanxxxxxxxxxxxxxx = _snowman.accepts("version").withRequiredArg().required();
      OptionSpec<Integer> _snowmanxxxxxxxxxxxxxxx = _snowman.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854, new Integer[0]);
      OptionSpec<Integer> _snowmanxxxxxxxxxxxxxxxx = _snowman.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480, new Integer[0]);
      OptionSpec<Integer> _snowmanxxxxxxxxxxxxxxxxx = _snowman.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
      OptionSpec<Integer> _snowmanxxxxxxxxxxxxxxxxxx = _snowman.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
      OptionSpec<String> _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.accepts("userProperties").withRequiredArg().defaultsTo("{}", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.accepts("profileProperties").withRequiredArg().defaultsTo("{}", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.accepts("assetIndex").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.accepts("userType").withRequiredArg().defaultsTo("legacy", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowman.accepts("versionType").withRequiredArg().defaultsTo("release", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.nonOptions();
      OptionSet _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.parse(args);
      List<String> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.valuesOf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
      if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
         System.out.println("Completely ignored arguments: " + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx);
      }

      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxx);
      Proxy _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Proxy.NO_PROXY;
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
         try {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Proxy(
               Type.SOCKS, new InetSocketAddress(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx))
            );
         } catch (Exception var71) {
         }
      }

      final String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx);
      final String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx);
      if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.equals(Proxy.NO_PROXY)
         && isNotNullOrEmpty(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
         && isNotNullOrEmpty(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
         Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(_snowman, _snowman.toCharArray());
            }
         });
      }

      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
      OptionalInt _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = toOptional(getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx));
      OptionalInt _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = toOptional(getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx));
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("fullscreen");
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("demo");
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("disableMultiplayer");
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("disableChat");
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
      Gson _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new GsonBuilder().registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = JsonHelper.deserialize(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx), PropertyMap.class
      );
      PropertyMap _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = JsonHelper.deserialize(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx), PropertyMap.class
      );
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
      File _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxx);
      File _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxx)
         ? getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxx)
         : new File(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "assets/");
      File _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxxx)
         ? getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxx)
         : new File(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "resourcepacks/");
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxxxxxxxxxx)
         ? (String)_snowmanxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
         : PlayerEntity.getOfflinePlayerUuid((String)_snowmanxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)).toString();
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxxxxxxxxxxxxxxxxxxx)
         ? (String)_snowmanxxxxxxxxxxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
         : null;
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanx);
      Integer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = getOption(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxx);
      CrashReport.initCrashReport();
      Bootstrap.initialize();
      Bootstrap.logMissing();
      Util.startTimerHack();
      Session _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Session(
         (String)_snowmanxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx),
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         (String)_snowmanxxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx),
         (String)_snowmanxxxxxxxxxxxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      RunArgs _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new RunArgs(
         new RunArgs.Network(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new WindowSettings(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new RunArgs.Directories(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new RunArgs.Game(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new RunArgs.AutoConnect(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Thread _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Thread("Client Shutdown Thread") {
         @Override
         public void run() {
            MinecraftClient _snowman = MinecraftClient.getInstance();
            if (_snowman != null) {
               IntegratedServer _snowmanx = _snowman.getServer();
               if (_snowmanx != null) {
                  _snowmanx.stop(true);
               }
            }
         }
      };
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
      Runtime.getRuntime().addShutdownHook(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
      new RenderCallStorage();

      final MinecraftClient _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      try {
         Thread.currentThread().setName("Render thread");
         RenderSystem.initRenderThread();
         RenderSystem.beginInitialization();
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new MinecraftClient(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         RenderSystem.finishInitialization();
      } catch (GlException var69) {
         LOGGER.warn("Failed to create window: ", var69);
         return;
      } catch (Throwable var70) {
         CrashReport _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = CrashReport.create(var70, "Initializing game");
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.addElement("Initialization");
         MinecraftClient.addSystemDetailsToCrashReport(
            null, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.game.version, null, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         MinecraftClient.printCrashReport(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         return;
      }

      Thread _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.shouldRenderAsync()) {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Thread("Game thread") {
            @Override
            public void run() {
               try {
                  RenderSystem.initGameThread(true);
                  _snowman.run();
               } catch (Throwable var2) {
                  Main.LOGGER.error("Exception in client thread", var2);
               }
            }
         };
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.start();

         while (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isRunning()) {
         }
      } else {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = null;

         try {
            RenderSystem.initGameThread(false);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.run();
         } catch (Throwable var68) {
            LOGGER.error("Unhandled game exception", var68);
         }
      }

      try {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.scheduleStop();
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.join();
         }
      } catch (InterruptedException var66) {
         LOGGER.error("Exception during client thread shutdown", var66);
      } finally {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.stop();
      }
   }

   private static OptionalInt toOptional(@Nullable Integer i) {
      return i != null ? OptionalInt.of(i) : OptionalInt.empty();
   }

   @Nullable
   private static <T> T getOption(OptionSet optionSet, OptionSpec<T> optionSpec) {
      try {
         return (T)optionSet.valueOf(optionSpec);
      } catch (Throwable var5) {
         if (optionSpec instanceof ArgumentAcceptingOptionSpec) {
            ArgumentAcceptingOptionSpec<T> _snowman = (ArgumentAcceptingOptionSpec<T>)optionSpec;
            List<T> _snowmanx = _snowman.defaultValues();
            if (!_snowmanx.isEmpty()) {
               return _snowmanx.get(0);
            }
         }

         throw var5;
      }
   }

   private static boolean isNotNullOrEmpty(@Nullable String s) {
      return s != null && !s.isEmpty();
   }

   static {
      System.setProperty("java.awt.headless", "true");
   }
}
