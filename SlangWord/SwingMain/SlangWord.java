
public class SlangWord {

	private String id;
	private String slang;
	private String definition;
	
	public String getSlang() {
		return slang;
	}
	public void setSlang(String slang) {
		this.slang = slang;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public SlangWord() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SlangWord(String slang, String definition) {
		super();
		this.slang = slang;
		this.definition = definition;
	}
	
}
