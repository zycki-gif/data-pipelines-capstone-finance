{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "a007ab15-0333-4606-886e-47a23236beb2", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "4d7f1b5d-b41a-43f6-a717-fa0dbc9e14b7", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val silverStockOrdersPath = sourceDataPath + \"/silver/stock_orders\"  \ncourseAdvertisements(\"silverStockOrdersPath\") = (\"v\", silverStockOrdersPath , silverStockOrdersPath)\n\nval silverInvestorsPath = sourceDataPath + \"/silver/investors\" \ncourseAdvertisements(\"silverInvestorsPath\") = (\"v\", silverInvestorsPath , silverInvestorsPath)\n\nval feesPath = sourceDataPath + \"/fees.csv\" \ncourseAdvertisements(\"feesPath\") = (\"v\", feesPath , feesPath)\n\nval targetDirectory = workingDir + \"/silver/stock_order_spend_balance\"\ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\nimport org.apache.spark.sql.SparkSession\n\ndef realityCheck(testMethod:(SparkSession, String, String, String, String) => DataFrame, spark:SparkSession, silverStockOrdersPath:String, silverInvestorsPath:String, feesPath:String, targetDirectory:String): Unit = {\n  import org.apache.spark.sql.functions._\n  import org.apache.spark.sql.types._\n  \n  val resultDF = testMethod(spark, silverStockOrdersPath, silverInvestorsPath, feesPath, targetDirectory)\n    \n  val stock_orders_cleaned = (spark.read.format(\"delta\").load(silverStockOrdersPath)\n                               .drop(\"clicked_items\")\n                             )\n  val investors = (spark.read.format(\"delta\").load(silverInvestorsPath)\n                   .select(\"investor_id\", \"subscription_id\"))\n  val fees = (spark\n          .read\n          .option(\"inferSchema\",\"true\")\n          .option(\"header\",\"true\")\n          .csv(feesPath)\n          .filter(col(\"product_id\") === 0)\n         )\n\n  val res = (stock_orders_cleaned.as(\"st\")\n             .join(investors.as(\"in\"), $\"st.investor\" === $\"in.investor_id\", \"left\")\n             .select($\"st.*\", $\"in.subscription_id\")\n            )\n\n  val result = (res.join(fees, usingColumns=Seq(\"subscription_id\"), joinType=\"left\")\n               .select(res(\"*\"), fees(\"fees\"))\n               )\n  \n  val correctDF = (result.withColumn(\"spend_balance\", \n                     when(\n                       col(\"type\") === \"BUY\", (lit(-1) * col(\"volume\") * col(\"price\") - col(\"fees\")))\n                       .otherwise(col(\"volume\") * col(\"price\") - col(\"fees\")))                        \n              )\n    \n \n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n      \n  }\n  \n  \n  val deltaTable = read_delta()\n  \n  val tests = new TestSuite()\n  tests.addTest(TestCase(id=\"PCF-12S-A\", description=\"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=true)))\n  tests.addTest(TestCase(id=\"PCF-12S-B\", description=\"Returns DataFrame with correct number of rows\",              \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-12S-C\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF, correctDF)))\n  tests.addTest(TestCase(id=\"PCF-12S-D\", description = \"Delta table in place\",\n           testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-12S-E\", description = \"Silver table has correct content\",\n           testFunction = () => compareDataFramesLimited(deltaTable, correctDF)))\n  \n  tests.displayResults()\n  \n}\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "1c74b0c0-db6c-4f56-af70-36563d3e3b88", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "824346fc-95c0-4294-9213-1c13a26fb01d", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "22057c90-e0f0-4e21-881d-da0bb145cf7b", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "27d0a5bb-a4e5-4eff-9cf8-58d74c9f524b", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "7ae5cb47-5842-49db-b5d9-b89f9f4a96a4", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-12S-ClassroomSetup", "origId": 0, "version": "NotebookV1"}