import java.util.stream.Collectors;

public class aeh {
   private static volatile aen a = aen.a(
      aem.a(aed.b().stream().collect(Collectors.toMap(ael.e::a, var0 -> (ael<buo>)var0))),
      aem.a(aeg.b().stream().collect(Collectors.toMap(ael.e::a, var0 -> (ael<blx>)var0))),
      aem.a(aef.b().stream().collect(Collectors.toMap(ael.e::a, var0 -> (ael<cuw>)var0))),
      aem.a(aee.b().stream().collect(Collectors.toMap(ael.e::a, var0 -> (ael<aqe<?>>)var0)))
   );

   public static aen a() {
      return a;
   }

   public static void a(aen var0) {
      a = _snowman;
   }
}
