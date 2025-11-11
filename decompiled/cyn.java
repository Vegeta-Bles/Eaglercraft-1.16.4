import com.mojang.serialization.Lifecycle;
import java.util.Set;
import javax.annotation.Nullable;

public interface cyn {
   brk D();

   void a(brk var1);

   boolean F();

   Set<String> G();

   void a(String var1, boolean var2);

   default void a(m var1) {
      _snowman.a("Known server brands", () -> String.join(", ", this.G()));
      _snowman.a("Level was modded", () -> Boolean.toString(this.F()));
      _snowman.a("Level storage version", () -> {
         int _snowman = this.z();
         return String.format("0x%05X - %s", _snowman, this.i(_snowman));
      });
   }

   default String i(int var1) {
      switch (_snowman) {
         case 19132:
            return "McRegion";
         case 19133:
            return "Anvil";
         default:
            return "Unknown?";
      }
   }

   @Nullable
   md E();

   void b(@Nullable md var1);

   cym H();

   bsa I();

   md a(gn var1, @Nullable md var2);

   boolean n();

   int z();

   String g();

   bru m();

   void a(bru var1);

   boolean o();

   aor s();

   void a(aor var1);

   boolean t();

   void d(boolean var1);

   brt q();

   md y();

   md C();

   void a(md var1);

   chw A();

   Lifecycle B();
}
