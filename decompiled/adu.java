import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;

public final class adu {
   private static final Map<bjk, Pair<String, String>> a = ImmutableMap.of(
      bjk.a,
      Pair.of("isGuiOpen", "isFilteringCraftable"),
      bjk.b,
      Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"),
      bjk.c,
      Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"),
      bjk.d,
      Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable")
   );
   private final Map<bjk, adu.a> b;

   private adu(Map<bjk, adu.a> var1) {
      this.b = _snowman;
   }

   public adu() {
      this(x.a(Maps.newEnumMap(bjk.class), var0 -> {
         for (bjk _snowman : bjk.values()) {
            var0.put(_snowman, new adu.a(false, false));
         }
      }));
   }

   public boolean a(bjk var1) {
      return this.b.get(_snowman).a;
   }

   public void a(bjk var1, boolean var2) {
      this.b.get(_snowman).a = _snowman;
   }

   public boolean b(bjk var1) {
      return this.b.get(_snowman).b;
   }

   public void b(bjk var1, boolean var2) {
      this.b.get(_snowman).b = _snowman;
   }

   public static adu a(nf var0) {
      Map<bjk, adu.a> _snowman = Maps.newEnumMap(bjk.class);

      for (bjk _snowmanx : bjk.values()) {
         boolean _snowmanxx = _snowman.readBoolean();
         boolean _snowmanxxx = _snowman.readBoolean();
         _snowman.put(_snowmanx, new adu.a(_snowmanxx, _snowmanxxx));
      }

      return new adu(_snowman);
   }

   public void b(nf var1) {
      for (bjk _snowman : bjk.values()) {
         adu.a _snowmanx = this.b.get(_snowman);
         if (_snowmanx == null) {
            _snowman.writeBoolean(false);
            _snowman.writeBoolean(false);
         } else {
            _snowman.writeBoolean(_snowmanx.a);
            _snowman.writeBoolean(_snowmanx.b);
         }
      }
   }

   public static adu a(md var0) {
      Map<bjk, adu.a> _snowman = Maps.newEnumMap(bjk.class);
      a.forEach((var2, var3) -> {
         boolean _snowmanx = _snowman.q((String)var3.getFirst());
         boolean _snowmanx = _snowman.q((String)var3.getSecond());
         _snowman.put(var2, new adu.a(_snowmanx, _snowmanx));
      });
      return new adu(_snowman);
   }

   public void b(md var1) {
      a.forEach((var2, var3) -> {
         adu.a _snowman = this.b.get(var2);
         _snowman.a((String)var3.getFirst(), _snowman.a);
         _snowman.a((String)var3.getSecond(), _snowman.b);
      });
   }

   public adu a() {
      Map<bjk, adu.a> _snowman = Maps.newEnumMap(bjk.class);

      for (bjk _snowmanx : bjk.values()) {
         adu.a _snowmanxx = this.b.get(_snowmanx);
         _snowman.put(_snowmanx, _snowmanxx.a());
      }

      return new adu(_snowman);
   }

   public void a(adu var1) {
      this.b.clear();

      for (bjk _snowman : bjk.values()) {
         adu.a _snowmanx = _snowman.b.get(_snowman);
         this.b.put(_snowman, _snowmanx.a());
      }
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman || _snowman instanceof adu && this.b.equals(((adu)_snowman).b);
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   static final class a {
      private boolean a;
      private boolean b;

      public a(boolean var1, boolean var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public adu.a a() {
         return new adu.a(this.a, this.b);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof adu.a)) {
            return false;
         } else {
            adu.a _snowman = (adu.a)_snowman;
            return this.a == _snowman.a && this.b == _snowman.b;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.a ? 1 : 0;
         return 31 * _snowman + (this.b ? 1 : 0);
      }

      @Override
      public String toString() {
         return "[open=" + this.a + ", filtering=" + this.b + ']';
      }
   }
}
