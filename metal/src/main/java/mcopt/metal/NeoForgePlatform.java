package mcopt.metal;

import java.nio.file.Path;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.FMLPaths;

public final class NeoForgePlatform {
	private NeoForgePlatform() {
	}

	public static boolean isModLoaded(String modId) {
		ModList mods = ModList.get();
		return mods != null ? mods.isLoaded(modId) : loadingModList().getModFileById(modId) != null;
	}

	public static String modVersion(String modId) {
		ModList mods = ModList.get();
		if (mods != null) {
			return mods.getModContainerById(modId)
				.map(container -> container.getModInfo().getVersion().toString())
				.orElse("");
		}
		var modFile = loadingModList().getModFileById(modId);
		if (modFile == null) return "";
		return modFile.getMods().stream()
			.filter(info -> info.getModId().equals(modId))
			.map(info -> info.getVersion().toString())
			.findFirst()
			.orElse("");
	}

	public static Path gameDir() {
		return FMLPaths.GAMEDIR.get();
	}

	private static LoadingModList loadingModList() {
		FMLLoader loader = FMLLoader.getCurrentOrNull();
		if (loader == null || loader.getLoadingModList() == null) {
			throw new IllegalStateException("NeoForge mod loading has not initialized its mod list");
		}
		return loader.getLoadingModList();
	}
}
