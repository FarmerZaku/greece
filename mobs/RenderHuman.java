package mod.greece.mobs;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHuman extends RenderBiped {
	private static ResourceLocation model = new ResourceLocation("greece", "/textures/mobs/bandit.png"); // removed "final"
	public RenderHuman(ModelBase par1ModelBase, float par2) {
		super((ModelBiped) par1ModelBase, par2);
		// TODO Auto-generated constructor stub
	}
	
	public RenderHuman(ModelBase par1ModelBase, float par2, String file) {
		super((ModelBiped) par1ModelBase, par2);
		model = new ResourceLocation("greece", file);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return model;
	}
	
}
