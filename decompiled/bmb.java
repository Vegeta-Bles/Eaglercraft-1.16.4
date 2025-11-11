import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class bmb {
   public static final Codec<bmb> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               gm.T.fieldOf("id").forGetter(var0x -> var0x.h),
               Codec.INT.fieldOf("Count").forGetter(var0x -> var0x.f),
               md.a.optionalFieldOf("tag").forGetter(var0x -> Optional.ofNullable(var0x.i))
            )
            .apply(var0, bmb::new)
   );
   private static final Logger d = LogManager.getLogger();
   public static final bmb b = new bmb((blx)null);
   public static final DecimalFormat c = x.a(new DecimalFormat("#.##"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
   private static final ob e = ob.a.a(k.f).b(true);
   private int f;
   private int g;
   @Deprecated
   private final blx h;
   private md i;
   private boolean j;
   private aqa k;
   private cel l;
   private boolean m;
   private cel n;
   private boolean o;

   public bmb(brw var1) {
      this(_snowman, 1);
   }

   private bmb(brw var1, int var2, Optional<md> var3) {
      this(_snowman, _snowman);
      _snowman.ifPresent(this::c);
   }

   public bmb(brw var1, int var2) {
      this.h = _snowman == null ? null : _snowman.h();
      this.f = _snowman;
      if (this.h != null && this.h.k()) {
         this.b(this.g());
      }

      this.I();
   }

   private void I() {
      this.j = false;
      this.j = this.a();
   }

   private bmb(md var1) {
      this.h = gm.T.a(new vk(_snowman.l("id")));
      this.f = _snowman.f("Count");
      if (_snowman.c("tag", 10)) {
         this.i = _snowman.p("tag");
         this.b().b(_snowman);
      }

      if (this.b().k()) {
         this.b(this.g());
      }

      this.I();
   }

   public static bmb a(md var0) {
      try {
         return new bmb(_snowman);
      } catch (RuntimeException var2) {
         d.debug("Tried to load invalid item: {}", _snowman, var2);
         return b;
      }
   }

   public boolean a() {
      if (this == b) {
         return true;
      } else {
         return this.b() == null || this.b() == bmd.a ? true : this.f <= 0;
      }
   }

   public bmb a(int var1) {
      int _snowman = Math.min(_snowman, this.f);
      bmb _snowmanx = this.i();
      _snowmanx.e(_snowman);
      this.g(_snowman);
      return _snowmanx;
   }

   public blx b() {
      return this.j ? bmd.a : this.h;
   }

   public aou a(boa var1) {
      bfw _snowman = _snowman.n();
      fx _snowmanx = _snowman.a();
      cel _snowmanxx = new cel(_snowman.p(), _snowmanx, false);
      if (_snowman != null && !_snowman.bC.e && !this.b(_snowman.p().p(), _snowmanxx)) {
         return aou.c;
      } else {
         blx _snowmanxxx = this.b();
         aou _snowmanxxxx = _snowmanxxx.a(_snowman);
         if (_snowman != null && _snowmanxxxx.a()) {
            _snowman.b(aea.c.b(_snowmanxxx));
         }

         return _snowmanxxxx;
      }
   }

   public float a(ceh var1) {
      return this.b().a(this, _snowman);
   }

   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      return this.b().a(_snowman, _snowman, _snowman);
   }

   public bmb a(brx var1, aqm var2) {
      return this.b().a(this, _snowman, _snowman);
   }

   public md b(md var1) {
      vk _snowman = gm.T.b(this.b());
      _snowman.a("id", _snowman == null ? "minecraft:air" : _snowman.toString());
      _snowman.a("Count", (byte)this.f);
      if (this.i != null) {
         _snowman.a("tag", this.i.g());
      }

      return _snowman;
   }

   public int c() {
      return this.b().i();
   }

   public boolean d() {
      return this.c() > 1 && (!this.e() || !this.f());
   }

   public boolean e() {
      if (!this.j && this.b().j() > 0) {
         md _snowman = this.o();
         return _snowman == null || !_snowman.q("Unbreakable");
      } else {
         return false;
      }
   }

   public boolean f() {
      return this.e() && this.g() > 0;
   }

   public int g() {
      return this.i == null ? 0 : this.i.h("Damage");
   }

   public void b(int var1) {
      this.p().b("Damage", Math.max(0, _snowman));
   }

   public int h() {
      return this.b().j();
   }

   public boolean a(int var1, Random var2, @Nullable aah var3) {
      if (!this.e()) {
         return false;
      } else {
         if (_snowman > 0) {
            int _snowman = bpu.a(bpw.v, this);
            int _snowmanx = 0;

            for (int _snowmanxx = 0; _snowman > 0 && _snowmanxx < _snowman; _snowmanxx++) {
               if (bpq.a(this, _snowman, _snowman)) {
                  _snowmanx++;
               }
            }

            _snowman -= _snowmanx;
            if (_snowman <= 0) {
               return false;
            }
         }

         if (_snowman != null && _snowman != 0) {
            ac.t.a(_snowman, this, this.g() + _snowman);
         }

         int _snowman = this.g() + _snowman;
         this.b(_snowman);
         return _snowman >= this.h();
      }
   }

   public <T extends aqm> void a(int var1, T var2, Consumer<T> var3) {
      if (!_snowman.l.v && (!(_snowman instanceof bfw) || !((bfw)_snowman).bC.d)) {
         if (this.e()) {
            if (this.a(_snowman, _snowman.cY(), _snowman instanceof aah ? (aah)_snowman : null)) {
               _snowman.accept(_snowman);
               blx _snowman = this.b();
               this.g(1);
               if (_snowman instanceof bfw) {
                  ((bfw)_snowman).b(aea.d.b(_snowman));
               }

               this.b(0);
            }
         }
      }
   }

   public void a(aqm var1, bfw var2) {
      blx _snowman = this.b();
      if (_snowman.a(this, _snowman, _snowman)) {
         _snowman.b(aea.c.b(_snowman));
      }
   }

   public void a(brx var1, ceh var2, fx var3, bfw var4) {
      blx _snowman = this.b();
      if (_snowman.a(this, _snowman, _snowman, _snowman, _snowman)) {
         _snowman.b(aea.c.b(_snowman));
      }
   }

   public boolean b(ceh var1) {
      return this.b().b(_snowman);
   }

   public aou a(bfw var1, aqm var2, aot var3) {
      return this.b().a(this, _snowman, _snowman, _snowman);
   }

   public bmb i() {
      if (this.a()) {
         return b;
      } else {
         bmb _snowman = new bmb(this.b(), this.f);
         _snowman.d(this.D());
         if (this.i != null) {
            _snowman.i = this.i.g();
         }

         return _snowman;
      }
   }

   public static boolean a(bmb var0, bmb var1) {
      if (_snowman.a() && _snowman.a()) {
         return true;
      } else if (_snowman.a() || _snowman.a()) {
         return false;
      } else {
         return _snowman.i == null && _snowman.i != null ? false : _snowman.i == null || _snowman.i.equals(_snowman.i);
      }
   }

   public static boolean b(bmb var0, bmb var1) {
      if (_snowman.a() && _snowman.a()) {
         return true;
      } else {
         return !_snowman.a() && !_snowman.a() ? _snowman.c(_snowman) : false;
      }
   }

   private boolean c(bmb var1) {
      if (this.f != _snowman.f) {
         return false;
      } else if (this.b() != _snowman.b()) {
         return false;
      } else {
         return this.i == null && _snowman.i != null ? false : this.i == null || this.i.equals(_snowman.i);
      }
   }

   public static boolean c(bmb var0, bmb var1) {
      if (_snowman == _snowman) {
         return true;
      } else {
         return !_snowman.a() && !_snowman.a() ? _snowman.a(_snowman) : false;
      }
   }

   public static boolean d(bmb var0, bmb var1) {
      if (_snowman == _snowman) {
         return true;
      } else {
         return !_snowman.a() && !_snowman.a() ? _snowman.b(_snowman) : false;
      }
   }

   public boolean a(bmb var1) {
      return !_snowman.a() && this.b() == _snowman.b();
   }

   public boolean b(bmb var1) {
      return !this.e() ? this.a(_snowman) : !_snowman.a() && this.b() == _snowman.b();
   }

   public String j() {
      return this.b().f(this);
   }

   @Override
   public String toString() {
      return this.f + " " + this.b();
   }

   public void a(brx var1, aqa var2, int var3, boolean var4) {
      if (this.g > 0) {
         this.g--;
      }

      if (this.b() != null) {
         this.b().a(this, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public void a(brx var1, bfw var2, int var3) {
      _snowman.a(aea.b.b(this.b()), _snowman);
      this.b().b(this, _snowman, _snowman);
   }

   public int k() {
      return this.b().e_(this);
   }

   public bnn l() {
      return this.b().d_(this);
   }

   public void a(brx var1, aqm var2, int var3) {
      this.b().a(this, _snowman, _snowman, _snowman);
   }

   public boolean m() {
      return this.b().j(this);
   }

   public boolean n() {
      return !this.j && this.i != null && !this.i.isEmpty();
   }

   @Nullable
   public md o() {
      return this.i;
   }

   public md p() {
      if (this.i == null) {
         this.c(new md());
      }

      return this.i;
   }

   public md a(String var1) {
      if (this.i != null && this.i.c(_snowman, 10)) {
         return this.i.p(_snowman);
      } else {
         md _snowman = new md();
         this.a(_snowman, _snowman);
         return _snowman;
      }
   }

   @Nullable
   public md b(String var1) {
      return this.i != null && this.i.c(_snowman, 10) ? this.i.p(_snowman) : null;
   }

   public void c(String var1) {
      if (this.i != null && this.i.e(_snowman)) {
         this.i.r(_snowman);
         if (this.i.isEmpty()) {
            this.i = null;
         }
      }
   }

   public mj q() {
      return this.i != null ? this.i.d("Enchantments", 10) : new mj();
   }

   public void c(@Nullable md var1) {
      this.i = _snowman;
      if (this.b().k()) {
         this.b(this.g());
      }
   }

   public nr r() {
      md _snowman = this.b("display");
      if (_snowman != null && _snowman.c("Name", 8)) {
         try {
            nr _snowmanx = nr.a.a(_snowman.l("Name"));
            if (_snowmanx != null) {
               return _snowmanx;
            }

            _snowman.r("Name");
         } catch (JsonParseException var3) {
            _snowman.r("Name");
         }
      }

      return this.b().h(this);
   }

   public bmb a(@Nullable nr var1) {
      md _snowman = this.a("display");
      if (_snowman != null) {
         _snowman.a("Name", nr.a.a(_snowman));
      } else {
         _snowman.r("Name");
      }

      return this;
   }

   public void s() {
      md _snowman = this.b("display");
      if (_snowman != null) {
         _snowman.r("Name");
         if (_snowman.isEmpty()) {
            this.c("display");
         }
      }

      if (this.i != null && this.i.isEmpty()) {
         this.i = null;
      }
   }

   public boolean t() {
      md _snowman = this.b("display");
      return _snowman != null && _snowman.c("Name", 8);
   }

   public List<nr> a(@Nullable bfw var1, bnl var2) {
      List<nr> _snowman = Lists.newArrayList();
      nx _snowmanx = new oe("").a(this.r()).a(this.v().e);
      if (this.t()) {
         _snowmanx.a(k.u);
      }

      _snowman.add(_snowmanx);
      if (!_snowman.a() && !this.t() && this.b() == bmd.nf) {
         _snowman.add(new oe("#" + bmh.d(this)).a(k.h));
      }

      int _snowmanxx = this.J();
      if (a(_snowmanxx, bmb.a.f)) {
         this.b().a(this, _snowman == null ? null : _snowman.l, _snowman, _snowman);
      }

      if (this.n()) {
         if (a(_snowmanxx, bmb.a.a)) {
            a(_snowman, this.q());
         }

         if (this.i.c("display", 10)) {
            md _snowmanxxx = this.i.p("display");
            if (a(_snowmanxx, bmb.a.g) && _snowmanxxx.c("color", 99)) {
               if (_snowman.a()) {
                  _snowman.add(new of("item.color", String.format("#%06X", _snowmanxxx.h("color"))).a(k.h));
               } else {
                  _snowman.add(new of("item.dyed").a(new k[]{k.h, k.u}));
               }
            }

            if (_snowmanxxx.d("Lore") == 9) {
               mj _snowmanxxxx = _snowmanxxx.d("Lore", 8);

               for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
                  String _snowmanxxxxxx = _snowmanxxxx.j(_snowmanxxxxx);

                  try {
                     nx _snowmanxxxxxxx = nr.a.a(_snowmanxxxxxx);
                     if (_snowmanxxxxxxx != null) {
                        _snowman.add(ns.a(_snowmanxxxxxxx, e));
                     }
                  } catch (JsonParseException var19) {
                     _snowmanxxx.r("Lore");
                  }
               }
            }
         }
      }

      if (a(_snowmanxx, bmb.a.b)) {
         for (aqf _snowmanxxxx : aqf.values()) {
            Multimap<arg, arj> _snowmanxxxxx = this.a(_snowmanxxxx);
            if (!_snowmanxxxxx.isEmpty()) {
               _snowman.add(oe.d);
               _snowman.add(new of("item.modifiers." + _snowmanxxxx.d()).a(k.h));

               for (Entry<arg, arj> _snowmanxxxxxx : _snowmanxxxxx.entries()) {
                  arj _snowmanxxxxxxx = _snowmanxxxxxx.getValue();
                  double _snowmanxxxxxxxx = _snowmanxxxxxxx.d();
                  boolean _snowmanxxxxxxxxx = false;
                  if (_snowman != null) {
                     if (_snowmanxxxxxxx.a() == blx.f) {
                        _snowmanxxxxxxxx += _snowman.c(arl.f);
                        _snowmanxxxxxxxx += (double)bpu.a(this, aqq.a);
                        _snowmanxxxxxxxxx = true;
                     } else if (_snowmanxxxxxxx.a() == blx.g) {
                        _snowmanxxxxxxxx += _snowman.c(arl.h);
                        _snowmanxxxxxxxxx = true;
                     }
                  }

                  double _snowmanxxxxxxxxxx;
                  if (_snowmanxxxxxxx.c() == arj.a.b || _snowmanxxxxxxx.c() == arj.a.c) {
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * 100.0;
                  } else if (_snowmanxxxxxx.getKey().equals(arl.c)) {
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * 10.0;
                  } else {
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx;
                  }

                  if (_snowmanxxxxxxxxx) {
                     _snowman.add(new oe(" ").a(new of("attribute.modifier.equals." + _snowmanxxxxxxx.c().a(), c.format(_snowmanxxxxxxxxxx), new of(_snowmanxxxxxx.getKey().c()))).a(k.c));
                  } else if (_snowmanxxxxxxxx > 0.0) {
                     _snowman.add(new of("attribute.modifier.plus." + _snowmanxxxxxxx.c().a(), c.format(_snowmanxxxxxxxxxx), new of(_snowmanxxxxxx.getKey().c())).a(k.j));
                  } else if (_snowmanxxxxxxxx < 0.0) {
                     _snowmanxxxxxxxxxx *= -1.0;
                     _snowman.add(new of("attribute.modifier.take." + _snowmanxxxxxxx.c().a(), c.format(_snowmanxxxxxxxxxx), new of(_snowmanxxxxxx.getKey().c())).a(k.m));
                  }
               }
            }
         }
      }

      if (this.n()) {
         if (a(_snowmanxx, bmb.a.c) && this.i.q("Unbreakable")) {
            _snowman.add(new of("item.unbreakable").a(k.j));
         }

         if (a(_snowmanxx, bmb.a.d) && this.i.c("CanDestroy", 9)) {
            mj _snowmanxxxxx = this.i.d("CanDestroy", 8);
            if (!_snowmanxxxxx.isEmpty()) {
               _snowman.add(oe.d);
               _snowman.add(new of("item.canBreak").a(k.h));

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
                  _snowman.addAll(d(_snowmanxxxxx.j(_snowmanxxxxxx)));
               }
            }
         }

         if (a(_snowmanxx, bmb.a.e) && this.i.c("CanPlaceOn", 9)) {
            mj _snowmanxxxxx = this.i.d("CanPlaceOn", 8);
            if (!_snowmanxxxxx.isEmpty()) {
               _snowman.add(oe.d);
               _snowman.add(new of("item.canPlace").a(k.h));

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
                  _snowman.addAll(d(_snowmanxxxxx.j(_snowmanxxxxxx)));
               }
            }
         }
      }

      if (_snowman.a()) {
         if (this.f()) {
            _snowman.add(new of("item.durability", this.h() - this.g(), this.h()));
         }

         _snowman.add(new oe(gm.T.b(this.b()).toString()).a(k.i));
         if (this.n()) {
            _snowman.add(new of("item.nbt_tags", this.i.d().size()).a(k.i));
         }
      }

      return _snowman;
   }

   private static boolean a(int var0, bmb.a var1) {
      return (_snowman & _snowman.a()) == 0;
   }

   private int J() {
      return this.n() && this.i.c("HideFlags", 99) ? this.i.h("HideFlags") : 0;
   }

   public void a(bmb.a var1) {
      md _snowman = this.p();
      _snowman.b("HideFlags", _snowman.h("HideFlags") | _snowman.a());
   }

   public static void a(List<nr> var0, mj var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         md _snowmanx = _snowman.a(_snowman);
         gm.R.b(vk.a(_snowmanx.l("id"))).ifPresent(var2x -> _snowman.add(var2x.d(_snowman.h("lvl"))));
      }
   }

   private static Collection<nr> d(String var0) {
      try {
         ei _snowman = new ei(new StringReader(_snowman), true).a(true);
         ceh _snowmanx = _snowman.b();
         vk _snowmanxx = _snowman.d();
         boolean _snowmanxxx = _snowmanx != null;
         boolean _snowmanxxxx = _snowmanxx != null;
         if (_snowmanxxx || _snowmanxxxx) {
            if (_snowmanxxx) {
               return Lists.newArrayList(new nr[]{_snowmanx.b().g().a(k.i)});
            }

            ael<buo> _snowmanxxxxx = aed.a().a(_snowmanxx);
            if (_snowmanxxxxx != null) {
               Collection<buo> _snowmanxxxxxx = _snowmanxxxxx.b();
               if (!_snowmanxxxxxx.isEmpty()) {
                  return _snowmanxxxxxx.stream().map(buo::g).map(var0x -> var0x.a(k.i)).collect(Collectors.toList());
               }
            }
         }
      } catch (CommandSyntaxException var8) {
      }

      return Lists.newArrayList(new nr[]{new oe("missingno").a(k.i)});
   }

   public boolean u() {
      return this.b().e(this);
   }

   public bmp v() {
      return this.b().i(this);
   }

   public boolean w() {
      return !this.b().f_(this) ? false : !this.x();
   }

   public void a(bps var1, int var2) {
      this.p();
      if (!this.i.c("Enchantments", 9)) {
         this.i.a("Enchantments", new mj());
      }

      mj _snowman = this.i.d("Enchantments", 10);
      md _snowmanx = new md();
      _snowmanx.a("id", String.valueOf(gm.R.b(_snowman)));
      _snowmanx.a("lvl", (short)((byte)_snowman));
      _snowman.add(_snowmanx);
   }

   public boolean x() {
      return this.i != null && this.i.c("Enchantments", 9) ? !this.i.d("Enchantments", 10).isEmpty() : false;
   }

   public void a(String var1, mt var2) {
      this.p().a(_snowman, _snowman);
   }

   public boolean y() {
      return this.k instanceof bcp;
   }

   public void a(@Nullable aqa var1) {
      this.k = _snowman;
   }

   @Nullable
   public bcp z() {
      return this.k instanceof bcp ? (bcp)this.A() : null;
   }

   @Nullable
   public aqa A() {
      return !this.j ? this.k : null;
   }

   public int B() {
      return this.n() && this.i.c("RepairCost", 3) ? this.i.h("RepairCost") : 0;
   }

   public void c(int var1) {
      this.p().b("RepairCost", _snowman);
   }

   public Multimap<arg, arj> a(aqf var1) {
      Multimap<arg, arj> _snowman;
      if (this.n() && this.i.c("AttributeModifiers", 9)) {
         _snowman = HashMultimap.create();
         mj _snowmanx = this.i.d("AttributeModifiers", 10);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            md _snowmanxxx = _snowmanx.a(_snowmanxx);
            if (!_snowmanxxx.c("Slot", 8) || _snowmanxxx.l("Slot").equals(_snowman.d())) {
               Optional<arg> _snowmanxxxx = gm.af.b(vk.a(_snowmanxxx.l("AttributeName")));
               if (_snowmanxxxx.isPresent()) {
                  arj _snowmanxxxxx = arj.a(_snowmanxxx);
                  if (_snowmanxxxxx != null && _snowmanxxxxx.a().getLeastSignificantBits() != 0L && _snowmanxxxxx.a().getMostSignificantBits() != 0L) {
                     _snowman.put(_snowmanxxxx.get(), _snowmanxxxxx);
                  }
               }
            }
         }
      } else {
         _snowman = this.b().a(_snowman);
      }

      return _snowman;
   }

   public void a(arg var1, arj var2, @Nullable aqf var3) {
      this.p();
      if (!this.i.c("AttributeModifiers", 9)) {
         this.i.a("AttributeModifiers", new mj());
      }

      mj _snowman = this.i.d("AttributeModifiers", 10);
      md _snowmanx = _snowman.e();
      _snowmanx.a("AttributeName", gm.af.b(_snowman).toString());
      if (_snowman != null) {
         _snowmanx.a("Slot", _snowman.d());
      }

      _snowman.add(_snowmanx);
   }

   public nr C() {
      nx _snowman = new oe("").a(this.r());
      if (this.t()) {
         _snowman.a(k.u);
      }

      nx _snowmanx = ns.a((nr)_snowman);
      if (!this.j) {
         _snowmanx.a(this.v().e).a(var1x -> var1x.a(new nv(nv.a.b, new nv.c(this))));
      }

      return _snowmanx;
   }

   private static boolean a(cel var0, @Nullable cel var1) {
      if (_snowman == null || _snowman.a() != _snowman.a()) {
         return false;
      } else if (_snowman.b() == null && _snowman.b() == null) {
         return true;
      } else {
         return _snowman.b() != null && _snowman.b() != null ? Objects.equals(_snowman.b().a(new md()), _snowman.b().a(new md())) : false;
      }
   }

   public boolean a(aen var1, cel var2) {
      if (a(_snowman, this.l)) {
         return this.m;
      } else {
         this.l = _snowman;
         if (this.n() && this.i.c("CanDestroy", 9)) {
            mj _snowman = this.i.d("CanDestroy", 8);

            for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
               String _snowmanxx = _snowman.j(_snowmanx);

               try {
                  Predicate<cel> _snowmanxxx = eg.a().a(new StringReader(_snowmanxx)).create(_snowman);
                  if (_snowmanxxx.test(_snowman)) {
                     this.m = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.m = false;
         return false;
      }
   }

   public boolean b(aen var1, cel var2) {
      if (a(_snowman, this.n)) {
         return this.o;
      } else {
         this.n = _snowman;
         if (this.n() && this.i.c("CanPlaceOn", 9)) {
            mj _snowman = this.i.d("CanPlaceOn", 8);

            for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
               String _snowmanxx = _snowman.j(_snowmanx);

               try {
                  Predicate<cel> _snowmanxxx = eg.a().a(new StringReader(_snowmanxx)).create(_snowman);
                  if (_snowmanxxx.test(_snowman)) {
                     this.o = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.o = false;
         return false;
      }
   }

   public int D() {
      return this.g;
   }

   public void d(int var1) {
      this.g = _snowman;
   }

   public int E() {
      return this.j ? 0 : this.f;
   }

   public void e(int var1) {
      this.f = _snowman;
      this.I();
   }

   public void f(int var1) {
      this.e(this.f + _snowman);
   }

   public void g(int var1) {
      this.f(-_snowman);
   }

   public void b(brx var1, aqm var2, int var3) {
      this.b().a(_snowman, _snowman, this, _snowman);
   }

   public boolean F() {
      return this.b().s();
   }

   public adp G() {
      return this.b().ae_();
   }

   public adp H() {
      return this.b().ad_();
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e,
      f,
      g;

      private int h = 1 << this.ordinal();

      private a() {
      }

      public int a() {
         return this.h;
      }
   }
}
