import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;

public class lq {
   public static String a = "gameteststructures";

   public static bzm a(int var0) {
      switch (_snowman) {
         case 0:
            return bzm.a;
         case 1:
            return bzm.b;
         case 2:
            return bzm.c;
         case 3:
            return bzm.d;
         default:
            throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + _snowman);
      }
   }

   public static dci a(cdj var0) {
      fx _snowman = _snowman.o();
      fx _snowmanx = _snowman.a(_snowman.j().b(-1, -1, -1));
      fx _snowmanxx = ctb.a(_snowmanx, byg.a, _snowman.l(), _snowman);
      return new dci(_snowman, _snowmanxx);
   }

   public static cra b(cdj var0) {
      fx _snowman = _snowman.o();
      fx _snowmanx = _snowman.a(_snowman.j().b(-1, -1, -1));
      fx _snowmanxx = ctb.a(_snowmanx, byg.a, _snowman.l(), _snowman);
      return new cra(_snowman, _snowmanxx);
   }

   public static void a(fx var0, fx var1, bzm var2, aag var3) {
      fx _snowman = ctb.a(_snowman.a(_snowman), byg.a, _snowman, _snowman);
      _snowman.a(_snowman, bup.er.n());
      cco _snowmanx = (cco)_snowman.c(_snowman);
      _snowmanx.d().a("test runthis");
      fx _snowmanxx = ctb.a(_snowman.b(0, 0, -1), byg.a, _snowman, _snowman);
      _snowman.a(_snowmanxx, bup.cB.n().a(_snowman));
   }

   public static void a(String var0, fx var1, fx var2, bzm var3, aag var4) {
      cra _snowman = a(_snowman, _snowman, _snowman);
      a(_snowman, _snowman.v(), _snowman);
      _snowman.a(_snowman, bup.mY.n());
      cdj _snowmanx = (cdj)_snowman.c(_snowman);
      _snowmanx.a(false);
      _snowmanx.a(new vk(_snowman));
      _snowmanx.c(_snowman);
      _snowmanx.a(cfo.a);
      _snowmanx.e(true);
   }

   public static cdj a(String var0, fx var1, bzm var2, int var3, aag var4, boolean var5) {
      fx _snowman = a(_snowman, _snowman).a();
      cra _snowmanx = a(_snowman, _snowman, _snowman);
      fx _snowmanxx;
      if (_snowman == bzm.a) {
         _snowmanxx = _snowman;
      } else if (_snowman == bzm.b) {
         _snowmanxx = _snowman.b(_snowman.w() - 1, 0, 0);
      } else if (_snowman == bzm.c) {
         _snowmanxx = _snowman.b(_snowman.u() - 1, 0, _snowman.w() - 1);
      } else {
         if (_snowman != bzm.d) {
            throw new IllegalArgumentException("Invalid rotation: " + _snowman);
         }

         _snowmanxx = _snowman.b(0, 0, _snowman.u() - 1);
      }

      a(_snowman, _snowman);
      a(_snowmanx, _snowman.v(), _snowman);
      cdj _snowmanxxx = a(_snowman, _snowmanxx, _snowman, _snowman, _snowman);
      _snowman.j().a(_snowmanx, true, false);
      _snowman.a(_snowmanx);
      return _snowmanxxx;
   }

   private static void a(fx var0, aag var1) {
      brd _snowman = new brd(_snowman);

      for (int _snowmanx = -1; _snowmanx < 4; _snowmanx++) {
         for (int _snowmanxx = -1; _snowmanxx < 4; _snowmanxx++) {
            int _snowmanxxx = _snowman.b + _snowmanx;
            int _snowmanxxxx = _snowman.c + _snowmanxx;
            _snowman.a(_snowmanxxx, _snowmanxxxx, true);
         }
      }
   }

   public static void a(cra var0, int var1, aag var2) {
      cra _snowman = new cra(_snowman.a - 2, _snowman.b - 3, _snowman.c - 3, _snowman.d + 3, _snowman.e + 20, _snowman.f + 3);
      fx.a(_snowman).forEach(var2x -> a(_snowman, var2x, _snowman));
      _snowman.j().a(_snowman, true, false);
      _snowman.a(_snowman);
      dci _snowmanx = new dci((double)_snowman.a, (double)_snowman.b, (double)_snowman.c, (double)_snowman.d, (double)_snowman.e, (double)_snowman.f);
      List<aqa> _snowmanxx = _snowman.a(aqa.class, _snowmanx, var0x -> !(var0x instanceof bfw));
      _snowmanxx.forEach(aqa::ad);
   }

   public static cra a(fx var0, fx var1, bzm var2) {
      fx _snowman = _snowman.a(_snowman).b(-1, -1, -1);
      fx _snowmanx = ctb.a(_snowman, byg.a, _snowman, _snowman);
      cra _snowmanxx = cra.a(_snowman.u(), _snowman.v(), _snowman.w(), _snowmanx.u(), _snowmanx.v(), _snowmanx.w());
      int _snowmanxxx = Math.min(_snowmanxx.a, _snowmanxx.d);
      int _snowmanxxxx = Math.min(_snowmanxx.c, _snowmanxx.f);
      fx _snowmanxxxxx = new fx(_snowman.u() - _snowmanxxx, 0, _snowman.w() - _snowmanxxxx);
      _snowmanxx.a(_snowmanxxxxx);
      return _snowmanxx;
   }

   public static Optional<fx> a(fx var0, int var1, aag var2) {
      return c(_snowman, _snowman, _snowman).stream().filter(var2x -> a(var2x, _snowman, _snowman)).findFirst();
   }

   @Nullable
   public static fx b(fx var0, int var1, aag var2) {
      Comparator<fx> _snowman = Comparator.comparingInt(var1x -> var1x.k(_snowman));
      Collection<fx> _snowmanx = c(_snowman, _snowman, _snowman);
      Optional<fx> _snowmanxx = _snowmanx.stream().min(_snowman);
      return _snowmanxx.orElse(null);
   }

   public static Collection<fx> c(fx var0, int var1, aag var2) {
      Collection<fx> _snowman = Lists.newArrayList();
      dci _snowmanx = new dci(_snowman);
      _snowmanx = _snowmanx.g((double)_snowman);

      for (int _snowmanxx = (int)_snowmanx.a; _snowmanxx <= (int)_snowmanx.d; _snowmanxx++) {
         for (int _snowmanxxx = (int)_snowmanx.b; _snowmanxxx <= (int)_snowmanx.e; _snowmanxxx++) {
            for (int _snowmanxxxx = (int)_snowmanx.c; _snowmanxxxx <= (int)_snowmanx.f; _snowmanxxxx++) {
               fx _snowmanxxxxx = new fx(_snowmanxx, _snowmanxxx, _snowmanxxxx);
               ceh _snowmanxxxxxx = _snowman.d_(_snowmanxxxxx);
               if (_snowmanxxxxxx.a(bup.mY)) {
                  _snowman.add(_snowmanxxxxx);
               }
            }
         }
      }

      return _snowman;
   }

   private static ctb a(String var0, aag var1) {
      csw _snowman = _snowman.n();
      ctb _snowmanx = _snowman.b(new vk(_snowman));
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         String _snowmanxx = _snowman + ".snbt";
         Path _snowmanxxx = Paths.get(a, _snowmanxx);
         md _snowmanxxxx = a(_snowmanxxx);
         if (_snowmanxxxx == null) {
            throw new RuntimeException("Could not find structure file " + _snowmanxxx + ", and the structure is not available in the world structures either.");
         } else {
            return _snowman.a(_snowmanxxxx);
         }
      }
   }

   private static cdj a(String var0, fx var1, bzm var2, aag var3, boolean var4) {
      _snowman.a(_snowman, bup.mY.n());
      cdj _snowman = (cdj)_snowman.c(_snowman);
      _snowman.a(cfo.b);
      _snowman.b(_snowman);
      _snowman.a(false);
      _snowman.a(new vk(_snowman));
      _snowman.a(_snowman, _snowman);
      if (_snowman.j() != fx.b) {
         return _snowman;
      } else {
         ctb _snowmanx = a(_snowman, _snowman);
         _snowman.a(_snowman, _snowman, _snowmanx);
         if (_snowman.j() == fx.b) {
            throw new RuntimeException("Failed to load structure " + _snowman);
         } else {
            return _snowman;
         }
      }
   }

   @Nullable
   private static md a(Path var0) {
      try {
         BufferedReader _snowman = Files.newBufferedReader(_snowman);
         String _snowmanx = IOUtils.toString(_snowman);
         return mu.a(_snowmanx);
      } catch (IOException var3) {
         return null;
      } catch (CommandSyntaxException var4) {
         throw new RuntimeException("Error while trying to load structure " + _snowman, var4);
      }
   }

   private static void a(int var0, fx var1, aag var2) {
      ceh _snowman = null;
      cpf _snowmanx = cpf.a(_snowman.r().b(gm.ay));
      if (_snowmanx instanceof cpf) {
         ceh[] _snowmanxx = _snowmanx.g();
         if (_snowman.v() < _snowman && _snowman.v() <= _snowmanxx.length) {
            _snowman = _snowmanxx[_snowman.v() - 1];
         }
      } else if (_snowman.v() == _snowman - 1) {
         _snowman = _snowman.v(_snowman).e().e().a();
      } else if (_snowman.v() < _snowman - 1) {
         _snowman = _snowman.v(_snowman).e().e().b();
      }

      if (_snowman == null) {
         _snowman = bup.a.n();
      }

      ef _snowmanxx = new ef(_snowman, Collections.emptySet(), null);
      _snowmanxx.a(_snowman, _snowman, 2);
      _snowman.a(_snowman, _snowman.b());
   }

   private static boolean a(fx var0, fx var1, aag var2) {
      cdj _snowman = (cdj)_snowman.c(_snowman);
      dci _snowmanx = a(_snowman).g(1.0);
      return _snowmanx.d(dcn.a(_snowman));
   }
}
