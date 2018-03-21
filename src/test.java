import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> sub = new HashMap<String, String>();
		sub.put("x", "John");
		sub.put("y", "Joe");
		Clause c1 = new Clause("~D(x,y)|~H(y)");
		Clause c2 = new Clause("D(John,x)|K(x)");
		Set<Clause> c = c1.unify(c2);
		for(Clause cl:c){
			System.out.println(cl);
		}
	}

}
