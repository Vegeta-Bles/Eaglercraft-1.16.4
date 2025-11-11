import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ekb extends ejq implements eke {
   private static final Logger f = LogManager.getLogger();
   @Deprecated
   public static final vk d = biz.c;
   @Deprecated
   public static final vk e = new vk("textures/atlas/particles.png");
   private final List<ekc> g = Lists.newArrayList();
   private final Set<vk> h = Sets.newHashSet();
   private final Map<vk, ekc> i = Maps.newHashMap();
   private final vk j;
   private final int k;

   public ekb(vk var1) {
      this.j = _snowman;
      this.k = RenderSystem.maxSupportedTextureSize();
   }

   @Override
   public void a(ach var1) throws IOException {
   }

   public void a(ekb.a var1) {
      this.h.clear();
      this.h.addAll(_snowman.a);
      f.info("Created: {}x{}x{} {}-atlas", _snowman.b, _snowman.c, _snowman.d, this.j);
      dex.a(this.b(), _snowman.d, _snowman.b, _snowman.c);
      this.f();

      for (ekc _snowman : _snowman.e) {
         this.i.put(_snowman.l(), _snowman);

         try {
            _snowman.o();
         } catch (Throwable var7) {
            l _snowmanx = l.a(var7, "Stitching texture atlas");
            m _snowmanxx = _snowmanx.a("Texture being stitched together");
            _snowmanxx.a("Atlas path", this.j);
            _snowmanxx.a("Sprite", _snowman);
            throw new u(_snowmanx);
         }

         if (_snowman.r()) {
            this.g.add(_snowman);
         }
      }
   }

   public ekb.a a(ach var1, Stream<vk> var2, anw var3, int var4) {
      _snowman.a("preparing");
      Set<vk> _snowman = _snowman.peek(var0 -> {
         if (var0 == null) {
            throw new IllegalArgumentException("Location cannot be null!");
         }
      }).collect(Collectors.toSet());
      int _snowmanx = this.k;
      ejz _snowmanxx = new ejz(_snowmanx, _snowmanx, _snowman);
      int _snowmanxxx = Integer.MAX_VALUE;
      int _snowmanxxxx = 1 << _snowman;
      _snowman.b("extracting_frames");

      for (ekc.a _snowmanxxxxx : this.a(_snowman, _snowman)) {
         _snowmanxxx = Math.min(_snowmanxxx, Math.min(_snowmanxxxxx.b(), _snowmanxxxxx.c()));
         int _snowmanxxxxxx = Math.min(Integer.lowestOneBit(_snowmanxxxxx.b()), Integer.lowestOneBit(_snowmanxxxxx.c()));
         if (_snowmanxxxxxx < _snowmanxxxx) {
            f.warn("Texture {} with size {}x{} limits mip level from {} to {}", _snowmanxxxxx.a(), _snowmanxxxxx.b(), _snowmanxxxxx.c(), afm.f(_snowmanxxxx), afm.f(_snowmanxxxxxx));
            _snowmanxxxx = _snowmanxxxxxx;
         }

         _snowmanxx.a(_snowmanxxxxx);
      }

      int _snowmanxxxxx = Math.min(_snowmanxxx, _snowmanxxxx);
      int _snowmanxxxxxx = afm.f(_snowmanxxxxx);
      int _snowmanxxxxxxx;
      if (_snowmanxxxxxx < _snowman) {
         f.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.j, _snowman, _snowmanxxxxxx, _snowmanxxxxx);
         _snowmanxxxxxxx = _snowmanxxxxxx;
      } else {
         _snowmanxxxxxxx = _snowman;
      }

      _snowman.b("register");
      _snowmanxx.a(ejv.b());
      _snowman.b("stitching");

      try {
         _snowmanxx.c();
      } catch (eka var16) {
         l _snowmanxxxxxxxx = l.a(var16, "Stitching");
         m _snowmanxxxxxxxxx = _snowmanxxxxxxxx.a("Stitcher");
         _snowmanxxxxxxxxx.a("Sprites", var16.a().stream().map(var0 -> String.format("%s[%dx%d]", var0.a(), var0.b(), var0.c())).collect(Collectors.joining(",")));
         _snowmanxxxxxxxxx.a("Max Texture Size", _snowmanx);
         throw new u(_snowmanxxxxxxxx);
      }

      _snowman.b("loading");
      List<ekc> _snowmanxxxxxxxx = this.a(_snowman, _snowmanxx, _snowmanxxxxxxx);
      _snowman.c();
      return new ekb.a(_snowman, _snowmanxx.a(), _snowmanxx.b(), _snowmanxxxxxxx, _snowmanxxxxxxxx);
   }

   private Collection<ekc.a> a(ach var1, Set<vk> var2) {
      List<CompletableFuture<?>> _snowman = Lists.newArrayList();
      ConcurrentLinkedQueue<ekc.a> _snowmanx = new ConcurrentLinkedQueue<>();

      for (vk _snowmanxx : _snowman) {
         if (!ejv.a().equals(_snowmanxx)) {
            _snowman.add(CompletableFuture.runAsync(() -> {
               vk _snowmanxxx = this.b(_snowman);

               ekc.a _snowmanx;
               try (acg _snowmanxx = _snowman.a(_snowmanxxx)) {
                  deu _snowmanxxx = new deu(_snowmanxx.toString(), _snowmanxx.b());
                  elc _snowmanxxxx = _snowmanxx.a(elc.a);
                  if (_snowmanxxxx == null) {
                     _snowmanxxxx = elc.b;
                  }

                  Pair<Integer, Integer> _snowmanxxxxx = _snowmanxxxx.a(_snowmanxxx.a, _snowmanxxx.b);
                  _snowmanx = new ekc.a(_snowman, (Integer)_snowmanxxxxx.getFirst(), (Integer)_snowmanxxxxx.getSecond(), _snowmanxxxx);
               } catch (RuntimeException var22) {
                  f.error("Unable to parse metadata from {} : {}", _snowmanxxx, var22);
                  return;
               } catch (IOException var23) {
                  f.error("Using missing texture, unable to load {} : {}", _snowmanxxx, var23);
                  return;
               }

               _snowman.add(_snowmanx);
            }, x.f()));
         }
      }

      CompletableFuture.allOf(_snowman.toArray(new CompletableFuture[0])).join();
      return _snowmanx;
   }

   private List<ekc> a(ach var1, ejz var2, int var3) {
      ConcurrentLinkedQueue<ekc> _snowman = new ConcurrentLinkedQueue<>();
      List<CompletableFuture<?>> _snowmanx = Lists.newArrayList();
      _snowman.a((var5x, var6, var7, var8, var9) -> {
         if (var5x == ejv.b()) {
            ejv _snowmanxx = ejv.a(this, _snowman, var6, var7, var8, var9);
            _snowman.add(_snowmanxx);
         } else {
            _snowman.add(CompletableFuture.runAsync(() -> {
               ekc _snowmanxx = this.a(_snowman, var5x, var6, var7, _snowman, var8, var9);
               if (_snowmanxx != null) {
                  _snowman.add(_snowmanxx);
               }
            }, x.f()));
         }
      });
      CompletableFuture.allOf(_snowmanx.toArray(new CompletableFuture[0])).join();
      return Lists.newArrayList(_snowman);
   }

   @Nullable
   private ekc a(ach var1, ekc.a var2, int var3, int var4, int var5, int var6, int var7) {
      vk _snowman = this.b(_snowman.a());

      try (acg _snowmanx = _snowman.a(_snowman)) {
         det _snowmanxx = det.a(_snowmanx.b());
         return new ekc(this, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxx);
      } catch (RuntimeException var25) {
         f.error("Unable to parse metadata from {}", _snowman, var25);
         return null;
      } catch (IOException var26) {
         f.error("Using missing texture, unable to load {}", _snowman, var26);
         return null;
      }
   }

   private vk b(vk var1) {
      return new vk(_snowman.b(), String.format("textures/%s%s", _snowman.a(), ".png"));
   }

   @Override
   public void a() {
      this.d();

      for (ekc _snowman : this.g) {
         _snowman.q();
      }
   }

   @Override
   public void e() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::a);
      } else {
         this.a();
      }
   }

   public ekc a(vk var1) {
      ekc _snowman = this.i.get(_snowman);
      return _snowman == null ? this.i.get(ejv.a()) : _snowman;
   }

   public void f() {
      for (ekc _snowman : this.i.values()) {
         _snowman.close();
      }

      this.i.clear();
      this.g.clear();
   }

   public vk g() {
      return this.j;
   }

   public void b(ekb.a var1) {
      this.a(false, _snowman.d > 0);
   }

   public static class a {
      final Set<vk> a;
      final int b;
      final int c;
      final int d;
      final List<ekc> e;

      public a(Set<vk> var1, int var2, int var3, int var4, List<ekc> var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }
   }
}
