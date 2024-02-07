{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "715f1923-d40c-4668-80b1-4136b6110fd2", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "2d139145-0f0e-47e8-ae5a-564e41f04176", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val macroDataPath = sourceDataPath + \"/bronze/macro\"\ncourseAdvertisements(\"macroDataPath\") = (\"v\", macroDataPath , macroDataPath)\n\nval investorsPath = sourceDataPath + \"/bronze/investors\"\ncourseAdvertisements(\"investorsPath\") = (\"v\", investorsPath , investorsPath)\n\nval targetDirectory = workingDir + \"/silver/investors.delta\"\ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "e076e181-4de7-40fc-9741-b9559ca8fa8c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e88bfcc5-c006-42c7-8bdd-9bec8c30cfba", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// SETUP\nimport org.apache.spark.sql.SparkSession\nimport org.apache.spark.sql.functions._\nimport org.apache.spark.sql.types._\n\ndef realityCheck(testMethod:(SparkSession, String, String, String) => DataFrame, spark:SparkSession, macroDataPath:String, investorsPath:String, targetDirectory:String): Unit = {\n\n  val macro_2 = (spark.read.format(\"delta\").load(macroDataPath))\n  \n  val max_date = macro_2.select(max(col(\"date\"))).collect()\n\n  val macro_row = (macro_2\n               .filter(col(\"date\") === max_date(0)(0))\n                   .select(\"fedfunds\", \"dexuseu\", \"unrate\", \"dgs10\", \"a191rl1q225sbea\", \"indpro\", \"bamlh0a0hym2\", \"gdp\")\n              ).collect()(0)\n\n  val metric = (macro_row.getDouble(0) * macro_row.getFloat(1) * macro_row.getFloat(2) * macro_row.getFloat(3) * macro_row.getFloat(4) * macro_row.getFloat(5) * macro_row.getFloat(6)\n            /  macro_row.getFloat(7))\n\n  val correctDF = (spark.read.format(\"delta\").load(investorsPath)\n                   .select(\"investor_id\", \"name\", \"education\", \"credit_score\", \"subscription_id\", \"risk_score\", \"fav_stocks\")\n                .withColumn(\"credit_score\", (col(\"credit_score\") + lit(metric)))\n                .withColumn(\"credit_score\", when(col(\"credit_score\") > 10, lit(10)).otherwise(col(\"credit_score\")))\n                .withColumn(\"credit_score\", when(col(\"credit_score\") < 0, lit(0)).otherwise(col(\"credit_score\")))\n                )\n\n  val resultDF = testMethod(spark, macroDataPath,investorsPath, targetDirectory)\n\n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n  }\n  \n  val deltaTable = read_delta()\n\n  val tests = new TestSuite()\n  tests.addTest(TestCase(id=\"PCF-09S-A\", description=\"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=false)))\n  tests.addTest(TestCase(id=\"PCF-09S-B\", description=\"Returns DataFrame with correct number of rows\",              \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-09S-C\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF, correctDF)))\n  tests.addTest(TestCase(id=\"PCF-09S-D\", description = \"Delta table in place\",\n           testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-09S-E\", description = \"Delta table has correct content\",\n           testFunction = () => compareDataFramesLimited(deltaTable, correctDF)))\n\n  tests.displayResults()\n  \n}\n  \ndisplayHTML(\"\"\"\nDeclared the following function:\n  <li><span style=\"color:green; font-weight:bold\">realityCheck</span></li>\n\"\"\")", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "dfd21d02-c5dd-40cf-a8cc-a27ae779554a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "33d29ea3-7975-45fe-8f46-f79a76377ae8", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "displayHTML(\"All done!\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "22516c7e-f2d9-4112-86ff-3d4dd77c693f", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "7bd1302b-ed0e-4ca8-a394-8be60d2b69cc", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "a32ba75c-7b81-41d2-8154-d03f30ceb82a", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-07S-ClassroomSetup", "origId": 0, "version": "NotebookV1"}