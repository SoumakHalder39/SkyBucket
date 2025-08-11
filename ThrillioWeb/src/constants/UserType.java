package constants;

public enum UserType {
  
    USER("user"),
    EDITOR("editor"),
    CHIEF_EDITOR("chiefeditor");

    private UserType(String user){
        this.user = user;
    }
    private String user;
    private String getUser(){
       return user;
    }
}
