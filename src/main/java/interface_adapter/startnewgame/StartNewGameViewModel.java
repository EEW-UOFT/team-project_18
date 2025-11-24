package interface_adapter.startnewgame;

import entity.CurrentGame;

public class StartNewGameViewModel{

    private CurrentGame currentGame;
    private  String errorMessage;
    public static final String TITLE_LABEL = "HomePage";

    public StartNewGameViewModel(){
    }

    public CurrentGame getCurrentGame(){return currentGame;}

    public void setCurrentGame(CurrentGame currentGame){this.currentGame = currentGame;}

    public String getErrorMessage(){ return errorMessage;}

    public void setErrorMessage(String errorMessage){this.errorMessage = errorMessage;}


}