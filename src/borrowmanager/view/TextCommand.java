package borrowmanager.view;

public class TextCommand {
	private String commandName, text;
	
	public TextCommand(String commandName, String text) {
		this.commandName = commandName;
		this.text = text ;
	}

	/**
	 * @return the commandName
	 */
	public String getCommandName() {
		return commandName;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	
}
