{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "5e374732-6c89-46d5-9742-acd27f0c158e", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "a97806b1-d22f-4b06-b392-75be87e4c68c", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val stockOrdersPath = financeDataPath + \"/stock_orders\"\ncourseAdvertisements(\"stockOrdersPath \") = (\"v\", stockOrdersPath , stockOrdersPath)\n\nval targetDirectory = workingDir + \"/stock_orders.delta\"\ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "721bf2f1-0332-4926-893e-2a93e5c392c6", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d8a12b70-34fc-4458-8656-d1c0d3ad0110", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// SETUP\n\nimport org.apache.spark.sql.SparkSession\ndef realityCheck(testMethod:(SparkSession, String, String) => DataFrame, spark:SparkSession, stockOrdersPath:String, targetDirectory:String): Unit = {\n  import org.apache.spark.sql.functions._\n  import org.apache.spark.sql.types._\n  import java.io.ByteArrayOutputStream\n  \n  val resultDF = testMethod(spark, stockOrdersPath, targetDirectory)\n  \n  val correctDF = spark.read.json(stockOrdersPath)\n    correctDF.show()\n    correctDF.printSchema()\n \n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n  }\n  \n  val resultOutput = new ByteArrayOutputStream\n  val correctOutput = new ByteArrayOutputStream\n\n  Console.withOut(resultOutput) {testMethod(spark, stockOrdersPath, targetDirectory)}\n  Console.withOut(correctOutput) {correctDF.show()}\n  \n  val deltaTable = read_delta()\n  \n  val tests = new TestSuite()\n  tests.addTest(TestCase(id=\"PCF-05B-A\", description=\"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=false)))\n  tests.addTest(TestCase(id=\"PCF-05B-B\", description=\"Returns DataFrame with correct number of rows\",     \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-05B-C\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF, correctDF)))\n  tests.addTest(TestCase(id=\"PCF-05B-D\", description=\"Prints expected output\", \n           testFunction = () => resultOutput.toString contains correctOutput.toString))\n  tests.addTest(TestCase(id=\"PCF-05B-E\", description = \"Delta table in place\",\n           testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-05B-F\", description = \"Delta table has correct content\",\n           testFunction = () => compareDataFramesLimited(deltaTable, correctDF)))\n  \n  tests.displayResults()\n  \n}\n\ndisplayHTML(\"\"\"\nDeclared the following function:\n  <li><span style=\"color:green; font-weight:bold\">realityCheck</span></li>\n\"\"\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "4610f4c3-5fac-4387-a0eb-9f11ee29a163", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "4e134630-9aa6-406c-8efb-e813fd7de280", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "ba5ad153-9494-4762-a481-956bbb8324eb", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-05B-ClassroomSetup", "origId": 0, "version": "NotebookV1"}