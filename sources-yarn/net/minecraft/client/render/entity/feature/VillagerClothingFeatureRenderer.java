package net.minecraft.client.render.entity.feature;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;

@Environment(EnvType.CLIENT)
public class VillagerClothingFeatureRenderer<T extends LivingEntity & VillagerDataContainer, M extends EntityModel<T> & ModelWithHat>
   extends FeatureRenderer<T, M>
   implements SynchronousResourceReloadListener {
   private static final Int2ObjectMap<Identifier> LEVEL_TO_ID = Util.make(new Int2ObjectOpenHashMap(), int2ObjectOpenHashMap -> {
      int2ObjectOpenHashMap.put(1, new Identifier("stone"));
      int2ObjectOpenHashMap.put(2, new Identifier("iron"));
      int2ObjectOpenHashMap.put(3, new Identifier("gold"));
      int2ObjectOpenHashMap.put(4, new Identifier("emerald"));
      int2ObjectOpenHashMap.put(5, new Identifier("diamond"));
   });
   private final Object2ObjectMap<VillagerType, VillagerResourceMetadata.HatType> villagerTypeToHat = new Object2ObjectOpenHashMap();
   private final Object2ObjectMap<VillagerProfession, VillagerResourceMetadata.HatType> professionToHat = new Object2ObjectOpenHashMap();
   private final ReloadableResourceManager resourceManager;
   private final String entityType;

   public VillagerClothingFeatureRenderer(FeatureRendererContext<T, M> context, ReloadableResourceManager resourceManager, String entityType) {
      super(context);
      this.resourceManager = resourceManager;
      this.entityType = entityType;
      resourceManager.registerListener(this);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, T arg3, float f, float g, float h, float j, float k, float l) {
      if (!arg3.isInvisible()) {
         VillagerData lv = arg3.getVillagerData();
         VillagerType lv2 = lv.getType();
         VillagerProfession lv3 = lv.getProfession();
         VillagerResourceMetadata.HatType lv4 = this.getHatType(this.villagerTypeToHat, "type", Registry.VILLAGER_TYPE, lv2);
         VillagerResourceMetadata.HatType lv5 = this.getHatType(this.professionToHat, "profession", Registry.VILLAGER_PROFESSION, lv3);
         M lv6 = this.getContextModel();
         lv6.setHatVisible(
            lv5 == VillagerResourceMetadata.HatType.NONE || lv5 == VillagerResourceMetadata.HatType.PARTIAL && lv4 != VillagerResourceMetadata.HatType.FULL
         );
         Identifier lv7 = this.findTexture("type", Registry.VILLAGER_TYPE.getId(lv2));
         renderModel(lv6, lv7, arg, arg2, i, arg3, 1.0F, 1.0F, 1.0F);
         lv6.setHatVisible(true);
         if (lv3 != VillagerProfession.NONE && !arg3.isBaby()) {
            Identifier lv8 = this.findTexture("profession", Registry.VILLAGER_PROFESSION.getId(lv3));
            renderModel(lv6, lv8, arg, arg2, i, arg3, 1.0F, 1.0F, 1.0F);
            if (lv3 != VillagerProfession.NITWIT) {
               Identifier lv9 = this.findTexture("profession_level", (Identifier)LEVEL_TO_ID.get(MathHelper.clamp(lv.getLevel(), 1, LEVEL_TO_ID.size())));
               renderModel(lv6, lv9, arg, arg2, i, arg3, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   private Identifier findTexture(String keyType, Identifier keyId) {
      return new Identifier(keyId.getNamespace(), "textures/entity/" + this.entityType + "/" + keyType + "/" + keyId.getPath() + ".png");
   }

   public <K> VillagerResourceMetadata.HatType getHatType(
      Object2ObjectMap<K, VillagerResourceMetadata.HatType> hatLookUp, String keyType, DefaultedRegistry<K> registry, K key
   ) {
      return (VillagerResourceMetadata.HatType)hatLookUp.computeIfAbsent(key, object2 -> {
         try (Resource lv = this.resourceManager.getResource(this.findTexture(keyType, registry.getId(key)))) {
            VillagerResourceMetadata lv2 = lv.getMetadata(VillagerResourceMetadata.READER);
            if (lv2 != null) {
               return lv2.getHatType();
            }
         } catch (IOException var21) {
         }

         return VillagerResourceMetadata.HatType.NONE;
      });
   }

   @Override
   public void apply(ResourceManager manager) {
      this.professionToHat.clear();
      this.villagerTypeToHat.clear();
   }
}
