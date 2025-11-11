import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class enr {
   private static final Marker a = MarkerManager.getMarker("SOUNDS");
   private static final Logger b = LogManager.getLogger();
   private static final Set<vk> c = Sets.newHashSet();
   private final enu d;
   private final dkd e;
   private boolean f;
   private final ddv g = new ddv();
   private final ddw h = this.g.c();
   private final enq i;
   private final ens j = new ens();
   private final enn k = new enn(this.g, this.j);
   private int l;
   private final Map<emt, enn.a> m = Maps.newHashMap();
   private final Multimap<adr, emt> n = HashMultimap.create();
   private final List<emu> o = Lists.newArrayList();
   private final Map<emt, Integer> p = Maps.newHashMap();
   private final Map<emt, Integer> q = Maps.newHashMap();
   private final List<ent> r = Lists.newArrayList();
   private final List<emu> s = Lists.newArrayList();
   private final List<emq> t = Lists.newArrayList();

   public enr(enu var1, dkd var2, ach var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.i = new enq(_snowman);
   }

   public void a() {
      c.clear();

      for (adp _snowman : gm.N) {
         vk _snowmanx = _snowman.a();
         if (this.d.a(_snowmanx) == null) {
            b.warn("Missing sound for event: {}", gm.N.b(_snowman));
            c.add(_snowmanx);
         }
      }

      this.b();
      this.g();
   }

   private synchronized void g() {
      if (!this.f) {
         try {
            this.g.a();
            this.h.c();
            this.h.a(this.e.a(adr.a));
            this.i.a(this.t).thenRun(this.t::clear);
            this.f = true;
            b.info(a, "Sound engine started");
         } catch (RuntimeException var2) {
            b.error(a, "Error starting SoundSystem. Turning off sounds & music", var2);
         }
      }
   }

   private float a(@Nullable adr var1) {
      return _snowman != null && _snowman != adr.a ? this.e.a(_snowman) : 1.0F;
   }

   public void a(adr var1, float var2) {
      if (this.f) {
         if (_snowman == adr.a) {
            this.h.a(_snowman);
         } else {
            this.m.forEach((var1x, var2x) -> {
               float _snowman = this.h(var1x);
               var2x.a(var1xx -> {
                  if (_snowman <= 0.0F) {
                     var1xx.f();
                  } else {
                     var1xx.b(_snowman);
                  }
               });
            });
         }
      }
   }

   public void b() {
      if (this.f) {
         this.c();
         this.i.a();
         this.g.b();
         this.f = false;
      }
   }

   public void a(emt var1) {
      if (this.f) {
         enn.a _snowman = this.m.get(_snowman);
         if (_snowman != null) {
            _snowman.a(ddu::f);
         }
      }
   }

   public void c() {
      if (this.f) {
         this.j.a();
         this.m.values().forEach(var0 -> var0.a(ddu::f));
         this.m.clear();
         this.k.b();
         this.p.clear();
         this.o.clear();
         this.n.clear();
         this.q.clear();
         this.s.clear();
      }
   }

   public void a(ent var1) {
      this.r.add(_snowman);
   }

   public void b(ent var1) {
      this.r.remove(_snowman);
   }

   public void a(boolean var1) {
      if (!_snowman) {
         this.h();
      }

      this.k.a();
   }

   private void h() {
      this.l++;
      this.s.stream().filter(emt::t).forEach(this::c);
      this.s.clear();

      for (emu _snowman : this.o) {
         if (!_snowman.t()) {
            this.a((emt)_snowman);
         }

         _snowman.r();
         if (_snowman.n()) {
            this.a((emt)_snowman);
         } else {
            float _snowmanx = this.h(_snowman);
            float _snowmanxx = this.g(_snowman);
            dcn _snowmanxxx = new dcn(_snowman.h(), _snowman.i(), _snowman.j());
            enn.a _snowmanxxxx = this.m.get(_snowman);
            if (_snowmanxxxx != null) {
               _snowmanxxxx.a(var3x -> {
                  var3x.b(_snowman);
                  var3x.a(_snowman);
                  var3x.a(_snowman);
               });
            }
         }
      }

      Iterator<Entry<emt, enn.a>> _snowman = this.m.entrySet().iterator();

      while (_snowman.hasNext()) {
         Entry<emt, enn.a> _snowmanx = _snowman.next();
         enn.a _snowmanxx = _snowmanx.getValue();
         emt _snowmanxxx = _snowmanx.getKey();
         float _snowmanxxxx = this.e.a(_snowmanxxx.c());
         if (_snowmanxxxx <= 0.0F) {
            _snowmanxx.a(ddu::f);
            _snowman.remove();
         } else if (_snowmanxx.a()) {
            int _snowmanxxxxx = this.q.get(_snowmanxxx);
            if (_snowmanxxxxx <= this.l) {
               if (e(_snowmanxxx)) {
                  this.p.put(_snowmanxxx, this.l + _snowmanxxx.e());
               }

               _snowman.remove();
               b.debug(a, "Removed channel {} because it's not playing anymore", _snowmanxx);
               this.q.remove(_snowmanxxx);

               try {
                  this.n.remove(_snowmanxxx.c(), _snowmanxxx);
               } catch (RuntimeException var8) {
               }

               if (_snowmanxxx instanceof emu) {
                  this.o.remove(_snowmanxxx);
               }
            }
         }
      }

      Iterator<Entry<emt, Integer>> _snowmanx = this.p.entrySet().iterator();

      while (_snowmanx.hasNext()) {
         Entry<emt, Integer> _snowmanxx = _snowmanx.next();
         if (this.l >= _snowmanxx.getValue()) {
            emt _snowmanxxx = _snowmanxx.getKey();
            if (_snowmanxxx instanceof emu) {
               ((emu)_snowmanxxx).r();
            }

            this.c(_snowmanxxx);
            _snowmanx.remove();
         }
      }
   }

   private static boolean d(emt var0) {
      return _snowman.e() > 0;
   }

   private static boolean e(emt var0) {
      return _snowman.d() && d(_snowman);
   }

   private static boolean f(emt var0) {
      return _snowman.d() && !d(_snowman);
   }

   public boolean b(emt var1) {
      if (!this.f) {
         return false;
      } else {
         return this.q.containsKey(_snowman) && this.q.get(_snowman) <= this.l ? true : this.m.containsKey(_snowman);
      }
   }

   public void c(emt var1) {
      if (this.f) {
         if (_snowman.t()) {
            env _snowman = _snowman.a(this.d);
            vk _snowmanx = _snowman.a();
            if (_snowman == null) {
               if (c.add(_snowmanx)) {
                  b.warn(a, "Unable to play unknown soundEvent: {}", _snowmanx);
               }
            } else {
               emq _snowmanxx = _snowman.b();
               if (_snowmanxx == enu.a) {
                  if (c.add(_snowmanx)) {
                     b.warn(a, "Unable to play empty soundEvent: {}", _snowmanx);
                  }
               } else {
                  float _snowmanxxx = _snowman.f();
                  float _snowmanxxxx = Math.max(_snowmanxxx, 1.0F) * (float)_snowmanxx.j();
                  adr _snowmanxxxxx = _snowman.c();
                  float _snowmanxxxxxx = this.h(_snowman);
                  float _snowmanxxxxxxx = this.g(_snowman);
                  emt.a _snowmanxxxxxxxx = _snowman.k();
                  boolean _snowmanxxxxxxxxx = _snowman.m();
                  if (_snowmanxxxxxx == 0.0F && !_snowman.s()) {
                     b.debug(a, "Skipped playing sound {}, volume was zero.", _snowmanxx.a());
                  } else {
                     dcn _snowmanxxxxxxxxxx = new dcn(_snowman.h(), _snowman.i(), _snowman.j());
                     if (!this.r.isEmpty()) {
                        boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx || _snowmanxxxxxxxx == emt.a.a || this.h.a().g(_snowmanxxxxxxxxxx) < (double)(_snowmanxxxx * _snowmanxxxx);
                        if (_snowmanxxxxxxxxxxx) {
                           for (ent _snowmanxxxxxxxxxxxx : this.r) {
                              _snowmanxxxxxxxxxxxx.a(_snowman, _snowman);
                           }
                        } else {
                           b.debug(a, "Did not notify listeners of soundEvent: {}, it is too far away to hear", _snowmanx);
                        }
                     }

                     if (this.h.b() <= 0.0F) {
                        b.debug(a, "Skipped playing soundEvent: {}, master volume was zero", _snowmanx);
                     } else {
                        boolean _snowmanxxxxxxxxxxx = f(_snowman);
                        boolean _snowmanxxxxxxxxxxxx = _snowmanxx.h();
                        CompletableFuture<enn.a> _snowmanxxxxxxxxxxxxx = this.k.a(_snowmanxx.h() ? ddv.c.b : ddv.c.a);
                        enn.a _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.join();
                        if (_snowmanxxxxxxxxxxxxxx == null) {
                           b.warn("Failed to create new sound handle");
                        } else {
                           b.debug(a, "Playing sound {} for event {}", _snowmanxx.a(), _snowmanx);
                           this.q.put(_snowman, this.l + 20);
                           this.m.put(_snowman, _snowmanxxxxxxxxxxxxxx);
                           this.n.put(_snowmanxxxxx, _snowman);
                           _snowmanxxxxxxxxxxxxxx.a(var8x -> {
                              var8x.a(_snowman);
                              var8x.b(_snowman);
                              if (_snowman == emt.a.b) {
                                 var8x.c(_snowman);
                              } else {
                                 var8x.h();
                              }

                              var8x.a(_snowman && !_snowman);
                              var8x.a(_snowman);
                              var8x.b(_snowman);
                           });
                           if (!_snowmanxxxxxxxxxxxx) {
                              this.i.a(_snowmanxx.b()).thenAccept(var1x -> _snowman.a(var1xx -> {
                                    var1xx.a(var1x);
                                    var1xx.c();
                                 }));
                           } else {
                              this.i.a(_snowmanxx.b(), _snowmanxxxxxxxxxxx).thenAccept(var1x -> _snowman.a(var1xx -> {
                                    var1xx.a(var1x);
                                    var1xx.c();
                                 }));
                           }

                           if (_snowman instanceof emu) {
                              this.o.add((emu)_snowman);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void a(emu var1) {
      this.s.add(_snowman);
   }

   public void a(emq var1) {
      this.t.add(_snowman);
   }

   private float g(emt var1) {
      return afm.a(_snowman.g(), 0.5F, 2.0F);
   }

   private float h(emt var1) {
      return afm.a(_snowman.f() * this.a(_snowman.c()), 0.0F, 1.0F);
   }

   public void d() {
      if (this.f) {
         this.k.a(var0 -> var0.forEach(ddu::d));
      }
   }

   public void e() {
      if (this.f) {
         this.k.a(var0 -> var0.forEach(ddu::e));
      }
   }

   public void a(emt var1, int var2) {
      this.p.put(_snowman, this.l + _snowman);
   }

   public void a(djk var1) {
      if (this.f && _snowman.h()) {
         dcn _snowman = _snowman.b();
         g _snowmanx = _snowman.l();
         g _snowmanxx = _snowman.m();
         this.j.execute(() -> {
            this.h.a(_snowman);
            this.h.a(_snowman, _snowman);
         });
      }
   }

   public void a(@Nullable vk var1, @Nullable adr var2) {
      if (_snowman != null) {
         for (emt _snowman : this.n.get(_snowman)) {
            if (_snowman == null || _snowman.a().equals(_snowman)) {
               this.a(_snowman);
            }
         }
      } else if (_snowman == null) {
         this.c();
      } else {
         for (emt _snowmanx : this.m.keySet()) {
            if (_snowmanx.a().equals(_snowman)) {
               this.a(_snowmanx);
            }
         }
      }
   }

   public String f() {
      return this.g.d();
   }
}
