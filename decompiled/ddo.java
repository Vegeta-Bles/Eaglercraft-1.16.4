import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ddo extends cxs {
   private static final Logger a = LogManager.getLogger();
   private ddn b;
   private md c;

   public ddo() {
      super("scoreboard");
   }

   public void a(ddn var1) {
      this.b = _snowman;
      if (this.c != null) {
         this.a(this.c);
      }
   }

   @Override
   public void a(md var1) {
      if (this.b == null) {
         this.c = _snowman;
      } else {
         this.b(_snowman.d("Objectives", 10));
         this.b.a(_snowman.d("PlayerScores", 10));
         if (_snowman.c("DisplaySlots", 10)) {
            this.c(_snowman.p("DisplaySlots"));
         }

         if (_snowman.c("Teams", 9)) {
            this.a(_snowman.d("Teams", 10));
         }
      }
   }

   protected void a(mj var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         md _snowmanx = _snowman.a(_snowman);
         String _snowmanxx = _snowmanx.l("Name");
         if (_snowmanxx.length() > 16) {
            _snowmanxx = _snowmanxx.substring(0, 16);
         }

         ddl _snowmanxxx = this.b.g(_snowmanxx);
         nr _snowmanxxxx = nr.a.a(_snowmanx.l("DisplayName"));
         if (_snowmanxxxx != null) {
            _snowmanxxx.a(_snowmanxxxx);
         }

         if (_snowmanx.c("TeamColor", 8)) {
            _snowmanxxx.a(k.b(_snowmanx.l("TeamColor")));
         }

         if (_snowmanx.c("AllowFriendlyFire", 99)) {
            _snowmanxxx.a(_snowmanx.q("AllowFriendlyFire"));
         }

         if (_snowmanx.c("SeeFriendlyInvisibles", 99)) {
            _snowmanxxx.b(_snowmanx.q("SeeFriendlyInvisibles"));
         }

         if (_snowmanx.c("MemberNamePrefix", 8)) {
            nr _snowmanxxxxx = nr.a.a(_snowmanx.l("MemberNamePrefix"));
            if (_snowmanxxxxx != null) {
               _snowmanxxx.b(_snowmanxxxxx);
            }
         }

         if (_snowmanx.c("MemberNameSuffix", 8)) {
            nr _snowmanxxxxx = nr.a.a(_snowmanx.l("MemberNameSuffix"));
            if (_snowmanxxxxx != null) {
               _snowmanxxx.c(_snowmanxxxxx);
            }
         }

         if (_snowmanx.c("NameTagVisibility", 8)) {
            ddp.b _snowmanxxxxx = ddp.b.a(_snowmanx.l("NameTagVisibility"));
            if (_snowmanxxxxx != null) {
               _snowmanxxx.a(_snowmanxxxxx);
            }
         }

         if (_snowmanx.c("DeathMessageVisibility", 8)) {
            ddp.b _snowmanxxxxx = ddp.b.a(_snowmanx.l("DeathMessageVisibility"));
            if (_snowmanxxxxx != null) {
               _snowmanxxx.b(_snowmanxxxxx);
            }
         }

         if (_snowmanx.c("CollisionRule", 8)) {
            ddp.a _snowmanxxxxx = ddp.a.a(_snowmanx.l("CollisionRule"));
            if (_snowmanxxxxx != null) {
               _snowmanxxx.a(_snowmanxxxxx);
            }
         }

         this.a(_snowmanxxx, _snowmanx.d("Players", 8));
      }
   }

   protected void a(ddl var1, mj var2) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         this.b.a(_snowman.j(_snowman), _snowman);
      }
   }

   protected void c(md var1) {
      for (int _snowman = 0; _snowman < 19; _snowman++) {
         if (_snowman.c("slot_" + _snowman, 8)) {
            String _snowmanx = _snowman.l("slot_" + _snowman);
            ddk _snowmanxx = this.b.d(_snowmanx);
            this.b.a(_snowman, _snowmanxx);
         }
      }
   }

   protected void b(mj var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         md _snowmanx = _snowman.a(_snowman);
         ddq.a(_snowmanx.l("CriteriaName")).ifPresent(var2x -> {
            String _snowmanxx = _snowman.l("Name");
            if (_snowmanxx.length() > 16) {
               _snowmanxx = _snowmanxx.substring(0, 16);
            }

            nr _snowmanx = nr.a.a(_snowman.l("DisplayName"));
            ddq.a _snowmanxx = ddq.a.a(_snowman.l("RenderType"));
            this.b.a(_snowmanxx, var2x, _snowmanx, _snowmanxx);
         });
      }
   }

   @Override
   public md b(md var1) {
      if (this.b == null) {
         a.warn("Tried to save scoreboard without having a scoreboard...");
         return _snowman;
      } else {
         _snowman.a("Objectives", this.e());
         _snowman.a("PlayerScores", this.b.i());
         _snowman.a("Teams", this.a());
         this.d(_snowman);
         return _snowman;
      }
   }

   protected mj a() {
      mj _snowman = new mj();

      for (ddl _snowmanx : this.b.g()) {
         md _snowmanxx = new md();
         _snowmanxx.a("Name", _snowmanx.b());
         _snowmanxx.a("DisplayName", nr.a.a(_snowmanx.c()));
         if (_snowmanx.n().b() >= 0) {
            _snowmanxx.a("TeamColor", _snowmanx.n().f());
         }

         _snowmanxx.a("AllowFriendlyFire", _snowmanx.h());
         _snowmanxx.a("SeeFriendlyInvisibles", _snowmanx.i());
         _snowmanxx.a("MemberNamePrefix", nr.a.a(_snowmanx.e()));
         _snowmanxx.a("MemberNameSuffix", nr.a.a(_snowmanx.f()));
         _snowmanxx.a("NameTagVisibility", _snowmanx.j().e);
         _snowmanxx.a("DeathMessageVisibility", _snowmanx.k().e);
         _snowmanxx.a("CollisionRule", _snowmanx.l().e);
         mj _snowmanxxx = new mj();

         for (String _snowmanxxxx : _snowmanx.g()) {
            _snowmanxxx.add(ms.a(_snowmanxxxx));
         }

         _snowmanxx.a("Players", _snowmanxxx);
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   protected void d(md var1) {
      md _snowman = new md();
      boolean _snowmanx = false;

      for (int _snowmanxx = 0; _snowmanxx < 19; _snowmanxx++) {
         ddk _snowmanxxx = this.b.a(_snowmanxx);
         if (_snowmanxxx != null) {
            _snowman.a("slot_" + _snowmanxx, _snowmanxxx.b());
            _snowmanx = true;
         }
      }

      if (_snowmanx) {
         _snowman.a("DisplaySlots", _snowman);
      }
   }

   protected mj e() {
      mj _snowman = new mj();

      for (ddk _snowmanx : this.b.c()) {
         if (_snowmanx.c() != null) {
            md _snowmanxx = new md();
            _snowmanxx.a("Name", _snowmanx.b());
            _snowmanxx.a("CriteriaName", _snowmanx.c().c());
            _snowmanxx.a("DisplayName", nr.a.a(_snowmanx.d()));
            _snowmanxx.a("RenderType", _snowmanx.f().a());
            _snowman.add(_snowmanxx);
         }
      }

      return _snowman;
   }
}
