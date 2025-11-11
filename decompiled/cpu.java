import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cpu implements clw {
   public static final Codec<cpu> a = RecordCodecBuilder.create(
      var0 -> var0.group(cpo.a.fieldOf("outer").forGetter(cpu::a), cpo.a.fieldOf("inner").forGetter(cpu::b)).apply(var0, cpu::new)
   );
   private final cpo<?> c;
   private final cpo<?> d;

   public cpu(cpo<?> var1, cpo<?> var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   public cpo<?> a() {
      return this.c;
   }

   public cpo<?> b() {
      return this.d;
   }
}
