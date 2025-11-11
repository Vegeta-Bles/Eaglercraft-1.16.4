import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cpe {
   public static final Codec<cpe> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.intRange(0, 256).fieldOf("height").forGetter(cpe::a), gm.Q.fieldOf("block").orElse(bup.a).forGetter(var0x -> var0x.b().b()))
            .apply(var0, cpe::new)
   );
   private final ceh b;
   private final int c;
   private int d;

   public cpe(int var1, buo var2) {
      this.c = _snowman;
      this.b = _snowman.n();
   }

   public int a() {
      return this.c;
   }

   public ceh b() {
      return this.b;
   }

   public int c() {
      return this.d;
   }

   public void a(int var1) {
      this.d = _snowman;
   }

   @Override
   public String toString() {
      return (this.c != 1 ? this.c + "*" : "") + gm.Q.b(this.b.b());
   }
}
