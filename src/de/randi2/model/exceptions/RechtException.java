package de.randi2.model.exceptions;

public class RechtException extends Exception {

    public static final String NULL_ARGUMENT = "Ungueltiges Argument: 'null'";
    
    public RechtException(String msg){
        super(msg);
    }
    
    public RechtException(){
    super();
    }

}
