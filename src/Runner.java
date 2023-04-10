import org.thedark.Array;

public class Runner {

	public static void main(String[] args) {

		Array<String> names = new Array<String>();

		names.add("Anthony", "Antonio", "Zach", "Sophia", "Sarah");
		names.add("Lily", "Tony", "Caroline", "Kim");
		names.add("Gary");
		names.add("Jim", "John", "Dave", "Eric", "Benjamin", "Terry", "Gabe", "Tim", "Tom", "Isaac", "Noah");
		names.insert("Jennifer", 5);
		
		names.sort((a, b) -> a.compareToIgnoreCase(b));

		Array<String> letters = new Array<String>();

		letters.add("H", "e", "l", "l", "o");
		letters.add(" ");
		letters.add("t", "h", "e", "r", "e", "!");
		
		Array<Integer> nums = new Array<Integer>();
		nums.add(38, 27, 43, 3, 9, 82, 10);
		nums.sort((Integer a, Integer b) -> a - b);
				
	}

}
