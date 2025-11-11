import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class dzs {
   private static final cde[] b = Arrays.stream(bkx.values()).sorted(Comparator.comparingInt(bkx::b)).map(cde::new).toArray(cde[]::new);
   private static final cde c = new cde(null);
   public static final dzs a = new dzs();
   private final ccn d = new ccn();
   private final ccn e = new cdn();
   private final ccv f = new ccv();
   private final cca g = new cca();
   private final ccf h = new ccf();
   private final ccq i = new ccq();
   private final dvo j = new dvo();
   private final dvz k = new dvz();

   public dzs() {
   }

   public void a(bmb var1, ebm.b var2, dfm var3, eag var4, int var5, int var6) {
      blx _snowman = _snowman.b();
      if (_snowman instanceof bkh) {
         buo _snowmanx = ((bkh)_snowman).e();
         if (_snowmanx instanceof btq) {
            GameProfile _snowmanxx = null;
            if (_snowman.n()) {
               md _snowmanxxx = _snowman.o();
               if (_snowmanxxx.c("SkullOwner", 10)) {
                  _snowmanxx = mp.a(_snowmanxxx.p("SkullOwner"));
               } else if (_snowmanxxx.c("SkullOwner", 8) && !StringUtils.isBlank(_snowmanxxx.l("SkullOwner"))) {
                  GameProfile var16 = new GameProfile(null, _snowmanxxx.l("SkullOwner"));
                  _snowmanxx = cdg.b(var16);
                  _snowmanxxx.r("SkullOwner");
                  _snowmanxxx.a("SkullOwner", mp.a(new md(), _snowmanxx));
               }
            }

            eco.a(null, 180.0F, ((btq)_snowmanx).b(), _snowmanxx, 0.0F, _snowman, _snowman, _snowman);
         } else {
            ccj _snowmanxx;
            if (_snowmanx instanceof btm) {
               this.g.a(_snowman, ((btm)_snowmanx).b());
               _snowmanxx = this.g;
            } else if (_snowmanx instanceof buj) {
               this.h.a(((buj)_snowmanx).c());
               _snowmanxx = this.h;
            } else if (_snowmanx == bup.kW) {
               _snowmanxx = this.i;
            } else if (_snowmanx == bup.bR) {
               _snowmanxx = this.d;
            } else if (_snowmanx == bup.ek) {
               _snowmanxx = this.f;
            } else if (_snowmanx == bup.fr) {
               _snowmanxx = this.e;
            } else {
               if (!(_snowmanx instanceof bzs)) {
                  return;
               }

               bkx _snowmanxxx = bzs.b(_snowman);
               if (_snowmanxxx == null) {
                  _snowmanxx = c;
               } else {
                  _snowmanxx = b[_snowmanxxx.b()];
               }
            }

            ecd.a.a(_snowmanxx, _snowman, _snowman, _snowman, _snowman);
         }
      } else {
         if (_snowman == bmd.qn) {
            boolean _snowmanx = _snowman.b("BlockEntityTag") != null;
            _snowman.a();
            _snowman.a(1.0F, -1.0F, -1.0F);
            elr _snowmanxx = _snowmanx ? els.g : els.h;
            dfq _snowmanxxx = _snowmanxx.c().a(efo.c(_snowman, this.j.a(_snowmanxx.a()), true, _snowman.u()));
            this.j.b().a(_snowman, _snowmanxxx, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 1.0F);
            if (_snowmanx) {
               List<Pair<ccb, bkx>> _snowmanxxxx = cca.a(bmv.d(_snowman), cca.a(_snowman));
               ebz.a(_snowman, _snowman, _snowman, _snowman, this.j.a(), _snowmanxx, false, _snowmanxxxx, _snowman.u());
            } else {
               this.j.a().a(_snowman, _snowmanxxx, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            _snowman.b();
         } else if (_snowman == bmd.qM) {
            _snowman.a();
            _snowman.a(1.0F, -1.0F, -1.0F);
            dfq _snowmanx = efo.c(_snowman, this.k.a(dvz.a), false, _snowman.u());
            this.k.a(_snowman, _snowmanx, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 1.0F);
            _snowman.b();
         }
      }
   }
}
