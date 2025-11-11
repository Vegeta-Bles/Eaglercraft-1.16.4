public class bje<T extends bic> {
   public static final bje<bij> a = a("generic_9x1", bij::a);
   public static final bje<bij> b = a("generic_9x2", bij::b);
   public static final bje<bij> c = a("generic_9x3", bij::c);
   public static final bje<bij> d = a("generic_9x4", bij::d);
   public static final bje<bij> e = a("generic_9x5", bij::e);
   public static final bje<bij> f = a("generic_9x6", bij::f);
   public static final bje<bir> g = a("generic_3x3", bir::new);
   public static final bje<bie> h = a("anvil", bie::new);
   public static final bje<bif> i = a("beacon", bif::new);
   public static final bje<big> j = a("blast_furnace", big::new);
   public static final bje<bih> k = a("brewing_stand", bih::new);
   public static final bje<bip> l = a("crafting", bip::new);
   public static final bje<bis> m = a("enchantment", bis::new);
   public static final bje<biu> n = a("furnace", biu::new);
   public static final bje<biw> o = a("grindstone", biw::new);
   public static final bje<bix> p = a("hopper", bix::new);
   public static final bje<bjb> q = a("lectern", (var0, var1) -> new bjb(var0));
   public static final bje<bjc> r = a("loom", bjc::new);
   public static final bje<bjg> s = a("merchant", bjg::new);
   public static final bje<bjo> t = a("shulker_box", bjo::new);
   public static final bje<bjs> u = a("smithing", bjs::new);
   public static final bje<bjt> v = a("smoker", bjt::new);
   public static final bje<bii> w = a("cartography_table", bii::new);
   public static final bje<bjv> x = a("stonecutter", bjv::new);
   private final bje.a<T> y;

   private static <T extends bic> bje<T> a(String var0, bje.a<T> var1) {
      return gm.a(gm.ac, _snowman, new bje<>(_snowman));
   }

   private bje(bje.a<T> var1) {
      this.y = _snowman;
   }

   public T a(int var1, bfv var2) {
      return this.y.create(_snowman, _snowman);
   }

   interface a<T extends bic> {
      T create(int var1, bfv var2);
   }
}
