package library.proj.model;

public enum Status {
    AVAILABLE(true),
    NOT_AVAILABLE(false);

    private final boolean status;

    Status(boolean status) {this.status = status;}

    @Override
    public String toString() {
        return status ? "available" : "not available";
    }
}
