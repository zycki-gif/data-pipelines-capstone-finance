{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%md-sandbox\n\n<div style=\"text-align: center; line-height: 0; padding-top: 9px;\">\n  <img src=\"https://databricks.com/wp-content/uploads/2018/03/db-academy-rgb-1200px.png\" alt=\"Databricks Learning\" style=\"width: 1200px\">\n</div>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "4ff6ba2b-fd2b-4367-83e7-431877324e12", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "47c1796d-50c4-4379-aec2-d35d5e0032ca", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<img src=\"https://files.training.databricks.com/images/Apache-Spark-Logo_TM_200px.png\" style=\"float: left: margin: 20px\"/>\n\n# Bronze To Silver - Loan Requests\n\nThere are several minor issues in the Loan Requests bronze table. Let's fix them!\n\n## In this exercise you will:\n* Be introduced to a hashing function you can use to encrypt sensitive information\n* Practice joining DataFrames\n* Practice how to cast timestamps to timestamp type\n* Learn how to filter DataFrames based on lists of items\n* Practice how to express conditions with `when` and `otherwise`\n\n## Prerequisites\n* Web browser: **Chrome**\n* A cluster configured with **8 cores** and **DBR 7.0**", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "5798d198-1e4c-4d72-874f-6ea802989b6c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "def11924-e488-490a-9c36-a760e22de8bf", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<h2 style=\"color:red\">Instructor Note</h2>\n\n\n\nThings to talk about:\n- mention that we're working with silver tables\n- talk about the goal:\n  - what are we building right now: we're fixing loan requests table while also extending it\n  - why is it useful: generating flags will help later on in filtering and computing statistics\n- hashing concept if the audience is not familiar with it\n- .isin() method and using non-spark objects with spark\n- talk about when-otherwise function if the participants are not familiar with them\n- reiterate on the importance of hashing\n- emphasize the importance of using built-in functions\n- encourage the participants to build columnar expressions outside of the query and save them to variables\n- the importance of the flags", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "168828d8-93f2-4c73-9c0e-0d4de5e50fbc", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "f2728389-eaf1-43c0-95d7-ab0e5f193d0e", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Setup<br>\n\nFor each lesson to execute correctly, please make sure to run the **`Classroom-Setup`** cell at the start of each lesson (see the next cell) and the **`Classroom-Cleanup`** cell at the end of each lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "75114a92-c16d-415d-b5f2-fed0716ff0a7", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "96ec5e8a-273d-4294-9ee0-ac03f6ce7235", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/PCF-09S-ClassroomSetup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "13e72a33-86aa-4a6f-9e4f-58fb5c86c10c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "67675d64-07f0-4c07-8456-b85f461e9cca", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 5, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Steps to complete<br>\nImplement the **`challenge()`** function to achieve the following:\n- Read the _Loan Requests_ bronze table from **`loanRequestsPath`**.\n- Convert the **`request_time`** and **`valid_to`** fields to timestamp.\n- Take a look at the data. There is a **`password`** field containing sensitive information. \n  Let's get this encrypted before the data hits the gold layer. \n  Create a new column called **`password_hash`** and set the column values to the <a href='https://spark.apache.org/docs/latest/api/python/pyspark.sql.html#pyspark.sql.functions.md5' target='_blank'>MD5 hash</a> of passwords.\n  Once the hashing is done, drop the **`password`** column.\n- Since the loan request module has recently experienced a glitch, some request amounts are blank empty strings. \n  Replace **`request_amount`**  with nulls and add a new _True/False_ column called **`missing_amount`**. \n  Set the **`missing_amount`** value so that it indicates whether or not the amount was left blank.\n- There are a few blacklisted investors. They are stored in a native list (not a DataFrame) called **`banned_investor_list`**, which was preloaded for you. \n  Create a new boolean column called **`banned_investor`** and indicate whether the investor of the respective request is a listed in **`banned_investor_list`**.\n- Read the Investors silver table from the  **`investorsPath`** into a DataFrame.\n- Join the Loan Requests you worked on with the Investors using a left join.\n- Now create a new column called **`loan_alert`** in the joined DataFrame. Set its value to **`red`** if the **`credit_score`** is smaller than or equal to 1. Otherwise set the value to **`green`**.\n- Write the resulting DataFrame as a Delta table to **`targetDirectory`**.\n- Return the final DataFrame.\n\n<p> The resulting DataFrame's expected schema is:\n  \n|name|type|\n|---|---|\n|investor_id|LongType|\n|loan_id|LongType|\n|product_id|LongType|\n|request_amount|StringType|\n|request_length|LongType|\n|request_time|TimestampType|\n|valid_to|TimestampType|\n|password_hash|StringType|\n|missing_amount|BooleanType|\n|banned_investor|BooleanType|\n|loan_alert|StringType|\n  ", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "2ce09eac-b6c3-4309-b00e-b9d17a074ff9", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "dab64cba-9de0-43cd-b25d-8912f1509efe", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 6, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER\n\ndef solution(spark:org.apache.spark.sql.SparkSession, loanRequestsPath:String, investorsPath:String, targetDirectory:String) : DataFrame = {\n  import org.apache.spark.sql.functions._\n  import org.apache.spark.sql.types._\n\n  // Read the _Loan Requests_ bronze table from `loanRequestsPath`\n  // Convert the `request_time` and `valid_to` fields to timestamp.\n  val loanRequests = (spark.read.format(\"delta\").load(loanRequestsPath)\n                        .withColumn(\"request_time\", col(\"request_time\").cast(\"timestamp\"))\n                        .withColumn(\"valid_to\", col(\"valid_to\").cast(\"timestamp\"))\n                      )\n  \n  // Create a new column called `password_hash` and set the column values to MD5 hash. Once the hashing is done, drop the `password` column.\n  // Some request amounts are blank empty strings. Replace them with nulls and add a new True/False column called `missing_amount` \n  // Create a new boolean column called `banned_investor` and indicate whether the investor of the respective request is a blacklisted investor.\n  val res = (loanRequests\n           .withColumn(\"password_hash\", md5($\"password\")).drop($\"password\")\n           .withColumn(\"request_amount\", when($\"request_amount\" === \"\", null).otherwise($\"request_amount\"))\n           .withColumn(\"missing_amount\", $\"request_amount\".isNull)\n           .withColumn(\"banned_investor\", col(\"investor_id\").isin(banned_investor_list:_*))\n            )\n  \n  // Read the Investors silver table from the  `investorsPath` into a DataFrame.\n  val investors = spark.read.format(\"delta\").load(investorsPath).select(\"investor_id\",\"credit_score\")\n \n  // Join the Loan Requests you worked on with the Investors using a left join.\n  // Now create a new column called `loan_alert` in the joined DataFrame. \n  // Set its value to \"red\" if the `credit_score` is smaller than or equal to 1. Otherwise set the value to \"green\"\n  val result = (res\n          .join(investors, \"investor_id\")\n          .withColumn(\"loan_alert\", when(col(\"credit_score\") <= 1, \"red\").otherwise(\"green\"))\n          .drop(\"credit_score\"))\n  \n  // Write the resulting DataFrame as a Delta table to `targetDirectory` with overwrite mode and overwrite schema option\n  result.write.mode(\"overwrite\").format(\"delta\").option(\"overwriteSchema\", \"true\").save(targetDirectory) \n  \n  // Return the final DataFrame.\n  result\n}\n\nval solutionDF = solution(spark, loanRequestsPath, investorsPath, targetDirectory)\ndisplay(solutionDF)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "8c94012e-7a02-4633-aa9d-431de9301224", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "6a1df0c6-e9c6-4578-90f1-01a6c677121e", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 7, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n<h2><img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Reality Check</h2>\n\nRun the following cell to make sure you are on track:", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "d7c112d9-9f08-404a-81d7-eb90ce8d1ff4", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "96432006-0c23-4d43-ba49-b6e2fc795ee0", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 8, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER - Test your solution\nrealityCheck(solution, spark, loanRequestsPath, investorsPath, targetDirectory)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "a2d02ba4-7daf-47a2-89d0-215129e9f996", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e51587eb-85b9-4709-8e2c-c59c4dc9e001", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 9, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Cleanup<br>\n\nRun the **`Classroom-Cleanup`** cell below to remove any artifacts created by this lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "011d35e7-0573-4c83-b2eb-993afb15649e", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "7503e75c-5739-4430-be09-113f69c23d50", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 10, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/Classroom-Cleanup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "b83b7827-e094-4b93-b3f0-7796907de936", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d6d4b408-dfc2-41dd-a437-646156c84c94", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 11, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## <img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Next Steps\n\nStart the next challenge, [Loan Requests 2]($./PCF 10S - Loan Requests 2)\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "7b2b618f-5c9f-4625-ba12-6a40fb61631a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "ce918a5e-edc7-4095-b2d9-b5be65ec860e", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 12, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n&copy; 2020 Databricks, Inc. All rights reserved.<br/>\nApache, Apache Spark, Spark and the Spark logo are trademarks of the <a href=\"http://www.apache.org/\">Apache Software Foundation</a>.<br/>\n<br/>\n<a href=\"https://databricks.com/privacy-policy\">Privacy Policy</a> | <a href=\"https://databricks.com/terms-of-use\">Terms of Use</a> | <a href=\"http://help.databricks.com/\">Support</a>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "fd75795c-7aea-476c-bb62-35d3d21e9ead", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "cc0fdb5a-5e1c-4335-bb61-a210885dfeeb", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 13, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "c27b2b4d-a0d9-4312-815d-3d45db53965f", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF 09S - Loan Requests", "origId": 0, "version": "NotebookV1"}