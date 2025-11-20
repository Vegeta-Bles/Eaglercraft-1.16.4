package net.minecraft.advancement;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.JsonHelper;

public class AdvancementProgress implements Comparable<AdvancementProgress> {
   private final Map<String, CriterionProgress> criteriaProgresses = Maps.newHashMap();
   private String[][] requirements = new String[0][];

   public AdvancementProgress() {
   }

   public void init(Map<String, AdvancementCriterion> criteria, String[][] requirements) {
      Set<String> set = criteria.keySet();
      this.criteriaProgresses.entrySet().removeIf(entry -> !set.contains(entry.getKey()));

      for (String string : set) {
         if (!this.criteriaProgresses.containsKey(string)) {
            this.criteriaProgresses.put(string, new CriterionProgress());
         }
      }

      this.requirements = requirements;
   }

   public boolean isDone() {
      if (this.requirements.length == 0) {
         return false;
      } else {
         for (String[] strings : this.requirements) {
            boolean bl = false;

            for (String string : strings) {
               CriterionProgress lv = this.getCriterionProgress(string);
               if (lv != null && lv.isObtained()) {
                  bl = true;
                  break;
               }
            }

            if (!bl) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isAnyObtained() {
      for (CriterionProgress lv : this.criteriaProgresses.values()) {
         if (lv.isObtained()) {
            return true;
         }
      }

      return false;
   }

   public boolean obtain(String name) {
      CriterionProgress lv = this.criteriaProgresses.get(name);
      if (lv != null && !lv.isObtained()) {
         lv.obtain();
         return true;
      } else {
         return false;
      }
   }

   public boolean reset(String name) {
      CriterionProgress lv = this.criteriaProgresses.get(name);
      if (lv != null && lv.isObtained()) {
         lv.reset();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "AdvancementProgress{criteria=" + this.criteriaProgresses + ", requirements=" + Arrays.deepToString(this.requirements) + '}';
   }

   public void toPacket(PacketByteBuf buf) {
      buf.writeVarInt(this.criteriaProgresses.size());

      for (Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
         buf.writeString(entry.getKey());
         entry.getValue().toPacket(buf);
      }
   }

   public static AdvancementProgress fromPacket(PacketByteBuf buf) {
      AdvancementProgress lv = new AdvancementProgress();
      int i = buf.readVarInt();

      for (int j = 0; j < i; j++) {
         lv.criteriaProgresses.put(buf.readString(32767), CriterionProgress.fromPacket(buf));
      }

      return lv;
   }

   @Nullable
   public CriterionProgress getCriterionProgress(String name) {
      return this.criteriaProgresses.get(name);
   }

   @Environment(EnvType.CLIENT)
   public float getProgressBarPercentage() {
      if (this.criteriaProgresses.isEmpty()) {
         return 0.0F;
      } else {
         float f = (float)this.requirements.length;
         float g = (float)this.countObtainedRequirements();
         return g / f;
      }
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public String getProgressBarFraction() {
      if (this.criteriaProgresses.isEmpty()) {
         return null;
      } else {
         int i = this.requirements.length;
         if (i <= 1) {
            return null;
         } else {
            int j = this.countObtainedRequirements();
            return j + "/" + i;
         }
      }
   }

   @Environment(EnvType.CLIENT)
   private int countObtainedRequirements() {
      int i = 0;

      for (String[] strings : this.requirements) {
         boolean bl = false;

         for (String string : strings) {
            CriterionProgress lv = this.getCriterionProgress(string);
            if (lv != null && lv.isObtained()) {
               bl = true;
               break;
            }
         }

         if (bl) {
            i++;
         }
      }

      return i;
   }

   public Iterable<String> getUnobtainedCriteria() {
      List<String> list = Lists.newArrayList();

      for (Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
         if (!entry.getValue().isObtained()) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   public Iterable<String> getObtainedCriteria() {
      List<String> list = Lists.newArrayList();

      for (Entry<String, CriterionProgress> entry : this.criteriaProgresses.entrySet()) {
         if (entry.getValue().isObtained()) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   @Nullable
   public Date getEarliestProgressObtainDate() {
      Date date = null;

      for (CriterionProgress lv : this.criteriaProgresses.values()) {
         if (lv.isObtained() && (date == null || lv.getObtainedDate().before(date))) {
            date = lv.getObtainedDate();
         }
      }

      return date;
   }

   public int compareTo(AdvancementProgress arg) {
      Date date = this.getEarliestProgressObtainDate();
      Date date2 = arg.getEarliestProgressObtainDate();
      if (date == null && date2 != null) {
         return 1;
      } else if (date != null && date2 == null) {
         return -1;
      } else {
         return date == null && date2 == null ? 0 : date.compareTo(date2);
      }
   }

   public static class Serializer implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress> {
      public Serializer() {
      }

      public JsonElement serialize(AdvancementProgress arg, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject jsonObject = new JsonObject();
         JsonObject jsonObject2 = new JsonObject();

         for (Entry<String, CriterionProgress> entry : arg.criteriaProgresses.entrySet()) {
            CriterionProgress lv = entry.getValue();
            if (lv.isObtained()) {
               jsonObject2.add(entry.getKey(), lv.toJson());
            }
         }

         if (!jsonObject2.entrySet().isEmpty()) {
            jsonObject.add("criteria", jsonObject2);
         }

         jsonObject.addProperty("done", arg.isDone());
         return jsonObject;
      }

      public AdvancementProgress deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject jsonObject = JsonHelper.asObject(jsonElement, "advancement");
         JsonObject jsonObject2 = JsonHelper.getObject(jsonObject, "criteria", new JsonObject());
         AdvancementProgress lv = new AdvancementProgress();

         for (Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
            String string = entry.getKey();
            lv.criteriaProgresses.put(string, CriterionProgress.obtainedAt(JsonHelper.asString(entry.getValue(), string)));
         }

         return lv;
      }
   }
}
