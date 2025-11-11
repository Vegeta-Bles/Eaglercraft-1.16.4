import java.util.function.Predicate;

public class dbq {
   public static final dbp a = a("inverted", new dbl.a());
   public static final dbp b = a("alternative", new dbe.b());
   public static final dbp c = a("random_chance", new dbt.a());
   public static final dbp d = a("random_chance_with_looting", new dbu.a());
   public static final dbp e = a("entity_properties", new dbr.a());
   public static final dbp f = a("killed_by_player", new dbs.a());
   public static final dbp g = a("entity_scores", new dbj.b());
   public static final dbp h = a("block_state_property", new dbn.b());
   public static final dbp i = a("match_tool", new dbv.a());
   public static final dbp j = a("table_bonus", new dbf.a());
   public static final dbp k = a("survives_explosion", new dbk.a());
   public static final dbp l = a("damage_source_properties", new dbi.a());
   public static final dbp m = a("location_check", new dbm.a());
   public static final dbp n = a("weather_check", new dbx.b());
   public static final dbp o = a("reference", new dbg.a());
   public static final dbp p = a("time_check", new dbw.b());

   private static dbp a(String var0, cze<? extends dbo> var1) {
      return gm.a(gm.aq, new vk(_snowman), new dbp(_snowman));
   }

   public static Object a() {
      return cyt.a(gm.aq, "condition", "condition", dbo::b).a();
   }

   public static <T> Predicate<T> a(Predicate<T>[] var0) {
      switch (_snowman.length) {
         case 0:
            return var0x -> true;
         case 1:
            return _snowman[0];
         case 2:
            return _snowman[0].and(_snowman[1]);
         default:
            return var1 -> {
               for (Predicate<T> _snowman : _snowman) {
                  if (!_snowman.test(var1)) {
                     return false;
                  }
               }

               return true;
            };
      }
   }

   public static <T> Predicate<T> b(Predicate<T>[] var0) {
      switch (_snowman.length) {
         case 0:
            return var0x -> false;
         case 1:
            return _snowman[0];
         case 2:
            return _snowman[0].or(_snowman[1]);
         default:
            return var1 -> {
               for (Predicate<T> _snowman : _snowman) {
                  if (_snowman.test(var1)) {
                     return true;
                  }
               }

               return false;
            };
      }
   }
}
