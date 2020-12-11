package client;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

public class User extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//possibly need to change to textpane with html allowance
	private String name;
    private JEditorPane nameField;

    public User(String name) {
		this.name = name;
		nameField = new JEditorPane();
		nameField.setContentType("text/html");
		nameField.setText(name);
		nameField.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(nameField);
    }
    
    public void setName(String newName) {
    	nameField.setText(newName);
    }

    public String getName() {
    	return name;
    }
}	