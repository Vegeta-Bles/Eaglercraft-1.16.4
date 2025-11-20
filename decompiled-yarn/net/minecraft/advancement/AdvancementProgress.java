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
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.JsonHelper;

public class AdvancementProgress implements Comparable<AdvancementProgress> {
   private final Map<String, CriterionProgress> criteriaProgresses = Maps.newHashMap();
   private String[][] requirements = new String[0][];

   public AdvancementProgress() {
   }

   public void init(Map<String, AdvancementCriterion> criteria, String[][] requirements) {
      Set<String> _snowman = criteria.keySet();
      this.criteriaProgresses.entrySet().removeIf(_snowmanx -> !_snowman.contains(_snowmanx.getKey()));

      for (String _snowmanx : _snowman) {
         if (!this.criteriaProgresses.containsKey(_snowmanx)) {
            this.criteriaProgresses.put(_snowmanx, new CriterionProgress());
         }
      }

      this.requirements = requirements;
   }

   public boolean isDone() {
      if (this.requirements.length == 0) {
         return false;
      } else {
         for (String[] _snowman : this.requirements) {
            boolean _snowmanx = false;

            for (String _snowmanxx : _snowman) {
               CriterionProgress _snowmanxxx = this.getCriterionProgress(_snowmanxx);
               if (_snowmanxxx != null && _snowmanxxx.isObtained()) {
                  _snowmanx = true;
                  break;
               }
            }

            if (!_snowmanx) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isAnyObtained() {
      for (CriterionProgress _snowman : this.criteriaProgresses.values()) {
         if (_snowman.isObtained()) {
            return true;
         }
      }

      return false;
   }

   public boolean obtain(String name) {
      CriterionProgress _snowman = this.criteriaProgresses.get(name);
      if (_snowman != null && !_snowman.isObtained()) {
         _snowman.obtain();
         return true;
      } else {
         return false;
      }
   }

   public boolean reset(String name) {
      CriterionProgress _snowman = this.criteriaProgresses.get(name);
      if (_snowman != null && _snowman.isObtained()) {
         _snowman.reset();
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

      for (Entry<String, CriterionProgress> _snowman : this.criteriaProgresses.entrySet()) {
         buf.writeString(_snowman.getKey());
         _snowman.getValue().toPacket(buf);
      }
   }

   public static AdvancementProgress fromPacket(PacketByteBuf buf) {
      AdvancementProgress _snowman = new AdvancementProgress();
      int _snowmanx = buf.readVarInt();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         _snowman.criteriaProgresses.put(buf.readString(32767), CriterionProgress.fromPacket(buf));
      }

      return _snowman;
   }

   @Nullable
   public CriterionProgress getCriterionProgress(String name) {
      return this.criteriaProgresses.get(name);
   }

   public float getProgressBarPercentage() {
      if (this.criteriaProgresses.isEmpty()) {
         return 0.0F;
      } else {
         float _snowman = (float)this.requirements.length;
         float _snowmanx = (float)this.countObtainedRequirements();
         return _snowmanx / _snowman;
      }
   }

   @Nullable
   public String getProgressBarFraction() {
      if (this.criteriaProgresses.isEmpty()) {
         return null;
      } else {
         int _snowman = this.requirements.length;
         if (_snowman <= 1) {
            return null;
         } else {
            int _snowmanx = this.countObtainedRequirements();
            return _snowmanx + "/" + _snowman;
         }
      }
   }

   private int countObtainedRequirements() {
      int _snowman = 0;

      for (String[] _snowmanx : this.requirements) {
         boolean _snowmanxx = false;

         for (String _snowmanxxx : _snowmanx) {
            CriterionProgress _snowmanxxxx = this.getCriterionProgress(_snowmanxxx);
            if (_snowmanxxxx != null && _snowmanxxxx.isObtained()) {
               _snowmanxx = true;
               break;
            }
         }

         if (_snowmanxx) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public Iterable<String> getUnobtainedCriteria() {
      List<String> _snowman = Lists.newArrayList();

      for (Entry<String, CriterionProgress> _snowmanx : this.criteriaProgresses.entrySet()) {
         if (!_snowmanx.getValue().isObtained()) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      return _snowman;
   }

   public Iterable<String> getObtainedCriteria() {
      List<String> _snowman = Lists.newArrayList();

      for (Entry<String, CriterionProgress> _snowmanx : this.criteriaProgresses.entrySet()) {
         if (_snowmanx.getValue().isObtained()) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      return _snowman;
   }

   @Nullable
   public Date getEarliestProgressObtainDate() {
      Date _snowman = null;

      for (CriterionProgress _snowmanx : this.criteriaProgresses.values()) {
         if (_snowmanx.isObtained() && (_snowman == null || _snowmanx.getObtainedDate().before(_snowman))) {
            _snowman = _snowmanx.getObtainedDate();
         }
      }

      return _snowman;
   }

   public int compareTo(AdvancementProgress _snowman) {
      Date _snowmanx = this.getEarliestProgressObtainDate();
      Date _snowmanxx = _snowman.getEarliestProgressObtainDate();
      if (_snowmanx == null && _snowmanxx != null) {
         return 1;
      } else if (_snowmanx != null && _snowmanxx == null) {
         return -1;
      } else {
         return _snowmanx == null && _snowmanxx == null ? 0 : _snowmanx.compareTo(_snowmanxx);
      }
   }

   public static class Serializer implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress> {
      public Serializer() {
      }

      public JsonElement serialize(AdvancementProgress _snowman, Type _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();
         JsonObject _snowmanxxxx = new JsonObject();

         for (Entry<String, CriterionProgress> _snowmanxxxxx : _snowman.criteriaProgresses.entrySet()) {
            CriterionProgress _snowmanxxxxxx = _snowmanxxxxx.getValue();
            if (_snowmanxxxxxx.isObtained()) {
               _snowmanxxxx.add(_snowmanxxxxx.getKey(), _snowmanxxxxxx.toJson());
            }
         }

         if (!_snowmanxxxx.entrySet().isEmpty()) {
            _snowmanxxx.add("criteria", _snowmanxxxx);
         }

         _snowmanxxx.addProperty("done", _snowman.isDone());
         return _snowmanxxx;
      }

      public AdvancementProgress deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "advancement");
         JsonObject _snowmanxxxx = JsonHelper.getObject(_snowmanxxx, "criteria", new JsonObject());
         AdvancementProgress _snowmanxxxxx = new AdvancementProgress();

         for (Entry<String, JsonElement> _snowmanxxxxxx : _snowmanxxxx.entrySet()) {
            String _snowmanxxxxxxx = _snowmanxxxxxx.getKey();
            _snowmanxxxxx.criteriaProgresses.put(_snowmanxxxxxxx, CriterionProgress.obtainedAt(JsonHelper.asString(_snowmanxxxxxx.getValue(), _snowmanxxxxxxx)));
         }

         return _snowmanxxxxx;
      }
   }
}
