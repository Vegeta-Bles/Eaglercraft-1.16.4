import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cxx extends cxs {
   private static final Logger k = LogManager.getLogger();
   public int a;
   public int b;
   public vj<brx> c;
   public boolean d;
   public boolean e;
   public byte f;
   public byte[] g = new byte[16384];
   public boolean h;
   public final List<cxx.a> i = Lists.newArrayList();
   private final Map<bfw, cxx.a> l = Maps.newHashMap();
   private final Map<String, cxt> m = Maps.newHashMap();
   public final Map<String, cxu> j = Maps.newLinkedHashMap();
   private final Map<String, cxv> n = Maps.newHashMap();

   public cxx(String var1) {
      super(_snowman);
   }

   public void a(int var1, int var2, int var3, boolean var4, boolean var5, vj<brx> var6) {
      this.f = (byte)_snowman;
      this.a((double)_snowman, (double)_snowman, this.f);
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.b();
   }

   public void a(double var1, double var3, int var5) {
      int _snowman = 128 * (1 << _snowman);
      int _snowmanx = afm.c((_snowman + 64.0) / (double)_snowman);
      int _snowmanxx = afm.c((_snowman + 64.0) / (double)_snowman);
      this.a = _snowmanx * _snowman + _snowman / 2 - 64;
      this.b = _snowmanxx * _snowman + _snowman / 2 - 64;
   }

   @Override
   public void a(md var1) {
      this.c = (vj<brx>)chd.a(new Dynamic(mo.a, _snowman.c("dimension")))
         .resultOrPartial(k::error)
         .orElseThrow(() -> new IllegalArgumentException("Invalid map dimension: " + _snowman.c("dimension")));
      this.a = _snowman.h("xCenter");
      this.b = _snowman.h("zCenter");
      this.f = (byte)afm.a(_snowman.f("scale"), 0, 4);
      this.d = !_snowman.c("trackingPosition", 1) || _snowman.q("trackingPosition");
      this.e = _snowman.q("unlimitedTracking");
      this.h = _snowman.q("locked");
      this.g = _snowman.m("colors");
      if (this.g.length != 16384) {
         this.g = new byte[16384];
      }

      mj _snowman = _snowman.d("banners", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         cxt _snowmanxx = cxt.a(_snowman.a(_snowmanx));
         this.m.put(_snowmanxx.f(), _snowmanxx);
         this.a(_snowmanxx.c(), null, _snowmanxx.f(), (double)_snowmanxx.a().u(), (double)_snowmanxx.a().w(), 180.0, _snowmanxx.d());
      }

      mj _snowmanx = _snowman.d("frames", 10);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         cxv _snowmanxxx = cxv.a(_snowmanx.a(_snowmanxx));
         this.n.put(_snowmanxxx.e(), _snowmanxxx);
         this.a(cxu.a.b, null, "frame-" + _snowmanxxx.d(), (double)_snowmanxxx.b().u(), (double)_snowmanxxx.b().w(), (double)_snowmanxxx.c(), null);
      }
   }

   @Override
   public md b(md var1) {
      vk.a.encodeStart(mo.a, this.c.a()).resultOrPartial(k::error).ifPresent(var1x -> _snowman.a("dimension", var1x));
      _snowman.b("xCenter", this.a);
      _snowman.b("zCenter", this.b);
      _snowman.a("scale", this.f);
      _snowman.a("colors", this.g);
      _snowman.a("trackingPosition", this.d);
      _snowman.a("unlimitedTracking", this.e);
      _snowman.a("locked", this.h);
      mj _snowman = new mj();

      for (cxt _snowmanx : this.m.values()) {
         _snowman.add(_snowmanx.e());
      }

      _snowman.a("banners", _snowman);
      mj _snowmanx = new mj();

      for (cxv _snowmanxx : this.n.values()) {
         _snowmanx.add(_snowmanxx.a());
      }

      _snowman.a("frames", _snowmanx);
      return _snowman;
   }

   public void a(cxx var1) {
      this.h = true;
      this.a = _snowman.a;
      this.b = _snowman.b;
      this.m.putAll(_snowman.m);
      this.j.putAll(_snowman.j);
      System.arraycopy(_snowman.g, 0, this.g, 0, _snowman.g.length);
      this.b();
   }

   public void a(bfw var1, bmb var2) {
      if (!this.l.containsKey(_snowman)) {
         cxx.a _snowman = new cxx.a(_snowman);
         this.l.put(_snowman, _snowman);
         this.i.add(_snowman);
      }

      if (!_snowman.bm.h(_snowman)) {
         this.j.remove(_snowman.R().getString());
      }

      for (int _snowman = 0; _snowman < this.i.size(); _snowman++) {
         cxx.a _snowmanx = this.i.get(_snowman);
         String _snowmanxx = _snowmanx.a.R().getString();
         if (!_snowmanx.a.y && (_snowmanx.a.bm.h(_snowman) || _snowman.y())) {
            if (!_snowman.y() && _snowmanx.a.l.Y() == this.c && this.d) {
               this.a(cxu.a.a, _snowmanx.a.l, _snowmanxx, _snowmanx.a.cD(), _snowmanx.a.cH(), (double)_snowmanx.a.p, null);
            }
         } else {
            this.l.remove(_snowmanx.a);
            this.i.remove(_snowmanx);
            this.j.remove(_snowmanxx);
         }
      }

      if (_snowman.y() && this.d) {
         bcp _snowmanx = _snowman.z();
         fx _snowmanxx = _snowmanx.n();
         cxv _snowmanxxx = this.n.get(cxv.a(_snowmanxx));
         if (_snowmanxxx != null && _snowmanx.Y() != _snowmanxxx.d() && this.n.containsKey(_snowmanxxx.e())) {
            this.j.remove("frame-" + _snowmanxxx.d());
         }

         cxv _snowmanxxxx = new cxv(_snowmanxx, _snowmanx.bZ().d() * 90, _snowmanx.Y());
         this.a(cxu.a.b, _snowman.l, "frame-" + _snowmanx.Y(), (double)_snowmanxx.u(), (double)_snowmanxx.w(), (double)(_snowmanx.bZ().d() * 90), null);
         this.n.put(_snowmanxxxx.e(), _snowmanxxxx);
      }

      md _snowmanx = _snowman.o();
      if (_snowmanx != null && _snowmanx.c("Decorations", 9)) {
         mj _snowmanxx = _snowmanx.d("Decorations", 10);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            md _snowmanxxxx = _snowmanxx.a(_snowmanxxx);
            if (!this.j.containsKey(_snowmanxxxx.l("id"))) {
               this.a(cxu.a.a(_snowmanxxxx.f("type")), _snowman.l, _snowmanxxxx.l("id"), _snowmanxxxx.k("x"), _snowmanxxxx.k("z"), _snowmanxxxx.k("rot"), null);
            }
         }
      }
   }

   public static void a(bmb var0, fx var1, String var2, cxu.a var3) {
      mj _snowman;
      if (_snowman.n() && _snowman.o().c("Decorations", 9)) {
         _snowman = _snowman.o().d("Decorations", 10);
      } else {
         _snowman = new mj();
         _snowman.a("Decorations", _snowman);
      }

      md _snowmanx = new md();
      _snowmanx.a("type", _snowman.a());
      _snowmanx.a("id", _snowman);
      _snowmanx.a("x", (double)_snowman.u());
      _snowmanx.a("z", (double)_snowman.w());
      _snowmanx.a("rot", 180.0);
      _snowman.add(_snowmanx);
      if (_snowman.c()) {
         md _snowmanxx = _snowman.a("display");
         _snowmanxx.b("MapColor", _snowman.d());
      }
   }

   private void a(cxu.a var1, @Nullable bry var2, String var3, double var4, double var6, double var8, @Nullable nr var10) {
      int _snowman = 1 << this.f;
      float _snowmanx = (float)(_snowman - (double)this.a) / (float)_snowman;
      float _snowmanxx = (float)(_snowman - (double)this.b) / (float)_snowman;
      byte _snowmanxxx = (byte)((int)((double)(_snowmanx * 2.0F) + 0.5));
      byte _snowmanxxxx = (byte)((int)((double)(_snowmanxx * 2.0F) + 0.5));
      int _snowmanxxxxx = 63;
      byte _snowmanxxxxxx;
      if (_snowmanx >= -63.0F && _snowmanxx >= -63.0F && _snowmanx <= 63.0F && _snowmanxx <= 63.0F) {
         _snowman += _snowman < 0.0 ? -8.0 : 8.0;
         _snowmanxxxxxx = (byte)((int)(_snowman * 16.0 / 360.0));
         if (this.c == brx.h && _snowman != null) {
            int _snowmanxxxxxxx = (int)(_snowman.h().f() / 10L);
            _snowmanxxxxxx = (byte)(_snowmanxxxxxxx * _snowmanxxxxxxx * 34187121 + _snowmanxxxxxxx * 121 >> 15 & 15);
         }
      } else {
         if (_snowman != cxu.a.a) {
            this.j.remove(_snowman);
            return;
         }

         int _snowmanxxxxxxx = 320;
         if (Math.abs(_snowmanx) < 320.0F && Math.abs(_snowmanxx) < 320.0F) {
            _snowman = cxu.a.g;
         } else {
            if (!this.e) {
               this.j.remove(_snowman);
               return;
            }

            _snowman = cxu.a.h;
         }

         _snowmanxxxxxx = 0;
         if (_snowmanx <= -63.0F) {
            _snowmanxxx = -128;
         }

         if (_snowmanxx <= -63.0F) {
            _snowmanxxxx = -128;
         }

         if (_snowmanx >= 63.0F) {
            _snowmanxxx = 127;
         }

         if (_snowmanxx >= 63.0F) {
            _snowmanxxxx = 127;
         }
      }

      this.j.put(_snowman, new cxu(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxx, _snowman));
   }

   @Nullable
   public oj<?> a(bmb var1, brc var2, bfw var3) {
      cxx.a _snowman = this.l.get(_snowman);
      return _snowman == null ? null : _snowman.a(_snowman);
   }

   public void a(int var1, int var2) {
      this.b();

      for (cxx.a _snowman : this.i) {
         _snowman.a(_snowman, _snowman);
      }
   }

   public cxx.a a(bfw var1) {
      cxx.a _snowman = this.l.get(_snowman);
      if (_snowman == null) {
         _snowman = new cxx.a(_snowman);
         this.l.put(_snowman, _snowman);
         this.i.add(_snowman);
      }

      return _snowman;
   }

   public void a(bry var1, fx var2) {
      double _snowman = (double)_snowman.u() + 0.5;
      double _snowmanx = (double)_snowman.w() + 0.5;
      int _snowmanxx = 1 << this.f;
      double _snowmanxxx = (_snowman - (double)this.a) / (double)_snowmanxx;
      double _snowmanxxxx = (_snowmanx - (double)this.b) / (double)_snowmanxx;
      int _snowmanxxxxx = 63;
      boolean _snowmanxxxxxx = false;
      if (_snowmanxxx >= -63.0 && _snowmanxxxx >= -63.0 && _snowmanxxx <= 63.0 && _snowmanxxxx <= 63.0) {
         cxt _snowmanxxxxxxx = cxt.a(_snowman, _snowman);
         if (_snowmanxxxxxxx == null) {
            return;
         }

         boolean _snowmanxxxxxxxx = true;
         if (this.m.containsKey(_snowmanxxxxxxx.f()) && this.m.get(_snowmanxxxxxxx.f()).equals(_snowmanxxxxxxx)) {
            this.m.remove(_snowmanxxxxxxx.f());
            this.j.remove(_snowmanxxxxxxx.f());
            _snowmanxxxxxxxx = false;
            _snowmanxxxxxx = true;
         }

         if (_snowmanxxxxxxxx) {
            this.m.put(_snowmanxxxxxxx.f(), _snowmanxxxxxxx);
            this.a(_snowmanxxxxxxx.c(), _snowman, _snowmanxxxxxxx.f(), _snowman, _snowmanx, 180.0, _snowmanxxxxxxx.d());
            _snowmanxxxxxx = true;
         }

         if (_snowmanxxxxxx) {
            this.b();
         }
      }
   }

   public void a(brc var1, int var2, int var3) {
      Iterator<cxt> _snowman = this.m.values().iterator();

      while (_snowman.hasNext()) {
         cxt _snowmanx = _snowman.next();
         if (_snowmanx.a().u() == _snowman && _snowmanx.a().w() == _snowman) {
            cxt _snowmanxx = cxt.a(_snowman, _snowmanx.a());
            if (!_snowmanx.equals(_snowmanxx)) {
               _snowman.remove();
               this.j.remove(_snowmanx.f());
            }
         }
      }
   }

   public void a(fx var1, int var2) {
      this.j.remove("frame-" + _snowman);
      this.n.remove(cxv.a(_snowman));
   }

   public class a {
      public final bfw a;
      private boolean d = true;
      private int e;
      private int f;
      private int g = 127;
      private int h = 127;
      private int i;
      public int b;

      public a(bfw var2) {
         this.a = _snowman;
      }

      @Nullable
      public oj<?> a(bmb var1) {
         if (this.d) {
            this.d = false;
            return new py(
               bmh.d(_snowman), cxx.this.f, cxx.this.d, cxx.this.h, cxx.this.j.values(), cxx.this.g, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f
            );
         } else {
            return this.i++ % 5 == 0 ? new py(bmh.d(_snowman), cxx.this.f, cxx.this.d, cxx.this.h, cxx.this.j.values(), cxx.this.g, 0, 0, 0, 0) : null;
         }
      }

      public void a(int var1, int var2) {
         if (this.d) {
            this.e = Math.min(this.e, _snowman);
            this.f = Math.min(this.f, _snowman);
            this.g = Math.max(this.g, _snowman);
            this.h = Math.max(this.h, _snowman);
         } else {
            this.d = true;
            this.e = _snowman;
            this.f = _snowman;
            this.g = _snowman;
            this.h = _snowman;
         }
      }
   }
}
