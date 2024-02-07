{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "ed2affa3-140a-4cbd-98bc-63e1457b6ef4", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "338ccdaa-b5ef-47cc-8fc4-39383fa550a1", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val silverStockOrdersPath = sourceDataPath + \"/silver/stock_orders\"\ncourseAdvertisements(\"silverStockOrdersPath\") = (\"v\", silverStockOrdersPath , silverStockOrdersPath)\n\nval targetDirectory = workingDir + \"/silver/stock_order_daily_metrics\"\ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "16f7b5dc-959d-4336-982c-9c3767a1e7f8", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "31613e84-37a3-4fab-a4e1-14fe8ecf37dd", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// SETUP\nimport org.apache.spark.sql.SparkSession\ndef realityCheck(testMethod:(SparkSession, String, String) => DataFrame, spark:SparkSession, silverStockOrdersPath:String, targetDirectory:String): Unit = {\n  import org.apache.spark.sql.functions._\n  import org.apache.spark.sql.types._\n  import java.io.ByteArrayOutputStream\n  import org.apache.spark.sql.expressions.Window\n  \n  val resultDF = testMethod(spark, silverStockOrdersPath, targetDirectory)\n  \n  val correctDF = (spark.read.format(\"delta\").load(silverStockOrdersPath)\n                       .select(\"order_timestamp\", \"ticker\", \"price\")\n                       .withColumn(\"date\", col(\"order_timestamp\").cast(\"date\")) \n                       .groupBy(\"date\", \"ticker\")\n                       .agg(round(min(col(\"price\")), 2).alias(\"low\"),\n                            round(max(col(\"price\")), 2).alias(\"high\"),  \n                            round(avg(col(\"price\")), 2).alias(\"average\")\n                           )\n                       .orderBy(\"date\",\"ticker\")\n                       )\n \n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n  }\n  \n  val resultOutput = new ByteArrayOutputStream\n  val correctOutput = new ByteArrayOutputStream\n\n  Console.withOut(resultOutput) {testMethod(spark, silverStockOrdersPath, targetDirectory)}\n  Console.withOut(correctOutput) {correctDF.show()}\n  \n  val deltaTable = read_delta()\n  \n  val tests = new TestSuite()\n  tests.addTest(TestCase(id=\"PCF-14S-A\", description=\"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=false)))\n  tests.addTest(TestCase(id=\"PCF-14S-B\", description=\"Returns DataFrame with correct number of rows\",     \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-14S-C\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF.drop(\"date\"), correctDF.drop(\"date\"))))  // Dropping order_timestamp as it uses the current_timestamp function, which is changing\n  tests.addTest(TestCase(id=\"PCF-14S-D\", description = \"Delta Table in place\",\n           testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-14S-E\", description = \"Delta Table has correct contents\",\n           testFunction = () => compareDataFramesLimited(deltaTable.drop(\"date\"), correctDF.drop(\"date\"))))  // Dropping order_timestamp as it uses the current_timestamp function, which is changing\n\n  tests.displayResults()\n\n}\n\ndisplayHTML(\"\"\"\nDeclared the following function:\n  <li><span style=\"color:green; font-weight:bold\">realityCheck</span></li>\n\"\"\")", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "aa7ac5c9-d181-4668-be0d-f1a95513d113", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "46a67be8-c465-478f-9e07-998b65ee2553", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "displayHTML(\"All done!\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "e5768bb3-f8f7-4fab-8f77-bef34ebeb624", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "5ce9d516-ffa5-4c41-9c38-41614d455691", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "11a166ba-9af8-4fad-bd1e-cc9ff3873789", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-14S-ClassroomSetup", "origId": 0, "version": "NotebookV1"}