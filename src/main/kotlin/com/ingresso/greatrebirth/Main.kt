package com.ingresso.greatrebirth

import com.ingresso.greatrebirth.client.screens.ScreenAltarRebirth
import com.ingresso.greatrebirth.common.block.BlockAltarRebirth
import com.ingresso.greatrebirth.common.capability.BuffsProvider
import com.ingresso.greatrebirth.common.container.ContainerAltarRebirth
import com.ingresso.greatrebirth.common.network.NetworkHandler
import com.ingresso.greatrebirth.common.tile.TileAltarRebirth
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.extensions.IForgeMenuType
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

@Mod(Main.MODID)
class Main {
    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        BLOCKS.register(modEventBus)
        ITEMS.register(modEventBus)
        TILE_ENTITIES.register(modEventBus)
        MENU_TYPES.register(modEventBus)
        CREATIVE_MODE_TABS.register(modEventBus)
        modEventBus.addListener(::onClientSetup)
        MinecraftForge.EVENT_BUS.register(this)
        NetworkHandler.registerPacket()
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    @SubscribeEvent
    fun commonSetup(event: FMLCommonSetupEvent) {}

    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            MenuScreens.register(CONTAINER_ALTAR_REBIRTH.get(), ::ScreenAltarRebirth)
        }
    }

    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {}

    @SubscribeEvent
    fun onAttachCapability(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is Player) {
            if (!event.`object`.getCapability(BuffsProvider.BUFF_CAP, null).isPresent) {
                event.addCapability(ResourceLocation(Main.MODID, "buffs"), BuffsProvider())
            }
        }
    }

    companion object {
        const val MODID = "greatrebirth"
        val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)
        val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)
        val TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID)
        val MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID)
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
            BlockEntityType.Builder.of(
                { pos: BlockPos, state: BlockState -> TileAltarRebirth(pos, state) },
                BLOCK_ALTAR_REBIRTH.get()
            ).build(null)
        }
        val CONTAINER_ALTAR_REBIRTH = MENU_TYPES.register("container_altar_rebirth") {
            IForgeMenuType.create(::ContainerAltarRebirth)
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
