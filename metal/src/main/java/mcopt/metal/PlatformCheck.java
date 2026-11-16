package mcopt.metal;

import com.mojang.logging.LogUtils;

/**
 * Stops the launch, before any Metal class loads, on a machine the native library can't run on: it is arm64-only and
 * the mod supports macOS 15 and later. The error is logged and thrown from the NeoForge mod constructor so the loader
 * reports it as a mod-loading failure. -Dmcopt.platformCheck.fake=linux (or x86_64, or a macOS version like 15.5)
 * pretends to be that platform, to test the message.
 */
final class PlatformCheck {
	private PlatformCheck() {
	}

	static void run() {
		String os = System.getProperty("os.name", ""), arch = System.getProperty("os.arch", ""), version = System.getProperty("os.version", "");
		String fake = System.getProperty("mcopt.platformCheck.fake");
		if (fake != null) {
			if (fake.matches("[0-9.]+")) version = fake;
			else if (fake.equals("x86_64") || fake.equals("amd64")) arch = fake;
			else os = fake;
		}
		String problem = problem(os, arch, version);
		if (problem == null) return;
		String name = "Metal Shaders";
		String fix = "To play on this computer, remove the " + name + " jar from the mods folder.";
		String message = name + " can't run here. " + problem + " " + fix;
		LogUtils.getLogger().error("{}", message);
		throw new IllegalStateException(message);
	}

	private static String problem(String os, String arch, String version) {
		if (!os.startsWith("Mac")) return "It needs an Apple Silicon Mac (M1 or newer) with macOS 15 or later, and this computer runs " + os + ".";
		if (!arch.equals("aarch64") && !arch.equals("arm64")) {
			return "It needs an Apple Silicon Mac (M1 or newer) and an Apple Silicon (arm64) Java, and this Java is " + arch
				+ " (an Intel Mac, or an Intel Java running through Rosetta).";
		}
		int major;
		try {
			major = Integer.parseInt(version.split("\\.")[0]);
		} catch (NumberFormatException e) {
			return null; // unknown version string: let it try rather than block a working Mac
		}
		// A JDK built against an older SDK may see macOS 26 as "16.x" (Apple's compatibility numbering); there was no macOS 16.
		// (2026-10-13) 15 (Sequoia) and later; mcopt.platformCheck.minMacos (15) moves the floor for testing
		int min = Integer.getInteger("mcopt.platformCheck.minMacos", 15);
		if (major < min) return "It needs macOS " + min + " or later, and this Mac runs macOS " + version + ". Update macOS in System Settings > General > Software Update.";
		return null;
	}
}
