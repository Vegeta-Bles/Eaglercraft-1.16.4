public class apw {
   public static final aps a = a(1, "speed", new aps(apt.a, 8171462).a(arl.d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2F, arj.a.c));
   public static final aps b = a(2, "slowness", new aps(apt.b, 5926017).a(arl.d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, arj.a.c));
   public static final aps c = a(3, "haste", new aps(apt.a, 14270531).a(arl.h, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.1F, arj.a.c));
   public static final aps d = a(4, "mining_fatigue", new aps(apt.b, 4866583).a(arl.h, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1F, arj.a.c));
   public static final aps e = a(5, "strength", new app(apt.a, 9643043, 3.0).a(arl.f, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0, arj.a.a));
   public static final aps f = a(6, "instant_health", new apr(apt.a, 16262179));
   public static final aps g = a(7, "instant_damage", new apr(apt.b, 4393481));
   public static final aps h = a(8, "jump_boost", new aps(apt.a, 2293580));
   public static final aps i = a(9, "nausea", new aps(apt.b, 5578058));
   public static final aps j = a(10, "regeneration", new aps(apt.a, 13458603));
   public static final aps k = a(11, "resistance", new aps(apt.a, 10044730));
   public static final aps l = a(12, "fire_resistance", new aps(apt.a, 14981690));
   public static final aps m = a(13, "water_breathing", new aps(apt.a, 3035801));
   public static final aps n = a(14, "invisibility", new aps(apt.a, 8356754));
   public static final aps o = a(15, "blindness", new aps(apt.b, 2039587));
   public static final aps p = a(16, "night_vision", new aps(apt.a, 2039713));
   public static final aps q = a(17, "hunger", new aps(apt.b, 5797459));
   public static final aps r = a(18, "weakness", new app(apt.b, 4738376, -4.0).a(arl.f, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0, arj.a.a));
   public static final aps s = a(19, "poison", new aps(apt.b, 5149489));
   public static final aps t = a(20, "wither", new aps(apt.b, 3484199));
   public static final aps u = a(21, "health_boost", new apq(apt.a, 16284963).a(arl.a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, arj.a.a));
   public static final aps v = a(22, "absorption", new apo(apt.a, 2445989));
   public static final aps w = a(23, "saturation", new apr(apt.a, 16262179));
   public static final aps x = a(24, "glowing", new aps(apt.c, 9740385));
   public static final aps y = a(25, "levitation", new aps(apt.b, 13565951));
   public static final aps z = a(26, "luck", new aps(apt.a, 3381504).a(arl.k, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0, arj.a.a));
   public static final aps A = a(27, "unluck", new aps(apt.b, 12624973).a(arl.k, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0, arj.a.a));
   public static final aps B = a(28, "slow_falling", new aps(apt.a, 16773073));
   public static final aps C = a(29, "conduit_power", new aps(apt.a, 1950417));
   public static final aps D = a(30, "dolphins_grace", new aps(apt.a, 8954814));
   public static final aps E = a(31, "bad_omen", new aps(apt.c, 745784) {
      @Override
      public boolean a(int var1, int var2) {
         return true;
      }

      @Override
      public void a(aqm var1, int var2) {
         if (_snowman instanceof aah && !_snowman.a_()) {
            aah _snowman = (aah)_snowman;
            aag _snowmanx = _snowman.u();
            if (_snowmanx.ad() == aor.a) {
               return;
            }

            if (_snowmanx.a_(_snowman.cB())) {
               _snowmanx.z().a(_snowman);
            }
         }
      }
   });
   public static final aps F = a(32, "hero_of_the_village", new aps(apt.a, 4521796));

   private static aps a(int var0, String var1, aps var2) {
      return gm.a(gm.P, _snowman, _snowman, _snowman);
   }
}
