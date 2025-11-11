import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class bkh extends blx {
   @Deprecated
   private final buo a;

   public bkh(buo var1, blx.a var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public aou a(boa var1) {
      aou _snowman = this.a(new bny(_snowman));
      return !_snowman.a() && this.s() ? this.a(_snowman.p(), _snowman.n(), _snowman.o()).a() : _snowman;
   }

   public aou a(bny var1) {
      if (!_snowman.b()) {
         return aou.d;
      } else {
         bny _snowman = this.b(_snowman);
         if (_snowman == null) {
            return aou.d;
         } else {
            ceh _snowmanx = this.c(_snowman);
            if (_snowmanx == null) {
               return aou.d;
            } else if (!this.a(_snowman, _snowmanx)) {
               return aou.d;
            } else {
               fx _snowmanxx = _snowman.a();
               brx _snowmanxxx = _snowman.p();
               bfw _snowmanxxxx = _snowman.n();
               bmb _snowmanxxxxx = _snowman.m();
               ceh _snowmanxxxxxx = _snowmanxxx.d_(_snowmanxx);
               buo _snowmanxxxxxxx = _snowmanxxxxxx.b();
               if (_snowmanxxxxxxx == _snowmanx.b()) {
                  _snowmanxxxxxx = this.a(_snowmanxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  this.a(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  _snowmanxxxxxxx.a(_snowmanxxx, _snowmanxx, _snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxx);
                  if (_snowmanxxxx instanceof aah) {
                     ac.y.a((aah)_snowmanxxxx, _snowmanxx, _snowmanxxxxx);
                  }
               }

               cae _snowmanxxxxxxxx = _snowmanxxxxxx.o();
               _snowmanxxx.a(_snowmanxxxx, _snowmanxx, this.a(_snowmanxxxxxx), adr.e, (_snowmanxxxxxxxx.a() + 1.0F) / 2.0F, _snowmanxxxxxxxx.b() * 0.8F);
               if (_snowmanxxxx == null || !_snowmanxxxx.bC.d) {
                  _snowmanxxxxx.g(1);
               }

               return aou.a(_snowmanxxx.v);
            }
         }
      }
   }

   protected adp a(ceh var1) {
      return _snowman.o().e();
   }

   @Nullable
   public bny b(bny var1) {
      return _snowman;
   }

   protected boolean a(fx var1, brx var2, @Nullable bfw var3, bmb var4, ceh var5) {
      return a(_snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   protected ceh c(bny var1) {
      ceh _snowman = this.e().a(_snowman);
      return _snowman != null && this.b(_snowman, _snowman) ? _snowman : null;
   }

   private ceh a(fx var1, brx var2, bmb var3, ceh var4) {
      ceh _snowman = _snowman;
      md _snowmanx = _snowman.o();
      if (_snowmanx != null) {
         md _snowmanxx = _snowmanx.p("BlockStateTag");
         cei<buo, ceh> _snowmanxxx = _snowman.b().m();

         for (String _snowmanxxxx : _snowmanxx.d()) {
            cfj<?> _snowmanxxxxx = _snowmanxxx.a(_snowmanxxxx);
            if (_snowmanxxxxx != null) {
               String _snowmanxxxxxx = _snowmanxx.c(_snowmanxxxx).f_();
               _snowman = a(_snowman, _snowmanxxxxx, _snowmanxxxxxx);
            }
         }
      }

      if (_snowman != _snowman) {
         _snowman.a(_snowman, _snowman, 2);
      }

      return _snowman;
   }

   private static <T extends Comparable<T>> ceh a(ceh var0, cfj<T> var1, String var2) {
      return _snowman.b(_snowman).map(var2x -> _snowman.a(_snowman, var2x)).orElse(_snowman);
   }

   protected boolean b(bny var1, ceh var2) {
      bfw _snowman = _snowman.n();
      dcs _snowmanx = _snowman == null ? dcs.a() : dcs.a(_snowman);
      return (!this.d() || _snowman.a((brz)_snowman.p(), _snowman.a())) && _snowman.p().a(_snowman, _snowman.a(), _snowmanx);
   }

   protected boolean d() {
      return true;
   }

   protected boolean a(bny var1, ceh var2) {
      return _snowman.p().a(_snowman.a(), _snowman, 11);
   }

   public static boolean a(brx var0, @Nullable bfw var1, fx var2, bmb var3) {
      MinecraftServer _snowman = _snowman.l();
      if (_snowman == null) {
         return false;
      } else {
         md _snowmanx = _snowman.b("BlockEntityTag");
         if (_snowmanx != null) {
            ccj _snowmanxx = _snowman.c(_snowman);
            if (_snowmanxx != null) {
               if (!_snowman.v && _snowmanxx.t() && (_snowman == null || !_snowman.eV())) {
                  return false;
               }

               md _snowmanxxx = _snowmanxx.a(new md());
               md _snowmanxxxx = _snowmanxxx.g();
               _snowmanxxx.a(_snowmanx);
               _snowmanxxx.b("x", _snowman.u());
               _snowmanxxx.b("y", _snowman.v());
               _snowmanxxx.b("z", _snowman.w());
               if (!_snowmanxxx.equals(_snowmanxxxx)) {
                  _snowmanxx.a(_snowman.d_(_snowman), _snowmanxxx);
                  _snowmanxx.X_();
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public String a() {
      return this.e().i();
   }

   @Override
   public void a(bks var1, gj<bmb> var2) {
      if (this.a(_snowman)) {
         this.e().a(_snowman, _snowman);
      }
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.e().a(_snowman, _snowman, _snowman, _snowman);
   }

   public buo e() {
      return this.a;
   }

   public void a(Map<buo, blx> var1, blx var2) {
      _snowman.put(this.e(), _snowman);
   }
}
