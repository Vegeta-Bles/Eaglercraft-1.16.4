import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jg implements hm {
   private static final Logger b = LogManager.getLogger();
   private static final Gson c = new GsonBuilder().setPrettyPrinting().create();
   private final hl d;

   public jg(hl var1) {
      this.d = _snowman;
   }

   @Override
   public void a(hn var1) throws IOException {
      Path _snowman = this.d.b();
      Set<vk> _snowmanx = Sets.newHashSet();
      a(var3x -> {
         if (!_snowman.add(var3x.b())) {
            throw new IllegalStateException("Duplicate recipe " + var3x.b());
         } else {
            a(_snowman, var3x.a(), _snowman.resolve("data/" + var3x.b().b() + "/recipes/" + var3x.b().a() + ".json"));
            JsonObject _snowmanxx = var3x.d();
            if (_snowmanxx != null) {
               b(_snowman, _snowmanxx, _snowman.resolve("data/" + var3x.b().b() + "/advancements/" + var3x.e().a() + ".json"));
            }
         }
      });
      b(_snowman, y.a.a().a("impossible", new bm.a()).b(), _snowman.resolve("data/minecraft/advancements/recipes/root.json"));
   }

   private static void a(hn var0, JsonObject var1, Path var2) {
      try {
         String _snowman = c.toJson(_snowman);
         String _snowmanx = a.hashUnencodedChars(_snowman).toString();
         if (!Objects.equals(_snowman.a(_snowman), _snowmanx) || !Files.exists(_snowman)) {
            Files.createDirectories(_snowman.getParent());

            try (BufferedWriter _snowmanxx = Files.newBufferedWriter(_snowman)) {
               _snowmanxx.write(_snowman);
            }
         }

         _snowman.a(_snowman, _snowmanx);
      } catch (IOException var18) {
         b.error("Couldn't save recipe {}", _snowman, var18);
      }
   }

   private static void b(hn var0, JsonObject var1, Path var2) {
      try {
         String _snowman = c.toJson(_snowman);
         String _snowmanx = a.hashUnencodedChars(_snowman).toString();
         if (!Objects.equals(_snowman.a(_snowman), _snowmanx) || !Files.exists(_snowman)) {
            Files.createDirectories(_snowman.getParent());

            try (BufferedWriter _snowmanxx = Files.newBufferedWriter(_snowman)) {
               _snowmanxx.write(_snowman);
            }
         }

         _snowman.a(_snowman, _snowmanx);
      } catch (IOException var18) {
         b.error("Couldn't save recipe advancement {}", _snowman, var18);
      }
   }

   private static void a(Consumer<jf> var0) {
      a(_snowman, bup.r, aeg.u);
      b(_snowman, bup.p, aeg.t);
      b(_snowman, bup.mC, aeg.x);
      a(_snowman, bup.s, aeg.r);
      b(_snowman, bup.q, aeg.v);
      b(_snowman, bup.n, aeg.s);
      b(_snowman, bup.o, aeg.w);
      b(_snowman, bup.mD, aeg.y);
      a(_snowman, bup.Z, bup.N);
      a(_snowman, bup.X, bup.L);
      a(_snowman, bup.aa, bup.O);
      a(_snowman, bup.Y, bup.M);
      a(_snowman, bup.V, bup.J);
      a(_snowman, bup.W, bup.K);
      a(_snowman, bup.ms, bup.mq);
      a(_snowman, bup.mj, bup.mh);
      a(_snowman, bup.af, bup.S);
      a(_snowman, bup.ad, bup.Q);
      a(_snowman, bup.ag, bup.T);
      a(_snowman, bup.ae, bup.R);
      a(_snowman, bup.ab, bup.U);
      a(_snowman, bup.ac, bup.P);
      a(_snowman, bup.mt, bup.mr);
      a(_snowman, bup.mk, bup.mi);
      b(_snowman, bmd.qs, bup.r);
      b(_snowman, bmd.qq, bup.p);
      b(_snowman, bmd.qt, bup.s);
      b(_snowman, bmd.qr, bup.q);
      b(_snowman, bmd.lR, bup.n);
      b(_snowman, bmd.qp, bup.o);
      c(_snowman, bup.fa, bup.r);
      d(_snowman, bup.iu, bup.r);
      e(_snowman, bup.ip, bup.r);
      f(_snowman, bup.ik, bup.r);
      g(_snowman, bup.cw, bup.r);
      h(_snowman, bup.hO, bup.r);
      i(_snowman, bup.gl, bup.r);
      j(_snowman, bup.ds, bup.r);
      k(_snowman, bup.cc, bup.r);
      c(_snowman, bup.eY, bup.p);
      d(_snowman, bup.is, bup.p);
      e(_snowman, bup.in, bup.p);
      f(_snowman, bup.ii, bup.p);
      g(_snowman, bup.cu, bup.p);
      h(_snowman, bup.hM, bup.p);
      i(_snowman, bup.ep, bup.p);
      j(_snowman, bup.dq, bup.p);
      k(_snowman, bup.cb, bup.p);
      c(_snowman, bup.mQ, bup.mC);
      d(_snowman, bup.mS, bup.mC);
      e(_snowman, bup.mI, bup.mC);
      f(_snowman, bup.mM, bup.mC);
      g(_snowman, bup.mG, bup.mC);
      h(_snowman, bup.mE, bup.mC);
      i(_snowman, bup.mO, bup.mC);
      j(_snowman, bup.mK, bup.mC);
      k(_snowman, bup.mU, bup.mC);
      c(_snowman, bup.fb, bup.s);
      d(_snowman, bup.iv, bup.s);
      e(_snowman, bup.iq, bup.s);
      f(_snowman, bup.il, bup.s);
      g(_snowman, bup.cx, bup.s);
      h(_snowman, bup.hP, bup.s);
      i(_snowman, bup.gm, bup.s);
      j(_snowman, bup.dt, bup.s);
      k(_snowman, bup.ce, bup.s);
      c(_snowman, bup.eZ, bup.q);
      d(_snowman, bup.it, bup.q);
      e(_snowman, bup.io, bup.q);
      f(_snowman, bup.ij, bup.q);
      g(_snowman, bup.cv, bup.q);
      h(_snowman, bup.hN, bup.q);
      i(_snowman, bup.eq, bup.q);
      j(_snowman, bup.dr, bup.q);
      k(_snowman, bup.cd, bup.q);
      c(_snowman, bup.eW, bup.n);
      d(_snowman, bup.cf, bup.n);
      e(_snowman, bup.cJ, bup.n);
      f(_snowman, bup.dQ, bup.n);
      g(_snowman, bup.cs, bup.n);
      h(_snowman, bup.hK, bup.n);
      i(_snowman, bup.bQ, bup.n);
      j(_snowman, bup.do_, bup.n);
      k(_snowman, bup.bZ, bup.n);
      c(_snowman, bup.eX, bup.o);
      d(_snowman, bup.ir, bup.o);
      e(_snowman, bup.im, bup.o);
      f(_snowman, bup.ih, bup.o);
      g(_snowman, bup.ct, bup.o);
      h(_snowman, bup.hL, bup.o);
      i(_snowman, bup.eo, bup.o);
      j(_snowman, bup.dp, bup.o);
      k(_snowman, bup.ca, bup.o);
      c(_snowman, bup.mR, bup.mD);
      d(_snowman, bup.mT, bup.mD);
      e(_snowman, bup.mJ, bup.mD);
      f(_snowman, bup.mN, bup.mD);
      g(_snowman, bup.mH, bup.mD);
      h(_snowman, bup.mF, bup.mD);
      i(_snowman, bup.mP, bup.mD);
      j(_snowman, bup.mL, bup.mD);
      k(_snowman, bup.mV, bup.mD);
      l(_snowman, bup.bn, bmd.mJ);
      m(_snowman, bup.gQ, bup.bn);
      n(_snowman, bup.gQ, bmd.mJ);
      o(_snowman, bmd.nd, bup.bn);
      p(_snowman, bmd.nd, bmd.mJ);
      q(_snowman, bmd.qb, bup.bn);
      l(_snowman, bup.bj, bmd.mF);
      m(_snowman, bup.gM, bup.bj);
      n(_snowman, bup.gM, bmd.mF);
      o(_snowman, bmd.mZ, bup.bj);
      p(_snowman, bmd.mZ, bmd.mF);
      q(_snowman, bmd.pX, bup.bj);
      l(_snowman, bup.bk, bmd.mG);
      m(_snowman, bup.gN, bup.bk);
      n(_snowman, bup.gN, bmd.mG);
      o(_snowman, bmd.na, bup.bk);
      p(_snowman, bmd.na, bmd.mG);
      q(_snowman, bmd.pY, bup.bk);
      l(_snowman, bup.bh, bmd.mD);
      m(_snowman, bup.gK, bup.bh);
      n(_snowman, bup.gK, bmd.mD);
      o(_snowman, bmd.mX, bup.bh);
      p(_snowman, bmd.mX, bmd.mD);
      q(_snowman, bmd.pV, bup.bh);
      l(_snowman, bup.bf, bmd.mB);
      m(_snowman, bup.gI, bup.bf);
      n(_snowman, bup.gI, bmd.mB);
      o(_snowman, bmd.mV, bup.bf);
      p(_snowman, bmd.mV, bmd.mB);
      q(_snowman, bmd.pT, bup.bf);
      l(_snowman, bup.bl, bmd.mH);
      m(_snowman, bup.gO, bup.bl);
      n(_snowman, bup.gO, bmd.mH);
      o(_snowman, bmd.nb, bup.bl);
      p(_snowman, bmd.nb, bmd.mH);
      q(_snowman, bmd.pZ, bup.bl);
      l(_snowman, bup.bb, bmd.mx);
      m(_snowman, bup.gE, bup.bb);
      n(_snowman, bup.gE, bmd.mx);
      o(_snowman, bmd.mR, bup.bb);
      p(_snowman, bmd.mR, bmd.mx);
      q(_snowman, bmd.pP, bup.bb);
      l(_snowman, bup.bg, bmd.mC);
      m(_snowman, bup.gJ, bup.bg);
      n(_snowman, bup.gJ, bmd.mC);
      o(_snowman, bmd.mW, bup.bg);
      p(_snowman, bmd.mW, bmd.mC);
      q(_snowman, bmd.pU, bup.bg);
      l(_snowman, bup.bd, bmd.mz);
      m(_snowman, bup.gG, bup.bd);
      n(_snowman, bup.gG, bmd.mz);
      o(_snowman, bmd.mT, bup.bd);
      p(_snowman, bmd.mT, bmd.mz);
      q(_snowman, bmd.pR, bup.bd);
      l(_snowman, bup.ba, bmd.mw);
      m(_snowman, bup.gD, bup.ba);
      n(_snowman, bup.gD, bmd.mw);
      o(_snowman, bmd.mQ, bup.ba);
      p(_snowman, bmd.mQ, bmd.mw);
      q(_snowman, bmd.pO, bup.ba);
      l(_snowman, bup.aZ, bmd.mv);
      m(_snowman, bup.gC, bup.aZ);
      n(_snowman, bup.gC, bmd.mv);
      o(_snowman, bmd.mP, bup.aZ);
      p(_snowman, bmd.mP, bmd.mv);
      q(_snowman, bmd.pN, bup.aZ);
      l(_snowman, bup.be, bmd.mA);
      m(_snowman, bup.gH, bup.be);
      n(_snowman, bup.gH, bmd.mA);
      o(_snowman, bmd.mU, bup.be);
      p(_snowman, bmd.mU, bmd.mA);
      q(_snowman, bmd.pS, bup.be);
      l(_snowman, bup.bi, bmd.mE);
      m(_snowman, bup.gL, bup.bi);
      n(_snowman, bup.gL, bmd.mE);
      o(_snowman, bmd.mY, bup.bi);
      p(_snowman, bmd.mY, bmd.mE);
      q(_snowman, bmd.pW, bup.bi);
      l(_snowman, bup.bm, bmd.mI);
      m(_snowman, bup.gP, bup.bm);
      n(_snowman, bup.gP, bmd.mI);
      o(_snowman, bmd.nc, bup.bm);
      p(_snowman, bmd.nc, bmd.mI);
      q(_snowman, bmd.qa, bup.bm);
      m(_snowman, bup.gB, bup.aY);
      o(_snowman, bmd.mO, bup.aY);
      q(_snowman, bmd.pM, bup.aY);
      l(_snowman, bup.bc, bmd.my);
      m(_snowman, bup.gF, bup.bc);
      n(_snowman, bup.gF, bmd.my);
      o(_snowman, bmd.mS, bup.bc);
      p(_snowman, bmd.mS, bmd.my);
      q(_snowman, bmd.pQ, bup.bc);
      r(_snowman, bup.dn, bmd.mJ);
      s(_snowman, bup.gk, bup.dn);
      t(_snowman, bup.gk, bmd.mJ);
      r(_snowman, bup.dj, bmd.mF);
      s(_snowman, bup.gg, bup.dj);
      t(_snowman, bup.gg, bmd.mF);
      r(_snowman, bup.dk, bmd.mG);
      s(_snowman, bup.gh, bup.dk);
      t(_snowman, bup.gh, bmd.mG);
      r(_snowman, bup.dh, bmd.mD);
      s(_snowman, bup.ge, bup.dh);
      t(_snowman, bup.ge, bmd.mD);
      r(_snowman, bup.df, bmd.mB);
      s(_snowman, bup.gc, bup.df);
      t(_snowman, bup.gc, bmd.mB);
      r(_snowman, bup.dl, bmd.mH);
      s(_snowman, bup.gi, bup.dl);
      t(_snowman, bup.gi, bmd.mH);
      r(_snowman, bup.db, bmd.mx);
      s(_snowman, bup.fY, bup.db);
      t(_snowman, bup.fY, bmd.mx);
      r(_snowman, bup.dg, bmd.mC);
      s(_snowman, bup.gd, bup.dg);
      t(_snowman, bup.gd, bmd.mC);
      r(_snowman, bup.dd, bmd.mz);
      s(_snowman, bup.ga, bup.dd);
      t(_snowman, bup.ga, bmd.mz);
      r(_snowman, bup.da, bmd.mw);
      s(_snowman, bup.fX, bup.da);
      t(_snowman, bup.fX, bmd.mw);
      r(_snowman, bup.cZ, bmd.mv);
      s(_snowman, bup.fW, bup.cZ);
      t(_snowman, bup.fW, bmd.mv);
      r(_snowman, bup.de, bmd.mA);
      s(_snowman, bup.gb, bup.de);
      t(_snowman, bup.gb, bmd.mA);
      r(_snowman, bup.di, bmd.mE);
      s(_snowman, bup.gf, bup.di);
      t(_snowman, bup.gf, bmd.mE);
      r(_snowman, bup.dm, bmd.mI);
      s(_snowman, bup.gj, bup.dm);
      t(_snowman, bup.gj, bmd.mI);
      r(_snowman, bup.cY, bmd.mu);
      s(_snowman, bup.fV, bup.cY);
      t(_snowman, bup.fV, bmd.mu);
      r(_snowman, bup.dc, bmd.my);
      s(_snowman, bup.fZ, bup.dc);
      t(_snowman, bup.fZ, bmd.my);
      u(_snowman, bup.fU, bmd.mJ);
      u(_snowman, bup.fQ, bmd.mF);
      u(_snowman, bup.fR, bmd.mG);
      u(_snowman, bup.fO, bmd.mD);
      u(_snowman, bup.fM, bmd.mB);
      u(_snowman, bup.fS, bmd.mH);
      u(_snowman, bup.fI, bmd.mx);
      u(_snowman, bup.fN, bmd.mC);
      u(_snowman, bup.fK, bmd.mz);
      u(_snowman, bup.fH, bmd.mw);
      u(_snowman, bup.fG, bmd.mv);
      u(_snowman, bup.fL, bmd.mA);
      u(_snowman, bup.fP, bmd.mE);
      u(_snowman, bup.fT, bmd.mI);
      u(_snowman, bup.fF, bmd.mu);
      u(_snowman, bup.fJ, bmd.my);
      v(_snowman, bup.kb, bmd.mJ);
      v(_snowman, bup.jX, bmd.mF);
      v(_snowman, bup.jY, bmd.mG);
      v(_snowman, bup.jV, bmd.mD);
      v(_snowman, bup.jT, bmd.mB);
      v(_snowman, bup.jZ, bmd.mH);
      v(_snowman, bup.jP, bmd.mx);
      v(_snowman, bup.jU, bmd.mC);
      v(_snowman, bup.jR, bmd.mz);
      v(_snowman, bup.jO, bmd.mw);
      v(_snowman, bup.jN, bmd.mv);
      v(_snowman, bup.jS, bmd.mA);
      v(_snowman, bup.jW, bmd.mE);
      v(_snowman, bup.ka, bmd.mI);
      v(_snowman, bup.jM, bmd.mu);
      v(_snowman, bup.jQ, bmd.my);
      jh.a(bup.fD, 6).a('#', bup.cz).a('S', bmd.kP).a('X', bmd.kh).a("XSX").a("X#X").a("XSX").a("has_rail", a((brw)bup.ch)).a(_snowman);
      ji.a(bup.g, 2).b(bup.e).b(bup.m).a("has_stone", a((brw)bup.e)).a(_snowman);
      jh.a(bup.fo).a('I', bup.bF).a('i', bmd.kh).a("III").a(" i ").a("iii").a("has_iron_block", a((brw)bup.bF)).a(_snowman);
      jh.a(bmd.pC).a('/', bmd.kP).a('_', bup.hR).a("///").a(" / ").a("/_/").a("has_stone_slab", a((brw)bup.hR)).a(_snowman);
      jh.a(bmd.kd, 4).a('#', bmd.kP).a('X', bmd.lw).a('Y', bmd.kT).a("X").a("#").a("Y").a("has_feather", a(bmd.kT)).a("has_flint", a(bmd.lw)).a(_snowman);
      jh.a(bup.lS, 1).a('P', aeg.c).a('S', aeg.j).a("PSP").a("P P").a("PSP").a("has_planks", a(aeg.c)).a("has_wood_slab", a(aeg.j)).a(_snowman);
      jh.a(bup.es).a('S', bmd.pm).a('G', bup.ap).a('O', bup.bK).a("GGG").a("GSG").a("OOO").a("has_nether_star", a(bmd.pm)).a(_snowman);
      jh.a(bup.nd).a('P', aeg.c).a('H', bmd.rq).a("PPP").a("HHH").a("PPP").a("has_honeycomb", a(bmd.rq)).a(_snowman);
      ji.a(bmd.qh).b(bmd.kQ).b(bmd.qf, 6).a("has_beetroot", a(bmd.qf)).a(_snowman);
      ji.a(bmd.mJ).b(bmd.mr).a("black_dye").a("has_ink_sac", a(bmd.mr)).a(_snowman);
      ji.a(bmd.mJ).b(bup.bA).a("black_dye").a("has_black_flower", a((brw)bup.bA)).a(_snowman, "black_dye_from_wither_rose");
      ji.a(bmd.nz, 2).b(bmd.nr).a("has_blaze_rod", a(bmd.nr)).a(_snowman);
      ji.a(bmd.mF).b(bmd.mt).a("blue_dye").a("has_lapis_lazuli", a(bmd.mt)).a(_snowman);
      ji.a(bmd.mF).b(bup.bz).a("blue_dye").a("has_blue_flower", a((brw)bup.bz)).a(_snowman, "blue_dye_from_cornflower");
      jh.a(bup.kV).a('#', bup.gT).a("###").a("###").a("###").a("has_packed_ice", a((brw)bup.gT)).a(_snowman);
      jh.a(bup.iM).a('X', bmd.mK).a("XXX").a("XXX").a("XXX").a("has_bonemeal", a(bmd.mK)).a(_snowman);
      ji.a(bmd.mK, 3).b(bmd.mL).a("bonemeal").a("has_bone", a(bmd.mL)).a(_snowman);
      ji.a(bmd.mK, 9).b(bup.iM).a("bonemeal").a("has_bone_block", a((brw)bup.iM)).a(_snowman, "bone_meal_from_bone_block");
      ji.a(bmd.mc).b(bmd.mb, 3).b(bmd.lS).a("has_paper", a(bmd.mb)).a(_snowman);
      jh.a(bup.bI).a('#', aeg.c).a('X', bmd.mc).a("###").a("XXX").a("###").a("has_book", a(bmd.mc)).a(_snowman);
      jh.a(bmd.kc).a('#', bmd.kP).a('X', bmd.kS).a(" #X").a("# X").a(" #X").a("has_string", a(bmd.kS)).a(_snowman);
      jh.a(bmd.kQ, 4)
         .a('#', aeg.c)
         .a("# #")
         .a(" # ")
         .a("has_brown_mushroom", a((brw)bup.bC))
         .a("has_red_mushroom", a((brw)bup.bD))
         .a("has_mushroom_stew", a(bmd.kR))
         .a(_snowman);
      jh.a(bmd.kX).a('#', bmd.kW).a("###").a("has_wheat", a(bmd.kW)).a(_snowman);
      jh.a(bup.ea).a('B', bmd.nr).a('#', aeg.ac).a(" B ").a("###").a("has_blaze_rod", a(bmd.nr)).a(_snowman);
      jh.a(bup.bG).a('#', bmd.lY).a("##").a("##").a("has_brick", a(bmd.lY)).a(_snowman);
      jh.a(bup.hW, 6).a('#', bup.bG).a("###").a("has_brick_block", a((brw)bup.bG)).a(_snowman);
      jh.a(bup.dR, 4).a('#', bup.bG).a("#  ").a("## ").a("###").a("has_brick_block", a((brw)bup.bG)).a(_snowman);
      ji.a(bmd.mG).b(bmd.ms).a("brown_dye").a("has_cocoa_beans", a(bmd.ms)).a(_snowman);
      jh.a(bmd.lK).a('#', bmd.kh).a("# #").a(" # ").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.cW).a('A', bmd.lT).a('B', bmd.mM).a('C', bmd.kW).a('E', bmd.mg).a("AAA").a("BEB").a("CCC").a("has_egg", a(bmd.mg)).a(_snowman);
      jh.a(bup.me).a('L', aeg.q).a('S', bmd.kP).a('C', aeg.X).a(" S ").a("SCS").a("LLL").a("has_stick", a(bmd.kP)).a("has_coal", a(aeg.X)).a(_snowman);
      jh.a(bmd.pk).a('#', bmd.mi).a('X', bmd.oY).a("# ").a(" X").a("has_carrot", a(bmd.oY)).a(_snowman);
      jh.a(bmd.pl).a('#', bmd.mi).a('X', bmd.bx).a("# ").a(" X").a("has_warped_fungus", a(bmd.bx)).a(_snowman);
      jh.a(bup.eb).a('#', bmd.kh).a("# #").a("# #").a("###").a("has_water_bucket", a(bmd.lL)).a(_snowman);
      jh.a(bup.na).a('#', aeg.j).a("# #").a("# #").a("###").a("has_wood_slab", a(aeg.j)).a(_snowman);
      jh.a(bup.bR).a('#', aeg.c).a("###").a("# #").a("###").a("has_lots_of_items", new bn.a(bg.b.a, bz.d.b(10), bz.d.e, bz.d.e, new bq[0])).a(_snowman);
      jh.a(bmd.me).a('A', bup.bR).a('B', bmd.lN).a("A").a("B").a("has_minecart", a(bmd.lN)).a(_snowman);
      jh.a(bup.nG).a('#', bup.hY).a("#").a("#").a("has_nether_bricks", a((brw)bup.dV)).a(_snowman);
      jh.a(bup.fA)
         .a('#', bup.hZ)
         .a("#")
         .a("#")
         .a("has_chiseled_quartz_block", a((brw)bup.fA))
         .a("has_quartz_block", a((brw)bup.fz))
         .a("has_quartz_pillar", a((brw)bup.fB))
         .a(_snowman);
      jh.a(bup.dx).a('#', bup.hX).a("#").a("#").a("has_stone_bricks", a(aeg.d)).a(_snowman);
      jh.a(bup.cG).a('#', bmd.lZ).a("##").a("##").a("has_clay_ball", a(bmd.lZ)).a(_snowman);
      jh.a(bmd.mj).a('#', bmd.ki).a('X', bmd.lP).a(" # ").a("#X#").a(" # ").a("has_redstone", a(bmd.lP)).a(_snowman);
      ji.a(bmd.ke, 9).b(bup.gS).a("has_coal_block", a((brw)bup.gS)).a(_snowman);
      jh.a(bup.gS).a('#', bmd.ke).a("###").a("###").a("###").a("has_coal", a(bmd.ke)).a(_snowman);
      jh.a(bup.k, 4).a('D', bup.j).a('G', bup.E).a("DG").a("GD").a("has_gravel", a((brw)bup.E)).a(_snowman);
      jh.a(bup.hV, 6).a('#', bup.m).a("###").a("has_cobblestone", a((brw)bup.m)).a(_snowman);
      jh.a(bup.et, 6).a('#', bup.m).a("###").a("###").a("has_cobblestone", a((brw)bup.m)).a(_snowman);
      jh.a(bup.fu).a('#', bup.cz).a('X', bmd.ps).a('I', bup.b).a(" # ").a("#X#").a("III").a("has_quartz", a(bmd.ps)).a(_snowman);
      jh.a(bmd.mh).a('#', bmd.kh).a('X', bmd.lP).a(" # ").a("#X#").a(" # ").a("has_redstone", a(bmd.lP)).a(_snowman);
      jh.a(bmd.ne, 8).a('#', bmd.kW).a('X', bmd.ms).a("#X#").a("has_cocoa", a(bmd.ms)).a(_snowman);
      jh.a(bup.bV).a('#', aeg.c).a("##").a("##").a("has_planks", a(aeg.c)).a(_snowman);
      jh.a(bmd.qQ)
         .a('~', bmd.kS)
         .a('#', bmd.kP)
         .a('&', bmd.kh)
         .a('$', bup.el)
         .a("#&#")
         .a("~$~")
         .a(" # ")
         .a("has_string", a(bmd.kS))
         .a("has_stick", a(bmd.kP))
         .a("has_iron_ingot", a(bmd.kh))
         .a("has_tripwire_hook", a((brw)bup.el))
         .a(_snowman);
      jh.a(bup.lR).a('#', aeg.c).a('@', bmd.kS).a("@@").a("##").a("has_string", a(bmd.kS)).a(_snowman);
      jh.a(bup.hH)
         .a('#', bup.ia)
         .a("#")
         .a("#")
         .a("has_red_sandstone", a((brw)bup.hG))
         .a("has_chiseled_red_sandstone", a((brw)bup.hH))
         .a("has_cut_red_sandstone", a((brw)bup.hI))
         .a(_snowman);
      jh.a(bup.au).a('#', bup.hS).a("#").a("#").a("has_stone_slab", a((brw)bup.hS)).a(_snowman);
      ji.a(bmd.mD, 2).b(bmd.mF).b(bmd.mH).a("has_green_dye", a(bmd.mH)).a("has_blue_dye", a(bmd.mF)).a(_snowman);
      jh.a(bup.gs).a('S', bmd.pv).a('I', bmd.mJ).a("SSS").a("SIS").a("SSS").a("has_prismarine_shard", a(bmd.pv)).a(_snowman);
      jh.a(bup.gt, 4).a('#', bup.gq).a("#  ").a("## ").a("###").a("has_prismarine", a((brw)bup.gq)).a(_snowman);
      jh.a(bup.gu, 4).a('#', bup.gr).a("#  ").a("## ").a("###").a("has_prismarine_bricks", a((brw)bup.gr)).a(_snowman);
      jh.a(bup.gv, 4).a('#', bup.gs).a("#  ").a("## ").a("###").a("has_dark_prismarine", a((brw)bup.gs)).a(_snowman);
      jh.a(bup.fv).a('Q', bmd.ps).a('G', bup.ap).a('W', bon.a(aeg.j)).a("GGG").a("QQQ").a("WWW").a("has_quartz", a(bmd.ps)).a(_snowman);
      jh.a(bup.aO, 6).a('R', bmd.lP).a('#', bup.cq).a('X', bmd.kh).a("X X").a("X#X").a("XRX").a("has_rail", a((brw)bup.ch)).a(_snowman);
      ji.a(bmd.kg, 9).b(bup.bU).a("has_diamond_block", a((brw)bup.bU)).a(_snowman);
      jh.a(bmd.kI).a('#', bmd.kP).a('X', bmd.kg).a("XX").a("X#").a(" #").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bup.bU).a('#', bmd.kg).a("###").a("###").a("###").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.ln).a('X', bmd.kg).a("X X").a("X X").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.ll).a('X', bmd.kg).a("X X").a("XXX").a("XXX").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.lk).a('X', bmd.kg).a("XXX").a("X X").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.kJ).a('#', bmd.kP).a('X', bmd.kg).a("XX").a(" #").a(" #").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.lm).a('X', bmd.kg).a("XXX").a("X X").a("X X").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.kH).a('#', bmd.kP).a('X', bmd.kg).a("XXX").a(" # ").a(" # ").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.kG).a('#', bmd.kP).a('X', bmd.kg).a("X").a("#").a("#").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bmd.kF).a('#', bmd.kP).a('X', bmd.kg).a("X").a("X").a("#").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bup.e, 2).a('Q', bmd.ps).a('C', bup.m).a("CQ").a("QC").a("has_quartz", a(bmd.ps)).a(_snowman);
      jh.a(bup.as).a('R', bmd.lP).a('#', bup.m).a('X', bmd.kc).a("###").a("#X#").a("#R#").a("has_bow", a(bmd.kc)).a(_snowman);
      jh.a(bup.fE).a('R', bmd.lP).a('#', bup.m).a("###").a("# #").a("#R#").a("has_redstone", a(bmd.lP)).a(_snowman);
      ji.a(bmd.oV, 9).b(bup.en).a("has_emerald_block", a((brw)bup.en)).a(_snowman);
      jh.a(bup.en).a('#', bmd.oV).a("###").a("###").a("###").a("has_emerald", a(bmd.oV)).a(_snowman);
      jh.a(bup.dZ).a('B', bmd.mc).a('#', bup.bK).a('D', bmd.kg).a(" B ").a("D#D").a("###").a("has_obsidian", a((brw)bup.bK)).a(_snowman);
      jh.a(bup.ek).a('#', bup.bK).a('E', bmd.nD).a("###").a("#E#").a("###").a("has_ender_eye", a(bmd.nD)).a(_snowman);
      ji.a(bmd.nD).b(bmd.nq).b(bmd.nz).a("has_blaze_powder", a(bmd.nz)).a(_snowman);
      jh.a(bup.iC, 4).a('#', bup.ee).a("##").a("##").a("has_end_stone", a((brw)bup.ee)).a(_snowman);
      jh.a(bmd.qc).a('T', bmd.ns).a('E', bmd.nD).a('G', bup.ap).a("GGG").a("GEG").a("GTG").a("has_ender_eye", a(bmd.nD)).a(_snowman);
      jh.a(bup.iw, 4).a('#', bmd.qe).a('/', bmd.nr).a("/").a("#").a("has_chorus_fruit_popped", a(bmd.qe)).a(_snowman);
      ji.a(bmd.ny).b(bmd.nx).b(bup.bC).b(bmd.mM).a("has_spider_eye", a(bmd.nx)).a(_snowman);
      ji.a(bmd.oS, 3).b(bmd.kU).b(bmd.nz).a(bon.a(bmd.ke, bmd.kf)).a("has_blaze_powder", a(bmd.nz)).a(_snowman);
      jh.a(bmd.mi).a('#', bmd.kP).a('X', bmd.kS).a("  #").a(" #X").a("# X").a("has_string", a(bmd.kS)).a(_snowman);
      ji.a(bmd.ka).b(bmd.kh).b(bmd.lw).a("has_flint", a(bmd.lw)).a("has_obsidian", a((brw)bup.bK)).a(_snowman);
      jh.a(bup.ev).a('#', bmd.lY).a("# #").a(" # ").a("has_brick", a(bmd.lY)).a(_snowman);
      jh.a(bup.bY).a('#', aeg.ac).a("###").a("# #").a("###").a("has_cobblestone", a(aeg.ac)).a(_snowman);
      jh.a(bmd.mf).a('A', bup.bY).a('B', bmd.lN).a("A").a("B").a("has_minecart", a(bmd.lN)).a(_snowman);
      jh.a(bmd.nw, 3).a('#', bup.ap).a("# #").a(" # ").a("has_glass", a((brw)bup.ap)).a(_snowman);
      jh.a(bup.dJ, 16).a('#', bup.ap).a("###").a("###").a("has_glass", a((brw)bup.ap)).a(_snowman);
      jh.a(bup.cS).a('#', bmd.mk).a("##").a("##").a("has_glowstone_dust", a(bmd.mk)).a(_snowman);
      jh.a(bmd.lA).a('#', bmd.ki).a('X', bmd.kb).a("###").a("#X#").a("###").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.ky).a('#', bmd.kP).a('X', bmd.ki).a("XX").a("X#").a(" #").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.lr).a('X', bmd.ki).a("X X").a("X X").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.pd).a('#', bmd.nt).a('X', bmd.oY).a("###").a("#X#").a("###").a("has_gold_nugget", a(bmd.nt)).a(_snowman);
      jh.a(bmd.lp).a('X', bmd.ki).a("X X").a("XXX").a("XXX").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.lo).a('X', bmd.ki).a("XXX").a("X X").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.kz).a('#', bmd.kP).a('X', bmd.ki).a("XX").a(" #").a(" #").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.lq).a('X', bmd.ki).a("XXX").a("X X").a("X X").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.kx).a('#', bmd.kP).a('X', bmd.ki).a("XXX").a(" # ").a(" # ").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bup.aN, 6).a('R', bmd.lP).a('#', bmd.kP).a('X', bmd.ki).a("X X").a("X#X").a("XRX").a("has_rail", a((brw)bup.ch)).a(_snowman);
      jh.a(bmd.kw).a('#', bmd.kP).a('X', bmd.ki).a("X").a("#").a("#").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bmd.kv).a('#', bmd.kP).a('X', bmd.ki).a("X").a("X").a("#").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      jh.a(bup.bE).a('#', bmd.ki).a("###").a("###").a("###").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      ji.a(bmd.ki, 9).b(bup.bE).a("gold_ingot").a("has_gold_block", a((brw)bup.bE)).a(_snowman, "gold_ingot_from_gold_block");
      jh.a(bmd.ki).a('#', bmd.nt).a("###").a("###").a("###").b("gold_ingot").a("has_gold_nugget", a(bmd.nt)).a(_snowman, "gold_ingot_from_nuggets");
      ji.a(bmd.nt, 9).b(bmd.ki).a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      ji.a(bup.c).b(bup.e).b(bmd.ps).a("has_quartz", a(bmd.ps)).a(_snowman);
      ji.a(bmd.mB, 2).b(bmd.mJ).b(bmd.mu).a("has_white_dye", a(bmd.mu)).a("has_black_dye", a(bmd.mJ)).a(_snowman);
      jh.a(bup.gA).a('#', bmd.kW).a("###").a("###").a("###").a("has_wheat", a(bmd.kW)).a(_snowman);
      jh.a(bup.ft).a('#', bmd.kh).a("##").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      ji.a(bmd.rt, 4).b(bmd.ru).b(bmd.nw, 4).a("has_honey_block", a((brw)bup.ne)).a(_snowman);
      jh.a(bup.ne, 1).a('S', bmd.rt).a("SS").a("SS").a("has_honey_bottle", a(bmd.rt)).a(_snowman);
      jh.a(bup.nf).a('H', bmd.rq).a("HH").a("HH").a("has_honeycomb", a(bmd.rq)).a(_snowman);
      jh.a(bup.fy).a('C', bup.bR).a('I', bmd.kh).a("I I").a("ICI").a(" I ").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.pu).a('A', bup.fy).a('B', bmd.lN).a("A").a("B").a("has_minecart", a(bmd.lN)).a(_snowman);
      jh.a(bmd.kD).a('#', bmd.kP).a('X', bmd.kh).a("XX").a("X#").a(" #").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.dH, 16).a('#', bmd.kh).a("###").a("###").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.bF).a('#', bmd.kh).a("###").a("###").a("###").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.lj).a('X', bmd.kh).a("X X").a("X X").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.lh).a('X', bmd.kh).a("X X").a("XXX").a("XXX").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.cr, 3).a('#', bmd.kh).a("##").a("##").a("##").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.lg).a('X', bmd.kh).a("XXX").a("X X").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.kE).a('#', bmd.kP).a('X', bmd.kh).a("XX").a(" #").a(" #").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      ji.a(bmd.kh, 9).b(bup.bF).a("iron_ingot").a("has_iron_block", a((brw)bup.bF)).a(_snowman, "iron_ingot_from_iron_block");
      jh.a(bmd.kh).a('#', bmd.qw).a("###").a("###").a("###").b("iron_ingot").a("has_iron_nugget", a(bmd.qw)).a(_snowman, "iron_ingot_from_nuggets");
      jh.a(bmd.li).a('X', bmd.kh).a("XXX").a("X X").a("X X").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      ji.a(bmd.qw, 9).b(bmd.kh).a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.kC).a('#', bmd.kP).a('X', bmd.kh).a("XXX").a(" # ").a(" # ").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.kB).a('#', bmd.kP).a('X', bmd.kh).a("X").a("#").a("#").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.kA).a('#', bmd.kP).a('X', bmd.kh).a("X").a("X").a("#").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.gp).a('#', bmd.kh).a("##").a("##").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.oW).a('#', bmd.kP).a('X', bmd.lS).a("###").a("#X#").a("###").a("has_leather", a(bmd.lS)).a(_snowman);
      jh.a(bup.cI).a('#', aeg.c).a('X', bmd.kg).a("###").a("#X#").a("###").a("has_diamond", a(bmd.kg)).a(_snowman);
      jh.a(bup.cg, 3).a('#', bmd.kP).a("# #").a("###").a("# #").a("has_stick", a(bmd.kP)).a(_snowman);
      jh.a(bup.ar).a('#', bmd.mt).a("###").a("###").a("###").a("has_lapis", a(bmd.mt)).a(_snowman);
      ji.a(bmd.mt, 9).b(bup.ar).a("has_lapis_block", a((brw)bup.ar)).a(_snowman);
      jh.a(bmd.pH, 2).a('~', bmd.kS).a('O', bmd.md).a("~~ ").a("~O ").a("  ~").a("has_slime_ball", a(bmd.md)).a(_snowman);
      jh.a(bmd.lS).a('#', bmd.pB).a("##").a("##").a("has_rabbit_hide", a(bmd.pB)).a(_snowman);
      jh.a(bmd.lb).a('X', bmd.lS).a("X X").a("X X").a("has_leather", a(bmd.lS)).a(_snowman);
      jh.a(bmd.kZ).a('X', bmd.lS).a("X X").a("XXX").a("XXX").a("has_leather", a(bmd.lS)).a(_snowman);
      jh.a(bmd.kY).a('X', bmd.lS).a("XXX").a("X X").a("has_leather", a(bmd.lS)).a(_snowman);
      jh.a(bmd.la).a('X', bmd.lS).a("XXX").a("X X").a("X X").a("has_leather", a(bmd.lS)).a(_snowman);
      jh.a(bmd.pG).a('X', bmd.lS).a("X X").a("XXX").a("X X").a("has_leather", a(bmd.lS)).a(_snowman);
      jh.a(bup.lY).a('S', aeg.j).a('B', bup.bI).a("SSS").a(" B ").a(" S ").a("has_book", a(bmd.mc)).a(_snowman);
      jh.a(bup.cp).a('#', bup.m).a('X', bmd.kP).a("X").a("#").a("has_cobblestone", a((brw)bup.m)).a(_snowman);
      ji.a(bmd.mx).b(bup.br).a("light_blue_dye").a("has_red_flower", a((brw)bup.br)).a(_snowman, "light_blue_dye_from_blue_orchid");
      ji.a(bmd.mx, 2)
         .b(bmd.mF)
         .b(bmd.mu)
         .a("light_blue_dye")
         .a("has_blue_dye", a(bmd.mF))
         .a("has_white_dye", a(bmd.mu))
         .a(_snowman, "light_blue_dye_from_blue_white_dye");
      ji.a(bmd.mC).b(bup.bt).a("light_gray_dye").a("has_red_flower", a((brw)bup.bt)).a(_snowman, "light_gray_dye_from_azure_bluet");
      ji.a(bmd.mC, 2)
         .b(bmd.mB)
         .b(bmd.mu)
         .a("light_gray_dye")
         .a("has_gray_dye", a(bmd.mB))
         .a("has_white_dye", a(bmd.mu))
         .a(_snowman, "light_gray_dye_from_gray_white_dye");
      ji.a(bmd.mC, 3)
         .b(bmd.mJ)
         .b(bmd.mu, 2)
         .a("light_gray_dye")
         .a("has_white_dye", a(bmd.mu))
         .a("has_black_dye", a(bmd.mJ))
         .a(_snowman, "light_gray_dye_from_black_white_dye");
      ji.a(bmd.mC).b(bup.by).a("light_gray_dye").a("has_red_flower", a((brw)bup.by)).a(_snowman, "light_gray_dye_from_oxeye_daisy");
      ji.a(bmd.mC).b(bup.bw).a("light_gray_dye").a("has_red_flower", a((brw)bup.bw)).a(_snowman, "light_gray_dye_from_white_tulip");
      jh.a(bup.fs).a('#', bmd.ki).a("##").a("has_gold_ingot", a(bmd.ki)).a(_snowman);
      ji.a(bmd.mz, 2).b(bmd.mH).b(bmd.mu).a("has_green_dye", a(bmd.mH)).a("has_white_dye", a(bmd.mu)).a(_snowman);
      jh.a(bup.cV).a('A', bup.cU).a('B', bup.bL).a("A").a("B").a("has_carved_pumpkin", a((brw)bup.cU)).a(_snowman);
      ji.a(bmd.mw).b(bup.bs).a("magenta_dye").a("has_red_flower", a((brw)bup.bs)).a(_snowman, "magenta_dye_from_allium");
      ji.a(bmd.mw, 4)
         .b(bmd.mF)
         .b(bmd.mI, 2)
         .b(bmd.mu)
         .a("magenta_dye")
         .a("has_blue_dye", a(bmd.mF))
         .a("has_rose_red", a(bmd.mI))
         .a("has_white_dye", a(bmd.mu))
         .a(_snowman, "magenta_dye_from_blue_red_white_dye");
      ji.a(bmd.mw, 3)
         .b(bmd.mF)
         .b(bmd.mI)
         .b(bmd.mA)
         .a("magenta_dye")
         .a("has_pink_dye", a(bmd.mA))
         .a("has_blue_dye", a(bmd.mF))
         .a("has_red_dye", a(bmd.mI))
         .a(_snowman, "magenta_dye_from_blue_red_pink");
      ji.a(bmd.mw, 2).b(bup.gV).a("magenta_dye").a("has_double_plant", a((brw)bup.gV)).a(_snowman, "magenta_dye_from_lilac");
      ji.a(bmd.mw, 2).b(bmd.mE).b(bmd.mA).a("magenta_dye").a("has_pink_dye", a(bmd.mA)).a("has_purple_dye", a(bmd.mE)).a(_snowman, "magenta_dye_from_purple_and_pink");
      jh.a(bup.iJ).a('#', bmd.nA).a("##").a("##").a("has_magma_cream", a(bmd.nA)).a(_snowman);
      ji.a(bmd.nA).b(bmd.nz).b(bmd.md).a("has_blaze_powder", a(bmd.nz)).a(_snowman);
      jh.a(bmd.pc).a('#', bmd.mb).a('X', bmd.mh).a("###").a("#X#").a("###").a("has_compass", a(bmd.mh)).a(_snowman);
      jh.a(bup.dK).a('M', bmd.nh).a("MMM").a("MMM").a("MMM").a("has_melon", a(bmd.nh)).a(_snowman);
      ji.a(bmd.nk).b(bmd.nh).a("has_melon", a(bmd.nh)).a(_snowman);
      jh.a(bmd.lN).a('#', bmd.kh).a("# #").a("###").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      ji.a(bup.bJ).b(bup.m).b(bup.dP).a("has_vine", a((brw)bup.dP)).a(_snowman);
      jh.a(bup.eu, 6).a('#', bup.bJ).a("###").a("###").a("has_mossy_cobblestone", a((brw)bup.bJ)).a(_snowman);
      ji.a(bup.dv).b(bup.du).b(bup.dP).a("has_mossy_cobblestone", a((brw)bup.bJ)).a(_snowman);
      ji.a(bmd.kR)
         .b(bup.bC)
         .b(bup.bD)
         .b(bmd.kQ)
         .a("has_mushroom_stew", a(bmd.kR))
         .a("has_bowl", a(bmd.kQ))
         .a("has_brown_mushroom", a((brw)bup.bC))
         .a("has_red_mushroom", a((brw)bup.bD))
         .a(_snowman);
      jh.a(bup.dV).a('N', bmd.pr).a("NN").a("NN").a("has_netherbrick", a(bmd.pr)).a(_snowman);
      jh.a(bup.dW, 6).a('#', bup.dV).a('-', bmd.pr).a("#-#").a("#-#").a("has_nether_brick", a((brw)bup.dV)).a(_snowman);
      jh.a(bup.hY, 6).a('#', bup.dV).a("###").a("has_nether_brick", a((brw)bup.dV)).a(_snowman);
      jh.a(bup.dX, 4).a('#', bup.dV).a("#  ").a("## ").a("###").a("has_nether_brick", a((brw)bup.dV)).a(_snowman);
      jh.a(bup.iK).a('#', bmd.nu).a("###").a("###").a("###").a("has_nether_wart", a(bmd.nu)).a(_snowman);
      jh.a(bup.aw).a('#', aeg.c).a('X', bmd.lP).a("###").a("#X#").a("###").a("has_redstone", a(bmd.lP)).a(_snowman);
      jh.a(bup.iO).a('Q', bmd.ps).a('R', bmd.lP).a('#', bup.m).a("###").a("RRQ").a("###").a("has_quartz", a(bmd.ps)).a(_snowman);
      ji.a(bmd.mv).b(bup.bv).a("orange_dye").a("has_red_flower", a((brw)bup.bv)).a(_snowman, "orange_dye_from_orange_tulip");
      ji.a(bmd.mv, 2).b(bmd.mI).b(bmd.my).a("orange_dye").a("has_red_dye", a(bmd.mI)).a("has_yellow_dye", a(bmd.my)).a(_snowman, "orange_dye_from_red_yellow");
      jh.a(bmd.lz).a('#', bmd.kP).a('X', bon.a(aeg.b)).a("###").a("#X#").a("###").a("has_wool", a(aeg.b)).a(_snowman);
      jh.a(bmd.mb, 3).a('#', bup.cH).a("###").a("has_reeds", a((brw)bup.cH)).a(_snowman);
      jh.a(bup.fB, 2)
         .a('#', bup.fz)
         .a("#")
         .a("#")
         .a("has_chiseled_quartz_block", a((brw)bup.fA))
         .a("has_quartz_block", a((brw)bup.fz))
         .a("has_quartz_pillar", a((brw)bup.fB))
         .a(_snowman);
      ji.a(bup.gT).b(bup.cD, 9).a("has_ice", a((brw)bup.cD)).a(_snowman);
      ji.a(bmd.mA, 2).b(bup.gX).a("pink_dye").a("has_double_plant", a((brw)bup.gX)).a(_snowman, "pink_dye_from_peony");
      ji.a(bmd.mA).b(bup.bx).a("pink_dye").a("has_red_flower", a((brw)bup.bx)).a(_snowman, "pink_dye_from_pink_tulip");
      ji.a(bmd.mA, 2).b(bmd.mI).b(bmd.mu).a("pink_dye").a("has_white_dye", a(bmd.mu)).a("has_red_dye", a(bmd.mI)).a(_snowman, "pink_dye_from_red_white_dye");
      jh.a(bup.aW).a('R', bmd.lP).a('#', bup.m).a('T', aeg.c).a('X', bmd.kh).a("TTT").a("#X#").a("#R#").a("has_redstone", a(bmd.lP)).a(_snowman);
      jh.a(bup.cP, 4).a('S', bup.cO).a("SS").a("SS").a("has_basalt", a((brw)bup.cO)).a(_snowman);
      jh.a(bup.d, 4).a('S', bup.c).a("SS").a("SS").a("has_stone", a((brw)bup.c)).a(_snowman);
      jh.a(bup.f, 4).a('S', bup.e).a("SS").a("SS").a("has_stone", a((brw)bup.e)).a(_snowman);
      jh.a(bup.h, 4).a('S', bup.g).a("SS").a("SS").a("has_stone", a((brw)bup.g)).a(_snowman);
      jh.a(bup.gq).a('S', bmd.pv).a("SS").a("SS").a("has_prismarine_shard", a(bmd.pv)).a(_snowman);
      jh.a(bup.gr).a('S', bmd.pv).a("SSS").a("SSS").a("SSS").a("has_prismarine_shard", a(bmd.pv)).a(_snowman);
      jh.a(bup.gw, 6).a('#', bup.gq).a("###").a("has_prismarine", a((brw)bup.gq)).a(_snowman);
      jh.a(bup.gx, 6).a('#', bup.gr).a("###").a("has_prismarine_bricks", a((brw)bup.gr)).a(_snowman);
      jh.a(bup.gy, 6).a('#', bup.gs).a("###").a("has_dark_prismarine", a((brw)bup.gs)).a(_snowman);
      ji.a(bmd.pn).b(bup.cK).b(bmd.mM).b(bmd.mg).a("has_carved_pumpkin", a((brw)bup.cU)).a("has_pumpkin", a((brw)bup.cK)).a(_snowman);
      ji.a(bmd.nj, 4).b(bup.cK).a("has_pumpkin", a((brw)bup.cK)).a(_snowman);
      ji.a(bmd.mE, 2).b(bmd.mF).b(bmd.mI).a("has_blue_dye", a(bmd.mF)).a("has_red_dye", a(bmd.mI)).a(_snowman);
      jh.a(bup.iP).a('#', bup.bR).a('-', bmd.qv).a("-").a("#").a("-").a("has_shulker_shell", a(bmd.qv)).a(_snowman);
      jh.a(bup.iz, 4).a('F', bmd.qe).a("FF").a("FF").a("has_chorus_fruit_popped", a(bmd.qe)).a(_snowman);
      jh.a(bup.iA).a('#', bup.ic).a("#").a("#").a("has_purpur_block", a((brw)bup.iz)).a(_snowman);
      jh.a(bup.ic, 6).a('#', bon.a(bup.iz, bup.iA)).a("###").a("has_purpur_block", a((brw)bup.iz)).a(_snowman);
      jh.a(bup.iB, 4).a('#', bon.a(bup.iz, bup.iA)).a("#  ").a("## ").a("###").a("has_purpur_block", a((brw)bup.iz)).a(_snowman);
      jh.a(bup.fz).a('#', bmd.ps).a("##").a("##").a("has_quartz", a(bmd.ps)).a(_snowman);
      jh.a(bup.nI, 4).a('#', bup.fz).a("##").a("##").a("has_quartz_block", a((brw)bup.fz)).a(_snowman);
      jh.a(bup.hZ, 6)
         .a('#', bon.a(bup.fA, bup.fz, bup.fB))
         .a("###")
         .a("has_chiseled_quartz_block", a((brw)bup.fA))
         .a("has_quartz_block", a((brw)bup.fz))
         .a("has_quartz_pillar", a((brw)bup.fB))
         .a(_snowman);
      jh.a(bup.fC, 4)
         .a('#', bon.a(bup.fA, bup.fz, bup.fB))
         .a("#  ")
         .a("## ")
         .a("###")
         .a("has_chiseled_quartz_block", a((brw)bup.fA))
         .a("has_quartz_block", a((brw)bup.fz))
         .a("has_quartz_pillar", a((brw)bup.fB))
         .a(_snowman);
      ji.a(bmd.pz).b(bmd.pa).b(bmd.py).b(bmd.kQ).b(bmd.oY).b(bup.bC).a("rabbit_stew").a("has_cooked_rabbit", a(bmd.py)).a(_snowman, "rabbit_stew_from_brown_mushroom");
      ji.a(bmd.pz).b(bmd.pa).b(bmd.py).b(bmd.kQ).b(bmd.oY).b(bup.bD).a("rabbit_stew").a("has_cooked_rabbit", a(bmd.py)).a(_snowman, "rabbit_stew_from_red_mushroom");
      jh.a(bup.ch, 16).a('#', bmd.kP).a('X', bmd.kh).a("X X").a("X#X").a("X X").a("has_minecart", a(bmd.lN)).a(_snowman);
      ji.a(bmd.lP, 9).b(bup.fw).a("has_redstone_block", a((brw)bup.fw)).a(_snowman);
      jh.a(bup.fw).a('#', bmd.lP).a("###").a("###").a("###").a("has_redstone", a(bmd.lP)).a(_snowman);
      jh.a(bup.eg).a('R', bmd.lP).a('G', bup.cS).a(" R ").a("RGR").a(" R ").a("has_glowstone", a((brw)bup.cS)).a(_snowman);
      jh.a(bup.cz).a('#', bmd.kP).a('X', bmd.lP).a("X").a("#").a("has_redstone", a(bmd.lP)).a(_snowman);
      ji.a(bmd.mI).b(bmd.qf).a("red_dye").a("has_beetroot", a(bmd.qf)).a(_snowman, "red_dye_from_beetroot");
      ji.a(bmd.mI).b(bup.bq).a("red_dye").a("has_red_flower", a((brw)bup.bq)).a(_snowman, "red_dye_from_poppy");
      ji.a(bmd.mI, 2).b(bup.gW).a("red_dye").a("has_double_plant", a((brw)bup.gW)).a(_snowman, "red_dye_from_rose_bush");
      ji.a(bmd.mI).b(bup.bu).a("red_dye").a("has_red_flower", a((brw)bup.bu)).a(_snowman, "red_dye_from_tulip");
      jh.a(bup.iL).a('W', bmd.nu).a('N', bmd.pr).a("NW").a("WN").a("has_nether_wart", a(bmd.nu)).a(_snowman);
      jh.a(bup.hG).a('#', bup.D).a("##").a("##").a("has_sand", a((brw)bup.D)).a(_snowman);
      jh.a(bup.ia, 6).a('#', bon.a(bup.hG, bup.hH)).a("###").a("has_red_sandstone", a((brw)bup.hG)).a("has_chiseled_red_sandstone", a((brw)bup.hH)).a(_snowman);
      jh.a(bup.ib, 6).a('#', bup.hI).a("###").a("has_cut_red_sandstone", a((brw)bup.hI)).a(_snowman);
      jh.a(bup.hJ, 4)
         .a('#', bon.a(bup.hG, bup.hH, bup.hI))
         .a("#  ")
         .a("## ")
         .a("###")
         .a("has_red_sandstone", a((brw)bup.hG))
         .a("has_chiseled_red_sandstone", a((brw)bup.hH))
         .a("has_cut_red_sandstone", a((brw)bup.hI))
         .a(_snowman);
      jh.a(bup.cX).a('#', bup.cz).a('X', bmd.lP).a('I', bup.b).a("#X#").a("III").a("has_redstone_torch", a((brw)bup.cz)).a(_snowman);
      jh.a(bup.at).a('#', bup.C).a("##").a("##").a("has_sand", a((brw)bup.C)).a(_snowman);
      jh.a(bup.hS, 6).a('#', bon.a(bup.at, bup.au)).a("###").a("has_sandstone", a((brw)bup.at)).a("has_chiseled_sandstone", a((brw)bup.au)).a(_snowman);
      jh.a(bup.hT, 6).a('#', bup.av).a("###").a("has_cut_sandstone", a((brw)bup.av)).a(_snowman);
      jh.a(bup.ei, 4)
         .a('#', bon.a(bup.at, bup.au, bup.av))
         .a("#  ")
         .a("## ")
         .a("###")
         .a("has_sandstone", a((brw)bup.at))
         .a("has_chiseled_sandstone", a((brw)bup.au))
         .a("has_cut_sandstone", a((brw)bup.av))
         .a(_snowman);
      jh.a(bup.gz).a('S', bmd.pv).a('C', bmd.pw).a("SCS").a("CCC").a("SCS").a("has_prismarine_crystals", a(bmd.pw)).a(_snowman);
      jh.a(bmd.ng).a('#', bmd.kh).a(" #").a("# ").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bmd.qn).a('W', aeg.c).a('o', bmd.kh).a("WoW").a("WWW").a(" W ").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.gn).a('#', bmd.md).a("###").a("###").a("###").a("has_slime_ball", a(bmd.md)).a(_snowman);
      ji.a(bmd.md, 9).b(bup.gn).a("has_slime", a((brw)bup.gn)).a(_snowman);
      jh.a(bup.hI, 4).a('#', bup.hG).a("##").a("##").a("has_red_sandstone", a((brw)bup.hG)).a(_snowman);
      jh.a(bup.av, 4).a('#', bup.at).a("##").a("##").a("has_sandstone", a((brw)bup.at)).a(_snowman);
      jh.a(bup.cE).a('#', bmd.lQ).a("##").a("##").a("has_snowball", a(bmd.lQ)).a(_snowman);
      jh.a(bup.cC, 6).a('#', bup.cE).a("###").a("has_snowball", a(bmd.lQ)).a(_snowman);
      jh.a(bup.mf).a('L', aeg.q).a('S', bmd.kP).a('#', aeg.R).a(" S ").a("S#S").a("LLL").a("has_stick", a(bmd.kP)).a("has_soul_sand", a(aeg.R)).a(_snowman);
      jh.a(bmd.nE).a('#', bmd.nt).a('X', bmd.nh).a("###").a("#X#").a("###").a("has_melon", a(bmd.nh)).a(_snowman);
      jh.a(bmd.qk, 2).a('#', bmd.mk).a('X', bmd.kd).a(" # ").a("#X#").a(" # ").a("has_glowstone_dust", a(bmd.mk)).a(_snowman);
      jh.a(bmd.kP, 4).a('#', aeg.c).a("#").a("#").b("sticks").a("has_planks", a(aeg.c)).a(_snowman);
      jh.a(bmd.kP, 1).a('#', bup.kY).a("#").a("#").b("sticks").a("has_bamboo", a((brw)bup.kY)).a(_snowman, "stick_from_bamboo_item");
      jh.a(bup.aP).a('P', bup.aW).a('S', bmd.md).a("S").a("P").a("has_slime_ball", a(bmd.md)).a(_snowman);
      jh.a(bup.du, 4).a('#', bup.b).a("##").a("##").a("has_stone", a((brw)bup.b)).a(_snowman);
      jh.a(bmd.kt).a('#', bmd.kP).a('X', aeg.ab).a("XX").a("X#").a(" #").a("has_cobblestone", a(aeg.ab)).a(_snowman);
      jh.a(bup.hX, 6).a('#', bup.du).a("###").a("has_stone_bricks", a(aeg.d)).a(_snowman);
      jh.a(bup.dS, 4).a('#', bup.du).a("#  ").a("## ").a("###").a("has_stone_bricks", a(aeg.d)).a(_snowman);
      ji.a(bup.cB).b(bup.b).a("has_stone", a((brw)bup.b)).a(_snowman);
      jh.a(bmd.ku).a('#', bmd.kP).a('X', aeg.ab).a("XX").a(" #").a(" #").a("has_cobblestone", a(aeg.ab)).a(_snowman);
      jh.a(bmd.ks).a('#', bmd.kP).a('X', aeg.ab).a("XXX").a(" # ").a(" # ").a("has_cobblestone", a(aeg.ab)).a(_snowman);
      jh.a(bup.cq).a('#', bup.b).a("##").a("has_stone", a((brw)bup.b)).a(_snowman);
      jh.a(bmd.kr).a('#', bmd.kP).a('X', aeg.ab).a("X").a("#").a("#").a("has_cobblestone", a(aeg.ab)).a(_snowman);
      jh.a(bup.hQ, 6).a('#', bup.b).a("###").a("has_stone", a((brw)bup.b)).a(_snowman);
      jh.a(bup.hR, 6).a('#', bup.id).a("###").a("has_smooth_stone", a((brw)bup.id)).a(_snowman);
      jh.a(bup.ci, 4).a('#', bup.m).a("#  ").a("## ").a("###").a("has_cobblestone", a((brw)bup.m)).a(_snowman);
      jh.a(bmd.kq).a('#', bmd.kP).a('X', aeg.ab).a("X").a("X").a("#").a("has_cobblestone", a(aeg.ab)).a(_snowman);
      jh.a(bup.aY).a('#', bmd.kS).a("##").a("##").a("has_string", a(bmd.kS)).a(_snowman, "white_wool_from_string");
      ji.a(bmd.mM).b(bup.cH).a("sugar").a("has_reeds", a((brw)bup.cH)).a(_snowman, "sugar_from_sugar_cane");
      ji.a(bmd.mM, 3).b(bmd.rt).a("sugar").a("has_honey_bottle", a(bmd.rt)).a(_snowman, "sugar_from_honey_bottle");
      jh.a(bup.nb).a('H', bmd.fL).a('R', bmd.lP).a(" R ").a("RHR").a(" R ").a("has_redstone", a(bmd.lP)).a("has_hay_block", a((brw)bup.gA)).a(_snowman);
      jh.a(bup.bH).a('#', bon.a(bup.C, bup.D)).a('X', bmd.kU).a("X#X").a("#X#").a("X#X").a("has_gunpowder", a(bmd.kU)).a(_snowman);
      jh.a(bmd.pt).a('A', bup.bH).a('B', bmd.lN).a("A").a("B").a("has_minecart", a(bmd.lN)).a(_snowman);
      jh.a(bup.bL, 4).a('#', bmd.kP).a('X', bon.a(bmd.ke, bmd.kf)).a("X").a("#").a("has_stone_pickaxe", a(bmd.ks)).a(_snowman);
      jh.a(bup.cQ, 4).a('X', bon.a(bmd.ke, bmd.kf)).a('#', bmd.kP).a('S', aeg.R).a("X").a("#").a("S").a("has_soul_sand", a(aeg.R)).a(_snowman);
      jh.a(bup.mc).a('#', bmd.cp).a('X', bmd.qw).a("XXX").a("X#X").a("XXX").a("has_iron_nugget", a(bmd.qw)).a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.md).a('#', bmd.dp).a('X', bmd.qw).a("XXX").a("X#X").a("XXX").a("has_soul_torch", a(bmd.dp)).a(_snowman);
      ji.a(bup.fr).b(bup.bR).b(bup.el).a("has_tripwire_hook", a((brw)bup.el)).a(_snowman);
      jh.a(bup.el, 2).a('#', aeg.c).a('S', bmd.kP).a('I', bmd.kh).a("I").a("S").a("#").a("has_string", a(bmd.kS)).a(_snowman);
      jh.a(bmd.jY).a('X', bmd.jZ).a("XXX").a("X X").a("has_scute", a(bmd.jZ)).a(_snowman);
      ji.a(bmd.kW, 9).b(bup.gA).a("has_hay_block", a((brw)bup.gA)).a(_snowman);
      ji.a(bmd.mu).b(bmd.mK).a("white_dye").a("has_bone_meal", a(bmd.mK)).a(_snowman);
      ji.a(bmd.mu).b(bup.bB).a("white_dye").a("has_white_flower", a((brw)bup.bB)).a(_snowman, "white_dye_from_lily_of_the_valley");
      jh.a(bmd.ko).a('#', bmd.kP).a('X', aeg.c).a("XX").a("X#").a(" #").a("has_stick", a(bmd.kP)).a(_snowman);
      jh.a(bmd.kp).a('#', bmd.kP).a('X', aeg.c).a("XX").a(" #").a(" #").a("has_stick", a(bmd.kP)).a(_snowman);
      jh.a(bmd.kn).a('#', bmd.kP).a('X', aeg.c).a("XXX").a(" # ").a(" # ").a("has_stick", a(bmd.kP)).a(_snowman);
      jh.a(bmd.km).a('#', bmd.kP).a('X', aeg.c).a("X").a("#").a("#").a("has_stick", a(bmd.kP)).a(_snowman);
      jh.a(bmd.kl).a('#', bmd.kP).a('X', aeg.c).a("X").a("X").a("#").a("has_stick", a(bmd.kP)).a(_snowman);
      ji.a(bmd.oT).b(bmd.mc).b(bmd.mr).b(bmd.kT).a("has_book", a(bmd.mc)).a(_snowman);
      ji.a(bmd.my).b(bup.bp).a("yellow_dye").a("has_yellow_flower", a((brw)bup.bp)).a(_snowman, "yellow_dye_from_dandelion");
      ji.a(bmd.my, 2).b(bup.gU).a("yellow_dye").a("has_double_plant", a((brw)bup.gU)).a(_snowman, "yellow_dye_from_sunflower");
      ji.a(bmd.ni, 9).b(bup.ke).a("has_dried_kelp_block", a((brw)bup.ke)).a(_snowman);
      ji.a(bup.ke).b(bmd.ni, 9).a("has_dried_kelp", a(bmd.ni)).a(_snowman);
      jh.a(bup.kW).a('#', bmd.qO).a('X', bmd.qP).a("###").a("#X#").a("###").a("has_nautilus_core", a(bmd.qP)).a("has_nautilus_shell", a(bmd.qO)).a(_snowman);
      jh.a(bup.ld, 4).a('#', bup.d).a("#  ").a("## ").a("###").a("has_polished_granite", a((brw)bup.d)).a(_snowman);
      jh.a(bup.le, 4).a('#', bup.ig).a("#  ").a("## ").a("###").a("has_smooth_red_sandstone", a((brw)bup.ig)).a(_snowman);
      jh.a(bup.lf, 4).a('#', bup.dv).a("#  ").a("## ").a("###").a("has_mossy_stone_bricks", a((brw)bup.dv)).a(_snowman);
      jh.a(bup.lg, 4).a('#', bup.f).a("#  ").a("## ").a("###").a("has_polished_diorite", a((brw)bup.f)).a(_snowman);
      jh.a(bup.lh, 4).a('#', bup.bJ).a("#  ").a("## ").a("###").a("has_mossy_cobblestone", a((brw)bup.bJ)).a(_snowman);
      jh.a(bup.li, 4).a('#', bup.iC).a("#  ").a("## ").a("###").a("has_end_stone_bricks", a((brw)bup.iC)).a(_snowman);
      jh.a(bup.lj, 4).a('#', bup.b).a("#  ").a("## ").a("###").a("has_stone", a((brw)bup.b)).a(_snowman);
      jh.a(bup.lk, 4).a('#', bup.ie).a("#  ").a("## ").a("###").a("has_smooth_sandstone", a((brw)bup.ie)).a(_snowman);
      jh.a(bup.ll, 4).a('#', bup.if_).a("#  ").a("## ").a("###").a("has_smooth_quartz", a((brw)bup.if_)).a(_snowman);
      jh.a(bup.lm, 4).a('#', bup.c).a("#  ").a("## ").a("###").a("has_granite", a((brw)bup.c)).a(_snowman);
      jh.a(bup.ln, 4).a('#', bup.g).a("#  ").a("## ").a("###").a("has_andesite", a((brw)bup.g)).a(_snowman);
      jh.a(bup.lo, 4).a('#', bup.iL).a("#  ").a("## ").a("###").a("has_red_nether_bricks", a((brw)bup.iL)).a(_snowman);
      jh.a(bup.lp, 4).a('#', bup.h).a("#  ").a("## ").a("###").a("has_polished_andesite", a((brw)bup.h)).a(_snowman);
      jh.a(bup.lq, 4).a('#', bup.e).a("#  ").a("## ").a("###").a("has_diorite", a((brw)bup.e)).a(_snowman);
      jh.a(bup.lr, 6).a('#', bup.d).a("###").a("has_polished_granite", a((brw)bup.d)).a(_snowman);
      jh.a(bup.ls, 6).a('#', bup.ig).a("###").a("has_smooth_red_sandstone", a((brw)bup.ig)).a(_snowman);
      jh.a(bup.lt, 6).a('#', bup.dv).a("###").a("has_mossy_stone_bricks", a((brw)bup.dv)).a(_snowman);
      jh.a(bup.lu, 6).a('#', bup.f).a("###").a("has_polished_diorite", a((brw)bup.f)).a(_snowman);
      jh.a(bup.lv, 6).a('#', bup.bJ).a("###").a("has_mossy_cobblestone", a((brw)bup.bJ)).a(_snowman);
      jh.a(bup.lw, 6).a('#', bup.iC).a("###").a("has_end_stone_bricks", a((brw)bup.iC)).a(_snowman);
      jh.a(bup.lx, 6).a('#', bup.ie).a("###").a("has_smooth_sandstone", a((brw)bup.ie)).a(_snowman);
      jh.a(bup.ly, 6).a('#', bup.if_).a("###").a("has_smooth_quartz", a((brw)bup.if_)).a(_snowman);
      jh.a(bup.lz, 6).a('#', bup.c).a("###").a("has_granite", a((brw)bup.c)).a(_snowman);
      jh.a(bup.lA, 6).a('#', bup.g).a("###").a("has_andesite", a((brw)bup.g)).a(_snowman);
      jh.a(bup.lB, 6).a('#', bup.iL).a("###").a("has_red_nether_bricks", a((brw)bup.iL)).a(_snowman);
      jh.a(bup.lC, 6).a('#', bup.h).a("###").a("has_polished_andesite", a((brw)bup.h)).a(_snowman);
      jh.a(bup.lD, 6).a('#', bup.e).a("###").a("has_diorite", a((brw)bup.e)).a(_snowman);
      jh.a(bup.lE, 6).a('#', bup.bG).a("###").a("###").a("has_bricks", a((brw)bup.bG)).a(_snowman);
      jh.a(bup.lF, 6).a('#', bup.gq).a("###").a("###").a("has_prismarine", a((brw)bup.gq)).a(_snowman);
      jh.a(bup.lG, 6).a('#', bup.hG).a("###").a("###").a("has_red_sandstone", a((brw)bup.hG)).a(_snowman);
      jh.a(bup.lH, 6).a('#', bup.dv).a("###").a("###").a("has_mossy_stone_bricks", a((brw)bup.dv)).a(_snowman);
      jh.a(bup.lI, 6).a('#', bup.c).a("###").a("###").a("has_granite", a((brw)bup.c)).a(_snowman);
      jh.a(bup.lJ, 6).a('#', bup.du).a("###").a("###").a("has_stone_bricks", a((brw)bup.du)).a(_snowman);
      jh.a(bup.lK, 6).a('#', bup.dV).a("###").a("###").a("has_nether_bricks", a((brw)bup.dV)).a(_snowman);
      jh.a(bup.lL, 6).a('#', bup.g).a("###").a("###").a("has_andesite", a((brw)bup.g)).a(_snowman);
      jh.a(bup.lM, 6).a('#', bup.iL).a("###").a("###").a("has_red_nether_bricks", a((brw)bup.iL)).a(_snowman);
      jh.a(bup.lN, 6).a('#', bup.at).a("###").a("###").a("has_sandstone", a((brw)bup.at)).a(_snowman);
      jh.a(bup.lO, 6).a('#', bup.iC).a("###").a("###").a("has_end_stone_bricks", a((brw)bup.iC)).a(_snowman);
      jh.a(bup.lP, 6).a('#', bup.e).a("###").a("###").a("has_diorite", a((brw)bup.e)).a(_snowman);
      ji.a(bmd.qU).b(bmd.mb).b(bmd.pi).a("has_creeper_head", a(bmd.pi)).a(_snowman);
      ji.a(bmd.qV).b(bmd.mb).b(bmd.pf).a("has_wither_skeleton_skull", a(bmd.pf)).a(_snowman);
      ji.a(bmd.qT).b(bmd.mb).b(bup.by).a("has_oxeye_daisy", a((brw)bup.by)).a(_snowman);
      ji.a(bmd.qW).b(bmd.mb).b(bmd.lB).a("has_enchanted_golden_apple", a(bmd.lB)).a(_snowman);
      jh.a(bup.lQ, 6).a('~', bmd.kS).a('I', bup.kY).a("I~I").a("I I").a("I I").a("has_bamboo", a((brw)bup.kY)).a(_snowman);
      jh.a(bup.lX).a('I', bmd.kP).a('-', bup.hQ).a('#', aeg.c).a("I-I").a("# #").a("has_stone_slab", a((brw)bup.hQ)).a(_snowman);
      jh.a(bup.lU).a('#', bup.id).a('X', bup.bY).a('I', bmd.kh).a("III").a("IXI").a("###").a("has_smooth_stone", a((brw)bup.id)).a(_snowman);
      jh.a(bup.lT).a('#', aeg.q).a('X', bup.bY).a(" # ").a("#X#").a(" # ").a("has_furnace", a((brw)bup.bY)).a(_snowman);
      jh.a(bup.lV).a('#', aeg.c).a('@', bmd.mb).a("@@").a("##").a("##").a("has_paper", a(bmd.mb)).a(_snowman);
      jh.a(bup.lZ).a('#', aeg.c).a('@', bmd.kh).a("@@").a("##").a("##").a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jh.a(bup.lW).a('#', aeg.c).a('@', bmd.lw).a("@@").a("##").a("##").a("has_flint", a(bmd.lw)).a(_snowman);
      jh.a(bup.ma).a('I', bmd.kh).a('#', bup.b).a(" I ").a("###").a("has_stone", a((brw)bup.b)).a(_snowman);
      jh.a(bup.no).a('S', bmd.dJ).a('#', bmd.kj).a("SSS").a("S#S").a("SSS").a("has_netherite_ingot", a(bmd.kj)).a(_snowman);
      jh.a(bup.ng).a('#', bmd.kj).a("###").a("###").a("###").a("has_netherite_ingot", a(bmd.kj)).a(_snowman);
      ji.a(bmd.kj, 9).b(bup.ng).a("netherite_ingot").a("has_netherite_block", a((brw)bup.ng)).a(_snowman, "netherite_ingot_from_netherite_block");
      ji.a(bmd.kj).b(bmd.kk, 4).b(bmd.ki, 4).a("netherite_ingot").a("has_netherite_scrap", a(bmd.kk)).a(_snowman);
      jh.a(bup.nj).a('O', bup.ni).a('G', bup.cS).a("OOO").a("GGG").a("OOO").a("has_obsidian", a((brw)bup.ni)).a(_snowman);
      jh.a(bup.nq, 4).a('#', bup.np).a("#  ").a("## ").a("###").a("has_blackstone", a((brw)bup.np)).a(_snowman);
      jh.a(bup.nB, 4).a('#', bup.nt).a("#  ").a("## ").a("###").a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman);
      jh.a(bup.ny, 4).a('#', bup.nu).a("#  ").a("## ").a("###").a("has_polished_blackstone_bricks", a((brw)bup.nu)).a(_snowman);
      jh.a(bup.ns, 6).a('#', bup.np).a("###").a("has_blackstone", a((brw)bup.np)).a(_snowman);
      jh.a(bup.nC, 6).a('#', bup.nt).a("###").a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman);
      jh.a(bup.nx, 6).a('#', bup.nu).a("###").a("has_polished_blackstone_bricks", a((brw)bup.nu)).a(_snowman);
      jh.a(bup.nt, 4).a('S', bup.np).a("SS").a("SS").a("has_blackstone", a((brw)bup.np)).a(_snowman);
      jh.a(bup.nu, 4).a('#', bup.nt).a("##").a("##").a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman);
      jh.a(bup.nw).a('#', bup.nC).a("#").a("#").a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman);
      jh.a(bup.nr, 6).a('#', bup.np).a("###").a("###").a("has_blackstone", a((brw)bup.np)).a(_snowman);
      jh.a(bup.nF, 6).a('#', bup.nt).a("###").a("###").a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman);
      jh.a(bup.nz, 6).a('#', bup.nu).a("###").a("###").a("has_polished_blackstone_bricks", a((brw)bup.nu)).a(_snowman);
      ji.a(bup.nE).b(bup.nt).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman);
      jh.a(bup.nD).a('#', bup.nt).a("##").a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman);
      jh.a(bup.dI).a('I', bmd.kh).a('N', bmd.qw).a("N").a("I").a("N").a("has_iron_nugget", a(bmd.qw)).a("has_iron_ingot", a(bmd.kh)).a(_snowman);
      jl.a(bos.c).a(_snowman, "armor_dye");
      jl.a(bos.k).a(_snowman, "banner_duplicate");
      jl.a(bos.d).a(_snowman, "book_cloning");
      jl.a(bos.g).a(_snowman, "firework_rocket");
      jl.a(bos.h).a(_snowman, "firework_star");
      jl.a(bos.i).a(_snowman, "firework_star_fade");
      jl.a(bos.e).a(_snowman, "map_cloning");
      jl.a(bos.f).a(_snowman, "map_extending");
      jl.a(bos.o).a(_snowman, "repair_item");
      jl.a(bos.l).a(_snowman, "shield_decoration");
      jl.a(bos.m).a(_snowman, "shulker_box_coloring");
      jl.a(bos.j).a(_snowman, "tipped_arrow");
      jl.a(bos.n).a(_snowman, "suspicious_stew");
      jj.c(bon.a(bmd.oZ), bmd.pa, 0.35F, 200).a("has_potato", a(bmd.oZ)).a(_snowman);
      jj.c(bon.a(bmd.lZ), bmd.lY, 0.3F, 200).a("has_clay_ball", a(bmd.lZ)).a(_snowman);
      jj.c(bon.a(aeg.p), bmd.kf, 0.15F, 200).a("has_log", a(aeg.p)).a(_snowman);
      jj.c(bon.a(bmd.qd), bmd.qe, 0.1F, 200).a("has_chorus_fruit", a(bmd.qd)).a(_snowman);
      jj.c(bon.a(bup.H.h()), bmd.ke, 0.1F, 200).a("has_coal_ore", a((brw)bup.H)).a(_snowman, "coal_from_smelting");
      jj.c(bon.a(bmd.nl), bmd.nm, 0.35F, 200).a("has_beef", a(bmd.nl)).a(_snowman);
      jj.c(bon.a(bmd.nn), bmd.no, 0.35F, 200).a("has_chicken", a(bmd.nn)).a(_snowman);
      jj.c(bon.a(bmd.ml), bmd.mp, 0.35F, 200).a("has_cod", a(bmd.ml)).a(_snowman);
      jj.c(bon.a(bup.kc), bmd.ni, 0.1F, 200).a("has_kelp", a((brw)bup.kc)).a(_snowman, "dried_kelp_from_smelting");
      jj.c(bon.a(bmd.mm), bmd.mq, 0.35F, 200).a("has_salmon", a(bmd.mm)).a(_snowman);
      jj.c(bon.a(bmd.pK), bmd.pL, 0.35F, 200).a("has_mutton", a(bmd.pK)).a(_snowman);
      jj.c(bon.a(bmd.lx), bmd.ly, 0.35F, 200).a("has_porkchop", a(bmd.lx)).a(_snowman);
      jj.c(bon.a(bmd.px), bmd.py, 0.35F, 200).a("has_rabbit", a(bmd.px)).a(_snowman);
      jj.c(bon.a(bup.bT.h()), bmd.kg, 1.0F, 200).a("has_diamond_ore", a((brw)bup.bT)).a(_snowman, "diamond_from_smelting");
      jj.c(bon.a(bup.aq.h()), bmd.mt, 0.2F, 200).a("has_lapis_ore", a((brw)bup.aq)).a(_snowman, "lapis_from_smelting");
      jj.c(bon.a(bup.ej.h()), bmd.oV, 1.0F, 200).a("has_emerald_ore", a((brw)bup.ej)).a(_snowman, "emerald_from_smelting");
      jj.c(bon.a(aeg.A), bup.ap.h(), 0.1F, 200).a("has_sand", a(aeg.A)).a(_snowman);
      jj.c(bon.a(aeg.P), bmd.ki, 1.0F, 200).a("has_gold_ore", a(aeg.P)).a(_snowman);
      jj.c(bon.a(bup.kU.h()), bmd.mz, 0.1F, 200).a("has_sea_pickle", a((brw)bup.kU)).a(_snowman, "lime_dye_from_smelting");
      jj.c(bon.a(bup.cF.h()), bmd.mH, 1.0F, 200).a("has_cactus", a((brw)bup.cF)).a(_snowman);
      jj.c(bon.a(bmd.kx, bmd.kw, bmd.ky, bmd.kz, bmd.kv, bmd.lo, bmd.lp, bmd.lq, bmd.lr, bmd.pE), bmd.nt, 0.1F, 200)
         .a("has_golden_pickaxe", a(bmd.kx))
         .a("has_golden_shovel", a(bmd.kw))
         .a("has_golden_axe", a(bmd.ky))
         .a("has_golden_hoe", a(bmd.kz))
         .a("has_golden_sword", a(bmd.kv))
         .a("has_golden_helmet", a(bmd.lo))
         .a("has_golden_chestplate", a(bmd.lp))
         .a("has_golden_leggings", a(bmd.lq))
         .a("has_golden_boots", a(bmd.lr))
         .a("has_golden_horse_armor", a(bmd.pE))
         .a(_snowman, "gold_nugget_from_smelting");
      jj.c(bon.a(bmd.kC, bmd.kB, bmd.kD, bmd.kE, bmd.kA, bmd.lg, bmd.lh, bmd.li, bmd.lj, bmd.pD, bmd.lc, bmd.ld, bmd.le, bmd.lf), bmd.qw, 0.1F, 200)
         .a("has_iron_pickaxe", a(bmd.kC))
         .a("has_iron_shovel", a(bmd.kB))
         .a("has_iron_axe", a(bmd.kD))
         .a("has_iron_hoe", a(bmd.kE))
         .a("has_iron_sword", a(bmd.kA))
         .a("has_iron_helmet", a(bmd.lg))
         .a("has_iron_chestplate", a(bmd.lh))
         .a("has_iron_leggings", a(bmd.li))
         .a("has_iron_boots", a(bmd.lj))
         .a("has_iron_horse_armor", a(bmd.pD))
         .a("has_chainmail_helmet", a(bmd.lc))
         .a("has_chainmail_chestplate", a(bmd.ld))
         .a("has_chainmail_leggings", a(bmd.le))
         .a("has_chainmail_boots", a(bmd.lf))
         .a(_snowman, "iron_nugget_from_smelting");
      jj.c(bon.a(bup.G.h()), bmd.kh, 0.7F, 200).a("has_iron_ore", a(bup.G.h())).a(_snowman);
      jj.c(bon.a(bup.cG), bup.gR.h(), 0.35F, 200).a("has_clay_block", a((brw)bup.cG)).a(_snowman);
      jj.c(bon.a(bup.cL), bmd.pr, 0.1F, 200).a("has_netherrack", a((brw)bup.cL)).a(_snowman);
      jj.c(bon.a(bup.fx), bmd.ps, 0.2F, 200).a("has_nether_quartz_ore", a((brw)bup.fx)).a(_snowman);
      jj.c(bon.a(bup.cy), bmd.lP, 0.7F, 200).a("has_redstone_ore", a((brw)bup.cy)).a(_snowman, "redstone_from_smelting");
      jj.c(bon.a(bup.ao), bup.an.h(), 0.15F, 200).a("has_wet_sponge", a((brw)bup.ao)).a(_snowman);
      jj.c(bon.a(bup.m), bup.b.h(), 0.1F, 200).a("has_cobblestone", a((brw)bup.m)).a(_snowman);
      jj.c(bon.a(bup.b), bup.id.h(), 0.1F, 200).a("has_stone", a((brw)bup.b)).a(_snowman);
      jj.c(bon.a(bup.at), bup.ie.h(), 0.1F, 200).a("has_sandstone", a((brw)bup.at)).a(_snowman);
      jj.c(bon.a(bup.hG), bup.ig.h(), 0.1F, 200).a("has_red_sandstone", a((brw)bup.hG)).a(_snowman);
      jj.c(bon.a(bup.fz), bup.if_.h(), 0.1F, 200).a("has_quartz_block", a((brw)bup.fz)).a(_snowman);
      jj.c(bon.a(bup.du), bup.dw.h(), 0.1F, 200).a("has_stone_bricks", a((brw)bup.du)).a(_snowman);
      jj.c(bon.a(bup.fU), bup.jv.h(), 0.1F, 200).a("has_black_terracotta", a((brw)bup.fU)).a(_snowman);
      jj.c(bon.a(bup.fQ), bup.jr.h(), 0.1F, 200).a("has_blue_terracotta", a((brw)bup.fQ)).a(_snowman);
      jj.c(bon.a(bup.fR), bup.js.h(), 0.1F, 200).a("has_brown_terracotta", a((brw)bup.fR)).a(_snowman);
      jj.c(bon.a(bup.fO), bup.jp.h(), 0.1F, 200).a("has_cyan_terracotta", a((brw)bup.fO)).a(_snowman);
      jj.c(bon.a(bup.fM), bup.jn.h(), 0.1F, 200).a("has_gray_terracotta", a((brw)bup.fM)).a(_snowman);
      jj.c(bon.a(bup.fS), bup.jt.h(), 0.1F, 200).a("has_green_terracotta", a((brw)bup.fS)).a(_snowman);
      jj.c(bon.a(bup.fI), bup.jj.h(), 0.1F, 200).a("has_light_blue_terracotta", a((brw)bup.fI)).a(_snowman);
      jj.c(bon.a(bup.fN), bup.jo.h(), 0.1F, 200).a("has_light_gray_terracotta", a((brw)bup.fN)).a(_snowman);
      jj.c(bon.a(bup.fK), bup.jl.h(), 0.1F, 200).a("has_lime_terracotta", a((brw)bup.fK)).a(_snowman);
      jj.c(bon.a(bup.fH), bup.ji.h(), 0.1F, 200).a("has_magenta_terracotta", a((brw)bup.fH)).a(_snowman);
      jj.c(bon.a(bup.fG), bup.jh.h(), 0.1F, 200).a("has_orange_terracotta", a((brw)bup.fG)).a(_snowman);
      jj.c(bon.a(bup.fL), bup.jm.h(), 0.1F, 200).a("has_pink_terracotta", a((brw)bup.fL)).a(_snowman);
      jj.c(bon.a(bup.fP), bup.jq.h(), 0.1F, 200).a("has_purple_terracotta", a((brw)bup.fP)).a(_snowman);
      jj.c(bon.a(bup.fT), bup.ju.h(), 0.1F, 200).a("has_red_terracotta", a((brw)bup.fT)).a(_snowman);
      jj.c(bon.a(bup.fF), bup.jg.h(), 0.1F, 200).a("has_white_terracotta", a((brw)bup.fF)).a(_snowman);
      jj.c(bon.a(bup.fJ), bup.jk.h(), 0.1F, 200).a("has_yellow_terracotta", a((brw)bup.fJ)).a(_snowman);
      jj.c(bon.a(bup.nh), bmd.kk, 2.0F, 200).a("has_ancient_debris", a((brw)bup.nh)).a(_snowman);
      jj.c(bon.a(bup.nu), bup.nv.h(), 0.1F, 200).a("has_blackstone_bricks", a((brw)bup.nu)).a(_snowman);
      jj.c(bon.a(bup.dV), bup.nH.h(), 0.1F, 200).a("has_nether_bricks", a((brw)bup.dV)).a(_snowman);
      jj.b(bon.a(bup.G.h()), bmd.kh, 0.7F, 100).a("has_iron_ore", a(bup.G.h())).a(_snowman, "iron_ingot_from_blasting");
      jj.b(bon.a(aeg.P), bmd.ki, 1.0F, 100).a("has_gold_ore", a(aeg.P)).a(_snowman, "gold_ingot_from_blasting");
      jj.b(bon.a(bup.bT.h()), bmd.kg, 1.0F, 100).a("has_diamond_ore", a((brw)bup.bT)).a(_snowman, "diamond_from_blasting");
      jj.b(bon.a(bup.aq.h()), bmd.mt, 0.2F, 100).a("has_lapis_ore", a((brw)bup.aq)).a(_snowman, "lapis_from_blasting");
      jj.b(bon.a(bup.cy), bmd.lP, 0.7F, 100).a("has_redstone_ore", a((brw)bup.cy)).a(_snowman, "redstone_from_blasting");
      jj.b(bon.a(bup.H.h()), bmd.ke, 0.1F, 100).a("has_coal_ore", a((brw)bup.H)).a(_snowman, "coal_from_blasting");
      jj.b(bon.a(bup.ej.h()), bmd.oV, 1.0F, 100).a("has_emerald_ore", a((brw)bup.ej)).a(_snowman, "emerald_from_blasting");
      jj.b(bon.a(bup.fx), bmd.ps, 0.2F, 100).a("has_nether_quartz_ore", a((brw)bup.fx)).a(_snowman, "quartz_from_blasting");
      jj.b(bon.a(bmd.kx, bmd.kw, bmd.ky, bmd.kz, bmd.kv, bmd.lo, bmd.lp, bmd.lq, bmd.lr, bmd.pE), bmd.nt, 0.1F, 100)
         .a("has_golden_pickaxe", a(bmd.kx))
         .a("has_golden_shovel", a(bmd.kw))
         .a("has_golden_axe", a(bmd.ky))
         .a("has_golden_hoe", a(bmd.kz))
         .a("has_golden_sword", a(bmd.kv))
         .a("has_golden_helmet", a(bmd.lo))
         .a("has_golden_chestplate", a(bmd.lp))
         .a("has_golden_leggings", a(bmd.lq))
         .a("has_golden_boots", a(bmd.lr))
         .a("has_golden_horse_armor", a(bmd.pE))
         .a(_snowman, "gold_nugget_from_blasting");
      jj.b(bon.a(bmd.kC, bmd.kB, bmd.kD, bmd.kE, bmd.kA, bmd.lg, bmd.lh, bmd.li, bmd.lj, bmd.pD, bmd.lc, bmd.ld, bmd.le, bmd.lf), bmd.qw, 0.1F, 100)
         .a("has_iron_pickaxe", a(bmd.kC))
         .a("has_iron_shovel", a(bmd.kB))
         .a("has_iron_axe", a(bmd.kD))
         .a("has_iron_hoe", a(bmd.kE))
         .a("has_iron_sword", a(bmd.kA))
         .a("has_iron_helmet", a(bmd.lg))
         .a("has_iron_chestplate", a(bmd.lh))
         .a("has_iron_leggings", a(bmd.li))
         .a("has_iron_boots", a(bmd.lj))
         .a("has_iron_horse_armor", a(bmd.pD))
         .a("has_chainmail_helmet", a(bmd.lc))
         .a("has_chainmail_chestplate", a(bmd.ld))
         .a("has_chainmail_leggings", a(bmd.le))
         .a("has_chainmail_boots", a(bmd.lf))
         .a(_snowman, "iron_nugget_from_blasting");
      jj.b(bon.a(bup.nh), bmd.kk, 2.0F, 100).a("has_ancient_debris", a((brw)bup.nh)).a(_snowman, "netherite_scrap_from_blasting");
      a(_snowman, "smoking", bos.r, 100);
      a(_snowman, "campfire_cooking", bos.s, 600);
      jk.a(bon.a(bup.b), bup.hQ, 2).a("has_stone", a((brw)bup.b)).a(_snowman, "stone_slab_from_stone_stonecutting");
      jk.a(bon.a(bup.b), bup.lj).a("has_stone", a((brw)bup.b)).a(_snowman, "stone_stairs_from_stone_stonecutting");
      jk.a(bon.a(bup.b), bup.du).a("has_stone", a((brw)bup.b)).a(_snowman, "stone_bricks_from_stone_stonecutting");
      jk.a(bon.a(bup.b), bup.hX, 2).a("has_stone", a((brw)bup.b)).a(_snowman, "stone_brick_slab_from_stone_stonecutting");
      jk.a(bon.a(bup.b), bup.dS).a("has_stone", a((brw)bup.b)).a(_snowman, "stone_brick_stairs_from_stone_stonecutting");
      jk.a(bon.a(bup.b), bup.dx).a("has_stone", a((brw)bup.b)).a(_snowman, "chiseled_stone_bricks_stone_from_stonecutting");
      jk.a(bon.a(bup.b), bup.lJ).a("has_stone", a((brw)bup.b)).a(_snowman, "stone_brick_walls_from_stone_stonecutting");
      jk.a(bon.a(bup.at), bup.av).a("has_sandstone", a((brw)bup.at)).a(_snowman, "cut_sandstone_from_sandstone_stonecutting");
      jk.a(bon.a(bup.at), bup.hS, 2).a("has_sandstone", a((brw)bup.at)).a(_snowman, "sandstone_slab_from_sandstone_stonecutting");
      jk.a(bon.a(bup.at), bup.hT, 2).a("has_sandstone", a((brw)bup.at)).a(_snowman, "cut_sandstone_slab_from_sandstone_stonecutting");
      jk.a(bon.a(bup.av), bup.hT, 2).a("has_cut_sandstone", a((brw)bup.at)).a(_snowman, "cut_sandstone_slab_from_cut_sandstone_stonecutting");
      jk.a(bon.a(bup.at), bup.ei).a("has_sandstone", a((brw)bup.at)).a(_snowman, "sandstone_stairs_from_sandstone_stonecutting");
      jk.a(bon.a(bup.at), bup.lN).a("has_sandstone", a((brw)bup.at)).a(_snowman, "sandstone_wall_from_sandstone_stonecutting");
      jk.a(bon.a(bup.at), bup.au).a("has_sandstone", a((brw)bup.at)).a(_snowman, "chiseled_sandstone_from_sandstone_stonecutting");
      jk.a(bon.a(bup.hG), bup.hI).a("has_red_sandstone", a((brw)bup.hG)).a(_snowman, "cut_red_sandstone_from_red_sandstone_stonecutting");
      jk.a(bon.a(bup.hG), bup.ia, 2).a("has_red_sandstone", a((brw)bup.hG)).a(_snowman, "red_sandstone_slab_from_red_sandstone_stonecutting");
      jk.a(bon.a(bup.hG), bup.ib, 2).a("has_red_sandstone", a((brw)bup.hG)).a(_snowman, "cut_red_sandstone_slab_from_red_sandstone_stonecutting");
      jk.a(bon.a(bup.hI), bup.ib, 2).a("has_cut_red_sandstone", a((brw)bup.hG)).a(_snowman, "cut_red_sandstone_slab_from_cut_red_sandstone_stonecutting");
      jk.a(bon.a(bup.hG), bup.hJ).a("has_red_sandstone", a((brw)bup.hG)).a(_snowman, "red_sandstone_stairs_from_red_sandstone_stonecutting");
      jk.a(bon.a(bup.hG), bup.lG).a("has_red_sandstone", a((brw)bup.hG)).a(_snowman, "red_sandstone_wall_from_red_sandstone_stonecutting");
      jk.a(bon.a(bup.hG), bup.hH).a("has_red_sandstone", a((brw)bup.hG)).a(_snowman, "chiseled_red_sandstone_from_red_sandstone_stonecutting");
      jk.a(bon.a(bup.fz), bup.hZ, 2).a("has_quartz_block", a((brw)bup.fz)).a(_snowman, "quartz_slab_from_stonecutting");
      jk.a(bon.a(bup.fz), bup.fC).a("has_quartz_block", a((brw)bup.fz)).a(_snowman, "quartz_stairs_from_quartz_block_stonecutting");
      jk.a(bon.a(bup.fz), bup.fB).a("has_quartz_block", a((brw)bup.fz)).a(_snowman, "quartz_pillar_from_quartz_block_stonecutting");
      jk.a(bon.a(bup.fz), bup.fA).a("has_quartz_block", a((brw)bup.fz)).a(_snowman, "chiseled_quartz_block_from_quartz_block_stonecutting");
      jk.a(bon.a(bup.fz), bup.nI).a("has_quartz_block", a((brw)bup.fz)).a(_snowman, "quartz_bricks_from_quartz_block_stonecutting");
      jk.a(bon.a(bup.m), bup.ci).a("has_cobblestone", a((brw)bup.m)).a(_snowman, "cobblestone_stairs_from_cobblestone_stonecutting");
      jk.a(bon.a(bup.m), bup.hV, 2).a("has_cobblestone", a((brw)bup.m)).a(_snowman, "cobblestone_slab_from_cobblestone_stonecutting");
      jk.a(bon.a(bup.m), bup.et).a("has_cobblestone", a((brw)bup.m)).a(_snowman, "cobblestone_wall_from_cobblestone_stonecutting");
      jk.a(bon.a(bup.du), bup.hX, 2).a("has_stone_bricks", a((brw)bup.du)).a(_snowman, "stone_brick_slab_from_stone_bricks_stonecutting");
      jk.a(bon.a(bup.du), bup.dS).a("has_stone_bricks", a((brw)bup.du)).a(_snowman, "stone_brick_stairs_from_stone_bricks_stonecutting");
      jk.a(bon.a(bup.du), bup.lJ).a("has_stone_bricks", a((brw)bup.du)).a(_snowman, "stone_brick_wall_from_stone_bricks_stonecutting");
      jk.a(bon.a(bup.du), bup.dx).a("has_stone_bricks", a((brw)bup.du)).a(_snowman, "chiseled_stone_bricks_from_stone_bricks_stonecutting");
      jk.a(bon.a(bup.bG), bup.hW, 2).a("has_bricks", a((brw)bup.bG)).a(_snowman, "brick_slab_from_bricks_stonecutting");
      jk.a(bon.a(bup.bG), bup.dR).a("has_bricks", a((brw)bup.bG)).a(_snowman, "brick_stairs_from_bricks_stonecutting");
      jk.a(bon.a(bup.bG), bup.lE).a("has_bricks", a((brw)bup.bG)).a(_snowman, "brick_wall_from_bricks_stonecutting");
      jk.a(bon.a(bup.dV), bup.hY, 2).a("has_nether_bricks", a((brw)bup.dV)).a(_snowman, "nether_brick_slab_from_nether_bricks_stonecutting");
      jk.a(bon.a(bup.dV), bup.dX).a("has_nether_bricks", a((brw)bup.dV)).a(_snowman, "nether_brick_stairs_from_nether_bricks_stonecutting");
      jk.a(bon.a(bup.dV), bup.lK).a("has_nether_bricks", a((brw)bup.dV)).a(_snowman, "nether_brick_wall_from_nether_bricks_stonecutting");
      jk.a(bon.a(bup.dV), bup.nG).a("has_nether_bricks", a((brw)bup.dV)).a(_snowman, "chiseled_nether_bricks_from_nether_bricks_stonecutting");
      jk.a(bon.a(bup.iL), bup.lB, 2).a("has_nether_bricks", a((brw)bup.iL)).a(_snowman, "red_nether_brick_slab_from_red_nether_bricks_stonecutting");
      jk.a(bon.a(bup.iL), bup.lo).a("has_nether_bricks", a((brw)bup.iL)).a(_snowman, "red_nether_brick_stairs_from_red_nether_bricks_stonecutting");
      jk.a(bon.a(bup.iL), bup.lM).a("has_nether_bricks", a((brw)bup.iL)).a(_snowman, "red_nether_brick_wall_from_red_nether_bricks_stonecutting");
      jk.a(bon.a(bup.iz), bup.ic, 2).a("has_purpur_block", a((brw)bup.iz)).a(_snowman, "purpur_slab_from_purpur_block_stonecutting");
      jk.a(bon.a(bup.iz), bup.iB).a("has_purpur_block", a((brw)bup.iz)).a(_snowman, "purpur_stairs_from_purpur_block_stonecutting");
      jk.a(bon.a(bup.iz), bup.iA).a("has_purpur_block", a((brw)bup.iz)).a(_snowman, "purpur_pillar_from_purpur_block_stonecutting");
      jk.a(bon.a(bup.gq), bup.gw, 2).a("has_prismarine", a((brw)bup.gq)).a(_snowman, "prismarine_slab_from_prismarine_stonecutting");
      jk.a(bon.a(bup.gq), bup.gt).a("has_prismarine", a((brw)bup.gq)).a(_snowman, "prismarine_stairs_from_prismarine_stonecutting");
      jk.a(bon.a(bup.gq), bup.lF).a("has_prismarine", a((brw)bup.gq)).a(_snowman, "prismarine_wall_from_prismarine_stonecutting");
      jk.a(bon.a(bup.gr), bup.gx, 2).a("has_prismarine_brick", a((brw)bup.gr)).a(_snowman, "prismarine_brick_slab_from_prismarine_stonecutting");
      jk.a(bon.a(bup.gr), bup.gu).a("has_prismarine_brick", a((brw)bup.gr)).a(_snowman, "prismarine_brick_stairs_from_prismarine_stonecutting");
      jk.a(bon.a(bup.gs), bup.gy, 2).a("has_dark_prismarine", a((brw)bup.gs)).a(_snowman, "dark_prismarine_slab_from_dark_prismarine_stonecutting");
      jk.a(bon.a(bup.gs), bup.gv).a("has_dark_prismarine", a((brw)bup.gs)).a(_snowman, "dark_prismarine_stairs_from_dark_prismarine_stonecutting");
      jk.a(bon.a(bup.g), bup.lA, 2).a("has_andesite", a((brw)bup.g)).a(_snowman, "andesite_slab_from_andesite_stonecutting");
      jk.a(bon.a(bup.g), bup.ln).a("has_andesite", a((brw)bup.g)).a(_snowman, "andesite_stairs_from_andesite_stonecutting");
      jk.a(bon.a(bup.g), bup.lL).a("has_andesite", a((brw)bup.g)).a(_snowman, "andesite_wall_from_andesite_stonecutting");
      jk.a(bon.a(bup.g), bup.h).a("has_andesite", a((brw)bup.g)).a(_snowman, "polished_andesite_from_andesite_stonecutting");
      jk.a(bon.a(bup.g), bup.lC, 2).a("has_andesite", a((brw)bup.g)).a(_snowman, "polished_andesite_slab_from_andesite_stonecutting");
      jk.a(bon.a(bup.g), bup.lp).a("has_andesite", a((brw)bup.g)).a(_snowman, "polished_andesite_stairs_from_andesite_stonecutting");
      jk.a(bon.a(bup.h), bup.lC, 2).a("has_polished_andesite", a((brw)bup.h)).a(_snowman, "polished_andesite_slab_from_polished_andesite_stonecutting");
      jk.a(bon.a(bup.h), bup.lp).a("has_polished_andesite", a((brw)bup.h)).a(_snowman, "polished_andesite_stairs_from_polished_andesite_stonecutting");
      jk.a(bon.a(bup.cO), bup.cP).a("has_basalt", a((brw)bup.cO)).a(_snowman, "polished_basalt_from_basalt_stonecutting");
      jk.a(bon.a(bup.c), bup.lz, 2).a("has_granite", a((brw)bup.c)).a(_snowman, "granite_slab_from_granite_stonecutting");
      jk.a(bon.a(bup.c), bup.lm).a("has_granite", a((brw)bup.c)).a(_snowman, "granite_stairs_from_granite_stonecutting");
      jk.a(bon.a(bup.c), bup.lI).a("has_granite", a((brw)bup.c)).a(_snowman, "granite_wall_from_granite_stonecutting");
      jk.a(bon.a(bup.c), bup.d).a("has_granite", a((brw)bup.c)).a(_snowman, "polished_granite_from_granite_stonecutting");
      jk.a(bon.a(bup.c), bup.lr, 2).a("has_granite", a((brw)bup.c)).a(_snowman, "polished_granite_slab_from_granite_stonecutting");
      jk.a(bon.a(bup.c), bup.ld).a("has_granite", a((brw)bup.c)).a(_snowman, "polished_granite_stairs_from_granite_stonecutting");
      jk.a(bon.a(bup.d), bup.lr, 2).a("has_polished_granite", a((brw)bup.d)).a(_snowman, "polished_granite_slab_from_polished_granite_stonecutting");
      jk.a(bon.a(bup.d), bup.ld).a("has_polished_granite", a((brw)bup.d)).a(_snowman, "polished_granite_stairs_from_polished_granite_stonecutting");
      jk.a(bon.a(bup.e), bup.lD, 2).a("has_diorite", a((brw)bup.e)).a(_snowman, "diorite_slab_from_diorite_stonecutting");
      jk.a(bon.a(bup.e), bup.lq).a("has_diorite", a((brw)bup.e)).a(_snowman, "diorite_stairs_from_diorite_stonecutting");
      jk.a(bon.a(bup.e), bup.lP).a("has_diorite", a((brw)bup.e)).a(_snowman, "diorite_wall_from_diorite_stonecutting");
      jk.a(bon.a(bup.e), bup.f).a("has_diorite", a((brw)bup.e)).a(_snowman, "polished_diorite_from_diorite_stonecutting");
      jk.a(bon.a(bup.e), bup.lu, 2).a("has_diorite", a((brw)bup.f)).a(_snowman, "polished_diorite_slab_from_diorite_stonecutting");
      jk.a(bon.a(bup.e), bup.lg).a("has_diorite", a((brw)bup.f)).a(_snowman, "polished_diorite_stairs_from_diorite_stonecutting");
      jk.a(bon.a(bup.f), bup.lu, 2).a("has_polished_diorite", a((brw)bup.f)).a(_snowman, "polished_diorite_slab_from_polished_diorite_stonecutting");
      jk.a(bon.a(bup.f), bup.lg).a("has_polished_diorite", a((brw)bup.f)).a(_snowman, "polished_diorite_stairs_from_polished_diorite_stonecutting");
      jk.a(bon.a(bup.dv), bup.lt, 2).a("has_mossy_stone_bricks", a((brw)bup.dv)).a(_snowman, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
      jk.a(bon.a(bup.dv), bup.lf).a("has_mossy_stone_bricks", a((brw)bup.dv)).a(_snowman, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
      jk.a(bon.a(bup.dv), bup.lH).a("has_mossy_stone_bricks", a((brw)bup.dv)).a(_snowman, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
      jk.a(bon.a(bup.bJ), bup.lv, 2).a("has_mossy_cobblestone", a((brw)bup.bJ)).a(_snowman, "mossy_cobblestone_slab_from_mossy_cobblestone_stonecutting");
      jk.a(bon.a(bup.bJ), bup.lh).a("has_mossy_cobblestone", a((brw)bup.bJ)).a(_snowman, "mossy_cobblestone_stairs_from_mossy_cobblestone_stonecutting");
      jk.a(bon.a(bup.bJ), bup.eu).a("has_mossy_cobblestone", a((brw)bup.bJ)).a(_snowman, "mossy_cobblestone_wall_from_mossy_cobblestone_stonecutting");
      jk.a(bon.a(bup.ie), bup.lx, 2).a("has_smooth_sandstone", a((brw)bup.ie)).a(_snowman, "smooth_sandstone_slab_from_smooth_sandstone_stonecutting");
      jk.a(bon.a(bup.ie), bup.lk).a("has_mossy_cobblestone", a((brw)bup.ie)).a(_snowman, "smooth_sandstone_stairs_from_smooth_sandstone_stonecutting");
      jk.a(bon.a(bup.ig), bup.ls, 2).a("has_smooth_red_sandstone", a((brw)bup.ig)).a(_snowman, "smooth_red_sandstone_slab_from_smooth_red_sandstone_stonecutting");
      jk.a(bon.a(bup.ig), bup.le).a("has_smooth_red_sandstone", a((brw)bup.ig)).a(_snowman, "smooth_red_sandstone_stairs_from_smooth_red_sandstone_stonecutting");
      jk.a(bon.a(bup.if_), bup.ly, 2).a("has_smooth_quartz", a((brw)bup.if_)).a(_snowman, "smooth_quartz_slab_from_smooth_quartz_stonecutting");
      jk.a(bon.a(bup.if_), bup.ll).a("has_smooth_quartz", a((brw)bup.if_)).a(_snowman, "smooth_quartz_stairs_from_smooth_quartz_stonecutting");
      jk.a(bon.a(bup.iC), bup.lw, 2).a("has_end_stone_brick", a((brw)bup.iC)).a(_snowman, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
      jk.a(bon.a(bup.iC), bup.li).a("has_end_stone_brick", a((brw)bup.iC)).a(_snowman, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
      jk.a(bon.a(bup.iC), bup.lO).a("has_end_stone_brick", a((brw)bup.iC)).a(_snowman, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
      jk.a(bon.a(bup.ee), bup.iC).a("has_end_stone", a((brw)bup.ee)).a(_snowman, "end_stone_bricks_from_end_stone_stonecutting");
      jk.a(bon.a(bup.ee), bup.lw, 2).a("has_end_stone", a((brw)bup.ee)).a(_snowman, "end_stone_brick_slab_from_end_stone_stonecutting");
      jk.a(bon.a(bup.ee), bup.li).a("has_end_stone", a((brw)bup.ee)).a(_snowman, "end_stone_brick_stairs_from_end_stone_stonecutting");
      jk.a(bon.a(bup.ee), bup.lO).a("has_end_stone", a((brw)bup.ee)).a(_snowman, "end_stone_brick_wall_from_end_stone_stonecutting");
      jk.a(bon.a(bup.id), bup.hR, 2).a("has_smooth_stone", a((brw)bup.id)).a(_snowman, "smooth_stone_slab_from_smooth_stone_stonecutting");
      jk.a(bon.a(bup.np), bup.ns, 2).a("has_blackstone", a((brw)bup.np)).a(_snowman, "blackstone_slab_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nq).a("has_blackstone", a((brw)bup.np)).a(_snowman, "blackstone_stairs_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nr).a("has_blackstone", a((brw)bup.np)).a(_snowman, "blackstone_wall_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nt).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nF).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_wall_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nC, 2).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_slab_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nB).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_stairs_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nw).a("has_blackstone", a((brw)bup.np)).a(_snowman, "chiseled_polished_blackstone_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nu).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_bricks_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nx, 2).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_brick_slab_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.ny).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_brick_stairs_from_blackstone_stonecutting");
      jk.a(bon.a(bup.np), bup.nz).a("has_blackstone", a((brw)bup.np)).a(_snowman, "polished_blackstone_brick_wall_from_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.nC, 2).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "polished_blackstone_slab_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.nB).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "polished_blackstone_stairs_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.nu).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "polished_blackstone_bricks_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.nF).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "polished_blackstone_wall_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.nx, 2).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "polished_blackstone_brick_slab_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.ny).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "polished_blackstone_brick_stairs_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.nz).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "polished_blackstone_brick_wall_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nt), bup.nw).a("has_polished_blackstone", a((brw)bup.nt)).a(_snowman, "chiseled_polished_blackstone_from_polished_blackstone_stonecutting");
      jk.a(bon.a(bup.nu), bup.nx, 2)
         .a("has_polished_blackstone_bricks", a((brw)bup.nu))
         .a(_snowman, "polished_blackstone_brick_slab_from_polished_blackstone_bricks_stonecutting");
      jk.a(bon.a(bup.nu), bup.ny)
         .a("has_polished_blackstone_bricks", a((brw)bup.nu))
         .a(_snowman, "polished_blackstone_brick_stairs_from_polished_blackstone_bricks_stonecutting");
      jk.a(bon.a(bup.nu), bup.nz)
         .a("has_polished_blackstone_bricks", a((brw)bup.nu))
         .a(_snowman, "polished_blackstone_brick_wall_from_polished_blackstone_bricks_stonecutting");
      a(_snowman, bmd.ll, bmd.lt);
      a(_snowman, bmd.lm, bmd.lu);
      a(_snowman, bmd.lk, bmd.ls);
      a(_snowman, bmd.ln, bmd.lv);
      a(_snowman, bmd.kF, bmd.kK);
      a(_snowman, bmd.kI, bmd.kN);
      a(_snowman, bmd.kH, bmd.kM);
      a(_snowman, bmd.kJ, bmd.kO);
      a(_snowman, bmd.kG, bmd.kL);
   }

   private static void a(Consumer<jf> var0, blx var1, blx var2) {
      jm.a(bon.a(_snowman), bon.a(bmd.kj), _snowman).a("has_netherite_ingot", a(bmd.kj)).a(_snowman, gm.T.b(_snowman.h()).a() + "_smithing");
   }

   private static void a(Consumer<jf> var0, brw var1, ael<blx> var2) {
      ji.a(_snowman, 4).a(_snowman).a("planks").a("has_log", a(_snowman)).a(_snowman);
   }

   private static void b(Consumer<jf> var0, brw var1, ael<blx> var2) {
      ji.a(_snowman, 4).a(_snowman).a("planks").a("has_logs", a(_snowman)).a(_snowman);
   }

   private static void a(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 3).a('#', _snowman).a("##").a("##").b("bark").a("has_log", a(_snowman)).a(_snowman);
   }

   private static void b(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman).a('#', _snowman).a("# #").a("###").b("boat").a("in_water", a(bup.A)).a(_snowman);
   }

   private static void c(Consumer<jf> var0, brw var1, brw var2) {
      ji.a(_snowman).b(_snowman).a("wooden_button").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void d(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 3).a('#', _snowman).a("##").a("##").a("##").b("wooden_door").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void e(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 3).a('#', bmd.kP).a('W', _snowman).a("W#W").a("W#W").b("wooden_fence").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void f(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman).a('#', bmd.kP).a('W', _snowman).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void g(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman).a('#', _snowman).a("##").b("wooden_pressure_plate").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void h(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 6).a('#', _snowman).a("###").b("wooden_slab").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void i(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 4).a('#', _snowman).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void j(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 2).a('#', _snowman).a("###").a("###").b("wooden_trapdoor").a("has_planks", a(_snowman)).a(_snowman);
   }

   private static void k(Consumer<jf> var0, brw var1, brw var2) {
      String _snowman = gm.T.b(_snowman.h()).a();
      jh.a(_snowman, 3).b("sign").a('#', _snowman).a('X', bmd.kP).a("###").a("###").a(" X ").a("has_" + _snowman, a(_snowman)).a(_snowman);
   }

   private static void l(Consumer<jf> var0, brw var1, brw var2) {
      ji.a(_snowman).b(_snowman).b(bup.aY).a("wool").a("has_white_wool", a((brw)bup.aY)).a(_snowman);
   }

   private static void m(Consumer<jf> var0, brw var1, brw var2) {
      String _snowman = gm.T.b(_snowman.h()).a();
      jh.a(_snowman, 3).a('#', _snowman).a("##").b("carpet").a("has_" + _snowman, a(_snowman)).a(_snowman);
   }

   private static void n(Consumer<jf> var0, brw var1, brw var2) {
      String _snowman = gm.T.b(_snowman.h()).a();
      String _snowmanx = gm.T.b(_snowman.h()).a();
      jh.a(_snowman, 8)
         .a('#', bup.gB)
         .a('$', _snowman)
         .a("###")
         .a("#$#")
         .a("###")
         .b("carpet")
         .a("has_white_carpet", a((brw)bup.gB))
         .a("has_" + _snowmanx, a(_snowman))
         .a(_snowman, _snowman + "_from_white_carpet");
   }

   private static void o(Consumer<jf> var0, brw var1, brw var2) {
      String _snowman = gm.T.b(_snowman.h()).a();
      jh.a(_snowman).a('#', _snowman).a('X', aeg.c).a("###").a("XXX").b("bed").a("has_" + _snowman, a(_snowman)).a(_snowman);
   }

   private static void p(Consumer<jf> var0, brw var1, brw var2) {
      String _snowman = gm.T.b(_snowman.h()).a();
      ji.a(_snowman).b(bmd.mO).b(_snowman).a("dyed_bed").a("has_bed", a(bmd.mO)).a(_snowman, _snowman + "_from_white_bed");
   }

   private static void q(Consumer<jf> var0, brw var1, brw var2) {
      String _snowman = gm.T.b(_snowman.h()).a();
      jh.a(_snowman).a('#', _snowman).a('|', bmd.kP).a("###").a("###").a(" | ").b("banner").a("has_" + _snowman, a(_snowman)).a(_snowman);
   }

   private static void r(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 8).a('#', bup.ap).a('X', _snowman).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", a((brw)bup.ap)).a(_snowman);
   }

   private static void s(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 16).a('#', _snowman).a("###").a("###").b("stained_glass_pane").a("has_glass", a(_snowman)).a(_snowman);
   }

   private static void t(Consumer<jf> var0, brw var1, brw var2) {
      String _snowman = gm.T.b(_snowman.h()).a();
      String _snowmanx = gm.T.b(_snowman.h()).a();
      jh.a(_snowman, 8)
         .a('#', bup.dJ)
         .a('$', _snowman)
         .a("###")
         .a("#$#")
         .a("###")
         .b("stained_glass_pane")
         .a("has_glass_pane", a((brw)bup.dJ))
         .a("has_" + _snowmanx, a(_snowman))
         .a(_snowman, _snowman + "_from_glass_pane");
   }

   private static void u(Consumer<jf> var0, brw var1, brw var2) {
      jh.a(_snowman, 8).a('#', bup.gR).a('X', _snowman).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", a((brw)bup.gR)).a(_snowman);
   }

   private static void v(Consumer<jf> var0, brw var1, brw var2) {
      ji.a(_snowman, 8).b(_snowman).b(bup.C, 4).b(bup.E, 4).a("concrete_powder").a("has_sand", a((brw)bup.C)).a("has_gravel", a((brw)bup.E)).a(_snowman);
   }

   private static void a(Consumer<jf> var0, String var1, boz<?> var2, int var3) {
      jj.a(bon.a(bmd.nl), bmd.nm, 0.35F, _snowman, _snowman).a("has_beef", a(bmd.nl)).a(_snowman, "cooked_beef_from_" + _snowman);
      jj.a(bon.a(bmd.nn), bmd.no, 0.35F, _snowman, _snowman).a("has_chicken", a(bmd.nn)).a(_snowman, "cooked_chicken_from_" + _snowman);
      jj.a(bon.a(bmd.ml), bmd.mp, 0.35F, _snowman, _snowman).a("has_cod", a(bmd.ml)).a(_snowman, "cooked_cod_from_" + _snowman);
      jj.a(bon.a(bup.kc), bmd.ni, 0.1F, _snowman, _snowman).a("has_kelp", a((brw)bup.kc)).a(_snowman, "dried_kelp_from_" + _snowman);
      jj.a(bon.a(bmd.mm), bmd.mq, 0.35F, _snowman, _snowman).a("has_salmon", a(bmd.mm)).a(_snowman, "cooked_salmon_from_" + _snowman);
      jj.a(bon.a(bmd.pK), bmd.pL, 0.35F, _snowman, _snowman).a("has_mutton", a(bmd.pK)).a(_snowman, "cooked_mutton_from_" + _snowman);
      jj.a(bon.a(bmd.lx), bmd.ly, 0.35F, _snowman, _snowman).a("has_porkchop", a(bmd.lx)).a(_snowman, "cooked_porkchop_from_" + _snowman);
      jj.a(bon.a(bmd.oZ), bmd.pa, 0.35F, _snowman, _snowman).a("has_potato", a(bmd.oZ)).a(_snowman, "baked_potato_from_" + _snowman);
      jj.a(bon.a(bmd.px), bmd.py, 0.35F, _snowman, _snowman).a("has_rabbit", a(bmd.px)).a(_snowman, "cooked_rabbit_from_" + _snowman);
   }

   private static bc.a a(buo var0) {
      return new bc.a(bg.b.a, _snowman, cm.a);
   }

   private static bn.a a(brw var0) {
      return a(bq.a.a().a(_snowman).b());
   }

   private static bn.a a(ael<blx> var0) {
      return a(bq.a.a().a(_snowman).b());
   }

   private static bn.a a(bq... var0) {
      return new bn.a(bg.b.a, bz.d.e, bz.d.e, bz.d.e, _snowman);
   }

   @Override
   public String a() {
      return "Recipes";
   }
}
