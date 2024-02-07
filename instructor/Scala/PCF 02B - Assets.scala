{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%md-sandbox\n\n<div style=\"text-align: center; line-height: 0; padding-top: 9px;\">\n  <img src=\"https://databricks.com/wp-content/uploads/2018/03/db-academy-rgb-1200px.png\" alt=\"Databricks Learning\" style=\"width: 1200px\">\n</div>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "236a0a15-4cbb-4e58-b1c8-26c88c427efd", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "2084e725-9695-4a12-9ed8-485e0a13b35e", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<img src=\"https://files.training.databricks.com/images/Apache-Spark-Logo_TM_200px.png\" style=\"float: left: margin: 20px\"/>\n\n# Source To Bronze - Assets\n\nAssets are stock tickers investors are buying and selling through our online platform. Create a Delta table from the Assets CSV and make sure that types and null values are correct.\n\n## In this exercise you will:\n* Read from a defined source path and create a bronze layer\n* Learn how to handle data quality issues\n\n## Prerequisites\n* Web browser: **Chrome**\n* A cluster configured with **8 cores** and **DBR 7.0**", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "5acaf779-0fb3-4193-8e97-e0e2dbd5c70f", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "66eb502f-8183-45f0-b260-f3d0a0638b86", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n<h2 style=\"color:red\">Instructor Note</h2>\n\n\n\nThings to talk about:\n- bronze level tables in detail, including:\n  - their purpose: \n    - store and retain data in its rawest form\n    - can be used later as the single source of truth\n    - new features can be generated using bronze tables\n  - what they are and what they aren't:\n    - data imported to data lake without any transformation\n    - not silver tables, eg: they are not filtered, cleaned or aggregated\n    - not gold tables, eg: should not be used as an input for dashboards, ml algorithms\n  - optionally questions if an operation is bronze level or not:\n    - stock trade list\n    - stock trade list from a specific good\n    - daily sums of the trades\n    - user account informations with hashed passwords\n  - links to articles to inspire:\n    - [multi-hop and ml](https://databricks.com/blog/2019/08/14/productionizing-machine-learning-with-delta-lake.html)\n    - [slides about the architecture](https://www.slideshare.net/JuanPauloGutierrez/delta-architecture)\n- the goal of this exercise (reiterate what assets are from the introduction)\n- walk them through the steps of the solution \n  - highlight the importance of nullValues option\n  - in case of guided delivery, feel free to talk about other useful options here, eg. timestampFormat, multiLine\n- warn the participants to check the output schema \n- suggest to implement the solution incrementally instead of doing it in one go\n- emphasize that they are suppose to create a function in order to check their solution\n- make sure that the participants run the classroom setup script which creates the necessary variables for them", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "c1b74e64-e9fc-46c3-a6bb-fc0284afe42f", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "fca0992d-5b9c-4bfd-ad4a-ac9e36255e1d", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Setup<br>\n\nFor each lesson to execute correctly, please make sure to run the **`Classroom-Setup`** cell at the start of each lesson (see the next cell) and the **`Classroom-Cleanup`** cell at the end of each lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "6b208aa1-a891-4ea3-b9e5-4e1707125b04", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "ad0bf0b7-0fb8-4351-a3ba-7507fef7e0b4", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/PCF-02B-ClassroomSetup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "480064a6-c2a4-40c8-be49-886b71cff472", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "cb1d2467-2821-419a-afb4-3a7d83312559", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 5, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Steps to Complete\nImplement the function **`challenge()`** to achieve the following:\n- Read the Assets CSV file from the **`assetsPath`** into a DataFrame.\n- Make sure both **`na`** values and empty values are picked up as nulls in every column.\n- Make sure you convert the \"ASSET_ID\" and \"PRODUCT_ID\" columns to integer.\n- Convert every column name to lowercase.\n- Write the resulting DataFrame as a Delta table to **`targetDirectory`**. \n- Return the final DataFrame, which should have the following schema:\n\n|name|type|\n|---|---|\n|assets_id|StringType|\n|product_id|IntegerType|\n|valid_from|TimeStamp|\n|valid_to|StringType|\n|asset_risk|IntegerType|\n|asset_type|StringType|", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "991a2be2-4ae6-475c-9383-ff81bb488efe", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "3ecc6b01-b747-4fd0-8b23-c2a32699ae52", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 6, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER\n\ndef solution(spark:org.apache.spark.sql.SparkSession, assetsPath:String, targetDirectory:String) : DataFrame = {\n  import org.apache.spark.sql.types._\n  import org.apache.spark.sql.functions._\n  \n  // Read assetsPath and make sure that:\n  // * \"na\" values are picked up as null.\n  val assetsRawDF = (spark.read\n                   .option(\"header\", true)\n                   .option(\"inferSchema\", true)\n                   .option(\"nullValue\", \"na\")\n                   .csv(assetsPath))\n  \n  // Convert uppercase column names to lowercase. Make sure to convert these fields to integers.\n  val assetsDF = (assetsRawDF\n               .withColumn(\"ASSET_ID\", col(\"ASSET_ID\").cast(IntegerType))\n               .withColumnRenamed(\"ASSET_ID\", \"asset_id\")\n               .withColumn(\"PRODUCT_ID\", col(\"PRODUCT_ID\").cast(IntegerType))\n               .withColumnRenamed(\"PRODUCT_ID\", \"product_id\"))\n \n  // Write the final DataFrame as a Delta table to \"targetDirectory\".\n    // Make sure you let Spark overwrite the target location if necessary.\n  assetsDF.write.mode(\"overwrite\").option(\"overwriteSchema\", \"true\").format(\"delta\").save(targetDirectory)  \n  \n  // Return the final DataFrame.\n  return assetsDF\n}\n\nval finalDF = solution(spark, assetsPath, targetDirectory)\ndisplay(finalDF)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "fe67d1b3-ac39-4727-8415-4238de9ec3e2", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "2a2a19ca-1929-4703-aab5-d08d8dc317c2", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 7, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n<h2><img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Reality Check</h2>\n\nRun the following cell to make sure you are on track:", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "f69c54a8-8252-499f-aaab-82dd990c1a3e", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "2ec233cc-77aa-4114-9cac-9ccf371d5a94", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 8, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// ANSWER - Test your solution\nrealityCheck(solution, spark, assetsPath, targetDirectory)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "f76d2a9e-34d6-43eb-a7b2-13e725433263", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "49456b32-cf17-4f11-ad2c-1e358d2d810f", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 9, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## ![Spark Logo Tiny](https://files.training.databricks.com/images/105/logo_spark_tiny.png) Classroom-Cleanup<br>\n\nRun the **`Classroom-Cleanup`** cell below to remove any artifacts created by this lesson.", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "d71f6239-900e-4879-8901-6fe3d4bb0304", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "a4d1ba2e-3d9a-4a57-96da-454c30f77708", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 10, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Includes/Classroom-Cleanup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "398d901d-804f-4981-a9c5-23e1f2dca596", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e5df2306-f1ea-411d-9634-7f123cca20e4", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 11, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n## <img src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"> Next Steps\n\nStart the next challenge, [Investors]($./PCF 03B - Investors)\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "aa4becac-719d-436f-b226-1c44baa9301a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e0a57b54-38a0-445e-a0d4-7a20fb3a57c8", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 12, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md-sandbox\n&copy; 2020 Databricks, Inc. All rights reserved.<br/>\nApache, Apache Spark, Spark and the Spark logo are trademarks of the <a href=\"http://www.apache.org/\">Apache Software Foundation</a>.<br/>\n<br/>\n<a href=\"https://databricks.com/privacy-policy\">Privacy Policy</a> | <a href=\"https://databricks.com/terms-of-use\">Terms of Use</a> | <a href=\"http://help.databricks.com/\">Support</a>", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "a21557ad-3f26-488e-8e8b-bb6ffcccd369", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "c97e4b44-373e-4e83-ba06-0858371acf5c", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 13, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "5b415f91-11a3-4250-a376-22b59cee35b2", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF 02B - Assets", "origId": 0, "version": "NotebookV1"}