package uy.edu.um.wtf.exceptions;

public class ExistingUser extends Exception {
    public ExistingUser(String message) {
        super(message);
    }

    public ExistingUser() {
    }
}
