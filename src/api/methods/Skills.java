
 
 
 
 
 
 
 
 
package api.methods;

import api.wrappers.*;

public class Skills {
	public static int[] XP_TABLE = {0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107,
		2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
		16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983,
		75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742,
		302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
		1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
		3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
		11805606, 13034431, 14391160, 15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
		31777943, 35085654, 38737661, 42769801, 47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
		85539082, 94442737, 104273167};
	public static String[] SKILL_NAMES = {"attack", "defence", "strength", "constitution", "range", "prayer",
			"magic", "cooking", "woodcutting", "fletching", "fishing", "firemaking", "crafting", "smithing", "mining",
			"herblore", "agility", "thieving", "slayer", "farming", "runecrafting", "hunter", "construction",
			"summoning", "dungeoneering"};
	public static int ATTACK_INDEX = 0;
	public static int DEFENSE_INDEX = 1;
	public static int STRENGTH_INDEX = 2;
	public static int CONSTITUTION_INDEX = 3;
	public static int RANGE_INDEX = 4;
	public static int PRAYER_INDEX = 5;
	public static int MAGIC_INDEX = 6;
	public static int COOKING_INDEX = 7;
	public static int WOODCUTTING_INDEX = 8;
	public static int FLETCHING_INDEX = 9;
	public static int FISHING_INDEX = 10;
	public static int FIREMAKING_INDEX = 11;
	public static int CRAFTING_INDEX = 12;
	public static int SMITHING_INDEX = 13;
	public static int MINING_INDEX = 14;
	public static int HERBLORE_INDEX = 15;
	public static int AGILITY_INDEX = 16;
	public static int THIEVING_INDEX = 17;
	public static int SLAYER_INDEX = 18;
	public static int FARMING_INDEX = 19;
	public static int RUNECRAFTING_INDEX = 20;
	public static int HUNTER_INDEX = 21;
	public static int CONSTRUCTION_INDEX = 22;
	public static int SUMMONING_INDEX = 23;
	public static int DUNGEONEERING_INDEX = 24;

	public static int getExpTillLevel(int index, int level){
		int real = getRealSkillLevel(index);
		if(real==-1)
			return -1;
		if (index == DUNGEONEERING_INDEX && (real == 120 || level > 120))
			return 0;
		else if (real == 99 || level > 99)
			return 0;
		return XP_TABLE[level+1] - getSkillExperience(index);
	}
	public static int getExpTillNextLevel(int index){
		return getExpTillLevel(index, getSkillLevel(index));
	}
	public static int getLevel(int exp) {
		for (int i = XP_TABLE.length-1; i>0; i--)
			if (exp>XP_TABLE[i])
				return i;
		return -1;
	}
	public static int getRealSkillLevel(int index){
		return getLevel(getSkillExperience(index));
	}
	public static int getSkillExperience(int index){
		Facade fac = Client.getFacade();
		if(fac!=null){
			SkillInfo[] array = fac.getSkillArray();
			if(array.length>index){
				SkillInfo skill = array[index];
				if(skill!=null){
					return skill.getExperience();
				}
			}
		}
		return -1;
	}
	public static int getSkillIndex(String statName) {
		for (int i = 0; i < SKILL_NAMES.length; i++)
			if (SKILL_NAMES[i].equalsIgnoreCase(statName))
				return i;
		return -1;
	}
	public static int getSkillLevel(int index){
		Facade fac = Client.getFacade();
		if(fac!=null){
			SkillInfo[] array = fac.getSkillArray();
			if(array.length>index){
				SkillInfo skill = array[index];
				if(skill!=null){
					return skill.getCurrentLevel();
				}
			}
		}
		return -1;
	}
	public static int getSkillMaxLevel(int index){
		Facade fac = Client.getFacade();
		if(fac!=null){
			SkillInfo[] array = fac.getSkillArray();
			if(array.length>index){
				SkillInfo skill = array[index];
				if(skill!=null){
					return skill.getMaxLevel();
				}
			}
		}
		return -1;
	}
	public static String getSkillName(int index) {
		if (index>=SKILL_NAMES.length) {
			return "invalid skill index";
		}
		return SKILL_NAMES[index];
	}
}
