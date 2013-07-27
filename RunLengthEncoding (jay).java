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
   private int starveTime;
   private DList1 RLE;
   private int cur=0;
   private int width;
   private int height;

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
    // Your solution here.
    this.starveTime=starveTime;
    width=i;
    height=j;
    RLE= new DList1();
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
    // Your solution here.
    width=i;
    height=j;

    RLE= new DList1();
    for(int index=0;index<runTypes.length;index++){
            int[] la= {runTypes[index],runLengths[index],starveTime};
            RLE.insertEnd(la);
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
    cur=0;
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
    // Replace the following line with your solution.
    int[] run= new int[2];
    if(cur>=RLE.size){return null;}
    run[0]=RLE.get(cur)[0];
    run[1]=RLE.get(cur)[1];
    cur++;
    return run;
  }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
    // Replace the following line with your solution
    int index=1;
    int counter=0;
    int[] run=RLE.get(counter);
    Ocean ocean= new Ocean(width, height, starveTime);
    for(int i=0; i<height;i++){
        for(int j=0; j<width; j++){
            if(run[0]==Ocean.FISH){
                ocean.addFish(j,i);
            }            
            if(run[0]==Ocean.SHARK){
                ocean.addShark(j,i,run[2]);
            }
            if(index>=run[1]){
               index=0;
               counter++;
               if(counter>=RLE.size){
                   return ocean;
               }
               run=RLE.get(counter);
            }
            index++;
        }
    }
   return ocean;
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
    // Your solution here, but you should probably leave the following line
    //   at the end.
    height=sea.height();
    width=sea.width();
    starveTime=sea.starveTime();
    RLE= new DList1();
    int runlength=0;
    int type=sea.cellContents(0,0);
    int food=sea.sharkFeeding(0,0);
    int type1=-1;
    int food1=-1;
    for(int j=0;j<sea.height();j++){
        for(int i=0;i<sea.width();i++){
            type1=sea.cellContents(i,j);
            food1=sea.sharkFeeding(i,j);
            if(type1==type && food==food1){runlength++;}
            else{
            int[] la ={type, runlength, food};
                RLE.insertEnd(la);
                type=type1;
                food=food1;
                runlength=1;
            }
            int[] ar ={type, runlength, food};
            if(j==sea.height()-1 && i==sea.width()-1){RLE.insertEnd(ar);}

        }
    }
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
    // Your solution here, but you should probably leave the following line
    //   at the end.
    int position=y*width+x;
    int count=0;
    int[] run=RLE.get(0);
    DList1 newList= new DList1();
    while(position>=0){
       run=RLE.get(count);
       if(position-run[1]<0){
           break;
       }
       else{
           position=position-run[1];
           count++;
           newList.insertEnd(run);
       }
    }
    int[] first={run[0],position,run[2]};
    int[] current={Ocean.FISH, 1, starveTime};
    int[] last={run[0], (run[1]-position)-1, run[2]};
    if(first[1]>0){
    newList.insertEnd(first);}
    newList.insertEnd(current);
    if(last[1]>0){
    newList.insertEnd(last);
    }
    count++;
    while(count<RLE.size){
        newList.insertEnd(RLE.get(count));
        count++;
    }
    RLE= smooth(newList);
    check();

  }

  private DList1 smooth(DList1 list){
     DList1 list2= new DList1(); 
     int[] lastrun= list.get(0);
     for(int i=1; i<list.size;i++){
         int[] run= list.get(i);
         if(lastrun[0]==run[0]&&(lastrun[0]!=Ocean.SHARK||lastrun[2]==run[2])){
            lastrun[1]=lastrun[1]+run[1];
         }
         else{
             list2.insertEnd(lastrun);
             lastrun=run;
         }
     }
     list2.insertEnd(lastrun);
     return list2;
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
    // Your solution here, but you should probably leave the following line
    //   at the end.
    int position=y*width+x;
    int count=0;
    int[] run=RLE.get(0);
    DList1 newList= new DList1();
    while(position>=0){
       run=RLE.get(count);
       if(position-run[1]<0){
           break;
       }
       else{
           position=position-run[1];
           count++;
           newList.insertEnd(run);
       }
    }
    int[] first={run[0],position,run[2]};
    int[] current={Ocean.SHARK, 1, starveTime};
    int[] last={run[0], (run[1]-position)-1, run[2]};
    if(first[1]>0){
    newList.insertEnd(first);}
    newList.insertEnd(current);
    if(last[1]>0){
    newList.insertEnd(last);
    }
    count++;
    while(count<RLE.size){
        newList.insertEnd(RLE.get(count));
        count++;
    }
    RLE= smooth(newList);
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  private void check() {
     int totallength=0;
     int[] lastrun={-1,-1,-1};
     for(int i=0; i<RLE.size;i++){
        int[] run=RLE.get(i);
        totallength=totallength+run[1];
        if(lastrun[0]==run[0]&&(lastrun[2]==run[2])){System.out.println("Warning: RLE is not optimally compressed.");}
        lastrun=run; 
     }
     if(totallength!=width*height){System.out.println("Warning: Length of RLE not equal to length of Ocean.");
}
  }

}
