package net.minecraft.client.realms;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.dto.BackupList;
import net.minecraft.client.realms.dto.Ops;
import net.minecraft.client.realms.dto.PendingInvite;
import net.minecraft.client.realms.dto.PendingInvitesList;
import net.minecraft.client.realms.dto.PingResult;
import net.minecraft.client.realms.dto.PlayerInfo;
import net.minecraft.client.realms.dto.RealmsDescriptionDto;
import net.minecraft.client.realms.dto.RealmsNews;
import net.minecraft.client.realms.dto.RealmsServer;
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
   public static RealmsClient.Environment currentEnvironment = RealmsClient.Environment.PRODUCTION;
   private static boolean initialized;
   private static final Logger LOGGER = LogManager.getLogger();
   private final String sessionId;
   private final String username;
   private final MinecraftClient client;
   private static final CheckedGson JSON = new CheckedGson();

   public static RealmsClient createRealmsClient() {
      MinecraftClient _snowman = MinecraftClient.getInstance();
      String _snowmanx = _snowman.getSession().getUsername();
      String _snowmanxx = _snowman.getSession().getSessionId();
      if (!initialized) {
         initialized = true;
         String _snowmanxxx = System.getenv("realms.environment");
         if (_snowmanxxx == null) {
            _snowmanxxx = System.getProperty("realms.environment");
         }

         if (_snowmanxxx != null) {
            if ("LOCAL".equals(_snowmanxxx)) {
               switchToLocal();
            } else if ("STAGE".equals(_snowmanxxx)) {
               switchToStage();
            }
         }
      }

      return new RealmsClient(_snowmanxx, _snowmanx, _snowman);
   }

   public static void switchToStage() {
      currentEnvironment = RealmsClient.Environment.STAGE;
   }

   public static void switchToProd() {
      currentEnvironment = RealmsClient.Environment.PRODUCTION;
   }

   public static void switchToLocal() {
      currentEnvironment = RealmsClient.Environment.LOCAL;
   }

   public RealmsClient(String sessionId, String username, MinecraftClient client) {
      this.sessionId = sessionId;
      this.username = username;
      this.client = client;
      RealmsClientConfig.setProxy(client.getNetworkProxy());
   }

   public RealmsServerList listWorlds() throws RealmsServiceException {
      String _snowman = this.url("worlds");
      String _snowmanx = this.execute(Request.get(_snowman));
      return RealmsServerList.parse(_snowmanx);
   }

   public RealmsServer getOwnWorld(long worldId) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$ID".replace("$ID", String.valueOf(worldId)));
      String _snowmanx = this.execute(Request.get(_snowman));
      return RealmsServer.parse(_snowmanx);
   }

   public RealmsServerPlayerLists getLiveStats() throws RealmsServiceException {
      String _snowman = this.url("activities/liveplayerlist");
      String _snowmanx = this.execute(Request.get(_snowman));
      return RealmsServerPlayerLists.parse(_snowmanx);
   }

   public net.minecraft.client.realms.dto.RealmsServerAddress join(long worldId) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/v1/$ID/join/pc".replace("$ID", "" + worldId));
      String _snowmanx = this.execute(Request.get(_snowman, 5000, 30000));
      return net.minecraft.client.realms.dto.RealmsServerAddress.parse(_snowmanx);
   }

   public void initializeWorld(long worldId, String name, String motd) throws RealmsServiceException {
      RealmsDescriptionDto _snowman = new RealmsDescriptionDto(name, motd);
      String _snowmanx = this.url("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanxx = JSON.toJson(_snowman);
      this.execute(Request.post(_snowmanx, _snowmanxx, 5000, 10000));
   }

   public Boolean mcoEnabled() throws RealmsServiceException {
      String _snowman = this.url("mco/available");
      String _snowmanx = this.execute(Request.get(_snowman));
      return Boolean.valueOf(_snowmanx);
   }

   public Boolean stageAvailable() throws RealmsServiceException {
      String _snowman = this.url("mco/stageAvailable");
      String _snowmanx = this.execute(Request.get(_snowman));
      return Boolean.valueOf(_snowmanx);
   }

   public RealmsClient.CompatibleVersionResponse clientCompatible() throws RealmsServiceException {
      String _snowman = this.url("mco/client/compatible");
      String _snowmanx = this.execute(Request.get(_snowman));

      try {
         return RealmsClient.CompatibleVersionResponse.valueOf(_snowmanx);
      } catch (IllegalArgumentException var5) {
         throw new RealmsServiceException(500, "Could not check compatible version, got response: " + _snowmanx, -1, "");
      }
   }

   public void uninvite(long worldId, String profileUuid) throws RealmsServiceException {
      String _snowman = this.url("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$UUID", profileUuid));
      this.execute(Request.delete(_snowman));
   }

   public void uninviteMyselfFrom(long worldId) throws RealmsServiceException {
      String _snowman = this.url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
      this.execute(Request.delete(_snowman));
   }

   public RealmsServer invite(long worldId, String profileName) throws RealmsServiceException {
      PlayerInfo _snowman = new PlayerInfo();
      _snowman.setName(profileName);
      String _snowmanx = this.url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanxx = this.execute(Request.post(_snowmanx, JSON.toJson(_snowman)));
      return RealmsServer.parse(_snowmanxx);
   }

   public BackupList backupsFor(long worldId) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanx = this.execute(Request.get(_snowman));
      return BackupList.parse(_snowmanx);
   }

   public void update(long worldId, String name, String motd) throws RealmsServiceException {
      RealmsDescriptionDto _snowman = new RealmsDescriptionDto(name, motd);
      String _snowmanx = this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
      this.execute(Request.post(_snowmanx, JSON.toJson(_snowman)));
   }

   public void updateSlot(long worldId, int slot, RealmsWorldOptions options) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$SLOT_ID", String.valueOf(slot)));
      String _snowmanx = options.toJson();
      this.execute(Request.post(_snowman, _snowmanx));
   }

   public boolean switchSlot(long worldId, int slot) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$SLOT_ID", String.valueOf(slot)));
      String _snowmanx = this.execute(Request.put(_snowman, ""));
      return Boolean.valueOf(_snowmanx);
   }

   public void restoreWorld(long worldId, String backupId) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(worldId)), "backupId=" + backupId);
      this.execute(Request.put(_snowman, "", 40000, 600000));
   }

   public WorldTemplatePaginatedList fetchWorldTemplates(int page, int pageSize, RealmsServer.WorldType type) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/templates/$WORLD_TYPE".replace("$WORLD_TYPE", type.toString()), String.format("page=%d&pageSize=%d", page, pageSize));
      String _snowmanx = this.execute(Request.get(_snowman));
      return WorldTemplatePaginatedList.parse(_snowmanx);
   }

   public Boolean putIntoMinigameMode(long worldId, String minigameId) throws RealmsServiceException {
      String _snowman = "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", minigameId).replace("$WORLD_ID", String.valueOf(worldId));
      String _snowmanx = this.url("worlds" + _snowman);
      return Boolean.valueOf(this.execute(Request.put(_snowmanx, "")));
   }

   public Ops op(long worldId, String profileUuid) throws RealmsServiceException {
      String _snowman = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$PROFILE_UUID", profileUuid);
      String _snowmanx = this.url("ops" + _snowman);
      return Ops.parse(this.execute(Request.post(_snowmanx, "")));
   }

   public Ops deop(long worldId, String profileUuid) throws RealmsServiceException {
      String _snowman = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(worldId)).replace("$PROFILE_UUID", profileUuid);
      String _snowmanx = this.url("ops" + _snowman);
      return Ops.parse(this.execute(Request.delete(_snowmanx)));
   }

   public Boolean open(long worldId) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanx = this.execute(Request.put(_snowman, ""));
      return Boolean.valueOf(_snowmanx);
   }

   public Boolean close(long worldId) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanx = this.execute(Request.put(_snowman, ""));
      return Boolean.valueOf(_snowmanx);
   }

   public Boolean resetWorldWithSeed(long worldId, String seed, Integer levelType, boolean generateStructures) throws RealmsServiceException {
      RealmsWorldResetDto _snowman = new RealmsWorldResetDto(seed, -1L, levelType, generateStructures);
      String _snowmanx = this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanxx = this.execute(Request.post(_snowmanx, JSON.toJson(_snowman), 30000, 80000));
      return Boolean.valueOf(_snowmanxx);
   }

   public Boolean resetWorldWithTemplate(long worldId, String worldTemplateId) throws RealmsServiceException {
      RealmsWorldResetDto _snowman = new RealmsWorldResetDto(null, Long.valueOf(worldTemplateId), -1, false);
      String _snowmanx = this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanxx = this.execute(Request.post(_snowmanx, JSON.toJson(_snowman), 30000, 80000));
      return Boolean.valueOf(_snowmanxx);
   }

   public Subscription subscriptionFor(long worldId) throws RealmsServiceException {
      String _snowman = this.url("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
      String _snowmanx = this.execute(Request.get(_snowman));
      return Subscription.parse(_snowmanx);
   }

   public int pendingInvitesCount() throws RealmsServiceException {
      return this.pendingInvites().pendingInvites.size();
   }

   public PendingInvitesList pendingInvites() throws RealmsServiceException {
      String _snowman = this.url("invites/pending");
      String _snowmanx = this.execute(Request.get(_snowman));
      PendingInvitesList _snowmanxx = PendingInvitesList.parse(_snowmanx);
      _snowmanxx.pendingInvites.removeIf(this::isOwnerBlocked);
      return _snowmanxx;
   }

   private boolean isOwnerBlocked(PendingInvite pendingInvite) {
      try {
         UUID _snowman = UUID.fromString(pendingInvite.worldOwnerUuid);
         return this.client.getSocialInteractionsManager().isPlayerBlocked(_snowman);
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   public void acceptInvitation(String invitationId) throws RealmsServiceException {
      String _snowman = this.url("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", invitationId));
      this.execute(Request.put(_snowman, ""));
   }

   public WorldDownload download(long worldId, int slotId) throws RealmsServiceException {
      String _snowman = this.url(
         "worlds" + "/$WORLD_ID/slot/$SLOT_ID/download".replace("$WORLD_ID", String.valueOf(worldId)).replace("$SLOT_ID", String.valueOf(slotId))
      );
      String _snowmanx = this.execute(Request.get(_snowman));
      return WorldDownload.parse(_snowmanx);
   }

   @Nullable
   public UploadInfo upload(long worldId, @Nullable String token) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf(worldId)));
      return UploadInfo.parse(this.execute(Request.put(_snowman, UploadInfo.createRequestContent(token))));
   }

   public void rejectInvitation(String invitationId) throws RealmsServiceException {
      String _snowman = this.url("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", invitationId));
      this.execute(Request.put(_snowman, ""));
   }

   public void agreeToTos() throws RealmsServiceException {
      String _snowman = this.url("mco/tos/agreed");
      this.execute(Request.post(_snowman, ""));
   }

   public RealmsNews getNews() throws RealmsServiceException {
      String _snowman = this.url("mco/v1/news");
      String _snowmanx = this.execute(Request.get(_snowman, 5000, 10000));
      return RealmsNews.parse(_snowmanx);
   }

   public void sendPingResults(PingResult pingResult) throws RealmsServiceException {
      String _snowman = this.url("regions/ping/stat");
      this.execute(Request.post(_snowman, JSON.toJson(pingResult)));
   }

   public Boolean trialAvailable() throws RealmsServiceException {
      String _snowman = this.url("trial");
      String _snowmanx = this.execute(Request.get(_snowman));
      return Boolean.valueOf(_snowmanx);
   }

   public void deleteWorld(long worldId) throws RealmsServiceException {
      String _snowman = this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(worldId)));
      this.execute(Request.delete(_snowman));
   }

   @Nullable
   private String url(String path) {
      return this.url(path, null);
   }

   @Nullable
   private String url(String path, @Nullable String queryString) {
      try {
         return new URI(currentEnvironment.protocol, currentEnvironment.baseUrl, "/" + path, queryString, null).toASCIIString();
      } catch (URISyntaxException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private String execute(Request<?> r) throws RealmsServiceException {
      r.cookie("sid", this.sessionId);
      r.cookie("user", this.username);
      r.cookie("version", SharedConstants.getGameVersion().getName());

      try {
         int _snowman = r.responseCode();
         if (_snowman != 503 && _snowman != 277) {
            String _snowmanx = r.text();
            if (_snowman >= 200 && _snowman < 300) {
               return _snowmanx;
            } else if (_snowman == 401) {
               String _snowmanxx = r.getHeader("WWW-Authenticate");
               LOGGER.info("Could not authorize you against Realms server: " + _snowmanxx);
               throw new RealmsServiceException(_snowman, _snowmanxx, -1, _snowmanxx);
            } else if (_snowmanx != null && _snowmanx.length() != 0) {
               RealmsError _snowmanxx = RealmsError.create(_snowmanx);
               LOGGER.error("Realms http code: " + _snowman + " -  error code: " + _snowmanxx.getErrorCode() + " -  message: " + _snowmanxx.getErrorMessage() + " - raw body: " + _snowmanx);
               throw new RealmsServiceException(_snowman, _snowmanx, _snowmanxx);
            } else {
               LOGGER.error("Realms error code: " + _snowman + " message: " + _snowmanx);
               throw new RealmsServiceException(_snowman, _snowmanx, _snowman, "");
            }
         } else {
            int _snowmanx = r.getRetryAfterHeader();
            throw new RetryCallException(_snowmanx, _snowman);
         }
      } catch (RealmsHttpException var5) {
         throw new RealmsServiceException(500, "Could not connect to Realms: " + var5.getMessage(), -1, "");
      }
   }

   public static enum CompatibleVersionResponse {
      COMPATIBLE,
      OUTDATED,
      OTHER;

      private CompatibleVersionResponse() {
      }
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
