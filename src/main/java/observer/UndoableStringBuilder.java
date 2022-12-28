package observer;

import java.util.EmptyStackException;
import java.util.Stack;

public class UndoableStringBuilder {
    private StringBuilder sb;
    private Stack<String> st;

    public UndoableStringBuilder() {
        sb = new StringBuilder();
        st = new <String>Stack();
    }

    /**
     * Appends the specified string to this character sequence.
     *
     * @param str is this specified String
     * @return the returned argument
     */
    public UndoableStringBuilder append(String str) {
        sb.append(str);
        st.push(sb.toString());
        return this;
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
    public UndoableStringBuilder delete(int start, int end) {
        try {
            sb.delete(start, end);
            st.push(sb.toString());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("StringIndexOutOfBoundsException : " + "start " + start + " end " + end + " length " + sb.length());
        }
        return this;
    }

    /**
     * Inserts the string into this character sequence
     *
     * @param offset starting index for inserting a new string
     * @param str    The inserted String
     * @return returning argument
     */
    public UndoableStringBuilder insert(int offset, String str) {
        try {
            sb.insert(offset, str);
            st.push(sb.toString());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("StringIndexOutOfBoundsException : " + "offset " + offset + " length " + sb.length());
        }
        return this;
    }

    /**
     * Replaces the characters in a substring of this sequence with characters in
     * the specified String. The substring begins at the specified start and
     * extends to the character at index end - 1 or to the end of the sequence if
     * no such character exists. First the characters in the substring are removed
     * and then the specified String is inserted at start. (This sequence will be
     * lengthened to accommodate the specified String if necessary).
     *
     * @param start Starting index
     * @param end   Ending index
     * @param str  replace String
     * @return returning argument
     */
    public UndoableStringBuilder replace(int start, int end, String str) {
        try {
            sb.replace(start, end, str);
            st.push(sb.toString());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("StringIndexOutOfBoundsException : " + "start " + start + " end " + end + " length " + sb.length());
        } catch (NullPointerException n) {
            System.out.println("NullPointerException: Cannot invoke String.length() because str is null");
        }
        return this;
    }

    /**
     * Causes this character sequence to be replaced by the reverse of the
     * sequence
     *
     * @return returning argument
     */
    public UndoableStringBuilder reverse() {
        sb.reverse();
        st.push(sb.toString());
        return this;
    }

    /**
     *A method which undo the last operation the user commit
     * */
    public void undo() {
        try {
            st.pop();
            sb = new StringBuilder(st.peek());
        } catch (EmptyStackException e) {
            sb = new StringBuilder();
        }
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
