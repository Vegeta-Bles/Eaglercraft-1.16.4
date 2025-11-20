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

   public ResourcePackManager(ResourcePackProvider... _snowman) {
      this(ResourcePackProfile::new, _snowman);
   }

   public void scanPacks() {
      List<String> _snowman = this.enabled.stream().map(ResourcePackProfile::getName).collect(ImmutableList.toImmutableList());
      this.close();
      this.profiles = this.providePackProfiles();
      this.enabled = this.buildEnabledProfiles(_snowman);
   }

   private Map<String, ResourcePackProfile> providePackProfiles() {
      Map<String, ResourcePackProfile> _snowman = Maps.newTreeMap();

      for (ResourcePackProvider _snowmanx : this.providers) {
         _snowmanx.register(_snowmanxx -> {
            ResourcePackProfile var10000 = _snowman.put(_snowmanxx.getName(), _snowmanxx);
         }, this.profileFactory);
      }

      return ImmutableMap.copyOf(_snowman);
   }

   public void setEnabledProfiles(Collection<String> enabled) {
      this.enabled = this.buildEnabledProfiles(enabled);
   }

   private List<ResourcePackProfile> buildEnabledProfiles(Collection<String> enabledNames) {
      List<ResourcePackProfile> _snowman = this.streamProfilesByName(enabledNames).collect(Collectors.toList());

      for (ResourcePackProfile _snowmanx : this.profiles.values()) {
         if (_snowmanx.isAlwaysEnabled() && !_snowman.contains(_snowmanx)) {
            _snowmanx.getInitialPosition().insert(_snowman, _snowmanx, Functions.identity(), false);
         }
      }

      return ImmutableList.copyOf(_snowman);
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
