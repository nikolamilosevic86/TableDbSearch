import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class CellIndexer {
	public static Connection conn = null;

	public static void performIndexing( String indexPath,
			boolean create) {
		
		Date start = new Date();
		try {
			System.out.println("Started indexing...");

			Directory dir = FSDirectory.open(new File(indexPath));
			// :Post-Release-Update-Version.LUCENE_XY:
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47,
					analyzer);

			if (create) {
				// Create a new index in the directory, removing any
				// previously indexed documents:
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}

			// Optional: for better indexing performance, if you
			// are indexing many documents, increase the RAM
			// buffer. But if you do this, increase the max heap
			// size to the JVM (eg add -Xmx512m or -Xmx1g):
			//
			// iwc.setRAMBufferSizeMB(256.0);

			IndexWriter writer = new IndexWriter(dir, iwc);
			CellIndexer.indexDocs(writer);

			// NOTE: if you want to maximize search performance,
			// you can optionally call forceMerge here. This can be
			// a terribly costly operation, so generally it's only
			// worth it when your index is relatively static (ie
			// you're done adding documents to it):
			//
			// writer.forceMerge(1);

			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime()
					+ " total milliseconds");

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
		}
	}

	/**
	 * Indexes the given file using the given writer, or if a directory is
	 * given, recurses over files and directories found under the given
	 * directory.
	 * 
	 * NOTE: This method indexes one document per input file. This is slow. For
	 * good throughput, put multiple documents into your input file(s). An
	 * example of this is in the benchmark module, which can create "line doc"
	 * files, one document per line, using the <a href=
	 * "../../../../../contrib-benchmark/org/apache/lucene/benchmark/byTask/tasks/WriteLineDocTask.html"
	 * >WriteLineDocTask</a>.
	 * 
	 * @param writer
	 *            Writer to the index where the given file/dir info will be
	 *            stored
	 * @param file
	 *            The file to index, or the directory to recurse into to find
	 *            files to index
	 * @throws IOException
	 *             If there is a low-level I/O error
	 */
	static void indexDocs(IndexWriter writer) throws IOException {
				
				try {
					 BufferedReader br = new BufferedReader(new FileReader("settings.cfg"));
				        // StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					String host="";
					String database_name= "";
					String database_username = "";
					String database_password = "";
					String database_port = "";
					LinkedList<TableDataDoc> DocumentList = new LinkedList<TableDataDoc>();
					while (line != null && line != "") {
						KeyValue kv = new KeyValue();
						String[] parts = line.split(";");
						kv.key = parts[0];
						kv.value = parts[1];
						if (kv.key.equals("database_host")) {
							host = kv.value;
						}
						if (kv.key.equals("database_name")) {
							database_name = kv.value;
						}
						if (kv.key.equals("database_username")) {
							database_username = kv.value;
						}
						if (kv.key.equals("database_password")) {
							database_password = kv.value;
						}
						if (kv.key.equals("database_port")) {
							database_port = kv.value;
						}
						line = br.readLine();
					}
				 	
					database_password = database_password.replace("\"", "");
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					String connectionUrl = "jdbc:mysql://"+host+":"+database_port+"/"+database_name;
					String connectionUser = database_username;
					String connectionPassword = database_password;
					conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);	
					
					Statement stmt = conn.createStatement();
			  		String selectTables = "SELECT idTable,idArticle,PMCID,PMID,pissn,eissn,Title,Abstract,JournalName,Source,SpecID,TableOrder,TableCaption,TableFooter,StructureType,PragmaticType,SpecPragmatic FROM table_db.arttable inner join article on article.idArticle=arttable.Article_idArticle where PMCID=3082157;";
			  		// execute insert SQL stetement
			  		ResultSet rs = stmt.executeQuery(selectTables);
	                while(rs.next())
	                {
	                	TableDataDoc newDoc = new TableDataDoc();
	                	newDoc.TableID = rs.getString(1);
	                	newDoc.ArticleID = rs.getString(2);
	                	newDoc.PMCID = rs.getString("PMCID");
	                	System.out.println("PMCID:"+newDoc.PMCID);
	                	newDoc.PMID = rs.getString(4);
	                	newDoc.pissn = rs.getString(5);
	                	newDoc.eissn = rs.getString(6);
	                	newDoc.ArticleTitle = rs.getString(7);
	                	// index 8 is Abstract. We don't need it now.
	                	newDoc.JournalName = rs.getString(9);
	                	newDoc.Source = rs.getString(10);
	                	newDoc.SpecID = rs.getString(11);
	                	newDoc.TableOrder = rs.getString(12);
	                	newDoc.TableCaption = rs.getString(13);
	                	newDoc.TableFooter = rs.getString(14);
	                	newDoc.TableStructureType = rs.getString(15);
	                	newDoc.PragmaticType = rs.getString(16);
	                	newDoc.SpecPragmaticType = rs.getString(17);
	                	Statement stmt2 = conn.createStatement();
	                	String selectCells = "SELECT * FROM table_db.cell where Table_idTable="+newDoc.TableID;
				  		// execute insert SQL stetement
				  		ResultSet rs2 = stmt2.executeQuery(selectCells);
				  		while(rs2.next())
		                {
				  			CellData cd = new CellData();
				  			cd.UniqueCellID = rs2.getString(1);
				  			cd.CellID = rs2.getString(2);
				  			cd.CellType = rs2.getString(3);
				  			cd.idTable = rs2.getString(4);
				  			cd.RowN = rs2.getString(5);
				  			cd.ColumnN = rs2.getString(6);
				  			cd.HeaderRef = rs2.getString(7);
				  			cd.StubRef = rs2.getString(8);
				  			cd.SuperRowRef = rs2.getString(9);
				  			cd.Content = rs2.getString(10);
				  			cd.WholeHeader = rs2.getString(11);
				  			cd.WholeStub = rs2.getString(12);
				  			cd.WholeSuperrow = rs2.getString(13);
				  			
				  			Statement stmt3 = conn.createStatement();
		                	String selectFunction = "SELECT * FROM table_db.cellroles where Cell_idCell="+cd.UniqueCellID;
					  		// execute insert SQL stetement
					  		ResultSet rs3 = stmt3.executeQuery(selectFunction);
					  		while(rs3.next())
					  		{
					  			int function = rs3.getInt(2);
					  			if(function==1){
					  				cd.Functions.add("Header");
					  			}
					  			if(function==2){
					  				cd.Functions.add("Stub");
					  			}
					  			if(function==3){
					  				cd.Functions.add("Data");
					  			}
					  			if(function==4){
					  				cd.Functions.add("Super-row");
					  			}
					  		}
					  		newDoc.cells.add(cd);
				  			
		                }
						for(int i=0;i<newDoc.cells.size();i++)
						{
							Document doc = new Document();
							CellData cell = newDoc.cells.get(i);
							//Add document meta data
							Field doc_id = new StringField("doc_id", newDoc.ArticleID+cell.idTable+cell.CellID,
									Field.Store.YES);
							doc.add(doc_id);
							TextField pmc_id = new TextField("pmc_id", newDoc.PMCID,
									Field.Store.YES);
							doc.add(pmc_id);
							TextField pm_id = new TextField("pm_id", newDoc.PMID,
									Field.Store.YES);
							doc.add(pm_id);
							TextField table_order = new TextField("table_order", newDoc.TableOrder,
									Field.Store.YES);
							doc.add(table_order);
							TextField table_caption = new TextField("table_caption", newDoc.TableCaption,
									Field.Store.YES);
							table_caption.setBoost(3.0f);
							doc.add(table_caption);
							TextField table_footer = new TextField("table_footer", newDoc.TableFooter,
									Field.Store.YES);
							table_footer.setBoost(3.0f);
							doc.add(table_footer);
							TextField article_title = new TextField("article_title", newDoc.ArticleTitle,
									Field.Store.YES);
							article_title.setBoost(3.0f);
							doc.add(article_title);
							TextField spec_pragmatic = new TextField("spec_pragmatic", newDoc.SpecPragmaticType,
									Field.Store.YES);
							spec_pragmatic.setBoost(3.0f);
							doc.add(spec_pragmatic);
							
							
							//Add cell data
							if(cell.WholeHeader==null){
								cell.WholeHeader = "";
							}
							TextField table_header = new TextField("cell_header", cell.WholeHeader,
									Field.Store.YES);	
							table_header.setBoost(5.0f);
							doc.add(table_header);
							if(cell.WholeStub==null){
								cell.WholeStub = "";
							}
							TextField table_stub = new TextField("cell_stub", cell.WholeStub,
									Field.Store.YES);	
							table_stub.setBoost(5.0f);
							doc.add(table_stub);
							if(cell.WholeSuperrow==null){
								cell.WholeSuperrow = "";
							}
							TextField table_superRow = new TextField("cell_superRow", cell.WholeSuperrow,
									Field.Store.YES);	
							table_superRow.setBoost(5.0f);
							doc.add(table_superRow);
							TextField table_data = new TextField("cell_data", cell.Content,
									Field.Store.YES);	
							table_data.setBoost(0.1f);
							doc.add(table_data);
							
							TextField table_location = new TextField("cell_location", cell.CellID,
									Field.Store.YES);	
							table_location.setBoost(1.0f);
							doc.add(table_location);
							
							if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
								// New index, so we just add the document (no old
								// document can be there):
								System.out.println("adding " + newDoc.PMCID +" "+newDoc.TableOrder+" "+cell.CellID+" " +cell.Content+" "+cell.WholeStub);
								writer.addDocument(doc);
							} else {
								// Existing index (an old copy of this document may have
								// been indexed) so
								// we use updateDocument instead to replace the old one
								// matching the exact
								// path, if present:
								System.out.println("updating " +newDoc.PMCID);
								writer.updateDocument(new Term("doc_id", newDoc.ArticleID+newDoc.cells.get(i).idTable+newDoc.cells.get(i).CellID),
										doc);
							}
							
						}
						
						
						
						
	                }

				}catch(Exception e){
					e.printStackTrace();
				}
				 
				finally {
					// fis.close();
				}
			
		
	}

}
