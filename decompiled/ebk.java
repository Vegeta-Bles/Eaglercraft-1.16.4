import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ebk {
   public static final ebk a = new ebk();
   private final List<ebj> b = Lists.newArrayList();
   private final List<elo> c;

   private ebk() {
      this.c = Collections.emptyList();
   }

   public ebk(els var1, ebf var2, Function<vk, ely> var3, List<ebj> var4) {
      this.c = _snowman.stream().map(var3x -> {
         ely _snowman = _snowman.apply(var3x.a());
         return Objects.equals(_snowman, _snowman) ? null : _snowman.a(var3x.a(), elp.a);
      }).collect(Collectors.toList());
      Collections.reverse(this.c);

      for (int _snowman = _snowman.size() - 1; _snowman >= 0; _snowman--) {
         this.b.add(_snowman.get(_snowman));
      }
   }

   @Nullable
   public elo a(elo var1, bmb var2, @Nullable dwt var3, @Nullable aqm var4) {
      if (!this.b.isEmpty()) {
         for (int _snowman = 0; _snowman < this.b.size(); _snowman++) {
            ebj _snowmanx = this.b.get(_snowman);
            if (_snowmanx.a(_snowman, _snowman, _snowman)) {
               elo _snowmanxx = this.c.get(_snowman);
               if (_snowmanxx == null) {
                  return _snowman;
               }

               return _snowmanxx;
            }
         }
      }

      return _snowman;
   }
}
