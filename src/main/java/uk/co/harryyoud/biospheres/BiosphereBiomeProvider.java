package uk.co.harryyoud.biospheres;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.harryyoud.biospheres.config.BiosphereConfig;

public class BiosphereBiomeProvider extends BiomeProvider {

	public final IWorld world;
	public static final Set<Biome> biomes;
	public static final Biome[] biomesArray;

	static {
		biomes = new HashSet<Biome>(ForgeRegistries.BIOMES.getValues());
		biomes.removeIf((biome) -> BiosphereConfig.bannedBiomeCategories.contains(biome.getCategory()));
		biomes.removeIf(
				(biome) -> BiosphereConfig.bannedBiomes.contains(ForgeRegistries.BIOMES.getKey(biome).toString()));
		biomesArray = biomes.toArray(new Biome[biomes.size()]);
	}

	protected BiosphereBiomeProvider(IWorld worldIn, OverworldBiomeProviderSettings settingsProvider) {
		super(biomes);
		this.world = worldIn;
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		// We get passed a biome coordinate, which is bitshifted to create our
		// approximate block coordinate
		x = (x << 2);
		z = (z << 2);
		BlockPos pos = new BlockPos(x, y, z);
		Sphere sphere = Sphere.getClosest(this.world, pos);
		return sphere.getBiome();
	}
}
