package net.minecraft.resource;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class ResourcePackManager implements AutoCloseable {
   private final Set<ResourcePackProvider> providers;
   private Map<String, ResourcePackProfile> profiles = ImmutableMap.of();
   private List<ResourcePackProfile> enabled = ImmutableList.of();
   private final ResourcePackProfile.Factory profileFactory;

   public ResourcePackManager(ResourcePackProfile.Factory profileFactory, ResourcePackProvider... providers) {
      this.profileFactory = profileFactory;
      this.providers = ImmutableSet.copyOf(providers);
   }

   public ResourcePackManager(ResourcePackProvider... args) {
      this(ResourcePackProfile::new, args);
   }

   public void scanPacks() {
      List<String> list = this.enabled.stream().map(ResourcePackProfile::getName).collect(ImmutableList.toImmutableList());
      this.close();
      this.profiles = this.providePackProfiles();
      this.enabled = this.buildEnabledProfiles(list);
   }

   private Map<String, ResourcePackProfile> providePackProfiles() {
      Map<String, ResourcePackProfile> map = Maps.newTreeMap();

      for (ResourcePackProvider lv : this.providers) {
         lv.register(arg -> {
            ResourcePackProfile var10000 = map.put(arg.getName(), arg);
         }, this.profileFactory);
      }

      return ImmutableMap.copyOf(map);
   }

   public void setEnabledProfiles(Collection<String> enabled) {
      this.enabled = this.buildEnabledProfiles(enabled);
   }

   private List<ResourcePackProfile> buildEnabledProfiles(Collection<String> enabledNames) {
      List<ResourcePackProfile> list = this.streamProfilesByName(enabledNames).collect(Collectors.toList());

      for (ResourcePackProfile lv : this.profiles.values()) {
         if (lv.isAlwaysEnabled() && !list.contains(lv)) {
            lv.getInitialPosition().insert(list, lv, Functions.identity(), false);
         }
      }

      return ImmutableList.copyOf(list);
   }

   private Stream<ResourcePackProfile> streamProfilesByName(Collection<String> names) {
      return names.stream().map(this.profiles::get).filter(Objects::nonNull);
   }

   public Collection<String> getNames() {
      return this.profiles.keySet();
   }

   public Collection<ResourcePackProfile> getProfiles() {
      return this.profiles.values();
   }

   public Collection<String> getEnabledNames() {
      return this.enabled.stream().map(ResourcePackProfile::getName).collect(ImmutableSet.toImmutableSet());
   }

   public Collection<ResourcePackProfile> getEnabledProfiles() {
      return this.enabled;
   }

   @Nullable
   public ResourcePackProfile getProfile(String name) {
      return this.profiles.get(name);
   }

   @Override
   public void close() {
      this.profiles.values().forEach(ResourcePackProfile::close);
   }

   public boolean hasProfile(String name) {
      return this.profiles.containsKey(name);
   }

   public List<ResourcePack> createResourcePacks() {
      return this.enabled.stream().map(ResourcePackProfile::createResourcePack).collect(ImmutableList.toImmutableList());
   }
}
