import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class azr {
   private static final Supplier<Set<azr>> y = Suppliers.memoize(() -> gm.ai.g().map(bfm::b).collect(Collectors.toSet()));
   public static final Predicate<azr> a = var0 -> y.get().contains(var0);
   public static final Predicate<azr> b = var0 -> true;
   private static final Set<ceh> z = ImmutableList.of(
         bup.aL, bup.aM, bup.aI, bup.aJ, bup.aG, bup.aE, bup.aK, bup.aA, bup.aF, bup.aC, bup.az, bup.ay, new buo[]{bup.aD, bup.aH, bup.ax, bup.aB}
      )
      .stream()
      .flatMap(var0 -> var0.m().a().stream())
      .filter(var0 -> var0.c(buj.a) == cev.a)
      .collect(ImmutableSet.toImmutableSet());
   private static final Map<ceh, azr> A = Maps.newHashMap();
   public static final azr c = a("unemployed", ImmutableSet.of(), 1, a, 1);
   public static final azr d = a("armorer", a(bup.lU), 1, 1);
   public static final azr e = a("butcher", a(bup.lT), 1, 1);
   public static final azr f = a("cartographer", a(bup.lV), 1, 1);
   public static final azr g = a("cleric", a(bup.ea), 1, 1);
   public static final azr h = a("farmer", a(bup.na), 1, 1);
   public static final azr i = a("fisherman", a(bup.lS), 1, 1);
   public static final azr j = a("fletcher", a(bup.lW), 1, 1);
   public static final azr k = a("leatherworker", a(bup.eb), 1, 1);
   public static final azr l = a("librarian", a(bup.lY), 1, 1);
   public static final azr m = a("mason", a(bup.ma), 1, 1);
   public static final azr n = a("nitwit", ImmutableSet.of(), 1, 1);
   public static final azr o = a("shepherd", a(bup.lR), 1, 1);
   public static final azr p = a("toolsmith", a(bup.lZ), 1, 1);
   public static final azr q = a("weaponsmith", a(bup.lX), 1, 1);
   public static final azr r = a("home", z, 1, 1);
   public static final azr s = a("meeting", a(bup.mb), 32, 6);
   public static final azr t = a("beehive", a(bup.nd), 0, 1);
   public static final azr u = a("bee_nest", a(bup.nc), 0, 1);
   public static final azr v = a("nether_portal", a(bup.cT), 0, 1);
   public static final azr w = a("lodestone", a(bup.no), 0, 1);
   protected static final Set<ceh> x = new ObjectOpenHashSet(A.keySet());
   private final String B;
   private final Set<ceh> C;
   private final int D;
   private final Predicate<azr> E;
   private final int F;

   private static Set<ceh> a(buo var0) {
      return ImmutableSet.copyOf(_snowman.m().a());
   }

   private azr(String var1, Set<ceh> var2, int var3, Predicate<azr> var4, int var5) {
      this.B = _snowman;
      this.C = ImmutableSet.copyOf(_snowman);
      this.D = _snowman;
      this.E = _snowman;
      this.F = _snowman;
   }

   private azr(String var1, Set<ceh> var2, int var3, int var4) {
      this.B = _snowman;
      this.C = ImmutableSet.copyOf(_snowman);
      this.D = _snowman;
      this.E = var1x -> var1x == this;
      this.F = _snowman;
   }

   public int b() {
      return this.D;
   }

   public Predicate<azr> c() {
      return this.E;
   }

   public int d() {
      return this.F;
   }

   @Override
   public String toString() {
      return this.B;
   }

   private static azr a(String var0, Set<ceh> var1, int var2, int var3) {
      return a(gm.a(gm.aj, new vk(_snowman), new azr(_snowman, _snowman, _snowman, _snowman)));
   }

   private static azr a(String var0, Set<ceh> var1, int var2, Predicate<azr> var3, int var4) {
      return a(gm.a(gm.aj, new vk(_snowman), new azr(_snowman, _snowman, _snowman, _snowman, _snowman)));
   }

   private static azr a(azr var0) {
      _snowman.C.forEach(var1 -> {
         azr _snowman = A.put(var1, _snowman);
         if (_snowman != null) {
            throw (IllegalStateException)x.c(new IllegalStateException(String.format("%s is defined in too many tags", var1)));
         }
      });
      return _snowman;
   }

   public static Optional<azr> b(ceh var0) {
      return Optional.ofNullable(A.get(_snowman));
   }
}
