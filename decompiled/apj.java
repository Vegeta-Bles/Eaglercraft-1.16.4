import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class apj {
   private final List<aph> a = Lists.newArrayList();
   private final aqm b;
   private int c;
   private int d;
   private int e;
   private boolean f;
   private boolean g;
   private String h;

   public apj(aqm var1) {
      this.b = _snowman;
   }

   public void a() {
      this.k();
      Optional<fx> _snowman = this.b.dr();
      if (_snowman.isPresent()) {
         ceh _snowmanx = this.b.l.d_(_snowman.get());
         if (_snowmanx.a(bup.cg) || _snowmanx.a(aed.J)) {
            this.h = "ladder";
         } else if (_snowmanx.a(bup.dP)) {
            this.h = "vines";
         } else if (_snowmanx.a(bup.mx) || _snowmanx.a(bup.my)) {
            this.h = "weeping_vines";
         } else if (_snowmanx.a(bup.mz) || _snowmanx.a(bup.mA)) {
            this.h = "twisting_vines";
         } else if (_snowmanx.a(bup.lQ)) {
            this.h = "scaffolding";
         } else {
            this.h = "other_climbable";
         }
      } else if (this.b.aE()) {
         this.h = "water";
      }
   }

   public void a(apk var1, float var2, float var3) {
      this.g();
      this.a();
      aph _snowman = new aph(_snowman, this.b.K, _snowman, _snowman, this.h, this.b.C);
      this.a.add(_snowman);
      this.c = this.b.K;
      this.g = true;
      if (_snowman.f() && !this.f && this.b.aX()) {
         this.f = true;
         this.d = this.b.K;
         this.e = this.d;
         this.b.g();
      }
   }

   public nr b() {
      if (this.a.isEmpty()) {
         return new of("death.attack.generic", this.b.d());
      } else {
         aph _snowman = this.j();
         aph _snowmanx = this.a.get(this.a.size() - 1);
         nr _snowmanxx = _snowmanx.h();
         aqa _snowmanxxx = _snowmanx.a().k();
         nr _snowmanxxxx;
         if (_snowman != null && _snowmanx.a() == apk.k) {
            nr _snowmanxxxxx = _snowman.h();
            if (_snowman.a() == apk.k || _snowman.a() == apk.m) {
               _snowmanxxxx = new of("death.fell.accident." + this.a(_snowman), this.b.d());
            } else if (_snowmanxxxxx != null && (_snowmanxx == null || !_snowmanxxxxx.equals(_snowmanxx))) {
               aqa _snowmanxxxxxx = _snowman.a().k();
               bmb _snowmanxxxxxxx = _snowmanxxxxxx instanceof aqm ? ((aqm)_snowmanxxxxxx).dD() : bmb.b;
               if (!_snowmanxxxxxxx.a() && _snowmanxxxxxxx.t()) {
                  _snowmanxxxx = new of("death.fell.assist.item", this.b.d(), _snowmanxxxxx, _snowmanxxxxxxx.C());
               } else {
                  _snowmanxxxx = new of("death.fell.assist", this.b.d(), _snowmanxxxxx);
               }
            } else if (_snowmanxx != null) {
               bmb _snowmanxxxxxx = _snowmanxxx instanceof aqm ? ((aqm)_snowmanxxx).dD() : bmb.b;
               if (!_snowmanxxxxxx.a() && _snowmanxxxxxx.t()) {
                  _snowmanxxxx = new of("death.fell.finish.item", this.b.d(), _snowmanxx, _snowmanxxxxxx.C());
               } else {
                  _snowmanxxxx = new of("death.fell.finish", this.b.d(), _snowmanxx);
               }
            } else {
               _snowmanxxxx = new of("death.fell.killer", this.b.d());
            }
         } else {
            _snowmanxxxx = _snowmanx.a().a(this.b);
         }

         return _snowmanxxxx;
      }
   }

   @Nullable
   public aqm c() {
      aqm _snowman = null;
      bfw _snowmanx = null;
      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;

      for (aph _snowmanxxxx : this.a) {
         if (_snowmanxxxx.a().k() instanceof bfw && (_snowmanx == null || _snowmanxxxx.c() > _snowmanxxx)) {
            _snowmanxxx = _snowmanxxxx.c();
            _snowmanx = (bfw)_snowmanxxxx.a().k();
         }

         if (_snowmanxxxx.a().k() instanceof aqm && (_snowman == null || _snowmanxxxx.c() > _snowmanxx)) {
            _snowmanxx = _snowmanxxxx.c();
            _snowman = (aqm)_snowmanxxxx.a().k();
         }
      }

      return (aqm)(_snowmanx != null && _snowmanxxx >= _snowmanxx / 3.0F ? _snowmanx : _snowman);
   }

   @Nullable
   private aph j() {
      aph _snowman = null;
      aph _snowmanx = null;
      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;

      for (int _snowmanxxxx = 0; _snowmanxxxx < this.a.size(); _snowmanxxxx++) {
         aph _snowmanxxxxx = this.a.get(_snowmanxxxx);
         aph _snowmanxxxxxx = _snowmanxxxx > 0 ? this.a.get(_snowmanxxxx - 1) : null;
         if ((_snowmanxxxxx.a() == apk.k || _snowmanxxxxx.a() == apk.m) && _snowmanxxxxx.j() > 0.0F && (_snowman == null || _snowmanxxxxx.j() > _snowmanxxx)) {
            if (_snowmanxxxx > 0) {
               _snowman = _snowmanxxxxxx;
            } else {
               _snowman = _snowmanxxxxx;
            }

            _snowmanxxx = _snowmanxxxxx.j();
         }

         if (_snowmanxxxxx.g() != null && (_snowmanx == null || _snowmanxxxxx.c() > _snowmanxx)) {
            _snowmanx = _snowmanxxxxx;
            _snowmanxx = _snowmanxxxxx.c();
         }
      }

      if (_snowmanxxx > 5.0F && _snowman != null) {
         return _snowman;
      } else {
         return _snowmanxx > 5.0F && _snowmanx != null ? _snowmanx : null;
      }
   }

   private String a(aph var1) {
      return _snowman.g() == null ? "generic" : _snowman.g();
   }

   public int f() {
      return this.f ? this.b.K - this.d : this.e - this.d;
   }

   private void k() {
      this.h = null;
   }

   public void g() {
      int _snowman = this.f ? 300 : 100;
      if (this.g && (!this.b.aX() || this.b.K - this.c > _snowman)) {
         boolean _snowmanx = this.f;
         this.g = false;
         this.f = false;
         this.e = this.b.K;
         if (_snowmanx) {
            this.b.h();
         }

         this.a.clear();
      }
   }

   public aqm h() {
      return this.b;
   }
}
