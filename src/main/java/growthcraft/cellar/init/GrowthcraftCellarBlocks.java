package growthcraft.cellar.init;

import growthcraft.cellar.Reference;
import growthcraft.cellar.blocks.BlockBrewKettle;
import growthcraft.cellar.blocks.BlockCultureJar;
import growthcraft.cellar.common.block.PREVBlockBrewKettle;
import growthcraft.core.common.definition.BlockDefinition;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCellarBlocks {

    public static Block blockCultureJar;
//    public static Block blockBrewKettle;
    public static BlockDefinition brewKettle;

    public static void init() {
        blockCultureJar = new BlockCultureJar("culture_jar");
//        blockBrewKettle = new BlockBrewKettle("brew_kettle");
        brewKettle = new BlockDefinition(new PREVBlockBrewKettle("brew_kettle"));
    }

    public static void register() {
        registerBlock(blockCultureJar, true, true);
//        registerBlock(blockBrewKettle, true, true);
        brewKettle.getBlock().setCreativeTab(tabGrowthcraft);
        brewKettle.register(true);
    }

    public static void registerRenders() {
        registerRender(blockCultureJar);
//        registerRender(blockBrewKettle);
        brewKettle.registerRender();
    }

    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock) {
        GameRegistry.register(block);

        if (setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }
        if (registerItemBlock) {
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void registerBlock(Block block, ItemBlock itemBlock) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
                new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void registerRender(Block block, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
