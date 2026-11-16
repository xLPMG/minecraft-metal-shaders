package mcopt.metal;

import java.awt.GraphicsEnvironment;
import javax.swing.JOptionPane;

/** The jar's Main-Class: someone ran (double-clicked) the mod jar, so explain that it's a mod and how to install it. */
public final class InstallHelp {
	private InstallHelp() {
	}

	public static void main(String[] args) {
		String version = InstallHelp.class.getPackage().getImplementationVersion();
		String name = "Metal Shaders";
		String text = name + (version == null ? "" : " " + version) + " is a NeoForge mod for Minecraft: Java Edition 26.3, not an app to open.\n"
			+ "\n"
			+ "To install it (Apple Silicon Mac, macOS 15 or later):\n"
			+ "1. Install NeoForge for Minecraft 26.3: https://neoforged.net/\n"
			+ "2. Put this jar in your mods folder (do not install Embeddium or Sodium):\n"
			+ "   ~/Library/Application Support/minecraft/mods (official launcher)\n"
			+ "3. Start Minecraft with the NeoForge profile.\n"
			+ "\n"
			+ "README.txt, next to this jar in the download, has the details.";
		System.out.println(text);
		if (!GraphicsEnvironment.isHeadless()) {
			JOptionPane.showMessageDialog(null, text, name, JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
