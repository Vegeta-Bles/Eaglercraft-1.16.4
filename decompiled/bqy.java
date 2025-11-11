import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public abstract class bqy implements da {
   private static final SimpleDateFormat b = new SimpleDateFormat("HH:mm:ss");
   private static final nr c = new oe("@");
   private long d = -1L;
   private boolean e = true;
   private int f;
   private boolean g = true;
   @Nullable
   private nr h;
   private String i = "";
   private nr j = c;

   public bqy() {
   }

   public int i() {
      return this.f;
   }

   public void a(int var1) {
      this.f = _snowman;
   }

   public nr j() {
      return this.h == null ? oe.d : this.h;
   }

   public md a(md var1) {
      _snowman.a("Command", this.i);
      _snowman.b("SuccessCount", this.f);
      _snowman.a("CustomName", nr.a.a(this.j));
      _snowman.a("TrackOutput", this.g);
      if (this.h != null && this.g) {
         _snowman.a("LastOutput", nr.a.a(this.h));
      }

      _snowman.a("UpdateLastExecution", this.e);
      if (this.e && this.d > 0L) {
         _snowman.a("LastExecution", this.d);
      }

      return _snowman;
   }

   public void b(md var1) {
      this.i = _snowman.l("Command");
      this.f = _snowman.h("SuccessCount");
      if (_snowman.c("CustomName", 8)) {
         this.a(nr.a.a(_snowman.l("CustomName")));
      }

      if (_snowman.c("TrackOutput", 1)) {
         this.g = _snowman.q("TrackOutput");
      }

      if (_snowman.c("LastOutput", 8) && this.g) {
         try {
            this.h = nr.a.a(_snowman.l("LastOutput"));
         } catch (Throwable var3) {
            this.h = new oe(var3.getMessage());
         }
      } else {
         this.h = null;
      }

      if (_snowman.e("UpdateLastExecution")) {
         this.e = _snowman.q("UpdateLastExecution");
      }

      if (this.e && _snowman.e("LastExecution")) {
         this.d = _snowman.i("LastExecution");
      } else {
         this.d = -1L;
      }
   }

   public void a(String var1) {
      this.i = _snowman;
      this.f = 0;
   }

   public String k() {
      return this.i;
   }

   public boolean a(brx var1) {
      if (_snowman.v || _snowman.T() == this.d) {
         return false;
      } else if ("Searge".equalsIgnoreCase(this.i)) {
         this.h = new oe("#itzlipofutzli");
         this.f = 1;
         return true;
      } else {
         this.f = 0;
         MinecraftServer _snowman = this.d().l();
         if (_snowman.m() && !aft.b(this.i)) {
            try {
               this.h = null;
               db _snowmanx = this.h().a((var1x, var2x, var3x) -> {
                  if (var2x) {
                     this.f++;
                  }
               });
               _snowman.aD().a(_snowmanx, this.i);
            } catch (Throwable var6) {
               l _snowmanxx = l.a(var6, "Executing command block");
               m _snowmanxxx = _snowmanxx.a("Command to be executed");
               _snowmanxxx.a("Command", this::k);
               _snowmanxxx.a("Name", () -> this.l().getString());
               throw new u(_snowmanxx);
            }
         }

         if (this.e) {
            this.d = _snowman.T();
         } else {
            this.d = -1L;
         }

         return true;
      }
   }

   public nr l() {
      return this.j;
   }

   public void a(@Nullable nr var1) {
      if (_snowman != null) {
         this.j = _snowman;
      } else {
         this.j = c;
      }
   }

   @Override
   public void a(nr var1, UUID var2) {
      if (this.g) {
         this.h = new oe("[" + b.format(new Date()) + "] ").a(_snowman);
         this.e();
      }
   }

   public abstract aag d();

   public abstract void e();

   public void b(@Nullable nr var1) {
      this.h = _snowman;
   }

   public void a(boolean var1) {
      this.g = _snowman;
   }

   public boolean m() {
      return this.g;
   }

   public aou a(bfw var1) {
      if (!_snowman.eV()) {
         return aou.c;
      } else {
         if (_snowman.cg().v) {
            _snowman.a(this);
         }

         return aou.a(_snowman.l.v);
      }
   }

   public abstract dcn f();

   public abstract db h();

   @Override
   public boolean a() {
      return this.d().V().b(brt.n) && this.g;
   }

   @Override
   public boolean b() {
      return this.g;
   }

   @Override
   public boolean R_() {
      return this.d().V().b(brt.h);
   }
}
