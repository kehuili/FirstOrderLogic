import java.util.HashSet;
import java.util.Set;


public class KnowledgeBase {
	public Set<Clause> clauses;

	public KnowledgeBase(Set<Clause> c) {
		clauses = new HashSet<Clause>(c);
	}

	public KnowledgeBase clone() {
		Set<Clause> l = new HashSet<Clause>(this.clauses);
		return new KnowledgeBase(l);
	}

	public void addClause(Clause c) {
		clauses.add(c);
	}

	public void deleteClause(int i) {
		clauses.remove(i);
		
	}
	public String toString(){
		String s = "";
		for(Clause c : clauses) {
			s+=c.toString()+"\n";
		}
		return s;
	}
	public Boolean resolution() {
		Set<Clause> newClause = new HashSet<Clause>();
		while (true) {
//			System.out.println();
			Clause[] cls = new Clause[this.clauses.size()];
			int ii = 0;
			for(Clause c:this.clauses){
				cls[ii] = c;
				ii++;
			}
			
			for (int i = 0; i < cls.length; i++) {
				for (int j = i + 1; j < cls.length; j++) {
					// if(!clauses.get(i).getUsed()) {
					Set<Clause> temp = cls[i].unify(cls[j]);
					if(temp==null) return true;
					if (temp.size() == 0) {
						
					}else{
						newClause.addAll(temp);
					}
					// }
				}
			}
			Boolean flag = false;
			for(Clause c : newClause){
				if(this.clauses.contains(c)){
					
				}else {
					flag = true;
					break;
				}
//				for(Clause c1 : this.clauses) {
//					if(c.equals(c1)){
//						
//					}else {
//						flag = true;
//						break;
//					}
//				}
				if(flag) break;
			}
			if(!flag) return false;
			this.clauses.addAll(newClause);
//			for(Clause c : newClause) {
//				this.addClause(c);
//			}
		}
	}
}
