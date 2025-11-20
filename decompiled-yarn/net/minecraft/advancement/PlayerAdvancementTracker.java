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

   public PlayerAdvancementTracker(DataFixer _snowman, PlayerManager _snowman, ServerAdvancementLoader _snowman, File _snowman, ServerPlayerEntity _snowman) {
      this.field_25324 = _snowman;
      this.field_25325 = _snowman;
      this.advancementFile = _snowman;
      this.owner = _snowman;
      this.load(_snowman);
   }

   public void setOwner(ServerPlayerEntity owner) {
      this.owner = owner;
   }

   public void clearCriteria() {
      for (Criterion<?> _snowman : Criteria.getCriteria()) {
         _snowman.endTracking(this);
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
      for (Advancement _snowman : advancementLoader.getAdvancements()) {
         this.beginTracking(_snowman);
      }
   }

   private void updateCompleted() {
      List<Advancement> _snowman = Lists.newArrayList();

      for (Entry<Advancement, AdvancementProgress> _snowmanx : this.advancementToProgress.entrySet()) {
         if (_snowmanx.getValue().isDone()) {
            _snowman.add(_snowmanx.getKey());
            this.progressUpdates.add(_snowmanx.getKey());
         }
      }

      for (Advancement _snowmanxx : _snowman) {
         this.updateDisplay(_snowmanxx);
      }
   }

   private void rewardEmptyAdvancements(ServerAdvancementLoader advancementLoader) {
      for (Advancement _snowman : advancementLoader.getAdvancements()) {
         if (_snowman.getCriteria().isEmpty()) {
            this.grantCriterion(_snowman, "");
            _snowman.getRewards().apply(this.owner);
         }
      }
   }

   private void load(ServerAdvancementLoader advancementLoader) {
      if (this.advancementFile.isFile()) {
         try {
            JsonReader _snowman = new JsonReader(new StringReader(Files.toString(this.advancementFile, StandardCharsets.UTF_8)));
            Throwable var3 = null;

            try {
               _snowman.setLenient(false);
               Dynamic<JsonElement> _snowmanx = new Dynamic(JsonOps.INSTANCE, Streams.parse(_snowman));
               if (!_snowmanx.get("DataVersion").asNumber().result().isPresent()) {
                  _snowmanx = _snowmanx.set("DataVersion", _snowmanx.createInt(1343));
               }

               _snowmanx = this.field_25324
                  .update(DataFixTypes.ADVANCEMENTS.getTypeReference(), _snowmanx, _snowmanx.get("DataVersion").asInt(0), SharedConstants.getGameVersion().getWorldVersion());
               _snowmanx = _snowmanx.remove("DataVersion");
               Map<Identifier, AdvancementProgress> _snowmanxx = (Map<Identifier, AdvancementProgress>)GSON.getAdapter(JSON_TYPE)
                  .fromJsonTree((JsonElement)_snowmanx.getValue());
               if (_snowmanxx == null) {
                  throw new JsonParseException("Found null for advancements");
               }

               Stream<Entry<Identifier, AdvancementProgress>> _snowmanxxx = _snowmanxx.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));

               for (Entry<Identifier, AdvancementProgress> _snowmanxxxx : _snowmanxxx.collect(Collectors.toList())) {
                  Advancement _snowmanxxxxx = advancementLoader.get(_snowmanxxxx.getKey());
                  if (_snowmanxxxxx == null) {
                     LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", _snowmanxxxx.getKey(), this.advancementFile);
                  } else {
                     this.initProgress(_snowmanxxxxx, _snowmanxxxx.getValue());
                  }
               }
            } catch (Throwable var19) {
               var3 = var19;
               throw var19;
            } finally {
               if (_snowman != null) {
                  if (var3 != null) {
                     try {
                        _snowman.close();
                     } catch (Throwable var18) {
                        var3.addSuppressed(var18);
                     }
                  } else {
                     _snowman.close();
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
      Map<Identifier, AdvancementProgress> _snowman = Maps.newHashMap();

      for (Entry<Advancement, AdvancementProgress> _snowmanx : this.advancementToProgress.entrySet()) {
         AdvancementProgress _snowmanxx = _snowmanx.getValue();
         if (_snowmanxx.isAnyObtained()) {
            _snowman.put(_snowmanx.getKey().getId(), _snowmanxx);
         }
      }

      if (this.advancementFile.getParentFile() != null) {
         this.advancementFile.getParentFile().mkdirs();
      }

      JsonElement _snowmanxx = GSON.toJsonTree(_snowman);
      _snowmanxx.getAsJsonObject().addProperty("DataVersion", SharedConstants.getGameVersion().getWorldVersion());

      try (
         OutputStream _snowmanxxx = new FileOutputStream(this.advancementFile);
         Writer _snowmanxxxx = new OutputStreamWriter(_snowmanxxx, Charsets.UTF_8.newEncoder());
      ) {
         GSON.toJson(_snowmanxx, _snowmanxxxx);
      } catch (IOException var35) {
         LOGGER.error("Couldn't save player advancements to {}", this.advancementFile, var35);
      }
   }

   public boolean grantCriterion(Advancement advancement, String criterionName) {
      boolean _snowman = false;
      AdvancementProgress _snowmanx = this.getProgress(advancement);
      boolean _snowmanxx = _snowmanx.isDone();
      if (_snowmanx.obtain(criterionName)) {
         this.endTrackingCompleted(advancement);
         this.progressUpdates.add(advancement);
         _snowman = true;
         if (!_snowmanxx && _snowmanx.isDone()) {
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

      if (_snowmanx.isDone()) {
         this.updateDisplay(advancement);
      }

      return _snowman;
   }

   public boolean revokeCriterion(Advancement advancement, String criterionName) {
      boolean _snowman = false;
      AdvancementProgress _snowmanx = this.getProgress(advancement);
      if (_snowmanx.reset(criterionName)) {
         this.beginTracking(advancement);
         this.progressUpdates.add(advancement);
         _snowman = true;
      }

      if (!_snowmanx.isAnyObtained()) {
         this.updateDisplay(advancement);
      }

      return _snowman;
   }

   private void beginTracking(Advancement advancement) {
      AdvancementProgress _snowman = this.getProgress(advancement);
      if (!_snowman.isDone()) {
         for (Entry<String, AdvancementCriterion> _snowmanx : advancement.getCriteria().entrySet()) {
            CriterionProgress _snowmanxx = _snowman.getCriterionProgress(_snowmanx.getKey());
            if (_snowmanxx != null && !_snowmanxx.isObtained()) {
               CriterionConditions _snowmanxxx = _snowmanx.getValue().getConditions();
               if (_snowmanxxx != null) {
                  Criterion<CriterionConditions> _snowmanxxxx = Criteria.getById(_snowmanxxx.getId());
                  if (_snowmanxxxx != null) {
                     _snowmanxxxx.beginTrackingCondition(this, new Criterion.ConditionsContainer<>(_snowmanxxx, advancement, _snowmanx.getKey()));
                  }
               }
            }
         }
      }
   }

   private void endTrackingCompleted(Advancement advancement) {
      AdvancementProgress _snowman = this.getProgress(advancement);

      for (Entry<String, AdvancementCriterion> _snowmanx : advancement.getCriteria().entrySet()) {
         CriterionProgress _snowmanxx = _snowman.getCriterionProgress(_snowmanx.getKey());
         if (_snowmanxx != null && (_snowmanxx.isObtained() || _snowman.isDone())) {
            CriterionConditions _snowmanxxx = _snowmanx.getValue().getConditions();
            if (_snowmanxxx != null) {
               Criterion<CriterionConditions> _snowmanxxxx = Criteria.getById(_snowmanxxx.getId());
               if (_snowmanxxxx != null) {
                  _snowmanxxxx.endTrackingCondition(this, new Criterion.ConditionsContainer<>(_snowmanxxx, advancement, _snowmanx.getKey()));
               }
            }
         }
      }
   }

   public void sendUpdate(ServerPlayerEntity player) {
      if (this.dirty || !this.visibilityUpdates.isEmpty() || !this.progressUpdates.isEmpty()) {
         Map<Identifier, AdvancementProgress> _snowman = Maps.newHashMap();
         Set<Advancement> _snowmanx = Sets.newLinkedHashSet();
         Set<Identifier> _snowmanxx = Sets.newLinkedHashSet();

         for (Advancement _snowmanxxx : this.progressUpdates) {
            if (this.visibleAdvancements.contains(_snowmanxxx)) {
               _snowman.put(_snowmanxxx.getId(), this.advancementToProgress.get(_snowmanxxx));
            }
         }

         for (Advancement _snowmanxxxx : this.visibilityUpdates) {
            if (this.visibleAdvancements.contains(_snowmanxxxx)) {
               _snowmanx.add(_snowmanxxxx);
            } else {
               _snowmanxx.add(_snowmanxxxx.getId());
            }
         }

         if (this.dirty || !_snowman.isEmpty() || !_snowmanx.isEmpty() || !_snowmanxx.isEmpty()) {
            player.networkHandler.sendPacket(new AdvancementUpdateS2CPacket(this.dirty, _snowmanx, _snowmanxx, _snowman));
            this.visibilityUpdates.clear();
            this.progressUpdates.clear();
         }
      }

      this.dirty = false;
   }

   public void setDisplayTab(@Nullable Advancement advancement) {
      Advancement _snowman = this.currentDisplayTab;
      if (advancement != null && advancement.getParent() == null && advancement.getDisplay() != null) {
         this.currentDisplayTab = advancement;
      } else {
         this.currentDisplayTab = null;
      }

      if (_snowman != this.currentDisplayTab) {
         this.owner.networkHandler.sendPacket(new SelectAdvancementTabS2CPacket(this.currentDisplayTab == null ? null : this.currentDisplayTab.getId()));
      }
   }

   public AdvancementProgress getProgress(Advancement advancement) {
      AdvancementProgress _snowman = this.advancementToProgress.get(advancement);
      if (_snowman == null) {
         _snowman = new AdvancementProgress();
         this.initProgress(advancement, _snowman);
      }

      return _snowman;
   }

   private void initProgress(Advancement advancement, AdvancementProgress progress) {
      progress.init(advancement.getCriteria(), advancement.getRequirements());
      this.advancementToProgress.put(advancement, progress);
   }

   private void updateDisplay(Advancement advancement) {
      boolean _snowman = this.canSee(advancement);
      boolean _snowmanx = this.visibleAdvancements.contains(advancement);
      if (_snowman && !_snowmanx) {
         this.visibleAdvancements.add(advancement);
         this.visibilityUpdates.add(advancement);
         if (this.advancementToProgress.containsKey(advancement)) {
            this.progressUpdates.add(advancement);
         }
      } else if (!_snowman && _snowmanx) {
         this.visibleAdvancements.remove(advancement);
         this.visibilityUpdates.add(advancement);
      }

      if (_snowman != _snowmanx && advancement.getParent() != null) {
         this.updateDisplay(advancement.getParent());
      }

      for (Advancement _snowmanxx : advancement.getChildren()) {
         this.updateDisplay(_snowmanxx);
      }
   }

   private boolean canSee(Advancement advancement) {
      for (int _snowman = 0; advancement != null && _snowman <= 2; _snowman++) {
         if (_snowman == 0 && this.hasChildrenDone(advancement)) {
            return true;
         }

         if (advancement.getDisplay() == null) {
            return false;
         }

         AdvancementProgress _snowmanx = this.getProgress(advancement);
         if (_snowmanx.isDone()) {
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
      AdvancementProgress _snowman = this.getProgress(advancement);
      if (_snowman.isDone()) {
         return true;
      } else {
         for (Advancement _snowmanx : advancement.getChildren()) {
            if (this.hasChildrenDone(_snowmanx)) {
               return true;
            }
         }

         return false;
      }
   }
}
