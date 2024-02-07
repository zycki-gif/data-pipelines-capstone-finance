{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "07752c43-2a1d-4b31-99a1-858cb6dbe5eb", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "25c9ad3c-089f-4d47-aad4-6e4ccebbce47", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val loanRequestsPath = financeDataPath + \"/loan_requests/loan_requests.xml\"\ncourseAdvertisements(\"loanRequestsPath\") = (\"v\", loanRequestsPath , loanRequestsPath)\n\nval targetDirectory = workingDir + \"/loan_requests.delta\"\ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "cf0ff870-faca-4d9a-b060-b4f4c9541a91", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "a572dd90-32e0-4814-a8d6-7616945a4f87", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// SETUP\nimport org.apache.spark.sql.SparkSession\ndef realityCheck(testMethod:(SparkSession, String, String) => DataFrame, spark:SparkSession, loanRequestsPath:String, targetDirectory:String): Unit = {\n  import org.apache.spark.sql.functions._\n  import org.apache.spark.sql.types._\n  import java.io.ByteArrayOutputStream\n  \n  val resultDF = testMethod(spark, loanRequestsPath, targetDirectory)\n  \n  val correctDF = (spark.read\n    .format(\"com.databricks.spark.xml\")\n    .option(\"rootTag\", \"loans\")\n    .option(\"rowTag\", \"loan_request\")\n    .option(\"inferSchema\", true)\n    .load(loanRequestsPath) \n   )\n\n \n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n  }\n  \n  val resultOutput = new ByteArrayOutputStream\n  val correctOutput = new ByteArrayOutputStream\n\n  Console.withOut(resultOutput) {testMethod(spark, loanRequestsPath, targetDirectory)}\n  Console.withOut(correctOutput) {correctDF.show(10, truncate=false)}\n  \n  val deltaTable = read_delta()\n  \n  val tests = new TestSuite()\n  tests.addTest(TestCase(id=\"PCF-04B-A\", description=\"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=false)))\n  tests.addTest(TestCase(id=\"PCF-04B-B\", description=\"Returns DataFrame with correct number of rows\",     \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-04B-C\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF, correctDF)))\n  tests.addTest(TestCase(id=\"PCF-04B-D\", description = \"Delta table in place\",\n           testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-04B-E\", description = \"Delta table has correct content\",\n           testFunction = () => !deltaTable.isEmpty && compareDataFramesLimited(deltaTable, resultDF)))\n\n  \n  tests.displayResults()\n  \n}\n\ndisplayHTML(\"\"\"\nDeclared the following function:\n  <li><span style=\"color:green; font-weight:bold\">realityCheck</span></li>\n\"\"\")", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "8c00ec7b-572b-45d6-8ed5-b36bf614cfe8", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "6cadb997-c2d6-442d-8f7f-d21ac6f04375", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "displayHTML(\"All done!\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "187b41aa-02b1-4bbb-96e1-ea862ab569d2", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "bd8ca1b5-f3fe-46d3-8d45-57ef79a3141d", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "7604972d-b835-4d2e-8af9-badd8cffb994", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-04B-ClassroomSetup", "origId": 0, "version": "NotebookV1"}