import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;

public class hr implements Consumer<Consumer<y>> {
   private static final List<vj<bsv>> a = ImmutableList.of(btb.i, btb.ax, btb.az, btb.ay, btb.aA);
   private static final bg.b b = bg.b.a(
      dbr.a(cyv.c.a, bg.a.a().a(bd.a.a().a(bq.a.a().a(bmd.lo).b()).b())).a().build(),
      dbr.a(cyv.c.a, bg.a.a().a(bd.a.a().b(bq.a.a().a(bmd.lp).b()).b())).a().build(),
      dbr.a(cyv.c.a, bg.a.a().a(bd.a.a().c(bq.a.a().a(bmd.lq).b()).b())).a().build(),
      dbr.a(cyv.c.a, bg.a.a().a(bd.a.a().d(bq.a.a().a(bmd.lr).b()).b())).a().build()
   );

   public hr() {
   }

   public void a(Consumer<y> var1) {
      y _snowman = y.a.a()
         .a(
            bup.iL,
            new of("advancements.nether.root.title"),
            new of("advancements.nether.root.description"),
            new vk("textures/gui/advancements/backgrounds/nether.png"),
            ai.a,
            false,
            false,
            false
         )
         .a("entered_nether", aq.a.a(brx.h))
         .a(_snowman, "nether/root");
      y _snowmanx = y.a.a()
         .a(_snowman)
         .a(
            bmd.oS,
            new of("advancements.nether.return_to_sender.title"),
            new of("advancements.nether.return_to_sender.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(50))
         .a("killed_ghast", bt.a.a(bg.a.a().a(aqe.D), aw.a.a().a(true).a(bg.a.a().a(aqe.N))))
         .a(_snowman, "nether/return_to_sender");
      y _snowmanxx = y.a.a()
         .a(_snowman)
         .a(bup.dV, new of("advancements.nether.find_fortress.title"), new of("advancements.nether.find_fortress.description"), null, ai.a, true, true, false)
         .a("fortress", bx.a.a(bw.a(cla.n)))
         .a(_snowman, "nether/find_fortress");
      y.a.a()
         .a(_snowman)
         .a(bmd.pc, new of("advancements.nether.fast_travel.title"), new of("advancements.nether.fast_travel.description"), null, ai.b, true, true, false)
         .a(ab.a.a(100))
         .a("travelled", cc.a.a(ay.a(bz.c.b(7000.0F))))
         .a(_snowman, "nether/fast_travel");
      y.a.a()
         .a(_snowmanx)
         .a(
            bmd.ns,
            new of("advancements.nether.uneasy_alliance.title"),
            new of("advancements.nether.uneasy_alliance.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(100))
         .a("killed_ghast", bt.a.a(bg.a.a().a(aqe.D).a(bw.b(brx.g))))
         .a(_snowman, "nether/uneasy_alliance");
      y _snowmanxxx = y.a.a()
         .a(_snowmanxx)
         .a(
            bup.fe,
            new of("advancements.nether.get_wither_skull.title"),
            new of("advancements.nether.get_wither_skull.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("wither_skull", bn.a.a(bup.fe))
         .a(_snowman, "nether/get_wither_skull");
      y _snowmanxxxx = y.a.a()
         .a(_snowmanxxx)
         .a(bmd.pm, new of("advancements.nether.summon_wither.title"), new of("advancements.nether.summon_wither.description"), null, ai.a, true, true, false)
         .a("summoned", cn.a.a(bg.a.a().a(aqe.aT)))
         .a(_snowman, "nether/summon_wither");
      y _snowmanxxxxx = y.a.a()
         .a(_snowmanxx)
         .a(
            bmd.nr,
            new of("advancements.nether.obtain_blaze_rod.title"),
            new of("advancements.nether.obtain_blaze_rod.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("blaze_rod", bn.a.a(bmd.nr))
         .a(_snowman, "nether/obtain_blaze_rod");
      y _snowmanxxxxxx = y.a.a()
         .a(_snowmanxxxx)
         .a(bup.es, new of("advancements.nether.create_beacon.title"), new of("advancements.nether.create_beacon.description"), null, ai.a, true, true, false)
         .a("beacon", as.a.a(bz.d.b(1)))
         .a(_snowman, "nether/create_beacon");
      y.a.a()
         .a(_snowmanxxxxxx)
         .a(
            bup.es,
            new of("advancements.nether.create_full_beacon.title"),
            new of("advancements.nether.create_full_beacon.description"),
            null,
            ai.c,
            true,
            true,
            false
         )
         .a("beacon", as.a.a(bz.d.a(4)))
         .a(_snowman, "nether/create_full_beacon");
      y _snowmanxxxxxxx = y.a.a()
         .a(_snowmanxxxxx)
         .a(bmd.nv, new of("advancements.nether.brew_potion.title"), new of("advancements.nether.brew_potion.description"), null, ai.a, true, true, false)
         .a("potion", ap.a.c())
         .a(_snowman, "nether/brew_potion");
      y _snowmanxxxxxxxx = y.a.a()
         .a(_snowmanxxxxxxx)
         .a(bmd.lT, new of("advancements.nether.all_potions.title"), new of("advancements.nether.all_potions.description"), null, ai.b, true, true, false)
         .a(ab.a.a(100))
         .a("all_effects", az.a.a(ca.a().a(apw.a).a(apw.b).a(apw.e).a(apw.h).a(apw.j).a(apw.l).a(apw.m).a(apw.n).a(apw.p).a(apw.r).a(apw.s).a(apw.B).a(apw.k)))
         .a(_snowman, "nether/all_potions");
      y.a.a()
         .a(_snowmanxxxxxxxx)
         .a(bmd.lK, new of("advancements.nether.all_effects.title"), new of("advancements.nether.all_effects.description"), null, ai.b, true, true, true)
         .a(ab.a.a(1000))
         .a(
            "all_effects",
            az.a.a(
               ca.a()
                  .a(apw.a)
                  .a(apw.b)
                  .a(apw.e)
                  .a(apw.h)
                  .a(apw.j)
                  .a(apw.l)
                  .a(apw.m)
                  .a(apw.n)
                  .a(apw.p)
                  .a(apw.r)
                  .a(apw.s)
                  .a(apw.t)
                  .a(apw.c)
                  .a(apw.d)
                  .a(apw.y)
                  .a(apw.x)
                  .a(apw.v)
                  .a(apw.q)
                  .a(apw.i)
                  .a(apw.k)
                  .a(apw.B)
                  .a(apw.C)
                  .a(apw.D)
                  .a(apw.o)
                  .a(apw.E)
                  .a(apw.F)
            )
         )
         .a(_snowman, "nether/all_effects");
      y _snowmanxxxxxxxxx = y.a.a()
         .a(_snowman)
         .a(
            bmd.ry,
            new of("advancements.nether.obtain_ancient_debris.title"),
            new of("advancements.nether.obtain_ancient_debris.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("ancient_debris", bn.a.a(bmd.ry))
         .a(_snowman, "nether/obtain_ancient_debris");
      y.a.a()
         .a(_snowmanxxxxxxxxx)
         .a(
            bmd.lt,
            new of("advancements.nether.netherite_armor.title"),
            new of("advancements.nether.netherite_armor.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(100))
         .a("netherite_armor", bn.a.a(bmd.ls, bmd.lt, bmd.lu, bmd.lv))
         .a(_snowman, "nether/netherite_armor");
      y.a.a()
         .a(_snowmanxxxxxxxxx)
         .a(bmd.rw, new of("advancements.nether.use_lodestone.title"), new of("advancements.nether.use_lodestone.description"), null, ai.a, true, true, false)
         .a("use_lodestone", br.a.a(bw.a.a().a(an.a.a().a(bup.no).b()), bq.a.a().a(bmd.mh)))
         .a(_snowman, "nether/use_lodestone");
      y _snowmanxxxxxxxxxx = y.a.a()
         .a(_snowman)
         .a(
            bmd.rA,
            new of("advancements.nether.obtain_crying_obsidian.title"),
            new of("advancements.nether.obtain_crying_obsidian.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("crying_obsidian", bn.a.a(bmd.rA))
         .a(_snowman, "nether/obtain_crying_obsidian");
      y.a.a()
         .a(_snowmanxxxxxxxxxx)
         .a(
            bmd.rN,
            new of("advancements.nether.charge_respawn_anchor.title"),
            new of("advancements.nether.charge_respawn_anchor.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("charge_respawn_anchor", br.a.a(bw.a.a().a(an.a.a().a(bup.nj).a(cm.a.a().a(bzj.a, 4).b()).b()), bq.a.a().a(bup.cS)))
         .a(_snowman, "nether/charge_respawn_anchor");
      y _snowmanxxxxxxxxxxx = y.a.a()
         .a(_snowman)
         .a(bmd.pl, new of("advancements.nether.ride_strider.title"), new of("advancements.nether.ride_strider.description"), null, ai.a, true, true, false)
         .a("used_warped_fungus_on_a_stick", bo.a.a(bg.b.a(bg.a.a().a(bg.a.a().a(aqe.aF).b()).b()), bq.a.a().a(bmd.pl).b(), bz.d.e))
         .a(_snowman, "nether/ride_strider");
      hp.a(y.a.a(), a)
         .a(_snowmanxxxxxxxxxxx)
         .a(bmd.lv, new of("advancements.nether.explore_nether.title"), new of("advancements.nether.explore_nether.description"), null, ai.b, true, true, false)
         .a(ab.a.a(500))
         .a(_snowman, "nether/explore_nether");
      y _snowmanxxxxxxxxxxxx = y.a.a()
         .a(_snowman)
         .a(bmd.rJ, new of("advancements.nether.find_bastion.title"), new of("advancements.nether.find_bastion.description"), null, ai.a, true, true, false)
         .a("bastion", bx.a.a(bw.a(cla.s)))
         .a(_snowman, "nether/find_bastion");
      y.a.a()
         .a(_snowmanxxxxxxxxxxxx)
         .a(bup.bR, new of("advancements.nether.loot_bastion.title"), new of("advancements.nether.loot_bastion.description"), null, ai.a, true, true, false)
         .a(aj.b)
         .a("loot_bastion_other", by.a.a(new vk("minecraft:chests/bastion_other")))
         .a("loot_bastion_treasure", by.a.a(new vk("minecraft:chests/bastion_treasure")))
         .a("loot_bastion_hoglin_stable", by.a.a(new vk("minecraft:chests/bastion_hoglin_stable")))
         .a("loot_bastion_bridge", by.a.a(new vk("minecraft:chests/bastion_bridge")))
         .a(_snowman, "nether/loot_bastion");
      y.a.a()
         .a(_snowman)
         .a(aj.b)
         .a(
            bmd.ki,
            new of("advancements.nether.distract_piglin.title"),
            new of("advancements.nether.distract_piglin.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("distract_piglin", bp.a.a(b, bq.a.a().a(aeg.O), bg.b.a(bg.a.a().a(aqe.ai).a(be.a.a().e(false).b()).b())))
         .a("distract_piglin_directly", cf.a.a(b, bq.a.a().a(bet.a), bg.b.a(bg.a.a().a(aqe.ai).a(be.a.a().e(false).b()).b())))
         .a(_snowman, "nether/distract_piglin");
   }
}
