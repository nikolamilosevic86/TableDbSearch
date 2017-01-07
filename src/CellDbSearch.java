import java.util.Arrays;


public class CellDbSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.out.println("Hello Cell search");
		
		String usage = "java LuceneTableSearch.jar" 
				+ " [-index INDEX_PATH] [-update]\n\n"
				+ "This indexes the documents in DOCS_PATH, creating a Lucene index"
				+ "in INDEX_PATH that can be searched with SearchFiles";
		String indexPath = "index";
		String docsPath = null;
		boolean create = true;
		if (!Arrays.asList(args).contains("-s")) {
			for (int i = 0; i < args.length; i++) {
				if ("-index".equals(args[i])) {
					indexPath = args[i + 1];
					i++;
				} else if ("-update".equals(args[i])) {
					create = false;
				}
			}

			CellIndexer.performIndexing( indexPath, create);
		} else {
			String index = "index";
			String field = "contents";
			String queries = null;
			int repeat = 0;
			boolean raw = false;
			String queryString = null;
			int hitsPerPage = 10;

			for (int i = 0; i < args.length; i++) {
				if ("-index".equals(args[i])) {
					index = args[i + 1];
					i++;
				} else if ("-field".equals(args[i])) {
					field = args[i + 1];
					i++;
				} else if ("-queries".equals(args[i])) {
					queries = args[i + 1];
					i++;
				} else if ("-query".equals(args[i])) {
					queryString = args[i + 1];
					i++;
				} else if ("-repeat".equals(args[i])) {
					repeat = Integer.parseInt(args[i + 1]);
					i++;
				} else if ("-raw".equals(args[i])) {
					raw = true;
				} else if ("-paging".equals(args[i])) {
					hitsPerPage = Integer.parseInt(args[i + 1]);
					if (hitsPerPage <= 0) {
						System.err
								.println("There must be at least 1 hit per page.");
						System.exit(1);
					}
					i++;
				}
			}
			try{
			CellSearcher.PerformSearch(queries, index, queryString, repeat,
					hitsPerPage, raw, field);
			}catch(Exception ex){
				ex.printStackTrace();
			}

		}

	}

}
