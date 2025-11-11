package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
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
      OptionSpec<Void> _snowmanx = _snowman.accepts("nogui");
      OptionSpec<Void> _snowmanxx = _snowman.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
      OptionSpec<Void> _snowmanxxx = _snowman.accepts("demo");
      OptionSpec<Void> _snowmanxxxx = _snowman.accepts("bonusChest");
      OptionSpec<Void> _snowmanxxxxx = _snowman.accepts("forceUpgrade");
      OptionSpec<Void> _snowmanxxxxxx = _snowman.accepts("eraseCache");
      OptionSpec<Void> _snowmanxxxxxxx = _snowman.accepts("safeMode", "Loads level with vanilla datapack only");
      OptionSpec<Void> _snowmanxxxxxxxx = _snowman.accepts("help").forHelp();
      OptionSpec<String> _snowmanxxxxxxxxx = _snowman.accepts("singleplayer").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxx = _snowman.accepts("universe").withRequiredArg().defaultsTo(".", new String[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxx = _snowman.accepts("world").withRequiredArg();
      OptionSpec<Integer> _snowmanxxxxxxxxxxxx = _snowman.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(-1, new Integer[0]);
      OptionSpec<String> _snowmanxxxxxxxxxxxxx = _snowman.accepts("serverId").withRequiredArg();
      OptionSpec<String> _snowmanxxxxxxxxxxxxxx = _snowman.nonOptions();

      try {
         OptionSet _snowmanxxxxxxxxxxxxxxx = _snowman.parse(_snowman);
         if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxxxxx)) {
            _snowman.printHelpOn(System.err);
            return;
         }

         l.h();
         vm.a();
         vm.c();
         x.l();
         gn.b _snowmanxxxxxxxxxxxxxxxx = gn.b();
         Path _snowmanxxxxxxxxxxxxxxxxx = Paths.get("server.properties");
         zi _snowmanxxxxxxxxxxxxxxxxxx = new zi(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
         _snowmanxxxxxxxxxxxxxxxxxx.b();
         Path _snowmanxxxxxxxxxxxxxxxxxxx = Paths.get("eula.txt");
         vr _snowmanxxxxxxxxxxxxxxxxxxxx = new vr(_snowmanxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxx)) {
            a.info("Initialized '{}' and '{}'", _snowmanxxxxxxxxxxxxxxxxx.toAbsolutePath(), _snowmanxxxxxxxxxxxxxxxxxxx.toAbsolutePath());
            return;
         }

         if (!_snowmanxxxxxxxxxxxxxxxxxxxx.a()) {
            a.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            return;
         }

         File _snowmanxxxxxxxxxxxxxxxxxxxxx = new File((String)_snowmanxxxxxxxxxxxxxxx.valueOf(_snowmanxxxxxxxxxx));
         YggdrasilAuthenticationService _snowmanxxxxxxxxxxxxxxxxxxxxxx = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
         MinecraftSessionService _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.createMinecraftSessionService();
         GameProfileRepository _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.createProfileRepository();
         acq _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = new acq(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, new File(_snowmanxxxxxxxxxxxxxxxxxxxxx, MinecraftServer.b.getName()));
         String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (String)Optional.ofNullable(_snowmanxxxxxxxxxxxxxxx.valueOf(_snowmanxxxxxxxxxxx)).orElse(_snowmanxxxxxxxxxxxxxxxxxx.a().n);
         cyg _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = cyg.a(_snowmanxxxxxxxxxxxxxxxxxxxxx.toPath());
         cyg.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.c(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx);
         MinecraftServer.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         brk _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.e();
         boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
            a.warn("Safe mode active, only vanilla datapack will be loaded");
         }

         abw _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new abw(new abz(), new abt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(cye.g).toFile(), abx.c));
         brk _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MinecraftServer.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == null ? brk.a : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         CompletableFuture<vz> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = vz.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.f(), dc.a.b, _snowmanxxxxxxxxxxxxxxxxxx.a().G, x.f(), Runnable::run
         );

         vz _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         try {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.get();
         } catch (Exception var41) {
            a.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", var41);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.close();
            return;
         }

         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.i();
         vh<mt> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = vh.a(mo.a, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.h(), _snowmanxxxxxxxxxxxxxxxx);
         cyn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == null) {
            bsa _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            chw _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxxx)) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MinecraftServer.c;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = chw.a(_snowmanxxxxxxxxxxxxxxxx);
            } else {
               zh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.a();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new bsa(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.n,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.m,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.y,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.l,
                  false,
                  new brt(),
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxx)
                  ? _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.W.j()
                  : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.W;
            }

            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new cyl(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, Lifecycle.stable());
         }

         if (_snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxx)) {
            a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, agb.a(), _snowmanxxxxxxxxxxxxxxx.has(_snowmanxxxxxx), () -> true, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.A().f());
         }

         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         cyn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         final zg _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MinecraftServer.a((Function<Thread, zg>)(var16x -> {
            zg _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new zg(var16x, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, agb.a(), _snowman, _snowman, _snowman, aar::new);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d((String)_snowman.valueOf(_snowman));
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a((Integer)_snowman.valueOf(_snowman));
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.c(_snowman.has(_snowman));
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b((String)_snowman.valueOf(_snowman));
            boolean _snowmanx = !_snowman.has(_snowman) && !_snowman.valuesOf(_snowman).contains("nogui");
            if (_snowmanx && !GraphicsEnvironment.isHeadless()) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.bd();
            }

            return _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         }));
         Thread _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new Thread("Server Shutdown Thread") {
            @Override
            public void run() {
               _snowman.a(true);
            }
         };
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setUncaughtExceptionHandler(new o(a));
         Runtime.getRuntime().addShutdownHook(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
      } catch (Exception var42) {
         a.fatal("Failed to start the minecraft server", var42);
      }
   }

   private static void a(cyg.a var0, DataFixer var1, boolean var2, BooleanSupplier var3, ImmutableSet<vj<brx>> var4) {
      a.info("Forcing world upgrade!");
      aoi _snowman = new aoi(_snowman, _snowman, _snowman, _snowman);
      nr _snowmanx = null;

      while (!_snowman.b()) {
         nr _snowmanxx = _snowman.h();
         if (_snowmanx != _snowmanxx) {
            _snowmanx = _snowmanxx;
            a.info(_snowman.h().getString());
         }

         int _snowmanxxx = _snowman.e();
         if (_snowmanxxx > 0) {
            int _snowmanxxxx = _snowman.f() + _snowman.g();
            a.info("{}% completed ({} / {} chunks)...", afm.d((float)_snowmanxxxx / (float)_snowmanxxx * 100.0F), _snowmanxxxx, _snowmanxxx);
         }

         if (!_snowman.getAsBoolean()) {
            _snowman.a();
         } else {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var10) {
            }
         }
      }
   }
}
