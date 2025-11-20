package net.minecraft.advancement;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.SelectAdvancementTabS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerAdvancementTracker {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(AdvancementProgress.class, new AdvancementProgress.Serializer())
      .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
      .setPrettyPrinting()
      .create();
   private static final TypeToken<Map<Identifier, AdvancementProgress>> JSON_TYPE = new TypeToken<Map<Identifier, AdvancementProgress>>() {
   };
   private final DataFixer field_25324;
   private final PlayerManager field_25325;
   private final File advancementFile;
   private final Map<Advancement, AdvancementProgress> advancementToProgress = Maps.newLinkedHashMap();
   private final Set<Advancement> visibleAdvancements = Sets.newLinkedHashSet();
   private final Set<Advancement> visibilityUpdates = Sets.newLinkedHashSet();
   private final Set<Advancement> progressUpdates = Sets.newLinkedHashSet();
   private ServerPlayerEntity owner;
   @Nullable
   private Advancement currentDisplayTab;
   private boolean dirty = true;

   public PlayerAdvancementTracker(DataFixer dataFixer, PlayerManager arg, ServerAdvancementLoader arg2, File file, ServerPlayerEntity arg3) {
      this.field_25324 = dataFixer;
      this.field_25325 = arg;
      this.advancementFile = file;
      this.owner = arg3;
      this.load(arg2);
   }

   public void setOwner(ServerPlayerEntity owner) {
      this.owner = owner;
   }

   public void clearCriteria() {
      for (Criterion<?> lv : Criteria.getCriteria()) {
         lv.endTracking(this);
      }
   }

   public void reload(ServerAdvancementLoader advancementLoader) {
      this.clearCriteria();
      this.advancementToProgress.clear();
      this.visibleAdvancements.clear();
      this.visibilityUpdates.clear();
      this.progressUpdates.clear();
      this.dirty = true;
      this.currentDisplayTab = null;
      this.load(advancementLoader);
   }

   private void beginTrackingAllAdvancements(ServerAdvancementLoader advancementLoader) {
      for (Advancement lv : advancementLoader.getAdvancements()) {
         this.beginTracking(lv);
      }
   }

   private void updateCompleted() {
      List<Advancement> list = Lists.newArrayList();

      for (Entry<Advancement, AdvancementProgress> entry : this.advancementToProgress.entrySet()) {
         if (entry.getValue().isDone()) {
            list.add(entry.getKey());
            this.progressUpdates.add(entry.getKey());
         }
      }

      for (Advancement lv : list) {
         this.updateDisplay(lv);
      }
   }

   private void rewardEmptyAdvancements(ServerAdvancementLoader advancementLoader) {
      for (Advancement lv : advancementLoader.getAdvancements()) {
         if (lv.getCriteria().isEmpty()) {
            this.grantCriterion(lv, "");
            lv.getRewards().apply(this.owner);
         }
      }
   }

   private void load(ServerAdvancementLoader advancementLoader) {
      if (this.advancementFile.isFile()) {
         try {
            JsonReader jsonReader = new JsonReader(new StringReader(Files.toString(this.advancementFile, StandardCharsets.UTF_8)));
            Throwable var3 = null;

            try {
               jsonReader.setLenient(false);
               Dynamic<JsonElement> dynamic = new Dynamic(JsonOps.INSTANCE, Streams.parse(jsonReader));
               if (!dynamic.get("DataVersion").asNumber().result().isPresent()) {
                  dynamic = dynamic.set("DataVersion", dynamic.createInt(1343));
               }

               dynamic = this.field_25324
                  .update(
                     DataFixTypes.ADVANCEMENTS.getTypeReference(),
                     dynamic,
                     dynamic.get("DataVersion").asInt(0),
                     SharedConstants.getGameVersion().getWorldVersion()
                  );
               dynamic = dynamic.remove("DataVersion");
               Map<Identifier, AdvancementProgress> map = (Map<Identifier, AdvancementProgress>)GSON.getAdapter(JSON_TYPE)
                  .fromJsonTree((JsonElement)dynamic.getValue());
               if (map == null) {
                  throw new JsonParseException("Found null for advancements");
               }

               Stream<Entry<Identifier, AdvancementProgress>> stream = map.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));

               for (Entry<Identifier, AdvancementProgress> entry : stream.collect(Collectors.toList())) {
                  Advancement lv = advancementLoader.get(entry.getKey());
                  if (lv == null) {
                     LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", entry.getKey(), this.advancementFile);
                  } else {
                     this.initProgress(lv, entry.getValue());
                  }
               }
            } catch (Throwable var19) {
               var3 = var19;
               throw var19;
            } finally {
               if (jsonReader != null) {
                  if (var3 != null) {
                     try {
                        jsonReader.close();
                     } catch (Throwable var18) {
                        var3.addSuppressed(var18);
                     }
                  } else {
                     jsonReader.close();
                  }
               }
            }
         } catch (JsonParseException var21) {
            LOGGER.error("Couldn't parse player advancements in {}", this.advancementFile, var21);
         } catch (IOException var22) {
            LOGGER.error("Couldn't access player advancements in {}", this.advancementFile, var22);
         }
      }

      this.rewardEmptyAdvancements(advancementLoader);
      this.updateCompleted();
      this.beginTrackingAllAdvancements(advancementLoader);
   }

   public void save() {
      Map<Identifier, AdvancementProgress> map = Maps.newHashMap();

      for (Entry<Advancement, AdvancementProgress> entry : this.advancementToProgress.entrySet()) {
         AdvancementProgress lv = entry.getValue();
         if (lv.isAnyObtained()) {
            map.put(entry.getKey().getId(), lv);
         }
      }

      if (this.advancementFile.getParentFile() != null) {
         this.advancementFile.getParentFile().mkdirs();
      }

      JsonElement jsonElement = GSON.toJsonTree(map);
      jsonElement.getAsJsonObject().addProperty("DataVersion", SharedConstants.getGameVersion().getWorldVersion());

      try (
         OutputStream outputStream = new FileOutputStream(this.advancementFile);
         Writer writer = new OutputStreamWriter(outputStream, Charsets.UTF_8.newEncoder());
      ) {
         GSON.toJson(jsonElement, writer);
      } catch (IOException var35) {
         LOGGER.error("Couldn't save player advancements to {}", this.advancementFile, var35);
      }
   }

   public boolean grantCriterion(Advancement advancement, String criterionName) {
      boolean bl = false;
      AdvancementProgress lv = this.getProgress(advancement);
      boolean bl2 = lv.isDone();
      if (lv.obtain(criterionName)) {
         this.endTrackingCompleted(advancement);
         this.progressUpdates.add(advancement);
         bl = true;
         if (!bl2 && lv.isDone()) {
            advancement.getRewards().apply(this.owner);
            if (advancement.getDisplay() != null
               && advancement.getDisplay().shouldAnnounceToChat()
               && this.owner.world.getGameRules().getBoolean(GameRules.ANNOUNCE_ADVANCEMENTS)) {
               this.field_25325
                  .broadcastChatMessage(
                     new TranslatableText(
                        "chat.type.advancement." + advancement.getDisplay().getFrame().getId(), this.owner.getDisplayName(), advancement.toHoverableText()
                     ),
                     MessageType.SYSTEM,
                     Util.NIL_UUID
                  );
            }
         }
      }

      if (lv.isDone()) {
         this.updateDisplay(advancement);
      }

      return bl;
   }

   public boolean revokeCriterion(Advancement advancement, String criterionName) {
      boolean bl = false;
      AdvancementProgress lv = this.getProgress(advancement);
      if (lv.reset(criterionName)) {
         this.beginTracking(advancement);
         this.progressUpdates.add(advancement);
         bl = true;
      }

      if (!lv.isAnyObtained()) {
         this.updateDisplay(advancement);
      }

      return bl;
   }

   private void beginTracking(Advancement advancement) {
      AdvancementProgress lv = this.getProgress(advancement);
      if (!lv.isDone()) {
         for (Entry<String, AdvancementCriterion> entry : advancement.getCriteria().entrySet()) {
            CriterionProgress lv2 = lv.getCriterionProgress(entry.getKey());
            if (lv2 != null && !lv2.isObtained()) {
               CriterionConditions lv3 = entry.getValue().getConditions();
               if (lv3 != null) {
                  Criterion<CriterionConditions> lv4 = Criteria.getById(lv3.getId());
                  if (lv4 != null) {
                     lv4.beginTrackingCondition(this, new Criterion.ConditionsContainer<>(lv3, advancement, entry.getKey()));
                  }
               }
            }
         }
      }
   }

   private void endTrackingCompleted(Advancement advancement) {
      AdvancementProgress lv = this.getProgress(advancement);

      for (Entry<String, AdvancementCriterion> entry : advancement.getCriteria().entrySet()) {
         CriterionProgress lv2 = lv.getCriterionProgress(entry.getKey());
         if (lv2 != null && (lv2.isObtained() || lv.isDone())) {
            CriterionConditions lv3 = entry.getValue().getConditions();
            if (lv3 != null) {
               Criterion<CriterionConditions> lv4 = Criteria.getById(lv3.getId());
               if (lv4 != null) {
                  lv4.endTrackingCondition(this, new Criterion.ConditionsContainer<>(lv3, advancement, entry.getKey()));
               }
            }
         }
      }
   }

   public void sendUpdate(ServerPlayerEntity player) {
      if (this.dirty || !this.visibilityUpdates.isEmpty() || !this.progressUpdates.isEmpty()) {
         Map<Identifier, AdvancementProgress> map = Maps.newHashMap();
         Set<Advancement> set = Sets.newLinkedHashSet();
         Set<Identifier> set2 = Sets.newLinkedHashSet();

         for (Advancement lv : this.progressUpdates) {
            if (this.visibleAdvancements.contains(lv)) {
               map.put(lv.getId(), this.advancementToProgress.get(lv));
            }
         }

         for (Advancement lv2 : this.visibilityUpdates) {
            if (this.visibleAdvancements.contains(lv2)) {
               set.add(lv2);
            } else {
               set2.add(lv2.getId());
            }
         }

         if (this.dirty || !map.isEmpty() || !set.isEmpty() || !set2.isEmpty()) {
            player.networkHandler.sendPacket(new AdvancementUpdateS2CPacket(this.dirty, set, set2, map));
            this.visibilityUpdates.clear();
            this.progressUpdates.clear();
         }
      }

      this.dirty = false;
   }

   public void setDisplayTab(@Nullable Advancement advancement) {
      Advancement lv = this.currentDisplayTab;
      if (advancement != null && advancement.getParent() == null && advancement.getDisplay() != null) {
         this.currentDisplayTab = advancement;
      } else {
         this.currentDisplayTab = null;
      }

      if (lv != this.currentDisplayTab) {
         this.owner.networkHandler.sendPacket(new SelectAdvancementTabS2CPacket(this.currentDisplayTab == null ? null : this.currentDisplayTab.getId()));
      }
   }

   public AdvancementProgress getProgress(Advancement advancement) {
      AdvancementProgress lv = this.advancementToProgress.get(advancement);
      if (lv == null) {
         lv = new AdvancementProgress();
         this.initProgress(advancement, lv);
      }

      return lv;
   }

   private void initProgress(Advancement advancement, AdvancementProgress progress) {
      progress.init(advancement.getCriteria(), advancement.getRequirements());
      this.advancementToProgress.put(advancement, progress);
   }

   private void updateDisplay(Advancement advancement) {
      boolean bl = this.canSee(advancement);
      boolean bl2 = this.visibleAdvancements.contains(advancement);
      if (bl && !bl2) {
         this.visibleAdvancements.add(advancement);
         this.visibilityUpdates.add(advancement);
         if (this.advancementToProgress.containsKey(advancement)) {
            this.progressUpdates.add(advancement);
         }
      } else if (!bl && bl2) {
         this.visibleAdvancements.remove(advancement);
         this.visibilityUpdates.add(advancement);
      }

      if (bl != bl2 && advancement.getParent() != null) {
         this.updateDisplay(advancement.getParent());
      }

      for (Advancement lv : advancement.getChildren()) {
         this.updateDisplay(lv);
      }
   }

   private boolean canSee(Advancement advancement) {
      for (int i = 0; advancement != null && i <= 2; i++) {
         if (i == 0 && this.hasChildrenDone(advancement)) {
            return true;
         }

         if (advancement.getDisplay() == null) {
            return false;
         }

         AdvancementProgress lv = this.getProgress(advancement);
         if (lv.isDone()) {
            return true;
         }

         if (advancement.getDisplay().isHidden()) {
            return false;
         }

         advancement = advancement.getParent();
      }

      return false;
   }

   private boolean hasChildrenDone(Advancement advancement) {
      AdvancementProgress lv = this.getProgress(advancement);
      if (lv.isDone()) {
         return true;
      } else {
         for (Advancement lv2 : advancement.getChildren()) {
            if (this.hasChildrenDone(lv2)) {
               return true;
            }
         }

         return false;
      }
   }
}
