import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum ge implements afs {
   a("down_east", gc.a, gc.f),
   b("down_north", gc.a, gc.c),
   c("down_south", gc.a, gc.d),
   d("down_west", gc.a, gc.e),
   e("up_east", gc.b, gc.f),
   f("up_north", gc.b, gc.c),
   g("up_south", gc.b, gc.d),
   h("up_west", gc.b, gc.e),
   i("west_up", gc.e, gc.b),
   j("east_up", gc.f, gc.b),
   k("north_up", gc.c, gc.b),
   l("south_up", gc.d, gc.b);

   private static final Int2ObjectMap<ge> m = new Int2ObjectOpenHashMap(values().length);
   private final String n;
   private final gc o;
   private final gc p;

   private static int b(gc var0, gc var1) {
      return _snowman.ordinal() << 3 | _snowman.ordinal();
   }

   private ge(String var3, gc var4, gc var5) {
      this.n = _snowman;
      this.p = _snowman;
      this.o = _snowman;
   }

   @Override
   public String a() {
      return this.n;
   }

   public static ge a(gc var0, gc var1) {
      int _snowman = b(_snowman, _snowman);
      return (ge)m.get(_snowman);
   }

   public gc b() {
      return this.p;
   }

   public gc c() {
      return this.o;
   }

   static {
      for (ge _snowman : values()) {
         m.put(b(_snowman.o, _snowman.p), _snowman);
      }
   }
}
