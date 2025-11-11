import java.util.function.Predicate;

public abstract class bmo extends blx {
   public static final Predicate<bmb> a = var0 -> var0.b().a(aeg.Y);
   public static final Predicate<bmb> b = a.or(var0 -> var0.b() == bmd.po);

   public bmo(blx.a var1) {
      super(_snowman);
   }

   public Predicate<bmb> e() {
      return this.b();
   }

   public abstract Predicate<bmb> b();

   public static bmb a(aqm var0, Predicate<bmb> var1) {
      if (_snowman.test(_snowman.b(aot.b))) {
         return _snowman.b(aot.b);
      } else {
         return _snowman.test(_snowman.b(aot.a)) ? _snowman.b(aot.a) : bmb.b;
      }
   }

   @Override
   public int c() {
      return 1;
   }

   public abstract int d();
}
