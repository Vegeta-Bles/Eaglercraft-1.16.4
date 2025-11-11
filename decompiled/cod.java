import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

public class cod {
   private final int a;
   private final int b;
   private final int c;
   private final int d;
   private final cok.a e;

   public cod(int var1, int var2, int var3, int var4, cok.a var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public <T> Dynamic<T> a(DynamicOps<T> var1) {
      Builder<T, T> _snowman = ImmutableMap.builder();
      _snowman.put(_snowman.createString("source_x"), _snowman.createInt(this.a))
         .put(_snowman.createString("source_ground_y"), _snowman.createInt(this.b))
         .put(_snowman.createString("source_z"), _snowman.createInt(this.c))
         .put(_snowman.createString("delta_y"), _snowman.createInt(this.d))
         .put(_snowman.createString("dest_proj"), _snowman.createString(this.e.b()));
      return new Dynamic(_snowman, _snowman.createMap(_snowman.build()));
   }

   public static <T> cod a(Dynamic<T> var0) {
      return new cod(
         _snowman.get("source_x").asInt(0),
         _snowman.get("source_ground_y").asInt(0),
         _snowman.get("source_z").asInt(0),
         _snowman.get("delta_y").asInt(0),
         cok.a.a(_snowman.get("dest_proj").asString(""))
      );
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         cod _snowman = (cod)_snowman;
         if (this.a != _snowman.a) {
            return false;
         } else if (this.c != _snowman.c) {
            return false;
         } else {
            return this.d != _snowman.d ? false : this.e == _snowman.e;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a;
      _snowman = 31 * _snowman + this.b;
      _snowman = 31 * _snowman + this.c;
      _snowman = 31 * _snowman + this.d;
      return 31 * _snowman + this.e.hashCode();
   }

   @Override
   public String toString() {
      return "JigsawJunction{sourceX="
         + this.a
         + ", sourceGroundY="
         + this.b
         + ", sourceZ="
         + this.c
         + ", deltaY="
         + this.d
         + ", destProjection="
         + this.e
         + '}';
   }
}
