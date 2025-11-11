import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;

public class bnu {
   private static final List<bnu.a<bnt>> a = Lists.newArrayList();
   private static final List<bnu.a<blx>> b = Lists.newArrayList();
   private static final List<bon> c = Lists.newArrayList();
   private static final Predicate<bmb> d = var0 -> {
      for (bon _snowman : c) {
         if (_snowman.a(var0)) {
            return true;
         }
      }

      return false;
   };

   public static boolean a(bmb var0) {
      return b(_snowman) || c(_snowman);
   }

   protected static boolean b(bmb var0) {
      int _snowman = 0;

      for (int _snowmanx = b.size(); _snowman < _snowmanx; _snowman++) {
         if (b.get(_snowman).b.a(_snowman)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean c(bmb var0) {
      int _snowman = 0;

      for (int _snowmanx = a.size(); _snowman < _snowmanx; _snowman++) {
         if (a.get(_snowman).b.a(_snowman)) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(bnt var0) {
      int _snowman = 0;

      for (int _snowmanx = a.size(); _snowman < _snowmanx; _snowman++) {
         if (a.get(_snowman).c == _snowman) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(bmb var0, bmb var1) {
      return !d.test(_snowman) ? false : b(_snowman, _snowman) || c(_snowman, _snowman);
   }

   protected static boolean b(bmb var0, bmb var1) {
      blx _snowman = _snowman.b();
      int _snowmanx = 0;

      for (int _snowmanxx = b.size(); _snowmanx < _snowmanxx; _snowmanx++) {
         bnu.a<blx> _snowmanxxx = b.get(_snowmanx);
         if (_snowmanxxx.a == _snowman && _snowmanxxx.b.a(_snowman)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean c(bmb var0, bmb var1) {
      bnt _snowman = bnv.d(_snowman);
      int _snowmanx = 0;

      for (int _snowmanxx = a.size(); _snowmanx < _snowmanxx; _snowmanx++) {
         bnu.a<bnt> _snowmanxxx = a.get(_snowmanx);
         if (_snowmanxxx.a == _snowman && _snowmanxxx.b.a(_snowman)) {
            return true;
         }
      }

      return false;
   }

   public static bmb d(bmb var0, bmb var1) {
      if (!_snowman.a()) {
         bnt _snowman = bnv.d(_snowman);
         blx _snowmanx = _snowman.b();
         int _snowmanxx = 0;

         for (int _snowmanxxx = b.size(); _snowmanxx < _snowmanxxx; _snowmanxx++) {
            bnu.a<blx> _snowmanxxxx = b.get(_snowmanxx);
            if (_snowmanxxxx.a == _snowmanx && _snowmanxxxx.b.a(_snowman)) {
               return bnv.a(new bmb(_snowmanxxxx.c), _snowman);
            }
         }

         _snowmanxx = 0;

         for (int _snowmanxxxx = a.size(); _snowmanxx < _snowmanxxxx; _snowmanxx++) {
            bnu.a<bnt> _snowmanxxxxx = a.get(_snowmanxx);
            if (_snowmanxxxxx.a == _snowman && _snowmanxxxxx.b.a(_snowman)) {
               return bnv.a(new bmb(_snowmanx), _snowmanxxxxx.c);
            }
         }
      }

      return _snowman;
   }

   public static void a() {
      a(bmd.nv);
      a(bmd.qj);
      a(bmd.qm);
      a(bmd.nv, bmd.kU, bmd.qj);
      a(bmd.qj, bmd.qi, bmd.qm);
      a(bnw.b, bmd.nE, bnw.c);
      a(bnw.b, bmd.ns, bnw.c);
      a(bnw.b, bmd.pA, bnw.c);
      a(bnw.b, bmd.nz, bnw.c);
      a(bnw.b, bmd.nx, bnw.c);
      a(bnw.b, bmd.mM, bnw.c);
      a(bnw.b, bmd.nA, bnw.c);
      a(bnw.b, bmd.mk, bnw.d);
      a(bnw.b, bmd.lP, bnw.c);
      a(bnw.b, bmd.nu, bnw.e);
      a(bnw.e, bmd.pd, bnw.f);
      a(bnw.f, bmd.lP, bnw.g);
      a(bnw.f, bmd.ny, bnw.h);
      a(bnw.g, bmd.ny, bnw.i);
      a(bnw.h, bmd.lP, bnw.i);
      a(bnw.e, bmd.nA, bnw.m);
      a(bnw.m, bmd.lP, bnw.n);
      a(bnw.e, bmd.pA, bnw.j);
      a(bnw.j, bmd.lP, bnw.k);
      a(bnw.j, bmd.mk, bnw.l);
      a(bnw.j, bmd.ny, bnw.r);
      a(bnw.k, bmd.ny, bnw.s);
      a(bnw.r, bmd.lP, bnw.s);
      a(bnw.r, bmd.mk, bnw.t);
      a(bnw.e, bmd.jY, bnw.u);
      a(bnw.u, bmd.lP, bnw.v);
      a(bnw.u, bmd.mk, bnw.w);
      a(bnw.o, bmd.ny, bnw.r);
      a(bnw.p, bmd.ny, bnw.s);
      a(bnw.e, bmd.mM, bnw.o);
      a(bnw.o, bmd.lP, bnw.p);
      a(bnw.o, bmd.mk, bnw.q);
      a(bnw.e, bmd.mo, bnw.x);
      a(bnw.x, bmd.lP, bnw.y);
      a(bnw.e, bmd.nE, bnw.z);
      a(bnw.z, bmd.mk, bnw.A);
      a(bnw.z, bmd.ny, bnw.B);
      a(bnw.A, bmd.ny, bnw.C);
      a(bnw.B, bmd.mk, bnw.C);
      a(bnw.D, bmd.ny, bnw.B);
      a(bnw.E, bmd.ny, bnw.B);
      a(bnw.F, bmd.ny, bnw.C);
      a(bnw.e, bmd.nx, bnw.D);
      a(bnw.D, bmd.lP, bnw.E);
      a(bnw.D, bmd.mk, bnw.F);
      a(bnw.e, bmd.ns, bnw.G);
      a(bnw.G, bmd.lP, bnw.H);
      a(bnw.G, bmd.mk, bnw.I);
      a(bnw.e, bmd.nz, bnw.J);
      a(bnw.J, bmd.lP, bnw.K);
      a(bnw.J, bmd.mk, bnw.L);
      a(bnw.b, bmd.ny, bnw.M);
      a(bnw.M, bmd.lP, bnw.N);
      a(bnw.e, bmd.qN, bnw.P);
      a(bnw.P, bmd.lP, bnw.Q);
   }

   private static void a(blx var0, blx var1, blx var2) {
      if (!(_snowman instanceof bmn)) {
         throw new IllegalArgumentException("Expected a potion, got: " + gm.T.b(_snowman));
      } else if (!(_snowman instanceof bmn)) {
         throw new IllegalArgumentException("Expected a potion, got: " + gm.T.b(_snowman));
      } else {
         b.add(new bnu.a<>(_snowman, bon.a(_snowman), _snowman));
      }
   }

   private static void a(blx var0) {
      if (!(_snowman instanceof bmn)) {
         throw new IllegalArgumentException("Expected a potion, got: " + gm.T.b(_snowman));
      } else {
         c.add(bon.a(_snowman));
      }
   }

   private static void a(bnt var0, blx var1, bnt var2) {
      a.add(new bnu.a<>(_snowman, bon.a(_snowman), _snowman));
   }

   static class a<T> {
      private final T a;
      private final bon b;
      private final T c;

      public a(T var1, bon var2, T var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
