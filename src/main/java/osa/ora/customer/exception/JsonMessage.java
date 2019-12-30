package osa.ora.customer.exception;

public class JsonMessage {
  private String type;
  private String message;
  
  public JsonMessage(String type, String message){
    this.type = type;
    this.message = message;        
  }
  
  public String getType(){
    return this.type;
  }
  
  public String getMessage(){
    return this.message;
  }
}
