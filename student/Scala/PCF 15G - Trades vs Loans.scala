{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%md-sandbox\n\n<div style=\"text-align: center; line-height: 0; padding-top: 9px;\">\n  <img src=\"https://databricks.com/wp-content/uploads/2018/03/db-academy-rgb-1200px.png\" alt=\"Databricks Learning\" style=\"width: 1200px\">\n</div>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "32cbcd8b-177e-41bb-9806-df0eb5e04fc0", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "3b76a360-d3cb-48e9-a4b8-7473393e5f51", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<img src=\"https://files.training.databricks.com/images/Apache-Spark-Logo_TM_200px.png\" style=\"float: left: margin: 20px\"/>\n\n# Silver To Gold - Sales Orders Table\n\nWe want to pay close attention to cases where we mark loan requests green (good to go) but the recent spending balance of the investor is deep into negative territory. This is a gold notebook required by our analysts to support their decision making.\n\n## In this exercise you will:\n* Find out how to register DataFrames as Spark SQL Temporary Views\n* Learn how to work with SQL queries\n\n## Prerequisites\n* Web browser: **Chrome**\n* A cluster configured with **8 cores** and **DBR 7.0**", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "ebe33549-9b41-4541-9044-a2bb385718b4", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "6b6f3dcd-754b-461a-98f0-d803e18c6863", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Setup<br>\n\nFor each lesson to execute correctly, please make sure to run the **`Classroom-Setup`** cell at the start of each lesson (see the next cell) and the **`Classroom-Cleanup`** cell at the end of each lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "41cb81cf-e59a-496b-9c9d-3ad82cd7bf1e", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "a2b31fee-b448-4b31-81ad-2454276404fe", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/PCF-15G-ClassroomSetup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "c19396f5-cc9f-4e9b-9477-4e81fd1a700f", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d8a23a97-45b8-4032-b4f4-04003fdc3ab1", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Steps to Complete<br>\n\nFor each lesson to execute correctly, please make sure to run the **`Classroom-Setup`** cell at the start of each lesson (see the next cell) and the **`Classroom-Cleanup`** cell at the end of each lesson.\n\n\nImplement the **`challenge()`** function to achieve the following:\n\n- Read Delta table from **`silverLoanRequestsPath`** to create the **`loanRequests`** DataFrame.\n- Read Delta table from **`silverStockOrdersPath`** to create the **`stockTransactions`** DataFrame.\n \n#### Use Spark SQL on the Temp Views to achieve the following:\n\n- Register the 2 tables as Spark SQL Temp Views, **`loans`** and **`stocks`** to create an SQL query.\n- Group the stocks table by **`investor`**, **`month`** and **`year`**, summing up the **`spend_balance`** column.\n- Inner join this grouped table with the **`loans`** table on **`investor`** and **`investor_id`** fields.\n- Only keep records where the **`loan_alert`** is green and **`stocks`** are from September 2019.\n- Select the columns to meet the expected schema described below.\n\n#### Use the DataFrame API to:\n\n- Print the schema.\n- Write the result to **`targetDirectory`** as a Delta table, using the overwrite mode.\n- Return the joined DataFrame.\n- Only select the columns listed below. The schema of the resulting DataFrame should look like this:\n\n|name|type|\n|---|---|\n|investor_id|LongType|\n|loan_alert|StringType|\n|amount|IntegerType|\n|total_spend_balance|DoubleType|", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "810c608c-f633-4173-a2cc-126b458c55fc", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1870ff58-236e-495a-a4b4-ecd478274a9f", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 5, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// TODO\n\ndef challenge(spark:org.apache.spark.sql.SparkSession, silverLoanRequestsPath:String, silverStockOrdersPath:String, targetDirectory:String) : DataFrame = {\n  \n  // Read the Delta table from `silverLoanRequestsPath` to create the `loanRequests` DataFrame.\n\n  // Read the Delta table from `silverStockOrdersPath` to create the `stockTransactions` DataFrame.\n  \n  // Register the 2 tables as Spark SQL Temp Views, loans and stocks to write a SQL query.\n\n  // Group the stocks table by investor, month and year, summing up the spend_balance column.\n  // Inner join this grouped table with the loans table on investor and investor_id fields.\n  // Only keep records where the loan_alert is green and stocks are from September 2019.\n  // Select the columns to meet the expected schema described in Steps to Complete.\n\n  // Print the schema.\n\n  // Write the result to `targetDirectory` as a Delta table, using the overwrite mode.\n\n  // Return the joined DataFrame.\n  return FILL_IN.DATAFRAME\n}\n\nval finalDF = challenge(spark, silverLoanRequestsPath, silverStockOrdersPath, targetDirectory)\ndisplay(finalDF)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "b712a0e6-699c-44b2-8a0d-704b907070a2", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "79c391e3-32a5-42b5-bc3d-9e2908da6fb8", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 6, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n<h2><img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Reality Check</h2>\n\nRun the following cell to make sure you are on track:", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "985f999c-eacb-4e0d-8c01-4d55a3abb061", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "72005d5a-1596-4327-a2a0-5ef33428e2d0", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 7, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// TODO - Test your solution\nrealityCheck(challenge, spark, silverLoanRequestsPath, silverStockOrdersPath, targetDirectory)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "4c9ca7a6-7910-4d96-abed-cec85a9e4d5c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "cf9ab772-5508-4f83-acfb-15860a20f433", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 8, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Cleanup<br>\n\nRun the **`Classroom-Cleanup`** cell below to remove any artifacts created by this lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "f08d7772-d1fd-4f27-96ba-6a035ec33571", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "92f495e0-e3a7-43be-844f-351ed35c743b", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 9, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/Classroom-Cleanup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "4c04fc25-e6ee-4a97-a0b4-eaf3de4e7411", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "8a28c2b5-986b-4d1f-878c-15eccfe77a75", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 10, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## <img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Next Steps\n\nStart the next challenge, [Price Over Time]($./PCF 16G - Price Over Time)\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "ff15ec77-ae7d-414e-8de2-7078bbf5f3ad", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "70144697-82a4-486f-b950-db0e98ff4627", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 11, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n&copy; 2020 Databricks, Inc. All rights reserved.<br/>\nApache, Apache Spark, Spark and the Spark logo are trademarks of the <a href=\"http://www.apache.org/\">Apache Software Foundation</a>.<br/>\n<br/>\n<a href=\"https://databricks.com/privacy-policy\">Privacy Policy</a> | <a href=\"https://databricks.com/terms-of-use\">Terms of Use</a> | <a href=\"http://help.databricks.com/\">Support</a>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "582cdb52-e22f-49d0-9348-c4d4f69a4a6d", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "7ceec91d-2239-49a5-83e7-4d6694a0767e", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 12, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "4d9783ed-0d5c-4c52-a59b-9056d797651b", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF 15G - Trades vs Loans", "origId": 0, "version": "NotebookV1"}