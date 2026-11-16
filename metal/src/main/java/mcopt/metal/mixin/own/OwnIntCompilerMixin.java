package mcopt.metal.mixin.own;

import mcopt.metal.own.OwnMaterials;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Native shading on our near terrain (OwnMaterials): the class of every block the compiler visits, for the store pass. */
@Mixin(value = SectionCompiler.class, remap = false)
abstract class OwnIntCompilerMixin {
	@Inject(method = "compile", at = @At("HEAD"))
	private void mcopt$compileBegin(CallbackInfoReturnable<SectionCompiler.Results> cir) {
		if (OwnMaterials.ON) OwnMaterials.compiling(true);
	}

	@Inject(method = "compile", at = @At("RETURN"))
	private void mcopt$compileEnd(CallbackInfoReturnable<SectionCompiler.Results> cir) {
		if (OwnMaterials.ON) OwnMaterials.compiling(false);
	}

	// NeoForge adds a deprecated four-argument bridge; the block-state call is in the five-argument implementation.
	@Redirect(method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderSectionRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
		at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/renderer/chunk/RenderSectionRegion;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
	private BlockState mcopt$ownMaterial(RenderSectionRegion region, BlockPos pos) {
		BlockState state = region.getBlockState(pos);
		if (OwnMaterials.ON) OwnMaterials.block(pos.getX(), pos.getY(), pos.getZ(), state);
		return state;
	}
}
