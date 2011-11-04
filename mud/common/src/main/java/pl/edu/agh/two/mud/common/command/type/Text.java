package pl.edu.agh.two.mud.common.command.type;

public class Text {
    private String text;

    public Text(String text) {
        this.text = text;
    }
    
    
    public String getText() {
        return text;
    }
    
    
    public void setText(String text) {
        this.text = text;
    }
    
}
