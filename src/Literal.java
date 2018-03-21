import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Literal {
	private String predicate;
	private String[] arguments;
	private Map<String, String> subtitution;
	
	private Boolean negate = false;
	private String literalString;

	public Literal(Literal l) {
		this.predicate = new String(l.predicate);
		this.arguments = new String[l.arguments.length];
		this.arguments = l.arguments.clone();
		this.subtitution = new HashMap<String, String>(l.subtitution);
		this.negate = l.negate;
		this.literalString = new String(l.literalString);
	}
	public Literal(String literal) {
		this.subtitution = new HashMap<String, String>();
		this.literalString = literal;
		if (literal.charAt(0) == '~') {
			negate = true;
			literal = literal.substring(1);
		}
		String[] temp = literal.split("\\" + "(");
		predicate = temp[0];

		temp[1] = temp[1].substring(0, temp[1].length() - 1);
		arguments = temp[1].split(",");
	}
	public String toString(Map<String, String> sub) {
		String s = "(";
		for(int i = 0; i< this.arguments.length; i++) {
			String arg;
			if(sub.containsKey(this.arguments[i])){
				arg = this.arguments[i]+"/"+sub.get(this.arguments[i]);
			}else arg = this.arguments[i];
			if(i!=this.arguments.length-1){
				s=s+arg+",";
			}else{
				s=s+arg+")";
			}
		}
		String s1 = "~";
		if(!negate) s1 =""; 
		return s1 + this.predicate+s;
	}
	public String toString() {
		String s = "(";
		for(int i = 0; i< this.arguments.length; i++) {
			String arg = this.arguments[i];
			if(i!=this.arguments.length-1){
				s=s+arg+",";
			}else{
				s=s+arg+")";
			}
		}
		String s1 = "~";
		if(!negate) s1 =""; 
		return s1 + this.predicate+s;
	}
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		Literal l = (Literal)arg0;
		if(this.literalString.equals(l.literalString)) return true;
//		return false;
		//different format of variables
		if(this.predicate.equals(l.predicate)&&this.negate==l.negate){
			for(int i = 0; i < this.arguments.length; i++) {
				char c1 = this.arguments[i].charAt(0);
				char c2 = l.arguments[i].charAt(0);
				if(c1>=97&&c1<=122&&c2>=97&&c2<=122){ //c1 var, c2 var
					
				}else if(c1>=97&&c1<=122&&c2>=97&&c2<=122){ //c1 constant, c2 cons
					if(this.arguments[i].equals(l.arguments[i])){
						
					}else return false;
				}
				
			}
		}else return false;
		return true;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int hash = this.predicate.hashCode()+this.negate.hashCode()*31;
		for(String s : this.arguments){
			if(s.charAt(0)<=90){ //constant
				hash=hash*31+s.hashCode();
			}
		}
		return hash;
	}
	public String getPredicate() {
		return predicate;
	}

	public String[] getArguments() {
		return arguments;
	}

	public Boolean getNegate() {
		return negate;
	}

	public int compare(Literal l) {
		if (this.predicate.equals(l.getPredicate())) {
			if (this.negate == l.getNegate()) {
				return 1;
			} else
				return 0;
		}
		return 2;
	}

	public void setArgument(int i, String arg) {
		arguments[i] = arg;
	}

	public Map<String, 	Map<String, String>> unify(Literal l) {
		Map<String, String> subs = new HashMap<String, String>();
		Map<String, String> subs1 = new HashMap<String, String>();
		if (this.compare(l) <= 1) {
			String[] args1 = l.getArguments();
			for (int i = 0; i < this.arguments.length; i++) {
				if (arguments[i].equals(args1[i])) {
					// no need to unify
				} else {
					char l1 = arguments[i].charAt(0);
					char l2 = args1[i].charAt(0);
					if (l1 >= 65 && l1 <= 90 && l2 >= 97 && l2 <= 122) { // constant->argument
						// l.setArgument(i, arguments[i]);
					} else if (l1 >= 65 && l1 <= 90 && l2 >= 65 && l2 <= 90) {
						// different constant, cannot unify
						return null;
					} else if (l2 >= 65 && l2 <= 90 && l1 >= 97 && l1 <= 122) { // argument<-constant
						// this.setArgument(i, args1[i]);
					} else if (l2 >= 97 && l2 <= 122 && l1 >= 97 && l1 <= 122) { // no constant,no need to unify

					}
				}
			}

			// unification
			for (int i = 0; i < this.arguments.length; i++) {
				char l1 = arguments[i].charAt(0);
				char l2 = args1[i].charAt(0);
				if (l1 >= 65 && l1 <= 90 && l2 >= 97 && l2 <= 122) { // constant->argument
//					l.setArgument(i, arguments[i]);
					subs1.put(args1[i], arguments[i]);
				} else if (l2 >= 65 && l2 <= 90 && l1 >= 97 && l1 <= 122) { // l1 argument<-constant 
//					this.setArgument(i, args1[i]);
					subs.put(arguments[i], args1[i]);
				}
			}
			Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
			map.put("literal1", subs);
			map.put("literal2", subs1);
			return map;
		} else return null;
	}
}
