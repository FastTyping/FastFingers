import java.util.Random;

public class Text {
	
	private static String text = "the be of end a to in he have it that for day I with as not on she at by this we you do but from or which one "
			+ "all will there say who make when can more if no man out other so what time up go about than into could state only new "
			+ "year some take come these know see use get like then first any work now may such give over think most even find day also "
			+ "after way many must look before great back through long where much should well people down own just because good each "
			+ "those feel seem how high too place little world very still nation hand old life tell write become here show house both "
			+ "between need mean call develop under last right move thing general school never same another begin while number part turn "
			+ "real leave might want point form off child few small since against ask late home interest large person end open public "
			+ "follow during present without again gold govern around possible head consider word program problem however lead system set "
			+ "order eye plan run keep face fact group play stand increase early course change help line would";
	private static String[] words = text.split("[ ]+");
	
	private Text(){
	}
	
	public static String gererateRandomText() {
		  StringBuilder newText = new StringBuilder("");
		  Random rand = new Random();
		  newText.append(words[rand.nextInt(200)]);
		  for (int i = 1; i < 99; i++) {
		   newText.append(" " + words[rand.nextInt(200)]);
		  }
		  return newText.toString();
		 }
	
}
