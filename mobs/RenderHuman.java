package mod.greece.mobs;

import java.util.Random;

import mod.greece.GreekVillager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHuman extends RenderBiped {
	boolean villager;
	private static ResourceLocation model = new ResourceLocation("greece", "/textures/mobs/bandit.png"); // removed "final"
	public RenderHuman(ModelBase par1ModelBase, float par2) {
		super((ModelBiped) par1ModelBase, par2);
	}
	
	public RenderHuman(ModelBase par1ModelBase, float par2, String file) {
		super((ModelBiped) par1ModelBase, par2);
		model = new ResourceLocation("greece", file);
	}
	
	public RenderHuman(ModelBase par1ModelBase, float par2, boolean customTexture) {
		super((ModelBiped) par1ModelBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if (entity.getClass() == GreekVillager.class) {
		//if (villager) {
			GreekVillager x = (GreekVillager) entity;
			int y = x.getProfession();
			if (y == 5) {
				model = new ResourceLocation("greece", "/textures/mobs/youth.png");
				return model;
			}
			else if (y == 1) {
				model = new ResourceLocation("greece", "/textures/mobs/orator.png");
				return model;
			}
			else
				model = new ResourceLocation("greece", "/textures/mobs/demesman.png");
				return model;
		}
		model = new ResourceLocation("greece", "/textures/mobs/bandit.png");
		return model;
	}
	
}
