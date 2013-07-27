/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
	private int width;
	private int height;
	private int starveTime;
	private DList1 RLE;
	private int position;
	



  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
	  RLE = new DList1();
	  this.width = i;
	  this.height = j; 
	  this.starveTime = starveTime;
	  int[] water = new int[3];
	  water[0] = Ocean.EMPTY;				//Type
	  water[1] = this.width * this.height;	//RunLength
	  water[2] = 0;							//Hunger Level (if shark)
	  RLE.insertEnd(water);
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
	  this.width = i;
	  this.height = j;
	  this.starveTime = starveTime;
	  RLE = new DList1();
	  
	  for(int k = 0; k < runTypes.length; k++){
		  int[] water = new int[3];
		  water[0] = runTypes[k];
		  water[1] = runLengths[k];
		  if(runTypes[k] == Ocean.SHARK){
			  water[2] = starveTime;
		  }
		  else{
			  water[2] = 0;
		  }
		  RLE.insertEnd(water);
	  }
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as an
   *  array of two ints), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
    // Your solution here.
	  this.position = 0;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  an array of two ints (constructed here), representing the type and the
   *  size of the run, in that order.
   *  @return the next run in the enumeration, represented by an array of
   *          two ints.  The int at index zero indicates the run type
   *          (Ocean.EMPTY, Ocean.SHARK, or Ocean.FISH).  The int at index one
   *          indicates the run length (which must be at least 1).
   */

  public int[] nextRun() {
	  int[] i = RLE.nth(position);
	  int[] j = new int[2];
	  if(position < RLE.length()){
		  if(i == null){
			  return null;
		  }
		  else{
			  j[0] = i[0];
			  j[1] = i[1];
			  }
			  position++;
			  return j;
		  }
	  else{
		  return null;
	  }
  }

  
  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
	  Ocean newOcean = new Ocean(width, height, starveTime);
	  int count = 0;
	  int[] array = RLE.nth(count);
	  for(int j = 0; j < height; j++){
		  for(int i = 0; i < width; i++){
			  if(array[1] == Ocean.EMPTY){
				  count++;
				  array = RLE.nth(count);
			  }
			  if(array[0] == Ocean.FISH){
				  newOcean.addFish(i, j);
			  }
			  else if(array[0] == Ocean.SHARK){
				  newOcean.addShark(i, j, array[2]);
			  }
		  array[1] -= 1;
	  	}
  	}
  	return newOcean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
	  this.height = sea.height();
	  this.width = sea.width();
	  this.starveTime = sea.starveTime();
	  this.RLE = new DList1();
	  int type = sea.cellContents(0, 0);
	  int length = 0;
	  int hunger = sea.sharkFeeding(0, 0);

	  for(int i = 0; i < this.height; i++){
		  for(int j = 0; j < this.width; j++){
			  int tempType = sea.cellContents(j, i);
			  int tempHunger = sea.sharkFeeding(j, i);

			  if(type == tempType && hunger == tempHunger){
				  length++;
			  }
			  else{
				  int[] array = new int[3];
				  array[0] = type;
				  array[1] = length;
				  array[2] = hunger;
				  RLE.insertEnd(array);
				  type = tempType;
				  hunger = tempHunger;
				  length = 1;
			  }
		  }
	  }
	  int[] lastNode = new int[3];
	  lastNode[0] = type;
	  lastNode[1] = length;
	  lastNode[2] = hunger;
	  RLE.insertEnd(lastNode);
	  check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
	  int pos = positioninRLE(x, y);
	  addType(pos, Ocean.FISH, 0);
    check();
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
	  int pos = positioninRLE(x, y);
	  addType(pos, Ocean.SHARK, starveTime);
    check();
  }
  
  private int positioninRLE(int x, int y){
		int z = x % this.width;
		if (z < 0) {
			z += this.width;
		}
		x = z;
		int k = x % this.width;
		if (k < 0) {
			k += this.width;
		}
		y = k;
	  return (this.width * y + x);
  }
  
  private void addType(int pos, int type, int hunger){
	  DListNode1 head = RLE.returnHead();
	  DList1 newList = new DList1();
	  while(head.item[1] <= pos){
		  if(pos - head.item[1] < 0)
		  {
			  break;
		  }
		  newList.insertEnd(head.item);
		  pos -= head.item[1];
		  head = head.next;
		  
	  }
	  int[] first = new int[3];
	  first[0] = head.item[0];
	  first[1] = pos;
	  first[2] = head.item[2];
	  int[] thing = new int[3];
	  thing[0] = type;
	  thing[1] = 1;
	  thing[2] = hunger;
	  int[] rest = new int[3];
	  rest[0] = head.item[0];
	  rest[1] = head.item[1] - pos - 1;
	  rest[2] = head.item[2];
	  
	  if(first[1] > 0){
		  newList.insertEnd(first);
	  }
	  newList.insertEnd(thing);
	  if(rest[1] > 0){
		  newList.insertEnd(rest);
	  }
	  while(head.next != null){
		  newList.insertEnd(head.item);
		  head = head.next;
	  }
	  RLE = smoosh(newList);
  }
  
  private DList1 smoosh(DList1 list){
	  DList1 squishedList = new DList1();
	  DListNode1 prev = list.returnHead();
	  int[] items = prev.item;
	  while(prev.next != null){
		  int[] temp = prev.next.item;
		  if(items[0] == temp[0] && items[0] != Ocean.SHARK){
			  items[1] = temp[1] + items[1];
		  }
		  else{
			  squishedList.insertEnd(items);
			  items = temp;
			  temp = prev.next.item;
		  }
		  
	  }
	  squishedList.insertEnd(items);
	  return squishedList;
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  private void check() {
	  int RLE_Length = 0;
	  for(int i = 0; i < RLE.length(); i++){
		  RLE_Length += RLE.nth(i)[1];
	  }
	  if(RLE_Length != this.width * this.height){
		  System.err.println("Error: Run Length Encoding not same size as number of Ocean cells!");
		  //System.exit(0);
	  }
	  for(int i = 0; i < RLE.length() - 1; i++){
		  if(RLE.nth(i)[0] == RLE.nth(i+1)[0] && RLE.nth(i)[0] != Ocean.SHARK){
			  System.err.println("Error: Run Length Encoding not same size as number of Ocean cells!");
			  //System.exit(0);
		  }
		  if(RLE.nth(i)[0] == RLE.nth(i+1)[0] && RLE.nth(i)[2] == RLE.nth(i+1)[2]){
			  System.err.println("Error: Two consecutive Sharks with same hunger!");
			  //System.exit(0);
		  }
		  if(RLE.nth(i)[1] < 1){
			  System.err.println("Error: node with length < 1");
			  //System.exit(0);
		  }
	  }
  }
}