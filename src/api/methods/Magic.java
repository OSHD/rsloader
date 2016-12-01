








package api.methods;

import api.wrappers.*;
/**
 * 
 * @author  Steven10172
 * @notes   
 *
 *
 */
public class Magic {

	private static final int parentInterface = 192;

	public static boolean isTabOpen() {
		return Tabs.MAGIC.isOpen();
	}

	public static int getActiveSpellBook() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static Interface getParentInterface() {
		return Client.getInterfaceCache()[parentInterface];
	}

	public static int getParentInterfaceID() {
		return parentInterface;
	}

	public enum SpellBook {
		STANDARD(0),
		ANCIENTS(1),
		LUNAR(2),
		DAEMONHEIM(3);

		public int id;

		SpellBook(int id) {
			this.id = id;
		}

		public boolean isActive() {
			return (getActiveSpellBook() == id);
		}
	}

	public enum Runes {
		FIRE(554),
		WATER(555),
		AIR(556),
		EARTH(557),
		MIND(558),
		BODY(559),
		DEATH(560),
		NATURE(561),
		CHAOS(562),
		LAW(563),
		COSMIC(564),
		BLOOD(565),
		SOUL(566),
		STEAM(4694),
		MIST(4695),
		DUST(4696),
		SMOKE(4697),
		MUD(4698),
		LAVA(4699);

		private int id;

		Runes(int runeID) {
			this.id = runeID;
		}

		public int getID() {
			return id;
		}
	}


	public enum Spell {
		HOME_TELEPORT_STANDARD(0, 24, SpellBook.STANDARD),
		WIND_RUSH(1, 143, 92, SpellBook.STANDARD),
		WIND_STRIKE(1, 3, 25, SpellBook.STANDARD),
		CONFUSE(3, 26, SpellBook.STANDARD),
		ENCHANT_CROSSBOW_BOLT(4, 27, SpellBook.STANDARD),
		WATER_STRIKE(5, 5, 28, SpellBook.STANDARD),
		LEVEL_1_ENCHANT(7, 29, SpellBook.STANDARD),
		EARTH_STRIKE(9, 7, 30, SpellBook.STANDARD),
		MOBILISING_ARMIES_TELEPORT(10, 37, SpellBook.STANDARD),
		WEAKEN(11, 31, SpellBook.STANDARD),
		FIRE_STRIKE(13, 9, 32, SpellBook.STANDARD),
		BONES_TO_BANANAS(15, 33, SpellBook.STANDARD),
		WIND_BOLT(17, 11, 34, SpellBook.STANDARD),
		CURSE(19, 35, SpellBook.STANDARD),
		BIND(20, 36, SpellBook.STANDARD),
		LOW_LEVEL_ALCHEMY(21, 38, SpellBook.STANDARD),
		WATER_BOLT(23, 13, 39, SpellBook.STANDARD),
		VARROCK_TELEPORT(25, 40, SpellBook.STANDARD),
		LEVEL_2_ENCHANT(27, 41, SpellBook.STANDARD),
		EARTH_BOLT(29, 15, 42, SpellBook.STANDARD),
		LUMBRIDGE_TELEPORT(31, 43, SpellBook.STANDARD),
		TELEKINETIC_GRAB(33, 44, SpellBook.STANDARD),
		FIRE_BOLT(35, 17, 45, SpellBook.STANDARD),
		FALADOR_TELEPORT(37, 46, SpellBook.STANDARD),
		CRUMBLE_UNDEAD(39, 35, 47, SpellBook.STANDARD),
		TELEPORT_TO_HOUSE(40, 48, SpellBook.STANDARD),
		WIND_BLAST(41, 19, 49, SpellBook.STANDARD),
		SUPERHEAT_ITEM(43, 50, SpellBook.STANDARD),
		CAMELOT_TELEPORT(45, 51, SpellBook.STANDARD),
		WATER_BLAST(47, 21, 52, SpellBook.STANDARD),
		LEVEL_3_ENCHANT(49, 53, SpellBook.STANDARD),
		IBAN_BLAST(50, 45, 54, SpellBook.STANDARD),
		SNARE(50, 55, SpellBook.STANDARD),
		MAGIC_DART(50, 37, 56, SpellBook.STANDARD),
		ARDOUGNE_TELEPORT(51, 57, SpellBook.STANDARD),
		EARTH_BLAST(53, 23, 58, SpellBook.STANDARD),
		HIGH_LEVEL_ALCHEMY(55, 59, SpellBook.STANDARD),
		CHARGE_WATER_ORB(56, 60, SpellBook.STANDARD),
		LEVEL_4_ENCHANT(57, 61, SpellBook.STANDARD),
		WATCHTOWER_TELEPORT(58, 62, SpellBook.STANDARD),
		FIRE_BLAST(59, 25, 63, SpellBook.STANDARD),
		CHARGE_EARTH_ORB(60, 64, SpellBook.STANDARD),
		BONES_TO_PEACHES(60, 65, SpellBook.STANDARD),
		SARADOMIN_STRIKE(60, 41, 66, SpellBook.STANDARD),
		CLAWS_OF_GUTHIX(60, 39, 67, SpellBook.STANDARD),
		FLAMES_OF_ZAMORAK(60, 43, 68, SpellBook.STANDARD),
		TROLLHEIM_TELEPORT(61, 69, SpellBook.STANDARD),
		WIND_WAVE(62, 27, 70, SpellBook.STANDARD),
		CHARGE_FIRE_ORB(63, 71, SpellBook.STANDARD),
		TELEPORT_TO_APE_ATOLL(64, 72, SpellBook.STANDARD),
		WATER_WAVE(65, 29, 73, SpellBook.STANDARD),
		CHARGE_AIR_ORB(66, 74, SpellBook.STANDARD),
		VULNERABILITY(66, 75, SpellBook.STANDARD),
		LEVEL_5_ENCHANT(68, 76, SpellBook.STANDARD),
		EARTH_WAVE(70, 31, 77, SpellBook.STANDARD),
		ENFEEBLE(73, 78, SpellBook.STANDARD),
		TELEOTHER_LUMBRIDGE(74, 79, SpellBook.STANDARD),
		FIRE_WAVE(75, 33, 80, SpellBook.STANDARD),
		STORM_OF_ARMADYL(77, 145, 99, SpellBook.STANDARD),
		ENTANGLE(79, 81, SpellBook.STANDARD),
		STUN(80, 82, SpellBook.STANDARD),
		CHARGE(80, 83, SpellBook.STANDARD),
		WIND_SURGE(81, 47, 84, SpellBook.STANDARD),
		TELEOTHER_FALADOR(82, 85, SpellBook.STANDARD),
		TELEPORT_BLOCK(85, 86, SpellBook.STANDARD),
		WATER_SURGE(85, 49, 87, SpellBook.STANDARD),
		LEVEL_6_ENCHANT(87, 88, SpellBook.STANDARD),
		EARTH_SURGE(90, 51, 89, SpellBook.STANDARD),
		TELEOTHER_CAMELOT(90, 90, SpellBook.STANDARD),
		FIRE_SURGE(95, 53, 91, SpellBook.STANDARD);

		private int childID;
		private int levelRequired;
		private SpellBook spellBookRequired;
		private int autoCastValue;

		Spell(int lvl, int spellChildID, SpellBook spellBook) {
			this(lvl, spellChildID, -1, spellBook);
		}

		Spell(int lvl, int autoCastValue, int spellChildID, Magic.SpellBook spellBook) {
			this.levelRequired = lvl;
			this.childID = spellChildID;
			this.spellBookRequired = spellBook;
		}

		public String getName() {
			return toString();
		}

		public int getID() {
			return childID;
		}

		public int getRequiredLevel() {
			return levelRequired;
		}

		public boolean hasAutoCast() {
			return (autoCastValue != -1);
		}

		public boolean isSetToAutoCast() {
			return hasAutoCast() && (Settings.get(108) == autoCastValue);
		}

		public void setAutoCast() {
			if(hasAutoCast()) {
				getChildInterface().doAction("Autocast");
			}
		}

		public boolean isSelected() {
			return (getChildInterface().getBorderThickness() == 2);
		}

		public String getSpellBookRequired() {
			return spellBookRequired.toString();
		}

		public boolean isCastAble() {
			if(hasRequiredLevel() && spellBookRequired.isActive() && isTabOpen()) {
				//Check to see if the Spell has required runes
			}
			return false;
		}

		private boolean hasRequiredLevel() {
			if(Skills.getRealSkillLevel(Skills.MAGIC_INDEX) >= levelRequired) {
				return true;
			}
			return false;
		}

		public InterfaceChild getChildInterface() {
			return getParentInterface().getChildren()[childID];
		}

		public void cast() {
			if(isCastAble()) {
				getChildInterface().click();
			}
		}
	}

	public enum Lodestone {
		BANDIT_CAMP(7),
		LUNAR_ISLE(39),
		AL_KHARID(40),
		ARDOUGNE(41),
		BURTHORPE(42),
		CATHERBY(43),
		DRAYNOR_VILLAGE(44),
		EDGEVILLE(45),
		FALADOR(46),
		LUMBRIDGE(47),
		PORT_SARIM(48),
		SEERS_VILLAGE(49),
		TAVERLEY(50),
		VARROCK(51),
		YANILLE(52);

		private static final int INTERFACE_ID = 1092;
		private static final int CLOSE_BTN = 60;
		private static final int INFO_TEXT = 66;
		private int id;

		Lodestone(int id) {
			this.id = id;
		}

		public void close() {
			getParentInterface().getChildren()[CLOSE_BTN].click();
		}

		public static Interface getParentInterface() {
			return Client.getInterfaceCache()[INTERFACE_ID];
		}

		public InterfaceChild getInterfaceChild() {
			return getParentInterface().getChildren()[id];
		}

		public void teleport() {
			if(canTeleport()) {
				getInterfaceChild().click();
			}
		}

		public boolean canTeleport() {
			if(getParentInterface() != null) {
				//Move the mouse to Hover the Lodestone to tell if it's active
				Mouse.move(getInterfaceChild().getRandomPoint());
				return !getParentInterface().getChildren()[INFO_TEXT].getText().contains("is not yet active");
			}
			return false;
		}
	}
}