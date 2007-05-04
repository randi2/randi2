package de.randi2.model.exceptions;

import de.randi2.utility.SystemException;

// TODO auskommentieren.
public class RechtException extends SystemException {

    public static final String NULL_ARGUMENT = "Ungueltiges Argument: 'null'";
    
    public RechtException(String msg){
        super(msg);
    }
}
