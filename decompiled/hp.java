import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;

public class hp implements Consumer<Consumer<y>> {
   private static final List<vj<bsv>> a = ImmutableList.of(
      btb.C,
      btb.h,
      btb.g,
      btb.c,
      btb.s,
      btb.H,
      btb.E,
      btb.L,
      btb.e,
      btb.z,
      btb.m,
      btb.t,
      new vj[]{
         btb.n,
         btb.M,
         btb.J,
         btb.b,
         btb.l,
         btb.G,
         btb.A,
         btb.w,
         btb.x,
         btb.p,
         btb.d,
         btb.r,
         btb.v,
         btb.q,
         btb.K,
         btb.F,
         btb.N,
         btb.D,
         btb.f,
         btb.B,
         btb.o,
         btb.I,
         btb.S,
         btb.T,
         btb.U,
         btb.W,
         btb.X,
         btb.Y,
         btb.av,
         btb.aw
      }
   );
   private static final aqe<?>[] b = new aqe[]{
      aqe.f,
      aqe.i,
      aqe.m,
      aqe.q,
      aqe.r,
      aqe.t,
      aqe.u,
      aqe.v,
      aqe.w,
      aqe.D,
      aqe.F,
      aqe.G,
      aqe.I,
      aqe.S,
      aqe.ag,
      aqe.ai,
      aqe.aj,
      aqe.ak,
      aqe.ap,
      aqe.as,
      aqe.au,
      aqe.av,
      aqe.ax,
      aqe.aC,
      aqe.aE,
      aqe.aO,
      aqe.aQ,
      aqe.aS,
      aqe.aU,
      aqe.aT,
      aqe.aX,
      aqe.ba,
      aqe.aY,
      aqe.bb
   };

   public hp() {
   }

   public void a(Consumer<y> var1) {
      y _snowman = y.a.a()
         .a(
            bmd.pc,
            new of("advancements.adventure.root.title"),
            new of("advancements.adventure.root.description"),
            new vk("textures/gui/advancements/backgrounds/adventure.png"),
            ai.a,
            false,
            false,
            false
         )
         .a(aj.b)
         .a("killed_something", bt.a.c())
         .a("killed_by_something", bt.a.d())
         .a(_snowman, "adventure/root");
      y _snowmanx = y.a.a()
         .a(_snowman)
         .a(
            bup.aL,
            new of("advancements.adventure.sleep_in_bed.title"),
            new of("advancements.adventure.sleep_in_bed.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("slept_in_bed", bx.a.c())
         .a(_snowman, "adventure/sleep_in_bed");
      a(y.a.a(), a)
         .a(_snowmanx)
         .a(
            bmd.ln,
            new of("advancements.adventure.adventuring_time.title"),
            new of("advancements.adventure.adventuring_time.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(500))
         .a(_snowman, "adventure/adventuring_time");
      y _snowmanxx = y.a.a()
         .a(_snowman)
         .a(bmd.oV, new of("advancements.adventure.trade.title"), new of("advancements.adventure.trade.description"), null, ai.a, true, true, false)
         .a("traded", cr.a.c())
         .a(_snowman, "adventure/trade");
      y _snowmanxxx = this.a(y.a.a())
         .a(_snowman)
         .a(bmd.kA, new of("advancements.adventure.kill_a_mob.title"), new of("advancements.adventure.kill_a_mob.description"), null, ai.a, true, true, false)
         .a(aj.b)
         .a(_snowman, "adventure/kill_a_mob");
      this.a(y.a.a())
         .a(_snowmanxxx)
         .a(
            bmd.kF,
            new of("advancements.adventure.kill_all_mobs.title"),
            new of("advancements.adventure.kill_all_mobs.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(100))
         .a(_snowman, "adventure/kill_all_mobs");
      y _snowmanxxxx = y.a.a()
         .a(_snowmanxxx)
         .a(bmd.kc, new of("advancements.adventure.shoot_arrow.title"), new of("advancements.adventure.shoot_arrow.description"), null, ai.a, true, true, false)
         .a("shot_arrow", ce.a.a(av.a.a().a(aw.a.a().a(true).a(bg.a.a().a(aee.e)))))
         .a(_snowman, "adventure/shoot_arrow");
      y _snowmanxxxxx = y.a.a()
         .a(_snowmanxxx)
         .a(
            bmd.qM,
            new of("advancements.adventure.throw_trident.title"),
            new of("advancements.adventure.throw_trident.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("shot_trident", ce.a.a(av.a.a().a(aw.a.a().a(true).a(bg.a.a().a(aqe.aK)))))
         .a(_snowman, "adventure/throw_trident");
      y.a.a()
         .a(_snowmanxxxxx)
         .a(
            bmd.qM,
            new of("advancements.adventure.very_very_frightening.title"),
            new of("advancements.adventure.very_very_frightening.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("struck_villager", ar.a.a(bg.a.a().a(aqe.aP).b()))
         .a(_snowman, "adventure/very_very_frightening");
      y.a.a()
         .a(_snowmanxx)
         .a(
            bup.cU,
            new of("advancements.adventure.summon_iron_golem.title"),
            new of("advancements.adventure.summon_iron_golem.description"),
            null,
            ai.c,
            true,
            true,
            false
         )
         .a("summoned_golem", cn.a.a(bg.a.a().a(aqe.K)))
         .a(_snowman, "adventure/summon_iron_golem");
      y.a.a()
         .a(_snowmanxxxx)
         .a(bmd.kd, new of("advancements.adventure.sniper_duel.title"), new of("advancements.adventure.sniper_duel.description"), null, ai.b, true, true, false)
         .a(ab.a.a(50))
         .a("killed_skeleton", bt.a.a(bg.a.a().a(aqe.av).a(ay.a(bz.c.b(50.0F))), aw.a.a().a(true)))
         .a(_snowman, "adventure/sniper_duel");
      y.a.a()
         .a(_snowmanxxx)
         .a(
            bmd.qu,
            new of("advancements.adventure.totem_of_undying.title"),
            new of("advancements.adventure.totem_of_undying.description"),
            null,
            ai.c,
            true,
            true,
            false
         )
         .a("used_totem", ct.a.a(bmd.qu))
         .a(_snowman, "adventure/totem_of_undying");
      y _snowmanxxxxxx = y.a.a()
         .a(_snowman)
         .a(bmd.qQ, new of("advancements.adventure.ol_betsy.title"), new of("advancements.adventure.ol_betsy.description"), null, ai.a, true, true, false)
         .a("shot_crossbow", cj.a.a(bmd.qQ))
         .a(_snowman, "adventure/ol_betsy");
      y.a.a()
         .a(_snowmanxxxxxx)
         .a(
            bmd.qQ,
            new of("advancements.adventure.whos_the_pillager_now.title"),
            new of("advancements.adventure.whos_the_pillager_now.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("kill_pillager", bs.a.a(bg.a.a().a(aqe.ak)))
         .a(_snowman, "adventure/whos_the_pillager_now");
      y.a.a()
         .a(_snowmanxxxxxx)
         .a(
            bmd.qQ,
            new of("advancements.adventure.two_birds_one_arrow.title"),
            new of("advancements.adventure.two_birds_one_arrow.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(65))
         .a("two_birds", bs.a.a(bg.a.a().a(aqe.ag), bg.a.a().a(aqe.ag)))
         .a(_snowman, "adventure/two_birds_one_arrow");
      y.a.a()
         .a(_snowmanxxxxxx)
         .a(bmd.qQ, new of("advancements.adventure.arbalistic.title"), new of("advancements.adventure.arbalistic.description"), null, ai.b, true, true, true)
         .a(ab.a.a(85))
         .a("arbalistic", bs.a.a(bz.d.a(5)))
         .a(_snowman, "adventure/arbalistic");
      y _snowmanxxxxxxx = y.a.a()
         .a(_snowman)
         .a(
            bhb.s(),
            new of("advancements.adventure.voluntary_exile.title"),
            new of("advancements.adventure.voluntary_exile.description"),
            null,
            ai.a,
            true,
            true,
            true
         )
         .a("voluntary_exile", bt.a.a(bg.a.a().a(aee.c).a(bd.b)))
         .a(_snowman, "adventure/voluntary_exile");
      y.a.a()
         .a(_snowmanxxxxxxx)
         .a(
            bhb.s(),
            new of("advancements.adventure.hero_of_the_village.title"),
            new of("advancements.adventure.hero_of_the_village.description"),
            null,
            ai.b,
            true,
            true,
            true
         )
         .a(ab.a.a(100))
         .a("hero_of_the_village", bx.a.d())
         .a(_snowman, "adventure/hero_of_the_village");
      y.a.a()
         .a(_snowman)
         .a(
            bup.ne.h(),
            new of("advancements.adventure.honey_block_slide.title"),
            new of("advancements.adventure.honey_block_slide.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("honey_block_slide", cl.a.a(bup.ne))
         .a(_snowman, "adventure/honey_block_slide");
      y.a.a()
         .a(_snowmanxxxx)
         .a(bup.nb.h(), new of("advancements.adventure.bullseye.title"), new of("advancements.adventure.bullseye.description"), null, ai.b, true, true, false)
         .a(ab.a.a(50))
         .a("bullseye", cp.a.a(bz.d.a(15), bg.b.a(bg.a.a().a(ay.a(bz.c.b(30.0F))).b())))
         .a(_snowman, "adventure/bullseye");
   }

   private y.a a(y.a var1) {
      for (aqe<?> _snowman : b) {
         _snowman.a(gm.S.b(_snowman).toString(), bt.a.a(bg.a.a().a(_snowman)));
      }

      return _snowman;
   }

   protected static y.a a(y.a var0, List<vj<bsv>> var1) {
      for (vj<bsv> _snowman : _snowman) {
         _snowman.a(_snowman.a().toString(), bx.a.a(bw.a(_snowman)));
      }

      return _snowman;
   }
}
