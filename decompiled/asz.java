import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class asz extends arv<aqu> {
   public asz() {
      super(ImmutableMap.of(ayd.i, aye.a, ayd.m, aye.b, ayd.n, aye.c, ayd.q, aye.c));
   }

   protected boolean a(aag var1, aqu var2) {
      return _snowman.u_().nextInt(10) == 0 && this.e(_snowman);
   }

   protected void a(aag var1, aqu var2, long var3) {
      aqm _snowman = this.b((aqm)_snowman);
      if (_snowman != null) {
         this.a(_snowman, _snowman, _snowman);
      } else {
         Optional<aqm> _snowmanx = this.b(_snowman);
         if (_snowmanx.isPresent()) {
            a(_snowman, _snowmanx.get());
         } else {
            this.a(_snowman).ifPresent(var1x -> a(_snowman, var1x));
         }
      }
   }

   private void a(aag var1, aqu var2, aqm var3) {
      for (int _snowman = 0; _snowman < 10; _snowman++) {
         dcn _snowmanx = azj.b(_snowman, 20, 8);
         if (_snowmanx != null && _snowman.a_(new fx(_snowmanx))) {
            _snowman.cJ().a(ayd.m, new ayf(_snowmanx, 0.6F, 0));
            return;
         }
      }
   }

   private static void a(aqu var0, aqm var1) {
      arf<?> _snowman = _snowman.cJ();
      _snowman.a(ayd.q, _snowman);
      _snowman.a(ayd.n, new asd(_snowman, true));
      _snowman.a(ayd.m, new ayf(new asd(_snowman, false), 0.6F, 1));
   }

   private Optional<aqm> a(aqu var1) {
      return this.d(_snowman).stream().findAny();
   }

   private Optional<aqm> b(aqu var1) {
      Map<aqm, Integer> _snowman = this.c(_snowman);
      return _snowman.entrySet()
         .stream()
         .sorted(Comparator.comparingInt(Entry::getValue))
         .filter(var0 -> var0.getValue() > 0 && var0.getValue() <= 5)
         .map(Entry::getKey)
         .findFirst();
   }

   private Map<aqm, Integer> c(aqu var1) {
      Map<aqm, Integer> _snowman = Maps.newHashMap();
      this.d(_snowman).stream().filter(this::c).forEach(var2x -> {
         Integer var10000 = _snowman.compute(this.a(var2x), (var0, var1x) -> var1x == null ? 1 : var1x + 1);
      });
      return _snowman;
   }

   private List<aqm> d(aqu var1) {
      return _snowman.cJ().c(ayd.i).get();
   }

   private aqm a(aqm var1) {
      return _snowman.cJ().c(ayd.q).get();
   }

   @Nullable
   private aqm b(aqm var1) {
      return _snowman.cJ().c(ayd.i).get().stream().filter(var2 -> this.a(_snowman, var2)).findAny().orElse(null);
   }

   private boolean c(aqm var1) {
      return _snowman.cJ().c(ayd.q).isPresent();
   }

   private boolean a(aqm var1, aqm var2) {
      return _snowman.cJ().c(ayd.q).filter(var1x -> var1x == _snowman).isPresent();
   }

   private boolean e(aqu var1) {
      return _snowman.cJ().a(ayd.i);
   }
}
