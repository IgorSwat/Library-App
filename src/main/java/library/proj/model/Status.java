package library.proj.model;

public enum Status {
    AVAILABLE(0),
    NOT_AVAILABLE(1);

    private final int status;

    Status(int status) {this.status = status;}

    @Override
    public String toString() {
        return status == 0 ? "available" : "not available";
    }
}
