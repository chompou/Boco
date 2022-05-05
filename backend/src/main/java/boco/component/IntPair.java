package boco.component;

public class IntPair implements Comparable<IntPair> {
    private int a;
    private int b;

    public IntPair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int compareTo(IntPair o) {
        if (this.b < o.b) {
            return 1;
        } else if (this.b > o.b) {
            return -1;
        }
        return 0;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void incrementB() {
        this.b++;
    }

    public void incrementA() {
        this.a++;
    }
}
