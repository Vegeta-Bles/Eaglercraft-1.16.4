import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;

@FunctionalInterface
public interface afa {
   afa a = var0 -> true;

   boolean accept(afb var1);

   static afa a(int var0, ob var1) {
      return var2 -> var2.accept(0, _snowman, _snowman);
   }

   static afa a(String var0, ob var1) {
      return _snowman.isEmpty() ? a : var2 -> afr.a(_snowman, _snowman, var2);
   }

   static afa b(String var0, ob var1, Int2IntFunction var2) {
      return _snowman.isEmpty() ? a : var3 -> afr.b(_snowman, _snowman, a(var3, _snowman));
   }

   static afb a(afb var0, Int2IntFunction var1) {
      return (var2, var3, var4) -> _snowman.accept(var2, var3, (Integer)_snowman.apply(var4));
   }

   static afa a(afa var0, afa var1) {
      return b(_snowman, _snowman);
   }

   static afa a(List<afa> var0) {
      int _snowman = _snowman.size();
      switch (_snowman) {
         case 0:
            return a;
         case 1:
            return _snowman.get(0);
         case 2:
            return b(_snowman.get(0), _snowman.get(1));
         default:
            return b(ImmutableList.copyOf(_snowman));
      }
   }

   static afa b(afa var0, afa var1) {
      return var2 -> _snowman.accept(var2) && _snowman.accept(var2);
   }

   static afa b(List<afa> var0) {
      return var1 -> {
         for (afa _snowman : _snowman) {
            if (!_snowman.accept(var1)) {
               return false;
            }
         }

         return true;
      };
   }
}
