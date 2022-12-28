package observer;

public class ConcreteMember implements Member {
    public UndoableStringBuilder usb;

    /**
     * a function that update the subscriber on the changes that been done in the StringBuilder
     * <p>
     * basically making a shallow copy to the place in the memory that the StringBuilder stored in
     *
     * @param usb
     */
    public void update(UndoableStringBuilder usb) {
        this.usb = usb;
    }

    public UndoableStringBuilder getUsb() {
        return usb;
    }

    @Override
    public String toString() {
        return usb.toString();
    }


}
