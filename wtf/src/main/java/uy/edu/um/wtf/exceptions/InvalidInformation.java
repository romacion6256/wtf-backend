package uy.edu.um.wtf.exceptions;

import lombok.Getter;

@Getter
public class InvalidInformation extends Exception {

    private String code; // New field for the error code

    // Constructor with an optional error code
    public InvalidInformation(String message) {
        this(null, message); // Call the main constructor with code set to null
    }

    public InvalidInformation(String code, String message) {
        super(message);
        this.code = code;
    }

}
