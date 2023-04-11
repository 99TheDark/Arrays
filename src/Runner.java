import org.thedark.utils.Array;

public class Runner {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Array<String> names = new Array<String>();

		names.add("Anthony", "Antonio", "Zach", "Sophia", "Sarah");
		names.add("Lily", "Tony", "Caroline", "Kim");
		names.add("Gary");
		names.add("Jim", "John", "Dave", "Eric", "Benjamin", "Terry", "Gabe", "Tim", "Tom", "Isaac", "Noah");
		names.insert("Jennifer", 5);
		names.insert("Josh", 0);
		names.add("Quinn");
		
		names.sort((a, b) -> a.compareToIgnoreCase(b));
		
		Array<Array<String>> tables = names.chunk(5);
		
		Array<String> sentance = new Array<String>();

		sentance.add("H", "e", "l", "l", "o");
		sentance.add(" ");
		sentance.add("t", "h", "e", "r", "e", "!");
		
		Array<Integer> nums = new Array<Integer>();
		nums.add(38, 27, 43, 3, 9, 82, 10);
		nums.sort((Integer a, Integer b) -> a - b);
		
		int sum = nums.reduce((val, cur) -> cur + val, 0);
		
		Array<Character> letters = new Array<Character>();
		letters.add('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H');
		letters.shuffle();
																
	}

}
