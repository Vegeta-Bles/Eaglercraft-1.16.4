import com.google.common.collect.Sets;
import java.util.Set;

public class eoa implements eof {
   private static final Set<buo> a = Sets.newHashSet(
      new buo[]{
         bup.J,
         bup.K,
         bup.L,
         bup.M,
         bup.N,
         bup.O,
         bup.mh,
         bup.mq,
         bup.V,
         bup.W,
         bup.X,
         bup.Y,
         bup.Z,
         bup.aa,
         bup.mj,
         bup.ms,
         bup.ah,
         bup.ai,
         bup.aj,
         bup.ak,
         bup.al,
         bup.am,
         bup.iK,
         bup.mn
      }
   );
   private static final nr b = new of("tutorial.find_tree.title");
   private static final nr c = new of("tutorial.find_tree.description");
   private final eoe d;
   private dms e;
   private int f;

   public eoa(eoe var1) {
      this.d = _snowman;
   }

   @Override
   public void a() {
      this.f++;
      if (this.d.f() != bru.b) {
         this.d.a(eog.f);
      } else {
         if (this.f == 1) {
            dzm _snowman = this.d.e().s;
            if (_snowman != null) {
               for (buo _snowmanx : a) {
                  if (_snowman.bm.h(new bmb(_snowmanx))) {
                     this.d.a(eog.e);
                     return;
                  }
               }

               if (a(_snowman)) {
                  this.d.a(eog.e);
                  return;
               }
            }
         }

         if (this.f >= 6000 && this.e == null) {
            this.e = new dms(dms.a.c, b, c, false);
            this.d.e().an().a(this.e);
         }
      }
   }

   @Override
   public void b() {
      if (this.e != null) {
         this.e.b();
         this.e = null;
      }
   }

   @Override
   public void a(dwt var1, dcl var2) {
      if (_snowman.c() == dcl.a.b) {
         ceh _snowman = _snowman.d_(((dcj)_snowman).a());
         if (a.contains(_snowman.b())) {
            this.d.a(eog.c);
         }
      }
   }

   @Override
   public void a(bmb var1) {
      for (buo _snowman : a) {
         if (_snowman.b() == _snowman.h()) {
            this.d.a(eog.e);
            return;
         }
      }
   }

   public static boolean a(dzm var0) {
      for (buo _snowman : a) {
         if (_snowman.D().a(aea.a.b(_snowman)) > 0) {
            return true;
         }
      }

      return false;
   }
}
