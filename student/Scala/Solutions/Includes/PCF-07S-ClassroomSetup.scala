{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "df1477c6-bd62-4ae5-93b1-901e3acefa30", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1764d8a5-d839-4c06-882a-dc6e1849ee23", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val macroDataPath = sourceDataPath + \"/bronze/macro\"\ncourseAdvertisements(\"macroDataPath\") = (\"v\", macroDataPath , macroDataPath)\n\nval investorsPath = sourceDataPath + \"/bronze/investors\"\ncourseAdvertisements(\"investorsPath\") = (\"v\", investorsPath , investorsPath)\n\nval targetDirectory = workingDir + \"/silver/investors.delta\"\ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "cadd59cb-922b-4da4-b28d-9fd1271da711", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e55efc48-db63-4619-9968-6ba8de67662d", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "// SETUP\nimport org.apache.spark.sql.SparkSession\nimport org.apache.spark.sql.functions._\nimport org.apache.spark.sql.types._\n\ndef realityCheck(testMethod:(SparkSession, String, String, String) => DataFrame, spark:SparkSession, macroDataPath:String, investorsPath:String, targetDirectory:String): Unit = {\n\n  val macro_2 = (spark.read.format(\"delta\").load(macroDataPath))\n  \n  val max_date = macro_2.select(max(col(\"date\"))).collect()\n\n  val macro_row = (macro_2\n               .filter(col(\"date\") === max_date(0)(0))\n                   .select(\"fedfunds\", \"dexuseu\", \"unrate\", \"dgs10\", \"a191rl1q225sbea\", \"indpro\", \"bamlh0a0hym2\", \"gdp\")\n              ).collect()(0)\n\n  val metric = (macro_row.getDouble(0) * macro_row.getFloat(1) * macro_row.getFloat(2) * macro_row.getFloat(3) * macro_row.getFloat(4) * macro_row.getFloat(5) * macro_row.getFloat(6)\n            /  macro_row.getFloat(7))\n\n  val correctDF = (spark.read.format(\"delta\").load(investorsPath)\n                   .select(\"investor_id\", \"name\", \"education\", \"credit_score\", \"subscription_id\", \"risk_score\", \"fav_stocks\")\n                .withColumn(\"credit_score\", (col(\"credit_score\") + lit(metric)))\n                .withColumn(\"credit_score\", when(col(\"credit_score\") > 10, lit(10)).otherwise(col(\"credit_score\")))\n                .withColumn(\"credit_score\", when(col(\"credit_score\") < 0, lit(0)).otherwise(col(\"credit_score\")))\n                )\n\n  val resultDF = testMethod(spark, macroDataPath,investorsPath, targetDirectory)\n\n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n  }\n  \n  val deltaTable = read_delta()\n\n  val tests = new TestSuite()\n  tests.addTest(TestCase(id=\"PCF-09S-A\", description=\"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=false)))\n  tests.addTest(TestCase(id=\"PCF-09S-B\", description=\"Returns DataFrame with correct number of rows\",              \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-09S-C\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF, correctDF)))\n  tests.addTest(TestCase(id=\"PCF-09S-D\", description = \"Delta table in place\",\n           testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-09S-E\", description = \"Delta table has correct content\",\n           testFunction = () => compareDataFramesLimited(deltaTable, correctDF)))\n\n  tests.displayResults()\n  \n}\n  \ndisplayHTML(\"\"\"\nDeclared the following function:\n  <li><span style=\"color:green; font-weight:bold\">realityCheck</span></li>\n\"\"\")", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "769baa7e-e363-4502-befe-a9f54a39a00a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "0e005015-12ee-4496-8fa6-e57754cefd4d", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "displayHTML(\"All done!\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "7705ab57-9ed0-4dbd-91ba-790db3e1d3ac", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "7287b2e9-bd9d-4c73-b41a-a4b81ab729fb", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "f37d9d02-9842-41ca-93a0-912549c30dc7", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-07S-ClassroomSetup", "origId": 0, "version": "NotebookV1"}