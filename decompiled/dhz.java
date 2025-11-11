import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhz extends eoo implements dhk {
   private static final Logger b = LogManager.getLogger();
   private final dot c;
   private volatile nr p = oe.d;
   @Nullable
   private volatile nr q;
   private volatile boolean r;
   private int s;
   private final dja t;
   private final int u = 212;
   public static final String[] a = new String[]{
      "▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃",
      "_ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄",
      "_ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅",
      "_ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆",
      "_ _ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇",
      "_ _ _ _ _ ▃ ▄ ▅ ▆ ▇ █",
      "_ _ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇",
      "_ _ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆",
      "_ _ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅",
      "_ ▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄",
      "▃ ▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃",
      "▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _",
      "▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _",
      "▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _",
      "▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _ _",
      "█ ▇ ▆ ▅ ▄ ▃ _ _ _ _ _",
      "▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _ _",
      "▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _ _",
      "▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _ _",
      "▄ ▅ ▆ ▇ █ ▇ ▆ ▅ ▄ ▃ _"
   };

   public dhz(dot var1, dja var2) {
      this.c = _snowman;
      this.t = _snowman;
      _snowman.a(this);
      Thread _snowman = new Thread(_snowman, "Realms-long-running-task");
      _snowman.setUncaughtExceptionHandler(new dhg(b));
      _snowman.start();
   }

   @Override
   public void d() {
      super.d();
      eoj.b(this.p.getString());
      this.s++;
      this.t.b();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.h();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void b() {
      this.t.d();
      this.a((dlj)(new dlj(this.k / 2 - 106, j(12), 212, 20, nq.d, var1 -> this.h())));
   }

   private void h() {
      this.r = true;
      this.t.a();
      this.i.a(this.c);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.p, this.k / 2, j(3), 16777215);
      nr _snowman = this.q;
      if (_snowman == null) {
         a(_snowman, this.o, a[this.s % a.length], this.k / 2, j(8), 8421504);
      } else {
         a(_snowman, this.o, _snowman, this.k / 2, j(8), 16711680);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(nr var1) {
      this.q = _snowman;
      eoj.a(_snowman.getString());
      this.i();
      this.a((dlj)(new dlj(this.k / 2 - 106, this.l / 4 + 120 + 12, 200, 20, nq.h, var1x -> this.h())));
   }

   private void i() {
      Set<dmi> _snowman = Sets.newHashSet(this.m);
      this.e.removeIf(_snowman::contains);
      this.m.clear();
   }

   public void b(nr var1) {
      this.p = _snowman;
   }

   public boolean a() {
      return this.r;
   }
}
