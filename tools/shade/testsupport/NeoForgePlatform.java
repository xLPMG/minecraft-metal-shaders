package mcopt.metal;

import java.nio.file.Path;

/** Standalone shader fixtures run without a loader and without optional mods. */
public final class NeoForgePlatform {
	private NeoForgePlatform() {
	}

	public static boolean isModLoaded(String modId) {
		return false;
	}

	public static String modVersion(String modId) {
		return "";
	}

	public static Path gameDir() {
		return Path.of(".");
	}
}
