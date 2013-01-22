package powercrystals.powerconverters.power.railcraft;

import buildcraft.core.IMachine;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import powercrystals.core.position.BlockPosition;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityRailCraftProducer extends TileEntityEnergyProducer<ITankContainer> implements IMachine
{
	public TileEntityRailCraftProducer()
	{
		super(PowerConverterCore.powerSystemSteam, 0, ITankContainer.class);
	}
	
	@Override
	public int produceEnergy(int energy)
	{
		int steam = energy / PowerConverterCore.powerSystemSteam.getInternalEnergyPerOutput();
		for(int i = 0; i < 6; i++)
		{
			BlockPosition bp = new BlockPosition(this);
			bp.orientation = ForgeDirection.getOrientation(i);
			bp.moveForwards(1);
			TileEntity te = worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
			
			if(te != null && te instanceof ITankContainer)
			{
				steam -= ((ITankContainer)te).fill(bp.orientation.getOpposite(), new LiquidStack(PowerConverterCore.steamId, steam), true);
			}
			if(steam <= 0)
			{
				return 0;
			}
		}

		return steam * PowerConverterCore.powerSystemSteam.getInternalEnergyPerOutput();
	}
	
	@Override
	public boolean isActive()
	{
		return false;
	}

	@Override
	public boolean manageLiquids()
	{
		return true;
	}

	@Override
	public boolean manageSolids()
	{
		return false;
	}

	@Override
	public boolean allowActions()
	{
		return false;
	}
}
