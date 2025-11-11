import com.google.gson.GsonBuilder;

public class cys {
   public static GsonBuilder a() {
      return new GsonBuilder()
         .registerTypeAdapter(czd.class, new czd.a())
         .registerTypeAdapter(cyp.class, new cyp.a())
         .registerTypeAdapter(cyr.class, new cyr.a())
         .registerTypeHierarchyAdapter(dbo.class, dbq.a())
         .registerTypeHierarchyAdapter(cyv.c.class, new cyv.c.a());
   }

   public static GsonBuilder b() {
      return a().registerTypeAdapter(cyu.class, new cyu.a()).registerTypeHierarchyAdapter(czq.class, czo.a()).registerTypeHierarchyAdapter(daj.class, dal.a());
   }

   public static GsonBuilder c() {
      return b().registerTypeAdapter(cyx.class, new cyx.b()).registerTypeAdapter(cyy.class, new cyy.b());
   }
}
