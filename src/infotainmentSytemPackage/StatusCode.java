package infotainmentSytemPackage;

public enum StatusCode {
    CONFIRMED(8),
    CONFIRMED_AND_FAILED(9),
    NO_ISSUE(0);

    int number;

    StatusCode(int number) {
        this.number = number;
    }
}
