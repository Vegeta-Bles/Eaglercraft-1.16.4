import com.google.common.base.Charsets;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class dyi implements acc {
   private static final List<dyk> b = ImmutableList.of(dyk.a, dyk.b, dyk.d, dyk.c, dyk.e);
   protected dwt a;
   private final Map<dyk, Queue<dyg>> c = Maps.newIdentityHashMap();
   private final Queue<dzd> d = Queues.newArrayDeque();
   private final ekd e;
   private final Random f = new Random();
   private final Int2ObjectMap<dyj<?>> g = new Int2ObjectOpenHashMap();
   private final Queue<dyg> h = Queues.newArrayDeque();
   private final Map<vk, dyi.a> i = Maps.newHashMap();
   private final ekb j = new ekb(ekb.e);

   public dyi(dwt var1, ekd var2) {
      _snowman.a(this.j.g(), this.j);
      this.a = _snowman;
      this.e = _snowman;
      this.e();
   }

   private void e() {
      this.a(hh.a, dyt.a::new);
      this.a(hh.b, dxx.a::new);
      this.a(hh.c, new dxg.a());
      this.a(hh.d, new dza.a());
      this.a(hh.e, dxk.a::new);
      this.a(hh.ad, dxj.a::new);
      this.a(hh.ab, dxl.a::new);
      this.a(hh.ag, dxm.a::new);
      this.a(hh.ah, dxm.b::new);
      this.a(hh.f, dyl.a::new);
      this.a(hh.F, dyz.a::new);
      this.a(hh.g, dxn.c::new);
      this.a(hh.ac, dzf.a::new);
      this.a(hh.h, dxn.a::new);
      this.a(hh.i, dxo.a::new);
      this.a(hh.af, dyz.b::new);
      this.a(hh.j, dxp.k::new);
      this.a(hh.k, dxp.j::new);
      this.a(hh.l, dxp.l::new);
      this.a(hh.m, dxp.r::new);
      this.a(hh.n, dxp.q::new);
      this.a(hh.o, dxq.a::new);
      this.a(hh.p, dyt.d::new);
      this.a(hh.q, new dyd.a());
      this.a(hh.r, dxn.b::new);
      this.a(hh.s, dxr.b::new);
      this.a(hh.t, dxs.a::new);
      this.a(hh.u, dyt.c::new);
      this.a(hh.v, new dxz.a());
      this.a(hh.w, dxy.a::new);
      this.a(hh.x, dxu.a::new);
      this.a(hh.y, dxv.d::new);
      this.a(hh.z, dze.a::new);
      this.a(hh.A, dxw.a::new);
      this.a(hh.C, dys.a::new);
      this.a(hh.B, dxw.a::new);
      this.a(hh.D, dxv.a::new);
      this.a(hh.E, dyz.c::new);
      this.a(hh.G, dxx.b::new);
      this.a(hh.H, dyt.b::new);
      this.a(hh.I, new dxi.a());
      this.a(hh.J, new dxi.b());
      this.a(hh.K, new dxi.c());
      this.a(hh.L, dyb.a::new);
      this.a(hh.M, dyc.a::new);
      this.a(hh.N, dyz.d::new);
      this.a(hh.ae, dxr.a::new);
      this.a(hh.O, dyf.a::new);
      this.a(hh.P, dxt.a::new);
      this.a(hh.Q, dym.a::new);
      this.a(hh.R, dzg.a::new);
      this.a(hh.S, dyr.a::new);
      this.a(hh.T, dyl.b::new);
      this.a(hh.U, dyu.a::new);
      this.a(hh.W, dxf.a::new);
      this.a(hh.X, dzc.a::new);
      this.a(hh.V, dyx.a::new);
      this.a(hh.Y, dyy.b::new);
      this.a(hh.Z, dyv.a::new);
      this.a(hh.aa, dyt.e::new);
      this.a(hh.ai, dxp.h::new);
      this.a(hh.aj, dxp.g::new);
      this.a(hh.ak, dxp.i::new);
      this.a(hh.al, dxp.m::new);
      this.a(hh.am, dxe.a::new);
      this.a(hh.an, dyy.a::new);
      this.a(hh.ao, dyy.c::new);
      this.a(hh.ap, dxp.o::new);
      this.a(hh.aq, dxp.n::new);
      this.a(hh.ar, dxp.p::new);
      this.a(hh.as, dyn.a::new);
      this.a(hh.at, dzh.a::new);
   }

   private <T extends hf> void a(hg<T> var1, dyj<T> var2) {
      this.g.put(gm.V.a(_snowman), _snowman);
   }

   private <T extends hf> void a(hg<T> var1, dyi.b<T> var2) {
      dyi.a _snowman = new dyi.a();
      this.i.put(gm.V.b(_snowman), _snowman);
      this.g.put(gm.V.a(_snowman), _snowman.create(_snowman));
   }

   @Override
   public CompletableFuture<Void> a(acc.a var1, ach var2, anw var3, anw var4, Executor var5, Executor var6) {
      Map<vk, List<vk>> _snowman = Maps.newConcurrentMap();
      CompletableFuture<?>[] _snowmanx = gm.V.c().stream().map(var4x -> CompletableFuture.runAsync(() -> this.a(_snowman, var4x, _snowman), _snowman)).toArray(CompletableFuture[]::new);
      return CompletableFuture.allOf(_snowmanx).thenApplyAsync(var4x -> {
         _snowman.a();
         _snowman.a("stitching");
         ekb.a _snowmanxx = this.j.a(_snowman, _snowman.values().stream().flatMap(Collection::stream), _snowman, 0);
         _snowman.c();
         _snowman.b();
         return _snowmanxx;
      }, _snowman).thenCompose(_snowman::a).thenAcceptAsync(var3x -> {
         this.c.clear();
         _snowman.a();
         _snowman.a("upload");
         this.j.a(var3x);
         _snowman.b("bindSpriteSets");
         ekc _snowmanxx = this.j.a(ejv.a());
         _snowman.forEach((var2x, var3xx) -> {
            ImmutableList<ekc> _snowmanx = var3xx.isEmpty() ? ImmutableList.of(_snowman) : var3xx.stream().map(this.j::a).collect(ImmutableList.toImmutableList());
            this.i.get(var2x).a(_snowmanx);
         });
         _snowman.c();
         _snowman.b();
      }, _snowman);
   }

   public void a() {
      this.j.f();
   }

   private void a(ach var1, vk var2, Map<vk, List<vk>> var3) {
      vk _snowman = new vk(_snowman.b(), "particles/" + _snowman.a() + ".json");

      try (
         acg _snowmanx = _snowman.a(_snowman);
         Reader _snowmanxx = new InputStreamReader(_snowmanx.b(), Charsets.UTF_8);
      ) {
         dyh _snowmanxxx = dyh.a(afd.a(_snowmanxx));
         List<vk> _snowmanxxxx = _snowmanxxx.a();
         boolean _snowmanxxxxx = this.i.containsKey(_snowman);
         if (_snowmanxxxx == null) {
            if (_snowmanxxxxx) {
               throw new IllegalStateException("Missing texture list for particle " + _snowman);
            }
         } else {
            if (!_snowmanxxxxx) {
               throw new IllegalStateException("Redundant texture list for particle " + _snowman);
            }

            _snowman.put(_snowman, _snowmanxxxx.stream().map(var0 -> new vk(var0.b(), "particle/" + var0.a())).collect(Collectors.toList()));
         }
      } catch (IOException var39) {
         throw new IllegalStateException("Failed to load description for particle " + _snowman, var39);
      }
   }

   public void a(aqa var1, hf var2) {
      this.d.add(new dzd(this.a, _snowman, _snowman));
   }

   public void a(aqa var1, hf var2, int var3) {
      this.d.add(new dzd(this.a, _snowman, _snowman, _snowman));
   }

   @Nullable
   public dyg a(hf var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      dyg _snowman = this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman != null) {
         this.a(_snowman);
         return _snowman;
      } else {
         return null;
      }
   }

   @Nullable
   private <T extends hf> dyg b(T var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      dyj<T> _snowman = (dyj<T>)this.g.get(gm.V.a(_snowman.b()));
      return _snowman == null ? null : _snowman.a(_snowman, this.a, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(dyg var1) {
      this.h.add(_snowman);
   }

   public void b() {
      this.c.forEach((var1x, var2) -> {
         this.a.Z().a(var1x.toString());
         this.a(var2);
         this.a.Z().c();
      });
      if (!this.d.isEmpty()) {
         List<dzd> _snowman = Lists.newArrayList();

         for (dzd _snowmanx : this.d) {
            _snowmanx.a();
            if (!_snowmanx.l()) {
               _snowman.add(_snowmanx);
            }
         }

         this.d.removeAll(_snowman);
      }

      dyg _snowman;
      if (!this.h.isEmpty()) {
         while ((_snowman = this.h.poll()) != null) {
            this.c.computeIfAbsent(_snowman.b(), var0 -> EvictingQueue.create(16384)).add(_snowman);
         }
      }
   }

   private void a(Collection<dyg> var1) {
      if (!_snowman.isEmpty()) {
         Iterator<dyg> _snowman = _snowman.iterator();

         while (_snowman.hasNext()) {
            dyg _snowmanx = _snowman.next();
            this.b(_snowmanx);
            if (!_snowmanx.l()) {
               _snowman.remove();
            }
         }
      }
   }

   private void b(dyg var1) {
      try {
         _snowman.a();
      } catch (Throwable var5) {
         l _snowman = l.a(var5, "Ticking Particle");
         m _snowmanx = _snowman.a("Particle being ticked");
         _snowmanx.a("Particle", _snowman::toString);
         _snowmanx.a("Particle Type", _snowman.b()::toString);
         throw new u(_snowman);
      }
   }

   public void a(dfm var1, eag.a var2, eaf var3, djk var4, float var5) {
      _snowman.c();
      RenderSystem.enableAlphaTest();
      RenderSystem.defaultAlphaFunc();
      RenderSystem.enableDepthTest();
      RenderSystem.enableFog();
      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(_snowman.c().a());

      for (dyk _snowman : b) {
         Iterable<dyg> _snowmanx = this.c.get(_snowman);
         if (_snowmanx != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            dfo _snowmanxx = dfo.a();
            dfh _snowmanxxx = _snowmanxx.c();
            _snowman.a(_snowmanxxx, this.e);

            for (dyg _snowmanxxxx : _snowmanx) {
               try {
                  _snowmanxxxx.a(_snowmanxxx, _snowman, _snowman);
               } catch (Throwable var16) {
                  l _snowmanxxxxx = l.a(var16, "Rendering Particle");
                  m _snowmanxxxxxx = _snowmanxxxxx.a("Particle being rendered");
                  _snowmanxxxxxx.a("Particle", _snowmanxxxx::toString);
                  _snowmanxxxxxx.a("Particle Type", _snowman::toString);
                  throw new u(_snowmanxxxxx);
               }
            }

            _snowman.a(_snowmanxx);
         }
      }

      RenderSystem.popMatrix();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
      RenderSystem.disableBlend();
      RenderSystem.defaultAlphaFunc();
      _snowman.b();
      RenderSystem.disableFog();
   }

   public void a(@Nullable dwt var1) {
      this.a = _snowman;
      this.c.clear();
      this.d.clear();
   }

   public void a(fx var1, ceh var2) {
      if (!_snowman.g()) {
         ddh _snowman = _snowman.j(this.a, _snowman);
         double _snowmanx = 0.25;
         _snowman.b(
            (var3x, var5, var7, var9, var11, var13) -> {
               double _snowmanxx = Math.min(1.0, var9 - var3x);
               double _snowmanx = Math.min(1.0, var11 - var5);
               double _snowmanxx = Math.min(1.0, var13 - var7);
               int _snowmanxxx = Math.max(2, afm.f(_snowmanxx / 0.25));
               int _snowmanxxxx = Math.max(2, afm.f(_snowmanx / 0.25));
               int _snowmanxxxxx = Math.max(2, afm.f(_snowmanxx / 0.25));

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxx; _snowmanxxxxxx++) {
                  for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxx; _snowmanxxxxxxx++) {
                     for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxx++) {
                        double _snowmanxxxxxxxxx = ((double)_snowmanxxxxxx + 0.5) / (double)_snowmanxxx;
                        double _snowmanxxxxxxxxxx = ((double)_snowmanxxxxxxx + 0.5) / (double)_snowmanxxxx;
                        double _snowmanxxxxxxxxxxx = ((double)_snowmanxxxxxxxx + 0.5) / (double)_snowmanxxxxx;
                        double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxx + var3x;
                        double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanx + var5;
                        double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * _snowmanxx + var7;
                        this.a(
                           new dza(
                                 this.a,
                                 (double)_snowman.u() + _snowmanxxxxxxxxxxxx,
                                 (double)_snowman.v() + _snowmanxxxxxxxxxxxxx,
                                 (double)_snowman.w() + _snowmanxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxx - 0.5,
                                 _snowmanxxxxxxxxxx - 0.5,
                                 _snowmanxxxxxxxxxxx - 0.5,
                                 _snowman
                              )
                              .a(_snowman)
                        );
                     }
                  }
               }
            }
         );
      }
   }

   public void a(fx var1, gc var2) {
      ceh _snowman = this.a.d_(_snowman);
      if (_snowman.h() != bzh.a) {
         int _snowmanx = _snowman.u();
         int _snowmanxx = _snowman.v();
         int _snowmanxxx = _snowman.w();
         float _snowmanxxxx = 0.1F;
         dci _snowmanxxxxx = _snowman.j(this.a, _snowman).a();
         double _snowmanxxxxxx = (double)_snowmanx + this.f.nextDouble() * (_snowmanxxxxx.d - _snowmanxxxxx.a - 0.2F) + 0.1F + _snowmanxxxxx.a;
         double _snowmanxxxxxxx = (double)_snowmanxx + this.f.nextDouble() * (_snowmanxxxxx.e - _snowmanxxxxx.b - 0.2F) + 0.1F + _snowmanxxxxx.b;
         double _snowmanxxxxxxxx = (double)_snowmanxxx + this.f.nextDouble() * (_snowmanxxxxx.f - _snowmanxxxxx.c - 0.2F) + 0.1F + _snowmanxxxxx.c;
         if (_snowman == gc.a) {
            _snowmanxxxxxxx = (double)_snowmanxx + _snowmanxxxxx.b - 0.1F;
         }

         if (_snowman == gc.b) {
            _snowmanxxxxxxx = (double)_snowmanxx + _snowmanxxxxx.e + 0.1F;
         }

         if (_snowman == gc.c) {
            _snowmanxxxxxxxx = (double)_snowmanxxx + _snowmanxxxxx.c - 0.1F;
         }

         if (_snowman == gc.d) {
            _snowmanxxxxxxxx = (double)_snowmanxxx + _snowmanxxxxx.f + 0.1F;
         }

         if (_snowman == gc.e) {
            _snowmanxxxxxx = (double)_snowmanx + _snowmanxxxxx.a - 0.1F;
         }

         if (_snowman == gc.f) {
            _snowmanxxxxxx = (double)_snowmanx + _snowmanxxxxx.d + 0.1F;
         }

         this.a(new dza(this.a, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0.0, 0.0, 0.0, _snowman).a(_snowman).c(0.2F).d(0.6F));
      }
   }

   public String d() {
      return String.valueOf(this.c.values().stream().mapToInt(Collection::size).sum());
   }

   class a implements dyw {
      private List<ekc> b;

      private a() {
      }

      @Override
      public ekc a(int var1, int var2) {
         return this.b.get(_snowman * (this.b.size() - 1) / _snowman);
      }

      @Override
      public ekc a(Random var1) {
         return this.b.get(_snowman.nextInt(this.b.size()));
      }

      public void a(List<ekc> var1) {
         this.b = ImmutableList.copyOf(_snowman);
      }
   }

   @FunctionalInterface
   interface b<T extends hf> {
      dyj<T> create(dyw var1);
   }
}
