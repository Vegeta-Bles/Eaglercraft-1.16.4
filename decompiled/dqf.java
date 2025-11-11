import java.util.Random;

public class dqf {
   private static final vk a = new vk("minecraft", "alt");
   private static final ob b = ob.a.a(a);
   private static final dqf c = new dqf();
   private final Random d = new Random();
   private final String[] e = new String[]{
      "the",
      "elder",
      "scrolls",
      "klaatu",
      "berata",
      "niktu",
      "xyzzy",
      "bless",
      "curse",
      "light",
      "darkness",
      "fire",
      "air",
      "earth",
      "water",
      "hot",
      "dry",
      "cold",
      "wet",
      "ignite",
      "snuff",
      "embiggen",
      "twist",
      "shorten",
      "stretch",
      "fiddle",
      "destroy",
      "imbue",
      "galvanize",
      "enchant",
      "free",
      "limited",
      "range",
      "of",
      "towards",
      "inside",
      "sphere",
      "cube",
      "self",
      "other",
      "ball",
      "mental",
      "physical",
      "grow",
      "shrink",
      "demon",
      "elemental",
      "spirit",
      "animal",
      "creature",
      "beast",
      "humanoid",
      "undead",
      "fresh",
      "stale",
      "phnglui",
      "mglwnafh",
      "cthulhu",
      "rlyeh",
      "wgahnagl",
      "fhtagn",
      "baguette"
   };

   private dqf() {
   }

   public static dqf a() {
      return c;
   }

   public nu a(dku var1, int var2) {
      StringBuilder _snowman = new StringBuilder();
      int _snowmanx = this.d.nextInt(2) + 3;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         if (_snowmanxx != 0) {
            _snowman.append(" ");
         }

         _snowman.append(x.a(this.e, this.d));
      }

      return _snowman.b().a(new oe(_snowman.toString()).c(b), _snowman, ob.a);
   }

   public void a(long var1) {
      this.d.setSeed(_snowman);
   }
}
