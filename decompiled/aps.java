import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class aps {
   private final Map<arg, arj> a = Maps.newHashMap();
   private final apt b;
   private final int c;
   @Nullable
   private String d;

   @Nullable
   public static aps a(int var0) {
      return gm.P.a(_snowman);
   }

   public static int a(aps var0) {
      return gm.P.a(_snowman);
   }

   protected aps(apt var1, int var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public void a(aqm var1, int var2) {
      if (this == apw.j) {
         if (_snowman.dk() < _snowman.dx()) {
            _snowman.b(1.0F);
         }
      } else if (this == apw.s) {
         if (_snowman.dk() > 1.0F) {
            _snowman.a(apk.o, 1.0F);
         }
      } else if (this == apw.t) {
         _snowman.a(apk.p, 1.0F);
      } else if (this == apw.q && _snowman instanceof bfw) {
         ((bfw)_snowman).t(0.005F * (float)(_snowman + 1));
      } else if (this == apw.w && _snowman instanceof bfw) {
         if (!_snowman.l.v) {
            ((bfw)_snowman).eI().a(_snowman + 1, 1.0F);
         }
      } else if ((this != apw.f || _snowman.dj()) && (this != apw.g || !_snowman.dj())) {
         if (this == apw.g && !_snowman.dj() || this == apw.f && _snowman.dj()) {
            _snowman.a(apk.o, (float)(6 << _snowman));
         }
      } else {
         _snowman.b((float)Math.max(4 << _snowman, 0));
      }
   }

   public void a(@Nullable aqa var1, @Nullable aqa var2, aqm var3, int var4, double var5) {
      if ((this != apw.f || _snowman.dj()) && (this != apw.g || !_snowman.dj())) {
         if (this == apw.g && !_snowman.dj() || this == apw.f && _snowman.dj()) {
            int _snowman = (int)(_snowman * (double)(6 << _snowman) + 0.5);
            if (_snowman == null) {
               _snowman.a(apk.o, (float)_snowman);
            } else {
               _snowman.a(apk.c(_snowman, _snowman), (float)_snowman);
            }
         } else {
            this.a(_snowman, _snowman);
         }
      } else {
         int _snowman = (int)(_snowman * (double)(4 << _snowman) + 0.5);
         _snowman.b((float)_snowman);
      }
   }

   public boolean a(int var1, int var2) {
      if (this == apw.j) {
         int _snowman = 50 >> _snowman;
         return _snowman > 0 ? _snowman % _snowman == 0 : true;
      } else if (this == apw.s) {
         int _snowman = 25 >> _snowman;
         return _snowman > 0 ? _snowman % _snowman == 0 : true;
      } else if (this == apw.t) {
         int _snowman = 40 >> _snowman;
         return _snowman > 0 ? _snowman % _snowman == 0 : true;
      } else {
         return this == apw.q;
      }
   }

   public boolean a() {
      return false;
   }

   protected String b() {
      if (this.d == null) {
         this.d = x.a("effect", gm.P.b(this));
      }

      return this.d;
   }

   public String c() {
      return this.b();
   }

   public nr d() {
      return new of(this.c());
   }

   public apt e() {
      return this.b;
   }

   public int f() {
      return this.c;
   }

   public aps a(arg var1, String var2, double var3, arj.a var5) {
      arj _snowman = new arj(UUID.fromString(_snowman), this::c, _snowman, _snowman);
      this.a.put(_snowman, _snowman);
      return this;
   }

   public Map<arg, arj> g() {
      return this.a;
   }

   public void a(aqm var1, ari var2, int var3) {
      for (Entry<arg, arj> _snowman : this.a.entrySet()) {
         arh _snowmanx = _snowman.a(_snowman.getKey());
         if (_snowmanx != null) {
            _snowmanx.d(_snowman.getValue());
         }
      }
   }

   public void b(aqm var1, ari var2, int var3) {
      for (Entry<arg, arj> _snowman : this.a.entrySet()) {
         arh _snowmanx = _snowman.a(_snowman.getKey());
         if (_snowmanx != null) {
            arj _snowmanxx = _snowman.getValue();
            _snowmanx.d(_snowmanxx);
            _snowmanx.c(new arj(_snowmanxx.a(), this.c() + " " + _snowman, this.a(_snowman, _snowmanxx), _snowmanxx.c()));
         }
      }
   }

   public double a(int var1, arj var2) {
      return _snowman.d() * (double)(_snowman + 1);
   }

   public boolean h() {
      return this.b == apt.a;
   }
}
