/* DList1.java */

/**
 *  A DList1 is a mutable doubly-linked list.  (No sentinel, not
 *  circularly linked.)
 */

public class DList1 {

  /**
   *  head references the first node.
   *  tail references the last node.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode1 head;
  protected DListNode1 tail;
  protected int size;

  /**
   *  DList1() constructor for an empty DList1.
   */
  public DList1() {
    head = null;
    tail = null;
    size = 0;
  }
  
  public boolean isEmpty() {
	  return size == 0;
  }
  
  public int length() {
	  return size;
  }


  /**
   *  insertFront() inserts an item at the front of a DList1.
   */
  public void insertFront(int[] i) {
          if (head == null && tail == null ) {
        	  head = new DListNode1(i, null, null);
        	  head = tail;
          } else {
        	  DListNode1 k = new DListNode1(i, head, null);
        	  head.prev = k;
        	  head = k;
          }
          size++;
  }

  public void insertEnd(int[] i) {
	  if (head == null && tail == null) {
		  head = new DListNode1(i, null, null);
		  tail = head;
	  } else {
		  DListNode1 k = new DListNode1(i, null, tail);
		  tail.next = k;
		  tail = k;
	  }
	  size++;
  }
  
  public int[] nth(int position) {
	  DListNode1 currentNode;
	  if (isEmpty() || position > size || position < 0) {
		  return null;
	  } else {
		  currentNode = head;
		  while(position > 0) {
			  currentNode = currentNode.next;
			  if(currentNode == null) {
				  return null;
			  }
			  position--;
		  }
		  return currentNode.item;
	  }
  }
  
  public DListNode1 returnHead(){
	  return this.head;
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   */
  public String toString() {
    String result = "[  ";
    DListNode1 current = head;
    while (current != null) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
}