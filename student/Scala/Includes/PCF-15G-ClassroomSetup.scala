{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "8facce13-309f-4378-a268-a88aea00611b", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e296a900-8cad-411a-80b3-778dcea8bbee", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val silverLoanRequestsPath = sourceDataPath + \"/silver/loan_requests_cleansed.delta\" \ncourseAdvertisements(\"silverLoanRequestsPath\") = (\"v\", silverLoanRequestsPath , silverLoanRequestsPath)\n\nval silverStockOrdersPath = sourceDataPath + \"/silver/stock_order_spend_balance\" \ncourseAdvertisements(\"silverStockOrdersPath\") = (\"v\", silverStockOrdersPath , silverStockOrdersPath)\n\nval targetDirectory = workingDir + \"/gold/trades_vs_loans\" \ncourseAdvertisements(\"targetDirectory\") = (\"v\", targetDirectory , targetDirectory)\n\ndef realityCheck(testMethod:(org.apache.spark.sql.SparkSession, String, String, String) => DataFrame, spark:org.apache.spark.sql.SparkSession, silverLoanRequestsPath:String, silverStockOrdersPath:String, targetDirectory:String): Unit = {\n  \n  import org.apache.spark.sql.functions._\n  import org.apache.spark.sql.types._\n  import java.io.ByteArrayOutputStream\n  \n  val resultDF = testMethod(spark, silverLoanRequestsPath, silverStockOrdersPath, targetDirectory)\n\n  val loanRequests = (spark.read.format(\"delta\").load(silverLoanRequestsPath))\n  val stockTransactions = (spark.read.format(\"delta\").load(silverStockOrdersPath))\n\n  loanRequests.createOrReplaceTempView(\"loans_sol\")\n  stockTransactions.createOrReplaceTempView(\"stocks_sol\")\n\n  val correctDF = spark.sql(\"\"\"SELECT \n                    l.investor_id, l.loan_alert, \n                    l.amount, s.total_spend_balance\n                    FROM\n                    loans_sol l\n                    JOIN (SELECT investor, SUM(spend_balance) total_spend_balance, month, year \n                          FROM stocks_sol\n                          GROUP BY investor, year, month\n                          ) s \n                    ON(s.investor = l.investor_id)\n                    WHERE l.loan_alert = \"green\" AND\n                          s.month = \"9\" AND s.year = \"2019\"\n                    \"\"\")\n  \n  \n  \n  val resultOutput = new ByteArrayOutputStream\n  val correctOutput = new ByteArrayOutputStream\n  \n  Console.withOut(resultOutput) {resultDF.printSchema()}\n  Console.withOut(correctOutput) {correctDF.printSchema()}\n  \n  def read_delta() : DataFrame = {\n      try {\n        spark.read.format(\"delta\").load(targetDirectory)\n      } catch { \n        case _: Throwable => spark.emptyDataFrame \n      }\n  }\n  \n  val deltaTable = read_delta()\n  \n  val tests = new TestSuite()\n  \n  tests.addTest(TestCase(id=\"PCF-14G-A\", description = \"Returns correct schema\",\n           testFunction = () => compareSchemas(resultDF.schema, correctDF.schema, testColumnOrder=false, testNullable=true)))\n  tests.addTest(TestCase(id=\"PCF-14G-B\", description = \"Returns DataFrame with correct number of rows\",              \n           testFunction = () => resultDF.count == correctDF.count))\n  tests.addTest(TestCase(id=\"PCF-14G-C\", description=\"Returns DataFrame with correct results\", \n           testFunction = () => compareDataFramesLimited(resultDF, correctDF)))\n  tests.addTest(TestCase(id=\"PCF-14G-D\", description = \"Prints expected output\", \n           testFunction = () => resultOutput.toString contains correctOutput.toString))\n  tests.addTest(TestCase(id=\"PCF-14G-E\", description = \"Delta table in place\",\n         testFunction = () => !deltaTable.isEmpty))\n  tests.addTest(TestCase(id=\"PCF-14G-G\", description = \"Delta table has correct content\",\n           testFunction = () => compareDataFramesLimited(deltaTable, correctDF)))\n \n  tests.displayResults()\n  \n  spark.catalog.dropTempView(\"loans_sol\")\n  spark.catalog.dropTempView(\"stocks_sol\")\n  \n}\nallDone(courseAdvertisements)\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "124054a9-6212-4180-9f49-0b354ca0f182", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "60306cb7-3596-4a3d-824e-9ff1309d7290", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "271ee3d1-7b3c-44be-9a18-13531062ef0c", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-15G-ClassroomSetup", "origId": 0, "version": "NotebookV1"}