package growthcraft.milk.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.shared.init.GrowthcraftCellarPotions;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.GrowthcraftCoreApis;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.effect.EffectExtinguish;
import growthcraft.core.shared.effect.EffectList;
import growthcraft.core.shared.effect.EffectRegistry;
import growthcraft.core.shared.effect.EffectUtils;
import growthcraft.core.shared.effect.IEffect;
import growthcraft.core.shared.fluids.FluidFactory;
import growthcraft.core.shared.fluids.TaggedFluidStacks;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetails;
import growthcraft.core.shared.fluids.FluidFactory.FluidDetailsBuilder;
import growthcraft.core.shared.item.CommonItemStackComparator;
import growthcraft.core.shared.item.EnumDye;
import growthcraft.core.shared.item.IItemStackComparator;
import growthcraft.core.shared.item.ItemFoodBottleFluid;
import growthcraft.core.shared.item.OreItemStacks;
import growthcraft.core.shared.item.recipes.ShapelessItemComparableRecipe;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import growthcraft.core.shared.utils.TickUtils;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.client.render.RenderButterChurnPlunger;
import growthcraft.milk.client.render.RenderCheesePress;
import growthcraft.milk.client.render.RenderCheeseVat;
import growthcraft.milk.client.render.RenderPancheon;
import growthcraft.milk.common.block.BlockButterChurn;
import growthcraft.milk.common.block.BlockButterChurnPlunger;
import growthcraft.milk.common.block.BlockCheeseBlock;
import growthcraft.milk.common.block.BlockCheesePress;
import growthcraft.milk.common.block.BlockCheeseVat;
import growthcraft.milk.common.block.BlockHangingCurds;
import growthcraft.milk.common.block.BlockPancheon;
import growthcraft.milk.common.block.BlockThistle;
import growthcraft.milk.common.block.fluids.BlockFluidButterMilk;
import growthcraft.milk.common.block.fluids.BlockFluidCheese;
import growthcraft.milk.common.block.fluids.BlockFluidCream;
import growthcraft.milk.common.block.fluids.BlockFluidMilk;
import growthcraft.milk.common.block.fluids.BlockFluidMilkCurds;
import growthcraft.milk.common.block.fluids.BlockFluidPasteurizedMilk;
import growthcraft.milk.common.block.fluids.BlockFluidRennet;
import growthcraft.milk.common.block.fluids.BlockFluidSkimMilk;
import growthcraft.milk.common.block.fluids.BlockFluidWhey;
import growthcraft.milk.common.fluids.FluidButterMilk;
import growthcraft.milk.common.fluids.FluidCheese;
import growthcraft.milk.common.fluids.FluidCream;
import growthcraft.milk.common.fluids.FluidMilk;
import growthcraft.milk.common.fluids.FluidMilkCurds;
import growthcraft.milk.common.fluids.FluidPasteurizedMilk;
import growthcraft.milk.common.fluids.FluidRennet;
import growthcraft.milk.common.fluids.FluidSkimMilk;
import growthcraft.milk.common.fluids.FluidWhey;
import growthcraft.milk.common.item.ItemAgedCheeseSlice;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.common.item.ItemBlockHangingCurds;
import growthcraft.milk.common.item.ItemButter;
import growthcraft.milk.common.item.ItemCheeseCloth;
import growthcraft.milk.common.item.ItemIceCream;
import growthcraft.milk.common.item.ItemSeedThistle;
import growthcraft.milk.common.item.ItemSimpleCheeseSlice;
import growthcraft.milk.common.item.ItemStarterCulture;
import growthcraft.milk.common.item.ItemStomach;
import growthcraft.milk.common.item.ItemThistle;
import growthcraft.milk.common.item.ItemWaxedCheeseSlice;
import growthcraft.milk.common.item.ItemYogurt;
import growthcraft.milk.common.lib.effect.EffectMilk;
import growthcraft.milk.common.lib.processing.DriedCurdsCheesePressRecipe;
import growthcraft.milk.common.tileentity.TileEntityButterChurn;
import growthcraft.milk.common.tileentity.TileEntityButterChurnPlunger;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import growthcraft.milk.common.tileentity.TileEntityCheesePress;
import growthcraft.milk.common.tileentity.TileEntityCheeseVat;
import growthcraft.milk.common.tileentity.TileEntityHangingCurds;
import growthcraft.milk.common.tileentity.TileEntityPancheon;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.cheese.CheeseUtils;
import growthcraft.milk.shared.config.GrowthcraftMilkConfig;
import growthcraft.milk.shared.definition.EnumCheeseStage;
import growthcraft.milk.shared.definition.ICheeseBlockStackFactory;
import growthcraft.milk.shared.definition.ICheeseCurdStackFactory;
import growthcraft.milk.shared.definition.ICheeseType;
import growthcraft.milk.shared.fluids.MilkFluidTags;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.AgedCheeseTypes;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.ButterTypes;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.IceCreamTypes;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.SimpleCheeseTypes;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.WaxedCheeseTypes;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.YogurtTypes;
import growthcraft.milk.shared.utils.CheeseVatRecipeBuilder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Init {
	private Init() {}
	
	////////
	// Blocks
    ////////
	
    public static void preInitBlocks() {
    	GrowthcraftMilkBlocks.thistle = new BlockDefinition( new BlockThistle("thistle") );
    	GrowthcraftMilkBlocks.cheeseVat = new BlockDefinition( new BlockCheeseVat("cheese_vat") );
    	GrowthcraftMilkBlocks.blockFluidMilk = GrowthcraftMilkFluids.milk.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.blockFluidRennet = GrowthcraftMilkFluids.rennet.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.blockFluidButterMilk = GrowthcraftMilkFluids.butterMilk.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.blockFluidCream = GrowthcraftMilkFluids.cream.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.blockFluidMilkCurds = GrowthcraftMilkFluids.curds.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.blockFluidPasteurizedMilk = GrowthcraftMilkFluids.pasteurizedMilk.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.blockFluidSkimMilk = GrowthcraftMilkFluids.skimMilk.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.blockFluidWhey = GrowthcraftMilkFluids.whey.getFluidBlockDefinition();
    	GrowthcraftMilkBlocks.pancheon = new BlockDefinition( new BlockPancheon("pancheon") );
    	GrowthcraftMilkBlocks.churn = new BlockDefinition( new BlockButterChurn("churn") );
    	GrowthcraftMilkBlocks.churnPlunger = new BlockDefinition(new BlockButterChurnPlunger("churn_plunger") );
    	GrowthcraftMilkBlocks.cheesePress = new BlockDefinition( new BlockCheesePress("cheese_press") );
    	GrowthcraftMilkBlocks.agedCheeseBlock = new BlockDefinition( new BlockCheeseBlock() );
    	GrowthcraftMilkBlocks.waxedCheeseBlock = new BlockDefinition( new BlockCheeseBlock() );
    	GrowthcraftMilkBlocks.agedCheeseCurds = new BlockDefinition(new BlockHangingCurds());
    	GrowthcraftMilkBlocks.waxedCheeseCurds = new BlockDefinition(new BlockHangingCurds());
    	GrowthcraftMilkBlocks.simpleCheeseCurds = new BlockDefinition(new BlockHangingCurds());
    }

    public static void registerBlocks() {
    	GrowthcraftMilkBlocks.thistle.register(false);
    	GrowthcraftMilkBlocks.cheeseVat.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.cheeseVat.register(true);
    	GrowthcraftMilkBlocks.pancheon.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.pancheon.register(true);
    	GrowthcraftMilkBlocks.churn.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.churn.register(true);
    	GrowthcraftMilkBlocks.churnPlunger.register(false);
    	GrowthcraftMilkBlocks.cheesePress.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.cheesePress.register(true);
    	GrowthcraftMilkBlocks.agedCheeseBlock.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.agedCheeseBlock.register(new ResourceLocation(Reference.MODID, "cheese_aged"), false);
    	GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.waxedCheeseBlock.register(new ResourceLocation(Reference.MODID, "cheese_waxed"), false);
    	GrowthcraftMilkBlocks.agedCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.agedCheeseCurds.register(new ResourceLocation(Reference.MODID, "curds_aged"), false);
    	GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.waxedCheeseCurds.register(new ResourceLocation(Reference.MODID, "curds_waxed"), false);
    	GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkBlocks.simpleCheeseCurds.register(new ResourceLocation(Reference.MODID, "curds_simple"), false);
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerBlockRenders() {
    	GrowthcraftMilkBlocks.thistle.registerItemRender();
    	GrowthcraftMilkBlocks.cheeseVat.registerItemRender();
    	GrowthcraftMilkBlocks.pancheon.registerItemRender();
    	GrowthcraftMilkBlocks.churn.registerItemRender();
    	GrowthcraftMilkBlocks.cheesePress.registerItemRender();
    }
    
    public static void registerTileEntities() {
    	GameRegistry.registerTileEntity(TileEntityPancheon.class, Reference.MODID + ":pancheon");
    	GameRegistry.registerTileEntity(TileEntityButterChurn.class, Reference.MODID + ":churn");
    	GameRegistry.registerTileEntity(TileEntityButterChurnPlunger.class, Reference.MODID + ":churn_plunger");
    	GameRegistry.registerTileEntity(TileEntityCheeseBlock.class, Reference.MODID + ":cheese_block");
    	GameRegistry.registerTileEntity(TileEntityHangingCurds.class, Reference.MODID + ":cheese_curds");
    	GameRegistry.registerTileEntity(TileEntityCheeseVat.class, Reference.MODID + ":cheese_vat");
    	GameRegistry.registerTileEntity(TileEntityCheesePress.class, Reference.MODID + ":cheese_press");
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerBlockSpecialRenders() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPancheon.class, new RenderPancheon());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCheeseVat.class, new RenderCheeseVat());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCheesePress.class, new RenderCheesePress());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityButterChurnPlunger.class, new RenderButterChurnPlunger());
    }
    
	////////
    // Items
	////////
    
    public static void preInitItems() {
    	GrowthcraftMilkItems.thistle = new ItemDefinition( new ItemThistle("thistle") );
    	GrowthcraftMilkItems.thistleSeed = new ItemDefinition( new ItemSeedThistle("thistle_seed") );
    	GrowthcraftMilkItems.stomach = new ItemDefinition(new ItemStomach("stomach") );
    	GrowthcraftMilkItems.cheeseCloth = new ItemDefinition( new ItemCheeseCloth("cheese_cloth") );
    	GrowthcraftMilkItems.starterCulture = new ItemDefinition( new ItemStarterCulture("starter_culture") );
    	GrowthcraftMilkItems.butter = new ItemDefinition( new ItemButter("butter", 2, 0.3F, false) );
    	GrowthcraftMilkItems.iceCream = new ItemDefinition( new ItemIceCream("ice_cream", 2, 0.3F, false) );
    	GrowthcraftMilkItems.yogurt = new ItemDefinition( new ItemYogurt("yogurt", 2, 0.3F, false) );
    	GrowthcraftMilkItems.agedCheeseSlice = new ItemDefinition( new ItemAgedCheeseSlice("cheese_aged_slice", 2, 0.3F, false) );
    	GrowthcraftMilkItems.agedCheeseBlockItem = new ItemDefinition( new ItemBlockCheeseBlock<AgedCheeseTypes>(GrowthcraftMilkBlocks.agedCheeseBlock.getBlock(), AgedCheeseTypes.values()) );
    	GrowthcraftMilkItems.waxedCheeseBlockItem = new ItemDefinition( new ItemBlockCheeseBlock<WaxedCheeseTypes>(GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock(), WaxedCheeseTypes.values()) );
    	GrowthcraftMilkItems.waxedCheeseSlice = new ItemDefinition( new ItemWaxedCheeseSlice("cheese_waxed_slice", 2, 0.3F, false) );
    	GrowthcraftMilkItems.simpleCheeseSlice = new ItemDefinition( new ItemSimpleCheeseSlice("cheese_simple_slice", 2, 0.3F, false) );
    	GrowthcraftMilkItems.agedCheeseCurdsItem = new ItemDefinition( new ItemBlockHangingCurds<AgedCheeseTypes>(GrowthcraftMilkBlocks.agedCheeseCurds.getBlock(), AgedCheeseTypes.values()) );
    	GrowthcraftMilkItems.waxedCheeseCurdsItem = new ItemDefinition( new ItemBlockHangingCurds<WaxedCheeseTypes>(GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock(), WaxedCheeseTypes.values()) );
    	GrowthcraftMilkItems.simpleCheeseCurdsItem = new ItemDefinition( new ItemBlockHangingCurds<SimpleCheeseTypes>(GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock(), SimpleCheeseTypes.values()) );
    }

    public static void registerItems() {
    	GrowthcraftMilkItems.thistle.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.thistle.register();
    	GrowthcraftMilkItems.thistleSeed.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.thistleSeed.register();
    	GrowthcraftMilkItems.stomach.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.stomach.register();
    	GrowthcraftMilkItems.cheeseCloth.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.cheeseCloth.register();
    	GrowthcraftMilkItems.starterCulture.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.starterCulture.register();
    	GrowthcraftMilkItems.butter.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.butter.register();
    	GrowthcraftMilkItems.iceCream.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.iceCream.register();
    	GrowthcraftMilkItems.yogurt.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.yogurt.register();
    	GrowthcraftMilkItems.agedCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.agedCheeseSlice.register();
    	GrowthcraftMilkItems.waxedCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.waxedCheeseSlice.register();
    	GrowthcraftMilkItems.simpleCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftMilkItems.simpleCheeseSlice.register();
    	GrowthcraftMilkItems.agedCheeseBlockItem.register(GrowthcraftMilkBlocks.agedCheeseBlock.getBlock().getRegistryName() /*new ResourceLocation(Reference.MODID, "cheese_aged")*/);
    	GrowthcraftMilkItems.waxedCheeseBlockItem.register(GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock().getRegistryName());
    	GrowthcraftMilkItems.agedCheeseCurdsItem.register(GrowthcraftMilkBlocks.agedCheeseCurds.getBlock().getRegistryName());
    	GrowthcraftMilkItems.waxedCheeseCurdsItem.register(GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock().getRegistryName());
    	GrowthcraftMilkItems.simpleCheeseCurdsItem.register(GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock().getRegistryName());
        
        registerItemOres();
    }
    
	private static void registerItemOres()
	{
		for (IceCreamTypes e : IceCreamTypes.values())
		{
			OreDictionary.registerOre("foodIceCream", e.asStack());
		}

		for (ButterTypes e : ButterTypes.values())
		{
			OreDictionary.registerOre("foodButter", e.asStack());
		}

		for (AgedCheeseTypes e : AgedCheeseTypes.values())
		{
			OreDictionary.registerOre("foodCheese", e.getCheeseItems().asStack());
		}
		
		for (WaxedCheeseTypes e : WaxedCheeseTypes.values())
		{
			OreDictionary.registerOre("foodCheese", e.getCheeseItems().asStack());
		}
		
		for (SimpleCheeseTypes e : SimpleCheeseTypes.values())
		{
			OreDictionary.registerOre("foodCheese", e.getCheeseItems().asStack());
		}

		OreDictionary.registerOre("foodYogurt", GrowthcraftMilkItems.yogurt.asStack());
		OreDictionary.registerOre("materialStomach", GrowthcraftMilkItems.stomach.asStack());
		OreDictionary.registerOre("rennetSource", GrowthcraftMilkItems.thistle.getItem());
		OreDictionary.registerOre("rennetSource", GrowthcraftMilkItems.stomach.asStack());
		OreDictionary.registerOre("foodOffal", GrowthcraftMilkItems.stomach.asStack());
		OreDictionary.registerOre("materialStarterCulture", GrowthcraftMilkItems.starterCulture.asStack());
		OreDictionary.registerOre("materialCheeseCloth", GrowthcraftMilkItems.cheeseCloth.asStack());
		
		if (GrowthcraftMilkItems.thistleSeed != null)
		{
			OreDictionary.registerOre("listAllseed", GrowthcraftMilkItems.thistleSeed.asStack());
		}
		
		OreDictionary.registerOre("foodMelon", Items.MELON);
		OreDictionary.registerOre("foodFruit", Items.MELON);
		OreDictionary.registerOre("foodChorusFruit", Items.CHORUS_FRUIT);
		OreDictionary.registerOre("foodFruit", Items.CHORUS_FRUIT);
	}
	
	@SideOnly(Side.CLIENT)
	private static <ET extends ICheeseType & IStringSerializable> void registerCheeseWheelItemRenders( ItemDefinition itemDef, ET[] types ) {
        for( ET type : types ) {
        	ICheeseBlockStackFactory blockStackFactory = type.getCheeseBlocks();
        	for( EnumCheeseStage stage : EnumCheeseStage.values() ) {
        		if( blockStackFactory.getInitialStage() != EnumCheeseStage.UNWAXED &&
        			stage == EnumCheeseStage.UNWAXED )
        			continue;	// ignore unwaxed stage for non waxable items
        		
        		if( stage == EnumCheeseStage.CUT ) {
        			for( int i = 1; i <= CheeseUtils.MAX_CUTS; i ++ ) {
        				int meta = CheeseUtils.getItemMetaFor(type, i, stage);
        				itemDef.registerRender(meta, type.getName() + "_" + stage.getName() );
        			}
        		}
        		else {
    				int meta = CheeseUtils.getItemMetaFor(type, CheeseUtils.MAX_CUTS, stage);
    				itemDef.registerRender(meta, type.getName() + "_" + stage.getName());
        		}
        	}
        }
	}
	
	@SideOnly(Side.CLIENT)
	private static <ET extends ICheeseType & IStringSerializable> void registerCheeseCurdsItemRenders( ItemDefinition itemDef, ET[] types ) {
        for( ET type : types ) {
        	int meta = CheeseUtils.getItemMetaFor(type, 4, EnumCheeseStage.UNAGED);
        	itemDef.registerRender(meta, type.getName() + "_curds");
        }
	}

	@SideOnly(Side.CLIENT)
	public
    static void registerItemRenders() {
		GrowthcraftMilkItems.thistle.registerRender();
		GrowthcraftMilkItems.thistleSeed.registerRender();
		GrowthcraftMilkItems.stomach.registerRender();
		GrowthcraftMilkItems.cheeseCloth.registerRender();
		GrowthcraftMilkItems.starterCulture.registerRender();

		GrowthcraftMilkItems.butter.registerRenders(ButterTypes.class);
		GrowthcraftMilkItems.iceCream.registerRenders(IceCreamTypes.class);
		GrowthcraftMilkItems.yogurt.registerRenders(YogurtTypes.class);
		GrowthcraftMilkItems.agedCheeseSlice.registerRenders(AgedCheeseTypes.class);
		GrowthcraftMilkItems.waxedCheeseSlice.registerRenders(WaxedCheeseTypes.class);
		GrowthcraftMilkItems.simpleCheeseSlice.registerRenders(SimpleCheeseTypes.class);
        
        registerCheeseWheelItemRenders(GrowthcraftMilkItems.agedCheeseBlockItem, AgedCheeseTypes.values());
        registerCheeseWheelItemRenders(GrowthcraftMilkItems.waxedCheeseBlockItem, WaxedCheeseTypes.values());
        
        registerCheeseCurdsItemRenders(GrowthcraftMilkItems.agedCheeseCurdsItem, AgedCheeseTypes.values());
        registerCheeseCurdsItemRenders(GrowthcraftMilkItems.waxedCheeseCurdsItem, WaxedCheeseTypes.values());
        registerCheeseCurdsItemRenders(GrowthcraftMilkItems.simpleCheeseCurdsItem, SimpleCheeseTypes.values());
    }
    
	@SideOnly(Side.CLIENT)
	public
	static void registerModelBakeryVariants() {
		GrowthcraftMilkItems.butter.registerModelBakeryVariants(ButterTypes.class);
//		agedCheeseBlockItem.registerModelBakeryVariants(AgedCheeseTypes.class);
	}
	
	////////
	// Fluids
	////////
	
	// TODO: Move these to some kind of cheese registry helper.
	
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void preInitCheeseTypes( Map<E, FluidFactory.FluidDetails> fluidMap, Class<E> clazz ) {
		E[] vals = clazz.getEnumConstants();
		for( E type : vals ) {
			final String fluidName = "fluid_cheese_" + type.getName();
			Fluid cheeseFluid = new FluidCheese(fluidName).setColor(type.getColor());
			FluidDetails fluidFactory = new FluidDetailsBuilder(cheeseFluid, FluidFactory.FEATURE_NONE)
						.setFluidBlockClass(BlockFluidCheese.class).build()
							.setBlockColor(cheeseFluid.getColor())
							.setItemColor(cheeseFluid.getColor());
			fluidMap.put(type, fluidFactory);
		}
	}
	
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseTypes( Map<E, FluidFactory.FluidDetails> fluidMap ) {
		for( Map.Entry<E, FluidDetails> entry : fluidMap.entrySet() ) {
			entry.getValue().registerObjects(Reference.MODID, "cheese_" + entry.getKey().getName() );
			CoreRegistry.instance().fluidDictionary().addFluidTags(entry.getValue().getFluid(), MilkFluidTags.CHEESE);
		}	
	}
	
	@SideOnly(Side.CLIENT)
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseRenders( Map<E, FluidFactory.FluidDetails> fluidMap ) {
		for( Map.Entry<E, FluidDetails> entry : fluidMap.entrySet() ) {
			entry.getValue().registerRenderer();
		}
	}

	@SideOnly(Side.CLIENT)
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheeseColorHandlers( Map<E, FluidFactory.FluidDetails> fluidMap ) {
		for( Map.Entry<E, FluidDetails> entry : fluidMap.entrySet() ) {
			entry.getValue().registerColorHandlers();
		}
	}
	
	private static void preInitCheeseFluids() {
		preInitCheeseTypes(GrowthcraftMilkFluids.cheesesSimple, SimpleCheeseTypes.class);
		preInitCheeseTypes(GrowthcraftMilkFluids.cheesesAged, AgedCheeseTypes.class);
		preInitCheeseTypes(GrowthcraftMilkFluids.cheesesWaxed, WaxedCheeseTypes.class);
	}
	
    
	public static List<Fluid> getMilkFluids()
	{
		final List<Fluid> milks = new ArrayList<Fluid>();
		if (GrowthcraftMilkFluids.milk != null)
			milks.add(GrowthcraftMilkFluids.milk.getFluid());
//		if (ForestryFluids.MILK.exists()) milks.add(ForestryFluids.MILK.getFluid());
		// Automagy Milk
		final Fluid fluidmilk = FluidRegistry.getFluid("fluidmilk");
		if (fluidmilk != null)
			milks.add(fluidmilk);
		return milks;
	}
    
	public static int roundToBottles(int fluidAmount) {
		int numBottles = fluidAmount / GrowthcraftCoreConfig.bottleCapacity;
		return numBottles * GrowthcraftCoreConfig.bottleCapacity;
	}
	
	private static void registerFluidOres()
	{
		if (GrowthcraftMilkFluids.milk != null)
		{
			OreDictionary.registerOre("bottleMilk", GrowthcraftMilkFluids.milk.getFoodBottle().asStack());
		}
		// Milk bucket is the vanilla milk bucket, derp
		OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
		if (GrowthcraftMilkFluids.skimMilk != null)
		{
			OreDictionary.registerOre("bottleSkimmilk", GrowthcraftMilkFluids.skimMilk.asFoodBottleItemStack());
			OreDictionary.registerOre("bucketSkimmilk", GrowthcraftMilkFluids.skimMilk.asBucketItemStack());
		}
		if (GrowthcraftMilkFluids.butterMilk != null)
		{
			OreDictionary.registerOre("bottleButtermilk", GrowthcraftMilkFluids.butterMilk.asFoodBottleItemStack());
			OreDictionary.registerOre("bucketButtermilk", GrowthcraftMilkFluids.butterMilk.asBucketItemStack());
		}
		if (GrowthcraftMilkFluids.whey != null)
		{
			OreDictionary.registerOre("bottleWhey", GrowthcraftMilkFluids.whey.asFoodBottleItemStack());
			OreDictionary.registerOre("bucketWhey", GrowthcraftMilkFluids.whey.asBucketItemStack());
			// https://github.com/GrowthcraftCE/Growthcraft-1.7/issues/419
			OreDictionary.registerOre("foodStock", GrowthcraftMilkFluids.whey.asFoodBottleItemStack());
		}
		if (GrowthcraftMilkFluids.cream != null)
		{
			OreDictionary.registerOre("bottleCream", GrowthcraftMilkFluids.cream.asBottleItemStack());
			OreDictionary.registerOre("bucketCream", GrowthcraftMilkFluids.cream.asBucketItemStack());
		}
	}

    public static void preInitFluids() {
		final IEffect milkEffect = EffectMilk.create(GrowthcraftCellarPotions.potionTipsy);
		if (GrowthcraftMilkConfig.milkEnabled)
		{
			FluidMilk fluidMilk = (FluidMilk)new FluidMilk("fluid_milk");
			ItemFoodBottleFluid foodBottleMilk = (ItemFoodBottleFluid)new ItemFoodBottleFluid(fluidMilk, 4, 0.3f, false);
			foodBottleMilk.setEffect(milkEffect).setAlwaysEdible();
			GrowthcraftMilkFluids.milk = new FluidDetailsBuilder(
				fluidMilk,
				FluidFactory.FEATURE_FOOD_BOTTLE | FluidFactory.FEATURE_BLOCK)
					.setFluidBlockClass(BlockFluidMilk.class)
					.setFoodBottle(foodBottleMilk).build()
						.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFF);
		}
		
		FluidButterMilk fluidButterMilk = new FluidButterMilk("fluid_butter_milk");
		ItemFoodBottleFluid foodBottleButterMilk = new ItemFoodBottleFluid(fluidButterMilk, 6, 0.4f, false);
		{
			final EffectList list = new EffectList();
			list.add(milkEffect);
			if (GrowthcraftMilkConfig.fantasyMilkEffects)
			{
				// Idea from here: http://www.altmedicine101.com/buttermilk
				list.add(new EffectExtinguish());
				list.add(EffectUtils.createAddPotionEffect(MobEffects.FIRE_RESISTANCE, TickUtils.seconds(15), 0));
			}
			foodBottleButterMilk.setEffect(list).setAlwaysEdible();
		}
		GrowthcraftMilkFluids.butterMilk = new FluidDetailsBuilder(fluidButterMilk, FluidFactory.FEATURE_ALL_EDIBLE)
				.setFluidBlockClass(BlockFluidButterMilk.class)
				.setFoodBottle(foodBottleButterMilk).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFEE7);

        FluidCream fluidCream = new FluidCream("fluid_cream");
        GrowthcraftMilkFluids.cream = new FluidDetailsBuilder(fluidCream)
				.setFluidBlockClass(BlockFluidCream.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFDD0);

        FluidMilkCurds fluidMilkCurds = new FluidMilkCurds("fluid_milk_curds");
        GrowthcraftMilkFluids.curds = new FluidDetailsBuilder(fluidMilkCurds)
				.setFluidBlockClass(BlockFluidMilkCurds.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFF6);
        
        FluidRennet fluidRennet = new FluidRennet("fluid_rennet");
        GrowthcraftMilkFluids.rennet = new FluidDetailsBuilder(fluidRennet)
				.setFluidBlockClass(BlockFluidRennet.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0x877243);
        
        FluidSkimMilk fluidSkimMilk = new FluidSkimMilk("fluid_skim_milk");
        ItemFoodBottleFluid foodBottleSkimMilk = new ItemFoodBottleFluid(fluidSkimMilk, 2, 0.2f, false);
		{
			final EffectList list = new EffectList();
			list.add(milkEffect);
			if (GrowthcraftMilkConfig.fantasyMilkEffects)
			{
				list.add(EffectUtils.createAddPotionEffect(MobEffects.SPEED, TickUtils.seconds(15), 0));
			}
			foodBottleSkimMilk.setEffect(list).setAlwaysEdible();
		}        
		GrowthcraftMilkFluids.skimMilk = new FluidDetailsBuilder(fluidSkimMilk, FluidFactory.FEATURE_ALL_EDIBLE)
        		.setFluidBlockClass(BlockFluidSkimMilk.class)
        		.setFoodBottle(foodBottleSkimMilk).build()
        			.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFA);
		
        FluidWhey fluidWhey = new FluidWhey("fluid_whey");
		ItemFoodBottleFluid foodBottleWhey = new ItemFoodBottleFluid(fluidWhey, 1, 0.1f, false);
		{
			final EffectList list = new EffectList();
			if (GrowthcraftMilkConfig.fantasyMilkEffects)
			{
				list.add(EffectUtils.createAddPotionEffect(MobEffects.STRENGTH, TickUtils.seconds(10), 0));
				list.add(EffectUtils.createAddPotionEffect(MobEffects.RESISTANCE, TickUtils.seconds(10), 0));
			}
			foodBottleWhey.setEffect(list).setAlwaysEdible();
		}
		GrowthcraftMilkFluids.whey = new FluidDetailsBuilder(fluidWhey, FluidFactory.FEATURE_ALL_EDIBLE)
				.setFluidBlockClass(BlockFluidWhey.class)
				.setFoodBottle(foodBottleWhey).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0x94a860);
        
        FluidPasteurizedMilk fluidPasteurizedMilk = new FluidPasteurizedMilk("fluid_pasteurized_milk");
        GrowthcraftMilkFluids.pasteurizedMilk = new FluidDetailsBuilder(fluidPasteurizedMilk)
				.setFluidBlockClass(BlockFluidPasteurizedMilk.class).build()
					.setCreativeTab(GrowthcraftCoreApis.tabGrowthcraft).setItemColor(0xFFFFFA);
		
		preInitCheeseFluids();
    }
	
    public static void initFluids() {
		int restCapRounded = roundToBottles(FluidContainerRegistry.BUCKET_VOLUME - 2*GrowthcraftCoreConfig.bottleCapacity);
    	
    	final List<Fluid> milks = getMilkFluids();
		for (Fluid f : milks)
		{
			CoreRegistry.instance().fluidDictionary().addFluidTags(f, MilkFluidTags.MILK);
			
			MilkRegistry.instance().pancheon().addRecipe(
				new FluidStack(f, 1000),
				GrowthcraftMilkFluids.cream.asFluidStack(2*GrowthcraftCoreConfig.bottleCapacity), GrowthcraftMilkFluids.skimMilk.asFluidStack(restCapRounded),
				TickUtils.minutes(1));
		}
	}
	
    public static void registerFluids() {
		if (GrowthcraftMilkFluids.milk != null)
		{
			GrowthcraftMilkFluids.milk.registerObjects(Reference.MODID, "milk");
			// ensure that we don't already have some variation of milk present
			if (FluidRegistry.getFluid("milk") == null)
			{
				FluidContainerRegistry.registerFluidContainer(GrowthcraftMilkFluids.milk.getFluid(), new ItemStack(Items.MILK_BUCKET, 1), new ItemStack(Items.BUCKET, 1));
//OBSOLET				EventHandlerBucketFill.instance().register(milk.getFluidBlock(), new ItemStack(Items.milk_bucket, 1));
			}
		}
		GrowthcraftMilkFluids.butterMilk.registerObjects(Reference.MODID, "butter_milk");
		GrowthcraftMilkFluids.cream.registerObjects(Reference.MODID, "cream");
		GrowthcraftMilkFluids.curds.registerObjects(Reference.MODID, "milk_curds");
		GrowthcraftMilkFluids.rennet.registerObjects(Reference.MODID, "rennet");
		GrowthcraftMilkFluids.skimMilk.registerObjects(Reference.MODID, "skim_milk");
		GrowthcraftMilkFluids.whey.registerObjects(Reference.MODID, "whey");
		GrowthcraftMilkFluids.pasteurizedMilk.registerObjects(Reference.MODID, "pasteurized_milk");

		CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.cream.getFluid(), MilkFluidTags.CREAM);
		CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.curds.getFluid(), MilkFluidTags.MILK_CURDS);
		CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.rennet.getFluid(), MilkFluidTags.RENNET);
		CoreRegistry.instance().fluidDictionary().addFluidTags(GrowthcraftMilkFluids.whey.getFluid(), MilkFluidTags.WHEY);

		GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftMilkFluids.rennet.getFluid())
			.brewsFrom(new FluidStack(FluidRegistry.WATER, 1000), new OreItemStacks("rennetSource"), TickUtils.minutes(1), null);

		GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftMilkFluids.pasteurizedMilk.getFluid())
			.brewsFrom(GrowthcraftMilkFluids.skimMilk.asFluidStack(250), new ItemStack(Items.SUGAR), TickUtils.minutes(1), new Residue(GrowthcraftMilkItems.starterCulture.asStack(1), 1.0f));

		GrowthcraftCellarApis.boozeBuilderFactory.create(GrowthcraftMilkFluids.skimMilk.getFluid())
			.culturesTo(250, GrowthcraftMilkItems.starterCulture.asStack(), 0.6f, TickUtils.seconds(12));

		GrowthcraftMilk.userApis.churnRecipes.addDefault(
				GrowthcraftMilkFluids.cream.asFluidStack(roundToBottles(1000)),
				GrowthcraftMilkFluids.butterMilk.asFluidStack(roundToBottles(500)),
				GrowthcraftMilkItems.butter.asStack(2),
				16);
		
		registerCheeseTypes(GrowthcraftMilkFluids.cheesesSimple);
		registerCheeseTypes(GrowthcraftMilkFluids.cheesesAged);
		registerCheeseTypes(GrowthcraftMilkFluids.cheesesWaxed);
		
		registerFluidOres();
    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidRenders() {
    	if( GrowthcraftMilkFluids.milk != null )
    		GrowthcraftMilkFluids.milk.registerRenderer();
    	GrowthcraftMilkFluids.butterMilk.registerRenderer();
    	GrowthcraftMilkFluids.cream.registerRenderer();
    	GrowthcraftMilkFluids.curds.registerRenderer();
    	GrowthcraftMilkFluids.rennet.registerRenderer();
    	GrowthcraftMilkFluids.skimMilk.registerRenderer();
    	GrowthcraftMilkFluids.whey.registerRenderer();
    	GrowthcraftMilkFluids.pasteurizedMilk.registerRenderer();
		registerCheeseRenders(GrowthcraftMilkFluids.cheesesSimple);
		registerCheeseRenders(GrowthcraftMilkFluids.cheesesAged);
		registerCheeseRenders(GrowthcraftMilkFluids.cheesesWaxed);
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerFluidColorHandlers() {
    	if (GrowthcraftMilkFluids.milk != null)
    		GrowthcraftMilkFluids.milk.registerColorHandlers();
    	GrowthcraftMilkFluids.butterMilk.registerColorHandlers();
    	GrowthcraftMilkFluids.cream.registerColorHandlers();
    	GrowthcraftMilkFluids.curds.registerColorHandlers();
    	GrowthcraftMilkFluids.rennet.registerColorHandlers();
    	GrowthcraftMilkFluids.skimMilk.registerColorHandlers();
    	GrowthcraftMilkFluids.whey.registerColorHandlers();
    	GrowthcraftMilkFluids.pasteurizedMilk.registerColorHandlers();
		registerCheeseColorHandlers(GrowthcraftMilkFluids.cheesesSimple);
		registerCheeseColorHandlers(GrowthcraftMilkFluids.cheesesAged);
		registerCheeseColorHandlers(GrowthcraftMilkFluids.cheesesWaxed);
    }
	
	////////
	// Cheeese
	////////
	
    public static void perInitCheese() {
    	MilkRegistry.instance().cheese().registerCheeses(AgedCheeseTypes.class);
    	MilkRegistry.instance().cheese().registerCheeses(SimpleCheeseTypes.class);
    	MilkRegistry.instance().cheese().registerCheeses(WaxedCheeseTypes.class);
    }
    
	////////
    // Effects
	////////
    
	public static void preInitEffects()
	{
		final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
		reg.register("booze_milk", EffectMilk.class);
//TODO:		reg.register("evil_booze_milk", EffectEvilBoozeMilk.class);
	}

	////////
	// Recipes
	////////
	
	private static void registerCheeseVatRecipes()
	{
		final String[] saltOres = { "foodSalt", "materialSalt", "dustSalt" };

		for (String saltOre : saltOres)
		{
			CheeseVatRecipeBuilder.buildRecipe("CHEDDAR Orange Dye Recipe")
				.outputFluids(WaxedCheeseTypes.CHEDDAR.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeOrange", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("CHEDDAR Pumpkin Recipe")
				.outputFluids(WaxedCheeseTypes.CHEDDAR.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new ItemStack(Blocks.PUMPKIN))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("GORGONZOLA Recipe")
				.outputFluids(AgedCheeseTypes.GORGONZOLA.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("foodFruit", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("EMMENTALER Recipe")
				.outputFluids(AgedCheeseTypes.EMMENTALER.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("cropWheat", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("APPENZELLER Wine Recipe")
				.outputFluids(AgedCheeseTypes.APPENZELLER.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"), new TaggedFluidStacks(1000, "wine"))
				.inputItems(new OreItemStacks(saltOre, 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("APPENZELLER Cider Recipe")
				.outputFluids(AgedCheeseTypes.APPENZELLER.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"), new TaggedFluidStacks(1000, "cider"))
				.inputItems(new OreItemStacks(saltOre, 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("ASIAGO Recipe")
				.outputFluids(AgedCheeseTypes.ASIAGO.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks(saltOre, 1), new OreItemStacks("dyeYellow", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("PARMESAN Recipe")
				.outputFluids(AgedCheeseTypes.PARMESAN.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeWhite", 1))
				.register();

			CheeseVatRecipeBuilder.buildRecipe("MONTEREY Recipe")
				.outputFluids(WaxedCheeseTypes.MONTEREY.getFluids().asFluidStack(5000))
				.inputFluids(new TaggedFluidStacks(5000, "milk_curds"))
				.inputItems(new OreItemStacks(saltOre, 1), new OreItemStacks("dyeRed", 1))
				.register();
		}
	}
	
	private static void registerCraftingRecipes() {
		final int ricottaBowlCount = GrowthcraftMilkConfig.ricottaBowlCount;
		final List<ItemStack> ricottaBowlRecipe = new ArrayList<ItemStack>();
		ricottaBowlRecipe.add(SimpleCheeseTypes.RICOTTA.getCurdBlocks().asStack());
		for (int i = 0; i < ricottaBowlCount; ++i)
		{
			ricottaBowlRecipe.add(new ItemStack(Items.BOWL, 1));
		}
		GameRegistry.addRecipe(new ShapelessItemComparableRecipe(new DriedCurdComparator(),
			SimpleCheeseTypes.RICOTTA.getCheeseItems().asStack(ricottaBowlCount), ricottaBowlRecipe
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(GrowthcraftMilkBlocks.cheeseVat.asStack(),
			GrowthcraftCellarBlocks.brewKettle.asStack()
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkBlocks.churn.asStack(),
			" S ",
			"P P",
			"PPP",
			'S', "stickWood",
			'P', "plankWood"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkBlocks.cheesePress.asStack(),
			"iii",
			"iCi",
			"ppp",
			'i', "ingotIron",
			'C', Blocks.CHEST,
			'p', "slabWood"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkBlocks.pancheon.asStack(),
			"c c",
			"ccc",
			'c', Items.CLAY_BALL
		));
		
		// Salted Butter
		GameRegistry.addRecipe(new ShapelessOreRecipe(ButterTypes.SALTED.asStack(), ButterTypes.UNSALTED.asStack(), "foodSalt"));

		// Yogurt - Plain
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.PLAIN.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL
		));

		// Yogurt - Chocolate
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.CHOCOLATE.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			EnumDye.COCOA_BEANS.asStack()
		));

		// Yogurt - Grape
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.GRAPEPURPLE.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"foodGrapesPurple"
		));

		// Yogurt - Apple
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.APPLE.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"foodApple"
		));

		// Yogurt - Honey
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.HONEY.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"honeyDrop"
		));

		// Yogurt - Melon
		GameRegistry.addRecipe(new ShapelessOreRecipe(YogurtTypes.WATERMELON.asStack(),
			Items.MILK_BUCKET,
			GrowthcraftMilkItems.starterCulture.asStack(),
			Items.BOWL,
			"foodMelon"
		));

		// Ice Cream - Plain
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.PLAIN.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR
		));

		// Ice Cream - Chocolate
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.CHOCOLATE.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			EnumDye.COCOA_BEANS.asStack()
		));

		// Ice Cream - Grape
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.GRAPEPURPLE.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodGrapesPurple"
		));

		// Ice Cream - Apple
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.APPLE.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodApple"
		));

		// Ice Cream - Honey
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.HONEY.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"honeyDrop"
		));

		// Ice Cream - Melon
		GameRegistry.addRecipe(new ShapelessOreRecipe(IceCreamTypes.WATERMELON.asStack(),
			GrowthcraftMilkFluids.cream.asBucketItemStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodMelon"
		));

		// Cheese Cloth
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftMilkItems.cheeseCloth.asStack(4),
			"sss",
			"s s",
			"sss",
			's', Items.STRING
		));
		
		if (GrowthcraftMilkItems.thistleSeed != null && GrowthcraftMilkBlocks.thistle != null)
		{
			GameRegistry.addShapelessRecipe(GrowthcraftMilkItems.thistleSeed.asStack(2), GrowthcraftMilkItems.thistle.getItem());
		}
	}
	
	private static <E extends Enum<?> & ICheeseType & IStringSerializable> void registerCheesePressRecipesFor(Class<E> enumType) {
		E[] values = enumType.getEnumConstants();
		for( ICheeseType type : values ) {
			ICheeseBlockStackFactory blockFactory = type.getCheeseBlocks();
			ICheeseCurdStackFactory curdsFactory = type.getCurdBlocks();
			if(blockFactory != null && curdsFactory != null) {
				MilkRegistry.instance().cheesePress().addRecipe(new DriedCurdsCheesePressRecipe(curdsFactory.asStack(), blockFactory.asStackForStage(4, blockFactory.getInitialStage()), 200));
			}
		}
	}
	
	private static void registerCheesePressRecipes()
	{
		registerCheesePressRecipesFor(AgedCheeseTypes.class);
		registerCheesePressRecipesFor(WaxedCheeseTypes.class);
	}

	
	public static void initRecipes() {
		registerCheeseVatRecipes();
		registerCheesePressRecipes();
		registerCraftingRecipes();
	}
	
	//
	
	public static class DriedCurdComparator implements IItemStackComparator
	{
		private CommonItemStackComparator common = new CommonItemStackComparator();

		public boolean equals(ItemStack expected, ItemStack actual)
		{
			if (expected.getItem() instanceof ItemBlockHangingCurds)
			{
				if (actual.getItem() instanceof ItemBlockHangingCurds)
				{
					final ItemBlockHangingCurds<?> actualCurd = (ItemBlockHangingCurds<?>)actual.getItem();
					final ItemBlockHangingCurds<?> expectedCurd = (ItemBlockHangingCurds<?>)expected.getItem();
					if (expectedCurd.getCheeseType(expected) == actualCurd.getCheeseType(actual))
					{
						if (actualCurd.isDried(actual)) return true;
					}
				}
				return false;
			}
			else
			{
				return common.equals(expected, actual);
			}
		}
	}

}
