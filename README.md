# TableDbSearch
Search for Tables in PMC. This project is dependent on database that is produced by [TableAnnotator](https://github.com/nikolamilosevic86/TableAnnotator) project. It provides functionality for indexing and searching both tables as a whole and individual cells. 


## Running instructions
###Indexing
You need to set up database connection information in settings.cfg file. Then for indexing tables run:
```java -cp TableDbSearch.jar TableDbSearchMain -index indexPath```
indexPath - path where index file is saved

For indexing cells run:
```java -cp TableDbSearch.jar CellDbSearch -index indexPath```


###Searching
Once database is indexed, tables can be searched in the following manner for tables containing "baseline characteristics"
```java -cp TableDbSearch.jar TableDbsearchMain -s -query "baseline characteristic"```

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
   table_caption: TableÂ 1. Baseline characteristics and antibody response
6. 3277821
	Table:TableÂ 1
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

Cells can be search with the following command:
```java -cp TableDbSearch.jar CellDbSearch -s -query "Age"```
or with query generation:

```java -cp TableDbSearch.jar CellQuestionAnswering```

Output will look like this: 
```
Searching for: pmc_id:age pm_id:age table_order:age table_caption:age table_footer:age article_title:age spec_pragmatic:age cell_header:age cell_stub:age cell_superRow:age cell_data:age
3420 total matching documents
1. PMC:2686318
	Table:Table 1
   table_caption: Baseline demographics and patient history of SERF study participants
   cell_data: ?Age < 65
   cell_header: Baseline demographics/history ; Risk factor ;  ; 
   cell_stub: ?Age < 65
   cell_superRow:  Age
2. PMC:2686318
	Table:Table 3
   table_caption: Univariate and multivariate predictors of death in the SERF study
   cell_data: ?80 ? age vs. age < 65
   cell_header: Risk factor ;  ; 
   cell_stub: ?80 ? age vs. age < 65
   cell_superRow:  Age
3. PMC:3326700
	Table:Table 1
   table_caption: General demographic and clinics characteristics
   cell_data: Age
   cell_header: Variables ; 
   cell_stub: Age
   cell_superRow: 
4. PMC:3405223
	Table:Table 1
   table_caption: Patient baseline characteristics
   cell_data: ?Median age (range), years
   cell_header: Characteristic, n (% unless otherwise stated) ; 
   cell_stub: ?Median age (range), years
   cell_superRow:  Age
5. PMC:3164103
	Table:Table 5
   table_caption: Sociodemographic and clinical characteristics associated with control of treated hypertension, dyslipidaemia, type 2 diabetes, and obesity
   cell_data: Age
   cell_header: Control of hypertension (BP <140/90 mmHg)a odds ratio (95% CI) ; 
   cell_stub: Age
   cell_superRow: 
6. PMC:2693527
	Table:Table 1
   table_caption: Patient characteristics at time of accrual.
   cell_data: Age
   cell_header: Parameter ; 
   cell_stub: Age
   cell_superRow: 
7. PMC:1570351
	Table:Table 1
   table_caption: Clinical characteristics, pattern of care and outcome of HIV-seronegative patients (n = 81). MMC = mitomycin-C, RT = radiotherapy, OS = overall survival, CSS = cancer-specific survival, m = months.
   cell_data: age
   cell_header: Variable ; 
   cell_stub: age
   cell_superRow: 
8. PMC:3388560
	Table:Table 1
   table_caption: ?Patient characteristics
   cell_data: Age
   cell_header: Variable ; 
   cell_stub: Age
   cell_superRow: 
9. PMC:2853514
	Table:Table 5
   table_caption: Patient satisfaction 6-months after surgery correlated with selected preoperative variables and other measures of surgical outcome at 6 months.
   cell_data: Age
   cell_header:  ; 
   cell_stub: Age
   cell_superRow: 
10. PMC:3103451
	Table:Table 3
   table_caption: Factors influencing treatment response
   cell_data: Age
   cell_header: Factor ; 
   cell_stub: Age
   cell_superRow: 
Press (n)ext page, (q)uit or enter number to jump to a page.
```

