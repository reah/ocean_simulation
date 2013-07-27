/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

	/**
	 *  Do not rename these constants.  WARNING:  if you change the numbers, you
	 *  will need to recompile Test4.java.  Failure to do so will give you a very
	 *  hard-to-find bug.
	 */

	public final static int EMPTY = 0;
	public final static int SHARK = 1;
	public final static int FISH = 2;

	/**
	 *  Define any variables associated with an Ocean object here.  These
	 *  variables MUST be private.
	 */
	private int width;
	private int height;
	private int starveTime;
	private int[][] newOcean;
	private int[][] Hunger;


	/**
	 *  The following methods are required for Part I.
	 */

	/**
	 *  Ocean() is a constructor that creates an empty ocean having width i and
	 *  height j, in which sharks starve after starveTime timesteps.
	 *  @param i is the width of the ocean.
	 *  @param j is the height of the ocean.
	 *  @param starveTime is the number of timesteps sharks survive without food.
	 */

	public Ocean(int i, int j, int starveTime) {
		// Your solution here.
		width = i;
		height = j;
		this.starveTime = starveTime;
		newOcean = new int[i][j];
		Hunger = new int[i][j];
	}

	/**
	 *  width() returns the width of an Ocean object.
	 *  @return the width of the ocean.
	 */

	public int width() {
		return width;
	}

	/**
	 *  height() returns the height of an Ocean object.
	 *  @return the height of the ocean.
	 */

	public int height() {
		return height;
	}

	/**
	 *  starveTime() returns the number of timesteps sharks survive without food.
	 *  @return the number of timesteps sharks survive without food.
	 */

	public int starveTime() {
		return starveTime;
	}

	/**
	 * Helper Function:
	 * 
	 * wrap_____() returns an int value wrapping around input integer to fit grid
	 * according to height and width private variables. 
	 * @param num is the number to be wrapped to grid.
	 * @return wrapped num placement on grid/newOcean.
	 */

	private int wrapWidth(int x){

		int z = x % width();
		if (z < 0) {
			z += width();
		}
		return z;
	}

	private int wrapHeight(int y){
		int z = y % height();
		if (z < 0) {
			z += height();
		}
		return z;
	}


	/**
	 *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
	 *  cell is already occupied, leave the cell as it is.
	 *  @param x is the x-coordinate of the cell to place a fish in.
	 *  @param y is the y-coordinate of the cell to place a fish in.
	 */

	public void addFish(int x, int y) {
		x = wrapWidth(x);
		y = wrapHeight(y);
		if(cellContents(x, y) == Ocean.EMPTY){
			newOcean[x][y] = Ocean.FISH;
		}
	}

	/**
	 *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
	 *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
	 *  just eaten.  If the cell is already occupied, leave the cell as it is.
	 *  @param x is the x-coordinate of the cell to place a shark in.
	 *  @param y is the y-coordinate of the cell to place a shark in.
	 */

	public void addShark(int x, int y) {
		addShark(wrapWidth(x), wrapHeight(y), starveTime);
	}

	/**
	 *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
	 *  a fish, and SHARK if it contains a shark.
	 *  @param x is the x-coordinate of the cell whose contents are queried.
	 *  @param y is the y-coordinate of the cell whose contents are queried.
	 */

	public int cellContents(int x, int y) {
		return newOcean[wrapWidth(x)][wrapHeight(y)];
	}

	/**
	 *  timeStep() performs a simulation timestep as described in README.
	 *  @return an ocean representing the elapse of one timestep.
	 */

	public Ocean timeStep() {
		Ocean nextOcean = new Ocean(width(), height(), starveTime());
		//		System.out.println(width());
		for(int i = 0; i < width(); i++){
			for(int j = 0; j < height(); j++){
				int numSharks = countAnimals(i, j, Ocean.SHARK);
				int numFish = countAnimals(i, j, Ocean.FISH);
				//int numEmpty = countAnimals(i, j, Ocean.EMPTY);
				int cell = this.cellContents(i, j);
				if (cell == Ocean.SHARK){
					if( numFish > 0 ){
						nextOcean.addShark(i, j);
					}
					else if (numFish == 0){
						if(this.Hunger[i][j] > 0){
							nextOcean.addShark(i, j); 
							nextOcean.Hunger[i][j] = this.Hunger[i][j] - 1;

						}
					}
				}
				else if(cell == Ocean.FISH){
					if(numSharks >= 2){
						nextOcean.addShark(i, j);
					}
					else if(numSharks == 0){
						nextOcean.addFish(i, j);
					}
				}

				else if(cell == Ocean.EMPTY){
					if(numSharks >= 2 && numFish >= 2){
						nextOcean.addShark(i, j);
					}
					else if(numFish >= 2 && numSharks <= 1){
						nextOcean.addFish(i, j);
					}
				}




			}
		}
		return nextOcean;
	}

	/*
	 * Helper function to find number of specified animal surrounding current cell.   
	 */
	private int countAnimals(int x, int y, int animalType){
		int count = 0;
		for(int i = x - 1; i <= x + 1; i++){
			for(int j = y - 1; j <= y+1; j++){
				if(cellContents(i, j) == animalType){
					count++;
				}
			}
		}
		return count;
	}

	/**
	 *  The following method is required for Part II.
	 */

	/**
	 *  addShark() (with three parameters) places a shark in cell (x, y) if the
	 *  cell is empty.  The shark's hunger is represented by the third parameter.
	 *  If the cell is already occupied, leave the cell as it is.  You will need
	 *  this method to help convert run-length encodings to Oceans.
	 *  @param x is the x-coordinate of the cell to place a shark in.
	 *  @param y is the y-coordinate of the cell to place a shark in.
	 *  @param feeding is an integer that indicates the shark's hunger.  You may
	 *         encode it any way you want; for instance, "feeding" may be the
	 *         last timestep the shark was fed, or the amount of time that has
	 *         passed since the shark was last fed, or the amount of time left
	 *         before the shark will starve.  It's up to you, but be consistent.
	 */

	public void addShark(int x, int y, int feeding) {
		if(cellContents(x, y) == Ocean.EMPTY){
			x = wrapWidth(x);
			y = wrapHeight(y);
			newOcean[x][y] = Ocean.SHARK;
			Hunger[x][y] = feeding;
		}
	}

	/**
	 *  The following method is required for Part III.
	 */

	/**
	 *  sharkFeeding() returns an integer that indicates the hunger of the shark
	 *  in cell (x, y), using the same "feeding" representation as the parameter
	 *  to addShark() described above.  If cell (x, y) does not contain a shark,
	 *  then its return value is undefined--that is, anything you want.
	 *  Normally, this method should not be called if cell (x, y) does not
	 *  contain a shark.  You will need this method to help convert Oceans to
	 *  run-length encodings.
	 *  @param x is the x-coordinate of the cell whose contents are queried.
	 *  @param y is the y-coordinate of the cell whose contents are queried.
	 */

	public int sharkFeeding(int x, int y) {
		if (cellContents(x, y) == Ocean.SHARK){
			return Hunger[x][y];
		}
		return 0;
	}
}