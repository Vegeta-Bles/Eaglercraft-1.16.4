import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class civ<FC extends cma, F extends cjl<FC>> implements chk<civ<?, ?>> {
   public static final Codec<civ<?, ?>> a = gm.aE.dispatch(var0 -> var0.e, cjl::a);
   public static final Codec<Supplier<civ<?, ?>>> b = vf.a(gm.au, a);
   public static final Codec<List<Supplier<civ<?, ?>>>> c = vf.b(gm.au, a);
   public static final Logger d = LogManager.getLogger();
   public final F e;
   public final FC f;

   public civ(F var1, FC var2) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public F b() {
      return this.e;
   }

   public FC c() {
      return this.f;
   }

   public civ<?, ?> b(cpo<?> var1) {
      return cjl.aa.b(new clv(() -> this, _snowman));
   }

   public clj a(float var1) {
      return new clj(this, _snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4) {
      return this.e.a(_snowman, _snowman, _snowman, _snowman, this.f);
   }

   public Stream<civ<?, ?>> d() {
      return Stream.concat(Stream.of(this), this.f.an_());
   }
}
