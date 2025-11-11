import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class dbb {
   private static final BiMap<vk, dba> m = HashBiMap.create();
   public static final dba a = a("empty", var0 -> {
   });
   public static final dba b = a("chest", var0 -> var0.a(dbc.f).b(dbc.a));
   public static final dba c = a("command", var0 -> var0.a(dbc.f).b(dbc.a));
   public static final dba d = a("selector", var0 -> var0.a(dbc.f).a(dbc.a));
   public static final dba e = a("fishing", var0 -> var0.a(dbc.f).a(dbc.i).b(dbc.a));
   public static final dba f = a("entity", var0 -> var0.a(dbc.a).a(dbc.f).a(dbc.c).b(dbc.d).b(dbc.e).b(dbc.b));
   public static final dba g = a("gift", var0 -> var0.a(dbc.f).a(dbc.a));
   public static final dba h = a("barter", var0 -> var0.a(dbc.a));
   public static final dba i = a("advancement_reward", var0 -> var0.a(dbc.a).a(dbc.f));
   public static final dba j = a("advancement_entity", var0 -> var0.a(dbc.a).a(dbc.f));
   public static final dba k = a("generic", var0 -> var0.a(dbc.a).a(dbc.b).a(dbc.c).a(dbc.d).a(dbc.e).a(dbc.f).a(dbc.g).a(dbc.h).a(dbc.i).a(dbc.j));
   public static final dba l = a("block", var0 -> var0.a(dbc.g).a(dbc.f).a(dbc.i).b(dbc.a).b(dbc.h).b(dbc.j));

   private static dba a(String var0, Consumer<dba.a> var1) {
      dba.a _snowman = new dba.a();
      _snowman.accept(_snowman);
      dba _snowmanx = _snowman.a();
      vk _snowmanxx = new vk(_snowman);
      dba _snowmanxxx = (dba)m.put(_snowmanxx, _snowmanx);
      if (_snowmanxxx != null) {
         throw new IllegalStateException("Loot table parameter set " + _snowmanxx + " is already registered");
      } else {
         return _snowmanx;
      }
   }

   @Nullable
   public static dba a(vk var0) {
      return (dba)m.get(_snowman);
   }

   @Nullable
   public static vk a(dba var0) {
      return (vk)m.inverse().get(_snowman);
   }
}
