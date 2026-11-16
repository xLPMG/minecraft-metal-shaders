package mcopt.metal;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = "mcopt_metal", dist = Dist.CLIENT)
public final class MetalMod {
	public MetalMod() {
		PlatformCheck.run();
	}
}
