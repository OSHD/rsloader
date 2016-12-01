package api.methods;

import injection.wrappers.Canvas;
import injection.wrappers.MouseListener;
import api.wrappers.*;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;


public class Client {
	private static FieldHook rsdata;
	private static FieldHook cameraPitch;
	private static FieldHook cameraX;
	private static FieldHook cameraY;
	private static FieldHook cameraYaw;
	private static FieldHook cameraZ;
	private static FieldHook canvas;
	private static FieldHook client;
	private static FieldHook currentMenuGroupNode;
	private static FieldHook currentAction;
	private static FieldHook collapsedMenuItems;
	private static FieldHook destinationX;
	private static FieldHook destinationY;
	private static FieldHook detailInfoNode;
	private static FieldHook facade;
	private static FieldHook interfaceBoundsArray;
	private static FieldHook interfaceCache;
	private static FieldHook interfaceNodeCache;
	private static FieldHook interfaceIndex;
	private static FieldHook itemDefLoader;
	private static FieldHook itemHashTable;
	private static FieldHook keyboard;
	private static FieldHook loadedHintArrows;
	private static FieldHook loginIndex;
	private static FieldHook loopCycle;
	private static FieldHook menuItems;
	private static FieldHook loadedProjectiles;
	private static FieldHook menuHeight;
	private static FieldHook menuOptionsCount;
	private static FieldHook menuOptionsCountCollapsed;
	private static FieldHook menuWidth;
	private static FieldHook menuX;
	private static FieldHook menuY;
	private static FieldHook minimapAngle;
	private static FieldHook minimapOffset;
	private static FieldHook minimapScale;
	private static FieldHook minimapSetting;
	private static FieldHook mouse;
	private static FieldHook mouseCrosshairState;
	private static FieldHook myPlayer;
	private static FieldHook nPCCount;
	private static FieldHook nPCIndexArray;
	private static FieldHook nPCNodeArray;
	private static FieldHook nPCNodeCache;
	private static FieldHook objectDefLoader;
	private static FieldHook password;
	private static FieldHook plane;
	private static FieldHook playerArray;
	private static FieldHook playerCount;
	private static FieldHook playerIndexArray;
	private static FieldHook playerModelCache;
	private static FieldHook render;
	private static FieldHook selectedItemName;
	private static FieldHook subMenuHeight;
	private static FieldHook subMenuWidth;
	private static FieldHook subMenuX;
	private static FieldHook subMenuY;
	private static FieldHook tileData;
	private static FieldHook validInterfaceArray;
	private static FieldHook username;
	private static FieldHook itemSelected;
	private static FieldHook menuCollapsed;
	private static FieldHook menuOpen;
	private static FieldHook spellSelected;
	private static FieldHook baseX;
	private static FieldHook baseY;

	public static void resetHooks() {
		rsdata = null;
		cameraPitch = null;
		cameraX = null;
		baseX = null;
		baseY = null;
		cameraY = null;
		cameraYaw = null;
		cameraZ = null;
		canvas = null;
		client = null;
		currentMenuGroupNode = null;
		currentAction = null;
		collapsedMenuItems = null;
		destinationX = null;
		destinationY = null;
		detailInfoNode = null;
		facade = null;
		interfaceBoundsArray = null;
		interfaceCache = null;
		interfaceNodeCache = null;
		interfaceIndex = null;
		itemDefLoader = null;
		itemHashTable = null;
		keyboard = null;
		loadedHintArrows = null;
		loginIndex = null;
		loopCycle = null;
		menuItems = null;
		loadedProjectiles = null;
		menuHeight = null;
		menuOptionsCount = null;
		menuOptionsCountCollapsed = null;
		menuWidth = null;
		menuX = null;
		menuY = null;
		minimapAngle = null;
		minimapOffset = null;
		minimapScale = null;
		minimapSetting = null;
		mouse = null;
		mouseCrosshairState = null;
		myPlayer = null;
		nPCCount = null;
		nPCIndexArray = null;
		nPCNodeArray = null;
		nPCNodeCache = null;
		objectDefLoader = null;
		password = null;
		plane = null;
		playerArray = null;
		playerCount = null;
		playerIndexArray = null;
		playerModelCache = null;
		render = null;
		selectedItemName = null;
		subMenuHeight = null;
		subMenuWidth = null;
		subMenuX = null;
		subMenuY = null;
		tileData = null;
		validInterfaceArray = null;
		username = null;
		itemSelected = null;
		menuCollapsed = null;
		menuOpen = null;
		spellSelected = null;
	}

	public static int getPlayerCount() {
		if (playerCount == null)
			playerCount = Data.staticFieldHooks.get("getPlayerCount");
		if (playerCount != null) {
			Object data = playerCount.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * playerCount.getIntMultiplier();
		}
		return -1;
	}
	
	public static int getBaseX() {

		if (baseX == null)
			baseX = Data.staticFieldHooks.get("getBaseX");
		if (baseX != null) {
			Object data = baseX.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * baseX.getIntMultiplier();
		}
		return -1;
	}

	public static int getBaseY() {
		if (baseY == null)
			baseY = Data.staticFieldHooks.get("getBaseY");
		if (baseY != null) {
			Object data = baseY.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * baseY.getIntMultiplier();
		}
		return -1;
	}

	public static int getCameraPitch() {
		if (cameraPitch == null)
			cameraPitch = Data.staticFieldHooks.get("getCameraPitch");
		if (cameraPitch != null) {
			Object data = cameraPitch.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * cameraPitch.getIntMultiplier();
		}
		return -1;
	}

	public static int getCameraX() {
		if (cameraX == null)
			cameraX = Data.staticFieldHooks.get("getCameraX");
		if (cameraX != null) {
			Object data = cameraX.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * cameraX.getIntMultiplier();
		}
		return -1;
	}
	
	public static GameObject[] getAllGameObjects()
	{
		return null;
//		FieldHook region = Data.staticFieldHooks.get("getRegion");
//		if(region != null)
//		{
//			ClassHook regionHook = Data.runtimeClassHooks.get("Region");
//			FieldHook sceneTiles = Data.staticFieldHooks.get("getSceneTiles");
//			Object data = sceneTiles.get(regionHook);
//			
//			if (data != null) {
//				Object[] array = new Object[Array.getLength(data)];
//				for (int i = 0; i < array.length; ++i) {
//					Object plData = Array.get(data, i);
//					array[i] = plData;
//				}
//				
//			}
//			
//			ClassHook sceneTileHook = Data.runtimeClassHooks.get("SceneTile");
//		}
//		
//		if (playerArray == null)
//			playerArray = Data.staticFieldHooks.get("getPlayerArray");
//		if (playerArray != null) {
//			Object data = playerArray.get(Data.clientBootClass);
//			if (data != null) {
//				Player[] array = new Player[Array.getLength(data)];
//				for (int i = 0; i < array.length; ++i) {
//					Object plData = Array.get(data, i);
//					array[i] = plData != null ? new Player(plData) : null;
//				}
//				return array;
//			}
//		}
//		return new Player[] {};
		
	}

	public static int getCameraY() {
		if (cameraY == null)
			cameraY = Data.staticFieldHooks.get("getCameraY");
		if (cameraY != null) {
			Object data = cameraY.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * cameraY.getIntMultiplier();
		}
		return -1;
	}

	public static int getCameraYaw() {
		if (cameraYaw == null)
			cameraYaw = Data.staticFieldHooks.get("getCameraYaw");
		if (cameraYaw != null) {
			Object data = cameraYaw.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * cameraYaw.getIntMultiplier();
		}
		return -1;
	}

	public static int getCameraZ() {
		if (cameraZ == null)
			cameraZ = Data.staticFieldHooks.get("getCameraZ");
		if (cameraZ != null) {
			Object data = cameraZ.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * cameraZ.getIntMultiplier();
		}
		return -1;
	}

	public static Canvas getCanvas() {
		if (canvas == null)
			canvas = Data.staticFieldHooks.get("getCanvas");
		if (canvas != null) {
			Object data = canvas.get(Data.clientBootClass);
			if (data != null)
				return (Canvas) data;
		}
		return null;
	}

	public static Object getClient() {
		if (client == null)
			client = Data.staticFieldHooks.get("getClient");
		if (client != null) {
			Object data = client.get(Data.clientBootClass);
			if (data != null)
				return data;
		}
		return null;
	}

	public static NodeSubQueue getCollapsedMenuItems() {
		if (collapsedMenuItems == null)
			collapsedMenuItems = Data.staticFieldHooks
					.get("getCollapsedMenuItems");
		if (collapsedMenuItems != null) {
			Object data = collapsedMenuItems.get(Data.clientBootClass);
			if (data != null)
				return new NodeSubQueue(data);
		}
		return null;
	}

	public static String getCurrentAction() {
		if (currentAction == null)
			currentAction = Data.staticFieldHooks.get("getCurrentAction");
		if (currentAction != null) {
			Object data = currentAction.get(Data.clientBootClass);
			if (data != null)
				return data.toString();
		}
		return "";
	}

	public static MenuGroupNode getCurrentMenuGroupNode() {
		if (currentMenuGroupNode == null)
			currentMenuGroupNode = Data.staticFieldHooks
					.get("getCurrentMenuGroupNode");
		if (currentMenuGroupNode != null) {
			Object data = currentMenuGroupNode.get(Data.clientBootClass);
			if (data != null)
				return new MenuGroupNode(data);
		}
		return null;
	}

	public static Tile getDestination() {
		int x = getDestinationX();
		int y = getDestinationY();
		if (x != -1 && y != -1)
			return new Tile(x, y, getPlane());
		return new Tile(-1, -1);
	}

	public static int getDestinationX() {
		if (destinationX == null)
			destinationX = Data.staticFieldHooks.get("getDestinationX");
		if (destinationX != null) {
			Object data = destinationX.get(Data.clientBootClass);
			if (data != null) {
				int localDest = (((Integer) data) * destinationX
						.getIntMultiplier());
				if (localDest > 0)
					return localDest + getBaseX();
			}
		}
		return -1;
	}

	public static int getDestinationY() {
		if (destinationY == null)
			destinationY = Data.staticFieldHooks.get("getDestinationY");
		if (destinationY != null) {
			Object data = destinationY.get(Data.clientBootClass);
			if (data != null) {
				int localDest = (((Integer) data) * destinationY
						.getIntMultiplier());
				if (localDest > 0)
					return localDest + getBaseY();
			}
		}
		return -1;
	}

	public static DetailInfoNode getDetailInfoNode() {
		if (detailInfoNode == null)
			detailInfoNode = Data.staticFieldHooks.get("getDetailInfoNode");
		if (detailInfoNode != null) {
			Object data = detailInfoNode.get(Data.clientBootClass);
			if (data != null)
				return new DetailInfoNode(data);
		}
		return null;
	}

	public static Facade getFacade() {
		if (facade == null)
			facade = Data.staticFieldHooks.get("getFacade");
		if (facade != null) {
			Object data = facade.get(Data.clientBootClass);
			if (data != null)
				return new Facade(data);
		}
		return null;
	}

	public static Rectangle[] getInterfaceBoundsArray() {
		if (interfaceBoundsArray == null)
			interfaceBoundsArray = Data.staticFieldHooks
					.get("getInterfaceBoundsArray");
		if (interfaceBoundsArray != null) {
			Object data = interfaceBoundsArray.get(Data.clientBootClass);
			if (data != null)
				return (Rectangle[]) data;
		}
		return new Rectangle[] {};
	}

	public static Interface[] getInterfaceCache() {
		if (interfaceCache == null)
			interfaceCache = Data.staticFieldHooks.get("getInterfaceCache");
		if (interfaceCache != null) {
			Object data = interfaceCache.get(Data.clientBootClass);
			if (data != null) {
				Interface[] array = new Interface[Array.getLength(data)];
				for (int i = 0; i < array.length; ++i) {
					Object indexData = Array.get(data, i);
					if (indexData != null) {
						Interface iface = new Interface(i, Array.get(data, i));
						if (iface.getChildren().length > 0)
							array[i] = iface;
						else
							array[i] = null;
					} else
						array[i] = null;
				}
				return array;
			}
		}
		return new Interface[] {};
	}

	public static HashTable getInterfaceNodeCache() {
		if (interfaceNodeCache == null)
			interfaceNodeCache = Data.staticFieldHooks
					.get("getInterfaceNodeCache");
		if (interfaceNodeCache != null) {
			Object data = interfaceNodeCache.get(Data.clientBootClass);
			if (data != null)
				return new HashTable(data);
		}
		return null;
	}

	public static int getInterfaceIndex() {
		if (interfaceIndex == null)
			interfaceIndex = Data.staticFieldHooks.get("getInterfaceIndex");
		if (interfaceIndex != null) {
			Object data = interfaceIndex.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * interfaceIndex.getIntMultiplier();
		}
		return -1;
	}

	public static ItemDefLoader getItemDefLoader() {
		if (itemDefLoader == null)
			destinationY = Data.staticFieldHooks.get("getItemDefLoader");
		if (itemDefLoader != null) {
			Object data = itemDefLoader.get(Data.clientBootClass);
			if (data != null)
				return new ItemDefLoader(data);
		}
		return null;
	}

	public static HashTable getItemHashTable() {
		if (itemHashTable == null)
			itemHashTable = Data.staticFieldHooks.get("getItemHashTable");
		if (itemHashTable != null) {
			Object data = itemHashTable.get(Data.clientBootClass);
			if (data != null)
				return new HashTable(data);
		}
		return null;
	}

	public static Object getKeyboard() {
		if (keyboard == null)
			keyboard = Data.staticFieldHooks.get("getKeyboard");
		if (keyboard != null) {
			Object data = keyboard.get(Data.clientBootClass);
			if (data != null)
				return data;
		}
		return null;
	}

	public static HintArrow[] getLoadedHintArrows() {
		if (loadedHintArrows == null)
			loadedHintArrows = Data.staticFieldHooks.get("getLoadedHintArrows");
		if (loadedHintArrows != null) {
			Object data = loadedHintArrows.get(Data.clientBootClass);
			if (data != null) {
				HintArrow[] array = new HintArrow[Array.getLength(data)];
				for (int i = 0; i < array.length; ++i)
					array[i] = new HintArrow(Array.get(data, i));
				return array;
			}
		}
		return new HintArrow[] {};
	}

	public static int getLoginIndex() {
		if (loginIndex == null)
			loginIndex = Data.staticFieldHooks.get("getLoginIndex");
		if (loginIndex != null) {
			Object data = loginIndex.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * loginIndex.getIntMultiplier();
		}
		return -1;
	}

	public static int getLoopCycle() {
		if (loopCycle == null)
			loopCycle = Data.staticFieldHooks.get("getLoopCycle");
		if (loopCycle != null) {
			Object data = loopCycle.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * loopCycle.getIntMultiplier();
		}
		return -1;
	}

	public static NodeList getMenuItems() {
		if (menuItems == null)
			menuItems = Data.staticFieldHooks.get("getMenuItems");
		if (menuItems != null) {
			Object data = menuItems.get(Data.clientBootClass);
			if (data != null)
				return new NodeList(data);
		}
		return null;
	}

	public static NodeList getLoadedProjectiles() {
		if (loadedProjectiles == null)
			loadedProjectiles = Data.staticFieldHooks
					.get("getLoadedProjectiles");
		if (loadedProjectiles != null) {
			Object data = loadedProjectiles.get(Data.clientBootClass);
			if (data != null)
				return new NodeList(data);
		}
		return null;
	}

	public static int getMenuHeight() {
		if (menuHeight == null)
			menuHeight = Data.staticFieldHooks.get("getMenuHeight");
		if (menuHeight != null && isMenuOpen()) {
			Object data = menuHeight.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * menuHeight.getIntMultiplier();
		}
		return -1;
	}

	public static int getMenuOptionsCount() {
		if (menuOptionsCount == null)
			menuOptionsCount = Data.staticFieldHooks.get("getMenuOptionsCount");
		if (menuOptionsCount != null) {
			Object data = menuOptionsCount.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * menuOptionsCount.getIntMultiplier();
		}
		return -1;
	}

	public static int getMenuOptionsCountCollapsed() {
		if (menuOptionsCountCollapsed == null)
			menuOptionsCountCollapsed = Data.staticFieldHooks
					.get("getMenuOptionsCountCollapsed");
		if (menuOptionsCountCollapsed != null) {
			Object data = menuOptionsCountCollapsed.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data)
						* menuOptionsCountCollapsed.getIntMultiplier();
		}
		return -1;
	}

	public static int getMenuWidth() {
		if (menuWidth == null)
			menuWidth = Data.staticFieldHooks.get("getMenuWidth");
		if (menuWidth != null && isMenuOpen()) {
			Object data = menuWidth.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * menuWidth.getIntMultiplier() - 2;
		}
		return -1;
	}

	public static int getMenuX() {
		if (menuX == null)
			menuX = Data.staticFieldHooks.get("getMenuX");
		if (menuX != null && isMenuOpen()) {
			Object data = menuX.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * menuX.getIntMultiplier() + 1;
		}
		return -1;
	}

	public static int getMenuY() {
		if (menuY == null)
			menuY = Data.staticFieldHooks.get("getMenuY");
		if (menuY != null && isMenuOpen()) {
			Object data = menuY.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * menuY.getIntMultiplier();
		}
		return -1;
	}

	public static float getMinimapAngle() {
		if (minimapAngle == null)
			minimapAngle = Data.staticFieldHooks.get("getMinimapAngle");
		if (minimapAngle != null) {
			Object data = minimapAngle.get(Data.clientBootClass);
			if (data != null)
				return (Float) data;
		}
		return -1;
	}

	public static int getMinimapOffset() {
		if (minimapOffset == null)
			minimapOffset = Data.staticFieldHooks.get("getMinimapOffset");
		if (minimapOffset != null) {
			Object data = minimapOffset.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * minimapOffset.getIntMultiplier();
		}
		return -1;
	}

	public static int getMinimapScale() {
		if (minimapScale == null)
			minimapScale = Data.staticFieldHooks.get("getMinimapScale");
		if (minimapScale != null) {
			Object data = minimapScale.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * minimapScale.getIntMultiplier();
		}
		return -1;
	}

	public static int getMinimapSetting() {
		if (minimapSetting == null)
			minimapSetting = Data.staticFieldHooks.get("getMinimapSetting");
		if (minimapSetting != null) {
			Object data = minimapSetting.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * minimapSetting.getIntMultiplier();
		}
		return -1;
	}

	public static MouseListener getMouse() {
		if (mouse == null)
			mouse = Data.staticFieldHooks.get("getMouse");
		if (mouse != null) {
			Object data = mouse.get(Data.clientBootClass);
			if (data != null)
				return (MouseListener) data;
		}
		return null;
	}

	public static int getMouseCrosshairState() {
		if (mouseCrosshairState == null)
			mouseCrosshairState = Data.staticFieldHooks
					.get("getMouseCrosshairState");
		if (mouseCrosshairState != null) {
			Object data = mouseCrosshairState.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data)
						* mouseCrosshairState.getIntMultiplier();
		}
		return -1;
	}

	public static int getNPCCount() {
		if (nPCCount == null)
			nPCCount = Data.staticFieldHooks.get("getNPCCount");
		if (nPCCount != null) {
			Object data = nPCCount.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * nPCCount.getIntMultiplier();
		}
		return -1;
	}

	public static int[] getNPCIndexArray() {
		if (nPCIndexArray == null)
			nPCIndexArray = Data.staticFieldHooks.get("getNPCIndexArray");
		if (nPCIndexArray != null) {
			Object data = nPCIndexArray.get(Data.clientBootClass);
			if (data != null)
				return (int[]) data;
		}
		return new int[] {};
	}

	public static NPCNode[] getNPCNodeArray() {
		if (nPCNodeArray == null)
			nPCNodeArray = Data.staticFieldHooks.get("getNPCNodeArray");
		if (nPCNodeArray != null) {
			Object data = nPCNodeArray.get(Data.clientBootClass);
			if (data != null) {
				NPCNode[] array = new NPCNode[Array.getLength(data)];
				for (int i = 0; i < array.length; ++i)
					array[i] = new NPCNode(Array.get(data, i));
				return array;
			}
		}
		return new NPCNode[] {};
	}

	public static HashTable getNPCNodeCache() {
		if (nPCNodeCache == null)
			nPCNodeCache = Data.staticFieldHooks.get("getNPCNodeCache");
		if (nPCNodeCache != null) {
			Object data = nPCNodeCache.get(Data.clientBootClass);
			if (data != null)
				return new HashTable(data);
		}
		return null;
	}

	public static ObjectDefLoader getObjectDefLoader() {
		if (objectDefLoader == null)
			objectDefLoader = Data.staticFieldHooks.get("getObjectDefLoader");
		if (objectDefLoader != null) {
			Object data = objectDefLoader.get(Data.clientBootClass);
			if (data != null)
				return new ObjectDefLoader(data);
		}
		return null;
	}

	public static String getPassword() {
		if (password == null)
			password = Data.staticFieldHooks.get("getPassword");
		if (password != null) {
			Object data = password.get(Data.clientBootClass);
			if (data != null)
				return data.toString();
		}
		return "";
	}

	public static int getPlane() {
		if (plane == null)
			plane = Data.staticFieldHooks.get("getPlane");
		if (plane != null) {
			Object data = plane.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * plane.getIntMultiplier();
		}
		return -1;
	}

	public static Player[] getPlayerArray() {
		if (playerArray == null)
			playerArray = Data.staticFieldHooks.get("getPlayerArray");
		if (playerArray != null) {
			Object data = playerArray.get(Data.clientBootClass);
			if (data != null) {
				Player[] array = new Player[Array.getLength(data)];
				for (int i = 0; i < array.length; ++i) {
					Object plData = Array.get(data, i);
					array[i] = plData != null ? new Player(plData) : null;
				}
				return array;
			}
		}
		return new Player[] {};
	}
	
	public static int getPlayerX()
	{
		if (getMyPlayer() == null) return -1;
		return (getMyPlayer().getLocationX() >> 7) + getBaseX();
	}
	
	public static int getPlayerY()
	{
		if (getMyPlayer() == null) return -1;
		return (getMyPlayer().getLocationY() >> 7) + getBaseY();
	}
	
	public static int getCurrentRegionX()
	{
		return getPlayerX() / 64;
	}
	
	public static int getCurrentRegionY()
	{
		return getPlayerY() / 64;
	}
	
	public static Player getMyPlayer() {
		if (myPlayer == null)
			myPlayer = Data.staticFieldHooks.get("getMyPlayer");
		if (myPlayer != null) {
			Object data = myPlayer.get(Data.clientBootClass);
			if (data != null)
				return new Player(data);
		}
		return null;
	}

	public static int[] getPlayerIndexArray() {
		if (playerIndexArray == null)
			playerIndexArray = Data.staticFieldHooks.get("getPlayerIndexArray");
		if (playerIndexArray != null) {
			Object data = playerIndexArray.get(Data.clientBootClass);
			if (data != null)
				return (int[]) data;
		}
		return new int[] {};
	}

	public static Cache getPlayerModels() {
		if (playerModelCache == null)
			playerModelCache = Data.staticFieldHooks.get("getPlayerModels");
		if (playerModelCache != null) {
			Object data = playerModelCache.get(Data.clientBootClass);
			if (data != null)
				return new Cache(data);
		}
		return null;
	}

	public static RenderLD getRenderLD() {
		if (render == null)
			render = Data.staticFieldHooks.get("getRender");
		if (render != null) {
			Object data = render.get(Data.clientBootClass);
			if (data != null)
				return new RenderLD(data);
		}
		return null;
	}

	public static Info getRSData() {
		if (rsdata == null)
			rsdata = Data.staticFieldHooks.get("getRSData");
		if (rsdata != null) {
			Object data = rsdata.get(Data.clientBootClass);
			if (data != null)
				return new Info(data);
		}
		return null;
	}

	public static String getSelectedItemName() {
		if (selectedItemName == null)
			selectedItemName = Data.staticFieldHooks.get("getSelectedItemName");
		if (selectedItemName != null) {
			Object data = selectedItemName.get(Data.clientBootClass);
			if (data != null)
				return data.toString();
		}
		return "";
	}

	public static int getSubMenuHeight() {
		if (subMenuHeight == null)
			subMenuHeight = Data.staticFieldHooks.get("getSubMenuHeight");
		if (subMenuHeight != null) {
			Object data = subMenuHeight.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * subMenuHeight.getIntMultiplier();
		}
		return -1;
	}

	public static int getSubMenuWidth() {
		if (subMenuWidth == null)
			subMenuWidth = Data.staticFieldHooks.get("getSubMenuWidth");
		if (subMenuWidth != null) {
			Object data = subMenuWidth.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * subMenuWidth.getIntMultiplier();
		}
		return -1;
	}

	public static int getSubMenuX() {
		if (subMenuX == null)
			subMenuX = Data.staticFieldHooks.get("getSubMenuX");
		if (subMenuX != null) {
			Object data = subMenuX.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * subMenuX.getIntMultiplier();
		}
		return -1;
	}

	public static int getSubMenuY() {
		if (subMenuY == null)
			subMenuY = Data.staticFieldHooks.get("getSubMenuY");
		if (subMenuY != null) {
			Object data = subMenuY.get(Data.clientBootClass);
			if (data != null)
				return ((Integer) data) * subMenuY.getIntMultiplier();
		}
		return -1;
	}

	public static float[] getTileData() {
		if (tileData == null)
			tileData = Data.staticFieldHooks.get("getTileData");
		if (tileData != null) {
			Object data = tileData.get(Data.clientBootClass);
			if (data != null)
				return (float[]) data;
		}
		return new float[] {};
	}

	public static boolean[] getValidInterfaceArray() {
		if (validInterfaceArray == null)
			validInterfaceArray = Data.staticFieldHooks
					.get("getValidInterfaceArray");
		if (validInterfaceArray != null) {
			Object data = validInterfaceArray.get(Data.clientBootClass);
			if (data != null)
				return (boolean[]) data;
		}
		return new boolean[] {};
	}

	public static String getUsername() {
		if (username == null)
			username = Data.staticFieldHooks.get("getUsername");
		if (username != null) {
			Object data = username.get(Data.clientBootClass);
			if (data != null)
				return data.toString();
		}
		return "";
	}

	public static boolean isItemSelected() {
		if (itemSelected == null)
			itemSelected = Data.staticFieldHooks.get("isItemSelected");
		if (itemSelected != null) {
			Object data = itemSelected.get(Data.clientBootClass);
			if (data != null)
				return (Boolean) data;
		}
		return false;
	}

	public static boolean isLoggedIn() {
		try {
			return Client.getRSData().getGroundInfo() != null;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isMenuCollapsed() {
		if (menuCollapsed == null)
			menuCollapsed = Data.staticFieldHooks.get("isMenuCollapsed");
		if (menuCollapsed != null) {
			Object data = menuCollapsed.get(Data.clientBootClass);
			if (data != null)
				return (Boolean) data;
		}
		return false;
	}

	public static boolean isMenuOpen() {
		if (menuOpen == null)
			menuOpen = Data.staticFieldHooks.get("isMenuOpen");
		if (menuOpen != null) {
			Object data = menuOpen.get(Data.clientBootClass);
			if (data != null)
				return (Boolean) data;
		}
		return false;
	}

	public static boolean isSpellSelected() {
		if (spellSelected == null)
			spellSelected = Data.staticFieldHooks.get("isSpellSelected");
		if (spellSelected != null) {
			Object data = spellSelected.get(Data.clientBootClass);
			if (data != null)
				return (Boolean) data;
		}
		return false;
	}
}
