import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class fk {
   private static final Logger a = LogManager.getLogger();
   private static final Map<Class<?>, fk.a<?>> b = Maps.newHashMap();
   private static final Map<vk, fk.a<?>> c = Maps.newHashMap();

   public static <T extends ArgumentType<?>> void a(String var0, Class<T> var1, fj<T> var2) {
      vk _snowman = new vk(_snowman);
      if (b.containsKey(_snowman)) {
         throw new IllegalArgumentException("Class " + _snowman.getName() + " already has a serializer!");
      } else if (c.containsKey(_snowman)) {
         throw new IllegalArgumentException("'" + _snowman + "' is already a registered serializer!");
      } else {
         fk.a<T> _snowmanx = new fk.a<>(_snowman, _snowman, _snowman);
         b.put(_snowman, _snowmanx);
         c.put(_snowman, _snowmanx);
      }
   }

   public static void a() {
      fn.a();
      a("entity", dk.class, new dk.a());
      a("game_profile", dm.class, new fl(dm::a));
      a("block_pos", ek.class, new fl(ek::a));
      a("column_pos", el.class, new fl(el::a));
      a("vec3", er.class, new fl(er::a));
      a("vec2", eq.class, new fl(eq::a));
      a("block_state", eh.class, new fl(eh::a));
      a("block_predicate", eg.class, new fl(eg::a));
      a("item_stack", ew.class, new fl(ew::a));
      a("item_predicate", ez.class, new fl(ez::a));
      a("color", df.class, new fl(df::a));
      a("component", dg.class, new fl(dg::a));
      a("message", dp.class, new fl(dp::a));
      a("nbt_compound_tag", dh.class, new fl(dh::a));
      a("nbt_tag", ds.class, new fl(ds::a));
      a("nbt_path", dr.class, new fl(dr::a));
      a("objective", dt.class, new fl(dt::a));
      a("objective_criteria", du.class, new fl(du::a));
      a("operation", dv.class, new fl(dv::a));
      a("particle", dw.class, new fl(dw::a));
      a("angle", de.class, new fl(de::a));
      a("rotation", eo.class, new fl(eo::a));
      a("scoreboard_slot", ea.class, new fl(ea::a));
      a("score_holder", dz.class, new dz.c());
      a("swizzle", ep.class, new fl(ep::a));
      a("team", ec.class, new fl(ec::a));
      a("item_slot", eb.class, new fl(eb::a));
      a("resource_location", dy.class, new fl(dy::a));
      a("mob_effect", dq.class, new fl(dq::a));
      a("function", ev.class, new fl(ev::a));
      a("entity_anchor", dj.class, new fl(dj::a));
      a("int_range", dx.b.class, new fl(dx::a));
      a("float_range", dx.a.class, new fl(dx::b));
      a("item_enchantment", dn.class, new fl(dn::a));
      a("entity_summon", dl.class, new fl(dl::a));
      a("dimension", di.class, new fl(di::a));
      a("time", ed.class, new fl(ed::a));
      a("uuid", ee.class, new fl(ee::a));
      if (w.d) {
         a("test_argument", lv.class, new fl(lv::a));
         a("test_class", ls.class, new fl(ls::a));
      }
   }

   @Nullable
   private static fk.a<?> a(vk var0) {
      return c.get(_snowman);
   }

   @Nullable
   private static fk.a<?> b(ArgumentType<?> var0) {
      return b.get(_snowman.getClass());
   }

   public static <T extends ArgumentType<?>> void a(nf var0, T var1) {
      fk.a<T> _snowman = (fk.a<T>)b(_snowman);
      if (_snowman == null) {
         a.error("Could not serialize {} ({}) - will not be sent to client!", _snowman, _snowman.getClass());
         _snowman.a(new vk(""));
      } else {
         _snowman.a(_snowman.c);
         _snowman.b.a(_snowman, _snowman);
      }
   }

   @Nullable
   public static ArgumentType<?> a(nf var0) {
      vk _snowman = _snowman.p();
      fk.a<?> _snowmanx = a(_snowman);
      if (_snowmanx == null) {
         a.error("Could not deserialize {}", _snowman);
         return null;
      } else {
         return _snowmanx.b.b(_snowman);
      }
   }

   private static <T extends ArgumentType<?>> void a(JsonObject var0, T var1) {
      fk.a<T> _snowman = (fk.a<T>)b(_snowman);
      if (_snowman == null) {
         a.error("Could not serialize argument {} ({})!", _snowman, _snowman.getClass());
         _snowman.addProperty("type", "unknown");
      } else {
         _snowman.addProperty("type", "argument");
         _snowman.addProperty("parser", _snowman.c.toString());
         JsonObject _snowmanx = new JsonObject();
         _snowman.b.a(_snowman, _snowmanx);
         if (_snowmanx.size() > 0) {
            _snowman.add("properties", _snowmanx);
         }
      }
   }

   public static <S> JsonObject a(CommandDispatcher<S> var0, CommandNode<S> var1) {
      JsonObject _snowman = new JsonObject();
      if (_snowman instanceof RootCommandNode) {
         _snowman.addProperty("type", "root");
      } else if (_snowman instanceof LiteralCommandNode) {
         _snowman.addProperty("type", "literal");
      } else if (_snowman instanceof ArgumentCommandNode) {
         a(_snowman, ((ArgumentCommandNode)_snowman).getType());
      } else {
         a.error("Could not serialize node {} ({})!", _snowman, _snowman.getClass());
         _snowman.addProperty("type", "unknown");
      }

      JsonObject _snowmanx = new JsonObject();

      for (CommandNode<S> _snowmanxx : _snowman.getChildren()) {
         _snowmanx.add(_snowmanxx.getName(), a(_snowman, _snowmanxx));
      }

      if (_snowmanx.size() > 0) {
         _snowman.add("children", _snowmanx);
      }

      if (_snowman.getCommand() != null) {
         _snowman.addProperty("executable", true);
      }

      if (_snowman.getRedirect() != null) {
         Collection<String> _snowmanxx = _snowman.getPath(_snowman.getRedirect());
         if (!_snowmanxx.isEmpty()) {
            JsonArray _snowmanxxx = new JsonArray();

            for (String _snowmanxxxx : _snowmanxx) {
               _snowmanxxx.add(_snowmanxxxx);
            }

            _snowman.add("redirect", _snowmanxxx);
         }
      }

      return _snowman;
   }

   public static boolean a(ArgumentType<?> var0) {
      return b(_snowman) != null;
   }

   public static <T> Set<ArgumentType<?>> a(CommandNode<T> var0) {
      Set<CommandNode<T>> _snowman = Sets.newIdentityHashSet();
      Set<ArgumentType<?>> _snowmanx = Sets.newHashSet();
      a(_snowman, _snowmanx, _snowman);
      return _snowmanx;
   }

   private static <T> void a(CommandNode<T> var0, Set<ArgumentType<?>> var1, Set<CommandNode<T>> var2) {
      if (_snowman.add(_snowman)) {
         if (_snowman instanceof ArgumentCommandNode) {
            _snowman.add(((ArgumentCommandNode)_snowman).getType());
         }

         _snowman.getChildren().forEach(var2x -> a(var2x, _snowman, _snowman));
         CommandNode<T> _snowman = _snowman.getRedirect();
         if (_snowman != null) {
            a(_snowman, _snowman, _snowman);
         }
      }
   }

   static class a<T extends ArgumentType<?>> {
      public final Class<T> a;
      public final fj<T> b;
      public final vk c;

      private a(Class<T> var1, fj<T> var2, vk var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
