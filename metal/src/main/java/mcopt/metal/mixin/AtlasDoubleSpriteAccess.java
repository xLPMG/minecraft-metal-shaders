package mcopt.metal.mixin;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** -Dmcopt.metal.atlasDouble: a sprite's padding (its animation quad is the sprite plus the padding on each side). */
@Mixin(value = TextureAtlasSprite.class, remap = false)
public interface AtlasDoubleSpriteAccess {
	@Accessor("padding")
	int mcopt$adPadding();
}
