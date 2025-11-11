import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

public class he implements hf {
   public static final hf.a<he> a = new hf.a<he>() {
      public he a(hg<he> var1, StringReader var2) throws CommandSyntaxException {
         _snowman.expect(' ');
         ey _snowman = new ey(_snowman, false).h();
         bmb _snowmanx = new ex(_snowman.b(), _snowman.c()).a(1, false);
         return new he(_snowman, _snowmanx);
      }

      public he a(hg<he> var1, nf var2) {
         return new he(_snowman, _snowman.n());
      }
   };
   private final hg<he> b;
   private final bmb c;

   public static Codec<he> a(hg<he> var0) {
      return bmb.a.xmap(var1 -> new he(_snowman, var1), var0x -> var0x.c);
   }

   public he(hg<he> var1, bmb var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) {
      _snowman.a(this.c);
   }

   @Override
   public String a() {
      return gm.V.b(this.b()) + " " + new ex(this.c.b(), this.c.o()).c();
   }

   @Override
   public hg<he> b() {
      return this.b;
   }

   public bmb c() {
      return this.c;
   }
}
