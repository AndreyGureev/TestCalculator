public enum Roman {
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);

    int value;

    Roman(int value) {
        this.value = value;
    }

    String RepeatName(int i) {
        return this.name().repeat(i);
    }
}