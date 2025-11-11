import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;

public class ayc<T> {
   private final T a;
   private long b;

   public ayc(T var1, long var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public void a() {
      if (this.e()) {
         this.b--;
      }
   }

   public static <T> ayc<T> a(T var0) {
      return new ayc<>(_snowman, Long.MAX_VALUE);
   }

   public static <T> ayc<T> a(T var0, long var1) {
      return new ayc<>(_snowman, _snowman);
   }

   public T c() {
      return this.a;
   }

   public boolean d() {
      return this.b <= 0L;
   }

   @Override
   public String toString() {
      return this.a.toString() + (this.e() ? " (ttl: " + this.b + ")" : "");
   }

   public boolean e() {
      return this.b != Long.MAX_VALUE;
   }

   public static <T> Codec<ayc<T>> a(Codec<T> var0) {
      return RecordCodecBuilder.create(
         var1 -> var1.group(
                  _snowman.fieldOf("value").forGetter(var0x -> var0x.a),
                  Codec.LONG.optionalFieldOf("ttl").forGetter(var0x -> var0x.e() ? Optional.of(var0x.b) : Optional.empty())
               )
               .apply(var1, (var0x, var1x) -> new ayc<>(var0x, var1x.orElse(Long.MAX_VALUE)))
      );
   }
}
