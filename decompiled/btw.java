import com.google.common.collect.Maps;
import java.util.Map;

public class btw extends btm {
   public static final cfg a = cex.aD;
   private static final Map<bkx, buo> b = Maps.newHashMap();
   private static final ddh c = buo.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

   public btw(bkx var1, ceg.c var2) {
      super(_snowman, _snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)));
      b.put(_snowman, this);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return _snowman.d_(_snowman.c()).c().b();
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return c;
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, Integer.valueOf(afm.c((double)((180.0F + _snowman.h()) * 16.0F / 360.0F) + 0.5) & 15));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman == gc.a && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, Integer.valueOf(_snowman.a(_snowman.c(a), 16)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(a, Integer.valueOf(_snowman.a(_snowman.c(a), 16)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   public static buo a(bkx var0) {
      return b.getOrDefault(_snowman, bup.ha);
   }
}
