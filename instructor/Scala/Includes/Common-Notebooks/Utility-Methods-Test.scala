{"commands": [{"bindings": {}, "collapsed": false, "command": "\nspark.conf.set(\"com.databricks.training.module-name\", \"common-notebooks\")\n\nval courseAdvertisements = scala.collection.mutable.Map[String,(String,String,String)]()", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "4a4ad970-000d-467d-9ea9-e10151d96d9d", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "af66818f-48c9-4a4e-a102-7d60cfd7bfac", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Utility-Methods", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "f47b4fde-3947-417e-8167-1f94de51ffa1", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1ffa8c91-ead9-4810-97ba-7a41ee57624d", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "import scala.collection.mutable.ArrayBuffer\nval scalaTests = ArrayBuffer.empty[Boolean]\ndef functionPassed(result: Boolean) = {\n  if (result) {\n    scalaTests += true\n  } else {\n    scalaTests += false\n  } \n}", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "15c5b076-a8a2-4f16-a5dd-e4bb8a1e6ec2", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "7765d864-098f-4044-a6e4-f7c90819f169", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `printRecordsPerPartition`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "857b8771-5c93-4043-86dd-e12124f5c558", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1b04e79b-a419-4307-bc5b-b7d4a1389e83", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testPrintRecordsPerPartition(): Boolean = {\n  \n  // Import Data\n  val peopleDF = spark.read.parquet(\"/mnt/training/dataframes/people-10m.parquet\")\n  \n  // Get printed results\n  import java.io.ByteArrayOutputStream\n  val printedOutput = new ByteArrayOutputStream\n  Console.withOut(printedOutput) {printRecordsPerPartition(peopleDF)}\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test if correct number of partitions are printing\n  try {\n    assert(\n      (for (element <- printedOutput.toString.split(\"\\n\") if element.startsWith(\"#\")) yield element).size == peopleDF.rdd.getNumPartitions\n    )\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The correct number of partitions were not identified for printRecordsPerPartition\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the correct number of partitions were not identified for printRecordsPerPartition\")\n    }\n  }\n  \n  // Test if each printed partition has a record number associated\n  try {\n    val recordMap = for (element <- printedOutput.toString.split(\"\\n\") if element.startsWith(\"#\")) \n      yield Map(\n        element.slice(element.indexOf(\"#\") + 1, element.indexOf(\":\") - element.indexOf(\"#\")) -> element.split(\" \")(1).replace(\",\", \"\").toInt\n      )\n    val recordCounts = (for (map <- recordMap if map.keySet.toSeq(0).toInt.isInstanceOf[Int]) yield map.getOrElse(map.keySet.toSeq(0), -1))\n    recordCounts.foreach(x => assert(x.isInstanceOf[Int]))\n    recordCounts.foreach(x => assert(x != -1))\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Not every partition has an associated record count\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for not every partition having an associated record count\")\n    }\n  }\n  \n  // Test if the sum of the printed number of records per partition equals the total number of records\n  try {\n    val printedSum = (\n      for (element <- printedOutput.toString.split(\"\\n\") if element.startsWith(\"#\")) yield element.split(\" \")(1).replace(\",\", \"\").toInt\n    ).sum\n    assert(printedSum == peopleDF.count)\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The sum of the number of records per partition does not match the total number of records\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the sum of the number of records per partition not matching the total number of records\")\n    }\n  }\n  \n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for printRecordsPerPartition passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for printRecordsPerPartition passed\")\n    false\n  }\n}\n\nfunctionPassed(testPrintRecordsPerPartition())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "4abe9c40-7a57-4adb-838f-df19344b5786", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1da75162-cdcb-4f12-8394-d281761098d1", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 5, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `computeFileStats`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "c9e667be-158b-4169-98e6-855ac8c33adc", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "99292e36-173c-424c-9da9-11675949f7f8", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 6, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testComputeFileStats(): Boolean = {\n  \n  // Set file path\n  val filePath = \"/mnt/training/global-sales/transactions/2017.parquet\"\n  \n  // Run and get output\n  val output = computeFileStats(filePath)\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test if result is correct structure\n  try {\n    assert(output.getClass.getName.startsWith(\"scala.Tuple\"))\n    assert(output.productArity == 2)\n    assert(output._1.isInstanceOf[Long])\n    assert(output._2.isInstanceOf[Long])\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The incorrect structure is returned for computeFileStats\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the incorrect structure being returned for computeFileStats\")\n    }\n  }\n  \n  // Test that correct result is returned\n  try {\n    assert(output._1 == 6276)\n    assert(output._2 == 1269333224)\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The incorrect result is returned for computeFileStats\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the incorrect result being returned for computeFileStats\")\n    }\n  }\n  \n  // Test that nonexistent file path throws error\n  try {\n    computeFileStats(\"alkshdahdnoinscoinwincwinecw/cw/cw/cd/c/wcdwdfobnwef\")\n    passedTest(false, \"A nonexistent file path did not throw an error for computeFileStats\")\n  } catch {\n    case a: java.io.FileNotFoundException => {\n      passedTest(true)\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for a nonexistent file path throwing an error for computeFileStats\")\n    }\n  }\n  \n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for computeFileStats passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for computeFileStats passed\")\n    false\n  }\n}\n\nfunctionPassed(testComputeFileStats())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "ce34cabb-33c7-4086-afd4-fc6ad46e05fe", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "bee240ab-54a5-4155-ace4-c54919405518", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 7, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `cacheAs`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "7ffd3dd3-bdd3-49c3-938f-ae3ba9130852", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1a6a16a8-d937-4985-98d4-0f9f974eda4c", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 8, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testCacheAs(): Boolean = {\n  \n  import org.apache.spark.storage.StorageLevel\n  // Import DF\n  val inputDF = spark.read.parquet(\"/mnt/training/global-sales/transactions/2017.parquet\").limit(100)\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test uncached table gets cached\n  try {\n    cacheAs(inputDF, \"testCacheTable12344321\", StorageLevel.MEMORY_ONLY)\n    assert(spark.catalog.isCached(\"testCacheTable12344321\"))\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Uncached table was not cached for cacheAs\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for uncached table being cached for cacheAs\")\n    }\n  }\n  \n  // Test cached table gets recached\n  try {\n    cacheAs(inputDF, \"testCacheTable12344321\", StorageLevel.MEMORY_ONLY)\n    assert(spark.catalog.isCached(\"testCacheTable12344321\"))\n    spark.catalog.uncacheTable(\"testCacheTable12344321\")\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Cached table was not recached for cacheAs\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for cached table being recached for cacheAs\")\n    }\n  }\n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for cacheAs passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for cacheAs passed\")\n    false\n  }\n}\n\nfunctionPassed(testCacheAs())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "f532a2ac-b87a-449a-9ebf-5050ffe1107a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "ff20afe5-355c-4928-88dd-673343f9c02d", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 9, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `benchmarkCount()`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "405ab641-e2bd-4cc0-861f-fdb973bd4b5f", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "aa8d1b99-1f68-4ee4-8bdd-51aa8b2a2d50", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 10, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testBenchmarkCount(): Boolean = {\n  \n  def testFunction() = {\n    spark.createDataFrame(Seq((1, 2, 3, 4), (5, 6, 7, 8), (9, 10, 11, 12)))\n  }\n  val output = benchmarkCount(testFunction)\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test that correct structure is returned\n  try {\n    assert(output.getClass.getName.startsWith(\"scala.Tuple\"))\n    assert(output.productArity == 3)\n    assert(output._1.isInstanceOf[org.apache.spark.sql.DataFrame])\n    assert(output._2.isInstanceOf[Long])\n    assert(output._3.isInstanceOf[Long])\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Correct structure not returned for benchmarkCount\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for correct structure being returned for benchmarkCount\")\n    }\n  }\n  \n  // Test that correct result is returned\n  try {\n    assert(output._1.rdd.collect().deep == testFunction().rdd.collect().deep)\n    assert(output._2 == testFunction().count())\n    assert(output._3 > 0)\n    assert(output._3 < 10000)\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Uncached table was not cached for cacheAs\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for uncached table being cached for cacheAs\")\n    }\n  }\n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for benchmarkCount passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for benchmarkCount passed\")\n    false\n  }\n}\n\nfunctionPassed(testBenchmarkCount())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "356ac323-38be-4bd4-810d-665463a50c87", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d759a430-a973-4a9c-b404-3ea529d9a058", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 11, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test **`untilStreamIsReady()`**", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "e8e18aff-5899-414c-a957-2840f638615c", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "64b7a82e-131c-468a-91fe-34d1b9113c2c", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 12, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val dataPath = \"dbfs:/mnt/training/definitive-guide/data/activity-data-stream.json\"\nval dataSchema = \"Recorded_At timestamp, Device string, Index long, Model string, User string, _corrupt_record String, gt string, x double, y double, z double\"\n\nval initialDF = spark\n  .readStream                             // Returns DataStreamReader\n  .option(\"maxFilesPerTrigger\", 1)        // Force processing of only 1 file per trigger \n  .schema(dataSchema)                     // Required for all streaming DataFrames\n  .json(dataPath)                         // The stream's source directory and file type\n\nval name = \"Testing_123\"\n\ndisplay(initialDF, streamName = name)\nuntilStreamIsReady(name)\nassert(spark.streams.active.length == 1, \"Expected 1 active stream, found \" + spark.streams.active.length)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "b1d8787b-7b9c-461a-b039-18d3c1b07f34", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "afee74f9-44c5-4413-b6cb-a41b85faec24", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 13, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "for (stream <- spark.streams.active) {\n  stream.stop()\n  var queries = spark.streams.active.filter(_.name == stream.name)\n  while (queries.length > 0) {\n    Thread.sleep(5*1000) // Give it a couple of seconds\n    queries = spark.streams.active.filter(_.name == stream.name)\n  }\n  println(\"\"\"The stream \"%s\" has beend terminated.\"\"\".format(stream.name))\n}", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "0fce680d-0bf4-4463-bfba-b171452da1af", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "0be0d357-62e6-40ae-8d73-22593cd2ddeb", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 14, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val numTestsPassed = scalaTests.groupBy(identity).mapValues(_.size)(true)\nif (numTestsPassed == scalaTests.length) {\n  println(s\"All $numTestsPassed tests for Scala passed\")\n} else {\n  println(s\"$numTestsPassed of ${scalaTests.length} tests for Scala passed\")\n  throw new Exception(s\"$numTestsPassed of ${scalaTests.length} tests for Scala passed\")\n}\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "8d8a29b1-6243-454b-98ad-c39f8980e579", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "16f836da-9bd3-471f-b286-6801205fb8da", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 15, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "5f59d6c1-3ec6-4a95-8824-e10eb2a4ae78", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "Utility-Methods-Test", "origId": 0, "version": "NotebookV1"}