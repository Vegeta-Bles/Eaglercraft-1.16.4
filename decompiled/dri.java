import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dri extends dot {
   private static final Logger a = LogManager.getLogger();
   private static final nr b = new of("pack.dropInfo").a(k.h);
   private static final nr c = new of("pack.folderInfo");
   private static final vk p = new vk("textures/misc/unknown_pack.png");
   private final drh q;
   private final dot r;
   @Nullable
   private dri.a s;
   private long t;
   private drj u;
   private drj v;
   private final File w;
   private dlj x;
   private final Map<String, vk> y = Maps.newHashMap();

   public dri(dot var1, abw var2, Consumer<abw> var3, File var4, nr var5) {
      super(_snowman);
      this.r = _snowman;
      this.q = new drh(this::k, this::a, _snowman, _snowman);
      this.w = _snowman;
      this.s = dri.a.a(_snowman);
   }

   @Override
   public void at_() {
      this.q.c();
      this.i.a(this.r);
      this.i();
   }

   private void i() {
      if (this.s != null) {
         try {
            this.s.close();
            this.s = null;
         } catch (Exception var2) {
         }
      }
   }

   @Override
   protected void b() {
      this.x = this.a((dlj)(new dlj(this.k / 2 + 4, this.l - 48, 150, 20, nq.c, var1 -> this.at_())));
      this.a(
         (dlj)(new dlj(
            this.k / 2 - 154, this.l - 48, 150, 20, new of("pack.openFolder"), var1 -> x.i().a(this.w), (var1, var2, var3, var4) -> this.b(var2, c, var3, var4)
         ))
      );
      this.u = new drj(this.i, 200, this.l, new of("pack.available.title"));
      this.u.g(this.k / 2 - 4 - 200);
      this.e.add(this.u);
      this.v = new drj(this.i, 200, this.l, new of("pack.selected.title"));
      this.v.g(this.k / 2 + 4);
      this.e.add(this.v);
      this.l();
   }

   @Override
   public void d() {
      if (this.s != null) {
         try {
            if (this.s.a()) {
               this.t = 20L;
            }
         } catch (IOException var2) {
            a.warn("Failed to poll for directory {} changes, stopping", this.w);
            this.i();
         }
      }

      if (this.t > 0L && --this.t == 0L) {
         this.l();
      }
   }

   private void k() {
      this.a(this.v, this.q.b());
      this.a(this.u, this.q.a());
      this.x.o = !this.v.au_().isEmpty();
   }

   private void a(drj var1, Stream<drh.a> var2) {
      _snowman.au_().clear();
      _snowman.forEach(var2x -> _snowman.au_().add(new drj.a(this.i, _snowman, this, var2x)));
   }

   private void l() {
      this.q.d();
      this.k();
      this.t = 0L;
      this.y.clear();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.e(0);
      this.u.a(_snowman, _snowman, _snowman, _snowman);
      this.v.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 8, 16777215);
      a(_snowman, this.o, b, this.k / 2, 20, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   protected static void a(djz var0, List<Path> var1, Path var2) {
      MutableBoolean _snowman = new MutableBoolean();
      _snowman.forEach(var2x -> {
         try (Stream<Path> _snowmanx = Files.walk(var2x)) {
            _snowmanx.forEach(var3x -> {
               try {
                  x.b(var2x.getParent(), _snowman, var3x);
               } catch (IOException var5) {
                  a.warn("Failed to copy datapack file  from {} to {}", var3x, _snowman, var5);
                  _snowman.setTrue();
               }
            });
         } catch (IOException var16) {
            a.warn("Failed to copy datapack file from {} to {}", var2x, _snowman);
            _snowman.setTrue();
         }
      });
      if (_snowman.isTrue()) {
         dmp.c(_snowman, _snowman.toString());
      }
   }

   @Override
   public void a(List<Path> var1) {
      String _snowman = _snowman.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
      this.i.a(new dns(var2x -> {
         if (var2x) {
            a(this.i, _snowman, this.w.toPath());
            this.l();
         }

         this.i.a(this);
      }, new of("pack.dropConfirm"), new oe(_snowman)));
   }

   private vk a(ekd var1, abu var2) {
      try (
         abj _snowman = _snowman.d();
         InputStream _snowmanx = _snowman.b("pack.png");
      ) {
         String _snowmanxx = _snowman.e();
         vk _snowmanxxx = new vk("minecraft", "pack/" + x.a(_snowmanxx, vk::b) + "/" + Hashing.sha1().hashUnencodedChars(_snowmanxx) + "/icon");
         det _snowmanxxxx = det.a(_snowmanx);
         _snowman.a(_snowmanxxx, new ejs(_snowmanxxxx));
         return _snowmanxxx;
      } catch (FileNotFoundException var41) {
      } catch (Exception var42) {
         a.warn("Failed to load icon from pack {}", _snowman.e(), var42);
      }

      return p;
   }

   private vk a(abu var1) {
      return this.y.computeIfAbsent(_snowman.e(), var2 -> this.a(this.i.M(), _snowman));
   }

   static class a implements AutoCloseable {
      private final WatchService a;
      private final Path b;

      public a(File var1) throws IOException {
         this.b = _snowman.toPath();
         this.a = this.b.getFileSystem().newWatchService();

         try {
            this.a(this.b);

            try (DirectoryStream<Path> _snowman = Files.newDirectoryStream(this.b)) {
               for (Path _snowmanx : _snowman) {
                  if (Files.isDirectory(_snowmanx, LinkOption.NOFOLLOW_LINKS)) {
                     this.a(_snowmanx);
                  }
               }
            }
         } catch (Exception var16) {
            this.a.close();
            throw var16;
         }
      }

      @Nullable
      public static dri.a a(File var0) {
         try {
            return new dri.a(_snowman);
         } catch (IOException var2) {
            dri.a.warn("Failed to initialize pack directory {} monitoring", _snowman, var2);
            return null;
         }
      }

      private void a(Path var1) throws IOException {
         _snowman.register(this.a, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
      }

      public boolean a() throws IOException {
         boolean _snowman = false;

         WatchKey _snowmanx;
         while ((_snowmanx = this.a.poll()) != null) {
            for (WatchEvent<?> _snowmanxx : _snowmanx.pollEvents()) {
               _snowman = true;
               if (_snowmanx.watchable() == this.b && _snowmanxx.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  Path _snowmanxxx = this.b.resolve((Path)_snowmanxx.context());
                  if (Files.isDirectory(_snowmanxxx, LinkOption.NOFOLLOW_LINKS)) {
                     this.a(_snowmanxxx);
                  }
               }
            }

            _snowmanx.reset();
         }

         return _snowman;
      }

      @Override
      public void close() throws IOException {
         this.a.close();
      }
   }
}
