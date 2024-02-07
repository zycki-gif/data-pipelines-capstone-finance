{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%md-sandbox\n\n<div style=\"text-align: center; line-height: 0; padding-top: 9px;\">\n  <img src=\"https://databricks.com/wp-content/uploads/2018/03/db-academy-rgb-1200px.png\" alt=\"Databricks Learning\" style=\"width: 1200px\">\n</div>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "8489c22e-bfb6-44c2-b783-510120b0906b", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "9d2c6488-448a-4c87-96b7-c21f5d2a51e2", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<img src=\"https://files.training.databricks.com/images/Apache-Spark-Logo_TM_200px.png\" style=\"float: left: margin: 20px\"/>\n\n# Bronze To Silver - Clicks\n\nInvestors are clicking on multiple tickers to see their time series charts during the session. We log this information in the clicked items field. In this notebook we are going to work on the cleaned version of the stock transactions and find the time spent on tickers. As always, you will write the end result as a Delta table.\n\n## In this exercise you will:\n* Learn how to work with complex data types like nested array type fields\n* Practice the application of feature engineering techniques with built-in functions or SQL expressions\n* Find out how to write User Defined Functions (UDFs) for nested array type fields\n* Get a handle on turning arrays into individual rows with `explode`\n\n## Prerequisites\n* Web browser: **Chrome**\n* A cluster configured with **8 cores** and **DBR 7.0**", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "26f6b1d1-6963-4e09-8a73-ea7e32e641d6", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1f94dd7a-97de-4b3f-b809-a317e9f98c17", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Setup<br>\n\nFor each lesson to execute correctly, please make sure to run the **`Classroom-Setup`** cell at the start of each lesson (see the next cell) and the **`Classroom-Cleanup`** cell at the end of each lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "7cdcff93-0163-4c49-a171-09287cf79687", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "45b4ca00-fcbe-41ed-b5b5-eeaf64592165", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/PCF-13S-ClassroomSetup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "ecb50f52-dcb0-47f8-ab66-a3f94c2623a3", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "b84dcccf-45fe-47f2-9ff8-71a6ce78a12b", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Steps to complete<br>\nImplement the **`challenge()`** function to achieve the following:\n- Read the Stock Orders table from **`silverStockOrdersPath`** into a DataFrame.\n- Select the **`transaction_id`**, **`investor`**, **`dow`**, **`clicked_items`** columns only.\n- Create a UDF to compute the total time spent in **`clicked_items`**. Return an IntegerType.\n- Create a new column named **`time_spent_on_site`** by using the UDF you created.\n- Transform the DataFrame to have one **`clicked_items`** per row and rename it **`ticker_clicked`**. It should contain only the ticker name.\n   - Also, create a **`time_spent_on_ticker`** column to have the time spent on that single ticker.\n- Write the resulting DataFrame to **`targetDirectory`** as a Delta table using the overwrite mode. \n- Return the resulting DataFrame.\n<p> **`silverStockOrdersPath`** and **`targetDirectory`** have already been defined in your environment.\n  \n  \nThe schema of the resulting DataFrame should look like this:\n  \n|name|type|\n|---|---|\n|transaction_id|LongType|\n|investor|LongType|\n|dow|IntegerType|\n|time_spent_on_site|IntegerType|\n|ticker_clicked|StringType|\n|time_spent_on_ticker|IntegerType|", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "bc94d7bf-d19b-4608-bc5f-77a1bbc9f252", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e166b40d-a42e-484e-9216-639ccf89af96", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 5, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER\n\ndef solution(spark:org.apache.spark.sql.SparkSession, silverStockOrdersPath:String, targetDirectory:String) : DataFrame = {\n  import org.apache.spark.sql.functions._\n \n  // Read the Stock Orders table from `silverStockOrdersPath` into a DataFrame.\n  // Select the `transaction_id`, `investor`, `dow`, `clicked_items` columns only.\n  val clickBase = (spark.read.format(\"delta\").load(silverStockOrdersPath)\n               .select(\"transaction_id\", \"investor\", \"dow\", \"clicked_items\")\n               )\n  \n  // Create a UDF to compute the total time spent in `clicked_items`. Return an IntegerType.\n  val time_spent_udf = udf((l:Seq[Seq[String]]) => l.map(_(1).toInt).sum)\n\n  // Create a new column named `time_spent_on_site` by using the UDF you created.\n  val clickBase2 = clickBase.withColumn(\"time_spent_on_site\", time_spent_udf(col(\"clicked_items\")))\n  \n  // Transform the DataFrame to have one clicked_items per row and rename it `ticker_clicked`. \n  // It should contain only the ticker name. \n  // Also, create a `time_spent_on_ticker` column to have the time spent on that single ticker.\n  val clickExp = (clickBase2\n                           .withColumn(\"clicked_item\", explode($\"clicked_items\"))\n                           .selectExpr(\"*\", \n                                       \"clicked_item[0] AS ticker_clicked\", \n                                       \"CAST(clicked_item[1] AS int) AS time_spent_on_ticker\")\n                           .drop(\"clicked_items\", \"clicked_item\")\n                          )\n  \n  // Write the resulting DataFrame to `targetDirectory` as a Delta table using the overwrite mode. \n  clickExp.write.mode(\"overwrite\").option(\"overwriteSchema\", \"true\").format(\"delta\").save(targetDirectory) \n  \n  // Return the resulting DataFrame.\n  return clickExp\n  \n}\n\nval solutionDF = solution(spark, silverStockOrdersPath, targetDirectory)\ndisplay(solutionDF)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "9b91471d-c4af-4119-a537-be10ea0c1e9a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "ecdd0aab-7260-48e8-ba0f-3c12a19baf4c", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 6, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n<h2><img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Reality Check</h2>\n\nRun the following cell to make sure you are on track:", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "fc8b5ac8-28ab-423b-99a1-e680419fe372", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "3198370e-dc9f-4540-a737-cea05bd1f493", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 7, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER - Test your solution\nrealityCheck(solution, spark, silverStockOrdersPath, targetDirectory)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "a49f4937-fccc-4193-aa41-c59d202cadd1", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "c0b93ba6-67dc-4f69-8e59-c59755acd70b", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 8, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Cleanup<br>\n\nRun the **`Classroom-Cleanup`** cell below to remove any artifacts created by this lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "46a37cda-a32b-4ebf-9d2b-47796fe07628", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "6f60ab2e-8279-4ca8-8068-a15455663026", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 9, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/Classroom-Cleanup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "fffe3375-9579-49b7-942d-19ee537df837", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "7b2f81c6-b59c-4b5d-9385-34c2d662c539", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 10, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## <img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Next Steps\n\nStart the next challenge, [Daily Stock Metrics]($./PCF 14S - Daily Stock Metrics)\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "8a160589-4a5f-4745-8820-f0cb4f3651e5", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "c3e6c0da-ae15-42bf-9770-7db3d919cf9e", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 11, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n&copy; 2020 Databricks, Inc. All rights reserved.<br/>\nApache, Apache Spark, Spark and the Spark logo are trademarks of the <a href=\"http://www.apache.org/\">Apache Software Foundation</a>.<br/>\n<br/>\n<a href=\"https://databricks.com/privacy-policy\">Privacy Policy</a> | <a href=\"https://databricks.com/terms-of-use\">Terms of Use</a> | <a href=\"http://help.databricks.com/\">Support</a>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "fd9171f5-5b53-4b0b-8e6c-1eee197586c6", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "13c0944c-fd56-4a0a-8e0d-5bad8c984519", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 12, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "bf7604df-f11e-4af8-8f6b-d96fa7dfe487", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF 13S - Clicks", "origId": 0, "version": "NotebookV1"}