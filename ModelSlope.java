package mod.greece;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSlope extends ModelBase {
	// fields
	ModelRenderer Base;
	/*ModelRenderer Head2;
	ModelRenderer Keyboard;
	ModelRenderer Head1;
	ModelRenderer Head3;
	ModelRenderer Stand1;
	ModelRenderer Stand2;
	ModelRenderer Till;*/
	
	public ModelSlope() {
		textureWidth = 64;
		textureHeight = 128;
		
		Base = new ModelRenderer(this, 0, 48).setTextureSize(textureWidth, textureHeight);
		Base.addBox(-8, -8, -8, 16, 8, 16, 0.0F);
		Base.setRotationPoint(0F, 24F, 0F);
		setRotation(Base, 0F, 0F, 0F);
		/*
		Head2 = new ModelRenderer(this, 0, 0).setTextureSize(textureWidth, textureHeight);
		Head2.addBox(0F, -1F, -4F, 4, 3, 8, 0.0F);
		Head2.setRotationPoint(3F, 20F, 2F);
		setRotation(Head2, 0F, 0F, -0.7853982F);
		Keyboard = new ModelRenderer(this, 0, 22).setTextureSize(textureWidth, textureHeight);
		Keyboard.addBox(-4F, -1F, -6F, 8, 1, 12, 0.0F);
		Keyboard.setRotationPoint(-3F, 21F, 0F);
		setRotation(Keyboard, 0F, 0F, 0F);
		Head1 = new ModelRenderer(this, 88, 0).setTextureSize(textureWidth, textureHeight);
		Head1.addBox(-1F, -2F, -4F, 2, 2, 8, 0.0F);
		Head1.setRotationPoint(6F, 21F, 2F);
		setRotation(Head1, 0F, 0F, 0F);
		Head3 = new ModelRenderer(this, 72, 0).setTextureSize(textureWidth, textureHeight);
		Head3.addBox(-1F, -1F, -1F, 1, 2, 3, 0.0F);
		Head3.setRotationPoint(4F, 18F, 3F);
		setRotation(Head3, 0F, 0F, 0.7853982F);
		Stand1 = new ModelRenderer(this, 72, 0).setTextureSize(textureWidth, textureHeight);
		Stand1.addBox(0F, -8F, 0F, 1, 8, 1, 0.0F);
		Stand1.setRotationPoint(4F, 21F, -5F);
		setRotation(Stand1, 0F, 0F, 0F);
		Stand2 = new ModelRenderer(this, 48, 0).setTextureSize(textureWidth, textureHeight);
		Stand2.addBox(0F, -2F, -1F, 1, 2, 3, 0.0F);
		Stand2.setRotationPoint(4F, 13F, -5F);
		setRotation(Stand2, 0F, 0.5235988F, 0F);
		Till = new ModelRenderer(this, 0, 86).setTextureSize(textureWidth, textureHeight);
		Till.addBox(-8F, -1F, -6F, 8, 1, 12, 0.0F);
		Till.setRotationPoint(0F, 23F, 0F);
		setRotation(Till, 0F, 0F, 0F);*/
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Base.render(f5);
		/*Head2.render(f5);
		Keyboard.render(f5);
		Head1.render(f5);
		Head3.render(f5);
		Stand1.render(f5);
		Stand2.render(f5);
		Till.render(f5);*/
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void renderAll() {
		Base.render(0.0625F);
		/*Head2.render(0.0625F);
		Keyboard.render(0.0625F);
		Head1.render(0.0625F);
		Head3.render(0.0625F);
		Stand1.render(0.0625F);
		Stand2.render(0.0625F);
		Till.render(0.0625F);*/
	}
	
	}
