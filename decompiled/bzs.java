import java.util.List;
import javax.annotation.Nullable;

public class bzs extends bud {
   public static final cfe<gc> a = bvz.a;
   public static final vk b = new vk("contents");
   @Nullable
   private final bkx c;

   public bzs(@Nullable bkx var1, ceg.c var2) {
      super(_snowman);
      this.c = _snowman;
      this.j(this.n.b().a(a, gc.b));
   }

   @Override
   public ccj a(brc var1) {
      return new cde(this.c);
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.b;
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         return aou.a;
      } else if (_snowman.a_()) {
         return aou.b;
      } else {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cde) {
            cde _snowmanx = (cde)_snowman;
            boolean _snowmanxx;
            if (_snowmanx.j() == cde.a.a) {
               gc _snowmanxxx = _snowman.c(a);
               _snowmanxx = _snowman.b(aoz.a(_snowman, _snowmanxxx));
            } else {
               _snowmanxx = true;
            }

            if (_snowmanxx) {
               _snowman.a(_snowmanx);
               _snowman.a(aea.ap);
               bet.a(_snowman, true);
            }

            return aou.b;
         } else {
            return aou.c;
         }
      }
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, _snowman.j());
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cde) {
         cde _snowmanx = (cde)_snowman;
         if (!_snowman.v && _snowman.b_() && !_snowmanx.c()) {
            bmb _snowmanxx = b(this.c());
            md _snowmanxxx = _snowmanx.e(new md());
            if (!_snowmanxxx.isEmpty()) {
               _snowmanxx.a("BlockEntityTag", _snowmanxxx);
            }

            if (_snowmanx.S()) {
               _snowmanxx.a(_snowmanx.T());
            }

            bcv _snowmanxxxx = new bcv(_snowman, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, _snowmanxx);
            _snowmanxxxx.m();
            _snowman.c(_snowmanxxxx);
         } else {
            _snowmanx.d(_snowman);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public List<bmb> a(ceh var1, cyv.a var2) {
      ccj _snowman = _snowman.b(dbc.h);
      if (_snowman instanceof cde) {
         cde _snowmanx = (cde)_snowman;
         _snowman = _snowman.a(b, (var1x, var2x) -> {
            for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
               var2x.accept(_snowman.a(_snowmanxx));
            }
         });
      }

      return super.a(_snowman, _snowman);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, aqm var4, bmb var5) {
      if (_snowman.t()) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cde) {
            ((cde)_snowman).a(_snowman.r());
         }
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof cde) {
            _snowman.c(_snowman, _snowman.b());
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(bmb var1, @Nullable brc var2, List<nr> var3, bnl var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      md _snowman = _snowman.b("BlockEntityTag");
      if (_snowman != null) {
         if (_snowman.c("LootTable", 8)) {
            _snowman.add(new oe("???????"));
         }

         if (_snowman.c("Items", 9)) {
            gj<bmb> _snowmanx = gj.a(27, bmb.b);
            aoo.b(_snowman, _snowmanx);
            int _snowmanxx = 0;
            int _snowmanxxx = 0;

            for (bmb _snowmanxxxx : _snowmanx) {
               if (!_snowmanxxxx.a()) {
                  _snowmanxxx++;
                  if (_snowmanxx <= 4) {
                     _snowmanxx++;
                     nx _snowmanxxxxx = _snowmanxxxx.r().e();
                     _snowmanxxxxx.c(" x").c(String.valueOf(_snowmanxxxx.E()));
                     _snowman.add(_snowmanxxxxx);
                  }
               }
            }

            if (_snowmanxxx - _snowmanxx > 0) {
               _snowman.add(new of("container.shulkerBox.more", _snowmanxxx - _snowmanxx).a(k.u));
            }
         }
      }
   }

   @Override
   public cvc f(ceh var1) {
      return cvc.b;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      ccj _snowman = _snowman.c(_snowman);
      return _snowman instanceof cde ? dde.a(((cde)_snowman).a(_snowman)) : dde.b();
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return bic.b((aon)_snowman.c(_snowman));
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      bmb _snowman = super.a(_snowman, _snowman, _snowman);
      cde _snowmanx = (cde)_snowman.c(_snowman);
      md _snowmanxx = _snowmanx.e(new md());
      if (!_snowmanxx.isEmpty()) {
         _snowman.a("BlockEntityTag", _snowmanxx);
      }

      return _snowman;
   }

   @Nullable
   public static bkx b(blx var0) {
      return c(buo.a(_snowman));
   }

   @Nullable
   public static bkx c(buo var0) {
      return _snowman instanceof bzs ? ((bzs)_snowman).c() : null;
   }

   public static buo a(@Nullable bkx var0) {
      if (_snowman == null) {
         return bup.iP;
      } else {
         switch (_snowman) {
            case a:
               return bup.iQ;
            case b:
               return bup.iR;
            case c:
               return bup.iS;
            case d:
               return bup.iT;
            case e:
               return bup.iU;
            case f:
               return bup.iV;
            case g:
               return bup.iW;
            case h:
               return bup.iX;
            case i:
               return bup.iY;
            case j:
               return bup.iZ;
            case k:
            default:
               return bup.ja;
            case l:
               return bup.jb;
            case m:
               return bup.jc;
            case n:
               return bup.jd;
            case o:
               return bup.je;
            case p:
               return bup.jf;
         }
      }
   }

   @Nullable
   public bkx c() {
      return this.c;
   }

   public static bmb b(@Nullable bkx var0) {
      return new bmb(a(_snowman));
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(a)));
   }
}
