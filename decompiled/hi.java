import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

public class hi extends hg<hi> implements hf {
   private static final hf.a<hi> a = new hf.a<hi>() {
      public hi a(hg<hi> var1, StringReader var2) throws CommandSyntaxException {
         return (hi)_snowman;
      }

      public hi a(hg<hi> var1, nf var2) {
         return (hi)_snowman;
      }
   };
   private final Codec<hi> b = Codec.unit(this::f);

   protected hi(boolean var1) {
      super(_snowman, a);
   }

   public hi f() {
      return this;
   }

   @Override
   public Codec<hi> e() {
      return this.b;
   }

   @Override
   public void a(nf var1) {
   }

   @Override
   public String a() {
      return gm.V.b(this).toString();
   }
}
