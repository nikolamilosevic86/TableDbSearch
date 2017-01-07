# TableDbSearch
Search for Tables in PMC

## Running instructions
###Indexing
You need to set up database connection information in settings.cfg file. Then run:
```java -jar TableDbSearch.java -index indexPath```
indexPath - path where index file is saved
###Searching
Once database is indexed, tables can be searched in the following manner for tables containing "baseline characteristics"
```java -jar TableDbSearch.java -s -query "baseline characteristic"```

Output will look like:
```
Searching for: (pmc_id:baseline pmc_id:characteristics) (pm_id:baseline pm_id:characteristics) (table_order:baseline table_order:characteristics) (table_caption:baseline table_caption:characteristics) (table_footer:baseline table_footer:characteristics) (table_footer:baseline table_footer:characteristics) (article_title:baseline article_title:characteristics) (spec_pragmatic:baseline spec_pragmatic:characteristics) (table_header:baseline table_header:characteristics) (table_stub:baseline table_stub:characteristics) (table_superRow:baseline table_superRow:characteristics) (table_data:baseline table_data:characteristics)
Time: 41ms
4408 total matching documents
1. 3393621
	Table:Table 1
   table_caption: Patient characteristics
2. 2361244
	Table:Table 2
   table_caption: Prognostic factors for overall survival, event-free survival and disease relapse (distant or local): univariate analysis
3. 1894974
	Table:Table 1
   table_caption: 
4. 3195264
	Table:Table 1
   table_caption: Demographic and baseline characteristics, indications for multidetector computer tomography (MDCT) and patient condition. BMI Body mass index
5. 3551874
	Table:Table without label
   table_caption: Table 1. Baseline characteristics and antibody response
6. 3277821
	Table:Table 1
   table_caption: Baseline demographics and clinical characteristics of study patients
7. 3162912
	Table:Table 4
   table_caption: Univariate analysis of time to progression (TTP) and overall survival (OS) using the Cox proportional hazard model
8. 526774
	Table:Table 1
   table_caption: Baseline characteristics for each group given at the individual and cluster levels.
9. 2699710
	Table:Table 1
   table_caption: Baseline demographics, disease characteristics, and changes after the 8-week treatment
10. 2927614
	Table:Table 2
   table_caption: Baseline characteristics of the cohort
Press (n)ext page, (q)uit or enter number to jump to a page.
```

First number is order of the result, the second is PMC id. 

