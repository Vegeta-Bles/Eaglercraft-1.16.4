import java.util.function.BiFunction;

public class dal {
   public static final BiFunction<bmb, cyv, bmb> a = (var0, var1) -> var0;
   public static final dak b = a("set_count", new daq.a());
   public static final dak c = a("enchant_with_levels", new dad.b());
   public static final dak d = a("enchant_randomly", new dac.b());
   public static final dak e = a("set_nbt", new dau.a());
   public static final dak f = a("furnace_smelt", new daw.a());
   public static final dak g = a("looting_enchant", new dam.b());
   public static final dak h = a("set_damage", new dar.a());
   public static final dak i = a("set_attributes", new dan.d());
   public static final dak j = a("set_name", new dat.a());
   public static final dak k = a("exploration_map", new dae.b());
   public static final dak l = a("set_stew_effect", new dav.b());
   public static final dak m = a("copy_name", new daa.b());
   public static final dak n = a("set_contents", new dao.b());
   public static final dak o = a("limit_count", new dah.a());
   public static final dak p = a("apply_bonus", new czx.e());
   public static final dak q = a("set_loot_table", new dap.a());
   public static final dak r = a("explosion_decay", new czy.a());
   public static final dak s = a("set_lore", new das.b());
   public static final dak t = a("fill_player_head", new daf.a());
   public static final dak u = a("copy_nbt", new dab.e());
   public static final dak v = a("copy_state", new czz.b());

   private static dak a(String var0, cze<? extends daj> var1) {
      return gm.a(gm.ap, new vk(_snowman), new dak(_snowman));
   }

   public static Object a() {
      return cyt.a(gm.ap, "function", "function", daj::b).a();
   }

   public static BiFunction<bmb, cyv, bmb> a(BiFunction<bmb, cyv, bmb>[] var0) {
      switch (_snowman.length) {
         case 0:
            return a;
         case 1:
            return _snowman[0];
         case 2:
            BiFunction<bmb, cyv, bmb> _snowman = _snowman[0];
            BiFunction<bmb, cyv, bmb> _snowmanx = _snowman[1];
            return (var2x, var3) -> _snowman.apply(_snowman.apply(var2x, var3), var3);
         default:
            return (var1x, var2x) -> {
               for (BiFunction<bmb, cyv, bmb> _snowmanxx : _snowman) {
                  var1x = _snowmanxx.apply(var1x, var2x);
               }

               return var1x;
            };
      }
   }
}
