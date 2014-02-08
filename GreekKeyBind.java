package mod.greece;

import java.util.EnumSet;
import java.util.Iterator;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
class GreekKeyBind extends KeyHandler
{
	 private EnumSet tickTypes = EnumSet.of(TickType.CLIENT);
	 public static boolean blockPressed = false;
	 public static boolean dropPressed = false;
	
	 public GreekKeyBind(KeyBinding[] keyBindings, boolean[] repeatings)
	 {
		 super(keyBindings, repeatings);
	 }
	 @Override
	 public String getLabel()
	 {
		 return "TutorialKey";
	 }
	 @Override
	 public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	 {
		 System.out.println("Key pressed: " + kb.keyDescription);
		 if (kb.keyDescription == "Block") {
			 this.blockPressed = true;
		 } else if (kb.keyDescription == "Drop") {
			 this.dropPressed = true;
		 }
		 //this.keyDown[kb.keyCode] = true;
		 //System.out.println("Key Pressed");
	 }
	 @Override
	 public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	 {
		 if (kb.keyDescription == "Block") {
			 this.blockPressed = false;
		 } else if (kb.keyDescription == "Drop") {
			 this.dropPressed = false;
		 }
	 }
	 @Override
	 public EnumSet<TickType> ticks()
	 {
		 return tickTypes;
	 }
	 
	 public boolean getKey(int keyCode) {
		 return keyDown[keyCode];
	 }
}