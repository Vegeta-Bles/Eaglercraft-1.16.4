public class czo {
   public static final czr a = a("empty", new czl.a());
   public static final czr b = a("item", new czn.a());
   public static final czr c = a("loot_table", new czt.a());
   public static final czr d = a("dynamic", new czk.a());
   public static final czr e = a("tag", new czv.a());
   public static final czr f = a("alternatives", czj.a(czh::new));
   public static final czr g = a("sequence", czj.a(czu::new));
   public static final czr h = a("group", czj.a(czm::new));

   private static czr a(String var0, cze<? extends czq> var1) {
      return gm.a(gm.ao, new vk(_snowman), new czr(_snowman));
   }

   public static Object a() {
      return cyt.<czq, czr>a(gm.ao, "entry", "type", czq::a).a();
   }
}
