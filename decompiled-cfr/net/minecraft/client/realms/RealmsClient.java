/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.CheckedGson;
import net.minecraft.client.realms.RealmsClientConfig;
import net.minecraft.client.realms.RealmsError;
import net.minecraft.client.realms.Request;
import net.minecraft.client.realms.dto.BackupList;
import net.minecraft.client.realms.dto.Ops;
import net.minecraft.client.realms.dto.PendingInvite;
import net.minecraft.client.realms.dto.PendingInvitesList;
import net.minecraft.client.realms.dto.PingResult;
import net.minecraft.client.realms.dto.PlayerInfo;
import net.minecraft.client.realms.dto.RealmsDescriptionDto;
import net.minecraft.client.realms.dto.RealmsNews;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsServerAddress;
import net.minecraft.client.realms.dto.RealmsServerList;
import net.minecraft.client.realms.dto.RealmsServerPlayerLists;
import net.minecraft.client.realms.dto.RealmsWorldOptions;
import net.minecraft.client.realms.dto.RealmsWorldResetDto;
import net.minecraft.client.realms.dto.Subscription;
import net.minecraft.client.realms.dto.UploadInfo;
import net.minecraft.client.realms.dto.WorldDownload;
import net.minecraft.client.realms.dto.WorldTemplatePaginatedList;
import net.minecraft.client.realms.exception.RealmsHttpException;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.exception.RetryCallException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsClient {
    public static Environment currentEnvironment = Environment.PRODUCTION;
    private static boolean initialized;
    private static final Logger LOGGER;
    private final String sessionId;
    private final String username;
    private final MinecraftClient client;
    private static final CheckedGson JSON;

    public static RealmsClient createRealmsClient() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        String _snowman2 = minecraftClient.getSession().getUsername();
        String _snowman3 = minecraftClient.getSession().getSessionId();
        if (!initialized) {
            initialized = true;
            String string = System.getenv("realms.environment");
            if (string == null) {
                string = System.getProperty("realms.environment");
            }
            if (string != null) {
                if ("LOCAL".equals(string)) {
                    RealmsClient.switchToLocal();
                } else if ("STAGE".equals(string)) {
                    RealmsClient.switchToStage();
                }
            }
        }
        return new RealmsClient(_snowman3, _snowman2, minecraftClient);
    }

    public static void switchToStage() {
        currentEnvironment = Environment.STAGE;
    }

    public static void switchToProd() {
        currentEnvironment = Environment.PRODUCTION;
    }

    public static void switchToLocal() {
        currentEnvironment = Environment.LOCAL;
    }

    public RealmsClient(String sessionId, String username, MinecraftClient client) {
        this.sessionId = sessionId;
        this.username = username;
        this.client = client;
        RealmsClientConfig.setProxy(client.getNetworkProxy());
    }

    public RealmsServerList listWorlds() throws RealmsServiceException {
        String string = this.url("worlds");
        _snowman = this.execute(Request.get(string));
        return RealmsServerList.parse(_snowman);
    }

    public RealmsServer getOwnWorld(long worldId) throws RealmsServiceException {
        String string = this.url("worlds" + "/$ID".replace("$ID", String.valueOf(worldId)));
        _snowman = this.execute(Request.get(string));
        return RealmsServer.parse(_snowman);
    }

    public RealmsServerPlayerLists getLiveStats() throws RealmsServiceException {
        String string = this.url("activities/liveplayerlist");
        _snowman = this.execute(Request.get(string));
        return RealmsServerPlayerLists.parse(_snowman);
    }

    public RealmsServerAddress join(long worldId) throws RealmsServiceException {
        String string = this.url("worlds" + "/v1/$ID/join/pc".replace("$ID", "" + worldId));
        _snowman = this.execute(Request.get(string, 5000, 30000));
        return RealmsServerAddress.parse(_snowman);
    }

    public void initializeWorld(long worldId, String name, String motd) throws RealmsServiceException {
        RealmsDescriptionDto realmsDescriptionDto = new RealmsDescriptionDto(name, motd);
        String _snowman2 = this.url("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf(worldId)));
        String _snowman3 = JSON.toJson(realmsDescriptionDto);
        this.execute(Request.post(_snowman2, _snowman3, 5000, 10000));
    }

    public Boolean mcoEnabled() throws RealmsServiceException {
        String string = this.url("mco/available");
        _snowman = this.execute(Request.get(string));
        return Boolean.valueOf(_snowman);
    }

    public Boolean stageAvailable() throws RealmsServiceException {
        String string = this.url("mco/stageAvailable");
        _snowman = this.execute(Request.get(string));
        return Boolean.valueOf(_snowman);
    }

    public CompatibleVersionResponse clientCompatible() throws RealmsServiceException {
        String string = this.url("mco/client/compatible");
        _snowman = this.execute(Request.get(string));
        try {
            CompatibleVersionResponse compatibleVersionResponse = CompatibleVersionResponse.valueOf(_snowman);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new RealmsServiceException(500, "Could not check compatible version, got response: " + _snowman, -1, "");
        }
        return compatibleVersionResponse;
    }

    public void uninvite(long worldId, String profileUuid) throws RealmsServiceException {
        String string = this.url("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$UUID", profileUuid));
        this.execute(Request.delete(string));
    }

    public void uninviteMyselfFrom(long worldId) throws RealmsServiceException {
        String string = this.url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
        this.execute(Request.delete(string));
    }

    public RealmsServer invite(long worldId, String profileName) throws RealmsServiceException {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setName(profileName);
        String _snowman2 = this.url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
        String _snowman3 = this.execute(Request.post(_snowman2, JSON.toJson(playerInfo)));
        return RealmsServer.parse(_snowman3);
    }

    public BackupList backupsFor(long worldId) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(worldId)));
        _snowman = this.execute(Request.get(string));
        return BackupList.parse(_snowman);
    }

    public void update(long worldId, String name, String motd) throws RealmsServiceException {
        RealmsDescriptionDto realmsDescriptionDto = new RealmsDescriptionDto(name, motd);
        String _snowman2 = this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
        this.execute(Request.post(_snowman2, JSON.toJson(realmsDescriptionDto)));
    }

    public void updateSlot(long worldId, int slot, RealmsWorldOptions options) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$SLOT_ID", String.valueOf(slot)));
        _snowman = options.toJson();
        this.execute(Request.post(string, _snowman));
    }

    public boolean switchSlot(long worldId, int slot) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$SLOT_ID", String.valueOf(slot)));
        _snowman = this.execute(Request.put(string, ""));
        return Boolean.valueOf(_snowman);
    }

    public void restoreWorld(long worldId, String backupId) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(worldId)), "backupId=" + backupId);
        this.execute(Request.put(string, "", 40000, 600000));
    }

    public WorldTemplatePaginatedList fetchWorldTemplates(int page, int pageSize, RealmsServer.WorldType type) throws RealmsServiceException {
        String string = this.url("worlds" + "/templates/$WORLD_TYPE".replace("$WORLD_TYPE", type.toString()), String.format("page=%d&pageSize=%d", page, pageSize));
        _snowman = this.execute(Request.get(string));
        return WorldTemplatePaginatedList.parse(_snowman);
    }

    public Boolean putIntoMinigameMode(long worldId, String minigameId) throws RealmsServiceException {
        String string = "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", minigameId).replace("$WORLD_ID", String.valueOf(worldId));
        _snowman = this.url("worlds" + string);
        return Boolean.valueOf(this.execute(Request.put(_snowman, "")));
    }

    public Ops op(long worldId, String profileUuid) throws RealmsServiceException {
        String string = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$PROFILE_UUID", profileUuid);
        _snowman = this.url("ops" + string);
        return Ops.parse(this.execute(Request.post(_snowman, "")));
    }

    public Ops deop(long worldId, String profileUuid) throws RealmsServiceException {
        String string = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$PROFILE_UUID", profileUuid);
        _snowman = this.url("ops" + string);
        return Ops.parse(this.execute(Request.delete(_snowman)));
    }

    public Boolean open(long worldId) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(worldId)));
        _snowman = this.execute(Request.put(string, ""));
        return Boolean.valueOf(_snowman);
    }

    public Boolean close(long worldId) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(worldId)));
        _snowman = this.execute(Request.put(string, ""));
        return Boolean.valueOf(_snowman);
    }

    public Boolean resetWorldWithSeed(long worldId, String seed, Integer levelType, boolean generateStructures) throws RealmsServiceException {
        RealmsWorldResetDto realmsWorldResetDto = new RealmsWorldResetDto(seed, -1L, levelType, generateStructures);
        String _snowman2 = this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(worldId)));
        String _snowman3 = this.execute(Request.post(_snowman2, JSON.toJson(realmsWorldResetDto), 30000, 80000));
        return Boolean.valueOf(_snowman3);
    }

    public Boolean resetWorldWithTemplate(long worldId, String worldTemplateId) throws RealmsServiceException {
        RealmsWorldResetDto realmsWorldResetDto = new RealmsWorldResetDto(null, Long.valueOf(worldTemplateId), -1, false);
        String _snowman2 = this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(worldId)));
        String _snowman3 = this.execute(Request.post(_snowman2, JSON.toJson(realmsWorldResetDto), 30000, 80000));
        return Boolean.valueOf(_snowman3);
    }

    public Subscription subscriptionFor(long worldId) throws RealmsServiceException {
        String string = this.url("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
        _snowman = this.execute(Request.get(string));
        return Subscription.parse(_snowman);
    }

    public int pendingInvitesCount() throws RealmsServiceException {
        return this.pendingInvites().pendingInvites.size();
    }

    public PendingInvitesList pendingInvites() throws RealmsServiceException {
        String string = this.url("invites/pending");
        _snowman = this.execute(Request.get(string));
        PendingInvitesList _snowman2 = PendingInvitesList.parse(_snowman);
        _snowman2.pendingInvites.removeIf(this::isOwnerBlocked);
        return _snowman2;
    }

    private boolean isOwnerBlocked(PendingInvite pendingInvite) {
        try {
            UUID uUID = UUID.fromString(pendingInvite.worldOwnerUuid);
            return this.client.getSocialInteractionsManager().isPlayerBlocked(uUID);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    public void acceptInvitation(String invitationId) throws RealmsServiceException {
        String string = this.url("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", invitationId));
        this.execute(Request.put(string, ""));
    }

    public WorldDownload download(long worldId, int slotId) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID/download".replace("$WORLD_ID", String.valueOf(worldId)).replace("$SLOT_ID", String.valueOf(slotId)));
        _snowman = this.execute(Request.get(string));
        return WorldDownload.parse(_snowman);
    }

    @Nullable
    public UploadInfo upload(long worldId, @Nullable String token) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf(worldId)));
        return UploadInfo.parse(this.execute(Request.put(string, UploadInfo.createRequestContent(token))));
    }

    public void rejectInvitation(String invitationId) throws RealmsServiceException {
        String string = this.url("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", invitationId));
        this.execute(Request.put(string, ""));
    }

    public void agreeToTos() throws RealmsServiceException {
        String string = this.url("mco/tos/agreed");
        this.execute(Request.post(string, ""));
    }

    public RealmsNews getNews() throws RealmsServiceException {
        String string = this.url("mco/v1/news");
        _snowman = this.execute(Request.get(string, 5000, 10000));
        return RealmsNews.parse(_snowman);
    }

    public void sendPingResults(PingResult pingResult) throws RealmsServiceException {
        String string = this.url("regions/ping/stat");
        this.execute(Request.post(string, JSON.toJson(pingResult)));
    }

    public Boolean trialAvailable() throws RealmsServiceException {
        String string = this.url("trial");
        _snowman = this.execute(Request.get(string));
        return Boolean.valueOf(_snowman);
    }

    public void deleteWorld(long worldId) throws RealmsServiceException {
        String string = this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
        this.execute(Request.delete(string));
    }

    @Nullable
    private String url(String path) {
        return this.url(path, null);
    }

    @Nullable
    private String url(String path, @Nullable String queryString) {
        try {
            return new URI(RealmsClient.currentEnvironment.protocol, RealmsClient.currentEnvironment.baseUrl, "/" + path, queryString, null).toASCIIString();
        }
        catch (URISyntaxException uRISyntaxException) {
            uRISyntaxException.printStackTrace();
            return null;
        }
    }

    private String execute(Request<?> r) throws RealmsServiceException {
        r.cookie("sid", this.sessionId);
        r.cookie("user", this.username);
        r.cookie("version", SharedConstants.getGameVersion().getName());
        try {
            int n = r.responseCode();
            if (n == 503 || n == 277) {
                _snowman = r.getRetryAfterHeader();
                throw new RetryCallException(_snowman, n);
            }
            String _snowman2 = r.text();
            if (n < 200 || n >= 300) {
                if (n == 401) {
                    String string = r.getHeader("WWW-Authenticate");
                    LOGGER.info("Could not authorize you against Realms server: " + string);
                    throw new RealmsServiceException(n, string, -1, string);
                }
                if (_snowman2 == null || _snowman2.length() == 0) {
                    LOGGER.error("Realms error code: " + n + " message: " + _snowman2);
                    throw new RealmsServiceException(n, _snowman2, n, "");
                }
                RealmsError realmsError = RealmsError.create(_snowman2);
                LOGGER.error("Realms http code: " + n + " -  error code: " + realmsError.getErrorCode() + " -  message: " + realmsError.getErrorMessage() + " - raw body: " + _snowman2);
                throw new RealmsServiceException(n, _snowman2, realmsError);
            }
            return _snowman2;
        }
        catch (RealmsHttpException realmsHttpException) {
            throw new RealmsServiceException(500, "Could not connect to Realms: " + realmsHttpException.getMessage(), -1, "");
        }
    }

    static {
        LOGGER = LogManager.getLogger();
        JSON = new CheckedGson();
    }

    public static enum CompatibleVersionResponse {
        COMPATIBLE,
        OUTDATED,
        OTHER;

    }

    public static enum Environment {
        PRODUCTION("pc.realms.minecraft.net", "https"),
        STAGE("pc-stage.realms.minecraft.net", "https"),
        LOCAL("localhost:8080", "http");

        public String baseUrl;
        public String protocol;

        private Environment(String baseUrl, String protocol) {
            this.baseUrl = baseUrl;
            this.protocol = protocol;
        }
    }
}

