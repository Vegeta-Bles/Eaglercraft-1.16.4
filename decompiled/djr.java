import java.util.Optional;
import javax.annotation.Nullable;

public class djr extends dkf {
   public djr(dez var1) {
      this(_snowman, _snowman.t());
   }

   private djr(dez var1, @Nullable der var2) {
      super("options.fullscreen.resolution", -1.0, _snowman != null ? (double)(_snowman.e() - 1) : -1.0, 1.0F, var2x -> {
         if (_snowman == null) {
            return -1.0;
         } else {
            Optional<dey> _snowman = _snowman.f();
            return _snowman.<Double>map(var1x -> (double)_snowman.a(var1x)).orElse(-1.0);
         }
      }, (var2x, var3) -> {
         if (_snowman != null) {
            if (var3 == -1.0) {
               _snowman.a(Optional.empty());
            } else {
               _snowman.a(Optional.of(_snowman.a(var3.intValue())));
            }
         }
      }, (var1x, var2x) -> {
         if (_snowman == null) {
            return new of("options.fullscreen.unavailable");
         } else {
            double _snowman = var2x.a(var1x);
            return _snowman == -1.0 ? var2x.a(new of("options.fullscreen.current")) : var2x.a(new oe(_snowman.a((int)_snowman).toString()));
         }
      });
   }
}
