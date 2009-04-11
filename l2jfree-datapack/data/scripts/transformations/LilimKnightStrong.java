package transformations;

import com.l2jfree.gameserver.instancemanager.TransformationManager;
import com.l2jfree.gameserver.model.L2DefaultTransformation;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;

/**
 * Description: <br>
 * This will handle the transformation, giving the skills, and removing them, when the player logs out and is transformed these skills
 * do not save.
 * When the player logs back in, there will be a call from the enterworld packet that will add all their skills.
 * The enterworld packet will transform a player.
 *
 * @author durgus
 *
 */
public class LilimKnightStrong extends L2DefaultTransformation
{
	public LilimKnightStrong()
	{
		// id, colRadius, colHeight
		super(207, 8.0, 24.4);
	}

	@Override
	public void transformedSkills(L2PcInstance player)
	{
		addSkill(player, 568, 4); // Attack Buster
		addSkill(player, 569, 4); // Attack Storm
		addSkill(player, 570, 4); // Attack Rage
		addSkill(player, 571, 4); // Poison Dust

		player.clearCharges();
	}

	@Override
	public void removeSkills(L2PcInstance player)
	{
		removeSkill(player, 568); // Attack Buster
		removeSkill(player, 569); // Attack Storm
		removeSkill(player, 570); // Attack Rage
		removeSkill(player, 571); // Poison Dust

		player.clearCharges();
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new LilimKnightStrong());
	}
}
