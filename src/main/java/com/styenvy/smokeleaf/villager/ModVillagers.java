package com.styenvy.smokeleaf.villager;

import com.google.common.collect.ImmutableSet;
import com.styenvy.smokeleaf.SmokeLeafEG;
import com.styenvy.smokeleaf.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModVillagers {
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, SmokeLeafEG.MODID);

    public static final DeferredHolder<VillagerProfession, VillagerProfession> SMOKELEAF_DEALER = 
            VILLAGER_PROFESSIONS.register("smokeleaf_dealer",
                    () -> new VillagerProfession(
                            "smokeleaf_dealer",
                            holder -> holder.value() == ModPOITypes.COOKIE_OVEN_POI.get(),
                            holder -> holder.value() == ModPOITypes.COOKIE_OVEN_POI.get(),
                            ImmutableSet.of(),
                            ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_BUTCHER));

    public static final DeferredHolder<VillagerProfession, VillagerProfession> SMOKELEAF_FARMER = 
            VILLAGER_PROFESSIONS.register("smokeleaf_farmer",
                    () -> new VillagerProfession(
                            "smokeleaf_farmer",
                            holder -> holder.value() == ModPOITypes.GERMINATION_TABLE_POI.get(),
                            holder -> holder.value() == ModPOITypes.GERMINATION_TABLE_POI.get(),
                            ImmutableSet.of(),
                            ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_FARMER));
}
