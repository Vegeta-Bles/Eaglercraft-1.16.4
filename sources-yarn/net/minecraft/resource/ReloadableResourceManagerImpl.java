package net.minecraft.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class ReloadableResourceManagerImpl implements ReloadableResourceManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, NamespaceResourceManager> namespaceManagers = Maps.newHashMap();
   private final List<ResourceReloadListener> listeners = Lists.newArrayList();
   private final List<ResourceReloadListener> initialListeners = Lists.newArrayList();
   private final Set<String> namespaces = Sets.newLinkedHashSet();
   private final List<ResourcePack> field_25145 = Lists.newArrayList();
   private final ResourceType type;

   public ReloadableResourceManagerImpl(ResourceType type) {
      this.type = type;
   }

   public void addPack(ResourcePack arg) {
      this.field_25145.add(arg);

      for (String string : arg.getNamespaces(this.type)) {
         this.namespaces.add(string);
         NamespaceResourceManager lv = this.namespaceManagers.get(string);
         if (lv == null) {
            lv = new NamespaceResourceManager(this.type, string);
            this.namespaceManagers.put(string, lv);
         }

         lv.addPack(arg);
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public Set<String> getAllNamespaces() {
      return this.namespaces;
   }

   @Override
   public Resource getResource(Identifier id) throws IOException {
      ResourceManager lv = this.namespaceManagers.get(id.getNamespace());
      if (lv != null) {
         return lv.getResource(id);
      } else {
         throw new FileNotFoundException(id.toString());
      }
   }

   @Environment(EnvType.CLIENT)
   @Override
   public boolean containsResource(Identifier id) {
      ResourceManager lv = this.namespaceManagers.get(id.getNamespace());
      return lv != null ? lv.containsResource(id) : false;
   }

   @Override
   public List<Resource> getAllResources(Identifier id) throws IOException {
      ResourceManager lv = this.namespaceManagers.get(id.getNamespace());
      if (lv != null) {
         return lv.getAllResources(id);
      } else {
         throw new FileNotFoundException(id.toString());
      }
   }

   @Override
   public Collection<Identifier> findResources(String resourceType, Predicate<String> pathPredicate) {
      Set<Identifier> set = Sets.newHashSet();

      for (NamespaceResourceManager lv : this.namespaceManagers.values()) {
         set.addAll(lv.findResources(resourceType, pathPredicate));
      }

      List<Identifier> list = Lists.newArrayList(set);
      Collections.sort(list);
      return list;
   }

   private void clear() {
      this.namespaceManagers.clear();
      this.namespaces.clear();
      this.field_25145.forEach(ResourcePack::close);
      this.field_25145.clear();
   }

   @Override
   public void close() {
      this.clear();
   }

   @Override
   public void registerListener(ResourceReloadListener listener) {
      this.listeners.add(listener);
      this.initialListeners.add(listener);
   }

   protected ResourceReloadMonitor beginReloadInner(
      Executor prepareExecutor, Executor applyExecutor, List<ResourceReloadListener> listeners, CompletableFuture<Unit> initialStage
   ) {
      ResourceReloadMonitor lv;
      if (LOGGER.isDebugEnabled()) {
         lv = new ProfilingResourceReloader(this, Lists.newArrayList(listeners), prepareExecutor, applyExecutor, initialStage);
      } else {
         lv = ResourceReloader.create(this, Lists.newArrayList(listeners), prepareExecutor, applyExecutor, initialStage);
      }

      this.initialListeners.clear();
      return lv;
   }

   @Override
   public ResourceReloadMonitor beginMonitoredReload(
      Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs
   ) {
      this.clear();
      LOGGER.info("Reloading ResourceManager: {}", new Supplier[]{() -> packs.stream().map(ResourcePack::getName).collect(Collectors.joining(", "))});

      for (ResourcePack lv : packs) {
         try {
            this.addPack(lv);
         } catch (Exception var8) {
            LOGGER.error("Failed to add resource pack {}", lv.getName(), var8);
            return new ReloadableResourceManagerImpl.FailedResourceReloadMonitor(new ReloadableResourceManagerImpl.PackAdditionFailedException(lv, var8));
         }
      }

      return this.beginReloadInner(prepareExecutor, applyExecutor, this.listeners, initialStage);
   }

   @Environment(EnvType.CLIENT)
   @Override
   public Stream<ResourcePack> streamResourcePacks() {
      return this.field_25145.stream();
   }

   static class FailedResourceReloadMonitor implements ResourceReloadMonitor {
      private final ReloadableResourceManagerImpl.PackAdditionFailedException exception;
      private final CompletableFuture<Unit> future;

      public FailedResourceReloadMonitor(ReloadableResourceManagerImpl.PackAdditionFailedException exception) {
         this.exception = exception;
         this.future = new CompletableFuture<>();
         this.future.completeExceptionally(exception);
      }

      @Override
      public CompletableFuture<Unit> whenComplete() {
         return this.future;
      }

      @Environment(EnvType.CLIENT)
      @Override
      public float getProgress() {
         return 0.0F;
      }

      @Environment(EnvType.CLIENT)
      @Override
      public boolean isPrepareStageComplete() {
         return false;
      }

      @Environment(EnvType.CLIENT)
      @Override
      public boolean isApplyStageComplete() {
         return true;
      }

      @Environment(EnvType.CLIENT)
      @Override
      public void throwExceptions() {
         throw this.exception;
      }
   }

   public static class PackAdditionFailedException extends RuntimeException {
      private final ResourcePack pack;

      public PackAdditionFailedException(ResourcePack pack, Throwable cause) {
         super(pack.getName(), cause);
         this.pack = pack;
      }

      @Environment(EnvType.CLIENT)
      public ResourcePack getPack() {
         return this.pack;
      }
   }
}
