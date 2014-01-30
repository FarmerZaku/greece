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
	 public static boolean keyPressed = false;
	
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
		 this.keyPressed = true;
		 System.out.println("Key Pressed");
	 }
	 @Override
	 public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	 {
		 this.keyPressed = false;
		 System.out.println("Key Released");
	 }
	 @Override
	 public EnumSet<TickType> ticks()
	 {
		 return tickTypes;
	 }
}