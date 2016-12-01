    
 
 
 
 
 
 
 
 
package api.methods;

    import api.wrappers.*;
    import environment.Data;

    import java.util.ArrayList;
    import java.util.Collections;

    public class Objects {
        public static AnimableObject[] getAllAnimableObjects(){
            try{
                ArrayList<AnimableObject> allObjects = new ArrayList<AnimableObject>();
                Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
                Ground[][] a1 = array[Client.getPlane()];
                for(Ground[] a2 : a1){
                    if(a2==null)
                        continue;
                    for(Ground gd : a2){
                        if(gd==null)
                            continue;
                        AnimableNode list = gd.getAnimableList();
                        if(list!=null){
                            Animable data = list.getAnimable();
                            if(data!=null){
                                AnimableObject fo = new AnimableObject(data.currentObject);
                                if(fo!=null)
                                    allObjects.add(fo);
                            }
                        }
                    }
                }
                return allObjects.toArray(new AnimableObject[]{});
            }
            catch(Exception e){
            }
            return new AnimableObject[]{};
        }
        public static AnimatedObject[] getAllAnimatedObjects(){
            ArrayList<AnimatedObject> animatedObjects = new ArrayList<AnimatedObject>();
            try{
                Ground[][][] tiles = Client.getRSData().getGroundInfo().getGroundArray();
                for(int x=0;x<tiles[Client.getPlane()].length;++x){
                    for(int y=0;y<tiles[Client.getPlane()][x].length;++y){
                        Ground g1 = tiles[Client.getPlane()][x][y];
                        if(g1==null)
                            continue;
                        if(g1.getBoundary1()!=null){
                            Boundary object = g1.getBoundary1();
                            if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedBoundaryObject").getClassName())){
                                animatedObjects.add(new AnimatedBoundaryObject(object.currentObject, x, y).getAnimatedObject());
                            }
                        }
                        if(g1.getBoundary2()!=null){
                            Boundary object = g1.getBoundary2();
                            if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedBoundaryObject").getClassName())){
                                animatedObjects.add(new AnimatedBoundaryObject(object.currentObject, x, y).getAnimatedObject());
                            }
                        }
                        if(g1.getWallDecoration1()!=null){
                            WallDecoration object = g1.getWallDecoration1();
                            if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedWallObject").getClassName())){
                                animatedObjects.add(new AnimatedWallObject(object.currentObject, x, y).getAnimatedObject());
                            }
                        }
                        if(g1.getWallDecoration2()!=null){
                            WallDecoration object = g1.getWallDecoration2();
                            if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedWallObject").getClassName())){
                                animatedObjects.add(new AnimatedWallObject(object.currentObject, x, y).getAnimatedObject());
                            }
                        }
                        if(g1.getFloorDecoration()!=null){
                            FloorDecoration object = g1.getFloorDecoration();
                            if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedFloorObject").getClassName())){
                                animatedObjects.add(new AnimatedFloorObject(object.currentObject, x, y).getAnimatedObject());
                            }
                        }
                        if(g1.getAnimableList()!=null){
                            AnimableNode node = g1.getAnimableList();
                            Animable object = node.getAnimable();
                            if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedAnimableObject").getClassName())){
                                animatedObjects.add(new AnimatedAnimableObject(object.currentObject).getAnimatedObject());
                            }
                        }
                    }
                }
            }
            catch(Exception e){

            }
            animatedObjects.removeAll(Collections.singleton(null));
            return animatedObjects.toArray(new AnimatedObject[]{});
        }
        public static BoundaryObject[] getAllBoundaryObjects(){
            try{
                ArrayList<BoundaryObject> allObjects = new ArrayList<BoundaryObject>();
                Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
                Ground[][] a1 = array[Client.getPlane()];
                int x=0;
                for(Ground[] a2 : a1){
                    if(a2==null)
                        continue;
                    int y=0;
                    for(Ground gd : a2){
                        if(gd==null)
                            continue;
                        if(gd.getBoundary1()!=null){
                            BoundaryObject fo = new BoundaryObject(gd.getBoundary1().currentObject, x, y);
                            if(fo!=null)
                                allObjects.add(fo);
                        }
                        if(gd.getBoundary2()!=null){
                            BoundaryObject fo = new BoundaryObject(gd.getBoundary2().currentObject, x, y);
                            if(fo!=null)
                                allObjects.add(fo);
                        }
                        y++;
                    }
                    x++;
                }
                return allObjects.toArray(new BoundaryObject[]{});
            }
            catch(Exception e){

            }
            return new BoundaryObject[]{};
        }
        public static FloorObject[] getAllFloorObjects(){
            try{
                ArrayList<FloorObject> allObjects = new ArrayList<FloorObject>();
                Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
                Ground[][] a1 = array[Client.getPlane()];
                int x=0;
                for(Ground[] a2 : a1){
                    if(a2==null)
                        continue;
                    int y=0;
                    for(Ground gd : a2){
                        if(gd==null)
                            continue;
                        if(gd.getFloorDecoration()!=null){
                            FloorObject fo = new FloorObject(gd.getFloorDecoration().currentObject, x, y);
                            if(fo!=null)
                                allObjects.add(fo);
                        }
                        y++;
                    }
                    x++;
                }
                return allObjects.toArray(new FloorObject[]{});
            }
            catch(Exception e){

            }
            return new FloorObject[]{};
        }
        public static WallObject[] getAllWallObjects(){
            try{
                ArrayList<WallObject> allObjects = new ArrayList<WallObject>();
                Ground[][][] array = Client.getRSData().getGroundInfo().getGroundArray();
                Ground[][] a1 = array[Client.getPlane()];
                int x=0;
                for(Ground[] a2 : a1){
                    if(a2==null)
                        continue;
                    int y=0;
                    for(Ground gd : a2){
                        if(gd==null)
                            continue;
                        if(gd.getWallDecoration1()!=null){
                            WallObject fo = new WallObject(gd.getWallDecoration1().currentObject, x, y);
                            if(fo!=null)
                                allObjects.add(fo);
                        }
                        if(gd.getWallDecoration2()!=null){
                            WallObject fo = new WallObject(gd.getWallDecoration2().currentObject, x, y);
                            if(fo!=null)
                                allObjects.add(fo);
                        }
                        y++;
                    }
                    x++;
                }
                return allObjects.toArray(new WallObject[]{});
            }
            catch(Exception e){

            }
            return new WallObject[]{};
        }
        public static GameObject[] getAllAt(Tile t){
            return getAllAt(t.getX(), t.getY());
        }
        public static GameObject[] getAllAt(int x, int y){
            ArrayList<GameObject> objects = new ArrayList<GameObject>();
            for(GameObject go : getAllObjects()){
                if(go.getLocation().equals(new Tile(x, y, 0)))
                    objects.add(go);
            }
            return objects.toArray(new GameObject[]{});
        }
        public static GameObject[] getAllObjects(){
            ArrayList<GameObject> objects = new ArrayList<GameObject>();
            for(AnimableObject ao : getAllAnimableObjects())
                objects.add(new GameObject(ao));
            for(AnimatedObject ao : getAllAnimatedObjects())
                objects.add(new GameObject(ao));
            for(BoundaryObject ao : getAllBoundaryObjects())
                objects.add(new GameObject(ao));
            for(FloorObject ao : getAllFloorObjects())
                objects.add(new GameObject(ao));
            for(WallObject ao : getAllWallObjects())
                objects.add(new GameObject(ao));
            return objects.toArray(new GameObject[]{});
        }
        public static AnimableObject getAnimableObjectAt(int x, int y){
            try{
                Ground g = Client.getRSData().getGroundInfo().getGroundArray()[Client.getPlane()][x-Client.getBaseX()][y-Client.getBaseY()];
                if(g.getAnimableList()==null)
                    return null;
                Animable a = new Animable(g.getAnimableList().getAnimable().currentObject);
                if(a.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimableObject").getClassName())){
                    return new AnimableObject(a.currentObject);
                }
            }
            catch(Exception e){
            }
            return null;
        }
        public static AnimatedObject getAnimatedObjectAt(int x, int y){
            try{
                Ground g1 = Client.getRSData().getGroundInfo().getGroundArray()[Client.getPlane()][x-Client.getBaseX()][y-Client.getBaseY()];
                if(g1.getBoundary1()!=null){
                    Boundary object = g1.getBoundary1();
                    if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedBoundaryObject").getClassName())){
                        return new AnimatedBoundaryObject(object.currentObject, x, y).getAnimatedObject();
                    }
                }
                if(g1.getBoundary2()!=null){
                    Boundary object = g1.getBoundary2();
                    if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedBoundaryObject").getClassName())){
                        return new AnimatedBoundaryObject(object.currentObject, x, y).getAnimatedObject();
                    }
                }
                if(g1.getWallDecoration1()!=null){
                    WallDecoration object = g1.getWallDecoration1();
                    if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedWallObject").getClassName())){
                        return new AnimatedWallObject(object.currentObject, x, y).getAnimatedObject();
                    }
                }
                if(g1.getWallDecoration2()!=null){
                    WallDecoration object = g1.getWallDecoration2();
                    if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedWallObject").getClassName())){
                        return new AnimatedWallObject(object.currentObject, x, y).getAnimatedObject();
                    }
                }
                if(g1.getFloorDecoration()!=null){
                    FloorDecoration object = g1.getFloorDecoration();
                    if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedFloorObject").getClassName())){
                        return new AnimatedFloorObject(object.currentObject, x, y).getAnimatedObject();
                    }
                }
                if(g1.getAnimableList()!=null){
                    AnimableNode node = g1.getAnimableList();
                    Animable object = node.getAnimable();
                    if(object.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedAnimableObject").getClassName())){
                        return new AnimatedAnimableObject(object.currentObject).getAnimatedObject();
                    }
                }
            }
            catch(Exception e){
            }
            return null;
        }
        public static BoundaryObject getBoundaryObjectAt(int x, int y){
            try{
                Ground g = Client.getRSData().getGroundInfo().getGroundArray()[Client.getPlane()][x-Client.getBaseX()][y-Client.getBaseY()];
                Boundary b = g.getBoundary1();
                if(b==null)
                    b=g.getBoundary2();
                if(b==null)
                    return null;
                if(b.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("BoundaryObject").getClassName())){
                    return new BoundaryObject(b.currentObject, x-Client.getBaseX(), y-Client.getBaseY());
                }
            }
            catch(Exception e){
            }
            return null;
        }
        public static FloorObject getFloorObjectAt(int x, int y){
            try{
                Ground g = Client.getRSData().getGroundInfo().getGroundArray()[Client.getPlane()][x-Client.getBaseX()][y-Client.getBaseY()];
                FloorDecoration a = g.getFloorDecoration();
                if(a.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("FloorObject").getClassName())){
                    return new FloorObject(a.currentObject, x-Client.getBaseX(), y-Client.getBaseY());
                }
            }
            catch(Exception e){
            }
            return null;
        }
        public static WallObject getWallObjectAt(int x, int y){
            try{
                Ground g = Client.getRSData().getGroundInfo().getGroundArray()[Client.getPlane()][x-Client.getBaseX()][y-Client.getBaseY()];
                WallDecoration b = g.getWallDecoration1();
                if(b==null)
                    b=g.getWallDecoration2();
                if(b==null)
                    return null;
                if(b.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("WallObject").getClassName())){
                    return new WallObject(b.currentObject, x-Client.getBaseX(), y-Client.getBaseY());
                }
            }
            catch(Exception e){
            }
            return null;
        }
        public static GameObject getObjectAt(Tile t){
            if(t==null)
                return null;
            return getObjectAt(t.getX(), t.getY());
        }
        public static GameObject getObjectAt(int x, int y){
            for(GameObject go : getAllObjects()){
                if(go.getLocation().equals(new Tile(x, y, 0)))
                    return go;
            }
            return null;
        }
        /**
         * Credits Natfoth
         * @param inputID
         * @return
         */
        public static AnimableObject[] getAnimableObjectsByID(int inputID) {
            ArrayList<AnimableObject> allAnimableObjects = new ArrayList<AnimableObject>();
            for (AnimableObject o : Objects.getAllAnimableObjects()) {
                if(o.getID() == inputID) allAnimableObjects.add(o);
            }
            return allAnimableObjects.toArray(new AnimableObject[]{});
        }
        public static AnimatedObject[] getAnimatedObjectsByID(int inputID) {
            ArrayList<AnimatedObject> allAnimatedObjects = new ArrayList<AnimatedObject>();
            for (AnimatedObject o : Objects.getAllAnimatedObjects()) {
                if(o.getID() == inputID) allAnimatedObjects.add(o);
            }
            return allAnimatedObjects.toArray(new AnimatedObject[]{});
        }
        /**
         * Credits Natfoth
         * @param inputID
         * @return
         */
        public static BoundaryObject[] getBoundaryObjectsByID(int inputID) {
            ArrayList<BoundaryObject> allBoundaryObjects = new ArrayList<BoundaryObject>();
            for (BoundaryObject o : Objects.getAllBoundaryObjects()) {
                if(o.getID() == inputID) allBoundaryObjects.add(o);
            }
            return allBoundaryObjects.toArray(new BoundaryObject[]{});
        }
        /**
         * Credits Natfoth
         * @param inputID
         * @return
         */
        public static FloorObject[] getFloorObjectsByID(int inputID) {
            ArrayList<FloorObject> allFloorObjects = new ArrayList<FloorObject>();
            for (FloorObject o : Objects.getAllFloorObjects()) {
                if(o.getID() == inputID) allFloorObjects.add(o);
            }
            return allFloorObjects.toArray(new FloorObject[]{});
        }
        /**
         * Credits Natfoth
         * @param inputID
         * @return
         */
        public static WallObject[] getWallObjectsByID(int inputID) {
            ArrayList<WallObject> allWallObjects = new ArrayList<WallObject>();
            for (WallObject o : Objects.getAllWallObjects()) {
                if(o.getID() == inputID) allWallObjects.add(o);
            }
            return allWallObjects.toArray(new WallObject[]{});
        }
        public static GameObject[] getObjectsByID(int id){
            ArrayList<GameObject> allObjects = new ArrayList<GameObject>();
            for (GameObject o : Objects.getAllObjects()) {
                if(o.getID() == id) allObjects.add(o);
            }
            return allObjects.toArray(new GameObject[]{});
        }
        public static AnimableObject[] getAnimableObjectsByName(String name){
            ArrayList<AnimableObject> objects = new ArrayList<AnimableObject>();
            for(AnimableObject ao : getAllAnimableObjects()){
                ObjectDef def = ao.getObjectDef();
                if(def==null)
                    continue;
                if(def.getName().equals(name))
                    objects.add(ao);
            }
            return objects.toArray(new AnimableObject[]{});
        }
        public static AnimatedObject[] getAnimatedObjectsByName(String name){
            ArrayList<AnimatedObject> objects = new ArrayList<AnimatedObject>();
            for(AnimatedObject ao : getAllAnimatedObjects()){
                ObjectDef def = ao.getObjectDef();
                if(def==null)
                    continue;
                if(def.getName().equals(name))
                    objects.add(ao);
            }
            return objects.toArray(new AnimatedObject[]{});
        }
        public static BoundaryObject[] getBoundaryObjectsByName(String name){
            ArrayList<BoundaryObject> objects = new ArrayList<BoundaryObject>();
            for(BoundaryObject ao : getAllBoundaryObjects()){
                ObjectDef def = ao.getObjectDef();
                if(def==null)
                    continue;
                if(def.getName().equals(name))
                    objects.add(ao);
            }
            return objects.toArray(new BoundaryObject[]{});
        }
        public static FloorObject[] getFloorObjectsByName(String name){
            ArrayList<FloorObject> objects = new ArrayList<FloorObject>();
            for(FloorObject ao : getAllFloorObjects()){
                ObjectDef def = ao.getObjectDef();
                if(def==null)
                    continue;
                if(def.getName().equals(name))
                    objects.add(ao);
            }
            return objects.toArray(new FloorObject[]{});
        }
        public static WallObject[] getWallObjectsByName(String name){
            ArrayList<WallObject> objects = new ArrayList<WallObject>();
            for(WallObject ao : getAllWallObjects()){
                ObjectDef def = ao.getObjectDef();
                if(def==null)
                    continue;
                if(def.getName().equals(name))
                    objects.add(ao);
            }
            return objects.toArray(new WallObject[]{});
        }
        public static GameObject[] getObjectsByName(String name){
            ArrayList<GameObject> objects = new ArrayList<GameObject>();
            for(GameObject ao : getAllObjects()){
                ObjectDef def = ao.getObjectDef();
                if(def==null)
                    continue;
                if(def.getName().equals(name))
                    objects.add(ao);
            }
            return objects.toArray(new GameObject[]{});
        }
        public static AnimableObject getNearestAnimableObjectByID(int...ids) {
            AnimableObject temp = null;
            double dist = Double.MAX_VALUE;
            for (AnimableObject ao : getAllAnimableObjects()) {
                int id = ao.getID();
                for (int i : ids) {
                    if (i == id) {
                        double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
                        if (distance < dist) {
                            dist = distance;
                            temp = ao;
                        }
                    }
                }
            }
            return temp;
        }
        public static AnimatedObject getNearestAnimatedObjectByID(int...ids) {
            AnimatedObject temp = null;
            double dist = Double.MAX_VALUE;
            for (AnimatedObject ao : getAllAnimatedObjects()) {
                int id = ao.getID();
                for (int i : ids) {
                    if (i == id) {
                        double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
                        if (distance < dist) {
                            dist = distance;
                            temp = ao;
                        }
                    }
                }
            }
            return temp;
        }
        public static BoundaryObject getNearestBoundaryObjectByID(int...ids) {
            BoundaryObject temp = null;
            double dist = Double.MAX_VALUE;
            for (BoundaryObject ao : getAllBoundaryObjects()) {
                int id = ao.getID();
                for (int i : ids) {
                    if (i == id) {
                        double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
                        if (distance < dist) {
                            dist = distance;
                            temp = ao;
                        }
                    }
                }
            }
            return temp;
        }
        public static FloorObject getNearestFloorObjectByID(int...ids) {
            FloorObject temp = null;
            double dist = Double.MAX_VALUE;
            for (FloorObject ao : getAllFloorObjects()) {
                int id = ao.getID();
                for (int i : ids) {
                    if (i == id) {
                        double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
                        if (distance < dist) {
                            dist = distance;
                            temp = ao;
                        }
                    }
                }
            }
            return temp;
        }
        public static WallObject getNearestWallObjectByID(int...ids) {
            WallObject temp = null;
            double dist = Double.MAX_VALUE;
            for (WallObject ao : getAllWallObjects()) {
                int id = ao.getID();
                for (int i : ids) {
                    if (i == id) {
                        double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
                        if (distance < dist) {
                            dist = distance;
                            temp = ao;
                        }
                    }
                }
            }
            return temp;
        }
        public static GameObject getNearestObjectByID(int...ids) {
            GameObject temp = null;
            double dist = Double.MAX_VALUE;
            for (GameObject ao : getAllObjects()) {
                int id = ao.getID();
                for (int i : ids) {
                    if (i == id) {
                        double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
                        if (distance < dist) {
                            dist = distance;
                            temp = ao;
                        }
                    }
                }
            }
            return temp;
        }
    }
