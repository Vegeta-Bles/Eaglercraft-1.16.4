import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class adj extends adi {
   private static final Logger d = LogManager.getLogger();
   private long e;
   private final int f;
   private final int g;
   private final int h;
   private final String i;
   private final String j;
   private DatagramSocket k;
   private final byte[] l = new byte[1460];
   private String m;
   private String n;
   private final Map<SocketAddress, adj.a> o;
   private final ade p;
   private long q;
   private final vy r;

   private adj(vy var1, int var2) {
      super("Query Listener");
      this.r = _snowman;
      this.f = _snowman;
      this.n = _snowman.h_();
      this.g = _snowman.p();
      this.i = _snowman.i_();
      this.h = _snowman.J();
      this.j = _snowman.k_();
      this.q = 0L;
      this.m = "0.0.0.0";
      if (!this.n.isEmpty() && !this.m.equals(this.n)) {
         this.m = this.n;
      } else {
         this.n = "0.0.0.0";

         try {
            InetAddress _snowman = InetAddress.getLocalHost();
            this.m = _snowman.getHostAddress();
         } catch (UnknownHostException var4) {
            d.warn("Unable to determine local host IP, please set server-ip in server.properties", var4);
         }
      }

      this.p = new ade(1460);
      this.o = Maps.newHashMap();
   }

   @Nullable
   public static adj a(vy var0) {
      int _snowman = _snowman.g_().s;
      if (0 < _snowman && 65535 >= _snowman) {
         adj _snowmanx = new adj(_snowman, _snowman);
         return !_snowmanx.a() ? null : _snowmanx;
      } else {
         d.warn("Invalid query port {} found in server.properties (queries disabled)", _snowman);
         return null;
      }
   }

   private void a(byte[] var1, DatagramPacket var2) throws IOException {
      this.k.send(new DatagramPacket(_snowman, _snowman.length, _snowman.getSocketAddress()));
   }

   private boolean a(DatagramPacket var1) throws IOException {
      byte[] _snowman = _snowman.getData();
      int _snowmanx = _snowman.getLength();
      SocketAddress _snowmanxx = _snowman.getSocketAddress();
      d.debug("Packet len {} [{}]", _snowmanx, _snowmanxx);
      if (3 <= _snowmanx && -2 == _snowman[0] && -3 == _snowman[1]) {
         d.debug("Packet '{}' [{}]", adf.a(_snowman[2]), _snowmanxx);
         switch (_snowman[2]) {
            case 0:
               if (!this.c(_snowman)) {
                  d.debug("Invalid challenge [{}]", _snowmanxx);
                  return false;
               } else if (15 == _snowmanx) {
                  this.a(this.b(_snowman), _snowman);
                  d.debug("Rules [{}]", _snowmanxx);
               } else {
                  ade _snowmanxxx = new ade(1460);
                  _snowmanxxx.a(0);
                  _snowmanxxx.a(this.a(_snowman.getSocketAddress()));
                  _snowmanxxx.a(this.i);
                  _snowmanxxx.a("SMP");
                  _snowmanxxx.a(this.j);
                  _snowmanxxx.a(Integer.toString(this.r.I()));
                  _snowmanxxx.a(Integer.toString(this.h));
                  _snowmanxxx.a((short)this.g);
                  _snowmanxxx.a(this.m);
                  this.a(_snowmanxxx.a(), _snowman);
                  d.debug("Status [{}]", _snowmanxx);
               }
            default:
               return true;
            case 9:
               this.d(_snowman);
               d.debug("Challenge [{}]", _snowmanxx);
               return true;
         }
      } else {
         d.debug("Invalid packet [{}]", _snowmanxx);
         return false;
      }
   }

   private byte[] b(DatagramPacket var1) throws IOException {
      long _snowman = x.b();
      if (_snowman < this.q + 5000L) {
         byte[] _snowmanx = this.p.a();
         byte[] _snowmanxx = this.a(_snowman.getSocketAddress());
         _snowmanx[1] = _snowmanxx[0];
         _snowmanx[2] = _snowmanxx[1];
         _snowmanx[3] = _snowmanxx[2];
         _snowmanx[4] = _snowmanxx[3];
         return _snowmanx;
      } else {
         this.q = _snowman;
         this.p.b();
         this.p.a(0);
         this.p.a(this.a(_snowman.getSocketAddress()));
         this.p.a("splitnum");
         this.p.a(128);
         this.p.a(0);
         this.p.a("hostname");
         this.p.a(this.i);
         this.p.a("gametype");
         this.p.a("SMP");
         this.p.a("game_id");
         this.p.a("MINECRAFT");
         this.p.a("version");
         this.p.a(this.r.H());
         this.p.a("plugins");
         this.p.a(this.r.j_());
         this.p.a("map");
         this.p.a(this.j);
         this.p.a("numplayers");
         this.p.a("" + this.r.I());
         this.p.a("maxplayers");
         this.p.a("" + this.h);
         this.p.a("hostport");
         this.p.a("" + this.g);
         this.p.a("hostip");
         this.p.a(this.m);
         this.p.a(0);
         this.p.a(1);
         this.p.a("player_");
         this.p.a(0);
         String[] _snowmanx = this.r.K();

         for (String _snowmanxx : _snowmanx) {
            this.p.a(_snowmanxx);
         }

         this.p.a(0);
         return this.p.a();
      }
   }

   private byte[] a(SocketAddress var1) {
      return this.o.get(_snowman).c();
   }

   private Boolean c(DatagramPacket var1) {
      SocketAddress _snowman = _snowman.getSocketAddress();
      if (!this.o.containsKey(_snowman)) {
         return false;
      } else {
         byte[] _snowmanx = _snowman.getData();
         return this.o.get(_snowman).a() == adf.c(_snowmanx, 7, _snowman.getLength());
      }
   }

   private void d(DatagramPacket var1) throws IOException {
      adj.a _snowman = new adj.a(_snowman);
      this.o.put(_snowman.getSocketAddress(), _snowman);
      this.a(_snowman.b(), _snowman);
   }

   private void d() {
      if (this.a) {
         long _snowman = x.b();
         if (_snowman >= this.e + 30000L) {
            this.e = _snowman;
            this.o.values().removeIf(var2 -> var2.a(_snowman));
         }
      }
   }

   @Override
   public void run() {
      d.info("Query running on {}:{}", this.n, this.f);
      this.e = x.b();
      DatagramPacket _snowman = new DatagramPacket(this.l, this.l.length);

      try {
         while (this.a) {
            try {
               this.k.receive(_snowman);
               this.d();
               this.a(_snowman);
            } catch (SocketTimeoutException var8) {
               this.d();
            } catch (PortUnreachableException var9) {
            } catch (IOException var10) {
               this.a(var10);
            }
         }
      } finally {
         d.debug("closeSocket: {}:{}", this.n, this.f);
         this.k.close();
      }
   }

   @Override
   public boolean a() {
      if (this.a) {
         return true;
      } else {
         return !this.e() ? false : super.a();
      }
   }

   private void a(Exception var1) {
      if (this.a) {
         d.warn("Unexpected exception", _snowman);
         if (!this.e()) {
            d.error("Failed to recover from exception, shutting down!");
            this.a = false;
         }
      }
   }

   private boolean e() {
      try {
         this.k = new DatagramSocket(this.f, InetAddress.getByName(this.n));
         this.k.setSoTimeout(500);
         return true;
      } catch (Exception var2) {
         d.warn("Unable to initialise query system on {}:{}", this.n, this.f, var2);
         return false;
      }
   }

   static class a {
      private final long a = new Date().getTime();
      private final int b;
      private final byte[] c;
      private final byte[] d;
      private final String e;

      public a(DatagramPacket var1) {
         byte[] _snowman = _snowman.getData();
         this.c = new byte[4];
         this.c[0] = _snowman[3];
         this.c[1] = _snowman[4];
         this.c[2] = _snowman[5];
         this.c[3] = _snowman[6];
         this.e = new String(this.c, StandardCharsets.UTF_8);
         this.b = new Random().nextInt(16777216);
         this.d = String.format("\t%s%d\u0000", this.e, this.b).getBytes(StandardCharsets.UTF_8);
      }

      public Boolean a(long var1) {
         return this.a < _snowman;
      }

      public int a() {
         return this.b;
      }

      public byte[] b() {
         return this.d;
      }

      public byte[] c() {
         return this.c;
      }
   }
}
