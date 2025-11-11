import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.List;

public class dsv implements dsr, dss {
   private static final Ordering<dwx> a = Ordering.from((var0, var1) -> ComparisonChain.start().compare(var0.a().getId(), var1.a().getId()).result());
   private static final nr b = new of("spectatorMenu.teleport");
   private static final nr c = new of("spectatorMenu.teleport.prompt");
   private final List<dss> d = Lists.newArrayList();

   public dsv() {
      this(a.sortedCopy(djz.C().w().e()));
   }

   public dsv(Collection<dwx> var1) {
      for (dwx _snowman : a.sortedCopy(_snowman)) {
         if (_snowman.b() != bru.e) {
            this.d.add(new dso(_snowman.a()));
         }
      }
   }

   @Override
   public List<dss> a() {
      return this.d;
   }

   @Override
   public nr b() {
      return c;
   }

   @Override
   public void a(dsq var1) {
      _snowman.a(this);
   }

   @Override
   public nr aA_() {
      return b;
   }

   @Override
   public void a(dfm var1, float var2, int var3) {
      djz.C().M().a(dml.a);
      dkw.a(_snowman, 0, 0, 0.0F, 0.0F, 16, 16, 256, 256);
   }

   @Override
   public boolean aB_() {
      return !this.d.isEmpty();
   }
}
