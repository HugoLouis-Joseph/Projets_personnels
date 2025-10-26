package ship;

public class Plane {
    protected String letter;
    protected Orientation ori;
    protected int cpl[];

    public Plane(Orientation ori , int[] cpl) {
        this.ori = ori;
        this.letter = " p ";
        this.cpl = cpl;
    }

    public String getLetter() {
        return this.letter;
    }

    public Orientation getOrientation() {
        return this.ori;
    }

    public int[] getCpl() {
        return this.cpl;
    }

    public void setCpl(int x , int y) {
        this.cpl[0] = x;
        this.cpl[1] = y;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
