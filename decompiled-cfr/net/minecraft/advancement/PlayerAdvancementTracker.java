/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.common.io.Files
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.google.gson.internal.Streams
 *  com.google.gson.reflect.TypeToken
 *  com.google.gson.stream.JsonReader
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementProgress;
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
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(AdvancementProgress.class, (Object)new AdvancementProgress.Serializer()).registerTypeAdapter(Identifier.class, (Object)new Identifier.Serializer()).setPrettyPrinting().create();
    private static final TypeToken<Map<Identifier, AdvancementProgress>> JSON_TYPE = new TypeToken<Map<Identifier, AdvancementProgress>>(){};
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

    public PlayerAdvancementTracker(DataFixer dataFixer, PlayerManager playerManager, ServerAdvancementLoader serverAdvancementLoader, File file, ServerPlayerEntity serverPlayerEntity) {
        this.field_25324 = dataFixer;
        this.field_25325 = playerManager;
        this.advancementFile = file;
        this.owner = serverPlayerEntity;
        this.load(serverAdvancementLoader);
    }

    public void setOwner(ServerPlayerEntity owner) {
        this.owner = owner;
    }

    public void clearCriteria() {
        for (Criterion<?> criterion : Criteria.getCriteria()) {
            criterion.endTracking(this);
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
        for (Advancement advancement : advancementLoader.getAdvancements()) {
            this.beginTracking(advancement);
        }
    }

    private void updateCompleted() {
        ArrayList arrayList = Lists.newArrayList();
        for (Map.Entry<Advancement, AdvancementProgress> _snowman2 : this.advancementToProgress.entrySet()) {
            if (!_snowman2.getValue().isDone()) continue;
            arrayList.add(_snowman2.getKey());
            this.progressUpdates.add((Advancement)_snowman2.getKey());
        }
        for (Map.Entry<Advancement, AdvancementProgress> _snowman2 : arrayList) {
            this.updateDisplay((Advancement)((Object)_snowman2));
        }
    }

    private void rewardEmptyAdvancements(ServerAdvancementLoader advancementLoader) {
        for (Advancement advancement : advancementLoader.getAdvancements()) {
            if (!advancement.getCriteria().isEmpty()) continue;
            this.grantCriterion(advancement, "");
            advancement.getRewards().apply(this.owner);
        }
    }

    private void load(ServerAdvancementLoader advancementLoader) {
        if (this.advancementFile.isFile()) {
            try (JsonReader jsonReader = new JsonReader((Reader)new StringReader(Files.toString((File)this.advancementFile, (Charset)StandardCharsets.UTF_8)));){
                jsonReader.setLenient(false);
                Dynamic dynamic = new Dynamic((DynamicOps)JsonOps.INSTANCE, (Object)Streams.parse((JsonReader)jsonReader));
                if (!dynamic.get("DataVersion").asNumber().result().isPresent()) {
                    dynamic = dynamic.set("DataVersion", dynamic.createInt(1343));
                }
                dynamic = this.field_25324.update(DataFixTypes.ADVANCEMENTS.getTypeReference(), dynamic, dynamic.get("DataVersion").asInt(0), SharedConstants.getGameVersion().getWorldVersion());
                dynamic = dynamic.remove("DataVersion");
                Map _snowman2 = (Map)GSON.getAdapter(JSON_TYPE).fromJsonTree((JsonElement)dynamic.getValue());
                if (_snowman2 == null) {
                    throw new JsonParseException("Found null for advancements");
                }
                Stream<Map.Entry> _snowman3 = _snowman2.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue));
                for (Map.Entry entry : _snowman3.collect(Collectors.toList())) {
                    Advancement advancement = advancementLoader.get((Identifier)entry.getKey());
                    if (advancement == null) {
                        LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", entry.getKey(), (Object)this.advancementFile);
                        continue;
                    }
                    this.initProgress(advancement, (AdvancementProgress)entry.getValue());
                }
            }
            catch (JsonParseException jsonParseException) {
                LOGGER.error("Couldn't parse player advancements in {}", (Object)this.advancementFile, (Object)jsonParseException);
            }
            catch (IOException iOException) {
                LOGGER.error("Couldn't access player advancements in {}", (Object)this.advancementFile, (Object)iOException);
            }
        }
        this.rewardEmptyAdvancements(advancementLoader);
        this.updateCompleted();
        this.beginTrackingAllAdvancements(advancementLoader);
    }

    public void save() {
        HashMap hashMap = Maps.newHashMap();
        for (Map.Entry<Advancement, AdvancementProgress> entry : this.advancementToProgress.entrySet()) {
            AdvancementProgress advancementProgress = entry.getValue();
            if (!advancementProgress.isAnyObtained()) continue;
            hashMap.put(entry.getKey().getId(), advancementProgress);
        }
        if (this.advancementFile.getParentFile() != null) {
            this.advancementFile.getParentFile().mkdirs();
        }
        JsonElement _snowman2 = GSON.toJsonTree((Object)hashMap);
        _snowman2.getAsJsonObject().addProperty("DataVersion", (Number)SharedConstants.getGameVersion().getWorldVersion());
        try {
            Map.Entry<Advancement, AdvancementProgress> entry;
            entry = new FileOutputStream(this.advancementFile);
            Throwable throwable = null;
            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)((Object)entry), Charsets.UTF_8.newEncoder());){
                GSON.toJson(_snowman2, (Appendable)outputStreamWriter);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (entry != null) {
                    if (throwable != null) {
                        try {
                            ((OutputStream)((Object)entry)).close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        ((OutputStream)((Object)entry)).close();
                    }
                }
            }
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't save player advancements to {}", (Object)this.advancementFile, (Object)iOException);
        }
    }

    public boolean grantCriterion(Advancement advancement, String criterionName) {
        boolean bl = false;
        AdvancementProgress _snowman2 = this.getProgress(advancement);
        _snowman = _snowman2.isDone();
        if (_snowman2.obtain(criterionName)) {
            this.endTrackingCompleted(advancement);
            this.progressUpdates.add(advancement);
            bl = true;
            if (!_snowman && _snowman2.isDone()) {
                advancement.getRewards().apply(this.owner);
                if (advancement.getDisplay() != null && advancement.getDisplay().shouldAnnounceToChat() && this.owner.world.getGameRules().getBoolean(GameRules.ANNOUNCE_ADVANCEMENTS)) {
                    this.field_25325.broadcastChatMessage(new TranslatableText("chat.type.advancement." + advancement.getDisplay().getFrame().getId(), this.owner.getDisplayName(), advancement.toHoverableText()), MessageType.SYSTEM, Util.NIL_UUID);
                }
            }
        }
        if (_snowman2.isDone()) {
            this.updateDisplay(advancement);
        }
        return bl;
    }

    public boolean revokeCriterion(Advancement advancement, String criterionName) {
        boolean bl = false;
        AdvancementProgress _snowman2 = this.getProgress(advancement);
        if (_snowman2.reset(criterionName)) {
            this.beginTracking(advancement);
            this.progressUpdates.add(advancement);
            bl = true;
        }
        if (!_snowman2.isAnyObtained()) {
            this.updateDisplay(advancement);
        }
        return bl;
    }

    private void beginTracking(Advancement advancement) {
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        if (advancementProgress.isDone()) {
            return;
        }
        for (Map.Entry<String, AdvancementCriterion> entry : advancement.getCriteria().entrySet()) {
            CriterionProgress criterionProgress = advancementProgress.getCriterionProgress(entry.getKey());
            if (criterionProgress == null || criterionProgress.isObtained() || (_snowman = entry.getValue().getConditions()) == null || (_snowman = Criteria.getById(_snowman.getId())) == null) continue;
            _snowman.beginTrackingCondition(this, new Criterion.ConditionsContainer<CriterionConditions>(_snowman, advancement, entry.getKey()));
        }
    }

    private void endTrackingCompleted(Advancement advancement) {
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        for (Map.Entry<String, AdvancementCriterion> entry : advancement.getCriteria().entrySet()) {
            CriterionProgress criterionProgress = advancementProgress.getCriterionProgress(entry.getKey());
            if (criterionProgress == null || !criterionProgress.isObtained() && !advancementProgress.isDone() || (_snowman = entry.getValue().getConditions()) == null || (_snowman = Criteria.getById(_snowman.getId())) == null) continue;
            _snowman.endTrackingCondition(this, new Criterion.ConditionsContainer<CriterionConditions>(_snowman, advancement, entry.getKey()));
        }
    }

    public void sendUpdate(ServerPlayerEntity player) {
        if (this.dirty || !this.visibilityUpdates.isEmpty() || !this.progressUpdates.isEmpty()) {
            HashMap hashMap = Maps.newHashMap();
            LinkedHashSet _snowman2 = Sets.newLinkedHashSet();
            LinkedHashSet _snowman3 = Sets.newLinkedHashSet();
            for (Advancement advancement : this.progressUpdates) {
                if (!this.visibleAdvancements.contains(advancement)) continue;
                hashMap.put(advancement.getId(), this.advancementToProgress.get(advancement));
            }
            for (Advancement advancement : this.visibilityUpdates) {
                if (this.visibleAdvancements.contains(advancement)) {
                    _snowman2.add(advancement);
                    continue;
                }
                _snowman3.add(advancement.getId());
            }
            if (this.dirty || !hashMap.isEmpty() || !_snowman2.isEmpty() || !_snowman3.isEmpty()) {
                player.networkHandler.sendPacket(new AdvancementUpdateS2CPacket(this.dirty, _snowman2, _snowman3, hashMap));
                this.visibilityUpdates.clear();
                this.progressUpdates.clear();
            }
        }
        this.dirty = false;
    }

    public void setDisplayTab(@Nullable Advancement advancement) {
        Advancement advancement2 = this.currentDisplayTab;
        this.currentDisplayTab = advancement != null && advancement.getParent() == null && advancement.getDisplay() != null ? advancement : null;
        if (advancement2 != this.currentDisplayTab) {
            this.owner.networkHandler.sendPacket(new SelectAdvancementTabS2CPacket(this.currentDisplayTab == null ? null : this.currentDisplayTab.getId()));
        }
    }

    public AdvancementProgress getProgress(Advancement advancement) {
        AdvancementProgress advancementProgress = this.advancementToProgress.get(advancement);
        if (advancementProgress == null) {
            advancementProgress = new AdvancementProgress();
            this.initProgress(advancement, advancementProgress);
        }
        return advancementProgress;
    }

    private void initProgress(Advancement advancement, AdvancementProgress progress) {
        progress.init(advancement.getCriteria(), advancement.getRequirements());
        this.advancementToProgress.put(advancement, progress);
    }

    private void updateDisplay(Advancement advancement) {
        boolean bl = this.canSee(advancement);
        _snowman = this.visibleAdvancements.contains(advancement);
        if (bl && !_snowman) {
            this.visibleAdvancements.add(advancement);
            this.visibilityUpdates.add(advancement);
            if (this.advancementToProgress.containsKey(advancement)) {
                this.progressUpdates.add(advancement);
            }
        } else if (!bl && _snowman) {
            this.visibleAdvancements.remove(advancement);
            this.visibilityUpdates.add(advancement);
        }
        if (bl != _snowman && advancement.getParent() != null) {
            this.updateDisplay(advancement.getParent());
        }
        for (Advancement advancement2 : advancement.getChildren()) {
            this.updateDisplay(advancement2);
        }
    }

    private boolean canSee(Advancement advancement) {
        for (int n = 0; advancement != null && n <= 2; advancement = advancement.getParent(), ++n) {
            if (n == 0 && this.hasChildrenDone(advancement)) {
                return true;
            }
            if (advancement.getDisplay() == null) {
                return false;
            }
            AdvancementProgress advancementProgress = this.getProgress(advancement);
            if (advancementProgress.isDone()) {
                return true;
            }
            if (!advancement.getDisplay().isHidden()) continue;
            return false;
        }
        return false;
    }

    private boolean hasChildrenDone(Advancement advancement) {
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        if (advancementProgress.isDone()) {
            return true;
        }
        for (Advancement advancement2 : advancement.getChildren()) {
            if (!this.hasChildrenDone(advancement2)) continue;
            return true;
        }
        return false;
    }
}

