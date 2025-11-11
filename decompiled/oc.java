import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class oc {
   private final String a;
   private final List<ob> b;
   private final Int2IntFunction c;

   private oc(String var1, List<ob> var2, Int2IntFunction var3) {
      this.a = _snowman;
      this.b = ImmutableList.copyOf(_snowman);
      this.c = _snowman;
   }

   public String a() {
      return this.a;
   }

   public List<afa> a(int var1, int var2, boolean var3) {
      if (_snowman == 0) {
         return ImmutableList.of();
      } else {
         List<afa> _snowman = Lists.newArrayList();
         ob _snowmanx = this.b.get(_snowman);
         int _snowmanxx = _snowman;

         for (int _snowmanxxx = 1; _snowmanxxx < _snowman; _snowmanxxx++) {
            int _snowmanxxxx = _snowman + _snowmanxxx;
            ob _snowmanxxxxx = this.b.get(_snowmanxxxx);
            if (!_snowmanxxxxx.equals(_snowmanx)) {
               String _snowmanxxxxxx = this.a.substring(_snowmanxx, _snowmanxxxx);
               _snowman.add(_snowman ? afa.b(_snowmanxxxxxx, _snowmanx, this.c) : afa.a(_snowmanxxxxxx, _snowmanx));
               _snowmanx = _snowmanxxxxx;
               _snowmanxx = _snowmanxxxx;
            }
         }

         if (_snowmanxx < _snowman + _snowman) {
            String _snowmanxxxx = this.a.substring(_snowmanxx, _snowman + _snowman);
            _snowman.add(_snowman ? afa.b(_snowmanxxxx, _snowmanx, this.c) : afa.a(_snowmanxxxx, _snowmanx));
         }

         return _snowman ? Lists.reverse(_snowman) : _snowman;
      }
   }

   public static oc a(nu var0, Int2IntFunction var1, UnaryOperator<String> var2) {
      StringBuilder _snowman = new StringBuilder();
      List<ob> _snowmanx = Lists.newArrayList();
      _snowman.a((var2x, var3x) -> {
         afr.c(var3x, var2x, (var2xx, var3xx, var4x) -> {
            _snowman.appendCodePoint(var4x);
            int _snowmanxx = Character.charCount(var4x);

            for (int _snowmanx = 0; _snowmanx < _snowmanxx; _snowmanx++) {
               _snowman.add(var3xx);
            }

            return true;
         });
         return Optional.empty();
      }, ob.a);
      return new oc(_snowman.apply(_snowman.toString()), _snowmanx, _snowman);
   }
}
