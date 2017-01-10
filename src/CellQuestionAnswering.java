import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.InputStreamReader;



public class CellQuestionAnswering {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String indexPath = "index";
		String docsPath = null;
		String index = "index";
		String field = "contents";
		String queries = null;
		int repeat = 0;
		boolean raw = false;
		String queryString = null;
		int hitsPerPage = 10;
		try{
		String topic = "";
		String information = "";
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is the topic of the search (pmcid,article title, table caption):");
		topic = in.readLine();
		System.out.println("What is the information you are looking (table caption, stub, header, super-row or data):");
		information = in.readLine();
		queryString = "(pmc_id:"+"\""+topic+"\" OR "+"article_title:"+"\""+topic+"\" OR "+"table_caption:"+"\""+topic+"\") AND "+
				"(cell_header:"+"\""+information+"\" OR "+"cell_stub:"+"\""+information+"\" OR "+"cell_superRow:"+"\""+information+"\" OR "
				+"cell_data:"+"\""+information+"\") ";
		

		CellSearcher.PerformSearch(queries, index, queryString, repeat,
				hitsPerPage, raw, field);
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

}
