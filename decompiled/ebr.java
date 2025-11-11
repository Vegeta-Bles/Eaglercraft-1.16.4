import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ebr implements ebq {
   private static final Splitter c = Splitter.on('|').omitEmptyStrings();
   private final String d;
   private final String e;

   public ebr(String var1, String var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   @Override
   public Predicate<ceh> getPredicate(cei<buo, ceh> var1) {
      cfj<?> _snowman = _snowman.a(this.d);
      if (_snowman == null) {
         throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.d, _snowman.c().toString()));
      } else {
         String _snowmanx = this.e;
         boolean _snowmanxx = !_snowmanx.isEmpty() && _snowmanx.charAt(0) == '!';
         if (_snowmanxx) {
            _snowmanx = _snowmanx.substring(1);
         }

         List<String> _snowmanxxx = c.splitToList(_snowmanx);
         if (_snowmanxxx.isEmpty()) {
            throw new RuntimeException(String.format("Empty value '%s' for property '%s' on '%s'", this.e, this.d, _snowman.c().toString()));
         } else {
            Predicate<ceh> _snowmanxxxx;
            if (_snowmanxxx.size() == 1) {
               _snowmanxxxx = this.a(_snowman, _snowman, _snowmanx);
            } else {
               List<Predicate<ceh>> _snowmanxxxxx = _snowmanxxx.stream().map(var3x -> this.a(_snowman, _snowman, var3x)).collect(Collectors.toList());
               _snowmanxxxx = var1x -> _snowman.stream().anyMatch(var1xx -> var1xx.test(var1x));
            }

            return _snowmanxx ? _snowmanxxxx.negate() : _snowmanxxxx;
         }
      }
   }

   private Predicate<ceh> a(cei<buo, ceh> var1, cfj<?> var2, String var3) {
      Optional<?> _snowman = _snowman.b(_snowman);
      if (!_snowman.isPresent()) {
         throw new RuntimeException(String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", _snowman, this.d, _snowman.c().toString(), this.e));
      } else {
         return var2x -> var2x.c(_snowman).equals(_snowman.get());
      }
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("key", this.d).add("value", this.e).toString();
   }
}
