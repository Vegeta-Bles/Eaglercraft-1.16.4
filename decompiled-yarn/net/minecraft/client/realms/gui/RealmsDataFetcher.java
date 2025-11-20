package net.minecraft.client.realms.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsNews;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsServerPlayerLists;
import net.minecraft.client.realms.util.RealmsPersistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsDataFetcher {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
   private volatile boolean stopped = true;
   private final Runnable serverListUpdateTask = new RealmsDataFetcher.ServerListUpdateTask();
   private final Runnable pendingInviteUpdateTask = new RealmsDataFetcher.PendingInviteUpdateTask();
   private final Runnable trialAvailabilityTask = new RealmsDataFetcher.TrialAvailabilityTask();
   private final Runnable liveStatsTask = new RealmsDataFetcher.LiveStatsTask();
   private final Runnable unreadNewsTask = new RealmsDataFetcher.UnreadNewsTask();
   private final Set<RealmsServer> removedServers = Sets.newHashSet();
   private List<RealmsServer> servers = Lists.newArrayList();
   private RealmsServerPlayerLists livestats;
   private int pendingInvitesCount;
   private boolean trialAvailable;
   private boolean hasUnreadNews;
   private String newsLink;
   private ScheduledFuture<?> serverListScheduledFuture;
   private ScheduledFuture<?> pendingInviteScheduledFuture;
   private ScheduledFuture<?> trialAvailableScheduledFuture;
   private ScheduledFuture<?> liveStatsScheduledFuture;
   private ScheduledFuture<?> unreadNewsScheduledFuture;
   private final Map<RealmsDataFetcher.Task, Boolean> fetchStatus = new ConcurrentHashMap<>(RealmsDataFetcher.Task.values().length);

   public RealmsDataFetcher() {
   }

   public boolean isStopped() {
      return this.stopped;
   }

   public synchronized void init() {
      if (this.stopped) {
         this.stopped = false;
         this.cancelTasks();
         this.scheduleTasks();
      }
   }

   public synchronized void initWithSpecificTaskList() {
      if (this.stopped) {
         this.stopped = false;
         this.cancelTasks();
         this.fetchStatus.put(RealmsDataFetcher.Task.PENDING_INVITE, false);
         this.pendingInviteScheduledFuture = this.scheduler.scheduleAtFixedRate(this.pendingInviteUpdateTask, 0L, 10L, TimeUnit.SECONDS);
         this.fetchStatus.put(RealmsDataFetcher.Task.TRIAL_AVAILABLE, false);
         this.trialAvailableScheduledFuture = this.scheduler.scheduleAtFixedRate(this.trialAvailabilityTask, 0L, 60L, TimeUnit.SECONDS);
         this.fetchStatus.put(RealmsDataFetcher.Task.UNREAD_NEWS, false);
         this.unreadNewsScheduledFuture = this.scheduler.scheduleAtFixedRate(this.unreadNewsTask, 0L, 300L, TimeUnit.SECONDS);
      }
   }

   public boolean isFetchedSinceLastTry(RealmsDataFetcher.Task task) {
      Boolean _snowman = this.fetchStatus.get(task);
      return _snowman == null ? false : _snowman;
   }

   public void markClean() {
      for (RealmsDataFetcher.Task _snowman : this.fetchStatus.keySet()) {
         this.fetchStatus.put(_snowman, false);
      }
   }

   public synchronized void forceUpdate() {
      this.stop();
      this.init();
   }

   public synchronized List<RealmsServer> getServers() {
      return Lists.newArrayList(this.servers);
   }

   public synchronized int getPendingInvitesCount() {
      return this.pendingInvitesCount;
   }

   public synchronized boolean isTrialAvailable() {
      return this.trialAvailable;
   }

   public synchronized RealmsServerPlayerLists getLivestats() {
      return this.livestats;
   }

   public synchronized boolean hasUnreadNews() {
      return this.hasUnreadNews;
   }

   public synchronized String newsLink() {
      return this.newsLink;
   }

   public synchronized void stop() {
      this.stopped = true;
      this.cancelTasks();
   }

   private void scheduleTasks() {
      for (RealmsDataFetcher.Task _snowman : RealmsDataFetcher.Task.values()) {
         this.fetchStatus.put(_snowman, false);
      }

      this.serverListScheduledFuture = this.scheduler.scheduleAtFixedRate(this.serverListUpdateTask, 0L, 60L, TimeUnit.SECONDS);
      this.pendingInviteScheduledFuture = this.scheduler.scheduleAtFixedRate(this.pendingInviteUpdateTask, 0L, 10L, TimeUnit.SECONDS);
      this.trialAvailableScheduledFuture = this.scheduler.scheduleAtFixedRate(this.trialAvailabilityTask, 0L, 60L, TimeUnit.SECONDS);
      this.liveStatsScheduledFuture = this.scheduler.scheduleAtFixedRate(this.liveStatsTask, 0L, 10L, TimeUnit.SECONDS);
      this.unreadNewsScheduledFuture = this.scheduler.scheduleAtFixedRate(this.unreadNewsTask, 0L, 300L, TimeUnit.SECONDS);
   }

   private void cancelTasks() {
      try {
         if (this.serverListScheduledFuture != null) {
            this.serverListScheduledFuture.cancel(false);
         }

         if (this.pendingInviteScheduledFuture != null) {
            this.pendingInviteScheduledFuture.cancel(false);
         }

         if (this.trialAvailableScheduledFuture != null) {
            this.trialAvailableScheduledFuture.cancel(false);
         }

         if (this.liveStatsScheduledFuture != null) {
            this.liveStatsScheduledFuture.cancel(false);
         }

         if (this.unreadNewsScheduledFuture != null) {
            this.unreadNewsScheduledFuture.cancel(false);
         }
      } catch (Exception var2) {
         LOGGER.error("Failed to cancel Realms tasks", var2);
      }
   }

   private synchronized void setServers(List<RealmsServer> newServers) {
      int _snowman = 0;

      for (RealmsServer _snowmanx : this.removedServers) {
         if (newServers.remove(_snowmanx)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         this.removedServers.clear();
      }

      this.servers = newServers;
   }

   public synchronized void removeItem(RealmsServer server) {
      this.servers.remove(server);
      this.removedServers.add(server);
   }

   private boolean isActive() {
      return !this.stopped;
   }

   class LiveStatsTask implements Runnable {
      private LiveStatsTask() {
      }

      @Override
      public void run() {
         if (RealmsDataFetcher.this.isActive()) {
            this.getLiveStats();
         }
      }

      private void getLiveStats() {
         try {
            RealmsClient _snowman = RealmsClient.createRealmsClient();
            RealmsDataFetcher.this.livestats = _snowman.getLiveStats();
            RealmsDataFetcher.this.fetchStatus.put(RealmsDataFetcher.Task.LIVE_STATS, true);
         } catch (Exception var2) {
            RealmsDataFetcher.LOGGER.error("Couldn't get live stats", var2);
         }
      }
   }

   class PendingInviteUpdateTask implements Runnable {
      private PendingInviteUpdateTask() {
      }

      @Override
      public void run() {
         if (RealmsDataFetcher.this.isActive()) {
            this.updatePendingInvites();
         }
      }

      private void updatePendingInvites() {
         try {
            RealmsClient _snowman = RealmsClient.createRealmsClient();
            RealmsDataFetcher.this.pendingInvitesCount = _snowman.pendingInvitesCount();
            RealmsDataFetcher.this.fetchStatus.put(RealmsDataFetcher.Task.PENDING_INVITE, true);
         } catch (Exception var2) {
            RealmsDataFetcher.LOGGER.error("Couldn't get pending invite count", var2);
         }
      }
   }

   class ServerListUpdateTask implements Runnable {
      private ServerListUpdateTask() {
      }

      @Override
      public void run() {
         if (RealmsDataFetcher.this.isActive()) {
            this.updateServersList();
         }
      }

      private void updateServersList() {
         try {
            RealmsClient _snowman = RealmsClient.createRealmsClient();
            List<RealmsServer> _snowmanx = _snowman.listWorlds().servers;
            if (_snowmanx != null) {
               _snowmanx.sort(new RealmsServer.McoServerComparator(MinecraftClient.getInstance().getSession().getUsername()));
               RealmsDataFetcher.this.setServers(_snowmanx);
               RealmsDataFetcher.this.fetchStatus.put(RealmsDataFetcher.Task.SERVER_LIST, true);
            } else {
               RealmsDataFetcher.LOGGER.warn("Realms server list was null or empty");
            }
         } catch (Exception var3) {
            RealmsDataFetcher.this.fetchStatus.put(RealmsDataFetcher.Task.SERVER_LIST, true);
            RealmsDataFetcher.LOGGER.error("Couldn't get server list", var3);
         }
      }
   }

   public static enum Task {
      SERVER_LIST,
      PENDING_INVITE,
      TRIAL_AVAILABLE,
      LIVE_STATS,
      UNREAD_NEWS;

      private Task() {
      }
   }

   class TrialAvailabilityTask implements Runnable {
      private TrialAvailabilityTask() {
      }

      @Override
      public void run() {
         if (RealmsDataFetcher.this.isActive()) {
            this.getTrialAvailable();
         }
      }

      private void getTrialAvailable() {
         try {
            RealmsClient _snowman = RealmsClient.createRealmsClient();
            RealmsDataFetcher.this.trialAvailable = _snowman.trialAvailable();
            RealmsDataFetcher.this.fetchStatus.put(RealmsDataFetcher.Task.TRIAL_AVAILABLE, true);
         } catch (Exception var2) {
            RealmsDataFetcher.LOGGER.error("Couldn't get trial availability", var2);
         }
      }
   }

   class UnreadNewsTask implements Runnable {
      private UnreadNewsTask() {
      }

      @Override
      public void run() {
         if (RealmsDataFetcher.this.isActive()) {
            this.getUnreadNews();
         }
      }

      private void getUnreadNews() {
         try {
            RealmsClient _snowman = RealmsClient.createRealmsClient();
            RealmsNews _snowmanx = null;

            try {
               _snowmanx = _snowman.getNews();
            } catch (Exception var5) {
            }

            RealmsPersistence.RealmsPersistenceData _snowmanxx = RealmsPersistence.readFile();
            if (_snowmanx != null) {
               String _snowmanxxx = _snowmanx.newsLink;
               if (_snowmanxxx != null && !_snowmanxxx.equals(_snowmanxx.newsLink)) {
                  _snowmanxx.hasUnreadNews = true;
                  _snowmanxx.newsLink = _snowmanxxx;
                  RealmsPersistence.writeFile(_snowmanxx);
               }
            }

            RealmsDataFetcher.this.hasUnreadNews = _snowmanxx.hasUnreadNews;
            RealmsDataFetcher.this.newsLink = _snowmanxx.newsLink;
            RealmsDataFetcher.this.fetchStatus.put(RealmsDataFetcher.Task.UNREAD_NEWS, true);
         } catch (Exception var6) {
            RealmsDataFetcher.LOGGER.error("Couldn't get unread news", var6);
         }
      }
   }
}
