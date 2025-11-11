import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;

public abstract class dzj extends bfw {
   private dwx e;
   public float a;
   public float b;
   public float c;
   public final dwt d;

   public dzj(dwt var1, GameProfile var2) {
      super(_snowman, _snowman.u(), _snowman.v(), _snowman);
      this.d = _snowman;
   }

   @Override
   public boolean a_() {
      dwx _snowman = djz.C().w().a(this.eA().getId());
      return _snowman != null && _snowman.b() == bru.e;
   }

   @Override
   public boolean b_() {
      dwx _snowman = djz.C().w().a(this.eA().getId());
      return _snowman != null && _snowman.b() == bru.c;
   }

   public boolean c() {
      return this.f() != null;
   }

   @Nullable
   protected dwx f() {
      if (this.e == null) {
         this.e = djz.C().w().a(this.bS());
      }

      return this.e;
   }

   public boolean n() {
      dwx _snowman = this.f();
      return _snowman != null && _snowman.e();
   }

   public vk o() {
      dwx _snowman = this.f();
      return _snowman == null ? ekj.a(this.bS()) : _snowman.g();
   }

   @Nullable
   public vk p() {
      dwx _snowman = this.f();
      return _snowman == null ? null : _snowman.h();
   }

   @Override
   public boolean q() {
      return this.f() != null;
   }

   @Nullable
   public vk r() {
      dwx _snowman = this.f();
      return _snowman == null ? null : _snowman.i();
   }

   public static ejt a(vk var0, String var1) {
      ekd _snowman = djz.C().M();
      ejq _snowmanx = _snowman.b(_snowman);
      if (_snowmanx == null) {
         _snowmanx = new ejt(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", aft.a(_snowman)), ekj.a(c(_snowman)), true, null);
         _snowman.a(_snowman, _snowmanx);
      }

      return (ejt)_snowmanx;
   }

   public static vk d(String var0) {
      return new vk("skins/" + Hashing.sha1().hashUnencodedChars(aft.a(_snowman)));
   }

   public String u() {
      dwx _snowman = this.f();
      return _snowman == null ? ekj.b(this.bS()) : _snowman.f();
   }

   public float v() {
      float _snowman = 1.0F;
      if (this.bC.b) {
         _snowman *= 1.1F;
      }

      _snowman = (float)((double)_snowman * ((this.b(arl.d) / (double)this.bC.b() + 1.0) / 2.0));
      if (this.bC.b() == 0.0F || Float.isNaN(_snowman) || Float.isInfinite(_snowman)) {
         _snowman = 1.0F;
      }

      if (this.dW() && this.dY().b() == bmd.kc) {
         int _snowmanx = this.ea();
         float _snowmanxx = (float)_snowmanx / 20.0F;
         if (_snowmanxx > 1.0F) {
            _snowmanxx = 1.0F;
         } else {
            _snowmanxx *= _snowmanxx;
         }

         _snowman *= 1.0F - _snowmanxx * 0.15F;
      }

      return afm.g(djz.C().k.aQ, 1.0F, _snowman);
   }
}
