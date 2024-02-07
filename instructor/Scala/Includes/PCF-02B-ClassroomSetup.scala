{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "39cce867-66d5-4fbe-bc3f-3e61cc76174a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "97ea3830-2dab-4ae0-877d-4655648fc393", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val assetsPath = financeDataPath + \"/assets/assets.csv\"\ncourseAdvertisements(\"assetsPath\") = (\"v\", assetsPath , assetsPath)\n\nval targetDirectory = workingDir + \"/assets.delta\"\ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "84c9fdba-f9c9-4b65-8f4d-8ce9eaaf61be", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d47e1c36-d4a3-4152-b8b9-c1a132ae8096", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// SETUP\nimport org.apache.spark.sql.SparkSession\n\ndef realityCheck(testMethod:(SparkSession, String, String) => DataFrame, spark:SparkSession, assetsPath:String, targetDirectory:String): Unit = {\n  import org.apache.spark.sql.functions._\n  import org.apache.spark.sql.types._\n  \n  val resultDF = testMethod(spark, assetsPath, targetDirectory)\n  \n  val assetsRawDF = (spark.read\n                   .option(\"header\", true)\n                   .option(\"inferSchema\", true)\n                   .option(\"nullValue\", \"na\")\n                   .csv(assetsPath))\n  \n  val correctDF = (assetsRawDF\n               .withColumn(\"ASSET_ID\", col(\"ASSET_ID\").cast(IntegerType))\n               .withColumnRenamed(\"ASSET_ID\", \"asset_id\")\n               .withColumn(\"PRODUCT_ID\", col(\"PRODUCT_ID\").cast(IntegerType))\n               .withColumnRenamed(\"PRODUCT_ID\", \"product_id\"))\n  \n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n      \n  }\n  \n  val deltaTable = read_delta()\n  \n  val tests = new TestSuite()\n  \n  tests.addTest(TestCase(id=\"PCF-02-A\", description=\"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=false)))\n  tests.addTest(TestCase(id=\"PCF-02-B\", description = \"Returns DataFrame without \\\"na\\\" values\",              \n       testFunction = () => resultDF.count() > 0 && resultDF\n       .filter((col(\"valid_to\") === \"na\")).count() == 0))\n  tests.addTest(TestCase(id=\"PCF-02-C\", description=\"Returns DataFrame with correct number of rows\",              \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-02-D\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF, correctDF)))\n  tests.addTest(TestCase(id=\"PCF-02-E\", description = \"Delta table in place\",\n           testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-02-F\", description = \"Delta table has correct content\",\n           testFunction = () => compareDataFramesLimited(deltaTable, correctDF)))\n  \n  tests.displayResults()\n}\n\ndisplayHTML(\"\"\"\nDeclared the following function:\n  <li><span style=\"color:green; font-weight:bold\">realityCheck</span></li>\n\"\"\")", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "aeda6411-24cc-4985-89a9-56e8f8c44d41", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e93403dd-2e5c-483a-a066-a9e6a0ce29ae", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "displayHTML(\"All done!\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "b14964ec-bd8a-4645-bb94-958bff27418c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "efddb6c4-8650-4dca-9637-ef9532b133a0", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "184421e6-16a1-4d05-a8b1-3db62d57d43a", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-02B-ClassroomSetup", "origId": 0, "version": "NotebookV1"}