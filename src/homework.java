import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class homework {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			// read from file
			String path = "input2.txt";
			File file = new File(path);
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(file));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			// first line, # of queries
			int nq = Integer.parseInt(br.readLine());
			// next nq lines, queries
			List<Clause> q = new ArrayList<Clause>();
			for (int i = 0; i < nq; i++) {
				String temp = br.readLine();
				if (temp.startsWith("~")) {
					temp = temp.substring(1);
				} else {
					temp = "~" + temp;
				}
				Clause c = new Clause(temp);
//				System.out.println(c.toString());
				q.add(c);
			}

			// # of sentences
			int ns = Integer.parseInt(br.readLine());
			// sentences in kb
			Set<Clause> sentences = new HashSet<Clause>();
			// String sentences[] = new String[ns];
			for (int i = 0; i < ns; i++) {
				// queries[i] = br.readLine();
				String temp = br.readLine();
				Clause c = new Clause(temp);
				sentences.add(c);
			}
			KnowledgeBase kb = new KnowledgeBase(sentences);
			Boolean[] b = new Boolean[q.size()];
			for (int i = 0; i < q.size(); i++) {
				KnowledgeBase kb1 = kb.clone();
				kb1.addClause(q.get(i));
//				System.out.print(kb1.toString());
				if (kb1.resolution()) {
					b[i] = true;
				} else
					b[i] = false;
			}
			writeTxt(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeTxt(Boolean[] b) {
		File writename = new File("output.txt");
		try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			for (Boolean f : b) {
//				System.out.println(f);
				out.write(f.toString().toUpperCase() + "\r\n");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
