package com.ingresso.greatrebirth

import com.ingresso.greatrebirth.common.block.BlockAltarRebirth
import com.ingresso.greatrebirth.common.tile.TileAltarRebirth
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import wayoftime.bloodmagic.BloodMagic
import wayoftime.bloodmagic.common.tile.TileAltar

@Mod(Main.MODID)
class Main {
    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        BLOCKS.register(modEventBus)
        ITEMS.register(modEventBus)
        CREATIVE_MODE_TABS.register(modEventBus)
        MinecraftForge.EVENT_BUS.register(this)
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    @SubscribeEvent
    fun commonSetup(event: FMLCommonSetupEvent) {}

    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {}

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ClientModEvents {
        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent?) {}
    }

    companion object {
        const val MODID = "greatrebirth"
        val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)
        val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)
        val TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
        val CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID)
//        val EXAMPLE_BLOCK =
//            BLOCKS.register("example_block") { Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)) }
//        val EXAMPLE_BLOCK_ITEM =
//            ITEMS.register<Item>("example_block") { BlockItem(EXAMPLE_BLOCK.get(), Item.Properties()) }
//        val EXAMPLE_ITEM = ITEMS.register("example_item") {
//            Item(
//                Item.Properties().food(
//                    FoodProperties.Builder()
//                        .alwaysEat().nutrition(1).saturationMod(2f).build()
//                )
//            )
//        }
        val BLOCK_ALTAR_REBIRTH = BLOCKS.register("altar_rebirth") {
            BlockAltarRebirth(BlockBehaviour.Properties.of()
                .sound(SoundType.ANVIL)
                .destroyTime(2.0f)
            )
        }
        val BLOCK_ALTAR_REBIRTH_ITEM = ITEMS.register("altar_rebirth") {
            BlockItem(BLOCK_ALTAR_REBIRTH.get(), Item.Properties())
        }
        val TILE_ALTAR_REBIRTH = TILE_ENTITIES.register("tile_altar_rebirth") {
            BlockEntityType.Builder.of(::TileAltarRebirth, BLOCK_ALTAR_REBIRTH.get()).build(null)
        }
        val EXAMPLE_TAB = CREATIVE_MODE_TABS.register("great_rebirth") {
            CreativeModeTab.builder()
                .withTabsAfter(CreativeModeTabs.SPAWN_EGGS)
                .icon { BLOCK_ALTAR_REBIRTH_ITEM.get().asItem().defaultInstance }
                .displayItems { parameters: ItemDisplayParameters?, output: CreativeModeTab.Output ->
                    output.accept(BLOCK_ALTAR_REBIRTH_ITEM.get())
                }.build()
        }
    }
}
