package mcopt.metal.mixin.own;

import java.util.Map;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** -Dmcopt.own.int.lists: vanilla's per-layer uber buffers. */
@Mixin(value = SectionRenderDispatcher.class, remap = false)
public interface OwnIntDispatcherAccess {
	@Accessor("chunkUberBuffers")
	Map<?, ?> mcopt$uberBuffers();
}
