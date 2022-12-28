package observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* Class that implements the Sender interface who based on the Observer design pattern.
 * this class will notify the subcribers about changes that happened in a specific UndoableStringBuilder
 * that they have register to get updates on.
 *
 *
*@author Noam Teshuva &&& Yovel Zigdon  ID 206865552_314648510
* */

public class GroupAdmin implements Sender {

    public List<Member> members ;
    public UndoableStringBuilder usb;

    public GroupAdmin(){
        this.members = new LinkedList<>();
        this.usb = new UndoableStringBuilder();
    }

    /**
     * register a new subscriber to get the list of subscribers
     * @param obj
     */
    public void register(Member obj) {
        if (members.contains(obj))
            System.out.println("this member already exist in the list");
        else
        members.add(obj);
    }

    /**
     * remove a subscriber from the list
     *
     * @param obj
     */
    public void unregister(Member obj) {

        members.remove(obj);
    }

    /**
     * notify all the ConcreteMember that register for updates about the new changes
     *
     */
    public void notifyConcreteMember(){
        for (Member obj:members)
        {
            obj.update(usb);
            System.out.println("ive been updated");
        }
    }
    /**
     * Inserts the string into this character sequence
     *
     * @param offset starting index for inserting a new string
     * @param obj The inserted String
     * @return returning argument
     */
    public void insert(int offset, String obj) {
        usb.insert(offset, obj);
        notifyConcreteMember();
    }
    /**
     * Appends the specified string to this character sequence.
     *
     * @param obj this specified String
     * @return the returned argument
     */
    @Override
    public void append(String obj) {
            usb.append(obj);
            notifyConcreteMember();
    }
    /**
     * Removes the characters in a substring of this sequence. The substring begins
     * at the specified start and extends to the character at index
     * end - 1 or to the end of the sequence if no such character exists.
     * If start is equal to end, no changes are made.
     *
     * @param start   starting index
     * @param end    ending index
     * @return returning argument
     */
    public void delete(int start, int end) {
        usb.delete(start, end);
        notifyConcreteMember();
    }

    /**
     *A method which undo the last operation the user commit
     * */

    public void undo() {
        usb.undo();
        notifyConcreteMember();
    }
    @Override
    public String toString() {
        return usb.toString();
    }
}
