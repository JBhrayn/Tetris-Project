package block;

import java.awt.Point;
import java.util.ArrayList;

public enum BlockType {

	I("I"), J("J"), L("L"), O("O"), S("S"), Z("Z"), T("T"), EMPTY("EMPTY"), DESTROYED("DESTROYED"), GHOST("GHOST");

	private BlockType(String name) {
		this.initializeDefaultLayouts(name);
	}
	
	public ArrayList<Point> getTilesLayout() {
		return tilesLayout;
	}
	
	public int getCenterPointIndex() {
		return centerPointIndex;
	}

	
	public void initializeDefaultLayouts(String name) {
		switch (name) {
		case "I":
				tilesLayout = new ArrayList<>(){{
					add(new Point(1,-1));
					add(new Point(1,0));
					add(new Point(1,1));
					add(new Point(1,2));
				}};
				centerPointIndex = 0;
			break;
		case "J":
				tilesLayout = new ArrayList<>(){{
					add(new Point(0,0));
					add(new Point(1,0));
					add(new Point(1,1));
					add(new Point(1,2));
				}};
				centerPointIndex = 2;
			break;
		case "L":
				tilesLayout = new ArrayList<>(){{
					add(new Point(0,2));
					add(new Point(1,2));
					add(new Point(1,1));
					add(new Point(1,0));
				}};
				centerPointIndex = 2;
			break;
		case "O":
				tilesLayout = new ArrayList<>(){{
					add(new Point(1,1));
					add(new Point(1,2));
					add(new Point(2,1));
					add(new Point(2,2));
				}};
				
				centerPointIndex = 0;
				break;

		case "S":
				tilesLayout = new ArrayList<>(){{
					add(new Point(0,1));
					add(new Point(0,2));
					add(new Point(1,0));
					add(new Point(1,1));
				}};
				centerPointIndex = 3;
			break;
		case "T":
				tilesLayout = new ArrayList<>(){{
					add(new Point(0,1));
					add(new Point(1,0));
					add(new Point(1,1));
					add(new Point(1,2));
				}};
				centerPointIndex = 2;
			break;
		case "Z":
				tilesLayout = new ArrayList<>(){{
					add(new Point(0,0));
					add(new Point(0,1));
					add(new Point(1,1));
					add(new Point(1,2));
				}};
				centerPointIndex = 2;
		default:
			break;
		}
	}
	
	private ArrayList<Point> tilesLayout;
	private int centerPointIndex;
}
