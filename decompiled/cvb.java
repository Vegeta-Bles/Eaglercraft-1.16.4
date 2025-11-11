public class cvb {
   public static final cvb[] a = new cvb[64];
   public static final cvb b = new cvb(0, 0);
   public static final cvb c = new cvb(1, 8368696);
   public static final cvb d = new cvb(2, 16247203);
   public static final cvb e = new cvb(3, 13092807);
   public static final cvb f = new cvb(4, 16711680);
   public static final cvb g = new cvb(5, 10526975);
   public static final cvb h = new cvb(6, 10987431);
   public static final cvb i = new cvb(7, 31744);
   public static final cvb j = new cvb(8, 16777215);
   public static final cvb k = new cvb(9, 10791096);
   public static final cvb l = new cvb(10, 9923917);
   public static final cvb m = new cvb(11, 7368816);
   public static final cvb n = new cvb(12, 4210943);
   public static final cvb o = new cvb(13, 9402184);
   public static final cvb p = new cvb(14, 16776437);
   public static final cvb q = new cvb(15, 14188339);
   public static final cvb r = new cvb(16, 11685080);
   public static final cvb s = new cvb(17, 6724056);
   public static final cvb t = new cvb(18, 15066419);
   public static final cvb u = new cvb(19, 8375321);
   public static final cvb v = new cvb(20, 15892389);
   public static final cvb w = new cvb(21, 5000268);
   public static final cvb x = new cvb(22, 10066329);
   public static final cvb y = new cvb(23, 5013401);
   public static final cvb z = new cvb(24, 8339378);
   public static final cvb A = new cvb(25, 3361970);
   public static final cvb B = new cvb(26, 6704179);
   public static final cvb C = new cvb(27, 6717235);
   public static final cvb D = new cvb(28, 10040115);
   public static final cvb E = new cvb(29, 1644825);
   public static final cvb F = new cvb(30, 16445005);
   public static final cvb G = new cvb(31, 6085589);
   public static final cvb H = new cvb(32, 4882687);
   public static final cvb I = new cvb(33, 55610);
   public static final cvb J = new cvb(34, 8476209);
   public static final cvb K = new cvb(35, 7340544);
   public static final cvb L = new cvb(36, 13742497);
   public static final cvb M = new cvb(37, 10441252);
   public static final cvb N = new cvb(38, 9787244);
   public static final cvb O = new cvb(39, 7367818);
   public static final cvb P = new cvb(40, 12223780);
   public static final cvb Q = new cvb(41, 6780213);
   public static final cvb R = new cvb(42, 10505550);
   public static final cvb S = new cvb(43, 3746083);
   public static final cvb T = new cvb(44, 8874850);
   public static final cvb U = new cvb(45, 5725276);
   public static final cvb V = new cvb(46, 8014168);
   public static final cvb W = new cvb(47, 4996700);
   public static final cvb X = new cvb(48, 4993571);
   public static final cvb Y = new cvb(49, 5001770);
   public static final cvb Z = new cvb(50, 9321518);
   public static final cvb aa = new cvb(51, 2430480);
   public static final cvb ab = new cvb(52, 12398641);
   public static final cvb ac = new cvb(53, 9715553);
   public static final cvb ad = new cvb(54, 6035741);
   public static final cvb ae = new cvb(55, 1474182);
   public static final cvb af = new cvb(56, 3837580);
   public static final cvb ag = new cvb(57, 5647422);
   public static final cvb ah = new cvb(58, 1356933);
   public final int ai;
   public final int aj;

   private cvb(int var1, int var2) {
      if (_snowman >= 0 && _snowman <= 63) {
         this.aj = _snowman;
         this.ai = _snowman;
         a[_snowman] = this;
      } else {
         throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
      }
   }

   public int a(int var1) {
      int _snowman = 220;
      if (_snowman == 3) {
         _snowman = 135;
      }

      if (_snowman == 2) {
         _snowman = 255;
      }

      if (_snowman == 1) {
         _snowman = 220;
      }

      if (_snowman == 0) {
         _snowman = 180;
      }

      int _snowmanx = (this.ai >> 16 & 0xFF) * _snowman / 255;
      int _snowmanxx = (this.ai >> 8 & 0xFF) * _snowman / 255;
      int _snowmanxxx = (this.ai & 0xFF) * _snowman / 255;
      return 0xFF000000 | _snowmanxxx << 16 | _snowmanxx << 8 | _snowmanx;
   }
}
