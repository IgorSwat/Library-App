package library.proj.model;

public enum Permissions {
    ADMIN(0),
    EMPLOYEE(1),
    USER(2);

    private final int permissions;

    Permissions(int permissions) {this.permissions = permissions;}

    @Override
    public String toString() {
        String name = super.toString();
        return name.toLowerCase();
    }
}
