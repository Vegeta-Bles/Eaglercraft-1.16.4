import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cpf {
   private static final Logger b = LogManager.getLogger();
   public static final Codec<cpf> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  vg.a(gm.ay).forGetter(var0x -> var0x.d),
                  chv.a.fieldOf("structures").forGetter(cpf::d),
                  cpe.a.listOf().fieldOf("layers").forGetter(cpf::f),
                  Codec.BOOL.fieldOf("lakes").orElse(false).forGetter(var0x -> var0x.k),
                  Codec.BOOL.fieldOf("features").orElse(false).forGetter(var0x -> var0x.j),
                  bsv.d.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(var0x -> Optional.of(var0x.g))
               )
               .apply(var0, cpf::new)
      )
      .stable();
   private static final Map<cla<?>, ciw<?, ?>> c = x.a(Maps.newHashMap(), var0 -> {
      var0.put(cla.c, ko.b);
      var0.put(cla.q, ko.t);
      var0.put(cla.k, ko.k);
      var0.put(cla.j, ko.j);
      var0.put(cla.f, ko.f);
      var0.put(cla.e, ko.e);
      var0.put(cla.g, ko.g);
      var0.put(cla.m, ko.m);
      var0.put(cla.i, ko.h);
      var0.put(cla.l, ko.l);
      var0.put(cla.o, ko.q);
      var0.put(cla.d, ko.d);
      var0.put(cla.n, ko.o);
      var0.put(cla.b, ko.a);
      var0.put(cla.h, ko.y);
      var0.put(cla.s, ko.s);
   });
   private final gm<bsv> d;
   private final chv e;
   private final List<cpe> f = Lists.newArrayList();
   private Supplier<bsv> g;
   private final ceh[] h = new ceh[256];
   private boolean i;
   private boolean j = false;
   private boolean k = false;

   public cpf(gm<bsv> var1, chv var2, List<cpe> var3, boolean var4, boolean var5, Optional<Supplier<bsv>> var6) {
      this(_snowman, _snowman);
      if (_snowman) {
         this.b();
      }

      if (_snowman) {
         this.a();
      }

      this.f.addAll(_snowman);
      this.h();
      if (!_snowman.isPresent()) {
         b.error("Unknown biome, defaulting to plains");
         this.g = () -> _snowman.d(btb.b);
      } else {
         this.g = _snowman.get();
      }
   }

   public cpf(chv var1, gm<bsv> var2) {
      this.d = _snowman;
      this.e = _snowman;
      this.g = () -> _snowman.d(btb.b);
   }

   public cpf a(chv var1) {
      return this.a(this.f, _snowman);
   }

   public cpf a(List<cpe> var1, chv var2) {
      cpf _snowman = new cpf(_snowman, this.d);

      for (cpe _snowmanx : _snowman) {
         _snowman.f.add(new cpe(_snowmanx.a(), _snowmanx.b().b()));
         _snowman.h();
      }

      _snowman.a(this.g);
      if (this.j) {
         _snowman.a();
      }

      if (this.k) {
         _snowman.b();
      }

      return _snowman;
   }

   public void a() {
      this.j = true;
   }

   public void b() {
      this.k = true;
   }

   public bsv c() {
      bsv _snowman = this.e();
      bsw _snowmanx = _snowman.e();
      bsw.a _snowmanxx = new bsw.a().a(_snowmanx.d());
      if (this.k) {
         _snowmanxx.a(chm.b.b, kh.O);
         _snowmanxx.a(chm.b.b, kh.P);
      }

      for (Entry<cla<?>, cmy> _snowmanxxx : this.e.a().entrySet()) {
         _snowmanxx.a(_snowmanx.a(c.get(_snowmanxxx.getKey())));
      }

      boolean _snowmanxxx = (!this.i || this.d.c(_snowman).equals(Optional.of(btb.Z))) && this.j;
      if (_snowmanxxx) {
         List<List<Supplier<civ<?, ?>>>> _snowmanxxxx = _snowmanx.c();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
            if (_snowmanxxxxx != chm.b.d.ordinal() && _snowmanxxxxx != chm.b.e.ordinal()) {
               for (Supplier<civ<?, ?>> _snowmanxxxxxx : _snowmanxxxx.get(_snowmanxxxxx)) {
                  _snowmanxx.a(_snowmanxxxxx, _snowmanxxxxxx);
               }
            }
         }
      }

      ceh[] _snowmanxxxx = this.g();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx.length; _snowmanxxxxxx++) {
         ceh _snowmanxxxxxxx = _snowmanxxxx[_snowmanxxxxxx];
         if (_snowmanxxxxxxx != null && !chn.a.e.e().test(_snowmanxxxxxxx)) {
            this.h[_snowmanxxxxxx] = null;
            _snowmanxx.a(chm.b.j, cjl.T.b(new cmd(_snowmanxxxxxx, _snowmanxxxxxxx)));
         }
      }

      return new bsv.a().a(_snowman.c()).a(_snowman.t()).a(_snowman.h()).b(_snowman.j()).c(_snowman.k()).d(_snowman.i()).a(_snowman.l()).a(_snowmanxx.a()).a(_snowman.b()).a();
   }

   public chv d() {
      return this.e;
   }

   public bsv e() {
      return this.g.get();
   }

   public void a(Supplier<bsv> var1) {
      this.g = _snowman;
   }

   public List<cpe> f() {
      return this.f;
   }

   public ceh[] g() {
      return this.h;
   }

   public void h() {
      Arrays.fill(this.h, 0, this.h.length, null);
      int _snowman = 0;

      for (cpe _snowmanx : this.f) {
         _snowmanx.a(_snowman);
         _snowman += _snowmanx.a();
      }

      this.i = true;

      for (cpe _snowmanx : this.f) {
         for (int _snowmanxx = _snowmanx.c(); _snowmanxx < _snowmanx.c() + _snowmanx.a(); _snowmanxx++) {
            ceh _snowmanxxx = _snowmanx.b();
            if (!_snowmanxxx.a(bup.a)) {
               this.i = false;
               this.h[_snowmanxx] = _snowmanxxx;
            }
         }
      }
   }

   public static cpf a(gm<bsv> var0) {
      chv _snowman = new chv(Optional.of(chv.c), Maps.newHashMap(ImmutableMap.of(cla.q, chv.b.get(cla.q))));
      cpf _snowmanx = new cpf(_snowman, _snowman);
      _snowmanx.g = () -> _snowman.d(btb.b);
      _snowmanx.f().add(new cpe(1, bup.z));
      _snowmanx.f().add(new cpe(2, bup.j));
      _snowmanx.f().add(new cpe(1, bup.i));
      _snowmanx.h();
      return _snowmanx;
   }
}
