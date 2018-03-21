import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Clause {
	private Set<Literal> literals;
	private Map<String, String> substitution;

	// private Boolean used;
	public Clause(Clause c) {
		this.literals = new HashSet<Literal>(c.literals);
		this.substitution = new HashMap<String, String>(c.substitution);
	}
	public Clause(String clause) {
		literals = new HashSet<Literal>();
		substitution = new HashMap<String, String>();
		// used = false;
		clause = clause.replace(" ", "");
		if (clause.contains("|")) {
			String[] temp = clause.split("\\|");
			for (String s : temp) {
				this.literals.add(new Literal(s));
			}
		} else {
			this.literals.add(new Literal(clause));
		}
	}
	public Clause(String clause, Map<String, String> sub) {
		literals = new HashSet<Literal>();
		substitution = new HashMap<String, String>(sub);
		// used = false;
		clause = clause.replace(" ", "");
		if (clause.contains("|")) {
			String[] temp = clause.split("\\|");
			for (String s : temp) {
				this.literals.add(new Literal(s));
			}
		} else {
			this.literals.add(new Literal(clause));
		}
	}
	public Clause(Set<Literal> l, Map<String, String> subs) {
		// used = false;
		this.literals = new HashSet<Literal>();
		for(Literal li : l){
			this.literals.add(new Literal(li));
		}
		
		this.substitution = new HashMap<String, String>(subs);
	}
	
	public Clause(Set<Literal> l) {
		// used = false;
		this.literals = new HashSet<Literal>();
		for(Literal li : l){
			this.literals.add(new Literal(li));
		}
		
		this.substitution = new HashMap<String, String>();
	}

	public Set<Literal> getLiterals() {
		return literals;
	}

	public Map<String, String> getSubs() {
		return this.substitution;
	}

	// public void setUsed(Boolean f) {
	// used = f;
	// }
	//
	// public Boolean getUsed() {
	// return used;
	// }

	public Boolean hasLiteral(Literal l) {
		for (Literal li : this.literals) {
			if (li.compare(l) <= 1) {
				return true;
			}
		}
		return false;
	}

	public boolean deleteLiteral(Literal l) {
		// l = null;
		return this.literals.remove(l);
	}

	public Clause generateNewClause(Clause c1, Clause c2, Map<String, String> subs1, Map<String, String> subs2) {
		Set<Literal> l = new HashSet<Literal>();
		Set<Map.Entry<String, String>> entries = subs2.entrySet();
		int num = 0;
		for (Map.Entry<String, String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (subs1.containsKey(key)) {
				if(!subs1.get(key).equals(value)){
					return null;
				}
//				for(Literal literal : c2.literals){
//					for(int i = 0; i< literal.getArguments().length; i++) {
//						if(literal.getArguments()[i].equals(key)){
//							literal.setArgument(i, key+num+"");
////							System.out.println(literal.toString()+"aaa");
//							subs1.put(key+num+"", value);
//							num++;
//						}else{subs1.put(key, value);}
//					}
//				}
			}else{
//				for(Literal literal : c2.literals){
//					for(int i = 0; i< literal.getArguments().length; i++) {
//						if(literal.getArguments()[i].equals(key)){
//							literal.setArgument(i, key+num+"");
////							System.out.println(literal.toString()+"aaa");
//							subs1.put(key+num+"", value);
//							num++;
//						}else{subs1.put(key, value);}
//					}
//				}
				subs1.put(key,value);
			}
		}
		for(Literal l1:c1.literals){
			for(int i = 0;i <l1.getArguments().length;i++){
				if(subs1.containsKey(l1.getArguments()[i])){
					l1.setArgument(i, subs1.get(l1.getArguments()[i]));
				}
			}
		}
		for(Literal l1:c2.literals){
			for(int i = 0;i <l1.getArguments().length;i++){
				if(subs1.containsKey(l1.getArguments()[i])){
//					String temp = subs1.get(l1.getArguments()[i]);
//					if(subs1.get(l1.getArguments()[i]).charAt(0)>=97){
//						subs1.remove(l1.getArguments()[i]);
//					}
					l1.setArgument(i, subs1.get(l1.getArguments()[i]));
					
				}
			}
		}
		
//		for(Literal l1 : c1.literals){
//			for(Literal l2 : c2.literals){
////				System.out.println(c1.toString()+"aaa");
//				//standard
//				for(int i = 0; i< l1.getArguments().length;i++){
//					for(int j = 0;j<l2.getArguments().length;j++){
//						if(l1.getArguments().equals(l2.getArguments())){
//							l2.setArgument(j, l2.getArguments()[i]+num+"");
//						}
//					}
//				}
//			}
//		}
		if(subs1.size()==0){
			subs1 = subs2;
		}
		l.addAll(c1.getLiterals());
		l.addAll(c2.getLiterals());
		return new Clause(l,subs1);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Literal l : this.literals) {
			s.append(l.toString(this.substitution)+"|");
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		Clause c = (Clause) arg0;
		// not equal if literal sizes are not same
		if (this.literals.size() != c.literals.size())
			return false;
		for (Literal l : this.literals) {
			if (c.literals.contains(l)) {

			} else
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		// TODO Auto-generated method stub
		// int hashCode =
		for (Literal l : this.literals) {
			hash = hash*31 + l.hashCode();
			for(int i = 0; i < l.getArguments().length; i++){
				if(this.substitution.containsKey(l.getArguments()[i])){
					hash=hash*31+(i+1)*this.substitution.get(l.getArguments()[i]).hashCode();
				}
			}
		}
		return hash;
	}

	public Set<Clause> unify(Clause c) {
		//standard
		for (Literal l1 : this.literals) {
			for (Literal l2 : c.getLiterals()) {
				for (int i = 0; i < l1.getArguments().length; i++) {
					for(int j=0;j<l2.getArguments().length;j++){
						if(l1.getArguments()[i].equals(l2.getArguments()[j])){
							String s1 = l1.getArguments()[i];
							String s2 = l2.getArguments()[j];
							char c1 = s1.charAt(0);
							char c2 = s2.charAt(0);
							if(c2 >= 97 && c2 <= 122&&c1 >= 97 && c1 <= 122){
								int num = 0;
								String s;
								if(s2.length()>1){
									s = s2.substring(1);
									num = Integer.parseInt(s)+1;
									s=s2.substring(0,1)+num;
								}else{
									s=s2+"1";
								}
								if(c.substitution.containsKey(s2)){
									c.substitution.put(s, c.substitution.get(s2));
									c.substitution.remove(s2);
								}
								l2.setArgument(j, s);
							}
						}
					}
				}
			}
		}
		Set<Clause> resolvents = new HashSet<Clause>();
		Map<String, String> subs1 = new HashMap<String, String>(this.substitution);
		Map<String, String> subs2 = new HashMap<String, String>(c.substitution);
		Boolean flag = false;
		for (Literal l1 : this.literals) {
			// if(l1==null) continue;
			for (Literal l2 : c.getLiterals()) {
				
				if (l1.compare(l2) == 0) {
					String[] args1 = l2.getArguments();
					String[] arguments = l1.getArguments();
					for (int i = 0; i < arguments.length; i++) {
						char c1 = arguments[i].charAt(0);
						char c2 = args1[i].charAt(0);
						if (c1 >= 65 && c1 <= 90 && c2 >= 97 && c2 <= 122) { // l1 constant->l2 argument
							if (subs2.containsKey(args1[i])) {
//								System.out.println(subs2.get(args1[i]));
								if (subs2.get(args1[i]).equals(arguments[i])) {
									
								}else flag = true;
							}else{
								subs2.put(args1[i], arguments[i]);
							}
							// l.setArgument(i, arguments[i]);
						} else if (c1 >= 65 && c1 <= 90 && c2 >= 65 && c2 <= 90) {
							// different constant, cannot unify
							if(args1[i].equals(arguments[i])){}
							else flag = true;
							
						} else if (c2 >= 65 && c2 <= 90 && c1 >= 97 && c1 <= 122) { // l1 argument<-l2 constant
							if (subs1.containsKey(arguments[i])) {
								if (subs1.get(arguments[i]).equals(args1[i])) {
									
								}else flag = true;
							}else{
								subs1.put(arguments[i], args1[i]);
							}
							// this.setArgument(i, args1[i]);
						} else if (c2 >= 97 && c2 <= 122 && c1 >= 97 && c1 <= 122) { //2 args
//							flag=true;
							if (subs1.containsKey(arguments[i])) {
								if(subs2.containsKey(args1[i])){
									if(subs1.get(arguments[i]).equals(subs2.get(args1[i]))){
										
									}else flag = true;
								}else {
									subs2.put(args1[i], subs1.get(arguments[i]));
								}
							}else{
								if(subs2.containsKey(args1[i])){
									subs1.put(arguments[i], subs2.get(args1[i]));
								}else{
									flag=true;
//									subs2.put(args1[i], arguments[i]);
								}
							}
						}
					}
					if(flag){ //cannot unify
//						subs1 = new HashMap<String, String>(this.substitution);
//						subs2 = new HashMap<String, String>(c.substitution);
					}else{ 
//						System.out.println(subs2.toString());
						Clause a = new Clause(this.getLiterals());
						Clause b = new Clause(c.getLiterals());
//						System.out.println("aaa:"+this.getLiterals().size());
						a.deleteLiteral(l1);
						b.deleteLiteral(l2);
//						System.out.println(subs2.toString());
//						System.out.println("aaa:"+a.toString()+" + "+b.toString());
						Clause cl = generateNewClause(a, b, subs1, subs2);
						if(cl==null){
							return resolvents;
						}
						System.out.println(this.toString()+" + "+c.toString()+" => "+cl.toString());
						
						if (cl.getLiterals().size() == 0) {
							return null;
						}
						resolvents.add(cl);
					}

					subs1 = new HashMap<String, String>(this.substitution);
					subs2 = new HashMap<String, String>(c.substitution);
				}
			}
		}
		return resolvents;
	}
}
