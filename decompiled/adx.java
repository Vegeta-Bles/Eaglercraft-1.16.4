import java.util.Objects;
import javax.annotation.Nullable;

public class adx<T> extends ddq {
   private final ady o;
   private final T p;
   private final adz<T> q;

   protected adx(adz<T> var1, T var2, ady var3) {
      super(a(_snowman, _snowman));
      this.q = _snowman;
      this.o = _snowman;
      this.p = _snowman;
   }

   public static <T> String a(adz<T> var0, T var1) {
      return a(gm.ag.b(_snowman)) + ":" + a(_snowman.a().b(_snowman));
   }

   private static <T> String a(@Nullable vk var0) {
      return _snowman.toString().replace(':', '.');
   }

   public adz<T> a() {
      return this.q;
   }

   public T b() {
      return this.p;
   }

   public String a(int var1) {
      return this.o.format(_snowman);
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman || _snowman instanceof adx && Objects.equals(this.c(), ((adx)_snowman).c());
   }

   @Override
   public int hashCode() {
      return this.c().hashCode();
   }

   @Override
   public String toString() {
      return "Stat{name=" + this.c() + ", formatter=" + this.o + '}';
   }
}
