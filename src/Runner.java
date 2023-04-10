import org.thedark.Array.Array;

public class Runner {

	public static void main(String[] args) {

		Array<String> names = new Array<String>();
		
		names.add("Anthony", "Antonio", "Zach", "Sophia", "Sarah");
		names.add("Lily", "Tony", "Caroline", "Kim");
		names.add("Gary");
		names.add("Jim", "John", "Dave", "Eric", "Benjamin", "Terry", "Gabe", "Tim", "Tom", "Isaac", "Noah");
		names.insert("Jennifer", 5);
		
		Array<String> letters = new Array<String>();
		
		letters.add("H", "e", "l", "l", "o");
		letters.add(" ");
		letters.add("t", "h", "e", "r", "e", "!");
		
		System.out.println(names);
		
	}

}