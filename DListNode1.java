/* DListNode1.java */

/**
 *  A DListNode1 is a node in a DList1 (doubly-linked list).
 */

public class DListNode1 {

  public int[] item;
  public DListNode1 prev;
  public DListNode1 next;

  DListNode1(int[] i, DListNode1 prev, DListNode1 next) {
    item = i;
    this.prev = prev;
    this.next = next;
  }
}