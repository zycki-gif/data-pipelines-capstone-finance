{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%md-sandbox\n\n<div style=\"text-align: center; line-height: 0; padding-top: 9px;\">\n  <img src=\"https://databricks.com/wp-content/uploads/2018/03/db-academy-rgb-1200px.png\" alt=\"Databricks Learning\" style=\"width: 1200px\">\n</div>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "e0395420-53e0-4c1b-8377-edaf697e768f", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "385bbb8e-9c70-4c23-8b69-dfc5a3570f79", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<img src=\"https://files.training.databricks.com/images/Apache-Spark-Logo_TM_200px.png\" style=\"float: left: margin: 20px\"/>\n\nWe are going to update our investors' credit scores by using the latest macroeconomic data received from the central bank.\n\n# Bronze To Silver - Investors\n\n## In this exercise you will:\n* Find out how to read from the bronze layer \n* Learn to create and rename columns\n* Practice using the `Row` object to compute with values on the Driver\n* Practice using `when` and `otherwise` to express conditions with Spark\n* Combine data available in a DataFrame and on the Driver with `lit`\n* Get familiar with writing out to Delta format\n\n## Prerequisites\n* Web browser: **Chrome**\n* A cluster configured with **8 cores** and **DBR 7.0**", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "648b96b0-ac0b-467a-a7af-cc9a42bed0c2", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "c3ea7b92-467d-47ef-82ea-64a0ffef4b86", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<h2 style=\"color:red\">Instructor Note</h2>\n\n\n\nThings to talk about:\n- silver level tables in detail, including:\n  - their purpose:\n    - clean, filter, deduplicate, fix bronze tables to create reliable base tables\n    - introduce newly engineered features, potentially using aggregated results as well\n    - provide all the required transformations to generate gold layer tables\n  - what they are and what they aren't:\n    - transformed tables to use as a basis of final tables\n    - not bronze tables, every problem is fixed, every new feature is created\n    - not gold tables, no zoomed in views and super-aggregated 10 row result tables\n  - optionally questions if an operation is silver level or not:\n    - stock list exported from the trading system\n    - top 3 most popularly traded stock type\n    - trader list with normalized names and added regional monetical statistics\n- talk about the goal:\n  - what are we building right now: We now use the 3rd party data to update our investor's credit score using a computed [macroeconomic metric](https://en.wikipedia.org/wiki/Economic_indicator).\n  - why is it useful: we'll always have up-to-date information about our investors\n- describe how can we use a single extracted value from a table (Row + lit)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "dc70f36a-dc65-40f2-b668-4c050a555cfc", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "9d8e90e3-2692-4deb-9ed0-e9c17766d416", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Setup<br>\n\nFor each lesson to execute correctly, please make sure to run the **`Classroom-Setup`** cell at the start of each lesson (see the next cell) and the **`Classroom-Cleanup`** cell at the end of each lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "22853205-6975-4e1a-b156-9337e28f203c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "b4c0e29e-afa1-427c-aba6-f62fd6936539", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/PCF-07S-ClassroomSetup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "e720d87f-bf02-4f11-a375-70a996a903ba", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "acac1c20-c0ba-4e7f-b3f2-2ce3a8dcec0f", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 5, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Steps to complete<br>\nImplement the **`challenge()`** function to achieve the following:\n- Read the macroeconomic data from **`macroDataPath`** into a DataFrame. \n- The source contains all historical macroeconomic data. However, we only need the row with the latest date.\n- Collect the row with the latest date to the driver and assign it to a variable.\n- Compute the metric using the elements of this row. Here is the formula for the metric:\n  * The formula will multiply several columns and divide them by the country's GDP.\n  * metric = (fedfunds X dexuseu X unrate X dgs10 X a191rl1q225sbea X indpro X bamlh0a0hym2) / \"gdp\"\n- Now we are ready to bring in the Investors. Read the **`investorsPath`** into a DataFrame.\n- We only need the  **`investor_id`**, **`name`**, **`education`**, **`credit_score`**, **`subscription_id`**, **`risk_score`** and **`fav_stocks`** fields from Investors.\n- Update the **`credit_score`** column by adding the metric you computed. E.g., if an investor's credit score is 3 and the metric is 0.2, the new credit score will be 3.2.\n- There is a condition for updated credit scores: they must be between 0 and 10. Make sure that they don't exceed 10 or go below 0. In case it's above 10, set the value to 10. In case it's below 0, set the value to 0.\n- Write the result to **`targetDirectory`** as a Delta table, using the overwrite mode and overwrite schema method.\n- Return the resulting DataFrame.\n\n<p> **`macroDataPath`**, **`investorsPath`** and **`targetDirectory`** are already defined in your environment.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "3ca0f80d-64d3-420c-9a94-3316d818ca3b", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "f86cd832-587d-460f-8b9e-f268579049ce", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 6, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER\n\ndef solution(spark:org.apache.spark.sql.SparkSession, macroDataPath:String, investorsPath:String, targetDirectory:String) : DataFrame = {\n  import org.apache.spark.sql.functions._\n  \n  // Read the macroeconomic data from `macroDataPath`.\n  val macro_2 = (spark.read.format(\"delta\").load(macroDataPath))\n  \n  // Collect the row with the latest date and assign it to a variable.\n  val maxDate = macro_2.select(max(col(\"date\"))).collect()\n  val macroRow = (macro_2\n               .filter(col(\"date\") === maxDate(0)(0))\n                   .select(\"fedfunds\", \"dexuseu\", \"unrate\", \"dgs10\", \"a191rl1q225sbea\", \"indpro\", \"bamlh0a0hym2\", \"gdp\")\n              ).collect()(0)\n\n  // Compute the metric using the elements of the row above, using the formula below:\n  //\u00a0metric = (fedfunds X dexuseu X unrate X dgs10 X a191rl1q225sbea X indpro X bamlh0a0hym2) / gdp\n  val metric = (macroRow.getDouble(0) * macroRow.getFloat(1) * macroRow.getFloat(2) * macroRow.getFloat(3) * macroRow.getFloat(4) * macroRow.getFloat(5) * macroRow.getFloat(6)\n            /  macroRow.getFloat(7))\n\n  // Read Investors from the `investorsPath`. \n\n  // We only need the `investor_id`, `name`, `education`, `credit_score`, `subscription_id`, `risk_score` and `fav_stocks` fields from Investors.\n  // Update the `credit_score` column by adding the metric you computed previously.\n  // Fix credit scores: they must be between 0 and 10. Make sure that they don't exceed 10 or go below 0. In case it's above 10, set the value to 10. In case it's below 0, set the value to 0.\n  val solution = (spark.read.format(\"delta\").load(investorsPath)\n                .select(\"investor_id\", \"name\", \"education\", \"credit_score\", \"subscription_id\", \"risk_score\", \"fav_stocks\")\n                .withColumn(\"credit_score\", (col(\"credit_score\") + lit(metric)))\n                .withColumn(\"credit_score\", when(col(\"credit_score\") > 10, lit(10)).otherwise(col(\"credit_score\")))\n                .withColumn(\"credit_score\", when(col(\"credit_score\") < 0, lit(0)).otherwise(col(\"credit_score\")))\n                )\n\n  // Write the result to targetDirectory as a Delta table, using the overwrite mode and overwrite schema method.\n  solution.write.mode(\"overwrite\").format(\"delta\").option(\"overwriteSchema\", \"true\").save(targetDirectory)\n  \n  // Return the resulting DataFrame.\n  return solution\n\n}\n\nval solutionDF = solution(spark, macroDataPath, investorsPath, targetDirectory)\ndisplay(solutionDF)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "1ccb1194-b074-4ce7-a7c8-03714b4604c8", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "c1468af8-531c-4e94-8298-8be8a8431b03", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 7, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n<h2><img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Reality Check</h2>\n\nRun the following cell to make sure you are on track:", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "6fe7ba0e-168a-48e3-b626-8de2c54b3eda", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d761e6bb-e230-444e-a934-ccdbfba058d1", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 8, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER - Test your solution\nrealityCheck(solution, spark, macroDataPath, investorsPath, targetDirectory)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "ef6fc716-ddd8-46a7-b1a6-3d39b2a7f0b9", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d3f0e4df-24ce-4d13-94a6-e6cfc6a253a6", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 9, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Cleanup<br>\n\nRun the **`Classroom-Cleanup`** cell below to remove any artifacts created by this lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "02e37fa0-3e36-4219-9d9f-a06e4f69333c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "90a66035-c55b-43d4-83e1-874d3ee11661", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 10, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/Classroom-Cleanup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "80507494-cacd-4c3b-a43d-ef875286c4cf", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "f12fb60e-8642-44a9-af31-7e21d47e86a7", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 11, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## <img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Next Steps\n\nStart the next challenge, [Investors 2]($./PCF 08S - Investors 2)\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "bad3c4d9-901f-4ec9-b525-6b6b39a2f2a3", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "8dac7afe-67f4-4d78-be3c-a215a9a42a00", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 12, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n&copy; 2020 Databricks, Inc. All rights reserved.<br/>\nApache, Apache Spark, Spark and the Spark logo are trademarks of the <a href=\"http://www.apache.org/\">Apache Software Foundation</a>.<br/>\n<br/>\n<a href=\"https://databricks.com/privacy-policy\">Privacy Policy</a> | <a href=\"https://databricks.com/terms-of-use\">Terms of Use</a> | <a href=\"http://help.databricks.com/\">Support</a>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "12896004-599c-464a-b7ae-8ac09f0d3914", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "064a4498-56a1-4b77-842d-4905915d803a", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 13, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "d37ea6b0-318b-4e8f-a4ab-b4fa1a7c72fc", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF 07S - Investors", "origId": 0, "version": "NotebookV1"}