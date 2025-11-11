import java.util.function.Supplier;

public class azc<U extends azb<?>> {
   public static final azc<ayp> a = a("dummy", ayp::new);
   public static final azc<ayu> b = a("nearest_items", ayu::new);
   public static final azc<ayv> c = a("nearest_living_entities", ayv::new);
   public static final azc<ayy> d = a("nearest_players", ayy::new);
   public static final azc<ayt> e = a("nearest_bed", ayt::new);
   public static final azc<ays> f = a("hurt_by", ays::new);
   public static final azc<aze> g = a("villager_hostiles", aze::new);
   public static final azc<azd> h = a("villager_babies", azd::new);
   public static final azc<ayz> i = a("secondary_pois", ayz::new);
   public static final azc<ayq> j = a("golem_detected", ayq::new);
   public static final azc<ayx> k = a("piglin_specific_sensor", ayx::new);
   public static final azc<ayw> l = a("piglin_brute_specific_sensor", ayw::new);
   public static final azc<ayr> m = a("hoglin_specific_sensor", ayr::new);
   public static final azc<ayo> n = a("nearest_adult", ayo::new);
   private final Supplier<U> o;

   private azc(Supplier<U> var1) {
      this.o = _snowman;
   }

   public U a() {
      return this.o.get();
   }

   private static <U extends azb<?>> azc<U> a(String var0, Supplier<U> var1) {
      return gm.a(gm.al, new vk(_snowman), new azc<>(_snowman));
   }
}
