package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
   private static final Logger a = LogManager.getLogger();

   public Main() {
   }

   public static void main(String[] var0) {
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
      OptionSpec<String> _snowmanxxxxxxxxxxx = _snowman.accepts("username").withRequiredArg().defaultsTo("Player" + x.b() % 1000L, new String[0]);
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
      OptionSet _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.parse(_snowman);
      List<String> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.valuesOf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
      if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
         System.out.println("Completely ignored arguments: " + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx);
      }

      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxx);
      Proxy _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Proxy.NO_PROXY;
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
         try {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Proxy(Type.SOCKS, new InetSocketAddress(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx)));
         } catch (Exception var71) {
         }
      }

      final String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx);
      final String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx);
      if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.equals(Proxy.NO_PROXY) && a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) && a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
         Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(_snowman, _snowman.toCharArray());
            }
         });
      }

      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
      OptionalInt _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx));
      OptionalInt _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx));
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("fullscreen");
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("demo");
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("disableMultiplayer");
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has("disableChat");
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
      Gson _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new GsonBuilder().registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afd.a(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx), PropertyMap.class
      );
      PropertyMap _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afd.a(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx), PropertyMap.class
      );
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
      File _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxx);
      File _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxx)
         ? a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxx)
         : new File(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "assets/");
      File _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxxx)
         ? a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxx)
         : new File(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "resourcepacks/");
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxxxxxxxxxx)
         ? (String)_snowmanxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
         : bfw.c((String)_snowmanxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)).toString();
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.has(_snowmanxxxxxxxxxxxxxxxxxxxxx)
         ? (String)_snowmanxxxxxxxxxxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
         : null;
      String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanx);
      Integer _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxx);
      l.h();
      vm.a();
      vm.c();
      x.l();
      dkm _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new dkm(
         (String)_snowmanxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx),
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         (String)_snowmanxxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx),
         (String)_snowmanxxxxxxxxxxxxxxxxxxxxxx.value(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      dsz _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new dsz(
         new dsz.d(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new dej(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new dsz.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new dsz.b(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         ),
         new dsz.c(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
      );
      Thread _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Thread("Client Shutdown Thread") {
         @Override
         public void run() {
            djz _snowman = djz.C();
            if (_snowman != null) {
               eng _snowmanx = _snowman.H();
               if (_snowmanx != null) {
                  _snowmanx.a(true);
               }
            }
         }
      };
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setUncaughtExceptionHandler(new o(a));
      Runtime.getRuntime().addShutdownHook(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
      new def();

      final djz _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      try {
         Thread.currentThread().setName("Render thread");
         RenderSystem.initRenderThread();
         RenderSystem.beginInitialization();
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new djz(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         RenderSystem.finishInitialization();
      } catch (dta var69) {
         a.warn("Failed to create window: ", var69);
         return;
      } catch (Throwable var70) {
         l _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = l.a(var70, "Initializing game");
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a("Initialization");
         djz.a(null, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d.b, null, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         djz.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         return;
      }

      Thread _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.aC()) {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Thread("Game thread") {
            @Override
            public void run() {
               try {
                  RenderSystem.initGameThread(true);
                  _snowman.e();
               } catch (Throwable var2) {
                  Main.a.error("Exception in client thread", var2);
               }
            }
         };
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.start();

         while (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.o()) {
         }
      } else {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = null;

         try {
            RenderSystem.initGameThread(false);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.e();
         } catch (Throwable var68) {
            a.error("Unhandled game exception", var68);
         }
      }

      try {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.n();
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.join();
         }
      } catch (InterruptedException var66) {
         a.error("Exception during client thread shutdown", var66);
      } finally {
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.l();
      }
   }

   private static OptionalInt a(@Nullable Integer var0) {
      return _snowman != null ? OptionalInt.of(_snowman) : OptionalInt.empty();
   }

   @Nullable
   private static <T> T a(OptionSet var0, OptionSpec<T> var1) {
      try {
         return (T)_snowman.valueOf(_snowman);
      } catch (Throwable var5) {
         if (_snowman instanceof ArgumentAcceptingOptionSpec) {
            ArgumentAcceptingOptionSpec<T> _snowman = (ArgumentAcceptingOptionSpec<T>)_snowman;
            List<T> _snowmanx = _snowman.defaultValues();
            if (!_snowmanx.isEmpty()) {
               return _snowmanx.get(0);
            }
         }

         throw var5;
      }
   }

   private static boolean a(@Nullable String var0) {
      return _snowman != null && !_snowman.isEmpty();
   }

   static {
      System.setProperty("java.awt.headless", "true");
   }
}
