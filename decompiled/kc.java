import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

public class kc {
   public static void a() {
   }

   static {
      kk.a(
         new cok(
            new vk("bastion/mobs/piglin"),
            new vk("empty"),
            ImmutableList.of(
               Pair.of(coi.b("bastion/mobs/melee_piglin"), 1),
               Pair.of(coi.b("bastion/mobs/sword_piglin"), 4),
               Pair.of(coi.b("bastion/mobs/crossbow_piglin"), 4),
               Pair.of(coi.b("bastion/mobs/empty"), 1)
            ),
            cok.a.b
         )
      );
      kk.a(
         new cok(
            new vk("bastion/mobs/hoglin"),
            new vk("empty"),
            ImmutableList.of(Pair.of(coi.b("bastion/mobs/hoglin"), 2), Pair.of(coi.b("bastion/mobs/empty"), 1)),
            cok.a.b
         )
      );
      kk.a(
         new cok(
            new vk("bastion/blocks/gold"),
            new vk("empty"),
            ImmutableList.of(Pair.of(coi.b("bastion/blocks/air"), 3), Pair.of(coi.b("bastion/blocks/gold"), 1)),
            cok.a.b
         )
      );
      kk.a(
         new cok(
            new vk("bastion/mobs/piglin_melee"),
            new vk("empty"),
            ImmutableList.of(
               Pair.of(coi.b("bastion/mobs/melee_piglin_always"), 1),
               Pair.of(coi.b("bastion/mobs/melee_piglin"), 5),
               Pair.of(coi.b("bastion/mobs/sword_piglin"), 1)
            ),
            cok.a.b
         )
      );
   }
}
