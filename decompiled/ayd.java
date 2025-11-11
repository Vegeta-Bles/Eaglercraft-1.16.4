import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ayd<U> {
   public static final ayd<Void> a = a("dummy");
   public static final ayd<gf> b = a("home", gf.a);
   public static final ayd<gf> c = a("job_site", gf.a);
   public static final ayd<gf> d = a("potential_job_site", gf.a);
   public static final ayd<gf> e = a("meeting_point", gf.a);
   public static final ayd<List<gf>> f = a("secondary_job_site");
   public static final ayd<List<aqm>> g = a("mobs");
   public static final ayd<List<aqm>> h = a("visible_mobs");
   public static final ayd<List<aqm>> i = a("visible_villager_babies");
   public static final ayd<List<bfw>> j = a("nearest_players");
   public static final ayd<bfw> k = a("nearest_visible_player");
   public static final ayd<bfw> l = a("nearest_visible_targetable_player");
   public static final ayd<ayf> m = a("walk_target");
   public static final ayd<atb> n = a("look_target");
   public static final ayd<aqm> o = a("attack_target");
   public static final ayd<Boolean> p = a("attack_cooling_down");
   public static final ayd<aqm> q = a("interaction_target");
   public static final ayd<apy> r = a("breed_target");
   public static final ayd<aqa> s = a("ride_target");
   public static final ayd<cxd> t = a("path");
   public static final ayd<List<gf>> u = a("interactable_doors");
   public static final ayd<Set<gf>> v = a("doors_to_close");
   public static final ayd<fx> w = a("nearest_bed");
   public static final ayd<apk> x = a("hurt_by");
   public static final ayd<aqm> y = a("hurt_by_entity");
   public static final ayd<aqm> z = a("avoid_target");
   public static final ayd<aqm> A = a("nearest_hostile");
   public static final ayd<gf> B = a("hiding_place");
   public static final ayd<Long> C = a("heard_bell_time");
   public static final ayd<Long> D = a("cant_reach_walk_target_since");
   public static final ayd<Boolean> E = a("golem_detected_recently", Codec.BOOL);
   public static final ayd<Long> F = a("last_slept", Codec.LONG);
   public static final ayd<Long> G = a("last_woken", Codec.LONG);
   public static final ayd<Long> H = a("last_worked_at_poi", Codec.LONG);
   public static final ayd<apy> I = a("nearest_visible_adult");
   public static final ayd<bcv> J = a("nearest_visible_wanted_item");
   public static final ayd<aqn> K = a("nearest_visible_nemesis");
   public static final ayd<UUID> L = a("angry_at", gq.a);
   public static final ayd<Boolean> M = a("universal_anger", Codec.BOOL);
   public static final ayd<Boolean> N = a("admiring_item", Codec.BOOL);
   public static final ayd<Integer> O = a("time_trying_to_reach_admire_item");
   public static final ayd<Boolean> P = a("disable_walk_to_admire_item");
   public static final ayd<Boolean> Q = a("admiring_disabled", Codec.BOOL);
   public static final ayd<Boolean> R = a("hunted_recently", Codec.BOOL);
   public static final ayd<fx> S = a("celebrate_location");
   public static final ayd<Boolean> T = a("dancing");
   public static final ayd<bem> U = a("nearest_visible_huntable_hoglin");
   public static final ayd<bem> V = a("nearest_visible_baby_hoglin");
   public static final ayd<bfw> W = a("nearest_targetable_player_not_wearing_gold");
   public static final ayd<List<ber>> X = a("nearby_adult_piglins");
   public static final ayd<List<ber>> Y = a("nearest_visible_adult_piglins");
   public static final ayd<List<bem>> Z = a("nearest_visible_adult_hoglins");
   public static final ayd<ber> aa = a("nearest_visible_adult_piglin");
   public static final ayd<aqm> ab = a("nearest_visible_zombified");
   public static final ayd<Integer> ac = a("visible_adult_piglin_count");
   public static final ayd<Integer> ad = a("visible_adult_hoglin_count");
   public static final ayd<bfw> ae = a("nearest_player_holding_wanted_item");
   public static final ayd<Boolean> af = a("ate_recently");
   public static final ayd<fx> ag = a("nearest_repellent");
   public static final ayd<Boolean> ah = a("pacified");
   private final Optional<Codec<ayc<U>>> ai;

   private ayd(Optional<Codec<U>> var1) {
      this.ai = _snowman.map(ayc::a);
   }

   @Override
   public String toString() {
      return gm.ak.b(this).toString();
   }

   public Optional<Codec<ayc<U>>> a() {
      return this.ai;
   }

   private static <U> ayd<U> a(String var0, Codec<U> var1) {
      return gm.a(gm.ak, new vk(_snowman), new ayd<>(Optional.of(_snowman)));
   }

   private static <U> ayd<U> a(String var0) {
      return gm.a(gm.ak, new vk(_snowman), new ayd<>(Optional.empty()));
   }
}
