package library.proj.model;

public enum Permissions {
    ADMIN(0),
    EMPLOYEE(1),
    USER(2);

    private final int permissions;

    Permissions(int permissions) {this.permissions = permissions;}

    public static Permissions parse(int value){
        return switch (value){
            case 0 -> ADMIN;
            case 1 -> EMPLOYEE;
            case 2 -> USER;
            default -> throw new IllegalArgumentException("Illegal argument (" + value +") for Permissions parsing");
        };
    }

    @Override
    public String toString() {
        String name = super.toString();
        return name.toLowerCase();
    }
}
