import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

public class hc implements hf {
   public static final hf.a<hc> a = new hf.a<hc>() {
      public hc a(hg<hc> var1, StringReader var2) throws CommandSyntaxException {
         _snowman.expect(' ');
         return new hc(_snowman, new ei(_snowman, false).a(false).b());
      }

      public hc a(hg<hc> var1, nf var2) {
         return new hc(_snowman, buo.m.a(_snowman.i()));
      }
   };
   private final hg<hc> b;
   private final ceh c;

   public static Codec<hc> a(hg<hc> var0) {
      return ceh.b.xmap(var1 -> new hc(_snowman, var1), var0x -> var0x.c);
   }

   public hc(hg<hc> var1, ceh var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) {
      _snowman.d(buo.m.a(this.c));
   }

   @Override
   public String a() {
      return gm.V.b(this.b()) + " " + ei.a(this.c);
   }

   @Override
   public hg<hc> b() {
      return this.b;
   }

   public ceh c() {
      return this.c;
   }
}
