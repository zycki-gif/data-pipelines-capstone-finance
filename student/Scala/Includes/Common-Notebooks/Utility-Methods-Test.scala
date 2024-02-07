{"commands": [{"bindings": {}, "collapsed": false, "command": "\nspark.conf.set(\"com.databricks.training.module-name\", \"common-notebooks\")\n\nval courseAdvertisements = scala.collection.mutable.Map[String,(String,String,String)]()", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "a4af07e1-62be-462a-9fe5-81b29413524a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "1bb0eb11-978f-49f8-9fec-eca17cec1afd", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Utility-Methods", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "0c23cb32-fb7d-4cfe-8108-bb636b691e90", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "ea632306-82b9-4845-ba1d-fd66ce629cba", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "import scala.collection.mutable.ArrayBuffer\nval scalaTests = ArrayBuffer.empty[Boolean]\ndef functionPassed(result: Boolean) = {\n  if (result) {\n    scalaTests += true\n  } else {\n    scalaTests += false\n  } \n}", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "db97a79c-84a6-41f3-b643-cf2c5ad7417a", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "8dbc9ce4-dd20-4fe3-b66e-e6c1221f54d5", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `printRecordsPerPartition`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "23ed8650-440e-41ab-a675-09749a3e3bc5", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "75dd8236-90fd-474b-a92e-1a281e7e92f0", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testPrintRecordsPerPartition(): Boolean = {\n  \n  // Import Data\n  val peopleDF = spark.read.parquet(\"/mnt/training/dataframes/people-10m.parquet\")\n  \n  // Get printed results\n  import java.io.ByteArrayOutputStream\n  val printedOutput = new ByteArrayOutputStream\n  Console.withOut(printedOutput) {printRecordsPerPartition(peopleDF)}\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test if correct number of partitions are printing\n  try {\n    assert(\n      (for (element <- printedOutput.toString.split(\"\\n\") if element.startsWith(\"#\")) yield element).size == peopleDF.rdd.getNumPartitions\n    )\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The correct number of partitions were not identified for printRecordsPerPartition\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the correct number of partitions were not identified for printRecordsPerPartition\")\n    }\n  }\n  \n  // Test if each printed partition has a record number associated\n  try {\n    val recordMap = for (element <- printedOutput.toString.split(\"\\n\") if element.startsWith(\"#\")) \n      yield Map(\n        element.slice(element.indexOf(\"#\") + 1, element.indexOf(\":\") - element.indexOf(\"#\")) -> element.split(\" \")(1).replace(\",\", \"\").toInt\n      )\n    val recordCounts = (for (map <- recordMap if map.keySet.toSeq(0).toInt.isInstanceOf[Int]) yield map.getOrElse(map.keySet.toSeq(0), -1))\n    recordCounts.foreach(x => assert(x.isInstanceOf[Int]))\n    recordCounts.foreach(x => assert(x != -1))\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Not every partition has an associated record count\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for not every partition having an associated record count\")\n    }\n  }\n  \n  // Test if the sum of the printed number of records per partition equals the total number of records\n  try {\n    val printedSum = (\n      for (element <- printedOutput.toString.split(\"\\n\") if element.startsWith(\"#\")) yield element.split(\" \")(1).replace(\",\", \"\").toInt\n    ).sum\n    assert(printedSum == peopleDF.count)\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The sum of the number of records per partition does not match the total number of records\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the sum of the number of records per partition not matching the total number of records\")\n    }\n  }\n  \n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for printRecordsPerPartition passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for printRecordsPerPartition passed\")\n    false\n  }\n}\n\nfunctionPassed(testPrintRecordsPerPartition())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "eb272487-de33-4fa1-a479-6620a8d795f8", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "80e7aedd-7dc8-44df-a6f2-12abcd7d8e1b", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 5, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `computeFileStats`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "49291c96-06c5-4263-9b6d-33fae2ca1e0d", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "09918d97-9c8c-4f8a-b8d6-328434cb1093", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 6, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testComputeFileStats(): Boolean = {\n  \n  // Set file path\n  val filePath = \"/mnt/training/global-sales/transactions/2017.parquet\"\n  \n  // Run and get output\n  val output = computeFileStats(filePath)\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test if result is correct structure\n  try {\n    assert(output.getClass.getName.startsWith(\"scala.Tuple\"))\n    assert(output.productArity == 2)\n    assert(output._1.isInstanceOf[Long])\n    assert(output._2.isInstanceOf[Long])\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The incorrect structure is returned for computeFileStats\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the incorrect structure being returned for computeFileStats\")\n    }\n  }\n  \n  // Test that correct result is returned\n  try {\n    assert(output._1 == 6276)\n    assert(output._2 == 1269333224)\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"The incorrect result is returned for computeFileStats\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for the incorrect result being returned for computeFileStats\")\n    }\n  }\n  \n  // Test that nonexistent file path throws error\n  try {\n    computeFileStats(\"alkshdahdnoinscoinwincwinecw/cw/cw/cd/c/wcdwdfobnwef\")\n    passedTest(false, \"A nonexistent file path did not throw an error for computeFileStats\")\n  } catch {\n    case a: java.io.FileNotFoundException => {\n      passedTest(true)\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for a nonexistent file path throwing an error for computeFileStats\")\n    }\n  }\n  \n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for computeFileStats passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for computeFileStats passed\")\n    false\n  }\n}\n\nfunctionPassed(testComputeFileStats())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "904bd134-ba49-4885-9f1d-87aab2d1c71d", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "a03cb60f-31b2-4d9c-b6db-a916607e78f0", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 7, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `cacheAs`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "e7cec82f-acbe-4281-945c-08d5c7071314", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "7b9159d9-b99f-4dec-9dfe-af72c4b175c2", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 8, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testCacheAs(): Boolean = {\n  \n  import org.apache.spark.storage.StorageLevel\n  // Import DF\n  val inputDF = spark.read.parquet(\"/mnt/training/global-sales/transactions/2017.parquet\").limit(100)\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test uncached table gets cached\n  try {\n    cacheAs(inputDF, \"testCacheTable12344321\", StorageLevel.MEMORY_ONLY)\n    assert(spark.catalog.isCached(\"testCacheTable12344321\"))\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Uncached table was not cached for cacheAs\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for uncached table being cached for cacheAs\")\n    }\n  }\n  \n  // Test cached table gets recached\n  try {\n    cacheAs(inputDF, \"testCacheTable12344321\", StorageLevel.MEMORY_ONLY)\n    assert(spark.catalog.isCached(\"testCacheTable12344321\"))\n    spark.catalog.uncacheTable(\"testCacheTable12344321\")\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Cached table was not recached for cacheAs\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for cached table being recached for cacheAs\")\n    }\n  }\n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for cacheAs passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for cacheAs passed\")\n    false\n  }\n}\n\nfunctionPassed(testCacheAs())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "21662fb8-20f4-4beb-b497-6665509f1170", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "d5d4e11c-5b7f-4d15-b003-3bff18eb2b63", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 9, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test `benchmarkCount()`", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "3d7d4296-0204-49be-ac13-e37b90f359cb", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "f4d40001-20bf-42b7-9eff-ce78d2e88d9e", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 10, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "def testBenchmarkCount(): Boolean = {\n  \n  def testFunction() = {\n    spark.createDataFrame(Seq((1, 2, 3, 4), (5, 6, 7, 8), (9, 10, 11, 12)))\n  }\n  val output = benchmarkCount(testFunction)\n  \n  // Setup tests\n  import scala.collection.mutable.ArrayBuffer\n  val testsPassed = ArrayBuffer.empty[Boolean]\n  \n  def passedTest(result: Boolean, message: String = null) = {\n    if (result) {\n      testsPassed += true\n    } else {\n      testsPassed += false\n      println(s\"Failed Test: $message\")\n    } \n  }\n  \n  \n  // Test that correct structure is returned\n  try {\n    assert(output.getClass.getName.startsWith(\"scala.Tuple\"))\n    assert(output.productArity == 3)\n    assert(output._1.isInstanceOf[org.apache.spark.sql.DataFrame])\n    assert(output._2.isInstanceOf[Long])\n    assert(output._3.isInstanceOf[Long])\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Correct structure not returned for benchmarkCount\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for correct structure being returned for benchmarkCount\")\n    }\n  }\n  \n  // Test that correct result is returned\n  try {\n    assert(output._1.rdd.collect().deep == testFunction().rdd.collect().deep)\n    assert(output._2 == testFunction().count())\n    assert(output._3 > 0)\n    assert(output._3 < 10000)\n    passedTest(true)\n  } catch {\n    case a: AssertionError => {\n      passedTest(false, \"Uncached table was not cached for cacheAs\")\n    }\n    case _: Exception => {\n      passedTest(false, \"non-descript error for uncached table being cached for cacheAs\")\n    }\n  }\n\n  val numTestsPassed = testsPassed.groupBy(identity).mapValues(_.size)(true)\n  if (numTestsPassed == testsPassed.length) {\n    println(s\"All $numTestsPassed tests for benchmarkCount passed\")\n    true\n  } else {\n    println(s\"$numTestsPassed of ${testsPassed.length} tests for benchmarkCount passed\")\n    false\n  }\n}\n\nfunctionPassed(testBenchmarkCount())", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "250846f6-068b-45cf-949c-11724b178ca7", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "f62ef641-dacb-4ebd-b15a-2929ad483f58", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 11, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%md\n\n## Test **`untilStreamIsReady()`**", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "716f6522-6fa1-49bf-9635-4a979d224e73", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "de281fbe-9d51-403a-82bb-2557b31093bb", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 12, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val dataPath = \"dbfs:/mnt/training/definitive-guide/data/activity-data-stream.json\"\nval dataSchema = \"Recorded_At timestamp, Device string, Index long, Model string, User string, _corrupt_record String, gt string, x double, y double, z double\"\n\nval initialDF = spark\n  .readStream                             // Returns DataStreamReader\n  .option(\"maxFilesPerTrigger\", 1)        // Force processing of only 1 file per trigger \n  .schema(dataSchema)                     // Required for all streaming DataFrames\n  .json(dataPath)                         // The stream's source directory and file type\n\nval name = \"Testing_123\"\n\ndisplay(initialDF, streamName = name)\nuntilStreamIsReady(name)\nassert(spark.streams.active.length == 1, \"Expected 1 active stream, found \" + spark.streams.active.length)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "9f2224d5-2a15-4526-acc2-a941a00bf8bc", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "e509575e-1520-420b-b077-57c16cb51d53", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 13, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "for (stream <- spark.streams.active) {\n  stream.stop()\n  var queries = spark.streams.active.filter(_.name == stream.name)\n  while (queries.length > 0) {\n    Thread.sleep(5*1000) // Give it a couple of seconds\n    queries = spark.streams.active.filter(_.name == stream.name)\n  }\n  println(\"\"\"The stream \"%s\" has beend terminated.\"\"\".format(stream.name))\n}", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "740a358f-c7ac-43cd-b682-9487f80f71c7", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "19b4ef4e-b4ad-4c78-8d19-3a31faaac2ba", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 14, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val numTestsPassed = scalaTests.groupBy(identity).mapValues(_.size)(true)\nif (numTestsPassed == scalaTests.length) {\n  println(s\"All $numTestsPassed tests for Scala passed\")\n} else {\n  println(s\"$numTestsPassed of ${scalaTests.length} tests for Scala passed\")\n  throw new Exception(s\"$numTestsPassed of ${scalaTests.length} tests for Scala passed\")\n}\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "42fa6366-2a6f-498f-8097-f75004004650", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "15bbae37-9fe3-4304-b91a-6a91ed4ad8e3", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 15, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "ddb2e5c0-faa4-4b63-bfb3-8c37734ef764", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "Utility-Methods-Test", "origId": 0, "version": "NotebookV1"}