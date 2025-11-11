import java.util.function.Consumer;

public class hq implements Consumer<Consumer<y>> {
   private static final aqe<?>[] a = new aqe[]{
      aqe.H, aqe.o, aqe.aa, aqe.ar, aqe.l, aqe.ab, aqe.ah, aqe.j, aqe.aW, aqe.ac, aqe.ao, aqe.Q, aqe.h, aqe.ae, aqe.C, aqe.e, aqe.G, aqe.aF
   };
   private static final blx[] b = new blx[]{bmd.ml, bmd.mn, bmd.mo, bmd.mm};
   private static final blx[] c = new blx[]{bmd.lW, bmd.lX, bmd.lU, bmd.lV};
   private static final blx[] d = new blx[]{
      bmd.kb,
      bmd.kR,
      bmd.kX,
      bmd.lx,
      bmd.ly,
      bmd.lA,
      bmd.lB,
      bmd.ml,
      bmd.mm,
      bmd.mn,
      bmd.mo,
      bmd.mp,
      bmd.mq,
      bmd.ne,
      bmd.nh,
      bmd.nl,
      bmd.nm,
      bmd.nn,
      bmd.no,
      bmd.np,
      bmd.nx,
      bmd.oY,
      bmd.oZ,
      bmd.pa,
      bmd.pb,
      bmd.pd,
      bmd.pn,
      bmd.px,
      bmd.py,
      bmd.pz,
      bmd.pK,
      bmd.pL,
      bmd.qd,
      bmd.qf,
      bmd.qh,
      bmd.ni,
      bmd.qR,
      bmd.rm,
      bmd.rt
   };

   public hq() {
   }

   public void a(Consumer<y> var1) {
      y _snowman = y.a.a()
         .a(
            bup.gA,
            new of("advancements.husbandry.root.title"),
            new of("advancements.husbandry.root.description"),
            new vk("textures/gui/advancements/backgrounds/husbandry.png"),
            ai.a,
            false,
            false,
            false
         )
         .a("consumed_item", at.a.c())
         .a(_snowman, "husbandry/root");
      y _snowmanx = y.a.a()
         .a(_snowman)
         .a(bmd.kW, new of("advancements.husbandry.plant_seed.title"), new of("advancements.husbandry.plant_seed.description"), null, ai.a, true, true, false)
         .a(aj.b)
         .a("wheat", cd.a.a(bup.bW))
         .a("pumpkin_stem", cd.a.a(bup.dN))
         .a("melon_stem", cd.a.a(bup.dO))
         .a("beetroots", cd.a.a(bup.iD))
         .a("nether_wart", cd.a.a(bup.dY))
         .a(_snowman, "husbandry/plant_seed");
      y _snowmanxx = y.a.a()
         .a(_snowman)
         .a(
            bmd.kW,
            new of("advancements.husbandry.breed_an_animal.title"),
            new of("advancements.husbandry.breed_an_animal.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a(aj.b)
         .a("bred", ao.a.c())
         .a(_snowman, "husbandry/breed_an_animal");
      this.a(y.a.a())
         .a(_snowmanx)
         .a(
            bmd.kb,
            new of("advancements.husbandry.balanced_diet.title"),
            new of("advancements.husbandry.balanced_diet.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(100))
         .a(_snowman, "husbandry/balanced_diet");
      y.a.a()
         .a(_snowmanx)
         .a(
            bmd.kO,
            new of("advancements.husbandry.netherite_hoe.title"),
            new of("advancements.husbandry.netherite_hoe.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(100))
         .a("netherite_hoe", bn.a.a(bmd.kO))
         .a(_snowman, "husbandry/obtain_netherite_hoe");
      y _snowmanxxx = y.a.a()
         .a(_snowman)
         .a(
            bmd.pH,
            new of("advancements.husbandry.tame_an_animal.title"),
            new of("advancements.husbandry.tame_an_animal.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a("tamed_animal", co.a.c())
         .a(_snowman, "husbandry/tame_an_animal");
      this.b(y.a.a())
         .a(_snowmanxx)
         .a(
            bmd.pd,
            new of("advancements.husbandry.breed_all_animals.title"),
            new of("advancements.husbandry.breed_all_animals.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(100))
         .a(_snowman, "husbandry/bred_all_animals");
      y _snowmanxxxx = this.d(y.a.a())
         .a(_snowman)
         .a(aj.b)
         .a(
            bmd.mi,
            new of("advancements.husbandry.fishy_business.title"),
            new of("advancements.husbandry.fishy_business.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a(_snowman, "husbandry/fishy_business");
      this.c(y.a.a())
         .a(_snowmanxxxx)
         .a(aj.b)
         .a(
            bmd.lU,
            new of("advancements.husbandry.tactical_fishing.title"),
            new of("advancements.husbandry.tactical_fishing.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a(_snowman, "husbandry/tactical_fishing");
      this.e(y.a.a())
         .a(_snowmanxxx)
         .a(
            bmd.ml,
            new of("advancements.husbandry.complete_catalogue.title"),
            new of("advancements.husbandry.complete_catalogue.description"),
            null,
            ai.b,
            true,
            true,
            false
         )
         .a(ab.a.a(50))
         .a(_snowman, "husbandry/complete_catalogue");
      y.a.a()
         .a(_snowman)
         .a("safely_harvest_honey", br.a.a(bw.a.a().a(an.a.a().a(aed.aj).b()).a(true), bq.a.a().a(bmd.nw)))
         .a(
            bmd.rt,
            new of("advancements.husbandry.safely_harvest_honey.title"),
            new of("advancements.husbandry.safely_harvest_honey.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a(_snowman, "husbandry/safely_harvest_honey");
      y.a.a()
         .a(_snowman)
         .a("silk_touch_nest", am.a.a(bup.nc, bq.a.a().a(new bb(bpw.u, bz.d.b(1))), bz.d.a(3)))
         .a(
            bup.nc,
            new of("advancements.husbandry.silk_touch_nest.title"),
            new of("advancements.husbandry.silk_touch_nest.description"),
            null,
            ai.a,
            true,
            true,
            false
         )
         .a(_snowman, "husbandry/silk_touch_nest");
   }

   private y.a a(y.a var1) {
      for (blx _snowman : d) {
         _snowman.a(gm.T.b(_snowman).a(), at.a.a(_snowman));
      }

      return _snowman;
   }

   private y.a b(y.a var1) {
      for (aqe<?> _snowman : a) {
         _snowman.a(aqe.a(_snowman).toString(), ao.a.a(bg.a.a().a(_snowman)));
      }

      _snowman.a(aqe.a(aqe.aN).toString(), ao.a.a(bg.a.a().a(aqe.aN).b(), bg.a.a().a(aqe.aN).b(), bg.a));
      return _snowman;
   }

   private y.a c(y.a var1) {
      for (blx _snowman : c) {
         _snowman.a(gm.T.b(_snowman).a(), bi.a.a(bq.a.a().a(_snowman).b()));
      }

      return _snowman;
   }

   private y.a d(y.a var1) {
      for (blx _snowman : b) {
         _snowman.a(gm.T.b(_snowman).a(), bk.a.a(bq.a, bg.a, bq.a.a().a(_snowman).b()));
      }

      return _snowman;
   }

   private y.a e(y.a var1) {
      bab.bq.forEach((var1x, var2) -> _snowman.a(var2.a(), co.a.a(bg.a.a().a(var2).b())));
      return _snowman;
   }
}
